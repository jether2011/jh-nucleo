package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.LogDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Log}.
 */
public interface LogService {

    /**
     * Save a log.
     *
     * @param logDTO the entity to save.
     * @return the persisted entity.
     */
    LogDTO save(LogDTO logDTO);

    /**
     * Get all the logs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LogDTO> findAll(Pageable pageable);


    /**
     * Get the "id" log.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LogDTO> findOne(Long id);

    /**
     * Delete the "id" log.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
