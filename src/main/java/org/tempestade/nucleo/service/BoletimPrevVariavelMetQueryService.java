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

import org.tempestade.nucleo.domain.BoletimPrevVariavelMet;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.BoletimPrevVariavelMetRepository;
import org.tempestade.nucleo.service.dto.BoletimPrevVariavelMetCriteria;
import org.tempestade.nucleo.service.dto.BoletimPrevVariavelMetDTO;
import org.tempestade.nucleo.service.mapper.BoletimPrevVariavelMetMapper;

/**
 * Service for executing complex queries for {@link BoletimPrevVariavelMet} entities in the database.
 * The main input is a {@link BoletimPrevVariavelMetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BoletimPrevVariavelMetDTO} or a {@link Page} of {@link BoletimPrevVariavelMetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BoletimPrevVariavelMetQueryService extends QueryService<BoletimPrevVariavelMet> {

    private final Logger log = LoggerFactory.getLogger(BoletimPrevVariavelMetQueryService.class);

    private final BoletimPrevVariavelMetRepository boletimPrevVariavelMetRepository;

    private final BoletimPrevVariavelMetMapper boletimPrevVariavelMetMapper;

    public BoletimPrevVariavelMetQueryService(BoletimPrevVariavelMetRepository boletimPrevVariavelMetRepository, BoletimPrevVariavelMetMapper boletimPrevVariavelMetMapper) {
        this.boletimPrevVariavelMetRepository = boletimPrevVariavelMetRepository;
        this.boletimPrevVariavelMetMapper = boletimPrevVariavelMetMapper;
    }

    /**
     * Return a {@link List} of {@link BoletimPrevVariavelMetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BoletimPrevVariavelMetDTO> findByCriteria(BoletimPrevVariavelMetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BoletimPrevVariavelMet> specification = createSpecification(criteria);
        return boletimPrevVariavelMetMapper.toDto(boletimPrevVariavelMetRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BoletimPrevVariavelMetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BoletimPrevVariavelMetDTO> findByCriteria(BoletimPrevVariavelMetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BoletimPrevVariavelMet> specification = createSpecification(criteria);
        return boletimPrevVariavelMetRepository.findAll(specification, page)
            .map(boletimPrevVariavelMetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BoletimPrevVariavelMetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BoletimPrevVariavelMet> specification = createSpecification(criteria);
        return boletimPrevVariavelMetRepository.count(specification);
    }

    /**
     * Function to convert {@link BoletimPrevVariavelMetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BoletimPrevVariavelMet> createSpecification(BoletimPrevVariavelMetCriteria criteria) {
        Specification<BoletimPrevVariavelMet> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BoletimPrevVariavelMet_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), BoletimPrevVariavelMet_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), BoletimPrevVariavelMet_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), BoletimPrevVariavelMet_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), BoletimPrevVariavelMet_.updated));
            }
            if (criteria.getBoletimPrevisaoId() != null) {
                specification = specification.and(buildSpecification(criteria.getBoletimPrevisaoId(),
                    root -> root.join(BoletimPrevVariavelMet_.boletimPrevisao, JoinType.LEFT).get(BoletimPrevisao_.id)));
            }
            if (criteria.getVariavelMeteorologicaId() != null) {
                specification = specification.and(buildSpecification(criteria.getVariavelMeteorologicaId(),
                    root -> root.join(BoletimPrevVariavelMet_.variavelMeteorologica, JoinType.LEFT).get(VariavelMeteorologica_.id)));
            }
        }
        return specification;
    }
}
