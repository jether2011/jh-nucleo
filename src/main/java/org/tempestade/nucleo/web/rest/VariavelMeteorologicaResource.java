package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.VariavelMeteorologicaService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.VariavelMeteorologicaDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.VariavelMeteorologica}.
 */
@RestController
@RequestMapping("/api")
public class VariavelMeteorologicaResource {

    private final Logger log = LoggerFactory.getLogger(VariavelMeteorologicaResource.class);

    private static final String ENTITY_NAME = "variavelMeteorologica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VariavelMeteorologicaService variavelMeteorologicaService;

    public VariavelMeteorologicaResource(VariavelMeteorologicaService variavelMeteorologicaService) {
        this.variavelMeteorologicaService = variavelMeteorologicaService;
    }

    /**
     * {@code POST  /variavel-meteorologicas} : Create a new variavelMeteorologica.
     *
     * @param variavelMeteorologicaDTO the variavelMeteorologicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new variavelMeteorologicaDTO, or with status {@code 400 (Bad Request)} if the variavelMeteorologica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/variavel-meteorologicas")
    public ResponseEntity<VariavelMeteorologicaDTO> createVariavelMeteorologica(@Valid @RequestBody VariavelMeteorologicaDTO variavelMeteorologicaDTO) throws URISyntaxException {
        log.debug("REST request to save VariavelMeteorologica : {}", variavelMeteorologicaDTO);
        if (variavelMeteorologicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new variavelMeteorologica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VariavelMeteorologicaDTO result = variavelMeteorologicaService.save(variavelMeteorologicaDTO);
        return ResponseEntity.created(new URI("/api/variavel-meteorologicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /variavel-meteorologicas} : Updates an existing variavelMeteorologica.
     *
     * @param variavelMeteorologicaDTO the variavelMeteorologicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated variavelMeteorologicaDTO,
     * or with status {@code 400 (Bad Request)} if the variavelMeteorologicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the variavelMeteorologicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/variavel-meteorologicas")
    public ResponseEntity<VariavelMeteorologicaDTO> updateVariavelMeteorologica(@Valid @RequestBody VariavelMeteorologicaDTO variavelMeteorologicaDTO) throws URISyntaxException {
        log.debug("REST request to update VariavelMeteorologica : {}", variavelMeteorologicaDTO);
        if (variavelMeteorologicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VariavelMeteorologicaDTO result = variavelMeteorologicaService.save(variavelMeteorologicaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, variavelMeteorologicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /variavel-meteorologicas} : get all the variavelMeteorologicas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of variavelMeteorologicas in body.
     */
    @GetMapping("/variavel-meteorologicas")
    public ResponseEntity<List<VariavelMeteorologicaDTO>> getAllVariavelMeteorologicas(Pageable pageable) {
        log.debug("REST request to get a page of VariavelMeteorologicas");
        Page<VariavelMeteorologicaDTO> page = variavelMeteorologicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /variavel-meteorologicas/:id} : get the "id" variavelMeteorologica.
     *
     * @param id the id of the variavelMeteorologicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the variavelMeteorologicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/variavel-meteorologicas/{id}")
    public ResponseEntity<VariavelMeteorologicaDTO> getVariavelMeteorologica(@PathVariable Long id) {
        log.debug("REST request to get VariavelMeteorologica : {}", id);
        Optional<VariavelMeteorologicaDTO> variavelMeteorologicaDTO = variavelMeteorologicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(variavelMeteorologicaDTO);
    }

    /**
     * {@code DELETE  /variavel-meteorologicas/:id} : delete the "id" variavelMeteorologica.
     *
     * @param id the id of the variavelMeteorologicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/variavel-meteorologicas/{id}")
    public ResponseEntity<Void> deleteVariavelMeteorologica(@PathVariable Long id) {
        log.debug("REST request to delete VariavelMeteorologica : {}", id);
        variavelMeteorologicaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
