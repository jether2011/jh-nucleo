package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.RecursoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.RecursoDTO;
import org.tempestade.nucleo.service.dto.RecursoCriteria;
import org.tempestade.nucleo.service.RecursoQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Recurso}.
 */
@RestController
@RequestMapping("/api")
public class RecursoResource {

    private final Logger log = LoggerFactory.getLogger(RecursoResource.class);

    private static final String ENTITY_NAME = "recurso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecursoService recursoService;

    private final RecursoQueryService recursoQueryService;

    public RecursoResource(RecursoService recursoService, RecursoQueryService recursoQueryService) {
        this.recursoService = recursoService;
        this.recursoQueryService = recursoQueryService;
    }

    /**
     * {@code POST  /recursos} : Create a new recurso.
     *
     * @param recursoDTO the recursoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recursoDTO, or with status {@code 400 (Bad Request)} if the recurso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recursos")
    public ResponseEntity<RecursoDTO> createRecurso(@Valid @RequestBody RecursoDTO recursoDTO) throws URISyntaxException {
        log.debug("REST request to save Recurso : {}", recursoDTO);
        if (recursoDTO.getId() != null) {
            throw new BadRequestAlertException("A new recurso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecursoDTO result = recursoService.save(recursoDTO);
        return ResponseEntity.created(new URI("/api/recursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recursos} : Updates an existing recurso.
     *
     * @param recursoDTO the recursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recursoDTO,
     * or with status {@code 400 (Bad Request)} if the recursoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recursos")
    public ResponseEntity<RecursoDTO> updateRecurso(@Valid @RequestBody RecursoDTO recursoDTO) throws URISyntaxException {
        log.debug("REST request to update Recurso : {}", recursoDTO);
        if (recursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecursoDTO result = recursoService.save(recursoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recursoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recursos} : get all the recursos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recursos in body.
     */
    @GetMapping("/recursos")
    public ResponseEntity<List<RecursoDTO>> getAllRecursos(RecursoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Recursos by criteria: {}", criteria);
        Page<RecursoDTO> page = recursoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recursos/count} : count all the recursos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/recursos/count")
    public ResponseEntity<Long> countRecursos(RecursoCriteria criteria) {
        log.debug("REST request to count Recursos by criteria: {}", criteria);
        return ResponseEntity.ok().body(recursoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /recursos/:id} : get the "id" recurso.
     *
     * @param id the id of the recursoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recursoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recursos/{id}")
    public ResponseEntity<RecursoDTO> getRecurso(@PathVariable Long id) {
        log.debug("REST request to get Recurso : {}", id);
        Optional<RecursoDTO> recursoDTO = recursoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recursoDTO);
    }

    /**
     * {@code DELETE  /recursos/:id} : delete the "id" recurso.
     *
     * @param id the id of the recursoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recursos/{id}")
    public ResponseEntity<Void> deleteRecurso(@PathVariable Long id) {
        log.debug("REST request to delete Recurso : {}", id);
        recursoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
