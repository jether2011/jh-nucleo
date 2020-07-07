package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.JornadaTrabalhoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.JornadaTrabalho}.
 */
public interface JornadaTrabalhoService {

    /**
     * Save a jornadaTrabalho.
     *
     * @param jornadaTrabalhoDTO the entity to save.
     * @return the persisted entity.
     */
    JornadaTrabalhoDTO save(JornadaTrabalhoDTO jornadaTrabalhoDTO);

    /**
     * Get all the jornadaTrabalhos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JornadaTrabalhoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" jornadaTrabalho.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JornadaTrabalhoDTO> findOne(Long id);

    /**
     * Delete the "id" jornadaTrabalho.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
