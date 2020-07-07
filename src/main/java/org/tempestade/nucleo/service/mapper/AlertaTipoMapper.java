package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.AlertaTipoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AlertaTipo} and its DTO {@link AlertaTipoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlertaTipoMapper extends EntityMapper<AlertaTipoDTO, AlertaTipo> {



    default AlertaTipo fromId(Long id) {
        if (id == null) {
            return null;
        }
        AlertaTipo alertaTipo = new AlertaTipo();
        alertaTipo.setId(id);
        return alertaTipo;
    }
}
