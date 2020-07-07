package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.PerfilDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Perfil}.
 */
public interface PerfilService {

    /**
     * Save a perfil.
     *
     * @param perfilDTO the entity to save.
     * @return the persisted entity.
     */
    PerfilDTO save(PerfilDTO perfilDTO);

    /**
     * Get all the perfils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PerfilDTO> findAll(Pageable pageable);


    /**
     * Get the "id" perfil.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PerfilDTO> findOne(Long id);

    /**
     * Delete the "id" perfil.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
