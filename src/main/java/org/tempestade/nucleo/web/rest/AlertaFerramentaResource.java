package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.AlertaFerramentaService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.AlertaFerramentaDTO;
import org.tempestade.nucleo.service.dto.AlertaFerramentaCriteria;
import org.tempestade.nucleo.service.AlertaFerramentaQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.AlertaFerramenta}.
 */
@RestController
@RequestMapping("/api")
public class AlertaFerramentaResource {

    private final Logger log = LoggerFactory.getLogger(AlertaFerramentaResource.class);

    private static final String ENTITY_NAME = "alertaFerramenta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlertaFerramentaService alertaFerramentaService;

    private final AlertaFerramentaQueryService alertaFerramentaQueryService;

    public AlertaFerramentaResource(AlertaFerramentaService alertaFerramentaService, AlertaFerramentaQueryService alertaFerramentaQueryService) {
        this.alertaFerramentaService = alertaFerramentaService;
        this.alertaFerramentaQueryService = alertaFerramentaQueryService;
    }

    /**
     * {@code POST  /alerta-ferramentas} : Create a new alertaFerramenta.
     *
     * @param alertaFerramentaDTO the alertaFerramentaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alertaFerramentaDTO, or with status {@code 400 (Bad Request)} if the alertaFerramenta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alerta-ferramentas")
    public ResponseEntity<AlertaFerramentaDTO> createAlertaFerramenta(@Valid @RequestBody AlertaFerramentaDTO alertaFerramentaDTO) throws URISyntaxException {
        log.debug("REST request to save AlertaFerramenta : {}", alertaFerramentaDTO);
        if (alertaFerramentaDTO.getId() != null) {
            throw new BadRequestAlertException("A new alertaFerramenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlertaFerramentaDTO result = alertaFerramentaService.save(alertaFerramentaDTO);
        return ResponseEntity.created(new URI("/api/alerta-ferramentas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alerta-ferramentas} : Updates an existing alertaFerramenta.
     *
     * @param alertaFerramentaDTO the alertaFerramentaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alertaFerramentaDTO,
     * or with status {@code 400 (Bad Request)} if the alertaFerramentaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alertaFerramentaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alerta-ferramentas")
    public ResponseEntity<AlertaFerramentaDTO> updateAlertaFerramenta(@Valid @RequestBody AlertaFerramentaDTO alertaFerramentaDTO) throws URISyntaxException {
        log.debug("REST request to update AlertaFerramenta : {}", alertaFerramentaDTO);
        if (alertaFerramentaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlertaFerramentaDTO result = alertaFerramentaService.save(alertaFerramentaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alertaFerramentaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alerta-ferramentas} : get all the alertaFerramentas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alertaFerramentas in body.
     */
    @GetMapping("/alerta-ferramentas")
    public ResponseEntity<List<AlertaFerramentaDTO>> getAllAlertaFerramentas(AlertaFerramentaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AlertaFerramentas by criteria: {}", criteria);
        Page<AlertaFerramentaDTO> page = alertaFerramentaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alerta-ferramentas/count} : count all the alertaFerramentas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/alerta-ferramentas/count")
    public ResponseEntity<Long> countAlertaFerramentas(AlertaFerramentaCriteria criteria) {
        log.debug("REST request to count AlertaFerramentas by criteria: {}", criteria);
        return ResponseEntity.ok().body(alertaFerramentaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /alerta-ferramentas/:id} : get the "id" alertaFerramenta.
     *
     * @param id the id of the alertaFerramentaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alertaFerramentaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alerta-ferramentas/{id}")
    public ResponseEntity<AlertaFerramentaDTO> getAlertaFerramenta(@PathVariable Long id) {
        log.debug("REST request to get AlertaFerramenta : {}", id);
        Optional<AlertaFerramentaDTO> alertaFerramentaDTO = alertaFerramentaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alertaFerramentaDTO);
    }

    /**
     * {@code DELETE  /alerta-ferramentas/:id} : delete the "id" alertaFerramenta.
     *
     * @param id the id of the alertaFerramentaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alerta-ferramentas/{id}")
    public ResponseEntity<Void> deleteAlertaFerramenta(@PathVariable Long id) {
        log.debug("REST request to delete AlertaFerramenta : {}", id);
        alertaFerramentaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
