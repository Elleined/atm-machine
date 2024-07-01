package com.elleined.atmmachineapi.hateoas.transaction;

import com.elleined.atmmachineapi.controller.ATMController;
import com.elleined.atmmachineapi.controller.TransactionController;
import com.elleined.atmmachineapi.dto.transaction.DepositTransactionDTO;
import com.elleined.atmmachineapi.model.User;
import lombok.RequiredArgsConstructor;
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
    protected List<Link> getAllRelatedLinks(User currentUser, DepositTransactionDTO dto, boolean doInclude) {
        return List.of(
                linkTo(methodOn(ATMController.class)
                        .deposit(currentUser.getId(), null, doInclude))
                        .withRel("deposit")
                        .withType(HttpMethod.POST.name())
        );
    }

    @Override
    protected List<Link> getAllSelfLinks(User currentUser, DepositTransactionDTO dto, boolean doInclude) {
        return List.of(
                linkTo(methodOn(TransactionController.class)
                        .getAllDepositTransactions(currentUser.getId(), 0, 0, null, null, doInclude))
                        .withSelfRel()
                        .withTitle("Get all deposit transactions")
                        .withType(HttpMethod.GET.name())
        );
    }
}
