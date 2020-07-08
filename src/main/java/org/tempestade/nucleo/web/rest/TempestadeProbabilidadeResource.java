package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.TempestadeProbabilidadeService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.TempestadeProbabilidadeDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.TempestadeProbabilidade}.
 */
@RestController
@RequestMapping("/api")
public class TempestadeProbabilidadeResource {

    private final Logger log = LoggerFactory.getLogger(TempestadeProbabilidadeResource.class);

    private static final String ENTITY_NAME = "tempestadeProbabilidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TempestadeProbabilidadeService tempestadeProbabilidadeService;

    public TempestadeProbabilidadeResource(TempestadeProbabilidadeService tempestadeProbabilidadeService) {
        this.tempestadeProbabilidadeService = tempestadeProbabilidadeService;
    }

    /**
     * {@code POST  /tempestade-probabilidades} : Create a new tempestadeProbabilidade.
     *
     * @param tempestadeProbabilidadeDTO the tempestadeProbabilidadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tempestadeProbabilidadeDTO, or with status {@code 400 (Bad Request)} if the tempestadeProbabilidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tempestade-probabilidades")
    public ResponseEntity<TempestadeProbabilidadeDTO> createTempestadeProbabilidade(@Valid @RequestBody TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO) throws URISyntaxException {
        log.debug("REST request to save TempestadeProbabilidade : {}", tempestadeProbabilidadeDTO);
        if (tempestadeProbabilidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new tempestadeProbabilidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TempestadeProbabilidadeDTO result = tempestadeProbabilidadeService.save(tempestadeProbabilidadeDTO);
        return ResponseEntity.created(new URI("/api/tempestade-probabilidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tempestade-probabilidades} : Updates an existing tempestadeProbabilidade.
     *
     * @param tempestadeProbabilidadeDTO the tempestadeProbabilidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tempestadeProbabilidadeDTO,
     * or with status {@code 400 (Bad Request)} if the tempestadeProbabilidadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tempestadeProbabilidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tempestade-probabilidades")
    public ResponseEntity<TempestadeProbabilidadeDTO> updateTempestadeProbabilidade(@Valid @RequestBody TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO) throws URISyntaxException {
        log.debug("REST request to update TempestadeProbabilidade : {}", tempestadeProbabilidadeDTO);
        if (tempestadeProbabilidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TempestadeProbabilidadeDTO result = tempestadeProbabilidadeService.save(tempestadeProbabilidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tempestadeProbabilidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tempestade-probabilidades} : get all the tempestadeProbabilidades.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tempestadeProbabilidades in body.
     */
    @GetMapping("/tempestade-probabilidades")
    public ResponseEntity<List<TempestadeProbabilidadeDTO>> getAllTempestadeProbabilidades(Pageable pageable) {
        log.debug("REST request to get a page of TempestadeProbabilidades");
        Page<TempestadeProbabilidadeDTO> page = tempestadeProbabilidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tempestade-probabilidades/:id} : get the "id" tempestadeProbabilidade.
     *
     * @param id the id of the tempestadeProbabilidadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tempestadeProbabilidadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tempestade-probabilidades/{id}")
    public ResponseEntity<TempestadeProbabilidadeDTO> getTempestadeProbabilidade(@PathVariable Long id) {
        log.debug("REST request to get TempestadeProbabilidade : {}", id);
        Optional<TempestadeProbabilidadeDTO> tempestadeProbabilidadeDTO = tempestadeProbabilidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tempestadeProbabilidadeDTO);
    }

    /**
     * {@code DELETE  /tempestade-probabilidades/:id} : delete the "id" tempestadeProbabilidade.
     *
     * @param id the id of the tempestadeProbabilidadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tempestade-probabilidades/{id}")
    public ResponseEntity<Void> deleteTempestadeProbabilidade(@PathVariable Long id) {
        log.debug("REST request to delete TempestadeProbabilidade : {}", id);
        tempestadeProbabilidadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
