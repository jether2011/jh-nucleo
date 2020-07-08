package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.AcumuladoChuvaFaixaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AcumuladoChuvaFaixa} and its DTO {@link AcumuladoChuvaFaixaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AcumuladoChuvaFaixaMapper extends EntityMapper<AcumuladoChuvaFaixaDTO, AcumuladoChuvaFaixa> {



    default AcumuladoChuvaFaixa fromId(Long id) {
        if (id == null) {
            return null;
        }
        AcumuladoChuvaFaixa acumuladoChuvaFaixa = new AcumuladoChuvaFaixa();
        acumuladoChuvaFaixa.setId(id);
        return acumuladoChuvaFaixa;
    }
}
