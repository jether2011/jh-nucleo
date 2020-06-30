package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.AvisoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.AvisoDTO;
import org.tempestade.nucleo.service.dto.AvisoCriteria;
import org.tempestade.nucleo.service.AvisoQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Aviso}.
 */
@RestController
@RequestMapping("/api")
public class AvisoResource {

    private final Logger log = LoggerFactory.getLogger(AvisoResource.class);

    private static final String ENTITY_NAME = "aviso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvisoService avisoService;

    private final AvisoQueryService avisoQueryService;

    public AvisoResource(AvisoService avisoService, AvisoQueryService avisoQueryService) {
        this.avisoService = avisoService;
        this.avisoQueryService = avisoQueryService;
    }

    /**
     * {@code POST  /avisos} : Create a new aviso.
     *
     * @param avisoDTO the avisoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avisoDTO, or with status {@code 400 (Bad Request)} if the aviso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avisos")
    public ResponseEntity<AvisoDTO> createAviso(@Valid @RequestBody AvisoDTO avisoDTO) throws URISyntaxException {
        log.debug("REST request to save Aviso : {}", avisoDTO);
        if (avisoDTO.getId() != null) {
            throw new BadRequestAlertException("A new aviso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvisoDTO result = avisoService.save(avisoDTO);
        return ResponseEntity.created(new URI("/api/avisos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avisos} : Updates an existing aviso.
     *
     * @param avisoDTO the avisoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avisoDTO,
     * or with status {@code 400 (Bad Request)} if the avisoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avisoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avisos")
    public ResponseEntity<AvisoDTO> updateAviso(@Valid @RequestBody AvisoDTO avisoDTO) throws URISyntaxException {
        log.debug("REST request to update Aviso : {}", avisoDTO);
        if (avisoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AvisoDTO result = avisoService.save(avisoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avisoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /avisos} : get all the avisos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avisos in body.
     */
    @GetMapping("/avisos")
    public ResponseEntity<List<AvisoDTO>> getAllAvisos(AvisoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Avisos by criteria: {}", criteria);
        Page<AvisoDTO> page = avisoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /avisos/count} : count all the avisos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/avisos/count")
    public ResponseEntity<Long> countAvisos(AvisoCriteria criteria) {
        log.debug("REST request to count Avisos by criteria: {}", criteria);
        return ResponseEntity.ok().body(avisoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /avisos/:id} : get the "id" aviso.
     *
     * @param id the id of the avisoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avisoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avisos/{id}")
    public ResponseEntity<AvisoDTO> getAviso(@PathVariable Long id) {
        log.debug("REST request to get Aviso : {}", id);
        Optional<AvisoDTO> avisoDTO = avisoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avisoDTO);
    }

    /**
     * {@code DELETE  /avisos/:id} : delete the "id" aviso.
     *
     * @param id the id of the avisoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avisos/{id}")
    public ResponseEntity<Void> deleteAviso(@PathVariable Long id) {
        log.debug("REST request to delete Aviso : {}", id);
        avisoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
