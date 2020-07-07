package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.PlanoStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoStatus} and its DTO {@link PlanoStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlanoStatusMapper extends EntityMapper<PlanoStatusDTO, PlanoStatus> {



    default PlanoStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlanoStatus planoStatus = new PlanoStatus();
        planoStatus.setId(id);
        return planoStatus;
    }
}
