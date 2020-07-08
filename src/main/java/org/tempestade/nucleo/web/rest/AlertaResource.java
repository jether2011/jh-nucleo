package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.AlertaService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.AlertaDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Alerta}.
 */
@RestController
@RequestMapping("/api")
public class AlertaResource {

    private final Logger log = LoggerFactory.getLogger(AlertaResource.class);

    private static final String ENTITY_NAME = "alerta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlertaService alertaService;

    public AlertaResource(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    /**
     * {@code POST  /alertas} : Create a new alerta.
     *
     * @param alertaDTO the alertaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alertaDTO, or with status {@code 400 (Bad Request)} if the alerta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alertas")
    public ResponseEntity<AlertaDTO> createAlerta(@Valid @RequestBody AlertaDTO alertaDTO) throws URISyntaxException {
        log.debug("REST request to save Alerta : {}", alertaDTO);
        if (alertaDTO.getId() != null) {
            throw new BadRequestAlertException("A new alerta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlertaDTO result = alertaService.save(alertaDTO);
        return ResponseEntity.created(new URI("/api/alertas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alertas} : Updates an existing alerta.
     *
     * @param alertaDTO the alertaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alertaDTO,
     * or with status {@code 400 (Bad Request)} if the alertaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alertaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alertas")
    public ResponseEntity<AlertaDTO> updateAlerta(@Valid @RequestBody AlertaDTO alertaDTO) throws URISyntaxException {
        log.debug("REST request to update Alerta : {}", alertaDTO);
        if (alertaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlertaDTO result = alertaService.save(alertaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alertaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alertas} : get all the alertas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alertas in body.
     */
    @GetMapping("/alertas")
    public ResponseEntity<List<AlertaDTO>> getAllAlertas(Pageable pageable) {
        log.debug("REST request to get a page of Alertas");
        Page<AlertaDTO> page = alertaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alertas/:id} : get the "id" alerta.
     *
     * @param id the id of the alertaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alertaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alertas/{id}")
    public ResponseEntity<AlertaDTO> getAlerta(@PathVariable Long id) {
        log.debug("REST request to get Alerta : {}", id);
        Optional<AlertaDTO> alertaDTO = alertaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alertaDTO);
    }

    /**
     * {@code DELETE  /alertas/:id} : delete the "id" alerta.
     *
     * @param id the id of the alertaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alertas/{id}")
    public ResponseEntity<Void> deleteAlerta(@PathVariable Long id) {
        log.debug("REST request to delete Alerta : {}", id);
        alertaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
