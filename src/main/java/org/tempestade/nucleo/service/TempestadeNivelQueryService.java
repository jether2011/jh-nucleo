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

import org.tempestade.nucleo.domain.TempestadeNivel;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.TempestadeNivelRepository;
import org.tempestade.nucleo.service.dto.TempestadeNivelCriteria;
import org.tempestade.nucleo.service.dto.TempestadeNivelDTO;
import org.tempestade.nucleo.service.mapper.TempestadeNivelMapper;

/**
 * Service for executing complex queries for {@link TempestadeNivel} entities in the database.
 * The main input is a {@link TempestadeNivelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TempestadeNivelDTO} or a {@link Page} of {@link TempestadeNivelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TempestadeNivelQueryService extends QueryService<TempestadeNivel> {

    private final Logger log = LoggerFactory.getLogger(TempestadeNivelQueryService.class);

    private final TempestadeNivelRepository tempestadeNivelRepository;

    private final TempestadeNivelMapper tempestadeNivelMapper;

    public TempestadeNivelQueryService(TempestadeNivelRepository tempestadeNivelRepository, TempestadeNivelMapper tempestadeNivelMapper) {
        this.tempestadeNivelRepository = tempestadeNivelRepository;
        this.tempestadeNivelMapper = tempestadeNivelMapper;
    }

    /**
     * Return a {@link List} of {@link TempestadeNivelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TempestadeNivelDTO> findByCriteria(TempestadeNivelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TempestadeNivel> specification = createSpecification(criteria);
        return tempestadeNivelMapper.toDto(tempestadeNivelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TempestadeNivelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TempestadeNivelDTO> findByCriteria(TempestadeNivelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TempestadeNivel> specification = createSpecification(criteria);
        return tempestadeNivelRepository.findAll(specification, page)
            .map(tempestadeNivelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TempestadeNivelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TempestadeNivel> specification = createSpecification(criteria);
        return tempestadeNivelRepository.count(specification);
    }

    /**
     * Function to convert {@link TempestadeNivelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TempestadeNivel> createSpecification(TempestadeNivelCriteria criteria) {
        Specification<TempestadeNivel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TempestadeNivel_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TempestadeNivel_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), TempestadeNivel_.descricao));
            }
            if (criteria.getTaxaDeRaios() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxaDeRaios(), TempestadeNivel_.taxaDeRaios));
            }
            if (criteria.getVentosVelocidade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVentosVelocidade(), TempestadeNivel_.ventosVelocidade));
            }
            if (criteria.getGranizo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGranizo(), TempestadeNivel_.granizo));
            }
            if (criteria.getPotencialDeDanos() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPotencialDeDanos(), TempestadeNivel_.potencialDeDanos));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), TempestadeNivel_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), TempestadeNivel_.updated));
            }
            if (criteria.getIntensidadeChuvaId() != null) {
                specification = specification.and(buildSpecification(criteria.getIntensidadeChuvaId(),
                    root -> root.join(TempestadeNivel_.intensidadeChuva, JoinType.LEFT).get(IntensidadeChuva_.id)));
            }
        }
        return specification;
    }
}
