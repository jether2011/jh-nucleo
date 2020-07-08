package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.AlvoBloqueioService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.AlvoBloqueioDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.AlvoBloqueio}.
 */
@RestController
@RequestMapping("/api")
public class AlvoBloqueioResource {

    private final Logger log = LoggerFactory.getLogger(AlvoBloqueioResource.class);

    private static final String ENTITY_NAME = "alvoBloqueio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlvoBloqueioService alvoBloqueioService;

    public AlvoBloqueioResource(AlvoBloqueioService alvoBloqueioService) {
        this.alvoBloqueioService = alvoBloqueioService;
    }

    /**
     * {@code POST  /alvo-bloqueios} : Create a new alvoBloqueio.
     *
     * @param alvoBloqueioDTO the alvoBloqueioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alvoBloqueioDTO, or with status {@code 400 (Bad Request)} if the alvoBloqueio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alvo-bloqueios")
    public ResponseEntity<AlvoBloqueioDTO> createAlvoBloqueio(@Valid @RequestBody AlvoBloqueioDTO alvoBloqueioDTO) throws URISyntaxException {
        log.debug("REST request to save AlvoBloqueio : {}", alvoBloqueioDTO);
        if (alvoBloqueioDTO.getId() != null) {
            throw new BadRequestAlertException("A new alvoBloqueio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlvoBloqueioDTO result = alvoBloqueioService.save(alvoBloqueioDTO);
        return ResponseEntity.created(new URI("/api/alvo-bloqueios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alvo-bloqueios} : Updates an existing alvoBloqueio.
     *
     * @param alvoBloqueioDTO the alvoBloqueioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alvoBloqueioDTO,
     * or with status {@code 400 (Bad Request)} if the alvoBloqueioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alvoBloqueioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alvo-bloqueios")
    public ResponseEntity<AlvoBloqueioDTO> updateAlvoBloqueio(@Valid @RequestBody AlvoBloqueioDTO alvoBloqueioDTO) throws URISyntaxException {
        log.debug("REST request to update AlvoBloqueio : {}", alvoBloqueioDTO);
        if (alvoBloqueioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlvoBloqueioDTO result = alvoBloqueioService.save(alvoBloqueioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alvoBloqueioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alvo-bloqueios} : get all the alvoBloqueios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alvoBloqueios in body.
     */
    @GetMapping("/alvo-bloqueios")
    public ResponseEntity<List<AlvoBloqueioDTO>> getAllAlvoBloqueios(Pageable pageable) {
        log.debug("REST request to get a page of AlvoBloqueios");
        Page<AlvoBloqueioDTO> page = alvoBloqueioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alvo-bloqueios/:id} : get the "id" alvoBloqueio.
     *
     * @param id the id of the alvoBloqueioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alvoBloqueioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alvo-bloqueios/{id}")
    public ResponseEntity<AlvoBloqueioDTO> getAlvoBloqueio(@PathVariable Long id) {
        log.debug("REST request to get AlvoBloqueio : {}", id);
        Optional<AlvoBloqueioDTO> alvoBloqueioDTO = alvoBloqueioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alvoBloqueioDTO);
    }

    /**
     * {@code DELETE  /alvo-bloqueios/:id} : delete the "id" alvoBloqueio.
     *
     * @param id the id of the alvoBloqueioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alvo-bloqueios/{id}")
    public ResponseEntity<Void> deleteAlvoBloqueio(@PathVariable Long id) {
        log.debug("REST request to delete AlvoBloqueio : {}", id);
        alvoBloqueioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
