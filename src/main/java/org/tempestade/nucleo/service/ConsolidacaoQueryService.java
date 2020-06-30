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

import org.tempestade.nucleo.domain.Consolidacao;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.ConsolidacaoRepository;
import org.tempestade.nucleo.service.dto.ConsolidacaoCriteria;
import org.tempestade.nucleo.service.dto.ConsolidacaoDTO;
import org.tempestade.nucleo.service.mapper.ConsolidacaoMapper;

/**
 * Service for executing complex queries for {@link Consolidacao} entities in the database.
 * The main input is a {@link ConsolidacaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConsolidacaoDTO} or a {@link Page} of {@link ConsolidacaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConsolidacaoQueryService extends QueryService<Consolidacao> {

    private final Logger log = LoggerFactory.getLogger(ConsolidacaoQueryService.class);

    private final ConsolidacaoRepository consolidacaoRepository;

    private final ConsolidacaoMapper consolidacaoMapper;

    public ConsolidacaoQueryService(ConsolidacaoRepository consolidacaoRepository, ConsolidacaoMapper consolidacaoMapper) {
        this.consolidacaoRepository = consolidacaoRepository;
        this.consolidacaoMapper = consolidacaoMapper;
    }

    /**
     * Return a {@link List} of {@link ConsolidacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConsolidacaoDTO> findByCriteria(ConsolidacaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Consolidacao> specification = createSpecification(criteria);
        return consolidacaoMapper.toDto(consolidacaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ConsolidacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConsolidacaoDTO> findByCriteria(ConsolidacaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Consolidacao> specification = createSpecification(criteria);
        return consolidacaoRepository.findAll(specification, page)
            .map(consolidacaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConsolidacaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Consolidacao> specification = createSpecification(criteria);
        return consolidacaoRepository.count(specification);
    }

    /**
     * Function to convert {@link ConsolidacaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Consolidacao> createSpecification(ConsolidacaoCriteria criteria) {
        Specification<Consolidacao> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Consolidacao_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Consolidacao_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Consolidacao_.descricao));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), Consolidacao_.data));
            }
            if (criteria.getTexto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTexto(), Consolidacao_.texto));
            }
            if (criteria.getQtdEmail() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtdEmail(), Consolidacao_.qtdEmail));
            }
            if (criteria.getImagem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImagem(), Consolidacao_.imagem));
            }
            if (criteria.getArquivoEml() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArquivoEml(), Consolidacao_.arquivoEml));
            }
            if (criteria.getAssunto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssunto(), Consolidacao_.assunto));
            }
            if (criteria.getSubAssunto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubAssunto(), Consolidacao_.subAssunto));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Consolidacao_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Consolidacao_.updated));
            }
            if (criteria.getPlanoRecursoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoRecursoId(),
                    root -> root.join(Consolidacao_.planoRecurso, JoinType.LEFT).get(PlanoRecurso_.id)));
            }
        }
        return specification;
    }
}
