package io.dr4w.marketplace.wallet.adapter.out.persistence;

import io.dr4w.marketplace.wallet.domain.model.Wallet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "wallets")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class WalletEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal balance;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    @Builder.Default
    private List<WalletTransactionEntity> transactions = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    Wallet toDomain() {
        Wallet wallet = Wallet.builder()
                .id(id)
                .userId(userId)
                .balance(balance)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
        transactions.stream().map(WalletTransactionEntity::toDomain).forEach(wallet.getTransactions()::add);
        return wallet;
    }

    static WalletEntity fromDomain(Wallet wallet) {
        List<WalletTransactionEntity> txEntities = wallet.getTransactions().stream()
                .map(WalletTransactionEntity::fromDomain)
                .toList();
        return WalletEntity.builder()
                .id(wallet.getId())
                .userId(wallet.getUserId())
                .balance(wallet.getBalance())
                .transactions(new ArrayList<>(txEntities))
                .createdAt(wallet.getCreatedAt())
                .updatedAt(wallet.getUpdatedAt())
                .build();
    }
}
