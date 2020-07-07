package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.ContatoPlanoRecursoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.ContatoPlanoRecursoDTO;
import org.tempestade.nucleo.service.dto.ContatoPlanoRecursoCriteria;
import org.tempestade.nucleo.service.ContatoPlanoRecursoQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.ContatoPlanoRecurso}.
 */
@RestController
@RequestMapping("/api")
public class ContatoPlanoRecursoResource {

    private final Logger log = LoggerFactory.getLogger(ContatoPlanoRecursoResource.class);

    private static final String ENTITY_NAME = "contatoPlanoRecurso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContatoPlanoRecursoService contatoPlanoRecursoService;

    private final ContatoPlanoRecursoQueryService contatoPlanoRecursoQueryService;

    public ContatoPlanoRecursoResource(ContatoPlanoRecursoService contatoPlanoRecursoService, ContatoPlanoRecursoQueryService contatoPlanoRecursoQueryService) {
        this.contatoPlanoRecursoService = contatoPlanoRecursoService;
        this.contatoPlanoRecursoQueryService = contatoPlanoRecursoQueryService;
    }

    /**
     * {@code POST  /contato-plano-recursos} : Create a new contatoPlanoRecurso.
     *
     * @param contatoPlanoRecursoDTO the contatoPlanoRecursoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contatoPlanoRecursoDTO, or with status {@code 400 (Bad Request)} if the contatoPlanoRecurso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contato-plano-recursos")
    public ResponseEntity<ContatoPlanoRecursoDTO> createContatoPlanoRecurso(@Valid @RequestBody ContatoPlanoRecursoDTO contatoPlanoRecursoDTO) throws URISyntaxException {
        log.debug("REST request to save ContatoPlanoRecurso : {}", contatoPlanoRecursoDTO);
        if (contatoPlanoRecursoDTO.getId() != null) {
            throw new BadRequestAlertException("A new contatoPlanoRecurso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContatoPlanoRecursoDTO result = contatoPlanoRecursoService.save(contatoPlanoRecursoDTO);
        return ResponseEntity.created(new URI("/api/contato-plano-recursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contato-plano-recursos} : Updates an existing contatoPlanoRecurso.
     *
     * @param contatoPlanoRecursoDTO the contatoPlanoRecursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contatoPlanoRecursoDTO,
     * or with status {@code 400 (Bad Request)} if the contatoPlanoRecursoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contatoPlanoRecursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contato-plano-recursos")
    public ResponseEntity<ContatoPlanoRecursoDTO> updateContatoPlanoRecurso(@Valid @RequestBody ContatoPlanoRecursoDTO contatoPlanoRecursoDTO) throws URISyntaxException {
        log.debug("REST request to update ContatoPlanoRecurso : {}", contatoPlanoRecursoDTO);
        if (contatoPlanoRecursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContatoPlanoRecursoDTO result = contatoPlanoRecursoService.save(contatoPlanoRecursoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contatoPlanoRecursoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contato-plano-recursos} : get all the contatoPlanoRecursos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contatoPlanoRecursos in body.
     */
    @GetMapping("/contato-plano-recursos")
    public ResponseEntity<List<ContatoPlanoRecursoDTO>> getAllContatoPlanoRecursos(ContatoPlanoRecursoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ContatoPlanoRecursos by criteria: {}", criteria);
        Page<ContatoPlanoRecursoDTO> page = contatoPlanoRecursoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contato-plano-recursos/count} : count all the contatoPlanoRecursos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/contato-plano-recursos/count")
    public ResponseEntity<Long> countContatoPlanoRecursos(ContatoPlanoRecursoCriteria criteria) {
        log.debug("REST request to count ContatoPlanoRecursos by criteria: {}", criteria);
        return ResponseEntity.ok().body(contatoPlanoRecursoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /contato-plano-recursos/:id} : get the "id" contatoPlanoRecurso.
     *
     * @param id the id of the contatoPlanoRecursoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contatoPlanoRecursoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contato-plano-recursos/{id}")
    public ResponseEntity<ContatoPlanoRecursoDTO> getContatoPlanoRecurso(@PathVariable Long id) {
        log.debug("REST request to get ContatoPlanoRecurso : {}", id);
        Optional<ContatoPlanoRecursoDTO> contatoPlanoRecursoDTO = contatoPlanoRecursoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contatoPlanoRecursoDTO);
    }

    /**
     * {@code DELETE  /contato-plano-recursos/:id} : delete the "id" contatoPlanoRecurso.
     *
     * @param id the id of the contatoPlanoRecursoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contato-plano-recursos/{id}")
    public ResponseEntity<Void> deleteContatoPlanoRecurso(@PathVariable Long id) {
        log.debug("REST request to delete ContatoPlanoRecurso : {}", id);
        contatoPlanoRecursoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
