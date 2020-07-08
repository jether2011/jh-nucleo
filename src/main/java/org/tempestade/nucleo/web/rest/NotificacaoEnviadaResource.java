package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.NotificacaoEnviadaService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.NotificacaoEnviadaDTO;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.NotificacaoEnviada}.
 */
@RestController
@RequestMapping("/api")
public class NotificacaoEnviadaResource {

    private final Logger log = LoggerFactory.getLogger(NotificacaoEnviadaResource.class);

    private static final String ENTITY_NAME = "notificacaoEnviada";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificacaoEnviadaService notificacaoEnviadaService;

    public NotificacaoEnviadaResource(NotificacaoEnviadaService notificacaoEnviadaService) {
        this.notificacaoEnviadaService = notificacaoEnviadaService;
    }

    /**
     * {@code POST  /notificacao-enviadas} : Create a new notificacaoEnviada.
     *
     * @param notificacaoEnviadaDTO the notificacaoEnviadaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificacaoEnviadaDTO, or with status {@code 400 (Bad Request)} if the notificacaoEnviada has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notificacao-enviadas")
    public ResponseEntity<NotificacaoEnviadaDTO> createNotificacaoEnviada(@Valid @RequestBody NotificacaoEnviadaDTO notificacaoEnviadaDTO) throws URISyntaxException {
        log.debug("REST request to save NotificacaoEnviada : {}", notificacaoEnviadaDTO);
        if (notificacaoEnviadaDTO.getId() != null) {
            throw new BadRequestAlertException("A new notificacaoEnviada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotificacaoEnviadaDTO result = notificacaoEnviadaService.save(notificacaoEnviadaDTO);
        return ResponseEntity.created(new URI("/api/notificacao-enviadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notificacao-enviadas} : Updates an existing notificacaoEnviada.
     *
     * @param notificacaoEnviadaDTO the notificacaoEnviadaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificacaoEnviadaDTO,
     * or with status {@code 400 (Bad Request)} if the notificacaoEnviadaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificacaoEnviadaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notificacao-enviadas")
    public ResponseEntity<NotificacaoEnviadaDTO> updateNotificacaoEnviada(@Valid @RequestBody NotificacaoEnviadaDTO notificacaoEnviadaDTO) throws URISyntaxException {
        log.debug("REST request to update NotificacaoEnviada : {}", notificacaoEnviadaDTO);
        if (notificacaoEnviadaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NotificacaoEnviadaDTO result = notificacaoEnviadaService.save(notificacaoEnviadaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notificacaoEnviadaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /notificacao-enviadas} : get all the notificacaoEnviadas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notificacaoEnviadas in body.
     */
    @GetMapping("/notificacao-enviadas")
    public ResponseEntity<List<NotificacaoEnviadaDTO>> getAllNotificacaoEnviadas(Pageable pageable) {
        log.debug("REST request to get a page of NotificacaoEnviadas");
        Page<NotificacaoEnviadaDTO> page = notificacaoEnviadaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notificacao-enviadas/:id} : get the "id" notificacaoEnviada.
     *
     * @param id the id of the notificacaoEnviadaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificacaoEnviadaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notificacao-enviadas/{id}")
    public ResponseEntity<NotificacaoEnviadaDTO> getNotificacaoEnviada(@PathVariable Long id) {
        log.debug("REST request to get NotificacaoEnviada : {}", id);
        Optional<NotificacaoEnviadaDTO> notificacaoEnviadaDTO = notificacaoEnviadaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notificacaoEnviadaDTO);
    }

    /**
     * {@code DELETE  /notificacao-enviadas/:id} : delete the "id" notificacaoEnviada.
     *
     * @param id the id of the notificacaoEnviadaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notificacao-enviadas/{id}")
    public ResponseEntity<Void> deleteNotificacaoEnviada(@PathVariable Long id) {
        log.debug("REST request to delete NotificacaoEnviada : {}", id);
        notificacaoEnviadaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
