package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.NotificacaoEnviadaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link NotificacaoEnviada} and its DTO {@link NotificacaoEnviadaDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanoRecursoMapper.class})
public interface NotificacaoEnviadaMapper extends EntityMapper<NotificacaoEnviadaDTO, NotificacaoEnviada> {

    @Mapping(source = "planoRecurso.id", target = "planoRecursoId")
    NotificacaoEnviadaDTO toDto(NotificacaoEnviada notificacaoEnviada);

    @Mapping(source = "planoRecursoId", target = "planoRecurso")
    NotificacaoEnviada toEntity(NotificacaoEnviadaDTO notificacaoEnviadaDTO);

    default NotificacaoEnviada fromId(Long id) {
        if (id == null) {
            return null;
        }
        NotificacaoEnviada notificacaoEnviada = new NotificacaoEnviada();
        notificacaoEnviada.setId(id);
        return notificacaoEnviada;
    }
}
