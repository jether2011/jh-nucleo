package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Informativo;
import org.tempestade.nucleo.domain.PlanoRecurso;
import org.tempestade.nucleo.repository.InformativoRepository;
import org.tempestade.nucleo.service.InformativoService;
import org.tempestade.nucleo.service.dto.InformativoDTO;
import org.tempestade.nucleo.service.mapper.InformativoMapper;
import org.tempestade.nucleo.service.dto.InformativoCriteria;
import org.tempestade.nucleo.service.InformativoQueryService;

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
 * Integration tests for the {@link InformativoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InformativoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

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
    private InformativoRepository informativoRepository;

    @Autowired
    private InformativoMapper informativoMapper;

    @Autowired
    private InformativoService informativoService;

    @Autowired
    private InformativoQueryService informativoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInformativoMockMvc;

    private Informativo informativo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Informativo createEntity(EntityManager em) {
        Informativo informativo = new Informativo()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .texto(DEFAULT_TEXTO)
            .qtdEmail(DEFAULT_QTD_EMAIL)
            .imagem(DEFAULT_IMAGEM)
            .arquivoEml(DEFAULT_ARQUIVO_EML)
            .assunto(DEFAULT_ASSUNTO)
            .subAssunto(DEFAULT_SUB_ASSUNTO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return informativo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Informativo createUpdatedEntity(EntityManager em) {
        Informativo informativo = new Informativo()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .texto(UPDATED_TEXTO)
            .qtdEmail(UPDATED_QTD_EMAIL)
            .imagem(UPDATED_IMAGEM)
            .arquivoEml(UPDATED_ARQUIVO_EML)
            .assunto(UPDATED_ASSUNTO)
            .subAssunto(UPDATED_SUB_ASSUNTO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return informativo;
    }

    @BeforeEach
    public void initTest() {
        informativo = createEntity(em);
    }

    @Test
    @Transactional
    public void createInformativo() throws Exception {
        int databaseSizeBeforeCreate = informativoRepository.findAll().size();
        // Create the Informativo
        InformativoDTO informativoDTO = informativoMapper.toDto(informativo);
        restInformativoMockMvc.perform(post("/api/informativos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informativoDTO)))
            .andExpect(status().isCreated());

        // Validate the Informativo in the database
        List<Informativo> informativoList = informativoRepository.findAll();
        assertThat(informativoList).hasSize(databaseSizeBeforeCreate + 1);
        Informativo testInformativo = informativoList.get(informativoList.size() - 1);
        assertThat(testInformativo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testInformativo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testInformativo.getTexto()).isEqualTo(DEFAULT_TEXTO);
        assertThat(testInformativo.getQtdEmail()).isEqualTo(DEFAULT_QTD_EMAIL);
        assertThat(testInformativo.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testInformativo.getArquivoEml()).isEqualTo(DEFAULT_ARQUIVO_EML);
        assertThat(testInformativo.getAssunto()).isEqualTo(DEFAULT_ASSUNTO);
        assertThat(testInformativo.getSubAssunto()).isEqualTo(DEFAULT_SUB_ASSUNTO);
        assertThat(testInformativo.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testInformativo.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createInformativoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = informativoRepository.findAll().size();

        // Create the Informativo with an existing ID
        informativo.setId(1L);
        InformativoDTO informativoDTO = informativoMapper.toDto(informativo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInformativoMockMvc.perform(post("/api/informativos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informativoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Informativo in the database
        List<Informativo> informativoList = informativoRepository.findAll();
        assertThat(informativoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = informativoRepository.findAll().size();
        // set the field null
        informativo.setNome(null);

        // Create the Informativo, which fails.
        InformativoDTO informativoDTO = informativoMapper.toDto(informativo);


        restInformativoMockMvc.perform(post("/api/informativos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informativoDTO)))
            .andExpect(status().isBadRequest());

        List<Informativo> informativoList = informativoRepository.findAll();
        assertThat(informativoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = informativoRepository.findAll().size();
        // set the field null
        informativo.setCreated(null);

        // Create the Informativo, which fails.
        InformativoDTO informativoDTO = informativoMapper.toDto(informativo);


        restInformativoMockMvc.perform(post("/api/informativos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informativoDTO)))
            .andExpect(status().isBadRequest());

        List<Informativo> informativoList = informativoRepository.findAll();
        assertThat(informativoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInformativos() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList
        restInformativoMockMvc.perform(get("/api/informativos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(informativo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
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
    public void getInformativo() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get the informativo
        restInformativoMockMvc.perform(get("/api/informativos/{id}", informativo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(informativo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
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
    public void getInformativosByIdFiltering() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        Long id = informativo.getId();

        defaultInformativoShouldBeFound("id.equals=" + id);
        defaultInformativoShouldNotBeFound("id.notEquals=" + id);

        defaultInformativoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInformativoShouldNotBeFound("id.greaterThan=" + id);

        defaultInformativoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInformativoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInformativosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where nome equals to DEFAULT_NOME
        defaultInformativoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the informativoList where nome equals to UPDATED_NOME
        defaultInformativoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllInformativosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where nome not equals to DEFAULT_NOME
        defaultInformativoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the informativoList where nome not equals to UPDATED_NOME
        defaultInformativoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllInformativosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultInformativoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the informativoList where nome equals to UPDATED_NOME
        defaultInformativoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllInformativosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where nome is not null
        defaultInformativoShouldBeFound("nome.specified=true");

        // Get all the informativoList where nome is null
        defaultInformativoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllInformativosByNomeContainsSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where nome contains DEFAULT_NOME
        defaultInformativoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the informativoList where nome contains UPDATED_NOME
        defaultInformativoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllInformativosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where nome does not contain DEFAULT_NOME
        defaultInformativoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the informativoList where nome does not contain UPDATED_NOME
        defaultInformativoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllInformativosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where descricao equals to DEFAULT_DESCRICAO
        defaultInformativoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the informativoList where descricao equals to UPDATED_DESCRICAO
        defaultInformativoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllInformativosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where descricao not equals to DEFAULT_DESCRICAO
        defaultInformativoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the informativoList where descricao not equals to UPDATED_DESCRICAO
        defaultInformativoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllInformativosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultInformativoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the informativoList where descricao equals to UPDATED_DESCRICAO
        defaultInformativoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllInformativosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where descricao is not null
        defaultInformativoShouldBeFound("descricao.specified=true");

        // Get all the informativoList where descricao is null
        defaultInformativoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllInformativosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where descricao contains DEFAULT_DESCRICAO
        defaultInformativoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the informativoList where descricao contains UPDATED_DESCRICAO
        defaultInformativoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllInformativosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where descricao does not contain DEFAULT_DESCRICAO
        defaultInformativoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the informativoList where descricao does not contain UPDATED_DESCRICAO
        defaultInformativoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllInformativosByTextoIsEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where texto equals to DEFAULT_TEXTO
        defaultInformativoShouldBeFound("texto.equals=" + DEFAULT_TEXTO);

        // Get all the informativoList where texto equals to UPDATED_TEXTO
        defaultInformativoShouldNotBeFound("texto.equals=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllInformativosByTextoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where texto not equals to DEFAULT_TEXTO
        defaultInformativoShouldNotBeFound("texto.notEquals=" + DEFAULT_TEXTO);

        // Get all the informativoList where texto not equals to UPDATED_TEXTO
        defaultInformativoShouldBeFound("texto.notEquals=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllInformativosByTextoIsInShouldWork() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where texto in DEFAULT_TEXTO or UPDATED_TEXTO
        defaultInformativoShouldBeFound("texto.in=" + DEFAULT_TEXTO + "," + UPDATED_TEXTO);

        // Get all the informativoList where texto equals to UPDATED_TEXTO
        defaultInformativoShouldNotBeFound("texto.in=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllInformativosByTextoIsNullOrNotNull() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where texto is not null
        defaultInformativoShouldBeFound("texto.specified=true");

        // Get all the informativoList where texto is null
        defaultInformativoShouldNotBeFound("texto.specified=false");
    }
                @Test
    @Transactional
    public void getAllInformativosByTextoContainsSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where texto contains DEFAULT_TEXTO
        defaultInformativoShouldBeFound("texto.contains=" + DEFAULT_TEXTO);

        // Get all the informativoList where texto contains UPDATED_TEXTO
        defaultInformativoShouldNotBeFound("texto.contains=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllInformativosByTextoNotContainsSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where texto does not contain DEFAULT_TEXTO
        defaultInformativoShouldNotBeFound("texto.doesNotContain=" + DEFAULT_TEXTO);

        // Get all the informativoList where texto does not contain UPDATED_TEXTO
        defaultInformativoShouldBeFound("texto.doesNotContain=" + UPDATED_TEXTO);
    }


    @Test
    @Transactional
    public void getAllInformativosByQtdEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where qtdEmail equals to DEFAULT_QTD_EMAIL
        defaultInformativoShouldBeFound("qtdEmail.equals=" + DEFAULT_QTD_EMAIL);

        // Get all the informativoList where qtdEmail equals to UPDATED_QTD_EMAIL
        defaultInformativoShouldNotBeFound("qtdEmail.equals=" + UPDATED_QTD_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInformativosByQtdEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where qtdEmail not equals to DEFAULT_QTD_EMAIL
        defaultInformativoShouldNotBeFound("qtdEmail.notEquals=" + DEFAULT_QTD_EMAIL);

        // Get all the informativoList where qtdEmail not equals to UPDATED_QTD_EMAIL
        defaultInformativoShouldBeFound("qtdEmail.notEquals=" + UPDATED_QTD_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInformativosByQtdEmailIsInShouldWork() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where qtdEmail in DEFAULT_QTD_EMAIL or UPDATED_QTD_EMAIL
        defaultInformativoShouldBeFound("qtdEmail.in=" + DEFAULT_QTD_EMAIL + "," + UPDATED_QTD_EMAIL);

        // Get all the informativoList where qtdEmail equals to UPDATED_QTD_EMAIL
        defaultInformativoShouldNotBeFound("qtdEmail.in=" + UPDATED_QTD_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInformativosByQtdEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where qtdEmail is not null
        defaultInformativoShouldBeFound("qtdEmail.specified=true");

        // Get all the informativoList where qtdEmail is null
        defaultInformativoShouldNotBeFound("qtdEmail.specified=false");
    }

    @Test
    @Transactional
    public void getAllInformativosByQtdEmailIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where qtdEmail is greater than or equal to DEFAULT_QTD_EMAIL
        defaultInformativoShouldBeFound("qtdEmail.greaterThanOrEqual=" + DEFAULT_QTD_EMAIL);

        // Get all the informativoList where qtdEmail is greater than or equal to UPDATED_QTD_EMAIL
        defaultInformativoShouldNotBeFound("qtdEmail.greaterThanOrEqual=" + UPDATED_QTD_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInformativosByQtdEmailIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where qtdEmail is less than or equal to DEFAULT_QTD_EMAIL
        defaultInformativoShouldBeFound("qtdEmail.lessThanOrEqual=" + DEFAULT_QTD_EMAIL);

        // Get all the informativoList where qtdEmail is less than or equal to SMALLER_QTD_EMAIL
        defaultInformativoShouldNotBeFound("qtdEmail.lessThanOrEqual=" + SMALLER_QTD_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInformativosByQtdEmailIsLessThanSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where qtdEmail is less than DEFAULT_QTD_EMAIL
        defaultInformativoShouldNotBeFound("qtdEmail.lessThan=" + DEFAULT_QTD_EMAIL);

        // Get all the informativoList where qtdEmail is less than UPDATED_QTD_EMAIL
        defaultInformativoShouldBeFound("qtdEmail.lessThan=" + UPDATED_QTD_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInformativosByQtdEmailIsGreaterThanSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where qtdEmail is greater than DEFAULT_QTD_EMAIL
        defaultInformativoShouldNotBeFound("qtdEmail.greaterThan=" + DEFAULT_QTD_EMAIL);

        // Get all the informativoList where qtdEmail is greater than SMALLER_QTD_EMAIL
        defaultInformativoShouldBeFound("qtdEmail.greaterThan=" + SMALLER_QTD_EMAIL);
    }


    @Test
    @Transactional
    public void getAllInformativosByImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where imagem equals to DEFAULT_IMAGEM
        defaultInformativoShouldBeFound("imagem.equals=" + DEFAULT_IMAGEM);

        // Get all the informativoList where imagem equals to UPDATED_IMAGEM
        defaultInformativoShouldNotBeFound("imagem.equals=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllInformativosByImagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where imagem not equals to DEFAULT_IMAGEM
        defaultInformativoShouldNotBeFound("imagem.notEquals=" + DEFAULT_IMAGEM);

        // Get all the informativoList where imagem not equals to UPDATED_IMAGEM
        defaultInformativoShouldBeFound("imagem.notEquals=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllInformativosByImagemIsInShouldWork() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where imagem in DEFAULT_IMAGEM or UPDATED_IMAGEM
        defaultInformativoShouldBeFound("imagem.in=" + DEFAULT_IMAGEM + "," + UPDATED_IMAGEM);

        // Get all the informativoList where imagem equals to UPDATED_IMAGEM
        defaultInformativoShouldNotBeFound("imagem.in=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllInformativosByImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where imagem is not null
        defaultInformativoShouldBeFound("imagem.specified=true");

        // Get all the informativoList where imagem is null
        defaultInformativoShouldNotBeFound("imagem.specified=false");
    }
                @Test
    @Transactional
    public void getAllInformativosByImagemContainsSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where imagem contains DEFAULT_IMAGEM
        defaultInformativoShouldBeFound("imagem.contains=" + DEFAULT_IMAGEM);

        // Get all the informativoList where imagem contains UPDATED_IMAGEM
        defaultInformativoShouldNotBeFound("imagem.contains=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllInformativosByImagemNotContainsSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where imagem does not contain DEFAULT_IMAGEM
        defaultInformativoShouldNotBeFound("imagem.doesNotContain=" + DEFAULT_IMAGEM);

        // Get all the informativoList where imagem does not contain UPDATED_IMAGEM
        defaultInformativoShouldBeFound("imagem.doesNotContain=" + UPDATED_IMAGEM);
    }


    @Test
    @Transactional
    public void getAllInformativosByArquivoEmlIsEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where arquivoEml equals to DEFAULT_ARQUIVO_EML
        defaultInformativoShouldBeFound("arquivoEml.equals=" + DEFAULT_ARQUIVO_EML);

        // Get all the informativoList where arquivoEml equals to UPDATED_ARQUIVO_EML
        defaultInformativoShouldNotBeFound("arquivoEml.equals=" + UPDATED_ARQUIVO_EML);
    }

    @Test
    @Transactional
    public void getAllInformativosByArquivoEmlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where arquivoEml not equals to DEFAULT_ARQUIVO_EML
        defaultInformativoShouldNotBeFound("arquivoEml.notEquals=" + DEFAULT_ARQUIVO_EML);

        // Get all the informativoList where arquivoEml not equals to UPDATED_ARQUIVO_EML
        defaultInformativoShouldBeFound("arquivoEml.notEquals=" + UPDATED_ARQUIVO_EML);
    }

    @Test
    @Transactional
    public void getAllInformativosByArquivoEmlIsInShouldWork() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where arquivoEml in DEFAULT_ARQUIVO_EML or UPDATED_ARQUIVO_EML
        defaultInformativoShouldBeFound("arquivoEml.in=" + DEFAULT_ARQUIVO_EML + "," + UPDATED_ARQUIVO_EML);

        // Get all the informativoList where arquivoEml equals to UPDATED_ARQUIVO_EML
        defaultInformativoShouldNotBeFound("arquivoEml.in=" + UPDATED_ARQUIVO_EML);
    }

    @Test
    @Transactional
    public void getAllInformativosByArquivoEmlIsNullOrNotNull() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where arquivoEml is not null
        defaultInformativoShouldBeFound("arquivoEml.specified=true");

        // Get all the informativoList where arquivoEml is null
        defaultInformativoShouldNotBeFound("arquivoEml.specified=false");
    }
                @Test
    @Transactional
    public void getAllInformativosByArquivoEmlContainsSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where arquivoEml contains DEFAULT_ARQUIVO_EML
        defaultInformativoShouldBeFound("arquivoEml.contains=" + DEFAULT_ARQUIVO_EML);

        // Get all the informativoList where arquivoEml contains UPDATED_ARQUIVO_EML
        defaultInformativoShouldNotBeFound("arquivoEml.contains=" + UPDATED_ARQUIVO_EML);
    }

    @Test
    @Transactional
    public void getAllInformativosByArquivoEmlNotContainsSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where arquivoEml does not contain DEFAULT_ARQUIVO_EML
        defaultInformativoShouldNotBeFound("arquivoEml.doesNotContain=" + DEFAULT_ARQUIVO_EML);

        // Get all the informativoList where arquivoEml does not contain UPDATED_ARQUIVO_EML
        defaultInformativoShouldBeFound("arquivoEml.doesNotContain=" + UPDATED_ARQUIVO_EML);
    }


    @Test
    @Transactional
    public void getAllInformativosByAssuntoIsEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where assunto equals to DEFAULT_ASSUNTO
        defaultInformativoShouldBeFound("assunto.equals=" + DEFAULT_ASSUNTO);

        // Get all the informativoList where assunto equals to UPDATED_ASSUNTO
        defaultInformativoShouldNotBeFound("assunto.equals=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllInformativosByAssuntoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where assunto not equals to DEFAULT_ASSUNTO
        defaultInformativoShouldNotBeFound("assunto.notEquals=" + DEFAULT_ASSUNTO);

        // Get all the informativoList where assunto not equals to UPDATED_ASSUNTO
        defaultInformativoShouldBeFound("assunto.notEquals=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllInformativosByAssuntoIsInShouldWork() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where assunto in DEFAULT_ASSUNTO or UPDATED_ASSUNTO
        defaultInformativoShouldBeFound("assunto.in=" + DEFAULT_ASSUNTO + "," + UPDATED_ASSUNTO);

        // Get all the informativoList where assunto equals to UPDATED_ASSUNTO
        defaultInformativoShouldNotBeFound("assunto.in=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllInformativosByAssuntoIsNullOrNotNull() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where assunto is not null
        defaultInformativoShouldBeFound("assunto.specified=true");

        // Get all the informativoList where assunto is null
        defaultInformativoShouldNotBeFound("assunto.specified=false");
    }
                @Test
    @Transactional
    public void getAllInformativosByAssuntoContainsSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where assunto contains DEFAULT_ASSUNTO
        defaultInformativoShouldBeFound("assunto.contains=" + DEFAULT_ASSUNTO);

        // Get all the informativoList where assunto contains UPDATED_ASSUNTO
        defaultInformativoShouldNotBeFound("assunto.contains=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllInformativosByAssuntoNotContainsSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where assunto does not contain DEFAULT_ASSUNTO
        defaultInformativoShouldNotBeFound("assunto.doesNotContain=" + DEFAULT_ASSUNTO);

        // Get all the informativoList where assunto does not contain UPDATED_ASSUNTO
        defaultInformativoShouldBeFound("assunto.doesNotContain=" + UPDATED_ASSUNTO);
    }


    @Test
    @Transactional
    public void getAllInformativosBySubAssuntoIsEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where subAssunto equals to DEFAULT_SUB_ASSUNTO
        defaultInformativoShouldBeFound("subAssunto.equals=" + DEFAULT_SUB_ASSUNTO);

        // Get all the informativoList where subAssunto equals to UPDATED_SUB_ASSUNTO
        defaultInformativoShouldNotBeFound("subAssunto.equals=" + UPDATED_SUB_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllInformativosBySubAssuntoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where subAssunto not equals to DEFAULT_SUB_ASSUNTO
        defaultInformativoShouldNotBeFound("subAssunto.notEquals=" + DEFAULT_SUB_ASSUNTO);

        // Get all the informativoList where subAssunto not equals to UPDATED_SUB_ASSUNTO
        defaultInformativoShouldBeFound("subAssunto.notEquals=" + UPDATED_SUB_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllInformativosBySubAssuntoIsInShouldWork() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where subAssunto in DEFAULT_SUB_ASSUNTO or UPDATED_SUB_ASSUNTO
        defaultInformativoShouldBeFound("subAssunto.in=" + DEFAULT_SUB_ASSUNTO + "," + UPDATED_SUB_ASSUNTO);

        // Get all the informativoList where subAssunto equals to UPDATED_SUB_ASSUNTO
        defaultInformativoShouldNotBeFound("subAssunto.in=" + UPDATED_SUB_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllInformativosBySubAssuntoIsNullOrNotNull() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where subAssunto is not null
        defaultInformativoShouldBeFound("subAssunto.specified=true");

        // Get all the informativoList where subAssunto is null
        defaultInformativoShouldNotBeFound("subAssunto.specified=false");
    }
                @Test
    @Transactional
    public void getAllInformativosBySubAssuntoContainsSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where subAssunto contains DEFAULT_SUB_ASSUNTO
        defaultInformativoShouldBeFound("subAssunto.contains=" + DEFAULT_SUB_ASSUNTO);

        // Get all the informativoList where subAssunto contains UPDATED_SUB_ASSUNTO
        defaultInformativoShouldNotBeFound("subAssunto.contains=" + UPDATED_SUB_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllInformativosBySubAssuntoNotContainsSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where subAssunto does not contain DEFAULT_SUB_ASSUNTO
        defaultInformativoShouldNotBeFound("subAssunto.doesNotContain=" + DEFAULT_SUB_ASSUNTO);

        // Get all the informativoList where subAssunto does not contain UPDATED_SUB_ASSUNTO
        defaultInformativoShouldBeFound("subAssunto.doesNotContain=" + UPDATED_SUB_ASSUNTO);
    }


    @Test
    @Transactional
    public void getAllInformativosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where created equals to DEFAULT_CREATED
        defaultInformativoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the informativoList where created equals to UPDATED_CREATED
        defaultInformativoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllInformativosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where created not equals to DEFAULT_CREATED
        defaultInformativoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the informativoList where created not equals to UPDATED_CREATED
        defaultInformativoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllInformativosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultInformativoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the informativoList where created equals to UPDATED_CREATED
        defaultInformativoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllInformativosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where created is not null
        defaultInformativoShouldBeFound("created.specified=true");

        // Get all the informativoList where created is null
        defaultInformativoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllInformativosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where updated equals to DEFAULT_UPDATED
        defaultInformativoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the informativoList where updated equals to UPDATED_UPDATED
        defaultInformativoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllInformativosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where updated not equals to DEFAULT_UPDATED
        defaultInformativoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the informativoList where updated not equals to UPDATED_UPDATED
        defaultInformativoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllInformativosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultInformativoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the informativoList where updated equals to UPDATED_UPDATED
        defaultInformativoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllInformativosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        // Get all the informativoList where updated is not null
        defaultInformativoShouldBeFound("updated.specified=true");

        // Get all the informativoList where updated is null
        defaultInformativoShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllInformativosByPlanoRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);
        PlanoRecurso planoRecurso = PlanoRecursoResourceIT.createEntity(em);
        em.persist(planoRecurso);
        em.flush();
        informativo.setPlanoRecurso(planoRecurso);
        informativoRepository.saveAndFlush(informativo);
        Long planoRecursoId = planoRecurso.getId();

        // Get all the informativoList where planoRecurso equals to planoRecursoId
        defaultInformativoShouldBeFound("planoRecursoId.equals=" + planoRecursoId);

        // Get all the informativoList where planoRecurso equals to planoRecursoId + 1
        defaultInformativoShouldNotBeFound("planoRecursoId.equals=" + (planoRecursoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInformativoShouldBeFound(String filter) throws Exception {
        restInformativoMockMvc.perform(get("/api/informativos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(informativo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO)))
            .andExpect(jsonPath("$.[*].qtdEmail").value(hasItem(DEFAULT_QTD_EMAIL)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.[*].arquivoEml").value(hasItem(DEFAULT_ARQUIVO_EML)))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO)))
            .andExpect(jsonPath("$.[*].subAssunto").value(hasItem(DEFAULT_SUB_ASSUNTO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restInformativoMockMvc.perform(get("/api/informativos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInformativoShouldNotBeFound(String filter) throws Exception {
        restInformativoMockMvc.perform(get("/api/informativos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInformativoMockMvc.perform(get("/api/informativos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInformativo() throws Exception {
        // Get the informativo
        restInformativoMockMvc.perform(get("/api/informativos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInformativo() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        int databaseSizeBeforeUpdate = informativoRepository.findAll().size();

        // Update the informativo
        Informativo updatedInformativo = informativoRepository.findById(informativo.getId()).get();
        // Disconnect from session so that the updates on updatedInformativo are not directly saved in db
        em.detach(updatedInformativo);
        updatedInformativo
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .texto(UPDATED_TEXTO)
            .qtdEmail(UPDATED_QTD_EMAIL)
            .imagem(UPDATED_IMAGEM)
            .arquivoEml(UPDATED_ARQUIVO_EML)
            .assunto(UPDATED_ASSUNTO)
            .subAssunto(UPDATED_SUB_ASSUNTO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        InformativoDTO informativoDTO = informativoMapper.toDto(updatedInformativo);

        restInformativoMockMvc.perform(put("/api/informativos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informativoDTO)))
            .andExpect(status().isOk());

        // Validate the Informativo in the database
        List<Informativo> informativoList = informativoRepository.findAll();
        assertThat(informativoList).hasSize(databaseSizeBeforeUpdate);
        Informativo testInformativo = informativoList.get(informativoList.size() - 1);
        assertThat(testInformativo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testInformativo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testInformativo.getTexto()).isEqualTo(UPDATED_TEXTO);
        assertThat(testInformativo.getQtdEmail()).isEqualTo(UPDATED_QTD_EMAIL);
        assertThat(testInformativo.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testInformativo.getArquivoEml()).isEqualTo(UPDATED_ARQUIVO_EML);
        assertThat(testInformativo.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testInformativo.getSubAssunto()).isEqualTo(UPDATED_SUB_ASSUNTO);
        assertThat(testInformativo.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testInformativo.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingInformativo() throws Exception {
        int databaseSizeBeforeUpdate = informativoRepository.findAll().size();

        // Create the Informativo
        InformativoDTO informativoDTO = informativoMapper.toDto(informativo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInformativoMockMvc.perform(put("/api/informativos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informativoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Informativo in the database
        List<Informativo> informativoList = informativoRepository.findAll();
        assertThat(informativoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInformativo() throws Exception {
        // Initialize the database
        informativoRepository.saveAndFlush(informativo);

        int databaseSizeBeforeDelete = informativoRepository.findAll().size();

        // Delete the informativo
        restInformativoMockMvc.perform(delete("/api/informativos/{id}", informativo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Informativo> informativoList = informativoRepository.findAll();
        assertThat(informativoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
