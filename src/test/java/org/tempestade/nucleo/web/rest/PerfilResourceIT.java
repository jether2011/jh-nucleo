package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Perfil;
import org.tempestade.nucleo.repository.PerfilRepository;
import org.tempestade.nucleo.service.PerfilService;
import org.tempestade.nucleo.service.dto.PerfilDTO;
import org.tempestade.nucleo.service.mapper.PerfilMapper;
import org.tempestade.nucleo.service.dto.PerfilCriteria;
import org.tempestade.nucleo.service.PerfilQueryService;

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
 * Integration tests for the {@link PerfilResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PerfilResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PerfilMapper perfilMapper;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private PerfilQueryService perfilQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerfilMockMvc;

    private Perfil perfil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Perfil createEntity(EntityManager em) {
        Perfil perfil = new Perfil()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return perfil;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Perfil createUpdatedEntity(EntityManager em) {
        Perfil perfil = new Perfil()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return perfil;
    }

    @BeforeEach
    public void initTest() {
        perfil = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerfil() throws Exception {
        int databaseSizeBeforeCreate = perfilRepository.findAll().size();
        // Create the Perfil
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);
        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isCreated());

        // Validate the Perfil in the database
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeCreate + 1);
        Perfil testPerfil = perfilList.get(perfilList.size() - 1);
        assertThat(testPerfil.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPerfil.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPerfil.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPerfil.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createPerfilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perfilRepository.findAll().size();

        // Create the Perfil with an existing ID
        perfil.setId(1L);
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Perfil in the database
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setName(null);

        // Create the Perfil, which fails.
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);


        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setDescricao(null);

        // Create the Perfil, which fails.
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);


        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setCreated(null);

        // Create the Perfil, which fails.
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);


        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPerfils() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList
        restPerfilMockMvc.perform(get("/api/perfils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getPerfil() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get the perfil
        restPerfilMockMvc.perform(get("/api/perfils/{id}", perfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(perfil.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getPerfilsByIdFiltering() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        Long id = perfil.getId();

        defaultPerfilShouldBeFound("id.equals=" + id);
        defaultPerfilShouldNotBeFound("id.notEquals=" + id);

        defaultPerfilShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPerfilShouldNotBeFound("id.greaterThan=" + id);

        defaultPerfilShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPerfilShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPerfilsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where name equals to DEFAULT_NAME
        defaultPerfilShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the perfilList where name equals to UPDATED_NAME
        defaultPerfilShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPerfilsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where name not equals to DEFAULT_NAME
        defaultPerfilShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the perfilList where name not equals to UPDATED_NAME
        defaultPerfilShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPerfilsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPerfilShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the perfilList where name equals to UPDATED_NAME
        defaultPerfilShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPerfilsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where name is not null
        defaultPerfilShouldBeFound("name.specified=true");

        // Get all the perfilList where name is null
        defaultPerfilShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPerfilsByNameContainsSomething() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where name contains DEFAULT_NAME
        defaultPerfilShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the perfilList where name contains UPDATED_NAME
        defaultPerfilShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPerfilsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where name does not contain DEFAULT_NAME
        defaultPerfilShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the perfilList where name does not contain UPDATED_NAME
        defaultPerfilShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPerfilsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where descricao equals to DEFAULT_DESCRICAO
        defaultPerfilShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the perfilList where descricao equals to UPDATED_DESCRICAO
        defaultPerfilShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPerfilsByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where descricao not equals to DEFAULT_DESCRICAO
        defaultPerfilShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the perfilList where descricao not equals to UPDATED_DESCRICAO
        defaultPerfilShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPerfilsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultPerfilShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the perfilList where descricao equals to UPDATED_DESCRICAO
        defaultPerfilShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPerfilsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where descricao is not null
        defaultPerfilShouldBeFound("descricao.specified=true");

        // Get all the perfilList where descricao is null
        defaultPerfilShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllPerfilsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where descricao contains DEFAULT_DESCRICAO
        defaultPerfilShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the perfilList where descricao contains UPDATED_DESCRICAO
        defaultPerfilShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPerfilsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where descricao does not contain DEFAULT_DESCRICAO
        defaultPerfilShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the perfilList where descricao does not contain UPDATED_DESCRICAO
        defaultPerfilShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllPerfilsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where created equals to DEFAULT_CREATED
        defaultPerfilShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the perfilList where created equals to UPDATED_CREATED
        defaultPerfilShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPerfilsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where created not equals to DEFAULT_CREATED
        defaultPerfilShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the perfilList where created not equals to UPDATED_CREATED
        defaultPerfilShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPerfilsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultPerfilShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the perfilList where created equals to UPDATED_CREATED
        defaultPerfilShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPerfilsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where created is not null
        defaultPerfilShouldBeFound("created.specified=true");

        // Get all the perfilList where created is null
        defaultPerfilShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllPerfilsByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where updated equals to DEFAULT_UPDATED
        defaultPerfilShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the perfilList where updated equals to UPDATED_UPDATED
        defaultPerfilShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPerfilsByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where updated not equals to DEFAULT_UPDATED
        defaultPerfilShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the perfilList where updated not equals to UPDATED_UPDATED
        defaultPerfilShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPerfilsByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultPerfilShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the perfilList where updated equals to UPDATED_UPDATED
        defaultPerfilShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPerfilsByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where updated is not null
        defaultPerfilShouldBeFound("updated.specified=true");

        // Get all the perfilList where updated is null
        defaultPerfilShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPerfilShouldBeFound(String filter) throws Exception {
        restPerfilMockMvc.perform(get("/api/perfils?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restPerfilMockMvc.perform(get("/api/perfils/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPerfilShouldNotBeFound(String filter) throws Exception {
        restPerfilMockMvc.perform(get("/api/perfils?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPerfilMockMvc.perform(get("/api/perfils/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPerfil() throws Exception {
        // Get the perfil
        restPerfilMockMvc.perform(get("/api/perfils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerfil() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        int databaseSizeBeforeUpdate = perfilRepository.findAll().size();

        // Update the perfil
        Perfil updatedPerfil = perfilRepository.findById(perfil.getId()).get();
        // Disconnect from session so that the updates on updatedPerfil are not directly saved in db
        em.detach(updatedPerfil);
        updatedPerfil
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        PerfilDTO perfilDTO = perfilMapper.toDto(updatedPerfil);

        restPerfilMockMvc.perform(put("/api/perfils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isOk());

        // Validate the Perfil in the database
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeUpdate);
        Perfil testPerfil = perfilList.get(perfilList.size() - 1);
        assertThat(testPerfil.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPerfil.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPerfil.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPerfil.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPerfil() throws Exception {
        int databaseSizeBeforeUpdate = perfilRepository.findAll().size();

        // Create the Perfil
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilMockMvc.perform(put("/api/perfils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Perfil in the database
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePerfil() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        int databaseSizeBeforeDelete = perfilRepository.findAll().size();

        // Delete the perfil
        restPerfilMockMvc.perform(delete("/api/perfils/{id}", perfil.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
