package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.BoletimService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.BoletimDTO;
import org.tempestade.nucleo.service.dto.BoletimCriteria;
import org.tempestade.nucleo.service.BoletimQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Boletim}.
 */
@RestController
@RequestMapping("/api")
public class BoletimResource {

    private final Logger log = LoggerFactory.getLogger(BoletimResource.class);

    private static final String ENTITY_NAME = "boletim";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoletimService boletimService;

    private final BoletimQueryService boletimQueryService;

    public BoletimResource(BoletimService boletimService, BoletimQueryService boletimQueryService) {
        this.boletimService = boletimService;
        this.boletimQueryService = boletimQueryService;
    }

    /**
     * {@code POST  /boletims} : Create a new boletim.
     *
     * @param boletimDTO the boletimDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boletimDTO, or with status {@code 400 (Bad Request)} if the boletim has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/boletims")
    public ResponseEntity<BoletimDTO> createBoletim(@Valid @RequestBody BoletimDTO boletimDTO) throws URISyntaxException {
        log.debug("REST request to save Boletim : {}", boletimDTO);
        if (boletimDTO.getId() != null) {
            throw new BadRequestAlertException("A new boletim cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BoletimDTO result = boletimService.save(boletimDTO);
        return ResponseEntity.created(new URI("/api/boletims/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /boletims} : Updates an existing boletim.
     *
     * @param boletimDTO the boletimDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boletimDTO,
     * or with status {@code 400 (Bad Request)} if the boletimDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the boletimDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/boletims")
    public ResponseEntity<BoletimDTO> updateBoletim(@Valid @RequestBody BoletimDTO boletimDTO) throws URISyntaxException {
        log.debug("REST request to update Boletim : {}", boletimDTO);
        if (boletimDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BoletimDTO result = boletimService.save(boletimDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, boletimDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /boletims} : get all the boletims.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of boletims in body.
     */
    @GetMapping("/boletims")
    public ResponseEntity<List<BoletimDTO>> getAllBoletims(BoletimCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Boletims by criteria: {}", criteria);
        Page<BoletimDTO> page = boletimQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /boletims/count} : count all the boletims.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/boletims/count")
    public ResponseEntity<Long> countBoletims(BoletimCriteria criteria) {
        log.debug("REST request to count Boletims by criteria: {}", criteria);
        return ResponseEntity.ok().body(boletimQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /boletims/:id} : get the "id" boletim.
     *
     * @param id the id of the boletimDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boletimDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/boletims/{id}")
    public ResponseEntity<BoletimDTO> getBoletim(@PathVariable Long id) {
        log.debug("REST request to get Boletim : {}", id);
        Optional<BoletimDTO> boletimDTO = boletimService.findOne(id);
        return ResponseUtil.wrapOrNotFound(boletimDTO);
    }

    /**
     * {@code DELETE  /boletims/:id} : delete the "id" boletim.
     *
     * @param id the id of the boletimDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/boletims/{id}")
    public ResponseEntity<Void> deleteBoletim(@PathVariable Long id) {
        log.debug("REST request to delete Boletim : {}", id);
        boletimService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
