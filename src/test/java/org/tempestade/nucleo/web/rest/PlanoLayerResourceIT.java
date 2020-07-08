package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.PlanoLayer;
import org.tempestade.nucleo.repository.PlanoLayerRepository;
import org.tempestade.nucleo.service.PlanoLayerService;
import org.tempestade.nucleo.service.dto.PlanoLayerDTO;
import org.tempestade.nucleo.service.mapper.PlanoLayerMapper;

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
