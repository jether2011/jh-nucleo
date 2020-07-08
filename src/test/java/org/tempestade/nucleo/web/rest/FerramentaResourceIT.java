package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Ferramenta;
import org.tempestade.nucleo.repository.FerramentaRepository;
import org.tempestade.nucleo.service.FerramentaService;
import org.tempestade.nucleo.service.dto.FerramentaDTO;
import org.tempestade.nucleo.service.mapper.FerramentaMapper;

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
 * Integration tests for the {@link FerramentaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FerramentaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_UPDATED = 1;
    private static final Integer UPDATED_UPDATED = 2;

    @Autowired
    private FerramentaRepository ferramentaRepository;

    @Autowired
    private FerramentaMapper ferramentaMapper;

    @Autowired
    private FerramentaService ferramentaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFerramentaMockMvc;

    private Ferramenta ferramenta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ferramenta createEntity(EntityManager em) {
        Ferramenta ferramenta = new Ferramenta()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return ferramenta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ferramenta createUpdatedEntity(EntityManager em) {
        Ferramenta ferramenta = new Ferramenta()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return ferramenta;
    }

    @BeforeEach
    public void initTest() {
        ferramenta = createEntity(em);
    }

    @Test
    @Transactional
    public void createFerramenta() throws Exception {
        int databaseSizeBeforeCreate = ferramentaRepository.findAll().size();
        // Create the Ferramenta
        FerramentaDTO ferramentaDTO = ferramentaMapper.toDto(ferramenta);
        restFerramentaMockMvc.perform(post("/api/ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ferramentaDTO)))
            .andExpect(status().isCreated());

        // Validate the Ferramenta in the database
        List<Ferramenta> ferramentaList = ferramentaRepository.findAll();
        assertThat(ferramentaList).hasSize(databaseSizeBeforeCreate + 1);
        Ferramenta testFerramenta = ferramentaList.get(ferramentaList.size() - 1);
        assertThat(testFerramenta.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testFerramenta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testFerramenta.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testFerramenta.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createFerramentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ferramentaRepository.findAll().size();

        // Create the Ferramenta with an existing ID
        ferramenta.setId(1L);
        FerramentaDTO ferramentaDTO = ferramentaMapper.toDto(ferramenta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFerramentaMockMvc.perform(post("/api/ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ferramentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ferramenta in the database
        List<Ferramenta> ferramentaList = ferramentaRepository.findAll();
        assertThat(ferramentaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ferramentaRepository.findAll().size();
        // set the field null
        ferramenta.setNome(null);

        // Create the Ferramenta, which fails.
        FerramentaDTO ferramentaDTO = ferramentaMapper.toDto(ferramenta);


        restFerramentaMockMvc.perform(post("/api/ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ferramentaDTO)))
            .andExpect(status().isBadRequest());

        List<Ferramenta> ferramentaList = ferramentaRepository.findAll();
        assertThat(ferramentaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = ferramentaRepository.findAll().size();
        // set the field null
        ferramenta.setCreated(null);

        // Create the Ferramenta, which fails.
        FerramentaDTO ferramentaDTO = ferramentaMapper.toDto(ferramenta);


        restFerramentaMockMvc.perform(post("/api/ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ferramentaDTO)))
            .andExpect(status().isBadRequest());

        List<Ferramenta> ferramentaList = ferramentaRepository.findAll();
        assertThat(ferramentaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFerramentas() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList
        restFerramentaMockMvc.perform(get("/api/ferramentas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ferramenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED)));
    }
    
    @Test
    @Transactional
    public void getFerramenta() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get the ferramenta
        restFerramentaMockMvc.perform(get("/api/ferramentas/{id}", ferramenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ferramenta.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED));
    }
    @Test
    @Transactional
    public void getNonExistingFerramenta() throws Exception {
        // Get the ferramenta
        restFerramentaMockMvc.perform(get("/api/ferramentas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFerramenta() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        int databaseSizeBeforeUpdate = ferramentaRepository.findAll().size();

        // Update the ferramenta
        Ferramenta updatedFerramenta = ferramentaRepository.findById(ferramenta.getId()).get();
        // Disconnect from session so that the updates on updatedFerramenta are not directly saved in db
        em.detach(updatedFerramenta);
        updatedFerramenta
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        FerramentaDTO ferramentaDTO = ferramentaMapper.toDto(updatedFerramenta);

        restFerramentaMockMvc.perform(put("/api/ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ferramentaDTO)))
            .andExpect(status().isOk());

        // Validate the Ferramenta in the database
        List<Ferramenta> ferramentaList = ferramentaRepository.findAll();
        assertThat(ferramentaList).hasSize(databaseSizeBeforeUpdate);
        Ferramenta testFerramenta = ferramentaList.get(ferramentaList.size() - 1);
        assertThat(testFerramenta.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFerramenta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testFerramenta.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testFerramenta.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingFerramenta() throws Exception {
        int databaseSizeBeforeUpdate = ferramentaRepository.findAll().size();

        // Create the Ferramenta
        FerramentaDTO ferramentaDTO = ferramentaMapper.toDto(ferramenta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFerramentaMockMvc.perform(put("/api/ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ferramentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ferramenta in the database
        List<Ferramenta> ferramentaList = ferramentaRepository.findAll();
        assertThat(ferramentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFerramenta() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        int databaseSizeBeforeDelete = ferramentaRepository.findAll().size();

        // Delete the ferramenta
        restFerramentaMockMvc.perform(delete("/api/ferramentas/{id}", ferramenta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ferramenta> ferramentaList = ferramentaRepository.findAll();
        assertThat(ferramentaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
