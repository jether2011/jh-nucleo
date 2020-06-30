package org.tempestade.nucleo.service.mapper;


import org.tempestade.nucleo.domain.*;
import org.tempestade.nucleo.service.dto.BoletimPrevisaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BoletimPrevisao} and its DTO {@link BoletimPrevisaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {BoletimMapper.class, BoletimPrevObsMapper.class, IntensidadeChuvaMapper.class, UmidadeArMapper.class, AlvoMapper.class, PontosCardeaisMapper.class, VentoVmFaixaMapper.class, TempestadeProbabilidadeMapper.class, TempestadeNivelMapper.class, AcumuladoChuvaFaixaMapper.class, CondicaoTempoMapper.class})
public interface BoletimPrevisaoMapper extends EntityMapper<BoletimPrevisaoDTO, BoletimPrevisao> {

    @Mapping(source = "boletim.id", target = "boletimId")
    @Mapping(source = "boletimPrevObs.id", target = "boletimPrevObsId")
    @Mapping(source = "intensidadeChuva.id", target = "intensidadeChuvaId")
    @Mapping(source = "umidadeAr.id", target = "umidadeArId")
    @Mapping(source = "alvo.id", target = "alvoId")
    @Mapping(source = "ventosOrigemPontosCardeais.id", target = "ventosOrigemPontosCardeaisId")
    @Mapping(source = "ventoRajadaVentoVmFaixa.id", target = "ventoRajadaVentoVmFaixaId")
    @Mapping(source = "tempestadeProbabilidade.id", target = "tempestadeProbabilidadeId")
    @Mapping(source = "tempestadeNivel.id", target = "tempestadeNivelId")
    @Mapping(source = "acumuladoChuvaFaixa.id", target = "acumuladoChuvaFaixaId")
    @Mapping(source = "condicaoTempo.id", target = "condicaoTempoId")
    BoletimPrevisaoDTO toDto(BoletimPrevisao boletimPrevisao);

    @Mapping(source = "boletimId", target = "boletim")
    @Mapping(source = "boletimPrevObsId", target = "boletimPrevObs")
    @Mapping(source = "intensidadeChuvaId", target = "intensidadeChuva")
    @Mapping(source = "umidadeArId", target = "umidadeAr")
    @Mapping(source = "alvoId", target = "alvo")
    @Mapping(source = "ventosOrigemPontosCardeaisId", target = "ventosOrigemPontosCardeais")
    @Mapping(source = "ventoRajadaVentoVmFaixaId", target = "ventoRajadaVentoVmFaixa")
    @Mapping(source = "tempestadeProbabilidadeId", target = "tempestadeProbabilidade")
    @Mapping(source = "tempestadeNivelId", target = "tempestadeNivel")
    @Mapping(source = "acumuladoChuvaFaixaId", target = "acumuladoChuvaFaixa")
    @Mapping(source = "condicaoTempoId", target = "condicaoTempo")
    BoletimPrevisao toEntity(BoletimPrevisaoDTO boletimPrevisaoDTO);

    default BoletimPrevisao fromId(Long id) {
        if (id == null) {
            return null;
        }
        BoletimPrevisao boletimPrevisao = new BoletimPrevisao();
        boletimPrevisao.setId(id);
        return boletimPrevisao;
    }
}
