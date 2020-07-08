package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.AlertaFerramenta;
import org.tempestade.nucleo.repository.AlertaFerramentaRepository;
import org.tempestade.nucleo.service.AlertaFerramentaService;
import org.tempestade.nucleo.service.dto.AlertaFerramentaDTO;
import org.tempestade.nucleo.service.mapper.AlertaFerramentaMapper;

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
 * Integration tests for the {@link AlertaFerramentaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlertaFerramentaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AlertaFerramentaRepository alertaFerramentaRepository;

    @Autowired
    private AlertaFerramentaMapper alertaFerramentaMapper;

    @Autowired
    private AlertaFerramentaService alertaFerramentaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlertaFerramentaMockMvc;

    private AlertaFerramenta alertaFerramenta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertaFerramenta createEntity(EntityManager em) {
        AlertaFerramenta alertaFerramenta = new AlertaFerramenta()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return alertaFerramenta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertaFerramenta createUpdatedEntity(EntityManager em) {
        AlertaFerramenta alertaFerramenta = new AlertaFerramenta()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return alertaFerramenta;
    }

    @BeforeEach
    public void initTest() {
        alertaFerramenta = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlertaFerramenta() throws Exception {
        int databaseSizeBeforeCreate = alertaFerramentaRepository.findAll().size();
        // Create the AlertaFerramenta
        AlertaFerramentaDTO alertaFerramentaDTO = alertaFerramentaMapper.toDto(alertaFerramenta);
        restAlertaFerramentaMockMvc.perform(post("/api/alerta-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaFerramentaDTO)))
            .andExpect(status().isCreated());

        // Validate the AlertaFerramenta in the database
        List<AlertaFerramenta> alertaFerramentaList = alertaFerramentaRepository.findAll();
        assertThat(alertaFerramentaList).hasSize(databaseSizeBeforeCreate + 1);
        AlertaFerramenta testAlertaFerramenta = alertaFerramentaList.get(alertaFerramentaList.size() - 1);
        assertThat(testAlertaFerramenta.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAlertaFerramenta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAlertaFerramenta.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAlertaFerramenta.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createAlertaFerramentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alertaFerramentaRepository.findAll().size();

        // Create the AlertaFerramenta with an existing ID
        alertaFerramenta.setId(1L);
        AlertaFerramentaDTO alertaFerramentaDTO = alertaFerramentaMapper.toDto(alertaFerramenta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertaFerramentaMockMvc.perform(post("/api/alerta-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaFerramentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlertaFerramenta in the database
        List<AlertaFerramenta> alertaFerramentaList = alertaFerramentaRepository.findAll();
        assertThat(alertaFerramentaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaFerramentaRepository.findAll().size();
        // set the field null
        alertaFerramenta.setNome(null);

        // Create the AlertaFerramenta, which fails.
        AlertaFerramentaDTO alertaFerramentaDTO = alertaFerramentaMapper.toDto(alertaFerramenta);


        restAlertaFerramentaMockMvc.perform(post("/api/alerta-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaFerramentaDTO)))
            .andExpect(status().isBadRequest());

        List<AlertaFerramenta> alertaFerramentaList = alertaFerramentaRepository.findAll();
        assertThat(alertaFerramentaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaFerramentaRepository.findAll().size();
        // set the field null
        alertaFerramenta.setCreated(null);

        // Create the AlertaFerramenta, which fails.
        AlertaFerramentaDTO alertaFerramentaDTO = alertaFerramentaMapper.toDto(alertaFerramenta);


        restAlertaFerramentaMockMvc.perform(post("/api/alerta-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaFerramentaDTO)))
            .andExpect(status().isBadRequest());

        List<AlertaFerramenta> alertaFerramentaList = alertaFerramentaRepository.findAll();
        assertThat(alertaFerramentaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentas() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList
        restAlertaFerramentaMockMvc.perform(get("/api/alerta-ferramentas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertaFerramenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getAlertaFerramenta() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get the alertaFerramenta
        restAlertaFerramentaMockMvc.perform(get("/api/alerta-ferramentas/{id}", alertaFerramenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alertaFerramenta.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAlertaFerramenta() throws Exception {
        // Get the alertaFerramenta
        restAlertaFerramentaMockMvc.perform(get("/api/alerta-ferramentas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlertaFerramenta() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        int databaseSizeBeforeUpdate = alertaFerramentaRepository.findAll().size();

        // Update the alertaFerramenta
        AlertaFerramenta updatedAlertaFerramenta = alertaFerramentaRepository.findById(alertaFerramenta.getId()).get();
        // Disconnect from session so that the updates on updatedAlertaFerramenta are not directly saved in db
        em.detach(updatedAlertaFerramenta);
        updatedAlertaFerramenta
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        AlertaFerramentaDTO alertaFerramentaDTO = alertaFerramentaMapper.toDto(updatedAlertaFerramenta);

        restAlertaFerramentaMockMvc.perform(put("/api/alerta-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaFerramentaDTO)))
            .andExpect(status().isOk());

        // Validate the AlertaFerramenta in the database
        List<AlertaFerramenta> alertaFerramentaList = alertaFerramentaRepository.findAll();
        assertThat(alertaFerramentaList).hasSize(databaseSizeBeforeUpdate);
        AlertaFerramenta testAlertaFerramenta = alertaFerramentaList.get(alertaFerramentaList.size() - 1);
        assertThat(testAlertaFerramenta.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAlertaFerramenta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAlertaFerramenta.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAlertaFerramenta.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAlertaFerramenta() throws Exception {
        int databaseSizeBeforeUpdate = alertaFerramentaRepository.findAll().size();

        // Create the AlertaFerramenta
        AlertaFerramentaDTO alertaFerramentaDTO = alertaFerramentaMapper.toDto(alertaFerramenta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertaFerramentaMockMvc.perform(put("/api/alerta-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaFerramentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlertaFerramenta in the database
        List<AlertaFerramenta> alertaFerramentaList = alertaFerramentaRepository.findAll();
        assertThat(alertaFerramentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlertaFerramenta() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        int databaseSizeBeforeDelete = alertaFerramentaRepository.findAll().size();

        // Delete the alertaFerramenta
        restAlertaFerramentaMockMvc.perform(delete("/api/alerta-ferramentas/{id}", alertaFerramenta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlertaFerramenta> alertaFerramentaList = alertaFerramentaRepository.findAll();
        assertThat(alertaFerramentaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
