package com.elleined.atmmachineapi.hateoas;

import com.elleined.atmmachineapi.controller.ATMController;
import com.elleined.atmmachineapi.controller.TransactionController;
import com.elleined.atmmachineapi.controller.UserController;
import com.elleined.atmmachineapi.dto.UserDTO;
import com.elleined.atmmachineapi.hateoas.transaction.WithdrawTransactionHateoasAssembler;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class UserHateoasAssembler extends HateoasAssembler<UserDTO> {

    @Override
    protected List<Link> getAllRelatedLinks(UserDTO dto, boolean doInclude) {
        return List.of(
                linkTo(methodOn(ATMController.class)
                        .withdraw(dto.getId(), null, doInclude))
                        .withRel("withdraw")
                        .withType(HttpMethod.POST.name()),

                linkTo(methodOn(TransactionController.class)
                        .getAllWithdrawalTransactions(dto.getId(), 0, 0, null, null, doInclude))
                        .withRel("withdraw")
                        .withTitle("Get all withdraw transactions")
                        .withType(HttpMethod.GET.name()),

                linkTo(methodOn(ATMController.class)
                        .deposit(dto.getId(), null, doInclude))
                        .withRel("deposit")
                        .withType(HttpMethod.POST.name()),

                linkTo(methodOn(TransactionController.class)
                        .getAllDepositTransactions(dto.getId(), 0, 0, null, null, doInclude))
                        .withRel("deposit")
                        .withTitle("Get all deposit transactions")
                        .withType(HttpMethod.GET.name()),

                linkTo(methodOn(ATMController.class)
                        .peerToPeer(dto.getId(), null, 0, doInclude))
                        .withRel("peer to peer")
                        .withType(HttpMethod.POST.name()),

                linkTo(methodOn(TransactionController.class)
                        .getAllReceiveMoneyTransactions(dto.getId(), 0, 0, null, null, doInclude))
                        .withRel("peer to peer")
                        .withTitle("Get all sent money transactions")
                        .withType(HttpMethod.GET.name()),

                linkTo(methodOn(TransactionController.class)
                        .getAllReceiveMoneyTransactions(dto.getId(), 0, 0, null, null, doInclude))
                        .withRel("peer to peer")
                        .withTitle("Get all received money transactions")
                        .withType(HttpMethod.GET.name())
        );
    }

    @Override
    protected List<Link> getAllSelfLinks(UserDTO dto, boolean doInclude) {
        return List.of(
                linkTo(methodOn(UserController.class)
                        .getById(dto.getId(), doInclude))
                        .withSelfRel()
                        .withTitle("Get by id")
                        .withType(HttpMethod.GET.name()),

                linkTo(methodOn(UserController.class)
                        .getByUUID(dto.getUuid(), doInclude))
                        .withSelfRel()
                        .withTitle("Get by uuid")
                        .withType(HttpMethod.GET.name()),

                linkTo(methodOn(UserController.class)
                        .save(null, doInclude))
                        .withSelfRel()
                        .withTitle("Save")
                        .withType(HttpMethod.POST.name())
        );
    }
}
