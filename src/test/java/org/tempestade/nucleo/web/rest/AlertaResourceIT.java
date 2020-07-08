package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Alerta;
import org.tempestade.nucleo.repository.AlertaRepository;
import org.tempestade.nucleo.service.AlertaService;
import org.tempestade.nucleo.service.dto.AlertaDTO;
import org.tempestade.nucleo.service.mapper.AlertaMapper;

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
 * Integration tests for the {@link AlertaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlertaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTATO = "AAAAAAAAAA";
    private static final String UPDATED_CONTATO = "BBBBBBBBBB";

    private static final String DEFAULT_DURACAO = "20:41:51";
    private static final String UPDATED_DURACAO = "03:33:19";

    private static final Boolean DEFAULT_AUTOMATICO = false;
    private static final Boolean UPDATED_AUTOMATICO = true;

    private static final Boolean DEFAULT_CRITICO = false;
    private static final Boolean UPDATED_CRITICO = true;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ALERTA_PAI_ID = 1;
    private static final Integer UPDATED_ALERTA_PAI_ID = 2;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private AlertaMapper alertaMapper;

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlertaMockMvc;

    private Alerta alerta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alerta createEntity(EntityManager em) {
        Alerta alerta = new Alerta()
            .nome(DEFAULT_NOME)
            .contato(DEFAULT_CONTATO)
            .duracao(DEFAULT_DURACAO)
            .automatico(DEFAULT_AUTOMATICO)
            .critico(DEFAULT_CRITICO)
            .observacao(DEFAULT_OBSERVACAO)
            .alertaPaiId(DEFAULT_ALERTA_PAI_ID)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return alerta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alerta createUpdatedEntity(EntityManager em) {
        Alerta alerta = new Alerta()
            .nome(UPDATED_NOME)
            .contato(UPDATED_CONTATO)
            .duracao(UPDATED_DURACAO)
            .automatico(UPDATED_AUTOMATICO)
            .critico(UPDATED_CRITICO)
            .observacao(UPDATED_OBSERVACAO)
            .alertaPaiId(UPDATED_ALERTA_PAI_ID)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return alerta;
    }

    @BeforeEach
    public void initTest() {
        alerta = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlerta() throws Exception {
        int databaseSizeBeforeCreate = alertaRepository.findAll().size();
        // Create the Alerta
        AlertaDTO alertaDTO = alertaMapper.toDto(alerta);
        restAlertaMockMvc.perform(post("/api/alertas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaDTO)))
            .andExpect(status().isCreated());

        // Validate the Alerta in the database
        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeCreate + 1);
        Alerta testAlerta = alertaList.get(alertaList.size() - 1);
        assertThat(testAlerta.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAlerta.getContato()).isEqualTo(DEFAULT_CONTATO);
        assertThat(testAlerta.getDuracao()).isEqualTo(DEFAULT_DURACAO);
        assertThat(testAlerta.isAutomatico()).isEqualTo(DEFAULT_AUTOMATICO);
        assertThat(testAlerta.isCritico()).isEqualTo(DEFAULT_CRITICO);
        assertThat(testAlerta.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testAlerta.getAlertaPaiId()).isEqualTo(DEFAULT_ALERTA_PAI_ID);
        assertThat(testAlerta.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAlerta.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createAlertaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alertaRepository.findAll().size();

        // Create the Alerta with an existing ID
        alerta.setId(1L);
        AlertaDTO alertaDTO = alertaMapper.toDto(alerta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertaMockMvc.perform(post("/api/alertas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alerta in the database
        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaRepository.findAll().size();
        // set the field null
        alerta.setNome(null);

        // Create the Alerta, which fails.
        AlertaDTO alertaDTO = alertaMapper.toDto(alerta);


        restAlertaMockMvc.perform(post("/api/alertas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaDTO)))
            .andExpect(status().isBadRequest());

        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaRepository.findAll().size();
        // set the field null
        alerta.setCreated(null);

        // Create the Alerta, which fails.
        AlertaDTO alertaDTO = alertaMapper.toDto(alerta);


        restAlertaMockMvc.perform(post("/api/alertas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaDTO)))
            .andExpect(status().isBadRequest());

        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlertas() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList
        restAlertaMockMvc.perform(get("/api/alertas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alerta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].contato").value(hasItem(DEFAULT_CONTATO)))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO)))
            .andExpect(jsonPath("$.[*].automatico").value(hasItem(DEFAULT_AUTOMATICO.booleanValue())))
            .andExpect(jsonPath("$.[*].critico").value(hasItem(DEFAULT_CRITICO.booleanValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].alertaPaiId").value(hasItem(DEFAULT_ALERTA_PAI_ID)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getAlerta() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get the alerta
        restAlertaMockMvc.perform(get("/api/alertas/{id}", alerta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alerta.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.contato").value(DEFAULT_CONTATO))
            .andExpect(jsonPath("$.duracao").value(DEFAULT_DURACAO))
            .andExpect(jsonPath("$.automatico").value(DEFAULT_AUTOMATICO.booleanValue()))
            .andExpect(jsonPath("$.critico").value(DEFAULT_CRITICO.booleanValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO))
            .andExpect(jsonPath("$.alertaPaiId").value(DEFAULT_ALERTA_PAI_ID))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAlerta() throws Exception {
        // Get the alerta
        restAlertaMockMvc.perform(get("/api/alertas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlerta() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        int databaseSizeBeforeUpdate = alertaRepository.findAll().size();

        // Update the alerta
        Alerta updatedAlerta = alertaRepository.findById(alerta.getId()).get();
        // Disconnect from session so that the updates on updatedAlerta are not directly saved in db
        em.detach(updatedAlerta);
        updatedAlerta
            .nome(UPDATED_NOME)
            .contato(UPDATED_CONTATO)
            .duracao(UPDATED_DURACAO)
            .automatico(UPDATED_AUTOMATICO)
            .critico(UPDATED_CRITICO)
            .observacao(UPDATED_OBSERVACAO)
            .alertaPaiId(UPDATED_ALERTA_PAI_ID)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        AlertaDTO alertaDTO = alertaMapper.toDto(updatedAlerta);

        restAlertaMockMvc.perform(put("/api/alertas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaDTO)))
            .andExpect(status().isOk());

        // Validate the Alerta in the database
        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeUpdate);
        Alerta testAlerta = alertaList.get(alertaList.size() - 1);
        assertThat(testAlerta.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAlerta.getContato()).isEqualTo(UPDATED_CONTATO);
        assertThat(testAlerta.getDuracao()).isEqualTo(UPDATED_DURACAO);
        assertThat(testAlerta.isAutomatico()).isEqualTo(UPDATED_AUTOMATICO);
        assertThat(testAlerta.isCritico()).isEqualTo(UPDATED_CRITICO);
        assertThat(testAlerta.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testAlerta.getAlertaPaiId()).isEqualTo(UPDATED_ALERTA_PAI_ID);
        assertThat(testAlerta.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAlerta.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAlerta() throws Exception {
        int databaseSizeBeforeUpdate = alertaRepository.findAll().size();

        // Create the Alerta
        AlertaDTO alertaDTO = alertaMapper.toDto(alerta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertaMockMvc.perform(put("/api/alertas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alerta in the database
        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlerta() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        int databaseSizeBeforeDelete = alertaRepository.findAll().size();

        // Delete the alerta
        restAlertaMockMvc.perform(delete("/api/alertas/{id}", alerta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
