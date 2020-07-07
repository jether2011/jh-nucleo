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

import org.tempestade.nucleo.domain.Ferramenta;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.FerramentaRepository;
import org.tempestade.nucleo.service.dto.FerramentaCriteria;
import org.tempestade.nucleo.service.dto.FerramentaDTO;
import org.tempestade.nucleo.service.mapper.FerramentaMapper;

/**
 * Service for executing complex queries for {@link Ferramenta} entities in the database.
 * The main input is a {@link FerramentaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FerramentaDTO} or a {@link Page} of {@link FerramentaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FerramentaQueryService extends QueryService<Ferramenta> {

    private final Logger log = LoggerFactory.getLogger(FerramentaQueryService.class);

    private final FerramentaRepository ferramentaRepository;

    private final FerramentaMapper ferramentaMapper;

    public FerramentaQueryService(FerramentaRepository ferramentaRepository, FerramentaMapper ferramentaMapper) {
        this.ferramentaRepository = ferramentaRepository;
        this.ferramentaMapper = ferramentaMapper;
    }

    /**
     * Return a {@link List} of {@link FerramentaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FerramentaDTO> findByCriteria(FerramentaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Ferramenta> specification = createSpecification(criteria);
        return ferramentaMapper.toDto(ferramentaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FerramentaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FerramentaDTO> findByCriteria(FerramentaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ferramenta> specification = createSpecification(criteria);
        return ferramentaRepository.findAll(specification, page)
            .map(ferramentaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FerramentaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Ferramenta> specification = createSpecification(criteria);
        return ferramentaRepository.count(specification);
    }

    /**
     * Function to convert {@link FerramentaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Ferramenta> createSpecification(FerramentaCriteria criteria) {
        Specification<Ferramenta> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Ferramenta_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Ferramenta_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Ferramenta_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Ferramenta_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Ferramenta_.updated));
            }
            if (criteria.getTipoFerramentaId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoFerramentaId(),
                    root -> root.join(Ferramenta_.tipoFerramenta, JoinType.LEFT).get(TipoFerramenta_.id)));
            }
        }
        return specification;
    }
}
