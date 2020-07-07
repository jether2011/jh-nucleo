package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.BoletimPrevisao;
import org.tempestade.nucleo.domain.Boletim;
import org.tempestade.nucleo.domain.BoletimPrevObs;
import org.tempestade.nucleo.domain.IntensidadeChuva;
import org.tempestade.nucleo.domain.UmidadeAr;
import org.tempestade.nucleo.domain.Alvo;
import org.tempestade.nucleo.domain.PontosCardeais;
import org.tempestade.nucleo.domain.VentoVmFaixa;
import org.tempestade.nucleo.domain.TempestadeProbabilidade;
import org.tempestade.nucleo.domain.TempestadeNivel;
import org.tempestade.nucleo.domain.AcumuladoChuvaFaixa;
import org.tempestade.nucleo.domain.CondicaoTempo;
import org.tempestade.nucleo.repository.BoletimPrevisaoRepository;
import org.tempestade.nucleo.service.BoletimPrevisaoService;
import org.tempestade.nucleo.service.dto.BoletimPrevisaoDTO;
import org.tempestade.nucleo.service.mapper.BoletimPrevisaoMapper;
import org.tempestade.nucleo.service.dto.BoletimPrevisaoCriteria;
import org.tempestade.nucleo.service.BoletimPrevisaoQueryService;

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
 * Integration tests for the {@link BoletimPrevisaoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BoletimPrevisaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_IMG_CONDICAO_TEMPO = 1;
    private static final Integer UPDATED_IMG_CONDICAO_TEMPO = 2;
    private static final Integer SMALLER_IMG_CONDICAO_TEMPO = 1 - 1;

    private static final String DEFAULT_CONDICAO_TEMPO = "AAAAAAAAAA";
    private static final String UPDATED_CONDICAO_TEMPO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_GRUPO_ORDEM = 1;
    private static final Integer UPDATED_GRUPO_ORDEM = 2;
    private static final Integer SMALLER_GRUPO_ORDEM = 1 - 1;

    private static final String DEFAULT_ONDAS = "AAAAAAAAAA";
    private static final String UPDATED_ONDAS = "BBBBBBBBBB";

    private static final Integer DEFAULT_TEMPERATURA_DE = 1;
    private static final Integer UPDATED_TEMPERATURA_DE = 2;
    private static final Integer SMALLER_TEMPERATURA_DE = 1 - 1;

    private static final Integer DEFAULT_TEMPERATURA_ATE = 1;
    private static final Integer UPDATED_TEMPERATURA_ATE = 2;
    private static final Integer SMALLER_TEMPERATURA_ATE = 1 - 1;

    private static final Integer DEFAULT_VENTOVELOCIDADEMEDIAKMH = 1;
    private static final Integer UPDATED_VENTOVELOCIDADEMEDIAKMH = 2;
    private static final Integer SMALLER_VENTOVELOCIDADEMEDIAKMH = 1 - 1;

    private static final String DEFAULT_VENTOS_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_VENTOS_OBSERVACAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VENTO_RAJADA = false;
    private static final Boolean UPDATED_VENTO_RAJADA = true;

    private static final String DEFAULT_TEMPESTADE_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_TEMPESTADE_OBSERVACAO = "BBBBBBBBBB";

    private static final String DEFAULT_CHUVA_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_CHUVA_OBSERVACAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BoletimPrevisaoRepository boletimPrevisaoRepository;

    @Autowired
    private BoletimPrevisaoMapper boletimPrevisaoMapper;

    @Autowired
    private BoletimPrevisaoService boletimPrevisaoService;

    @Autowired
    private BoletimPrevisaoQueryService boletimPrevisaoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoletimPrevisaoMockMvc;

    private BoletimPrevisao boletimPrevisao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoletimPrevisao createEntity(EntityManager em) {
        BoletimPrevisao boletimPrevisao = new BoletimPrevisao()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .local(DEFAULT_LOCAL)
            .imgCondicaoTempo(DEFAULT_IMG_CONDICAO_TEMPO)
            .condicaoTempo(DEFAULT_CONDICAO_TEMPO)
            .observacao(DEFAULT_OBSERVACAO)
            .grupoOrdem(DEFAULT_GRUPO_ORDEM)
            .ondas(DEFAULT_ONDAS)
            .temperaturaDe(DEFAULT_TEMPERATURA_DE)
            .temperaturaAte(DEFAULT_TEMPERATURA_ATE)
            .ventovelocidademediakmh(DEFAULT_VENTOVELOCIDADEMEDIAKMH)
            .ventosObservacao(DEFAULT_VENTOS_OBSERVACAO)
            .ventoRajada(DEFAULT_VENTO_RAJADA)
            .tempestadeObservacao(DEFAULT_TEMPESTADE_OBSERVACAO)
            .chuvaObservacao(DEFAULT_CHUVA_OBSERVACAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return boletimPrevisao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoletimPrevisao createUpdatedEntity(EntityManager em) {
        BoletimPrevisao boletimPrevisao = new BoletimPrevisao()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .local(UPDATED_LOCAL)
            .imgCondicaoTempo(UPDATED_IMG_CONDICAO_TEMPO)
            .condicaoTempo(UPDATED_CONDICAO_TEMPO)
            .observacao(UPDATED_OBSERVACAO)
            .grupoOrdem(UPDATED_GRUPO_ORDEM)
            .ondas(UPDATED_ONDAS)
            .temperaturaDe(UPDATED_TEMPERATURA_DE)
            .temperaturaAte(UPDATED_TEMPERATURA_ATE)
            .ventovelocidademediakmh(UPDATED_VENTOVELOCIDADEMEDIAKMH)
            .ventosObservacao(UPDATED_VENTOS_OBSERVACAO)
            .ventoRajada(UPDATED_VENTO_RAJADA)
            .tempestadeObservacao(UPDATED_TEMPESTADE_OBSERVACAO)
            .chuvaObservacao(UPDATED_CHUVA_OBSERVACAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return boletimPrevisao;
    }

    @BeforeEach
    public void initTest() {
        boletimPrevisao = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoletimPrevisao() throws Exception {
        int databaseSizeBeforeCreate = boletimPrevisaoRepository.findAll().size();
        // Create the BoletimPrevisao
        BoletimPrevisaoDTO boletimPrevisaoDTO = boletimPrevisaoMapper.toDto(boletimPrevisao);
        restBoletimPrevisaoMockMvc.perform(post("/api/boletim-previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevisaoDTO)))
            .andExpect(status().isCreated());

        // Validate the BoletimPrevisao in the database
        List<BoletimPrevisao> boletimPrevisaoList = boletimPrevisaoRepository.findAll();
        assertThat(boletimPrevisaoList).hasSize(databaseSizeBeforeCreate + 1);
        BoletimPrevisao testBoletimPrevisao = boletimPrevisaoList.get(boletimPrevisaoList.size() - 1);
        assertThat(testBoletimPrevisao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testBoletimPrevisao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testBoletimPrevisao.getLocal()).isEqualTo(DEFAULT_LOCAL);
        assertThat(testBoletimPrevisao.getImgCondicaoTempo()).isEqualTo(DEFAULT_IMG_CONDICAO_TEMPO);
        assertThat(testBoletimPrevisao.getCondicaoTempo()).isEqualTo(DEFAULT_CONDICAO_TEMPO);
        assertThat(testBoletimPrevisao.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testBoletimPrevisao.getGrupoOrdem()).isEqualTo(DEFAULT_GRUPO_ORDEM);
        assertThat(testBoletimPrevisao.getOndas()).isEqualTo(DEFAULT_ONDAS);
        assertThat(testBoletimPrevisao.getTemperaturaDe()).isEqualTo(DEFAULT_TEMPERATURA_DE);
        assertThat(testBoletimPrevisao.getTemperaturaAte()).isEqualTo(DEFAULT_TEMPERATURA_ATE);
        assertThat(testBoletimPrevisao.getVentovelocidademediakmh()).isEqualTo(DEFAULT_VENTOVELOCIDADEMEDIAKMH);
        assertThat(testBoletimPrevisao.getVentosObservacao()).isEqualTo(DEFAULT_VENTOS_OBSERVACAO);
        assertThat(testBoletimPrevisao.isVentoRajada()).isEqualTo(DEFAULT_VENTO_RAJADA);
        assertThat(testBoletimPrevisao.getTempestadeObservacao()).isEqualTo(DEFAULT_TEMPESTADE_OBSERVACAO);
        assertThat(testBoletimPrevisao.getChuvaObservacao()).isEqualTo(DEFAULT_CHUVA_OBSERVACAO);
        assertThat(testBoletimPrevisao.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testBoletimPrevisao.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createBoletimPrevisaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boletimPrevisaoRepository.findAll().size();

        // Create the BoletimPrevisao with an existing ID
        boletimPrevisao.setId(1L);
        BoletimPrevisaoDTO boletimPrevisaoDTO = boletimPrevisaoMapper.toDto(boletimPrevisao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoletimPrevisaoMockMvc.perform(post("/api/boletim-previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevisaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoletimPrevisao in the database
        List<BoletimPrevisao> boletimPrevisaoList = boletimPrevisaoRepository.findAll();
        assertThat(boletimPrevisaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimPrevisaoRepository.findAll().size();
        // set the field null
        boletimPrevisao.setNome(null);

        // Create the BoletimPrevisao, which fails.
        BoletimPrevisaoDTO boletimPrevisaoDTO = boletimPrevisaoMapper.toDto(boletimPrevisao);


        restBoletimPrevisaoMockMvc.perform(post("/api/boletim-previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevisaoDTO)))
            .andExpect(status().isBadRequest());

        List<BoletimPrevisao> boletimPrevisaoList = boletimPrevisaoRepository.findAll();
        assertThat(boletimPrevisaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimPrevisaoRepository.findAll().size();
        // set the field null
        boletimPrevisao.setCreated(null);

        // Create the BoletimPrevisao, which fails.
        BoletimPrevisaoDTO boletimPrevisaoDTO = boletimPrevisaoMapper.toDto(boletimPrevisao);


        restBoletimPrevisaoMockMvc.perform(post("/api/boletim-previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevisaoDTO)))
            .andExpect(status().isBadRequest());

        List<BoletimPrevisao> boletimPrevisaoList = boletimPrevisaoRepository.findAll();
        assertThat(boletimPrevisaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaos() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList
        restBoletimPrevisaoMockMvc.perform(get("/api/boletim-previsaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boletimPrevisao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].local").value(hasItem(DEFAULT_LOCAL)))
            .andExpect(jsonPath("$.[*].imgCondicaoTempo").value(hasItem(DEFAULT_IMG_CONDICAO_TEMPO)))
            .andExpect(jsonPath("$.[*].condicaoTempo").value(hasItem(DEFAULT_CONDICAO_TEMPO)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].grupoOrdem").value(hasItem(DEFAULT_GRUPO_ORDEM)))
            .andExpect(jsonPath("$.[*].ondas").value(hasItem(DEFAULT_ONDAS)))
            .andExpect(jsonPath("$.[*].temperaturaDe").value(hasItem(DEFAULT_TEMPERATURA_DE)))
            .andExpect(jsonPath("$.[*].temperaturaAte").value(hasItem(DEFAULT_TEMPERATURA_ATE)))
            .andExpect(jsonPath("$.[*].ventovelocidademediakmh").value(hasItem(DEFAULT_VENTOVELOCIDADEMEDIAKMH)))
            .andExpect(jsonPath("$.[*].ventosObservacao").value(hasItem(DEFAULT_VENTOS_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].ventoRajada").value(hasItem(DEFAULT_VENTO_RAJADA.booleanValue())))
            .andExpect(jsonPath("$.[*].tempestadeObservacao").value(hasItem(DEFAULT_TEMPESTADE_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].chuvaObservacao").value(hasItem(DEFAULT_CHUVA_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getBoletimPrevisao() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get the boletimPrevisao
        restBoletimPrevisaoMockMvc.perform(get("/api/boletim-previsaos/{id}", boletimPrevisao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(boletimPrevisao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.local").value(DEFAULT_LOCAL))
            .andExpect(jsonPath("$.imgCondicaoTempo").value(DEFAULT_IMG_CONDICAO_TEMPO))
            .andExpect(jsonPath("$.condicaoTempo").value(DEFAULT_CONDICAO_TEMPO))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO))
            .andExpect(jsonPath("$.grupoOrdem").value(DEFAULT_GRUPO_ORDEM))
            .andExpect(jsonPath("$.ondas").value(DEFAULT_ONDAS))
            .andExpect(jsonPath("$.temperaturaDe").value(DEFAULT_TEMPERATURA_DE))
            .andExpect(jsonPath("$.temperaturaAte").value(DEFAULT_TEMPERATURA_ATE))
            .andExpect(jsonPath("$.ventovelocidademediakmh").value(DEFAULT_VENTOVELOCIDADEMEDIAKMH))
            .andExpect(jsonPath("$.ventosObservacao").value(DEFAULT_VENTOS_OBSERVACAO))
            .andExpect(jsonPath("$.ventoRajada").value(DEFAULT_VENTO_RAJADA.booleanValue()))
            .andExpect(jsonPath("$.tempestadeObservacao").value(DEFAULT_TEMPESTADE_OBSERVACAO))
            .andExpect(jsonPath("$.chuvaObservacao").value(DEFAULT_CHUVA_OBSERVACAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getBoletimPrevisaosByIdFiltering() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        Long id = boletimPrevisao.getId();

        defaultBoletimPrevisaoShouldBeFound("id.equals=" + id);
        defaultBoletimPrevisaoShouldNotBeFound("id.notEquals=" + id);

        defaultBoletimPrevisaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBoletimPrevisaoShouldNotBeFound("id.greaterThan=" + id);

        defaultBoletimPrevisaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBoletimPrevisaoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where nome equals to DEFAULT_NOME
        defaultBoletimPrevisaoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the boletimPrevisaoList where nome equals to UPDATED_NOME
        defaultBoletimPrevisaoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where nome not equals to DEFAULT_NOME
        defaultBoletimPrevisaoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the boletimPrevisaoList where nome not equals to UPDATED_NOME
        defaultBoletimPrevisaoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultBoletimPrevisaoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the boletimPrevisaoList where nome equals to UPDATED_NOME
        defaultBoletimPrevisaoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where nome is not null
        defaultBoletimPrevisaoShouldBeFound("nome.specified=true");

        // Get all the boletimPrevisaoList where nome is null
        defaultBoletimPrevisaoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimPrevisaosByNomeContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where nome contains DEFAULT_NOME
        defaultBoletimPrevisaoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the boletimPrevisaoList where nome contains UPDATED_NOME
        defaultBoletimPrevisaoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where nome does not contain DEFAULT_NOME
        defaultBoletimPrevisaoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the boletimPrevisaoList where nome does not contain UPDATED_NOME
        defaultBoletimPrevisaoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where descricao equals to DEFAULT_DESCRICAO
        defaultBoletimPrevisaoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the boletimPrevisaoList where descricao equals to UPDATED_DESCRICAO
        defaultBoletimPrevisaoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where descricao not equals to DEFAULT_DESCRICAO
        defaultBoletimPrevisaoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the boletimPrevisaoList where descricao not equals to UPDATED_DESCRICAO
        defaultBoletimPrevisaoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultBoletimPrevisaoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the boletimPrevisaoList where descricao equals to UPDATED_DESCRICAO
        defaultBoletimPrevisaoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where descricao is not null
        defaultBoletimPrevisaoShouldBeFound("descricao.specified=true");

        // Get all the boletimPrevisaoList where descricao is null
        defaultBoletimPrevisaoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimPrevisaosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where descricao contains DEFAULT_DESCRICAO
        defaultBoletimPrevisaoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the boletimPrevisaoList where descricao contains UPDATED_DESCRICAO
        defaultBoletimPrevisaoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where descricao does not contain DEFAULT_DESCRICAO
        defaultBoletimPrevisaoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the boletimPrevisaoList where descricao does not contain UPDATED_DESCRICAO
        defaultBoletimPrevisaoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByLocalIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where local equals to DEFAULT_LOCAL
        defaultBoletimPrevisaoShouldBeFound("local.equals=" + DEFAULT_LOCAL);

        // Get all the boletimPrevisaoList where local equals to UPDATED_LOCAL
        defaultBoletimPrevisaoShouldNotBeFound("local.equals=" + UPDATED_LOCAL);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByLocalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where local not equals to DEFAULT_LOCAL
        defaultBoletimPrevisaoShouldNotBeFound("local.notEquals=" + DEFAULT_LOCAL);

        // Get all the boletimPrevisaoList where local not equals to UPDATED_LOCAL
        defaultBoletimPrevisaoShouldBeFound("local.notEquals=" + UPDATED_LOCAL);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByLocalIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where local in DEFAULT_LOCAL or UPDATED_LOCAL
        defaultBoletimPrevisaoShouldBeFound("local.in=" + DEFAULT_LOCAL + "," + UPDATED_LOCAL);

        // Get all the boletimPrevisaoList where local equals to UPDATED_LOCAL
        defaultBoletimPrevisaoShouldNotBeFound("local.in=" + UPDATED_LOCAL);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByLocalIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where local is not null
        defaultBoletimPrevisaoShouldBeFound("local.specified=true");

        // Get all the boletimPrevisaoList where local is null
        defaultBoletimPrevisaoShouldNotBeFound("local.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimPrevisaosByLocalContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where local contains DEFAULT_LOCAL
        defaultBoletimPrevisaoShouldBeFound("local.contains=" + DEFAULT_LOCAL);

        // Get all the boletimPrevisaoList where local contains UPDATED_LOCAL
        defaultBoletimPrevisaoShouldNotBeFound("local.contains=" + UPDATED_LOCAL);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByLocalNotContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where local does not contain DEFAULT_LOCAL
        defaultBoletimPrevisaoShouldNotBeFound("local.doesNotContain=" + DEFAULT_LOCAL);

        // Get all the boletimPrevisaoList where local does not contain UPDATED_LOCAL
        defaultBoletimPrevisaoShouldBeFound("local.doesNotContain=" + UPDATED_LOCAL);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByImgCondicaoTempoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where imgCondicaoTempo equals to DEFAULT_IMG_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldBeFound("imgCondicaoTempo.equals=" + DEFAULT_IMG_CONDICAO_TEMPO);

        // Get all the boletimPrevisaoList where imgCondicaoTempo equals to UPDATED_IMG_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldNotBeFound("imgCondicaoTempo.equals=" + UPDATED_IMG_CONDICAO_TEMPO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByImgCondicaoTempoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where imgCondicaoTempo not equals to DEFAULT_IMG_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldNotBeFound("imgCondicaoTempo.notEquals=" + DEFAULT_IMG_CONDICAO_TEMPO);

        // Get all the boletimPrevisaoList where imgCondicaoTempo not equals to UPDATED_IMG_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldBeFound("imgCondicaoTempo.notEquals=" + UPDATED_IMG_CONDICAO_TEMPO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByImgCondicaoTempoIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where imgCondicaoTempo in DEFAULT_IMG_CONDICAO_TEMPO or UPDATED_IMG_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldBeFound("imgCondicaoTempo.in=" + DEFAULT_IMG_CONDICAO_TEMPO + "," + UPDATED_IMG_CONDICAO_TEMPO);

        // Get all the boletimPrevisaoList where imgCondicaoTempo equals to UPDATED_IMG_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldNotBeFound("imgCondicaoTempo.in=" + UPDATED_IMG_CONDICAO_TEMPO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByImgCondicaoTempoIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where imgCondicaoTempo is not null
        defaultBoletimPrevisaoShouldBeFound("imgCondicaoTempo.specified=true");

        // Get all the boletimPrevisaoList where imgCondicaoTempo is null
        defaultBoletimPrevisaoShouldNotBeFound("imgCondicaoTempo.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByImgCondicaoTempoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where imgCondicaoTempo is greater than or equal to DEFAULT_IMG_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldBeFound("imgCondicaoTempo.greaterThanOrEqual=" + DEFAULT_IMG_CONDICAO_TEMPO);

        // Get all the boletimPrevisaoList where imgCondicaoTempo is greater than or equal to UPDATED_IMG_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldNotBeFound("imgCondicaoTempo.greaterThanOrEqual=" + UPDATED_IMG_CONDICAO_TEMPO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByImgCondicaoTempoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where imgCondicaoTempo is less than or equal to DEFAULT_IMG_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldBeFound("imgCondicaoTempo.lessThanOrEqual=" + DEFAULT_IMG_CONDICAO_TEMPO);

        // Get all the boletimPrevisaoList where imgCondicaoTempo is less than or equal to SMALLER_IMG_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldNotBeFound("imgCondicaoTempo.lessThanOrEqual=" + SMALLER_IMG_CONDICAO_TEMPO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByImgCondicaoTempoIsLessThanSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where imgCondicaoTempo is less than DEFAULT_IMG_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldNotBeFound("imgCondicaoTempo.lessThan=" + DEFAULT_IMG_CONDICAO_TEMPO);

        // Get all the boletimPrevisaoList where imgCondicaoTempo is less than UPDATED_IMG_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldBeFound("imgCondicaoTempo.lessThan=" + UPDATED_IMG_CONDICAO_TEMPO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByImgCondicaoTempoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where imgCondicaoTempo is greater than DEFAULT_IMG_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldNotBeFound("imgCondicaoTempo.greaterThan=" + DEFAULT_IMG_CONDICAO_TEMPO);

        // Get all the boletimPrevisaoList where imgCondicaoTempo is greater than SMALLER_IMG_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldBeFound("imgCondicaoTempo.greaterThan=" + SMALLER_IMG_CONDICAO_TEMPO);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByCondicaoTempoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where condicaoTempo equals to DEFAULT_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldBeFound("condicaoTempo.equals=" + DEFAULT_CONDICAO_TEMPO);

        // Get all the boletimPrevisaoList where condicaoTempo equals to UPDATED_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldNotBeFound("condicaoTempo.equals=" + UPDATED_CONDICAO_TEMPO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByCondicaoTempoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where condicaoTempo not equals to DEFAULT_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldNotBeFound("condicaoTempo.notEquals=" + DEFAULT_CONDICAO_TEMPO);

        // Get all the boletimPrevisaoList where condicaoTempo not equals to UPDATED_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldBeFound("condicaoTempo.notEquals=" + UPDATED_CONDICAO_TEMPO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByCondicaoTempoIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where condicaoTempo in DEFAULT_CONDICAO_TEMPO or UPDATED_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldBeFound("condicaoTempo.in=" + DEFAULT_CONDICAO_TEMPO + "," + UPDATED_CONDICAO_TEMPO);

        // Get all the boletimPrevisaoList where condicaoTempo equals to UPDATED_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldNotBeFound("condicaoTempo.in=" + UPDATED_CONDICAO_TEMPO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByCondicaoTempoIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where condicaoTempo is not null
        defaultBoletimPrevisaoShouldBeFound("condicaoTempo.specified=true");

        // Get all the boletimPrevisaoList where condicaoTempo is null
        defaultBoletimPrevisaoShouldNotBeFound("condicaoTempo.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimPrevisaosByCondicaoTempoContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where condicaoTempo contains DEFAULT_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldBeFound("condicaoTempo.contains=" + DEFAULT_CONDICAO_TEMPO);

        // Get all the boletimPrevisaoList where condicaoTempo contains UPDATED_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldNotBeFound("condicaoTempo.contains=" + UPDATED_CONDICAO_TEMPO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByCondicaoTempoNotContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where condicaoTempo does not contain DEFAULT_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldNotBeFound("condicaoTempo.doesNotContain=" + DEFAULT_CONDICAO_TEMPO);

        // Get all the boletimPrevisaoList where condicaoTempo does not contain UPDATED_CONDICAO_TEMPO
        defaultBoletimPrevisaoShouldBeFound("condicaoTempo.doesNotContain=" + UPDATED_CONDICAO_TEMPO);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByObservacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where observacao equals to DEFAULT_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("observacao.equals=" + DEFAULT_OBSERVACAO);

        // Get all the boletimPrevisaoList where observacao equals to UPDATED_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("observacao.equals=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByObservacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where observacao not equals to DEFAULT_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("observacao.notEquals=" + DEFAULT_OBSERVACAO);

        // Get all the boletimPrevisaoList where observacao not equals to UPDATED_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("observacao.notEquals=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByObservacaoIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where observacao in DEFAULT_OBSERVACAO or UPDATED_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("observacao.in=" + DEFAULT_OBSERVACAO + "," + UPDATED_OBSERVACAO);

        // Get all the boletimPrevisaoList where observacao equals to UPDATED_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("observacao.in=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByObservacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where observacao is not null
        defaultBoletimPrevisaoShouldBeFound("observacao.specified=true");

        // Get all the boletimPrevisaoList where observacao is null
        defaultBoletimPrevisaoShouldNotBeFound("observacao.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimPrevisaosByObservacaoContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where observacao contains DEFAULT_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("observacao.contains=" + DEFAULT_OBSERVACAO);

        // Get all the boletimPrevisaoList where observacao contains UPDATED_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("observacao.contains=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByObservacaoNotContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where observacao does not contain DEFAULT_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("observacao.doesNotContain=" + DEFAULT_OBSERVACAO);

        // Get all the boletimPrevisaoList where observacao does not contain UPDATED_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("observacao.doesNotContain=" + UPDATED_OBSERVACAO);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByGrupoOrdemIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where grupoOrdem equals to DEFAULT_GRUPO_ORDEM
        defaultBoletimPrevisaoShouldBeFound("grupoOrdem.equals=" + DEFAULT_GRUPO_ORDEM);

        // Get all the boletimPrevisaoList where grupoOrdem equals to UPDATED_GRUPO_ORDEM
        defaultBoletimPrevisaoShouldNotBeFound("grupoOrdem.equals=" + UPDATED_GRUPO_ORDEM);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByGrupoOrdemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where grupoOrdem not equals to DEFAULT_GRUPO_ORDEM
        defaultBoletimPrevisaoShouldNotBeFound("grupoOrdem.notEquals=" + DEFAULT_GRUPO_ORDEM);

        // Get all the boletimPrevisaoList where grupoOrdem not equals to UPDATED_GRUPO_ORDEM
        defaultBoletimPrevisaoShouldBeFound("grupoOrdem.notEquals=" + UPDATED_GRUPO_ORDEM);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByGrupoOrdemIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where grupoOrdem in DEFAULT_GRUPO_ORDEM or UPDATED_GRUPO_ORDEM
        defaultBoletimPrevisaoShouldBeFound("grupoOrdem.in=" + DEFAULT_GRUPO_ORDEM + "," + UPDATED_GRUPO_ORDEM);

        // Get all the boletimPrevisaoList where grupoOrdem equals to UPDATED_GRUPO_ORDEM
        defaultBoletimPrevisaoShouldNotBeFound("grupoOrdem.in=" + UPDATED_GRUPO_ORDEM);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByGrupoOrdemIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where grupoOrdem is not null
        defaultBoletimPrevisaoShouldBeFound("grupoOrdem.specified=true");

        // Get all the boletimPrevisaoList where grupoOrdem is null
        defaultBoletimPrevisaoShouldNotBeFound("grupoOrdem.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByGrupoOrdemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where grupoOrdem is greater than or equal to DEFAULT_GRUPO_ORDEM
        defaultBoletimPrevisaoShouldBeFound("grupoOrdem.greaterThanOrEqual=" + DEFAULT_GRUPO_ORDEM);

        // Get all the boletimPrevisaoList where grupoOrdem is greater than or equal to UPDATED_GRUPO_ORDEM
        defaultBoletimPrevisaoShouldNotBeFound("grupoOrdem.greaterThanOrEqual=" + UPDATED_GRUPO_ORDEM);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByGrupoOrdemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where grupoOrdem is less than or equal to DEFAULT_GRUPO_ORDEM
        defaultBoletimPrevisaoShouldBeFound("grupoOrdem.lessThanOrEqual=" + DEFAULT_GRUPO_ORDEM);

        // Get all the boletimPrevisaoList where grupoOrdem is less than or equal to SMALLER_GRUPO_ORDEM
        defaultBoletimPrevisaoShouldNotBeFound("grupoOrdem.lessThanOrEqual=" + SMALLER_GRUPO_ORDEM);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByGrupoOrdemIsLessThanSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where grupoOrdem is less than DEFAULT_GRUPO_ORDEM
        defaultBoletimPrevisaoShouldNotBeFound("grupoOrdem.lessThan=" + DEFAULT_GRUPO_ORDEM);

        // Get all the boletimPrevisaoList where grupoOrdem is less than UPDATED_GRUPO_ORDEM
        defaultBoletimPrevisaoShouldBeFound("grupoOrdem.lessThan=" + UPDATED_GRUPO_ORDEM);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByGrupoOrdemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where grupoOrdem is greater than DEFAULT_GRUPO_ORDEM
        defaultBoletimPrevisaoShouldNotBeFound("grupoOrdem.greaterThan=" + DEFAULT_GRUPO_ORDEM);

        // Get all the boletimPrevisaoList where grupoOrdem is greater than SMALLER_GRUPO_ORDEM
        defaultBoletimPrevisaoShouldBeFound("grupoOrdem.greaterThan=" + SMALLER_GRUPO_ORDEM);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByOndasIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ondas equals to DEFAULT_ONDAS
        defaultBoletimPrevisaoShouldBeFound("ondas.equals=" + DEFAULT_ONDAS);

        // Get all the boletimPrevisaoList where ondas equals to UPDATED_ONDAS
        defaultBoletimPrevisaoShouldNotBeFound("ondas.equals=" + UPDATED_ONDAS);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByOndasIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ondas not equals to DEFAULT_ONDAS
        defaultBoletimPrevisaoShouldNotBeFound("ondas.notEquals=" + DEFAULT_ONDAS);

        // Get all the boletimPrevisaoList where ondas not equals to UPDATED_ONDAS
        defaultBoletimPrevisaoShouldBeFound("ondas.notEquals=" + UPDATED_ONDAS);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByOndasIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ondas in DEFAULT_ONDAS or UPDATED_ONDAS
        defaultBoletimPrevisaoShouldBeFound("ondas.in=" + DEFAULT_ONDAS + "," + UPDATED_ONDAS);

        // Get all the boletimPrevisaoList where ondas equals to UPDATED_ONDAS
        defaultBoletimPrevisaoShouldNotBeFound("ondas.in=" + UPDATED_ONDAS);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByOndasIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ondas is not null
        defaultBoletimPrevisaoShouldBeFound("ondas.specified=true");

        // Get all the boletimPrevisaoList where ondas is null
        defaultBoletimPrevisaoShouldNotBeFound("ondas.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimPrevisaosByOndasContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ondas contains DEFAULT_ONDAS
        defaultBoletimPrevisaoShouldBeFound("ondas.contains=" + DEFAULT_ONDAS);

        // Get all the boletimPrevisaoList where ondas contains UPDATED_ONDAS
        defaultBoletimPrevisaoShouldNotBeFound("ondas.contains=" + UPDATED_ONDAS);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByOndasNotContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ondas does not contain DEFAULT_ONDAS
        defaultBoletimPrevisaoShouldNotBeFound("ondas.doesNotContain=" + DEFAULT_ONDAS);

        // Get all the boletimPrevisaoList where ondas does not contain UPDATED_ONDAS
        defaultBoletimPrevisaoShouldBeFound("ondas.doesNotContain=" + UPDATED_ONDAS);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTemperaturaDeIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where temperaturaDe equals to DEFAULT_TEMPERATURA_DE
        defaultBoletimPrevisaoShouldBeFound("temperaturaDe.equals=" + DEFAULT_TEMPERATURA_DE);

        // Get all the boletimPrevisaoList where temperaturaDe equals to UPDATED_TEMPERATURA_DE
        defaultBoletimPrevisaoShouldNotBeFound("temperaturaDe.equals=" + UPDATED_TEMPERATURA_DE);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTemperaturaDeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where temperaturaDe not equals to DEFAULT_TEMPERATURA_DE
        defaultBoletimPrevisaoShouldNotBeFound("temperaturaDe.notEquals=" + DEFAULT_TEMPERATURA_DE);

        // Get all the boletimPrevisaoList where temperaturaDe not equals to UPDATED_TEMPERATURA_DE
        defaultBoletimPrevisaoShouldBeFound("temperaturaDe.notEquals=" + UPDATED_TEMPERATURA_DE);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTemperaturaDeIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where temperaturaDe in DEFAULT_TEMPERATURA_DE or UPDATED_TEMPERATURA_DE
        defaultBoletimPrevisaoShouldBeFound("temperaturaDe.in=" + DEFAULT_TEMPERATURA_DE + "," + UPDATED_TEMPERATURA_DE);

        // Get all the boletimPrevisaoList where temperaturaDe equals to UPDATED_TEMPERATURA_DE
        defaultBoletimPrevisaoShouldNotBeFound("temperaturaDe.in=" + UPDATED_TEMPERATURA_DE);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTemperaturaDeIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where temperaturaDe is not null
        defaultBoletimPrevisaoShouldBeFound("temperaturaDe.specified=true");

        // Get all the boletimPrevisaoList where temperaturaDe is null
        defaultBoletimPrevisaoShouldNotBeFound("temperaturaDe.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTemperaturaDeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where temperaturaDe is greater than or equal to DEFAULT_TEMPERATURA_DE
        defaultBoletimPrevisaoShouldBeFound("temperaturaDe.greaterThanOrEqual=" + DEFAULT_TEMPERATURA_DE);

        // Get all the boletimPrevisaoList where temperaturaDe is greater than or equal to UPDATED_TEMPERATURA_DE
        defaultBoletimPrevisaoShouldNotBeFound("temperaturaDe.greaterThanOrEqual=" + UPDATED_TEMPERATURA_DE);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTemperaturaDeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where temperaturaDe is less than or equal to DEFAULT_TEMPERATURA_DE
        defaultBoletimPrevisaoShouldBeFound("temperaturaDe.lessThanOrEqual=" + DEFAULT_TEMPERATURA_DE);

        // Get all the boletimPrevisaoList where temperaturaDe is less than or equal to SMALLER_TEMPERATURA_DE
        defaultBoletimPrevisaoShouldNotBeFound("temperaturaDe.lessThanOrEqual=" + SMALLER_TEMPERATURA_DE);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTemperaturaDeIsLessThanSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where temperaturaDe is less than DEFAULT_TEMPERATURA_DE
        defaultBoletimPrevisaoShouldNotBeFound("temperaturaDe.lessThan=" + DEFAULT_TEMPERATURA_DE);

        // Get all the boletimPrevisaoList where temperaturaDe is less than UPDATED_TEMPERATURA_DE
        defaultBoletimPrevisaoShouldBeFound("temperaturaDe.lessThan=" + UPDATED_TEMPERATURA_DE);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTemperaturaDeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where temperaturaDe is greater than DEFAULT_TEMPERATURA_DE
        defaultBoletimPrevisaoShouldNotBeFound("temperaturaDe.greaterThan=" + DEFAULT_TEMPERATURA_DE);

        // Get all the boletimPrevisaoList where temperaturaDe is greater than SMALLER_TEMPERATURA_DE
        defaultBoletimPrevisaoShouldBeFound("temperaturaDe.greaterThan=" + SMALLER_TEMPERATURA_DE);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTemperaturaAteIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where temperaturaAte equals to DEFAULT_TEMPERATURA_ATE
        defaultBoletimPrevisaoShouldBeFound("temperaturaAte.equals=" + DEFAULT_TEMPERATURA_ATE);

        // Get all the boletimPrevisaoList where temperaturaAte equals to UPDATED_TEMPERATURA_ATE
        defaultBoletimPrevisaoShouldNotBeFound("temperaturaAte.equals=" + UPDATED_TEMPERATURA_ATE);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTemperaturaAteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where temperaturaAte not equals to DEFAULT_TEMPERATURA_ATE
        defaultBoletimPrevisaoShouldNotBeFound("temperaturaAte.notEquals=" + DEFAULT_TEMPERATURA_ATE);

        // Get all the boletimPrevisaoList where temperaturaAte not equals to UPDATED_TEMPERATURA_ATE
        defaultBoletimPrevisaoShouldBeFound("temperaturaAte.notEquals=" + UPDATED_TEMPERATURA_ATE);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTemperaturaAteIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where temperaturaAte in DEFAULT_TEMPERATURA_ATE or UPDATED_TEMPERATURA_ATE
        defaultBoletimPrevisaoShouldBeFound("temperaturaAte.in=" + DEFAULT_TEMPERATURA_ATE + "," + UPDATED_TEMPERATURA_ATE);

        // Get all the boletimPrevisaoList where temperaturaAte equals to UPDATED_TEMPERATURA_ATE
        defaultBoletimPrevisaoShouldNotBeFound("temperaturaAte.in=" + UPDATED_TEMPERATURA_ATE);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTemperaturaAteIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where temperaturaAte is not null
        defaultBoletimPrevisaoShouldBeFound("temperaturaAte.specified=true");

        // Get all the boletimPrevisaoList where temperaturaAte is null
        defaultBoletimPrevisaoShouldNotBeFound("temperaturaAte.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTemperaturaAteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where temperaturaAte is greater than or equal to DEFAULT_TEMPERATURA_ATE
        defaultBoletimPrevisaoShouldBeFound("temperaturaAte.greaterThanOrEqual=" + DEFAULT_TEMPERATURA_ATE);

        // Get all the boletimPrevisaoList where temperaturaAte is greater than or equal to UPDATED_TEMPERATURA_ATE
        defaultBoletimPrevisaoShouldNotBeFound("temperaturaAte.greaterThanOrEqual=" + UPDATED_TEMPERATURA_ATE);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTemperaturaAteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where temperaturaAte is less than or equal to DEFAULT_TEMPERATURA_ATE
        defaultBoletimPrevisaoShouldBeFound("temperaturaAte.lessThanOrEqual=" + DEFAULT_TEMPERATURA_ATE);

        // Get all the boletimPrevisaoList where temperaturaAte is less than or equal to SMALLER_TEMPERATURA_ATE
        defaultBoletimPrevisaoShouldNotBeFound("temperaturaAte.lessThanOrEqual=" + SMALLER_TEMPERATURA_ATE);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTemperaturaAteIsLessThanSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where temperaturaAte is less than DEFAULT_TEMPERATURA_ATE
        defaultBoletimPrevisaoShouldNotBeFound("temperaturaAte.lessThan=" + DEFAULT_TEMPERATURA_ATE);

        // Get all the boletimPrevisaoList where temperaturaAte is less than UPDATED_TEMPERATURA_ATE
        defaultBoletimPrevisaoShouldBeFound("temperaturaAte.lessThan=" + UPDATED_TEMPERATURA_ATE);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTemperaturaAteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where temperaturaAte is greater than DEFAULT_TEMPERATURA_ATE
        defaultBoletimPrevisaoShouldNotBeFound("temperaturaAte.greaterThan=" + DEFAULT_TEMPERATURA_ATE);

        // Get all the boletimPrevisaoList where temperaturaAte is greater than SMALLER_TEMPERATURA_ATE
        defaultBoletimPrevisaoShouldBeFound("temperaturaAte.greaterThan=" + SMALLER_TEMPERATURA_ATE);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentovelocidademediakmhIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventovelocidademediakmh equals to DEFAULT_VENTOVELOCIDADEMEDIAKMH
        defaultBoletimPrevisaoShouldBeFound("ventovelocidademediakmh.equals=" + DEFAULT_VENTOVELOCIDADEMEDIAKMH);

        // Get all the boletimPrevisaoList where ventovelocidademediakmh equals to UPDATED_VENTOVELOCIDADEMEDIAKMH
        defaultBoletimPrevisaoShouldNotBeFound("ventovelocidademediakmh.equals=" + UPDATED_VENTOVELOCIDADEMEDIAKMH);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentovelocidademediakmhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventovelocidademediakmh not equals to DEFAULT_VENTOVELOCIDADEMEDIAKMH
        defaultBoletimPrevisaoShouldNotBeFound("ventovelocidademediakmh.notEquals=" + DEFAULT_VENTOVELOCIDADEMEDIAKMH);

        // Get all the boletimPrevisaoList where ventovelocidademediakmh not equals to UPDATED_VENTOVELOCIDADEMEDIAKMH
        defaultBoletimPrevisaoShouldBeFound("ventovelocidademediakmh.notEquals=" + UPDATED_VENTOVELOCIDADEMEDIAKMH);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentovelocidademediakmhIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventovelocidademediakmh in DEFAULT_VENTOVELOCIDADEMEDIAKMH or UPDATED_VENTOVELOCIDADEMEDIAKMH
        defaultBoletimPrevisaoShouldBeFound("ventovelocidademediakmh.in=" + DEFAULT_VENTOVELOCIDADEMEDIAKMH + "," + UPDATED_VENTOVELOCIDADEMEDIAKMH);

        // Get all the boletimPrevisaoList where ventovelocidademediakmh equals to UPDATED_VENTOVELOCIDADEMEDIAKMH
        defaultBoletimPrevisaoShouldNotBeFound("ventovelocidademediakmh.in=" + UPDATED_VENTOVELOCIDADEMEDIAKMH);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentovelocidademediakmhIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventovelocidademediakmh is not null
        defaultBoletimPrevisaoShouldBeFound("ventovelocidademediakmh.specified=true");

        // Get all the boletimPrevisaoList where ventovelocidademediakmh is null
        defaultBoletimPrevisaoShouldNotBeFound("ventovelocidademediakmh.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentovelocidademediakmhIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventovelocidademediakmh is greater than or equal to DEFAULT_VENTOVELOCIDADEMEDIAKMH
        defaultBoletimPrevisaoShouldBeFound("ventovelocidademediakmh.greaterThanOrEqual=" + DEFAULT_VENTOVELOCIDADEMEDIAKMH);

        // Get all the boletimPrevisaoList where ventovelocidademediakmh is greater than or equal to UPDATED_VENTOVELOCIDADEMEDIAKMH
        defaultBoletimPrevisaoShouldNotBeFound("ventovelocidademediakmh.greaterThanOrEqual=" + UPDATED_VENTOVELOCIDADEMEDIAKMH);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentovelocidademediakmhIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventovelocidademediakmh is less than or equal to DEFAULT_VENTOVELOCIDADEMEDIAKMH
        defaultBoletimPrevisaoShouldBeFound("ventovelocidademediakmh.lessThanOrEqual=" + DEFAULT_VENTOVELOCIDADEMEDIAKMH);

        // Get all the boletimPrevisaoList where ventovelocidademediakmh is less than or equal to SMALLER_VENTOVELOCIDADEMEDIAKMH
        defaultBoletimPrevisaoShouldNotBeFound("ventovelocidademediakmh.lessThanOrEqual=" + SMALLER_VENTOVELOCIDADEMEDIAKMH);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentovelocidademediakmhIsLessThanSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventovelocidademediakmh is less than DEFAULT_VENTOVELOCIDADEMEDIAKMH
        defaultBoletimPrevisaoShouldNotBeFound("ventovelocidademediakmh.lessThan=" + DEFAULT_VENTOVELOCIDADEMEDIAKMH);

        // Get all the boletimPrevisaoList where ventovelocidademediakmh is less than UPDATED_VENTOVELOCIDADEMEDIAKMH
        defaultBoletimPrevisaoShouldBeFound("ventovelocidademediakmh.lessThan=" + UPDATED_VENTOVELOCIDADEMEDIAKMH);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentovelocidademediakmhIsGreaterThanSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventovelocidademediakmh is greater than DEFAULT_VENTOVELOCIDADEMEDIAKMH
        defaultBoletimPrevisaoShouldNotBeFound("ventovelocidademediakmh.greaterThan=" + DEFAULT_VENTOVELOCIDADEMEDIAKMH);

        // Get all the boletimPrevisaoList where ventovelocidademediakmh is greater than SMALLER_VENTOVELOCIDADEMEDIAKMH
        defaultBoletimPrevisaoShouldBeFound("ventovelocidademediakmh.greaterThan=" + SMALLER_VENTOVELOCIDADEMEDIAKMH);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentosObservacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventosObservacao equals to DEFAULT_VENTOS_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("ventosObservacao.equals=" + DEFAULT_VENTOS_OBSERVACAO);

        // Get all the boletimPrevisaoList where ventosObservacao equals to UPDATED_VENTOS_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("ventosObservacao.equals=" + UPDATED_VENTOS_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentosObservacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventosObservacao not equals to DEFAULT_VENTOS_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("ventosObservacao.notEquals=" + DEFAULT_VENTOS_OBSERVACAO);

        // Get all the boletimPrevisaoList where ventosObservacao not equals to UPDATED_VENTOS_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("ventosObservacao.notEquals=" + UPDATED_VENTOS_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentosObservacaoIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventosObservacao in DEFAULT_VENTOS_OBSERVACAO or UPDATED_VENTOS_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("ventosObservacao.in=" + DEFAULT_VENTOS_OBSERVACAO + "," + UPDATED_VENTOS_OBSERVACAO);

        // Get all the boletimPrevisaoList where ventosObservacao equals to UPDATED_VENTOS_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("ventosObservacao.in=" + UPDATED_VENTOS_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentosObservacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventosObservacao is not null
        defaultBoletimPrevisaoShouldBeFound("ventosObservacao.specified=true");

        // Get all the boletimPrevisaoList where ventosObservacao is null
        defaultBoletimPrevisaoShouldNotBeFound("ventosObservacao.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentosObservacaoContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventosObservacao contains DEFAULT_VENTOS_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("ventosObservacao.contains=" + DEFAULT_VENTOS_OBSERVACAO);

        // Get all the boletimPrevisaoList where ventosObservacao contains UPDATED_VENTOS_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("ventosObservacao.contains=" + UPDATED_VENTOS_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentosObservacaoNotContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventosObservacao does not contain DEFAULT_VENTOS_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("ventosObservacao.doesNotContain=" + DEFAULT_VENTOS_OBSERVACAO);

        // Get all the boletimPrevisaoList where ventosObservacao does not contain UPDATED_VENTOS_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("ventosObservacao.doesNotContain=" + UPDATED_VENTOS_OBSERVACAO);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentoRajadaIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventoRajada equals to DEFAULT_VENTO_RAJADA
        defaultBoletimPrevisaoShouldBeFound("ventoRajada.equals=" + DEFAULT_VENTO_RAJADA);

        // Get all the boletimPrevisaoList where ventoRajada equals to UPDATED_VENTO_RAJADA
        defaultBoletimPrevisaoShouldNotBeFound("ventoRajada.equals=" + UPDATED_VENTO_RAJADA);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentoRajadaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventoRajada not equals to DEFAULT_VENTO_RAJADA
        defaultBoletimPrevisaoShouldNotBeFound("ventoRajada.notEquals=" + DEFAULT_VENTO_RAJADA);

        // Get all the boletimPrevisaoList where ventoRajada not equals to UPDATED_VENTO_RAJADA
        defaultBoletimPrevisaoShouldBeFound("ventoRajada.notEquals=" + UPDATED_VENTO_RAJADA);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentoRajadaIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventoRajada in DEFAULT_VENTO_RAJADA or UPDATED_VENTO_RAJADA
        defaultBoletimPrevisaoShouldBeFound("ventoRajada.in=" + DEFAULT_VENTO_RAJADA + "," + UPDATED_VENTO_RAJADA);

        // Get all the boletimPrevisaoList where ventoRajada equals to UPDATED_VENTO_RAJADA
        defaultBoletimPrevisaoShouldNotBeFound("ventoRajada.in=" + UPDATED_VENTO_RAJADA);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentoRajadaIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where ventoRajada is not null
        defaultBoletimPrevisaoShouldBeFound("ventoRajada.specified=true");

        // Get all the boletimPrevisaoList where ventoRajada is null
        defaultBoletimPrevisaoShouldNotBeFound("ventoRajada.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTempestadeObservacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where tempestadeObservacao equals to DEFAULT_TEMPESTADE_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("tempestadeObservacao.equals=" + DEFAULT_TEMPESTADE_OBSERVACAO);

        // Get all the boletimPrevisaoList where tempestadeObservacao equals to UPDATED_TEMPESTADE_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("tempestadeObservacao.equals=" + UPDATED_TEMPESTADE_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTempestadeObservacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where tempestadeObservacao not equals to DEFAULT_TEMPESTADE_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("tempestadeObservacao.notEquals=" + DEFAULT_TEMPESTADE_OBSERVACAO);

        // Get all the boletimPrevisaoList where tempestadeObservacao not equals to UPDATED_TEMPESTADE_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("tempestadeObservacao.notEquals=" + UPDATED_TEMPESTADE_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTempestadeObservacaoIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where tempestadeObservacao in DEFAULT_TEMPESTADE_OBSERVACAO or UPDATED_TEMPESTADE_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("tempestadeObservacao.in=" + DEFAULT_TEMPESTADE_OBSERVACAO + "," + UPDATED_TEMPESTADE_OBSERVACAO);

        // Get all the boletimPrevisaoList where tempestadeObservacao equals to UPDATED_TEMPESTADE_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("tempestadeObservacao.in=" + UPDATED_TEMPESTADE_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTempestadeObservacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where tempestadeObservacao is not null
        defaultBoletimPrevisaoShouldBeFound("tempestadeObservacao.specified=true");

        // Get all the boletimPrevisaoList where tempestadeObservacao is null
        defaultBoletimPrevisaoShouldNotBeFound("tempestadeObservacao.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimPrevisaosByTempestadeObservacaoContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where tempestadeObservacao contains DEFAULT_TEMPESTADE_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("tempestadeObservacao.contains=" + DEFAULT_TEMPESTADE_OBSERVACAO);

        // Get all the boletimPrevisaoList where tempestadeObservacao contains UPDATED_TEMPESTADE_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("tempestadeObservacao.contains=" + UPDATED_TEMPESTADE_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTempestadeObservacaoNotContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where tempestadeObservacao does not contain DEFAULT_TEMPESTADE_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("tempestadeObservacao.doesNotContain=" + DEFAULT_TEMPESTADE_OBSERVACAO);

        // Get all the boletimPrevisaoList where tempestadeObservacao does not contain UPDATED_TEMPESTADE_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("tempestadeObservacao.doesNotContain=" + UPDATED_TEMPESTADE_OBSERVACAO);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByChuvaObservacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where chuvaObservacao equals to DEFAULT_CHUVA_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("chuvaObservacao.equals=" + DEFAULT_CHUVA_OBSERVACAO);

        // Get all the boletimPrevisaoList where chuvaObservacao equals to UPDATED_CHUVA_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("chuvaObservacao.equals=" + UPDATED_CHUVA_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByChuvaObservacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where chuvaObservacao not equals to DEFAULT_CHUVA_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("chuvaObservacao.notEquals=" + DEFAULT_CHUVA_OBSERVACAO);

        // Get all the boletimPrevisaoList where chuvaObservacao not equals to UPDATED_CHUVA_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("chuvaObservacao.notEquals=" + UPDATED_CHUVA_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByChuvaObservacaoIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where chuvaObservacao in DEFAULT_CHUVA_OBSERVACAO or UPDATED_CHUVA_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("chuvaObservacao.in=" + DEFAULT_CHUVA_OBSERVACAO + "," + UPDATED_CHUVA_OBSERVACAO);

        // Get all the boletimPrevisaoList where chuvaObservacao equals to UPDATED_CHUVA_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("chuvaObservacao.in=" + UPDATED_CHUVA_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByChuvaObservacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where chuvaObservacao is not null
        defaultBoletimPrevisaoShouldBeFound("chuvaObservacao.specified=true");

        // Get all the boletimPrevisaoList where chuvaObservacao is null
        defaultBoletimPrevisaoShouldNotBeFound("chuvaObservacao.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimPrevisaosByChuvaObservacaoContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where chuvaObservacao contains DEFAULT_CHUVA_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("chuvaObservacao.contains=" + DEFAULT_CHUVA_OBSERVACAO);

        // Get all the boletimPrevisaoList where chuvaObservacao contains UPDATED_CHUVA_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("chuvaObservacao.contains=" + UPDATED_CHUVA_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByChuvaObservacaoNotContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where chuvaObservacao does not contain DEFAULT_CHUVA_OBSERVACAO
        defaultBoletimPrevisaoShouldNotBeFound("chuvaObservacao.doesNotContain=" + DEFAULT_CHUVA_OBSERVACAO);

        // Get all the boletimPrevisaoList where chuvaObservacao does not contain UPDATED_CHUVA_OBSERVACAO
        defaultBoletimPrevisaoShouldBeFound("chuvaObservacao.doesNotContain=" + UPDATED_CHUVA_OBSERVACAO);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where created equals to DEFAULT_CREATED
        defaultBoletimPrevisaoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the boletimPrevisaoList where created equals to UPDATED_CREATED
        defaultBoletimPrevisaoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where created not equals to DEFAULT_CREATED
        defaultBoletimPrevisaoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the boletimPrevisaoList where created not equals to UPDATED_CREATED
        defaultBoletimPrevisaoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultBoletimPrevisaoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the boletimPrevisaoList where created equals to UPDATED_CREATED
        defaultBoletimPrevisaoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where created is not null
        defaultBoletimPrevisaoShouldBeFound("created.specified=true");

        // Get all the boletimPrevisaoList where created is null
        defaultBoletimPrevisaoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where updated equals to DEFAULT_UPDATED
        defaultBoletimPrevisaoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the boletimPrevisaoList where updated equals to UPDATED_UPDATED
        defaultBoletimPrevisaoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where updated not equals to DEFAULT_UPDATED
        defaultBoletimPrevisaoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the boletimPrevisaoList where updated not equals to UPDATED_UPDATED
        defaultBoletimPrevisaoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultBoletimPrevisaoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the boletimPrevisaoList where updated equals to UPDATED_UPDATED
        defaultBoletimPrevisaoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList where updated is not null
        defaultBoletimPrevisaoShouldBeFound("updated.specified=true");

        // Get all the boletimPrevisaoList where updated is null
        defaultBoletimPrevisaoShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaosByBoletimIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        Boletim boletim = BoletimResourceIT.createEntity(em);
        em.persist(boletim);
        em.flush();
        boletimPrevisao.setBoletim(boletim);
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        Long boletimId = boletim.getId();

        // Get all the boletimPrevisaoList where boletim equals to boletimId
        defaultBoletimPrevisaoShouldBeFound("boletimId.equals=" + boletimId);

        // Get all the boletimPrevisaoList where boletim equals to boletimId + 1
        defaultBoletimPrevisaoShouldNotBeFound("boletimId.equals=" + (boletimId + 1));
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByBoletimPrevObsIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        BoletimPrevObs boletimPrevObs = BoletimPrevObsResourceIT.createEntity(em);
        em.persist(boletimPrevObs);
        em.flush();
        boletimPrevisao.setBoletimPrevObs(boletimPrevObs);
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        Long boletimPrevObsId = boletimPrevObs.getId();

        // Get all the boletimPrevisaoList where boletimPrevObs equals to boletimPrevObsId
        defaultBoletimPrevisaoShouldBeFound("boletimPrevObsId.equals=" + boletimPrevObsId);

        // Get all the boletimPrevisaoList where boletimPrevObs equals to boletimPrevObsId + 1
        defaultBoletimPrevisaoShouldNotBeFound("boletimPrevObsId.equals=" + (boletimPrevObsId + 1));
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByIntensidadeChuvaIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        IntensidadeChuva intensidadeChuva = IntensidadeChuvaResourceIT.createEntity(em);
        em.persist(intensidadeChuva);
        em.flush();
        boletimPrevisao.setIntensidadeChuva(intensidadeChuva);
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        Long intensidadeChuvaId = intensidadeChuva.getId();

        // Get all the boletimPrevisaoList where intensidadeChuva equals to intensidadeChuvaId
        defaultBoletimPrevisaoShouldBeFound("intensidadeChuvaId.equals=" + intensidadeChuvaId);

        // Get all the boletimPrevisaoList where intensidadeChuva equals to intensidadeChuvaId + 1
        defaultBoletimPrevisaoShouldNotBeFound("intensidadeChuvaId.equals=" + (intensidadeChuvaId + 1));
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByUmidadeArIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        UmidadeAr umidadeAr = UmidadeArResourceIT.createEntity(em);
        em.persist(umidadeAr);
        em.flush();
        boletimPrevisao.setUmidadeAr(umidadeAr);
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        Long umidadeArId = umidadeAr.getId();

        // Get all the boletimPrevisaoList where umidadeAr equals to umidadeArId
        defaultBoletimPrevisaoShouldBeFound("umidadeArId.equals=" + umidadeArId);

        // Get all the boletimPrevisaoList where umidadeAr equals to umidadeArId + 1
        defaultBoletimPrevisaoShouldNotBeFound("umidadeArId.equals=" + (umidadeArId + 1));
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByAlvoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        Alvo alvo = AlvoResourceIT.createEntity(em);
        em.persist(alvo);
        em.flush();
        boletimPrevisao.setAlvo(alvo);
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        Long alvoId = alvo.getId();

        // Get all the boletimPrevisaoList where alvo equals to alvoId
        defaultBoletimPrevisaoShouldBeFound("alvoId.equals=" + alvoId);

        // Get all the boletimPrevisaoList where alvo equals to alvoId + 1
        defaultBoletimPrevisaoShouldNotBeFound("alvoId.equals=" + (alvoId + 1));
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByPontosCardeaisIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        PontosCardeais pontosCardeais = PontosCardeaisResourceIT.createEntity(em);
        em.persist(pontosCardeais);
        em.flush();
        boletimPrevisao.setPontosCardeais(pontosCardeais);
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        Long pontosCardeaisId = pontosCardeais.getId();

        // Get all the boletimPrevisaoList where pontosCardeais equals to pontosCardeaisId
        defaultBoletimPrevisaoShouldBeFound("pontosCardeaisId.equals=" + pontosCardeaisId);

        // Get all the boletimPrevisaoList where pontosCardeais equals to pontosCardeaisId + 1
        defaultBoletimPrevisaoShouldNotBeFound("pontosCardeaisId.equals=" + (pontosCardeaisId + 1));
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByVentoVmFaixaIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        VentoVmFaixa ventoVmFaixa = VentoVmFaixaResourceIT.createEntity(em);
        em.persist(ventoVmFaixa);
        em.flush();
        boletimPrevisao.setVentoVmFaixa(ventoVmFaixa);
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        Long ventoVmFaixaId = ventoVmFaixa.getId();

        // Get all the boletimPrevisaoList where ventoVmFaixa equals to ventoVmFaixaId
        defaultBoletimPrevisaoShouldBeFound("ventoVmFaixaId.equals=" + ventoVmFaixaId);

        // Get all the boletimPrevisaoList where ventoVmFaixa equals to ventoVmFaixaId + 1
        defaultBoletimPrevisaoShouldNotBeFound("ventoVmFaixaId.equals=" + (ventoVmFaixaId + 1));
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTempestadeProbabilidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        TempestadeProbabilidade tempestadeProbabilidade = TempestadeProbabilidadeResourceIT.createEntity(em);
        em.persist(tempestadeProbabilidade);
        em.flush();
        boletimPrevisao.setTempestadeProbabilidade(tempestadeProbabilidade);
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        Long tempestadeProbabilidadeId = tempestadeProbabilidade.getId();

        // Get all the boletimPrevisaoList where tempestadeProbabilidade equals to tempestadeProbabilidadeId
        defaultBoletimPrevisaoShouldBeFound("tempestadeProbabilidadeId.equals=" + tempestadeProbabilidadeId);

        // Get all the boletimPrevisaoList where tempestadeProbabilidade equals to tempestadeProbabilidadeId + 1
        defaultBoletimPrevisaoShouldNotBeFound("tempestadeProbabilidadeId.equals=" + (tempestadeProbabilidadeId + 1));
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByTempestadeNivelIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        TempestadeNivel tempestadeNivel = TempestadeNivelResourceIT.createEntity(em);
        em.persist(tempestadeNivel);
        em.flush();
        boletimPrevisao.setTempestadeNivel(tempestadeNivel);
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        Long tempestadeNivelId = tempestadeNivel.getId();

        // Get all the boletimPrevisaoList where tempestadeNivel equals to tempestadeNivelId
        defaultBoletimPrevisaoShouldBeFound("tempestadeNivelId.equals=" + tempestadeNivelId);

        // Get all the boletimPrevisaoList where tempestadeNivel equals to tempestadeNivelId + 1
        defaultBoletimPrevisaoShouldNotBeFound("tempestadeNivelId.equals=" + (tempestadeNivelId + 1));
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByAcumuladoChuvaFaixaIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        AcumuladoChuvaFaixa acumuladoChuvaFaixa = AcumuladoChuvaFaixaResourceIT.createEntity(em);
        em.persist(acumuladoChuvaFaixa);
        em.flush();
        boletimPrevisao.setAcumuladoChuvaFaixa(acumuladoChuvaFaixa);
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        Long acumuladoChuvaFaixaId = acumuladoChuvaFaixa.getId();

        // Get all the boletimPrevisaoList where acumuladoChuvaFaixa equals to acumuladoChuvaFaixaId
        defaultBoletimPrevisaoShouldBeFound("acumuladoChuvaFaixaId.equals=" + acumuladoChuvaFaixaId);

        // Get all the boletimPrevisaoList where acumuladoChuvaFaixa equals to acumuladoChuvaFaixaId + 1
        defaultBoletimPrevisaoShouldNotBeFound("acumuladoChuvaFaixaId.equals=" + (acumuladoChuvaFaixaId + 1));
    }


    @Test
    @Transactional
    public void getAllBoletimPrevisaosByCondicaoTempoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        CondicaoTempo condicaoTempo = CondicaoTempoResourceIT.createEntity(em);
        em.persist(condicaoTempo);
        em.flush();
        boletimPrevisao.setCondicaoTempo(condicaoTempo);
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);
        Long condicaoTempoId = condicaoTempo.getId();

        // Get all the boletimPrevisaoList where condicaoTempo equals to condicaoTempoId
        defaultBoletimPrevisaoShouldBeFound("condicaoTempoId.equals=" + condicaoTempoId);

        // Get all the boletimPrevisaoList where condicaoTempo equals to condicaoTempoId + 1
        defaultBoletimPrevisaoShouldNotBeFound("condicaoTempoId.equals=" + (condicaoTempoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBoletimPrevisaoShouldBeFound(String filter) throws Exception {
        restBoletimPrevisaoMockMvc.perform(get("/api/boletim-previsaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boletimPrevisao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].local").value(hasItem(DEFAULT_LOCAL)))
            .andExpect(jsonPath("$.[*].imgCondicaoTempo").value(hasItem(DEFAULT_IMG_CONDICAO_TEMPO)))
            .andExpect(jsonPath("$.[*].condicaoTempo").value(hasItem(DEFAULT_CONDICAO_TEMPO)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].grupoOrdem").value(hasItem(DEFAULT_GRUPO_ORDEM)))
            .andExpect(jsonPath("$.[*].ondas").value(hasItem(DEFAULT_ONDAS)))
            .andExpect(jsonPath("$.[*].temperaturaDe").value(hasItem(DEFAULT_TEMPERATURA_DE)))
            .andExpect(jsonPath("$.[*].temperaturaAte").value(hasItem(DEFAULT_TEMPERATURA_ATE)))
            .andExpect(jsonPath("$.[*].ventovelocidademediakmh").value(hasItem(DEFAULT_VENTOVELOCIDADEMEDIAKMH)))
            .andExpect(jsonPath("$.[*].ventosObservacao").value(hasItem(DEFAULT_VENTOS_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].ventoRajada").value(hasItem(DEFAULT_VENTO_RAJADA.booleanValue())))
            .andExpect(jsonPath("$.[*].tempestadeObservacao").value(hasItem(DEFAULT_TEMPESTADE_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].chuvaObservacao").value(hasItem(DEFAULT_CHUVA_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restBoletimPrevisaoMockMvc.perform(get("/api/boletim-previsaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBoletimPrevisaoShouldNotBeFound(String filter) throws Exception {
        restBoletimPrevisaoMockMvc.perform(get("/api/boletim-previsaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBoletimPrevisaoMockMvc.perform(get("/api/boletim-previsaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBoletimPrevisao() throws Exception {
        // Get the boletimPrevisao
        restBoletimPrevisaoMockMvc.perform(get("/api/boletim-previsaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoletimPrevisao() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        int databaseSizeBeforeUpdate = boletimPrevisaoRepository.findAll().size();

        // Update the boletimPrevisao
        BoletimPrevisao updatedBoletimPrevisao = boletimPrevisaoRepository.findById(boletimPrevisao.getId()).get();
        // Disconnect from session so that the updates on updatedBoletimPrevisao are not directly saved in db
        em.detach(updatedBoletimPrevisao);
        updatedBoletimPrevisao
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .local(UPDATED_LOCAL)
            .imgCondicaoTempo(UPDATED_IMG_CONDICAO_TEMPO)
            .condicaoTempo(UPDATED_CONDICAO_TEMPO)
            .observacao(UPDATED_OBSERVACAO)
            .grupoOrdem(UPDATED_GRUPO_ORDEM)
            .ondas(UPDATED_ONDAS)
            .temperaturaDe(UPDATED_TEMPERATURA_DE)
            .temperaturaAte(UPDATED_TEMPERATURA_ATE)
            .ventovelocidademediakmh(UPDATED_VENTOVELOCIDADEMEDIAKMH)
            .ventosObservacao(UPDATED_VENTOS_OBSERVACAO)
            .ventoRajada(UPDATED_VENTO_RAJADA)
            .tempestadeObservacao(UPDATED_TEMPESTADE_OBSERVACAO)
            .chuvaObservacao(UPDATED_CHUVA_OBSERVACAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        BoletimPrevisaoDTO boletimPrevisaoDTO = boletimPrevisaoMapper.toDto(updatedBoletimPrevisao);

        restBoletimPrevisaoMockMvc.perform(put("/api/boletim-previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevisaoDTO)))
            .andExpect(status().isOk());

        // Validate the BoletimPrevisao in the database
        List<BoletimPrevisao> boletimPrevisaoList = boletimPrevisaoRepository.findAll();
        assertThat(boletimPrevisaoList).hasSize(databaseSizeBeforeUpdate);
        BoletimPrevisao testBoletimPrevisao = boletimPrevisaoList.get(boletimPrevisaoList.size() - 1);
        assertThat(testBoletimPrevisao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testBoletimPrevisao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testBoletimPrevisao.getLocal()).isEqualTo(UPDATED_LOCAL);
        assertThat(testBoletimPrevisao.getImgCondicaoTempo()).isEqualTo(UPDATED_IMG_CONDICAO_TEMPO);
        assertThat(testBoletimPrevisao.getCondicaoTempo()).isEqualTo(UPDATED_CONDICAO_TEMPO);
        assertThat(testBoletimPrevisao.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testBoletimPrevisao.getGrupoOrdem()).isEqualTo(UPDATED_GRUPO_ORDEM);
        assertThat(testBoletimPrevisao.getOndas()).isEqualTo(UPDATED_ONDAS);
        assertThat(testBoletimPrevisao.getTemperaturaDe()).isEqualTo(UPDATED_TEMPERATURA_DE);
        assertThat(testBoletimPrevisao.getTemperaturaAte()).isEqualTo(UPDATED_TEMPERATURA_ATE);
        assertThat(testBoletimPrevisao.getVentovelocidademediakmh()).isEqualTo(UPDATED_VENTOVELOCIDADEMEDIAKMH);
        assertThat(testBoletimPrevisao.getVentosObservacao()).isEqualTo(UPDATED_VENTOS_OBSERVACAO);
        assertThat(testBoletimPrevisao.isVentoRajada()).isEqualTo(UPDATED_VENTO_RAJADA);
        assertThat(testBoletimPrevisao.getTempestadeObservacao()).isEqualTo(UPDATED_TEMPESTADE_OBSERVACAO);
        assertThat(testBoletimPrevisao.getChuvaObservacao()).isEqualTo(UPDATED_CHUVA_OBSERVACAO);
        assertThat(testBoletimPrevisao.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testBoletimPrevisao.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingBoletimPrevisao() throws Exception {
        int databaseSizeBeforeUpdate = boletimPrevisaoRepository.findAll().size();

        // Create the BoletimPrevisao
        BoletimPrevisaoDTO boletimPrevisaoDTO = boletimPrevisaoMapper.toDto(boletimPrevisao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoletimPrevisaoMockMvc.perform(put("/api/boletim-previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevisaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoletimPrevisao in the database
        List<BoletimPrevisao> boletimPrevisaoList = boletimPrevisaoRepository.findAll();
        assertThat(boletimPrevisaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBoletimPrevisao() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        int databaseSizeBeforeDelete = boletimPrevisaoRepository.findAll().size();

        // Delete the boletimPrevisao
        restBoletimPrevisaoMockMvc.perform(delete("/api/boletim-previsaos/{id}", boletimPrevisao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BoletimPrevisao> boletimPrevisaoList = boletimPrevisaoRepository.findAll();
        assertThat(boletimPrevisaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
