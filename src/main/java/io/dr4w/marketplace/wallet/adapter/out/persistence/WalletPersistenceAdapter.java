package io.dr4w.marketplace.wallet.adapter.out.persistence;

import io.dr4w.marketplace.wallet.domain.model.Wallet;
import io.dr4w.marketplace.wallet.domain.port.out.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletPersistenceAdapter implements WalletRepository {

    private final WalletJpaRepository jpaRepository;

    @Override
    public Wallet save(Wallet wallet) {
        return jpaRepository.save(WalletEntity.fromDomain(wallet)).toDomain();
    }

    @Override
    public Optional<Wallet> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).map(WalletEntity::toDomain);
    }

    @Override
    public Wallet findOrCreateByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId)
                .map(WalletEntity::toDomain)
                .orElseGet(() -> {
                    Instant now = Instant.now();
                    Wallet wallet = Wallet.builder()
                            .id(UUID.randomUUID())
                            .userId(userId)
                            .balance(BigDecimal.ZERO)
                            .createdAt(now)
                            .updatedAt(now)
                            .build();
                    return save(wallet);
                });
    }
}
