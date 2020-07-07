package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.AlvoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Alvo} and its DTO {@link AlvoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanoMapper.class})
public interface AlvoMapper extends EntityMapper<AlvoDTO, Alvo> {

    @Mapping(source = "plano.id", target = "planoId")
    AlvoDTO toDto(Alvo alvo);

    @Mapping(source = "planoId", target = "plano")
    Alvo toEntity(AlvoDTO alvoDTO);

    default Alvo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Alvo alvo = new Alvo();
        alvo.setId(id);
        return alvo;
    }
}
