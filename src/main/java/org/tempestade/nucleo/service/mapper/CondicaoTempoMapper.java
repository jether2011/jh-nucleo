package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.CondicaoTempoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CondicaoTempo} and its DTO {@link CondicaoTempoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CondicaoTempoMapper extends EntityMapper<CondicaoTempoDTO, CondicaoTempo> {



    default CondicaoTempo fromId(Long id) {
        if (id == null) {
            return null;
        }
        CondicaoTempo condicaoTempo = new CondicaoTempo();
        condicaoTempo.setId(id);
        return condicaoTempo;
    }
}
