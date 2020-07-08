package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.AlertaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Alerta} and its DTO {@link AlertaDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanoRecursoMapper.class, AlvoMapper.class, UsuarioMapper.class, AlertaRiscoMapper.class, TempestadeNivelMapper.class, AlertaTipoMapper.class})
public interface AlertaMapper extends EntityMapper<AlertaDTO, Alerta> {

    @Mapping(source = "planoRecurso.id", target = "planoRecursoId")
    @Mapping(source = "alvo.id", target = "alvoId")
    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "alertaRisco.id", target = "alertaRiscoId")
    @Mapping(source = "tempestadeNivel.id", target = "tempestadeNivelId")
    @Mapping(source = "alertaTipo.id", target = "alertaTipoId")
    AlertaDTO toDto(Alerta alerta);

    @Mapping(source = "planoRecursoId", target = "planoRecurso")
    @Mapping(source = "alvoId", target = "alvo")
    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(source = "alertaRiscoId", target = "alertaRisco")
    @Mapping(source = "tempestadeNivelId", target = "tempestadeNivel")
    @Mapping(source = "alertaTipoId", target = "alertaTipo")
    Alerta toEntity(AlertaDTO alertaDTO);

    default Alerta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Alerta alerta = new Alerta();
        alerta.setId(id);
        return alerta;
    }
}
