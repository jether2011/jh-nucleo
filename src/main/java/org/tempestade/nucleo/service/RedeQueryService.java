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

import org.tempestade.nucleo.domain.Rede;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.RedeRepository;
import org.tempestade.nucleo.service.dto.RedeCriteria;
import org.tempestade.nucleo.service.dto.RedeDTO;
import org.tempestade.nucleo.service.mapper.RedeMapper;

/**
 * Service for executing complex queries for {@link Rede} entities in the database.
 * The main input is a {@link RedeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RedeDTO} or a {@link Page} of {@link RedeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RedeQueryService extends QueryService<Rede> {

    private final Logger log = LoggerFactory.getLogger(RedeQueryService.class);

    private final RedeRepository redeRepository;

    private final RedeMapper redeMapper;

    public RedeQueryService(RedeRepository redeRepository, RedeMapper redeMapper) {
        this.redeRepository = redeRepository;
        this.redeMapper = redeMapper;
    }

    /**
     * Return a {@link List} of {@link RedeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RedeDTO> findByCriteria(RedeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Rede> specification = createSpecification(criteria);
        return redeMapper.toDto(redeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RedeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RedeDTO> findByCriteria(RedeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Rede> specification = createSpecification(criteria);
        return redeRepository.findAll(specification, page)
            .map(redeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RedeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Rede> specification = createSpecification(criteria);
        return redeRepository.count(specification);
    }

    /**
     * Function to convert {@link RedeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Rede> createSpecification(RedeCriteria criteria) {
        Specification<Rede> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Rede_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Rede_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Rede_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Rede_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Rede_.updated));
            }
        }
        return specification;
    }
}
