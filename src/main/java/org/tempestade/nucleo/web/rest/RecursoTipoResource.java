package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.RecursoTipoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.RecursoTipoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.tempestade.nucleo.domain.RecursoTipo}.
 */
@RestController
@RequestMapping("/api")
public class RecursoTipoResource {

    private final Logger log = LoggerFactory.getLogger(RecursoTipoResource.class);

    private static final String ENTITY_NAME = "recursoTipo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecursoTipoService recursoTipoService;

    public RecursoTipoResource(RecursoTipoService recursoTipoService) {
        this.recursoTipoService = recursoTipoService;
    }

    /**
     * {@code POST  /recurso-tipos} : Create a new recursoTipo.
     *
     * @param recursoTipoDTO the recursoTipoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recursoTipoDTO, or with status {@code 400 (Bad Request)} if the recursoTipo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recurso-tipos")
    public ResponseEntity<RecursoTipoDTO> createRecursoTipo(@Valid @RequestBody RecursoTipoDTO recursoTipoDTO) throws URISyntaxException {
        log.debug("REST request to save RecursoTipo : {}", recursoTipoDTO);
        if (recursoTipoDTO.getId() != null) {
            throw new BadRequestAlertException("A new recursoTipo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecursoTipoDTO result = recursoTipoService.save(recursoTipoDTO);
        return ResponseEntity.created(new URI("/api/recurso-tipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recurso-tipos} : Updates an existing recursoTipo.
     *
     * @param recursoTipoDTO the recursoTipoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recursoTipoDTO,
     * or with status {@code 400 (Bad Request)} if the recursoTipoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recursoTipoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recurso-tipos")
    public ResponseEntity<RecursoTipoDTO> updateRecursoTipo(@Valid @RequestBody RecursoTipoDTO recursoTipoDTO) throws URISyntaxException {
        log.debug("REST request to update RecursoTipo : {}", recursoTipoDTO);
        if (recursoTipoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecursoTipoDTO result = recursoTipoService.save(recursoTipoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recursoTipoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recurso-tipos} : get all the recursoTipos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recursoTipos in body.
     */
    @GetMapping("/recurso-tipos")
    public ResponseEntity<List<RecursoTipoDTO>> getAllRecursoTipos(Pageable pageable) {
        log.debug("REST request to get a page of RecursoTipos");
        Page<RecursoTipoDTO> page = recursoTipoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recurso-tipos/:id} : get the "id" recursoTipo.
     *
     * @param id the id of the recursoTipoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recursoTipoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recurso-tipos/{id}")
    public ResponseEntity<RecursoTipoDTO> getRecursoTipo(@PathVariable Long id) {
        log.debug("REST request to get RecursoTipo : {}", id);
        Optional<RecursoTipoDTO> recursoTipoDTO = recursoTipoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recursoTipoDTO);
    }

    /**
     * {@code DELETE  /recurso-tipos/:id} : delete the "id" recursoTipo.
     *
     * @param id the id of the recursoTipoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recurso-tipos/{id}")
    public ResponseEntity<Void> deleteRecursoTipo(@PathVariable Long id) {
        log.debug("REST request to delete RecursoTipo : {}", id);
        recursoTipoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
