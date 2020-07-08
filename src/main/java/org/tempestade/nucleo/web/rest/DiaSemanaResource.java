package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.DiaSemanaService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.DiaSemanaDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.DiaSemana}.
 */
@RestController
@RequestMapping("/api")
public class DiaSemanaResource {

    private final Logger log = LoggerFactory.getLogger(DiaSemanaResource.class);

    private static final String ENTITY_NAME = "diaSemana";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiaSemanaService diaSemanaService;

    public DiaSemanaResource(DiaSemanaService diaSemanaService) {
        this.diaSemanaService = diaSemanaService;
    }

    /**
     * {@code POST  /dia-semanas} : Create a new diaSemana.
     *
     * @param diaSemanaDTO the diaSemanaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diaSemanaDTO, or with status {@code 400 (Bad Request)} if the diaSemana has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dia-semanas")
    public ResponseEntity<DiaSemanaDTO> createDiaSemana(@Valid @RequestBody DiaSemanaDTO diaSemanaDTO) throws URISyntaxException {
        log.debug("REST request to save DiaSemana : {}", diaSemanaDTO);
        if (diaSemanaDTO.getId() != null) {
            throw new BadRequestAlertException("A new diaSemana cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiaSemanaDTO result = diaSemanaService.save(diaSemanaDTO);
        return ResponseEntity.created(new URI("/api/dia-semanas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dia-semanas} : Updates an existing diaSemana.
     *
     * @param diaSemanaDTO the diaSemanaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diaSemanaDTO,
     * or with status {@code 400 (Bad Request)} if the diaSemanaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diaSemanaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dia-semanas")
    public ResponseEntity<DiaSemanaDTO> updateDiaSemana(@Valid @RequestBody DiaSemanaDTO diaSemanaDTO) throws URISyntaxException {
        log.debug("REST request to update DiaSemana : {}", diaSemanaDTO);
        if (diaSemanaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiaSemanaDTO result = diaSemanaService.save(diaSemanaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diaSemanaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dia-semanas} : get all the diaSemanas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diaSemanas in body.
     */
    @GetMapping("/dia-semanas")
    public ResponseEntity<List<DiaSemanaDTO>> getAllDiaSemanas(Pageable pageable) {
        log.debug("REST request to get a page of DiaSemanas");
        Page<DiaSemanaDTO> page = diaSemanaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dia-semanas/:id} : get the "id" diaSemana.
     *
     * @param id the id of the diaSemanaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diaSemanaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dia-semanas/{id}")
    public ResponseEntity<DiaSemanaDTO> getDiaSemana(@PathVariable Long id) {
        log.debug("REST request to get DiaSemana : {}", id);
        Optional<DiaSemanaDTO> diaSemanaDTO = diaSemanaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diaSemanaDTO);
    }

    /**
     * {@code DELETE  /dia-semanas/:id} : delete the "id" diaSemana.
     *
     * @param id the id of the diaSemanaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dia-semanas/{id}")
    public ResponseEntity<Void> deleteDiaSemana(@PathVariable Long id) {
        log.debug("REST request to delete DiaSemana : {}", id);
        diaSemanaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
