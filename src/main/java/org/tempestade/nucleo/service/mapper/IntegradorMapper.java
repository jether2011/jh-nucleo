package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.IntegradorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Integrador} and its DTO {@link IntegradorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IntegradorMapper extends EntityMapper<IntegradorDTO, Integrador> {



    default Integrador fromId(Long id) {
        if (id == null) {
            return null;
        }
        Integrador integrador = new Integrador();
        integrador.setId(id);
        return integrador;
    }
}
