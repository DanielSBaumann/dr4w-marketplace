package io.dr4w.marketplace.identity.adapter.out.persistence;

import io.dr4w.marketplace.identity.domain.model.Email;
import io.dr4w.marketplace.identity.domain.model.User;
import io.dr4w.marketplace.identity.domain.model.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, unique = true, length = 200)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 200)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;

    @Column(length = 11)
    private String cpf;

    @Column(length = 11)
    private String phone;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "zip_code", length = 8)
    private String zipCode;

    @Column(length = 2)
    private String state;

    @Column(length = 200)
    private String city;

    @Column(length = 200)
    private String neighborhood;

    @Column(length = 200)
    private String street;

    @Column(name = "street_number", length = 20)
    private String streetNumber;

    @Column(length = 200)
    private String complement;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public User toDomain() {
        return User.builder()
                .id(id)
                .name(name)
                .email(new Email(email))
                .passwordHash(passwordHash)
                .role(role)
                .cpf(cpf)
                .phone(phone)
                .birthDate(birthDate)
                .zipCode(zipCode)
                .state(state)
                .city(city)
                .neighborhood(neighborhood)
                .street(street)
                .streetNumber(streetNumber)
                .complement(complement)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static UserEntity fromDomain(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail().value())
                .passwordHash(user.getPasswordHash())
                .role(user.getRole())
                .cpf(user.getCpf())
                .phone(user.getPhone())
                .birthDate(user.getBirthDate())
                .zipCode(user.getZipCode())
                .state(user.getState())
                .city(user.getCity())
                .neighborhood(user.getNeighborhood())
                .street(user.getStreet())
                .streetNumber(user.getStreetNumber())
                .complement(user.getComplement())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
