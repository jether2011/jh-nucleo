package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.TipoFerramentaService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.TipoFerramentaDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.TipoFerramenta}.
 */
@RestController
@RequestMapping("/api")
public class TipoFerramentaResource {

    private final Logger log = LoggerFactory.getLogger(TipoFerramentaResource.class);

    private static final String ENTITY_NAME = "tipoFerramenta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoFerramentaService tipoFerramentaService;

    public TipoFerramentaResource(TipoFerramentaService tipoFerramentaService) {
        this.tipoFerramentaService = tipoFerramentaService;
    }

    /**
     * {@code POST  /tipo-ferramentas} : Create a new tipoFerramenta.
     *
     * @param tipoFerramentaDTO the tipoFerramentaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoFerramentaDTO, or with status {@code 400 (Bad Request)} if the tipoFerramenta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-ferramentas")
    public ResponseEntity<TipoFerramentaDTO> createTipoFerramenta(@Valid @RequestBody TipoFerramentaDTO tipoFerramentaDTO) throws URISyntaxException {
        log.debug("REST request to save TipoFerramenta : {}", tipoFerramentaDTO);
        if (tipoFerramentaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoFerramenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoFerramentaDTO result = tipoFerramentaService.save(tipoFerramentaDTO);
        return ResponseEntity.created(new URI("/api/tipo-ferramentas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-ferramentas} : Updates an existing tipoFerramenta.
     *
     * @param tipoFerramentaDTO the tipoFerramentaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoFerramentaDTO,
     * or with status {@code 400 (Bad Request)} if the tipoFerramentaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoFerramentaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-ferramentas")
    public ResponseEntity<TipoFerramentaDTO> updateTipoFerramenta(@Valid @RequestBody TipoFerramentaDTO tipoFerramentaDTO) throws URISyntaxException {
        log.debug("REST request to update TipoFerramenta : {}", tipoFerramentaDTO);
        if (tipoFerramentaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoFerramentaDTO result = tipoFerramentaService.save(tipoFerramentaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoFerramentaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-ferramentas} : get all the tipoFerramentas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoFerramentas in body.
     */
    @GetMapping("/tipo-ferramentas")
    public ResponseEntity<List<TipoFerramentaDTO>> getAllTipoFerramentas(Pageable pageable) {
        log.debug("REST request to get a page of TipoFerramentas");
        Page<TipoFerramentaDTO> page = tipoFerramentaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-ferramentas/:id} : get the "id" tipoFerramenta.
     *
     * @param id the id of the tipoFerramentaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoFerramentaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-ferramentas/{id}")
    public ResponseEntity<TipoFerramentaDTO> getTipoFerramenta(@PathVariable Long id) {
        log.debug("REST request to get TipoFerramenta : {}", id);
        Optional<TipoFerramentaDTO> tipoFerramentaDTO = tipoFerramentaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoFerramentaDTO);
    }

    /**
     * {@code DELETE  /tipo-ferramentas/:id} : delete the "id" tipoFerramenta.
     *
     * @param id the id of the tipoFerramentaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-ferramentas/{id}")
    public ResponseEntity<Void> deleteTipoFerramenta(@PathVariable Long id) {
        log.debug("REST request to delete TipoFerramenta : {}", id);
        tipoFerramentaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
