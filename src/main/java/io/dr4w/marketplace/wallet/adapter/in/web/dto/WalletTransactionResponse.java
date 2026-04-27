package io.dr4w.marketplace.wallet.adapter.in.web.dto;

import io.dr4w.marketplace.wallet.domain.model.TransactionType;
import io.dr4w.marketplace.wallet.domain.model.WalletTransaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record WalletTransactionResponse(
        UUID id,
        BigDecimal amount,
        TransactionType type,
        String description,
        UUID referenceId,
        Instant createdAt
) {
    public static WalletTransactionResponse from(WalletTransaction tx) {
        return new WalletTransactionResponse(
                tx.getId(),
                tx.getAmount(),
                tx.getType(),
                tx.getDescription(),
                tx.getReferenceId(),
                tx.getCreatedAt()
        );
    }
}
