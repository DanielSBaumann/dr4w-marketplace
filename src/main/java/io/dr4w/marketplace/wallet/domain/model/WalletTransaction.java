package io.dr4w.marketplace.wallet.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class WalletTransaction {

    private final UUID id;
    private final UUID walletId;
    private final BigDecimal amount;
    private final TransactionType type;
    private final String description;
    private final UUID referenceId;
    private final Instant createdAt;
}
