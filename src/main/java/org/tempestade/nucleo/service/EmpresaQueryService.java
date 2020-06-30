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

import org.tempestade.nucleo.domain.Empresa;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.EmpresaRepository;
import org.tempestade.nucleo.service.dto.EmpresaCriteria;
import org.tempestade.nucleo.service.dto.EmpresaDTO;
import org.tempestade.nucleo.service.mapper.EmpresaMapper;

/**
 * Service for executing complex queries for {@link Empresa} entities in the database.
 * The main input is a {@link EmpresaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmpresaDTO} or a {@link Page} of {@link EmpresaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmpresaQueryService extends QueryService<Empresa> {

    private final Logger log = LoggerFactory.getLogger(EmpresaQueryService.class);

    private final EmpresaRepository empresaRepository;

    private final EmpresaMapper empresaMapper;

    public EmpresaQueryService(EmpresaRepository empresaRepository, EmpresaMapper empresaMapper) {
        this.empresaRepository = empresaRepository;
        this.empresaMapper = empresaMapper;
    }

    /**
     * Return a {@link List} of {@link EmpresaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmpresaDTO> findByCriteria(EmpresaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Empresa> specification = createSpecification(criteria);
        return empresaMapper.toDto(empresaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmpresaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmpresaDTO> findByCriteria(EmpresaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Empresa> specification = createSpecification(criteria);
        return empresaRepository.findAll(specification, page)
            .map(empresaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmpresaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Empresa> specification = createSpecification(criteria);
        return empresaRepository.count(specification);
    }

    /**
     * Function to convert {@link EmpresaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Empresa> createSpecification(EmpresaCriteria criteria) {
        Specification<Empresa> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Empresa_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Empresa_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Empresa_.descricao));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Empresa_.email));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Empresa_.titulo));
            }
            if (criteria.getNomeReduzido() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeReduzido(), Empresa_.nomeReduzido));
            }
            if (criteria.getLogo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogo(), Empresa_.logo));
            }
            if (criteria.getApelido() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApelido(), Empresa_.apelido));
            }
            if (criteria.getObservacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservacao(), Empresa_.observacao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Empresa_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Empresa_.updated));
            }
        }
        return specification;
    }
}
