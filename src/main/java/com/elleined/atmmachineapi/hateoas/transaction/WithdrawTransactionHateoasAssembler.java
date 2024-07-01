package com.elleined.atmmachineapi.hateoas.transaction;

import com.elleined.atmmachineapi.dto.transaction.WithdrawTransactionDTO;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WithdrawTransactionHateoasAssembler extends TransactionHateoasAssembler<WithdrawTransactionDTO> {
    private final Faker faker;

    @Override
    protected List<Link> getAllRelatedLinks(WithdrawTransactionDTO dto, boolean doInclude) {
        return List.of();
    }

    @Override
    protected List<Link> getAllSelfLinks(WithdrawTransactionDTO dto, boolean doInclude) {
        return List.of();
    }
}
