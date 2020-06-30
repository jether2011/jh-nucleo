package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.PlanoUsuarioService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.PlanoUsuarioDTO;
import org.tempestade.nucleo.service.dto.PlanoUsuarioCriteria;
import org.tempestade.nucleo.service.PlanoUsuarioQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.PlanoUsuario}.
 */
@RestController
@RequestMapping("/api")
public class PlanoUsuarioResource {

    private final Logger log = LoggerFactory.getLogger(PlanoUsuarioResource.class);

    private static final String ENTITY_NAME = "planoUsuario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoUsuarioService planoUsuarioService;

    private final PlanoUsuarioQueryService planoUsuarioQueryService;

    public PlanoUsuarioResource(PlanoUsuarioService planoUsuarioService, PlanoUsuarioQueryService planoUsuarioQueryService) {
        this.planoUsuarioService = planoUsuarioService;
        this.planoUsuarioQueryService = planoUsuarioQueryService;
    }

    /**
     * {@code POST  /plano-usuarios} : Create a new planoUsuario.
     *
     * @param planoUsuarioDTO the planoUsuarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoUsuarioDTO, or with status {@code 400 (Bad Request)} if the planoUsuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plano-usuarios")
    public ResponseEntity<PlanoUsuarioDTO> createPlanoUsuario(@Valid @RequestBody PlanoUsuarioDTO planoUsuarioDTO) throws URISyntaxException {
        log.debug("REST request to save PlanoUsuario : {}", planoUsuarioDTO);
        if (planoUsuarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new planoUsuario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanoUsuarioDTO result = planoUsuarioService.save(planoUsuarioDTO);
        return ResponseEntity.created(new URI("/api/plano-usuarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plano-usuarios} : Updates an existing planoUsuario.
     *
     * @param planoUsuarioDTO the planoUsuarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoUsuarioDTO,
     * or with status {@code 400 (Bad Request)} if the planoUsuarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planoUsuarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plano-usuarios")
    public ResponseEntity<PlanoUsuarioDTO> updatePlanoUsuario(@Valid @RequestBody PlanoUsuarioDTO planoUsuarioDTO) throws URISyntaxException {
        log.debug("REST request to update PlanoUsuario : {}", planoUsuarioDTO);
        if (planoUsuarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanoUsuarioDTO result = planoUsuarioService.save(planoUsuarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoUsuarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plano-usuarios} : get all the planoUsuarios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoUsuarios in body.
     */
    @GetMapping("/plano-usuarios")
    public ResponseEntity<List<PlanoUsuarioDTO>> getAllPlanoUsuarios(PlanoUsuarioCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PlanoUsuarios by criteria: {}", criteria);
        Page<PlanoUsuarioDTO> page = planoUsuarioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plano-usuarios/count} : count all the planoUsuarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/plano-usuarios/count")
    public ResponseEntity<Long> countPlanoUsuarios(PlanoUsuarioCriteria criteria) {
        log.debug("REST request to count PlanoUsuarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(planoUsuarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /plano-usuarios/:id} : get the "id" planoUsuario.
     *
     * @param id the id of the planoUsuarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planoUsuarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plano-usuarios/{id}")
    public ResponseEntity<PlanoUsuarioDTO> getPlanoUsuario(@PathVariable Long id) {
        log.debug("REST request to get PlanoUsuario : {}", id);
        Optional<PlanoUsuarioDTO> planoUsuarioDTO = planoUsuarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planoUsuarioDTO);
    }

    /**
     * {@code DELETE  /plano-usuarios/:id} : delete the "id" planoUsuario.
     *
     * @param id the id of the planoUsuarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plano-usuarios/{id}")
    public ResponseEntity<Void> deletePlanoUsuario(@PathVariable Long id) {
        log.debug("REST request to delete PlanoUsuario : {}", id);
        planoUsuarioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
