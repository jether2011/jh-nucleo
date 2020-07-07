package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.VentoVmFaixaService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.VentoVmFaixaDTO;
import org.tempestade.nucleo.service.dto.VentoVmFaixaCriteria;
import org.tempestade.nucleo.service.VentoVmFaixaQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.VentoVmFaixa}.
 */
@RestController
@RequestMapping("/api")
public class VentoVmFaixaResource {

    private final Logger log = LoggerFactory.getLogger(VentoVmFaixaResource.class);

    private static final String ENTITY_NAME = "ventoVmFaixa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VentoVmFaixaService ventoVmFaixaService;

    private final VentoVmFaixaQueryService ventoVmFaixaQueryService;

    public VentoVmFaixaResource(VentoVmFaixaService ventoVmFaixaService, VentoVmFaixaQueryService ventoVmFaixaQueryService) {
        this.ventoVmFaixaService = ventoVmFaixaService;
        this.ventoVmFaixaQueryService = ventoVmFaixaQueryService;
    }

    /**
     * {@code POST  /vento-vm-faixas} : Create a new ventoVmFaixa.
     *
     * @param ventoVmFaixaDTO the ventoVmFaixaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ventoVmFaixaDTO, or with status {@code 400 (Bad Request)} if the ventoVmFaixa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vento-vm-faixas")
    public ResponseEntity<VentoVmFaixaDTO> createVentoVmFaixa(@Valid @RequestBody VentoVmFaixaDTO ventoVmFaixaDTO) throws URISyntaxException {
        log.debug("REST request to save VentoVmFaixa : {}", ventoVmFaixaDTO);
        if (ventoVmFaixaDTO.getId() != null) {
            throw new BadRequestAlertException("A new ventoVmFaixa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VentoVmFaixaDTO result = ventoVmFaixaService.save(ventoVmFaixaDTO);
        return ResponseEntity.created(new URI("/api/vento-vm-faixas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vento-vm-faixas} : Updates an existing ventoVmFaixa.
     *
     * @param ventoVmFaixaDTO the ventoVmFaixaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ventoVmFaixaDTO,
     * or with status {@code 400 (Bad Request)} if the ventoVmFaixaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ventoVmFaixaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vento-vm-faixas")
    public ResponseEntity<VentoVmFaixaDTO> updateVentoVmFaixa(@Valid @RequestBody VentoVmFaixaDTO ventoVmFaixaDTO) throws URISyntaxException {
        log.debug("REST request to update VentoVmFaixa : {}", ventoVmFaixaDTO);
        if (ventoVmFaixaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VentoVmFaixaDTO result = ventoVmFaixaService.save(ventoVmFaixaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ventoVmFaixaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vento-vm-faixas} : get all the ventoVmFaixas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ventoVmFaixas in body.
     */
    @GetMapping("/vento-vm-faixas")
    public ResponseEntity<List<VentoVmFaixaDTO>> getAllVentoVmFaixas(VentoVmFaixaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get VentoVmFaixas by criteria: {}", criteria);
        Page<VentoVmFaixaDTO> page = ventoVmFaixaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vento-vm-faixas/count} : count all the ventoVmFaixas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vento-vm-faixas/count")
    public ResponseEntity<Long> countVentoVmFaixas(VentoVmFaixaCriteria criteria) {
        log.debug("REST request to count VentoVmFaixas by criteria: {}", criteria);
        return ResponseEntity.ok().body(ventoVmFaixaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vento-vm-faixas/:id} : get the "id" ventoVmFaixa.
     *
     * @param id the id of the ventoVmFaixaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ventoVmFaixaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vento-vm-faixas/{id}")
    public ResponseEntity<VentoVmFaixaDTO> getVentoVmFaixa(@PathVariable Long id) {
        log.debug("REST request to get VentoVmFaixa : {}", id);
        Optional<VentoVmFaixaDTO> ventoVmFaixaDTO = ventoVmFaixaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ventoVmFaixaDTO);
    }

    /**
     * {@code DELETE  /vento-vm-faixas/:id} : delete the "id" ventoVmFaixa.
     *
     * @param id the id of the ventoVmFaixaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vento-vm-faixas/{id}")
    public ResponseEntity<Void> deleteVentoVmFaixa(@PathVariable Long id) {
        log.debug("REST request to delete VentoVmFaixa : {}", id);
        ventoVmFaixaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
