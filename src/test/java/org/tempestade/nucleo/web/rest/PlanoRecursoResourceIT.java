package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.PlanoRecurso;
import org.tempestade.nucleo.repository.PlanoRecursoRepository;
import org.tempestade.nucleo.service.PlanoRecursoService;
import org.tempestade.nucleo.service.dto.PlanoRecursoDTO;
import org.tempestade.nucleo.service.mapper.PlanoRecursoMapper;

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
 * Integration tests for the {@link PlanoRecursoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlanoRecursoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PlanoRecursoRepository planoRecursoRepository;

    @Autowired
    private PlanoRecursoMapper planoRecursoMapper;

    @Autowired
    private PlanoRecursoService planoRecursoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoRecursoMockMvc;

    private PlanoRecurso planoRecurso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoRecurso createEntity(EntityManager em) {
        PlanoRecurso planoRecurso = new PlanoRecurso()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .ativo(DEFAULT_ATIVO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return planoRecurso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoRecurso createUpdatedEntity(EntityManager em) {
        PlanoRecurso planoRecurso = new PlanoRecurso()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .ativo(UPDATED_ATIVO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return planoRecurso;
    }

    @BeforeEach
    public void initTest() {
        planoRecurso = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoRecurso() throws Exception {
        int databaseSizeBeforeCreate = planoRecursoRepository.findAll().size();
        // Create the PlanoRecurso
        PlanoRecursoDTO planoRecursoDTO = planoRecursoMapper.toDto(planoRecurso);
        restPlanoRecursoMockMvc.perform(post("/api/plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanoRecurso in the database
        List<PlanoRecurso> planoRecursoList = planoRecursoRepository.findAll();
        assertThat(planoRecursoList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoRecurso testPlanoRecurso = planoRecursoList.get(planoRecursoList.size() - 1);
        assertThat(testPlanoRecurso.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlanoRecurso.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlanoRecurso.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testPlanoRecurso.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPlanoRecurso.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createPlanoRecursoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoRecursoRepository.findAll().size();

        // Create the PlanoRecurso with an existing ID
        planoRecurso.setId(1L);
        PlanoRecursoDTO planoRecursoDTO = planoRecursoMapper.toDto(planoRecurso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoRecursoMockMvc.perform(post("/api/plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoRecurso in the database
        List<PlanoRecurso> planoRecursoList = planoRecursoRepository.findAll();
        assertThat(planoRecursoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRecursoRepository.findAll().size();
        // set the field null
        planoRecurso.setName(null);

        // Create the PlanoRecurso, which fails.
        PlanoRecursoDTO planoRecursoDTO = planoRecursoMapper.toDto(planoRecurso);


        restPlanoRecursoMockMvc.perform(post("/api/plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRecurso> planoRecursoList = planoRecursoRepository.findAll();
        assertThat(planoRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRecursoRepository.findAll().size();
        // set the field null
        planoRecurso.setDescricao(null);

        // Create the PlanoRecurso, which fails.
        PlanoRecursoDTO planoRecursoDTO = planoRecursoMapper.toDto(planoRecurso);


        restPlanoRecursoMockMvc.perform(post("/api/plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRecurso> planoRecursoList = planoRecursoRepository.findAll();
        assertThat(planoRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRecursoRepository.findAll().size();
        // set the field null
        planoRecurso.setCreated(null);

        // Create the PlanoRecurso, which fails.
        PlanoRecursoDTO planoRecursoDTO = planoRecursoMapper.toDto(planoRecurso);


        restPlanoRecursoMockMvc.perform(post("/api/plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRecurso> planoRecursoList = planoRecursoRepository.findAll();
        assertThat(planoRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursos() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList
        restPlanoRecursoMockMvc.perform(get("/api/plano-recursos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getPlanoRecurso() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get the planoRecurso
        restPlanoRecursoMockMvc.perform(get("/api/plano-recursos/{id}", planoRecurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoRecurso.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPlanoRecurso() throws Exception {
        // Get the planoRecurso
        restPlanoRecursoMockMvc.perform(get("/api/plano-recursos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoRecurso() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        int databaseSizeBeforeUpdate = planoRecursoRepository.findAll().size();

        // Update the planoRecurso
        PlanoRecurso updatedPlanoRecurso = planoRecursoRepository.findById(planoRecurso.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoRecurso are not directly saved in db
        em.detach(updatedPlanoRecurso);
        updatedPlanoRecurso
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .ativo(UPDATED_ATIVO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        PlanoRecursoDTO planoRecursoDTO = planoRecursoMapper.toDto(updatedPlanoRecurso);

        restPlanoRecursoMockMvc.perform(put("/api/plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoDTO)))
            .andExpect(status().isOk());

        // Validate the PlanoRecurso in the database
        List<PlanoRecurso> planoRecursoList = planoRecursoRepository.findAll();
        assertThat(planoRecursoList).hasSize(databaseSizeBeforeUpdate);
        PlanoRecurso testPlanoRecurso = planoRecursoList.get(planoRecursoList.size() - 1);
        assertThat(testPlanoRecurso.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanoRecurso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoRecurso.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testPlanoRecurso.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPlanoRecurso.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = planoRecursoRepository.findAll().size();

        // Create the PlanoRecurso
        PlanoRecursoDTO planoRecursoDTO = planoRecursoMapper.toDto(planoRecurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoRecursoMockMvc.perform(put("/api/plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoRecurso in the database
        List<PlanoRecurso> planoRecursoList = planoRecursoRepository.findAll();
        assertThat(planoRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanoRecurso() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        int databaseSizeBeforeDelete = planoRecursoRepository.findAll().size();

        // Delete the planoRecurso
        restPlanoRecursoMockMvc.perform(delete("/api/plano-recursos/{id}", planoRecurso.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoRecurso> planoRecursoList = planoRecursoRepository.findAll();
        assertThat(planoRecursoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
