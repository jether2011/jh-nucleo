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

import org.tempestade.nucleo.domain.AlertaRisco;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.AlertaRiscoRepository;
import org.tempestade.nucleo.service.dto.AlertaRiscoCriteria;
import org.tempestade.nucleo.service.dto.AlertaRiscoDTO;
import org.tempestade.nucleo.service.mapper.AlertaRiscoMapper;

/**
 * Service for executing complex queries for {@link AlertaRisco} entities in the database.
 * The main input is a {@link AlertaRiscoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AlertaRiscoDTO} or a {@link Page} of {@link AlertaRiscoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AlertaRiscoQueryService extends QueryService<AlertaRisco> {

    private final Logger log = LoggerFactory.getLogger(AlertaRiscoQueryService.class);

    private final AlertaRiscoRepository alertaRiscoRepository;

    private final AlertaRiscoMapper alertaRiscoMapper;

    public AlertaRiscoQueryService(AlertaRiscoRepository alertaRiscoRepository, AlertaRiscoMapper alertaRiscoMapper) {
        this.alertaRiscoRepository = alertaRiscoRepository;
        this.alertaRiscoMapper = alertaRiscoMapper;
    }

    /**
     * Return a {@link List} of {@link AlertaRiscoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AlertaRiscoDTO> findByCriteria(AlertaRiscoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AlertaRisco> specification = createSpecification(criteria);
        return alertaRiscoMapper.toDto(alertaRiscoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AlertaRiscoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AlertaRiscoDTO> findByCriteria(AlertaRiscoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AlertaRisco> specification = createSpecification(criteria);
        return alertaRiscoRepository.findAll(specification, page)
            .map(alertaRiscoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AlertaRiscoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AlertaRisco> specification = createSpecification(criteria);
        return alertaRiscoRepository.count(specification);
    }

    /**
     * Function to convert {@link AlertaRiscoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AlertaRisco> createSpecification(AlertaRiscoCriteria criteria) {
        Specification<AlertaRisco> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AlertaRisco_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), AlertaRisco_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), AlertaRisco_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), AlertaRisco_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), AlertaRisco_.updated));
            }
        }
        return specification;
    }
}
