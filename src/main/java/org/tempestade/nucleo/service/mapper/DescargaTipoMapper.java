package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.DescargaTipoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DescargaTipo} and its DTO {@link DescargaTipoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DescargaTipoMapper extends EntityMapper<DescargaTipoDTO, DescargaTipo> {



    default DescargaTipo fromId(Long id) {
        if (id == null) {
            return null;
        }
        DescargaTipo descargaTipo = new DescargaTipo();
        descargaTipo.setId(id);
        return descargaTipo;
    }
}
