package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.AvisoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Aviso} and its DTO {@link AvisoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AvisoTipoMapper.class})
public interface AvisoMapper extends EntityMapper<AvisoDTO, Aviso> {

    @Mapping(source = "avisoTipo.id", target = "avisoTipoId")
    AvisoDTO toDto(Aviso aviso);

    @Mapping(source = "avisoTipoId", target = "avisoTipo")
    Aviso toEntity(AvisoDTO avisoDTO);

    default Aviso fromId(Long id) {
        if (id == null) {
            return null;
        }
        Aviso aviso = new Aviso();
        aviso.setId(id);
        return aviso;
    }
}
