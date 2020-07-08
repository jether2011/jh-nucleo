package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.RedeService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.RedeDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.Rede}.
 */
@RestController
@RequestMapping("/api")
public class RedeResource {

    private final Logger log = LoggerFactory.getLogger(RedeResource.class);

    private static final String ENTITY_NAME = "rede";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RedeService redeService;

    public RedeResource(RedeService redeService) {
        this.redeService = redeService;
    }

    /**
     * {@code POST  /redes} : Create a new rede.
     *
     * @param redeDTO the redeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new redeDTO, or with status {@code 400 (Bad Request)} if the rede has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/redes")
    public ResponseEntity<RedeDTO> createRede(@Valid @RequestBody RedeDTO redeDTO) throws URISyntaxException {
        log.debug("REST request to save Rede : {}", redeDTO);
        if (redeDTO.getId() != null) {
            throw new BadRequestAlertException("A new rede cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RedeDTO result = redeService.save(redeDTO);
        return ResponseEntity.created(new URI("/api/redes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /redes} : Updates an existing rede.
     *
     * @param redeDTO the redeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated redeDTO,
     * or with status {@code 400 (Bad Request)} if the redeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the redeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/redes")
    public ResponseEntity<RedeDTO> updateRede(@Valid @RequestBody RedeDTO redeDTO) throws URISyntaxException {
        log.debug("REST request to update Rede : {}", redeDTO);
        if (redeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RedeDTO result = redeService.save(redeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, redeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /redes} : get all the redes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of redes in body.
     */
    @GetMapping("/redes")
    public ResponseEntity<List<RedeDTO>> getAllRedes(Pageable pageable) {
        log.debug("REST request to get a page of Redes");
        Page<RedeDTO> page = redeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /redes/:id} : get the "id" rede.
     *
     * @param id the id of the redeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the redeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/redes/{id}")
    public ResponseEntity<RedeDTO> getRede(@PathVariable Long id) {
        log.debug("REST request to get Rede : {}", id);
        Optional<RedeDTO> redeDTO = redeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(redeDTO);
    }

    /**
     * {@code DELETE  /redes/:id} : delete the "id" rede.
     *
     * @param id the id of the redeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/redes/{id}")
    public ResponseEntity<Void> deleteRede(@PathVariable Long id) {
        log.debug("REST request to delete Rede : {}", id);
        redeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
