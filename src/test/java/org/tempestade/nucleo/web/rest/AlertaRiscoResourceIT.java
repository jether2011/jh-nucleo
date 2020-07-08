package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.AlertaRisco;
import org.tempestade.nucleo.repository.AlertaRiscoRepository;
import org.tempestade.nucleo.service.AlertaRiscoService;
import org.tempestade.nucleo.service.dto.AlertaRiscoDTO;
import org.tempestade.nucleo.service.mapper.AlertaRiscoMapper;

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
 * Integration tests for the {@link AlertaRiscoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlertaRiscoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AlertaRiscoRepository alertaRiscoRepository;

    @Autowired
    private AlertaRiscoMapper alertaRiscoMapper;

    @Autowired
    private AlertaRiscoService alertaRiscoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlertaRiscoMockMvc;

    private AlertaRisco alertaRisco;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertaRisco createEntity(EntityManager em) {
        AlertaRisco alertaRisco = new AlertaRisco()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return alertaRisco;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertaRisco createUpdatedEntity(EntityManager em) {
        AlertaRisco alertaRisco = new AlertaRisco()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return alertaRisco;
    }

    @BeforeEach
    public void initTest() {
        alertaRisco = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlertaRisco() throws Exception {
        int databaseSizeBeforeCreate = alertaRiscoRepository.findAll().size();
        // Create the AlertaRisco
        AlertaRiscoDTO alertaRiscoDTO = alertaRiscoMapper.toDto(alertaRisco);
        restAlertaRiscoMockMvc.perform(post("/api/alerta-riscos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaRiscoDTO)))
            .andExpect(status().isCreated());

        // Validate the AlertaRisco in the database
        List<AlertaRisco> alertaRiscoList = alertaRiscoRepository.findAll();
        assertThat(alertaRiscoList).hasSize(databaseSizeBeforeCreate + 1);
        AlertaRisco testAlertaRisco = alertaRiscoList.get(alertaRiscoList.size() - 1);
        assertThat(testAlertaRisco.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAlertaRisco.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAlertaRisco.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAlertaRisco.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createAlertaRiscoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alertaRiscoRepository.findAll().size();

        // Create the AlertaRisco with an existing ID
        alertaRisco.setId(1L);
        AlertaRiscoDTO alertaRiscoDTO = alertaRiscoMapper.toDto(alertaRisco);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertaRiscoMockMvc.perform(post("/api/alerta-riscos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaRiscoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlertaRisco in the database
        List<AlertaRisco> alertaRiscoList = alertaRiscoRepository.findAll();
        assertThat(alertaRiscoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaRiscoRepository.findAll().size();
        // set the field null
        alertaRisco.setNome(null);

        // Create the AlertaRisco, which fails.
        AlertaRiscoDTO alertaRiscoDTO = alertaRiscoMapper.toDto(alertaRisco);


        restAlertaRiscoMockMvc.perform(post("/api/alerta-riscos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaRiscoDTO)))
            .andExpect(status().isBadRequest());

        List<AlertaRisco> alertaRiscoList = alertaRiscoRepository.findAll();
        assertThat(alertaRiscoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaRiscoRepository.findAll().size();
        // set the field null
        alertaRisco.setCreated(null);

        // Create the AlertaRisco, which fails.
        AlertaRiscoDTO alertaRiscoDTO = alertaRiscoMapper.toDto(alertaRisco);


        restAlertaRiscoMockMvc.perform(post("/api/alerta-riscos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaRiscoDTO)))
            .andExpect(status().isBadRequest());

        List<AlertaRisco> alertaRiscoList = alertaRiscoRepository.findAll();
        assertThat(alertaRiscoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlertaRiscos() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList
        restAlertaRiscoMockMvc.perform(get("/api/alerta-riscos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertaRisco.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getAlertaRisco() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get the alertaRisco
        restAlertaRiscoMockMvc.perform(get("/api/alerta-riscos/{id}", alertaRisco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alertaRisco.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAlertaRisco() throws Exception {
        // Get the alertaRisco
        restAlertaRiscoMockMvc.perform(get("/api/alerta-riscos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlertaRisco() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        int databaseSizeBeforeUpdate = alertaRiscoRepository.findAll().size();

        // Update the alertaRisco
        AlertaRisco updatedAlertaRisco = alertaRiscoRepository.findById(alertaRisco.getId()).get();
        // Disconnect from session so that the updates on updatedAlertaRisco are not directly saved in db
        em.detach(updatedAlertaRisco);
        updatedAlertaRisco
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        AlertaRiscoDTO alertaRiscoDTO = alertaRiscoMapper.toDto(updatedAlertaRisco);

        restAlertaRiscoMockMvc.perform(put("/api/alerta-riscos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaRiscoDTO)))
            .andExpect(status().isOk());

        // Validate the AlertaRisco in the database
        List<AlertaRisco> alertaRiscoList = alertaRiscoRepository.findAll();
        assertThat(alertaRiscoList).hasSize(databaseSizeBeforeUpdate);
        AlertaRisco testAlertaRisco = alertaRiscoList.get(alertaRiscoList.size() - 1);
        assertThat(testAlertaRisco.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAlertaRisco.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAlertaRisco.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAlertaRisco.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAlertaRisco() throws Exception {
        int databaseSizeBeforeUpdate = alertaRiscoRepository.findAll().size();

        // Create the AlertaRisco
        AlertaRiscoDTO alertaRiscoDTO = alertaRiscoMapper.toDto(alertaRisco);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertaRiscoMockMvc.perform(put("/api/alerta-riscos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaRiscoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlertaRisco in the database
        List<AlertaRisco> alertaRiscoList = alertaRiscoRepository.findAll();
        assertThat(alertaRiscoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlertaRisco() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        int databaseSizeBeforeDelete = alertaRiscoRepository.findAll().size();

        // Delete the alertaRisco
        restAlertaRiscoMockMvc.perform(delete("/api/alerta-riscos/{id}", alertaRisco.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlertaRisco> alertaRiscoList = alertaRiscoRepository.findAll();
        assertThat(alertaRiscoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
