package com.elleined.atmmachineapi.hateoas.transaction;

import com.elleined.atmmachineapi.dto.transaction.PeerToPeerTransactionDTO;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeerToPeerTransactionHateoasAssembler extends TransactionHateoasAssembler<PeerToPeerTransactionDTO> {
    private final Faker faker;

    @Override
    protected List<Link> getAllRelatedLinks(PeerToPeerTransactionDTO dto, boolean doInclude) {
        return List.of();
    }

    @Override
    protected List<Link> getAllSelfLinks(PeerToPeerTransactionDTO dto, boolean doInclude) {
        return List.of();
    }
}
