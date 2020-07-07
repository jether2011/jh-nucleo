package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.RecursoTipo;
import org.tempestade.nucleo.repository.RecursoTipoRepository;
import org.tempestade.nucleo.service.RecursoTipoService;
import org.tempestade.nucleo.service.dto.RecursoTipoDTO;
import org.tempestade.nucleo.service.mapper.RecursoTipoMapper;
import org.tempestade.nucleo.service.dto.RecursoTipoCriteria;
import org.tempestade.nucleo.service.RecursoTipoQueryService;

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
    private RecursoTipoQueryService recursoTipoQueryService;

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
    public void getRecursoTiposByIdFiltering() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        Long id = recursoTipo.getId();

        defaultRecursoTipoShouldBeFound("id.equals=" + id);
        defaultRecursoTipoShouldNotBeFound("id.notEquals=" + id);

        defaultRecursoTipoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRecursoTipoShouldNotBeFound("id.greaterThan=" + id);

        defaultRecursoTipoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRecursoTipoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRecursoTiposByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where name equals to DEFAULT_NAME
        defaultRecursoTipoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the recursoTipoList where name equals to UPDATED_NAME
        defaultRecursoTipoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecursoTiposByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where name not equals to DEFAULT_NAME
        defaultRecursoTipoShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the recursoTipoList where name not equals to UPDATED_NAME
        defaultRecursoTipoShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecursoTiposByNameIsInShouldWork() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRecursoTipoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the recursoTipoList where name equals to UPDATED_NAME
        defaultRecursoTipoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecursoTiposByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where name is not null
        defaultRecursoTipoShouldBeFound("name.specified=true");

        // Get all the recursoTipoList where name is null
        defaultRecursoTipoShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllRecursoTiposByNameContainsSomething() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where name contains DEFAULT_NAME
        defaultRecursoTipoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the recursoTipoList where name contains UPDATED_NAME
        defaultRecursoTipoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecursoTiposByNameNotContainsSomething() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where name does not contain DEFAULT_NAME
        defaultRecursoTipoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the recursoTipoList where name does not contain UPDATED_NAME
        defaultRecursoTipoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllRecursoTiposByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where descricao equals to DEFAULT_DESCRICAO
        defaultRecursoTipoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the recursoTipoList where descricao equals to UPDATED_DESCRICAO
        defaultRecursoTipoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllRecursoTiposByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where descricao not equals to DEFAULT_DESCRICAO
        defaultRecursoTipoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the recursoTipoList where descricao not equals to UPDATED_DESCRICAO
        defaultRecursoTipoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllRecursoTiposByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultRecursoTipoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the recursoTipoList where descricao equals to UPDATED_DESCRICAO
        defaultRecursoTipoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllRecursoTiposByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where descricao is not null
        defaultRecursoTipoShouldBeFound("descricao.specified=true");

        // Get all the recursoTipoList where descricao is null
        defaultRecursoTipoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllRecursoTiposByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where descricao contains DEFAULT_DESCRICAO
        defaultRecursoTipoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the recursoTipoList where descricao contains UPDATED_DESCRICAO
        defaultRecursoTipoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllRecursoTiposByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where descricao does not contain DEFAULT_DESCRICAO
        defaultRecursoTipoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the recursoTipoList where descricao does not contain UPDATED_DESCRICAO
        defaultRecursoTipoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllRecursoTiposByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where created equals to DEFAULT_CREATED
        defaultRecursoTipoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the recursoTipoList where created equals to UPDATED_CREATED
        defaultRecursoTipoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllRecursoTiposByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where created not equals to DEFAULT_CREATED
        defaultRecursoTipoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the recursoTipoList where created not equals to UPDATED_CREATED
        defaultRecursoTipoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllRecursoTiposByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultRecursoTipoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the recursoTipoList where created equals to UPDATED_CREATED
        defaultRecursoTipoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllRecursoTiposByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where created is not null
        defaultRecursoTipoShouldBeFound("created.specified=true");

        // Get all the recursoTipoList where created is null
        defaultRecursoTipoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursoTiposByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where updated equals to DEFAULT_UPDATED
        defaultRecursoTipoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the recursoTipoList where updated equals to UPDATED_UPDATED
        defaultRecursoTipoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllRecursoTiposByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where updated not equals to DEFAULT_UPDATED
        defaultRecursoTipoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the recursoTipoList where updated not equals to UPDATED_UPDATED
        defaultRecursoTipoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllRecursoTiposByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultRecursoTipoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the recursoTipoList where updated equals to UPDATED_UPDATED
        defaultRecursoTipoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllRecursoTiposByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursoTipoRepository.saveAndFlush(recursoTipo);

        // Get all the recursoTipoList where updated is not null
        defaultRecursoTipoShouldBeFound("updated.specified=true");

        // Get all the recursoTipoList where updated is null
        defaultRecursoTipoShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRecursoTipoShouldBeFound(String filter) throws Exception {
        restRecursoTipoMockMvc.perform(get("/api/recurso-tipos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recursoTipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restRecursoTipoMockMvc.perform(get("/api/recurso-tipos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRecursoTipoShouldNotBeFound(String filter) throws Exception {
        restRecursoTipoMockMvc.perform(get("/api/recurso-tipos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRecursoTipoMockMvc.perform(get("/api/recurso-tipos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
