package com.elleined.atmmachineapi.dto.transaction;


import com.elleined.atmmachineapi.dto.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class DepositTransactionDTO extends TransactionDTO {
    private UserDTO userDTO;
}
