package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.BoletimPrevObsService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.BoletimPrevObsDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.BoletimPrevObs}.
 */
@RestController
@RequestMapping("/api")
public class BoletimPrevObsResource {

    private final Logger log = LoggerFactory.getLogger(BoletimPrevObsResource.class);

    private static final String ENTITY_NAME = "boletimPrevObs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoletimPrevObsService boletimPrevObsService;

    public BoletimPrevObsResource(BoletimPrevObsService boletimPrevObsService) {
        this.boletimPrevObsService = boletimPrevObsService;
    }

    /**
     * {@code POST  /boletim-prev-obs} : Create a new boletimPrevObs.
     *
     * @param boletimPrevObsDTO the boletimPrevObsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boletimPrevObsDTO, or with status {@code 400 (Bad Request)} if the boletimPrevObs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/boletim-prev-obs")
    public ResponseEntity<BoletimPrevObsDTO> createBoletimPrevObs(@Valid @RequestBody BoletimPrevObsDTO boletimPrevObsDTO) throws URISyntaxException {
        log.debug("REST request to save BoletimPrevObs : {}", boletimPrevObsDTO);
        if (boletimPrevObsDTO.getId() != null) {
            throw new BadRequestAlertException("A new boletimPrevObs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BoletimPrevObsDTO result = boletimPrevObsService.save(boletimPrevObsDTO);
        return ResponseEntity.created(new URI("/api/boletim-prev-obs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /boletim-prev-obs} : Updates an existing boletimPrevObs.
     *
     * @param boletimPrevObsDTO the boletimPrevObsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boletimPrevObsDTO,
     * or with status {@code 400 (Bad Request)} if the boletimPrevObsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the boletimPrevObsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/boletim-prev-obs")
    public ResponseEntity<BoletimPrevObsDTO> updateBoletimPrevObs(@Valid @RequestBody BoletimPrevObsDTO boletimPrevObsDTO) throws URISyntaxException {
        log.debug("REST request to update BoletimPrevObs : {}", boletimPrevObsDTO);
        if (boletimPrevObsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BoletimPrevObsDTO result = boletimPrevObsService.save(boletimPrevObsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, boletimPrevObsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /boletim-prev-obs} : get all the boletimPrevObs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of boletimPrevObs in body.
     */
    @GetMapping("/boletim-prev-obs")
    public ResponseEntity<List<BoletimPrevObsDTO>> getAllBoletimPrevObs(Pageable pageable) {
        log.debug("REST request to get a page of BoletimPrevObs");
        Page<BoletimPrevObsDTO> page = boletimPrevObsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /boletim-prev-obs/:id} : get the "id" boletimPrevObs.
     *
     * @param id the id of the boletimPrevObsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boletimPrevObsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/boletim-prev-obs/{id}")
    public ResponseEntity<BoletimPrevObsDTO> getBoletimPrevObs(@PathVariable Long id) {
        log.debug("REST request to get BoletimPrevObs : {}", id);
        Optional<BoletimPrevObsDTO> boletimPrevObsDTO = boletimPrevObsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(boletimPrevObsDTO);
    }

    /**
     * {@code DELETE  /boletim-prev-obs/:id} : delete the "id" boletimPrevObs.
     *
     * @param id the id of the boletimPrevObsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/boletim-prev-obs/{id}")
    public ResponseEntity<Void> deleteBoletimPrevObs(@PathVariable Long id) {
        log.debug("REST request to delete BoletimPrevObs : {}", id);
        boletimPrevObsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
