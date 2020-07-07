package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Descarga;
import org.tempestade.nucleo.domain.Rede;
import org.tempestade.nucleo.domain.DescargaTipo;
import org.tempestade.nucleo.domain.DescargaUnidade;
import org.tempestade.nucleo.domain.Alerta;
import org.tempestade.nucleo.repository.DescargaRepository;
import org.tempestade.nucleo.service.DescargaService;
import org.tempestade.nucleo.service.dto.DescargaDTO;
import org.tempestade.nucleo.service.mapper.DescargaMapper;
import org.tempestade.nucleo.service.dto.DescargaCriteria;
import org.tempestade.nucleo.service.DescargaQueryService;

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
 * Integration tests for the {@link DescargaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DescargaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_QTD = 1;
    private static final Integer UPDATED_QTD = 2;
    private static final Integer SMALLER_QTD = 1 - 1;

    private static final Instant DEFAULT_DATA_PRIMEIRA_DESCARGA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_PRIMEIRA_DESCARGA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TEMPO_ANTECIPACAO = "22:55:47";
    private static final String UPDATED_TEMPO_ANTECIPACAO = "21:37:10";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DescargaRepository descargaRepository;

    @Autowired
    private DescargaMapper descargaMapper;

    @Autowired
    private DescargaService descargaService;

    @Autowired
    private DescargaQueryService descargaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDescargaMockMvc;

    private Descarga descarga;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Descarga createEntity(EntityManager em) {
        Descarga descarga = new Descarga()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .qtd(DEFAULT_QTD)
            .dataPrimeiraDescarga(DEFAULT_DATA_PRIMEIRA_DESCARGA)
            .tempoAntecipacao(DEFAULT_TEMPO_ANTECIPACAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return descarga;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Descarga createUpdatedEntity(EntityManager em) {
        Descarga descarga = new Descarga()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .qtd(UPDATED_QTD)
            .dataPrimeiraDescarga(UPDATED_DATA_PRIMEIRA_DESCARGA)
            .tempoAntecipacao(UPDATED_TEMPO_ANTECIPACAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return descarga;
    }

    @BeforeEach
    public void initTest() {
        descarga = createEntity(em);
    }

    @Test
    @Transactional
    public void createDescarga() throws Exception {
        int databaseSizeBeforeCreate = descargaRepository.findAll().size();
        // Create the Descarga
        DescargaDTO descargaDTO = descargaMapper.toDto(descarga);
        restDescargaMockMvc.perform(post("/api/descargas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaDTO)))
            .andExpect(status().isCreated());

        // Validate the Descarga in the database
        List<Descarga> descargaList = descargaRepository.findAll();
        assertThat(descargaList).hasSize(databaseSizeBeforeCreate + 1);
        Descarga testDescarga = descargaList.get(descargaList.size() - 1);
        assertThat(testDescarga.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDescarga.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDescarga.getQtd()).isEqualTo(DEFAULT_QTD);
        assertThat(testDescarga.getDataPrimeiraDescarga()).isEqualTo(DEFAULT_DATA_PRIMEIRA_DESCARGA);
        assertThat(testDescarga.getTempoAntecipacao()).isEqualTo(DEFAULT_TEMPO_ANTECIPACAO);
        assertThat(testDescarga.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testDescarga.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createDescargaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = descargaRepository.findAll().size();

        // Create the Descarga with an existing ID
        descarga.setId(1L);
        DescargaDTO descargaDTO = descargaMapper.toDto(descarga);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDescargaMockMvc.perform(post("/api/descargas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Descarga in the database
        List<Descarga> descargaList = descargaRepository.findAll();
        assertThat(descargaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = descargaRepository.findAll().size();
        // set the field null
        descarga.setNome(null);

        // Create the Descarga, which fails.
        DescargaDTO descargaDTO = descargaMapper.toDto(descarga);


        restDescargaMockMvc.perform(post("/api/descargas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaDTO)))
            .andExpect(status().isBadRequest());

        List<Descarga> descargaList = descargaRepository.findAll();
        assertThat(descargaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataPrimeiraDescargaIsRequired() throws Exception {
        int databaseSizeBeforeTest = descargaRepository.findAll().size();
        // set the field null
        descarga.setDataPrimeiraDescarga(null);

        // Create the Descarga, which fails.
        DescargaDTO descargaDTO = descargaMapper.toDto(descarga);


        restDescargaMockMvc.perform(post("/api/descargas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaDTO)))
            .andExpect(status().isBadRequest());

        List<Descarga> descargaList = descargaRepository.findAll();
        assertThat(descargaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = descargaRepository.findAll().size();
        // set the field null
        descarga.setCreated(null);

        // Create the Descarga, which fails.
        DescargaDTO descargaDTO = descargaMapper.toDto(descarga);


        restDescargaMockMvc.perform(post("/api/descargas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaDTO)))
            .andExpect(status().isBadRequest());

        List<Descarga> descargaList = descargaRepository.findAll();
        assertThat(descargaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDescargas() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList
        restDescargaMockMvc.perform(get("/api/descargas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(descarga.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].qtd").value(hasItem(DEFAULT_QTD)))
            .andExpect(jsonPath("$.[*].dataPrimeiraDescarga").value(hasItem(DEFAULT_DATA_PRIMEIRA_DESCARGA.toString())))
            .andExpect(jsonPath("$.[*].tempoAntecipacao").value(hasItem(DEFAULT_TEMPO_ANTECIPACAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getDescarga() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get the descarga
        restDescargaMockMvc.perform(get("/api/descargas/{id}", descarga.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(descarga.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.qtd").value(DEFAULT_QTD))
            .andExpect(jsonPath("$.dataPrimeiraDescarga").value(DEFAULT_DATA_PRIMEIRA_DESCARGA.toString()))
            .andExpect(jsonPath("$.tempoAntecipacao").value(DEFAULT_TEMPO_ANTECIPACAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getDescargasByIdFiltering() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        Long id = descarga.getId();

        defaultDescargaShouldBeFound("id.equals=" + id);
        defaultDescargaShouldNotBeFound("id.notEquals=" + id);

        defaultDescargaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDescargaShouldNotBeFound("id.greaterThan=" + id);

        defaultDescargaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDescargaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDescargasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where nome equals to DEFAULT_NOME
        defaultDescargaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the descargaList where nome equals to UPDATED_NOME
        defaultDescargaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllDescargasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where nome not equals to DEFAULT_NOME
        defaultDescargaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the descargaList where nome not equals to UPDATED_NOME
        defaultDescargaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllDescargasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultDescargaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the descargaList where nome equals to UPDATED_NOME
        defaultDescargaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllDescargasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where nome is not null
        defaultDescargaShouldBeFound("nome.specified=true");

        // Get all the descargaList where nome is null
        defaultDescargaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllDescargasByNomeContainsSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where nome contains DEFAULT_NOME
        defaultDescargaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the descargaList where nome contains UPDATED_NOME
        defaultDescargaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllDescargasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where nome does not contain DEFAULT_NOME
        defaultDescargaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the descargaList where nome does not contain UPDATED_NOME
        defaultDescargaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllDescargasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where descricao equals to DEFAULT_DESCRICAO
        defaultDescargaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the descargaList where descricao equals to UPDATED_DESCRICAO
        defaultDescargaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllDescargasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where descricao not equals to DEFAULT_DESCRICAO
        defaultDescargaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the descargaList where descricao not equals to UPDATED_DESCRICAO
        defaultDescargaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllDescargasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultDescargaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the descargaList where descricao equals to UPDATED_DESCRICAO
        defaultDescargaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllDescargasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where descricao is not null
        defaultDescargaShouldBeFound("descricao.specified=true");

        // Get all the descargaList where descricao is null
        defaultDescargaShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllDescargasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where descricao contains DEFAULT_DESCRICAO
        defaultDescargaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the descargaList where descricao contains UPDATED_DESCRICAO
        defaultDescargaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllDescargasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where descricao does not contain DEFAULT_DESCRICAO
        defaultDescargaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the descargaList where descricao does not contain UPDATED_DESCRICAO
        defaultDescargaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllDescargasByQtdIsEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where qtd equals to DEFAULT_QTD
        defaultDescargaShouldBeFound("qtd.equals=" + DEFAULT_QTD);

        // Get all the descargaList where qtd equals to UPDATED_QTD
        defaultDescargaShouldNotBeFound("qtd.equals=" + UPDATED_QTD);
    }

    @Test
    @Transactional
    public void getAllDescargasByQtdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where qtd not equals to DEFAULT_QTD
        defaultDescargaShouldNotBeFound("qtd.notEquals=" + DEFAULT_QTD);

        // Get all the descargaList where qtd not equals to UPDATED_QTD
        defaultDescargaShouldBeFound("qtd.notEquals=" + UPDATED_QTD);
    }

    @Test
    @Transactional
    public void getAllDescargasByQtdIsInShouldWork() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where qtd in DEFAULT_QTD or UPDATED_QTD
        defaultDescargaShouldBeFound("qtd.in=" + DEFAULT_QTD + "," + UPDATED_QTD);

        // Get all the descargaList where qtd equals to UPDATED_QTD
        defaultDescargaShouldNotBeFound("qtd.in=" + UPDATED_QTD);
    }

    @Test
    @Transactional
    public void getAllDescargasByQtdIsNullOrNotNull() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where qtd is not null
        defaultDescargaShouldBeFound("qtd.specified=true");

        // Get all the descargaList where qtd is null
        defaultDescargaShouldNotBeFound("qtd.specified=false");
    }

    @Test
    @Transactional
    public void getAllDescargasByQtdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where qtd is greater than or equal to DEFAULT_QTD
        defaultDescargaShouldBeFound("qtd.greaterThanOrEqual=" + DEFAULT_QTD);

        // Get all the descargaList where qtd is greater than or equal to UPDATED_QTD
        defaultDescargaShouldNotBeFound("qtd.greaterThanOrEqual=" + UPDATED_QTD);
    }

    @Test
    @Transactional
    public void getAllDescargasByQtdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where qtd is less than or equal to DEFAULT_QTD
        defaultDescargaShouldBeFound("qtd.lessThanOrEqual=" + DEFAULT_QTD);

        // Get all the descargaList where qtd is less than or equal to SMALLER_QTD
        defaultDescargaShouldNotBeFound("qtd.lessThanOrEqual=" + SMALLER_QTD);
    }

    @Test
    @Transactional
    public void getAllDescargasByQtdIsLessThanSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where qtd is less than DEFAULT_QTD
        defaultDescargaShouldNotBeFound("qtd.lessThan=" + DEFAULT_QTD);

        // Get all the descargaList where qtd is less than UPDATED_QTD
        defaultDescargaShouldBeFound("qtd.lessThan=" + UPDATED_QTD);
    }

    @Test
    @Transactional
    public void getAllDescargasByQtdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where qtd is greater than DEFAULT_QTD
        defaultDescargaShouldNotBeFound("qtd.greaterThan=" + DEFAULT_QTD);

        // Get all the descargaList where qtd is greater than SMALLER_QTD
        defaultDescargaShouldBeFound("qtd.greaterThan=" + SMALLER_QTD);
    }


    @Test
    @Transactional
    public void getAllDescargasByDataPrimeiraDescargaIsEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where dataPrimeiraDescarga equals to DEFAULT_DATA_PRIMEIRA_DESCARGA
        defaultDescargaShouldBeFound("dataPrimeiraDescarga.equals=" + DEFAULT_DATA_PRIMEIRA_DESCARGA);

        // Get all the descargaList where dataPrimeiraDescarga equals to UPDATED_DATA_PRIMEIRA_DESCARGA
        defaultDescargaShouldNotBeFound("dataPrimeiraDescarga.equals=" + UPDATED_DATA_PRIMEIRA_DESCARGA);
    }

    @Test
    @Transactional
    public void getAllDescargasByDataPrimeiraDescargaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where dataPrimeiraDescarga not equals to DEFAULT_DATA_PRIMEIRA_DESCARGA
        defaultDescargaShouldNotBeFound("dataPrimeiraDescarga.notEquals=" + DEFAULT_DATA_PRIMEIRA_DESCARGA);

        // Get all the descargaList where dataPrimeiraDescarga not equals to UPDATED_DATA_PRIMEIRA_DESCARGA
        defaultDescargaShouldBeFound("dataPrimeiraDescarga.notEquals=" + UPDATED_DATA_PRIMEIRA_DESCARGA);
    }

    @Test
    @Transactional
    public void getAllDescargasByDataPrimeiraDescargaIsInShouldWork() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where dataPrimeiraDescarga in DEFAULT_DATA_PRIMEIRA_DESCARGA or UPDATED_DATA_PRIMEIRA_DESCARGA
        defaultDescargaShouldBeFound("dataPrimeiraDescarga.in=" + DEFAULT_DATA_PRIMEIRA_DESCARGA + "," + UPDATED_DATA_PRIMEIRA_DESCARGA);

        // Get all the descargaList where dataPrimeiraDescarga equals to UPDATED_DATA_PRIMEIRA_DESCARGA
        defaultDescargaShouldNotBeFound("dataPrimeiraDescarga.in=" + UPDATED_DATA_PRIMEIRA_DESCARGA);
    }

    @Test
    @Transactional
    public void getAllDescargasByDataPrimeiraDescargaIsNullOrNotNull() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where dataPrimeiraDescarga is not null
        defaultDescargaShouldBeFound("dataPrimeiraDescarga.specified=true");

        // Get all the descargaList where dataPrimeiraDescarga is null
        defaultDescargaShouldNotBeFound("dataPrimeiraDescarga.specified=false");
    }

    @Test
    @Transactional
    public void getAllDescargasByTempoAntecipacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where tempoAntecipacao equals to DEFAULT_TEMPO_ANTECIPACAO
        defaultDescargaShouldBeFound("tempoAntecipacao.equals=" + DEFAULT_TEMPO_ANTECIPACAO);

        // Get all the descargaList where tempoAntecipacao equals to UPDATED_TEMPO_ANTECIPACAO
        defaultDescargaShouldNotBeFound("tempoAntecipacao.equals=" + UPDATED_TEMPO_ANTECIPACAO);
    }

    @Test
    @Transactional
    public void getAllDescargasByTempoAntecipacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where tempoAntecipacao not equals to DEFAULT_TEMPO_ANTECIPACAO
        defaultDescargaShouldNotBeFound("tempoAntecipacao.notEquals=" + DEFAULT_TEMPO_ANTECIPACAO);

        // Get all the descargaList where tempoAntecipacao not equals to UPDATED_TEMPO_ANTECIPACAO
        defaultDescargaShouldBeFound("tempoAntecipacao.notEquals=" + UPDATED_TEMPO_ANTECIPACAO);
    }

    @Test
    @Transactional
    public void getAllDescargasByTempoAntecipacaoIsInShouldWork() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where tempoAntecipacao in DEFAULT_TEMPO_ANTECIPACAO or UPDATED_TEMPO_ANTECIPACAO
        defaultDescargaShouldBeFound("tempoAntecipacao.in=" + DEFAULT_TEMPO_ANTECIPACAO + "," + UPDATED_TEMPO_ANTECIPACAO);

        // Get all the descargaList where tempoAntecipacao equals to UPDATED_TEMPO_ANTECIPACAO
        defaultDescargaShouldNotBeFound("tempoAntecipacao.in=" + UPDATED_TEMPO_ANTECIPACAO);
    }

    @Test
    @Transactional
    public void getAllDescargasByTempoAntecipacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where tempoAntecipacao is not null
        defaultDescargaShouldBeFound("tempoAntecipacao.specified=true");

        // Get all the descargaList where tempoAntecipacao is null
        defaultDescargaShouldNotBeFound("tempoAntecipacao.specified=false");
    }
                @Test
    @Transactional
    public void getAllDescargasByTempoAntecipacaoContainsSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where tempoAntecipacao contains DEFAULT_TEMPO_ANTECIPACAO
        defaultDescargaShouldBeFound("tempoAntecipacao.contains=" + DEFAULT_TEMPO_ANTECIPACAO);

        // Get all the descargaList where tempoAntecipacao contains UPDATED_TEMPO_ANTECIPACAO
        defaultDescargaShouldNotBeFound("tempoAntecipacao.contains=" + UPDATED_TEMPO_ANTECIPACAO);
    }

    @Test
    @Transactional
    public void getAllDescargasByTempoAntecipacaoNotContainsSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where tempoAntecipacao does not contain DEFAULT_TEMPO_ANTECIPACAO
        defaultDescargaShouldNotBeFound("tempoAntecipacao.doesNotContain=" + DEFAULT_TEMPO_ANTECIPACAO);

        // Get all the descargaList where tempoAntecipacao does not contain UPDATED_TEMPO_ANTECIPACAO
        defaultDescargaShouldBeFound("tempoAntecipacao.doesNotContain=" + UPDATED_TEMPO_ANTECIPACAO);
    }


    @Test
    @Transactional
    public void getAllDescargasByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where created equals to DEFAULT_CREATED
        defaultDescargaShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the descargaList where created equals to UPDATED_CREATED
        defaultDescargaShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllDescargasByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where created not equals to DEFAULT_CREATED
        defaultDescargaShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the descargaList where created not equals to UPDATED_CREATED
        defaultDescargaShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllDescargasByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultDescargaShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the descargaList where created equals to UPDATED_CREATED
        defaultDescargaShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllDescargasByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where created is not null
        defaultDescargaShouldBeFound("created.specified=true");

        // Get all the descargaList where created is null
        defaultDescargaShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllDescargasByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where updated equals to DEFAULT_UPDATED
        defaultDescargaShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the descargaList where updated equals to UPDATED_UPDATED
        defaultDescargaShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllDescargasByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where updated not equals to DEFAULT_UPDATED
        defaultDescargaShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the descargaList where updated not equals to UPDATED_UPDATED
        defaultDescargaShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllDescargasByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultDescargaShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the descargaList where updated equals to UPDATED_UPDATED
        defaultDescargaShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllDescargasByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        // Get all the descargaList where updated is not null
        defaultDescargaShouldBeFound("updated.specified=true");

        // Get all the descargaList where updated is null
        defaultDescargaShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllDescargasByRedeIsEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);
        Rede rede = RedeResourceIT.createEntity(em);
        em.persist(rede);
        em.flush();
        descarga.setRede(rede);
        descargaRepository.saveAndFlush(descarga);
        Long redeId = rede.getId();

        // Get all the descargaList where rede equals to redeId
        defaultDescargaShouldBeFound("redeId.equals=" + redeId);

        // Get all the descargaList where rede equals to redeId + 1
        defaultDescargaShouldNotBeFound("redeId.equals=" + (redeId + 1));
    }


    @Test
    @Transactional
    public void getAllDescargasByDescargaTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);
        DescargaTipo descargaTipo = DescargaTipoResourceIT.createEntity(em);
        em.persist(descargaTipo);
        em.flush();
        descarga.setDescargaTipo(descargaTipo);
        descargaRepository.saveAndFlush(descarga);
        Long descargaTipoId = descargaTipo.getId();

        // Get all the descargaList where descargaTipo equals to descargaTipoId
        defaultDescargaShouldBeFound("descargaTipoId.equals=" + descargaTipoId);

        // Get all the descargaList where descargaTipo equals to descargaTipoId + 1
        defaultDescargaShouldNotBeFound("descargaTipoId.equals=" + (descargaTipoId + 1));
    }


    @Test
    @Transactional
    public void getAllDescargasByDescargaUnidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);
        DescargaUnidade descargaUnidade = DescargaUnidadeResourceIT.createEntity(em);
        em.persist(descargaUnidade);
        em.flush();
        descarga.setDescargaUnidade(descargaUnidade);
        descargaRepository.saveAndFlush(descarga);
        Long descargaUnidadeId = descargaUnidade.getId();

        // Get all the descargaList where descargaUnidade equals to descargaUnidadeId
        defaultDescargaShouldBeFound("descargaUnidadeId.equals=" + descargaUnidadeId);

        // Get all the descargaList where descargaUnidade equals to descargaUnidadeId + 1
        defaultDescargaShouldNotBeFound("descargaUnidadeId.equals=" + (descargaUnidadeId + 1));
    }


    @Test
    @Transactional
    public void getAllDescargasByAlertaIsEqualToSomething() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);
        Alerta alerta = AlertaResourceIT.createEntity(em);
        em.persist(alerta);
        em.flush();
        descarga.setAlerta(alerta);
        descargaRepository.saveAndFlush(descarga);
        Long alertaId = alerta.getId();

        // Get all the descargaList where alerta equals to alertaId
        defaultDescargaShouldBeFound("alertaId.equals=" + alertaId);

        // Get all the descargaList where alerta equals to alertaId + 1
        defaultDescargaShouldNotBeFound("alertaId.equals=" + (alertaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDescargaShouldBeFound(String filter) throws Exception {
        restDescargaMockMvc.perform(get("/api/descargas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(descarga.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].qtd").value(hasItem(DEFAULT_QTD)))
            .andExpect(jsonPath("$.[*].dataPrimeiraDescarga").value(hasItem(DEFAULT_DATA_PRIMEIRA_DESCARGA.toString())))
            .andExpect(jsonPath("$.[*].tempoAntecipacao").value(hasItem(DEFAULT_TEMPO_ANTECIPACAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restDescargaMockMvc.perform(get("/api/descargas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDescargaShouldNotBeFound(String filter) throws Exception {
        restDescargaMockMvc.perform(get("/api/descargas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDescargaMockMvc.perform(get("/api/descargas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDescarga() throws Exception {
        // Get the descarga
        restDescargaMockMvc.perform(get("/api/descargas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDescarga() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        int databaseSizeBeforeUpdate = descargaRepository.findAll().size();

        // Update the descarga
        Descarga updatedDescarga = descargaRepository.findById(descarga.getId()).get();
        // Disconnect from session so that the updates on updatedDescarga are not directly saved in db
        em.detach(updatedDescarga);
        updatedDescarga
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .qtd(UPDATED_QTD)
            .dataPrimeiraDescarga(UPDATED_DATA_PRIMEIRA_DESCARGA)
            .tempoAntecipacao(UPDATED_TEMPO_ANTECIPACAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        DescargaDTO descargaDTO = descargaMapper.toDto(updatedDescarga);

        restDescargaMockMvc.perform(put("/api/descargas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaDTO)))
            .andExpect(status().isOk());

        // Validate the Descarga in the database
        List<Descarga> descargaList = descargaRepository.findAll();
        assertThat(descargaList).hasSize(databaseSizeBeforeUpdate);
        Descarga testDescarga = descargaList.get(descargaList.size() - 1);
        assertThat(testDescarga.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDescarga.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDescarga.getQtd()).isEqualTo(UPDATED_QTD);
        assertThat(testDescarga.getDataPrimeiraDescarga()).isEqualTo(UPDATED_DATA_PRIMEIRA_DESCARGA);
        assertThat(testDescarga.getTempoAntecipacao()).isEqualTo(UPDATED_TEMPO_ANTECIPACAO);
        assertThat(testDescarga.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDescarga.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingDescarga() throws Exception {
        int databaseSizeBeforeUpdate = descargaRepository.findAll().size();

        // Create the Descarga
        DescargaDTO descargaDTO = descargaMapper.toDto(descarga);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDescargaMockMvc.perform(put("/api/descargas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Descarga in the database
        List<Descarga> descargaList = descargaRepository.findAll();
        assertThat(descargaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDescarga() throws Exception {
        // Initialize the database
        descargaRepository.saveAndFlush(descarga);

        int databaseSizeBeforeDelete = descargaRepository.findAll().size();

        // Delete the descarga
        restDescargaMockMvc.perform(delete("/api/descargas/{id}", descarga.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Descarga> descargaList = descargaRepository.findAll();
        assertThat(descargaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
