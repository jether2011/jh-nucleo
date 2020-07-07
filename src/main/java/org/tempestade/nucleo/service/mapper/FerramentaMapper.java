package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.FerramentaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ferramenta} and its DTO {@link FerramentaDTO}.
 */
@Mapper(componentModel = "spring", uses = {TipoFerramentaMapper.class})
public interface FerramentaMapper extends EntityMapper<FerramentaDTO, Ferramenta> {

    @Mapping(source = "tipoFerramenta.id", target = "tipoFerramentaId")
    FerramentaDTO toDto(Ferramenta ferramenta);

    @Mapping(source = "tipoFerramentaId", target = "tipoFerramenta")
    Ferramenta toEntity(FerramentaDTO ferramentaDTO);

    default Ferramenta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ferramenta ferramenta = new Ferramenta();
        ferramenta.setId(id);
        return ferramenta;
    }
}
