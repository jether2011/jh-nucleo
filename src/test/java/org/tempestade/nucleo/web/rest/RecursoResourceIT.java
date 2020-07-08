package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Recurso;
import org.tempestade.nucleo.repository.RecursoRepository;
import org.tempestade.nucleo.service.RecursoService;
import org.tempestade.nucleo.service.dto.RecursoDTO;
import org.tempestade.nucleo.service.mapper.RecursoMapper;

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
 * Integration tests for the {@link RecursoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecursoResourceIT {

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
    private RecursoRepository recursoRepository;

    @Autowired
    private RecursoMapper recursoMapper;

    @Autowired
    private RecursoService recursoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecursoMockMvc;

    private Recurso recurso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recurso createEntity(EntityManager em) {
        Recurso recurso = new Recurso()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .ativo(DEFAULT_ATIVO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return recurso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recurso createUpdatedEntity(EntityManager em) {
        Recurso recurso = new Recurso()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .ativo(UPDATED_ATIVO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return recurso;
    }

    @BeforeEach
    public void initTest() {
        recurso = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecurso() throws Exception {
        int databaseSizeBeforeCreate = recursoRepository.findAll().size();
        // Create the Recurso
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);
        restRecursoMockMvc.perform(post("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoDTO)))
            .andExpect(status().isCreated());

        // Validate the Recurso in the database
        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeCreate + 1);
        Recurso testRecurso = recursoList.get(recursoList.size() - 1);
        assertThat(testRecurso.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRecurso.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testRecurso.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testRecurso.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testRecurso.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createRecursoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recursoRepository.findAll().size();

        // Create the Recurso with an existing ID
        recurso.setId(1L);
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecursoMockMvc.perform(post("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recurso in the database
        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoRepository.findAll().size();
        // set the field null
        recurso.setName(null);

        // Create the Recurso, which fails.
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);


        restRecursoMockMvc.perform(post("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoDTO)))
            .andExpect(status().isBadRequest());

        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoRepository.findAll().size();
        // set the field null
        recurso.setDescricao(null);

        // Create the Recurso, which fails.
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);


        restRecursoMockMvc.perform(post("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoDTO)))
            .andExpect(status().isBadRequest());

        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoRepository.findAll().size();
        // set the field null
        recurso.setCreated(null);

        // Create the Recurso, which fails.
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);


        restRecursoMockMvc.perform(post("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoDTO)))
            .andExpect(status().isBadRequest());

        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecursos() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList
        restRecursoMockMvc.perform(get("/api/recursos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getRecurso() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get the recurso
        restRecursoMockMvc.perform(get("/api/recursos/{id}", recurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recurso.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRecurso() throws Exception {
        // Get the recurso
        restRecursoMockMvc.perform(get("/api/recursos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecurso() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        int databaseSizeBeforeUpdate = recursoRepository.findAll().size();

        // Update the recurso
        Recurso updatedRecurso = recursoRepository.findById(recurso.getId()).get();
        // Disconnect from session so that the updates on updatedRecurso are not directly saved in db
        em.detach(updatedRecurso);
        updatedRecurso
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .ativo(UPDATED_ATIVO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        RecursoDTO recursoDTO = recursoMapper.toDto(updatedRecurso);

        restRecursoMockMvc.perform(put("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoDTO)))
            .andExpect(status().isOk());

        // Validate the Recurso in the database
        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeUpdate);
        Recurso testRecurso = recursoList.get(recursoList.size() - 1);
        assertThat(testRecurso.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRecurso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testRecurso.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testRecurso.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testRecurso.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingRecurso() throws Exception {
        int databaseSizeBeforeUpdate = recursoRepository.findAll().size();

        // Create the Recurso
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecursoMockMvc.perform(put("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recurso in the database
        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecurso() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        int databaseSizeBeforeDelete = recursoRepository.findAll().size();

        // Delete the recurso
        restRecursoMockMvc.perform(delete("/api/recursos/{id}", recurso.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
