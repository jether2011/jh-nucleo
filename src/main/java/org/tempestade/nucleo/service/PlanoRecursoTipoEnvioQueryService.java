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

import org.tempestade.nucleo.domain.PlanoRecursoTipoEnvio;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.PlanoRecursoTipoEnvioRepository;
import org.tempestade.nucleo.service.dto.PlanoRecursoTipoEnvioCriteria;
import org.tempestade.nucleo.service.dto.PlanoRecursoTipoEnvioDTO;
import org.tempestade.nucleo.service.mapper.PlanoRecursoTipoEnvioMapper;

/**
 * Service for executing complex queries for {@link PlanoRecursoTipoEnvio} entities in the database.
 * The main input is a {@link PlanoRecursoTipoEnvioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlanoRecursoTipoEnvioDTO} or a {@link Page} of {@link PlanoRecursoTipoEnvioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanoRecursoTipoEnvioQueryService extends QueryService<PlanoRecursoTipoEnvio> {

    private final Logger log = LoggerFactory.getLogger(PlanoRecursoTipoEnvioQueryService.class);

    private final PlanoRecursoTipoEnvioRepository planoRecursoTipoEnvioRepository;

    private final PlanoRecursoTipoEnvioMapper planoRecursoTipoEnvioMapper;

    public PlanoRecursoTipoEnvioQueryService(PlanoRecursoTipoEnvioRepository planoRecursoTipoEnvioRepository, PlanoRecursoTipoEnvioMapper planoRecursoTipoEnvioMapper) {
        this.planoRecursoTipoEnvioRepository = planoRecursoTipoEnvioRepository;
        this.planoRecursoTipoEnvioMapper = planoRecursoTipoEnvioMapper;
    }

    /**
     * Return a {@link List} of {@link PlanoRecursoTipoEnvioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlanoRecursoTipoEnvioDTO> findByCriteria(PlanoRecursoTipoEnvioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PlanoRecursoTipoEnvio> specification = createSpecification(criteria);
        return planoRecursoTipoEnvioMapper.toDto(planoRecursoTipoEnvioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlanoRecursoTipoEnvioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoRecursoTipoEnvioDTO> findByCriteria(PlanoRecursoTipoEnvioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PlanoRecursoTipoEnvio> specification = createSpecification(criteria);
        return planoRecursoTipoEnvioRepository.findAll(specification, page)
            .map(planoRecursoTipoEnvioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanoRecursoTipoEnvioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlanoRecursoTipoEnvio> specification = createSpecification(criteria);
        return planoRecursoTipoEnvioRepository.count(specification);
    }

    /**
     * Function to convert {@link PlanoRecursoTipoEnvioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlanoRecursoTipoEnvio> createSpecification(PlanoRecursoTipoEnvioCriteria criteria) {
        Specification<PlanoRecursoTipoEnvio> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PlanoRecursoTipoEnvio_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PlanoRecursoTipoEnvio_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), PlanoRecursoTipoEnvio_.descricao));
            }
            if (criteria.getQtd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtd(), PlanoRecursoTipoEnvio_.qtd));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), PlanoRecursoTipoEnvio_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), PlanoRecursoTipoEnvio_.updated));
            }
            if (criteria.getPlanoRecursoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoRecursoId(),
                    root -> root.join(PlanoRecursoTipoEnvio_.planoRecurso, JoinType.LEFT).get(PlanoRecurso_.id)));
            }
            if (criteria.getTipoEnvioId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoEnvioId(),
                    root -> root.join(PlanoRecursoTipoEnvio_.tipoEnvio, JoinType.LEFT).get(TipoEnvio_.id)));
            }
        }
        return specification;
    }
}
