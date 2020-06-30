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

import org.tempestade.nucleo.domain.Layer;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.LayerRepository;
import org.tempestade.nucleo.service.dto.LayerCriteria;
import org.tempestade.nucleo.service.dto.LayerDTO;
import org.tempestade.nucleo.service.mapper.LayerMapper;

/**
 * Service for executing complex queries for {@link Layer} entities in the database.
 * The main input is a {@link LayerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LayerDTO} or a {@link Page} of {@link LayerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LayerQueryService extends QueryService<Layer> {

    private final Logger log = LoggerFactory.getLogger(LayerQueryService.class);

    private final LayerRepository layerRepository;

    private final LayerMapper layerMapper;

    public LayerQueryService(LayerRepository layerRepository, LayerMapper layerMapper) {
        this.layerRepository = layerRepository;
        this.layerMapper = layerMapper;
    }

    /**
     * Return a {@link List} of {@link LayerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LayerDTO> findByCriteria(LayerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Layer> specification = createSpecification(criteria);
        return layerMapper.toDto(layerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LayerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LayerDTO> findByCriteria(LayerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Layer> specification = createSpecification(criteria);
        return layerRepository.findAll(specification, page)
            .map(layerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LayerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Layer> specification = createSpecification(criteria);
        return layerRepository.count(specification);
    }

    /**
     * Function to convert {@link LayerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Layer> createSpecification(LayerCriteria criteria) {
        Specification<Layer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Layer_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Layer_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Layer_.description));
            }
            if (criteria.getMapHost() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMapHost(), Layer_.mapHost));
            }
            if (criteria.getLayerType() != null) {
                specification = specification.and(buildSpecification(criteria.getLayerType(), Layer_.layerType));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Layer_.title));
            }
            if (criteria.getAttribution() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttribution(), Layer_.attribution));
            }
            if (criteria.getWorkspace() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWorkspace(), Layer_.workspace));
            }
            if (criteria.getOpacity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOpacity(), Layer_.opacity));
            }
            if (criteria.getBaselayer() != null) {
                specification = specification.and(buildSpecification(criteria.getBaselayer(), Layer_.baselayer));
            }
            if (criteria.getTiled() != null) {
                specification = specification.and(buildSpecification(criteria.getTiled(), Layer_.tiled));
            }
            if (criteria.getGwcActived() != null) {
                specification = specification.and(buildSpecification(criteria.getGwcActived(), Layer_.gwcActived));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Layer_.active));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), Layer_.enabled));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Layer_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Layer_.updated));
            }
        }
        return specification;
    }
}
