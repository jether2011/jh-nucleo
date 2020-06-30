package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.CondicaoTempoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.CondicaoTempo}.
 */
public interface CondicaoTempoService {

    /**
     * Save a condicaoTempo.
     *
     * @param condicaoTempoDTO the entity to save.
     * @return the persisted entity.
     */
    CondicaoTempoDTO save(CondicaoTempoDTO condicaoTempoDTO);

    /**
     * Get all the condicaoTempos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CondicaoTempoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" condicaoTempo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CondicaoTempoDTO> findOne(Long id);

    /**
     * Delete the "id" condicaoTempo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
