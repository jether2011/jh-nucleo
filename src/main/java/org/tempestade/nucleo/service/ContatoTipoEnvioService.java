package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.ContatoTipoEnvioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.ContatoTipoEnvio}.
 */
public interface ContatoTipoEnvioService {

    /**
     * Save a contatoTipoEnvio.
     *
     * @param contatoTipoEnvioDTO the entity to save.
     * @return the persisted entity.
     */
    ContatoTipoEnvioDTO save(ContatoTipoEnvioDTO contatoTipoEnvioDTO);

    /**
     * Get all the contatoTipoEnvios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContatoTipoEnvioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" contatoTipoEnvio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContatoTipoEnvioDTO> findOne(Long id);

    /**
     * Delete the "id" contatoTipoEnvio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
