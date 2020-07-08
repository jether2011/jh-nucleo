package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.AlertaTipo;
import org.tempestade.nucleo.repository.AlertaTipoRepository;
import org.tempestade.nucleo.service.AlertaTipoService;
import org.tempestade.nucleo.service.dto.AlertaTipoDTO;
import org.tempestade.nucleo.service.mapper.AlertaTipoMapper;

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
 * Integration tests for the {@link AlertaTipoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlertaTipoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AlertaTipoRepository alertaTipoRepository;

    @Autowired
    private AlertaTipoMapper alertaTipoMapper;

    @Autowired
    private AlertaTipoService alertaTipoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlertaTipoMockMvc;

    private AlertaTipo alertaTipo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertaTipo createEntity(EntityManager em) {
        AlertaTipo alertaTipo = new AlertaTipo()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return alertaTipo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertaTipo createUpdatedEntity(EntityManager em) {
        AlertaTipo alertaTipo = new AlertaTipo()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return alertaTipo;
    }

    @BeforeEach
    public void initTest() {
        alertaTipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlertaTipo() throws Exception {
        int databaseSizeBeforeCreate = alertaTipoRepository.findAll().size();
        // Create the AlertaTipo
        AlertaTipoDTO alertaTipoDTO = alertaTipoMapper.toDto(alertaTipo);
        restAlertaTipoMockMvc.perform(post("/api/alerta-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaTipoDTO)))
            .andExpect(status().isCreated());

        // Validate the AlertaTipo in the database
        List<AlertaTipo> alertaTipoList = alertaTipoRepository.findAll();
        assertThat(alertaTipoList).hasSize(databaseSizeBeforeCreate + 1);
        AlertaTipo testAlertaTipo = alertaTipoList.get(alertaTipoList.size() - 1);
        assertThat(testAlertaTipo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAlertaTipo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAlertaTipo.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAlertaTipo.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createAlertaTipoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alertaTipoRepository.findAll().size();

        // Create the AlertaTipo with an existing ID
        alertaTipo.setId(1L);
        AlertaTipoDTO alertaTipoDTO = alertaTipoMapper.toDto(alertaTipo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertaTipoMockMvc.perform(post("/api/alerta-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaTipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlertaTipo in the database
        List<AlertaTipo> alertaTipoList = alertaTipoRepository.findAll();
        assertThat(alertaTipoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaTipoRepository.findAll().size();
        // set the field null
        alertaTipo.setNome(null);

        // Create the AlertaTipo, which fails.
        AlertaTipoDTO alertaTipoDTO = alertaTipoMapper.toDto(alertaTipo);


        restAlertaTipoMockMvc.perform(post("/api/alerta-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaTipoDTO)))
            .andExpect(status().isBadRequest());

        List<AlertaTipo> alertaTipoList = alertaTipoRepository.findAll();
        assertThat(alertaTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaTipoRepository.findAll().size();
        // set the field null
        alertaTipo.setCreated(null);

        // Create the AlertaTipo, which fails.
        AlertaTipoDTO alertaTipoDTO = alertaTipoMapper.toDto(alertaTipo);


        restAlertaTipoMockMvc.perform(post("/api/alerta-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaTipoDTO)))
            .andExpect(status().isBadRequest());

        List<AlertaTipo> alertaTipoList = alertaTipoRepository.findAll();
        assertThat(alertaTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlertaTipos() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList
        restAlertaTipoMockMvc.perform(get("/api/alerta-tipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertaTipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getAlertaTipo() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get the alertaTipo
        restAlertaTipoMockMvc.perform(get("/api/alerta-tipos/{id}", alertaTipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alertaTipo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAlertaTipo() throws Exception {
        // Get the alertaTipo
        restAlertaTipoMockMvc.perform(get("/api/alerta-tipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlertaTipo() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        int databaseSizeBeforeUpdate = alertaTipoRepository.findAll().size();

        // Update the alertaTipo
        AlertaTipo updatedAlertaTipo = alertaTipoRepository.findById(alertaTipo.getId()).get();
        // Disconnect from session so that the updates on updatedAlertaTipo are not directly saved in db
        em.detach(updatedAlertaTipo);
        updatedAlertaTipo
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        AlertaTipoDTO alertaTipoDTO = alertaTipoMapper.toDto(updatedAlertaTipo);

        restAlertaTipoMockMvc.perform(put("/api/alerta-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaTipoDTO)))
            .andExpect(status().isOk());

        // Validate the AlertaTipo in the database
        List<AlertaTipo> alertaTipoList = alertaTipoRepository.findAll();
        assertThat(alertaTipoList).hasSize(databaseSizeBeforeUpdate);
        AlertaTipo testAlertaTipo = alertaTipoList.get(alertaTipoList.size() - 1);
        assertThat(testAlertaTipo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAlertaTipo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAlertaTipo.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAlertaTipo.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAlertaTipo() throws Exception {
        int databaseSizeBeforeUpdate = alertaTipoRepository.findAll().size();

        // Create the AlertaTipo
        AlertaTipoDTO alertaTipoDTO = alertaTipoMapper.toDto(alertaTipo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertaTipoMockMvc.perform(put("/api/alerta-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaTipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlertaTipo in the database
        List<AlertaTipo> alertaTipoList = alertaTipoRepository.findAll();
        assertThat(alertaTipoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlertaTipo() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        int databaseSizeBeforeDelete = alertaTipoRepository.findAll().size();

        // Delete the alertaTipo
        restAlertaTipoMockMvc.perform(delete("/api/alerta-tipos/{id}", alertaTipo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlertaTipo> alertaTipoList = alertaTipoRepository.findAll();
        assertThat(alertaTipoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
