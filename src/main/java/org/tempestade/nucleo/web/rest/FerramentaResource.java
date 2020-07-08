package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.FerramentaService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.FerramentaDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Ferramenta}.
 */
@RestController
@RequestMapping("/api")
public class FerramentaResource {

    private final Logger log = LoggerFactory.getLogger(FerramentaResource.class);

    private static final String ENTITY_NAME = "ferramenta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FerramentaService ferramentaService;

    public FerramentaResource(FerramentaService ferramentaService) {
        this.ferramentaService = ferramentaService;
    }

    /**
     * {@code POST  /ferramentas} : Create a new ferramenta.
     *
     * @param ferramentaDTO the ferramentaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ferramentaDTO, or with status {@code 400 (Bad Request)} if the ferramenta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ferramentas")
    public ResponseEntity<FerramentaDTO> createFerramenta(@Valid @RequestBody FerramentaDTO ferramentaDTO) throws URISyntaxException {
        log.debug("REST request to save Ferramenta : {}", ferramentaDTO);
        if (ferramentaDTO.getId() != null) {
            throw new BadRequestAlertException("A new ferramenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FerramentaDTO result = ferramentaService.save(ferramentaDTO);
        return ResponseEntity.created(new URI("/api/ferramentas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ferramentas} : Updates an existing ferramenta.
     *
     * @param ferramentaDTO the ferramentaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ferramentaDTO,
     * or with status {@code 400 (Bad Request)} if the ferramentaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ferramentaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ferramentas")
    public ResponseEntity<FerramentaDTO> updateFerramenta(@Valid @RequestBody FerramentaDTO ferramentaDTO) throws URISyntaxException {
        log.debug("REST request to update Ferramenta : {}", ferramentaDTO);
        if (ferramentaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FerramentaDTO result = ferramentaService.save(ferramentaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ferramentaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ferramentas} : get all the ferramentas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ferramentas in body.
     */
    @GetMapping("/ferramentas")
    public ResponseEntity<List<FerramentaDTO>> getAllFerramentas(Pageable pageable) {
        log.debug("REST request to get a page of Ferramentas");
        Page<FerramentaDTO> page = ferramentaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ferramentas/:id} : get the "id" ferramenta.
     *
     * @param id the id of the ferramentaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ferramentaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ferramentas/{id}")
    public ResponseEntity<FerramentaDTO> getFerramenta(@PathVariable Long id) {
        log.debug("REST request to get Ferramenta : {}", id);
        Optional<FerramentaDTO> ferramentaDTO = ferramentaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ferramentaDTO);
    }

    /**
     * {@code DELETE  /ferramentas/:id} : delete the "id" ferramenta.
     *
     * @param id the id of the ferramentaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ferramentas/{id}")
    public ResponseEntity<Void> deleteFerramenta(@PathVariable Long id) {
        log.debug("REST request to delete Ferramenta : {}", id);
        ferramentaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
