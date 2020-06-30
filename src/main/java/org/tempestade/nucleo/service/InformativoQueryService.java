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

import org.tempestade.nucleo.domain.Informativo;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.InformativoRepository;
import org.tempestade.nucleo.service.dto.InformativoCriteria;
import org.tempestade.nucleo.service.dto.InformativoDTO;
import org.tempestade.nucleo.service.mapper.InformativoMapper;

/**
 * Service for executing complex queries for {@link Informativo} entities in the database.
 * The main input is a {@link InformativoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InformativoDTO} or a {@link Page} of {@link InformativoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InformativoQueryService extends QueryService<Informativo> {

    private final Logger log = LoggerFactory.getLogger(InformativoQueryService.class);

    private final InformativoRepository informativoRepository;

    private final InformativoMapper informativoMapper;

    public InformativoQueryService(InformativoRepository informativoRepository, InformativoMapper informativoMapper) {
        this.informativoRepository = informativoRepository;
        this.informativoMapper = informativoMapper;
    }

    /**
     * Return a {@link List} of {@link InformativoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InformativoDTO> findByCriteria(InformativoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Informativo> specification = createSpecification(criteria);
        return informativoMapper.toDto(informativoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InformativoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InformativoDTO> findByCriteria(InformativoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Informativo> specification = createSpecification(criteria);
        return informativoRepository.findAll(specification, page)
            .map(informativoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InformativoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Informativo> specification = createSpecification(criteria);
        return informativoRepository.count(specification);
    }

    /**
     * Function to convert {@link InformativoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Informativo> createSpecification(InformativoCriteria criteria) {
        Specification<Informativo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Informativo_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Informativo_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Informativo_.descricao));
            }
            if (criteria.getTexto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTexto(), Informativo_.texto));
            }
            if (criteria.getQtdEmail() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtdEmail(), Informativo_.qtdEmail));
            }
            if (criteria.getImagem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImagem(), Informativo_.imagem));
            }
            if (criteria.getArquivoEml() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArquivoEml(), Informativo_.arquivoEml));
            }
            if (criteria.getAssunto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssunto(), Informativo_.assunto));
            }
            if (criteria.getSubAssunto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubAssunto(), Informativo_.subAssunto));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Informativo_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Informativo_.updated));
            }
            if (criteria.getPlanoRecursoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoRecursoId(),
                    root -> root.join(Informativo_.planoRecurso, JoinType.LEFT).get(PlanoRecurso_.id)));
            }
        }
        return specification;
    }
}
