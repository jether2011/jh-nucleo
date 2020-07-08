package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.AcumuladoChuvaFaixaService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.AcumuladoChuvaFaixaDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.AcumuladoChuvaFaixa}.
 */
@RestController
@RequestMapping("/api")
public class AcumuladoChuvaFaixaResource {

    private final Logger log = LoggerFactory.getLogger(AcumuladoChuvaFaixaResource.class);

    private static final String ENTITY_NAME = "acumuladoChuvaFaixa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcumuladoChuvaFaixaService acumuladoChuvaFaixaService;

    public AcumuladoChuvaFaixaResource(AcumuladoChuvaFaixaService acumuladoChuvaFaixaService) {
        this.acumuladoChuvaFaixaService = acumuladoChuvaFaixaService;
    }

    /**
     * {@code POST  /acumulado-chuva-faixas} : Create a new acumuladoChuvaFaixa.
     *
     * @param acumuladoChuvaFaixaDTO the acumuladoChuvaFaixaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new acumuladoChuvaFaixaDTO, or with status {@code 400 (Bad Request)} if the acumuladoChuvaFaixa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/acumulado-chuva-faixas")
    public ResponseEntity<AcumuladoChuvaFaixaDTO> createAcumuladoChuvaFaixa(@Valid @RequestBody AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO) throws URISyntaxException {
        log.debug("REST request to save AcumuladoChuvaFaixa : {}", acumuladoChuvaFaixaDTO);
        if (acumuladoChuvaFaixaDTO.getId() != null) {
            throw new BadRequestAlertException("A new acumuladoChuvaFaixa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcumuladoChuvaFaixaDTO result = acumuladoChuvaFaixaService.save(acumuladoChuvaFaixaDTO);
        return ResponseEntity.created(new URI("/api/acumulado-chuva-faixas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /acumulado-chuva-faixas} : Updates an existing acumuladoChuvaFaixa.
     *
     * @param acumuladoChuvaFaixaDTO the acumuladoChuvaFaixaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acumuladoChuvaFaixaDTO,
     * or with status {@code 400 (Bad Request)} if the acumuladoChuvaFaixaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the acumuladoChuvaFaixaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/acumulado-chuva-faixas")
    public ResponseEntity<AcumuladoChuvaFaixaDTO> updateAcumuladoChuvaFaixa(@Valid @RequestBody AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO) throws URISyntaxException {
        log.debug("REST request to update AcumuladoChuvaFaixa : {}", acumuladoChuvaFaixaDTO);
        if (acumuladoChuvaFaixaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AcumuladoChuvaFaixaDTO result = acumuladoChuvaFaixaService.save(acumuladoChuvaFaixaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, acumuladoChuvaFaixaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /acumulado-chuva-faixas} : get all the acumuladoChuvaFaixas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of acumuladoChuvaFaixas in body.
     */
    @GetMapping("/acumulado-chuva-faixas")
    public ResponseEntity<List<AcumuladoChuvaFaixaDTO>> getAllAcumuladoChuvaFaixas(Pageable pageable) {
        log.debug("REST request to get a page of AcumuladoChuvaFaixas");
        Page<AcumuladoChuvaFaixaDTO> page = acumuladoChuvaFaixaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /acumulado-chuva-faixas/:id} : get the "id" acumuladoChuvaFaixa.
     *
     * @param id the id of the acumuladoChuvaFaixaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the acumuladoChuvaFaixaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/acumulado-chuva-faixas/{id}")
    public ResponseEntity<AcumuladoChuvaFaixaDTO> getAcumuladoChuvaFaixa(@PathVariable Long id) {
        log.debug("REST request to get AcumuladoChuvaFaixa : {}", id);
        Optional<AcumuladoChuvaFaixaDTO> acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(acumuladoChuvaFaixaDTO);
    }

    /**
     * {@code DELETE  /acumulado-chuva-faixas/:id} : delete the "id" acumuladoChuvaFaixa.
     *
     * @param id the id of the acumuladoChuvaFaixaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/acumulado-chuva-faixas/{id}")
    public ResponseEntity<Void> deleteAcumuladoChuvaFaixa(@PathVariable Long id) {
        log.debug("REST request to delete AcumuladoChuvaFaixa : {}", id);
        acumuladoChuvaFaixaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
