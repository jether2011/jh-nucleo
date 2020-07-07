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

import org.tempestade.nucleo.domain.Noticia;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.NoticiaRepository;
import org.tempestade.nucleo.service.dto.NoticiaCriteria;
import org.tempestade.nucleo.service.dto.NoticiaDTO;
import org.tempestade.nucleo.service.mapper.NoticiaMapper;

/**
 * Service for executing complex queries for {@link Noticia} entities in the database.
 * The main input is a {@link NoticiaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NoticiaDTO} or a {@link Page} of {@link NoticiaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NoticiaQueryService extends QueryService<Noticia> {

    private final Logger log = LoggerFactory.getLogger(NoticiaQueryService.class);

    private final NoticiaRepository noticiaRepository;

    private final NoticiaMapper noticiaMapper;

    public NoticiaQueryService(NoticiaRepository noticiaRepository, NoticiaMapper noticiaMapper) {
        this.noticiaRepository = noticiaRepository;
        this.noticiaMapper = noticiaMapper;
    }

    /**
     * Return a {@link List} of {@link NoticiaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NoticiaDTO> findByCriteria(NoticiaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Noticia> specification = createSpecification(criteria);
        return noticiaMapper.toDto(noticiaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NoticiaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NoticiaDTO> findByCriteria(NoticiaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Noticia> specification = createSpecification(criteria);
        return noticiaRepository.findAll(specification, page)
            .map(noticiaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NoticiaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Noticia> specification = createSpecification(criteria);
        return noticiaRepository.count(specification);
    }

    /**
     * Function to convert {@link NoticiaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Noticia> createSpecification(NoticiaCriteria criteria) {
        Specification<Noticia> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Noticia_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Noticia_.name));
            }
            if (criteria.getTexto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTexto(), Noticia_.texto));
            }
            if (criteria.getEnviado() != null) {
                specification = specification.and(buildSpecification(criteria.getEnviado(), Noticia_.enviado));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Noticia_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Noticia_.updated));
            }
        }
        return specification;
    }
}
