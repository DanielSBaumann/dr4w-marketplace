package io.dr4w.marketplace.identity;

import io.dr4w.marketplace.AbstractIntegrationTest;
import io.dr4w.marketplace.identity.adapter.in.web.dto.AuthResponse;
import io.dr4w.marketplace.identity.adapter.in.web.dto.LoginRequest;
import io.dr4w.marketplace.identity.adapter.in.web.dto.RegisterRequest;
import io.dr4w.marketplace.identity.domain.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class AuthControllerIT extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void registerAndLoginSuccessfully() {
        var register = new RegisterRequest("Test Buyer", "buyer@test.com", "Test@12345", UserRole.BUYER);
        ResponseEntity<AuthResponse> registerResponse = restTemplate.postForEntity(
                "/api/v1/auth/register", register, AuthResponse.class);

        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(registerResponse.getBody()).isNotNull();
        assertThat(registerResponse.getBody().accessToken()).isNotBlank();

        var login = new LoginRequest("buyer@test.com", "Test@12345");
        ResponseEntity<AuthResponse> loginResponse = restTemplate.postForEntity(
                "/api/v1/auth/login", login, AuthResponse.class);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResponse.getBody()).isNotNull();
        assertThat(loginResponse.getBody().accessToken()).isNotBlank();
    }

    @Test
    void registerWithDuplicateEmailReturnConflict() {
        var request = new RegisterRequest("Dup User", "duplicate@test.com", "Test@12345", UserRole.BUYER);
        restTemplate.postForEntity("/api/v1/auth/register", request, AuthResponse.class);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                "/api/v1/auth/register", request, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void loginWithWrongPasswordReturnsUnauthorized() {
        var register = new RegisterRequest("Valid User", "valid@test.com", "Test@12345", UserRole.BUYER);
        restTemplate.postForEntity("/api/v1/auth/register", register, AuthResponse.class);

        var login = new LoginRequest("valid@test.com", "WrongPassword");
        ResponseEntity<Void> response = restTemplate.postForEntity(
                "/api/v1/auth/login", login, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
