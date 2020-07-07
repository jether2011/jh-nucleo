package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.VariavelMeteorologicaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VariavelMeteorologica} and its DTO {@link VariavelMeteorologicaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VariavelMeteorologicaMapper extends EntityMapper<VariavelMeteorologicaDTO, VariavelMeteorologica> {



    default VariavelMeteorologica fromId(Long id) {
        if (id == null) {
            return null;
        }
        VariavelMeteorologica variavelMeteorologica = new VariavelMeteorologica();
        variavelMeteorologica.setId(id);
        return variavelMeteorologica;
    }
}
