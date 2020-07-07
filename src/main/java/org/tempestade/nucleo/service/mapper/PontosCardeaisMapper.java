package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.PontosCardeaisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PontosCardeais} and its DTO {@link PontosCardeaisDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PontosCardeaisMapper extends EntityMapper<PontosCardeaisDTO, PontosCardeais> {



    default PontosCardeais fromId(Long id) {
        if (id == null) {
            return null;
        }
        PontosCardeais pontosCardeais = new PontosCardeais();
        pontosCardeais.setId(id);
        return pontosCardeais;
    }
}
