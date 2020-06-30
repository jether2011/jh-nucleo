package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.PrevisaoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.PrevisaoDTO;
import org.tempestade.nucleo.service.dto.PrevisaoCriteria;
import org.tempestade.nucleo.service.PrevisaoQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Previsao}.
 */
@RestController
@RequestMapping("/api")
public class PrevisaoResource {

    private final Logger log = LoggerFactory.getLogger(PrevisaoResource.class);

    private static final String ENTITY_NAME = "previsao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrevisaoService previsaoService;

    private final PrevisaoQueryService previsaoQueryService;

    public PrevisaoResource(PrevisaoService previsaoService, PrevisaoQueryService previsaoQueryService) {
        this.previsaoService = previsaoService;
        this.previsaoQueryService = previsaoQueryService;
    }

    /**
     * {@code POST  /previsaos} : Create a new previsao.
     *
     * @param previsaoDTO the previsaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new previsaoDTO, or with status {@code 400 (Bad Request)} if the previsao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/previsaos")
    public ResponseEntity<PrevisaoDTO> createPrevisao(@Valid @RequestBody PrevisaoDTO previsaoDTO) throws URISyntaxException {
        log.debug("REST request to save Previsao : {}", previsaoDTO);
        if (previsaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new previsao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrevisaoDTO result = previsaoService.save(previsaoDTO);
        return ResponseEntity.created(new URI("/api/previsaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /previsaos} : Updates an existing previsao.
     *
     * @param previsaoDTO the previsaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated previsaoDTO,
     * or with status {@code 400 (Bad Request)} if the previsaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the previsaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/previsaos")
    public ResponseEntity<PrevisaoDTO> updatePrevisao(@Valid @RequestBody PrevisaoDTO previsaoDTO) throws URISyntaxException {
        log.debug("REST request to update Previsao : {}", previsaoDTO);
        if (previsaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrevisaoDTO result = previsaoService.save(previsaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, previsaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /previsaos} : get all the previsaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of previsaos in body.
     */
    @GetMapping("/previsaos")
    public ResponseEntity<List<PrevisaoDTO>> getAllPrevisaos(PrevisaoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Previsaos by criteria: {}", criteria);
        Page<PrevisaoDTO> page = previsaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /previsaos/count} : count all the previsaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/previsaos/count")
    public ResponseEntity<Long> countPrevisaos(PrevisaoCriteria criteria) {
        log.debug("REST request to count Previsaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(previsaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /previsaos/:id} : get the "id" previsao.
     *
     * @param id the id of the previsaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the previsaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/previsaos/{id}")
    public ResponseEntity<PrevisaoDTO> getPrevisao(@PathVariable Long id) {
        log.debug("REST request to get Previsao : {}", id);
        Optional<PrevisaoDTO> previsaoDTO = previsaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(previsaoDTO);
    }

    /**
     * {@code DELETE  /previsaos/:id} : delete the "id" previsao.
     *
     * @param id the id of the previsaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/previsaos/{id}")
    public ResponseEntity<Void> deletePrevisao(@PathVariable Long id) {
        log.debug("REST request to delete Previsao : {}", id);
        previsaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
