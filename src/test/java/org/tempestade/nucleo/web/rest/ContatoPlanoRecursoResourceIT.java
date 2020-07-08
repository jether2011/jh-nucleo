package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.ContatoPlanoRecurso;
import org.tempestade.nucleo.repository.ContatoPlanoRecursoRepository;
import org.tempestade.nucleo.service.ContatoPlanoRecursoService;
import org.tempestade.nucleo.service.dto.ContatoPlanoRecursoDTO;
import org.tempestade.nucleo.service.mapper.ContatoPlanoRecursoMapper;

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
 * Integration tests for the {@link ContatoPlanoRecursoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContatoPlanoRecursoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ContatoPlanoRecursoRepository contatoPlanoRecursoRepository;

    @Autowired
    private ContatoPlanoRecursoMapper contatoPlanoRecursoMapper;

    @Autowired
    private ContatoPlanoRecursoService contatoPlanoRecursoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContatoPlanoRecursoMockMvc;

    private ContatoPlanoRecurso contatoPlanoRecurso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContatoPlanoRecurso createEntity(EntityManager em) {
        ContatoPlanoRecurso contatoPlanoRecurso = new ContatoPlanoRecurso()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return contatoPlanoRecurso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContatoPlanoRecurso createUpdatedEntity(EntityManager em) {
        ContatoPlanoRecurso contatoPlanoRecurso = new ContatoPlanoRecurso()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return contatoPlanoRecurso;
    }

    @BeforeEach
    public void initTest() {
        contatoPlanoRecurso = createEntity(em);
    }

    @Test
    @Transactional
    public void createContatoPlanoRecurso() throws Exception {
        int databaseSizeBeforeCreate = contatoPlanoRecursoRepository.findAll().size();
        // Create the ContatoPlanoRecurso
        ContatoPlanoRecursoDTO contatoPlanoRecursoDTO = contatoPlanoRecursoMapper.toDto(contatoPlanoRecurso);
        restContatoPlanoRecursoMockMvc.perform(post("/api/contato-plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoPlanoRecursoDTO)))
            .andExpect(status().isCreated());

        // Validate the ContatoPlanoRecurso in the database
        List<ContatoPlanoRecurso> contatoPlanoRecursoList = contatoPlanoRecursoRepository.findAll();
        assertThat(contatoPlanoRecursoList).hasSize(databaseSizeBeforeCreate + 1);
        ContatoPlanoRecurso testContatoPlanoRecurso = contatoPlanoRecursoList.get(contatoPlanoRecursoList.size() - 1);
        assertThat(testContatoPlanoRecurso.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testContatoPlanoRecurso.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testContatoPlanoRecurso.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testContatoPlanoRecurso.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createContatoPlanoRecursoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contatoPlanoRecursoRepository.findAll().size();

        // Create the ContatoPlanoRecurso with an existing ID
        contatoPlanoRecurso.setId(1L);
        ContatoPlanoRecursoDTO contatoPlanoRecursoDTO = contatoPlanoRecursoMapper.toDto(contatoPlanoRecurso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContatoPlanoRecursoMockMvc.perform(post("/api/contato-plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoPlanoRecursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContatoPlanoRecurso in the database
        List<ContatoPlanoRecurso> contatoPlanoRecursoList = contatoPlanoRecursoRepository.findAll();
        assertThat(contatoPlanoRecursoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoPlanoRecursoRepository.findAll().size();
        // set the field null
        contatoPlanoRecurso.setNome(null);

        // Create the ContatoPlanoRecurso, which fails.
        ContatoPlanoRecursoDTO contatoPlanoRecursoDTO = contatoPlanoRecursoMapper.toDto(contatoPlanoRecurso);


        restContatoPlanoRecursoMockMvc.perform(post("/api/contato-plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoPlanoRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<ContatoPlanoRecurso> contatoPlanoRecursoList = contatoPlanoRecursoRepository.findAll();
        assertThat(contatoPlanoRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoPlanoRecursoRepository.findAll().size();
        // set the field null
        contatoPlanoRecurso.setCreated(null);

        // Create the ContatoPlanoRecurso, which fails.
        ContatoPlanoRecursoDTO contatoPlanoRecursoDTO = contatoPlanoRecursoMapper.toDto(contatoPlanoRecurso);


        restContatoPlanoRecursoMockMvc.perform(post("/api/contato-plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoPlanoRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<ContatoPlanoRecurso> contatoPlanoRecursoList = contatoPlanoRecursoRepository.findAll();
        assertThat(contatoPlanoRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursos() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList
        restContatoPlanoRecursoMockMvc.perform(get("/api/contato-plano-recursos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contatoPlanoRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getContatoPlanoRecurso() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get the contatoPlanoRecurso
        restContatoPlanoRecursoMockMvc.perform(get("/api/contato-plano-recursos/{id}", contatoPlanoRecurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contatoPlanoRecurso.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingContatoPlanoRecurso() throws Exception {
        // Get the contatoPlanoRecurso
        restContatoPlanoRecursoMockMvc.perform(get("/api/contato-plano-recursos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContatoPlanoRecurso() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        int databaseSizeBeforeUpdate = contatoPlanoRecursoRepository.findAll().size();

        // Update the contatoPlanoRecurso
        ContatoPlanoRecurso updatedContatoPlanoRecurso = contatoPlanoRecursoRepository.findById(contatoPlanoRecurso.getId()).get();
        // Disconnect from session so that the updates on updatedContatoPlanoRecurso are not directly saved in db
        em.detach(updatedContatoPlanoRecurso);
        updatedContatoPlanoRecurso
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        ContatoPlanoRecursoDTO contatoPlanoRecursoDTO = contatoPlanoRecursoMapper.toDto(updatedContatoPlanoRecurso);

        restContatoPlanoRecursoMockMvc.perform(put("/api/contato-plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoPlanoRecursoDTO)))
            .andExpect(status().isOk());

        // Validate the ContatoPlanoRecurso in the database
        List<ContatoPlanoRecurso> contatoPlanoRecursoList = contatoPlanoRecursoRepository.findAll();
        assertThat(contatoPlanoRecursoList).hasSize(databaseSizeBeforeUpdate);
        ContatoPlanoRecurso testContatoPlanoRecurso = contatoPlanoRecursoList.get(contatoPlanoRecursoList.size() - 1);
        assertThat(testContatoPlanoRecurso.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testContatoPlanoRecurso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testContatoPlanoRecurso.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testContatoPlanoRecurso.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingContatoPlanoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = contatoPlanoRecursoRepository.findAll().size();

        // Create the ContatoPlanoRecurso
        ContatoPlanoRecursoDTO contatoPlanoRecursoDTO = contatoPlanoRecursoMapper.toDto(contatoPlanoRecurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContatoPlanoRecursoMockMvc.perform(put("/api/contato-plano-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoPlanoRecursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContatoPlanoRecurso in the database
        List<ContatoPlanoRecurso> contatoPlanoRecursoList = contatoPlanoRecursoRepository.findAll();
        assertThat(contatoPlanoRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContatoPlanoRecurso() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        int databaseSizeBeforeDelete = contatoPlanoRecursoRepository.findAll().size();

        // Delete the contatoPlanoRecurso
        restContatoPlanoRecursoMockMvc.perform(delete("/api/contato-plano-recursos/{id}", contatoPlanoRecurso.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContatoPlanoRecurso> contatoPlanoRecursoList = contatoPlanoRecursoRepository.findAll();
        assertThat(contatoPlanoRecursoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
