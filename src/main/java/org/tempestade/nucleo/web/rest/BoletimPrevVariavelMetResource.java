package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.BoletimPrevVariavelMetService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.BoletimPrevVariavelMetDTO;
import org.tempestade.nucleo.service.dto.BoletimPrevVariavelMetCriteria;
import org.tempestade.nucleo.service.BoletimPrevVariavelMetQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.BoletimPrevVariavelMet}.
 */
@RestController
@RequestMapping("/api")
public class BoletimPrevVariavelMetResource {

    private final Logger log = LoggerFactory.getLogger(BoletimPrevVariavelMetResource.class);

    private static final String ENTITY_NAME = "boletimPrevVariavelMet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoletimPrevVariavelMetService boletimPrevVariavelMetService;

    private final BoletimPrevVariavelMetQueryService boletimPrevVariavelMetQueryService;

    public BoletimPrevVariavelMetResource(BoletimPrevVariavelMetService boletimPrevVariavelMetService, BoletimPrevVariavelMetQueryService boletimPrevVariavelMetQueryService) {
        this.boletimPrevVariavelMetService = boletimPrevVariavelMetService;
        this.boletimPrevVariavelMetQueryService = boletimPrevVariavelMetQueryService;
    }

    /**
     * {@code POST  /boletim-prev-variavel-mets} : Create a new boletimPrevVariavelMet.
     *
     * @param boletimPrevVariavelMetDTO the boletimPrevVariavelMetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boletimPrevVariavelMetDTO, or with status {@code 400 (Bad Request)} if the boletimPrevVariavelMet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/boletim-prev-variavel-mets")
    public ResponseEntity<BoletimPrevVariavelMetDTO> createBoletimPrevVariavelMet(@Valid @RequestBody BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO) throws URISyntaxException {
        log.debug("REST request to save BoletimPrevVariavelMet : {}", boletimPrevVariavelMetDTO);
        if (boletimPrevVariavelMetDTO.getId() != null) {
            throw new BadRequestAlertException("A new boletimPrevVariavelMet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BoletimPrevVariavelMetDTO result = boletimPrevVariavelMetService.save(boletimPrevVariavelMetDTO);
        return ResponseEntity.created(new URI("/api/boletim-prev-variavel-mets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /boletim-prev-variavel-mets} : Updates an existing boletimPrevVariavelMet.
     *
     * @param boletimPrevVariavelMetDTO the boletimPrevVariavelMetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boletimPrevVariavelMetDTO,
     * or with status {@code 400 (Bad Request)} if the boletimPrevVariavelMetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the boletimPrevVariavelMetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/boletim-prev-variavel-mets")
    public ResponseEntity<BoletimPrevVariavelMetDTO> updateBoletimPrevVariavelMet(@Valid @RequestBody BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO) throws URISyntaxException {
        log.debug("REST request to update BoletimPrevVariavelMet : {}", boletimPrevVariavelMetDTO);
        if (boletimPrevVariavelMetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BoletimPrevVariavelMetDTO result = boletimPrevVariavelMetService.save(boletimPrevVariavelMetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, boletimPrevVariavelMetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /boletim-prev-variavel-mets} : get all the boletimPrevVariavelMets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of boletimPrevVariavelMets in body.
     */
    @GetMapping("/boletim-prev-variavel-mets")
    public ResponseEntity<List<BoletimPrevVariavelMetDTO>> getAllBoletimPrevVariavelMets(BoletimPrevVariavelMetCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BoletimPrevVariavelMets by criteria: {}", criteria);
        Page<BoletimPrevVariavelMetDTO> page = boletimPrevVariavelMetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /boletim-prev-variavel-mets/count} : count all the boletimPrevVariavelMets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/boletim-prev-variavel-mets/count")
    public ResponseEntity<Long> countBoletimPrevVariavelMets(BoletimPrevVariavelMetCriteria criteria) {
        log.debug("REST request to count BoletimPrevVariavelMets by criteria: {}", criteria);
        return ResponseEntity.ok().body(boletimPrevVariavelMetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /boletim-prev-variavel-mets/:id} : get the "id" boletimPrevVariavelMet.
     *
     * @param id the id of the boletimPrevVariavelMetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boletimPrevVariavelMetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/boletim-prev-variavel-mets/{id}")
    public ResponseEntity<BoletimPrevVariavelMetDTO> getBoletimPrevVariavelMet(@PathVariable Long id) {
        log.debug("REST request to get BoletimPrevVariavelMet : {}", id);
        Optional<BoletimPrevVariavelMetDTO> boletimPrevVariavelMetDTO = boletimPrevVariavelMetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(boletimPrevVariavelMetDTO);
    }

    /**
     * {@code DELETE  /boletim-prev-variavel-mets/:id} : delete the "id" boletimPrevVariavelMet.
     *
     * @param id the id of the boletimPrevVariavelMetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/boletim-prev-variavel-mets/{id}")
    public ResponseEntity<Void> deleteBoletimPrevVariavelMet(@PathVariable Long id) {
        log.debug("REST request to delete BoletimPrevVariavelMet : {}", id);
        boletimPrevVariavelMetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
