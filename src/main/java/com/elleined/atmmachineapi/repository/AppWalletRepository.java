package com.elleined.atmmachineapi.repository;

import com.elleined.atmmachineapi.model.AppWallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppWalletRepository extends JpaRepository<AppWallet, Integer> {
}