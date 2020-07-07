package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.CondicaoTempoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.CondicaoTempoDTO;
import org.tempestade.nucleo.service.dto.CondicaoTempoCriteria;
import org.tempestade.nucleo.service.CondicaoTempoQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.CondicaoTempo}.
 */
@RestController
@RequestMapping("/api")
public class CondicaoTempoResource {

    private final Logger log = LoggerFactory.getLogger(CondicaoTempoResource.class);

    private static final String ENTITY_NAME = "condicaoTempo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CondicaoTempoService condicaoTempoService;

    private final CondicaoTempoQueryService condicaoTempoQueryService;

    public CondicaoTempoResource(CondicaoTempoService condicaoTempoService, CondicaoTempoQueryService condicaoTempoQueryService) {
        this.condicaoTempoService = condicaoTempoService;
        this.condicaoTempoQueryService = condicaoTempoQueryService;
    }

    /**
     * {@code POST  /condicao-tempos} : Create a new condicaoTempo.
     *
     * @param condicaoTempoDTO the condicaoTempoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new condicaoTempoDTO, or with status {@code 400 (Bad Request)} if the condicaoTempo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/condicao-tempos")
    public ResponseEntity<CondicaoTempoDTO> createCondicaoTempo(@Valid @RequestBody CondicaoTempoDTO condicaoTempoDTO) throws URISyntaxException {
        log.debug("REST request to save CondicaoTempo : {}", condicaoTempoDTO);
        if (condicaoTempoDTO.getId() != null) {
            throw new BadRequestAlertException("A new condicaoTempo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CondicaoTempoDTO result = condicaoTempoService.save(condicaoTempoDTO);
        return ResponseEntity.created(new URI("/api/condicao-tempos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /condicao-tempos} : Updates an existing condicaoTempo.
     *
     * @param condicaoTempoDTO the condicaoTempoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated condicaoTempoDTO,
     * or with status {@code 400 (Bad Request)} if the condicaoTempoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the condicaoTempoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/condicao-tempos")
    public ResponseEntity<CondicaoTempoDTO> updateCondicaoTempo(@Valid @RequestBody CondicaoTempoDTO condicaoTempoDTO) throws URISyntaxException {
        log.debug("REST request to update CondicaoTempo : {}", condicaoTempoDTO);
        if (condicaoTempoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CondicaoTempoDTO result = condicaoTempoService.save(condicaoTempoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, condicaoTempoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /condicao-tempos} : get all the condicaoTempos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of condicaoTempos in body.
     */
    @GetMapping("/condicao-tempos")
    public ResponseEntity<List<CondicaoTempoDTO>> getAllCondicaoTempos(CondicaoTempoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CondicaoTempos by criteria: {}", criteria);
        Page<CondicaoTempoDTO> page = condicaoTempoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /condicao-tempos/count} : count all the condicaoTempos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/condicao-tempos/count")
    public ResponseEntity<Long> countCondicaoTempos(CondicaoTempoCriteria criteria) {
        log.debug("REST request to count CondicaoTempos by criteria: {}", criteria);
        return ResponseEntity.ok().body(condicaoTempoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /condicao-tempos/:id} : get the "id" condicaoTempo.
     *
     * @param id the id of the condicaoTempoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the condicaoTempoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/condicao-tempos/{id}")
    public ResponseEntity<CondicaoTempoDTO> getCondicaoTempo(@PathVariable Long id) {
        log.debug("REST request to get CondicaoTempo : {}", id);
        Optional<CondicaoTempoDTO> condicaoTempoDTO = condicaoTempoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(condicaoTempoDTO);
    }

    /**
     * {@code DELETE  /condicao-tempos/:id} : delete the "id" condicaoTempo.
     *
     * @param id the id of the condicaoTempoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/condicao-tempos/{id}")
    public ResponseEntity<Void> deleteCondicaoTempo(@PathVariable Long id) {
        log.debug("REST request to delete CondicaoTempo : {}", id);
        condicaoTempoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
