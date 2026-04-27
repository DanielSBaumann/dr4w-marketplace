package io.dr4w.marketplace.wallet.adapter.in.web;

import io.dr4w.marketplace.wallet.adapter.in.web.dto.WalletResponse;
import io.dr4w.marketplace.wallet.domain.port.in.GetWalletUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final GetWalletUseCase getWalletUseCase;

    @GetMapping
    public ResponseEntity<WalletResponse> getWallet(@AuthenticationPrincipal UserDetails user) {
        UUID userId = UUID.fromString(user.getUsername());
        var wallet = getWalletUseCase.execute(new GetWalletUseCase.Query(userId));
        return ResponseEntity.ok(WalletResponse.from(wallet));
    }
}
