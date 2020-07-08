package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.ContatoTipoEnvioService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.ContatoTipoEnvioDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.ContatoTipoEnvio}.
 */
@RestController
@RequestMapping("/api")
public class ContatoTipoEnvioResource {

    private final Logger log = LoggerFactory.getLogger(ContatoTipoEnvioResource.class);

    private static final String ENTITY_NAME = "contatoTipoEnvio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContatoTipoEnvioService contatoTipoEnvioService;

    public ContatoTipoEnvioResource(ContatoTipoEnvioService contatoTipoEnvioService) {
        this.contatoTipoEnvioService = contatoTipoEnvioService;
    }

    /**
     * {@code POST  /contato-tipo-envios} : Create a new contatoTipoEnvio.
     *
     * @param contatoTipoEnvioDTO the contatoTipoEnvioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contatoTipoEnvioDTO, or with status {@code 400 (Bad Request)} if the contatoTipoEnvio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contato-tipo-envios")
    public ResponseEntity<ContatoTipoEnvioDTO> createContatoTipoEnvio(@Valid @RequestBody ContatoTipoEnvioDTO contatoTipoEnvioDTO) throws URISyntaxException {
        log.debug("REST request to save ContatoTipoEnvio : {}", contatoTipoEnvioDTO);
        if (contatoTipoEnvioDTO.getId() != null) {
            throw new BadRequestAlertException("A new contatoTipoEnvio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContatoTipoEnvioDTO result = contatoTipoEnvioService.save(contatoTipoEnvioDTO);
        return ResponseEntity.created(new URI("/api/contato-tipo-envios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contato-tipo-envios} : Updates an existing contatoTipoEnvio.
     *
     * @param contatoTipoEnvioDTO the contatoTipoEnvioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contatoTipoEnvioDTO,
     * or with status {@code 400 (Bad Request)} if the contatoTipoEnvioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contatoTipoEnvioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contato-tipo-envios")
    public ResponseEntity<ContatoTipoEnvioDTO> updateContatoTipoEnvio(@Valid @RequestBody ContatoTipoEnvioDTO contatoTipoEnvioDTO) throws URISyntaxException {
        log.debug("REST request to update ContatoTipoEnvio : {}", contatoTipoEnvioDTO);
        if (contatoTipoEnvioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContatoTipoEnvioDTO result = contatoTipoEnvioService.save(contatoTipoEnvioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contatoTipoEnvioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contato-tipo-envios} : get all the contatoTipoEnvios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contatoTipoEnvios in body.
     */
    @GetMapping("/contato-tipo-envios")
    public ResponseEntity<List<ContatoTipoEnvioDTO>> getAllContatoTipoEnvios(Pageable pageable) {
        log.debug("REST request to get a page of ContatoTipoEnvios");
        Page<ContatoTipoEnvioDTO> page = contatoTipoEnvioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contato-tipo-envios/:id} : get the "id" contatoTipoEnvio.
     *
     * @param id the id of the contatoTipoEnvioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contatoTipoEnvioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contato-tipo-envios/{id}")
    public ResponseEntity<ContatoTipoEnvioDTO> getContatoTipoEnvio(@PathVariable Long id) {
        log.debug("REST request to get ContatoTipoEnvio : {}", id);
        Optional<ContatoTipoEnvioDTO> contatoTipoEnvioDTO = contatoTipoEnvioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contatoTipoEnvioDTO);
    }

    /**
     * {@code DELETE  /contato-tipo-envios/:id} : delete the "id" contatoTipoEnvio.
     *
     * @param id the id of the contatoTipoEnvioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contato-tipo-envios/{id}")
    public ResponseEntity<Void> deleteContatoTipoEnvio(@PathVariable Long id) {
        log.debug("REST request to delete ContatoTipoEnvio : {}", id);
        contatoTipoEnvioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
