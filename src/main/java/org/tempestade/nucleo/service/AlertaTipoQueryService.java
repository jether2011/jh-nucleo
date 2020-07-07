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

import org.tempestade.nucleo.domain.AlertaTipo;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.AlertaTipoRepository;
import org.tempestade.nucleo.service.dto.AlertaTipoCriteria;
import org.tempestade.nucleo.service.dto.AlertaTipoDTO;
import org.tempestade.nucleo.service.mapper.AlertaTipoMapper;

/**
 * Service for executing complex queries for {@link AlertaTipo} entities in the database.
 * The main input is a {@link AlertaTipoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AlertaTipoDTO} or a {@link Page} of {@link AlertaTipoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AlertaTipoQueryService extends QueryService<AlertaTipo> {

    private final Logger log = LoggerFactory.getLogger(AlertaTipoQueryService.class);

    private final AlertaTipoRepository alertaTipoRepository;

    private final AlertaTipoMapper alertaTipoMapper;

    public AlertaTipoQueryService(AlertaTipoRepository alertaTipoRepository, AlertaTipoMapper alertaTipoMapper) {
        this.alertaTipoRepository = alertaTipoRepository;
        this.alertaTipoMapper = alertaTipoMapper;
    }

    /**
     * Return a {@link List} of {@link AlertaTipoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AlertaTipoDTO> findByCriteria(AlertaTipoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AlertaTipo> specification = createSpecification(criteria);
        return alertaTipoMapper.toDto(alertaTipoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AlertaTipoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AlertaTipoDTO> findByCriteria(AlertaTipoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AlertaTipo> specification = createSpecification(criteria);
        return alertaTipoRepository.findAll(specification, page)
            .map(alertaTipoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AlertaTipoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AlertaTipo> specification = createSpecification(criteria);
        return alertaTipoRepository.count(specification);
    }

    /**
     * Function to convert {@link AlertaTipoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AlertaTipo> createSpecification(AlertaTipoCriteria criteria) {
        Specification<AlertaTipo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AlertaTipo_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), AlertaTipo_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), AlertaTipo_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), AlertaTipo_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), AlertaTipo_.updated));
            }
        }
        return specification;
    }
}
