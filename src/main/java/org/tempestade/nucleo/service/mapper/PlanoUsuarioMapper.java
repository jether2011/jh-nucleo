package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.PlanoUsuarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoUsuario} and its DTO {@link PlanoUsuarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanoMapper.class, UsuarioMapper.class})
public interface PlanoUsuarioMapper extends EntityMapper<PlanoUsuarioDTO, PlanoUsuario> {

    @Mapping(source = "plano.id", target = "planoId")
    @Mapping(source = "usuario.id", target = "usuarioId")
    PlanoUsuarioDTO toDto(PlanoUsuario planoUsuario);

    @Mapping(source = "planoId", target = "plano")
    @Mapping(source = "usuarioId", target = "usuario")
    PlanoUsuario toEntity(PlanoUsuarioDTO planoUsuarioDTO);

    default PlanoUsuario fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlanoUsuario planoUsuario = new PlanoUsuario();
        planoUsuario.setId(id);
        return planoUsuario;
    }
}
