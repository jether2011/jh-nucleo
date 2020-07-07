package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.IntegradorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Integrador}.
 */
public interface IntegradorService {

    /**
     * Save a integrador.
     *
     * @param integradorDTO the entity to save.
     * @return the persisted entity.
     */
    IntegradorDTO save(IntegradorDTO integradorDTO);

    /**
     * Get all the integradors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IntegradorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" integrador.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntegradorDTO> findOne(Long id);

    /**
     * Delete the "id" integrador.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
