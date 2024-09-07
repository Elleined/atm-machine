package com.elleined.atmmachineapi.dto.transaction;

import com.elleined.atmmachineapi.dto.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class TransactionDTO extends DTO {
    private String trn;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
}
