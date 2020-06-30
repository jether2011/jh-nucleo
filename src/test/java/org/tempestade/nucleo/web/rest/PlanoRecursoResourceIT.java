package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.PlanoRecurso;
import org.tempestade.nucleo.domain.Plano;
import org.tempestade.nucleo.domain.Recurso;
import org.tempestade.nucleo.repository.PlanoRecursoRepository;
import org.tempestade.nucleo.service.PlanoRecursoService;
import org.tempestade.nucleo.service.dto.PlanoRecursoDTO;
import org.tempestade.nucleo.service.mapper.PlanoRecursoMapper;
import org.tempestade.nucleo.service.dto.PlanoRecursoCriteria;
import org.tempestade.nucleo.service.PlanoRecursoQueryService;

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
 * Integration tests for the {@link PlanoRecursoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlanoRecursoResourceIT {

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
    private PlanoRecursoRepository planoRecursoRepository;

    @Autowired
    private PlanoRecursoMapper planoRecursoMapper;

    @Autowired
    private PlanoRecursoService planoRecursoService;

    @Autowired
    private PlanoRecursoQueryService planoRecursoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoRecursoMockMvc;

    private PlanoRecurso planoRecurso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoRecurso createEntity(EntityManager em) {
        PlanoRecurso planoRecurso = new PlanoRecurso()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .ativo(DEFAULT_ATIVO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        // Add required entity
        Plano plano;
        if (TestUtil.findAll(em, Plano.class).isEmpty()) {
            plano = PlanoResourceIT.createEntity(em);
            em.persist(plano);
            em.flush();
        } else {
            plano = TestUtil.findAll(em, Plano.class).get(0);
        }
        planoRecurso.setPlano(plano);
        // Add required entity
        Recurso recurso;
        if (TestUtil.findAll(em, Recurso.class).isEmpty()) {
            recurso = RecursoResourceIT.createEntity(em);
            em.persist(recurso);
            em.flush();
        } else {
            recurso = TestUtil.findAll(em, Recurso.class).get(0);
        }
        planoRecurso.setRecurso(recurso);
        return planoRecurso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoRecurso createUpdatedEntity(EntityManager em) {
        PlanoRecurso planoRecurso = new PlanoRecurso()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .ativo(UPDATED_ATIVO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        // Add required entity
        Plano plano;
        if (TestUtil.findAll(em, Plano.class).isEmpty()) {
            plano = PlanoResourceIT.createUpdatedEntity(em);
            em.persist(plano);
            em.flush();
        } else {
            plano = TestUtil.findAll(em, Plano.class).get(0);
        }
        planoRecurso.setPlano(plano);
        // Add required entity
        Recurso recurso;
        if (TestUtil.findAll(em, Recurso.class).isEmpty()) {
            recurso = RecursoResourceIT.createUpdatedEntity(em);
            em.persist(recurso);
            em.flush();
        } else {
            recurso = TestUtil.findAll(em, Recurso.class).get(0);
        }
        planoRecurso.setRecurso(recurso);
        return planoRecurso;
    }

    @BeforeEach
    public void initTest() {
        planoRecurso = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoRecurso() throws Exception {
        int databaseSizeBeforeCreate = planoRecursoRepository.findAll().size();
        // Create the PlanoRecurso
        PlanoRecursoDTO planoRecursoDTO = planoRecursoMapper.toDto(planoRecurso);
        restPlanoRecursoMockMvc.perform(post("/api/plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanoRecurso in the database
        List<PlanoRecurso> planoRecursoList = planoRecursoRepository.findAll();
        assertThat(planoRecursoList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoRecurso testPlanoRecurso = planoRecursoList.get(planoRecursoList.size() - 1);
        assertThat(testPlanoRecurso.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlanoRecurso.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlanoRecurso.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testPlanoRecurso.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPlanoRecurso.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createPlanoRecursoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoRecursoRepository.findAll().size();

        // Create the PlanoRecurso with an existing ID
        planoRecurso.setId(1L);
        PlanoRecursoDTO planoRecursoDTO = planoRecursoMapper.toDto(planoRecurso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoRecursoMockMvc.perform(post("/api/plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoRecurso in the database
        List<PlanoRecurso> planoRecursoList = planoRecursoRepository.findAll();
        assertThat(planoRecursoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRecursoRepository.findAll().size();
        // set the field null
        planoRecurso.setName(null);

        // Create the PlanoRecurso, which fails.
        PlanoRecursoDTO planoRecursoDTO = planoRecursoMapper.toDto(planoRecurso);


        restPlanoRecursoMockMvc.perform(post("/api/plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRecurso> planoRecursoList = planoRecursoRepository.findAll();
        assertThat(planoRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRecursoRepository.findAll().size();
        // set the field null
        planoRecurso.setDescricao(null);

        // Create the PlanoRecurso, which fails.
        PlanoRecursoDTO planoRecursoDTO = planoRecursoMapper.toDto(planoRecurso);


        restPlanoRecursoMockMvc.perform(post("/api/plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRecurso> planoRecursoList = planoRecursoRepository.findAll();
        assertThat(planoRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRecursoRepository.findAll().size();
        // set the field null
        planoRecurso.setCreated(null);

        // Create the PlanoRecurso, which fails.
        PlanoRecursoDTO planoRecursoDTO = planoRecursoMapper.toDto(planoRecurso);


        restPlanoRecursoMockMvc.perform(post("/api/plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRecurso> planoRecursoList = planoRecursoRepository.findAll();
        assertThat(planoRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursos() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList
        restPlanoRecursoMockMvc.perform(get("/api/plano-recursos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getPlanoRecurso() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get the planoRecurso
        restPlanoRecursoMockMvc.perform(get("/api/plano-recursos/{id}", planoRecurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoRecurso.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getPlanoRecursosByIdFiltering() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        Long id = planoRecurso.getId();

        defaultPlanoRecursoShouldBeFound("id.equals=" + id);
        defaultPlanoRecursoShouldNotBeFound("id.notEquals=" + id);

        defaultPlanoRecursoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlanoRecursoShouldNotBeFound("id.greaterThan=" + id);

        defaultPlanoRecursoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlanoRecursoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPlanoRecursosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where name equals to DEFAULT_NAME
        defaultPlanoRecursoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the planoRecursoList where name equals to UPDATED_NAME
        defaultPlanoRecursoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where name not equals to DEFAULT_NAME
        defaultPlanoRecursoShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the planoRecursoList where name not equals to UPDATED_NAME
        defaultPlanoRecursoShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPlanoRecursoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the planoRecursoList where name equals to UPDATED_NAME
        defaultPlanoRecursoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where name is not null
        defaultPlanoRecursoShouldBeFound("name.specified=true");

        // Get all the planoRecursoList where name is null
        defaultPlanoRecursoShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanoRecursosByNameContainsSomething() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where name contains DEFAULT_NAME
        defaultPlanoRecursoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the planoRecursoList where name contains UPDATED_NAME
        defaultPlanoRecursoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where name does not contain DEFAULT_NAME
        defaultPlanoRecursoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the planoRecursoList where name does not contain UPDATED_NAME
        defaultPlanoRecursoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPlanoRecursosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where descricao equals to DEFAULT_DESCRICAO
        defaultPlanoRecursoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the planoRecursoList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoRecursoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where descricao not equals to DEFAULT_DESCRICAO
        defaultPlanoRecursoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the planoRecursoList where descricao not equals to UPDATED_DESCRICAO
        defaultPlanoRecursoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultPlanoRecursoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the planoRecursoList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoRecursoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where descricao is not null
        defaultPlanoRecursoShouldBeFound("descricao.specified=true");

        // Get all the planoRecursoList where descricao is null
        defaultPlanoRecursoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanoRecursosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where descricao contains DEFAULT_DESCRICAO
        defaultPlanoRecursoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the planoRecursoList where descricao contains UPDATED_DESCRICAO
        defaultPlanoRecursoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where descricao does not contain DEFAULT_DESCRICAO
        defaultPlanoRecursoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the planoRecursoList where descricao does not contain UPDATED_DESCRICAO
        defaultPlanoRecursoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllPlanoRecursosByAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where ativo equals to DEFAULT_ATIVO
        defaultPlanoRecursoShouldBeFound("ativo.equals=" + DEFAULT_ATIVO);

        // Get all the planoRecursoList where ativo equals to UPDATED_ATIVO
        defaultPlanoRecursoShouldNotBeFound("ativo.equals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByAtivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where ativo not equals to DEFAULT_ATIVO
        defaultPlanoRecursoShouldNotBeFound("ativo.notEquals=" + DEFAULT_ATIVO);

        // Get all the planoRecursoList where ativo not equals to UPDATED_ATIVO
        defaultPlanoRecursoShouldBeFound("ativo.notEquals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where ativo in DEFAULT_ATIVO or UPDATED_ATIVO
        defaultPlanoRecursoShouldBeFound("ativo.in=" + DEFAULT_ATIVO + "," + UPDATED_ATIVO);

        // Get all the planoRecursoList where ativo equals to UPDATED_ATIVO
        defaultPlanoRecursoShouldNotBeFound("ativo.in=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where ativo is not null
        defaultPlanoRecursoShouldBeFound("ativo.specified=true");

        // Get all the planoRecursoList where ativo is null
        defaultPlanoRecursoShouldNotBeFound("ativo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where created equals to DEFAULT_CREATED
        defaultPlanoRecursoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the planoRecursoList where created equals to UPDATED_CREATED
        defaultPlanoRecursoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where created not equals to DEFAULT_CREATED
        defaultPlanoRecursoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the planoRecursoList where created not equals to UPDATED_CREATED
        defaultPlanoRecursoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultPlanoRecursoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the planoRecursoList where created equals to UPDATED_CREATED
        defaultPlanoRecursoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where created is not null
        defaultPlanoRecursoShouldBeFound("created.specified=true");

        // Get all the planoRecursoList where created is null
        defaultPlanoRecursoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where updated equals to DEFAULT_UPDATED
        defaultPlanoRecursoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the planoRecursoList where updated equals to UPDATED_UPDATED
        defaultPlanoRecursoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where updated not equals to DEFAULT_UPDATED
        defaultPlanoRecursoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the planoRecursoList where updated not equals to UPDATED_UPDATED
        defaultPlanoRecursoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultPlanoRecursoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the planoRecursoList where updated equals to UPDATED_UPDATED
        defaultPlanoRecursoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        // Get all the planoRecursoList where updated is not null
        defaultPlanoRecursoShouldBeFound("updated.specified=true");

        // Get all the planoRecursoList where updated is null
        defaultPlanoRecursoShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanoRecursosByPlanoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Plano plano = planoRecurso.getPlano();
        planoRecursoRepository.saveAndFlush(planoRecurso);
        Long planoId = plano.getId();

        // Get all the planoRecursoList where plano equals to planoId
        defaultPlanoRecursoShouldBeFound("planoId.equals=" + planoId);

        // Get all the planoRecursoList where plano equals to planoId + 1
        defaultPlanoRecursoShouldNotBeFound("planoId.equals=" + (planoId + 1));
    }


    @Test
    @Transactional
    public void getAllPlanoRecursosByRecursoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Recurso recurso = planoRecurso.getRecurso();
        planoRecursoRepository.saveAndFlush(planoRecurso);
        Long recursoId = recurso.getId();

        // Get all the planoRecursoList where recurso equals to recursoId
        defaultPlanoRecursoShouldBeFound("recursoId.equals=" + recursoId);

        // Get all the planoRecursoList where recurso equals to recursoId + 1
        defaultPlanoRecursoShouldNotBeFound("recursoId.equals=" + (recursoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlanoRecursoShouldBeFound(String filter) throws Exception {
        restPlanoRecursoMockMvc.perform(get("/api/plano-recursos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restPlanoRecursoMockMvc.perform(get("/api/plano-recursos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlanoRecursoShouldNotBeFound(String filter) throws Exception {
        restPlanoRecursoMockMvc.perform(get("/api/plano-recursos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanoRecursoMockMvc.perform(get("/api/plano-recursos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPlanoRecurso() throws Exception {
        // Get the planoRecurso
        restPlanoRecursoMockMvc.perform(get("/api/plano-recursos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoRecurso() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        int databaseSizeBeforeUpdate = planoRecursoRepository.findAll().size();

        // Update the planoRecurso
        PlanoRecurso updatedPlanoRecurso = planoRecursoRepository.findById(planoRecurso.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoRecurso are not directly saved in db
        em.detach(updatedPlanoRecurso);
        updatedPlanoRecurso
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .ativo(UPDATED_ATIVO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        PlanoRecursoDTO planoRecursoDTO = planoRecursoMapper.toDto(updatedPlanoRecurso);

        restPlanoRecursoMockMvc.perform(put("/api/plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoDTO)))
            .andExpect(status().isOk());

        // Validate the PlanoRecurso in the database
        List<PlanoRecurso> planoRecursoList = planoRecursoRepository.findAll();
        assertThat(planoRecursoList).hasSize(databaseSizeBeforeUpdate);
        PlanoRecurso testPlanoRecurso = planoRecursoList.get(planoRecursoList.size() - 1);
        assertThat(testPlanoRecurso.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanoRecurso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoRecurso.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testPlanoRecurso.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPlanoRecurso.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = planoRecursoRepository.findAll().size();

        // Create the PlanoRecurso
        PlanoRecursoDTO planoRecursoDTO = planoRecursoMapper.toDto(planoRecurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoRecursoMockMvc.perform(put("/api/plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoRecurso in the database
        List<PlanoRecurso> planoRecursoList = planoRecursoRepository.findAll();
        assertThat(planoRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanoRecurso() throws Exception {
        // Initialize the database
        planoRecursoRepository.saveAndFlush(planoRecurso);

        int databaseSizeBeforeDelete = planoRecursoRepository.findAll().size();

        // Delete the planoRecurso
        restPlanoRecursoMockMvc.perform(delete("/api/plano-recursos/{id}", planoRecurso.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoRecurso> planoRecursoList = planoRecursoRepository.findAll();
        assertThat(planoRecursoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
