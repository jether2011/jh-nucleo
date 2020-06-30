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

import org.tempestade.nucleo.domain.TempestadeProbabilidade;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.TempestadeProbabilidadeRepository;
import org.tempestade.nucleo.service.dto.TempestadeProbabilidadeCriteria;
import org.tempestade.nucleo.service.dto.TempestadeProbabilidadeDTO;
import org.tempestade.nucleo.service.mapper.TempestadeProbabilidadeMapper;

/**
 * Service for executing complex queries for {@link TempestadeProbabilidade} entities in the database.
 * The main input is a {@link TempestadeProbabilidadeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TempestadeProbabilidadeDTO} or a {@link Page} of {@link TempestadeProbabilidadeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TempestadeProbabilidadeQueryService extends QueryService<TempestadeProbabilidade> {

    private final Logger log = LoggerFactory.getLogger(TempestadeProbabilidadeQueryService.class);

    private final TempestadeProbabilidadeRepository tempestadeProbabilidadeRepository;

    private final TempestadeProbabilidadeMapper tempestadeProbabilidadeMapper;

    public TempestadeProbabilidadeQueryService(TempestadeProbabilidadeRepository tempestadeProbabilidadeRepository, TempestadeProbabilidadeMapper tempestadeProbabilidadeMapper) {
        this.tempestadeProbabilidadeRepository = tempestadeProbabilidadeRepository;
        this.tempestadeProbabilidadeMapper = tempestadeProbabilidadeMapper;
    }

    /**
     * Return a {@link List} of {@link TempestadeProbabilidadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TempestadeProbabilidadeDTO> findByCriteria(TempestadeProbabilidadeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TempestadeProbabilidade> specification = createSpecification(criteria);
        return tempestadeProbabilidadeMapper.toDto(tempestadeProbabilidadeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TempestadeProbabilidadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TempestadeProbabilidadeDTO> findByCriteria(TempestadeProbabilidadeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TempestadeProbabilidade> specification = createSpecification(criteria);
        return tempestadeProbabilidadeRepository.findAll(specification, page)
            .map(tempestadeProbabilidadeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TempestadeProbabilidadeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TempestadeProbabilidade> specification = createSpecification(criteria);
        return tempestadeProbabilidadeRepository.count(specification);
    }

    /**
     * Function to convert {@link TempestadeProbabilidadeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TempestadeProbabilidade> createSpecification(TempestadeProbabilidadeCriteria criteria) {
        Specification<TempestadeProbabilidade> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TempestadeProbabilidade_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TempestadeProbabilidade_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), TempestadeProbabilidade_.descricao));
            }
            if (criteria.getFaixa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFaixa(), TempestadeProbabilidade_.faixa));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), TempestadeProbabilidade_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), TempestadeProbabilidade_.updated));
            }
        }
        return specification;
    }
}
