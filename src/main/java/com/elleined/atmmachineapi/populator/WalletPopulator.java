package com.elleined.atmmachineapi.populator;

import com.elleined.atmmachineapi.model.AppWallet;
import com.elleined.atmmachineapi.repository.AppWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletPopulator implements Populator {
    private final AppWalletRepository appWalletRepository;

    @Override
    public void populate() {
        AppWallet appWallet = AppWallet.builder()
                .balance(new BigDecimal(0))
                .build();

        appWalletRepository.save(appWallet);
    }
}
