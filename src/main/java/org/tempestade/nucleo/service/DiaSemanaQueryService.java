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

import org.tempestade.nucleo.domain.DiaSemana;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.DiaSemanaRepository;
import org.tempestade.nucleo.service.dto.DiaSemanaCriteria;
import org.tempestade.nucleo.service.dto.DiaSemanaDTO;
import org.tempestade.nucleo.service.mapper.DiaSemanaMapper;

/**
 * Service for executing complex queries for {@link DiaSemana} entities in the database.
 * The main input is a {@link DiaSemanaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DiaSemanaDTO} or a {@link Page} of {@link DiaSemanaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DiaSemanaQueryService extends QueryService<DiaSemana> {

    private final Logger log = LoggerFactory.getLogger(DiaSemanaQueryService.class);

    private final DiaSemanaRepository diaSemanaRepository;

    private final DiaSemanaMapper diaSemanaMapper;

    public DiaSemanaQueryService(DiaSemanaRepository diaSemanaRepository, DiaSemanaMapper diaSemanaMapper) {
        this.diaSemanaRepository = diaSemanaRepository;
        this.diaSemanaMapper = diaSemanaMapper;
    }

    /**
     * Return a {@link List} of {@link DiaSemanaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DiaSemanaDTO> findByCriteria(DiaSemanaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DiaSemana> specification = createSpecification(criteria);
        return diaSemanaMapper.toDto(diaSemanaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DiaSemanaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DiaSemanaDTO> findByCriteria(DiaSemanaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DiaSemana> specification = createSpecification(criteria);
        return diaSemanaRepository.findAll(specification, page)
            .map(diaSemanaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DiaSemanaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DiaSemana> specification = createSpecification(criteria);
        return diaSemanaRepository.count(specification);
    }

    /**
     * Function to convert {@link DiaSemanaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DiaSemana> createSpecification(DiaSemanaCriteria criteria) {
        Specification<DiaSemana> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DiaSemana_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), DiaSemana_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), DiaSemana_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), DiaSemana_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), DiaSemana_.updated));
            }
        }
        return specification;
    }
}
