package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.RedeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Rede}.
 */
public interface RedeService {

    /**
     * Save a rede.
     *
     * @param redeDTO the entity to save.
     * @return the persisted entity.
     */
    RedeDTO save(RedeDTO redeDTO);

    /**
     * Get all the redes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RedeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" rede.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RedeDTO> findOne(Long id);

    /**
     * Delete the "id" rede.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
