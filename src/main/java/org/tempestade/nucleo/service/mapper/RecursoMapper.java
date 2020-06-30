package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.RecursoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Recurso} and its DTO {@link RecursoDTO}.
 */
@Mapper(componentModel = "spring", uses = {RecursoTipoMapper.class, VariavelMeteorologicaMapper.class})
public interface RecursoMapper extends EntityMapper<RecursoDTO, Recurso> {

    @Mapping(source = "recursoTipo.id", target = "recursoTipoId")
    @Mapping(source = "variavelMeteorologica.id", target = "variavelMeteorologicaId")
    RecursoDTO toDto(Recurso recurso);

    @Mapping(source = "recursoTipoId", target = "recursoTipo")
    @Mapping(source = "variavelMeteorologicaId", target = "variavelMeteorologica")
    Recurso toEntity(RecursoDTO recursoDTO);

    default Recurso fromId(Long id) {
        if (id == null) {
            return null;
        }
        Recurso recurso = new Recurso();
        recurso.setId(id);
        return recurso;
    }
}
