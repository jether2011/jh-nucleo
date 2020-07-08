package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.BoletimPrevisaoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.BoletimPrevisaoDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.BoletimPrevisao}.
 */
@RestController
@RequestMapping("/api")
public class BoletimPrevisaoResource {

    private final Logger log = LoggerFactory.getLogger(BoletimPrevisaoResource.class);

    private static final String ENTITY_NAME = "boletimPrevisao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoletimPrevisaoService boletimPrevisaoService;

    public BoletimPrevisaoResource(BoletimPrevisaoService boletimPrevisaoService) {
        this.boletimPrevisaoService = boletimPrevisaoService;
    }

    /**
     * {@code POST  /boletim-previsaos} : Create a new boletimPrevisao.
     *
     * @param boletimPrevisaoDTO the boletimPrevisaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boletimPrevisaoDTO, or with status {@code 400 (Bad Request)} if the boletimPrevisao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/boletim-previsaos")
    public ResponseEntity<BoletimPrevisaoDTO> createBoletimPrevisao(@Valid @RequestBody BoletimPrevisaoDTO boletimPrevisaoDTO) throws URISyntaxException {
        log.debug("REST request to save BoletimPrevisao : {}", boletimPrevisaoDTO);
        if (boletimPrevisaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new boletimPrevisao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BoletimPrevisaoDTO result = boletimPrevisaoService.save(boletimPrevisaoDTO);
        return ResponseEntity.created(new URI("/api/boletim-previsaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /boletim-previsaos} : Updates an existing boletimPrevisao.
     *
     * @param boletimPrevisaoDTO the boletimPrevisaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boletimPrevisaoDTO,
     * or with status {@code 400 (Bad Request)} if the boletimPrevisaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the boletimPrevisaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/boletim-previsaos")
    public ResponseEntity<BoletimPrevisaoDTO> updateBoletimPrevisao(@Valid @RequestBody BoletimPrevisaoDTO boletimPrevisaoDTO) throws URISyntaxException {
        log.debug("REST request to update BoletimPrevisao : {}", boletimPrevisaoDTO);
        if (boletimPrevisaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BoletimPrevisaoDTO result = boletimPrevisaoService.save(boletimPrevisaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, boletimPrevisaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /boletim-previsaos} : get all the boletimPrevisaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of boletimPrevisaos in body.
     */
    @GetMapping("/boletim-previsaos")
    public ResponseEntity<List<BoletimPrevisaoDTO>> getAllBoletimPrevisaos(Pageable pageable) {
        log.debug("REST request to get a page of BoletimPrevisaos");
        Page<BoletimPrevisaoDTO> page = boletimPrevisaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /boletim-previsaos/:id} : get the "id" boletimPrevisao.
     *
     * @param id the id of the boletimPrevisaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boletimPrevisaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/boletim-previsaos/{id}")
    public ResponseEntity<BoletimPrevisaoDTO> getBoletimPrevisao(@PathVariable Long id) {
        log.debug("REST request to get BoletimPrevisao : {}", id);
        Optional<BoletimPrevisaoDTO> boletimPrevisaoDTO = boletimPrevisaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(boletimPrevisaoDTO);
    }

    /**
     * {@code DELETE  /boletim-previsaos/:id} : delete the "id" boletimPrevisao.
     *
     * @param id the id of the boletimPrevisaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/boletim-previsaos/{id}")
    public ResponseEntity<Void> deleteBoletimPrevisao(@PathVariable Long id) {
        log.debug("REST request to delete BoletimPrevisao : {}", id);
        boletimPrevisaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
