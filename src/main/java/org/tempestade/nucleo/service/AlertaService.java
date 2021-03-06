package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.AlertaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Alerta}.
 */
public interface AlertaService {

    /**
     * Save a alerta.
     *
     * @param alertaDTO the entity to save.
     * @return the persisted entity.
     */
    AlertaDTO save(AlertaDTO alertaDTO);

    /**
     * Get all the alertas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlertaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" alerta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlertaDTO> findOne(Long id);

    /**
     * Delete the "id" alerta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
