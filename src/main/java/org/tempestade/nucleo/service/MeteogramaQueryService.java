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

import org.tempestade.nucleo.domain.Meteograma;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.MeteogramaRepository;
import org.tempestade.nucleo.service.dto.MeteogramaCriteria;
import org.tempestade.nucleo.service.dto.MeteogramaDTO;
import org.tempestade.nucleo.service.mapper.MeteogramaMapper;

/**
 * Service for executing complex queries for {@link Meteograma} entities in the database.
 * The main input is a {@link MeteogramaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MeteogramaDTO} or a {@link Page} of {@link MeteogramaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MeteogramaQueryService extends QueryService<Meteograma> {

    private final Logger log = LoggerFactory.getLogger(MeteogramaQueryService.class);

    private final MeteogramaRepository meteogramaRepository;

    private final MeteogramaMapper meteogramaMapper;

    public MeteogramaQueryService(MeteogramaRepository meteogramaRepository, MeteogramaMapper meteogramaMapper) {
        this.meteogramaRepository = meteogramaRepository;
        this.meteogramaMapper = meteogramaMapper;
    }

    /**
     * Return a {@link List} of {@link MeteogramaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MeteogramaDTO> findByCriteria(MeteogramaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Meteograma> specification = createSpecification(criteria);
        return meteogramaMapper.toDto(meteogramaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MeteogramaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MeteogramaDTO> findByCriteria(MeteogramaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Meteograma> specification = createSpecification(criteria);
        return meteogramaRepository.findAll(specification, page)
            .map(meteogramaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MeteogramaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Meteograma> specification = createSpecification(criteria);
        return meteogramaRepository.count(specification);
    }

    /**
     * Function to convert {@link MeteogramaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Meteograma> createSpecification(MeteogramaCriteria criteria) {
        Specification<Meteograma> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Meteograma_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Meteograma_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Meteograma_.descricao));
            }
            if (criteria.getArquivo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArquivo(), Meteograma_.arquivo));
            }
            if (criteria.getFolder() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFolder(), Meteograma_.folder));
            }
            if (criteria.getTipoarquivo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipoarquivo(), Meteograma_.tipoarquivo));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Meteograma_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Meteograma_.updated));
            }
            if (criteria.getPlanoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoId(),
                    root -> root.join(Meteograma_.plano, JoinType.LEFT).get(Plano_.id)));
            }
        }
        return specification;
    }
}
