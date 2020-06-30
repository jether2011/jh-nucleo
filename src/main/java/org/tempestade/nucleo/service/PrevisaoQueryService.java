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

import org.tempestade.nucleo.domain.Previsao;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.PrevisaoRepository;
import org.tempestade.nucleo.service.dto.PrevisaoCriteria;
import org.tempestade.nucleo.service.dto.PrevisaoDTO;
import org.tempestade.nucleo.service.mapper.PrevisaoMapper;

/**
 * Service for executing complex queries for {@link Previsao} entities in the database.
 * The main input is a {@link PrevisaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PrevisaoDTO} or a {@link Page} of {@link PrevisaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrevisaoQueryService extends QueryService<Previsao> {

    private final Logger log = LoggerFactory.getLogger(PrevisaoQueryService.class);

    private final PrevisaoRepository previsaoRepository;

    private final PrevisaoMapper previsaoMapper;

    public PrevisaoQueryService(PrevisaoRepository previsaoRepository, PrevisaoMapper previsaoMapper) {
        this.previsaoRepository = previsaoRepository;
        this.previsaoMapper = previsaoMapper;
    }

    /**
     * Return a {@link List} of {@link PrevisaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PrevisaoDTO> findByCriteria(PrevisaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Previsao> specification = createSpecification(criteria);
        return previsaoMapper.toDto(previsaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PrevisaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrevisaoDTO> findByCriteria(PrevisaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Previsao> specification = createSpecification(criteria);
        return previsaoRepository.findAll(specification, page)
            .map(previsaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PrevisaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Previsao> specification = createSpecification(criteria);
        return previsaoRepository.count(specification);
    }

    /**
     * Function to convert {@link PrevisaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Previsao> createSpecification(PrevisaoCriteria criteria) {
        Specification<Previsao> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Previsao_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Previsao_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Previsao_.descricao));
            }
            if (criteria.getTextoNorte() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoNorte(), Previsao_.textoNorte));
            }
            if (criteria.getTextoNorteImagem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoNorteImagem(), Previsao_.textoNorteImagem));
            }
            if (criteria.getTextoNordeste() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoNordeste(), Previsao_.textoNordeste));
            }
            if (criteria.getTextoNordesteImagem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoNordesteImagem(), Previsao_.textoNordesteImagem));
            }
            if (criteria.getTextoSul() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoSul(), Previsao_.textoSul));
            }
            if (criteria.getTextoSulImagem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoSulImagem(), Previsao_.textoSulImagem));
            }
            if (criteria.getTextoSudeste() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoSudeste(), Previsao_.textoSudeste));
            }
            if (criteria.getTextoSudesteImagem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoSudesteImagem(), Previsao_.textoSudesteImagem));
            }
            if (criteria.getTextoCentroOeste() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoCentroOeste(), Previsao_.textoCentroOeste));
            }
            if (criteria.getTextoCentroOesteImagem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoCentroOesteImagem(), Previsao_.textoCentroOesteImagem));
            }
            if (criteria.getEnviado() != null) {
                specification = specification.and(buildSpecification(criteria.getEnviado(), Previsao_.enviado));
            }
            if (criteria.getTextoBrasil() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoBrasil(), Previsao_.textoBrasil));
            }
            if (criteria.getTextoBrasilImagem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoBrasilImagem(), Previsao_.textoBrasilImagem));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Previsao_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Previsao_.updated));
            }
        }
        return specification;
    }
}
