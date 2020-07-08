package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.DescargaService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.DescargaDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Descarga}.
 */
@RestController
@RequestMapping("/api")
public class DescargaResource {

    private final Logger log = LoggerFactory.getLogger(DescargaResource.class);

    private static final String ENTITY_NAME = "descarga";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DescargaService descargaService;

    public DescargaResource(DescargaService descargaService) {
        this.descargaService = descargaService;
    }

    /**
     * {@code POST  /descargas} : Create a new descarga.
     *
     * @param descargaDTO the descargaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new descargaDTO, or with status {@code 400 (Bad Request)} if the descarga has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/descargas")
    public ResponseEntity<DescargaDTO> createDescarga(@Valid @RequestBody DescargaDTO descargaDTO) throws URISyntaxException {
        log.debug("REST request to save Descarga : {}", descargaDTO);
        if (descargaDTO.getId() != null) {
            throw new BadRequestAlertException("A new descarga cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DescargaDTO result = descargaService.save(descargaDTO);
        return ResponseEntity.created(new URI("/api/descargas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /descargas} : Updates an existing descarga.
     *
     * @param descargaDTO the descargaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated descargaDTO,
     * or with status {@code 400 (Bad Request)} if the descargaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the descargaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/descargas")
    public ResponseEntity<DescargaDTO> updateDescarga(@Valid @RequestBody DescargaDTO descargaDTO) throws URISyntaxException {
        log.debug("REST request to update Descarga : {}", descargaDTO);
        if (descargaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DescargaDTO result = descargaService.save(descargaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, descargaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /descargas} : get all the descargas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of descargas in body.
     */
    @GetMapping("/descargas")
    public ResponseEntity<List<DescargaDTO>> getAllDescargas(Pageable pageable) {
        log.debug("REST request to get a page of Descargas");
        Page<DescargaDTO> page = descargaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /descargas/:id} : get the "id" descarga.
     *
     * @param id the id of the descargaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the descargaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/descargas/{id}")
    public ResponseEntity<DescargaDTO> getDescarga(@PathVariable Long id) {
        log.debug("REST request to get Descarga : {}", id);
        Optional<DescargaDTO> descargaDTO = descargaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(descargaDTO);
    }

    /**
     * {@code DELETE  /descargas/:id} : delete the "id" descarga.
     *
     * @param id the id of the descargaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/descargas/{id}")
    public ResponseEntity<Void> deleteDescarga(@PathVariable Long id) {
        log.debug("REST request to delete Descarga : {}", id);
        descargaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
