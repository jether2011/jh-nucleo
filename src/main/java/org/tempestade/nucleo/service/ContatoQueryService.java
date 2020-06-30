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

import org.tempestade.nucleo.domain.Contato;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.ContatoRepository;
import org.tempestade.nucleo.service.dto.ContatoCriteria;
import org.tempestade.nucleo.service.dto.ContatoDTO;
import org.tempestade.nucleo.service.mapper.ContatoMapper;

/**
 * Service for executing complex queries for {@link Contato} entities in the database.
 * The main input is a {@link ContatoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContatoDTO} or a {@link Page} of {@link ContatoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContatoQueryService extends QueryService<Contato> {

    private final Logger log = LoggerFactory.getLogger(ContatoQueryService.class);

    private final ContatoRepository contatoRepository;

    private final ContatoMapper contatoMapper;

    public ContatoQueryService(ContatoRepository contatoRepository, ContatoMapper contatoMapper) {
        this.contatoRepository = contatoRepository;
        this.contatoMapper = contatoMapper;
    }

    /**
     * Return a {@link List} of {@link ContatoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContatoDTO> findByCriteria(ContatoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Contato> specification = createSpecification(criteria);
        return contatoMapper.toDto(contatoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContatoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContatoDTO> findByCriteria(ContatoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Contato> specification = createSpecification(criteria);
        return contatoRepository.findAll(specification, page)
            .map(contatoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContatoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Contato> specification = createSpecification(criteria);
        return contatoRepository.count(specification);
    }

    /**
     * Function to convert {@link ContatoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Contato> createSpecification(ContatoCriteria criteria) {
        Specification<Contato> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Contato_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Contato_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Contato_.descricao));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Contato_.email));
            }
            if (criteria.getCelular() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCelular(), Contato_.celular));
            }
            if (criteria.getAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getAtivo(), Contato_.ativo));
            }
            if (criteria.getContatoAlertaTelefonico() != null) {
                specification = specification.and(buildSpecification(criteria.getContatoAlertaTelefonico(), Contato_.contatoAlertaTelefonico));
            }
            if (criteria.getPrioridade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrioridade(), Contato_.prioridade));
            }
            if (criteria.getHoraLigacaoInicial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHoraLigacaoInicial(), Contato_.horaLigacaoInicial));
            }
            if (criteria.getHoraLigacaoFinal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHoraLigacaoFinal(), Contato_.horaLigacaoFinal));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Contato_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Contato_.updated));
            }
        }
        return specification;
    }
}
