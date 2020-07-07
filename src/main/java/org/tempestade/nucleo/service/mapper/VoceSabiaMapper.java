package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.VoceSabiaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VoceSabia} and its DTO {@link VoceSabiaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VoceSabiaMapper extends EntityMapper<VoceSabiaDTO, VoceSabia> {



    default VoceSabia fromId(Long id) {
        if (id == null) {
            return null;
        }
        VoceSabia voceSabia = new VoceSabia();
        voceSabia.setId(id);
        return voceSabia;
    }
}
