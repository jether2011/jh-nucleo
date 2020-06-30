package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.AvisoTipoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.AvisoTipoDTO;
import org.tempestade.nucleo.service.dto.AvisoTipoCriteria;
import org.tempestade.nucleo.service.AvisoTipoQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.AvisoTipo}.
 */
@RestController
@RequestMapping("/api")
public class AvisoTipoResource {

    private final Logger log = LoggerFactory.getLogger(AvisoTipoResource.class);

    private static final String ENTITY_NAME = "avisoTipo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvisoTipoService avisoTipoService;

    private final AvisoTipoQueryService avisoTipoQueryService;

    public AvisoTipoResource(AvisoTipoService avisoTipoService, AvisoTipoQueryService avisoTipoQueryService) {
        this.avisoTipoService = avisoTipoService;
        this.avisoTipoQueryService = avisoTipoQueryService;
    }

    /**
     * {@code POST  /aviso-tipos} : Create a new avisoTipo.
     *
     * @param avisoTipoDTO the avisoTipoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avisoTipoDTO, or with status {@code 400 (Bad Request)} if the avisoTipo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aviso-tipos")
    public ResponseEntity<AvisoTipoDTO> createAvisoTipo(@Valid @RequestBody AvisoTipoDTO avisoTipoDTO) throws URISyntaxException {
        log.debug("REST request to save AvisoTipo : {}", avisoTipoDTO);
        if (avisoTipoDTO.getId() != null) {
            throw new BadRequestAlertException("A new avisoTipo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvisoTipoDTO result = avisoTipoService.save(avisoTipoDTO);
        return ResponseEntity.created(new URI("/api/aviso-tipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aviso-tipos} : Updates an existing avisoTipo.
     *
     * @param avisoTipoDTO the avisoTipoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avisoTipoDTO,
     * or with status {@code 400 (Bad Request)} if the avisoTipoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avisoTipoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aviso-tipos")
    public ResponseEntity<AvisoTipoDTO> updateAvisoTipo(@Valid @RequestBody AvisoTipoDTO avisoTipoDTO) throws URISyntaxException {
        log.debug("REST request to update AvisoTipo : {}", avisoTipoDTO);
        if (avisoTipoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AvisoTipoDTO result = avisoTipoService.save(avisoTipoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avisoTipoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aviso-tipos} : get all the avisoTipos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avisoTipos in body.
     */
    @GetMapping("/aviso-tipos")
    public ResponseEntity<List<AvisoTipoDTO>> getAllAvisoTipos(AvisoTipoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AvisoTipos by criteria: {}", criteria);
        Page<AvisoTipoDTO> page = avisoTipoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aviso-tipos/count} : count all the avisoTipos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/aviso-tipos/count")
    public ResponseEntity<Long> countAvisoTipos(AvisoTipoCriteria criteria) {
        log.debug("REST request to count AvisoTipos by criteria: {}", criteria);
        return ResponseEntity.ok().body(avisoTipoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /aviso-tipos/:id} : get the "id" avisoTipo.
     *
     * @param id the id of the avisoTipoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avisoTipoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aviso-tipos/{id}")
    public ResponseEntity<AvisoTipoDTO> getAvisoTipo(@PathVariable Long id) {
        log.debug("REST request to get AvisoTipo : {}", id);
        Optional<AvisoTipoDTO> avisoTipoDTO = avisoTipoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avisoTipoDTO);
    }

    /**
     * {@code DELETE  /aviso-tipos/:id} : delete the "id" avisoTipo.
     *
     * @param id the id of the avisoTipoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aviso-tipos/{id}")
    public ResponseEntity<Void> deleteAvisoTipo(@PathVariable Long id) {
        log.debug("REST request to delete AvisoTipo : {}", id);
        avisoTipoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
