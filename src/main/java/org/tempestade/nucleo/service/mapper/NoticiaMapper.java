package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.NoticiaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Noticia} and its DTO {@link NoticiaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NoticiaMapper extends EntityMapper<NoticiaDTO, Noticia> {



    default Noticia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Noticia noticia = new Noticia();
        noticia.setId(id);
        return noticia;
    }
}
