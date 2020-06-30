package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.AlvoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Alvo}.
 */
public interface AlvoService {

    /**
     * Save a alvo.
     *
     * @param alvoDTO the entity to save.
     * @return the persisted entity.
     */
    AlvoDTO save(AlvoDTO alvoDTO);

    /**
     * Get all the alvos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlvoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" alvo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlvoDTO> findOne(Long id);

    /**
     * Delete the "id" alvo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
