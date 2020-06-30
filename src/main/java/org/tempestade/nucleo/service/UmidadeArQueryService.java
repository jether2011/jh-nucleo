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

import org.tempestade.nucleo.domain.UmidadeAr;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.UmidadeArRepository;
import org.tempestade.nucleo.service.dto.UmidadeArCriteria;
import org.tempestade.nucleo.service.dto.UmidadeArDTO;
import org.tempestade.nucleo.service.mapper.UmidadeArMapper;

/**
 * Service for executing complex queries for {@link UmidadeAr} entities in the database.
 * The main input is a {@link UmidadeArCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UmidadeArDTO} or a {@link Page} of {@link UmidadeArDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UmidadeArQueryService extends QueryService<UmidadeAr> {

    private final Logger log = LoggerFactory.getLogger(UmidadeArQueryService.class);

    private final UmidadeArRepository umidadeArRepository;

    private final UmidadeArMapper umidadeArMapper;

    public UmidadeArQueryService(UmidadeArRepository umidadeArRepository, UmidadeArMapper umidadeArMapper) {
        this.umidadeArRepository = umidadeArRepository;
        this.umidadeArMapper = umidadeArMapper;
    }

    /**
     * Return a {@link List} of {@link UmidadeArDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UmidadeArDTO> findByCriteria(UmidadeArCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UmidadeAr> specification = createSpecification(criteria);
        return umidadeArMapper.toDto(umidadeArRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UmidadeArDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UmidadeArDTO> findByCriteria(UmidadeArCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UmidadeAr> specification = createSpecification(criteria);
        return umidadeArRepository.findAll(specification, page)
            .map(umidadeArMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UmidadeArCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UmidadeAr> specification = createSpecification(criteria);
        return umidadeArRepository.count(specification);
    }

    /**
     * Function to convert {@link UmidadeArCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UmidadeAr> createSpecification(UmidadeArCriteria criteria) {
        Specification<UmidadeAr> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UmidadeAr_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), UmidadeAr_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), UmidadeAr_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), UmidadeAr_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), UmidadeAr_.updated));
            }
        }
        return specification;
    }
}
