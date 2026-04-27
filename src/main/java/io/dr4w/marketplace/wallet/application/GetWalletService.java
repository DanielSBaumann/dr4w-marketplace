package io.dr4w.marketplace.wallet.application;

import io.dr4w.marketplace.wallet.domain.model.Wallet;
import io.dr4w.marketplace.wallet.domain.port.in.GetWalletUseCase;
import io.dr4w.marketplace.wallet.domain.port.out.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetWalletService implements GetWalletUseCase {

    private final WalletRepository walletRepository;

    @Override
    @Transactional(readOnly = true)
    public Wallet execute(Query query) {
        return walletRepository.findOrCreateByUserId(query.userId());
    }
}
