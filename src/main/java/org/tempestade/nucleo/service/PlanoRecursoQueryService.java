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

import org.tempestade.nucleo.domain.PlanoRecurso;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.PlanoRecursoRepository;
import org.tempestade.nucleo.service.dto.PlanoRecursoCriteria;
import org.tempestade.nucleo.service.dto.PlanoRecursoDTO;
import org.tempestade.nucleo.service.mapper.PlanoRecursoMapper;

/**
 * Service for executing complex queries for {@link PlanoRecurso} entities in the database.
 * The main input is a {@link PlanoRecursoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlanoRecursoDTO} or a {@link Page} of {@link PlanoRecursoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanoRecursoQueryService extends QueryService<PlanoRecurso> {

    private final Logger log = LoggerFactory.getLogger(PlanoRecursoQueryService.class);

    private final PlanoRecursoRepository planoRecursoRepository;

    private final PlanoRecursoMapper planoRecursoMapper;

    public PlanoRecursoQueryService(PlanoRecursoRepository planoRecursoRepository, PlanoRecursoMapper planoRecursoMapper) {
        this.planoRecursoRepository = planoRecursoRepository;
        this.planoRecursoMapper = planoRecursoMapper;
    }

    /**
     * Return a {@link List} of {@link PlanoRecursoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlanoRecursoDTO> findByCriteria(PlanoRecursoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PlanoRecurso> specification = createSpecification(criteria);
        return planoRecursoMapper.toDto(planoRecursoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlanoRecursoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoRecursoDTO> findByCriteria(PlanoRecursoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PlanoRecurso> specification = createSpecification(criteria);
        return planoRecursoRepository.findAll(specification, page)
            .map(planoRecursoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanoRecursoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlanoRecurso> specification = createSpecification(criteria);
        return planoRecursoRepository.count(specification);
    }

    /**
     * Function to convert {@link PlanoRecursoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlanoRecurso> createSpecification(PlanoRecursoCriteria criteria) {
        Specification<PlanoRecurso> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PlanoRecurso_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PlanoRecurso_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), PlanoRecurso_.descricao));
            }
            if (criteria.getAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getAtivo(), PlanoRecurso_.ativo));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), PlanoRecurso_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), PlanoRecurso_.updated));
            }
            if (criteria.getPlanoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoId(),
                    root -> root.join(PlanoRecurso_.plano, JoinType.LEFT).get(Plano_.id)));
            }
            if (criteria.getRecursoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRecursoId(),
                    root -> root.join(PlanoRecurso_.recurso, JoinType.LEFT).get(Recurso_.id)));
            }
        }
        return specification;
    }
}
