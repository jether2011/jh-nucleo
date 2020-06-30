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

import org.tempestade.nucleo.domain.PontosCardeais;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.PontosCardeaisRepository;
import org.tempestade.nucleo.service.dto.PontosCardeaisCriteria;
import org.tempestade.nucleo.service.dto.PontosCardeaisDTO;
import org.tempestade.nucleo.service.mapper.PontosCardeaisMapper;

/**
 * Service for executing complex queries for {@link PontosCardeais} entities in the database.
 * The main input is a {@link PontosCardeaisCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PontosCardeaisDTO} or a {@link Page} of {@link PontosCardeaisDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PontosCardeaisQueryService extends QueryService<PontosCardeais> {

    private final Logger log = LoggerFactory.getLogger(PontosCardeaisQueryService.class);

    private final PontosCardeaisRepository pontosCardeaisRepository;

    private final PontosCardeaisMapper pontosCardeaisMapper;

    public PontosCardeaisQueryService(PontosCardeaisRepository pontosCardeaisRepository, PontosCardeaisMapper pontosCardeaisMapper) {
        this.pontosCardeaisRepository = pontosCardeaisRepository;
        this.pontosCardeaisMapper = pontosCardeaisMapper;
    }

    /**
     * Return a {@link List} of {@link PontosCardeaisDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PontosCardeaisDTO> findByCriteria(PontosCardeaisCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PontosCardeais> specification = createSpecification(criteria);
        return pontosCardeaisMapper.toDto(pontosCardeaisRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PontosCardeaisDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PontosCardeaisDTO> findByCriteria(PontosCardeaisCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PontosCardeais> specification = createSpecification(criteria);
        return pontosCardeaisRepository.findAll(specification, page)
            .map(pontosCardeaisMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PontosCardeaisCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PontosCardeais> specification = createSpecification(criteria);
        return pontosCardeaisRepository.count(specification);
    }

    /**
     * Function to convert {@link PontosCardeaisCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PontosCardeais> createSpecification(PontosCardeaisCriteria criteria) {
        Specification<PontosCardeais> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PontosCardeais_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PontosCardeais_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), PontosCardeais_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), PontosCardeais_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), PontosCardeais_.updated));
            }
        }
        return specification;
    }
}
