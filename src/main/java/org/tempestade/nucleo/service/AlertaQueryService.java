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

import org.tempestade.nucleo.domain.Alerta;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.AlertaRepository;
import org.tempestade.nucleo.service.dto.AlertaCriteria;
import org.tempestade.nucleo.service.dto.AlertaDTO;
import org.tempestade.nucleo.service.mapper.AlertaMapper;

/**
 * Service for executing complex queries for {@link Alerta} entities in the database.
 * The main input is a {@link AlertaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AlertaDTO} or a {@link Page} of {@link AlertaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AlertaQueryService extends QueryService<Alerta> {

    private final Logger log = LoggerFactory.getLogger(AlertaQueryService.class);

    private final AlertaRepository alertaRepository;

    private final AlertaMapper alertaMapper;

    public AlertaQueryService(AlertaRepository alertaRepository, AlertaMapper alertaMapper) {
        this.alertaRepository = alertaRepository;
        this.alertaMapper = alertaMapper;
    }

    /**
     * Return a {@link List} of {@link AlertaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AlertaDTO> findByCriteria(AlertaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Alerta> specification = createSpecification(criteria);
        return alertaMapper.toDto(alertaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AlertaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AlertaDTO> findByCriteria(AlertaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Alerta> specification = createSpecification(criteria);
        return alertaRepository.findAll(specification, page)
            .map(alertaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AlertaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Alerta> specification = createSpecification(criteria);
        return alertaRepository.count(specification);
    }

    /**
     * Function to convert {@link AlertaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Alerta> createSpecification(AlertaCriteria criteria) {
        Specification<Alerta> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Alerta_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Alerta_.nome));
            }
            if (criteria.getContato() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContato(), Alerta_.contato));
            }
            if (criteria.getDuracao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDuracao(), Alerta_.duracao));
            }
            if (criteria.getAutomatico() != null) {
                specification = specification.and(buildSpecification(criteria.getAutomatico(), Alerta_.automatico));
            }
            if (criteria.getCritico() != null) {
                specification = specification.and(buildSpecification(criteria.getCritico(), Alerta_.critico));
            }
            if (criteria.getObservacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservacao(), Alerta_.observacao));
            }
            if (criteria.getAlertaPaiId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAlertaPaiId(), Alerta_.alertaPaiId));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Alerta_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Alerta_.updated));
            }
            if (criteria.getPlanoRecursoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoRecursoId(),
                    root -> root.join(Alerta_.planoRecurso, JoinType.LEFT).get(PlanoRecurso_.id)));
            }
            if (criteria.getAlvoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAlvoId(),
                    root -> root.join(Alerta_.alvo, JoinType.LEFT).get(Alvo_.id)));
            }
            if (criteria.getOperadorUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getOperadorUsuarioId(),
                    root -> root.join(Alerta_.operadorUsuario, JoinType.LEFT).get(Usuario_.id)));
            }
            if (criteria.getAlertaRiscoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAlertaRiscoId(),
                    root -> root.join(Alerta_.alertaRisco, JoinType.LEFT).get(AlertaRisco_.id)));
            }
            if (criteria.getTempestadeNivelId() != null) {
                specification = specification.and(buildSpecification(criteria.getTempestadeNivelId(),
                    root -> root.join(Alerta_.tempestadeNivel, JoinType.LEFT).get(TempestadeNivel_.id)));
            }
            if (criteria.getAlertaTipoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAlertaTipoId(),
                    root -> root.join(Alerta_.alertaTipo, JoinType.LEFT).get(AlertaTipo_.id)));
            }
        }
        return specification;
    }
}
