package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.LayerService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.LayerDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Layer}.
 */
@RestController
@RequestMapping("/api")
public class LayerResource {

    private final Logger log = LoggerFactory.getLogger(LayerResource.class);

    private static final String ENTITY_NAME = "layer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LayerService layerService;

    public LayerResource(LayerService layerService) {
        this.layerService = layerService;
    }

    /**
     * {@code POST  /layers} : Create a new layer.
     *
     * @param layerDTO the layerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new layerDTO, or with status {@code 400 (Bad Request)} if the layer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/layers")
    public ResponseEntity<LayerDTO> createLayer(@Valid @RequestBody LayerDTO layerDTO) throws URISyntaxException {
        log.debug("REST request to save Layer : {}", layerDTO);
        if (layerDTO.getId() != null) {
            throw new BadRequestAlertException("A new layer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LayerDTO result = layerService.save(layerDTO);
        return ResponseEntity.created(new URI("/api/layers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /layers} : Updates an existing layer.
     *
     * @param layerDTO the layerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated layerDTO,
     * or with status {@code 400 (Bad Request)} if the layerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the layerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/layers")
    public ResponseEntity<LayerDTO> updateLayer(@Valid @RequestBody LayerDTO layerDTO) throws URISyntaxException {
        log.debug("REST request to update Layer : {}", layerDTO);
        if (layerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LayerDTO result = layerService.save(layerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, layerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /layers} : get all the layers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of layers in body.
     */
    @GetMapping("/layers")
    public ResponseEntity<List<LayerDTO>> getAllLayers(Pageable pageable) {
        log.debug("REST request to get a page of Layers");
        Page<LayerDTO> page = layerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /layers/:id} : get the "id" layer.
     *
     * @param id the id of the layerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the layerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/layers/{id}")
    public ResponseEntity<LayerDTO> getLayer(@PathVariable Long id) {
        log.debug("REST request to get Layer : {}", id);
        Optional<LayerDTO> layerDTO = layerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(layerDTO);
    }

    /**
     * {@code DELETE  /layers/:id} : delete the "id" layer.
     *
     * @param id the id of the layerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/layers/{id}")
    public ResponseEntity<Void> deleteLayer(@PathVariable Long id) {
        log.debug("REST request to delete Layer : {}", id);
        layerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
