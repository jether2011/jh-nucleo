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

import org.tempestade.nucleo.domain.PlanoLayer;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.PlanoLayerRepository;
import org.tempestade.nucleo.service.dto.PlanoLayerCriteria;
import org.tempestade.nucleo.service.dto.PlanoLayerDTO;
import org.tempestade.nucleo.service.mapper.PlanoLayerMapper;

/**
 * Service for executing complex queries for {@link PlanoLayer} entities in the database.
 * The main input is a {@link PlanoLayerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlanoLayerDTO} or a {@link Page} of {@link PlanoLayerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanoLayerQueryService extends QueryService<PlanoLayer> {

    private final Logger log = LoggerFactory.getLogger(PlanoLayerQueryService.class);

    private final PlanoLayerRepository planoLayerRepository;

    private final PlanoLayerMapper planoLayerMapper;

    public PlanoLayerQueryService(PlanoLayerRepository planoLayerRepository, PlanoLayerMapper planoLayerMapper) {
        this.planoLayerRepository = planoLayerRepository;
        this.planoLayerMapper = planoLayerMapper;
    }

    /**
     * Return a {@link List} of {@link PlanoLayerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlanoLayerDTO> findByCriteria(PlanoLayerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PlanoLayer> specification = createSpecification(criteria);
        return planoLayerMapper.toDto(planoLayerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlanoLayerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoLayerDTO> findByCriteria(PlanoLayerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PlanoLayer> specification = createSpecification(criteria);
        return planoLayerRepository.findAll(specification, page)
            .map(planoLayerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanoLayerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlanoLayer> specification = createSpecification(criteria);
        return planoLayerRepository.count(specification);
    }

    /**
     * Function to convert {@link PlanoLayerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlanoLayer> createSpecification(PlanoLayerCriteria criteria) {
        Specification<PlanoLayer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PlanoLayer_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PlanoLayer_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), PlanoLayer_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), PlanoLayer_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), PlanoLayer_.updated));
            }
            if (criteria.getPlanoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoId(),
                    root -> root.join(PlanoLayer_.plano, JoinType.LEFT).get(Plano_.id)));
            }
            if (criteria.getLayerId() != null) {
                specification = specification.and(buildSpecification(criteria.getLayerId(),
                    root -> root.join(PlanoLayer_.layer, JoinType.LEFT).get(Layer_.id)));
            }
            if (criteria.getAlvoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAlvoId(),
                    root -> root.join(PlanoLayer_.alvo, JoinType.LEFT).get(Alvo_.id)));
            }
        }
        return specification;
    }
}
