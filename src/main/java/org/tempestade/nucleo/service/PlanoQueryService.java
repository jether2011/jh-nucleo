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

import org.tempestade.nucleo.domain.Plano;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.PlanoRepository;
import org.tempestade.nucleo.service.dto.PlanoCriteria;
import org.tempestade.nucleo.service.dto.PlanoDTO;
import org.tempestade.nucleo.service.mapper.PlanoMapper;

/**
 * Service for executing complex queries for {@link Plano} entities in the database.
 * The main input is a {@link PlanoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlanoDTO} or a {@link Page} of {@link PlanoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanoQueryService extends QueryService<Plano> {

    private final Logger log = LoggerFactory.getLogger(PlanoQueryService.class);

    private final PlanoRepository planoRepository;

    private final PlanoMapper planoMapper;

    public PlanoQueryService(PlanoRepository planoRepository, PlanoMapper planoMapper) {
        this.planoRepository = planoRepository;
        this.planoMapper = planoMapper;
    }

    /**
     * Return a {@link List} of {@link PlanoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlanoDTO> findByCriteria(PlanoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Plano> specification = createSpecification(criteria);
        return planoMapper.toDto(planoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlanoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoDTO> findByCriteria(PlanoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Plano> specification = createSpecification(criteria);
        return planoRepository.findAll(specification, page)
            .map(planoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Plano> specification = createSpecification(criteria);
        return planoRepository.count(specification);
    }

    /**
     * Function to convert {@link PlanoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Plano> createSpecification(PlanoCriteria criteria) {
        Specification<Plano> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Plano_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Plano_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Plano_.descricao));
            }
            if (criteria.getHorarioPrevisto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHorarioPrevisto(), Plano_.horarioPrevisto));
            }
            if (criteria.getTrackingAtivo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrackingAtivo(), Plano_.trackingAtivo));
            }
            if (criteria.getPlrAtivo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPlrAtivo(), Plano_.plrAtivo));
            }
            if (criteria.getCodigoWidgetPrevisao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCodigoWidgetPrevisao(), Plano_.codigoWidgetPrevisao));
            }
            if (criteria.getKmlAlvo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKmlAlvo(), Plano_.kmlAlvo));
            }
            if (criteria.getZoomMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZoomMin(), Plano_.zoomMin));
            }
            if (criteria.getDtInicioContrato() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDtInicioContrato(), Plano_.dtInicioContrato));
            }
            if (criteria.getDataFimContrato() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataFimContrato(), Plano_.dataFimContrato));
            }
            if (criteria.getHorarioMonitInicio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHorarioMonitInicio(), Plano_.horarioMonitInicio));
            }
            if (criteria.getHorarioMonitFinal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHorarioMonitFinal(), Plano_.horarioMonitFinal));
            }
            if (criteria.getBlocos() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBlocos(), Plano_.blocos));
            }
            if (criteria.getExtent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExtent(), Plano_.extent));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Plano_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Plano_.updated));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpresaId(),
                    root -> root.join(Plano_.empresa, JoinType.LEFT).get(Empresa_.id)));
            }
            if (criteria.getPlanoStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoStatusId(),
                    root -> root.join(Plano_.planoStatus, JoinType.LEFT).get(PlanoStatus_.id)));
            }
        }
        return specification;
    }
}
