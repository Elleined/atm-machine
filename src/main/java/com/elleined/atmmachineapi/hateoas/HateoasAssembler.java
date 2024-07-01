package com.elleined.atmmachineapi.hateoas;

import com.elleined.atmmachineapi.dto.DTO;
import com.elleined.atmmachineapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;

import java.util.Collection;
import java.util.List;

public abstract class HateoasAssembler<T extends DTO>  {
    public void addLinks(User currentUser, T dto, boolean doInclude) {
        dto.addAllIf(doInclude, () -> getAllSelfLinks(currentUser, dto, doInclude));
        dto.addAllIf(doInclude, () -> getAllRelatedLinks(currentUser, dto, doInclude));
    }

    public void addLinks(User currentUser, Collection<T> dtos, boolean doInclude) {
        dtos.forEach(dto -> addLinks(currentUser, dto, doInclude));
    }

    public void addLinks(User currentUser, Page<T> dtos, boolean doInclude) {
        dtos.forEach(dto -> addLinks(currentUser, dto, doInclude));
    }

    protected abstract List<Link> getAllRelatedLinks(User currentUser, T dto, boolean doInclude);
    protected abstract List<Link> getAllSelfLinks(User currentUser, T dto, boolean doInclude);
}