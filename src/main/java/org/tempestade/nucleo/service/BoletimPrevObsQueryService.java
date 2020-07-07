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

import org.tempestade.nucleo.domain.BoletimPrevObs;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.BoletimPrevObsRepository;
import org.tempestade.nucleo.service.dto.BoletimPrevObsCriteria;
import org.tempestade.nucleo.service.dto.BoletimPrevObsDTO;
import org.tempestade.nucleo.service.mapper.BoletimPrevObsMapper;

/**
 * Service for executing complex queries for {@link BoletimPrevObs} entities in the database.
 * The main input is a {@link BoletimPrevObsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BoletimPrevObsDTO} or a {@link Page} of {@link BoletimPrevObsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BoletimPrevObsQueryService extends QueryService<BoletimPrevObs> {

    private final Logger log = LoggerFactory.getLogger(BoletimPrevObsQueryService.class);

    private final BoletimPrevObsRepository boletimPrevObsRepository;

    private final BoletimPrevObsMapper boletimPrevObsMapper;

    public BoletimPrevObsQueryService(BoletimPrevObsRepository boletimPrevObsRepository, BoletimPrevObsMapper boletimPrevObsMapper) {
        this.boletimPrevObsRepository = boletimPrevObsRepository;
        this.boletimPrevObsMapper = boletimPrevObsMapper;
    }

    /**
     * Return a {@link List} of {@link BoletimPrevObsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BoletimPrevObsDTO> findByCriteria(BoletimPrevObsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BoletimPrevObs> specification = createSpecification(criteria);
        return boletimPrevObsMapper.toDto(boletimPrevObsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BoletimPrevObsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BoletimPrevObsDTO> findByCriteria(BoletimPrevObsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BoletimPrevObs> specification = createSpecification(criteria);
        return boletimPrevObsRepository.findAll(specification, page)
            .map(boletimPrevObsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BoletimPrevObsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BoletimPrevObs> specification = createSpecification(criteria);
        return boletimPrevObsRepository.count(specification);
    }

    /**
     * Function to convert {@link BoletimPrevObsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BoletimPrevObs> createSpecification(BoletimPrevObsCriteria criteria) {
        Specification<BoletimPrevObs> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BoletimPrevObs_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), BoletimPrevObs_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), BoletimPrevObs_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), BoletimPrevObs_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), BoletimPrevObs_.updated));
            }
        }
        return specification;
    }
}
