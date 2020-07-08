package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.NotificacaoEnviadaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.NotificacaoEnviada}.
 */
public interface NotificacaoEnviadaService {

    /**
     * Save a notificacaoEnviada.
     *
     * @param notificacaoEnviadaDTO the entity to save.
     * @return the persisted entity.
     */
    NotificacaoEnviadaDTO save(NotificacaoEnviadaDTO notificacaoEnviadaDTO);

    /**
     * Get all the notificacaoEnviadas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NotificacaoEnviadaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" notificacaoEnviada.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NotificacaoEnviadaDTO> findOne(Long id);

    /**
     * Delete the "id" notificacaoEnviada.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
