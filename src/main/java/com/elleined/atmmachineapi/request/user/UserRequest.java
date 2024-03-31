package com.elleined.atmmachineapi.request.user;

import com.elleined.atmmachineapi.request.Request;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserRequest extends Request {

    @NotBlank(message = "User name cannot null, blank, or empty")
    private String name;

    @Builder
    public UserRequest(String name) {
        this.name = name;
    }
}
