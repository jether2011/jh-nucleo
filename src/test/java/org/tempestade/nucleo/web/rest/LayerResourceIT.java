package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Layer;
import org.tempestade.nucleo.repository.LayerRepository;
import org.tempestade.nucleo.service.LayerService;
import org.tempestade.nucleo.service.dto.LayerDTO;
import org.tempestade.nucleo.service.mapper.LayerMapper;

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

import org.tempestade.nucleo.domain.enumeration.LayerType;
/**
 * Integration tests for the {@link LayerResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LayerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MAP_HOST = "AAAAAAAAAA";
    private static final String UPDATED_MAP_HOST = "BBBBBBBBBB";

    private static final LayerType DEFAULT_LAYER_TYPE = LayerType.WMS;
    private static final LayerType UPDATED_LAYER_TYPE = LayerType.WMS;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTION = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTION = "BBBBBBBBBB";

    private static final String DEFAULT_WORKSPACE = "AAAAAAAAAA";
    private static final String UPDATED_WORKSPACE = "BBBBBBBBBB";

    private static final Integer DEFAULT_OPACITY = 1;
    private static final Integer UPDATED_OPACITY = 2;

    private static final Boolean DEFAULT_BASELAYER = false;
    private static final Boolean UPDATED_BASELAYER = true;

    private static final Boolean DEFAULT_TILED = false;
    private static final Boolean UPDATED_TILED = true;

    private static final Boolean DEFAULT_GWC_ACTIVED = false;
    private static final Boolean UPDATED_GWC_ACTIVED = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private LayerRepository layerRepository;

    @Autowired
    private LayerMapper layerMapper;

    @Autowired
    private LayerService layerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLayerMockMvc;

    private Layer layer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Layer createEntity(EntityManager em) {
        Layer layer = new Layer()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .mapHost(DEFAULT_MAP_HOST)
            .layerType(DEFAULT_LAYER_TYPE)
            .title(DEFAULT_TITLE)
            .attribution(DEFAULT_ATTRIBUTION)
            .workspace(DEFAULT_WORKSPACE)
            .opacity(DEFAULT_OPACITY)
            .baselayer(DEFAULT_BASELAYER)
            .tiled(DEFAULT_TILED)
            .gwcActived(DEFAULT_GWC_ACTIVED)
            .active(DEFAULT_ACTIVE)
            .enabled(DEFAULT_ENABLED)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return layer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Layer createUpdatedEntity(EntityManager em) {
        Layer layer = new Layer()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .mapHost(UPDATED_MAP_HOST)
            .layerType(UPDATED_LAYER_TYPE)
            .title(UPDATED_TITLE)
            .attribution(UPDATED_ATTRIBUTION)
            .workspace(UPDATED_WORKSPACE)
            .opacity(UPDATED_OPACITY)
            .baselayer(UPDATED_BASELAYER)
            .tiled(UPDATED_TILED)
            .gwcActived(UPDATED_GWC_ACTIVED)
            .active(UPDATED_ACTIVE)
            .enabled(UPDATED_ENABLED)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return layer;
    }

    @BeforeEach
    public void initTest() {
        layer = createEntity(em);
    }

    @Test
    @Transactional
    public void createLayer() throws Exception {
        int databaseSizeBeforeCreate = layerRepository.findAll().size();
        // Create the Layer
        LayerDTO layerDTO = layerMapper.toDto(layer);
        restLayerMockMvc.perform(post("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isCreated());

        // Validate the Layer in the database
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeCreate + 1);
        Layer testLayer = layerList.get(layerList.size() - 1);
        assertThat(testLayer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLayer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLayer.getMapHost()).isEqualTo(DEFAULT_MAP_HOST);
        assertThat(testLayer.getLayerType()).isEqualTo(DEFAULT_LAYER_TYPE);
        assertThat(testLayer.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testLayer.getAttribution()).isEqualTo(DEFAULT_ATTRIBUTION);
        assertThat(testLayer.getWorkspace()).isEqualTo(DEFAULT_WORKSPACE);
        assertThat(testLayer.getOpacity()).isEqualTo(DEFAULT_OPACITY);
        assertThat(testLayer.isBaselayer()).isEqualTo(DEFAULT_BASELAYER);
        assertThat(testLayer.isTiled()).isEqualTo(DEFAULT_TILED);
        assertThat(testLayer.isGwcActived()).isEqualTo(DEFAULT_GWC_ACTIVED);
        assertThat(testLayer.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testLayer.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testLayer.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testLayer.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createLayerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = layerRepository.findAll().size();

        // Create the Layer with an existing ID
        layer.setId(1L);
        LayerDTO layerDTO = layerMapper.toDto(layer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLayerMockMvc.perform(post("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Layer in the database
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = layerRepository.findAll().size();
        // set the field null
        layer.setName(null);

        // Create the Layer, which fails.
        LayerDTO layerDTO = layerMapper.toDto(layer);


        restLayerMockMvc.perform(post("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isBadRequest());

        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = layerRepository.findAll().size();
        // set the field null
        layer.setDescription(null);

        // Create the Layer, which fails.
        LayerDTO layerDTO = layerMapper.toDto(layer);


        restLayerMockMvc.perform(post("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isBadRequest());

        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMapHostIsRequired() throws Exception {
        int databaseSizeBeforeTest = layerRepository.findAll().size();
        // set the field null
        layer.setMapHost(null);

        // Create the Layer, which fails.
        LayerDTO layerDTO = layerMapper.toDto(layer);


        restLayerMockMvc.perform(post("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isBadRequest());

        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLayerTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = layerRepository.findAll().size();
        // set the field null
        layer.setLayerType(null);

        // Create the Layer, which fails.
        LayerDTO layerDTO = layerMapper.toDto(layer);


        restLayerMockMvc.perform(post("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isBadRequest());

        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = layerRepository.findAll().size();
        // set the field null
        layer.setTitle(null);

        // Create the Layer, which fails.
        LayerDTO layerDTO = layerMapper.toDto(layer);


        restLayerMockMvc.perform(post("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isBadRequest());

        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWorkspaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = layerRepository.findAll().size();
        // set the field null
        layer.setWorkspace(null);

        // Create the Layer, which fails.
        LayerDTO layerDTO = layerMapper.toDto(layer);


        restLayerMockMvc.perform(post("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isBadRequest());

        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = layerRepository.findAll().size();
        // set the field null
        layer.setOpacity(null);

        // Create the Layer, which fails.
        LayerDTO layerDTO = layerMapper.toDto(layer);


        restLayerMockMvc.perform(post("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isBadRequest());

        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBaselayerIsRequired() throws Exception {
        int databaseSizeBeforeTest = layerRepository.findAll().size();
        // set the field null
        layer.setBaselayer(null);

        // Create the Layer, which fails.
        LayerDTO layerDTO = layerMapper.toDto(layer);


        restLayerMockMvc.perform(post("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isBadRequest());

        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTiledIsRequired() throws Exception {
        int databaseSizeBeforeTest = layerRepository.findAll().size();
        // set the field null
        layer.setTiled(null);

        // Create the Layer, which fails.
        LayerDTO layerDTO = layerMapper.toDto(layer);


        restLayerMockMvc.perform(post("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isBadRequest());

        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGwcActivedIsRequired() throws Exception {
        int databaseSizeBeforeTest = layerRepository.findAll().size();
        // set the field null
        layer.setGwcActived(null);

        // Create the Layer, which fails.
        LayerDTO layerDTO = layerMapper.toDto(layer);


        restLayerMockMvc.perform(post("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isBadRequest());

        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = layerRepository.findAll().size();
        // set the field null
        layer.setActive(null);

        // Create the Layer, which fails.
        LayerDTO layerDTO = layerMapper.toDto(layer);


        restLayerMockMvc.perform(post("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isBadRequest());

        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = layerRepository.findAll().size();
        // set the field null
        layer.setEnabled(null);

        // Create the Layer, which fails.
        LayerDTO layerDTO = layerMapper.toDto(layer);


        restLayerMockMvc.perform(post("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isBadRequest());

        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = layerRepository.findAll().size();
        // set the field null
        layer.setCreated(null);

        // Create the Layer, which fails.
        LayerDTO layerDTO = layerMapper.toDto(layer);


        restLayerMockMvc.perform(post("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isBadRequest());

        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLayers() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList
        restLayerMockMvc.perform(get("/api/layers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(layer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].mapHost").value(hasItem(DEFAULT_MAP_HOST)))
            .andExpect(jsonPath("$.[*].layerType").value(hasItem(DEFAULT_LAYER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].attribution").value(hasItem(DEFAULT_ATTRIBUTION)))
            .andExpect(jsonPath("$.[*].workspace").value(hasItem(DEFAULT_WORKSPACE)))
            .andExpect(jsonPath("$.[*].opacity").value(hasItem(DEFAULT_OPACITY)))
            .andExpect(jsonPath("$.[*].baselayer").value(hasItem(DEFAULT_BASELAYER.booleanValue())))
            .andExpect(jsonPath("$.[*].tiled").value(hasItem(DEFAULT_TILED.booleanValue())))
            .andExpect(jsonPath("$.[*].gwcActived").value(hasItem(DEFAULT_GWC_ACTIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getLayer() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get the layer
        restLayerMockMvc.perform(get("/api/layers/{id}", layer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(layer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.mapHost").value(DEFAULT_MAP_HOST))
            .andExpect(jsonPath("$.layerType").value(DEFAULT_LAYER_TYPE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.attribution").value(DEFAULT_ATTRIBUTION))
            .andExpect(jsonPath("$.workspace").value(DEFAULT_WORKSPACE))
            .andExpect(jsonPath("$.opacity").value(DEFAULT_OPACITY))
            .andExpect(jsonPath("$.baselayer").value(DEFAULT_BASELAYER.booleanValue()))
            .andExpect(jsonPath("$.tiled").value(DEFAULT_TILED.booleanValue()))
            .andExpect(jsonPath("$.gwcActived").value(DEFAULT_GWC_ACTIVED.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingLayer() throws Exception {
        // Get the layer
        restLayerMockMvc.perform(get("/api/layers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLayer() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        int databaseSizeBeforeUpdate = layerRepository.findAll().size();

        // Update the layer
        Layer updatedLayer = layerRepository.findById(layer.getId()).get();
        // Disconnect from session so that the updates on updatedLayer are not directly saved in db
        em.detach(updatedLayer);
        updatedLayer
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .mapHost(UPDATED_MAP_HOST)
            .layerType(UPDATED_LAYER_TYPE)
            .title(UPDATED_TITLE)
            .attribution(UPDATED_ATTRIBUTION)
            .workspace(UPDATED_WORKSPACE)
            .opacity(UPDATED_OPACITY)
            .baselayer(UPDATED_BASELAYER)
            .tiled(UPDATED_TILED)
            .gwcActived(UPDATED_GWC_ACTIVED)
            .active(UPDATED_ACTIVE)
            .enabled(UPDATED_ENABLED)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        LayerDTO layerDTO = layerMapper.toDto(updatedLayer);

        restLayerMockMvc.perform(put("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isOk());

        // Validate the Layer in the database
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeUpdate);
        Layer testLayer = layerList.get(layerList.size() - 1);
        assertThat(testLayer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLayer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLayer.getMapHost()).isEqualTo(UPDATED_MAP_HOST);
        assertThat(testLayer.getLayerType()).isEqualTo(UPDATED_LAYER_TYPE);
        assertThat(testLayer.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLayer.getAttribution()).isEqualTo(UPDATED_ATTRIBUTION);
        assertThat(testLayer.getWorkspace()).isEqualTo(UPDATED_WORKSPACE);
        assertThat(testLayer.getOpacity()).isEqualTo(UPDATED_OPACITY);
        assertThat(testLayer.isBaselayer()).isEqualTo(UPDATED_BASELAYER);
        assertThat(testLayer.isTiled()).isEqualTo(UPDATED_TILED);
        assertThat(testLayer.isGwcActived()).isEqualTo(UPDATED_GWC_ACTIVED);
        assertThat(testLayer.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testLayer.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testLayer.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testLayer.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingLayer() throws Exception {
        int databaseSizeBeforeUpdate = layerRepository.findAll().size();

        // Create the Layer
        LayerDTO layerDTO = layerMapper.toDto(layer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLayerMockMvc.perform(put("/api/layers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(layerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Layer in the database
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLayer() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        int databaseSizeBeforeDelete = layerRepository.findAll().size();

        // Delete the layer
        restLayerMockMvc.perform(delete("/api/layers/{id}", layer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
