package com.elleined.atmmachineapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO extends DTO {
    private String name;

    private String uuid;

    private BigDecimal balance;

    List<Integer> sentMoneyTransactionIds;
    List<Integer> receiveMoneyTransactionIds;
    List<Integer> withdrawTransactionIds;
    List<Integer> depositTransactionIds;

    @Builder
    public UserDTO(int id, String name, String uuid, BigDecimal balance, List<Integer> sentMoneyTransactionIds, List<Integer> receiveMoneyTransactionIds, List<Integer> withdrawTransactionIds, List<Integer> depositTransactionIds) {
        super(id);
        this.name = name;
        this.uuid = uuid;
        this.balance = balance;
        this.sentMoneyTransactionIds = sentMoneyTransactionIds;
        this.receiveMoneyTransactionIds = receiveMoneyTransactionIds;
        this.withdrawTransactionIds = withdrawTransactionIds;
        this.depositTransactionIds = depositTransactionIds;
    }
}
