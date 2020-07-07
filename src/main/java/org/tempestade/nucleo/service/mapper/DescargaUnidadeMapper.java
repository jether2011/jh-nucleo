package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.DescargaUnidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DescargaUnidade} and its DTO {@link DescargaUnidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DescargaUnidadeMapper extends EntityMapper<DescargaUnidadeDTO, DescargaUnidade> {



    default DescargaUnidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        DescargaUnidade descargaUnidade = new DescargaUnidade();
        descargaUnidade.setId(id);
        return descargaUnidade;
    }
}
