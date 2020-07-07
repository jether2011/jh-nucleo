package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Recurso;
import org.tempestade.nucleo.domain.RecursoTipo;
import org.tempestade.nucleo.domain.VariavelMeteorologica;
import org.tempestade.nucleo.repository.RecursoRepository;
import org.tempestade.nucleo.service.RecursoService;
import org.tempestade.nucleo.service.dto.RecursoDTO;
import org.tempestade.nucleo.service.mapper.RecursoMapper;
import org.tempestade.nucleo.service.dto.RecursoCriteria;
import org.tempestade.nucleo.service.RecursoQueryService;

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
 * Integration tests for the {@link RecursoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecursoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private RecursoMapper recursoMapper;

    @Autowired
    private RecursoService recursoService;

    @Autowired
    private RecursoQueryService recursoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecursoMockMvc;

    private Recurso recurso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recurso createEntity(EntityManager em) {
        Recurso recurso = new Recurso()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .ativo(DEFAULT_ATIVO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return recurso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recurso createUpdatedEntity(EntityManager em) {
        Recurso recurso = new Recurso()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .ativo(UPDATED_ATIVO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return recurso;
    }

    @BeforeEach
    public void initTest() {
        recurso = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecurso() throws Exception {
        int databaseSizeBeforeCreate = recursoRepository.findAll().size();
        // Create the Recurso
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);
        restRecursoMockMvc.perform(post("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoDTO)))
            .andExpect(status().isCreated());

        // Validate the Recurso in the database
        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeCreate + 1);
        Recurso testRecurso = recursoList.get(recursoList.size() - 1);
        assertThat(testRecurso.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRecurso.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testRecurso.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testRecurso.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testRecurso.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createRecursoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recursoRepository.findAll().size();

        // Create the Recurso with an existing ID
        recurso.setId(1L);
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecursoMockMvc.perform(post("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recurso in the database
        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoRepository.findAll().size();
        // set the field null
        recurso.setName(null);

        // Create the Recurso, which fails.
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);


        restRecursoMockMvc.perform(post("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoDTO)))
            .andExpect(status().isBadRequest());

        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoRepository.findAll().size();
        // set the field null
        recurso.setDescricao(null);

        // Create the Recurso, which fails.
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);


        restRecursoMockMvc.perform(post("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoDTO)))
            .andExpect(status().isBadRequest());

        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoRepository.findAll().size();
        // set the field null
        recurso.setCreated(null);

        // Create the Recurso, which fails.
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);


        restRecursoMockMvc.perform(post("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoDTO)))
            .andExpect(status().isBadRequest());

        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecursos() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList
        restRecursoMockMvc.perform(get("/api/recursos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getRecurso() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get the recurso
        restRecursoMockMvc.perform(get("/api/recursos/{id}", recurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recurso.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getRecursosByIdFiltering() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        Long id = recurso.getId();

        defaultRecursoShouldBeFound("id.equals=" + id);
        defaultRecursoShouldNotBeFound("id.notEquals=" + id);

        defaultRecursoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRecursoShouldNotBeFound("id.greaterThan=" + id);

        defaultRecursoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRecursoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRecursosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where name equals to DEFAULT_NAME
        defaultRecursoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the recursoList where name equals to UPDATED_NAME
        defaultRecursoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecursosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where name not equals to DEFAULT_NAME
        defaultRecursoShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the recursoList where name not equals to UPDATED_NAME
        defaultRecursoShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecursosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRecursoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the recursoList where name equals to UPDATED_NAME
        defaultRecursoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecursosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where name is not null
        defaultRecursoShouldBeFound("name.specified=true");

        // Get all the recursoList where name is null
        defaultRecursoShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllRecursosByNameContainsSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where name contains DEFAULT_NAME
        defaultRecursoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the recursoList where name contains UPDATED_NAME
        defaultRecursoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecursosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where name does not contain DEFAULT_NAME
        defaultRecursoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the recursoList where name does not contain UPDATED_NAME
        defaultRecursoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllRecursosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where descricao equals to DEFAULT_DESCRICAO
        defaultRecursoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the recursoList where descricao equals to UPDATED_DESCRICAO
        defaultRecursoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllRecursosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where descricao not equals to DEFAULT_DESCRICAO
        defaultRecursoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the recursoList where descricao not equals to UPDATED_DESCRICAO
        defaultRecursoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllRecursosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultRecursoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the recursoList where descricao equals to UPDATED_DESCRICAO
        defaultRecursoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllRecursosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where descricao is not null
        defaultRecursoShouldBeFound("descricao.specified=true");

        // Get all the recursoList where descricao is null
        defaultRecursoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllRecursosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where descricao contains DEFAULT_DESCRICAO
        defaultRecursoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the recursoList where descricao contains UPDATED_DESCRICAO
        defaultRecursoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllRecursosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where descricao does not contain DEFAULT_DESCRICAO
        defaultRecursoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the recursoList where descricao does not contain UPDATED_DESCRICAO
        defaultRecursoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllRecursosByAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where ativo equals to DEFAULT_ATIVO
        defaultRecursoShouldBeFound("ativo.equals=" + DEFAULT_ATIVO);

        // Get all the recursoList where ativo equals to UPDATED_ATIVO
        defaultRecursoShouldNotBeFound("ativo.equals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllRecursosByAtivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where ativo not equals to DEFAULT_ATIVO
        defaultRecursoShouldNotBeFound("ativo.notEquals=" + DEFAULT_ATIVO);

        // Get all the recursoList where ativo not equals to UPDATED_ATIVO
        defaultRecursoShouldBeFound("ativo.notEquals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllRecursosByAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where ativo in DEFAULT_ATIVO or UPDATED_ATIVO
        defaultRecursoShouldBeFound("ativo.in=" + DEFAULT_ATIVO + "," + UPDATED_ATIVO);

        // Get all the recursoList where ativo equals to UPDATED_ATIVO
        defaultRecursoShouldNotBeFound("ativo.in=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllRecursosByAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where ativo is not null
        defaultRecursoShouldBeFound("ativo.specified=true");

        // Get all the recursoList where ativo is null
        defaultRecursoShouldNotBeFound("ativo.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where created equals to DEFAULT_CREATED
        defaultRecursoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the recursoList where created equals to UPDATED_CREATED
        defaultRecursoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllRecursosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where created not equals to DEFAULT_CREATED
        defaultRecursoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the recursoList where created not equals to UPDATED_CREATED
        defaultRecursoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllRecursosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultRecursoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the recursoList where created equals to UPDATED_CREATED
        defaultRecursoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllRecursosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where created is not null
        defaultRecursoShouldBeFound("created.specified=true");

        // Get all the recursoList where created is null
        defaultRecursoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where updated equals to DEFAULT_UPDATED
        defaultRecursoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the recursoList where updated equals to UPDATED_UPDATED
        defaultRecursoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllRecursosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where updated not equals to DEFAULT_UPDATED
        defaultRecursoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the recursoList where updated not equals to UPDATED_UPDATED
        defaultRecursoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllRecursosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultRecursoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the recursoList where updated equals to UPDATED_UPDATED
        defaultRecursoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllRecursosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList where updated is not null
        defaultRecursoShouldBeFound("updated.specified=true");

        // Get all the recursoList where updated is null
        defaultRecursoShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosByRecursoTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);
        RecursoTipo recursoTipo = RecursoTipoResourceIT.createEntity(em);
        em.persist(recursoTipo);
        em.flush();
        recurso.setRecursoTipo(recursoTipo);
        recursoRepository.saveAndFlush(recurso);
        Long recursoTipoId = recursoTipo.getId();

        // Get all the recursoList where recursoTipo equals to recursoTipoId
        defaultRecursoShouldBeFound("recursoTipoId.equals=" + recursoTipoId);

        // Get all the recursoList where recursoTipo equals to recursoTipoId + 1
        defaultRecursoShouldNotBeFound("recursoTipoId.equals=" + (recursoTipoId + 1));
    }


    @Test
    @Transactional
    public void getAllRecursosByVariavelMeteorologicaIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);
        VariavelMeteorologica variavelMeteorologica = VariavelMeteorologicaResourceIT.createEntity(em);
        em.persist(variavelMeteorologica);
        em.flush();
        recurso.setVariavelMeteorologica(variavelMeteorologica);
        recursoRepository.saveAndFlush(recurso);
        Long variavelMeteorologicaId = variavelMeteorologica.getId();

        // Get all the recursoList where variavelMeteorologica equals to variavelMeteorologicaId
        defaultRecursoShouldBeFound("variavelMeteorologicaId.equals=" + variavelMeteorologicaId);

        // Get all the recursoList where variavelMeteorologica equals to variavelMeteorologicaId + 1
        defaultRecursoShouldNotBeFound("variavelMeteorologicaId.equals=" + (variavelMeteorologicaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRecursoShouldBeFound(String filter) throws Exception {
        restRecursoMockMvc.perform(get("/api/recursos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restRecursoMockMvc.perform(get("/api/recursos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRecursoShouldNotBeFound(String filter) throws Exception {
        restRecursoMockMvc.perform(get("/api/recursos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRecursoMockMvc.perform(get("/api/recursos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRecurso() throws Exception {
        // Get the recurso
        restRecursoMockMvc.perform(get("/api/recursos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecurso() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        int databaseSizeBeforeUpdate = recursoRepository.findAll().size();

        // Update the recurso
        Recurso updatedRecurso = recursoRepository.findById(recurso.getId()).get();
        // Disconnect from session so that the updates on updatedRecurso are not directly saved in db
        em.detach(updatedRecurso);
        updatedRecurso
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .ativo(UPDATED_ATIVO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        RecursoDTO recursoDTO = recursoMapper.toDto(updatedRecurso);

        restRecursoMockMvc.perform(put("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoDTO)))
            .andExpect(status().isOk());

        // Validate the Recurso in the database
        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeUpdate);
        Recurso testRecurso = recursoList.get(recursoList.size() - 1);
        assertThat(testRecurso.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRecurso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testRecurso.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testRecurso.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testRecurso.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingRecurso() throws Exception {
        int databaseSizeBeforeUpdate = recursoRepository.findAll().size();

        // Create the Recurso
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecursoMockMvc.perform(put("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recurso in the database
        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecurso() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        int databaseSizeBeforeDelete = recursoRepository.findAll().size();

        // Delete the recurso
        restRecursoMockMvc.perform(delete("/api/recursos/{id}", recurso.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
