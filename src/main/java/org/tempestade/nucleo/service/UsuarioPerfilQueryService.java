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

import org.tempestade.nucleo.domain.UsuarioPerfil;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.UsuarioPerfilRepository;
import org.tempestade.nucleo.service.dto.UsuarioPerfilCriteria;
import org.tempestade.nucleo.service.dto.UsuarioPerfilDTO;
import org.tempestade.nucleo.service.mapper.UsuarioPerfilMapper;

/**
 * Service for executing complex queries for {@link UsuarioPerfil} entities in the database.
 * The main input is a {@link UsuarioPerfilCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UsuarioPerfilDTO} or a {@link Page} of {@link UsuarioPerfilDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UsuarioPerfilQueryService extends QueryService<UsuarioPerfil> {

    private final Logger log = LoggerFactory.getLogger(UsuarioPerfilQueryService.class);

    private final UsuarioPerfilRepository usuarioPerfilRepository;

    private final UsuarioPerfilMapper usuarioPerfilMapper;

    public UsuarioPerfilQueryService(UsuarioPerfilRepository usuarioPerfilRepository, UsuarioPerfilMapper usuarioPerfilMapper) {
        this.usuarioPerfilRepository = usuarioPerfilRepository;
        this.usuarioPerfilMapper = usuarioPerfilMapper;
    }

    /**
     * Return a {@link List} of {@link UsuarioPerfilDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UsuarioPerfilDTO> findByCriteria(UsuarioPerfilCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UsuarioPerfil> specification = createSpecification(criteria);
        return usuarioPerfilMapper.toDto(usuarioPerfilRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UsuarioPerfilDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UsuarioPerfilDTO> findByCriteria(UsuarioPerfilCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UsuarioPerfil> specification = createSpecification(criteria);
        return usuarioPerfilRepository.findAll(specification, page)
            .map(usuarioPerfilMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UsuarioPerfilCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UsuarioPerfil> specification = createSpecification(criteria);
        return usuarioPerfilRepository.count(specification);
    }

    /**
     * Function to convert {@link UsuarioPerfilCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UsuarioPerfil> createSpecification(UsuarioPerfilCriteria criteria) {
        Specification<UsuarioPerfil> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UsuarioPerfil_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), UsuarioPerfil_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), UsuarioPerfil_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), UsuarioPerfil_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), UsuarioPerfil_.updated));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(UsuarioPerfil_.usuario, JoinType.LEFT).get(Usuario_.id)));
            }
            if (criteria.getPerfilId() != null) {
                specification = specification.and(buildSpecification(criteria.getPerfilId(),
                    root -> root.join(UsuarioPerfil_.perfil, JoinType.LEFT).get(Perfil_.id)));
            }
        }
        return specification;
    }
}
