package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.InformativoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.InformativoDTO;
import org.tempestade.nucleo.service.dto.InformativoCriteria;
import org.tempestade.nucleo.service.InformativoQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Informativo}.
 */
@RestController
@RequestMapping("/api")
public class InformativoResource {

    private final Logger log = LoggerFactory.getLogger(InformativoResource.class);

    private static final String ENTITY_NAME = "informativo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InformativoService informativoService;

    private final InformativoQueryService informativoQueryService;

    public InformativoResource(InformativoService informativoService, InformativoQueryService informativoQueryService) {
        this.informativoService = informativoService;
        this.informativoQueryService = informativoQueryService;
    }

    /**
     * {@code POST  /informativos} : Create a new informativo.
     *
     * @param informativoDTO the informativoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new informativoDTO, or with status {@code 400 (Bad Request)} if the informativo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/informativos")
    public ResponseEntity<InformativoDTO> createInformativo(@Valid @RequestBody InformativoDTO informativoDTO) throws URISyntaxException {
        log.debug("REST request to save Informativo : {}", informativoDTO);
        if (informativoDTO.getId() != null) {
            throw new BadRequestAlertException("A new informativo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InformativoDTO result = informativoService.save(informativoDTO);
        return ResponseEntity.created(new URI("/api/informativos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /informativos} : Updates an existing informativo.
     *
     * @param informativoDTO the informativoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informativoDTO,
     * or with status {@code 400 (Bad Request)} if the informativoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the informativoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/informativos")
    public ResponseEntity<InformativoDTO> updateInformativo(@Valid @RequestBody InformativoDTO informativoDTO) throws URISyntaxException {
        log.debug("REST request to update Informativo : {}", informativoDTO);
        if (informativoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InformativoDTO result = informativoService.save(informativoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, informativoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /informativos} : get all the informativos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of informativos in body.
     */
    @GetMapping("/informativos")
    public ResponseEntity<List<InformativoDTO>> getAllInformativos(InformativoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Informativos by criteria: {}", criteria);
        Page<InformativoDTO> page = informativoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /informativos/count} : count all the informativos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/informativos/count")
    public ResponseEntity<Long> countInformativos(InformativoCriteria criteria) {
        log.debug("REST request to count Informativos by criteria: {}", criteria);
        return ResponseEntity.ok().body(informativoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /informativos/:id} : get the "id" informativo.
     *
     * @param id the id of the informativoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the informativoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/informativos/{id}")
    public ResponseEntity<InformativoDTO> getInformativo(@PathVariable Long id) {
        log.debug("REST request to get Informativo : {}", id);
        Optional<InformativoDTO> informativoDTO = informativoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(informativoDTO);
    }

    /**
     * {@code DELETE  /informativos/:id} : delete the "id" informativo.
     *
     * @param id the id of the informativoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/informativos/{id}")
    public ResponseEntity<Void> deleteInformativo(@PathVariable Long id) {
        log.debug("REST request to delete Informativo : {}", id);
        informativoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
