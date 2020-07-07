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

import org.tempestade.nucleo.domain.Recurso;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.RecursoRepository;
import org.tempestade.nucleo.service.dto.RecursoCriteria;
import org.tempestade.nucleo.service.dto.RecursoDTO;
import org.tempestade.nucleo.service.mapper.RecursoMapper;

/**
 * Service for executing complex queries for {@link Recurso} entities in the database.
 * The main input is a {@link RecursoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RecursoDTO} or a {@link Page} of {@link RecursoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RecursoQueryService extends QueryService<Recurso> {

    private final Logger log = LoggerFactory.getLogger(RecursoQueryService.class);

    private final RecursoRepository recursoRepository;

    private final RecursoMapper recursoMapper;

    public RecursoQueryService(RecursoRepository recursoRepository, RecursoMapper recursoMapper) {
        this.recursoRepository = recursoRepository;
        this.recursoMapper = recursoMapper;
    }

    /**
     * Return a {@link List} of {@link RecursoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RecursoDTO> findByCriteria(RecursoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Recurso> specification = createSpecification(criteria);
        return recursoMapper.toDto(recursoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RecursoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RecursoDTO> findByCriteria(RecursoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Recurso> specification = createSpecification(criteria);
        return recursoRepository.findAll(specification, page)
            .map(recursoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RecursoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Recurso> specification = createSpecification(criteria);
        return recursoRepository.count(specification);
    }

    /**
     * Function to convert {@link RecursoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Recurso> createSpecification(RecursoCriteria criteria) {
        Specification<Recurso> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Recurso_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Recurso_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Recurso_.descricao));
            }
            if (criteria.getAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getAtivo(), Recurso_.ativo));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Recurso_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Recurso_.updated));
            }
            if (criteria.getRecursoTipoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRecursoTipoId(),
                    root -> root.join(Recurso_.recursoTipo, JoinType.LEFT).get(RecursoTipo_.id)));
            }
            if (criteria.getVariavelMeteorologicaId() != null) {
                specification = specification.and(buildSpecification(criteria.getVariavelMeteorologicaId(),
                    root -> root.join(Recurso_.variavelMeteorologica, JoinType.LEFT).get(VariavelMeteorologica_.id)));
            }
        }
        return specification;
    }
}
