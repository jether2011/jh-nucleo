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

import org.tempestade.nucleo.domain.ContatoPlanoRecurso;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.ContatoPlanoRecursoRepository;
import org.tempestade.nucleo.service.dto.ContatoPlanoRecursoCriteria;
import org.tempestade.nucleo.service.dto.ContatoPlanoRecursoDTO;
import org.tempestade.nucleo.service.mapper.ContatoPlanoRecursoMapper;

/**
 * Service for executing complex queries for {@link ContatoPlanoRecurso} entities in the database.
 * The main input is a {@link ContatoPlanoRecursoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContatoPlanoRecursoDTO} or a {@link Page} of {@link ContatoPlanoRecursoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContatoPlanoRecursoQueryService extends QueryService<ContatoPlanoRecurso> {

    private final Logger log = LoggerFactory.getLogger(ContatoPlanoRecursoQueryService.class);

    private final ContatoPlanoRecursoRepository contatoPlanoRecursoRepository;

    private final ContatoPlanoRecursoMapper contatoPlanoRecursoMapper;

    public ContatoPlanoRecursoQueryService(ContatoPlanoRecursoRepository contatoPlanoRecursoRepository, ContatoPlanoRecursoMapper contatoPlanoRecursoMapper) {
        this.contatoPlanoRecursoRepository = contatoPlanoRecursoRepository;
        this.contatoPlanoRecursoMapper = contatoPlanoRecursoMapper;
    }

    /**
     * Return a {@link List} of {@link ContatoPlanoRecursoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContatoPlanoRecursoDTO> findByCriteria(ContatoPlanoRecursoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ContatoPlanoRecurso> specification = createSpecification(criteria);
        return contatoPlanoRecursoMapper.toDto(contatoPlanoRecursoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContatoPlanoRecursoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContatoPlanoRecursoDTO> findByCriteria(ContatoPlanoRecursoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ContatoPlanoRecurso> specification = createSpecification(criteria);
        return contatoPlanoRecursoRepository.findAll(specification, page)
            .map(contatoPlanoRecursoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContatoPlanoRecursoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ContatoPlanoRecurso> specification = createSpecification(criteria);
        return contatoPlanoRecursoRepository.count(specification);
    }

    /**
     * Function to convert {@link ContatoPlanoRecursoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ContatoPlanoRecurso> createSpecification(ContatoPlanoRecursoCriteria criteria) {
        Specification<ContatoPlanoRecurso> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ContatoPlanoRecurso_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), ContatoPlanoRecurso_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), ContatoPlanoRecurso_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), ContatoPlanoRecurso_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), ContatoPlanoRecurso_.updated));
            }
            if (criteria.getContatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getContatoId(),
                    root -> root.join(ContatoPlanoRecurso_.contato, JoinType.LEFT).get(Contato_.id)));
            }
            if (criteria.getPlanoRecursoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoRecursoId(),
                    root -> root.join(ContatoPlanoRecurso_.planoRecurso, JoinType.LEFT).get(PlanoRecurso_.id)));
            }
        }
        return specification;
    }
}
