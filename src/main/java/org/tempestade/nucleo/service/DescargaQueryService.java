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

import org.tempestade.nucleo.domain.Descarga;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.DescargaRepository;
import org.tempestade.nucleo.service.dto.DescargaCriteria;
import org.tempestade.nucleo.service.dto.DescargaDTO;
import org.tempestade.nucleo.service.mapper.DescargaMapper;

/**
 * Service for executing complex queries for {@link Descarga} entities in the database.
 * The main input is a {@link DescargaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DescargaDTO} or a {@link Page} of {@link DescargaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DescargaQueryService extends QueryService<Descarga> {

    private final Logger log = LoggerFactory.getLogger(DescargaQueryService.class);

    private final DescargaRepository descargaRepository;

    private final DescargaMapper descargaMapper;

    public DescargaQueryService(DescargaRepository descargaRepository, DescargaMapper descargaMapper) {
        this.descargaRepository = descargaRepository;
        this.descargaMapper = descargaMapper;
    }

    /**
     * Return a {@link List} of {@link DescargaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DescargaDTO> findByCriteria(DescargaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Descarga> specification = createSpecification(criteria);
        return descargaMapper.toDto(descargaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DescargaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DescargaDTO> findByCriteria(DescargaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Descarga> specification = createSpecification(criteria);
        return descargaRepository.findAll(specification, page)
            .map(descargaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DescargaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Descarga> specification = createSpecification(criteria);
        return descargaRepository.count(specification);
    }

    /**
     * Function to convert {@link DescargaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Descarga> createSpecification(DescargaCriteria criteria) {
        Specification<Descarga> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Descarga_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Descarga_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Descarga_.descricao));
            }
            if (criteria.getQtd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtd(), Descarga_.qtd));
            }
            if (criteria.getDataPrimeiraDescarga() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataPrimeiraDescarga(), Descarga_.dataPrimeiraDescarga));
            }
            if (criteria.getTempoAntecipacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTempoAntecipacao(), Descarga_.tempoAntecipacao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Descarga_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Descarga_.updated));
            }
            if (criteria.getRedeId() != null) {
                specification = specification.and(buildSpecification(criteria.getRedeId(),
                    root -> root.join(Descarga_.rede, JoinType.LEFT).get(Rede_.id)));
            }
            if (criteria.getDescargaTipoId() != null) {
                specification = specification.and(buildSpecification(criteria.getDescargaTipoId(),
                    root -> root.join(Descarga_.descargaTipo, JoinType.LEFT).get(DescargaTipo_.id)));
            }
            if (criteria.getDescargaUnidadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDescargaUnidadeId(),
                    root -> root.join(Descarga_.descargaUnidade, JoinType.LEFT).get(DescargaUnidade_.id)));
            }
            if (criteria.getAlertaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAlertaId(),
                    root -> root.join(Descarga_.alerta, JoinType.LEFT).get(Alerta_.id)));
            }
        }
        return specification;
    }
}
