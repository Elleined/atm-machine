package com.elleined.atmmachineapi.hateoas.transaction;

import com.elleined.atmmachineapi.controller.TransactionController;
import com.elleined.atmmachineapi.dto.transaction.DepositTransactionDTO;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
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
public class DepositTransactionHateoasAssembler extends TransactionHateoasAssembler<DepositTransactionDTO> {
    @Override
    protected List<Link> getAllRelatedLinks(DepositTransactionDTO dto, boolean doInclude) {
        return List.of();
    }

    @Override
    protected List<Link> getAllSelfLinks(DepositTransactionDTO dto, boolean doInclude) {
        return List.of(
                linkTo(methodOn(TransactionController.class)
                        .getAllDepositTransactions(dto.getUserDTO().getId(), 0, 0, null, null, doInclude))
                        .withSelfRel()
                        .withTitle("Get all deposit transactions")
                        .withType(HttpMethod.GET.name())
        );
    }
}
