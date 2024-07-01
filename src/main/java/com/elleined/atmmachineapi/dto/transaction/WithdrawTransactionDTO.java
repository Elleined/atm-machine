package com.elleined.atmmachineapi.dto.transaction;

import com.elleined.atmmachineapi.controller.ATMController;
import com.elleined.atmmachineapi.controller.TransactionController;
import com.elleined.atmmachineapi.dto.UserDTO;
import com.elleined.atmmachineapi.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
@Setter
public class WithdrawTransactionDTO extends TransactionDTO {
    private UserDTO userDTO;

    @Builder
    public WithdrawTransactionDTO(int id, String trn, BigDecimal amount, LocalDateTime transactionDate, UserDTO userDTO) {
        super(id, trn, amount, transactionDate);
        this.userDTO = userDTO;
    }

    @Override
    public WithdrawTransactionDTO addLinks(User currentUser, boolean doInclude) {
        super.addLinks(currentUser, doInclude);

        return this;
    }

    @Override
    protected List<Link> getAllRelatedLinks(User currentUser, boolean doInclude) {
        return List.of(
                linkTo(methodOn(ATMController.class)
                        .withdraw(currentUser.getId(), null, doInclude))
                        .withRel("withdraw")
                        .withType(HttpMethod.POST.name())
        );
    }

    @Override
    protected List<Link> getAllSelfLinks(User currentUser, boolean doInclude) {
        return List.of(
                linkTo(methodOn(TransactionController.class)
                        .getAllWithdrawalTransactions(currentUser.getId(), 0, 0, null, null, doInclude))
                        .withSelfRel()
                        .withTitle("Get all withdraw transactions")
                        .withType(HttpMethod.GET.name())
        );
    }
}
