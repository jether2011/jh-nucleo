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

import org.tempestade.nucleo.domain.VentoVmFaixa;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.VentoVmFaixaRepository;
import org.tempestade.nucleo.service.dto.VentoVmFaixaCriteria;
import org.tempestade.nucleo.service.dto.VentoVmFaixaDTO;
import org.tempestade.nucleo.service.mapper.VentoVmFaixaMapper;

/**
 * Service for executing complex queries for {@link VentoVmFaixa} entities in the database.
 * The main input is a {@link VentoVmFaixaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VentoVmFaixaDTO} or a {@link Page} of {@link VentoVmFaixaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VentoVmFaixaQueryService extends QueryService<VentoVmFaixa> {

    private final Logger log = LoggerFactory.getLogger(VentoVmFaixaQueryService.class);

    private final VentoVmFaixaRepository ventoVmFaixaRepository;

    private final VentoVmFaixaMapper ventoVmFaixaMapper;

    public VentoVmFaixaQueryService(VentoVmFaixaRepository ventoVmFaixaRepository, VentoVmFaixaMapper ventoVmFaixaMapper) {
        this.ventoVmFaixaRepository = ventoVmFaixaRepository;
        this.ventoVmFaixaMapper = ventoVmFaixaMapper;
    }

    /**
     * Return a {@link List} of {@link VentoVmFaixaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VentoVmFaixaDTO> findByCriteria(VentoVmFaixaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VentoVmFaixa> specification = createSpecification(criteria);
        return ventoVmFaixaMapper.toDto(ventoVmFaixaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VentoVmFaixaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VentoVmFaixaDTO> findByCriteria(VentoVmFaixaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VentoVmFaixa> specification = createSpecification(criteria);
        return ventoVmFaixaRepository.findAll(specification, page)
            .map(ventoVmFaixaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VentoVmFaixaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VentoVmFaixa> specification = createSpecification(criteria);
        return ventoVmFaixaRepository.count(specification);
    }

    /**
     * Function to convert {@link VentoVmFaixaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VentoVmFaixa> createSpecification(VentoVmFaixaCriteria criteria) {
        Specification<VentoVmFaixa> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VentoVmFaixa_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), VentoVmFaixa_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), VentoVmFaixa_.descricao));
            }
            if (criteria.getDe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDe(), VentoVmFaixa_.de));
            }
            if (criteria.getAte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAte(), VentoVmFaixa_.ate));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), VentoVmFaixa_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), VentoVmFaixa_.updated));
            }
        }
        return specification;
    }
}
