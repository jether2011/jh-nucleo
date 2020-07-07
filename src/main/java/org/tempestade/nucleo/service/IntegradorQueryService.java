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

import org.tempestade.nucleo.domain.Integrador;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.IntegradorRepository;
import org.tempestade.nucleo.service.dto.IntegradorCriteria;
import org.tempestade.nucleo.service.dto.IntegradorDTO;
import org.tempestade.nucleo.service.mapper.IntegradorMapper;

/**
 * Service for executing complex queries for {@link Integrador} entities in the database.
 * The main input is a {@link IntegradorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IntegradorDTO} or a {@link Page} of {@link IntegradorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IntegradorQueryService extends QueryService<Integrador> {

    private final Logger log = LoggerFactory.getLogger(IntegradorQueryService.class);

    private final IntegradorRepository integradorRepository;

    private final IntegradorMapper integradorMapper;

    public IntegradorQueryService(IntegradorRepository integradorRepository, IntegradorMapper integradorMapper) {
        this.integradorRepository = integradorRepository;
        this.integradorMapper = integradorMapper;
    }

    /**
     * Return a {@link List} of {@link IntegradorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IntegradorDTO> findByCriteria(IntegradorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Integrador> specification = createSpecification(criteria);
        return integradorMapper.toDto(integradorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IntegradorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IntegradorDTO> findByCriteria(IntegradorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Integrador> specification = createSpecification(criteria);
        return integradorRepository.findAll(specification, page)
            .map(integradorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IntegradorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Integrador> specification = createSpecification(criteria);
        return integradorRepository.count(specification);
    }

    /**
     * Function to convert {@link IntegradorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Integrador> createSpecification(IntegradorCriteria criteria) {
        Specification<Integrador> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Integrador_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Integrador_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Integrador_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Integrador_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Integrador_.updated));
            }
        }
        return specification;
    }
}
