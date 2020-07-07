package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.AlertaFerramentaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AlertaFerramenta} and its DTO {@link AlertaFerramentaDTO}.
 */
@Mapper(componentModel = "spring", uses = {AlertaMapper.class, FerramentaMapper.class})
public interface AlertaFerramentaMapper extends EntityMapper<AlertaFerramentaDTO, AlertaFerramenta> {

    @Mapping(source = "alerta.id", target = "alertaId")
    @Mapping(source = "ferramenta.id", target = "ferramentaId")
    AlertaFerramentaDTO toDto(AlertaFerramenta alertaFerramenta);

    @Mapping(source = "alertaId", target = "alerta")
    @Mapping(source = "ferramentaId", target = "ferramenta")
    AlertaFerramenta toEntity(AlertaFerramentaDTO alertaFerramentaDTO);

    default AlertaFerramenta fromId(Long id) {
        if (id == null) {
            return null;
        }
        AlertaFerramenta alertaFerramenta = new AlertaFerramenta();
        alertaFerramenta.setId(id);
        return alertaFerramenta;
    }
}
