package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.AlertaTipoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.AlertaTipo}.
 */
public interface AlertaTipoService {

    /**
     * Save a alertaTipo.
     *
     * @param alertaTipoDTO the entity to save.
     * @return the persisted entity.
     */
    AlertaTipoDTO save(AlertaTipoDTO alertaTipoDTO);

    /**
     * Get all the alertaTipos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlertaTipoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" alertaTipo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlertaTipoDTO> findOne(Long id);

    /**
     * Delete the "id" alertaTipo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
