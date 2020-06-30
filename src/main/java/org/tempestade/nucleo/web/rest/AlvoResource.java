package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.AlvoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.AlvoDTO;
import org.tempestade.nucleo.service.dto.AlvoCriteria;
import org.tempestade.nucleo.service.AlvoQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Alvo}.
 */
@RestController
@RequestMapping("/api")
public class AlvoResource {

    private final Logger log = LoggerFactory.getLogger(AlvoResource.class);

    private static final String ENTITY_NAME = "alvo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlvoService alvoService;

    private final AlvoQueryService alvoQueryService;

    public AlvoResource(AlvoService alvoService, AlvoQueryService alvoQueryService) {
        this.alvoService = alvoService;
        this.alvoQueryService = alvoQueryService;
    }

    /**
     * {@code POST  /alvos} : Create a new alvo.
     *
     * @param alvoDTO the alvoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alvoDTO, or with status {@code 400 (Bad Request)} if the alvo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alvos")
    public ResponseEntity<AlvoDTO> createAlvo(@Valid @RequestBody AlvoDTO alvoDTO) throws URISyntaxException {
        log.debug("REST request to save Alvo : {}", alvoDTO);
        if (alvoDTO.getId() != null) {
            throw new BadRequestAlertException("A new alvo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlvoDTO result = alvoService.save(alvoDTO);
        return ResponseEntity.created(new URI("/api/alvos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alvos} : Updates an existing alvo.
     *
     * @param alvoDTO the alvoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alvoDTO,
     * or with status {@code 400 (Bad Request)} if the alvoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alvoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alvos")
    public ResponseEntity<AlvoDTO> updateAlvo(@Valid @RequestBody AlvoDTO alvoDTO) throws URISyntaxException {
        log.debug("REST request to update Alvo : {}", alvoDTO);
        if (alvoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlvoDTO result = alvoService.save(alvoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alvoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alvos} : get all the alvos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alvos in body.
     */
    @GetMapping("/alvos")
    public ResponseEntity<List<AlvoDTO>> getAllAlvos(AlvoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Alvos by criteria: {}", criteria);
        Page<AlvoDTO> page = alvoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alvos/count} : count all the alvos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/alvos/count")
    public ResponseEntity<Long> countAlvos(AlvoCriteria criteria) {
        log.debug("REST request to count Alvos by criteria: {}", criteria);
        return ResponseEntity.ok().body(alvoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /alvos/:id} : get the "id" alvo.
     *
     * @param id the id of the alvoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alvoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alvos/{id}")
    public ResponseEntity<AlvoDTO> getAlvo(@PathVariable Long id) {
        log.debug("REST request to get Alvo : {}", id);
        Optional<AlvoDTO> alvoDTO = alvoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alvoDTO);
    }

    /**
     * {@code DELETE  /alvos/:id} : delete the "id" alvo.
     *
     * @param id the id of the alvoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alvos/{id}")
    public ResponseEntity<Void> deleteAlvo(@PathVariable Long id) {
        log.debug("REST request to delete Alvo : {}", id);
        alvoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
