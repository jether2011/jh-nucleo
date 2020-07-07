package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.RecursoTemplateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RecursoTemplate} and its DTO {@link RecursoTemplateDTO}.
 */
@Mapper(componentModel = "spring", uses = {RecursoMapper.class, TipoEnvioMapper.class, AlertaTipoMapper.class})
public interface RecursoTemplateMapper extends EntityMapper<RecursoTemplateDTO, RecursoTemplate> {

    @Mapping(source = "recurso.id", target = "recursoId")
    @Mapping(source = "tipoEnvio.id", target = "tipoEnvioId")
    @Mapping(source = "alertaTipo.id", target = "alertaTipoId")
    RecursoTemplateDTO toDto(RecursoTemplate recursoTemplate);

    @Mapping(source = "recursoId", target = "recurso")
    @Mapping(source = "tipoEnvioId", target = "tipoEnvio")
    @Mapping(source = "alertaTipoId", target = "alertaTipo")
    RecursoTemplate toEntity(RecursoTemplateDTO recursoTemplateDTO);

    default RecursoTemplate fromId(Long id) {
        if (id == null) {
            return null;
        }
        RecursoTemplate recursoTemplate = new RecursoTemplate();
        recursoTemplate.setId(id);
        return recursoTemplate;
    }
}
