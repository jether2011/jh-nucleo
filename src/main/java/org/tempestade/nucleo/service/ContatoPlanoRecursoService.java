package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.ContatoPlanoRecursoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.ContatoPlanoRecurso}.
 */
public interface ContatoPlanoRecursoService {

    /**
     * Save a contatoPlanoRecurso.
     *
     * @param contatoPlanoRecursoDTO the entity to save.
     * @return the persisted entity.
     */
    ContatoPlanoRecursoDTO save(ContatoPlanoRecursoDTO contatoPlanoRecursoDTO);

    /**
     * Get all the contatoPlanoRecursos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContatoPlanoRecursoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" contatoPlanoRecurso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContatoPlanoRecursoDTO> findOne(Long id);

    /**
     * Delete the "id" contatoPlanoRecurso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
