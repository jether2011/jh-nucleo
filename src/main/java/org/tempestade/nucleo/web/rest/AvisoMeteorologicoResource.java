package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.AvisoMeteorologicoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.AvisoMeteorologicoDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.AvisoMeteorologico}.
 */
@RestController
@RequestMapping("/api")
public class AvisoMeteorologicoResource {

    private final Logger log = LoggerFactory.getLogger(AvisoMeteorologicoResource.class);

    private static final String ENTITY_NAME = "avisoMeteorologico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvisoMeteorologicoService avisoMeteorologicoService;

    public AvisoMeteorologicoResource(AvisoMeteorologicoService avisoMeteorologicoService) {
        this.avisoMeteorologicoService = avisoMeteorologicoService;
    }

    /**
     * {@code POST  /aviso-meteorologicos} : Create a new avisoMeteorologico.
     *
     * @param avisoMeteorologicoDTO the avisoMeteorologicoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avisoMeteorologicoDTO, or with status {@code 400 (Bad Request)} if the avisoMeteorologico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aviso-meteorologicos")
    public ResponseEntity<AvisoMeteorologicoDTO> createAvisoMeteorologico(@Valid @RequestBody AvisoMeteorologicoDTO avisoMeteorologicoDTO) throws URISyntaxException {
        log.debug("REST request to save AvisoMeteorologico : {}", avisoMeteorologicoDTO);
        if (avisoMeteorologicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new avisoMeteorologico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvisoMeteorologicoDTO result = avisoMeteorologicoService.save(avisoMeteorologicoDTO);
        return ResponseEntity.created(new URI("/api/aviso-meteorologicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aviso-meteorologicos} : Updates an existing avisoMeteorologico.
     *
     * @param avisoMeteorologicoDTO the avisoMeteorologicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avisoMeteorologicoDTO,
     * or with status {@code 400 (Bad Request)} if the avisoMeteorologicoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avisoMeteorologicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aviso-meteorologicos")
    public ResponseEntity<AvisoMeteorologicoDTO> updateAvisoMeteorologico(@Valid @RequestBody AvisoMeteorologicoDTO avisoMeteorologicoDTO) throws URISyntaxException {
        log.debug("REST request to update AvisoMeteorologico : {}", avisoMeteorologicoDTO);
        if (avisoMeteorologicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AvisoMeteorologicoDTO result = avisoMeteorologicoService.save(avisoMeteorologicoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avisoMeteorologicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aviso-meteorologicos} : get all the avisoMeteorologicos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avisoMeteorologicos in body.
     */
    @GetMapping("/aviso-meteorologicos")
    public ResponseEntity<List<AvisoMeteorologicoDTO>> getAllAvisoMeteorologicos(Pageable pageable) {
        log.debug("REST request to get a page of AvisoMeteorologicos");
        Page<AvisoMeteorologicoDTO> page = avisoMeteorologicoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aviso-meteorologicos/:id} : get the "id" avisoMeteorologico.
     *
     * @param id the id of the avisoMeteorologicoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avisoMeteorologicoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aviso-meteorologicos/{id}")
    public ResponseEntity<AvisoMeteorologicoDTO> getAvisoMeteorologico(@PathVariable Long id) {
        log.debug("REST request to get AvisoMeteorologico : {}", id);
        Optional<AvisoMeteorologicoDTO> avisoMeteorologicoDTO = avisoMeteorologicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avisoMeteorologicoDTO);
    }

    /**
     * {@code DELETE  /aviso-meteorologicos/:id} : delete the "id" avisoMeteorologico.
     *
     * @param id the id of the avisoMeteorologicoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aviso-meteorologicos/{id}")
    public ResponseEntity<Void> deleteAvisoMeteorologico(@PathVariable Long id) {
        log.debug("REST request to delete AvisoMeteorologico : {}", id);
        avisoMeteorologicoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
