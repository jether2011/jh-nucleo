package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Descarga;
import org.tempestade.nucleo.repository.DescargaRepository;
import org.tempestade.nucleo.service.DescargaService;
import org.tempestade.nucleo.service.dto.DescargaDTO;
import org.tempestade.nucleo.service.mapper.DescargaMapper;

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
