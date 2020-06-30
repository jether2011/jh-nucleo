package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.PlanoRedeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoRede} and its DTO {@link PlanoRedeDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanoMapper.class, RedeMapper.class})
public interface PlanoRedeMapper extends EntityMapper<PlanoRedeDTO, PlanoRede> {

    @Mapping(source = "plano.id", target = "planoId")
    @Mapping(source = "rede.id", target = "redeId")
    PlanoRedeDTO toDto(PlanoRede planoRede);

    @Mapping(source = "planoId", target = "plano")
    @Mapping(source = "redeId", target = "rede")
    PlanoRede toEntity(PlanoRedeDTO planoRedeDTO);

    default PlanoRede fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlanoRede planoRede = new PlanoRede();
        planoRede.setId(id);
        return planoRede;
    }
}
