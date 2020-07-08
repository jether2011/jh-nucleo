package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.BoletimDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Boletim} and its DTO {@link BoletimDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanoRecursoMapper.class})
public interface BoletimMapper extends EntityMapper<BoletimDTO, Boletim> {

    @Mapping(source = "planoRecurso.id", target = "planoRecursoId")
    BoletimDTO toDto(Boletim boletim);

    @Mapping(source = "planoRecursoId", target = "planoRecurso")
    Boletim toEntity(BoletimDTO boletimDTO);

    default Boletim fromId(Long id) {
        if (id == null) {
            return null;
        }
        Boletim boletim = new Boletim();
        boletim.setId(id);
        return boletim;
    }
}
