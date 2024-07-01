package com.elleined.atmmachineapi.dto.transaction;

import com.elleined.atmmachineapi.dto.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class WithdrawTransactionDTO extends TransactionDTO {
    private UserDTO userDTO;

    @Builder
    public WithdrawTransactionDTO(int id, String trn, BigDecimal amount, LocalDateTime transactionDate, UserDTO userDTO) {
        super(id, trn, amount, transactionDate);
        this.userDTO = userDTO;
    }
}
