package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.InformativoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Informativo}.
 */
public interface InformativoService {

    /**
     * Save a informativo.
     *
     * @param informativoDTO the entity to save.
     * @return the persisted entity.
     */
    InformativoDTO save(InformativoDTO informativoDTO);

    /**
     * Get all the informativos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InformativoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" informativo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InformativoDTO> findOne(Long id);

    /**
     * Delete the "id" informativo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
