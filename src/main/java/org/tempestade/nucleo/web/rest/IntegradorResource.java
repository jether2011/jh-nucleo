package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.IntegradorService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.IntegradorDTO;
import org.tempestade.nucleo.service.dto.IntegradorCriteria;
import org.tempestade.nucleo.service.IntegradorQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Integrador}.
 */
@RestController
@RequestMapping("/api")
public class IntegradorResource {

    private final Logger log = LoggerFactory.getLogger(IntegradorResource.class);

    private static final String ENTITY_NAME = "integrador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntegradorService integradorService;

    private final IntegradorQueryService integradorQueryService;

    public IntegradorResource(IntegradorService integradorService, IntegradorQueryService integradorQueryService) {
        this.integradorService = integradorService;
        this.integradorQueryService = integradorQueryService;
    }

    /**
     * {@code POST  /integradors} : Create a new integrador.
     *
     * @param integradorDTO the integradorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new integradorDTO, or with status {@code 400 (Bad Request)} if the integrador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/integradors")
    public ResponseEntity<IntegradorDTO> createIntegrador(@Valid @RequestBody IntegradorDTO integradorDTO) throws URISyntaxException {
        log.debug("REST request to save Integrador : {}", integradorDTO);
        if (integradorDTO.getId() != null) {
            throw new BadRequestAlertException("A new integrador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntegradorDTO result = integradorService.save(integradorDTO);
        return ResponseEntity.created(new URI("/api/integradors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /integradors} : Updates an existing integrador.
     *
     * @param integradorDTO the integradorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated integradorDTO,
     * or with status {@code 400 (Bad Request)} if the integradorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the integradorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/integradors")
    public ResponseEntity<IntegradorDTO> updateIntegrador(@Valid @RequestBody IntegradorDTO integradorDTO) throws URISyntaxException {
        log.debug("REST request to update Integrador : {}", integradorDTO);
        if (integradorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IntegradorDTO result = integradorService.save(integradorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, integradorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /integradors} : get all the integradors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of integradors in body.
     */
    @GetMapping("/integradors")
    public ResponseEntity<List<IntegradorDTO>> getAllIntegradors(IntegradorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Integradors by criteria: {}", criteria);
        Page<IntegradorDTO> page = integradorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /integradors/count} : count all the integradors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/integradors/count")
    public ResponseEntity<Long> countIntegradors(IntegradorCriteria criteria) {
        log.debug("REST request to count Integradors by criteria: {}", criteria);
        return ResponseEntity.ok().body(integradorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /integradors/:id} : get the "id" integrador.
     *
     * @param id the id of the integradorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the integradorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/integradors/{id}")
    public ResponseEntity<IntegradorDTO> getIntegrador(@PathVariable Long id) {
        log.debug("REST request to get Integrador : {}", id);
        Optional<IntegradorDTO> integradorDTO = integradorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(integradorDTO);
    }

    /**
     * {@code DELETE  /integradors/:id} : delete the "id" integrador.
     *
     * @param id the id of the integradorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/integradors/{id}")
    public ResponseEntity<Void> deleteIntegrador(@PathVariable Long id) {
        log.debug("REST request to delete Integrador : {}", id);
        integradorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
