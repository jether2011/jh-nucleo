package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Consolidacao;
import org.tempestade.nucleo.repository.ConsolidacaoRepository;
import org.tempestade.nucleo.service.ConsolidacaoService;
import org.tempestade.nucleo.service.dto.ConsolidacaoDTO;
import org.tempestade.nucleo.service.mapper.ConsolidacaoMapper;

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

    private static final String DEFAULT_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_QTD_EMAIL = 1;
    private static final Integer UPDATED_QTD_EMAIL = 2;

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
