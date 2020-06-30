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

import org.tempestade.nucleo.domain.CondicaoTempo;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.CondicaoTempoRepository;
import org.tempestade.nucleo.service.dto.CondicaoTempoCriteria;
import org.tempestade.nucleo.service.dto.CondicaoTempoDTO;
import org.tempestade.nucleo.service.mapper.CondicaoTempoMapper;

/**
 * Service for executing complex queries for {@link CondicaoTempo} entities in the database.
 * The main input is a {@link CondicaoTempoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CondicaoTempoDTO} or a {@link Page} of {@link CondicaoTempoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CondicaoTempoQueryService extends QueryService<CondicaoTempo> {

    private final Logger log = LoggerFactory.getLogger(CondicaoTempoQueryService.class);

    private final CondicaoTempoRepository condicaoTempoRepository;

    private final CondicaoTempoMapper condicaoTempoMapper;

    public CondicaoTempoQueryService(CondicaoTempoRepository condicaoTempoRepository, CondicaoTempoMapper condicaoTempoMapper) {
        this.condicaoTempoRepository = condicaoTempoRepository;
        this.condicaoTempoMapper = condicaoTempoMapper;
    }

    /**
     * Return a {@link List} of {@link CondicaoTempoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CondicaoTempoDTO> findByCriteria(CondicaoTempoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CondicaoTempo> specification = createSpecification(criteria);
        return condicaoTempoMapper.toDto(condicaoTempoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CondicaoTempoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CondicaoTempoDTO> findByCriteria(CondicaoTempoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CondicaoTempo> specification = createSpecification(criteria);
        return condicaoTempoRepository.findAll(specification, page)
            .map(condicaoTempoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CondicaoTempoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CondicaoTempo> specification = createSpecification(criteria);
        return condicaoTempoRepository.count(specification);
    }

    /**
     * Function to convert {@link CondicaoTempoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CondicaoTempo> createSpecification(CondicaoTempoCriteria criteria) {
        Specification<CondicaoTempo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CondicaoTempo_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), CondicaoTempo_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), CondicaoTempo_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), CondicaoTempo_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), CondicaoTempo_.updated));
            }
        }
        return specification;
    }
}
