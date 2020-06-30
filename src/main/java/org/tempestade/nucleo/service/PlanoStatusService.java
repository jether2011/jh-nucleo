package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.PlanoStatusDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.PlanoStatus}.
 */
public interface PlanoStatusService {

    /**
     * Save a planoStatus.
     *
     * @param planoStatusDTO the entity to save.
     * @return the persisted entity.
     */
    PlanoStatusDTO save(PlanoStatusDTO planoStatusDTO);

    /**
     * Get all the planoStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanoStatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" planoStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanoStatusDTO> findOne(Long id);

    /**
     * Delete the "id" planoStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
