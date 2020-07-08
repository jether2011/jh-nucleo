package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.VoceSabiaService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.VoceSabiaDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.VoceSabia}.
 */
@RestController
@RequestMapping("/api")
public class VoceSabiaResource {

    private final Logger log = LoggerFactory.getLogger(VoceSabiaResource.class);

    private static final String ENTITY_NAME = "voceSabia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoceSabiaService voceSabiaService;

    public VoceSabiaResource(VoceSabiaService voceSabiaService) {
        this.voceSabiaService = voceSabiaService;
    }

    /**
     * {@code POST  /voce-sabias} : Create a new voceSabia.
     *
     * @param voceSabiaDTO the voceSabiaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voceSabiaDTO, or with status {@code 400 (Bad Request)} if the voceSabia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/voce-sabias")
    public ResponseEntity<VoceSabiaDTO> createVoceSabia(@Valid @RequestBody VoceSabiaDTO voceSabiaDTO) throws URISyntaxException {
        log.debug("REST request to save VoceSabia : {}", voceSabiaDTO);
        if (voceSabiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new voceSabia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoceSabiaDTO result = voceSabiaService.save(voceSabiaDTO);
        return ResponseEntity.created(new URI("/api/voce-sabias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /voce-sabias} : Updates an existing voceSabia.
     *
     * @param voceSabiaDTO the voceSabiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voceSabiaDTO,
     * or with status {@code 400 (Bad Request)} if the voceSabiaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voceSabiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/voce-sabias")
    public ResponseEntity<VoceSabiaDTO> updateVoceSabia(@Valid @RequestBody VoceSabiaDTO voceSabiaDTO) throws URISyntaxException {
        log.debug("REST request to update VoceSabia : {}", voceSabiaDTO);
        if (voceSabiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VoceSabiaDTO result = voceSabiaService.save(voceSabiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, voceSabiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /voce-sabias} : get all the voceSabias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voceSabias in body.
     */
    @GetMapping("/voce-sabias")
    public ResponseEntity<List<VoceSabiaDTO>> getAllVoceSabias(Pageable pageable) {
        log.debug("REST request to get a page of VoceSabias");
        Page<VoceSabiaDTO> page = voceSabiaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /voce-sabias/:id} : get the "id" voceSabia.
     *
     * @param id the id of the voceSabiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voceSabiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/voce-sabias/{id}")
    public ResponseEntity<VoceSabiaDTO> getVoceSabia(@PathVariable Long id) {
        log.debug("REST request to get VoceSabia : {}", id);
        Optional<VoceSabiaDTO> voceSabiaDTO = voceSabiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voceSabiaDTO);
    }

    /**
     * {@code DELETE  /voce-sabias/:id} : delete the "id" voceSabia.
     *
     * @param id the id of the voceSabiaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/voce-sabias/{id}")
    public ResponseEntity<Void> deleteVoceSabia(@PathVariable Long id) {
        log.debug("REST request to delete VoceSabia : {}", id);
        voceSabiaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
