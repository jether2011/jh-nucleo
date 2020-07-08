package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.DiaSemanaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DiaSemana} and its DTO {@link DiaSemanaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DiaSemanaMapper extends EntityMapper<DiaSemanaDTO, DiaSemana> {



    default DiaSemana fromId(Long id) {
        if (id == null) {
            return null;
        }
        DiaSemana diaSemana = new DiaSemana();
        diaSemana.setId(id);
        return diaSemana;
    }
}
