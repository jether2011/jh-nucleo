package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.TipoFerramentaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoFerramenta} and its DTO {@link TipoFerramentaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoFerramentaMapper extends EntityMapper<TipoFerramentaDTO, TipoFerramenta> {



    default TipoFerramenta fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoFerramenta tipoFerramenta = new TipoFerramenta();
        tipoFerramenta.setId(id);
        return tipoFerramenta;
    }
}
