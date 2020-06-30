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

import org.tempestade.nucleo.domain.ContatoTipoEnvio;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.ContatoTipoEnvioRepository;
import org.tempestade.nucleo.service.dto.ContatoTipoEnvioCriteria;
import org.tempestade.nucleo.service.dto.ContatoTipoEnvioDTO;
import org.tempestade.nucleo.service.mapper.ContatoTipoEnvioMapper;

/**
 * Service for executing complex queries for {@link ContatoTipoEnvio} entities in the database.
 * The main input is a {@link ContatoTipoEnvioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContatoTipoEnvioDTO} or a {@link Page} of {@link ContatoTipoEnvioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContatoTipoEnvioQueryService extends QueryService<ContatoTipoEnvio> {

    private final Logger log = LoggerFactory.getLogger(ContatoTipoEnvioQueryService.class);

    private final ContatoTipoEnvioRepository contatoTipoEnvioRepository;

    private final ContatoTipoEnvioMapper contatoTipoEnvioMapper;

    public ContatoTipoEnvioQueryService(ContatoTipoEnvioRepository contatoTipoEnvioRepository, ContatoTipoEnvioMapper contatoTipoEnvioMapper) {
        this.contatoTipoEnvioRepository = contatoTipoEnvioRepository;
        this.contatoTipoEnvioMapper = contatoTipoEnvioMapper;
    }

    /**
     * Return a {@link List} of {@link ContatoTipoEnvioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContatoTipoEnvioDTO> findByCriteria(ContatoTipoEnvioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ContatoTipoEnvio> specification = createSpecification(criteria);
        return contatoTipoEnvioMapper.toDto(contatoTipoEnvioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContatoTipoEnvioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContatoTipoEnvioDTO> findByCriteria(ContatoTipoEnvioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ContatoTipoEnvio> specification = createSpecification(criteria);
        return contatoTipoEnvioRepository.findAll(specification, page)
            .map(contatoTipoEnvioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContatoTipoEnvioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ContatoTipoEnvio> specification = createSpecification(criteria);
        return contatoTipoEnvioRepository.count(specification);
    }

    /**
     * Function to convert {@link ContatoTipoEnvioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ContatoTipoEnvio> createSpecification(ContatoTipoEnvioCriteria criteria) {
        Specification<ContatoTipoEnvio> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ContatoTipoEnvio_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), ContatoTipoEnvio_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), ContatoTipoEnvio_.descricao));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), ContatoTipoEnvio_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), ContatoTipoEnvio_.updated));
            }
            if (criteria.getContatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getContatoId(),
                    root -> root.join(ContatoTipoEnvio_.contato, JoinType.LEFT).get(Contato_.id)));
            }
            if (criteria.getTipoEnvioId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoEnvioId(),
                    root -> root.join(ContatoTipoEnvio_.tipoEnvio, JoinType.LEFT).get(TipoEnvio_.id)));
            }
        }
        return specification;
    }
}
