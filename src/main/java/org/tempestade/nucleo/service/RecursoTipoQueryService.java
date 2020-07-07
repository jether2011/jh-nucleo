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

import org.tempestade.nucleo.domain.RecursoTipo;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.RecursoTipoRepository;
import org.tempestade.nucleo.service.dto.RecursoTipoCriteria;
import org.tempestade.nucleo.service.dto.RecursoTipoDTO;
import org.tempestade.nucleo.service.mapper.RecursoTipoMapper;

/**
 * Service for executing complex queries for {@link RecursoTipo} entities in the database.
 * The main input is a {@link RecursoTipoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RecursoTipoDTO} or a {@link Page} of {@link RecursoTipoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RecursoTipoQueryService extends QueryService<RecursoTipo> {

    private final Logger log = LoggerFactory.getLogger(RecursoTipoQueryService.class);

    private final RecursoTipoRepository recursoTipoRepository;

    private final RecursoTipoMapper recursoTipoMapper;

    public RecursoTipoQueryService(RecursoTipoRepository recursoTipoRepository, RecursoTipoMapper recursoTipoMapper) {
        this.recursoTipoRepository = recursoTipoRepository;
        this.recursoTipoMapper = recursoTipoMapper;
    }

    /**
     * Return a {@link List} of {@link RecursoTipoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RecursoTipoDTO> findByCriteria(RecursoTipoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RecursoTipo> specification = createSpecification(criteria);
        return recursoTipoMapper.toDto(recursoTipoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RecursoTipoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RecursoTipoDTO> findByCriteria(RecursoTipoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RecursoTipo> specification = createSpecification(criteria);
        return recursoTipoRepository.findAll(specification, page)
            .map(recursoTipoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RecursoTipoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RecursoTipo> specification = createSpecification(criteria);
        return recursoTipoRepository.count(specification);
    }

    /**
     * Function to convert {@link RecursoTipoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RecursoTipo> createSpecification(RecursoTipoCriteria criteria) {
        Specification<RecursoTipo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RecursoTipo_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), RecursoTipo_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), RecursoTipo_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), RecursoTipo_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), RecursoTipo_.updated));
            }
        }
        return specification;
    }
}
