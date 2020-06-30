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

import org.tempestade.nucleo.domain.DescargaTipo;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.DescargaTipoRepository;
import org.tempestade.nucleo.service.dto.DescargaTipoCriteria;
import org.tempestade.nucleo.service.dto.DescargaTipoDTO;
import org.tempestade.nucleo.service.mapper.DescargaTipoMapper;

/**
 * Service for executing complex queries for {@link DescargaTipo} entities in the database.
 * The main input is a {@link DescargaTipoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DescargaTipoDTO} or a {@link Page} of {@link DescargaTipoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DescargaTipoQueryService extends QueryService<DescargaTipo> {

    private final Logger log = LoggerFactory.getLogger(DescargaTipoQueryService.class);

    private final DescargaTipoRepository descargaTipoRepository;

    private final DescargaTipoMapper descargaTipoMapper;

    public DescargaTipoQueryService(DescargaTipoRepository descargaTipoRepository, DescargaTipoMapper descargaTipoMapper) {
        this.descargaTipoRepository = descargaTipoRepository;
        this.descargaTipoMapper = descargaTipoMapper;
    }

    /**
     * Return a {@link List} of {@link DescargaTipoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DescargaTipoDTO> findByCriteria(DescargaTipoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DescargaTipo> specification = createSpecification(criteria);
        return descargaTipoMapper.toDto(descargaTipoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DescargaTipoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DescargaTipoDTO> findByCriteria(DescargaTipoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DescargaTipo> specification = createSpecification(criteria);
        return descargaTipoRepository.findAll(specification, page)
            .map(descargaTipoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DescargaTipoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DescargaTipo> specification = createSpecification(criteria);
        return descargaTipoRepository.count(specification);
    }

    /**
     * Function to convert {@link DescargaTipoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DescargaTipo> createSpecification(DescargaTipoCriteria criteria) {
        Specification<DescargaTipo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DescargaTipo_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), DescargaTipo_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), DescargaTipo_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), DescargaTipo_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), DescargaTipo_.updated));
            }
        }
        return specification;
    }
}
