package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.PlanoLayerService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.PlanoLayerDTO;
import org.tempestade.nucleo.service.dto.PlanoLayerCriteria;
import org.tempestade.nucleo.service.PlanoLayerQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.PlanoLayer}.
 */
@RestController
@RequestMapping("/api")
public class PlanoLayerResource {

    private final Logger log = LoggerFactory.getLogger(PlanoLayerResource.class);

    private static final String ENTITY_NAME = "planoLayer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoLayerService planoLayerService;

    private final PlanoLayerQueryService planoLayerQueryService;

    public PlanoLayerResource(PlanoLayerService planoLayerService, PlanoLayerQueryService planoLayerQueryService) {
        this.planoLayerService = planoLayerService;
        this.planoLayerQueryService = planoLayerQueryService;
    }

    /**
     * {@code POST  /plano-layers} : Create a new planoLayer.
     *
     * @param planoLayerDTO the planoLayerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoLayerDTO, or with status {@code 400 (Bad Request)} if the planoLayer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plano-layers")
    public ResponseEntity<PlanoLayerDTO> createPlanoLayer(@Valid @RequestBody PlanoLayerDTO planoLayerDTO) throws URISyntaxException {
        log.debug("REST request to save PlanoLayer : {}", planoLayerDTO);
        if (planoLayerDTO.getId() != null) {
            throw new BadRequestAlertException("A new planoLayer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanoLayerDTO result = planoLayerService.save(planoLayerDTO);
        return ResponseEntity.created(new URI("/api/plano-layers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plano-layers} : Updates an existing planoLayer.
     *
     * @param planoLayerDTO the planoLayerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoLayerDTO,
     * or with status {@code 400 (Bad Request)} if the planoLayerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planoLayerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plano-layers")
    public ResponseEntity<PlanoLayerDTO> updatePlanoLayer(@Valid @RequestBody PlanoLayerDTO planoLayerDTO) throws URISyntaxException {
        log.debug("REST request to update PlanoLayer : {}", planoLayerDTO);
        if (planoLayerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanoLayerDTO result = planoLayerService.save(planoLayerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoLayerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plano-layers} : get all the planoLayers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoLayers in body.
     */
    @GetMapping("/plano-layers")
    public ResponseEntity<List<PlanoLayerDTO>> getAllPlanoLayers(PlanoLayerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PlanoLayers by criteria: {}", criteria);
        Page<PlanoLayerDTO> page = planoLayerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plano-layers/count} : count all the planoLayers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/plano-layers/count")
    public ResponseEntity<Long> countPlanoLayers(PlanoLayerCriteria criteria) {
        log.debug("REST request to count PlanoLayers by criteria: {}", criteria);
        return ResponseEntity.ok().body(planoLayerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /plano-layers/:id} : get the "id" planoLayer.
     *
     * @param id the id of the planoLayerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planoLayerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plano-layers/{id}")
    public ResponseEntity<PlanoLayerDTO> getPlanoLayer(@PathVariable Long id) {
        log.debug("REST request to get PlanoLayer : {}", id);
        Optional<PlanoLayerDTO> planoLayerDTO = planoLayerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planoLayerDTO);
    }

    /**
     * {@code DELETE  /plano-layers/:id} : delete the "id" planoLayer.
     *
     * @param id the id of the planoLayerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plano-layers/{id}")
    public ResponseEntity<Void> deletePlanoLayer(@PathVariable Long id) {
        log.debug("REST request to delete PlanoLayer : {}", id);
        planoLayerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
