package io.dr4w.marketplace.wallet.adapter.in.web.dto;

import io.dr4w.marketplace.wallet.domain.model.Wallet;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record WalletResponse(
        UUID id,
        UUID userId,
        BigDecimal balance,
        List<WalletTransactionResponse> transactions
) {
    public static WalletResponse from(Wallet wallet) {
        return new WalletResponse(
                wallet.getId(),
                wallet.getUserId(),
                wallet.getBalance(),
                wallet.getTransactions().stream().map(WalletTransactionResponse::from).toList()
        );
    }
}
