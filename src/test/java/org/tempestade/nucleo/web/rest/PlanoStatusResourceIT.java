package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.PlanoStatus;
import org.tempestade.nucleo.repository.PlanoStatusRepository;
import org.tempestade.nucleo.service.PlanoStatusService;
import org.tempestade.nucleo.service.dto.PlanoStatusDTO;
import org.tempestade.nucleo.service.mapper.PlanoStatusMapper;

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
 * Integration tests for the {@link PlanoStatusResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlanoStatusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PlanoStatusRepository planoStatusRepository;

    @Autowired
    private PlanoStatusMapper planoStatusMapper;

    @Autowired
    private PlanoStatusService planoStatusService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoStatusMockMvc;

    private PlanoStatus planoStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoStatus createEntity(EntityManager em) {
        PlanoStatus planoStatus = new PlanoStatus()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return planoStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoStatus createUpdatedEntity(EntityManager em) {
        PlanoStatus planoStatus = new PlanoStatus()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return planoStatus;
    }

    @BeforeEach
    public void initTest() {
        planoStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoStatus() throws Exception {
        int databaseSizeBeforeCreate = planoStatusRepository.findAll().size();
        // Create the PlanoStatus
        PlanoStatusDTO planoStatusDTO = planoStatusMapper.toDto(planoStatus);
        restPlanoStatusMockMvc.perform(post("/api/plano-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanoStatus in the database
        List<PlanoStatus> planoStatusList = planoStatusRepository.findAll();
        assertThat(planoStatusList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoStatus testPlanoStatus = planoStatusList.get(planoStatusList.size() - 1);
        assertThat(testPlanoStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlanoStatus.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlanoStatus.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPlanoStatus.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createPlanoStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoStatusRepository.findAll().size();

        // Create the PlanoStatus with an existing ID
        planoStatus.setId(1L);
        PlanoStatusDTO planoStatusDTO = planoStatusMapper.toDto(planoStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoStatusMockMvc.perform(post("/api/plano-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoStatus in the database
        List<PlanoStatus> planoStatusList = planoStatusRepository.findAll();
        assertThat(planoStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoStatusRepository.findAll().size();
        // set the field null
        planoStatus.setName(null);

        // Create the PlanoStatus, which fails.
        PlanoStatusDTO planoStatusDTO = planoStatusMapper.toDto(planoStatus);


        restPlanoStatusMockMvc.perform(post("/api/plano-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoStatusDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoStatus> planoStatusList = planoStatusRepository.findAll();
        assertThat(planoStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoStatusRepository.findAll().size();
        // set the field null
        planoStatus.setDescricao(null);

        // Create the PlanoStatus, which fails.
        PlanoStatusDTO planoStatusDTO = planoStatusMapper.toDto(planoStatus);


        restPlanoStatusMockMvc.perform(post("/api/plano-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoStatusDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoStatus> planoStatusList = planoStatusRepository.findAll();
        assertThat(planoStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoStatusRepository.findAll().size();
        // set the field null
        planoStatus.setCreated(null);

        // Create the PlanoStatus, which fails.
        PlanoStatusDTO planoStatusDTO = planoStatusMapper.toDto(planoStatus);


        restPlanoStatusMockMvc.perform(post("/api/plano-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoStatusDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoStatus> planoStatusList = planoStatusRepository.findAll();
        assertThat(planoStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanoStatuses() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList
        restPlanoStatusMockMvc.perform(get("/api/plano-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getPlanoStatus() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get the planoStatus
        restPlanoStatusMockMvc.perform(get("/api/plano-statuses/{id}", planoStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPlanoStatus() throws Exception {
        // Get the planoStatus
        restPlanoStatusMockMvc.perform(get("/api/plano-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoStatus() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        int databaseSizeBeforeUpdate = planoStatusRepository.findAll().size();

        // Update the planoStatus
        PlanoStatus updatedPlanoStatus = planoStatusRepository.findById(planoStatus.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoStatus are not directly saved in db
        em.detach(updatedPlanoStatus);
        updatedPlanoStatus
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        PlanoStatusDTO planoStatusDTO = planoStatusMapper.toDto(updatedPlanoStatus);

        restPlanoStatusMockMvc.perform(put("/api/plano-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoStatusDTO)))
            .andExpect(status().isOk());

        // Validate the PlanoStatus in the database
        List<PlanoStatus> planoStatusList = planoStatusRepository.findAll();
        assertThat(planoStatusList).hasSize(databaseSizeBeforeUpdate);
        PlanoStatus testPlanoStatus = planoStatusList.get(planoStatusList.size() - 1);
        assertThat(testPlanoStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanoStatus.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoStatus.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPlanoStatus.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoStatus() throws Exception {
        int databaseSizeBeforeUpdate = planoStatusRepository.findAll().size();

        // Create the PlanoStatus
        PlanoStatusDTO planoStatusDTO = planoStatusMapper.toDto(planoStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoStatusMockMvc.perform(put("/api/plano-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoStatus in the database
        List<PlanoStatus> planoStatusList = planoStatusRepository.findAll();
        assertThat(planoStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanoStatus() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        int databaseSizeBeforeDelete = planoStatusRepository.findAll().size();

        // Delete the planoStatus
        restPlanoStatusMockMvc.perform(delete("/api/plano-statuses/{id}", planoStatus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoStatus> planoStatusList = planoStatusRepository.findAll();
        assertThat(planoStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
