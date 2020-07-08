package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.AvisoMeteorologicoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.AvisoMeteorologico}.
 */
public interface AvisoMeteorologicoService {

    /**
     * Save a avisoMeteorologico.
     *
     * @param avisoMeteorologicoDTO the entity to save.
     * @return the persisted entity.
     */
    AvisoMeteorologicoDTO save(AvisoMeteorologicoDTO avisoMeteorologicoDTO);

    /**
     * Get all the avisoMeteorologicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AvisoMeteorologicoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" avisoMeteorologico.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AvisoMeteorologicoDTO> findOne(Long id);

    /**
     * Delete the "id" avisoMeteorologico.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
