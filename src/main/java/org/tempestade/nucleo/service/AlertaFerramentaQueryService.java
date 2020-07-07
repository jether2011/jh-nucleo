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

import org.tempestade.nucleo.domain.AlertaFerramenta;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.AlertaFerramentaRepository;
import org.tempestade.nucleo.service.dto.AlertaFerramentaCriteria;
import org.tempestade.nucleo.service.dto.AlertaFerramentaDTO;
import org.tempestade.nucleo.service.mapper.AlertaFerramentaMapper;

/**
 * Service for executing complex queries for {@link AlertaFerramenta} entities in the database.
 * The main input is a {@link AlertaFerramentaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AlertaFerramentaDTO} or a {@link Page} of {@link AlertaFerramentaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AlertaFerramentaQueryService extends QueryService<AlertaFerramenta> {

    private final Logger log = LoggerFactory.getLogger(AlertaFerramentaQueryService.class);

    private final AlertaFerramentaRepository alertaFerramentaRepository;

    private final AlertaFerramentaMapper alertaFerramentaMapper;

    public AlertaFerramentaQueryService(AlertaFerramentaRepository alertaFerramentaRepository, AlertaFerramentaMapper alertaFerramentaMapper) {
        this.alertaFerramentaRepository = alertaFerramentaRepository;
        this.alertaFerramentaMapper = alertaFerramentaMapper;
    }

    /**
     * Return a {@link List} of {@link AlertaFerramentaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AlertaFerramentaDTO> findByCriteria(AlertaFerramentaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AlertaFerramenta> specification = createSpecification(criteria);
        return alertaFerramentaMapper.toDto(alertaFerramentaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AlertaFerramentaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AlertaFerramentaDTO> findByCriteria(AlertaFerramentaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AlertaFerramenta> specification = createSpecification(criteria);
        return alertaFerramentaRepository.findAll(specification, page)
            .map(alertaFerramentaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AlertaFerramentaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AlertaFerramenta> specification = createSpecification(criteria);
        return alertaFerramentaRepository.count(specification);
    }

    /**
     * Function to convert {@link AlertaFerramentaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AlertaFerramenta> createSpecification(AlertaFerramentaCriteria criteria) {
        Specification<AlertaFerramenta> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AlertaFerramenta_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), AlertaFerramenta_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), AlertaFerramenta_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), AlertaFerramenta_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), AlertaFerramenta_.updated));
            }
            if (criteria.getAlertaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAlertaId(),
                    root -> root.join(AlertaFerramenta_.alerta, JoinType.LEFT).get(Alerta_.id)));
            }
            if (criteria.getFerramentaId() != null) {
                specification = specification.and(buildSpecification(criteria.getFerramentaId(),
                    root -> root.join(AlertaFerramenta_.ferramenta, JoinType.LEFT).get(Ferramenta_.id)));
            }
        }
        return specification;
    }
}
