package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.MeteogramaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Meteograma} and its DTO {@link MeteogramaDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanoMapper.class})
public interface MeteogramaMapper extends EntityMapper<MeteogramaDTO, Meteograma> {

    @Mapping(source = "plano.id", target = "planoId")
    MeteogramaDTO toDto(Meteograma meteograma);

    @Mapping(source = "planoId", target = "plano")
    Meteograma toEntity(MeteogramaDTO meteogramaDTO);

    default Meteograma fromId(Long id) {
        if (id == null) {
            return null;
        }
        Meteograma meteograma = new Meteograma();
        meteograma.setId(id);
        return meteograma;
    }
}
