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

import org.tempestade.nucleo.domain.TipoFerramenta;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.TipoFerramentaRepository;
import org.tempestade.nucleo.service.dto.TipoFerramentaCriteria;
import org.tempestade.nucleo.service.dto.TipoFerramentaDTO;
import org.tempestade.nucleo.service.mapper.TipoFerramentaMapper;

/**
 * Service for executing complex queries for {@link TipoFerramenta} entities in the database.
 * The main input is a {@link TipoFerramentaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoFerramentaDTO} or a {@link Page} of {@link TipoFerramentaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoFerramentaQueryService extends QueryService<TipoFerramenta> {

    private final Logger log = LoggerFactory.getLogger(TipoFerramentaQueryService.class);

    private final TipoFerramentaRepository tipoFerramentaRepository;

    private final TipoFerramentaMapper tipoFerramentaMapper;

    public TipoFerramentaQueryService(TipoFerramentaRepository tipoFerramentaRepository, TipoFerramentaMapper tipoFerramentaMapper) {
        this.tipoFerramentaRepository = tipoFerramentaRepository;
        this.tipoFerramentaMapper = tipoFerramentaMapper;
    }

    /**
     * Return a {@link List} of {@link TipoFerramentaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoFerramentaDTO> findByCriteria(TipoFerramentaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoFerramenta> specification = createSpecification(criteria);
        return tipoFerramentaMapper.toDto(tipoFerramentaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TipoFerramentaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoFerramentaDTO> findByCriteria(TipoFerramentaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoFerramenta> specification = createSpecification(criteria);
        return tipoFerramentaRepository.findAll(specification, page)
            .map(tipoFerramentaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoFerramentaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoFerramenta> specification = createSpecification(criteria);
        return tipoFerramentaRepository.count(specification);
    }

    /**
     * Function to convert {@link TipoFerramentaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TipoFerramenta> createSpecification(TipoFerramentaCriteria criteria) {
        Specification<TipoFerramenta> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TipoFerramenta_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TipoFerramenta_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), TipoFerramenta_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), TipoFerramenta_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), TipoFerramenta_.updated));
            }
        }
        return specification;
    }
}
