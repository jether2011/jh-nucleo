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

import org.tempestade.nucleo.domain.Aviso;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.AvisoRepository;
import org.tempestade.nucleo.service.dto.AvisoCriteria;
import org.tempestade.nucleo.service.dto.AvisoDTO;
import org.tempestade.nucleo.service.mapper.AvisoMapper;

/**
 * Service for executing complex queries for {@link Aviso} entities in the database.
 * The main input is a {@link AvisoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AvisoDTO} or a {@link Page} of {@link AvisoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AvisoQueryService extends QueryService<Aviso> {

    private final Logger log = LoggerFactory.getLogger(AvisoQueryService.class);

    private final AvisoRepository avisoRepository;

    private final AvisoMapper avisoMapper;

    public AvisoQueryService(AvisoRepository avisoRepository, AvisoMapper avisoMapper) {
        this.avisoRepository = avisoRepository;
        this.avisoMapper = avisoMapper;
    }

    /**
     * Return a {@link List} of {@link AvisoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AvisoDTO> findByCriteria(AvisoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Aviso> specification = createSpecification(criteria);
        return avisoMapper.toDto(avisoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AvisoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AvisoDTO> findByCriteria(AvisoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Aviso> specification = createSpecification(criteria);
        return avisoRepository.findAll(specification, page)
            .map(avisoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AvisoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Aviso> specification = createSpecification(criteria);
        return avisoRepository.count(specification);
    }

    /**
     * Function to convert {@link AvisoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Aviso> createSpecification(AvisoCriteria criteria) {
        Specification<Aviso> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Aviso_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Aviso_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Aviso_.descricao));
            }
            if (criteria.getEnviado() != null) {
                specification = specification.and(buildSpecification(criteria.getEnviado(), Aviso_.enviado));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Aviso_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Aviso_.updated));
            }
            if (criteria.getAvisoTipoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAvisoTipoId(),
                    root -> root.join(Aviso_.avisoTipo, JoinType.LEFT).get(AvisoTipo_.id)));
            }
        }
        return specification;
    }
}
