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

import org.tempestade.nucleo.domain.TipoEnvio;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.TipoEnvioRepository;
import org.tempestade.nucleo.service.dto.TipoEnvioCriteria;
import org.tempestade.nucleo.service.dto.TipoEnvioDTO;
import org.tempestade.nucleo.service.mapper.TipoEnvioMapper;

/**
 * Service for executing complex queries for {@link TipoEnvio} entities in the database.
 * The main input is a {@link TipoEnvioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoEnvioDTO} or a {@link Page} of {@link TipoEnvioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoEnvioQueryService extends QueryService<TipoEnvio> {

    private final Logger log = LoggerFactory.getLogger(TipoEnvioQueryService.class);

    private final TipoEnvioRepository tipoEnvioRepository;

    private final TipoEnvioMapper tipoEnvioMapper;

    public TipoEnvioQueryService(TipoEnvioRepository tipoEnvioRepository, TipoEnvioMapper tipoEnvioMapper) {
        this.tipoEnvioRepository = tipoEnvioRepository;
        this.tipoEnvioMapper = tipoEnvioMapper;
    }

    /**
     * Return a {@link List} of {@link TipoEnvioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoEnvioDTO> findByCriteria(TipoEnvioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoEnvio> specification = createSpecification(criteria);
        return tipoEnvioMapper.toDto(tipoEnvioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TipoEnvioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoEnvioDTO> findByCriteria(TipoEnvioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoEnvio> specification = createSpecification(criteria);
        return tipoEnvioRepository.findAll(specification, page)
            .map(tipoEnvioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoEnvioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoEnvio> specification = createSpecification(criteria);
        return tipoEnvioRepository.count(specification);
    }

    /**
     * Function to convert {@link TipoEnvioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TipoEnvio> createSpecification(TipoEnvioCriteria criteria) {
        Specification<TipoEnvio> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TipoEnvio_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TipoEnvio_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), TipoEnvio_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), TipoEnvio_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), TipoEnvio_.updated));
            }
        }
        return specification;
    }
}
