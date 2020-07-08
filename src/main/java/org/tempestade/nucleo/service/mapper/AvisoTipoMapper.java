package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.AvisoTipoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AvisoTipo} and its DTO {@link AvisoTipoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AvisoTipoMapper extends EntityMapper<AvisoTipoDTO, AvisoTipo> {



    default AvisoTipo fromId(Long id) {
        if (id == null) {
            return null;
        }
        AvisoTipo avisoTipo = new AvisoTipo();
        avisoTipo.setId(id);
        return avisoTipo;
    }
}
