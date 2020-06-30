package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.AlertaRiscoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AlertaRisco} and its DTO {@link AlertaRiscoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlertaRiscoMapper extends EntityMapper<AlertaRiscoDTO, AlertaRisco> {



    default AlertaRisco fromId(Long id) {
        if (id == null) {
            return null;
        }
        AlertaRisco alertaRisco = new AlertaRisco();
        alertaRisco.setId(id);
        return alertaRisco;
    }
}
