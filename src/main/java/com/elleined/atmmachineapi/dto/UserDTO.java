package com.elleined.atmmachineapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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
}
