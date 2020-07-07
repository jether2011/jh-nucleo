package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.PlanoRecursoTipoEnvioService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.PlanoRecursoTipoEnvioDTO;
import org.tempestade.nucleo.service.dto.PlanoRecursoTipoEnvioCriteria;
import org.tempestade.nucleo.service.PlanoRecursoTipoEnvioQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.PlanoRecursoTipoEnvio}.
 */
@RestController
@RequestMapping("/api")
public class PlanoRecursoTipoEnvioResource {

    private final Logger log = LoggerFactory.getLogger(PlanoRecursoTipoEnvioResource.class);

    private static final String ENTITY_NAME = "planoRecursoTipoEnvio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoRecursoTipoEnvioService planoRecursoTipoEnvioService;

    private final PlanoRecursoTipoEnvioQueryService planoRecursoTipoEnvioQueryService;

    public PlanoRecursoTipoEnvioResource(PlanoRecursoTipoEnvioService planoRecursoTipoEnvioService, PlanoRecursoTipoEnvioQueryService planoRecursoTipoEnvioQueryService) {
        this.planoRecursoTipoEnvioService = planoRecursoTipoEnvioService;
        this.planoRecursoTipoEnvioQueryService = planoRecursoTipoEnvioQueryService;
    }

    /**
     * {@code POST  /plano-recurso-tipo-envios} : Create a new planoRecursoTipoEnvio.
     *
     * @param planoRecursoTipoEnvioDTO the planoRecursoTipoEnvioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoRecursoTipoEnvioDTO, or with status {@code 400 (Bad Request)} if the planoRecursoTipoEnvio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plano-recurso-tipo-envios")
    public ResponseEntity<PlanoRecursoTipoEnvioDTO> createPlanoRecursoTipoEnvio(@Valid @RequestBody PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO) throws URISyntaxException {
        log.debug("REST request to save PlanoRecursoTipoEnvio : {}", planoRecursoTipoEnvioDTO);
        if (planoRecursoTipoEnvioDTO.getId() != null) {
            throw new BadRequestAlertException("A new planoRecursoTipoEnvio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanoRecursoTipoEnvioDTO result = planoRecursoTipoEnvioService.save(planoRecursoTipoEnvioDTO);
        return ResponseEntity.created(new URI("/api/plano-recurso-tipo-envios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plano-recurso-tipo-envios} : Updates an existing planoRecursoTipoEnvio.
     *
     * @param planoRecursoTipoEnvioDTO the planoRecursoTipoEnvioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoRecursoTipoEnvioDTO,
     * or with status {@code 400 (Bad Request)} if the planoRecursoTipoEnvioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planoRecursoTipoEnvioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plano-recurso-tipo-envios")
    public ResponseEntity<PlanoRecursoTipoEnvioDTO> updatePlanoRecursoTipoEnvio(@Valid @RequestBody PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO) throws URISyntaxException {
        log.debug("REST request to update PlanoRecursoTipoEnvio : {}", planoRecursoTipoEnvioDTO);
        if (planoRecursoTipoEnvioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanoRecursoTipoEnvioDTO result = planoRecursoTipoEnvioService.save(planoRecursoTipoEnvioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoRecursoTipoEnvioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plano-recurso-tipo-envios} : get all the planoRecursoTipoEnvios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoRecursoTipoEnvios in body.
     */
    @GetMapping("/plano-recurso-tipo-envios")
    public ResponseEntity<List<PlanoRecursoTipoEnvioDTO>> getAllPlanoRecursoTipoEnvios(PlanoRecursoTipoEnvioCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PlanoRecursoTipoEnvios by criteria: {}", criteria);
        Page<PlanoRecursoTipoEnvioDTO> page = planoRecursoTipoEnvioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plano-recurso-tipo-envios/count} : count all the planoRecursoTipoEnvios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/plano-recurso-tipo-envios/count")
    public ResponseEntity<Long> countPlanoRecursoTipoEnvios(PlanoRecursoTipoEnvioCriteria criteria) {
        log.debug("REST request to count PlanoRecursoTipoEnvios by criteria: {}", criteria);
        return ResponseEntity.ok().body(planoRecursoTipoEnvioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /plano-recurso-tipo-envios/:id} : get the "id" planoRecursoTipoEnvio.
     *
     * @param id the id of the planoRecursoTipoEnvioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planoRecursoTipoEnvioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plano-recurso-tipo-envios/{id}")
    public ResponseEntity<PlanoRecursoTipoEnvioDTO> getPlanoRecursoTipoEnvio(@PathVariable Long id) {
        log.debug("REST request to get PlanoRecursoTipoEnvio : {}", id);
        Optional<PlanoRecursoTipoEnvioDTO> planoRecursoTipoEnvioDTO = planoRecursoTipoEnvioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planoRecursoTipoEnvioDTO);
    }

    /**
     * {@code DELETE  /plano-recurso-tipo-envios/:id} : delete the "id" planoRecursoTipoEnvio.
     *
     * @param id the id of the planoRecursoTipoEnvioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plano-recurso-tipo-envios/{id}")
    public ResponseEntity<Void> deletePlanoRecursoTipoEnvio(@PathVariable Long id) {
        log.debug("REST request to delete PlanoRecursoTipoEnvio : {}", id);
        planoRecursoTipoEnvioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
