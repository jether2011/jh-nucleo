package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.PlanoLayer;
import org.tempestade.nucleo.domain.Plano;
import org.tempestade.nucleo.domain.Layer;
import org.tempestade.nucleo.domain.Alvo;
import org.tempestade.nucleo.repository.PlanoLayerRepository;
import org.tempestade.nucleo.service.PlanoLayerService;
import org.tempestade.nucleo.service.dto.PlanoLayerDTO;
import org.tempestade.nucleo.service.mapper.PlanoLayerMapper;
import org.tempestade.nucleo.service.dto.PlanoLayerCriteria;
import org.tempestade.nucleo.service.PlanoLayerQueryService;

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
 * Integration tests for the {@link PlanoLayerResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlanoLayerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PlanoLayerRepository planoLayerRepository;

    @Autowired
    private PlanoLayerMapper planoLayerMapper;

    @Autowired
    private PlanoLayerService planoLayerService;

    @Autowired
    private PlanoLayerQueryService planoLayerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoLayerMockMvc;

    private PlanoLayer planoLayer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoLayer createEntity(EntityManager em) {
        PlanoLayer planoLayer = new PlanoLayer()
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
        planoLayer.setPlano(plano);
        // Add required entity
        Layer layer;
        if (TestUtil.findAll(em, Layer.class).isEmpty()) {
            layer = LayerResourceIT.createEntity(em);
            em.persist(layer);
            em.flush();
        } else {
            layer = TestUtil.findAll(em, Layer.class).get(0);
        }
        planoLayer.setLayer(layer);
        return planoLayer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoLayer createUpdatedEntity(EntityManager em) {
        PlanoLayer planoLayer = new PlanoLayer()
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
        planoLayer.setPlano(plano);
        // Add required entity
        Layer layer;
        if (TestUtil.findAll(em, Layer.class).isEmpty()) {
            layer = LayerResourceIT.createUpdatedEntity(em);
            em.persist(layer);
            em.flush();
        } else {
            layer = TestUtil.findAll(em, Layer.class).get(0);
        }
        planoLayer.setLayer(layer);
        return planoLayer;
    }

    @BeforeEach
    public void initTest() {
        planoLayer = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoLayer() throws Exception {
        int databaseSizeBeforeCreate = planoLayerRepository.findAll().size();
        // Create the PlanoLayer
        PlanoLayerDTO planoLayerDTO = planoLayerMapper.toDto(planoLayer);
        restPlanoLayerMockMvc.perform(post("/api/plano-layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoLayerDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanoLayer in the database
        List<PlanoLayer> planoLayerList = planoLayerRepository.findAll();
        assertThat(planoLayerList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoLayer testPlanoLayer = planoLayerList.get(planoLayerList.size() - 1);
        assertThat(testPlanoLayer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlanoLayer.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlanoLayer.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPlanoLayer.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createPlanoLayerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoLayerRepository.findAll().size();

        // Create the PlanoLayer with an existing ID
        planoLayer.setId(1L);
        PlanoLayerDTO planoLayerDTO = planoLayerMapper.toDto(planoLayer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoLayerMockMvc.perform(post("/api/plano-layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoLayerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoLayer in the database
        List<PlanoLayer> planoLayerList = planoLayerRepository.findAll();
        assertThat(planoLayerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoLayerRepository.findAll().size();
        // set the field null
        planoLayer.setName(null);

        // Create the PlanoLayer, which fails.
        PlanoLayerDTO planoLayerDTO = planoLayerMapper.toDto(planoLayer);


        restPlanoLayerMockMvc.perform(post("/api/plano-layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoLayerDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoLayer> planoLayerList = planoLayerRepository.findAll();
        assertThat(planoLayerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoLayerRepository.findAll().size();
        // set the field null
        planoLayer.setDescricao(null);

        // Create the PlanoLayer, which fails.
        PlanoLayerDTO planoLayerDTO = planoLayerMapper.toDto(planoLayer);


        restPlanoLayerMockMvc.perform(post("/api/plano-layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoLayerDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoLayer> planoLayerList = planoLayerRepository.findAll();
        assertThat(planoLayerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoLayerRepository.findAll().size();
        // set the field null
        planoLayer.setCreated(null);

        // Create the PlanoLayer, which fails.
        PlanoLayerDTO planoLayerDTO = planoLayerMapper.toDto(planoLayer);


        restPlanoLayerMockMvc.perform(post("/api/plano-layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoLayerDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoLayer> planoLayerList = planoLayerRepository.findAll();
        assertThat(planoLayerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanoLayers() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList
        restPlanoLayerMockMvc.perform(get("/api/plano-layers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoLayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getPlanoLayer() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get the planoLayer
        restPlanoLayerMockMvc.perform(get("/api/plano-layers/{id}", planoLayer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoLayer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getPlanoLayersByIdFiltering() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        Long id = planoLayer.getId();

        defaultPlanoLayerShouldBeFound("id.equals=" + id);
        defaultPlanoLayerShouldNotBeFound("id.notEquals=" + id);

        defaultPlanoLayerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlanoLayerShouldNotBeFound("id.greaterThan=" + id);

        defaultPlanoLayerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlanoLayerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPlanoLayersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where name equals to DEFAULT_NAME
        defaultPlanoLayerShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the planoLayerList where name equals to UPDATED_NAME
        defaultPlanoLayerShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoLayersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where name not equals to DEFAULT_NAME
        defaultPlanoLayerShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the planoLayerList where name not equals to UPDATED_NAME
        defaultPlanoLayerShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoLayersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPlanoLayerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the planoLayerList where name equals to UPDATED_NAME
        defaultPlanoLayerShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoLayersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where name is not null
        defaultPlanoLayerShouldBeFound("name.specified=true");

        // Get all the planoLayerList where name is null
        defaultPlanoLayerShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanoLayersByNameContainsSomething() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where name contains DEFAULT_NAME
        defaultPlanoLayerShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the planoLayerList where name contains UPDATED_NAME
        defaultPlanoLayerShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoLayersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where name does not contain DEFAULT_NAME
        defaultPlanoLayerShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the planoLayerList where name does not contain UPDATED_NAME
        defaultPlanoLayerShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPlanoLayersByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where descricao equals to DEFAULT_DESCRICAO
        defaultPlanoLayerShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the planoLayerList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoLayerShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoLayersByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where descricao not equals to DEFAULT_DESCRICAO
        defaultPlanoLayerShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the planoLayerList where descricao not equals to UPDATED_DESCRICAO
        defaultPlanoLayerShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoLayersByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultPlanoLayerShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the planoLayerList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoLayerShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoLayersByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where descricao is not null
        defaultPlanoLayerShouldBeFound("descricao.specified=true");

        // Get all the planoLayerList where descricao is null
        defaultPlanoLayerShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanoLayersByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where descricao contains DEFAULT_DESCRICAO
        defaultPlanoLayerShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the planoLayerList where descricao contains UPDATED_DESCRICAO
        defaultPlanoLayerShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoLayersByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where descricao does not contain DEFAULT_DESCRICAO
        defaultPlanoLayerShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the planoLayerList where descricao does not contain UPDATED_DESCRICAO
        defaultPlanoLayerShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllPlanoLayersByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where created equals to DEFAULT_CREATED
        defaultPlanoLayerShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the planoLayerList where created equals to UPDATED_CREATED
        defaultPlanoLayerShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoLayersByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where created not equals to DEFAULT_CREATED
        defaultPlanoLayerShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the planoLayerList where created not equals to UPDATED_CREATED
        defaultPlanoLayerShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoLayersByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultPlanoLayerShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the planoLayerList where created equals to UPDATED_CREATED
        defaultPlanoLayerShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoLayersByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where created is not null
        defaultPlanoLayerShouldBeFound("created.specified=true");

        // Get all the planoLayerList where created is null
        defaultPlanoLayerShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanoLayersByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where updated equals to DEFAULT_UPDATED
        defaultPlanoLayerShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the planoLayerList where updated equals to UPDATED_UPDATED
        defaultPlanoLayerShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoLayersByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where updated not equals to DEFAULT_UPDATED
        defaultPlanoLayerShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the planoLayerList where updated not equals to UPDATED_UPDATED
        defaultPlanoLayerShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoLayersByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultPlanoLayerShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the planoLayerList where updated equals to UPDATED_UPDATED
        defaultPlanoLayerShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoLayersByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        // Get all the planoLayerList where updated is not null
        defaultPlanoLayerShouldBeFound("updated.specified=true");

        // Get all the planoLayerList where updated is null
        defaultPlanoLayerShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanoLayersByPlanoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Plano plano = planoLayer.getPlano();
        planoLayerRepository.saveAndFlush(planoLayer);
        Long planoId = plano.getId();

        // Get all the planoLayerList where plano equals to planoId
        defaultPlanoLayerShouldBeFound("planoId.equals=" + planoId);

        // Get all the planoLayerList where plano equals to planoId + 1
        defaultPlanoLayerShouldNotBeFound("planoId.equals=" + (planoId + 1));
    }


    @Test
    @Transactional
    public void getAllPlanoLayersByLayerIsEqualToSomething() throws Exception {
        // Get already existing entity
        Layer layer = planoLayer.getLayer();
        planoLayerRepository.saveAndFlush(planoLayer);
        Long layerId = layer.getId();

        // Get all the planoLayerList where layer equals to layerId
        defaultPlanoLayerShouldBeFound("layerId.equals=" + layerId);

        // Get all the planoLayerList where layer equals to layerId + 1
        defaultPlanoLayerShouldNotBeFound("layerId.equals=" + (layerId + 1));
    }


    @Test
    @Transactional
    public void getAllPlanoLayersByAlvoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);
        Alvo alvo = AlvoResourceIT.createEntity(em);
        em.persist(alvo);
        em.flush();
        planoLayer.setAlvo(alvo);
        planoLayerRepository.saveAndFlush(planoLayer);
        Long alvoId = alvo.getId();

        // Get all the planoLayerList where alvo equals to alvoId
        defaultPlanoLayerShouldBeFound("alvoId.equals=" + alvoId);

        // Get all the planoLayerList where alvo equals to alvoId + 1
        defaultPlanoLayerShouldNotBeFound("alvoId.equals=" + (alvoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlanoLayerShouldBeFound(String filter) throws Exception {
        restPlanoLayerMockMvc.perform(get("/api/plano-layers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoLayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restPlanoLayerMockMvc.perform(get("/api/plano-layers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlanoLayerShouldNotBeFound(String filter) throws Exception {
        restPlanoLayerMockMvc.perform(get("/api/plano-layers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanoLayerMockMvc.perform(get("/api/plano-layers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPlanoLayer() throws Exception {
        // Get the planoLayer
        restPlanoLayerMockMvc.perform(get("/api/plano-layers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoLayer() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        int databaseSizeBeforeUpdate = planoLayerRepository.findAll().size();

        // Update the planoLayer
        PlanoLayer updatedPlanoLayer = planoLayerRepository.findById(planoLayer.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoLayer are not directly saved in db
        em.detach(updatedPlanoLayer);
        updatedPlanoLayer
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        PlanoLayerDTO planoLayerDTO = planoLayerMapper.toDto(updatedPlanoLayer);

        restPlanoLayerMockMvc.perform(put("/api/plano-layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoLayerDTO)))
            .andExpect(status().isOk());

        // Validate the PlanoLayer in the database
        List<PlanoLayer> planoLayerList = planoLayerRepository.findAll();
        assertThat(planoLayerList).hasSize(databaseSizeBeforeUpdate);
        PlanoLayer testPlanoLayer = planoLayerList.get(planoLayerList.size() - 1);
        assertThat(testPlanoLayer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanoLayer.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoLayer.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPlanoLayer.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoLayer() throws Exception {
        int databaseSizeBeforeUpdate = planoLayerRepository.findAll().size();

        // Create the PlanoLayer
        PlanoLayerDTO planoLayerDTO = planoLayerMapper.toDto(planoLayer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoLayerMockMvc.perform(put("/api/plano-layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoLayerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoLayer in the database
        List<PlanoLayer> planoLayerList = planoLayerRepository.findAll();
        assertThat(planoLayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanoLayer() throws Exception {
        // Initialize the database
        planoLayerRepository.saveAndFlush(planoLayer);

        int databaseSizeBeforeDelete = planoLayerRepository.findAll().size();

        // Delete the planoLayer
        restPlanoLayerMockMvc.perform(delete("/api/plano-layers/{id}", planoLayer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoLayer> planoLayerList = planoLayerRepository.findAll();
        assertThat(planoLayerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
