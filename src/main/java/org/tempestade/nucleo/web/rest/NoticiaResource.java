package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.NoticiaService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.NoticiaDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Noticia}.
 */
@RestController
@RequestMapping("/api")
public class NoticiaResource {

    private final Logger log = LoggerFactory.getLogger(NoticiaResource.class);

    private static final String ENTITY_NAME = "noticia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NoticiaService noticiaService;

    public NoticiaResource(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }

    /**
     * {@code POST  /noticias} : Create a new noticia.
     *
     * @param noticiaDTO the noticiaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new noticiaDTO, or with status {@code 400 (Bad Request)} if the noticia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/noticias")
    public ResponseEntity<NoticiaDTO> createNoticia(@Valid @RequestBody NoticiaDTO noticiaDTO) throws URISyntaxException {
        log.debug("REST request to save Noticia : {}", noticiaDTO);
        if (noticiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new noticia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NoticiaDTO result = noticiaService.save(noticiaDTO);
        return ResponseEntity.created(new URI("/api/noticias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /noticias} : Updates an existing noticia.
     *
     * @param noticiaDTO the noticiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noticiaDTO,
     * or with status {@code 400 (Bad Request)} if the noticiaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the noticiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/noticias")
    public ResponseEntity<NoticiaDTO> updateNoticia(@Valid @RequestBody NoticiaDTO noticiaDTO) throws URISyntaxException {
        log.debug("REST request to update Noticia : {}", noticiaDTO);
        if (noticiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NoticiaDTO result = noticiaService.save(noticiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noticiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /noticias} : get all the noticias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of noticias in body.
     */
    @GetMapping("/noticias")
    public ResponseEntity<List<NoticiaDTO>> getAllNoticias(Pageable pageable) {
        log.debug("REST request to get a page of Noticias");
        Page<NoticiaDTO> page = noticiaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /noticias/:id} : get the "id" noticia.
     *
     * @param id the id of the noticiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noticiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/noticias/{id}")
    public ResponseEntity<NoticiaDTO> getNoticia(@PathVariable Long id) {
        log.debug("REST request to get Noticia : {}", id);
        Optional<NoticiaDTO> noticiaDTO = noticiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noticiaDTO);
    }

    /**
     * {@code DELETE  /noticias/:id} : delete the "id" noticia.
     *
     * @param id the id of the noticiaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/noticias/{id}")
    public ResponseEntity<Void> deleteNoticia(@PathVariable Long id) {
        log.debug("REST request to delete Noticia : {}", id);
        noticiaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
