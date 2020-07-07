package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.TempestadeNivel;
import org.tempestade.nucleo.domain.IntensidadeChuva;
import org.tempestade.nucleo.repository.TempestadeNivelRepository;
import org.tempestade.nucleo.service.TempestadeNivelService;
import org.tempestade.nucleo.service.dto.TempestadeNivelDTO;
import org.tempestade.nucleo.service.mapper.TempestadeNivelMapper;
import org.tempestade.nucleo.service.dto.TempestadeNivelCriteria;
import org.tempestade.nucleo.service.TempestadeNivelQueryService;

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
 * Integration tests for the {@link TempestadeNivelResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TempestadeNivelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_TAXA_DE_RAIOS = "AAAAAAAAAA";
    private static final String UPDATED_TAXA_DE_RAIOS = "BBBBBBBBBB";

    private static final String DEFAULT_VENTOS_VELOCIDADE = "AAAAAAAAAA";
    private static final String UPDATED_VENTOS_VELOCIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_GRANIZO = "AAAAAAAAAA";
    private static final String UPDATED_GRANIZO = "BBBBBBBBBB";

    private static final String DEFAULT_POTENCIAL_DE_DANOS = "AAAAAAAAAA";
    private static final String UPDATED_POTENCIAL_DE_DANOS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TempestadeNivelRepository tempestadeNivelRepository;

    @Autowired
    private TempestadeNivelMapper tempestadeNivelMapper;

    @Autowired
    private TempestadeNivelService tempestadeNivelService;

    @Autowired
    private TempestadeNivelQueryService tempestadeNivelQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTempestadeNivelMockMvc;

    private TempestadeNivel tempestadeNivel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TempestadeNivel createEntity(EntityManager em) {
        TempestadeNivel tempestadeNivel = new TempestadeNivel()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .taxaDeRaios(DEFAULT_TAXA_DE_RAIOS)
            .ventosVelocidade(DEFAULT_VENTOS_VELOCIDADE)
            .granizo(DEFAULT_GRANIZO)
            .potencialDeDanos(DEFAULT_POTENCIAL_DE_DANOS)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return tempestadeNivel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TempestadeNivel createUpdatedEntity(EntityManager em) {
        TempestadeNivel tempestadeNivel = new TempestadeNivel()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .taxaDeRaios(UPDATED_TAXA_DE_RAIOS)
            .ventosVelocidade(UPDATED_VENTOS_VELOCIDADE)
            .granizo(UPDATED_GRANIZO)
            .potencialDeDanos(UPDATED_POTENCIAL_DE_DANOS)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return tempestadeNivel;
    }

    @BeforeEach
    public void initTest() {
        tempestadeNivel = createEntity(em);
    }

    @Test
    @Transactional
    public void createTempestadeNivel() throws Exception {
        int databaseSizeBeforeCreate = tempestadeNivelRepository.findAll().size();
        // Create the TempestadeNivel
        TempestadeNivelDTO tempestadeNivelDTO = tempestadeNivelMapper.toDto(tempestadeNivel);
        restTempestadeNivelMockMvc.perform(post("/api/tempestade-nivels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeNivelDTO)))
            .andExpect(status().isCreated());

        // Validate the TempestadeNivel in the database
        List<TempestadeNivel> tempestadeNivelList = tempestadeNivelRepository.findAll();
        assertThat(tempestadeNivelList).hasSize(databaseSizeBeforeCreate + 1);
        TempestadeNivel testTempestadeNivel = tempestadeNivelList.get(tempestadeNivelList.size() - 1);
        assertThat(testTempestadeNivel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTempestadeNivel.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTempestadeNivel.getTaxaDeRaios()).isEqualTo(DEFAULT_TAXA_DE_RAIOS);
        assertThat(testTempestadeNivel.getVentosVelocidade()).isEqualTo(DEFAULT_VENTOS_VELOCIDADE);
        assertThat(testTempestadeNivel.getGranizo()).isEqualTo(DEFAULT_GRANIZO);
        assertThat(testTempestadeNivel.getPotencialDeDanos()).isEqualTo(DEFAULT_POTENCIAL_DE_DANOS);
        assertThat(testTempestadeNivel.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTempestadeNivel.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createTempestadeNivelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tempestadeNivelRepository.findAll().size();

        // Create the TempestadeNivel with an existing ID
        tempestadeNivel.setId(1L);
        TempestadeNivelDTO tempestadeNivelDTO = tempestadeNivelMapper.toDto(tempestadeNivel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTempestadeNivelMockMvc.perform(post("/api/tempestade-nivels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeNivelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TempestadeNivel in the database
        List<TempestadeNivel> tempestadeNivelList = tempestadeNivelRepository.findAll();
        assertThat(tempestadeNivelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempestadeNivelRepository.findAll().size();
        // set the field null
        tempestadeNivel.setName(null);

        // Create the TempestadeNivel, which fails.
        TempestadeNivelDTO tempestadeNivelDTO = tempestadeNivelMapper.toDto(tempestadeNivel);


        restTempestadeNivelMockMvc.perform(post("/api/tempestade-nivels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeNivelDTO)))
            .andExpect(status().isBadRequest());

        List<TempestadeNivel> tempestadeNivelList = tempestadeNivelRepository.findAll();
        assertThat(tempestadeNivelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempestadeNivelRepository.findAll().size();
        // set the field null
        tempestadeNivel.setDescricao(null);

        // Create the TempestadeNivel, which fails.
        TempestadeNivelDTO tempestadeNivelDTO = tempestadeNivelMapper.toDto(tempestadeNivel);


        restTempestadeNivelMockMvc.perform(post("/api/tempestade-nivels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeNivelDTO)))
            .andExpect(status().isBadRequest());

        List<TempestadeNivel> tempestadeNivelList = tempestadeNivelRepository.findAll();
        assertThat(tempestadeNivelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempestadeNivelRepository.findAll().size();
        // set the field null
        tempestadeNivel.setCreated(null);

        // Create the TempestadeNivel, which fails.
        TempestadeNivelDTO tempestadeNivelDTO = tempestadeNivelMapper.toDto(tempestadeNivel);


        restTempestadeNivelMockMvc.perform(post("/api/tempestade-nivels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeNivelDTO)))
            .andExpect(status().isBadRequest());

        List<TempestadeNivel> tempestadeNivelList = tempestadeNivelRepository.findAll();
        assertThat(tempestadeNivelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivels() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList
        restTempestadeNivelMockMvc.perform(get("/api/tempestade-nivels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tempestadeNivel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].taxaDeRaios").value(hasItem(DEFAULT_TAXA_DE_RAIOS)))
            .andExpect(jsonPath("$.[*].ventosVelocidade").value(hasItem(DEFAULT_VENTOS_VELOCIDADE)))
            .andExpect(jsonPath("$.[*].granizo").value(hasItem(DEFAULT_GRANIZO)))
            .andExpect(jsonPath("$.[*].potencialDeDanos").value(hasItem(DEFAULT_POTENCIAL_DE_DANOS)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getTempestadeNivel() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get the tempestadeNivel
        restTempestadeNivelMockMvc.perform(get("/api/tempestade-nivels/{id}", tempestadeNivel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tempestadeNivel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.taxaDeRaios").value(DEFAULT_TAXA_DE_RAIOS))
            .andExpect(jsonPath("$.ventosVelocidade").value(DEFAULT_VENTOS_VELOCIDADE))
            .andExpect(jsonPath("$.granizo").value(DEFAULT_GRANIZO))
            .andExpect(jsonPath("$.potencialDeDanos").value(DEFAULT_POTENCIAL_DE_DANOS))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getTempestadeNivelsByIdFiltering() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        Long id = tempestadeNivel.getId();

        defaultTempestadeNivelShouldBeFound("id.equals=" + id);
        defaultTempestadeNivelShouldNotBeFound("id.notEquals=" + id);

        defaultTempestadeNivelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTempestadeNivelShouldNotBeFound("id.greaterThan=" + id);

        defaultTempestadeNivelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTempestadeNivelShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTempestadeNivelsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where name equals to DEFAULT_NAME
        defaultTempestadeNivelShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the tempestadeNivelList where name equals to UPDATED_NAME
        defaultTempestadeNivelShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where name not equals to DEFAULT_NAME
        defaultTempestadeNivelShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the tempestadeNivelList where name not equals to UPDATED_NAME
        defaultTempestadeNivelShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTempestadeNivelShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the tempestadeNivelList where name equals to UPDATED_NAME
        defaultTempestadeNivelShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where name is not null
        defaultTempestadeNivelShouldBeFound("name.specified=true");

        // Get all the tempestadeNivelList where name is null
        defaultTempestadeNivelShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTempestadeNivelsByNameContainsSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where name contains DEFAULT_NAME
        defaultTempestadeNivelShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the tempestadeNivelList where name contains UPDATED_NAME
        defaultTempestadeNivelShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where name does not contain DEFAULT_NAME
        defaultTempestadeNivelShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the tempestadeNivelList where name does not contain UPDATED_NAME
        defaultTempestadeNivelShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTempestadeNivelsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where descricao equals to DEFAULT_DESCRICAO
        defaultTempestadeNivelShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the tempestadeNivelList where descricao equals to UPDATED_DESCRICAO
        defaultTempestadeNivelShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where descricao not equals to DEFAULT_DESCRICAO
        defaultTempestadeNivelShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the tempestadeNivelList where descricao not equals to UPDATED_DESCRICAO
        defaultTempestadeNivelShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultTempestadeNivelShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the tempestadeNivelList where descricao equals to UPDATED_DESCRICAO
        defaultTempestadeNivelShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where descricao is not null
        defaultTempestadeNivelShouldBeFound("descricao.specified=true");

        // Get all the tempestadeNivelList where descricao is null
        defaultTempestadeNivelShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllTempestadeNivelsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where descricao contains DEFAULT_DESCRICAO
        defaultTempestadeNivelShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the tempestadeNivelList where descricao contains UPDATED_DESCRICAO
        defaultTempestadeNivelShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where descricao does not contain DEFAULT_DESCRICAO
        defaultTempestadeNivelShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the tempestadeNivelList where descricao does not contain UPDATED_DESCRICAO
        defaultTempestadeNivelShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllTempestadeNivelsByTaxaDeRaiosIsEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where taxaDeRaios equals to DEFAULT_TAXA_DE_RAIOS
        defaultTempestadeNivelShouldBeFound("taxaDeRaios.equals=" + DEFAULT_TAXA_DE_RAIOS);

        // Get all the tempestadeNivelList where taxaDeRaios equals to UPDATED_TAXA_DE_RAIOS
        defaultTempestadeNivelShouldNotBeFound("taxaDeRaios.equals=" + UPDATED_TAXA_DE_RAIOS);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByTaxaDeRaiosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where taxaDeRaios not equals to DEFAULT_TAXA_DE_RAIOS
        defaultTempestadeNivelShouldNotBeFound("taxaDeRaios.notEquals=" + DEFAULT_TAXA_DE_RAIOS);

        // Get all the tempestadeNivelList where taxaDeRaios not equals to UPDATED_TAXA_DE_RAIOS
        defaultTempestadeNivelShouldBeFound("taxaDeRaios.notEquals=" + UPDATED_TAXA_DE_RAIOS);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByTaxaDeRaiosIsInShouldWork() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where taxaDeRaios in DEFAULT_TAXA_DE_RAIOS or UPDATED_TAXA_DE_RAIOS
        defaultTempestadeNivelShouldBeFound("taxaDeRaios.in=" + DEFAULT_TAXA_DE_RAIOS + "," + UPDATED_TAXA_DE_RAIOS);

        // Get all the tempestadeNivelList where taxaDeRaios equals to UPDATED_TAXA_DE_RAIOS
        defaultTempestadeNivelShouldNotBeFound("taxaDeRaios.in=" + UPDATED_TAXA_DE_RAIOS);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByTaxaDeRaiosIsNullOrNotNull() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where taxaDeRaios is not null
        defaultTempestadeNivelShouldBeFound("taxaDeRaios.specified=true");

        // Get all the tempestadeNivelList where taxaDeRaios is null
        defaultTempestadeNivelShouldNotBeFound("taxaDeRaios.specified=false");
    }
                @Test
    @Transactional
    public void getAllTempestadeNivelsByTaxaDeRaiosContainsSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where taxaDeRaios contains DEFAULT_TAXA_DE_RAIOS
        defaultTempestadeNivelShouldBeFound("taxaDeRaios.contains=" + DEFAULT_TAXA_DE_RAIOS);

        // Get all the tempestadeNivelList where taxaDeRaios contains UPDATED_TAXA_DE_RAIOS
        defaultTempestadeNivelShouldNotBeFound("taxaDeRaios.contains=" + UPDATED_TAXA_DE_RAIOS);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByTaxaDeRaiosNotContainsSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where taxaDeRaios does not contain DEFAULT_TAXA_DE_RAIOS
        defaultTempestadeNivelShouldNotBeFound("taxaDeRaios.doesNotContain=" + DEFAULT_TAXA_DE_RAIOS);

        // Get all the tempestadeNivelList where taxaDeRaios does not contain UPDATED_TAXA_DE_RAIOS
        defaultTempestadeNivelShouldBeFound("taxaDeRaios.doesNotContain=" + UPDATED_TAXA_DE_RAIOS);
    }


    @Test
    @Transactional
    public void getAllTempestadeNivelsByVentosVelocidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where ventosVelocidade equals to DEFAULT_VENTOS_VELOCIDADE
        defaultTempestadeNivelShouldBeFound("ventosVelocidade.equals=" + DEFAULT_VENTOS_VELOCIDADE);

        // Get all the tempestadeNivelList where ventosVelocidade equals to UPDATED_VENTOS_VELOCIDADE
        defaultTempestadeNivelShouldNotBeFound("ventosVelocidade.equals=" + UPDATED_VENTOS_VELOCIDADE);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByVentosVelocidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where ventosVelocidade not equals to DEFAULT_VENTOS_VELOCIDADE
        defaultTempestadeNivelShouldNotBeFound("ventosVelocidade.notEquals=" + DEFAULT_VENTOS_VELOCIDADE);

        // Get all the tempestadeNivelList where ventosVelocidade not equals to UPDATED_VENTOS_VELOCIDADE
        defaultTempestadeNivelShouldBeFound("ventosVelocidade.notEquals=" + UPDATED_VENTOS_VELOCIDADE);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByVentosVelocidadeIsInShouldWork() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where ventosVelocidade in DEFAULT_VENTOS_VELOCIDADE or UPDATED_VENTOS_VELOCIDADE
        defaultTempestadeNivelShouldBeFound("ventosVelocidade.in=" + DEFAULT_VENTOS_VELOCIDADE + "," + UPDATED_VENTOS_VELOCIDADE);

        // Get all the tempestadeNivelList where ventosVelocidade equals to UPDATED_VENTOS_VELOCIDADE
        defaultTempestadeNivelShouldNotBeFound("ventosVelocidade.in=" + UPDATED_VENTOS_VELOCIDADE);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByVentosVelocidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where ventosVelocidade is not null
        defaultTempestadeNivelShouldBeFound("ventosVelocidade.specified=true");

        // Get all the tempestadeNivelList where ventosVelocidade is null
        defaultTempestadeNivelShouldNotBeFound("ventosVelocidade.specified=false");
    }
                @Test
    @Transactional
    public void getAllTempestadeNivelsByVentosVelocidadeContainsSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where ventosVelocidade contains DEFAULT_VENTOS_VELOCIDADE
        defaultTempestadeNivelShouldBeFound("ventosVelocidade.contains=" + DEFAULT_VENTOS_VELOCIDADE);

        // Get all the tempestadeNivelList where ventosVelocidade contains UPDATED_VENTOS_VELOCIDADE
        defaultTempestadeNivelShouldNotBeFound("ventosVelocidade.contains=" + UPDATED_VENTOS_VELOCIDADE);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByVentosVelocidadeNotContainsSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where ventosVelocidade does not contain DEFAULT_VENTOS_VELOCIDADE
        defaultTempestadeNivelShouldNotBeFound("ventosVelocidade.doesNotContain=" + DEFAULT_VENTOS_VELOCIDADE);

        // Get all the tempestadeNivelList where ventosVelocidade does not contain UPDATED_VENTOS_VELOCIDADE
        defaultTempestadeNivelShouldBeFound("ventosVelocidade.doesNotContain=" + UPDATED_VENTOS_VELOCIDADE);
    }


    @Test
    @Transactional
    public void getAllTempestadeNivelsByGranizoIsEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where granizo equals to DEFAULT_GRANIZO
        defaultTempestadeNivelShouldBeFound("granizo.equals=" + DEFAULT_GRANIZO);

        // Get all the tempestadeNivelList where granizo equals to UPDATED_GRANIZO
        defaultTempestadeNivelShouldNotBeFound("granizo.equals=" + UPDATED_GRANIZO);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByGranizoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where granizo not equals to DEFAULT_GRANIZO
        defaultTempestadeNivelShouldNotBeFound("granizo.notEquals=" + DEFAULT_GRANIZO);

        // Get all the tempestadeNivelList where granizo not equals to UPDATED_GRANIZO
        defaultTempestadeNivelShouldBeFound("granizo.notEquals=" + UPDATED_GRANIZO);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByGranizoIsInShouldWork() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where granizo in DEFAULT_GRANIZO or UPDATED_GRANIZO
        defaultTempestadeNivelShouldBeFound("granizo.in=" + DEFAULT_GRANIZO + "," + UPDATED_GRANIZO);

        // Get all the tempestadeNivelList where granizo equals to UPDATED_GRANIZO
        defaultTempestadeNivelShouldNotBeFound("granizo.in=" + UPDATED_GRANIZO);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByGranizoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where granizo is not null
        defaultTempestadeNivelShouldBeFound("granizo.specified=true");

        // Get all the tempestadeNivelList where granizo is null
        defaultTempestadeNivelShouldNotBeFound("granizo.specified=false");
    }
                @Test
    @Transactional
    public void getAllTempestadeNivelsByGranizoContainsSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where granizo contains DEFAULT_GRANIZO
        defaultTempestadeNivelShouldBeFound("granizo.contains=" + DEFAULT_GRANIZO);

        // Get all the tempestadeNivelList where granizo contains UPDATED_GRANIZO
        defaultTempestadeNivelShouldNotBeFound("granizo.contains=" + UPDATED_GRANIZO);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByGranizoNotContainsSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where granizo does not contain DEFAULT_GRANIZO
        defaultTempestadeNivelShouldNotBeFound("granizo.doesNotContain=" + DEFAULT_GRANIZO);

        // Get all the tempestadeNivelList where granizo does not contain UPDATED_GRANIZO
        defaultTempestadeNivelShouldBeFound("granizo.doesNotContain=" + UPDATED_GRANIZO);
    }


    @Test
    @Transactional
    public void getAllTempestadeNivelsByPotencialDeDanosIsEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where potencialDeDanos equals to DEFAULT_POTENCIAL_DE_DANOS
        defaultTempestadeNivelShouldBeFound("potencialDeDanos.equals=" + DEFAULT_POTENCIAL_DE_DANOS);

        // Get all the tempestadeNivelList where potencialDeDanos equals to UPDATED_POTENCIAL_DE_DANOS
        defaultTempestadeNivelShouldNotBeFound("potencialDeDanos.equals=" + UPDATED_POTENCIAL_DE_DANOS);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByPotencialDeDanosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where potencialDeDanos not equals to DEFAULT_POTENCIAL_DE_DANOS
        defaultTempestadeNivelShouldNotBeFound("potencialDeDanos.notEquals=" + DEFAULT_POTENCIAL_DE_DANOS);

        // Get all the tempestadeNivelList where potencialDeDanos not equals to UPDATED_POTENCIAL_DE_DANOS
        defaultTempestadeNivelShouldBeFound("potencialDeDanos.notEquals=" + UPDATED_POTENCIAL_DE_DANOS);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByPotencialDeDanosIsInShouldWork() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where potencialDeDanos in DEFAULT_POTENCIAL_DE_DANOS or UPDATED_POTENCIAL_DE_DANOS
        defaultTempestadeNivelShouldBeFound("potencialDeDanos.in=" + DEFAULT_POTENCIAL_DE_DANOS + "," + UPDATED_POTENCIAL_DE_DANOS);

        // Get all the tempestadeNivelList where potencialDeDanos equals to UPDATED_POTENCIAL_DE_DANOS
        defaultTempestadeNivelShouldNotBeFound("potencialDeDanos.in=" + UPDATED_POTENCIAL_DE_DANOS);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByPotencialDeDanosIsNullOrNotNull() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where potencialDeDanos is not null
        defaultTempestadeNivelShouldBeFound("potencialDeDanos.specified=true");

        // Get all the tempestadeNivelList where potencialDeDanos is null
        defaultTempestadeNivelShouldNotBeFound("potencialDeDanos.specified=false");
    }
                @Test
    @Transactional
    public void getAllTempestadeNivelsByPotencialDeDanosContainsSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where potencialDeDanos contains DEFAULT_POTENCIAL_DE_DANOS
        defaultTempestadeNivelShouldBeFound("potencialDeDanos.contains=" + DEFAULT_POTENCIAL_DE_DANOS);

        // Get all the tempestadeNivelList where potencialDeDanos contains UPDATED_POTENCIAL_DE_DANOS
        defaultTempestadeNivelShouldNotBeFound("potencialDeDanos.contains=" + UPDATED_POTENCIAL_DE_DANOS);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByPotencialDeDanosNotContainsSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where potencialDeDanos does not contain DEFAULT_POTENCIAL_DE_DANOS
        defaultTempestadeNivelShouldNotBeFound("potencialDeDanos.doesNotContain=" + DEFAULT_POTENCIAL_DE_DANOS);

        // Get all the tempestadeNivelList where potencialDeDanos does not contain UPDATED_POTENCIAL_DE_DANOS
        defaultTempestadeNivelShouldBeFound("potencialDeDanos.doesNotContain=" + UPDATED_POTENCIAL_DE_DANOS);
    }


    @Test
    @Transactional
    public void getAllTempestadeNivelsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where created equals to DEFAULT_CREATED
        defaultTempestadeNivelShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the tempestadeNivelList where created equals to UPDATED_CREATED
        defaultTempestadeNivelShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where created not equals to DEFAULT_CREATED
        defaultTempestadeNivelShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the tempestadeNivelList where created not equals to UPDATED_CREATED
        defaultTempestadeNivelShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultTempestadeNivelShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the tempestadeNivelList where created equals to UPDATED_CREATED
        defaultTempestadeNivelShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where created is not null
        defaultTempestadeNivelShouldBeFound("created.specified=true");

        // Get all the tempestadeNivelList where created is null
        defaultTempestadeNivelShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where updated equals to DEFAULT_UPDATED
        defaultTempestadeNivelShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the tempestadeNivelList where updated equals to UPDATED_UPDATED
        defaultTempestadeNivelShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where updated not equals to DEFAULT_UPDATED
        defaultTempestadeNivelShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the tempestadeNivelList where updated not equals to UPDATED_UPDATED
        defaultTempestadeNivelShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultTempestadeNivelShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the tempestadeNivelList where updated equals to UPDATED_UPDATED
        defaultTempestadeNivelShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList where updated is not null
        defaultTempestadeNivelShouldBeFound("updated.specified=true");

        // Get all the tempestadeNivelList where updated is null
        defaultTempestadeNivelShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllTempestadeNivelsByIntensidadeChuvaIsEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);
        IntensidadeChuva intensidadeChuva = IntensidadeChuvaResourceIT.createEntity(em);
        em.persist(intensidadeChuva);
        em.flush();
        tempestadeNivel.setIntensidadeChuva(intensidadeChuva);
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);
        Long intensidadeChuvaId = intensidadeChuva.getId();

        // Get all the tempestadeNivelList where intensidadeChuva equals to intensidadeChuvaId
        defaultTempestadeNivelShouldBeFound("intensidadeChuvaId.equals=" + intensidadeChuvaId);

        // Get all the tempestadeNivelList where intensidadeChuva equals to intensidadeChuvaId + 1
        defaultTempestadeNivelShouldNotBeFound("intensidadeChuvaId.equals=" + (intensidadeChuvaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTempestadeNivelShouldBeFound(String filter) throws Exception {
        restTempestadeNivelMockMvc.perform(get("/api/tempestade-nivels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tempestadeNivel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].taxaDeRaios").value(hasItem(DEFAULT_TAXA_DE_RAIOS)))
            .andExpect(jsonPath("$.[*].ventosVelocidade").value(hasItem(DEFAULT_VENTOS_VELOCIDADE)))
            .andExpect(jsonPath("$.[*].granizo").value(hasItem(DEFAULT_GRANIZO)))
            .andExpect(jsonPath("$.[*].potencialDeDanos").value(hasItem(DEFAULT_POTENCIAL_DE_DANOS)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restTempestadeNivelMockMvc.perform(get("/api/tempestade-nivels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTempestadeNivelShouldNotBeFound(String filter) throws Exception {
        restTempestadeNivelMockMvc.perform(get("/api/tempestade-nivels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTempestadeNivelMockMvc.perform(get("/api/tempestade-nivels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTempestadeNivel() throws Exception {
        // Get the tempestadeNivel
        restTempestadeNivelMockMvc.perform(get("/api/tempestade-nivels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTempestadeNivel() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        int databaseSizeBeforeUpdate = tempestadeNivelRepository.findAll().size();

        // Update the tempestadeNivel
        TempestadeNivel updatedTempestadeNivel = tempestadeNivelRepository.findById(tempestadeNivel.getId()).get();
        // Disconnect from session so that the updates on updatedTempestadeNivel are not directly saved in db
        em.detach(updatedTempestadeNivel);
        updatedTempestadeNivel
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .taxaDeRaios(UPDATED_TAXA_DE_RAIOS)
            .ventosVelocidade(UPDATED_VENTOS_VELOCIDADE)
            .granizo(UPDATED_GRANIZO)
            .potencialDeDanos(UPDATED_POTENCIAL_DE_DANOS)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        TempestadeNivelDTO tempestadeNivelDTO = tempestadeNivelMapper.toDto(updatedTempestadeNivel);

        restTempestadeNivelMockMvc.perform(put("/api/tempestade-nivels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeNivelDTO)))
            .andExpect(status().isOk());

        // Validate the TempestadeNivel in the database
        List<TempestadeNivel> tempestadeNivelList = tempestadeNivelRepository.findAll();
        assertThat(tempestadeNivelList).hasSize(databaseSizeBeforeUpdate);
        TempestadeNivel testTempestadeNivel = tempestadeNivelList.get(tempestadeNivelList.size() - 1);
        assertThat(testTempestadeNivel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTempestadeNivel.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTempestadeNivel.getTaxaDeRaios()).isEqualTo(UPDATED_TAXA_DE_RAIOS);
        assertThat(testTempestadeNivel.getVentosVelocidade()).isEqualTo(UPDATED_VENTOS_VELOCIDADE);
        assertThat(testTempestadeNivel.getGranizo()).isEqualTo(UPDATED_GRANIZO);
        assertThat(testTempestadeNivel.getPotencialDeDanos()).isEqualTo(UPDATED_POTENCIAL_DE_DANOS);
        assertThat(testTempestadeNivel.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTempestadeNivel.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingTempestadeNivel() throws Exception {
        int databaseSizeBeforeUpdate = tempestadeNivelRepository.findAll().size();

        // Create the TempestadeNivel
        TempestadeNivelDTO tempestadeNivelDTO = tempestadeNivelMapper.toDto(tempestadeNivel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTempestadeNivelMockMvc.perform(put("/api/tempestade-nivels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeNivelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TempestadeNivel in the database
        List<TempestadeNivel> tempestadeNivelList = tempestadeNivelRepository.findAll();
        assertThat(tempestadeNivelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTempestadeNivel() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        int databaseSizeBeforeDelete = tempestadeNivelRepository.findAll().size();

        // Delete the tempestadeNivel
        restTempestadeNivelMockMvc.perform(delete("/api/tempestade-nivels/{id}", tempestadeNivel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TempestadeNivel> tempestadeNivelList = tempestadeNivelRepository.findAll();
        assertThat(tempestadeNivelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
