package com.elleined.atmmachineapi.hateoas.transaction;

import com.elleined.atmmachineapi.controller.TransactionController;
import com.elleined.atmmachineapi.dto.transaction.PeerToPeerTransactionDTO;
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
public class PeerToPeerTransactionHateoasAssembler extends TransactionHateoasAssembler<PeerToPeerTransactionDTO> {

    @Override
    protected List<Link> getAllRelatedLinks(PeerToPeerTransactionDTO dto, boolean doInclude) {
        return List.of();
    }

    @Override
    protected List<Link> getAllSelfLinks(PeerToPeerTransactionDTO dto, boolean doInclude) {
        return List.of(
                linkTo(methodOn(TransactionController.class)
                        .getAllReceiveMoneyTransactions(0, 0, 0, null, null, doInclude))
                        .withSelfRel()
                        .withTitle("Get all sent money transactions")
                        .withType(HttpMethod.GET.name()),

                linkTo(methodOn(TransactionController.class)
                        .getAllReceiveMoneyTransactions(0, 0, 0, null, null, doInclude))
                        .withSelfRel()
                        .withTitle("Get all received money transactions")
                        .withType(HttpMethod.GET.name())
        );
    }
}
