package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.AvisoMeteorologicoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AvisoMeteorologico} and its DTO {@link AvisoMeteorologicoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanoRecursoMapper.class})
public interface AvisoMeteorologicoMapper extends EntityMapper<AvisoMeteorologicoDTO, AvisoMeteorologico> {

    @Mapping(source = "planoRecurso.id", target = "planoRecursoId")
    AvisoMeteorologicoDTO toDto(AvisoMeteorologico avisoMeteorologico);

    @Mapping(source = "planoRecursoId", target = "planoRecurso")
    AvisoMeteorologico toEntity(AvisoMeteorologicoDTO avisoMeteorologicoDTO);

    default AvisoMeteorologico fromId(Long id) {
        if (id == null) {
            return null;
        }
        AvisoMeteorologico avisoMeteorologico = new AvisoMeteorologico();
        avisoMeteorologico.setId(id);
        return avisoMeteorologico;
    }
}
