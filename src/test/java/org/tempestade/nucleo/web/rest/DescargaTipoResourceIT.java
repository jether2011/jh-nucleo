package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.DescargaTipo;
import org.tempestade.nucleo.repository.DescargaTipoRepository;
import org.tempestade.nucleo.service.DescargaTipoService;
import org.tempestade.nucleo.service.dto.DescargaTipoDTO;
import org.tempestade.nucleo.service.mapper.DescargaTipoMapper;

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
 * Integration tests for the {@link DescargaTipoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DescargaTipoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DescargaTipoRepository descargaTipoRepository;

    @Autowired
    private DescargaTipoMapper descargaTipoMapper;

    @Autowired
    private DescargaTipoService descargaTipoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDescargaTipoMockMvc;

    private DescargaTipo descargaTipo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DescargaTipo createEntity(EntityManager em) {
        DescargaTipo descargaTipo = new DescargaTipo()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return descargaTipo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DescargaTipo createUpdatedEntity(EntityManager em) {
        DescargaTipo descargaTipo = new DescargaTipo()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return descargaTipo;
    }

    @BeforeEach
    public void initTest() {
        descargaTipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createDescargaTipo() throws Exception {
        int databaseSizeBeforeCreate = descargaTipoRepository.findAll().size();
        // Create the DescargaTipo
        DescargaTipoDTO descargaTipoDTO = descargaTipoMapper.toDto(descargaTipo);
        restDescargaTipoMockMvc.perform(post("/api/descarga-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaTipoDTO)))
            .andExpect(status().isCreated());

        // Validate the DescargaTipo in the database
        List<DescargaTipo> descargaTipoList = descargaTipoRepository.findAll();
        assertThat(descargaTipoList).hasSize(databaseSizeBeforeCreate + 1);
        DescargaTipo testDescargaTipo = descargaTipoList.get(descargaTipoList.size() - 1);
        assertThat(testDescargaTipo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDescargaTipo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDescargaTipo.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testDescargaTipo.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createDescargaTipoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = descargaTipoRepository.findAll().size();

        // Create the DescargaTipo with an existing ID
        descargaTipo.setId(1L);
        DescargaTipoDTO descargaTipoDTO = descargaTipoMapper.toDto(descargaTipo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDescargaTipoMockMvc.perform(post("/api/descarga-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaTipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DescargaTipo in the database
        List<DescargaTipo> descargaTipoList = descargaTipoRepository.findAll();
        assertThat(descargaTipoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = descargaTipoRepository.findAll().size();
        // set the field null
        descargaTipo.setNome(null);

        // Create the DescargaTipo, which fails.
        DescargaTipoDTO descargaTipoDTO = descargaTipoMapper.toDto(descargaTipo);


        restDescargaTipoMockMvc.perform(post("/api/descarga-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaTipoDTO)))
            .andExpect(status().isBadRequest());

        List<DescargaTipo> descargaTipoList = descargaTipoRepository.findAll();
        assertThat(descargaTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = descargaTipoRepository.findAll().size();
        // set the field null
        descargaTipo.setCreated(null);

        // Create the DescargaTipo, which fails.
        DescargaTipoDTO descargaTipoDTO = descargaTipoMapper.toDto(descargaTipo);


        restDescargaTipoMockMvc.perform(post("/api/descarga-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaTipoDTO)))
            .andExpect(status().isBadRequest());

        List<DescargaTipo> descargaTipoList = descargaTipoRepository.findAll();
        assertThat(descargaTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDescargaTipos() throws Exception {
        // Initialize the database
        descargaTipoRepository.saveAndFlush(descargaTipo);

        // Get all the descargaTipoList
        restDescargaTipoMockMvc.perform(get("/api/descarga-tipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(descargaTipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getDescargaTipo() throws Exception {
        // Initialize the database
        descargaTipoRepository.saveAndFlush(descargaTipo);

        // Get the descargaTipo
        restDescargaTipoMockMvc.perform(get("/api/descarga-tipos/{id}", descargaTipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(descargaTipo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDescargaTipo() throws Exception {
        // Get the descargaTipo
        restDescargaTipoMockMvc.perform(get("/api/descarga-tipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDescargaTipo() throws Exception {
        // Initialize the database
        descargaTipoRepository.saveAndFlush(descargaTipo);

        int databaseSizeBeforeUpdate = descargaTipoRepository.findAll().size();

        // Update the descargaTipo
        DescargaTipo updatedDescargaTipo = descargaTipoRepository.findById(descargaTipo.getId()).get();
        // Disconnect from session so that the updates on updatedDescargaTipo are not directly saved in db
        em.detach(updatedDescargaTipo);
        updatedDescargaTipo
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        DescargaTipoDTO descargaTipoDTO = descargaTipoMapper.toDto(updatedDescargaTipo);

        restDescargaTipoMockMvc.perform(put("/api/descarga-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaTipoDTO)))
            .andExpect(status().isOk());

        // Validate the DescargaTipo in the database
        List<DescargaTipo> descargaTipoList = descargaTipoRepository.findAll();
        assertThat(descargaTipoList).hasSize(databaseSizeBeforeUpdate);
        DescargaTipo testDescargaTipo = descargaTipoList.get(descargaTipoList.size() - 1);
        assertThat(testDescargaTipo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDescargaTipo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDescargaTipo.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDescargaTipo.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingDescargaTipo() throws Exception {
        int databaseSizeBeforeUpdate = descargaTipoRepository.findAll().size();

        // Create the DescargaTipo
        DescargaTipoDTO descargaTipoDTO = descargaTipoMapper.toDto(descargaTipo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDescargaTipoMockMvc.perform(put("/api/descarga-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaTipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DescargaTipo in the database
        List<DescargaTipo> descargaTipoList = descargaTipoRepository.findAll();
        assertThat(descargaTipoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDescargaTipo() throws Exception {
        // Initialize the database
        descargaTipoRepository.saveAndFlush(descargaTipo);

        int databaseSizeBeforeDelete = descargaTipoRepository.findAll().size();

        // Delete the descargaTipo
        restDescargaTipoMockMvc.perform(delete("/api/descarga-tipos/{id}", descargaTipo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DescargaTipo> descargaTipoList = descargaTipoRepository.findAll();
        assertThat(descargaTipoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
