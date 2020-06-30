package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.RecursoTipoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RecursoTipo} and its DTO {@link RecursoTipoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RecursoTipoMapper extends EntityMapper<RecursoTipoDTO, RecursoTipo> {



    default RecursoTipo fromId(Long id) {
        if (id == null) {
            return null;
        }
        RecursoTipo recursoTipo = new RecursoTipo();
        recursoTipo.setId(id);
        return recursoTipo;
    }
}
