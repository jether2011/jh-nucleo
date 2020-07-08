package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.MeteogramaService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.MeteogramaDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Meteograma}.
 */
@RestController
@RequestMapping("/api")
public class MeteogramaResource {

    private final Logger log = LoggerFactory.getLogger(MeteogramaResource.class);

    private static final String ENTITY_NAME = "meteograma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeteogramaService meteogramaService;

    public MeteogramaResource(MeteogramaService meteogramaService) {
        this.meteogramaService = meteogramaService;
    }

    /**
     * {@code POST  /meteogramas} : Create a new meteograma.
     *
     * @param meteogramaDTO the meteogramaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meteogramaDTO, or with status {@code 400 (Bad Request)} if the meteograma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meteogramas")
    public ResponseEntity<MeteogramaDTO> createMeteograma(@Valid @RequestBody MeteogramaDTO meteogramaDTO) throws URISyntaxException {
        log.debug("REST request to save Meteograma : {}", meteogramaDTO);
        if (meteogramaDTO.getId() != null) {
            throw new BadRequestAlertException("A new meteograma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeteogramaDTO result = meteogramaService.save(meteogramaDTO);
        return ResponseEntity.created(new URI("/api/meteogramas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meteogramas} : Updates an existing meteograma.
     *
     * @param meteogramaDTO the meteogramaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meteogramaDTO,
     * or with status {@code 400 (Bad Request)} if the meteogramaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meteogramaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meteogramas")
    public ResponseEntity<MeteogramaDTO> updateMeteograma(@Valid @RequestBody MeteogramaDTO meteogramaDTO) throws URISyntaxException {
        log.debug("REST request to update Meteograma : {}", meteogramaDTO);
        if (meteogramaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MeteogramaDTO result = meteogramaService.save(meteogramaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meteogramaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /meteogramas} : get all the meteogramas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meteogramas in body.
     */
    @GetMapping("/meteogramas")
    public ResponseEntity<List<MeteogramaDTO>> getAllMeteogramas(Pageable pageable) {
        log.debug("REST request to get a page of Meteogramas");
        Page<MeteogramaDTO> page = meteogramaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /meteogramas/:id} : get the "id" meteograma.
     *
     * @param id the id of the meteogramaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meteogramaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meteogramas/{id}")
    public ResponseEntity<MeteogramaDTO> getMeteograma(@PathVariable Long id) {
        log.debug("REST request to get Meteograma : {}", id);
        Optional<MeteogramaDTO> meteogramaDTO = meteogramaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(meteogramaDTO);
    }

    /**
     * {@code DELETE  /meteogramas/:id} : delete the "id" meteograma.
     *
     * @param id the id of the meteogramaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meteogramas/{id}")
    public ResponseEntity<Void> deleteMeteograma(@PathVariable Long id) {
        log.debug("REST request to delete Meteograma : {}", id);
        meteogramaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
