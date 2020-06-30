package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.PerfilService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.PerfilDTO;
import org.tempestade.nucleo.service.dto.PerfilCriteria;
import org.tempestade.nucleo.service.PerfilQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Perfil}.
 */
@RestController
@RequestMapping("/api")
public class PerfilResource {

    private final Logger log = LoggerFactory.getLogger(PerfilResource.class);

    private static final String ENTITY_NAME = "perfil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerfilService perfilService;

    private final PerfilQueryService perfilQueryService;

    public PerfilResource(PerfilService perfilService, PerfilQueryService perfilQueryService) {
        this.perfilService = perfilService;
        this.perfilQueryService = perfilQueryService;
    }

    /**
     * {@code POST  /perfils} : Create a new perfil.
     *
     * @param perfilDTO the perfilDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perfilDTO, or with status {@code 400 (Bad Request)} if the perfil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/perfils")
    public ResponseEntity<PerfilDTO> createPerfil(@Valid @RequestBody PerfilDTO perfilDTO) throws URISyntaxException {
        log.debug("REST request to save Perfil : {}", perfilDTO);
        if (perfilDTO.getId() != null) {
            throw new BadRequestAlertException("A new perfil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerfilDTO result = perfilService.save(perfilDTO);
        return ResponseEntity.created(new URI("/api/perfils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /perfils} : Updates an existing perfil.
     *
     * @param perfilDTO the perfilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfilDTO,
     * or with status {@code 400 (Bad Request)} if the perfilDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perfilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/perfils")
    public ResponseEntity<PerfilDTO> updatePerfil(@Valid @RequestBody PerfilDTO perfilDTO) throws URISyntaxException {
        log.debug("REST request to update Perfil : {}", perfilDTO);
        if (perfilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PerfilDTO result = perfilService.save(perfilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfilDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /perfils} : get all the perfils.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perfils in body.
     */
    @GetMapping("/perfils")
    public ResponseEntity<List<PerfilDTO>> getAllPerfils(PerfilCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Perfils by criteria: {}", criteria);
        Page<PerfilDTO> page = perfilQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /perfils/count} : count all the perfils.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/perfils/count")
    public ResponseEntity<Long> countPerfils(PerfilCriteria criteria) {
        log.debug("REST request to count Perfils by criteria: {}", criteria);
        return ResponseEntity.ok().body(perfilQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /perfils/:id} : get the "id" perfil.
     *
     * @param id the id of the perfilDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perfilDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/perfils/{id}")
    public ResponseEntity<PerfilDTO> getPerfil(@PathVariable Long id) {
        log.debug("REST request to get Perfil : {}", id);
        Optional<PerfilDTO> perfilDTO = perfilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(perfilDTO);
    }

    /**
     * {@code DELETE  /perfils/:id} : delete the "id" perfil.
     *
     * @param id the id of the perfilDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/perfils/{id}")
    public ResponseEntity<Void> deletePerfil(@PathVariable Long id) {
        log.debug("REST request to delete Perfil : {}", id);
        perfilService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
