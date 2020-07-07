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

import org.tempestade.nucleo.domain.PlanoStatus;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.PlanoStatusRepository;
import org.tempestade.nucleo.service.dto.PlanoStatusCriteria;
import org.tempestade.nucleo.service.dto.PlanoStatusDTO;
import org.tempestade.nucleo.service.mapper.PlanoStatusMapper;

/**
 * Service for executing complex queries for {@link PlanoStatus} entities in the database.
 * The main input is a {@link PlanoStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlanoStatusDTO} or a {@link Page} of {@link PlanoStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanoStatusQueryService extends QueryService<PlanoStatus> {

    private final Logger log = LoggerFactory.getLogger(PlanoStatusQueryService.class);

    private final PlanoStatusRepository planoStatusRepository;

    private final PlanoStatusMapper planoStatusMapper;

    public PlanoStatusQueryService(PlanoStatusRepository planoStatusRepository, PlanoStatusMapper planoStatusMapper) {
        this.planoStatusRepository = planoStatusRepository;
        this.planoStatusMapper = planoStatusMapper;
    }

    /**
     * Return a {@link List} of {@link PlanoStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlanoStatusDTO> findByCriteria(PlanoStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PlanoStatus> specification = createSpecification(criteria);
        return planoStatusMapper.toDto(planoStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlanoStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoStatusDTO> findByCriteria(PlanoStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PlanoStatus> specification = createSpecification(criteria);
        return planoStatusRepository.findAll(specification, page)
            .map(planoStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanoStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlanoStatus> specification = createSpecification(criteria);
        return planoStatusRepository.count(specification);
    }

    /**
     * Function to convert {@link PlanoStatusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlanoStatus> createSpecification(PlanoStatusCriteria criteria) {
        Specification<PlanoStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PlanoStatus_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PlanoStatus_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), PlanoStatus_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), PlanoStatus_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), PlanoStatus_.updated));
            }
        }
        return specification;
    }
}
