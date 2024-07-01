package com.elleined.atmmachineapi.hateoas.transaction;

import com.elleined.atmmachineapi.dto.transaction.TransactionDTO;
import com.elleined.atmmachineapi.hateoas.HateoasAssembler;


public abstract class TransactionHateoasAssembler<T extends TransactionDTO> extends HateoasAssembler<T> {
}
