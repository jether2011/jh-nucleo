package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.RecursoTemplateService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.RecursoTemplateDTO;
import org.tempestade.nucleo.service.dto.RecursoTemplateCriteria;
import org.tempestade.nucleo.service.RecursoTemplateQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.RecursoTemplate}.
 */
@RestController
@RequestMapping("/api")
public class RecursoTemplateResource {

    private final Logger log = LoggerFactory.getLogger(RecursoTemplateResource.class);

    private static final String ENTITY_NAME = "recursoTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecursoTemplateService recursoTemplateService;

    private final RecursoTemplateQueryService recursoTemplateQueryService;

    public RecursoTemplateResource(RecursoTemplateService recursoTemplateService, RecursoTemplateQueryService recursoTemplateQueryService) {
        this.recursoTemplateService = recursoTemplateService;
        this.recursoTemplateQueryService = recursoTemplateQueryService;
    }

    /**
     * {@code POST  /recurso-templates} : Create a new recursoTemplate.
     *
     * @param recursoTemplateDTO the recursoTemplateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recursoTemplateDTO, or with status {@code 400 (Bad Request)} if the recursoTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recurso-templates")
    public ResponseEntity<RecursoTemplateDTO> createRecursoTemplate(@Valid @RequestBody RecursoTemplateDTO recursoTemplateDTO) throws URISyntaxException {
        log.debug("REST request to save RecursoTemplate : {}", recursoTemplateDTO);
        if (recursoTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new recursoTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecursoTemplateDTO result = recursoTemplateService.save(recursoTemplateDTO);
        return ResponseEntity.created(new URI("/api/recurso-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recurso-templates} : Updates an existing recursoTemplate.
     *
     * @param recursoTemplateDTO the recursoTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recursoTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the recursoTemplateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recursoTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recurso-templates")
    public ResponseEntity<RecursoTemplateDTO> updateRecursoTemplate(@Valid @RequestBody RecursoTemplateDTO recursoTemplateDTO) throws URISyntaxException {
        log.debug("REST request to update RecursoTemplate : {}", recursoTemplateDTO);
        if (recursoTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecursoTemplateDTO result = recursoTemplateService.save(recursoTemplateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recursoTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recurso-templates} : get all the recursoTemplates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recursoTemplates in body.
     */
    @GetMapping("/recurso-templates")
    public ResponseEntity<List<RecursoTemplateDTO>> getAllRecursoTemplates(RecursoTemplateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RecursoTemplates by criteria: {}", criteria);
        Page<RecursoTemplateDTO> page = recursoTemplateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recurso-templates/count} : count all the recursoTemplates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/recurso-templates/count")
    public ResponseEntity<Long> countRecursoTemplates(RecursoTemplateCriteria criteria) {
        log.debug("REST request to count RecursoTemplates by criteria: {}", criteria);
        return ResponseEntity.ok().body(recursoTemplateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /recurso-templates/:id} : get the "id" recursoTemplate.
     *
     * @param id the id of the recursoTemplateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recursoTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recurso-templates/{id}")
    public ResponseEntity<RecursoTemplateDTO> getRecursoTemplate(@PathVariable Long id) {
        log.debug("REST request to get RecursoTemplate : {}", id);
        Optional<RecursoTemplateDTO> recursoTemplateDTO = recursoTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recursoTemplateDTO);
    }

    /**
     * {@code DELETE  /recurso-templates/:id} : delete the "id" recursoTemplate.
     *
     * @param id the id of the recursoTemplateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recurso-templates/{id}")
    public ResponseEntity<Void> deleteRecursoTemplate(@PathVariable Long id) {
        log.debug("REST request to delete RecursoTemplate : {}", id);
        recursoTemplateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
