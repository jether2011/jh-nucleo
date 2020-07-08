package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.DescargaUnidadeService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.DescargaUnidadeDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.DescargaUnidade}.
 */
@RestController
@RequestMapping("/api")
public class DescargaUnidadeResource {

    private final Logger log = LoggerFactory.getLogger(DescargaUnidadeResource.class);

    private static final String ENTITY_NAME = "descargaUnidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DescargaUnidadeService descargaUnidadeService;

    public DescargaUnidadeResource(DescargaUnidadeService descargaUnidadeService) {
        this.descargaUnidadeService = descargaUnidadeService;
    }

    /**
     * {@code POST  /descarga-unidades} : Create a new descargaUnidade.
     *
     * @param descargaUnidadeDTO the descargaUnidadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new descargaUnidadeDTO, or with status {@code 400 (Bad Request)} if the descargaUnidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/descarga-unidades")
    public ResponseEntity<DescargaUnidadeDTO> createDescargaUnidade(@Valid @RequestBody DescargaUnidadeDTO descargaUnidadeDTO) throws URISyntaxException {
        log.debug("REST request to save DescargaUnidade : {}", descargaUnidadeDTO);
        if (descargaUnidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new descargaUnidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DescargaUnidadeDTO result = descargaUnidadeService.save(descargaUnidadeDTO);
        return ResponseEntity.created(new URI("/api/descarga-unidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /descarga-unidades} : Updates an existing descargaUnidade.
     *
     * @param descargaUnidadeDTO the descargaUnidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated descargaUnidadeDTO,
     * or with status {@code 400 (Bad Request)} if the descargaUnidadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the descargaUnidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/descarga-unidades")
    public ResponseEntity<DescargaUnidadeDTO> updateDescargaUnidade(@Valid @RequestBody DescargaUnidadeDTO descargaUnidadeDTO) throws URISyntaxException {
        log.debug("REST request to update DescargaUnidade : {}", descargaUnidadeDTO);
        if (descargaUnidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DescargaUnidadeDTO result = descargaUnidadeService.save(descargaUnidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, descargaUnidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /descarga-unidades} : get all the descargaUnidades.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of descargaUnidades in body.
     */
    @GetMapping("/descarga-unidades")
    public ResponseEntity<List<DescargaUnidadeDTO>> getAllDescargaUnidades(Pageable pageable) {
        log.debug("REST request to get a page of DescargaUnidades");
        Page<DescargaUnidadeDTO> page = descargaUnidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /descarga-unidades/:id} : get the "id" descargaUnidade.
     *
     * @param id the id of the descargaUnidadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the descargaUnidadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/descarga-unidades/{id}")
    public ResponseEntity<DescargaUnidadeDTO> getDescargaUnidade(@PathVariable Long id) {
        log.debug("REST request to get DescargaUnidade : {}", id);
        Optional<DescargaUnidadeDTO> descargaUnidadeDTO = descargaUnidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(descargaUnidadeDTO);
    }

    /**
     * {@code DELETE  /descarga-unidades/:id} : delete the "id" descargaUnidade.
     *
     * @param id the id of the descargaUnidadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/descarga-unidades/{id}")
    public ResponseEntity<Void> deleteDescargaUnidade(@PathVariable Long id) {
        log.debug("REST request to delete DescargaUnidade : {}", id);
        descargaUnidadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
