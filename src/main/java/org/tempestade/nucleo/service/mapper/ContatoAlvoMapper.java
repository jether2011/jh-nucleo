package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.ContatoAlvoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContatoAlvo} and its DTO {@link ContatoAlvoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContatoMapper.class, AlvoMapper.class})
public interface ContatoAlvoMapper extends EntityMapper<ContatoAlvoDTO, ContatoAlvo> {

    @Mapping(source = "contato.id", target = "contatoId")
    @Mapping(source = "alvo.id", target = "alvoId")
    ContatoAlvoDTO toDto(ContatoAlvo contatoAlvo);

    @Mapping(source = "contatoId", target = "contato")
    @Mapping(source = "alvoId", target = "alvo")
    ContatoAlvo toEntity(ContatoAlvoDTO contatoAlvoDTO);

    default ContatoAlvo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContatoAlvo contatoAlvo = new ContatoAlvo();
        contatoAlvo.setId(id);
        return contatoAlvo;
    }
}
