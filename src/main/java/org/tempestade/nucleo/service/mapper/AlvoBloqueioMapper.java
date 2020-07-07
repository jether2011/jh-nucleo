package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.AlvoBloqueioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AlvoBloqueio} and its DTO {@link AlvoBloqueioDTO}.
 */
@Mapper(componentModel = "spring", uses = {AlvoMapper.class})
public interface AlvoBloqueioMapper extends EntityMapper<AlvoBloqueioDTO, AlvoBloqueio> {

    @Mapping(source = "alvo.id", target = "alvoId")
    AlvoBloqueioDTO toDto(AlvoBloqueio alvoBloqueio);

    @Mapping(source = "alvoId", target = "alvo")
    AlvoBloqueio toEntity(AlvoBloqueioDTO alvoBloqueioDTO);

    default AlvoBloqueio fromId(Long id) {
        if (id == null) {
            return null;
        }
        AlvoBloqueio alvoBloqueio = new AlvoBloqueio();
        alvoBloqueio.setId(id);
        return alvoBloqueio;
    }
}
