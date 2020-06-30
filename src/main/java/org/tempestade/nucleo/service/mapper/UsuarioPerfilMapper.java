package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.UsuarioPerfilDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UsuarioPerfil} and its DTO {@link UsuarioPerfilDTO}.
 */
@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, PerfilMapper.class})
public interface UsuarioPerfilMapper extends EntityMapper<UsuarioPerfilDTO, UsuarioPerfil> {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "perfil.id", target = "perfilId")
    UsuarioPerfilDTO toDto(UsuarioPerfil usuarioPerfil);

    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(source = "perfilId", target = "perfil")
    UsuarioPerfil toEntity(UsuarioPerfilDTO usuarioPerfilDTO);

    default UsuarioPerfil fromId(Long id) {
        if (id == null) {
            return null;
        }
        UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
        usuarioPerfil.setId(id);
        return usuarioPerfil;
    }
}
