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

import org.tempestade.nucleo.domain.DescargaUnidade;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.DescargaUnidadeRepository;
import org.tempestade.nucleo.service.dto.DescargaUnidadeCriteria;
import org.tempestade.nucleo.service.dto.DescargaUnidadeDTO;
import org.tempestade.nucleo.service.mapper.DescargaUnidadeMapper;

/**
 * Service for executing complex queries for {@link DescargaUnidade} entities in the database.
 * The main input is a {@link DescargaUnidadeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DescargaUnidadeDTO} or a {@link Page} of {@link DescargaUnidadeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DescargaUnidadeQueryService extends QueryService<DescargaUnidade> {

    private final Logger log = LoggerFactory.getLogger(DescargaUnidadeQueryService.class);

    private final DescargaUnidadeRepository descargaUnidadeRepository;

    private final DescargaUnidadeMapper descargaUnidadeMapper;

    public DescargaUnidadeQueryService(DescargaUnidadeRepository descargaUnidadeRepository, DescargaUnidadeMapper descargaUnidadeMapper) {
        this.descargaUnidadeRepository = descargaUnidadeRepository;
        this.descargaUnidadeMapper = descargaUnidadeMapper;
    }

    /**
     * Return a {@link List} of {@link DescargaUnidadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DescargaUnidadeDTO> findByCriteria(DescargaUnidadeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DescargaUnidade> specification = createSpecification(criteria);
        return descargaUnidadeMapper.toDto(descargaUnidadeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DescargaUnidadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DescargaUnidadeDTO> findByCriteria(DescargaUnidadeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DescargaUnidade> specification = createSpecification(criteria);
        return descargaUnidadeRepository.findAll(specification, page)
            .map(descargaUnidadeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DescargaUnidadeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DescargaUnidade> specification = createSpecification(criteria);
        return descargaUnidadeRepository.count(specification);
    }

    /**
     * Function to convert {@link DescargaUnidadeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DescargaUnidade> createSpecification(DescargaUnidadeCriteria criteria) {
        Specification<DescargaUnidade> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DescargaUnidade_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), DescargaUnidade_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), DescargaUnidade_.descricao));
            }
            if (criteria.getUnidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnidade(), DescargaUnidade_.unidade));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), DescargaUnidade_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), DescargaUnidade_.updated));
            }
        }
        return specification;
    }
}
