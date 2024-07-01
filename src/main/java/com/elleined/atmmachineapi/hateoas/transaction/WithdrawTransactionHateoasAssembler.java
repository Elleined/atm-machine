package com.elleined.atmmachineapi.hateoas.transaction;

import com.elleined.atmmachineapi.controller.TransactionController;
import com.elleined.atmmachineapi.dto.transaction.WithdrawTransactionDTO;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class WithdrawTransactionHateoasAssembler extends TransactionHateoasAssembler<WithdrawTransactionDTO> {

    @Override
    protected List<Link> getAllRelatedLinks(WithdrawTransactionDTO dto, boolean doInclude) {
        return List.of();
    }

    @Override
    protected List<Link> getAllSelfLinks(WithdrawTransactionDTO dto, boolean doInclude) {
        return List.of(
                linkTo(methodOn(TransactionController.class)
                        .getAllWithdrawalTransactions(dto.getUserDTO().getId(), 0, 0, null, null, doInclude))
                        .withSelfRel()
                        .withTitle("Get all withdraw transactions")
                        .withType(HttpMethod.GET.name())
        );
    }
}
