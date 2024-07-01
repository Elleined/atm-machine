package com.elleined.atmmachineapi.hateoas.transaction;

import com.elleined.atmmachineapi.controller.ATMController;
import com.elleined.atmmachineapi.controller.TransactionController;
import com.elleined.atmmachineapi.dto.transaction.WithdrawTransactionDTO;
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
public class WithdrawTransactionHateoasAssembler extends TransactionHateoasAssembler<WithdrawTransactionDTO> {

    @Override
    protected List<Link> getAllRelatedLinks(User currentUser, WithdrawTransactionDTO dto, boolean doInclude) {
        return List.of(
                linkTo(methodOn(ATMController.class)
                        .withdraw(currentUser.getId(), null, doInclude))
                        .withRel("withdraw")
                        .withType(HttpMethod.POST.name())
        );
    }

    @Override
    protected List<Link> getAllSelfLinks(User currentUser, WithdrawTransactionDTO dto, boolean doInclude) {
        return List.of(
                linkTo(methodOn(TransactionController.class)
                        .getAllWithdrawalTransactions(currentUser.getId(), 0, 0, null, null, doInclude))
                        .withSelfRel()
                        .withTitle("Get all withdraw transactions")
                        .withType(HttpMethod.GET.name())
        );
    }
}
