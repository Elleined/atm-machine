package com.elleined.atmmachineapi.hateoas;

import com.elleined.atmmachineapi.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHateoasAssembler extends HateoasAssembler<UserDTO> {

    @Override
    protected List<Link> getAllRelatedLinks(UserDTO dto, boolean doInclude) {
        return List.of();
    }

    @Override
    protected List<Link> getAllSelfLinks(UserDTO dto, boolean doInclude) {
        return List.of();
    }
}
