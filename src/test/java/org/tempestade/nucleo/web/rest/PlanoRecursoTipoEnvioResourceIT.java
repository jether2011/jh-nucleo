package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.PlanoRecursoTipoEnvio;
import org.tempestade.nucleo.repository.PlanoRecursoTipoEnvioRepository;
import org.tempestade.nucleo.service.PlanoRecursoTipoEnvioService;
import org.tempestade.nucleo.service.dto.PlanoRecursoTipoEnvioDTO;
import org.tempestade.nucleo.service.mapper.PlanoRecursoTipoEnvioMapper;

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
 * Integration tests for the {@link PlanoRecursoTipoEnvioResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlanoRecursoTipoEnvioResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_QTD = 1;
    private static final Integer UPDATED_QTD = 2;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PlanoRecursoTipoEnvioRepository planoRecursoTipoEnvioRepository;

    @Autowired
    private PlanoRecursoTipoEnvioMapper planoRecursoTipoEnvioMapper;

    @Autowired
    private PlanoRecursoTipoEnvioService planoRecursoTipoEnvioService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoRecursoTipoEnvioMockMvc;

    private PlanoRecursoTipoEnvio planoRecursoTipoEnvio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoRecursoTipoEnvio createEntity(EntityManager em) {
        PlanoRecursoTipoEnvio planoRecursoTipoEnvio = new PlanoRecursoTipoEnvio()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .qtd(DEFAULT_QTD)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return planoRecursoTipoEnvio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoRecursoTipoEnvio createUpdatedEntity(EntityManager em) {
        PlanoRecursoTipoEnvio planoRecursoTipoEnvio = new PlanoRecursoTipoEnvio()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .qtd(UPDATED_QTD)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return planoRecursoTipoEnvio;
    }

    @BeforeEach
    public void initTest() {
        planoRecursoTipoEnvio = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoRecursoTipoEnvio() throws Exception {
        int databaseSizeBeforeCreate = planoRecursoTipoEnvioRepository.findAll().size();
        // Create the PlanoRecursoTipoEnvio
        PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO = planoRecursoTipoEnvioMapper.toDto(planoRecursoTipoEnvio);
        restPlanoRecursoTipoEnvioMockMvc.perform(post("/api/plano-recurso-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoTipoEnvioDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanoRecursoTipoEnvio in the database
        List<PlanoRecursoTipoEnvio> planoRecursoTipoEnvioList = planoRecursoTipoEnvioRepository.findAll();
        assertThat(planoRecursoTipoEnvioList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoRecursoTipoEnvio testPlanoRecursoTipoEnvio = planoRecursoTipoEnvioList.get(planoRecursoTipoEnvioList.size() - 1);
        assertThat(testPlanoRecursoTipoEnvio.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlanoRecursoTipoEnvio.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlanoRecursoTipoEnvio.getQtd()).isEqualTo(DEFAULT_QTD);
        assertThat(testPlanoRecursoTipoEnvio.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPlanoRecursoTipoEnvio.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createPlanoRecursoTipoEnvioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoRecursoTipoEnvioRepository.findAll().size();

        // Create the PlanoRecursoTipoEnvio with an existing ID
        planoRecursoTipoEnvio.setId(1L);
        PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO = planoRecursoTipoEnvioMapper.toDto(planoRecursoTipoEnvio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoRecursoTipoEnvioMockMvc.perform(post("/api/plano-recurso-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoRecursoTipoEnvio in the database
        List<PlanoRecursoTipoEnvio> planoRecursoTipoEnvioList = planoRecursoTipoEnvioRepository.findAll();
        assertThat(planoRecursoTipoEnvioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRecursoTipoEnvioRepository.findAll().size();
        // set the field null
        planoRecursoTipoEnvio.setName(null);

        // Create the PlanoRecursoTipoEnvio, which fails.
        PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO = planoRecursoTipoEnvioMapper.toDto(planoRecursoTipoEnvio);


        restPlanoRecursoTipoEnvioMockMvc.perform(post("/api/plano-recurso-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRecursoTipoEnvio> planoRecursoTipoEnvioList = planoRecursoTipoEnvioRepository.findAll();
        assertThat(planoRecursoTipoEnvioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRecursoTipoEnvioRepository.findAll().size();
        // set the field null
        planoRecursoTipoEnvio.setDescricao(null);

        // Create the PlanoRecursoTipoEnvio, which fails.
        PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO = planoRecursoTipoEnvioMapper.toDto(planoRecursoTipoEnvio);


        restPlanoRecursoTipoEnvioMockMvc.perform(post("/api/plano-recurso-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRecursoTipoEnvio> planoRecursoTipoEnvioList = planoRecursoTipoEnvioRepository.findAll();
        assertThat(planoRecursoTipoEnvioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRecursoTipoEnvioRepository.findAll().size();
        // set the field null
        planoRecursoTipoEnvio.setCreated(null);

        // Create the PlanoRecursoTipoEnvio, which fails.
        PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO = planoRecursoTipoEnvioMapper.toDto(planoRecursoTipoEnvio);


        restPlanoRecursoTipoEnvioMockMvc.perform(post("/api/plano-recurso-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRecursoTipoEnvio> planoRecursoTipoEnvioList = planoRecursoTipoEnvioRepository.findAll();
        assertThat(planoRecursoTipoEnvioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnvios() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList
        restPlanoRecursoTipoEnvioMockMvc.perform(get("/api/plano-recurso-tipo-envios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoRecursoTipoEnvio.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].qtd").value(hasItem(DEFAULT_QTD)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getPlanoRecursoTipoEnvio() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get the planoRecursoTipoEnvio
        restPlanoRecursoTipoEnvioMockMvc.perform(get("/api/plano-recurso-tipo-envios/{id}", planoRecursoTipoEnvio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoRecursoTipoEnvio.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.qtd").value(DEFAULT_QTD))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPlanoRecursoTipoEnvio() throws Exception {
        // Get the planoRecursoTipoEnvio
        restPlanoRecursoTipoEnvioMockMvc.perform(get("/api/plano-recurso-tipo-envios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoRecursoTipoEnvio() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        int databaseSizeBeforeUpdate = planoRecursoTipoEnvioRepository.findAll().size();

        // Update the planoRecursoTipoEnvio
        PlanoRecursoTipoEnvio updatedPlanoRecursoTipoEnvio = planoRecursoTipoEnvioRepository.findById(planoRecursoTipoEnvio.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoRecursoTipoEnvio are not directly saved in db
        em.detach(updatedPlanoRecursoTipoEnvio);
        updatedPlanoRecursoTipoEnvio
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .qtd(UPDATED_QTD)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO = planoRecursoTipoEnvioMapper.toDto(updatedPlanoRecursoTipoEnvio);

        restPlanoRecursoTipoEnvioMockMvc.perform(put("/api/plano-recurso-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoTipoEnvioDTO)))
            .andExpect(status().isOk());

        // Validate the PlanoRecursoTipoEnvio in the database
        List<PlanoRecursoTipoEnvio> planoRecursoTipoEnvioList = planoRecursoTipoEnvioRepository.findAll();
        assertThat(planoRecursoTipoEnvioList).hasSize(databaseSizeBeforeUpdate);
        PlanoRecursoTipoEnvio testPlanoRecursoTipoEnvio = planoRecursoTipoEnvioList.get(planoRecursoTipoEnvioList.size() - 1);
        assertThat(testPlanoRecursoTipoEnvio.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanoRecursoTipoEnvio.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoRecursoTipoEnvio.getQtd()).isEqualTo(UPDATED_QTD);
        assertThat(testPlanoRecursoTipoEnvio.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPlanoRecursoTipoEnvio.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoRecursoTipoEnvio() throws Exception {
        int databaseSizeBeforeUpdate = planoRecursoTipoEnvioRepository.findAll().size();

        // Create the PlanoRecursoTipoEnvio
        PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO = planoRecursoTipoEnvioMapper.toDto(planoRecursoTipoEnvio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoRecursoTipoEnvioMockMvc.perform(put("/api/plano-recurso-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoRecursoTipoEnvio in the database
        List<PlanoRecursoTipoEnvio> planoRecursoTipoEnvioList = planoRecursoTipoEnvioRepository.findAll();
        assertThat(planoRecursoTipoEnvioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanoRecursoTipoEnvio() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        int databaseSizeBeforeDelete = planoRecursoTipoEnvioRepository.findAll().size();

        // Delete the planoRecursoTipoEnvio
        restPlanoRecursoTipoEnvioMockMvc.perform(delete("/api/plano-recurso-tipo-envios/{id}", planoRecursoTipoEnvio.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoRecursoTipoEnvio> planoRecursoTipoEnvioList = planoRecursoTipoEnvioRepository.findAll();
        assertThat(planoRecursoTipoEnvioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
