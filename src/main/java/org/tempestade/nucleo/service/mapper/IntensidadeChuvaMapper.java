package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.IntensidadeChuvaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IntensidadeChuva} and its DTO {@link IntensidadeChuvaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IntensidadeChuvaMapper extends EntityMapper<IntensidadeChuvaDTO, IntensidadeChuva> {



    default IntensidadeChuva fromId(Long id) {
        if (id == null) {
            return null;
        }
        IntensidadeChuva intensidadeChuva = new IntensidadeChuva();
        intensidadeChuva.setId(id);
        return intensidadeChuva;
    }
}
