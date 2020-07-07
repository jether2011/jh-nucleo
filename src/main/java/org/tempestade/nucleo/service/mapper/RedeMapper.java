package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.RedeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rede} and its DTO {@link RedeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RedeMapper extends EntityMapper<RedeDTO, Rede> {



    default Rede fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rede rede = new Rede();
        rede.setId(id);
        return rede;
    }
}
