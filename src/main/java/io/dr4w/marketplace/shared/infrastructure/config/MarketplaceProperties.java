package io.dr4w.marketplace.shared.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "marketplace")
public record MarketplaceProperties(JwtProperties jwt, PlatformProperties platform) {

    public record JwtProperties(String secret, long expirationMs, long refreshExpirationMs) {}

    public record PlatformProperties(int feePercentage) {}
}
