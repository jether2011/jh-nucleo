package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.TipoEnvioIntegradorService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.TipoEnvioIntegradorDTO;
import org.tempestade.nucleo.service.dto.TipoEnvioIntegradorCriteria;
import org.tempestade.nucleo.service.TipoEnvioIntegradorQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.TipoEnvioIntegrador}.
 */
@RestController
@RequestMapping("/api")
public class TipoEnvioIntegradorResource {

    private final Logger log = LoggerFactory.getLogger(TipoEnvioIntegradorResource.class);

    private static final String ENTITY_NAME = "tipoEnvioIntegrador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoEnvioIntegradorService tipoEnvioIntegradorService;

    private final TipoEnvioIntegradorQueryService tipoEnvioIntegradorQueryService;

    public TipoEnvioIntegradorResource(TipoEnvioIntegradorService tipoEnvioIntegradorService, TipoEnvioIntegradorQueryService tipoEnvioIntegradorQueryService) {
        this.tipoEnvioIntegradorService = tipoEnvioIntegradorService;
        this.tipoEnvioIntegradorQueryService = tipoEnvioIntegradorQueryService;
    }

    /**
     * {@code POST  /tipo-envio-integradors} : Create a new tipoEnvioIntegrador.
     *
     * @param tipoEnvioIntegradorDTO the tipoEnvioIntegradorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoEnvioIntegradorDTO, or with status {@code 400 (Bad Request)} if the tipoEnvioIntegrador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-envio-integradors")
    public ResponseEntity<TipoEnvioIntegradorDTO> createTipoEnvioIntegrador(@Valid @RequestBody TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO) throws URISyntaxException {
        log.debug("REST request to save TipoEnvioIntegrador : {}", tipoEnvioIntegradorDTO);
        if (tipoEnvioIntegradorDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoEnvioIntegrador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoEnvioIntegradorDTO result = tipoEnvioIntegradorService.save(tipoEnvioIntegradorDTO);
        return ResponseEntity.created(new URI("/api/tipo-envio-integradors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-envio-integradors} : Updates an existing tipoEnvioIntegrador.
     *
     * @param tipoEnvioIntegradorDTO the tipoEnvioIntegradorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoEnvioIntegradorDTO,
     * or with status {@code 400 (Bad Request)} if the tipoEnvioIntegradorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoEnvioIntegradorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-envio-integradors")
    public ResponseEntity<TipoEnvioIntegradorDTO> updateTipoEnvioIntegrador(@Valid @RequestBody TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO) throws URISyntaxException {
        log.debug("REST request to update TipoEnvioIntegrador : {}", tipoEnvioIntegradorDTO);
        if (tipoEnvioIntegradorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoEnvioIntegradorDTO result = tipoEnvioIntegradorService.save(tipoEnvioIntegradorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoEnvioIntegradorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-envio-integradors} : get all the tipoEnvioIntegradors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoEnvioIntegradors in body.
     */
    @GetMapping("/tipo-envio-integradors")
    public ResponseEntity<List<TipoEnvioIntegradorDTO>> getAllTipoEnvioIntegradors(TipoEnvioIntegradorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TipoEnvioIntegradors by criteria: {}", criteria);
        Page<TipoEnvioIntegradorDTO> page = tipoEnvioIntegradorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-envio-integradors/count} : count all the tipoEnvioIntegradors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tipo-envio-integradors/count")
    public ResponseEntity<Long> countTipoEnvioIntegradors(TipoEnvioIntegradorCriteria criteria) {
        log.debug("REST request to count TipoEnvioIntegradors by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoEnvioIntegradorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-envio-integradors/:id} : get the "id" tipoEnvioIntegrador.
     *
     * @param id the id of the tipoEnvioIntegradorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoEnvioIntegradorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-envio-integradors/{id}")
    public ResponseEntity<TipoEnvioIntegradorDTO> getTipoEnvioIntegrador(@PathVariable Long id) {
        log.debug("REST request to get TipoEnvioIntegrador : {}", id);
        Optional<TipoEnvioIntegradorDTO> tipoEnvioIntegradorDTO = tipoEnvioIntegradorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoEnvioIntegradorDTO);
    }

    /**
     * {@code DELETE  /tipo-envio-integradors/:id} : delete the "id" tipoEnvioIntegrador.
     *
     * @param id the id of the tipoEnvioIntegradorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-envio-integradors/{id}")
    public ResponseEntity<Void> deleteTipoEnvioIntegrador(@PathVariable Long id) {
        log.debug("REST request to delete TipoEnvioIntegrador : {}", id);
        tipoEnvioIntegradorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
