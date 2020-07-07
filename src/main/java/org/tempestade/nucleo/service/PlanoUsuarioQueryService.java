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

import org.tempestade.nucleo.domain.PlanoUsuario;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.PlanoUsuarioRepository;
import org.tempestade.nucleo.service.dto.PlanoUsuarioCriteria;
import org.tempestade.nucleo.service.dto.PlanoUsuarioDTO;
import org.tempestade.nucleo.service.mapper.PlanoUsuarioMapper;

/**
 * Service for executing complex queries for {@link PlanoUsuario} entities in the database.
 * The main input is a {@link PlanoUsuarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlanoUsuarioDTO} or a {@link Page} of {@link PlanoUsuarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanoUsuarioQueryService extends QueryService<PlanoUsuario> {

    private final Logger log = LoggerFactory.getLogger(PlanoUsuarioQueryService.class);

    private final PlanoUsuarioRepository planoUsuarioRepository;

    private final PlanoUsuarioMapper planoUsuarioMapper;

    public PlanoUsuarioQueryService(PlanoUsuarioRepository planoUsuarioRepository, PlanoUsuarioMapper planoUsuarioMapper) {
        this.planoUsuarioRepository = planoUsuarioRepository;
        this.planoUsuarioMapper = planoUsuarioMapper;
    }

    /**
     * Return a {@link List} of {@link PlanoUsuarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlanoUsuarioDTO> findByCriteria(PlanoUsuarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PlanoUsuario> specification = createSpecification(criteria);
        return planoUsuarioMapper.toDto(planoUsuarioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlanoUsuarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoUsuarioDTO> findByCriteria(PlanoUsuarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PlanoUsuario> specification = createSpecification(criteria);
        return planoUsuarioRepository.findAll(specification, page)
            .map(planoUsuarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanoUsuarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlanoUsuario> specification = createSpecification(criteria);
        return planoUsuarioRepository.count(specification);
    }

    /**
     * Function to convert {@link PlanoUsuarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlanoUsuario> createSpecification(PlanoUsuarioCriteria criteria) {
        Specification<PlanoUsuario> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PlanoUsuario_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PlanoUsuario_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), PlanoUsuario_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), PlanoUsuario_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), PlanoUsuario_.updated));
            }
            if (criteria.getPlanoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoId(),
                    root -> root.join(PlanoUsuario_.plano, JoinType.LEFT).get(Plano_.id)));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(PlanoUsuario_.usuario, JoinType.LEFT).get(Usuario_.id)));
            }
        }
        return specification;
    }
}
