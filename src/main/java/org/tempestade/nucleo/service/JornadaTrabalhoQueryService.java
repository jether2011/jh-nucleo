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

import org.tempestade.nucleo.domain.JornadaTrabalho;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.JornadaTrabalhoRepository;
import org.tempestade.nucleo.service.dto.JornadaTrabalhoCriteria;
import org.tempestade.nucleo.service.dto.JornadaTrabalhoDTO;
import org.tempestade.nucleo.service.mapper.JornadaTrabalhoMapper;

/**
 * Service for executing complex queries for {@link JornadaTrabalho} entities in the database.
 * The main input is a {@link JornadaTrabalhoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JornadaTrabalhoDTO} or a {@link Page} of {@link JornadaTrabalhoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JornadaTrabalhoQueryService extends QueryService<JornadaTrabalho> {

    private final Logger log = LoggerFactory.getLogger(JornadaTrabalhoQueryService.class);

    private final JornadaTrabalhoRepository jornadaTrabalhoRepository;

    private final JornadaTrabalhoMapper jornadaTrabalhoMapper;

    public JornadaTrabalhoQueryService(JornadaTrabalhoRepository jornadaTrabalhoRepository, JornadaTrabalhoMapper jornadaTrabalhoMapper) {
        this.jornadaTrabalhoRepository = jornadaTrabalhoRepository;
        this.jornadaTrabalhoMapper = jornadaTrabalhoMapper;
    }

    /**
     * Return a {@link List} of {@link JornadaTrabalhoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JornadaTrabalhoDTO> findByCriteria(JornadaTrabalhoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<JornadaTrabalho> specification = createSpecification(criteria);
        return jornadaTrabalhoMapper.toDto(jornadaTrabalhoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link JornadaTrabalhoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JornadaTrabalhoDTO> findByCriteria(JornadaTrabalhoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<JornadaTrabalho> specification = createSpecification(criteria);
        return jornadaTrabalhoRepository.findAll(specification, page)
            .map(jornadaTrabalhoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JornadaTrabalhoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<JornadaTrabalho> specification = createSpecification(criteria);
        return jornadaTrabalhoRepository.count(specification);
    }

    /**
     * Function to convert {@link JornadaTrabalhoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<JornadaTrabalho> createSpecification(JornadaTrabalhoCriteria criteria) {
        Specification<JornadaTrabalho> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), JornadaTrabalho_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), JornadaTrabalho_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), JornadaTrabalho_.descricao));
            }
            if (criteria.getHorainicio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHorainicio(), JornadaTrabalho_.horainicio));
            }
            if (criteria.getDuracao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDuracao(), JornadaTrabalho_.duracao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), JornadaTrabalho_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), JornadaTrabalho_.updated));
            }
            if (criteria.getPlanoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoId(),
                    root -> root.join(JornadaTrabalho_.plano, JoinType.LEFT).get(Plano_.id)));
            }
            if (criteria.getDiaSemanaId() != null) {
                specification = specification.and(buildSpecification(criteria.getDiaSemanaId(),
                    root -> root.join(JornadaTrabalho_.diaSemana, JoinType.LEFT).get(DiaSemana_.id)));
            }
        }
        return specification;
    }
}
