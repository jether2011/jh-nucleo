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

import org.tempestade.nucleo.domain.AlvoBloqueio;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.AlvoBloqueioRepository;
import org.tempestade.nucleo.service.dto.AlvoBloqueioCriteria;
import org.tempestade.nucleo.service.dto.AlvoBloqueioDTO;
import org.tempestade.nucleo.service.mapper.AlvoBloqueioMapper;

/**
 * Service for executing complex queries for {@link AlvoBloqueio} entities in the database.
 * The main input is a {@link AlvoBloqueioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AlvoBloqueioDTO} or a {@link Page} of {@link AlvoBloqueioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AlvoBloqueioQueryService extends QueryService<AlvoBloqueio> {

    private final Logger log = LoggerFactory.getLogger(AlvoBloqueioQueryService.class);

    private final AlvoBloqueioRepository alvoBloqueioRepository;

    private final AlvoBloqueioMapper alvoBloqueioMapper;

    public AlvoBloqueioQueryService(AlvoBloqueioRepository alvoBloqueioRepository, AlvoBloqueioMapper alvoBloqueioMapper) {
        this.alvoBloqueioRepository = alvoBloqueioRepository;
        this.alvoBloqueioMapper = alvoBloqueioMapper;
    }

    /**
     * Return a {@link List} of {@link AlvoBloqueioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AlvoBloqueioDTO> findByCriteria(AlvoBloqueioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AlvoBloqueio> specification = createSpecification(criteria);
        return alvoBloqueioMapper.toDto(alvoBloqueioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AlvoBloqueioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AlvoBloqueioDTO> findByCriteria(AlvoBloqueioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AlvoBloqueio> specification = createSpecification(criteria);
        return alvoBloqueioRepository.findAll(specification, page)
            .map(alvoBloqueioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AlvoBloqueioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AlvoBloqueio> specification = createSpecification(criteria);
        return alvoBloqueioRepository.count(specification);
    }

    /**
     * Function to convert {@link AlvoBloqueioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AlvoBloqueio> createSpecification(AlvoBloqueioCriteria criteria) {
        Specification<AlvoBloqueio> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AlvoBloqueio_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), AlvoBloqueio_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), AlvoBloqueio_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), AlvoBloqueio_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), AlvoBloqueio_.updated));
            }
            if (criteria.getAlvoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAlvoId(),
                    root -> root.join(AlvoBloqueio_.alvo, JoinType.LEFT).get(Alvo_.id)));
            }
        }
        return specification;
    }
}
