package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.TipoEnvioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoEnvio} and its DTO {@link TipoEnvioDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoEnvioMapper extends EntityMapper<TipoEnvioDTO, TipoEnvio> {



    default TipoEnvio fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoEnvio tipoEnvio = new TipoEnvio();
        tipoEnvio.setId(id);
        return tipoEnvio;
    }
}
