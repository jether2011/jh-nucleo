package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.DescargaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Descarga} and its DTO {@link DescargaDTO}.
 */
@Mapper(componentModel = "spring", uses = {RedeMapper.class, DescargaTipoMapper.class, DescargaUnidadeMapper.class, AlertaMapper.class})
public interface DescargaMapper extends EntityMapper<DescargaDTO, Descarga> {

    @Mapping(source = "rede.id", target = "redeId")
    @Mapping(source = "descargaTipo.id", target = "descargaTipoId")
    @Mapping(source = "descargaUnidade.id", target = "descargaUnidadeId")
    @Mapping(source = "alerta.id", target = "alertaId")
    DescargaDTO toDto(Descarga descarga);

    @Mapping(source = "redeId", target = "rede")
    @Mapping(source = "descargaTipoId", target = "descargaTipo")
    @Mapping(source = "descargaUnidadeId", target = "descargaUnidade")
    @Mapping(source = "alertaId", target = "alerta")
    Descarga toEntity(DescargaDTO descargaDTO);

    default Descarga fromId(Long id) {
        if (id == null) {
            return null;
        }
        Descarga descarga = new Descarga();
        descarga.setId(id);
        return descarga;
    }
}
