package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.PlanoStatusService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.PlanoStatusDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.PlanoStatus}.
 */
@RestController
@RequestMapping("/api")
public class PlanoStatusResource {

    private final Logger log = LoggerFactory.getLogger(PlanoStatusResource.class);

    private static final String ENTITY_NAME = "planoStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoStatusService planoStatusService;

    public PlanoStatusResource(PlanoStatusService planoStatusService) {
        this.planoStatusService = planoStatusService;
    }

    /**
     * {@code POST  /plano-statuses} : Create a new planoStatus.
     *
     * @param planoStatusDTO the planoStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoStatusDTO, or with status {@code 400 (Bad Request)} if the planoStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plano-statuses")
    public ResponseEntity<PlanoStatusDTO> createPlanoStatus(@Valid @RequestBody PlanoStatusDTO planoStatusDTO) throws URISyntaxException {
        log.debug("REST request to save PlanoStatus : {}", planoStatusDTO);
        if (planoStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new planoStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanoStatusDTO result = planoStatusService.save(planoStatusDTO);
        return ResponseEntity.created(new URI("/api/plano-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plano-statuses} : Updates an existing planoStatus.
     *
     * @param planoStatusDTO the planoStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoStatusDTO,
     * or with status {@code 400 (Bad Request)} if the planoStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planoStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plano-statuses")
    public ResponseEntity<PlanoStatusDTO> updatePlanoStatus(@Valid @RequestBody PlanoStatusDTO planoStatusDTO) throws URISyntaxException {
        log.debug("REST request to update PlanoStatus : {}", planoStatusDTO);
        if (planoStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanoStatusDTO result = planoStatusService.save(planoStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plano-statuses} : get all the planoStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoStatuses in body.
     */
    @GetMapping("/plano-statuses")
    public ResponseEntity<List<PlanoStatusDTO>> getAllPlanoStatuses(Pageable pageable) {
        log.debug("REST request to get a page of PlanoStatuses");
        Page<PlanoStatusDTO> page = planoStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plano-statuses/:id} : get the "id" planoStatus.
     *
     * @param id the id of the planoStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planoStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plano-statuses/{id}")
    public ResponseEntity<PlanoStatusDTO> getPlanoStatus(@PathVariable Long id) {
        log.debug("REST request to get PlanoStatus : {}", id);
        Optional<PlanoStatusDTO> planoStatusDTO = planoStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planoStatusDTO);
    }

    /**
     * {@code DELETE  /plano-statuses/:id} : delete the "id" planoStatus.
     *
     * @param id the id of the planoStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plano-statuses/{id}")
    public ResponseEntity<Void> deletePlanoStatus(@PathVariable Long id) {
        log.debug("REST request to delete PlanoStatus : {}", id);
        planoStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
