package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.AvisoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Aviso}.
 */
public interface AvisoService {

    /**
     * Save a aviso.
     *
     * @param avisoDTO the entity to save.
     * @return the persisted entity.
     */
    AvisoDTO save(AvisoDTO avisoDTO);

    /**
     * Get all the avisos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AvisoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" aviso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AvisoDTO> findOne(Long id);

    /**
     * Delete the "id" aviso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
