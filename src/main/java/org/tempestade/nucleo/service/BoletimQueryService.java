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

import org.tempestade.nucleo.domain.Boletim;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.BoletimRepository;
import org.tempestade.nucleo.service.dto.BoletimCriteria;
import org.tempestade.nucleo.service.dto.BoletimDTO;
import org.tempestade.nucleo.service.mapper.BoletimMapper;

/**
 * Service for executing complex queries for {@link Boletim} entities in the database.
 * The main input is a {@link BoletimCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BoletimDTO} or a {@link Page} of {@link BoletimDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BoletimQueryService extends QueryService<Boletim> {

    private final Logger log = LoggerFactory.getLogger(BoletimQueryService.class);

    private final BoletimRepository boletimRepository;

    private final BoletimMapper boletimMapper;

    public BoletimQueryService(BoletimRepository boletimRepository, BoletimMapper boletimMapper) {
        this.boletimRepository = boletimRepository;
        this.boletimMapper = boletimMapper;
    }

    /**
     * Return a {@link List} of {@link BoletimDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BoletimDTO> findByCriteria(BoletimCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Boletim> specification = createSpecification(criteria);
        return boletimMapper.toDto(boletimRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BoletimDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BoletimDTO> findByCriteria(BoletimCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Boletim> specification = createSpecification(criteria);
        return boletimRepository.findAll(specification, page)
            .map(boletimMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BoletimCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Boletim> specification = createSpecification(criteria);
        return boletimRepository.count(specification);
    }

    /**
     * Function to convert {@link BoletimCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Boletim> createSpecification(BoletimCriteria criteria) {
        Specification<Boletim> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Boletim_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Boletim_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Boletim_.descricao));
            }
            if (criteria.getTexto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTexto(), Boletim_.texto));
            }
            if (criteria.getTextoSms() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoSms(), Boletim_.textoSms));
            }
            if (criteria.getImagem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImagem(), Boletim_.imagem));
            }
            if (criteria.getAssunto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssunto(), Boletim_.assunto));
            }
            if (criteria.getTextoParte2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoParte2(), Boletim_.textoParte2));
            }
            if (criteria.getTextoParte3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoParte3(), Boletim_.textoParte3));
            }
            if (criteria.getSubAssunto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubAssunto(), Boletim_.subAssunto));
            }
            if (criteria.getNaoExibirPagEmpresa() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNaoExibirPagEmpresa(), Boletim_.naoExibirPagEmpresa));
            }
            if (criteria.getCritico() != null) {
                specification = specification.and(buildSpecification(criteria.getCritico(), Boletim_.critico));
            }
            if (criteria.getAprovado() != null) {
                specification = specification.and(buildSpecification(criteria.getAprovado(), Boletim_.aprovado));
            }
            if (criteria.getEnviarSms() != null) {
                specification = specification.and(buildSpecification(criteria.getEnviarSms(), Boletim_.enviarSms));
            }
            if (criteria.getEnviarEmail() != null) {
                specification = specification.and(buildSpecification(criteria.getEnviarEmail(), Boletim_.enviarEmail));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Boletim_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Boletim_.updated));
            }
            if (criteria.getPlanoRecursoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoRecursoId(),
                    root -> root.join(Boletim_.planoRecurso, JoinType.LEFT).get(PlanoRecurso_.id)));
            }
        }
        return specification;
    }
}
