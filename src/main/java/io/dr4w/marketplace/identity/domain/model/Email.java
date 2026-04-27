package io.dr4w.marketplace.identity.domain.model;

import java.util.regex.Pattern;

public record Email(String value) {

    private static final Pattern PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public Email {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Email must not be blank");
        if (!PATTERN.matcher(value.trim()).matches()) throw new IllegalArgumentException("Invalid email format");
        value = value.trim().toLowerCase();
    }

    @Override
    public String toString() {
        return value;
    }
}
