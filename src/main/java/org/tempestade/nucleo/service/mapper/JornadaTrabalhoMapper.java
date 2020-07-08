package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.JornadaTrabalhoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link JornadaTrabalho} and its DTO {@link JornadaTrabalhoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanoMapper.class, DiaSemanaMapper.class})
public interface JornadaTrabalhoMapper extends EntityMapper<JornadaTrabalhoDTO, JornadaTrabalho> {

    @Mapping(source = "plano.id", target = "planoId")
    @Mapping(source = "diaSemana.id", target = "diaSemanaId")
    JornadaTrabalhoDTO toDto(JornadaTrabalho jornadaTrabalho);

    @Mapping(source = "planoId", target = "plano")
    @Mapping(source = "diaSemanaId", target = "diaSemana")
    JornadaTrabalho toEntity(JornadaTrabalhoDTO jornadaTrabalhoDTO);

    default JornadaTrabalho fromId(Long id) {
        if (id == null) {
            return null;
        }
        JornadaTrabalho jornadaTrabalho = new JornadaTrabalho();
        jornadaTrabalho.setId(id);
        return jornadaTrabalho;
    }
}
