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

import org.tempestade.nucleo.domain.NotificacaoEnviada;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.NotificacaoEnviadaRepository;
import org.tempestade.nucleo.service.dto.NotificacaoEnviadaCriteria;
import org.tempestade.nucleo.service.dto.NotificacaoEnviadaDTO;
import org.tempestade.nucleo.service.mapper.NotificacaoEnviadaMapper;

/**
 * Service for executing complex queries for {@link NotificacaoEnviada} entities in the database.
 * The main input is a {@link NotificacaoEnviadaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NotificacaoEnviadaDTO} or a {@link Page} of {@link NotificacaoEnviadaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NotificacaoEnviadaQueryService extends QueryService<NotificacaoEnviada> {

    private final Logger log = LoggerFactory.getLogger(NotificacaoEnviadaQueryService.class);

    private final NotificacaoEnviadaRepository notificacaoEnviadaRepository;

    private final NotificacaoEnviadaMapper notificacaoEnviadaMapper;

    public NotificacaoEnviadaQueryService(NotificacaoEnviadaRepository notificacaoEnviadaRepository, NotificacaoEnviadaMapper notificacaoEnviadaMapper) {
        this.notificacaoEnviadaRepository = notificacaoEnviadaRepository;
        this.notificacaoEnviadaMapper = notificacaoEnviadaMapper;
    }

    /**
     * Return a {@link List} of {@link NotificacaoEnviadaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NotificacaoEnviadaDTO> findByCriteria(NotificacaoEnviadaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NotificacaoEnviada> specification = createSpecification(criteria);
        return notificacaoEnviadaMapper.toDto(notificacaoEnviadaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NotificacaoEnviadaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificacaoEnviadaDTO> findByCriteria(NotificacaoEnviadaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NotificacaoEnviada> specification = createSpecification(criteria);
        return notificacaoEnviadaRepository.findAll(specification, page)
            .map(notificacaoEnviadaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NotificacaoEnviadaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NotificacaoEnviada> specification = createSpecification(criteria);
        return notificacaoEnviadaRepository.count(specification);
    }

    /**
     * Function to convert {@link NotificacaoEnviadaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NotificacaoEnviada> createSpecification(NotificacaoEnviadaCriteria criteria) {
        Specification<NotificacaoEnviada> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NotificacaoEnviada_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), NotificacaoEnviada_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), NotificacaoEnviada_.descricao));
            }
            if (criteria.getDestinatarios() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDestinatarios(), NotificacaoEnviada_.destinatarios));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo(), NotificacaoEnviada_.tipo));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), NotificacaoEnviada_.status));
            }
            if (criteria.getAssunto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssunto(), NotificacaoEnviada_.assunto));
            }
            if (criteria.getEnviado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnviado(), NotificacaoEnviada_.enviado));
            }
            if (criteria.getContador() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContador(), NotificacaoEnviada_.contador));
            }
            if (criteria.getAmazonMessageId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAmazonMessageId(), NotificacaoEnviada_.amazonMessageId));
            }
            if (criteria.getAmazonDateLog() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmazonDateLog(), NotificacaoEnviada_.amazonDateLog));
            }
            if (criteria.getPriceInUsd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriceInUsd(), NotificacaoEnviada_.priceInUsd));
            }
            if (criteria.getAmazonResposta() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAmazonResposta(), NotificacaoEnviada_.amazonResposta));
            }
            if (criteria.getReferenceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReferenceId(), NotificacaoEnviada_.referenceId));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), NotificacaoEnviada_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), NotificacaoEnviada_.updated));
            }
            if (criteria.getPlanoRecursoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoRecursoId(),
                    root -> root.join(NotificacaoEnviada_.planoRecurso, JoinType.LEFT).get(PlanoRecurso_.id)));
            }
        }
        return specification;
    }
}
