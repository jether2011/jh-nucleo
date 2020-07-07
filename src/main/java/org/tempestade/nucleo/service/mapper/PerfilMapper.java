package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.PerfilDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Perfil} and its DTO {@link PerfilDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PerfilMapper extends EntityMapper<PerfilDTO, Perfil> {



    default Perfil fromId(Long id) {
        if (id == null) {
            return null;
        }
        Perfil perfil = new Perfil();
        perfil.setId(id);
        return perfil;
    }
}
