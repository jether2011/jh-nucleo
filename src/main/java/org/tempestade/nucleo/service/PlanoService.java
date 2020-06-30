package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.PlanoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Plano}.
 */
public interface PlanoService {

    /**
     * Save a plano.
     *
     * @param planoDTO the entity to save.
     * @return the persisted entity.
     */
    PlanoDTO save(PlanoDTO planoDTO);

    /**
     * Get all the planos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" plano.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanoDTO> findOne(Long id);

    /**
     * Delete the "id" plano.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
