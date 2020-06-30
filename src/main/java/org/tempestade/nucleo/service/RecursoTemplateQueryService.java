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

import org.tempestade.nucleo.domain.RecursoTemplate;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.RecursoTemplateRepository;
import org.tempestade.nucleo.service.dto.RecursoTemplateCriteria;
import org.tempestade.nucleo.service.dto.RecursoTemplateDTO;
import org.tempestade.nucleo.service.mapper.RecursoTemplateMapper;

/**
 * Service for executing complex queries for {@link RecursoTemplate} entities in the database.
 * The main input is a {@link RecursoTemplateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RecursoTemplateDTO} or a {@link Page} of {@link RecursoTemplateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RecursoTemplateQueryService extends QueryService<RecursoTemplate> {

    private final Logger log = LoggerFactory.getLogger(RecursoTemplateQueryService.class);

    private final RecursoTemplateRepository recursoTemplateRepository;

    private final RecursoTemplateMapper recursoTemplateMapper;

    public RecursoTemplateQueryService(RecursoTemplateRepository recursoTemplateRepository, RecursoTemplateMapper recursoTemplateMapper) {
        this.recursoTemplateRepository = recursoTemplateRepository;
        this.recursoTemplateMapper = recursoTemplateMapper;
    }

    /**
     * Return a {@link List} of {@link RecursoTemplateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RecursoTemplateDTO> findByCriteria(RecursoTemplateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RecursoTemplate> specification = createSpecification(criteria);
        return recursoTemplateMapper.toDto(recursoTemplateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RecursoTemplateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RecursoTemplateDTO> findByCriteria(RecursoTemplateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RecursoTemplate> specification = createSpecification(criteria);
        return recursoTemplateRepository.findAll(specification, page)
            .map(recursoTemplateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RecursoTemplateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RecursoTemplate> specification = createSpecification(criteria);
        return recursoTemplateRepository.count(specification);
    }

    /**
     * Function to convert {@link RecursoTemplateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RecursoTemplate> createSpecification(RecursoTemplateCriteria criteria) {
        Specification<RecursoTemplate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RecursoTemplate_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), RecursoTemplate_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), RecursoTemplate_.descricao));
            }
            if (criteria.getTemplate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTemplate(), RecursoTemplate_.template));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), RecursoTemplate_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), RecursoTemplate_.updated));
            }
            if (criteria.getRecursoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRecursoId(),
                    root -> root.join(RecursoTemplate_.recurso, JoinType.LEFT).get(Recurso_.id)));
            }
            if (criteria.getTipoEnvioId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoEnvioId(),
                    root -> root.join(RecursoTemplate_.tipoEnvio, JoinType.LEFT).get(TipoEnvio_.id)));
            }
            if (criteria.getAlertaTipoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAlertaTipoId(),
                    root -> root.join(RecursoTemplate_.alertaTipo, JoinType.LEFT).get(AlertaTipo_.id)));
            }
        }
        return specification;
    }
}
