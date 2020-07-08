package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.UmidadeArService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.UmidadeArDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.UmidadeAr}.
 */
@RestController
@RequestMapping("/api")
public class UmidadeArResource {

    private final Logger log = LoggerFactory.getLogger(UmidadeArResource.class);

    private static final String ENTITY_NAME = "umidadeAr";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UmidadeArService umidadeArService;

    public UmidadeArResource(UmidadeArService umidadeArService) {
        this.umidadeArService = umidadeArService;
    }

    /**
     * {@code POST  /umidade-ars} : Create a new umidadeAr.
     *
     * @param umidadeArDTO the umidadeArDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new umidadeArDTO, or with status {@code 400 (Bad Request)} if the umidadeAr has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/umidade-ars")
    public ResponseEntity<UmidadeArDTO> createUmidadeAr(@Valid @RequestBody UmidadeArDTO umidadeArDTO) throws URISyntaxException {
        log.debug("REST request to save UmidadeAr : {}", umidadeArDTO);
        if (umidadeArDTO.getId() != null) {
            throw new BadRequestAlertException("A new umidadeAr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UmidadeArDTO result = umidadeArService.save(umidadeArDTO);
        return ResponseEntity.created(new URI("/api/umidade-ars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /umidade-ars} : Updates an existing umidadeAr.
     *
     * @param umidadeArDTO the umidadeArDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated umidadeArDTO,
     * or with status {@code 400 (Bad Request)} if the umidadeArDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the umidadeArDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/umidade-ars")
    public ResponseEntity<UmidadeArDTO> updateUmidadeAr(@Valid @RequestBody UmidadeArDTO umidadeArDTO) throws URISyntaxException {
        log.debug("REST request to update UmidadeAr : {}", umidadeArDTO);
        if (umidadeArDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UmidadeArDTO result = umidadeArService.save(umidadeArDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, umidadeArDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /umidade-ars} : get all the umidadeArs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of umidadeArs in body.
     */
    @GetMapping("/umidade-ars")
    public ResponseEntity<List<UmidadeArDTO>> getAllUmidadeArs(Pageable pageable) {
        log.debug("REST request to get a page of UmidadeArs");
        Page<UmidadeArDTO> page = umidadeArService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /umidade-ars/:id} : get the "id" umidadeAr.
     *
     * @param id the id of the umidadeArDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the umidadeArDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/umidade-ars/{id}")
    public ResponseEntity<UmidadeArDTO> getUmidadeAr(@PathVariable Long id) {
        log.debug("REST request to get UmidadeAr : {}", id);
        Optional<UmidadeArDTO> umidadeArDTO = umidadeArService.findOne(id);
        return ResponseUtil.wrapOrNotFound(umidadeArDTO);
    }

    /**
     * {@code DELETE  /umidade-ars/:id} : delete the "id" umidadeAr.
     *
     * @param id the id of the umidadeArDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/umidade-ars/{id}")
    public ResponseEntity<Void> deleteUmidadeAr(@PathVariable Long id) {
        log.debug("REST request to delete UmidadeAr : {}", id);
        umidadeArService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
