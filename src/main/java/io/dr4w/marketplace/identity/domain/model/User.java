package io.dr4w.marketplace.identity.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class User {

    private final UUID id;
    private final String name;
    private final Email email;
    private final String passwordHash;
    private final UserRole role;
    private String cpf;
    private String phone;
    private LocalDate birthDate;
    private String zipCode;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String streetNumber;
    private String complement;
    private final Instant createdAt;
    private Instant updatedAt;

    public boolean isBuyer()  { return role == UserRole.BUYER; }
    public boolean isVendor() { return role == UserRole.VENDOR; }
    public boolean isAdmin()  { return role == UserRole.ADMIN; }

    public User updateProfile(String cpf, String phone, LocalDate birthDate,
                              String zipCode, String state, String city,
                              String neighborhood, String street, String streetNumber, String complement) {
        this.cpf          = cpf;
        this.phone        = phone;
        this.birthDate    = birthDate;
        this.zipCode      = zipCode;
        this.state        = state;
        this.city         = city;
        this.neighborhood = neighborhood;
        this.street       = street;
        this.streetNumber = streetNumber;
        this.complement   = complement;
        this.updatedAt    = Instant.now();
        return this;
    }
}
