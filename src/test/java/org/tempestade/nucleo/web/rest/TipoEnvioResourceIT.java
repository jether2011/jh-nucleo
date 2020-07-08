package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.TipoEnvio;
import org.tempestade.nucleo.repository.TipoEnvioRepository;
import org.tempestade.nucleo.service.TipoEnvioService;
import org.tempestade.nucleo.service.dto.TipoEnvioDTO;
import org.tempestade.nucleo.service.mapper.TipoEnvioMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TipoEnvioResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoEnvioResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TipoEnvioRepository tipoEnvioRepository;

    @Autowired
    private TipoEnvioMapper tipoEnvioMapper;

    @Autowired
    private TipoEnvioService tipoEnvioService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoEnvioMockMvc;

    private TipoEnvio tipoEnvio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEnvio createEntity(EntityManager em) {
        TipoEnvio tipoEnvio = new TipoEnvio()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return tipoEnvio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEnvio createUpdatedEntity(EntityManager em) {
        TipoEnvio tipoEnvio = new TipoEnvio()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return tipoEnvio;
    }

    @BeforeEach
    public void initTest() {
        tipoEnvio = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoEnvio() throws Exception {
        int databaseSizeBeforeCreate = tipoEnvioRepository.findAll().size();
        // Create the TipoEnvio
        TipoEnvioDTO tipoEnvioDTO = tipoEnvioMapper.toDto(tipoEnvio);
        restTipoEnvioMockMvc.perform(post("/api/tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoEnvio in the database
        List<TipoEnvio> tipoEnvioList = tipoEnvioRepository.findAll();
        assertThat(tipoEnvioList).hasSize(databaseSizeBeforeCreate + 1);
        TipoEnvio testTipoEnvio = tipoEnvioList.get(tipoEnvioList.size() - 1);
        assertThat(testTipoEnvio.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTipoEnvio.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTipoEnvio.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTipoEnvio.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createTipoEnvioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoEnvioRepository.findAll().size();

        // Create the TipoEnvio with an existing ID
        tipoEnvio.setId(1L);
        TipoEnvioDTO tipoEnvioDTO = tipoEnvioMapper.toDto(tipoEnvio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoEnvioMockMvc.perform(post("/api/tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoEnvio in the database
        List<TipoEnvio> tipoEnvioList = tipoEnvioRepository.findAll();
        assertThat(tipoEnvioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoEnvioRepository.findAll().size();
        // set the field null
        tipoEnvio.setName(null);

        // Create the TipoEnvio, which fails.
        TipoEnvioDTO tipoEnvioDTO = tipoEnvioMapper.toDto(tipoEnvio);


        restTipoEnvioMockMvc.perform(post("/api/tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        List<TipoEnvio> tipoEnvioList = tipoEnvioRepository.findAll();
        assertThat(tipoEnvioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoEnvioRepository.findAll().size();
        // set the field null
        tipoEnvio.setDescricao(null);

        // Create the TipoEnvio, which fails.
        TipoEnvioDTO tipoEnvioDTO = tipoEnvioMapper.toDto(tipoEnvio);


        restTipoEnvioMockMvc.perform(post("/api/tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        List<TipoEnvio> tipoEnvioList = tipoEnvioRepository.findAll();
        assertThat(tipoEnvioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoEnvioRepository.findAll().size();
        // set the field null
        tipoEnvio.setCreated(null);

        // Create the TipoEnvio, which fails.
        TipoEnvioDTO tipoEnvioDTO = tipoEnvioMapper.toDto(tipoEnvio);


        restTipoEnvioMockMvc.perform(post("/api/tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        List<TipoEnvio> tipoEnvioList = tipoEnvioRepository.findAll();
        assertThat(tipoEnvioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoEnvios() throws Exception {
        // Initialize the database
        tipoEnvioRepository.saveAndFlush(tipoEnvio);

        // Get all the tipoEnvioList
        restTipoEnvioMockMvc.perform(get("/api/tipo-envios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoEnvio.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoEnvio() throws Exception {
        // Initialize the database
        tipoEnvioRepository.saveAndFlush(tipoEnvio);

        // Get the tipoEnvio
        restTipoEnvioMockMvc.perform(get("/api/tipo-envios/{id}", tipoEnvio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoEnvio.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTipoEnvio() throws Exception {
        // Get the tipoEnvio
        restTipoEnvioMockMvc.perform(get("/api/tipo-envios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoEnvio() throws Exception {
        // Initialize the database
        tipoEnvioRepository.saveAndFlush(tipoEnvio);

        int databaseSizeBeforeUpdate = tipoEnvioRepository.findAll().size();

        // Update the tipoEnvio
        TipoEnvio updatedTipoEnvio = tipoEnvioRepository.findById(tipoEnvio.getId()).get();
        // Disconnect from session so that the updates on updatedTipoEnvio are not directly saved in db
        em.detach(updatedTipoEnvio);
        updatedTipoEnvio
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        TipoEnvioDTO tipoEnvioDTO = tipoEnvioMapper.toDto(updatedTipoEnvio);

        restTipoEnvioMockMvc.perform(put("/api/tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioDTO)))
            .andExpect(status().isOk());

        // Validate the TipoEnvio in the database
        List<TipoEnvio> tipoEnvioList = tipoEnvioRepository.findAll();
        assertThat(tipoEnvioList).hasSize(databaseSizeBeforeUpdate);
        TipoEnvio testTipoEnvio = tipoEnvioList.get(tipoEnvioList.size() - 1);
        assertThat(testTipoEnvio.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTipoEnvio.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTipoEnvio.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTipoEnvio.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoEnvio() throws Exception {
        int databaseSizeBeforeUpdate = tipoEnvioRepository.findAll().size();

        // Create the TipoEnvio
        TipoEnvioDTO tipoEnvioDTO = tipoEnvioMapper.toDto(tipoEnvio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoEnvioMockMvc.perform(put("/api/tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoEnvio in the database
        List<TipoEnvio> tipoEnvioList = tipoEnvioRepository.findAll();
        assertThat(tipoEnvioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoEnvio() throws Exception {
        // Initialize the database
        tipoEnvioRepository.saveAndFlush(tipoEnvio);

        int databaseSizeBeforeDelete = tipoEnvioRepository.findAll().size();

        // Delete the tipoEnvio
        restTipoEnvioMockMvc.perform(delete("/api/tipo-envios/{id}", tipoEnvio.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoEnvio> tipoEnvioList = tipoEnvioRepository.findAll();
        assertThat(tipoEnvioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
