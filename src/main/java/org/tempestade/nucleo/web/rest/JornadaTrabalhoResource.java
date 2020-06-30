package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.service.JornadaTrabalhoService;
import org.tempestade.nucleo.web.rest.errors.BadRequestAlertException;
import org.tempestade.nucleo.service.dto.JornadaTrabalhoDTO;
import org.tempestade.nucleo.service.dto.JornadaTrabalhoCriteria;
import org.tempestade.nucleo.service.JornadaTrabalhoQueryService;

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
 * REST controller for managing {@link org.tempestade.nucleo.domain.JornadaTrabalho}.
 */
@RestController
@RequestMapping("/api")
public class JornadaTrabalhoResource {

    private final Logger log = LoggerFactory.getLogger(JornadaTrabalhoResource.class);

    private static final String ENTITY_NAME = "jornadaTrabalho";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JornadaTrabalhoService jornadaTrabalhoService;

    private final JornadaTrabalhoQueryService jornadaTrabalhoQueryService;

    public JornadaTrabalhoResource(JornadaTrabalhoService jornadaTrabalhoService, JornadaTrabalhoQueryService jornadaTrabalhoQueryService) {
        this.jornadaTrabalhoService = jornadaTrabalhoService;
        this.jornadaTrabalhoQueryService = jornadaTrabalhoQueryService;
    }

    /**
     * {@code POST  /jornada-trabalhos} : Create a new jornadaTrabalho.
     *
     * @param jornadaTrabalhoDTO the jornadaTrabalhoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jornadaTrabalhoDTO, or with status {@code 400 (Bad Request)} if the jornadaTrabalho has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jornada-trabalhos")
    public ResponseEntity<JornadaTrabalhoDTO> createJornadaTrabalho(@Valid @RequestBody JornadaTrabalhoDTO jornadaTrabalhoDTO) throws URISyntaxException {
        log.debug("REST request to save JornadaTrabalho : {}", jornadaTrabalhoDTO);
        if (jornadaTrabalhoDTO.getId() != null) {
            throw new BadRequestAlertException("A new jornadaTrabalho cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JornadaTrabalhoDTO result = jornadaTrabalhoService.save(jornadaTrabalhoDTO);
        return ResponseEntity.created(new URI("/api/jornada-trabalhos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jornada-trabalhos} : Updates an existing jornadaTrabalho.
     *
     * @param jornadaTrabalhoDTO the jornadaTrabalhoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jornadaTrabalhoDTO,
     * or with status {@code 400 (Bad Request)} if the jornadaTrabalhoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jornadaTrabalhoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jornada-trabalhos")
    public ResponseEntity<JornadaTrabalhoDTO> updateJornadaTrabalho(@Valid @RequestBody JornadaTrabalhoDTO jornadaTrabalhoDTO) throws URISyntaxException {
        log.debug("REST request to update JornadaTrabalho : {}", jornadaTrabalhoDTO);
        if (jornadaTrabalhoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JornadaTrabalhoDTO result = jornadaTrabalhoService.save(jornadaTrabalhoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jornadaTrabalhoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /jornada-trabalhos} : get all the jornadaTrabalhos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jornadaTrabalhos in body.
     */
    @GetMapping("/jornada-trabalhos")
    public ResponseEntity<List<JornadaTrabalhoDTO>> getAllJornadaTrabalhos(JornadaTrabalhoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get JornadaTrabalhos by criteria: {}", criteria);
        Page<JornadaTrabalhoDTO> page = jornadaTrabalhoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /jornada-trabalhos/count} : count all the jornadaTrabalhos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/jornada-trabalhos/count")
    public ResponseEntity<Long> countJornadaTrabalhos(JornadaTrabalhoCriteria criteria) {
        log.debug("REST request to count JornadaTrabalhos by criteria: {}", criteria);
        return ResponseEntity.ok().body(jornadaTrabalhoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /jornada-trabalhos/:id} : get the "id" jornadaTrabalho.
     *
     * @param id the id of the jornadaTrabalhoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jornadaTrabalhoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jornada-trabalhos/{id}")
    public ResponseEntity<JornadaTrabalhoDTO> getJornadaTrabalho(@PathVariable Long id) {
        log.debug("REST request to get JornadaTrabalho : {}", id);
        Optional<JornadaTrabalhoDTO> jornadaTrabalhoDTO = jornadaTrabalhoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jornadaTrabalhoDTO);
    }

    /**
     * {@code DELETE  /jornada-trabalhos/:id} : delete the "id" jornadaTrabalho.
     *
     * @param id the id of the jornadaTrabalhoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jornada-trabalhos/{id}")
    public ResponseEntity<Void> deleteJornadaTrabalho(@PathVariable Long id) {
        log.debug("REST request to delete JornadaTrabalho : {}", id);
        jornadaTrabalhoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
