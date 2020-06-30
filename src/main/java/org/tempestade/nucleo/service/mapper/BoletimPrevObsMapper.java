package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.BoletimPrevObsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BoletimPrevObs} and its DTO {@link BoletimPrevObsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BoletimPrevObsMapper extends EntityMapper<BoletimPrevObsDTO, BoletimPrevObs> {



    default BoletimPrevObs fromId(Long id) {
        if (id == null) {
            return null;
        }
        BoletimPrevObs boletimPrevObs = new BoletimPrevObs();
        boletimPrevObs.setId(id);
        return boletimPrevObs;
    }
}
