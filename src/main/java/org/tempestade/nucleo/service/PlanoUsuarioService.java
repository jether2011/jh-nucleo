package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.PlanoUsuarioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.PlanoUsuario}.
 */
public interface PlanoUsuarioService {

    /**
     * Save a planoUsuario.
     *
     * @param planoUsuarioDTO the entity to save.
     * @return the persisted entity.
     */
    PlanoUsuarioDTO save(PlanoUsuarioDTO planoUsuarioDTO);

    /**
     * Get all the planoUsuarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanoUsuarioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" planoUsuario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanoUsuarioDTO> findOne(Long id);

    /**
     * Delete the "id" planoUsuario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
