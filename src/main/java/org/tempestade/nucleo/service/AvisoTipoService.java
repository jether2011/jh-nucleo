package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.AvisoTipoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.AvisoTipo}.
 */
public interface AvisoTipoService {

    /**
     * Save a avisoTipo.
     *
     * @param avisoTipoDTO the entity to save.
     * @return the persisted entity.
     */
    AvisoTipoDTO save(AvisoTipoDTO avisoTipoDTO);

    /**
     * Get all the avisoTipos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AvisoTipoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" avisoTipo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AvisoTipoDTO> findOne(Long id);

    /**
     * Delete the "id" avisoTipo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
