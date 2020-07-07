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

import org.tempestade.nucleo.domain.Alvo;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.AlvoRepository;
import org.tempestade.nucleo.service.dto.AlvoCriteria;
import org.tempestade.nucleo.service.dto.AlvoDTO;
import org.tempestade.nucleo.service.mapper.AlvoMapper;

/**
 * Service for executing complex queries for {@link Alvo} entities in the database.
 * The main input is a {@link AlvoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AlvoDTO} or a {@link Page} of {@link AlvoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AlvoQueryService extends QueryService<Alvo> {

    private final Logger log = LoggerFactory.getLogger(AlvoQueryService.class);

    private final AlvoRepository alvoRepository;

    private final AlvoMapper alvoMapper;

    public AlvoQueryService(AlvoRepository alvoRepository, AlvoMapper alvoMapper) {
        this.alvoRepository = alvoRepository;
        this.alvoMapper = alvoMapper;
    }

    /**
     * Return a {@link List} of {@link AlvoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AlvoDTO> findByCriteria(AlvoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Alvo> specification = createSpecification(criteria);
        return alvoMapper.toDto(alvoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AlvoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AlvoDTO> findByCriteria(AlvoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Alvo> specification = createSpecification(criteria);
        return alvoRepository.findAll(specification, page)
            .map(alvoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AlvoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Alvo> specification = createSpecification(criteria);
        return alvoRepository.count(specification);
    }

    /**
     * Function to convert {@link AlvoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Alvo> createSpecification(AlvoCriteria criteria) {
        Specification<Alvo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Alvo_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Alvo_.nome));
            }
            if (criteria.getNomeReduzido() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeReduzido(), Alvo_.nomeReduzido));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Alvo_.descricao));
            }
            if (criteria.getPrimeiroPonto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrimeiroPonto(), Alvo_.primeiroPonto));
            }
            if (criteria.getUltimoPonto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUltimoPonto(), Alvo_.ultimoPonto));
            }
            if (criteria.getHorarioLiberacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHorarioLiberacao(), Alvo_.horarioLiberacao));
            }
            if (criteria.getHorario() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHorario(), Alvo_.horario));
            }
            if (criteria.getDuracao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDuracao(), Alvo_.duracao));
            }
            if (criteria.getDuracaoAtual() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDuracaoAtual(), Alvo_.duracaoAtual));
            }
            if (criteria.getDataDesativado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataDesativado(), Alvo_.dataDesativado));
            }
            if (criteria.getCoordenadasAlertaPontos() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCoordenadasAlertaPontos(), Alvo_.coordenadasAlertaPontos));
            }
            if (criteria.getCoordenadasLiberacaoPontos() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCoordenadasLiberacaoPontos(), Alvo_.coordenadasLiberacaoPontos));
            }
            if (criteria.getTelegramTokenBot() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelegramTokenBot(), Alvo_.telegramTokenBot));
            }
            if (criteria.getTelegramChatId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelegramChatId(), Alvo_.telegramChatId));
            }
            if (criteria.getHorarioBloqueioNotificacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHorarioBloqueioNotificacao(), Alvo_.horarioBloqueioNotificacao));
            }
            if (criteria.getCoordenadasOriginalPontos() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCoordenadasOriginalPontos(), Alvo_.coordenadasOriginalPontos));
            }
            if (criteria.getAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getAtivo(), Alvo_.ativo));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Alvo_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Alvo_.updated));
            }
            if (criteria.getPlanoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoId(),
                    root -> root.join(Alvo_.plano, JoinType.LEFT).get(Plano_.id)));
            }
        }
        return specification;
    }
}
