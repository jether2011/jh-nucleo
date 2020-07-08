package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.TipoEnvioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.TipoEnvio}.
 */
public interface TipoEnvioService {

    /**
     * Save a tipoEnvio.
     *
     * @param tipoEnvioDTO the entity to save.
     * @return the persisted entity.
     */
    TipoEnvioDTO save(TipoEnvioDTO tipoEnvioDTO);

    /**
     * Get all the tipoEnvios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoEnvioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoEnvio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoEnvioDTO> findOne(Long id);

    /**
     * Delete the "id" tipoEnvio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
