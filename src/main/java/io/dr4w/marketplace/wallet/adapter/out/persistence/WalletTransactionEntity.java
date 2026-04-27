package io.dr4w.marketplace.wallet.adapter.out.persistence;

import io.dr4w.marketplace.wallet.domain.model.TransactionType;
import io.dr4w.marketplace.wallet.domain.model.WalletTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "wallet_transactions")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class WalletTransactionEntity {

    @Id
    private UUID id;

    @Column(name = "wallet_id", nullable = false)
    private UUID walletId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType type;

    @Column(length = 500)
    private String description;

    @Column(name = "reference_id")
    private UUID referenceId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    WalletTransaction toDomain() {
        return WalletTransaction.builder()
                .id(id)
                .walletId(walletId)
                .amount(amount)
                .type(type)
                .description(description)
                .referenceId(referenceId)
                .createdAt(createdAt)
                .build();
    }

    static WalletTransactionEntity fromDomain(WalletTransaction tx) {
        return WalletTransactionEntity.builder()
                .id(tx.getId())
                .walletId(tx.getWalletId())
                .amount(tx.getAmount())
                .type(tx.getType())
                .description(tx.getDescription())
                .referenceId(tx.getReferenceId())
                .createdAt(tx.getCreatedAt())
                .build();
    }
}
