package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.TempestadeProbabilidade;
import org.tempestade.nucleo.repository.TempestadeProbabilidadeRepository;
import org.tempestade.nucleo.service.TempestadeProbabilidadeService;
import org.tempestade.nucleo.service.dto.TempestadeProbabilidadeDTO;
import org.tempestade.nucleo.service.mapper.TempestadeProbabilidadeMapper;
import org.tempestade.nucleo.service.dto.TempestadeProbabilidadeCriteria;
import org.tempestade.nucleo.service.TempestadeProbabilidadeQueryService;

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
 * Integration tests for the {@link TempestadeProbabilidadeResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TempestadeProbabilidadeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_FAIXA = "AAAAAAAAAA";
    private static final String UPDATED_FAIXA = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TempestadeProbabilidadeRepository tempestadeProbabilidadeRepository;

    @Autowired
    private TempestadeProbabilidadeMapper tempestadeProbabilidadeMapper;

    @Autowired
    private TempestadeProbabilidadeService tempestadeProbabilidadeService;

    @Autowired
    private TempestadeProbabilidadeQueryService tempestadeProbabilidadeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTempestadeProbabilidadeMockMvc;

    private TempestadeProbabilidade tempestadeProbabilidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TempestadeProbabilidade createEntity(EntityManager em) {
        TempestadeProbabilidade tempestadeProbabilidade = new TempestadeProbabilidade()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .faixa(DEFAULT_FAIXA)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return tempestadeProbabilidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TempestadeProbabilidade createUpdatedEntity(EntityManager em) {
        TempestadeProbabilidade tempestadeProbabilidade = new TempestadeProbabilidade()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .faixa(UPDATED_FAIXA)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return tempestadeProbabilidade;
    }

    @BeforeEach
    public void initTest() {
        tempestadeProbabilidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createTempestadeProbabilidade() throws Exception {
        int databaseSizeBeforeCreate = tempestadeProbabilidadeRepository.findAll().size();
        // Create the TempestadeProbabilidade
        TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO = tempestadeProbabilidadeMapper.toDto(tempestadeProbabilidade);
        restTempestadeProbabilidadeMockMvc.perform(post("/api/tempestade-probabilidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeProbabilidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the TempestadeProbabilidade in the database
        List<TempestadeProbabilidade> tempestadeProbabilidadeList = tempestadeProbabilidadeRepository.findAll();
        assertThat(tempestadeProbabilidadeList).hasSize(databaseSizeBeforeCreate + 1);
        TempestadeProbabilidade testTempestadeProbabilidade = tempestadeProbabilidadeList.get(tempestadeProbabilidadeList.size() - 1);
        assertThat(testTempestadeProbabilidade.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTempestadeProbabilidade.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTempestadeProbabilidade.getFaixa()).isEqualTo(DEFAULT_FAIXA);
        assertThat(testTempestadeProbabilidade.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTempestadeProbabilidade.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createTempestadeProbabilidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tempestadeProbabilidadeRepository.findAll().size();

        // Create the TempestadeProbabilidade with an existing ID
        tempestadeProbabilidade.setId(1L);
        TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO = tempestadeProbabilidadeMapper.toDto(tempestadeProbabilidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTempestadeProbabilidadeMockMvc.perform(post("/api/tempestade-probabilidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeProbabilidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TempestadeProbabilidade in the database
        List<TempestadeProbabilidade> tempestadeProbabilidadeList = tempestadeProbabilidadeRepository.findAll();
        assertThat(tempestadeProbabilidadeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempestadeProbabilidadeRepository.findAll().size();
        // set the field null
        tempestadeProbabilidade.setName(null);

        // Create the TempestadeProbabilidade, which fails.
        TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO = tempestadeProbabilidadeMapper.toDto(tempestadeProbabilidade);


        restTempestadeProbabilidadeMockMvc.perform(post("/api/tempestade-probabilidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeProbabilidadeDTO)))
            .andExpect(status().isBadRequest());

        List<TempestadeProbabilidade> tempestadeProbabilidadeList = tempestadeProbabilidadeRepository.findAll();
        assertThat(tempestadeProbabilidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempestadeProbabilidadeRepository.findAll().size();
        // set the field null
        tempestadeProbabilidade.setDescricao(null);

        // Create the TempestadeProbabilidade, which fails.
        TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO = tempestadeProbabilidadeMapper.toDto(tempestadeProbabilidade);


        restTempestadeProbabilidadeMockMvc.perform(post("/api/tempestade-probabilidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeProbabilidadeDTO)))
            .andExpect(status().isBadRequest());

        List<TempestadeProbabilidade> tempestadeProbabilidadeList = tempestadeProbabilidadeRepository.findAll();
        assertThat(tempestadeProbabilidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempestadeProbabilidadeRepository.findAll().size();
        // set the field null
        tempestadeProbabilidade.setCreated(null);

        // Create the TempestadeProbabilidade, which fails.
        TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO = tempestadeProbabilidadeMapper.toDto(tempestadeProbabilidade);


        restTempestadeProbabilidadeMockMvc.perform(post("/api/tempestade-probabilidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeProbabilidadeDTO)))
            .andExpect(status().isBadRequest());

        List<TempestadeProbabilidade> tempestadeProbabilidadeList = tempestadeProbabilidadeRepository.findAll();
        assertThat(tempestadeProbabilidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidades() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList
        restTempestadeProbabilidadeMockMvc.perform(get("/api/tempestade-probabilidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tempestadeProbabilidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].faixa").value(hasItem(DEFAULT_FAIXA)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getTempestadeProbabilidade() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get the tempestadeProbabilidade
        restTempestadeProbabilidadeMockMvc.perform(get("/api/tempestade-probabilidades/{id}", tempestadeProbabilidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tempestadeProbabilidade.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.faixa").value(DEFAULT_FAIXA))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getTempestadeProbabilidadesByIdFiltering() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        Long id = tempestadeProbabilidade.getId();

        defaultTempestadeProbabilidadeShouldBeFound("id.equals=" + id);
        defaultTempestadeProbabilidadeShouldNotBeFound("id.notEquals=" + id);

        defaultTempestadeProbabilidadeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTempestadeProbabilidadeShouldNotBeFound("id.greaterThan=" + id);

        defaultTempestadeProbabilidadeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTempestadeProbabilidadeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where name equals to DEFAULT_NAME
        defaultTempestadeProbabilidadeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the tempestadeProbabilidadeList where name equals to UPDATED_NAME
        defaultTempestadeProbabilidadeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where name not equals to DEFAULT_NAME
        defaultTempestadeProbabilidadeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the tempestadeProbabilidadeList where name not equals to UPDATED_NAME
        defaultTempestadeProbabilidadeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTempestadeProbabilidadeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the tempestadeProbabilidadeList where name equals to UPDATED_NAME
        defaultTempestadeProbabilidadeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where name is not null
        defaultTempestadeProbabilidadeShouldBeFound("name.specified=true");

        // Get all the tempestadeProbabilidadeList where name is null
        defaultTempestadeProbabilidadeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByNameContainsSomething() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where name contains DEFAULT_NAME
        defaultTempestadeProbabilidadeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the tempestadeProbabilidadeList where name contains UPDATED_NAME
        defaultTempestadeProbabilidadeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where name does not contain DEFAULT_NAME
        defaultTempestadeProbabilidadeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the tempestadeProbabilidadeList where name does not contain UPDATED_NAME
        defaultTempestadeProbabilidadeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where descricao equals to DEFAULT_DESCRICAO
        defaultTempestadeProbabilidadeShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the tempestadeProbabilidadeList where descricao equals to UPDATED_DESCRICAO
        defaultTempestadeProbabilidadeShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where descricao not equals to DEFAULT_DESCRICAO
        defaultTempestadeProbabilidadeShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the tempestadeProbabilidadeList where descricao not equals to UPDATED_DESCRICAO
        defaultTempestadeProbabilidadeShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultTempestadeProbabilidadeShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the tempestadeProbabilidadeList where descricao equals to UPDATED_DESCRICAO
        defaultTempestadeProbabilidadeShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where descricao is not null
        defaultTempestadeProbabilidadeShouldBeFound("descricao.specified=true");

        // Get all the tempestadeProbabilidadeList where descricao is null
        defaultTempestadeProbabilidadeShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where descricao contains DEFAULT_DESCRICAO
        defaultTempestadeProbabilidadeShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the tempestadeProbabilidadeList where descricao contains UPDATED_DESCRICAO
        defaultTempestadeProbabilidadeShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where descricao does not contain DEFAULT_DESCRICAO
        defaultTempestadeProbabilidadeShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the tempestadeProbabilidadeList where descricao does not contain UPDATED_DESCRICAO
        defaultTempestadeProbabilidadeShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByFaixaIsEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where faixa equals to DEFAULT_FAIXA
        defaultTempestadeProbabilidadeShouldBeFound("faixa.equals=" + DEFAULT_FAIXA);

        // Get all the tempestadeProbabilidadeList where faixa equals to UPDATED_FAIXA
        defaultTempestadeProbabilidadeShouldNotBeFound("faixa.equals=" + UPDATED_FAIXA);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByFaixaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where faixa not equals to DEFAULT_FAIXA
        defaultTempestadeProbabilidadeShouldNotBeFound("faixa.notEquals=" + DEFAULT_FAIXA);

        // Get all the tempestadeProbabilidadeList where faixa not equals to UPDATED_FAIXA
        defaultTempestadeProbabilidadeShouldBeFound("faixa.notEquals=" + UPDATED_FAIXA);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByFaixaIsInShouldWork() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where faixa in DEFAULT_FAIXA or UPDATED_FAIXA
        defaultTempestadeProbabilidadeShouldBeFound("faixa.in=" + DEFAULT_FAIXA + "," + UPDATED_FAIXA);

        // Get all the tempestadeProbabilidadeList where faixa equals to UPDATED_FAIXA
        defaultTempestadeProbabilidadeShouldNotBeFound("faixa.in=" + UPDATED_FAIXA);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByFaixaIsNullOrNotNull() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where faixa is not null
        defaultTempestadeProbabilidadeShouldBeFound("faixa.specified=true");

        // Get all the tempestadeProbabilidadeList where faixa is null
        defaultTempestadeProbabilidadeShouldNotBeFound("faixa.specified=false");
    }
                @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByFaixaContainsSomething() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where faixa contains DEFAULT_FAIXA
        defaultTempestadeProbabilidadeShouldBeFound("faixa.contains=" + DEFAULT_FAIXA);

        // Get all the tempestadeProbabilidadeList where faixa contains UPDATED_FAIXA
        defaultTempestadeProbabilidadeShouldNotBeFound("faixa.contains=" + UPDATED_FAIXA);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByFaixaNotContainsSomething() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where faixa does not contain DEFAULT_FAIXA
        defaultTempestadeProbabilidadeShouldNotBeFound("faixa.doesNotContain=" + DEFAULT_FAIXA);

        // Get all the tempestadeProbabilidadeList where faixa does not contain UPDATED_FAIXA
        defaultTempestadeProbabilidadeShouldBeFound("faixa.doesNotContain=" + UPDATED_FAIXA);
    }


    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where created equals to DEFAULT_CREATED
        defaultTempestadeProbabilidadeShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the tempestadeProbabilidadeList where created equals to UPDATED_CREATED
        defaultTempestadeProbabilidadeShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where created not equals to DEFAULT_CREATED
        defaultTempestadeProbabilidadeShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the tempestadeProbabilidadeList where created not equals to UPDATED_CREATED
        defaultTempestadeProbabilidadeShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultTempestadeProbabilidadeShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the tempestadeProbabilidadeList where created equals to UPDATED_CREATED
        defaultTempestadeProbabilidadeShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where created is not null
        defaultTempestadeProbabilidadeShouldBeFound("created.specified=true");

        // Get all the tempestadeProbabilidadeList where created is null
        defaultTempestadeProbabilidadeShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where updated equals to DEFAULT_UPDATED
        defaultTempestadeProbabilidadeShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the tempestadeProbabilidadeList where updated equals to UPDATED_UPDATED
        defaultTempestadeProbabilidadeShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where updated not equals to DEFAULT_UPDATED
        defaultTempestadeProbabilidadeShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the tempestadeProbabilidadeList where updated not equals to UPDATED_UPDATED
        defaultTempestadeProbabilidadeShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultTempestadeProbabilidadeShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the tempestadeProbabilidadeList where updated equals to UPDATED_UPDATED
        defaultTempestadeProbabilidadeShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidadesByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList where updated is not null
        defaultTempestadeProbabilidadeShouldBeFound("updated.specified=true");

        // Get all the tempestadeProbabilidadeList where updated is null
        defaultTempestadeProbabilidadeShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTempestadeProbabilidadeShouldBeFound(String filter) throws Exception {
        restTempestadeProbabilidadeMockMvc.perform(get("/api/tempestade-probabilidades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tempestadeProbabilidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].faixa").value(hasItem(DEFAULT_FAIXA)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restTempestadeProbabilidadeMockMvc.perform(get("/api/tempestade-probabilidades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTempestadeProbabilidadeShouldNotBeFound(String filter) throws Exception {
        restTempestadeProbabilidadeMockMvc.perform(get("/api/tempestade-probabilidades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTempestadeProbabilidadeMockMvc.perform(get("/api/tempestade-probabilidades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTempestadeProbabilidade() throws Exception {
        // Get the tempestadeProbabilidade
        restTempestadeProbabilidadeMockMvc.perform(get("/api/tempestade-probabilidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTempestadeProbabilidade() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        int databaseSizeBeforeUpdate = tempestadeProbabilidadeRepository.findAll().size();

        // Update the tempestadeProbabilidade
        TempestadeProbabilidade updatedTempestadeProbabilidade = tempestadeProbabilidadeRepository.findById(tempestadeProbabilidade.getId()).get();
        // Disconnect from session so that the updates on updatedTempestadeProbabilidade are not directly saved in db
        em.detach(updatedTempestadeProbabilidade);
        updatedTempestadeProbabilidade
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .faixa(UPDATED_FAIXA)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO = tempestadeProbabilidadeMapper.toDto(updatedTempestadeProbabilidade);

        restTempestadeProbabilidadeMockMvc.perform(put("/api/tempestade-probabilidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeProbabilidadeDTO)))
            .andExpect(status().isOk());

        // Validate the TempestadeProbabilidade in the database
        List<TempestadeProbabilidade> tempestadeProbabilidadeList = tempestadeProbabilidadeRepository.findAll();
        assertThat(tempestadeProbabilidadeList).hasSize(databaseSizeBeforeUpdate);
        TempestadeProbabilidade testTempestadeProbabilidade = tempestadeProbabilidadeList.get(tempestadeProbabilidadeList.size() - 1);
        assertThat(testTempestadeProbabilidade.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTempestadeProbabilidade.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTempestadeProbabilidade.getFaixa()).isEqualTo(UPDATED_FAIXA);
        assertThat(testTempestadeProbabilidade.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTempestadeProbabilidade.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingTempestadeProbabilidade() throws Exception {
        int databaseSizeBeforeUpdate = tempestadeProbabilidadeRepository.findAll().size();

        // Create the TempestadeProbabilidade
        TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO = tempestadeProbabilidadeMapper.toDto(tempestadeProbabilidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTempestadeProbabilidadeMockMvc.perform(put("/api/tempestade-probabilidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeProbabilidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TempestadeProbabilidade in the database
        List<TempestadeProbabilidade> tempestadeProbabilidadeList = tempestadeProbabilidadeRepository.findAll();
        assertThat(tempestadeProbabilidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTempestadeProbabilidade() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        int databaseSizeBeforeDelete = tempestadeProbabilidadeRepository.findAll().size();

        // Delete the tempestadeProbabilidade
        restTempestadeProbabilidadeMockMvc.perform(delete("/api/tempestade-probabilidades/{id}", tempestadeProbabilidade.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TempestadeProbabilidade> tempestadeProbabilidadeList = tempestadeProbabilidadeRepository.findAll();
        assertThat(tempestadeProbabilidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
