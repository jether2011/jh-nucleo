package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Layer;
import org.tempestade.nucleo.repository.LayerRepository;
import org.tempestade.nucleo.service.LayerService;
import org.tempestade.nucleo.service.dto.LayerDTO;
import org.tempestade.nucleo.service.mapper.LayerMapper;
import org.tempestade.nucleo.service.dto.LayerCriteria;
import org.tempestade.nucleo.service.LayerQueryService;

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
    private static final Integer SMALLER_OPACITY = 1 - 1;

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
    private LayerQueryService layerQueryService;

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
    public void getLayersByIdFiltering() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        Long id = layer.getId();

        defaultLayerShouldBeFound("id.equals=" + id);
        defaultLayerShouldNotBeFound("id.notEquals=" + id);

        defaultLayerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLayerShouldNotBeFound("id.greaterThan=" + id);

        defaultLayerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLayerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLayersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where name equals to DEFAULT_NAME
        defaultLayerShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the layerList where name equals to UPDATED_NAME
        defaultLayerShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLayersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where name not equals to DEFAULT_NAME
        defaultLayerShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the layerList where name not equals to UPDATED_NAME
        defaultLayerShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLayersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLayerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the layerList where name equals to UPDATED_NAME
        defaultLayerShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLayersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where name is not null
        defaultLayerShouldBeFound("name.specified=true");

        // Get all the layerList where name is null
        defaultLayerShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllLayersByNameContainsSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where name contains DEFAULT_NAME
        defaultLayerShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the layerList where name contains UPDATED_NAME
        defaultLayerShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLayersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where name does not contain DEFAULT_NAME
        defaultLayerShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the layerList where name does not contain UPDATED_NAME
        defaultLayerShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllLayersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where description equals to DEFAULT_DESCRIPTION
        defaultLayerShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the layerList where description equals to UPDATED_DESCRIPTION
        defaultLayerShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLayersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where description not equals to DEFAULT_DESCRIPTION
        defaultLayerShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the layerList where description not equals to UPDATED_DESCRIPTION
        defaultLayerShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLayersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultLayerShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the layerList where description equals to UPDATED_DESCRIPTION
        defaultLayerShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLayersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where description is not null
        defaultLayerShouldBeFound("description.specified=true");

        // Get all the layerList where description is null
        defaultLayerShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllLayersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where description contains DEFAULT_DESCRIPTION
        defaultLayerShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the layerList where description contains UPDATED_DESCRIPTION
        defaultLayerShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLayersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where description does not contain DEFAULT_DESCRIPTION
        defaultLayerShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the layerList where description does not contain UPDATED_DESCRIPTION
        defaultLayerShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllLayersByMapHostIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where mapHost equals to DEFAULT_MAP_HOST
        defaultLayerShouldBeFound("mapHost.equals=" + DEFAULT_MAP_HOST);

        // Get all the layerList where mapHost equals to UPDATED_MAP_HOST
        defaultLayerShouldNotBeFound("mapHost.equals=" + UPDATED_MAP_HOST);
    }

    @Test
    @Transactional
    public void getAllLayersByMapHostIsNotEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where mapHost not equals to DEFAULT_MAP_HOST
        defaultLayerShouldNotBeFound("mapHost.notEquals=" + DEFAULT_MAP_HOST);

        // Get all the layerList where mapHost not equals to UPDATED_MAP_HOST
        defaultLayerShouldBeFound("mapHost.notEquals=" + UPDATED_MAP_HOST);
    }

    @Test
    @Transactional
    public void getAllLayersByMapHostIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where mapHost in DEFAULT_MAP_HOST or UPDATED_MAP_HOST
        defaultLayerShouldBeFound("mapHost.in=" + DEFAULT_MAP_HOST + "," + UPDATED_MAP_HOST);

        // Get all the layerList where mapHost equals to UPDATED_MAP_HOST
        defaultLayerShouldNotBeFound("mapHost.in=" + UPDATED_MAP_HOST);
    }

    @Test
    @Transactional
    public void getAllLayersByMapHostIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where mapHost is not null
        defaultLayerShouldBeFound("mapHost.specified=true");

        // Get all the layerList where mapHost is null
        defaultLayerShouldNotBeFound("mapHost.specified=false");
    }
                @Test
    @Transactional
    public void getAllLayersByMapHostContainsSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where mapHost contains DEFAULT_MAP_HOST
        defaultLayerShouldBeFound("mapHost.contains=" + DEFAULT_MAP_HOST);

        // Get all the layerList where mapHost contains UPDATED_MAP_HOST
        defaultLayerShouldNotBeFound("mapHost.contains=" + UPDATED_MAP_HOST);
    }

    @Test
    @Transactional
    public void getAllLayersByMapHostNotContainsSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where mapHost does not contain DEFAULT_MAP_HOST
        defaultLayerShouldNotBeFound("mapHost.doesNotContain=" + DEFAULT_MAP_HOST);

        // Get all the layerList where mapHost does not contain UPDATED_MAP_HOST
        defaultLayerShouldBeFound("mapHost.doesNotContain=" + UPDATED_MAP_HOST);
    }


    @Test
    @Transactional
    public void getAllLayersByLayerTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where layerType equals to DEFAULT_LAYER_TYPE
        defaultLayerShouldBeFound("layerType.equals=" + DEFAULT_LAYER_TYPE);

        // Get all the layerList where layerType equals to UPDATED_LAYER_TYPE
        defaultLayerShouldNotBeFound("layerType.equals=" + UPDATED_LAYER_TYPE);
    }

    @Test
    @Transactional
    public void getAllLayersByLayerTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where layerType not equals to DEFAULT_LAYER_TYPE
        defaultLayerShouldNotBeFound("layerType.notEquals=" + DEFAULT_LAYER_TYPE);

        // Get all the layerList where layerType not equals to UPDATED_LAYER_TYPE
        defaultLayerShouldBeFound("layerType.notEquals=" + UPDATED_LAYER_TYPE);
    }

    @Test
    @Transactional
    public void getAllLayersByLayerTypeIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where layerType in DEFAULT_LAYER_TYPE or UPDATED_LAYER_TYPE
        defaultLayerShouldBeFound("layerType.in=" + DEFAULT_LAYER_TYPE + "," + UPDATED_LAYER_TYPE);

        // Get all the layerList where layerType equals to UPDATED_LAYER_TYPE
        defaultLayerShouldNotBeFound("layerType.in=" + UPDATED_LAYER_TYPE);
    }

    @Test
    @Transactional
    public void getAllLayersByLayerTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where layerType is not null
        defaultLayerShouldBeFound("layerType.specified=true");

        // Get all the layerList where layerType is null
        defaultLayerShouldNotBeFound("layerType.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where title equals to DEFAULT_TITLE
        defaultLayerShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the layerList where title equals to UPDATED_TITLE
        defaultLayerShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllLayersByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where title not equals to DEFAULT_TITLE
        defaultLayerShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the layerList where title not equals to UPDATED_TITLE
        defaultLayerShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllLayersByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultLayerShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the layerList where title equals to UPDATED_TITLE
        defaultLayerShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllLayersByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where title is not null
        defaultLayerShouldBeFound("title.specified=true");

        // Get all the layerList where title is null
        defaultLayerShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllLayersByTitleContainsSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where title contains DEFAULT_TITLE
        defaultLayerShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the layerList where title contains UPDATED_TITLE
        defaultLayerShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllLayersByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where title does not contain DEFAULT_TITLE
        defaultLayerShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the layerList where title does not contain UPDATED_TITLE
        defaultLayerShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllLayersByAttributionIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where attribution equals to DEFAULT_ATTRIBUTION
        defaultLayerShouldBeFound("attribution.equals=" + DEFAULT_ATTRIBUTION);

        // Get all the layerList where attribution equals to UPDATED_ATTRIBUTION
        defaultLayerShouldNotBeFound("attribution.equals=" + UPDATED_ATTRIBUTION);
    }

    @Test
    @Transactional
    public void getAllLayersByAttributionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where attribution not equals to DEFAULT_ATTRIBUTION
        defaultLayerShouldNotBeFound("attribution.notEquals=" + DEFAULT_ATTRIBUTION);

        // Get all the layerList where attribution not equals to UPDATED_ATTRIBUTION
        defaultLayerShouldBeFound("attribution.notEquals=" + UPDATED_ATTRIBUTION);
    }

    @Test
    @Transactional
    public void getAllLayersByAttributionIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where attribution in DEFAULT_ATTRIBUTION or UPDATED_ATTRIBUTION
        defaultLayerShouldBeFound("attribution.in=" + DEFAULT_ATTRIBUTION + "," + UPDATED_ATTRIBUTION);

        // Get all the layerList where attribution equals to UPDATED_ATTRIBUTION
        defaultLayerShouldNotBeFound("attribution.in=" + UPDATED_ATTRIBUTION);
    }

    @Test
    @Transactional
    public void getAllLayersByAttributionIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where attribution is not null
        defaultLayerShouldBeFound("attribution.specified=true");

        // Get all the layerList where attribution is null
        defaultLayerShouldNotBeFound("attribution.specified=false");
    }
                @Test
    @Transactional
    public void getAllLayersByAttributionContainsSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where attribution contains DEFAULT_ATTRIBUTION
        defaultLayerShouldBeFound("attribution.contains=" + DEFAULT_ATTRIBUTION);

        // Get all the layerList where attribution contains UPDATED_ATTRIBUTION
        defaultLayerShouldNotBeFound("attribution.contains=" + UPDATED_ATTRIBUTION);
    }

    @Test
    @Transactional
    public void getAllLayersByAttributionNotContainsSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where attribution does not contain DEFAULT_ATTRIBUTION
        defaultLayerShouldNotBeFound("attribution.doesNotContain=" + DEFAULT_ATTRIBUTION);

        // Get all the layerList where attribution does not contain UPDATED_ATTRIBUTION
        defaultLayerShouldBeFound("attribution.doesNotContain=" + UPDATED_ATTRIBUTION);
    }


    @Test
    @Transactional
    public void getAllLayersByWorkspaceIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where workspace equals to DEFAULT_WORKSPACE
        defaultLayerShouldBeFound("workspace.equals=" + DEFAULT_WORKSPACE);

        // Get all the layerList where workspace equals to UPDATED_WORKSPACE
        defaultLayerShouldNotBeFound("workspace.equals=" + UPDATED_WORKSPACE);
    }

    @Test
    @Transactional
    public void getAllLayersByWorkspaceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where workspace not equals to DEFAULT_WORKSPACE
        defaultLayerShouldNotBeFound("workspace.notEquals=" + DEFAULT_WORKSPACE);

        // Get all the layerList where workspace not equals to UPDATED_WORKSPACE
        defaultLayerShouldBeFound("workspace.notEquals=" + UPDATED_WORKSPACE);
    }

    @Test
    @Transactional
    public void getAllLayersByWorkspaceIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where workspace in DEFAULT_WORKSPACE or UPDATED_WORKSPACE
        defaultLayerShouldBeFound("workspace.in=" + DEFAULT_WORKSPACE + "," + UPDATED_WORKSPACE);

        // Get all the layerList where workspace equals to UPDATED_WORKSPACE
        defaultLayerShouldNotBeFound("workspace.in=" + UPDATED_WORKSPACE);
    }

    @Test
    @Transactional
    public void getAllLayersByWorkspaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where workspace is not null
        defaultLayerShouldBeFound("workspace.specified=true");

        // Get all the layerList where workspace is null
        defaultLayerShouldNotBeFound("workspace.specified=false");
    }
                @Test
    @Transactional
    public void getAllLayersByWorkspaceContainsSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where workspace contains DEFAULT_WORKSPACE
        defaultLayerShouldBeFound("workspace.contains=" + DEFAULT_WORKSPACE);

        // Get all the layerList where workspace contains UPDATED_WORKSPACE
        defaultLayerShouldNotBeFound("workspace.contains=" + UPDATED_WORKSPACE);
    }

    @Test
    @Transactional
    public void getAllLayersByWorkspaceNotContainsSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where workspace does not contain DEFAULT_WORKSPACE
        defaultLayerShouldNotBeFound("workspace.doesNotContain=" + DEFAULT_WORKSPACE);

        // Get all the layerList where workspace does not contain UPDATED_WORKSPACE
        defaultLayerShouldBeFound("workspace.doesNotContain=" + UPDATED_WORKSPACE);
    }


    @Test
    @Transactional
    public void getAllLayersByOpacityIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where opacity equals to DEFAULT_OPACITY
        defaultLayerShouldBeFound("opacity.equals=" + DEFAULT_OPACITY);

        // Get all the layerList where opacity equals to UPDATED_OPACITY
        defaultLayerShouldNotBeFound("opacity.equals=" + UPDATED_OPACITY);
    }

    @Test
    @Transactional
    public void getAllLayersByOpacityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where opacity not equals to DEFAULT_OPACITY
        defaultLayerShouldNotBeFound("opacity.notEquals=" + DEFAULT_OPACITY);

        // Get all the layerList where opacity not equals to UPDATED_OPACITY
        defaultLayerShouldBeFound("opacity.notEquals=" + UPDATED_OPACITY);
    }

    @Test
    @Transactional
    public void getAllLayersByOpacityIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where opacity in DEFAULT_OPACITY or UPDATED_OPACITY
        defaultLayerShouldBeFound("opacity.in=" + DEFAULT_OPACITY + "," + UPDATED_OPACITY);

        // Get all the layerList where opacity equals to UPDATED_OPACITY
        defaultLayerShouldNotBeFound("opacity.in=" + UPDATED_OPACITY);
    }

    @Test
    @Transactional
    public void getAllLayersByOpacityIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where opacity is not null
        defaultLayerShouldBeFound("opacity.specified=true");

        // Get all the layerList where opacity is null
        defaultLayerShouldNotBeFound("opacity.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByOpacityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where opacity is greater than or equal to DEFAULT_OPACITY
        defaultLayerShouldBeFound("opacity.greaterThanOrEqual=" + DEFAULT_OPACITY);

        // Get all the layerList where opacity is greater than or equal to UPDATED_OPACITY
        defaultLayerShouldNotBeFound("opacity.greaterThanOrEqual=" + UPDATED_OPACITY);
    }

    @Test
    @Transactional
    public void getAllLayersByOpacityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where opacity is less than or equal to DEFAULT_OPACITY
        defaultLayerShouldBeFound("opacity.lessThanOrEqual=" + DEFAULT_OPACITY);

        // Get all the layerList where opacity is less than or equal to SMALLER_OPACITY
        defaultLayerShouldNotBeFound("opacity.lessThanOrEqual=" + SMALLER_OPACITY);
    }

    @Test
    @Transactional
    public void getAllLayersByOpacityIsLessThanSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where opacity is less than DEFAULT_OPACITY
        defaultLayerShouldNotBeFound("opacity.lessThan=" + DEFAULT_OPACITY);

        // Get all the layerList where opacity is less than UPDATED_OPACITY
        defaultLayerShouldBeFound("opacity.lessThan=" + UPDATED_OPACITY);
    }

    @Test
    @Transactional
    public void getAllLayersByOpacityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where opacity is greater than DEFAULT_OPACITY
        defaultLayerShouldNotBeFound("opacity.greaterThan=" + DEFAULT_OPACITY);

        // Get all the layerList where opacity is greater than SMALLER_OPACITY
        defaultLayerShouldBeFound("opacity.greaterThan=" + SMALLER_OPACITY);
    }


    @Test
    @Transactional
    public void getAllLayersByBaselayerIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where baselayer equals to DEFAULT_BASELAYER
        defaultLayerShouldBeFound("baselayer.equals=" + DEFAULT_BASELAYER);

        // Get all the layerList where baselayer equals to UPDATED_BASELAYER
        defaultLayerShouldNotBeFound("baselayer.equals=" + UPDATED_BASELAYER);
    }

    @Test
    @Transactional
    public void getAllLayersByBaselayerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where baselayer not equals to DEFAULT_BASELAYER
        defaultLayerShouldNotBeFound("baselayer.notEquals=" + DEFAULT_BASELAYER);

        // Get all the layerList where baselayer not equals to UPDATED_BASELAYER
        defaultLayerShouldBeFound("baselayer.notEquals=" + UPDATED_BASELAYER);
    }

    @Test
    @Transactional
    public void getAllLayersByBaselayerIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where baselayer in DEFAULT_BASELAYER or UPDATED_BASELAYER
        defaultLayerShouldBeFound("baselayer.in=" + DEFAULT_BASELAYER + "," + UPDATED_BASELAYER);

        // Get all the layerList where baselayer equals to UPDATED_BASELAYER
        defaultLayerShouldNotBeFound("baselayer.in=" + UPDATED_BASELAYER);
    }

    @Test
    @Transactional
    public void getAllLayersByBaselayerIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where baselayer is not null
        defaultLayerShouldBeFound("baselayer.specified=true");

        // Get all the layerList where baselayer is null
        defaultLayerShouldNotBeFound("baselayer.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByTiledIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where tiled equals to DEFAULT_TILED
        defaultLayerShouldBeFound("tiled.equals=" + DEFAULT_TILED);

        // Get all the layerList where tiled equals to UPDATED_TILED
        defaultLayerShouldNotBeFound("tiled.equals=" + UPDATED_TILED);
    }

    @Test
    @Transactional
    public void getAllLayersByTiledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where tiled not equals to DEFAULT_TILED
        defaultLayerShouldNotBeFound("tiled.notEquals=" + DEFAULT_TILED);

        // Get all the layerList where tiled not equals to UPDATED_TILED
        defaultLayerShouldBeFound("tiled.notEquals=" + UPDATED_TILED);
    }

    @Test
    @Transactional
    public void getAllLayersByTiledIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where tiled in DEFAULT_TILED or UPDATED_TILED
        defaultLayerShouldBeFound("tiled.in=" + DEFAULT_TILED + "," + UPDATED_TILED);

        // Get all the layerList where tiled equals to UPDATED_TILED
        defaultLayerShouldNotBeFound("tiled.in=" + UPDATED_TILED);
    }

    @Test
    @Transactional
    public void getAllLayersByTiledIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where tiled is not null
        defaultLayerShouldBeFound("tiled.specified=true");

        // Get all the layerList where tiled is null
        defaultLayerShouldNotBeFound("tiled.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByGwcActivedIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where gwcActived equals to DEFAULT_GWC_ACTIVED
        defaultLayerShouldBeFound("gwcActived.equals=" + DEFAULT_GWC_ACTIVED);

        // Get all the layerList where gwcActived equals to UPDATED_GWC_ACTIVED
        defaultLayerShouldNotBeFound("gwcActived.equals=" + UPDATED_GWC_ACTIVED);
    }

    @Test
    @Transactional
    public void getAllLayersByGwcActivedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where gwcActived not equals to DEFAULT_GWC_ACTIVED
        defaultLayerShouldNotBeFound("gwcActived.notEquals=" + DEFAULT_GWC_ACTIVED);

        // Get all the layerList where gwcActived not equals to UPDATED_GWC_ACTIVED
        defaultLayerShouldBeFound("gwcActived.notEquals=" + UPDATED_GWC_ACTIVED);
    }

    @Test
    @Transactional
    public void getAllLayersByGwcActivedIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where gwcActived in DEFAULT_GWC_ACTIVED or UPDATED_GWC_ACTIVED
        defaultLayerShouldBeFound("gwcActived.in=" + DEFAULT_GWC_ACTIVED + "," + UPDATED_GWC_ACTIVED);

        // Get all the layerList where gwcActived equals to UPDATED_GWC_ACTIVED
        defaultLayerShouldNotBeFound("gwcActived.in=" + UPDATED_GWC_ACTIVED);
    }

    @Test
    @Transactional
    public void getAllLayersByGwcActivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where gwcActived is not null
        defaultLayerShouldBeFound("gwcActived.specified=true");

        // Get all the layerList where gwcActived is null
        defaultLayerShouldNotBeFound("gwcActived.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where active equals to DEFAULT_ACTIVE
        defaultLayerShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the layerList where active equals to UPDATED_ACTIVE
        defaultLayerShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllLayersByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where active not equals to DEFAULT_ACTIVE
        defaultLayerShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the layerList where active not equals to UPDATED_ACTIVE
        defaultLayerShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllLayersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultLayerShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the layerList where active equals to UPDATED_ACTIVE
        defaultLayerShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllLayersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where active is not null
        defaultLayerShouldBeFound("active.specified=true");

        // Get all the layerList where active is null
        defaultLayerShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where enabled equals to DEFAULT_ENABLED
        defaultLayerShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the layerList where enabled equals to UPDATED_ENABLED
        defaultLayerShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllLayersByEnabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where enabled not equals to DEFAULT_ENABLED
        defaultLayerShouldNotBeFound("enabled.notEquals=" + DEFAULT_ENABLED);

        // Get all the layerList where enabled not equals to UPDATED_ENABLED
        defaultLayerShouldBeFound("enabled.notEquals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllLayersByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultLayerShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the layerList where enabled equals to UPDATED_ENABLED
        defaultLayerShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllLayersByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where enabled is not null
        defaultLayerShouldBeFound("enabled.specified=true");

        // Get all the layerList where enabled is null
        defaultLayerShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where created equals to DEFAULT_CREATED
        defaultLayerShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the layerList where created equals to UPDATED_CREATED
        defaultLayerShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllLayersByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where created not equals to DEFAULT_CREATED
        defaultLayerShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the layerList where created not equals to UPDATED_CREATED
        defaultLayerShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllLayersByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultLayerShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the layerList where created equals to UPDATED_CREATED
        defaultLayerShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllLayersByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where created is not null
        defaultLayerShouldBeFound("created.specified=true");

        // Get all the layerList where created is null
        defaultLayerShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where updated equals to DEFAULT_UPDATED
        defaultLayerShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the layerList where updated equals to UPDATED_UPDATED
        defaultLayerShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllLayersByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where updated not equals to DEFAULT_UPDATED
        defaultLayerShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the layerList where updated not equals to UPDATED_UPDATED
        defaultLayerShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllLayersByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultLayerShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the layerList where updated equals to UPDATED_UPDATED
        defaultLayerShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllLayersByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where updated is not null
        defaultLayerShouldBeFound("updated.specified=true");

        // Get all the layerList where updated is null
        defaultLayerShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLayerShouldBeFound(String filter) throws Exception {
        restLayerMockMvc.perform(get("/api/layers?sort=id,desc&" + filter))
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

        // Check, that the count call also returns 1
        restLayerMockMvc.perform(get("/api/layers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLayerShouldNotBeFound(String filter) throws Exception {
        restLayerMockMvc.perform(get("/api/layers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLayerMockMvc.perform(get("/api/layers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
