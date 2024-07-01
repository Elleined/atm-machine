package com.elleined.atmmachineapi.hateoas.transaction;

import com.elleined.atmmachineapi.controller.ATMController;
import com.elleined.atmmachineapi.controller.TransactionController;
import com.elleined.atmmachineapi.dto.transaction.PeerToPeerTransactionDTO;
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
public class PeerToPeerTransactionHateoasAssembler extends TransactionHateoasAssembler<PeerToPeerTransactionDTO> {

    @Override
    protected List<Link> getAllRelatedLinks(User currentUser, PeerToPeerTransactionDTO dto, boolean doInclude) {
        return List.of(
                linkTo(methodOn(ATMController.class)
                        .peerToPeer(currentUser.getId(), null, 0, doInclude))
                        .withRel("peer to peer")
                        .withType(HttpMethod.POST.name())
        );
    }

    @Override
    protected List<Link> getAllSelfLinks(User currentUser, PeerToPeerTransactionDTO dto, boolean doInclude) {
        return List.of(
                linkTo(methodOn(TransactionController.class)
                        .getAllReceiveMoneyTransactions(currentUser.getId(), 0, 0, null, null, doInclude))
                        .withSelfRel()
                        .withTitle("Get all sent money transactions")
                        .withType(HttpMethod.GET.name()),

                linkTo(methodOn(TransactionController.class)
                        .getAllReceiveMoneyTransactions(currentUser.getId(), 0, 0, null, null, doInclude))
                        .withSelfRel()
                        .withTitle("Get all received money transactions")
                        .withType(HttpMethod.GET.name())
        );
    }
}
