package com.elleined.atmmachineapi.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

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
