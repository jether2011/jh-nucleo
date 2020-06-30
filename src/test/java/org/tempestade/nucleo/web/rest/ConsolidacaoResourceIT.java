package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Consolidacao;
import org.tempestade.nucleo.domain.PlanoRecurso;
import org.tempestade.nucleo.repository.ConsolidacaoRepository;
import org.tempestade.nucleo.service.ConsolidacaoService;
import org.tempestade.nucleo.service.dto.ConsolidacaoDTO;
import org.tempestade.nucleo.service.mapper.ConsolidacaoMapper;
import org.tempestade.nucleo.service.dto.ConsolidacaoCriteria;
import org.tempestade.nucleo.service.ConsolidacaoQueryService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ConsolidacaoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConsolidacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_QTD_EMAIL = 1;
    private static final Integer UPDATED_QTD_EMAIL = 2;
    private static final Integer SMALLER_QTD_EMAIL = 1 - 1;

    private static final String DEFAULT_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_ARQUIVO_EML = "AAAAAAAAAA";
    private static final String UPDATED_ARQUIVO_EML = "BBBBBBBBBB";

    private static final String DEFAULT_ASSUNTO = "AAAAAAAAAA";
    private static final String UPDATED_ASSUNTO = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_ASSUNTO = "AAAAAAAAAA";
    private static final String UPDATED_SUB_ASSUNTO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ConsolidacaoRepository consolidacaoRepository;

    @Autowired
    private ConsolidacaoMapper consolidacaoMapper;

    @Autowired
    private ConsolidacaoService consolidacaoService;

    @Autowired
    private ConsolidacaoQueryService consolidacaoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConsolidacaoMockMvc;

    private Consolidacao consolidacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consolidacao createEntity(EntityManager em) {
        Consolidacao consolidacao = new Consolidacao()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .data(DEFAULT_DATA)
            .texto(DEFAULT_TEXTO)
            .qtdEmail(DEFAULT_QTD_EMAIL)
            .imagem(DEFAULT_IMAGEM)
            .arquivoEml(DEFAULT_ARQUIVO_EML)
            .assunto(DEFAULT_ASSUNTO)
            .subAssunto(DEFAULT_SUB_ASSUNTO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return consolidacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consolidacao createUpdatedEntity(EntityManager em) {
        Consolidacao consolidacao = new Consolidacao()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .data(UPDATED_DATA)
            .texto(UPDATED_TEXTO)
            .qtdEmail(UPDATED_QTD_EMAIL)
            .imagem(UPDATED_IMAGEM)
            .arquivoEml(UPDATED_ARQUIVO_EML)
            .assunto(UPDATED_ASSUNTO)
            .subAssunto(UPDATED_SUB_ASSUNTO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return consolidacao;
    }

    @BeforeEach
    public void initTest() {
        consolidacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsolidacao() throws Exception {
        int databaseSizeBeforeCreate = consolidacaoRepository.findAll().size();
        // Create the Consolidacao
        ConsolidacaoDTO consolidacaoDTO = consolidacaoMapper.toDto(consolidacao);
        restConsolidacaoMockMvc.perform(post("/api/consolidacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consolidacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Consolidacao in the database
        List<Consolidacao> consolidacaoList = consolidacaoRepository.findAll();
        assertThat(consolidacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Consolidacao testConsolidacao = consolidacaoList.get(consolidacaoList.size() - 1);
        assertThat(testConsolidacao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testConsolidacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testConsolidacao.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testConsolidacao.getTexto()).isEqualTo(DEFAULT_TEXTO);
        assertThat(testConsolidacao.getQtdEmail()).isEqualTo(DEFAULT_QTD_EMAIL);
        assertThat(testConsolidacao.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testConsolidacao.getArquivoEml()).isEqualTo(DEFAULT_ARQUIVO_EML);
        assertThat(testConsolidacao.getAssunto()).isEqualTo(DEFAULT_ASSUNTO);
        assertThat(testConsolidacao.getSubAssunto()).isEqualTo(DEFAULT_SUB_ASSUNTO);
        assertThat(testConsolidacao.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testConsolidacao.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createConsolidacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consolidacaoRepository.findAll().size();

        // Create the Consolidacao with an existing ID
        consolidacao.setId(1L);
        ConsolidacaoDTO consolidacaoDTO = consolidacaoMapper.toDto(consolidacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsolidacaoMockMvc.perform(post("/api/consolidacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consolidacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consolidacao in the database
        List<Consolidacao> consolidacaoList = consolidacaoRepository.findAll();
        assertThat(consolidacaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = consolidacaoRepository.findAll().size();
        // set the field null
        consolidacao.setNome(null);

        // Create the Consolidacao, which fails.
        ConsolidacaoDTO consolidacaoDTO = consolidacaoMapper.toDto(consolidacao);


        restConsolidacaoMockMvc.perform(post("/api/consolidacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consolidacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Consolidacao> consolidacaoList = consolidacaoRepository.findAll();
        assertThat(consolidacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = consolidacaoRepository.findAll().size();
        // set the field null
        consolidacao.setData(null);

        // Create the Consolidacao, which fails.
        ConsolidacaoDTO consolidacaoDTO = consolidacaoMapper.toDto(consolidacao);


        restConsolidacaoMockMvc.perform(post("/api/consolidacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consolidacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Consolidacao> consolidacaoList = consolidacaoRepository.findAll();
        assertThat(consolidacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = consolidacaoRepository.findAll().size();
        // set the field null
        consolidacao.setCreated(null);

        // Create the Consolidacao, which fails.
        ConsolidacaoDTO consolidacaoDTO = consolidacaoMapper.toDto(consolidacao);


        restConsolidacaoMockMvc.perform(post("/api/consolidacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consolidacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Consolidacao> consolidacaoList = consolidacaoRepository.findAll();
        assertThat(consolidacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsolidacaos() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList
        restConsolidacaoMockMvc.perform(get("/api/consolidacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consolidacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO)))
            .andExpect(jsonPath("$.[*].qtdEmail").value(hasItem(DEFAULT_QTD_EMAIL)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.[*].arquivoEml").value(hasItem(DEFAULT_ARQUIVO_EML)))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO)))
            .andExpect(jsonPath("$.[*].subAssunto").value(hasItem(DEFAULT_SUB_ASSUNTO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getConsolidacao() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get the consolidacao
        restConsolidacaoMockMvc.perform(get("/api/consolidacaos/{id}", consolidacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consolidacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.texto").value(DEFAULT_TEXTO))
            .andExpect(jsonPath("$.qtdEmail").value(DEFAULT_QTD_EMAIL))
            .andExpect(jsonPath("$.imagem").value(DEFAULT_IMAGEM))
            .andExpect(jsonPath("$.arquivoEml").value(DEFAULT_ARQUIVO_EML))
            .andExpect(jsonPath("$.assunto").value(DEFAULT_ASSUNTO))
            .andExpect(jsonPath("$.subAssunto").value(DEFAULT_SUB_ASSUNTO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getConsolidacaosByIdFiltering() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        Long id = consolidacao.getId();

        defaultConsolidacaoShouldBeFound("id.equals=" + id);
        defaultConsolidacaoShouldNotBeFound("id.notEquals=" + id);

        defaultConsolidacaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConsolidacaoShouldNotBeFound("id.greaterThan=" + id);

        defaultConsolidacaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConsolidacaoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllConsolidacaosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where nome equals to DEFAULT_NOME
        defaultConsolidacaoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the consolidacaoList where nome equals to UPDATED_NOME
        defaultConsolidacaoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where nome not equals to DEFAULT_NOME
        defaultConsolidacaoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the consolidacaoList where nome not equals to UPDATED_NOME
        defaultConsolidacaoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultConsolidacaoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the consolidacaoList where nome equals to UPDATED_NOME
        defaultConsolidacaoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where nome is not null
        defaultConsolidacaoShouldBeFound("nome.specified=true");

        // Get all the consolidacaoList where nome is null
        defaultConsolidacaoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllConsolidacaosByNomeContainsSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where nome contains DEFAULT_NOME
        defaultConsolidacaoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the consolidacaoList where nome contains UPDATED_NOME
        defaultConsolidacaoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where nome does not contain DEFAULT_NOME
        defaultConsolidacaoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the consolidacaoList where nome does not contain UPDATED_NOME
        defaultConsolidacaoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllConsolidacaosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where descricao equals to DEFAULT_DESCRICAO
        defaultConsolidacaoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the consolidacaoList where descricao equals to UPDATED_DESCRICAO
        defaultConsolidacaoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where descricao not equals to DEFAULT_DESCRICAO
        defaultConsolidacaoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the consolidacaoList where descricao not equals to UPDATED_DESCRICAO
        defaultConsolidacaoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultConsolidacaoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the consolidacaoList where descricao equals to UPDATED_DESCRICAO
        defaultConsolidacaoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where descricao is not null
        defaultConsolidacaoShouldBeFound("descricao.specified=true");

        // Get all the consolidacaoList where descricao is null
        defaultConsolidacaoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllConsolidacaosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where descricao contains DEFAULT_DESCRICAO
        defaultConsolidacaoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the consolidacaoList where descricao contains UPDATED_DESCRICAO
        defaultConsolidacaoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where descricao does not contain DEFAULT_DESCRICAO
        defaultConsolidacaoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the consolidacaoList where descricao does not contain UPDATED_DESCRICAO
        defaultConsolidacaoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllConsolidacaosByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where data equals to DEFAULT_DATA
        defaultConsolidacaoShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the consolidacaoList where data equals to UPDATED_DATA
        defaultConsolidacaoShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where data not equals to DEFAULT_DATA
        defaultConsolidacaoShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the consolidacaoList where data not equals to UPDATED_DATA
        defaultConsolidacaoShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByDataIsInShouldWork() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where data in DEFAULT_DATA or UPDATED_DATA
        defaultConsolidacaoShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the consolidacaoList where data equals to UPDATED_DATA
        defaultConsolidacaoShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where data is not null
        defaultConsolidacaoShouldBeFound("data.specified=true");

        // Get all the consolidacaoList where data is null
        defaultConsolidacaoShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where data is greater than or equal to DEFAULT_DATA
        defaultConsolidacaoShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the consolidacaoList where data is greater than or equal to UPDATED_DATA
        defaultConsolidacaoShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where data is less than or equal to DEFAULT_DATA
        defaultConsolidacaoShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the consolidacaoList where data is less than or equal to SMALLER_DATA
        defaultConsolidacaoShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where data is less than DEFAULT_DATA
        defaultConsolidacaoShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the consolidacaoList where data is less than UPDATED_DATA
        defaultConsolidacaoShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where data is greater than DEFAULT_DATA
        defaultConsolidacaoShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the consolidacaoList where data is greater than SMALLER_DATA
        defaultConsolidacaoShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllConsolidacaosByTextoIsEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where texto equals to DEFAULT_TEXTO
        defaultConsolidacaoShouldBeFound("texto.equals=" + DEFAULT_TEXTO);

        // Get all the consolidacaoList where texto equals to UPDATED_TEXTO
        defaultConsolidacaoShouldNotBeFound("texto.equals=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByTextoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where texto not equals to DEFAULT_TEXTO
        defaultConsolidacaoShouldNotBeFound("texto.notEquals=" + DEFAULT_TEXTO);

        // Get all the consolidacaoList where texto not equals to UPDATED_TEXTO
        defaultConsolidacaoShouldBeFound("texto.notEquals=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByTextoIsInShouldWork() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where texto in DEFAULT_TEXTO or UPDATED_TEXTO
        defaultConsolidacaoShouldBeFound("texto.in=" + DEFAULT_TEXTO + "," + UPDATED_TEXTO);

        // Get all the consolidacaoList where texto equals to UPDATED_TEXTO
        defaultConsolidacaoShouldNotBeFound("texto.in=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByTextoIsNullOrNotNull() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where texto is not null
        defaultConsolidacaoShouldBeFound("texto.specified=true");

        // Get all the consolidacaoList where texto is null
        defaultConsolidacaoShouldNotBeFound("texto.specified=false");
    }
                @Test
    @Transactional
    public void getAllConsolidacaosByTextoContainsSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where texto contains DEFAULT_TEXTO
        defaultConsolidacaoShouldBeFound("texto.contains=" + DEFAULT_TEXTO);

        // Get all the consolidacaoList where texto contains UPDATED_TEXTO
        defaultConsolidacaoShouldNotBeFound("texto.contains=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByTextoNotContainsSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where texto does not contain DEFAULT_TEXTO
        defaultConsolidacaoShouldNotBeFound("texto.doesNotContain=" + DEFAULT_TEXTO);

        // Get all the consolidacaoList where texto does not contain UPDATED_TEXTO
        defaultConsolidacaoShouldBeFound("texto.doesNotContain=" + UPDATED_TEXTO);
    }


    @Test
    @Transactional
    public void getAllConsolidacaosByQtdEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where qtdEmail equals to DEFAULT_QTD_EMAIL
        defaultConsolidacaoShouldBeFound("qtdEmail.equals=" + DEFAULT_QTD_EMAIL);

        // Get all the consolidacaoList where qtdEmail equals to UPDATED_QTD_EMAIL
        defaultConsolidacaoShouldNotBeFound("qtdEmail.equals=" + UPDATED_QTD_EMAIL);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByQtdEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where qtdEmail not equals to DEFAULT_QTD_EMAIL
        defaultConsolidacaoShouldNotBeFound("qtdEmail.notEquals=" + DEFAULT_QTD_EMAIL);

        // Get all the consolidacaoList where qtdEmail not equals to UPDATED_QTD_EMAIL
        defaultConsolidacaoShouldBeFound("qtdEmail.notEquals=" + UPDATED_QTD_EMAIL);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByQtdEmailIsInShouldWork() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where qtdEmail in DEFAULT_QTD_EMAIL or UPDATED_QTD_EMAIL
        defaultConsolidacaoShouldBeFound("qtdEmail.in=" + DEFAULT_QTD_EMAIL + "," + UPDATED_QTD_EMAIL);

        // Get all the consolidacaoList where qtdEmail equals to UPDATED_QTD_EMAIL
        defaultConsolidacaoShouldNotBeFound("qtdEmail.in=" + UPDATED_QTD_EMAIL);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByQtdEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where qtdEmail is not null
        defaultConsolidacaoShouldBeFound("qtdEmail.specified=true");

        // Get all the consolidacaoList where qtdEmail is null
        defaultConsolidacaoShouldNotBeFound("qtdEmail.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByQtdEmailIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where qtdEmail is greater than or equal to DEFAULT_QTD_EMAIL
        defaultConsolidacaoShouldBeFound("qtdEmail.greaterThanOrEqual=" + DEFAULT_QTD_EMAIL);

        // Get all the consolidacaoList where qtdEmail is greater than or equal to UPDATED_QTD_EMAIL
        defaultConsolidacaoShouldNotBeFound("qtdEmail.greaterThanOrEqual=" + UPDATED_QTD_EMAIL);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByQtdEmailIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where qtdEmail is less than or equal to DEFAULT_QTD_EMAIL
        defaultConsolidacaoShouldBeFound("qtdEmail.lessThanOrEqual=" + DEFAULT_QTD_EMAIL);

        // Get all the consolidacaoList where qtdEmail is less than or equal to SMALLER_QTD_EMAIL
        defaultConsolidacaoShouldNotBeFound("qtdEmail.lessThanOrEqual=" + SMALLER_QTD_EMAIL);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByQtdEmailIsLessThanSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where qtdEmail is less than DEFAULT_QTD_EMAIL
        defaultConsolidacaoShouldNotBeFound("qtdEmail.lessThan=" + DEFAULT_QTD_EMAIL);

        // Get all the consolidacaoList where qtdEmail is less than UPDATED_QTD_EMAIL
        defaultConsolidacaoShouldBeFound("qtdEmail.lessThan=" + UPDATED_QTD_EMAIL);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByQtdEmailIsGreaterThanSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where qtdEmail is greater than DEFAULT_QTD_EMAIL
        defaultConsolidacaoShouldNotBeFound("qtdEmail.greaterThan=" + DEFAULT_QTD_EMAIL);

        // Get all the consolidacaoList where qtdEmail is greater than SMALLER_QTD_EMAIL
        defaultConsolidacaoShouldBeFound("qtdEmail.greaterThan=" + SMALLER_QTD_EMAIL);
    }


    @Test
    @Transactional
    public void getAllConsolidacaosByImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where imagem equals to DEFAULT_IMAGEM
        defaultConsolidacaoShouldBeFound("imagem.equals=" + DEFAULT_IMAGEM);

        // Get all the consolidacaoList where imagem equals to UPDATED_IMAGEM
        defaultConsolidacaoShouldNotBeFound("imagem.equals=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByImagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where imagem not equals to DEFAULT_IMAGEM
        defaultConsolidacaoShouldNotBeFound("imagem.notEquals=" + DEFAULT_IMAGEM);

        // Get all the consolidacaoList where imagem not equals to UPDATED_IMAGEM
        defaultConsolidacaoShouldBeFound("imagem.notEquals=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByImagemIsInShouldWork() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where imagem in DEFAULT_IMAGEM or UPDATED_IMAGEM
        defaultConsolidacaoShouldBeFound("imagem.in=" + DEFAULT_IMAGEM + "," + UPDATED_IMAGEM);

        // Get all the consolidacaoList where imagem equals to UPDATED_IMAGEM
        defaultConsolidacaoShouldNotBeFound("imagem.in=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where imagem is not null
        defaultConsolidacaoShouldBeFound("imagem.specified=true");

        // Get all the consolidacaoList where imagem is null
        defaultConsolidacaoShouldNotBeFound("imagem.specified=false");
    }
                @Test
    @Transactional
    public void getAllConsolidacaosByImagemContainsSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where imagem contains DEFAULT_IMAGEM
        defaultConsolidacaoShouldBeFound("imagem.contains=" + DEFAULT_IMAGEM);

        // Get all the consolidacaoList where imagem contains UPDATED_IMAGEM
        defaultConsolidacaoShouldNotBeFound("imagem.contains=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByImagemNotContainsSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where imagem does not contain DEFAULT_IMAGEM
        defaultConsolidacaoShouldNotBeFound("imagem.doesNotContain=" + DEFAULT_IMAGEM);

        // Get all the consolidacaoList where imagem does not contain UPDATED_IMAGEM
        defaultConsolidacaoShouldBeFound("imagem.doesNotContain=" + UPDATED_IMAGEM);
    }


    @Test
    @Transactional
    public void getAllConsolidacaosByArquivoEmlIsEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where arquivoEml equals to DEFAULT_ARQUIVO_EML
        defaultConsolidacaoShouldBeFound("arquivoEml.equals=" + DEFAULT_ARQUIVO_EML);

        // Get all the consolidacaoList where arquivoEml equals to UPDATED_ARQUIVO_EML
        defaultConsolidacaoShouldNotBeFound("arquivoEml.equals=" + UPDATED_ARQUIVO_EML);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByArquivoEmlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where arquivoEml not equals to DEFAULT_ARQUIVO_EML
        defaultConsolidacaoShouldNotBeFound("arquivoEml.notEquals=" + DEFAULT_ARQUIVO_EML);

        // Get all the consolidacaoList where arquivoEml not equals to UPDATED_ARQUIVO_EML
        defaultConsolidacaoShouldBeFound("arquivoEml.notEquals=" + UPDATED_ARQUIVO_EML);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByArquivoEmlIsInShouldWork() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where arquivoEml in DEFAULT_ARQUIVO_EML or UPDATED_ARQUIVO_EML
        defaultConsolidacaoShouldBeFound("arquivoEml.in=" + DEFAULT_ARQUIVO_EML + "," + UPDATED_ARQUIVO_EML);

        // Get all the consolidacaoList where arquivoEml equals to UPDATED_ARQUIVO_EML
        defaultConsolidacaoShouldNotBeFound("arquivoEml.in=" + UPDATED_ARQUIVO_EML);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByArquivoEmlIsNullOrNotNull() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where arquivoEml is not null
        defaultConsolidacaoShouldBeFound("arquivoEml.specified=true");

        // Get all the consolidacaoList where arquivoEml is null
        defaultConsolidacaoShouldNotBeFound("arquivoEml.specified=false");
    }
                @Test
    @Transactional
    public void getAllConsolidacaosByArquivoEmlContainsSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where arquivoEml contains DEFAULT_ARQUIVO_EML
        defaultConsolidacaoShouldBeFound("arquivoEml.contains=" + DEFAULT_ARQUIVO_EML);

        // Get all the consolidacaoList where arquivoEml contains UPDATED_ARQUIVO_EML
        defaultConsolidacaoShouldNotBeFound("arquivoEml.contains=" + UPDATED_ARQUIVO_EML);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByArquivoEmlNotContainsSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where arquivoEml does not contain DEFAULT_ARQUIVO_EML
        defaultConsolidacaoShouldNotBeFound("arquivoEml.doesNotContain=" + DEFAULT_ARQUIVO_EML);

        // Get all the consolidacaoList where arquivoEml does not contain UPDATED_ARQUIVO_EML
        defaultConsolidacaoShouldBeFound("arquivoEml.doesNotContain=" + UPDATED_ARQUIVO_EML);
    }


    @Test
    @Transactional
    public void getAllConsolidacaosByAssuntoIsEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where assunto equals to DEFAULT_ASSUNTO
        defaultConsolidacaoShouldBeFound("assunto.equals=" + DEFAULT_ASSUNTO);

        // Get all the consolidacaoList where assunto equals to UPDATED_ASSUNTO
        defaultConsolidacaoShouldNotBeFound("assunto.equals=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByAssuntoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where assunto not equals to DEFAULT_ASSUNTO
        defaultConsolidacaoShouldNotBeFound("assunto.notEquals=" + DEFAULT_ASSUNTO);

        // Get all the consolidacaoList where assunto not equals to UPDATED_ASSUNTO
        defaultConsolidacaoShouldBeFound("assunto.notEquals=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByAssuntoIsInShouldWork() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where assunto in DEFAULT_ASSUNTO or UPDATED_ASSUNTO
        defaultConsolidacaoShouldBeFound("assunto.in=" + DEFAULT_ASSUNTO + "," + UPDATED_ASSUNTO);

        // Get all the consolidacaoList where assunto equals to UPDATED_ASSUNTO
        defaultConsolidacaoShouldNotBeFound("assunto.in=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByAssuntoIsNullOrNotNull() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where assunto is not null
        defaultConsolidacaoShouldBeFound("assunto.specified=true");

        // Get all the consolidacaoList where assunto is null
        defaultConsolidacaoShouldNotBeFound("assunto.specified=false");
    }
                @Test
    @Transactional
    public void getAllConsolidacaosByAssuntoContainsSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where assunto contains DEFAULT_ASSUNTO
        defaultConsolidacaoShouldBeFound("assunto.contains=" + DEFAULT_ASSUNTO);

        // Get all the consolidacaoList where assunto contains UPDATED_ASSUNTO
        defaultConsolidacaoShouldNotBeFound("assunto.contains=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByAssuntoNotContainsSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where assunto does not contain DEFAULT_ASSUNTO
        defaultConsolidacaoShouldNotBeFound("assunto.doesNotContain=" + DEFAULT_ASSUNTO);

        // Get all the consolidacaoList where assunto does not contain UPDATED_ASSUNTO
        defaultConsolidacaoShouldBeFound("assunto.doesNotContain=" + UPDATED_ASSUNTO);
    }


    @Test
    @Transactional
    public void getAllConsolidacaosBySubAssuntoIsEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where subAssunto equals to DEFAULT_SUB_ASSUNTO
        defaultConsolidacaoShouldBeFound("subAssunto.equals=" + DEFAULT_SUB_ASSUNTO);

        // Get all the consolidacaoList where subAssunto equals to UPDATED_SUB_ASSUNTO
        defaultConsolidacaoShouldNotBeFound("subAssunto.equals=" + UPDATED_SUB_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosBySubAssuntoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where subAssunto not equals to DEFAULT_SUB_ASSUNTO
        defaultConsolidacaoShouldNotBeFound("subAssunto.notEquals=" + DEFAULT_SUB_ASSUNTO);

        // Get all the consolidacaoList where subAssunto not equals to UPDATED_SUB_ASSUNTO
        defaultConsolidacaoShouldBeFound("subAssunto.notEquals=" + UPDATED_SUB_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosBySubAssuntoIsInShouldWork() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where subAssunto in DEFAULT_SUB_ASSUNTO or UPDATED_SUB_ASSUNTO
        defaultConsolidacaoShouldBeFound("subAssunto.in=" + DEFAULT_SUB_ASSUNTO + "," + UPDATED_SUB_ASSUNTO);

        // Get all the consolidacaoList where subAssunto equals to UPDATED_SUB_ASSUNTO
        defaultConsolidacaoShouldNotBeFound("subAssunto.in=" + UPDATED_SUB_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosBySubAssuntoIsNullOrNotNull() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where subAssunto is not null
        defaultConsolidacaoShouldBeFound("subAssunto.specified=true");

        // Get all the consolidacaoList where subAssunto is null
        defaultConsolidacaoShouldNotBeFound("subAssunto.specified=false");
    }
                @Test
    @Transactional
    public void getAllConsolidacaosBySubAssuntoContainsSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where subAssunto contains DEFAULT_SUB_ASSUNTO
        defaultConsolidacaoShouldBeFound("subAssunto.contains=" + DEFAULT_SUB_ASSUNTO);

        // Get all the consolidacaoList where subAssunto contains UPDATED_SUB_ASSUNTO
        defaultConsolidacaoShouldNotBeFound("subAssunto.contains=" + UPDATED_SUB_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosBySubAssuntoNotContainsSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where subAssunto does not contain DEFAULT_SUB_ASSUNTO
        defaultConsolidacaoShouldNotBeFound("subAssunto.doesNotContain=" + DEFAULT_SUB_ASSUNTO);

        // Get all the consolidacaoList where subAssunto does not contain UPDATED_SUB_ASSUNTO
        defaultConsolidacaoShouldBeFound("subAssunto.doesNotContain=" + UPDATED_SUB_ASSUNTO);
    }


    @Test
    @Transactional
    public void getAllConsolidacaosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where created equals to DEFAULT_CREATED
        defaultConsolidacaoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the consolidacaoList where created equals to UPDATED_CREATED
        defaultConsolidacaoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where created not equals to DEFAULT_CREATED
        defaultConsolidacaoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the consolidacaoList where created not equals to UPDATED_CREATED
        defaultConsolidacaoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultConsolidacaoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the consolidacaoList where created equals to UPDATED_CREATED
        defaultConsolidacaoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where created is not null
        defaultConsolidacaoShouldBeFound("created.specified=true");

        // Get all the consolidacaoList where created is null
        defaultConsolidacaoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where updated equals to DEFAULT_UPDATED
        defaultConsolidacaoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the consolidacaoList where updated equals to UPDATED_UPDATED
        defaultConsolidacaoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where updated not equals to DEFAULT_UPDATED
        defaultConsolidacaoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the consolidacaoList where updated not equals to UPDATED_UPDATED
        defaultConsolidacaoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultConsolidacaoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the consolidacaoList where updated equals to UPDATED_UPDATED
        defaultConsolidacaoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        // Get all the consolidacaoList where updated is not null
        defaultConsolidacaoShouldBeFound("updated.specified=true");

        // Get all the consolidacaoList where updated is null
        defaultConsolidacaoShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsolidacaosByPlanoRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);
        PlanoRecurso planoRecurso = PlanoRecursoResourceIT.createEntity(em);
        em.persist(planoRecurso);
        em.flush();
        consolidacao.setPlanoRecurso(planoRecurso);
        consolidacaoRepository.saveAndFlush(consolidacao);
        Long planoRecursoId = planoRecurso.getId();

        // Get all the consolidacaoList where planoRecurso equals to planoRecursoId
        defaultConsolidacaoShouldBeFound("planoRecursoId.equals=" + planoRecursoId);

        // Get all the consolidacaoList where planoRecurso equals to planoRecursoId + 1
        defaultConsolidacaoShouldNotBeFound("planoRecursoId.equals=" + (planoRecursoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConsolidacaoShouldBeFound(String filter) throws Exception {
        restConsolidacaoMockMvc.perform(get("/api/consolidacaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consolidacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO)))
            .andExpect(jsonPath("$.[*].qtdEmail").value(hasItem(DEFAULT_QTD_EMAIL)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.[*].arquivoEml").value(hasItem(DEFAULT_ARQUIVO_EML)))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO)))
            .andExpect(jsonPath("$.[*].subAssunto").value(hasItem(DEFAULT_SUB_ASSUNTO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restConsolidacaoMockMvc.perform(get("/api/consolidacaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConsolidacaoShouldNotBeFound(String filter) throws Exception {
        restConsolidacaoMockMvc.perform(get("/api/consolidacaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConsolidacaoMockMvc.perform(get("/api/consolidacaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingConsolidacao() throws Exception {
        // Get the consolidacao
        restConsolidacaoMockMvc.perform(get("/api/consolidacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsolidacao() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        int databaseSizeBeforeUpdate = consolidacaoRepository.findAll().size();

        // Update the consolidacao
        Consolidacao updatedConsolidacao = consolidacaoRepository.findById(consolidacao.getId()).get();
        // Disconnect from session so that the updates on updatedConsolidacao are not directly saved in db
        em.detach(updatedConsolidacao);
        updatedConsolidacao
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .data(UPDATED_DATA)
            .texto(UPDATED_TEXTO)
            .qtdEmail(UPDATED_QTD_EMAIL)
            .imagem(UPDATED_IMAGEM)
            .arquivoEml(UPDATED_ARQUIVO_EML)
            .assunto(UPDATED_ASSUNTO)
            .subAssunto(UPDATED_SUB_ASSUNTO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        ConsolidacaoDTO consolidacaoDTO = consolidacaoMapper.toDto(updatedConsolidacao);

        restConsolidacaoMockMvc.perform(put("/api/consolidacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consolidacaoDTO)))
            .andExpect(status().isOk());

        // Validate the Consolidacao in the database
        List<Consolidacao> consolidacaoList = consolidacaoRepository.findAll();
        assertThat(consolidacaoList).hasSize(databaseSizeBeforeUpdate);
        Consolidacao testConsolidacao = consolidacaoList.get(consolidacaoList.size() - 1);
        assertThat(testConsolidacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testConsolidacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testConsolidacao.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testConsolidacao.getTexto()).isEqualTo(UPDATED_TEXTO);
        assertThat(testConsolidacao.getQtdEmail()).isEqualTo(UPDATED_QTD_EMAIL);
        assertThat(testConsolidacao.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testConsolidacao.getArquivoEml()).isEqualTo(UPDATED_ARQUIVO_EML);
        assertThat(testConsolidacao.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testConsolidacao.getSubAssunto()).isEqualTo(UPDATED_SUB_ASSUNTO);
        assertThat(testConsolidacao.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testConsolidacao.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingConsolidacao() throws Exception {
        int databaseSizeBeforeUpdate = consolidacaoRepository.findAll().size();

        // Create the Consolidacao
        ConsolidacaoDTO consolidacaoDTO = consolidacaoMapper.toDto(consolidacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsolidacaoMockMvc.perform(put("/api/consolidacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consolidacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consolidacao in the database
        List<Consolidacao> consolidacaoList = consolidacaoRepository.findAll();
        assertThat(consolidacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsolidacao() throws Exception {
        // Initialize the database
        consolidacaoRepository.saveAndFlush(consolidacao);

        int databaseSizeBeforeDelete = consolidacaoRepository.findAll().size();

        // Delete the consolidacao
        restConsolidacaoMockMvc.perform(delete("/api/consolidacaos/{id}", consolidacao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consolidacao> consolidacaoList = consolidacaoRepository.findAll();
        assertThat(consolidacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
