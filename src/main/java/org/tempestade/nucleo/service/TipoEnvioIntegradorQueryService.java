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

import org.tempestade.nucleo.domain.TipoEnvioIntegrador;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.TipoEnvioIntegradorRepository;
import org.tempestade.nucleo.service.dto.TipoEnvioIntegradorCriteria;
import org.tempestade.nucleo.service.dto.TipoEnvioIntegradorDTO;
import org.tempestade.nucleo.service.mapper.TipoEnvioIntegradorMapper;

/**
 * Service for executing complex queries for {@link TipoEnvioIntegrador} entities in the database.
 * The main input is a {@link TipoEnvioIntegradorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoEnvioIntegradorDTO} or a {@link Page} of {@link TipoEnvioIntegradorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoEnvioIntegradorQueryService extends QueryService<TipoEnvioIntegrador> {

    private final Logger log = LoggerFactory.getLogger(TipoEnvioIntegradorQueryService.class);

    private final TipoEnvioIntegradorRepository tipoEnvioIntegradorRepository;

    private final TipoEnvioIntegradorMapper tipoEnvioIntegradorMapper;

    public TipoEnvioIntegradorQueryService(TipoEnvioIntegradorRepository tipoEnvioIntegradorRepository, TipoEnvioIntegradorMapper tipoEnvioIntegradorMapper) {
        this.tipoEnvioIntegradorRepository = tipoEnvioIntegradorRepository;
        this.tipoEnvioIntegradorMapper = tipoEnvioIntegradorMapper;
    }

    /**
     * Return a {@link List} of {@link TipoEnvioIntegradorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoEnvioIntegradorDTO> findByCriteria(TipoEnvioIntegradorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoEnvioIntegrador> specification = createSpecification(criteria);
        return tipoEnvioIntegradorMapper.toDto(tipoEnvioIntegradorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TipoEnvioIntegradorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoEnvioIntegradorDTO> findByCriteria(TipoEnvioIntegradorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoEnvioIntegrador> specification = createSpecification(criteria);
        return tipoEnvioIntegradorRepository.findAll(specification, page)
            .map(tipoEnvioIntegradorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoEnvioIntegradorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoEnvioIntegrador> specification = createSpecification(criteria);
        return tipoEnvioIntegradorRepository.count(specification);
    }

    /**
     * Function to convert {@link TipoEnvioIntegradorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TipoEnvioIntegrador> createSpecification(TipoEnvioIntegradorCriteria criteria) {
        Specification<TipoEnvioIntegrador> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TipoEnvioIntegrador_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TipoEnvioIntegrador_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), TipoEnvioIntegrador_.descricao));
            }
            if (criteria.getAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getAtivo(), TipoEnvioIntegrador_.ativo));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), TipoEnvioIntegrador_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), TipoEnvioIntegrador_.updated));
            }
            if (criteria.getTipoEnvioId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoEnvioId(),
                    root -> root.join(TipoEnvioIntegrador_.tipoEnvio, JoinType.LEFT).get(TipoEnvio_.id)));
            }
            if (criteria.getIntegradorId() != null) {
                specification = specification.and(buildSpecification(criteria.getIntegradorId(),
                    root -> root.join(TipoEnvioIntegrador_.integrador, JoinType.LEFT).get(Integrador_.id)));
            }
        }
        return specification;
    }
}
