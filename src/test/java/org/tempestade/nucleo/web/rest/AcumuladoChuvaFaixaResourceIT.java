package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.AcumuladoChuvaFaixa;
import org.tempestade.nucleo.repository.AcumuladoChuvaFaixaRepository;
import org.tempestade.nucleo.service.AcumuladoChuvaFaixaService;
import org.tempestade.nucleo.service.dto.AcumuladoChuvaFaixaDTO;
import org.tempestade.nucleo.service.mapper.AcumuladoChuvaFaixaMapper;
import org.tempestade.nucleo.service.dto.AcumuladoChuvaFaixaCriteria;
import org.tempestade.nucleo.service.AcumuladoChuvaFaixaQueryService;

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
 * Integration tests for the {@link AcumuladoChuvaFaixaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AcumuladoChuvaFaixaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_DE_MM = 1;
    private static final Integer UPDATED_DE_MM = 2;
    private static final Integer SMALLER_DE_MM = 1 - 1;

    private static final Integer DEFAULT_ATE_MM = 1;
    private static final Integer UPDATED_ATE_MM = 2;
    private static final Integer SMALLER_ATE_MM = 1 - 1;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AcumuladoChuvaFaixaRepository acumuladoChuvaFaixaRepository;

    @Autowired
    private AcumuladoChuvaFaixaMapper acumuladoChuvaFaixaMapper;

    @Autowired
    private AcumuladoChuvaFaixaService acumuladoChuvaFaixaService;

    @Autowired
    private AcumuladoChuvaFaixaQueryService acumuladoChuvaFaixaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcumuladoChuvaFaixaMockMvc;

    private AcumuladoChuvaFaixa acumuladoChuvaFaixa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcumuladoChuvaFaixa createEntity(EntityManager em) {
        AcumuladoChuvaFaixa acumuladoChuvaFaixa = new AcumuladoChuvaFaixa()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .deMm(DEFAULT_DE_MM)
            .ateMm(DEFAULT_ATE_MM)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return acumuladoChuvaFaixa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcumuladoChuvaFaixa createUpdatedEntity(EntityManager em) {
        AcumuladoChuvaFaixa acumuladoChuvaFaixa = new AcumuladoChuvaFaixa()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .deMm(UPDATED_DE_MM)
            .ateMm(UPDATED_ATE_MM)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return acumuladoChuvaFaixa;
    }

    @BeforeEach
    public void initTest() {
        acumuladoChuvaFaixa = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcumuladoChuvaFaixa() throws Exception {
        int databaseSizeBeforeCreate = acumuladoChuvaFaixaRepository.findAll().size();
        // Create the AcumuladoChuvaFaixa
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaMapper.toDto(acumuladoChuvaFaixa);
        restAcumuladoChuvaFaixaMockMvc.perform(post("/api/acumulado-chuva-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acumuladoChuvaFaixaDTO)))
            .andExpect(status().isCreated());

        // Validate the AcumuladoChuvaFaixa in the database
        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeCreate + 1);
        AcumuladoChuvaFaixa testAcumuladoChuvaFaixa = acumuladoChuvaFaixaList.get(acumuladoChuvaFaixaList.size() - 1);
        assertThat(testAcumuladoChuvaFaixa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAcumuladoChuvaFaixa.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAcumuladoChuvaFaixa.getDeMm()).isEqualTo(DEFAULT_DE_MM);
        assertThat(testAcumuladoChuvaFaixa.getAteMm()).isEqualTo(DEFAULT_ATE_MM);
        assertThat(testAcumuladoChuvaFaixa.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAcumuladoChuvaFaixa.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createAcumuladoChuvaFaixaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acumuladoChuvaFaixaRepository.findAll().size();

        // Create the AcumuladoChuvaFaixa with an existing ID
        acumuladoChuvaFaixa.setId(1L);
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaMapper.toDto(acumuladoChuvaFaixa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcumuladoChuvaFaixaMockMvc.perform(post("/api/acumulado-chuva-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acumuladoChuvaFaixaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcumuladoChuvaFaixa in the database
        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = acumuladoChuvaFaixaRepository.findAll().size();
        // set the field null
        acumuladoChuvaFaixa.setNome(null);

        // Create the AcumuladoChuvaFaixa, which fails.
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaMapper.toDto(acumuladoChuvaFaixa);


        restAcumuladoChuvaFaixaMockMvc.perform(post("/api/acumulado-chuva-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acumuladoChuvaFaixaDTO)))
            .andExpect(status().isBadRequest());

        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeMmIsRequired() throws Exception {
        int databaseSizeBeforeTest = acumuladoChuvaFaixaRepository.findAll().size();
        // set the field null
        acumuladoChuvaFaixa.setDeMm(null);

        // Create the AcumuladoChuvaFaixa, which fails.
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaMapper.toDto(acumuladoChuvaFaixa);


        restAcumuladoChuvaFaixaMockMvc.perform(post("/api/acumulado-chuva-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acumuladoChuvaFaixaDTO)))
            .andExpect(status().isBadRequest());

        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAteMmIsRequired() throws Exception {
        int databaseSizeBeforeTest = acumuladoChuvaFaixaRepository.findAll().size();
        // set the field null
        acumuladoChuvaFaixa.setAteMm(null);

        // Create the AcumuladoChuvaFaixa, which fails.
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaMapper.toDto(acumuladoChuvaFaixa);


        restAcumuladoChuvaFaixaMockMvc.perform(post("/api/acumulado-chuva-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acumuladoChuvaFaixaDTO)))
            .andExpect(status().isBadRequest());

        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = acumuladoChuvaFaixaRepository.findAll().size();
        // set the field null
        acumuladoChuvaFaixa.setCreated(null);

        // Create the AcumuladoChuvaFaixa, which fails.
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaMapper.toDto(acumuladoChuvaFaixa);


        restAcumuladoChuvaFaixaMockMvc.perform(post("/api/acumulado-chuva-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acumuladoChuvaFaixaDTO)))
            .andExpect(status().isBadRequest());

        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixas() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList
        restAcumuladoChuvaFaixaMockMvc.perform(get("/api/acumulado-chuva-faixas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acumuladoChuvaFaixa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].deMm").value(hasItem(DEFAULT_DE_MM)))
            .andExpect(jsonPath("$.[*].ateMm").value(hasItem(DEFAULT_ATE_MM)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getAcumuladoChuvaFaixa() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get the acumuladoChuvaFaixa
        restAcumuladoChuvaFaixaMockMvc.perform(get("/api/acumulado-chuva-faixas/{id}", acumuladoChuvaFaixa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(acumuladoChuvaFaixa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.deMm").value(DEFAULT_DE_MM))
            .andExpect(jsonPath("$.ateMm").value(DEFAULT_ATE_MM))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getAcumuladoChuvaFaixasByIdFiltering() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        Long id = acumuladoChuvaFaixa.getId();

        defaultAcumuladoChuvaFaixaShouldBeFound("id.equals=" + id);
        defaultAcumuladoChuvaFaixaShouldNotBeFound("id.notEquals=" + id);

        defaultAcumuladoChuvaFaixaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAcumuladoChuvaFaixaShouldNotBeFound("id.greaterThan=" + id);

        defaultAcumuladoChuvaFaixaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAcumuladoChuvaFaixaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where nome equals to DEFAULT_NOME
        defaultAcumuladoChuvaFaixaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the acumuladoChuvaFaixaList where nome equals to UPDATED_NOME
        defaultAcumuladoChuvaFaixaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where nome not equals to DEFAULT_NOME
        defaultAcumuladoChuvaFaixaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the acumuladoChuvaFaixaList where nome not equals to UPDATED_NOME
        defaultAcumuladoChuvaFaixaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAcumuladoChuvaFaixaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the acumuladoChuvaFaixaList where nome equals to UPDATED_NOME
        defaultAcumuladoChuvaFaixaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where nome is not null
        defaultAcumuladoChuvaFaixaShouldBeFound("nome.specified=true");

        // Get all the acumuladoChuvaFaixaList where nome is null
        defaultAcumuladoChuvaFaixaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByNomeContainsSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where nome contains DEFAULT_NOME
        defaultAcumuladoChuvaFaixaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the acumuladoChuvaFaixaList where nome contains UPDATED_NOME
        defaultAcumuladoChuvaFaixaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where nome does not contain DEFAULT_NOME
        defaultAcumuladoChuvaFaixaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the acumuladoChuvaFaixaList where nome does not contain UPDATED_NOME
        defaultAcumuladoChuvaFaixaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where descricao equals to DEFAULT_DESCRICAO
        defaultAcumuladoChuvaFaixaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the acumuladoChuvaFaixaList where descricao equals to UPDATED_DESCRICAO
        defaultAcumuladoChuvaFaixaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where descricao not equals to DEFAULT_DESCRICAO
        defaultAcumuladoChuvaFaixaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the acumuladoChuvaFaixaList where descricao not equals to UPDATED_DESCRICAO
        defaultAcumuladoChuvaFaixaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultAcumuladoChuvaFaixaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the acumuladoChuvaFaixaList where descricao equals to UPDATED_DESCRICAO
        defaultAcumuladoChuvaFaixaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where descricao is not null
        defaultAcumuladoChuvaFaixaShouldBeFound("descricao.specified=true");

        // Get all the acumuladoChuvaFaixaList where descricao is null
        defaultAcumuladoChuvaFaixaShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where descricao contains DEFAULT_DESCRICAO
        defaultAcumuladoChuvaFaixaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the acumuladoChuvaFaixaList where descricao contains UPDATED_DESCRICAO
        defaultAcumuladoChuvaFaixaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where descricao does not contain DEFAULT_DESCRICAO
        defaultAcumuladoChuvaFaixaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the acumuladoChuvaFaixaList where descricao does not contain UPDATED_DESCRICAO
        defaultAcumuladoChuvaFaixaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByDeMmIsEqualToSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where deMm equals to DEFAULT_DE_MM
        defaultAcumuladoChuvaFaixaShouldBeFound("deMm.equals=" + DEFAULT_DE_MM);

        // Get all the acumuladoChuvaFaixaList where deMm equals to UPDATED_DE_MM
        defaultAcumuladoChuvaFaixaShouldNotBeFound("deMm.equals=" + UPDATED_DE_MM);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByDeMmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where deMm not equals to DEFAULT_DE_MM
        defaultAcumuladoChuvaFaixaShouldNotBeFound("deMm.notEquals=" + DEFAULT_DE_MM);

        // Get all the acumuladoChuvaFaixaList where deMm not equals to UPDATED_DE_MM
        defaultAcumuladoChuvaFaixaShouldBeFound("deMm.notEquals=" + UPDATED_DE_MM);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByDeMmIsInShouldWork() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where deMm in DEFAULT_DE_MM or UPDATED_DE_MM
        defaultAcumuladoChuvaFaixaShouldBeFound("deMm.in=" + DEFAULT_DE_MM + "," + UPDATED_DE_MM);

        // Get all the acumuladoChuvaFaixaList where deMm equals to UPDATED_DE_MM
        defaultAcumuladoChuvaFaixaShouldNotBeFound("deMm.in=" + UPDATED_DE_MM);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByDeMmIsNullOrNotNull() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where deMm is not null
        defaultAcumuladoChuvaFaixaShouldBeFound("deMm.specified=true");

        // Get all the acumuladoChuvaFaixaList where deMm is null
        defaultAcumuladoChuvaFaixaShouldNotBeFound("deMm.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByDeMmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where deMm is greater than or equal to DEFAULT_DE_MM
        defaultAcumuladoChuvaFaixaShouldBeFound("deMm.greaterThanOrEqual=" + DEFAULT_DE_MM);

        // Get all the acumuladoChuvaFaixaList where deMm is greater than or equal to UPDATED_DE_MM
        defaultAcumuladoChuvaFaixaShouldNotBeFound("deMm.greaterThanOrEqual=" + UPDATED_DE_MM);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByDeMmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where deMm is less than or equal to DEFAULT_DE_MM
        defaultAcumuladoChuvaFaixaShouldBeFound("deMm.lessThanOrEqual=" + DEFAULT_DE_MM);

        // Get all the acumuladoChuvaFaixaList where deMm is less than or equal to SMALLER_DE_MM
        defaultAcumuladoChuvaFaixaShouldNotBeFound("deMm.lessThanOrEqual=" + SMALLER_DE_MM);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByDeMmIsLessThanSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where deMm is less than DEFAULT_DE_MM
        defaultAcumuladoChuvaFaixaShouldNotBeFound("deMm.lessThan=" + DEFAULT_DE_MM);

        // Get all the acumuladoChuvaFaixaList where deMm is less than UPDATED_DE_MM
        defaultAcumuladoChuvaFaixaShouldBeFound("deMm.lessThan=" + UPDATED_DE_MM);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByDeMmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where deMm is greater than DEFAULT_DE_MM
        defaultAcumuladoChuvaFaixaShouldNotBeFound("deMm.greaterThan=" + DEFAULT_DE_MM);

        // Get all the acumuladoChuvaFaixaList where deMm is greater than SMALLER_DE_MM
        defaultAcumuladoChuvaFaixaShouldBeFound("deMm.greaterThan=" + SMALLER_DE_MM);
    }


    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByAteMmIsEqualToSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where ateMm equals to DEFAULT_ATE_MM
        defaultAcumuladoChuvaFaixaShouldBeFound("ateMm.equals=" + DEFAULT_ATE_MM);

        // Get all the acumuladoChuvaFaixaList where ateMm equals to UPDATED_ATE_MM
        defaultAcumuladoChuvaFaixaShouldNotBeFound("ateMm.equals=" + UPDATED_ATE_MM);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByAteMmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where ateMm not equals to DEFAULT_ATE_MM
        defaultAcumuladoChuvaFaixaShouldNotBeFound("ateMm.notEquals=" + DEFAULT_ATE_MM);

        // Get all the acumuladoChuvaFaixaList where ateMm not equals to UPDATED_ATE_MM
        defaultAcumuladoChuvaFaixaShouldBeFound("ateMm.notEquals=" + UPDATED_ATE_MM);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByAteMmIsInShouldWork() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where ateMm in DEFAULT_ATE_MM or UPDATED_ATE_MM
        defaultAcumuladoChuvaFaixaShouldBeFound("ateMm.in=" + DEFAULT_ATE_MM + "," + UPDATED_ATE_MM);

        // Get all the acumuladoChuvaFaixaList where ateMm equals to UPDATED_ATE_MM
        defaultAcumuladoChuvaFaixaShouldNotBeFound("ateMm.in=" + UPDATED_ATE_MM);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByAteMmIsNullOrNotNull() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where ateMm is not null
        defaultAcumuladoChuvaFaixaShouldBeFound("ateMm.specified=true");

        // Get all the acumuladoChuvaFaixaList where ateMm is null
        defaultAcumuladoChuvaFaixaShouldNotBeFound("ateMm.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByAteMmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where ateMm is greater than or equal to DEFAULT_ATE_MM
        defaultAcumuladoChuvaFaixaShouldBeFound("ateMm.greaterThanOrEqual=" + DEFAULT_ATE_MM);

        // Get all the acumuladoChuvaFaixaList where ateMm is greater than or equal to UPDATED_ATE_MM
        defaultAcumuladoChuvaFaixaShouldNotBeFound("ateMm.greaterThanOrEqual=" + UPDATED_ATE_MM);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByAteMmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where ateMm is less than or equal to DEFAULT_ATE_MM
        defaultAcumuladoChuvaFaixaShouldBeFound("ateMm.lessThanOrEqual=" + DEFAULT_ATE_MM);

        // Get all the acumuladoChuvaFaixaList where ateMm is less than or equal to SMALLER_ATE_MM
        defaultAcumuladoChuvaFaixaShouldNotBeFound("ateMm.lessThanOrEqual=" + SMALLER_ATE_MM);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByAteMmIsLessThanSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where ateMm is less than DEFAULT_ATE_MM
        defaultAcumuladoChuvaFaixaShouldNotBeFound("ateMm.lessThan=" + DEFAULT_ATE_MM);

        // Get all the acumuladoChuvaFaixaList where ateMm is less than UPDATED_ATE_MM
        defaultAcumuladoChuvaFaixaShouldBeFound("ateMm.lessThan=" + UPDATED_ATE_MM);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByAteMmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where ateMm is greater than DEFAULT_ATE_MM
        defaultAcumuladoChuvaFaixaShouldNotBeFound("ateMm.greaterThan=" + DEFAULT_ATE_MM);

        // Get all the acumuladoChuvaFaixaList where ateMm is greater than SMALLER_ATE_MM
        defaultAcumuladoChuvaFaixaShouldBeFound("ateMm.greaterThan=" + SMALLER_ATE_MM);
    }


    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where created equals to DEFAULT_CREATED
        defaultAcumuladoChuvaFaixaShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the acumuladoChuvaFaixaList where created equals to UPDATED_CREATED
        defaultAcumuladoChuvaFaixaShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where created not equals to DEFAULT_CREATED
        defaultAcumuladoChuvaFaixaShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the acumuladoChuvaFaixaList where created not equals to UPDATED_CREATED
        defaultAcumuladoChuvaFaixaShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultAcumuladoChuvaFaixaShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the acumuladoChuvaFaixaList where created equals to UPDATED_CREATED
        defaultAcumuladoChuvaFaixaShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where created is not null
        defaultAcumuladoChuvaFaixaShouldBeFound("created.specified=true");

        // Get all the acumuladoChuvaFaixaList where created is null
        defaultAcumuladoChuvaFaixaShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where updated equals to DEFAULT_UPDATED
        defaultAcumuladoChuvaFaixaShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the acumuladoChuvaFaixaList where updated equals to UPDATED_UPDATED
        defaultAcumuladoChuvaFaixaShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where updated not equals to DEFAULT_UPDATED
        defaultAcumuladoChuvaFaixaShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the acumuladoChuvaFaixaList where updated not equals to UPDATED_UPDATED
        defaultAcumuladoChuvaFaixaShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultAcumuladoChuvaFaixaShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the acumuladoChuvaFaixaList where updated equals to UPDATED_UPDATED
        defaultAcumuladoChuvaFaixaShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixasByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList where updated is not null
        defaultAcumuladoChuvaFaixaShouldBeFound("updated.specified=true");

        // Get all the acumuladoChuvaFaixaList where updated is null
        defaultAcumuladoChuvaFaixaShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAcumuladoChuvaFaixaShouldBeFound(String filter) throws Exception {
        restAcumuladoChuvaFaixaMockMvc.perform(get("/api/acumulado-chuva-faixas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acumuladoChuvaFaixa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].deMm").value(hasItem(DEFAULT_DE_MM)))
            .andExpect(jsonPath("$.[*].ateMm").value(hasItem(DEFAULT_ATE_MM)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restAcumuladoChuvaFaixaMockMvc.perform(get("/api/acumulado-chuva-faixas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAcumuladoChuvaFaixaShouldNotBeFound(String filter) throws Exception {
        restAcumuladoChuvaFaixaMockMvc.perform(get("/api/acumulado-chuva-faixas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAcumuladoChuvaFaixaMockMvc.perform(get("/api/acumulado-chuva-faixas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAcumuladoChuvaFaixa() throws Exception {
        // Get the acumuladoChuvaFaixa
        restAcumuladoChuvaFaixaMockMvc.perform(get("/api/acumulado-chuva-faixas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcumuladoChuvaFaixa() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        int databaseSizeBeforeUpdate = acumuladoChuvaFaixaRepository.findAll().size();

        // Update the acumuladoChuvaFaixa
        AcumuladoChuvaFaixa updatedAcumuladoChuvaFaixa = acumuladoChuvaFaixaRepository.findById(acumuladoChuvaFaixa.getId()).get();
        // Disconnect from session so that the updates on updatedAcumuladoChuvaFaixa are not directly saved in db
        em.detach(updatedAcumuladoChuvaFaixa);
        updatedAcumuladoChuvaFaixa
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .deMm(UPDATED_DE_MM)
            .ateMm(UPDATED_ATE_MM)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaMapper.toDto(updatedAcumuladoChuvaFaixa);

        restAcumuladoChuvaFaixaMockMvc.perform(put("/api/acumulado-chuva-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acumuladoChuvaFaixaDTO)))
            .andExpect(status().isOk());

        // Validate the AcumuladoChuvaFaixa in the database
        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeUpdate);
        AcumuladoChuvaFaixa testAcumuladoChuvaFaixa = acumuladoChuvaFaixaList.get(acumuladoChuvaFaixaList.size() - 1);
        assertThat(testAcumuladoChuvaFaixa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAcumuladoChuvaFaixa.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAcumuladoChuvaFaixa.getDeMm()).isEqualTo(UPDATED_DE_MM);
        assertThat(testAcumuladoChuvaFaixa.getAteMm()).isEqualTo(UPDATED_ATE_MM);
        assertThat(testAcumuladoChuvaFaixa.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAcumuladoChuvaFaixa.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAcumuladoChuvaFaixa() throws Exception {
        int databaseSizeBeforeUpdate = acumuladoChuvaFaixaRepository.findAll().size();

        // Create the AcumuladoChuvaFaixa
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaMapper.toDto(acumuladoChuvaFaixa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcumuladoChuvaFaixaMockMvc.perform(put("/api/acumulado-chuva-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acumuladoChuvaFaixaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcumuladoChuvaFaixa in the database
        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAcumuladoChuvaFaixa() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        int databaseSizeBeforeDelete = acumuladoChuvaFaixaRepository.findAll().size();

        // Delete the acumuladoChuvaFaixa
        restAcumuladoChuvaFaixaMockMvc.perform(delete("/api/acumulado-chuva-faixas/{id}", acumuladoChuvaFaixa.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
