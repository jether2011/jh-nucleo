package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.PlanoLayerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoLayer} and its DTO {@link PlanoLayerDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanoMapper.class, LayerMapper.class, AlvoMapper.class})
public interface PlanoLayerMapper extends EntityMapper<PlanoLayerDTO, PlanoLayer> {

    @Mapping(source = "plano.id", target = "planoId")
    @Mapping(source = "layer.id", target = "layerId")
    @Mapping(source = "alvo.id", target = "alvoId")
    PlanoLayerDTO toDto(PlanoLayer planoLayer);

    @Mapping(source = "planoId", target = "plano")
    @Mapping(source = "layerId", target = "layer")
    @Mapping(source = "alvoId", target = "alvo")
    PlanoLayer toEntity(PlanoLayerDTO planoLayerDTO);

    default PlanoLayer fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlanoLayer planoLayer = new PlanoLayer();
        planoLayer.setId(id);
        return planoLayer;
    }
}
