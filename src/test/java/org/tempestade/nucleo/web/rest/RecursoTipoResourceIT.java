package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.RecursoTipo;
import org.tempestade.nucleo.repository.RecursoTipoRepository;
import org.tempestade.nucleo.service.RecursoTipoService;
import org.tempestade.nucleo.service.dto.RecursoTipoDTO;
import org.tempestade.nucleo.service.mapper.RecursoTipoMapper;

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
 * Integration tests for the {@link RecursoTipoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecursoTipoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RecursoTipoRepository recursoTipoRepository;

    @Autowired
    private RecursoTipoMapper recursoTipoMapper;

    @Autowired
    private RecursoTipoService recursoTipoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecursoTipoMockMvc;

    private RecursoTipo recursoTipo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecursoTipo createEntity(EntityManager em) {
        RecursoTipo recursoTipo = new RecursoTipo()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return recursoTipo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecursoTipo createUpdatedEntity(EntityManager em) {
        RecursoTipo recursoTipo = new RecursoTipo()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return recursoTipo;
    }

    @BeforeEach
    public void initTest() {
        recursoTipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecursoTipo() throws Exception {
        int databaseSizeBeforeCreate = recursoTipoRepository.findAll().size();
        // Create the RecursoTipo
        RecursoTipoDTO recursoTipoDTO = recursoTipoMapper.toDto(recursoTipo);
        restRecursoTipoMockMvc.perform(post("/api/recurso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoTipoDTO)))
            .andExpect(status().isCreated());

        // Validate the RecursoTipo in the database
        List<RecursoTipo> recursoTipoList = recursoTipoRepository.findAll();
        assertThat(recursoTipoList).hasSize(databaseSizeBeforeCreate + 1);
        RecursoTipo testRecursoTipo = recursoTipoList.get(recursoTipoList.size() - 1);
        assertThat(testRecursoTipo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRecursoTipo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testRecursoTipo.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testRecursoTipo.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createRecursoTipoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recursoTipoRepository.findAll().size();

        // Create the RecursoTipo with an existing ID
        recursoTipo.setId(1L);
        RecursoTipoDTO recursoTipoDTO = recursoTipoMapper.toDto(recursoTipo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecursoTipoMockMvc.perform(post("/api/recurso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoTipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecursoTipo in the database
        List<RecursoTipo> recursoTipoList = recursoTipoRepository.findAll();
        assertThat(recursoTipoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoTipoRepository.findAll().size();
        // set the field null
        recursoTipo.setName(null);

        // Create the RecursoTipo, which fails.
        RecursoTipoDTO recursoTipoDTO = recursoTipoMapper.toDto(recursoTipo);


        restRecursoTipoMockMvc.perform(post("/api/recurso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoTipoDTO)))
            .andExpect(status().isBadRequest());

        List<RecursoTipo> recursoTipoList = recursoTipoRepository.findAll();
        assertThat(recursoTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoTipoRepository.findAll().size();
        // set the field null
        recursoTipo.setDescricao(null);

        // Create the RecursoTipo, which fails.
        RecursoTipoDTO recursoTipoDTO = recursoTipoMapper.toDto(recursoTipo);


        restRecursoTipoMockMvc.perform(post("/api/recurso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoTipoDTO)))
            .andExpect(status().isBadRequest());

        List<RecursoTipo> recursoTipoList = recursoTipoRepository.findAll();
        assertThat(recursoTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoTipoRepository.findAll().size();
        // set the field null
        recursoTipo.setCreated(null);

        // Create the RecursoTipo, which fails.
        RecursoTipoDTO recursoTipoDTO = recursoTipoMapper.toDto(recursoTipo);


        restRecursoTipoMockMvc.perform(post("/api/recurso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoTipoDTO)))
            .andExpect(status().isBadRequest());

        List<RecursoTipo> recursoTipoList = recursoTipoRepository.findAll();
        assertThat(recursoTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecursoTipos() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList
        restRecursoTipoMockMvc.perform(get("/api/recurso-tipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recursoTipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getRecursoTipo() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get the recursoTipo
        restRecursoTipoMockMvc.perform(get("/api/recurso-tipos/{id}", recursoTipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recursoTipo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRecursoTipo() throws Exception {
        // Get the recursoTipo
        restRecursoTipoMockMvc.perform(get("/api/recurso-tipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecursoTipo() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        int databaseSizeBeforeUpdate = recursoTipoRepository.findAll().size();

        // Update the recursoTipo
        RecursoTipo updatedRecursoTipo = recursoTipoRepository.findById(recursoTipo.getId()).get();
        // Disconnect from session so that the updates on updatedRecursoTipo are not directly saved in db
        em.detach(updatedRecursoTipo);
        updatedRecursoTipo
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        RecursoTipoDTO recursoTipoDTO = recursoTipoMapper.toDto(updatedRecursoTipo);

        restRecursoTipoMockMvc.perform(put("/api/recurso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoTipoDTO)))
            .andExpect(status().isOk());

        // Validate the RecursoTipo in the database
        List<RecursoTipo> recursoTipoList = recursoTipoRepository.findAll();
        assertThat(recursoTipoList).hasSize(databaseSizeBeforeUpdate);
        RecursoTipo testRecursoTipo = recursoTipoList.get(recursoTipoList.size() - 1);
        assertThat(testRecursoTipo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRecursoTipo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testRecursoTipo.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testRecursoTipo.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingRecursoTipo() throws Exception {
        int databaseSizeBeforeUpdate = recursoTipoRepository.findAll().size();

        // Create the RecursoTipo
        RecursoTipoDTO recursoTipoDTO = recursoTipoMapper.toDto(recursoTipo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecursoTipoMockMvc.perform(put("/api/recurso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoTipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecursoTipo in the database
        List<RecursoTipo> recursoTipoList = recursoTipoRepository.findAll();
        assertThat(recursoTipoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecursoTipo() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        int databaseSizeBeforeDelete = recursoTipoRepository.findAll().size();

        // Delete the recursoTipo
        restRecursoTipoMockMvc.perform(delete("/api/recurso-tipos/{id}", recursoTipo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecursoTipo> recursoTipoList = recursoTipoRepository.findAll();
        assertThat(recursoTipoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
