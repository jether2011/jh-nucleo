package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.VentoVmFaixaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VentoVmFaixa} and its DTO {@link VentoVmFaixaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VentoVmFaixaMapper extends EntityMapper<VentoVmFaixaDTO, VentoVmFaixa> {



    default VentoVmFaixa fromId(Long id) {
        if (id == null) {
            return null;
        }
        VentoVmFaixa ventoVmFaixa = new VentoVmFaixa();
        ventoVmFaixa.setId(id);
        return ventoVmFaixa;
    }
}
