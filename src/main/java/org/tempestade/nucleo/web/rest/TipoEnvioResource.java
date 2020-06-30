package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.TipoEnvioService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.TipoEnvioDTO;
import org.tempestade.nucleo.service.dto.TipoEnvioCriteria;
import org.tempestade.nucleo.service.TipoEnvioQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.TipoEnvio}.
 */
@RestController
@RequestMapping("/api")
public class TipoEnvioResource {

    private final Logger log = LoggerFactory.getLogger(TipoEnvioResource.class);

    private static final String ENTITY_NAME = "tipoEnvio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoEnvioService tipoEnvioService;

    private final TipoEnvioQueryService tipoEnvioQueryService;

    public TipoEnvioResource(TipoEnvioService tipoEnvioService, TipoEnvioQueryService tipoEnvioQueryService) {
        this.tipoEnvioService = tipoEnvioService;
        this.tipoEnvioQueryService = tipoEnvioQueryService;
    }

    /**
     * {@code POST  /tipo-envios} : Create a new tipoEnvio.
     *
     * @param tipoEnvioDTO the tipoEnvioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoEnvioDTO, or with status {@code 400 (Bad Request)} if the tipoEnvio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-envios")
    public ResponseEntity<TipoEnvioDTO> createTipoEnvio(@Valid @RequestBody TipoEnvioDTO tipoEnvioDTO) throws URISyntaxException {
        log.debug("REST request to save TipoEnvio : {}", tipoEnvioDTO);
        if (tipoEnvioDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoEnvio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoEnvioDTO result = tipoEnvioService.save(tipoEnvioDTO);
        return ResponseEntity.created(new URI("/api/tipo-envios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-envios} : Updates an existing tipoEnvio.
     *
     * @param tipoEnvioDTO the tipoEnvioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoEnvioDTO,
     * or with status {@code 400 (Bad Request)} if the tipoEnvioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoEnvioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-envios")
    public ResponseEntity<TipoEnvioDTO> updateTipoEnvio(@Valid @RequestBody TipoEnvioDTO tipoEnvioDTO) throws URISyntaxException {
        log.debug("REST request to update TipoEnvio : {}", tipoEnvioDTO);
        if (tipoEnvioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoEnvioDTO result = tipoEnvioService.save(tipoEnvioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoEnvioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-envios} : get all the tipoEnvios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoEnvios in body.
     */
    @GetMapping("/tipo-envios")
    public ResponseEntity<List<TipoEnvioDTO>> getAllTipoEnvios(TipoEnvioCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TipoEnvios by criteria: {}", criteria);
        Page<TipoEnvioDTO> page = tipoEnvioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-envios/count} : count all the tipoEnvios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tipo-envios/count")
    public ResponseEntity<Long> countTipoEnvios(TipoEnvioCriteria criteria) {
        log.debug("REST request to count TipoEnvios by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoEnvioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-envios/:id} : get the "id" tipoEnvio.
     *
     * @param id the id of the tipoEnvioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoEnvioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-envios/{id}")
    public ResponseEntity<TipoEnvioDTO> getTipoEnvio(@PathVariable Long id) {
        log.debug("REST request to get TipoEnvio : {}", id);
        Optional<TipoEnvioDTO> tipoEnvioDTO = tipoEnvioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoEnvioDTO);
    }

    /**
     * {@code DELETE  /tipo-envios/:id} : delete the "id" tipoEnvio.
     *
     * @param id the id of the tipoEnvioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-envios/{id}")
    public ResponseEntity<Void> deleteTipoEnvio(@PathVariable Long id) {
        log.debug("REST request to delete TipoEnvio : {}", id);
        tipoEnvioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
