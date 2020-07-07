package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.PlanoRecursoTipoEnvioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.PlanoRecursoTipoEnvio}.
 */
public interface PlanoRecursoTipoEnvioService {

    /**
     * Save a planoRecursoTipoEnvio.
     *
     * @param planoRecursoTipoEnvioDTO the entity to save.
     * @return the persisted entity.
     */
    PlanoRecursoTipoEnvioDTO save(PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO);

    /**
     * Get all the planoRecursoTipoEnvios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanoRecursoTipoEnvioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" planoRecursoTipoEnvio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanoRecursoTipoEnvioDTO> findOne(Long id);

    /**
     * Delete the "id" planoRecursoTipoEnvio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
