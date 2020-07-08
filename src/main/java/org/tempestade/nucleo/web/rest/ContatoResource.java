package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.ContatoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.ContatoDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Contato}.
 */
@RestController
@RequestMapping("/api")
public class ContatoResource {

    private final Logger log = LoggerFactory.getLogger(ContatoResource.class);

    private static final String ENTITY_NAME = "contato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContatoService contatoService;

    public ContatoResource(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    /**
     * {@code POST  /contatoes} : Create a new contato.
     *
     * @param contatoDTO the contatoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contatoDTO, or with status {@code 400 (Bad Request)} if the contato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contatoes")
    public ResponseEntity<ContatoDTO> createContato(@Valid @RequestBody ContatoDTO contatoDTO) throws URISyntaxException {
        log.debug("REST request to save Contato : {}", contatoDTO);
        if (contatoDTO.getId() != null) {
            throw new BadRequestAlertException("A new contato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContatoDTO result = contatoService.save(contatoDTO);
        return ResponseEntity.created(new URI("/api/contatoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contatoes} : Updates an existing contato.
     *
     * @param contatoDTO the contatoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contatoDTO,
     * or with status {@code 400 (Bad Request)} if the contatoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contatoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contatoes")
    public ResponseEntity<ContatoDTO> updateContato(@Valid @RequestBody ContatoDTO contatoDTO) throws URISyntaxException {
        log.debug("REST request to update Contato : {}", contatoDTO);
        if (contatoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContatoDTO result = contatoService.save(contatoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contatoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contatoes} : get all the contatoes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contatoes in body.
     */
    @GetMapping("/contatoes")
    public ResponseEntity<List<ContatoDTO>> getAllContatoes(Pageable pageable) {
        log.debug("REST request to get a page of Contatoes");
        Page<ContatoDTO> page = contatoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contatoes/:id} : get the "id" contato.
     *
     * @param id the id of the contatoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contatoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contatoes/{id}")
    public ResponseEntity<ContatoDTO> getContato(@PathVariable Long id) {
        log.debug("REST request to get Contato : {}", id);
        Optional<ContatoDTO> contatoDTO = contatoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contatoDTO);
    }

    /**
     * {@code DELETE  /contatoes/:id} : delete the "id" contato.
     *
     * @param id the id of the contatoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contatoes/{id}")
    public ResponseEntity<Void> deleteContato(@PathVariable Long id) {
        log.debug("REST request to delete Contato : {}", id);
        contatoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
