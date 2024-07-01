package com.elleined.atmmachineapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
public abstract class DTO extends RepresentationModel<DTO> {
    private int id;
}
