package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.TempestadeProbabilidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TempestadeProbabilidade} and its DTO {@link TempestadeProbabilidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TempestadeProbabilidadeMapper extends EntityMapper<TempestadeProbabilidadeDTO, TempestadeProbabilidade> {



    default TempestadeProbabilidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        TempestadeProbabilidade tempestadeProbabilidade = new TempestadeProbabilidade();
        tempestadeProbabilidade.setId(id);
        return tempestadeProbabilidade;
    }
}
