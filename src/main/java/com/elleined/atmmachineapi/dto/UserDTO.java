package com.elleined.atmmachineapi.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserDTO {
    private int id;
    private String name;
    private String uuid;
    private BigDecimal balance;
}
