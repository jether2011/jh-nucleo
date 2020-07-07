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

import org.tempestade.nucleo.domain.PlanoRede;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.PlanoRedeRepository;
import org.tempestade.nucleo.service.dto.PlanoRedeCriteria;
import org.tempestade.nucleo.service.dto.PlanoRedeDTO;
import org.tempestade.nucleo.service.mapper.PlanoRedeMapper;

/**
 * Service for executing complex queries for {@link PlanoRede} entities in the database.
 * The main input is a {@link PlanoRedeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlanoRedeDTO} or a {@link Page} of {@link PlanoRedeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanoRedeQueryService extends QueryService<PlanoRede> {

    private final Logger log = LoggerFactory.getLogger(PlanoRedeQueryService.class);

    private final PlanoRedeRepository planoRedeRepository;

    private final PlanoRedeMapper planoRedeMapper;

    public PlanoRedeQueryService(PlanoRedeRepository planoRedeRepository, PlanoRedeMapper planoRedeMapper) {
        this.planoRedeRepository = planoRedeRepository;
        this.planoRedeMapper = planoRedeMapper;
    }

    /**
     * Return a {@link List} of {@link PlanoRedeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlanoRedeDTO> findByCriteria(PlanoRedeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PlanoRede> specification = createSpecification(criteria);
        return planoRedeMapper.toDto(planoRedeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlanoRedeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoRedeDTO> findByCriteria(PlanoRedeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PlanoRede> specification = createSpecification(criteria);
        return planoRedeRepository.findAll(specification, page)
            .map(planoRedeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanoRedeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlanoRede> specification = createSpecification(criteria);
        return planoRedeRepository.count(specification);
    }

    /**
     * Function to convert {@link PlanoRedeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlanoRede> createSpecification(PlanoRedeCriteria criteria) {
        Specification<PlanoRede> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PlanoRede_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PlanoRede_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), PlanoRede_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), PlanoRede_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), PlanoRede_.updated));
            }
            if (criteria.getPlanoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoId(),
                    root -> root.join(PlanoRede_.plano, JoinType.LEFT).get(Plano_.id)));
            }
            if (criteria.getRedeId() != null) {
                specification = specification.and(buildSpecification(criteria.getRedeId(),
                    root -> root.join(PlanoRede_.rede, JoinType.LEFT).get(Rede_.id)));
            }
        }
        return specification;
    }
}
