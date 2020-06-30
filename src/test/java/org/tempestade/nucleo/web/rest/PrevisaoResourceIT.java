package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Previsao;
import org.tempestade.nucleo.repository.PrevisaoRepository;
import org.tempestade.nucleo.service.PrevisaoService;
import org.tempestade.nucleo.service.dto.PrevisaoDTO;
import org.tempestade.nucleo.service.mapper.PrevisaoMapper;
import org.tempestade.nucleo.service.dto.PrevisaoCriteria;
import org.tempestade.nucleo.service.PrevisaoQueryService;

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
 * Integration tests for the {@link PrevisaoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PrevisaoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_NORTE = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_NORTE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_NORTE_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_NORTE_IMAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_NORDESTE = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_NORDESTE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_NORDESTE_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_NORDESTE_IMAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_SUL = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_SUL = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_SUL_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_SUL_IMAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_SUDESTE = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_SUDESTE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_SUDESTE_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_SUDESTE_IMAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_CENTRO_OESTE = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_CENTRO_OESTE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_CENTRO_OESTE_IMAGEM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENVIADO = false;
    private static final Boolean UPDATED_ENVIADO = true;

    private static final String DEFAULT_TEXTO_BRASIL = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_BRASIL = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_BRASIL_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_BRASIL_IMAGEM = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PrevisaoRepository previsaoRepository;

    @Autowired
    private PrevisaoMapper previsaoMapper;

    @Autowired
    private PrevisaoService previsaoService;

    @Autowired
    private PrevisaoQueryService previsaoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrevisaoMockMvc;

    private Previsao previsao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Previsao createEntity(EntityManager em) {
        Previsao previsao = new Previsao()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .textoNorte(DEFAULT_TEXTO_NORTE)
            .textoNorteImagem(DEFAULT_TEXTO_NORTE_IMAGEM)
            .textoNordeste(DEFAULT_TEXTO_NORDESTE)
            .textoNordesteImagem(DEFAULT_TEXTO_NORDESTE_IMAGEM)
            .textoSul(DEFAULT_TEXTO_SUL)
            .textoSulImagem(DEFAULT_TEXTO_SUL_IMAGEM)
            .textoSudeste(DEFAULT_TEXTO_SUDESTE)
            .textoSudesteImagem(DEFAULT_TEXTO_SUDESTE_IMAGEM)
            .textoCentroOeste(DEFAULT_TEXTO_CENTRO_OESTE)
            .textoCentroOesteImagem(DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM)
            .enviado(DEFAULT_ENVIADO)
            .textoBrasil(DEFAULT_TEXTO_BRASIL)
            .textoBrasilImagem(DEFAULT_TEXTO_BRASIL_IMAGEM)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return previsao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Previsao createUpdatedEntity(EntityManager em) {
        Previsao previsao = new Previsao()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .textoNorte(UPDATED_TEXTO_NORTE)
            .textoNorteImagem(UPDATED_TEXTO_NORTE_IMAGEM)
            .textoNordeste(UPDATED_TEXTO_NORDESTE)
            .textoNordesteImagem(UPDATED_TEXTO_NORDESTE_IMAGEM)
            .textoSul(UPDATED_TEXTO_SUL)
            .textoSulImagem(UPDATED_TEXTO_SUL_IMAGEM)
            .textoSudeste(UPDATED_TEXTO_SUDESTE)
            .textoSudesteImagem(UPDATED_TEXTO_SUDESTE_IMAGEM)
            .textoCentroOeste(UPDATED_TEXTO_CENTRO_OESTE)
            .textoCentroOesteImagem(UPDATED_TEXTO_CENTRO_OESTE_IMAGEM)
            .enviado(UPDATED_ENVIADO)
            .textoBrasil(UPDATED_TEXTO_BRASIL)
            .textoBrasilImagem(UPDATED_TEXTO_BRASIL_IMAGEM)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return previsao;
    }

    @BeforeEach
    public void initTest() {
        previsao = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrevisao() throws Exception {
        int databaseSizeBeforeCreate = previsaoRepository.findAll().size();
        // Create the Previsao
        PrevisaoDTO previsaoDTO = previsaoMapper.toDto(previsao);
        restPrevisaoMockMvc.perform(post("/api/previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(previsaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Previsao in the database
        List<Previsao> previsaoList = previsaoRepository.findAll();
        assertThat(previsaoList).hasSize(databaseSizeBeforeCreate + 1);
        Previsao testPrevisao = previsaoList.get(previsaoList.size() - 1);
        assertThat(testPrevisao.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPrevisao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPrevisao.getTextoNorte()).isEqualTo(DEFAULT_TEXTO_NORTE);
        assertThat(testPrevisao.getTextoNorteImagem()).isEqualTo(DEFAULT_TEXTO_NORTE_IMAGEM);
        assertThat(testPrevisao.getTextoNordeste()).isEqualTo(DEFAULT_TEXTO_NORDESTE);
        assertThat(testPrevisao.getTextoNordesteImagem()).isEqualTo(DEFAULT_TEXTO_NORDESTE_IMAGEM);
        assertThat(testPrevisao.getTextoSul()).isEqualTo(DEFAULT_TEXTO_SUL);
        assertThat(testPrevisao.getTextoSulImagem()).isEqualTo(DEFAULT_TEXTO_SUL_IMAGEM);
        assertThat(testPrevisao.getTextoSudeste()).isEqualTo(DEFAULT_TEXTO_SUDESTE);
        assertThat(testPrevisao.getTextoSudesteImagem()).isEqualTo(DEFAULT_TEXTO_SUDESTE_IMAGEM);
        assertThat(testPrevisao.getTextoCentroOeste()).isEqualTo(DEFAULT_TEXTO_CENTRO_OESTE);
        assertThat(testPrevisao.getTextoCentroOesteImagem()).isEqualTo(DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM);
        assertThat(testPrevisao.isEnviado()).isEqualTo(DEFAULT_ENVIADO);
        assertThat(testPrevisao.getTextoBrasil()).isEqualTo(DEFAULT_TEXTO_BRASIL);
        assertThat(testPrevisao.getTextoBrasilImagem()).isEqualTo(DEFAULT_TEXTO_BRASIL_IMAGEM);
        assertThat(testPrevisao.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPrevisao.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createPrevisaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = previsaoRepository.findAll().size();

        // Create the Previsao with an existing ID
        previsao.setId(1L);
        PrevisaoDTO previsaoDTO = previsaoMapper.toDto(previsao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrevisaoMockMvc.perform(post("/api/previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(previsaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Previsao in the database
        List<Previsao> previsaoList = previsaoRepository.findAll();
        assertThat(previsaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = previsaoRepository.findAll().size();
        // set the field null
        previsao.setName(null);

        // Create the Previsao, which fails.
        PrevisaoDTO previsaoDTO = previsaoMapper.toDto(previsao);


        restPrevisaoMockMvc.perform(post("/api/previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(previsaoDTO)))
            .andExpect(status().isBadRequest());

        List<Previsao> previsaoList = previsaoRepository.findAll();
        assertThat(previsaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = previsaoRepository.findAll().size();
        // set the field null
        previsao.setDescricao(null);

        // Create the Previsao, which fails.
        PrevisaoDTO previsaoDTO = previsaoMapper.toDto(previsao);


        restPrevisaoMockMvc.perform(post("/api/previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(previsaoDTO)))
            .andExpect(status().isBadRequest());

        List<Previsao> previsaoList = previsaoRepository.findAll();
        assertThat(previsaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = previsaoRepository.findAll().size();
        // set the field null
        previsao.setCreated(null);

        // Create the Previsao, which fails.
        PrevisaoDTO previsaoDTO = previsaoMapper.toDto(previsao);


        restPrevisaoMockMvc.perform(post("/api/previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(previsaoDTO)))
            .andExpect(status().isBadRequest());

        List<Previsao> previsaoList = previsaoRepository.findAll();
        assertThat(previsaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrevisaos() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList
        restPrevisaoMockMvc.perform(get("/api/previsaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(previsao.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].textoNorte").value(hasItem(DEFAULT_TEXTO_NORTE)))
            .andExpect(jsonPath("$.[*].textoNorteImagem").value(hasItem(DEFAULT_TEXTO_NORTE_IMAGEM)))
            .andExpect(jsonPath("$.[*].textoNordeste").value(hasItem(DEFAULT_TEXTO_NORDESTE)))
            .andExpect(jsonPath("$.[*].textoNordesteImagem").value(hasItem(DEFAULT_TEXTO_NORDESTE_IMAGEM)))
            .andExpect(jsonPath("$.[*].textoSul").value(hasItem(DEFAULT_TEXTO_SUL)))
            .andExpect(jsonPath("$.[*].textoSulImagem").value(hasItem(DEFAULT_TEXTO_SUL_IMAGEM)))
            .andExpect(jsonPath("$.[*].textoSudeste").value(hasItem(DEFAULT_TEXTO_SUDESTE)))
            .andExpect(jsonPath("$.[*].textoSudesteImagem").value(hasItem(DEFAULT_TEXTO_SUDESTE_IMAGEM)))
            .andExpect(jsonPath("$.[*].textoCentroOeste").value(hasItem(DEFAULT_TEXTO_CENTRO_OESTE)))
            .andExpect(jsonPath("$.[*].textoCentroOesteImagem").value(hasItem(DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM)))
            .andExpect(jsonPath("$.[*].enviado").value(hasItem(DEFAULT_ENVIADO.booleanValue())))
            .andExpect(jsonPath("$.[*].textoBrasil").value(hasItem(DEFAULT_TEXTO_BRASIL)))
            .andExpect(jsonPath("$.[*].textoBrasilImagem").value(hasItem(DEFAULT_TEXTO_BRASIL_IMAGEM)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getPrevisao() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get the previsao
        restPrevisaoMockMvc.perform(get("/api/previsaos/{id}", previsao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(previsao.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.textoNorte").value(DEFAULT_TEXTO_NORTE))
            .andExpect(jsonPath("$.textoNorteImagem").value(DEFAULT_TEXTO_NORTE_IMAGEM))
            .andExpect(jsonPath("$.textoNordeste").value(DEFAULT_TEXTO_NORDESTE))
            .andExpect(jsonPath("$.textoNordesteImagem").value(DEFAULT_TEXTO_NORDESTE_IMAGEM))
            .andExpect(jsonPath("$.textoSul").value(DEFAULT_TEXTO_SUL))
            .andExpect(jsonPath("$.textoSulImagem").value(DEFAULT_TEXTO_SUL_IMAGEM))
            .andExpect(jsonPath("$.textoSudeste").value(DEFAULT_TEXTO_SUDESTE))
            .andExpect(jsonPath("$.textoSudesteImagem").value(DEFAULT_TEXTO_SUDESTE_IMAGEM))
            .andExpect(jsonPath("$.textoCentroOeste").value(DEFAULT_TEXTO_CENTRO_OESTE))
            .andExpect(jsonPath("$.textoCentroOesteImagem").value(DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM))
            .andExpect(jsonPath("$.enviado").value(DEFAULT_ENVIADO.booleanValue()))
            .andExpect(jsonPath("$.textoBrasil").value(DEFAULT_TEXTO_BRASIL))
            .andExpect(jsonPath("$.textoBrasilImagem").value(DEFAULT_TEXTO_BRASIL_IMAGEM))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getPrevisaosByIdFiltering() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        Long id = previsao.getId();

        defaultPrevisaoShouldBeFound("id.equals=" + id);
        defaultPrevisaoShouldNotBeFound("id.notEquals=" + id);

        defaultPrevisaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPrevisaoShouldNotBeFound("id.greaterThan=" + id);

        defaultPrevisaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPrevisaoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPrevisaosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where name equals to DEFAULT_NAME
        defaultPrevisaoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the previsaoList where name equals to UPDATED_NAME
        defaultPrevisaoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where name not equals to DEFAULT_NAME
        defaultPrevisaoShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the previsaoList where name not equals to UPDATED_NAME
        defaultPrevisaoShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPrevisaoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the previsaoList where name equals to UPDATED_NAME
        defaultPrevisaoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where name is not null
        defaultPrevisaoShouldBeFound("name.specified=true");

        // Get all the previsaoList where name is null
        defaultPrevisaoShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPrevisaosByNameContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where name contains DEFAULT_NAME
        defaultPrevisaoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the previsaoList where name contains UPDATED_NAME
        defaultPrevisaoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where name does not contain DEFAULT_NAME
        defaultPrevisaoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the previsaoList where name does not contain UPDATED_NAME
        defaultPrevisaoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPrevisaosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where descricao equals to DEFAULT_DESCRICAO
        defaultPrevisaoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the previsaoList where descricao equals to UPDATED_DESCRICAO
        defaultPrevisaoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where descricao not equals to DEFAULT_DESCRICAO
        defaultPrevisaoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the previsaoList where descricao not equals to UPDATED_DESCRICAO
        defaultPrevisaoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultPrevisaoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the previsaoList where descricao equals to UPDATED_DESCRICAO
        defaultPrevisaoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where descricao is not null
        defaultPrevisaoShouldBeFound("descricao.specified=true");

        // Get all the previsaoList where descricao is null
        defaultPrevisaoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllPrevisaosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where descricao contains DEFAULT_DESCRICAO
        defaultPrevisaoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the previsaoList where descricao contains UPDATED_DESCRICAO
        defaultPrevisaoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where descricao does not contain DEFAULT_DESCRICAO
        defaultPrevisaoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the previsaoList where descricao does not contain UPDATED_DESCRICAO
        defaultPrevisaoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllPrevisaosByTextoNorteIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNorte equals to DEFAULT_TEXTO_NORTE
        defaultPrevisaoShouldBeFound("textoNorte.equals=" + DEFAULT_TEXTO_NORTE);

        // Get all the previsaoList where textoNorte equals to UPDATED_TEXTO_NORTE
        defaultPrevisaoShouldNotBeFound("textoNorte.equals=" + UPDATED_TEXTO_NORTE);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoNorteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNorte not equals to DEFAULT_TEXTO_NORTE
        defaultPrevisaoShouldNotBeFound("textoNorte.notEquals=" + DEFAULT_TEXTO_NORTE);

        // Get all the previsaoList where textoNorte not equals to UPDATED_TEXTO_NORTE
        defaultPrevisaoShouldBeFound("textoNorte.notEquals=" + UPDATED_TEXTO_NORTE);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoNorteIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNorte in DEFAULT_TEXTO_NORTE or UPDATED_TEXTO_NORTE
        defaultPrevisaoShouldBeFound("textoNorte.in=" + DEFAULT_TEXTO_NORTE + "," + UPDATED_TEXTO_NORTE);

        // Get all the previsaoList where textoNorte equals to UPDATED_TEXTO_NORTE
        defaultPrevisaoShouldNotBeFound("textoNorte.in=" + UPDATED_TEXTO_NORTE);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoNorteIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNorte is not null
        defaultPrevisaoShouldBeFound("textoNorte.specified=true");

        // Get all the previsaoList where textoNorte is null
        defaultPrevisaoShouldNotBeFound("textoNorte.specified=false");
    }
                @Test
    @Transactional
    public void getAllPrevisaosByTextoNorteContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNorte contains DEFAULT_TEXTO_NORTE
        defaultPrevisaoShouldBeFound("textoNorte.contains=" + DEFAULT_TEXTO_NORTE);

        // Get all the previsaoList where textoNorte contains UPDATED_TEXTO_NORTE
        defaultPrevisaoShouldNotBeFound("textoNorte.contains=" + UPDATED_TEXTO_NORTE);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoNorteNotContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNorte does not contain DEFAULT_TEXTO_NORTE
        defaultPrevisaoShouldNotBeFound("textoNorte.doesNotContain=" + DEFAULT_TEXTO_NORTE);

        // Get all the previsaoList where textoNorte does not contain UPDATED_TEXTO_NORTE
        defaultPrevisaoShouldBeFound("textoNorte.doesNotContain=" + UPDATED_TEXTO_NORTE);
    }


    @Test
    @Transactional
    public void getAllPrevisaosByTextoNorteImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNorteImagem equals to DEFAULT_TEXTO_NORTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoNorteImagem.equals=" + DEFAULT_TEXTO_NORTE_IMAGEM);

        // Get all the previsaoList where textoNorteImagem equals to UPDATED_TEXTO_NORTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoNorteImagem.equals=" + UPDATED_TEXTO_NORTE_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoNorteImagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNorteImagem not equals to DEFAULT_TEXTO_NORTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoNorteImagem.notEquals=" + DEFAULT_TEXTO_NORTE_IMAGEM);

        // Get all the previsaoList where textoNorteImagem not equals to UPDATED_TEXTO_NORTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoNorteImagem.notEquals=" + UPDATED_TEXTO_NORTE_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoNorteImagemIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNorteImagem in DEFAULT_TEXTO_NORTE_IMAGEM or UPDATED_TEXTO_NORTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoNorteImagem.in=" + DEFAULT_TEXTO_NORTE_IMAGEM + "," + UPDATED_TEXTO_NORTE_IMAGEM);

        // Get all the previsaoList where textoNorteImagem equals to UPDATED_TEXTO_NORTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoNorteImagem.in=" + UPDATED_TEXTO_NORTE_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoNorteImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNorteImagem is not null
        defaultPrevisaoShouldBeFound("textoNorteImagem.specified=true");

        // Get all the previsaoList where textoNorteImagem is null
        defaultPrevisaoShouldNotBeFound("textoNorteImagem.specified=false");
    }
                @Test
    @Transactional
    public void getAllPrevisaosByTextoNorteImagemContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNorteImagem contains DEFAULT_TEXTO_NORTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoNorteImagem.contains=" + DEFAULT_TEXTO_NORTE_IMAGEM);

        // Get all the previsaoList where textoNorteImagem contains UPDATED_TEXTO_NORTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoNorteImagem.contains=" + UPDATED_TEXTO_NORTE_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoNorteImagemNotContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNorteImagem does not contain DEFAULT_TEXTO_NORTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoNorteImagem.doesNotContain=" + DEFAULT_TEXTO_NORTE_IMAGEM);

        // Get all the previsaoList where textoNorteImagem does not contain UPDATED_TEXTO_NORTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoNorteImagem.doesNotContain=" + UPDATED_TEXTO_NORTE_IMAGEM);
    }


    @Test
    @Transactional
    public void getAllPrevisaosByTextoNordesteIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNordeste equals to DEFAULT_TEXTO_NORDESTE
        defaultPrevisaoShouldBeFound("textoNordeste.equals=" + DEFAULT_TEXTO_NORDESTE);

        // Get all the previsaoList where textoNordeste equals to UPDATED_TEXTO_NORDESTE
        defaultPrevisaoShouldNotBeFound("textoNordeste.equals=" + UPDATED_TEXTO_NORDESTE);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoNordesteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNordeste not equals to DEFAULT_TEXTO_NORDESTE
        defaultPrevisaoShouldNotBeFound("textoNordeste.notEquals=" + DEFAULT_TEXTO_NORDESTE);

        // Get all the previsaoList where textoNordeste not equals to UPDATED_TEXTO_NORDESTE
        defaultPrevisaoShouldBeFound("textoNordeste.notEquals=" + UPDATED_TEXTO_NORDESTE);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoNordesteIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNordeste in DEFAULT_TEXTO_NORDESTE or UPDATED_TEXTO_NORDESTE
        defaultPrevisaoShouldBeFound("textoNordeste.in=" + DEFAULT_TEXTO_NORDESTE + "," + UPDATED_TEXTO_NORDESTE);

        // Get all the previsaoList where textoNordeste equals to UPDATED_TEXTO_NORDESTE
        defaultPrevisaoShouldNotBeFound("textoNordeste.in=" + UPDATED_TEXTO_NORDESTE);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoNordesteIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNordeste is not null
        defaultPrevisaoShouldBeFound("textoNordeste.specified=true");

        // Get all the previsaoList where textoNordeste is null
        defaultPrevisaoShouldNotBeFound("textoNordeste.specified=false");
    }
                @Test
    @Transactional
    public void getAllPrevisaosByTextoNordesteContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNordeste contains DEFAULT_TEXTO_NORDESTE
        defaultPrevisaoShouldBeFound("textoNordeste.contains=" + DEFAULT_TEXTO_NORDESTE);

        // Get all the previsaoList where textoNordeste contains UPDATED_TEXTO_NORDESTE
        defaultPrevisaoShouldNotBeFound("textoNordeste.contains=" + UPDATED_TEXTO_NORDESTE);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoNordesteNotContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNordeste does not contain DEFAULT_TEXTO_NORDESTE
        defaultPrevisaoShouldNotBeFound("textoNordeste.doesNotContain=" + DEFAULT_TEXTO_NORDESTE);

        // Get all the previsaoList where textoNordeste does not contain UPDATED_TEXTO_NORDESTE
        defaultPrevisaoShouldBeFound("textoNordeste.doesNotContain=" + UPDATED_TEXTO_NORDESTE);
    }


    @Test
    @Transactional
    public void getAllPrevisaosByTextoNordesteImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNordesteImagem equals to DEFAULT_TEXTO_NORDESTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoNordesteImagem.equals=" + DEFAULT_TEXTO_NORDESTE_IMAGEM);

        // Get all the previsaoList where textoNordesteImagem equals to UPDATED_TEXTO_NORDESTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoNordesteImagem.equals=" + UPDATED_TEXTO_NORDESTE_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoNordesteImagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNordesteImagem not equals to DEFAULT_TEXTO_NORDESTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoNordesteImagem.notEquals=" + DEFAULT_TEXTO_NORDESTE_IMAGEM);

        // Get all the previsaoList where textoNordesteImagem not equals to UPDATED_TEXTO_NORDESTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoNordesteImagem.notEquals=" + UPDATED_TEXTO_NORDESTE_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoNordesteImagemIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNordesteImagem in DEFAULT_TEXTO_NORDESTE_IMAGEM or UPDATED_TEXTO_NORDESTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoNordesteImagem.in=" + DEFAULT_TEXTO_NORDESTE_IMAGEM + "," + UPDATED_TEXTO_NORDESTE_IMAGEM);

        // Get all the previsaoList where textoNordesteImagem equals to UPDATED_TEXTO_NORDESTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoNordesteImagem.in=" + UPDATED_TEXTO_NORDESTE_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoNordesteImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNordesteImagem is not null
        defaultPrevisaoShouldBeFound("textoNordesteImagem.specified=true");

        // Get all the previsaoList where textoNordesteImagem is null
        defaultPrevisaoShouldNotBeFound("textoNordesteImagem.specified=false");
    }
                @Test
    @Transactional
    public void getAllPrevisaosByTextoNordesteImagemContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNordesteImagem contains DEFAULT_TEXTO_NORDESTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoNordesteImagem.contains=" + DEFAULT_TEXTO_NORDESTE_IMAGEM);

        // Get all the previsaoList where textoNordesteImagem contains UPDATED_TEXTO_NORDESTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoNordesteImagem.contains=" + UPDATED_TEXTO_NORDESTE_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoNordesteImagemNotContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoNordesteImagem does not contain DEFAULT_TEXTO_NORDESTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoNordesteImagem.doesNotContain=" + DEFAULT_TEXTO_NORDESTE_IMAGEM);

        // Get all the previsaoList where textoNordesteImagem does not contain UPDATED_TEXTO_NORDESTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoNordesteImagem.doesNotContain=" + UPDATED_TEXTO_NORDESTE_IMAGEM);
    }


    @Test
    @Transactional
    public void getAllPrevisaosByTextoSulIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSul equals to DEFAULT_TEXTO_SUL
        defaultPrevisaoShouldBeFound("textoSul.equals=" + DEFAULT_TEXTO_SUL);

        // Get all the previsaoList where textoSul equals to UPDATED_TEXTO_SUL
        defaultPrevisaoShouldNotBeFound("textoSul.equals=" + UPDATED_TEXTO_SUL);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoSulIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSul not equals to DEFAULT_TEXTO_SUL
        defaultPrevisaoShouldNotBeFound("textoSul.notEquals=" + DEFAULT_TEXTO_SUL);

        // Get all the previsaoList where textoSul not equals to UPDATED_TEXTO_SUL
        defaultPrevisaoShouldBeFound("textoSul.notEquals=" + UPDATED_TEXTO_SUL);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoSulIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSul in DEFAULT_TEXTO_SUL or UPDATED_TEXTO_SUL
        defaultPrevisaoShouldBeFound("textoSul.in=" + DEFAULT_TEXTO_SUL + "," + UPDATED_TEXTO_SUL);

        // Get all the previsaoList where textoSul equals to UPDATED_TEXTO_SUL
        defaultPrevisaoShouldNotBeFound("textoSul.in=" + UPDATED_TEXTO_SUL);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoSulIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSul is not null
        defaultPrevisaoShouldBeFound("textoSul.specified=true");

        // Get all the previsaoList where textoSul is null
        defaultPrevisaoShouldNotBeFound("textoSul.specified=false");
    }
                @Test
    @Transactional
    public void getAllPrevisaosByTextoSulContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSul contains DEFAULT_TEXTO_SUL
        defaultPrevisaoShouldBeFound("textoSul.contains=" + DEFAULT_TEXTO_SUL);

        // Get all the previsaoList where textoSul contains UPDATED_TEXTO_SUL
        defaultPrevisaoShouldNotBeFound("textoSul.contains=" + UPDATED_TEXTO_SUL);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoSulNotContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSul does not contain DEFAULT_TEXTO_SUL
        defaultPrevisaoShouldNotBeFound("textoSul.doesNotContain=" + DEFAULT_TEXTO_SUL);

        // Get all the previsaoList where textoSul does not contain UPDATED_TEXTO_SUL
        defaultPrevisaoShouldBeFound("textoSul.doesNotContain=" + UPDATED_TEXTO_SUL);
    }


    @Test
    @Transactional
    public void getAllPrevisaosByTextoSulImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSulImagem equals to DEFAULT_TEXTO_SUL_IMAGEM
        defaultPrevisaoShouldBeFound("textoSulImagem.equals=" + DEFAULT_TEXTO_SUL_IMAGEM);

        // Get all the previsaoList where textoSulImagem equals to UPDATED_TEXTO_SUL_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoSulImagem.equals=" + UPDATED_TEXTO_SUL_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoSulImagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSulImagem not equals to DEFAULT_TEXTO_SUL_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoSulImagem.notEquals=" + DEFAULT_TEXTO_SUL_IMAGEM);

        // Get all the previsaoList where textoSulImagem not equals to UPDATED_TEXTO_SUL_IMAGEM
        defaultPrevisaoShouldBeFound("textoSulImagem.notEquals=" + UPDATED_TEXTO_SUL_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoSulImagemIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSulImagem in DEFAULT_TEXTO_SUL_IMAGEM or UPDATED_TEXTO_SUL_IMAGEM
        defaultPrevisaoShouldBeFound("textoSulImagem.in=" + DEFAULT_TEXTO_SUL_IMAGEM + "," + UPDATED_TEXTO_SUL_IMAGEM);

        // Get all the previsaoList where textoSulImagem equals to UPDATED_TEXTO_SUL_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoSulImagem.in=" + UPDATED_TEXTO_SUL_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoSulImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSulImagem is not null
        defaultPrevisaoShouldBeFound("textoSulImagem.specified=true");

        // Get all the previsaoList where textoSulImagem is null
        defaultPrevisaoShouldNotBeFound("textoSulImagem.specified=false");
    }
                @Test
    @Transactional
    public void getAllPrevisaosByTextoSulImagemContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSulImagem contains DEFAULT_TEXTO_SUL_IMAGEM
        defaultPrevisaoShouldBeFound("textoSulImagem.contains=" + DEFAULT_TEXTO_SUL_IMAGEM);

        // Get all the previsaoList where textoSulImagem contains UPDATED_TEXTO_SUL_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoSulImagem.contains=" + UPDATED_TEXTO_SUL_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoSulImagemNotContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSulImagem does not contain DEFAULT_TEXTO_SUL_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoSulImagem.doesNotContain=" + DEFAULT_TEXTO_SUL_IMAGEM);

        // Get all the previsaoList where textoSulImagem does not contain UPDATED_TEXTO_SUL_IMAGEM
        defaultPrevisaoShouldBeFound("textoSulImagem.doesNotContain=" + UPDATED_TEXTO_SUL_IMAGEM);
    }


    @Test
    @Transactional
    public void getAllPrevisaosByTextoSudesteIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSudeste equals to DEFAULT_TEXTO_SUDESTE
        defaultPrevisaoShouldBeFound("textoSudeste.equals=" + DEFAULT_TEXTO_SUDESTE);

        // Get all the previsaoList where textoSudeste equals to UPDATED_TEXTO_SUDESTE
        defaultPrevisaoShouldNotBeFound("textoSudeste.equals=" + UPDATED_TEXTO_SUDESTE);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoSudesteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSudeste not equals to DEFAULT_TEXTO_SUDESTE
        defaultPrevisaoShouldNotBeFound("textoSudeste.notEquals=" + DEFAULT_TEXTO_SUDESTE);

        // Get all the previsaoList where textoSudeste not equals to UPDATED_TEXTO_SUDESTE
        defaultPrevisaoShouldBeFound("textoSudeste.notEquals=" + UPDATED_TEXTO_SUDESTE);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoSudesteIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSudeste in DEFAULT_TEXTO_SUDESTE or UPDATED_TEXTO_SUDESTE
        defaultPrevisaoShouldBeFound("textoSudeste.in=" + DEFAULT_TEXTO_SUDESTE + "," + UPDATED_TEXTO_SUDESTE);

        // Get all the previsaoList where textoSudeste equals to UPDATED_TEXTO_SUDESTE
        defaultPrevisaoShouldNotBeFound("textoSudeste.in=" + UPDATED_TEXTO_SUDESTE);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoSudesteIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSudeste is not null
        defaultPrevisaoShouldBeFound("textoSudeste.specified=true");

        // Get all the previsaoList where textoSudeste is null
        defaultPrevisaoShouldNotBeFound("textoSudeste.specified=false");
    }
                @Test
    @Transactional
    public void getAllPrevisaosByTextoSudesteContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSudeste contains DEFAULT_TEXTO_SUDESTE
        defaultPrevisaoShouldBeFound("textoSudeste.contains=" + DEFAULT_TEXTO_SUDESTE);

        // Get all the previsaoList where textoSudeste contains UPDATED_TEXTO_SUDESTE
        defaultPrevisaoShouldNotBeFound("textoSudeste.contains=" + UPDATED_TEXTO_SUDESTE);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoSudesteNotContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSudeste does not contain DEFAULT_TEXTO_SUDESTE
        defaultPrevisaoShouldNotBeFound("textoSudeste.doesNotContain=" + DEFAULT_TEXTO_SUDESTE);

        // Get all the previsaoList where textoSudeste does not contain UPDATED_TEXTO_SUDESTE
        defaultPrevisaoShouldBeFound("textoSudeste.doesNotContain=" + UPDATED_TEXTO_SUDESTE);
    }


    @Test
    @Transactional
    public void getAllPrevisaosByTextoSudesteImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSudesteImagem equals to DEFAULT_TEXTO_SUDESTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoSudesteImagem.equals=" + DEFAULT_TEXTO_SUDESTE_IMAGEM);

        // Get all the previsaoList where textoSudesteImagem equals to UPDATED_TEXTO_SUDESTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoSudesteImagem.equals=" + UPDATED_TEXTO_SUDESTE_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoSudesteImagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSudesteImagem not equals to DEFAULT_TEXTO_SUDESTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoSudesteImagem.notEquals=" + DEFAULT_TEXTO_SUDESTE_IMAGEM);

        // Get all the previsaoList where textoSudesteImagem not equals to UPDATED_TEXTO_SUDESTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoSudesteImagem.notEquals=" + UPDATED_TEXTO_SUDESTE_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoSudesteImagemIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSudesteImagem in DEFAULT_TEXTO_SUDESTE_IMAGEM or UPDATED_TEXTO_SUDESTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoSudesteImagem.in=" + DEFAULT_TEXTO_SUDESTE_IMAGEM + "," + UPDATED_TEXTO_SUDESTE_IMAGEM);

        // Get all the previsaoList where textoSudesteImagem equals to UPDATED_TEXTO_SUDESTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoSudesteImagem.in=" + UPDATED_TEXTO_SUDESTE_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoSudesteImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSudesteImagem is not null
        defaultPrevisaoShouldBeFound("textoSudesteImagem.specified=true");

        // Get all the previsaoList where textoSudesteImagem is null
        defaultPrevisaoShouldNotBeFound("textoSudesteImagem.specified=false");
    }
                @Test
    @Transactional
    public void getAllPrevisaosByTextoSudesteImagemContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSudesteImagem contains DEFAULT_TEXTO_SUDESTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoSudesteImagem.contains=" + DEFAULT_TEXTO_SUDESTE_IMAGEM);

        // Get all the previsaoList where textoSudesteImagem contains UPDATED_TEXTO_SUDESTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoSudesteImagem.contains=" + UPDATED_TEXTO_SUDESTE_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoSudesteImagemNotContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoSudesteImagem does not contain DEFAULT_TEXTO_SUDESTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoSudesteImagem.doesNotContain=" + DEFAULT_TEXTO_SUDESTE_IMAGEM);

        // Get all the previsaoList where textoSudesteImagem does not contain UPDATED_TEXTO_SUDESTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoSudesteImagem.doesNotContain=" + UPDATED_TEXTO_SUDESTE_IMAGEM);
    }


    @Test
    @Transactional
    public void getAllPrevisaosByTextoCentroOesteIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoCentroOeste equals to DEFAULT_TEXTO_CENTRO_OESTE
        defaultPrevisaoShouldBeFound("textoCentroOeste.equals=" + DEFAULT_TEXTO_CENTRO_OESTE);

        // Get all the previsaoList where textoCentroOeste equals to UPDATED_TEXTO_CENTRO_OESTE
        defaultPrevisaoShouldNotBeFound("textoCentroOeste.equals=" + UPDATED_TEXTO_CENTRO_OESTE);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoCentroOesteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoCentroOeste not equals to DEFAULT_TEXTO_CENTRO_OESTE
        defaultPrevisaoShouldNotBeFound("textoCentroOeste.notEquals=" + DEFAULT_TEXTO_CENTRO_OESTE);

        // Get all the previsaoList where textoCentroOeste not equals to UPDATED_TEXTO_CENTRO_OESTE
        defaultPrevisaoShouldBeFound("textoCentroOeste.notEquals=" + UPDATED_TEXTO_CENTRO_OESTE);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoCentroOesteIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoCentroOeste in DEFAULT_TEXTO_CENTRO_OESTE or UPDATED_TEXTO_CENTRO_OESTE
        defaultPrevisaoShouldBeFound("textoCentroOeste.in=" + DEFAULT_TEXTO_CENTRO_OESTE + "," + UPDATED_TEXTO_CENTRO_OESTE);

        // Get all the previsaoList where textoCentroOeste equals to UPDATED_TEXTO_CENTRO_OESTE
        defaultPrevisaoShouldNotBeFound("textoCentroOeste.in=" + UPDATED_TEXTO_CENTRO_OESTE);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoCentroOesteIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoCentroOeste is not null
        defaultPrevisaoShouldBeFound("textoCentroOeste.specified=true");

        // Get all the previsaoList where textoCentroOeste is null
        defaultPrevisaoShouldNotBeFound("textoCentroOeste.specified=false");
    }
                @Test
    @Transactional
    public void getAllPrevisaosByTextoCentroOesteContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoCentroOeste contains DEFAULT_TEXTO_CENTRO_OESTE
        defaultPrevisaoShouldBeFound("textoCentroOeste.contains=" + DEFAULT_TEXTO_CENTRO_OESTE);

        // Get all the previsaoList where textoCentroOeste contains UPDATED_TEXTO_CENTRO_OESTE
        defaultPrevisaoShouldNotBeFound("textoCentroOeste.contains=" + UPDATED_TEXTO_CENTRO_OESTE);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoCentroOesteNotContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoCentroOeste does not contain DEFAULT_TEXTO_CENTRO_OESTE
        defaultPrevisaoShouldNotBeFound("textoCentroOeste.doesNotContain=" + DEFAULT_TEXTO_CENTRO_OESTE);

        // Get all the previsaoList where textoCentroOeste does not contain UPDATED_TEXTO_CENTRO_OESTE
        defaultPrevisaoShouldBeFound("textoCentroOeste.doesNotContain=" + UPDATED_TEXTO_CENTRO_OESTE);
    }


    @Test
    @Transactional
    public void getAllPrevisaosByTextoCentroOesteImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoCentroOesteImagem equals to DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoCentroOesteImagem.equals=" + DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM);

        // Get all the previsaoList where textoCentroOesteImagem equals to UPDATED_TEXTO_CENTRO_OESTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoCentroOesteImagem.equals=" + UPDATED_TEXTO_CENTRO_OESTE_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoCentroOesteImagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoCentroOesteImagem not equals to DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoCentroOesteImagem.notEquals=" + DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM);

        // Get all the previsaoList where textoCentroOesteImagem not equals to UPDATED_TEXTO_CENTRO_OESTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoCentroOesteImagem.notEquals=" + UPDATED_TEXTO_CENTRO_OESTE_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoCentroOesteImagemIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoCentroOesteImagem in DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM or UPDATED_TEXTO_CENTRO_OESTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoCentroOesteImagem.in=" + DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM + "," + UPDATED_TEXTO_CENTRO_OESTE_IMAGEM);

        // Get all the previsaoList where textoCentroOesteImagem equals to UPDATED_TEXTO_CENTRO_OESTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoCentroOesteImagem.in=" + UPDATED_TEXTO_CENTRO_OESTE_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoCentroOesteImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoCentroOesteImagem is not null
        defaultPrevisaoShouldBeFound("textoCentroOesteImagem.specified=true");

        // Get all the previsaoList where textoCentroOesteImagem is null
        defaultPrevisaoShouldNotBeFound("textoCentroOesteImagem.specified=false");
    }
                @Test
    @Transactional
    public void getAllPrevisaosByTextoCentroOesteImagemContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoCentroOesteImagem contains DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoCentroOesteImagem.contains=" + DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM);

        // Get all the previsaoList where textoCentroOesteImagem contains UPDATED_TEXTO_CENTRO_OESTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoCentroOesteImagem.contains=" + UPDATED_TEXTO_CENTRO_OESTE_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoCentroOesteImagemNotContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoCentroOesteImagem does not contain DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoCentroOesteImagem.doesNotContain=" + DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM);

        // Get all the previsaoList where textoCentroOesteImagem does not contain UPDATED_TEXTO_CENTRO_OESTE_IMAGEM
        defaultPrevisaoShouldBeFound("textoCentroOesteImagem.doesNotContain=" + UPDATED_TEXTO_CENTRO_OESTE_IMAGEM);
    }


    @Test
    @Transactional
    public void getAllPrevisaosByEnviadoIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where enviado equals to DEFAULT_ENVIADO
        defaultPrevisaoShouldBeFound("enviado.equals=" + DEFAULT_ENVIADO);

        // Get all the previsaoList where enviado equals to UPDATED_ENVIADO
        defaultPrevisaoShouldNotBeFound("enviado.equals=" + UPDATED_ENVIADO);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByEnviadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where enviado not equals to DEFAULT_ENVIADO
        defaultPrevisaoShouldNotBeFound("enviado.notEquals=" + DEFAULT_ENVIADO);

        // Get all the previsaoList where enviado not equals to UPDATED_ENVIADO
        defaultPrevisaoShouldBeFound("enviado.notEquals=" + UPDATED_ENVIADO);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByEnviadoIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where enviado in DEFAULT_ENVIADO or UPDATED_ENVIADO
        defaultPrevisaoShouldBeFound("enviado.in=" + DEFAULT_ENVIADO + "," + UPDATED_ENVIADO);

        // Get all the previsaoList where enviado equals to UPDATED_ENVIADO
        defaultPrevisaoShouldNotBeFound("enviado.in=" + UPDATED_ENVIADO);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByEnviadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where enviado is not null
        defaultPrevisaoShouldBeFound("enviado.specified=true");

        // Get all the previsaoList where enviado is null
        defaultPrevisaoShouldNotBeFound("enviado.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoBrasilIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoBrasil equals to DEFAULT_TEXTO_BRASIL
        defaultPrevisaoShouldBeFound("textoBrasil.equals=" + DEFAULT_TEXTO_BRASIL);

        // Get all the previsaoList where textoBrasil equals to UPDATED_TEXTO_BRASIL
        defaultPrevisaoShouldNotBeFound("textoBrasil.equals=" + UPDATED_TEXTO_BRASIL);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoBrasilIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoBrasil not equals to DEFAULT_TEXTO_BRASIL
        defaultPrevisaoShouldNotBeFound("textoBrasil.notEquals=" + DEFAULT_TEXTO_BRASIL);

        // Get all the previsaoList where textoBrasil not equals to UPDATED_TEXTO_BRASIL
        defaultPrevisaoShouldBeFound("textoBrasil.notEquals=" + UPDATED_TEXTO_BRASIL);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoBrasilIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoBrasil in DEFAULT_TEXTO_BRASIL or UPDATED_TEXTO_BRASIL
        defaultPrevisaoShouldBeFound("textoBrasil.in=" + DEFAULT_TEXTO_BRASIL + "," + UPDATED_TEXTO_BRASIL);

        // Get all the previsaoList where textoBrasil equals to UPDATED_TEXTO_BRASIL
        defaultPrevisaoShouldNotBeFound("textoBrasil.in=" + UPDATED_TEXTO_BRASIL);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoBrasilIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoBrasil is not null
        defaultPrevisaoShouldBeFound("textoBrasil.specified=true");

        // Get all the previsaoList where textoBrasil is null
        defaultPrevisaoShouldNotBeFound("textoBrasil.specified=false");
    }
                @Test
    @Transactional
    public void getAllPrevisaosByTextoBrasilContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoBrasil contains DEFAULT_TEXTO_BRASIL
        defaultPrevisaoShouldBeFound("textoBrasil.contains=" + DEFAULT_TEXTO_BRASIL);

        // Get all the previsaoList where textoBrasil contains UPDATED_TEXTO_BRASIL
        defaultPrevisaoShouldNotBeFound("textoBrasil.contains=" + UPDATED_TEXTO_BRASIL);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoBrasilNotContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoBrasil does not contain DEFAULT_TEXTO_BRASIL
        defaultPrevisaoShouldNotBeFound("textoBrasil.doesNotContain=" + DEFAULT_TEXTO_BRASIL);

        // Get all the previsaoList where textoBrasil does not contain UPDATED_TEXTO_BRASIL
        defaultPrevisaoShouldBeFound("textoBrasil.doesNotContain=" + UPDATED_TEXTO_BRASIL);
    }


    @Test
    @Transactional
    public void getAllPrevisaosByTextoBrasilImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoBrasilImagem equals to DEFAULT_TEXTO_BRASIL_IMAGEM
        defaultPrevisaoShouldBeFound("textoBrasilImagem.equals=" + DEFAULT_TEXTO_BRASIL_IMAGEM);

        // Get all the previsaoList where textoBrasilImagem equals to UPDATED_TEXTO_BRASIL_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoBrasilImagem.equals=" + UPDATED_TEXTO_BRASIL_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoBrasilImagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoBrasilImagem not equals to DEFAULT_TEXTO_BRASIL_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoBrasilImagem.notEquals=" + DEFAULT_TEXTO_BRASIL_IMAGEM);

        // Get all the previsaoList where textoBrasilImagem not equals to UPDATED_TEXTO_BRASIL_IMAGEM
        defaultPrevisaoShouldBeFound("textoBrasilImagem.notEquals=" + UPDATED_TEXTO_BRASIL_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoBrasilImagemIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoBrasilImagem in DEFAULT_TEXTO_BRASIL_IMAGEM or UPDATED_TEXTO_BRASIL_IMAGEM
        defaultPrevisaoShouldBeFound("textoBrasilImagem.in=" + DEFAULT_TEXTO_BRASIL_IMAGEM + "," + UPDATED_TEXTO_BRASIL_IMAGEM);

        // Get all the previsaoList where textoBrasilImagem equals to UPDATED_TEXTO_BRASIL_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoBrasilImagem.in=" + UPDATED_TEXTO_BRASIL_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoBrasilImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoBrasilImagem is not null
        defaultPrevisaoShouldBeFound("textoBrasilImagem.specified=true");

        // Get all the previsaoList where textoBrasilImagem is null
        defaultPrevisaoShouldNotBeFound("textoBrasilImagem.specified=false");
    }
                @Test
    @Transactional
    public void getAllPrevisaosByTextoBrasilImagemContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoBrasilImagem contains DEFAULT_TEXTO_BRASIL_IMAGEM
        defaultPrevisaoShouldBeFound("textoBrasilImagem.contains=" + DEFAULT_TEXTO_BRASIL_IMAGEM);

        // Get all the previsaoList where textoBrasilImagem contains UPDATED_TEXTO_BRASIL_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoBrasilImagem.contains=" + UPDATED_TEXTO_BRASIL_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByTextoBrasilImagemNotContainsSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where textoBrasilImagem does not contain DEFAULT_TEXTO_BRASIL_IMAGEM
        defaultPrevisaoShouldNotBeFound("textoBrasilImagem.doesNotContain=" + DEFAULT_TEXTO_BRASIL_IMAGEM);

        // Get all the previsaoList where textoBrasilImagem does not contain UPDATED_TEXTO_BRASIL_IMAGEM
        defaultPrevisaoShouldBeFound("textoBrasilImagem.doesNotContain=" + UPDATED_TEXTO_BRASIL_IMAGEM);
    }


    @Test
    @Transactional
    public void getAllPrevisaosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where created equals to DEFAULT_CREATED
        defaultPrevisaoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the previsaoList where created equals to UPDATED_CREATED
        defaultPrevisaoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where created not equals to DEFAULT_CREATED
        defaultPrevisaoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the previsaoList where created not equals to UPDATED_CREATED
        defaultPrevisaoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultPrevisaoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the previsaoList where created equals to UPDATED_CREATED
        defaultPrevisaoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where created is not null
        defaultPrevisaoShouldBeFound("created.specified=true");

        // Get all the previsaoList where created is null
        defaultPrevisaoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrevisaosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where updated equals to DEFAULT_UPDATED
        defaultPrevisaoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the previsaoList where updated equals to UPDATED_UPDATED
        defaultPrevisaoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where updated not equals to DEFAULT_UPDATED
        defaultPrevisaoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the previsaoList where updated not equals to UPDATED_UPDATED
        defaultPrevisaoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultPrevisaoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the previsaoList where updated equals to UPDATED_UPDATED
        defaultPrevisaoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPrevisaosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList where updated is not null
        defaultPrevisaoShouldBeFound("updated.specified=true");

        // Get all the previsaoList where updated is null
        defaultPrevisaoShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPrevisaoShouldBeFound(String filter) throws Exception {
        restPrevisaoMockMvc.perform(get("/api/previsaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(previsao.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].textoNorte").value(hasItem(DEFAULT_TEXTO_NORTE)))
            .andExpect(jsonPath("$.[*].textoNorteImagem").value(hasItem(DEFAULT_TEXTO_NORTE_IMAGEM)))
            .andExpect(jsonPath("$.[*].textoNordeste").value(hasItem(DEFAULT_TEXTO_NORDESTE)))
            .andExpect(jsonPath("$.[*].textoNordesteImagem").value(hasItem(DEFAULT_TEXTO_NORDESTE_IMAGEM)))
            .andExpect(jsonPath("$.[*].textoSul").value(hasItem(DEFAULT_TEXTO_SUL)))
            .andExpect(jsonPath("$.[*].textoSulImagem").value(hasItem(DEFAULT_TEXTO_SUL_IMAGEM)))
            .andExpect(jsonPath("$.[*].textoSudeste").value(hasItem(DEFAULT_TEXTO_SUDESTE)))
            .andExpect(jsonPath("$.[*].textoSudesteImagem").value(hasItem(DEFAULT_TEXTO_SUDESTE_IMAGEM)))
            .andExpect(jsonPath("$.[*].textoCentroOeste").value(hasItem(DEFAULT_TEXTO_CENTRO_OESTE)))
            .andExpect(jsonPath("$.[*].textoCentroOesteImagem").value(hasItem(DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM)))
            .andExpect(jsonPath("$.[*].enviado").value(hasItem(DEFAULT_ENVIADO.booleanValue())))
            .andExpect(jsonPath("$.[*].textoBrasil").value(hasItem(DEFAULT_TEXTO_BRASIL)))
            .andExpect(jsonPath("$.[*].textoBrasilImagem").value(hasItem(DEFAULT_TEXTO_BRASIL_IMAGEM)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restPrevisaoMockMvc.perform(get("/api/previsaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPrevisaoShouldNotBeFound(String filter) throws Exception {
        restPrevisaoMockMvc.perform(get("/api/previsaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPrevisaoMockMvc.perform(get("/api/previsaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPrevisao() throws Exception {
        // Get the previsao
        restPrevisaoMockMvc.perform(get("/api/previsaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrevisao() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        int databaseSizeBeforeUpdate = previsaoRepository.findAll().size();

        // Update the previsao
        Previsao updatedPrevisao = previsaoRepository.findById(previsao.getId()).get();
        // Disconnect from session so that the updates on updatedPrevisao are not directly saved in db
        em.detach(updatedPrevisao);
        updatedPrevisao
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .textoNorte(UPDATED_TEXTO_NORTE)
            .textoNorteImagem(UPDATED_TEXTO_NORTE_IMAGEM)
            .textoNordeste(UPDATED_TEXTO_NORDESTE)
            .textoNordesteImagem(UPDATED_TEXTO_NORDESTE_IMAGEM)
            .textoSul(UPDATED_TEXTO_SUL)
            .textoSulImagem(UPDATED_TEXTO_SUL_IMAGEM)
            .textoSudeste(UPDATED_TEXTO_SUDESTE)
            .textoSudesteImagem(UPDATED_TEXTO_SUDESTE_IMAGEM)
            .textoCentroOeste(UPDATED_TEXTO_CENTRO_OESTE)
            .textoCentroOesteImagem(UPDATED_TEXTO_CENTRO_OESTE_IMAGEM)
            .enviado(UPDATED_ENVIADO)
            .textoBrasil(UPDATED_TEXTO_BRASIL)
            .textoBrasilImagem(UPDATED_TEXTO_BRASIL_IMAGEM)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        PrevisaoDTO previsaoDTO = previsaoMapper.toDto(updatedPrevisao);

        restPrevisaoMockMvc.perform(put("/api/previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(previsaoDTO)))
            .andExpect(status().isOk());

        // Validate the Previsao in the database
        List<Previsao> previsaoList = previsaoRepository.findAll();
        assertThat(previsaoList).hasSize(databaseSizeBeforeUpdate);
        Previsao testPrevisao = previsaoList.get(previsaoList.size() - 1);
        assertThat(testPrevisao.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPrevisao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPrevisao.getTextoNorte()).isEqualTo(UPDATED_TEXTO_NORTE);
        assertThat(testPrevisao.getTextoNorteImagem()).isEqualTo(UPDATED_TEXTO_NORTE_IMAGEM);
        assertThat(testPrevisao.getTextoNordeste()).isEqualTo(UPDATED_TEXTO_NORDESTE);
        assertThat(testPrevisao.getTextoNordesteImagem()).isEqualTo(UPDATED_TEXTO_NORDESTE_IMAGEM);
        assertThat(testPrevisao.getTextoSul()).isEqualTo(UPDATED_TEXTO_SUL);
        assertThat(testPrevisao.getTextoSulImagem()).isEqualTo(UPDATED_TEXTO_SUL_IMAGEM);
        assertThat(testPrevisao.getTextoSudeste()).isEqualTo(UPDATED_TEXTO_SUDESTE);
        assertThat(testPrevisao.getTextoSudesteImagem()).isEqualTo(UPDATED_TEXTO_SUDESTE_IMAGEM);
        assertThat(testPrevisao.getTextoCentroOeste()).isEqualTo(UPDATED_TEXTO_CENTRO_OESTE);
        assertThat(testPrevisao.getTextoCentroOesteImagem()).isEqualTo(UPDATED_TEXTO_CENTRO_OESTE_IMAGEM);
        assertThat(testPrevisao.isEnviado()).isEqualTo(UPDATED_ENVIADO);
        assertThat(testPrevisao.getTextoBrasil()).isEqualTo(UPDATED_TEXTO_BRASIL);
        assertThat(testPrevisao.getTextoBrasilImagem()).isEqualTo(UPDATED_TEXTO_BRASIL_IMAGEM);
        assertThat(testPrevisao.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPrevisao.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPrevisao() throws Exception {
        int databaseSizeBeforeUpdate = previsaoRepository.findAll().size();

        // Create the Previsao
        PrevisaoDTO previsaoDTO = previsaoMapper.toDto(previsao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrevisaoMockMvc.perform(put("/api/previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(previsaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Previsao in the database
        List<Previsao> previsaoList = previsaoRepository.findAll();
        assertThat(previsaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrevisao() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        int databaseSizeBeforeDelete = previsaoRepository.findAll().size();

        // Delete the previsao
        restPrevisaoMockMvc.perform(delete("/api/previsaos/{id}", previsao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Previsao> previsaoList = previsaoRepository.findAll();
        assertThat(previsaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
