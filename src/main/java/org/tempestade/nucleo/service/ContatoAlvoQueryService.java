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

import org.tempestade.nucleo.domain.ContatoAlvo;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.ContatoAlvoRepository;
import org.tempestade.nucleo.service.dto.ContatoAlvoCriteria;
import org.tempestade.nucleo.service.dto.ContatoAlvoDTO;
import org.tempestade.nucleo.service.mapper.ContatoAlvoMapper;

/**
 * Service for executing complex queries for {@link ContatoAlvo} entities in the database.
 * The main input is a {@link ContatoAlvoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContatoAlvoDTO} or a {@link Page} of {@link ContatoAlvoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContatoAlvoQueryService extends QueryService<ContatoAlvo> {

    private final Logger log = LoggerFactory.getLogger(ContatoAlvoQueryService.class);

    private final ContatoAlvoRepository contatoAlvoRepository;

    private final ContatoAlvoMapper contatoAlvoMapper;

    public ContatoAlvoQueryService(ContatoAlvoRepository contatoAlvoRepository, ContatoAlvoMapper contatoAlvoMapper) {
        this.contatoAlvoRepository = contatoAlvoRepository;
        this.contatoAlvoMapper = contatoAlvoMapper;
    }

    /**
     * Return a {@link List} of {@link ContatoAlvoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContatoAlvoDTO> findByCriteria(ContatoAlvoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ContatoAlvo> specification = createSpecification(criteria);
        return contatoAlvoMapper.toDto(contatoAlvoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContatoAlvoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContatoAlvoDTO> findByCriteria(ContatoAlvoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ContatoAlvo> specification = createSpecification(criteria);
        return contatoAlvoRepository.findAll(specification, page)
            .map(contatoAlvoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContatoAlvoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ContatoAlvo> specification = createSpecification(criteria);
        return contatoAlvoRepository.count(specification);
    }

    /**
     * Function to convert {@link ContatoAlvoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ContatoAlvo> createSpecification(ContatoAlvoCriteria criteria) {
        Specification<ContatoAlvo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ContatoAlvo_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), ContatoAlvo_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), ContatoAlvo_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), ContatoAlvo_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), ContatoAlvo_.updated));
            }
            if (criteria.getContatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getContatoId(),
                    root -> root.join(ContatoAlvo_.contato, JoinType.LEFT).get(Contato_.id)));
            }
            if (criteria.getAlvoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAlvoId(),
                    root -> root.join(ContatoAlvo_.alvo, JoinType.LEFT).get(Alvo_.id)));
            }
        }
        return specification;
    }
}
