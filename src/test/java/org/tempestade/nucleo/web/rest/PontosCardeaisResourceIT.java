package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.PontosCardeais;
import org.tempestade.nucleo.repository.PontosCardeaisRepository;
import org.tempestade.nucleo.service.PontosCardeaisService;
import org.tempestade.nucleo.service.dto.PontosCardeaisDTO;
import org.tempestade.nucleo.service.mapper.PontosCardeaisMapper;
import org.tempestade.nucleo.service.dto.PontosCardeaisCriteria;
import org.tempestade.nucleo.service.PontosCardeaisQueryService;

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
    private PontosCardeaisQueryService pontosCardeaisQueryService;

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
    public void getPontosCardeaisByIdFiltering() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        Long id = pontosCardeais.getId();

        defaultPontosCardeaisShouldBeFound("id.equals=" + id);
        defaultPontosCardeaisShouldNotBeFound("id.notEquals=" + id);

        defaultPontosCardeaisShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPontosCardeaisShouldNotBeFound("id.greaterThan=" + id);

        defaultPontosCardeaisShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPontosCardeaisShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPontosCardeaisByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where name equals to DEFAULT_NAME
        defaultPontosCardeaisShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the pontosCardeaisList where name equals to UPDATED_NAME
        defaultPontosCardeaisShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPontosCardeaisByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where name not equals to DEFAULT_NAME
        defaultPontosCardeaisShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the pontosCardeaisList where name not equals to UPDATED_NAME
        defaultPontosCardeaisShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPontosCardeaisByNameIsInShouldWork() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPontosCardeaisShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the pontosCardeaisList where name equals to UPDATED_NAME
        defaultPontosCardeaisShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPontosCardeaisByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where name is not null
        defaultPontosCardeaisShouldBeFound("name.specified=true");

        // Get all the pontosCardeaisList where name is null
        defaultPontosCardeaisShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPontosCardeaisByNameContainsSomething() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where name contains DEFAULT_NAME
        defaultPontosCardeaisShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the pontosCardeaisList where name contains UPDATED_NAME
        defaultPontosCardeaisShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPontosCardeaisByNameNotContainsSomething() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where name does not contain DEFAULT_NAME
        defaultPontosCardeaisShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the pontosCardeaisList where name does not contain UPDATED_NAME
        defaultPontosCardeaisShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPontosCardeaisByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where descricao equals to DEFAULT_DESCRICAO
        defaultPontosCardeaisShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the pontosCardeaisList where descricao equals to UPDATED_DESCRICAO
        defaultPontosCardeaisShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPontosCardeaisByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where descricao not equals to DEFAULT_DESCRICAO
        defaultPontosCardeaisShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the pontosCardeaisList where descricao not equals to UPDATED_DESCRICAO
        defaultPontosCardeaisShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPontosCardeaisByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultPontosCardeaisShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the pontosCardeaisList where descricao equals to UPDATED_DESCRICAO
        defaultPontosCardeaisShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPontosCardeaisByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where descricao is not null
        defaultPontosCardeaisShouldBeFound("descricao.specified=true");

        // Get all the pontosCardeaisList where descricao is null
        defaultPontosCardeaisShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllPontosCardeaisByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where descricao contains DEFAULT_DESCRICAO
        defaultPontosCardeaisShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the pontosCardeaisList where descricao contains UPDATED_DESCRICAO
        defaultPontosCardeaisShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPontosCardeaisByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where descricao does not contain DEFAULT_DESCRICAO
        defaultPontosCardeaisShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the pontosCardeaisList where descricao does not contain UPDATED_DESCRICAO
        defaultPontosCardeaisShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllPontosCardeaisByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where created equals to DEFAULT_CREATED
        defaultPontosCardeaisShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the pontosCardeaisList where created equals to UPDATED_CREATED
        defaultPontosCardeaisShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPontosCardeaisByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where created not equals to DEFAULT_CREATED
        defaultPontosCardeaisShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the pontosCardeaisList where created not equals to UPDATED_CREATED
        defaultPontosCardeaisShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPontosCardeaisByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultPontosCardeaisShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the pontosCardeaisList where created equals to UPDATED_CREATED
        defaultPontosCardeaisShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPontosCardeaisByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where created is not null
        defaultPontosCardeaisShouldBeFound("created.specified=true");

        // Get all the pontosCardeaisList where created is null
        defaultPontosCardeaisShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllPontosCardeaisByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where updated equals to DEFAULT_UPDATED
        defaultPontosCardeaisShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the pontosCardeaisList where updated equals to UPDATED_UPDATED
        defaultPontosCardeaisShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPontosCardeaisByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where updated not equals to DEFAULT_UPDATED
        defaultPontosCardeaisShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the pontosCardeaisList where updated not equals to UPDATED_UPDATED
        defaultPontosCardeaisShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPontosCardeaisByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultPontosCardeaisShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the pontosCardeaisList where updated equals to UPDATED_UPDATED
        defaultPontosCardeaisShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPontosCardeaisByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        pontosCardeaisRepository.saveAndFlush(pontosCardeais);

        // Get all the pontosCardeaisList where updated is not null
        defaultPontosCardeaisShouldBeFound("updated.specified=true");

        // Get all the pontosCardeaisList where updated is null
        defaultPontosCardeaisShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPontosCardeaisShouldBeFound(String filter) throws Exception {
        restPontosCardeaisMockMvc.perform(get("/api/pontos-cardeais?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pontosCardeais.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restPontosCardeaisMockMvc.perform(get("/api/pontos-cardeais/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPontosCardeaisShouldNotBeFound(String filter) throws Exception {
        restPontosCardeaisMockMvc.perform(get("/api/pontos-cardeais?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPontosCardeaisMockMvc.perform(get("/api/pontos-cardeais/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
