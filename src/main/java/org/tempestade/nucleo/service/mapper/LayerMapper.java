package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.LayerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Layer} and its DTO {@link LayerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LayerMapper extends EntityMapper<LayerDTO, Layer> {



    default Layer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Layer layer = new Layer();
        layer.setId(id);
        return layer;
    }
}
