package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.TempestadeNivelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TempestadeNivel} and its DTO {@link TempestadeNivelDTO}.
 */
@Mapper(componentModel = "spring", uses = {IntensidadeChuvaMapper.class})
public interface TempestadeNivelMapper extends EntityMapper<TempestadeNivelDTO, TempestadeNivel> {

    @Mapping(source = "intensidadeChuva.id", target = "intensidadeChuvaId")
    TempestadeNivelDTO toDto(TempestadeNivel tempestadeNivel);

    @Mapping(source = "intensidadeChuvaId", target = "intensidadeChuva")
    TempestadeNivel toEntity(TempestadeNivelDTO tempestadeNivelDTO);

    default TempestadeNivel fromId(Long id) {
        if (id == null) {
            return null;
        }
        TempestadeNivel tempestadeNivel = new TempestadeNivel();
        tempestadeNivel.setId(id);
        return tempestadeNivel;
    }
}
