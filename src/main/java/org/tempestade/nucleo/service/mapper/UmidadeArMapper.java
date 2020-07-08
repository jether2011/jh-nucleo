package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.UmidadeArDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UmidadeAr} and its DTO {@link UmidadeArDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UmidadeArMapper extends EntityMapper<UmidadeArDTO, UmidadeAr> {



    default UmidadeAr fromId(Long id) {
        if (id == null) {
            return null;
        }
        UmidadeAr umidadeAr = new UmidadeAr();
        umidadeAr.setId(id);
        return umidadeAr;
    }
}
