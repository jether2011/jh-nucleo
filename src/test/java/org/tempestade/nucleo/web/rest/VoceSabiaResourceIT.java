package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.VoceSabia;
import org.tempestade.nucleo.repository.VoceSabiaRepository;
import org.tempestade.nucleo.service.VoceSabiaService;
import org.tempestade.nucleo.service.dto.VoceSabiaDTO;
import org.tempestade.nucleo.service.mapper.VoceSabiaMapper;

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
 * Integration tests for the {@link VoceSabiaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VoceSabiaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEM = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VoceSabiaRepository voceSabiaRepository;

    @Autowired
    private VoceSabiaMapper voceSabiaMapper;

    @Autowired
    private VoceSabiaService voceSabiaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoceSabiaMockMvc;

    private VoceSabia voceSabia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoceSabia createEntity(EntityManager em) {
        VoceSabia voceSabia = new VoceSabia()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .titulo(DEFAULT_TITULO)
            .texto(DEFAULT_TEXTO)
            .imagem(DEFAULT_IMAGEM)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return voceSabia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoceSabia createUpdatedEntity(EntityManager em) {
        VoceSabia voceSabia = new VoceSabia()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .titulo(UPDATED_TITULO)
            .texto(UPDATED_TEXTO)
            .imagem(UPDATED_IMAGEM)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return voceSabia;
    }

    @BeforeEach
    public void initTest() {
        voceSabia = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoceSabia() throws Exception {
        int databaseSizeBeforeCreate = voceSabiaRepository.findAll().size();
        // Create the VoceSabia
        VoceSabiaDTO voceSabiaDTO = voceSabiaMapper.toDto(voceSabia);
        restVoceSabiaMockMvc.perform(post("/api/voce-sabias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voceSabiaDTO)))
            .andExpect(status().isCreated());

        // Validate the VoceSabia in the database
        List<VoceSabia> voceSabiaList = voceSabiaRepository.findAll();
        assertThat(voceSabiaList).hasSize(databaseSizeBeforeCreate + 1);
        VoceSabia testVoceSabia = voceSabiaList.get(voceSabiaList.size() - 1);
        assertThat(testVoceSabia.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVoceSabia.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testVoceSabia.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testVoceSabia.getTexto()).isEqualTo(DEFAULT_TEXTO);
        assertThat(testVoceSabia.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testVoceSabia.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testVoceSabia.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createVoceSabiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voceSabiaRepository.findAll().size();

        // Create the VoceSabia with an existing ID
        voceSabia.setId(1L);
        VoceSabiaDTO voceSabiaDTO = voceSabiaMapper.toDto(voceSabia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoceSabiaMockMvc.perform(post("/api/voce-sabias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voceSabiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VoceSabia in the database
        List<VoceSabia> voceSabiaList = voceSabiaRepository.findAll();
        assertThat(voceSabiaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = voceSabiaRepository.findAll().size();
        // set the field null
        voceSabia.setName(null);

        // Create the VoceSabia, which fails.
        VoceSabiaDTO voceSabiaDTO = voceSabiaMapper.toDto(voceSabia);


        restVoceSabiaMockMvc.perform(post("/api/voce-sabias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voceSabiaDTO)))
            .andExpect(status().isBadRequest());

        List<VoceSabia> voceSabiaList = voceSabiaRepository.findAll();
        assertThat(voceSabiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = voceSabiaRepository.findAll().size();
        // set the field null
        voceSabia.setDescricao(null);

        // Create the VoceSabia, which fails.
        VoceSabiaDTO voceSabiaDTO = voceSabiaMapper.toDto(voceSabia);


        restVoceSabiaMockMvc.perform(post("/api/voce-sabias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voceSabiaDTO)))
            .andExpect(status().isBadRequest());

        List<VoceSabia> voceSabiaList = voceSabiaRepository.findAll();
        assertThat(voceSabiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = voceSabiaRepository.findAll().size();
        // set the field null
        voceSabia.setCreated(null);

        // Create the VoceSabia, which fails.
        VoceSabiaDTO voceSabiaDTO = voceSabiaMapper.toDto(voceSabia);


        restVoceSabiaMockMvc.perform(post("/api/voce-sabias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voceSabiaDTO)))
            .andExpect(status().isBadRequest());

        List<VoceSabia> voceSabiaList = voceSabiaRepository.findAll();
        assertThat(voceSabiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVoceSabias() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList
        restVoceSabiaMockMvc.perform(get("/api/voce-sabias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voceSabia.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getVoceSabia() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get the voceSabia
        restVoceSabiaMockMvc.perform(get("/api/voce-sabias/{id}", voceSabia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voceSabia.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.texto").value(DEFAULT_TEXTO))
            .andExpect(jsonPath("$.imagem").value(DEFAULT_IMAGEM))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingVoceSabia() throws Exception {
        // Get the voceSabia
        restVoceSabiaMockMvc.perform(get("/api/voce-sabias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoceSabia() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        int databaseSizeBeforeUpdate = voceSabiaRepository.findAll().size();

        // Update the voceSabia
        VoceSabia updatedVoceSabia = voceSabiaRepository.findById(voceSabia.getId()).get();
        // Disconnect from session so that the updates on updatedVoceSabia are not directly saved in db
        em.detach(updatedVoceSabia);
        updatedVoceSabia
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .titulo(UPDATED_TITULO)
            .texto(UPDATED_TEXTO)
            .imagem(UPDATED_IMAGEM)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        VoceSabiaDTO voceSabiaDTO = voceSabiaMapper.toDto(updatedVoceSabia);

        restVoceSabiaMockMvc.perform(put("/api/voce-sabias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voceSabiaDTO)))
            .andExpect(status().isOk());

        // Validate the VoceSabia in the database
        List<VoceSabia> voceSabiaList = voceSabiaRepository.findAll();
        assertThat(voceSabiaList).hasSize(databaseSizeBeforeUpdate);
        VoceSabia testVoceSabia = voceSabiaList.get(voceSabiaList.size() - 1);
        assertThat(testVoceSabia.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVoceSabia.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testVoceSabia.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testVoceSabia.getTexto()).isEqualTo(UPDATED_TEXTO);
        assertThat(testVoceSabia.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testVoceSabia.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testVoceSabia.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingVoceSabia() throws Exception {
        int databaseSizeBeforeUpdate = voceSabiaRepository.findAll().size();

        // Create the VoceSabia
        VoceSabiaDTO voceSabiaDTO = voceSabiaMapper.toDto(voceSabia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoceSabiaMockMvc.perform(put("/api/voce-sabias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voceSabiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VoceSabia in the database
        List<VoceSabia> voceSabiaList = voceSabiaRepository.findAll();
        assertThat(voceSabiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVoceSabia() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        int databaseSizeBeforeDelete = voceSabiaRepository.findAll().size();

        // Delete the voceSabia
        restVoceSabiaMockMvc.perform(delete("/api/voce-sabias/{id}", voceSabia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VoceSabia> voceSabiaList = voceSabiaRepository.findAll();
        assertThat(voceSabiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
