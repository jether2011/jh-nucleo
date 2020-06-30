package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.PlanoRedeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.PlanoRede}.
 */
public interface PlanoRedeService {

    /**
     * Save a planoRede.
     *
     * @param planoRedeDTO the entity to save.
     * @return the persisted entity.
     */
    PlanoRedeDTO save(PlanoRedeDTO planoRedeDTO);

    /**
     * Get all the planoRedes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanoRedeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" planoRede.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanoRedeDTO> findOne(Long id);

    /**
     * Delete the "id" planoRede.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
