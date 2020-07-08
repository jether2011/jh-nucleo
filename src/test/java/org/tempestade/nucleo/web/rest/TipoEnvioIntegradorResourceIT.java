package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.TipoEnvioIntegrador;
import org.tempestade.nucleo.repository.TipoEnvioIntegradorRepository;
import org.tempestade.nucleo.service.TipoEnvioIntegradorService;
import org.tempestade.nucleo.service.dto.TipoEnvioIntegradorDTO;
import org.tempestade.nucleo.service.mapper.TipoEnvioIntegradorMapper;

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
 * Integration tests for the {@link TipoEnvioIntegradorResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoEnvioIntegradorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TipoEnvioIntegradorRepository tipoEnvioIntegradorRepository;

    @Autowired
    private TipoEnvioIntegradorMapper tipoEnvioIntegradorMapper;

    @Autowired
    private TipoEnvioIntegradorService tipoEnvioIntegradorService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoEnvioIntegradorMockMvc;

    private TipoEnvioIntegrador tipoEnvioIntegrador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEnvioIntegrador createEntity(EntityManager em) {
        TipoEnvioIntegrador tipoEnvioIntegrador = new TipoEnvioIntegrador()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .ativo(DEFAULT_ATIVO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return tipoEnvioIntegrador;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEnvioIntegrador createUpdatedEntity(EntityManager em) {
        TipoEnvioIntegrador tipoEnvioIntegrador = new TipoEnvioIntegrador()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .ativo(UPDATED_ATIVO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return tipoEnvioIntegrador;
    }

    @BeforeEach
    public void initTest() {
        tipoEnvioIntegrador = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoEnvioIntegrador() throws Exception {
        int databaseSizeBeforeCreate = tipoEnvioIntegradorRepository.findAll().size();
        // Create the TipoEnvioIntegrador
        TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO = tipoEnvioIntegradorMapper.toDto(tipoEnvioIntegrador);
        restTipoEnvioIntegradorMockMvc.perform(post("/api/tipo-envio-integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioIntegradorDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoEnvioIntegrador in the database
        List<TipoEnvioIntegrador> tipoEnvioIntegradorList = tipoEnvioIntegradorRepository.findAll();
        assertThat(tipoEnvioIntegradorList).hasSize(databaseSizeBeforeCreate + 1);
        TipoEnvioIntegrador testTipoEnvioIntegrador = tipoEnvioIntegradorList.get(tipoEnvioIntegradorList.size() - 1);
        assertThat(testTipoEnvioIntegrador.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTipoEnvioIntegrador.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTipoEnvioIntegrador.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testTipoEnvioIntegrador.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTipoEnvioIntegrador.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createTipoEnvioIntegradorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoEnvioIntegradorRepository.findAll().size();

        // Create the TipoEnvioIntegrador with an existing ID
        tipoEnvioIntegrador.setId(1L);
        TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO = tipoEnvioIntegradorMapper.toDto(tipoEnvioIntegrador);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoEnvioIntegradorMockMvc.perform(post("/api/tipo-envio-integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioIntegradorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoEnvioIntegrador in the database
        List<TipoEnvioIntegrador> tipoEnvioIntegradorList = tipoEnvioIntegradorRepository.findAll();
        assertThat(tipoEnvioIntegradorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoEnvioIntegradorRepository.findAll().size();
        // set the field null
        tipoEnvioIntegrador.setName(null);

        // Create the TipoEnvioIntegrador, which fails.
        TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO = tipoEnvioIntegradorMapper.toDto(tipoEnvioIntegrador);


        restTipoEnvioIntegradorMockMvc.perform(post("/api/tipo-envio-integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioIntegradorDTO)))
            .andExpect(status().isBadRequest());

        List<TipoEnvioIntegrador> tipoEnvioIntegradorList = tipoEnvioIntegradorRepository.findAll();
        assertThat(tipoEnvioIntegradorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoEnvioIntegradorRepository.findAll().size();
        // set the field null
        tipoEnvioIntegrador.setDescricao(null);

        // Create the TipoEnvioIntegrador, which fails.
        TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO = tipoEnvioIntegradorMapper.toDto(tipoEnvioIntegrador);


        restTipoEnvioIntegradorMockMvc.perform(post("/api/tipo-envio-integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioIntegradorDTO)))
            .andExpect(status().isBadRequest());

        List<TipoEnvioIntegrador> tipoEnvioIntegradorList = tipoEnvioIntegradorRepository.findAll();
        assertThat(tipoEnvioIntegradorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoEnvioIntegradorRepository.findAll().size();
        // set the field null
        tipoEnvioIntegrador.setCreated(null);

        // Create the TipoEnvioIntegrador, which fails.
        TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO = tipoEnvioIntegradorMapper.toDto(tipoEnvioIntegrador);


        restTipoEnvioIntegradorMockMvc.perform(post("/api/tipo-envio-integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioIntegradorDTO)))
            .andExpect(status().isBadRequest());

        List<TipoEnvioIntegrador> tipoEnvioIntegradorList = tipoEnvioIntegradorRepository.findAll();
        assertThat(tipoEnvioIntegradorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradors() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList
        restTipoEnvioIntegradorMockMvc.perform(get("/api/tipo-envio-integradors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoEnvioIntegrador.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoEnvioIntegrador() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get the tipoEnvioIntegrador
        restTipoEnvioIntegradorMockMvc.perform(get("/api/tipo-envio-integradors/{id}", tipoEnvioIntegrador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoEnvioIntegrador.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTipoEnvioIntegrador() throws Exception {
        // Get the tipoEnvioIntegrador
        restTipoEnvioIntegradorMockMvc.perform(get("/api/tipo-envio-integradors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoEnvioIntegrador() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        int databaseSizeBeforeUpdate = tipoEnvioIntegradorRepository.findAll().size();

        // Update the tipoEnvioIntegrador
        TipoEnvioIntegrador updatedTipoEnvioIntegrador = tipoEnvioIntegradorRepository.findById(tipoEnvioIntegrador.getId()).get();
        // Disconnect from session so that the updates on updatedTipoEnvioIntegrador are not directly saved in db
        em.detach(updatedTipoEnvioIntegrador);
        updatedTipoEnvioIntegrador
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .ativo(UPDATED_ATIVO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO = tipoEnvioIntegradorMapper.toDto(updatedTipoEnvioIntegrador);

        restTipoEnvioIntegradorMockMvc.perform(put("/api/tipo-envio-integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioIntegradorDTO)))
            .andExpect(status().isOk());

        // Validate the TipoEnvioIntegrador in the database
        List<TipoEnvioIntegrador> tipoEnvioIntegradorList = tipoEnvioIntegradorRepository.findAll();
        assertThat(tipoEnvioIntegradorList).hasSize(databaseSizeBeforeUpdate);
        TipoEnvioIntegrador testTipoEnvioIntegrador = tipoEnvioIntegradorList.get(tipoEnvioIntegradorList.size() - 1);
        assertThat(testTipoEnvioIntegrador.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTipoEnvioIntegrador.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTipoEnvioIntegrador.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testTipoEnvioIntegrador.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTipoEnvioIntegrador.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoEnvioIntegrador() throws Exception {
        int databaseSizeBeforeUpdate = tipoEnvioIntegradorRepository.findAll().size();

        // Create the TipoEnvioIntegrador
        TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO = tipoEnvioIntegradorMapper.toDto(tipoEnvioIntegrador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoEnvioIntegradorMockMvc.perform(put("/api/tipo-envio-integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioIntegradorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoEnvioIntegrador in the database
        List<TipoEnvioIntegrador> tipoEnvioIntegradorList = tipoEnvioIntegradorRepository.findAll();
        assertThat(tipoEnvioIntegradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoEnvioIntegrador() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        int databaseSizeBeforeDelete = tipoEnvioIntegradorRepository.findAll().size();

        // Delete the tipoEnvioIntegrador
        restTipoEnvioIntegradorMockMvc.perform(delete("/api/tipo-envio-integradors/{id}", tipoEnvioIntegrador.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoEnvioIntegrador> tipoEnvioIntegradorList = tipoEnvioIntegradorRepository.findAll();
        assertThat(tipoEnvioIntegradorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
