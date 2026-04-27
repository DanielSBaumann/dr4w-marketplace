package io.dr4w.marketplace.wallet.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class Wallet {

    private final UUID id;
    private final UUID userId;
    private BigDecimal balance;
    @Builder.Default
    private final List<WalletTransaction> transactions = new ArrayList<>();
    private final Instant createdAt;
    private Instant updatedAt;

    public void credit(BigDecimal amount, String description, UUID referenceId) {
        this.balance = this.balance.add(amount);
        this.updatedAt = Instant.now();
        this.transactions.add(WalletTransaction.builder()
                .id(UUID.randomUUID())
                .walletId(this.id)
                .amount(amount)
                .type(TransactionType.CREDIT)
                .description(description)
                .referenceId(referenceId)
                .createdAt(Instant.now())
                .build());
    }

    public void debit(BigDecimal amount, String description, UUID referenceId) {
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }
        this.balance = this.balance.subtract(amount);
        this.updatedAt = Instant.now();
        this.transactions.add(WalletTransaction.builder()
                .id(UUID.randomUUID())
                .walletId(this.id)
                .amount(amount)
                .type(TransactionType.DEBIT)
                .description(description)
                .referenceId(referenceId)
                .createdAt(Instant.now())
                .build());
    }
}
