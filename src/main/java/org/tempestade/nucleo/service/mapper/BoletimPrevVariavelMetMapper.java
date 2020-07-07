package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.BoletimPrevVariavelMetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BoletimPrevVariavelMet} and its DTO {@link BoletimPrevVariavelMetDTO}.
 */
@Mapper(componentModel = "spring", uses = {BoletimPrevisaoMapper.class, VariavelMeteorologicaMapper.class})
public interface BoletimPrevVariavelMetMapper extends EntityMapper<BoletimPrevVariavelMetDTO, BoletimPrevVariavelMet> {

    @Mapping(source = "boletimPrevisao.id", target = "boletimPrevisaoId")
    @Mapping(source = "variavelMeteorologica.id", target = "variavelMeteorologicaId")
    BoletimPrevVariavelMetDTO toDto(BoletimPrevVariavelMet boletimPrevVariavelMet);

    @Mapping(source = "boletimPrevisaoId", target = "boletimPrevisao")
    @Mapping(source = "variavelMeteorologicaId", target = "variavelMeteorologica")
    BoletimPrevVariavelMet toEntity(BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO);

    default BoletimPrevVariavelMet fromId(Long id) {
        if (id == null) {
            return null;
        }
        BoletimPrevVariavelMet boletimPrevVariavelMet = new BoletimPrevVariavelMet();
        boletimPrevVariavelMet.setId(id);
        return boletimPrevVariavelMet;
    }
}
