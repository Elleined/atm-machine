package com.elleined.atmmachineapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserDTO {
    private int id;

    @NotBlank(message = "User name cannot null, blank, or empty")
    private String name;

    @NotBlank(message = "User uuid cannot null, blank, or empty")
    private String uuid;

    private BigDecimal balance;
}
