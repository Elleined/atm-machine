package com.elleined.atmmachineapi.dto;

import com.elleined.atmmachineapi.controller.ATMController;
import com.elleined.atmmachineapi.controller.TransactionController;
import com.elleined.atmmachineapi.controller.UserController;
import com.elleined.atmmachineapi.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
@Setter
public class UserDTO extends DTO {
    private String name;
    private String uuid;
    private BigDecimal balance;

    @Builder
    public UserDTO(int id, String name, String uuid, BigDecimal balance) {
        super(id);
        this.name = name;
        this.uuid = uuid;
        this.balance = balance;
    }

    @Override
    public UserDTO addLinks(User currentUser, boolean doInclude) {
        super.addLinks(currentUser, doInclude);
        return this;
    }

    @Override
    protected List<Link> getAllRelatedLinks(User currentUser, boolean doInclude) {
        return List.of(
                linkTo(methodOn(ATMController.class)
                        .withdraw(currentUser.getId(), null, doInclude))
                        .withRel("withdraw")
                        .withType(HttpMethod.POST.name()),

                linkTo(methodOn(TransactionController.class)
                        .getAllWithdrawalTransactions(currentUser.getId(), 0, 0, null, null, doInclude))
                        .withRel("withdraw")
                        .withTitle("Get all withdraw transactions")
                        .withType(HttpMethod.GET.name()),

                linkTo(methodOn(ATMController.class)
                        .deposit(currentUser.getId(), null, doInclude))
                        .withRel("deposit")
                        .withType(HttpMethod.POST.name()),

                linkTo(methodOn(TransactionController.class)
                        .getAllDepositTransactions(currentUser.getId(), 0, 0, null, null, doInclude))
                        .withRel("deposit")
                        .withTitle("Get all deposit transactions")
                        .withType(HttpMethod.GET.name()),

                linkTo(methodOn(ATMController.class)
                        .peerToPeer(currentUser.getId(), null, 0, doInclude))
                        .withRel("peer to peer")
                        .withType(HttpMethod.POST.name()),

                linkTo(methodOn(TransactionController.class)
                        .getAllReceiveMoneyTransactions(currentUser.getId(), 0, 0, null, null, doInclude))
                        .withRel("peer to peer")
                        .withTitle("Get all sent money transactions")
                        .withType(HttpMethod.GET.name()),

                linkTo(methodOn(TransactionController.class)
                        .getAllReceiveMoneyTransactions(currentUser.getId(), 0, 0, null, null, doInclude))
                        .withRel("peer to peer")
                        .withTitle("Get all received money transactions")
                        .withType(HttpMethod.GET.name())
        );
    }

    @Override
    protected List<Link> getAllSelfLinks(User currentUser, boolean doInclude) {
        return List.of(
                linkTo(methodOn(UserController.class)
                        .getById(currentUser.getId(), doInclude))
                        .withSelfRel()
                        .withTitle("Get by id")
                        .withType(HttpMethod.GET.name()),

                linkTo(methodOn(UserController.class)
                        .getByUUID(currentUser.getUuid(), doInclude))
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
