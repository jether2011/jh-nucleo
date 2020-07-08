package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.TipoEnvioIntegradorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.TipoEnvioIntegrador}.
 */
public interface TipoEnvioIntegradorService {

    /**
     * Save a tipoEnvioIntegrador.
     *
     * @param tipoEnvioIntegradorDTO the entity to save.
     * @return the persisted entity.
     */
    TipoEnvioIntegradorDTO save(TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO);

    /**
     * Get all the tipoEnvioIntegradors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoEnvioIntegradorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoEnvioIntegrador.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoEnvioIntegradorDTO> findOne(Long id);

    /**
     * Delete the "id" tipoEnvioIntegrador.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
