package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.PlanoRecursoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.PlanoRecursoDTO;
import org.tempestade.nucleo.service.dto.PlanoRecursoCriteria;
import org.tempestade.nucleo.service.PlanoRecursoQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.PlanoRecurso}.
 */
@RestController
@RequestMapping("/api")
public class PlanoRecursoResource {

    private final Logger log = LoggerFactory.getLogger(PlanoRecursoResource.class);

    private static final String ENTITY_NAME = "planoRecurso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoRecursoService planoRecursoService;

    private final PlanoRecursoQueryService planoRecursoQueryService;

    public PlanoRecursoResource(PlanoRecursoService planoRecursoService, PlanoRecursoQueryService planoRecursoQueryService) {
        this.planoRecursoService = planoRecursoService;
        this.planoRecursoQueryService = planoRecursoQueryService;
    }

    /**
     * {@code POST  /plano-recursos} : Create a new planoRecurso.
     *
     * @param planoRecursoDTO the planoRecursoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoRecursoDTO, or with status {@code 400 (Bad Request)} if the planoRecurso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plano-recursos")
    public ResponseEntity<PlanoRecursoDTO> createPlanoRecurso(@Valid @RequestBody PlanoRecursoDTO planoRecursoDTO) throws URISyntaxException {
        log.debug("REST request to save PlanoRecurso : {}", planoRecursoDTO);
        if (planoRecursoDTO.getId() != null) {
            throw new BadRequestAlertException("A new planoRecurso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanoRecursoDTO result = planoRecursoService.save(planoRecursoDTO);
        return ResponseEntity.created(new URI("/api/plano-recursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plano-recursos} : Updates an existing planoRecurso.
     *
     * @param planoRecursoDTO the planoRecursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoRecursoDTO,
     * or with status {@code 400 (Bad Request)} if the planoRecursoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planoRecursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plano-recursos")
    public ResponseEntity<PlanoRecursoDTO> updatePlanoRecurso(@Valid @RequestBody PlanoRecursoDTO planoRecursoDTO) throws URISyntaxException {
        log.debug("REST request to update PlanoRecurso : {}", planoRecursoDTO);
        if (planoRecursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanoRecursoDTO result = planoRecursoService.save(planoRecursoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoRecursoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plano-recursos} : get all the planoRecursos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoRecursos in body.
     */
    @GetMapping("/plano-recursos")
    public ResponseEntity<List<PlanoRecursoDTO>> getAllPlanoRecursos(PlanoRecursoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PlanoRecursos by criteria: {}", criteria);
        Page<PlanoRecursoDTO> page = planoRecursoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plano-recursos/count} : count all the planoRecursos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/plano-recursos/count")
    public ResponseEntity<Long> countPlanoRecursos(PlanoRecursoCriteria criteria) {
        log.debug("REST request to count PlanoRecursos by criteria: {}", criteria);
        return ResponseEntity.ok().body(planoRecursoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /plano-recursos/:id} : get the "id" planoRecurso.
     *
     * @param id the id of the planoRecursoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planoRecursoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plano-recursos/{id}")
    public ResponseEntity<PlanoRecursoDTO> getPlanoRecurso(@PathVariable Long id) {
        log.debug("REST request to get PlanoRecurso : {}", id);
        Optional<PlanoRecursoDTO> planoRecursoDTO = planoRecursoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planoRecursoDTO);
    }

    /**
     * {@code DELETE  /plano-recursos/:id} : delete the "id" planoRecurso.
     *
     * @param id the id of the planoRecursoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plano-recursos/{id}")
    public ResponseEntity<Void> deletePlanoRecurso(@PathVariable Long id) {
        log.debug("REST request to delete PlanoRecurso : {}", id);
        planoRecursoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
