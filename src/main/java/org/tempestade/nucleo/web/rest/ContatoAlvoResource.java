package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.ContatoAlvoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.ContatoAlvoDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.ContatoAlvo}.
 */
@RestController
@RequestMapping("/api")
public class ContatoAlvoResource {

    private final Logger log = LoggerFactory.getLogger(ContatoAlvoResource.class);

    private static final String ENTITY_NAME = "contatoAlvo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContatoAlvoService contatoAlvoService;

    public ContatoAlvoResource(ContatoAlvoService contatoAlvoService) {
        this.contatoAlvoService = contatoAlvoService;
    }

    /**
     * {@code POST  /contato-alvos} : Create a new contatoAlvo.
     *
     * @param contatoAlvoDTO the contatoAlvoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contatoAlvoDTO, or with status {@code 400 (Bad Request)} if the contatoAlvo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contato-alvos")
    public ResponseEntity<ContatoAlvoDTO> createContatoAlvo(@Valid @RequestBody ContatoAlvoDTO contatoAlvoDTO) throws URISyntaxException {
        log.debug("REST request to save ContatoAlvo : {}", contatoAlvoDTO);
        if (contatoAlvoDTO.getId() != null) {
            throw new BadRequestAlertException("A new contatoAlvo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContatoAlvoDTO result = contatoAlvoService.save(contatoAlvoDTO);
        return ResponseEntity.created(new URI("/api/contato-alvos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contato-alvos} : Updates an existing contatoAlvo.
     *
     * @param contatoAlvoDTO the contatoAlvoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contatoAlvoDTO,
     * or with status {@code 400 (Bad Request)} if the contatoAlvoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contatoAlvoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contato-alvos")
    public ResponseEntity<ContatoAlvoDTO> updateContatoAlvo(@Valid @RequestBody ContatoAlvoDTO contatoAlvoDTO) throws URISyntaxException {
        log.debug("REST request to update ContatoAlvo : {}", contatoAlvoDTO);
        if (contatoAlvoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContatoAlvoDTO result = contatoAlvoService.save(contatoAlvoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contatoAlvoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contato-alvos} : get all the contatoAlvos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contatoAlvos in body.
     */
    @GetMapping("/contato-alvos")
    public ResponseEntity<List<ContatoAlvoDTO>> getAllContatoAlvos(Pageable pageable) {
        log.debug("REST request to get a page of ContatoAlvos");
        Page<ContatoAlvoDTO> page = contatoAlvoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contato-alvos/:id} : get the "id" contatoAlvo.
     *
     * @param id the id of the contatoAlvoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contatoAlvoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contato-alvos/{id}")
    public ResponseEntity<ContatoAlvoDTO> getContatoAlvo(@PathVariable Long id) {
        log.debug("REST request to get ContatoAlvo : {}", id);
        Optional<ContatoAlvoDTO> contatoAlvoDTO = contatoAlvoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contatoAlvoDTO);
    }

    /**
     * {@code DELETE  /contato-alvos/:id} : delete the "id" contatoAlvo.
     *
     * @param id the id of the contatoAlvoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contato-alvos/{id}")
    public ResponseEntity<Void> deleteContatoAlvo(@PathVariable Long id) {
        log.debug("REST request to delete ContatoAlvo : {}", id);
        contatoAlvoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
