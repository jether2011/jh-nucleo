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

import org.tempestade.nucleo.domain.VoceSabia;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.VoceSabiaRepository;
import org.tempestade.nucleo.service.dto.VoceSabiaCriteria;
import org.tempestade.nucleo.service.dto.VoceSabiaDTO;
import org.tempestade.nucleo.service.mapper.VoceSabiaMapper;

/**
 * Service for executing complex queries for {@link VoceSabia} entities in the database.
 * The main input is a {@link VoceSabiaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VoceSabiaDTO} or a {@link Page} of {@link VoceSabiaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VoceSabiaQueryService extends QueryService<VoceSabia> {

    private final Logger log = LoggerFactory.getLogger(VoceSabiaQueryService.class);

    private final VoceSabiaRepository voceSabiaRepository;

    private final VoceSabiaMapper voceSabiaMapper;

    public VoceSabiaQueryService(VoceSabiaRepository voceSabiaRepository, VoceSabiaMapper voceSabiaMapper) {
        this.voceSabiaRepository = voceSabiaRepository;
        this.voceSabiaMapper = voceSabiaMapper;
    }

    /**
     * Return a {@link List} of {@link VoceSabiaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VoceSabiaDTO> findByCriteria(VoceSabiaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VoceSabia> specification = createSpecification(criteria);
        return voceSabiaMapper.toDto(voceSabiaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VoceSabiaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VoceSabiaDTO> findByCriteria(VoceSabiaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VoceSabia> specification = createSpecification(criteria);
        return voceSabiaRepository.findAll(specification, page)
            .map(voceSabiaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VoceSabiaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VoceSabia> specification = createSpecification(criteria);
        return voceSabiaRepository.count(specification);
    }

    /**
     * Function to convert {@link VoceSabiaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VoceSabia> createSpecification(VoceSabiaCriteria criteria) {
        Specification<VoceSabia> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VoceSabia_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), VoceSabia_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), VoceSabia_.descricao));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), VoceSabia_.titulo));
            }
            if (criteria.getTexto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTexto(), VoceSabia_.texto));
            }
            if (criteria.getImagem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImagem(), VoceSabia_.imagem));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), VoceSabia_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), VoceSabia_.updated));
            }
        }
        return specification;
    }
}
