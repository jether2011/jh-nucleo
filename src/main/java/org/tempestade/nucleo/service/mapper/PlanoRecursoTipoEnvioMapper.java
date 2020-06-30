package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.PlanoRecursoTipoEnvioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoRecursoTipoEnvio} and its DTO {@link PlanoRecursoTipoEnvioDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanoRecursoMapper.class, TipoEnvioMapper.class})
public interface PlanoRecursoTipoEnvioMapper extends EntityMapper<PlanoRecursoTipoEnvioDTO, PlanoRecursoTipoEnvio> {

    @Mapping(source = "planoRecurso.id", target = "planoRecursoId")
    @Mapping(source = "tipoEnvio.id", target = "tipoEnvioId")
    PlanoRecursoTipoEnvioDTO toDto(PlanoRecursoTipoEnvio planoRecursoTipoEnvio);

    @Mapping(source = "planoRecursoId", target = "planoRecurso")
    @Mapping(source = "tipoEnvioId", target = "tipoEnvio")
    PlanoRecursoTipoEnvio toEntity(PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO);

    default PlanoRecursoTipoEnvio fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlanoRecursoTipoEnvio planoRecursoTipoEnvio = new PlanoRecursoTipoEnvio();
        planoRecursoTipoEnvio.setId(id);
        return planoRecursoTipoEnvio;
    }
}
