package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.PlanoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Plano} and its DTO {@link PlanoDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class, PlanoStatusMapper.class})
public interface PlanoMapper extends EntityMapper<PlanoDTO, Plano> {

    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "planoStatus.id", target = "planoStatusId")
    PlanoDTO toDto(Plano plano);

    @Mapping(source = "empresaId", target = "empresa")
    @Mapping(source = "planoStatusId", target = "planoStatus")
    Plano toEntity(PlanoDTO planoDTO);

    default Plano fromId(Long id) {
        if (id == null) {
            return null;
        }
        Plano plano = new Plano();
        plano.setId(id);
        return plano;
    }
}
