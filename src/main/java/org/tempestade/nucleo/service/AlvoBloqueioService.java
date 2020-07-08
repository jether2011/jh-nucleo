package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.AlvoBloqueioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.AlvoBloqueio}.
 */
public interface AlvoBloqueioService {

    /**
     * Save a alvoBloqueio.
     *
     * @param alvoBloqueioDTO the entity to save.
     * @return the persisted entity.
     */
    AlvoBloqueioDTO save(AlvoBloqueioDTO alvoBloqueioDTO);

    /**
     * Get all the alvoBloqueios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlvoBloqueioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" alvoBloqueio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlvoBloqueioDTO> findOne(Long id);

    /**
     * Delete the "id" alvoBloqueio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
