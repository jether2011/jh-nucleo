package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.ContatoTipoEnvioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContatoTipoEnvio} and its DTO {@link ContatoTipoEnvioDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContatoMapper.class, TipoEnvioMapper.class})
public interface ContatoTipoEnvioMapper extends EntityMapper<ContatoTipoEnvioDTO, ContatoTipoEnvio> {

    @Mapping(source = "contato.id", target = "contatoId")
    @Mapping(source = "tipoEnvio.id", target = "tipoEnvioId")
    ContatoTipoEnvioDTO toDto(ContatoTipoEnvio contatoTipoEnvio);

    @Mapping(source = "contatoId", target = "contato")
    @Mapping(source = "tipoEnvioId", target = "tipoEnvio")
    ContatoTipoEnvio toEntity(ContatoTipoEnvioDTO contatoTipoEnvioDTO);

    default ContatoTipoEnvio fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContatoTipoEnvio contatoTipoEnvio = new ContatoTipoEnvio();
        contatoTipoEnvio.setId(id);
        return contatoTipoEnvio;
    }
}
