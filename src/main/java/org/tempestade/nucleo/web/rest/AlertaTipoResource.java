package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.AlertaTipoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.AlertaTipoDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.AlertaTipo}.
 */
@RestController
@RequestMapping("/api")
public class AlertaTipoResource {

    private final Logger log = LoggerFactory.getLogger(AlertaTipoResource.class);

    private static final String ENTITY_NAME = "alertaTipo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlertaTipoService alertaTipoService;

    public AlertaTipoResource(AlertaTipoService alertaTipoService) {
        this.alertaTipoService = alertaTipoService;
    }

    /**
     * {@code POST  /alerta-tipos} : Create a new alertaTipo.
     *
     * @param alertaTipoDTO the alertaTipoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alertaTipoDTO, or with status {@code 400 (Bad Request)} if the alertaTipo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alerta-tipos")
    public ResponseEntity<AlertaTipoDTO> createAlertaTipo(@Valid @RequestBody AlertaTipoDTO alertaTipoDTO) throws URISyntaxException {
        log.debug("REST request to save AlertaTipo : {}", alertaTipoDTO);
        if (alertaTipoDTO.getId() != null) {
            throw new BadRequestAlertException("A new alertaTipo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlertaTipoDTO result = alertaTipoService.save(alertaTipoDTO);
        return ResponseEntity.created(new URI("/api/alerta-tipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alerta-tipos} : Updates an existing alertaTipo.
     *
     * @param alertaTipoDTO the alertaTipoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alertaTipoDTO,
     * or with status {@code 400 (Bad Request)} if the alertaTipoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alertaTipoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alerta-tipos")
    public ResponseEntity<AlertaTipoDTO> updateAlertaTipo(@Valid @RequestBody AlertaTipoDTO alertaTipoDTO) throws URISyntaxException {
        log.debug("REST request to update AlertaTipo : {}", alertaTipoDTO);
        if (alertaTipoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlertaTipoDTO result = alertaTipoService.save(alertaTipoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alertaTipoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alerta-tipos} : get all the alertaTipos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alertaTipos in body.
     */
    @GetMapping("/alerta-tipos")
    public ResponseEntity<List<AlertaTipoDTO>> getAllAlertaTipos(Pageable pageable) {
        log.debug("REST request to get a page of AlertaTipos");
        Page<AlertaTipoDTO> page = alertaTipoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alerta-tipos/:id} : get the "id" alertaTipo.
     *
     * @param id the id of the alertaTipoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alertaTipoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alerta-tipos/{id}")
    public ResponseEntity<AlertaTipoDTO> getAlertaTipo(@PathVariable Long id) {
        log.debug("REST request to get AlertaTipo : {}", id);
        Optional<AlertaTipoDTO> alertaTipoDTO = alertaTipoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alertaTipoDTO);
    }

    /**
     * {@code DELETE  /alerta-tipos/:id} : delete the "id" alertaTipo.
     *
     * @param id the id of the alertaTipoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alerta-tipos/{id}")
    public ResponseEntity<Void> deleteAlertaTipo(@PathVariable Long id) {
        log.debug("REST request to delete AlertaTipo : {}", id);
        alertaTipoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
