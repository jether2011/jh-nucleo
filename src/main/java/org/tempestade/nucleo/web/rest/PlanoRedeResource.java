package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.PlanoRedeService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.PlanoRedeDTO;
import org.tempestade.nucleo.service.dto.PlanoRedeCriteria;
import org.tempestade.nucleo.service.PlanoRedeQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.PlanoRede}.
 */
@RestController
@RequestMapping("/api")
public class PlanoRedeResource {

    private final Logger log = LoggerFactory.getLogger(PlanoRedeResource.class);

    private static final String ENTITY_NAME = "planoRede";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoRedeService planoRedeService;

    private final PlanoRedeQueryService planoRedeQueryService;

    public PlanoRedeResource(PlanoRedeService planoRedeService, PlanoRedeQueryService planoRedeQueryService) {
        this.planoRedeService = planoRedeService;
        this.planoRedeQueryService = planoRedeQueryService;
    }

    /**
     * {@code POST  /plano-redes} : Create a new planoRede.
     *
     * @param planoRedeDTO the planoRedeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoRedeDTO, or with status {@code 400 (Bad Request)} if the planoRede has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plano-redes")
    public ResponseEntity<PlanoRedeDTO> createPlanoRede(@Valid @RequestBody PlanoRedeDTO planoRedeDTO) throws URISyntaxException {
        log.debug("REST request to save PlanoRede : {}", planoRedeDTO);
        if (planoRedeDTO.getId() != null) {
            throw new BadRequestAlertException("A new planoRede cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanoRedeDTO result = planoRedeService.save(planoRedeDTO);
        return ResponseEntity.created(new URI("/api/plano-redes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plano-redes} : Updates an existing planoRede.
     *
     * @param planoRedeDTO the planoRedeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoRedeDTO,
     * or with status {@code 400 (Bad Request)} if the planoRedeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planoRedeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plano-redes")
    public ResponseEntity<PlanoRedeDTO> updatePlanoRede(@Valid @RequestBody PlanoRedeDTO planoRedeDTO) throws URISyntaxException {
        log.debug("REST request to update PlanoRede : {}", planoRedeDTO);
        if (planoRedeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanoRedeDTO result = planoRedeService.save(planoRedeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoRedeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plano-redes} : get all the planoRedes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoRedes in body.
     */
    @GetMapping("/plano-redes")
    public ResponseEntity<List<PlanoRedeDTO>> getAllPlanoRedes(PlanoRedeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PlanoRedes by criteria: {}", criteria);
        Page<PlanoRedeDTO> page = planoRedeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plano-redes/count} : count all the planoRedes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/plano-redes/count")
    public ResponseEntity<Long> countPlanoRedes(PlanoRedeCriteria criteria) {
        log.debug("REST request to count PlanoRedes by criteria: {}", criteria);
        return ResponseEntity.ok().body(planoRedeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /plano-redes/:id} : get the "id" planoRede.
     *
     * @param id the id of the planoRedeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planoRedeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plano-redes/{id}")
    public ResponseEntity<PlanoRedeDTO> getPlanoRede(@PathVariable Long id) {
        log.debug("REST request to get PlanoRede : {}", id);
        Optional<PlanoRedeDTO> planoRedeDTO = planoRedeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planoRedeDTO);
    }

    /**
     * {@code DELETE  /plano-redes/:id} : delete the "id" planoRede.
     *
     * @param id the id of the planoRedeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plano-redes/{id}")
    public ResponseEntity<Void> deletePlanoRede(@PathVariable Long id) {
        log.debug("REST request to delete PlanoRede : {}", id);
        planoRedeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
