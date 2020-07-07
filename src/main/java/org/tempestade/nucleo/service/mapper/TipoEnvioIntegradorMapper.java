package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.TipoEnvioIntegradorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoEnvioIntegrador} and its DTO {@link TipoEnvioIntegradorDTO}.
 */
@Mapper(componentModel = "spring", uses = {TipoEnvioMapper.class, IntegradorMapper.class})
public interface TipoEnvioIntegradorMapper extends EntityMapper<TipoEnvioIntegradorDTO, TipoEnvioIntegrador> {

    @Mapping(source = "tipoEnvio.id", target = "tipoEnvioId")
    @Mapping(source = "integrador.id", target = "integradorId")
    TipoEnvioIntegradorDTO toDto(TipoEnvioIntegrador tipoEnvioIntegrador);

    @Mapping(source = "tipoEnvioId", target = "tipoEnvio")
    @Mapping(source = "integradorId", target = "integrador")
    TipoEnvioIntegrador toEntity(TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO);

    default TipoEnvioIntegrador fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoEnvioIntegrador tipoEnvioIntegrador = new TipoEnvioIntegrador();
        tipoEnvioIntegrador.setId(id);
        return tipoEnvioIntegrador;
    }
}
