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

import org.tempestade.nucleo.domain.AvisoTipo;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.AvisoTipoRepository;
import org.tempestade.nucleo.service.dto.AvisoTipoCriteria;
import org.tempestade.nucleo.service.dto.AvisoTipoDTO;
import org.tempestade.nucleo.service.mapper.AvisoTipoMapper;

/**
 * Service for executing complex queries for {@link AvisoTipo} entities in the database.
 * The main input is a {@link AvisoTipoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AvisoTipoDTO} or a {@link Page} of {@link AvisoTipoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AvisoTipoQueryService extends QueryService<AvisoTipo> {

    private final Logger log = LoggerFactory.getLogger(AvisoTipoQueryService.class);

    private final AvisoTipoRepository avisoTipoRepository;

    private final AvisoTipoMapper avisoTipoMapper;

    public AvisoTipoQueryService(AvisoTipoRepository avisoTipoRepository, AvisoTipoMapper avisoTipoMapper) {
        this.avisoTipoRepository = avisoTipoRepository;
        this.avisoTipoMapper = avisoTipoMapper;
    }

    /**
     * Return a {@link List} of {@link AvisoTipoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AvisoTipoDTO> findByCriteria(AvisoTipoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AvisoTipo> specification = createSpecification(criteria);
        return avisoTipoMapper.toDto(avisoTipoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AvisoTipoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AvisoTipoDTO> findByCriteria(AvisoTipoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AvisoTipo> specification = createSpecification(criteria);
        return avisoTipoRepository.findAll(specification, page)
            .map(avisoTipoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AvisoTipoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AvisoTipo> specification = createSpecification(criteria);
        return avisoTipoRepository.count(specification);
    }

    /**
     * Function to convert {@link AvisoTipoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AvisoTipo> createSpecification(AvisoTipoCriteria criteria) {
        Specification<AvisoTipo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AvisoTipo_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), AvisoTipo_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), AvisoTipo_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), AvisoTipo_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), AvisoTipo_.updated));
            }
        }
        return specification;
    }
}
