package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.ConsolidacaoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.ConsolidacaoDTO;
import org.tempestade.nucleo.service.dto.ConsolidacaoCriteria;
import org.tempestade.nucleo.service.ConsolidacaoQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Consolidacao}.
 */
@RestController
@RequestMapping("/api")
public class ConsolidacaoResource {

    private final Logger log = LoggerFactory.getLogger(ConsolidacaoResource.class);

    private static final String ENTITY_NAME = "consolidacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsolidacaoService consolidacaoService;

    private final ConsolidacaoQueryService consolidacaoQueryService;

    public ConsolidacaoResource(ConsolidacaoService consolidacaoService, ConsolidacaoQueryService consolidacaoQueryService) {
        this.consolidacaoService = consolidacaoService;
        this.consolidacaoQueryService = consolidacaoQueryService;
    }

    /**
     * {@code POST  /consolidacaos} : Create a new consolidacao.
     *
     * @param consolidacaoDTO the consolidacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consolidacaoDTO, or with status {@code 400 (Bad Request)} if the consolidacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consolidacaos")
    public ResponseEntity<ConsolidacaoDTO> createConsolidacao(@Valid @RequestBody ConsolidacaoDTO consolidacaoDTO) throws URISyntaxException {
        log.debug("REST request to save Consolidacao : {}", consolidacaoDTO);
        if (consolidacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new consolidacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsolidacaoDTO result = consolidacaoService.save(consolidacaoDTO);
        return ResponseEntity.created(new URI("/api/consolidacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consolidacaos} : Updates an existing consolidacao.
     *
     * @param consolidacaoDTO the consolidacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consolidacaoDTO,
     * or with status {@code 400 (Bad Request)} if the consolidacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consolidacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consolidacaos")
    public ResponseEntity<ConsolidacaoDTO> updateConsolidacao(@Valid @RequestBody ConsolidacaoDTO consolidacaoDTO) throws URISyntaxException {
        log.debug("REST request to update Consolidacao : {}", consolidacaoDTO);
        if (consolidacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConsolidacaoDTO result = consolidacaoService.save(consolidacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consolidacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /consolidacaos} : get all the consolidacaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consolidacaos in body.
     */
    @GetMapping("/consolidacaos")
    public ResponseEntity<List<ConsolidacaoDTO>> getAllConsolidacaos(ConsolidacaoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Consolidacaos by criteria: {}", criteria);
        Page<ConsolidacaoDTO> page = consolidacaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /consolidacaos/count} : count all the consolidacaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/consolidacaos/count")
    public ResponseEntity<Long> countConsolidacaos(ConsolidacaoCriteria criteria) {
        log.debug("REST request to count Consolidacaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(consolidacaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /consolidacaos/:id} : get the "id" consolidacao.
     *
     * @param id the id of the consolidacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consolidacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consolidacaos/{id}")
    public ResponseEntity<ConsolidacaoDTO> getConsolidacao(@PathVariable Long id) {
        log.debug("REST request to get Consolidacao : {}", id);
        Optional<ConsolidacaoDTO> consolidacaoDTO = consolidacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consolidacaoDTO);
    }

    /**
     * {@code DELETE  /consolidacaos/:id} : delete the "id" consolidacao.
     *
     * @param id the id of the consolidacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consolidacaos/{id}")
    public ResponseEntity<Void> deleteConsolidacao(@PathVariable Long id) {
        log.debug("REST request to delete Consolidacao : {}", id);
        consolidacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
