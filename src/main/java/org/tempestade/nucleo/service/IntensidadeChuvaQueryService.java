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

import org.tempestade.nucleo.domain.IntensidadeChuva;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.IntensidadeChuvaRepository;
import org.tempestade.nucleo.service.dto.IntensidadeChuvaCriteria;
import org.tempestade.nucleo.service.dto.IntensidadeChuvaDTO;
import org.tempestade.nucleo.service.mapper.IntensidadeChuvaMapper;

/**
 * Service for executing complex queries for {@link IntensidadeChuva} entities in the database.
 * The main input is a {@link IntensidadeChuvaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IntensidadeChuvaDTO} or a {@link Page} of {@link IntensidadeChuvaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IntensidadeChuvaQueryService extends QueryService<IntensidadeChuva> {

    private final Logger log = LoggerFactory.getLogger(IntensidadeChuvaQueryService.class);

    private final IntensidadeChuvaRepository intensidadeChuvaRepository;

    private final IntensidadeChuvaMapper intensidadeChuvaMapper;

    public IntensidadeChuvaQueryService(IntensidadeChuvaRepository intensidadeChuvaRepository, IntensidadeChuvaMapper intensidadeChuvaMapper) {
        this.intensidadeChuvaRepository = intensidadeChuvaRepository;
        this.intensidadeChuvaMapper = intensidadeChuvaMapper;
    }

    /**
     * Return a {@link List} of {@link IntensidadeChuvaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IntensidadeChuvaDTO> findByCriteria(IntensidadeChuvaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IntensidadeChuva> specification = createSpecification(criteria);
        return intensidadeChuvaMapper.toDto(intensidadeChuvaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IntensidadeChuvaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IntensidadeChuvaDTO> findByCriteria(IntensidadeChuvaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IntensidadeChuva> specification = createSpecification(criteria);
        return intensidadeChuvaRepository.findAll(specification, page)
            .map(intensidadeChuvaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IntensidadeChuvaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IntensidadeChuva> specification = createSpecification(criteria);
        return intensidadeChuvaRepository.count(specification);
    }

    /**
     * Function to convert {@link IntensidadeChuvaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IntensidadeChuva> createSpecification(IntensidadeChuvaCriteria criteria) {
        Specification<IntensidadeChuva> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IntensidadeChuva_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), IntensidadeChuva_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), IntensidadeChuva_.descricao));
            }
            if (criteria.getFaixa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFaixa(), IntensidadeChuva_.faixa));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), IntensidadeChuva_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), IntensidadeChuva_.updated));
            }
        }
        return specification;
    }
}
