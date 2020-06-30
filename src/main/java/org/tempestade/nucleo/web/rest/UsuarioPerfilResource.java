package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.UsuarioPerfilService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.UsuarioPerfilDTO;
import org.tempestade.nucleo.service.dto.UsuarioPerfilCriteria;
import org.tempestade.nucleo.service.UsuarioPerfilQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.UsuarioPerfil}.
 */
@RestController
@RequestMapping("/api")
public class UsuarioPerfilResource {

    private final Logger log = LoggerFactory.getLogger(UsuarioPerfilResource.class);

    private static final String ENTITY_NAME = "usuarioPerfil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsuarioPerfilService usuarioPerfilService;

    private final UsuarioPerfilQueryService usuarioPerfilQueryService;

    public UsuarioPerfilResource(UsuarioPerfilService usuarioPerfilService, UsuarioPerfilQueryService usuarioPerfilQueryService) {
        this.usuarioPerfilService = usuarioPerfilService;
        this.usuarioPerfilQueryService = usuarioPerfilQueryService;
    }

    /**
     * {@code POST  /usuario-perfils} : Create a new usuarioPerfil.
     *
     * @param usuarioPerfilDTO the usuarioPerfilDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarioPerfilDTO, or with status {@code 400 (Bad Request)} if the usuarioPerfil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/usuario-perfils")
    public ResponseEntity<UsuarioPerfilDTO> createUsuarioPerfil(@Valid @RequestBody UsuarioPerfilDTO usuarioPerfilDTO) throws URISyntaxException {
        log.debug("REST request to save UsuarioPerfil : {}", usuarioPerfilDTO);
        if (usuarioPerfilDTO.getId() != null) {
            throw new BadRequestAlertException("A new usuarioPerfil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsuarioPerfilDTO result = usuarioPerfilService.save(usuarioPerfilDTO);
        return ResponseEntity.created(new URI("/api/usuario-perfils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usuario-perfils} : Updates an existing usuarioPerfil.
     *
     * @param usuarioPerfilDTO the usuarioPerfilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioPerfilDTO,
     * or with status {@code 400 (Bad Request)} if the usuarioPerfilDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarioPerfilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usuario-perfils")
    public ResponseEntity<UsuarioPerfilDTO> updateUsuarioPerfil(@Valid @RequestBody UsuarioPerfilDTO usuarioPerfilDTO) throws URISyntaxException {
        log.debug("REST request to update UsuarioPerfil : {}", usuarioPerfilDTO);
        if (usuarioPerfilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsuarioPerfilDTO result = usuarioPerfilService.save(usuarioPerfilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarioPerfilDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /usuario-perfils} : get all the usuarioPerfils.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usuarioPerfils in body.
     */
    @GetMapping("/usuario-perfils")
    public ResponseEntity<List<UsuarioPerfilDTO>> getAllUsuarioPerfils(UsuarioPerfilCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UsuarioPerfils by criteria: {}", criteria);
        Page<UsuarioPerfilDTO> page = usuarioPerfilQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /usuario-perfils/count} : count all the usuarioPerfils.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/usuario-perfils/count")
    public ResponseEntity<Long> countUsuarioPerfils(UsuarioPerfilCriteria criteria) {
        log.debug("REST request to count UsuarioPerfils by criteria: {}", criteria);
        return ResponseEntity.ok().body(usuarioPerfilQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /usuario-perfils/:id} : get the "id" usuarioPerfil.
     *
     * @param id the id of the usuarioPerfilDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuarioPerfilDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/usuario-perfils/{id}")
    public ResponseEntity<UsuarioPerfilDTO> getUsuarioPerfil(@PathVariable Long id) {
        log.debug("REST request to get UsuarioPerfil : {}", id);
        Optional<UsuarioPerfilDTO> usuarioPerfilDTO = usuarioPerfilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usuarioPerfilDTO);
    }

    /**
     * {@code DELETE  /usuario-perfils/:id} : delete the "id" usuarioPerfil.
     *
     * @param id the id of the usuarioPerfilDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/usuario-perfils/{id}")
    public ResponseEntity<Void> deleteUsuarioPerfil(@PathVariable Long id) {
        log.debug("REST request to delete UsuarioPerfil : {}", id);
        usuarioPerfilService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
