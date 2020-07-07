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

import org.tempestade.nucleo.domain.AcumuladoChuvaFaixa;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.AcumuladoChuvaFaixaRepository;
import org.tempestade.nucleo.service.dto.AcumuladoChuvaFaixaCriteria;
import org.tempestade.nucleo.service.dto.AcumuladoChuvaFaixaDTO;
import org.tempestade.nucleo.service.mapper.AcumuladoChuvaFaixaMapper;

/**
 * Service for executing complex queries for {@link AcumuladoChuvaFaixa} entities in the database.
 * The main input is a {@link AcumuladoChuvaFaixaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AcumuladoChuvaFaixaDTO} or a {@link Page} of {@link AcumuladoChuvaFaixaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AcumuladoChuvaFaixaQueryService extends QueryService<AcumuladoChuvaFaixa> {

    private final Logger log = LoggerFactory.getLogger(AcumuladoChuvaFaixaQueryService.class);

    private final AcumuladoChuvaFaixaRepository acumuladoChuvaFaixaRepository;

    private final AcumuladoChuvaFaixaMapper acumuladoChuvaFaixaMapper;

    public AcumuladoChuvaFaixaQueryService(AcumuladoChuvaFaixaRepository acumuladoChuvaFaixaRepository, AcumuladoChuvaFaixaMapper acumuladoChuvaFaixaMapper) {
        this.acumuladoChuvaFaixaRepository = acumuladoChuvaFaixaRepository;
        this.acumuladoChuvaFaixaMapper = acumuladoChuvaFaixaMapper;
    }

    /**
     * Return a {@link List} of {@link AcumuladoChuvaFaixaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AcumuladoChuvaFaixaDTO> findByCriteria(AcumuladoChuvaFaixaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AcumuladoChuvaFaixa> specification = createSpecification(criteria);
        return acumuladoChuvaFaixaMapper.toDto(acumuladoChuvaFaixaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AcumuladoChuvaFaixaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AcumuladoChuvaFaixaDTO> findByCriteria(AcumuladoChuvaFaixaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AcumuladoChuvaFaixa> specification = createSpecification(criteria);
        return acumuladoChuvaFaixaRepository.findAll(specification, page)
            .map(acumuladoChuvaFaixaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AcumuladoChuvaFaixaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AcumuladoChuvaFaixa> specification = createSpecification(criteria);
        return acumuladoChuvaFaixaRepository.count(specification);
    }

    /**
     * Function to convert {@link AcumuladoChuvaFaixaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AcumuladoChuvaFaixa> createSpecification(AcumuladoChuvaFaixaCriteria criteria) {
        Specification<AcumuladoChuvaFaixa> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AcumuladoChuvaFaixa_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), AcumuladoChuvaFaixa_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), AcumuladoChuvaFaixa_.descricao));
            }
            if (criteria.getDeMm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeMm(), AcumuladoChuvaFaixa_.deMm));
            }
            if (criteria.getAteMm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAteMm(), AcumuladoChuvaFaixa_.ateMm));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), AcumuladoChuvaFaixa_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), AcumuladoChuvaFaixa_.updated));
            }
        }
        return specification;
    }
}
