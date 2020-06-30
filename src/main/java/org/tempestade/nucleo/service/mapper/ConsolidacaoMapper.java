package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.ConsolidacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Consolidacao} and its DTO {@link ConsolidacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanoRecursoMapper.class})
public interface ConsolidacaoMapper extends EntityMapper<ConsolidacaoDTO, Consolidacao> {

    @Mapping(source = "planoRecurso.id", target = "planoRecursoId")
    ConsolidacaoDTO toDto(Consolidacao consolidacao);

    @Mapping(source = "planoRecursoId", target = "planoRecurso")
    Consolidacao toEntity(ConsolidacaoDTO consolidacaoDTO);

    default Consolidacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Consolidacao consolidacao = new Consolidacao();
        consolidacao.setId(id);
        return consolidacao;
    }
}
