package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.PontosCardeaisService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.PontosCardeaisDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.PontosCardeais}.
 */
@RestController
@RequestMapping("/api")
public class PontosCardeaisResource {

    private final Logger log = LoggerFactory.getLogger(PontosCardeaisResource.class);

    private static final String ENTITY_NAME = "pontosCardeais";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PontosCardeaisService pontosCardeaisService;

    public PontosCardeaisResource(PontosCardeaisService pontosCardeaisService) {
        this.pontosCardeaisService = pontosCardeaisService;
    }

    /**
     * {@code POST  /pontos-cardeais} : Create a new pontosCardeais.
     *
     * @param pontosCardeaisDTO the pontosCardeaisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pontosCardeaisDTO, or with status {@code 400 (Bad Request)} if the pontosCardeais has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pontos-cardeais")
    public ResponseEntity<PontosCardeaisDTO> createPontosCardeais(@Valid @RequestBody PontosCardeaisDTO pontosCardeaisDTO) throws URISyntaxException {
        log.debug("REST request to save PontosCardeais : {}", pontosCardeaisDTO);
        if (pontosCardeaisDTO.getId() != null) {
            throw new BadRequestAlertException("A new pontosCardeais cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PontosCardeaisDTO result = pontosCardeaisService.save(pontosCardeaisDTO);
        return ResponseEntity.created(new URI("/api/pontos-cardeais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pontos-cardeais} : Updates an existing pontosCardeais.
     *
     * @param pontosCardeaisDTO the pontosCardeaisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pontosCardeaisDTO,
     * or with status {@code 400 (Bad Request)} if the pontosCardeaisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pontosCardeaisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pontos-cardeais")
    public ResponseEntity<PontosCardeaisDTO> updatePontosCardeais(@Valid @RequestBody PontosCardeaisDTO pontosCardeaisDTO) throws URISyntaxException {
        log.debug("REST request to update PontosCardeais : {}", pontosCardeaisDTO);
        if (pontosCardeaisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PontosCardeaisDTO result = pontosCardeaisService.save(pontosCardeaisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pontosCardeaisDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pontos-cardeais} : get all the pontosCardeais.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pontosCardeais in body.
     */
    @GetMapping("/pontos-cardeais")
    public ResponseEntity<List<PontosCardeaisDTO>> getAllPontosCardeais(Pageable pageable) {
        log.debug("REST request to get a page of PontosCardeais");
        Page<PontosCardeaisDTO> page = pontosCardeaisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pontos-cardeais/:id} : get the "id" pontosCardeais.
     *
     * @param id the id of the pontosCardeaisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pontosCardeaisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pontos-cardeais/{id}")
    public ResponseEntity<PontosCardeaisDTO> getPontosCardeais(@PathVariable Long id) {
        log.debug("REST request to get PontosCardeais : {}", id);
        Optional<PontosCardeaisDTO> pontosCardeaisDTO = pontosCardeaisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pontosCardeaisDTO);
    }

    /**
     * {@code DELETE  /pontos-cardeais/:id} : delete the "id" pontosCardeais.
     *
     * @param id the id of the pontosCardeaisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pontos-cardeais/{id}")
    public ResponseEntity<Void> deletePontosCardeais(@PathVariable Long id) {
        log.debug("REST request to delete PontosCardeais : {}", id);
        pontosCardeaisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
