package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.PrevisaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Previsao} and its DTO {@link PrevisaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PrevisaoMapper extends EntityMapper<PrevisaoDTO, Previsao> {



    default Previsao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Previsao previsao = new Previsao();
        previsao.setId(id);
        return previsao;
    }
}
