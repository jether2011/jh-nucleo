package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.InformativoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Informativo} and its DTO {@link InformativoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanoRecursoMapper.class})
public interface InformativoMapper extends EntityMapper<InformativoDTO, Informativo> {

    @Mapping(source = "planoRecurso.id", target = "planoRecursoId")
    InformativoDTO toDto(Informativo informativo);

    @Mapping(source = "planoRecursoId", target = "planoRecurso")
    Informativo toEntity(InformativoDTO informativoDTO);

    default Informativo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Informativo informativo = new Informativo();
        informativo.setId(id);
        return informativo;
    }
}
