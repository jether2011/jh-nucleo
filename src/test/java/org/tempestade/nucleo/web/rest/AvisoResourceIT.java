package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Aviso;
import org.tempestade.nucleo.repository.AvisoRepository;
import org.tempestade.nucleo.service.AvisoService;
import org.tempestade.nucleo.service.dto.AvisoDTO;
import org.tempestade.nucleo.service.mapper.AvisoMapper;

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
 * Integration tests for the {@link AvisoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AvisoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENVIADO = false;
    private static final Boolean UPDATED_ENVIADO = true;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AvisoRepository avisoRepository;

    @Autowired
    private AvisoMapper avisoMapper;

    @Autowired
    private AvisoService avisoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvisoMockMvc;

    private Aviso aviso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aviso createEntity(EntityManager em) {
        Aviso aviso = new Aviso()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .enviado(DEFAULT_ENVIADO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return aviso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aviso createUpdatedEntity(EntityManager em) {
        Aviso aviso = new Aviso()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .enviado(UPDATED_ENVIADO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return aviso;
    }

    @BeforeEach
    public void initTest() {
        aviso = createEntity(em);
    }

    @Test
    @Transactional
    public void createAviso() throws Exception {
        int databaseSizeBeforeCreate = avisoRepository.findAll().size();
        // Create the Aviso
        AvisoDTO avisoDTO = avisoMapper.toDto(aviso);
        restAvisoMockMvc.perform(post("/api/avisos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoDTO)))
            .andExpect(status().isCreated());

        // Validate the Aviso in the database
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeCreate + 1);
        Aviso testAviso = avisoList.get(avisoList.size() - 1);
        assertThat(testAviso.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAviso.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAviso.isEnviado()).isEqualTo(DEFAULT_ENVIADO);
        assertThat(testAviso.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAviso.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createAvisoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avisoRepository.findAll().size();

        // Create the Aviso with an existing ID
        aviso.setId(1L);
        AvisoDTO avisoDTO = avisoMapper.toDto(aviso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvisoMockMvc.perform(post("/api/avisos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aviso in the database
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoRepository.findAll().size();
        // set the field null
        aviso.setNome(null);

        // Create the Aviso, which fails.
        AvisoDTO avisoDTO = avisoMapper.toDto(aviso);


        restAvisoMockMvc.perform(post("/api/avisos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoDTO)))
            .andExpect(status().isBadRequest());

        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoRepository.findAll().size();
        // set the field null
        aviso.setCreated(null);

        // Create the Aviso, which fails.
        AvisoDTO avisoDTO = avisoMapper.toDto(aviso);


        restAvisoMockMvc.perform(post("/api/avisos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoDTO)))
            .andExpect(status().isBadRequest());

        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvisos() throws Exception {
        // Initialize the database
        avisoRepository.saveAndFlush(aviso);

        // Get all the avisoList
        restAvisoMockMvc.perform(get("/api/avisos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aviso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].enviado").value(hasItem(DEFAULT_ENVIADO.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getAviso() throws Exception {
        // Initialize the database
        avisoRepository.saveAndFlush(aviso);

        // Get the aviso
        restAvisoMockMvc.perform(get("/api/avisos/{id}", aviso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aviso.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.enviado").value(DEFAULT_ENVIADO.booleanValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAviso() throws Exception {
        // Get the aviso
        restAvisoMockMvc.perform(get("/api/avisos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAviso() throws Exception {
        // Initialize the database
        avisoRepository.saveAndFlush(aviso);

        int databaseSizeBeforeUpdate = avisoRepository.findAll().size();

        // Update the aviso
        Aviso updatedAviso = avisoRepository.findById(aviso.getId()).get();
        // Disconnect from session so that the updates on updatedAviso are not directly saved in db
        em.detach(updatedAviso);
        updatedAviso
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .enviado(UPDATED_ENVIADO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        AvisoDTO avisoDTO = avisoMapper.toDto(updatedAviso);

        restAvisoMockMvc.perform(put("/api/avisos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoDTO)))
            .andExpect(status().isOk());

        // Validate the Aviso in the database
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeUpdate);
        Aviso testAviso = avisoList.get(avisoList.size() - 1);
        assertThat(testAviso.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAviso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAviso.isEnviado()).isEqualTo(UPDATED_ENVIADO);
        assertThat(testAviso.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAviso.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAviso() throws Exception {
        int databaseSizeBeforeUpdate = avisoRepository.findAll().size();

        // Create the Aviso
        AvisoDTO avisoDTO = avisoMapper.toDto(aviso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvisoMockMvc.perform(put("/api/avisos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aviso in the database
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAviso() throws Exception {
        // Initialize the database
        avisoRepository.saveAndFlush(aviso);

        int databaseSizeBeforeDelete = avisoRepository.findAll().size();

        // Delete the aviso
        restAvisoMockMvc.perform(delete("/api/avisos/{id}", aviso.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
