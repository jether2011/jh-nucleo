package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.ContatoAlvoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.ContatoAlvo}.
 */
public interface ContatoAlvoService {

    /**
     * Save a contatoAlvo.
     *
     * @param contatoAlvoDTO the entity to save.
     * @return the persisted entity.
     */
    ContatoAlvoDTO save(ContatoAlvoDTO contatoAlvoDTO);

    /**
     * Get all the contatoAlvos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContatoAlvoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" contatoAlvo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContatoAlvoDTO> findOne(Long id);

    /**
     * Delete the "id" contatoAlvo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
