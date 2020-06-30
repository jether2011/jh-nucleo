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

import org.tempestade.nucleo.domain.VariavelMeteorologica;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.VariavelMeteorologicaRepository;
import org.tempestade.nucleo.service.dto.VariavelMeteorologicaCriteria;
import org.tempestade.nucleo.service.dto.VariavelMeteorologicaDTO;
import org.tempestade.nucleo.service.mapper.VariavelMeteorologicaMapper;

/**
 * Service for executing complex queries for {@link VariavelMeteorologica} entities in the database.
 * The main input is a {@link VariavelMeteorologicaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VariavelMeteorologicaDTO} or a {@link Page} of {@link VariavelMeteorologicaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VariavelMeteorologicaQueryService extends QueryService<VariavelMeteorologica> {

    private final Logger log = LoggerFactory.getLogger(VariavelMeteorologicaQueryService.class);

    private final VariavelMeteorologicaRepository variavelMeteorologicaRepository;

    private final VariavelMeteorologicaMapper variavelMeteorologicaMapper;

    public VariavelMeteorologicaQueryService(VariavelMeteorologicaRepository variavelMeteorologicaRepository, VariavelMeteorologicaMapper variavelMeteorologicaMapper) {
        this.variavelMeteorologicaRepository = variavelMeteorologicaRepository;
        this.variavelMeteorologicaMapper = variavelMeteorologicaMapper;
    }

    /**
     * Return a {@link List} of {@link VariavelMeteorologicaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VariavelMeteorologicaDTO> findByCriteria(VariavelMeteorologicaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VariavelMeteorologica> specification = createSpecification(criteria);
        return variavelMeteorologicaMapper.toDto(variavelMeteorologicaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VariavelMeteorologicaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VariavelMeteorologicaDTO> findByCriteria(VariavelMeteorologicaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VariavelMeteorologica> specification = createSpecification(criteria);
        return variavelMeteorologicaRepository.findAll(specification, page)
            .map(variavelMeteorologicaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VariavelMeteorologicaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VariavelMeteorologica> specification = createSpecification(criteria);
        return variavelMeteorologicaRepository.count(specification);
    }

    /**
     * Function to convert {@link VariavelMeteorologicaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VariavelMeteorologica> createSpecification(VariavelMeteorologicaCriteria criteria) {
        Specification<VariavelMeteorologica> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VariavelMeteorologica_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), VariavelMeteorologica_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), VariavelMeteorologica_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), VariavelMeteorologica_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), VariavelMeteorologica_.updated));
            }
        }
        return specification;
    }
}
