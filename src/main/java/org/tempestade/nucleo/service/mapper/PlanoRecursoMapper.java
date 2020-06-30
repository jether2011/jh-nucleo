package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.PlanoRecursoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoRecurso} and its DTO {@link PlanoRecursoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanoMapper.class, RecursoMapper.class})
public interface PlanoRecursoMapper extends EntityMapper<PlanoRecursoDTO, PlanoRecurso> {

    @Mapping(source = "plano.id", target = "planoId")
    @Mapping(source = "recurso.id", target = "recursoId")
    PlanoRecursoDTO toDto(PlanoRecurso planoRecurso);

    @Mapping(source = "planoId", target = "plano")
    @Mapping(source = "recursoId", target = "recurso")
    PlanoRecurso toEntity(PlanoRecursoDTO planoRecursoDTO);

    default PlanoRecurso fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlanoRecurso planoRecurso = new PlanoRecurso();
        planoRecurso.setId(id);
        return planoRecurso;
    }
}
