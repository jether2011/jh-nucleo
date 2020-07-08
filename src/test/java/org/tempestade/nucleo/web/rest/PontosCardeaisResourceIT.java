package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.PontosCardeais;
import org.tempestade.nucleo.repository.PontosCardeaisRepository;
import org.tempestade.nucleo.service.PontosCardeaisService;
import org.tempestade.nucleo.service.dto.PontosCardeaisDTO;
import org.tempestade.nucleo.service.mapper.PontosCardeaisMapper;

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
 * Integration tests for the {@link PontosCardeaisResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PontosCardeaisResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PontosCardeaisRepository pontosCardeaisRepository;

    @Autowired
    private PontosCardeaisMapper pontosCardeaisMapper;

    @Autowired
    private PontosCardeaisService pontosCardeaisService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPontosCardeaisMockMvc;

    private PontosCardeais pontosCardeais;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PontosCardeais createEntity(EntityManager em) {
        PontosCardeais pontosCardeais = new PontosCardeais()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return pontosCardeais;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PontosCardeais createUpdatedEntity(EntityManager em) {
        PontosCardeais pontosCardeais = new PontosCardeais()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return pontosCardeais;
    }

    @BeforeEach
    public void initTest() {
        pontosCardeais = createEntity(em);
    }

    @Test
    @Transactional
    public void createPontosCardeais() throws Exception {
        int databaseSizeBeforeCreate = pontosCardeaisRepository.findAll().size();
        // Create the PontosCardeais
        PontosCardeaisDTO pontosCardeaisDTO = pontosCardeaisMapper.toDto(pontosCardeais);
        restPontosCardeaisMockMvc.perform(post("/api/pontos-cardeais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pontosCardeaisDTO)))
            .andExpect(status().isCreated());

        // Validate the PontosCardeais in the database
        List<PontosCardeais> pontosCardeaisList = pontosCardeaisRepository.findAll();
        assertThat(pontosCardeaisList).hasSize(databaseSizeBeforeCreate + 1);
        PontosCardeais testPontosCardeais = pontosCardeaisList.get(pontosCardeaisList.size() - 1);
        assertThat(testPontosCardeais.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPontosCardeais.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPontosCardeais.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPontosCardeais.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createPontosCardeaisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pontosCardeaisRepository.findAll().size();

        // Create the PontosCardeais with an existing ID
        pontosCardeais.setId(1L);
        PontosCardeaisDTO pontosCardeaisDTO = pontosCardeaisMapper.toDto(pontosCardeais);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPontosCardeaisMockMvc.perform(post("/api/pontos-cardeais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pontosCardeaisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PontosCardeais in the database
        List<PontosCardeais> pontosCardeaisList = pontosCardeaisRepository.findAll();
        assertThat(pontosCardeaisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pontosCardeaisRepository.findAll().size();
        // set the field null
        pontosCardeais.setName(null);

        // Create the PontosCardeais, which fails.
        PontosCardeaisDTO pontosCardeaisDTO = pontosCardeaisMapper.toDto(pontosCardeais);


        restPontosCardeaisMockMvc.perform(post("/api/pontos-cardeais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pontosCardeaisDTO)))
            .andExpect(status().isBadRequest());

        List<PontosCardeais> pontosCardeaisList = pontosCardeaisRepository.findAll();
        assertThat(pontosCardeaisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pontosCardeaisRepository.findAll().size();
        // set the field null
        pontosCardeais.setDescricao(null);

        // Create the PontosCardeais, which fails.
        PontosCardeaisDTO pontosCardeaisDTO = pontosCardeaisMapper.toDto(pontosCardeais);


        restPontosCardeaisMockMvc.perform(post("/api/pontos-cardeais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pontosCardeaisDTO)))
            .andExpect(status().isBadRequest());

        List<PontosCardeais> pontosCardeaisList = pontosCardeaisRepository.findAll();
        assertThat(pontosCardeaisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = pontosCardeaisRepository.findAll().size();
        // set the field null
        pontosCardeais.setCreated(null);

        // Create the PontosCardeais, which fails.
        PontosCardeaisDTO pontosCardeaisDTO = pontosCardeaisMapper.toDto(pontosCardeais);


        restPontosCardeaisMockMvc.perform(post("/api/pontos-cardeais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pontosCardeaisDTO)))
            .andExpect(status().isBadRequest());

        List<PontosCardeais> pontosCardeaisList = pontosCardeaisRepository.findAll();
        assertThat(pontosCardeaisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPontosCardeais() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList
        restPontosCardeaisMockMvc.perform(get("/api/pontos-cardeais?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pontosCardeais.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getPontosCardeais() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get the pontosCardeais
        restPontosCardeaisMockMvc.perform(get("/api/pontos-cardeais/{id}", pontosCardeais.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pontosCardeais.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPontosCardeais() throws Exception {
        // Get the pontosCardeais
        restPontosCardeaisMockMvc.perform(get("/api/pontos-cardeais/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePontosCardeais() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        int databaseSizeBeforeUpdate = pontosCardeaisRepository.findAll().size();

        // Update the pontosCardeais
        PontosCardeais updatedPontosCardeais = pontosCardeaisRepository.findById(pontosCardeais.getId()).get();
        // Disconnect from session so that the updates on updatedPontosCardeais are not directly saved in db
        em.detach(updatedPontosCardeais);
        updatedPontosCardeais
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        PontosCardeaisDTO pontosCardeaisDTO = pontosCardeaisMapper.toDto(updatedPontosCardeais);

        restPontosCardeaisMockMvc.perform(put("/api/pontos-cardeais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pontosCardeaisDTO)))
            .andExpect(status().isOk());

        // Validate the PontosCardeais in the database
        List<PontosCardeais> pontosCardeaisList = pontosCardeaisRepository.findAll();
        assertThat(pontosCardeaisList).hasSize(databaseSizeBeforeUpdate);
        PontosCardeais testPontosCardeais = pontosCardeaisList.get(pontosCardeaisList.size() - 1);
        assertThat(testPontosCardeais.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPontosCardeais.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPontosCardeais.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPontosCardeais.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPontosCardeais() throws Exception {
        int databaseSizeBeforeUpdate = pontosCardeaisRepository.findAll().size();

        // Create the PontosCardeais
        PontosCardeaisDTO pontosCardeaisDTO = pontosCardeaisMapper.toDto(pontosCardeais);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPontosCardeaisMockMvc.perform(put("/api/pontos-cardeais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pontosCardeaisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PontosCardeais in the database
        List<PontosCardeais> pontosCardeaisList = pontosCardeaisRepository.findAll();
        assertThat(pontosCardeaisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePontosCardeais() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        int databaseSizeBeforeDelete = pontosCardeaisRepository.findAll().size();

        // Delete the pontosCardeais
        restPontosCardeaisMockMvc.perform(delete("/api/pontos-cardeais/{id}", pontosCardeais.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PontosCardeais> pontosCardeaisList = pontosCardeaisRepository.findAll();
        assertThat(pontosCardeaisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
