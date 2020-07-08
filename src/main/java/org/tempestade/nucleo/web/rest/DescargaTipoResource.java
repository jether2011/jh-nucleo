package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.DescargaTipoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.DescargaTipoDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.DescargaTipo}.
 */
@RestController
@RequestMapping("/api")
public class DescargaTipoResource {

    private final Logger log = LoggerFactory.getLogger(DescargaTipoResource.class);

    private static final String ENTITY_NAME = "descargaTipo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DescargaTipoService descargaTipoService;

    public DescargaTipoResource(DescargaTipoService descargaTipoService) {
        this.descargaTipoService = descargaTipoService;
    }

    /**
     * {@code POST  /descarga-tipos} : Create a new descargaTipo.
     *
     * @param descargaTipoDTO the descargaTipoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new descargaTipoDTO, or with status {@code 400 (Bad Request)} if the descargaTipo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/descarga-tipos")
    public ResponseEntity<DescargaTipoDTO> createDescargaTipo(@Valid @RequestBody DescargaTipoDTO descargaTipoDTO) throws URISyntaxException {
        log.debug("REST request to save DescargaTipo : {}", descargaTipoDTO);
        if (descargaTipoDTO.getId() != null) {
            throw new BadRequestAlertException("A new descargaTipo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DescargaTipoDTO result = descargaTipoService.save(descargaTipoDTO);
        return ResponseEntity.created(new URI("/api/descarga-tipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /descarga-tipos} : Updates an existing descargaTipo.
     *
     * @param descargaTipoDTO the descargaTipoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated descargaTipoDTO,
     * or with status {@code 400 (Bad Request)} if the descargaTipoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the descargaTipoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/descarga-tipos")
    public ResponseEntity<DescargaTipoDTO> updateDescargaTipo(@Valid @RequestBody DescargaTipoDTO descargaTipoDTO) throws URISyntaxException {
        log.debug("REST request to update DescargaTipo : {}", descargaTipoDTO);
        if (descargaTipoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DescargaTipoDTO result = descargaTipoService.save(descargaTipoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, descargaTipoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /descarga-tipos} : get all the descargaTipos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of descargaTipos in body.
     */
    @GetMapping("/descarga-tipos")
    public ResponseEntity<List<DescargaTipoDTO>> getAllDescargaTipos(Pageable pageable) {
        log.debug("REST request to get a page of DescargaTipos");
        Page<DescargaTipoDTO> page = descargaTipoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /descarga-tipos/:id} : get the "id" descargaTipo.
     *
     * @param id the id of the descargaTipoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the descargaTipoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/descarga-tipos/{id}")
    public ResponseEntity<DescargaTipoDTO> getDescargaTipo(@PathVariable Long id) {
        log.debug("REST request to get DescargaTipo : {}", id);
        Optional<DescargaTipoDTO> descargaTipoDTO = descargaTipoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(descargaTipoDTO);
    }

    /**
     * {@code DELETE  /descarga-tipos/:id} : delete the "id" descargaTipo.
     *
     * @param id the id of the descargaTipoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/descarga-tipos/{id}")
    public ResponseEntity<Void> deleteDescargaTipo(@PathVariable Long id) {
        log.debug("REST request to delete DescargaTipo : {}", id);
        descargaTipoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
