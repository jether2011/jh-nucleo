package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.RecursoTemplateDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.RecursoTemplate}.
 */
public interface RecursoTemplateService {

    /**
     * Save a recursoTemplate.
     *
     * @param recursoTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    RecursoTemplateDTO save(RecursoTemplateDTO recursoTemplateDTO);

    /**
     * Get all the recursoTemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RecursoTemplateDTO> findAll(Pageable pageable);


    /**
     * Get the "id" recursoTemplate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecursoTemplateDTO> findOne(Long id);

    /**
     * Delete the "id" recursoTemplate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
