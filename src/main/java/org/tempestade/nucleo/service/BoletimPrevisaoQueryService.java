package org.tempestade.nucleo.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.tempestade.nucleo.domain.BoletimPrevisao;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.BoletimPrevisaoRepository;
import org.tempestade.nucleo.service.dto.BoletimPrevisaoCriteria;
import org.tempestade.nucleo.service.dto.BoletimPrevisaoDTO;
import org.tempestade.nucleo.service.mapper.BoletimPrevisaoMapper;

/**
 * Service for executing complex queries for {@link BoletimPrevisao} entities in the database.
 * The main input is a {@link BoletimPrevisaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BoletimPrevisaoDTO} or a {@link Page} of {@link BoletimPrevisaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BoletimPrevisaoQueryService extends QueryService<BoletimPrevisao> {

    private final Logger log = LoggerFactory.getLogger(BoletimPrevisaoQueryService.class);

    private final BoletimPrevisaoRepository boletimPrevisaoRepository;

    private final BoletimPrevisaoMapper boletimPrevisaoMapper;

    public BoletimPrevisaoQueryService(BoletimPrevisaoRepository boletimPrevisaoRepository, BoletimPrevisaoMapper boletimPrevisaoMapper) {
        this.boletimPrevisaoRepository = boletimPrevisaoRepository;
        this.boletimPrevisaoMapper = boletimPrevisaoMapper;
    }

    /**
     * Return a {@link List} of {@link BoletimPrevisaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BoletimPrevisaoDTO> findByCriteria(BoletimPrevisaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BoletimPrevisao> specification = createSpecification(criteria);
        return boletimPrevisaoMapper.toDto(boletimPrevisaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BoletimPrevisaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BoletimPrevisaoDTO> findByCriteria(BoletimPrevisaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BoletimPrevisao> specification = createSpecification(criteria);
        return boletimPrevisaoRepository.findAll(specification, page)
            .map(boletimPrevisaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BoletimPrevisaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BoletimPrevisao> specification = createSpecification(criteria);
        return boletimPrevisaoRepository.count(specification);
    }

    /**
     * Function to convert {@link BoletimPrevisaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BoletimPrevisao> createSpecification(BoletimPrevisaoCriteria criteria) {
        Specification<BoletimPrevisao> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BoletimPrevisao_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), BoletimPrevisao_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), BoletimPrevisao_.descricao));
            }
            if (criteria.getLocal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocal(), BoletimPrevisao_.local));
            }
            if (criteria.getImgCondicaoTempo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getImgCondicaoTempo(), BoletimPrevisao_.imgCondicaoTempo));
            }
            if (criteria.getCondicaoTempo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCondicaoTempo(), BoletimPrevisao_.condicaoTempo));
            }
            if (criteria.getObservacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservacao(), BoletimPrevisao_.observacao));
            }
            if (criteria.getGrupoOrdem() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrupoOrdem(), BoletimPrevisao_.grupoOrdem));
            }
            if (criteria.getOndas() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOndas(), BoletimPrevisao_.ondas));
            }
            if (criteria.getTemperaturaDe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTemperaturaDe(), BoletimPrevisao_.temperaturaDe));
            }
            if (criteria.getTemperaturaAte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTemperaturaAte(), BoletimPrevisao_.temperaturaAte));
            }
            if (criteria.getVentovelocidademediakmh() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVentovelocidademediakmh(), BoletimPrevisao_.ventovelocidademediakmh));
            }
            if (criteria.getVentosObservacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVentosObservacao(), BoletimPrevisao_.ventosObservacao));
            }
            if (criteria.getVentoRajada() != null) {
                specification = specification.and(buildSpecification(criteria.getVentoRajada(), BoletimPrevisao_.ventoRajada));
            }
            if (criteria.getTempestadeObservacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTempestadeObservacao(), BoletimPrevisao_.tempestadeObservacao));
            }
            if (criteria.getChuvaObservacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChuvaObservacao(), BoletimPrevisao_.chuvaObservacao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), BoletimPrevisao_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), BoletimPrevisao_.updated));
            }
            if (criteria.getBoletimId() != null) {
                specification = specification.and(buildSpecification(criteria.getBoletimId(),
                    root -> root.join(BoletimPrevisao_.boletim, JoinType.LEFT).get(Boletim_.id)));
            }
            if (criteria.getBoletimPrevObsId() != null) {
                specification = specification.and(buildSpecification(criteria.getBoletimPrevObsId(),
                    root -> root.join(BoletimPrevisao_.boletimPrevObs, JoinType.LEFT).get(BoletimPrevObs_.id)));
            }
            if (criteria.getIntensidadeChuvaId() != null) {
                specification = specification.and(buildSpecification(criteria.getIntensidadeChuvaId(),
                    root -> root.join(BoletimPrevisao_.intensidadeChuva, JoinType.LEFT).get(IntensidadeChuva_.id)));
            }
            if (criteria.getUmidadeArId() != null) {
                specification = specification.and(buildSpecification(criteria.getUmidadeArId(),
                    root -> root.join(BoletimPrevisao_.umidadeAr, JoinType.LEFT).get(UmidadeAr_.id)));
            }
            if (criteria.getAlvoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAlvoId(),
                    root -> root.join(BoletimPrevisao_.alvo, JoinType.LEFT).get(Alvo_.id)));
            }
            if (criteria.getVentosOrigemPontosCardeaisId() != null) {
                specification = specification.and(buildSpecification(criteria.getVentosOrigemPontosCardeaisId(),
                    root -> root.join(BoletimPrevisao_.ventosOrigemPontosCardeais, JoinType.LEFT).get(PontosCardeais_.id)));
            }
            if (criteria.getVentoRajadaVentoVmFaixaId() != null) {
                specification = specification.and(buildSpecification(criteria.getVentoRajadaVentoVmFaixaId(),
                    root -> root.join(BoletimPrevisao_.ventoRajadaVentoVmFaixa, JoinType.LEFT).get(VentoVmFaixa_.id)));
            }
            if (criteria.getTempestadeProbabilidadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTempestadeProbabilidadeId(),
                    root -> root.join(BoletimPrevisao_.tempestadeProbabilidade, JoinType.LEFT).get(TempestadeProbabilidade_.id)));
            }
            if (criteria.getTempestadeNivelId() != null) {
                specification = specification.and(buildSpecification(criteria.getTempestadeNivelId(),
                    root -> root.join(BoletimPrevisao_.tempestadeNivel, JoinType.LEFT).get(TempestadeNivel_.id)));
            }
            if (criteria.getAcumuladoChuvaFaixaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAcumuladoChuvaFaixaId(),
                    root -> root.join(BoletimPrevisao_.acumuladoChuvaFaixa, JoinType.LEFT).get(AcumuladoChuvaFaixa_.id)));
            }
            if (criteria.getCondicaoTempoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCondicaoTempoId(),
                    root -> root.join(BoletimPrevisao_.condicaoTempo, JoinType.LEFT).get(CondicaoTempo_.id)));
            }
        }
        return specification;
    }
}
