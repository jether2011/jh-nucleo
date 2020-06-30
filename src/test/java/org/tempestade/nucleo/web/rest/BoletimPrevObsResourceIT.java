package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.BoletimPrevObs;
import org.tempestade.nucleo.repository.BoletimPrevObsRepository;
import org.tempestade.nucleo.service.BoletimPrevObsService;
import org.tempestade.nucleo.service.dto.BoletimPrevObsDTO;
import org.tempestade.nucleo.service.mapper.BoletimPrevObsMapper;
import org.tempestade.nucleo.service.dto.BoletimPrevObsCriteria;
import org.tempestade.nucleo.service.BoletimPrevObsQueryService;

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
 * Integration tests for the {@link BoletimPrevObsResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BoletimPrevObsResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BoletimPrevObsRepository boletimPrevObsRepository;

    @Autowired
    private BoletimPrevObsMapper boletimPrevObsMapper;

    @Autowired
    private BoletimPrevObsService boletimPrevObsService;

    @Autowired
    private BoletimPrevObsQueryService boletimPrevObsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoletimPrevObsMockMvc;

    private BoletimPrevObs boletimPrevObs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoletimPrevObs createEntity(EntityManager em) {
        BoletimPrevObs boletimPrevObs = new BoletimPrevObs()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return boletimPrevObs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoletimPrevObs createUpdatedEntity(EntityManager em) {
        BoletimPrevObs boletimPrevObs = new BoletimPrevObs()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return boletimPrevObs;
    }

    @BeforeEach
    public void initTest() {
        boletimPrevObs = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoletimPrevObs() throws Exception {
        int databaseSizeBeforeCreate = boletimPrevObsRepository.findAll().size();
        // Create the BoletimPrevObs
        BoletimPrevObsDTO boletimPrevObsDTO = boletimPrevObsMapper.toDto(boletimPrevObs);
        restBoletimPrevObsMockMvc.perform(post("/api/boletim-prev-obs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevObsDTO)))
            .andExpect(status().isCreated());

        // Validate the BoletimPrevObs in the database
        List<BoletimPrevObs> boletimPrevObsList = boletimPrevObsRepository.findAll();
        assertThat(boletimPrevObsList).hasSize(databaseSizeBeforeCreate + 1);
        BoletimPrevObs testBoletimPrevObs = boletimPrevObsList.get(boletimPrevObsList.size() - 1);
        assertThat(testBoletimPrevObs.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testBoletimPrevObs.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testBoletimPrevObs.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testBoletimPrevObs.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createBoletimPrevObsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boletimPrevObsRepository.findAll().size();

        // Create the BoletimPrevObs with an existing ID
        boletimPrevObs.setId(1L);
        BoletimPrevObsDTO boletimPrevObsDTO = boletimPrevObsMapper.toDto(boletimPrevObs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoletimPrevObsMockMvc.perform(post("/api/boletim-prev-obs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevObsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoletimPrevObs in the database
        List<BoletimPrevObs> boletimPrevObsList = boletimPrevObsRepository.findAll();
        assertThat(boletimPrevObsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimPrevObsRepository.findAll().size();
        // set the field null
        boletimPrevObs.setNome(null);

        // Create the BoletimPrevObs, which fails.
        BoletimPrevObsDTO boletimPrevObsDTO = boletimPrevObsMapper.toDto(boletimPrevObs);


        restBoletimPrevObsMockMvc.perform(post("/api/boletim-prev-obs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevObsDTO)))
            .andExpect(status().isBadRequest());

        List<BoletimPrevObs> boletimPrevObsList = boletimPrevObsRepository.findAll();
        assertThat(boletimPrevObsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimPrevObsRepository.findAll().size();
        // set the field null
        boletimPrevObs.setCreated(null);

        // Create the BoletimPrevObs, which fails.
        BoletimPrevObsDTO boletimPrevObsDTO = boletimPrevObsMapper.toDto(boletimPrevObs);


        restBoletimPrevObsMockMvc.perform(post("/api/boletim-prev-obs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevObsDTO)))
            .andExpect(status().isBadRequest());

        List<BoletimPrevObs> boletimPrevObsList = boletimPrevObsRepository.findAll();
        assertThat(boletimPrevObsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObs() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList
        restBoletimPrevObsMockMvc.perform(get("/api/boletim-prev-obs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boletimPrevObs.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getBoletimPrevObs() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get the boletimPrevObs
        restBoletimPrevObsMockMvc.perform(get("/api/boletim-prev-obs/{id}", boletimPrevObs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(boletimPrevObs.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getBoletimPrevObsByIdFiltering() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        Long id = boletimPrevObs.getId();

        defaultBoletimPrevObsShouldBeFound("id.equals=" + id);
        defaultBoletimPrevObsShouldNotBeFound("id.notEquals=" + id);

        defaultBoletimPrevObsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBoletimPrevObsShouldNotBeFound("id.greaterThan=" + id);

        defaultBoletimPrevObsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBoletimPrevObsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevObsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where nome equals to DEFAULT_NOME
        defaultBoletimPrevObsShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the boletimPrevObsList where nome equals to UPDATED_NOME
        defaultBoletimPrevObsShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObsByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where nome not equals to DEFAULT_NOME
        defaultBoletimPrevObsShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the boletimPrevObsList where nome not equals to UPDATED_NOME
        defaultBoletimPrevObsShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultBoletimPrevObsShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the boletimPrevObsList where nome equals to UPDATED_NOME
        defaultBoletimPrevObsShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where nome is not null
        defaultBoletimPrevObsShouldBeFound("nome.specified=true");

        // Get all the boletimPrevObsList where nome is null
        defaultBoletimPrevObsShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimPrevObsByNomeContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where nome contains DEFAULT_NOME
        defaultBoletimPrevObsShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the boletimPrevObsList where nome contains UPDATED_NOME
        defaultBoletimPrevObsShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where nome does not contain DEFAULT_NOME
        defaultBoletimPrevObsShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the boletimPrevObsList where nome does not contain UPDATED_NOME
        defaultBoletimPrevObsShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevObsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where descricao equals to DEFAULT_DESCRICAO
        defaultBoletimPrevObsShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the boletimPrevObsList where descricao equals to UPDATED_DESCRICAO
        defaultBoletimPrevObsShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObsByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where descricao not equals to DEFAULT_DESCRICAO
        defaultBoletimPrevObsShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the boletimPrevObsList where descricao not equals to UPDATED_DESCRICAO
        defaultBoletimPrevObsShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultBoletimPrevObsShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the boletimPrevObsList where descricao equals to UPDATED_DESCRICAO
        defaultBoletimPrevObsShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where descricao is not null
        defaultBoletimPrevObsShouldBeFound("descricao.specified=true");

        // Get all the boletimPrevObsList where descricao is null
        defaultBoletimPrevObsShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimPrevObsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where descricao contains DEFAULT_DESCRICAO
        defaultBoletimPrevObsShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the boletimPrevObsList where descricao contains UPDATED_DESCRICAO
        defaultBoletimPrevObsShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where descricao does not contain DEFAULT_DESCRICAO
        defaultBoletimPrevObsShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the boletimPrevObsList where descricao does not contain UPDATED_DESCRICAO
        defaultBoletimPrevObsShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevObsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where created equals to DEFAULT_CREATED
        defaultBoletimPrevObsShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the boletimPrevObsList where created equals to UPDATED_CREATED
        defaultBoletimPrevObsShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where created not equals to DEFAULT_CREATED
        defaultBoletimPrevObsShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the boletimPrevObsList where created not equals to UPDATED_CREATED
        defaultBoletimPrevObsShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultBoletimPrevObsShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the boletimPrevObsList where created equals to UPDATED_CREATED
        defaultBoletimPrevObsShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where created is not null
        defaultBoletimPrevObsShouldBeFound("created.specified=true");

        // Get all the boletimPrevObsList where created is null
        defaultBoletimPrevObsShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObsByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where updated equals to DEFAULT_UPDATED
        defaultBoletimPrevObsShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the boletimPrevObsList where updated equals to UPDATED_UPDATED
        defaultBoletimPrevObsShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObsByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where updated not equals to DEFAULT_UPDATED
        defaultBoletimPrevObsShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the boletimPrevObsList where updated not equals to UPDATED_UPDATED
        defaultBoletimPrevObsShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObsByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultBoletimPrevObsShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the boletimPrevObsList where updated equals to UPDATED_UPDATED
        defaultBoletimPrevObsShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObsByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList where updated is not null
        defaultBoletimPrevObsShouldBeFound("updated.specified=true");

        // Get all the boletimPrevObsList where updated is null
        defaultBoletimPrevObsShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBoletimPrevObsShouldBeFound(String filter) throws Exception {
        restBoletimPrevObsMockMvc.perform(get("/api/boletim-prev-obs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boletimPrevObs.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restBoletimPrevObsMockMvc.perform(get("/api/boletim-prev-obs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBoletimPrevObsShouldNotBeFound(String filter) throws Exception {
        restBoletimPrevObsMockMvc.perform(get("/api/boletim-prev-obs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBoletimPrevObsMockMvc.perform(get("/api/boletim-prev-obs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBoletimPrevObs() throws Exception {
        // Get the boletimPrevObs
        restBoletimPrevObsMockMvc.perform(get("/api/boletim-prev-obs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoletimPrevObs() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        int databaseSizeBeforeUpdate = boletimPrevObsRepository.findAll().size();

        // Update the boletimPrevObs
        BoletimPrevObs updatedBoletimPrevObs = boletimPrevObsRepository.findById(boletimPrevObs.getId()).get();
        // Disconnect from session so that the updates on updatedBoletimPrevObs are not directly saved in db
        em.detach(updatedBoletimPrevObs);
        updatedBoletimPrevObs
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        BoletimPrevObsDTO boletimPrevObsDTO = boletimPrevObsMapper.toDto(updatedBoletimPrevObs);

        restBoletimPrevObsMockMvc.perform(put("/api/boletim-prev-obs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevObsDTO)))
            .andExpect(status().isOk());

        // Validate the BoletimPrevObs in the database
        List<BoletimPrevObs> boletimPrevObsList = boletimPrevObsRepository.findAll();
        assertThat(boletimPrevObsList).hasSize(databaseSizeBeforeUpdate);
        BoletimPrevObs testBoletimPrevObs = boletimPrevObsList.get(boletimPrevObsList.size() - 1);
        assertThat(testBoletimPrevObs.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testBoletimPrevObs.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testBoletimPrevObs.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testBoletimPrevObs.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingBoletimPrevObs() throws Exception {
        int databaseSizeBeforeUpdate = boletimPrevObsRepository.findAll().size();

        // Create the BoletimPrevObs
        BoletimPrevObsDTO boletimPrevObsDTO = boletimPrevObsMapper.toDto(boletimPrevObs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoletimPrevObsMockMvc.perform(put("/api/boletim-prev-obs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevObsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoletimPrevObs in the database
        List<BoletimPrevObs> boletimPrevObsList = boletimPrevObsRepository.findAll();
        assertThat(boletimPrevObsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBoletimPrevObs() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        int databaseSizeBeforeDelete = boletimPrevObsRepository.findAll().size();

        // Delete the boletimPrevObs
        restBoletimPrevObsMockMvc.perform(delete("/api/boletim-prev-obs/{id}", boletimPrevObs.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BoletimPrevObs> boletimPrevObsList = boletimPrevObsRepository.findAll();
        assertThat(boletimPrevObsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
