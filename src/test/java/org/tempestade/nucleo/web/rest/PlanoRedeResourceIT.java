package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.PlanoRede;
import org.tempestade.nucleo.domain.Plano;
import org.tempestade.nucleo.domain.Rede;
import org.tempestade.nucleo.repository.PlanoRedeRepository;
import org.tempestade.nucleo.service.PlanoRedeService;
import org.tempestade.nucleo.service.dto.PlanoRedeDTO;
import org.tempestade.nucleo.service.mapper.PlanoRedeMapper;
import org.tempestade.nucleo.service.dto.PlanoRedeCriteria;
import org.tempestade.nucleo.service.PlanoRedeQueryService;

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
 * Integration tests for the {@link PlanoRedeResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlanoRedeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PlanoRedeRepository planoRedeRepository;

    @Autowired
    private PlanoRedeMapper planoRedeMapper;

    @Autowired
    private PlanoRedeService planoRedeService;

    @Autowired
    private PlanoRedeQueryService planoRedeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoRedeMockMvc;

    private PlanoRede planoRede;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoRede createEntity(EntityManager em) {
        PlanoRede planoRede = new PlanoRede()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
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
        planoRede.setPlano(plano);
        // Add required entity
        Rede rede;
        if (TestUtil.findAll(em, Rede.class).isEmpty()) {
            rede = RedeResourceIT.createEntity(em);
            em.persist(rede);
            em.flush();
        } else {
            rede = TestUtil.findAll(em, Rede.class).get(0);
        }
        planoRede.setRede(rede);
        return planoRede;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoRede createUpdatedEntity(EntityManager em) {
        PlanoRede planoRede = new PlanoRede()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
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
        planoRede.setPlano(plano);
        // Add required entity
        Rede rede;
        if (TestUtil.findAll(em, Rede.class).isEmpty()) {
            rede = RedeResourceIT.createUpdatedEntity(em);
            em.persist(rede);
            em.flush();
        } else {
            rede = TestUtil.findAll(em, Rede.class).get(0);
        }
        planoRede.setRede(rede);
        return planoRede;
    }

    @BeforeEach
    public void initTest() {
        planoRede = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoRede() throws Exception {
        int databaseSizeBeforeCreate = planoRedeRepository.findAll().size();
        // Create the PlanoRede
        PlanoRedeDTO planoRedeDTO = planoRedeMapper.toDto(planoRede);
        restPlanoRedeMockMvc.perform(post("/api/plano-redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRedeDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanoRede in the database
        List<PlanoRede> planoRedeList = planoRedeRepository.findAll();
        assertThat(planoRedeList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoRede testPlanoRede = planoRedeList.get(planoRedeList.size() - 1);
        assertThat(testPlanoRede.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlanoRede.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlanoRede.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPlanoRede.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createPlanoRedeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoRedeRepository.findAll().size();

        // Create the PlanoRede with an existing ID
        planoRede.setId(1L);
        PlanoRedeDTO planoRedeDTO = planoRedeMapper.toDto(planoRede);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoRedeMockMvc.perform(post("/api/plano-redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRedeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoRede in the database
        List<PlanoRede> planoRedeList = planoRedeRepository.findAll();
        assertThat(planoRedeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRedeRepository.findAll().size();
        // set the field null
        planoRede.setName(null);

        // Create the PlanoRede, which fails.
        PlanoRedeDTO planoRedeDTO = planoRedeMapper.toDto(planoRede);


        restPlanoRedeMockMvc.perform(post("/api/plano-redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRedeDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRede> planoRedeList = planoRedeRepository.findAll();
        assertThat(planoRedeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRedeRepository.findAll().size();
        // set the field null
        planoRede.setDescricao(null);

        // Create the PlanoRede, which fails.
        PlanoRedeDTO planoRedeDTO = planoRedeMapper.toDto(planoRede);


        restPlanoRedeMockMvc.perform(post("/api/plano-redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRedeDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRede> planoRedeList = planoRedeRepository.findAll();
        assertThat(planoRedeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRedeRepository.findAll().size();
        // set the field null
        planoRede.setCreated(null);

        // Create the PlanoRede, which fails.
        PlanoRedeDTO planoRedeDTO = planoRedeMapper.toDto(planoRede);


        restPlanoRedeMockMvc.perform(post("/api/plano-redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRedeDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRede> planoRedeList = planoRedeRepository.findAll();
        assertThat(planoRedeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanoRedes() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList
        restPlanoRedeMockMvc.perform(get("/api/plano-redes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoRede.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getPlanoRede() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get the planoRede
        restPlanoRedeMockMvc.perform(get("/api/plano-redes/{id}", planoRede.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoRede.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getPlanoRedesByIdFiltering() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        Long id = planoRede.getId();

        defaultPlanoRedeShouldBeFound("id.equals=" + id);
        defaultPlanoRedeShouldNotBeFound("id.notEquals=" + id);

        defaultPlanoRedeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlanoRedeShouldNotBeFound("id.greaterThan=" + id);

        defaultPlanoRedeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlanoRedeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPlanoRedesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where name equals to DEFAULT_NAME
        defaultPlanoRedeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the planoRedeList where name equals to UPDATED_NAME
        defaultPlanoRedeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoRedesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where name not equals to DEFAULT_NAME
        defaultPlanoRedeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the planoRedeList where name not equals to UPDATED_NAME
        defaultPlanoRedeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoRedesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPlanoRedeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the planoRedeList where name equals to UPDATED_NAME
        defaultPlanoRedeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoRedesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where name is not null
        defaultPlanoRedeShouldBeFound("name.specified=true");

        // Get all the planoRedeList where name is null
        defaultPlanoRedeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanoRedesByNameContainsSomething() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where name contains DEFAULT_NAME
        defaultPlanoRedeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the planoRedeList where name contains UPDATED_NAME
        defaultPlanoRedeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoRedesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where name does not contain DEFAULT_NAME
        defaultPlanoRedeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the planoRedeList where name does not contain UPDATED_NAME
        defaultPlanoRedeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPlanoRedesByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where descricao equals to DEFAULT_DESCRICAO
        defaultPlanoRedeShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the planoRedeList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoRedeShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoRedesByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where descricao not equals to DEFAULT_DESCRICAO
        defaultPlanoRedeShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the planoRedeList where descricao not equals to UPDATED_DESCRICAO
        defaultPlanoRedeShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoRedesByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultPlanoRedeShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the planoRedeList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoRedeShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoRedesByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where descricao is not null
        defaultPlanoRedeShouldBeFound("descricao.specified=true");

        // Get all the planoRedeList where descricao is null
        defaultPlanoRedeShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanoRedesByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where descricao contains DEFAULT_DESCRICAO
        defaultPlanoRedeShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the planoRedeList where descricao contains UPDATED_DESCRICAO
        defaultPlanoRedeShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoRedesByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where descricao does not contain DEFAULT_DESCRICAO
        defaultPlanoRedeShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the planoRedeList where descricao does not contain UPDATED_DESCRICAO
        defaultPlanoRedeShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllPlanoRedesByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where created equals to DEFAULT_CREATED
        defaultPlanoRedeShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the planoRedeList where created equals to UPDATED_CREATED
        defaultPlanoRedeShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRedesByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where created not equals to DEFAULT_CREATED
        defaultPlanoRedeShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the planoRedeList where created not equals to UPDATED_CREATED
        defaultPlanoRedeShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRedesByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultPlanoRedeShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the planoRedeList where created equals to UPDATED_CREATED
        defaultPlanoRedeShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRedesByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where created is not null
        defaultPlanoRedeShouldBeFound("created.specified=true");

        // Get all the planoRedeList where created is null
        defaultPlanoRedeShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanoRedesByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where updated equals to DEFAULT_UPDATED
        defaultPlanoRedeShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the planoRedeList where updated equals to UPDATED_UPDATED
        defaultPlanoRedeShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRedesByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where updated not equals to DEFAULT_UPDATED
        defaultPlanoRedeShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the planoRedeList where updated not equals to UPDATED_UPDATED
        defaultPlanoRedeShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRedesByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultPlanoRedeShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the planoRedeList where updated equals to UPDATED_UPDATED
        defaultPlanoRedeShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRedesByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList where updated is not null
        defaultPlanoRedeShouldBeFound("updated.specified=true");

        // Get all the planoRedeList where updated is null
        defaultPlanoRedeShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanoRedesByPlanoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Plano plano = planoRede.getPlano();
        planoRedeRepository.saveAndFlush(planoRede);
        Long planoId = plano.getId();

        // Get all the planoRedeList where plano equals to planoId
        defaultPlanoRedeShouldBeFound("planoId.equals=" + planoId);

        // Get all the planoRedeList where plano equals to planoId + 1
        defaultPlanoRedeShouldNotBeFound("planoId.equals=" + (planoId + 1));
    }


    @Test
    @Transactional
    public void getAllPlanoRedesByRedeIsEqualToSomething() throws Exception {
        // Get already existing entity
        Rede rede = planoRede.getRede();
        planoRedeRepository.saveAndFlush(planoRede);
        Long redeId = rede.getId();

        // Get all the planoRedeList where rede equals to redeId
        defaultPlanoRedeShouldBeFound("redeId.equals=" + redeId);

        // Get all the planoRedeList where rede equals to redeId + 1
        defaultPlanoRedeShouldNotBeFound("redeId.equals=" + (redeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlanoRedeShouldBeFound(String filter) throws Exception {
        restPlanoRedeMockMvc.perform(get("/api/plano-redes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoRede.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restPlanoRedeMockMvc.perform(get("/api/plano-redes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlanoRedeShouldNotBeFound(String filter) throws Exception {
        restPlanoRedeMockMvc.perform(get("/api/plano-redes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanoRedeMockMvc.perform(get("/api/plano-redes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPlanoRede() throws Exception {
        // Get the planoRede
        restPlanoRedeMockMvc.perform(get("/api/plano-redes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoRede() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        int databaseSizeBeforeUpdate = planoRedeRepository.findAll().size();

        // Update the planoRede
        PlanoRede updatedPlanoRede = planoRedeRepository.findById(planoRede.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoRede are not directly saved in db
        em.detach(updatedPlanoRede);
        updatedPlanoRede
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        PlanoRedeDTO planoRedeDTO = planoRedeMapper.toDto(updatedPlanoRede);

        restPlanoRedeMockMvc.perform(put("/api/plano-redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRedeDTO)))
            .andExpect(status().isOk());

        // Validate the PlanoRede in the database
        List<PlanoRede> planoRedeList = planoRedeRepository.findAll();
        assertThat(planoRedeList).hasSize(databaseSizeBeforeUpdate);
        PlanoRede testPlanoRede = planoRedeList.get(planoRedeList.size() - 1);
        assertThat(testPlanoRede.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanoRede.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoRede.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPlanoRede.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoRede() throws Exception {
        int databaseSizeBeforeUpdate = planoRedeRepository.findAll().size();

        // Create the PlanoRede
        PlanoRedeDTO planoRedeDTO = planoRedeMapper.toDto(planoRede);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoRedeMockMvc.perform(put("/api/plano-redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRedeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoRede in the database
        List<PlanoRede> planoRedeList = planoRedeRepository.findAll();
        assertThat(planoRedeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanoRede() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        int databaseSizeBeforeDelete = planoRedeRepository.findAll().size();

        // Delete the planoRede
        restPlanoRedeMockMvc.perform(delete("/api/plano-redes/{id}", planoRede.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoRede> planoRedeList = planoRedeRepository.findAll();
        assertThat(planoRedeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
