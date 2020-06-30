package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.TempestadeNivelService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.TempestadeNivelDTO;
import org.tempestade.nucleo.service.dto.TempestadeNivelCriteria;
import org.tempestade.nucleo.service.TempestadeNivelQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.TempestadeNivel}.
 */
@RestController
@RequestMapping("/api")
public class TempestadeNivelResource {

    private final Logger log = LoggerFactory.getLogger(TempestadeNivelResource.class);

    private static final String ENTITY_NAME = "tempestadeNivel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TempestadeNivelService tempestadeNivelService;

    private final TempestadeNivelQueryService tempestadeNivelQueryService;

    public TempestadeNivelResource(TempestadeNivelService tempestadeNivelService, TempestadeNivelQueryService tempestadeNivelQueryService) {
        this.tempestadeNivelService = tempestadeNivelService;
        this.tempestadeNivelQueryService = tempestadeNivelQueryService;
    }

    /**
     * {@code POST  /tempestade-nivels} : Create a new tempestadeNivel.
     *
     * @param tempestadeNivelDTO the tempestadeNivelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tempestadeNivelDTO, or with status {@code 400 (Bad Request)} if the tempestadeNivel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tempestade-nivels")
    public ResponseEntity<TempestadeNivelDTO> createTempestadeNivel(@Valid @RequestBody TempestadeNivelDTO tempestadeNivelDTO) throws URISyntaxException {
        log.debug("REST request to save TempestadeNivel : {}", tempestadeNivelDTO);
        if (tempestadeNivelDTO.getId() != null) {
            throw new BadRequestAlertException("A new tempestadeNivel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TempestadeNivelDTO result = tempestadeNivelService.save(tempestadeNivelDTO);
        return ResponseEntity.created(new URI("/api/tempestade-nivels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tempestade-nivels} : Updates an existing tempestadeNivel.
     *
     * @param tempestadeNivelDTO the tempestadeNivelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tempestadeNivelDTO,
     * or with status {@code 400 (Bad Request)} if the tempestadeNivelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tempestadeNivelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tempestade-nivels")
    public ResponseEntity<TempestadeNivelDTO> updateTempestadeNivel(@Valid @RequestBody TempestadeNivelDTO tempestadeNivelDTO) throws URISyntaxException {
        log.debug("REST request to update TempestadeNivel : {}", tempestadeNivelDTO);
        if (tempestadeNivelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TempestadeNivelDTO result = tempestadeNivelService.save(tempestadeNivelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tempestadeNivelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tempestade-nivels} : get all the tempestadeNivels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tempestadeNivels in body.
     */
    @GetMapping("/tempestade-nivels")
    public ResponseEntity<List<TempestadeNivelDTO>> getAllTempestadeNivels(TempestadeNivelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TempestadeNivels by criteria: {}", criteria);
        Page<TempestadeNivelDTO> page = tempestadeNivelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tempestade-nivels/count} : count all the tempestadeNivels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tempestade-nivels/count")
    public ResponseEntity<Long> countTempestadeNivels(TempestadeNivelCriteria criteria) {
        log.debug("REST request to count TempestadeNivels by criteria: {}", criteria);
        return ResponseEntity.ok().body(tempestadeNivelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tempestade-nivels/:id} : get the "id" tempestadeNivel.
     *
     * @param id the id of the tempestadeNivelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tempestadeNivelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tempestade-nivels/{id}")
    public ResponseEntity<TempestadeNivelDTO> getTempestadeNivel(@PathVariable Long id) {
        log.debug("REST request to get TempestadeNivel : {}", id);
        Optional<TempestadeNivelDTO> tempestadeNivelDTO = tempestadeNivelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tempestadeNivelDTO);
    }

    /**
     * {@code DELETE  /tempestade-nivels/:id} : delete the "id" tempestadeNivel.
     *
     * @param id the id of the tempestadeNivelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tempestade-nivels/{id}")
    public ResponseEntity<Void> deleteTempestadeNivel(@PathVariable Long id) {
        log.debug("REST request to delete TempestadeNivel : {}", id);
        tempestadeNivelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
