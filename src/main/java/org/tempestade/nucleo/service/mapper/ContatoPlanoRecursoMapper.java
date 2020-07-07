package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.ContatoPlanoRecursoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContatoPlanoRecurso} and its DTO {@link ContatoPlanoRecursoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContatoMapper.class, PlanoRecursoMapper.class})
public interface ContatoPlanoRecursoMapper extends EntityMapper<ContatoPlanoRecursoDTO, ContatoPlanoRecurso> {

    @Mapping(source = "contato.id", target = "contatoId")
    @Mapping(source = "planoRecurso.id", target = "planoRecursoId")
    ContatoPlanoRecursoDTO toDto(ContatoPlanoRecurso contatoPlanoRecurso);

    @Mapping(source = "contatoId", target = "contato")
    @Mapping(source = "planoRecursoId", target = "planoRecurso")
    ContatoPlanoRecurso toEntity(ContatoPlanoRecursoDTO contatoPlanoRecursoDTO);

    default ContatoPlanoRecurso fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContatoPlanoRecurso contatoPlanoRecurso = new ContatoPlanoRecurso();
        contatoPlanoRecurso.setId(id);
        return contatoPlanoRecurso;
    }
}
