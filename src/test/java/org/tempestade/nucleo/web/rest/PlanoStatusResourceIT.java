package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.PlanoStatus;
import org.tempestade.nucleo.repository.PlanoStatusRepository;
import org.tempestade.nucleo.service.PlanoStatusService;
import org.tempestade.nucleo.service.dto.PlanoStatusDTO;
import org.tempestade.nucleo.service.mapper.PlanoStatusMapper;
import org.tempestade.nucleo.service.dto.PlanoStatusCriteria;
import org.tempestade.nucleo.service.PlanoStatusQueryService;

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
    private PlanoStatusQueryService planoStatusQueryService;

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
    public void getPlanoStatusesByIdFiltering() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        Long id = planoStatus.getId();

        defaultPlanoStatusShouldBeFound("id.equals=" + id);
        defaultPlanoStatusShouldNotBeFound("id.notEquals=" + id);

        defaultPlanoStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlanoStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultPlanoStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlanoStatusShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPlanoStatusesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where name equals to DEFAULT_NAME
        defaultPlanoStatusShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the planoStatusList where name equals to UPDATED_NAME
        defaultPlanoStatusShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoStatusesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where name not equals to DEFAULT_NAME
        defaultPlanoStatusShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the planoStatusList where name not equals to UPDATED_NAME
        defaultPlanoStatusShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoStatusesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPlanoStatusShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the planoStatusList where name equals to UPDATED_NAME
        defaultPlanoStatusShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoStatusesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where name is not null
        defaultPlanoStatusShouldBeFound("name.specified=true");

        // Get all the planoStatusList where name is null
        defaultPlanoStatusShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanoStatusesByNameContainsSomething() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where name contains DEFAULT_NAME
        defaultPlanoStatusShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the planoStatusList where name contains UPDATED_NAME
        defaultPlanoStatusShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoStatusesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where name does not contain DEFAULT_NAME
        defaultPlanoStatusShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the planoStatusList where name does not contain UPDATED_NAME
        defaultPlanoStatusShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPlanoStatusesByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where descricao equals to DEFAULT_DESCRICAO
        defaultPlanoStatusShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the planoStatusList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoStatusShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoStatusesByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where descricao not equals to DEFAULT_DESCRICAO
        defaultPlanoStatusShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the planoStatusList where descricao not equals to UPDATED_DESCRICAO
        defaultPlanoStatusShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoStatusesByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultPlanoStatusShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the planoStatusList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoStatusShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoStatusesByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where descricao is not null
        defaultPlanoStatusShouldBeFound("descricao.specified=true");

        // Get all the planoStatusList where descricao is null
        defaultPlanoStatusShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanoStatusesByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where descricao contains DEFAULT_DESCRICAO
        defaultPlanoStatusShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the planoStatusList where descricao contains UPDATED_DESCRICAO
        defaultPlanoStatusShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoStatusesByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where descricao does not contain DEFAULT_DESCRICAO
        defaultPlanoStatusShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the planoStatusList where descricao does not contain UPDATED_DESCRICAO
        defaultPlanoStatusShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllPlanoStatusesByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where created equals to DEFAULT_CREATED
        defaultPlanoStatusShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the planoStatusList where created equals to UPDATED_CREATED
        defaultPlanoStatusShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoStatusesByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where created not equals to DEFAULT_CREATED
        defaultPlanoStatusShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the planoStatusList where created not equals to UPDATED_CREATED
        defaultPlanoStatusShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoStatusesByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultPlanoStatusShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the planoStatusList where created equals to UPDATED_CREATED
        defaultPlanoStatusShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoStatusesByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where created is not null
        defaultPlanoStatusShouldBeFound("created.specified=true");

        // Get all the planoStatusList where created is null
        defaultPlanoStatusShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanoStatusesByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where updated equals to DEFAULT_UPDATED
        defaultPlanoStatusShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the planoStatusList where updated equals to UPDATED_UPDATED
        defaultPlanoStatusShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoStatusesByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where updated not equals to DEFAULT_UPDATED
        defaultPlanoStatusShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the planoStatusList where updated not equals to UPDATED_UPDATED
        defaultPlanoStatusShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoStatusesByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultPlanoStatusShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the planoStatusList where updated equals to UPDATED_UPDATED
        defaultPlanoStatusShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoStatusesByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoStatusRepository.saveAndFlush(planoStatus);

        // Get all the planoStatusList where updated is not null
        defaultPlanoStatusShouldBeFound("updated.specified=true");

        // Get all the planoStatusList where updated is null
        defaultPlanoStatusShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlanoStatusShouldBeFound(String filter) throws Exception {
        restPlanoStatusMockMvc.perform(get("/api/plano-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restPlanoStatusMockMvc.perform(get("/api/plano-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlanoStatusShouldNotBeFound(String filter) throws Exception {
        restPlanoStatusMockMvc.perform(get("/api/plano-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanoStatusMockMvc.perform(get("/api/plano-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
