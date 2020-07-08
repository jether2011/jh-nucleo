package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Informativo;
import org.tempestade.nucleo.repository.InformativoRepository;
import org.tempestade.nucleo.service.InformativoService;
import org.tempestade.nucleo.service.dto.InformativoDTO;
import org.tempestade.nucleo.service.mapper.InformativoMapper;

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
