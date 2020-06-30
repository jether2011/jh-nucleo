package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.IntensidadeChuvaService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.IntensidadeChuvaDTO;
import org.tempestade.nucleo.service.dto.IntensidadeChuvaCriteria;
import org.tempestade.nucleo.service.IntensidadeChuvaQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.IntensidadeChuva}.
 */
@RestController
@RequestMapping("/api")
public class IntensidadeChuvaResource {

    private final Logger log = LoggerFactory.getLogger(IntensidadeChuvaResource.class);

    private static final String ENTITY_NAME = "intensidadeChuva";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntensidadeChuvaService intensidadeChuvaService;

    private final IntensidadeChuvaQueryService intensidadeChuvaQueryService;

    public IntensidadeChuvaResource(IntensidadeChuvaService intensidadeChuvaService, IntensidadeChuvaQueryService intensidadeChuvaQueryService) {
        this.intensidadeChuvaService = intensidadeChuvaService;
        this.intensidadeChuvaQueryService = intensidadeChuvaQueryService;
    }

    /**
     * {@code POST  /intensidade-chuvas} : Create a new intensidadeChuva.
     *
     * @param intensidadeChuvaDTO the intensidadeChuvaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new intensidadeChuvaDTO, or with status {@code 400 (Bad Request)} if the intensidadeChuva has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/intensidade-chuvas")
    public ResponseEntity<IntensidadeChuvaDTO> createIntensidadeChuva(@Valid @RequestBody IntensidadeChuvaDTO intensidadeChuvaDTO) throws URISyntaxException {
        log.debug("REST request to save IntensidadeChuva : {}", intensidadeChuvaDTO);
        if (intensidadeChuvaDTO.getId() != null) {
            throw new BadRequestAlertException("A new intensidadeChuva cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntensidadeChuvaDTO result = intensidadeChuvaService.save(intensidadeChuvaDTO);
        return ResponseEntity.created(new URI("/api/intensidade-chuvas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /intensidade-chuvas} : Updates an existing intensidadeChuva.
     *
     * @param intensidadeChuvaDTO the intensidadeChuvaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intensidadeChuvaDTO,
     * or with status {@code 400 (Bad Request)} if the intensidadeChuvaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the intensidadeChuvaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/intensidade-chuvas")
    public ResponseEntity<IntensidadeChuvaDTO> updateIntensidadeChuva(@Valid @RequestBody IntensidadeChuvaDTO intensidadeChuvaDTO) throws URISyntaxException {
        log.debug("REST request to update IntensidadeChuva : {}", intensidadeChuvaDTO);
        if (intensidadeChuvaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IntensidadeChuvaDTO result = intensidadeChuvaService.save(intensidadeChuvaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, intensidadeChuvaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /intensidade-chuvas} : get all the intensidadeChuvas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of intensidadeChuvas in body.
     */
    @GetMapping("/intensidade-chuvas")
    public ResponseEntity<List<IntensidadeChuvaDTO>> getAllIntensidadeChuvas(IntensidadeChuvaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get IntensidadeChuvas by criteria: {}", criteria);
        Page<IntensidadeChuvaDTO> page = intensidadeChuvaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /intensidade-chuvas/count} : count all the intensidadeChuvas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/intensidade-chuvas/count")
    public ResponseEntity<Long> countIntensidadeChuvas(IntensidadeChuvaCriteria criteria) {
        log.debug("REST request to count IntensidadeChuvas by criteria: {}", criteria);
        return ResponseEntity.ok().body(intensidadeChuvaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /intensidade-chuvas/:id} : get the "id" intensidadeChuva.
     *
     * @param id the id of the intensidadeChuvaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the intensidadeChuvaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/intensidade-chuvas/{id}")
    public ResponseEntity<IntensidadeChuvaDTO> getIntensidadeChuva(@PathVariable Long id) {
        log.debug("REST request to get IntensidadeChuva : {}", id);
        Optional<IntensidadeChuvaDTO> intensidadeChuvaDTO = intensidadeChuvaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(intensidadeChuvaDTO);
    }

    /**
     * {@code DELETE  /intensidade-chuvas/:id} : delete the "id" intensidadeChuva.
     *
     * @param id the id of the intensidadeChuvaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/intensidade-chuvas/{id}")
    public ResponseEntity<Void> deleteIntensidadeChuva(@PathVariable Long id) {
        log.debug("REST request to delete IntensidadeChuva : {}", id);
        intensidadeChuvaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
