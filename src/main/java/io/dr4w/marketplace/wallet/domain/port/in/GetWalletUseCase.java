package io.dr4w.marketplace.wallet.domain.port.in;

import io.dr4w.marketplace.wallet.domain.model.Wallet;

import java.util.UUID;

public interface GetWalletUseCase {

    record Query(UUID userId) {}

    Wallet execute(Query query);
}
