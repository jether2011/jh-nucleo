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

import org.tempestade.nucleo.domain.AvisoMeteorologico;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.AvisoMeteorologicoRepository;
import org.tempestade.nucleo.service.dto.AvisoMeteorologicoCriteria;
import org.tempestade.nucleo.service.dto.AvisoMeteorologicoDTO;
import org.tempestade.nucleo.service.mapper.AvisoMeteorologicoMapper;

/**
 * Service for executing complex queries for {@link AvisoMeteorologico} entities in the database.
 * The main input is a {@link AvisoMeteorologicoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AvisoMeteorologicoDTO} or a {@link Page} of {@link AvisoMeteorologicoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AvisoMeteorologicoQueryService extends QueryService<AvisoMeteorologico> {

    private final Logger log = LoggerFactory.getLogger(AvisoMeteorologicoQueryService.class);

    private final AvisoMeteorologicoRepository avisoMeteorologicoRepository;

    private final AvisoMeteorologicoMapper avisoMeteorologicoMapper;

    public AvisoMeteorologicoQueryService(AvisoMeteorologicoRepository avisoMeteorologicoRepository, AvisoMeteorologicoMapper avisoMeteorologicoMapper) {
        this.avisoMeteorologicoRepository = avisoMeteorologicoRepository;
        this.avisoMeteorologicoMapper = avisoMeteorologicoMapper;
    }

    /**
     * Return a {@link List} of {@link AvisoMeteorologicoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AvisoMeteorologicoDTO> findByCriteria(AvisoMeteorologicoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AvisoMeteorologico> specification = createSpecification(criteria);
        return avisoMeteorologicoMapper.toDto(avisoMeteorologicoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AvisoMeteorologicoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AvisoMeteorologicoDTO> findByCriteria(AvisoMeteorologicoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AvisoMeteorologico> specification = createSpecification(criteria);
        return avisoMeteorologicoRepository.findAll(specification, page)
            .map(avisoMeteorologicoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AvisoMeteorologicoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AvisoMeteorologico> specification = createSpecification(criteria);
        return avisoMeteorologicoRepository.count(specification);
    }

    /**
     * Function to convert {@link AvisoMeteorologicoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AvisoMeteorologico> createSpecification(AvisoMeteorologicoCriteria criteria) {
        Specification<AvisoMeteorologico> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AvisoMeteorologico_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), AvisoMeteorologico_.nome));
            }
            if (criteria.getAssunto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssunto(), AvisoMeteorologico_.assunto));
            }
            if (criteria.getInicio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInicio(), AvisoMeteorologico_.inicio));
            }
            if (criteria.getFim() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFim(), AvisoMeteorologico_.fim));
            }
            if (criteria.getTexto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTexto(), AvisoMeteorologico_.texto));
            }
            if (criteria.getImagem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImagem(), AvisoMeteorologico_.imagem));
            }
            if (criteria.getImagemAssinatura() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImagemAssinatura(), AvisoMeteorologico_.imagemAssinatura));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), AvisoMeteorologico_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), AvisoMeteorologico_.updated));
            }
            if (criteria.getPlanoRecursoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoRecursoId(),
                    root -> root.join(AvisoMeteorologico_.planoRecurso, JoinType.LEFT).get(PlanoRecurso_.id)));
            }
        }
        return specification;
    }
}
