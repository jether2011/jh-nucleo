package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.AlertaRiscoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.AlertaRiscoDTO;
import org.tempestade.nucleo.service.dto.AlertaRiscoCriteria;
import org.tempestade.nucleo.service.AlertaRiscoQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.AlertaRisco}.
 */
@RestController
@RequestMapping("/api")
public class AlertaRiscoResource {

    private final Logger log = LoggerFactory.getLogger(AlertaRiscoResource.class);

    private static final String ENTITY_NAME = "alertaRisco";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlertaRiscoService alertaRiscoService;

    private final AlertaRiscoQueryService alertaRiscoQueryService;

    public AlertaRiscoResource(AlertaRiscoService alertaRiscoService, AlertaRiscoQueryService alertaRiscoQueryService) {
        this.alertaRiscoService = alertaRiscoService;
        this.alertaRiscoQueryService = alertaRiscoQueryService;
    }

    /**
     * {@code POST  /alerta-riscos} : Create a new alertaRisco.
     *
     * @param alertaRiscoDTO the alertaRiscoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alertaRiscoDTO, or with status {@code 400 (Bad Request)} if the alertaRisco has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alerta-riscos")
    public ResponseEntity<AlertaRiscoDTO> createAlertaRisco(@Valid @RequestBody AlertaRiscoDTO alertaRiscoDTO) throws URISyntaxException {
        log.debug("REST request to save AlertaRisco : {}", alertaRiscoDTO);
        if (alertaRiscoDTO.getId() != null) {
            throw new BadRequestAlertException("A new alertaRisco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlertaRiscoDTO result = alertaRiscoService.save(alertaRiscoDTO);
        return ResponseEntity.created(new URI("/api/alerta-riscos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alerta-riscos} : Updates an existing alertaRisco.
     *
     * @param alertaRiscoDTO the alertaRiscoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alertaRiscoDTO,
     * or with status {@code 400 (Bad Request)} if the alertaRiscoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alertaRiscoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alerta-riscos")
    public ResponseEntity<AlertaRiscoDTO> updateAlertaRisco(@Valid @RequestBody AlertaRiscoDTO alertaRiscoDTO) throws URISyntaxException {
        log.debug("REST request to update AlertaRisco : {}", alertaRiscoDTO);
        if (alertaRiscoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlertaRiscoDTO result = alertaRiscoService.save(alertaRiscoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alertaRiscoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alerta-riscos} : get all the alertaRiscos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alertaRiscos in body.
     */
    @GetMapping("/alerta-riscos")
    public ResponseEntity<List<AlertaRiscoDTO>> getAllAlertaRiscos(AlertaRiscoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AlertaRiscos by criteria: {}", criteria);
        Page<AlertaRiscoDTO> page = alertaRiscoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alerta-riscos/count} : count all the alertaRiscos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/alerta-riscos/count")
    public ResponseEntity<Long> countAlertaRiscos(AlertaRiscoCriteria criteria) {
        log.debug("REST request to count AlertaRiscos by criteria: {}", criteria);
        return ResponseEntity.ok().body(alertaRiscoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /alerta-riscos/:id} : get the "id" alertaRisco.
     *
     * @param id the id of the alertaRiscoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alertaRiscoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alerta-riscos/{id}")
    public ResponseEntity<AlertaRiscoDTO> getAlertaRisco(@PathVariable Long id) {
        log.debug("REST request to get AlertaRisco : {}", id);
        Optional<AlertaRiscoDTO> alertaRiscoDTO = alertaRiscoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alertaRiscoDTO);
    }

    /**
     * {@code DELETE  /alerta-riscos/:id} : delete the "id" alertaRisco.
     *
     * @param id the id of the alertaRiscoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alerta-riscos/{id}")
    public ResponseEntity<Void> deleteAlertaRisco(@PathVariable Long id) {
        log.debug("REST request to delete AlertaRisco : {}", id);
        alertaRiscoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
