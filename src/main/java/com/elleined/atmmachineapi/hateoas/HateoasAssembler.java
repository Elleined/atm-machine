package com.elleined.atmmachineapi.hateoas;

import com.elleined.atmmachineapi.dto.DTO;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;

import java.util.Collection;
import java.util.List;

public abstract class HateoasAssembler<T extends DTO>  {
    public void addLinks(T dto, boolean doInclude) {
        dto.addAllIf(doInclude, () -> getAllSelfLinks(dto, doInclude));
        dto.addAllIf(doInclude, () -> getAllRelatedLinks(dto, doInclude));
    }

    public void addLinks(Collection<T> dtos, boolean doInclude) {
        dtos.forEach(dto -> addLinks(dto, doInclude));
    }

    public void addLinks(Page<T> dtos, boolean doInclude) {
        dtos.forEach(dto -> addLinks(dto, doInclude));
    }

    protected abstract List<Link> getAllRelatedLinks(T dto, boolean doInclude);
    protected abstract List<Link> getAllSelfLinks(T dto, boolean doInclude);
}