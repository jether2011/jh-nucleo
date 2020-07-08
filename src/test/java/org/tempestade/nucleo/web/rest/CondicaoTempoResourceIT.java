package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.CondicaoTempo;
import org.tempestade.nucleo.repository.CondicaoTempoRepository;
import org.tempestade.nucleo.service.CondicaoTempoService;
import org.tempestade.nucleo.service.dto.CondicaoTempoDTO;
import org.tempestade.nucleo.service.mapper.CondicaoTempoMapper;

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
 * Integration tests for the {@link CondicaoTempoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CondicaoTempoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CondicaoTempoRepository condicaoTempoRepository;

    @Autowired
    private CondicaoTempoMapper condicaoTempoMapper;

    @Autowired
    private CondicaoTempoService condicaoTempoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCondicaoTempoMockMvc;

    private CondicaoTempo condicaoTempo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CondicaoTempo createEntity(EntityManager em) {
        CondicaoTempo condicaoTempo = new CondicaoTempo()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return condicaoTempo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CondicaoTempo createUpdatedEntity(EntityManager em) {
        CondicaoTempo condicaoTempo = new CondicaoTempo()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return condicaoTempo;
    }

    @BeforeEach
    public void initTest() {
        condicaoTempo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCondicaoTempo() throws Exception {
        int databaseSizeBeforeCreate = condicaoTempoRepository.findAll().size();
        // Create the CondicaoTempo
        CondicaoTempoDTO condicaoTempoDTO = condicaoTempoMapper.toDto(condicaoTempo);
        restCondicaoTempoMockMvc.perform(post("/api/condicao-tempos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(condicaoTempoDTO)))
            .andExpect(status().isCreated());

        // Validate the CondicaoTempo in the database
        List<CondicaoTempo> condicaoTempoList = condicaoTempoRepository.findAll();
        assertThat(condicaoTempoList).hasSize(databaseSizeBeforeCreate + 1);
        CondicaoTempo testCondicaoTempo = condicaoTempoList.get(condicaoTempoList.size() - 1);
        assertThat(testCondicaoTempo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCondicaoTempo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testCondicaoTempo.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testCondicaoTempo.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createCondicaoTempoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = condicaoTempoRepository.findAll().size();

        // Create the CondicaoTempo with an existing ID
        condicaoTempo.setId(1L);
        CondicaoTempoDTO condicaoTempoDTO = condicaoTempoMapper.toDto(condicaoTempo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCondicaoTempoMockMvc.perform(post("/api/condicao-tempos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(condicaoTempoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CondicaoTempo in the database
        List<CondicaoTempo> condicaoTempoList = condicaoTempoRepository.findAll();
        assertThat(condicaoTempoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = condicaoTempoRepository.findAll().size();
        // set the field null
        condicaoTempo.setNome(null);

        // Create the CondicaoTempo, which fails.
        CondicaoTempoDTO condicaoTempoDTO = condicaoTempoMapper.toDto(condicaoTempo);


        restCondicaoTempoMockMvc.perform(post("/api/condicao-tempos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(condicaoTempoDTO)))
            .andExpect(status().isBadRequest());

        List<CondicaoTempo> condicaoTempoList = condicaoTempoRepository.findAll();
        assertThat(condicaoTempoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = condicaoTempoRepository.findAll().size();
        // set the field null
        condicaoTempo.setCreated(null);

        // Create the CondicaoTempo, which fails.
        CondicaoTempoDTO condicaoTempoDTO = condicaoTempoMapper.toDto(condicaoTempo);


        restCondicaoTempoMockMvc.perform(post("/api/condicao-tempos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(condicaoTempoDTO)))
            .andExpect(status().isBadRequest());

        List<CondicaoTempo> condicaoTempoList = condicaoTempoRepository.findAll();
        assertThat(condicaoTempoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCondicaoTempos() throws Exception {
        // Initialize the database
        condicaoTempoRepository.saveAndFlush(condicaoTempo);

        // Get all the condicaoTempoList
        restCondicaoTempoMockMvc.perform(get("/api/condicao-tempos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(condicaoTempo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getCondicaoTempo() throws Exception {
        // Initialize the database
        condicaoTempoRepository.saveAndFlush(condicaoTempo);

        // Get the condicaoTempo
        restCondicaoTempoMockMvc.perform(get("/api/condicao-tempos/{id}", condicaoTempo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(condicaoTempo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCondicaoTempo() throws Exception {
        // Get the condicaoTempo
        restCondicaoTempoMockMvc.perform(get("/api/condicao-tempos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCondicaoTempo() throws Exception {
        // Initialize the database
        condicaoTempoRepository.saveAndFlush(condicaoTempo);

        int databaseSizeBeforeUpdate = condicaoTempoRepository.findAll().size();

        // Update the condicaoTempo
        CondicaoTempo updatedCondicaoTempo = condicaoTempoRepository.findById(condicaoTempo.getId()).get();
        // Disconnect from session so that the updates on updatedCondicaoTempo are not directly saved in db
        em.detach(updatedCondicaoTempo);
        updatedCondicaoTempo
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        CondicaoTempoDTO condicaoTempoDTO = condicaoTempoMapper.toDto(updatedCondicaoTempo);

        restCondicaoTempoMockMvc.perform(put("/api/condicao-tempos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(condicaoTempoDTO)))
            .andExpect(status().isOk());

        // Validate the CondicaoTempo in the database
        List<CondicaoTempo> condicaoTempoList = condicaoTempoRepository.findAll();
        assertThat(condicaoTempoList).hasSize(databaseSizeBeforeUpdate);
        CondicaoTempo testCondicaoTempo = condicaoTempoList.get(condicaoTempoList.size() - 1);
        assertThat(testCondicaoTempo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCondicaoTempo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testCondicaoTempo.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testCondicaoTempo.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingCondicaoTempo() throws Exception {
        int databaseSizeBeforeUpdate = condicaoTempoRepository.findAll().size();

        // Create the CondicaoTempo
        CondicaoTempoDTO condicaoTempoDTO = condicaoTempoMapper.toDto(condicaoTempo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCondicaoTempoMockMvc.perform(put("/api/condicao-tempos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(condicaoTempoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CondicaoTempo in the database
        List<CondicaoTempo> condicaoTempoList = condicaoTempoRepository.findAll();
        assertThat(condicaoTempoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCondicaoTempo() throws Exception {
        // Initialize the database
        condicaoTempoRepository.saveAndFlush(condicaoTempo);

        int databaseSizeBeforeDelete = condicaoTempoRepository.findAll().size();

        // Delete the condicaoTempo
        restCondicaoTempoMockMvc.perform(delete("/api/condicao-tempos/{id}", condicaoTempo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CondicaoTempo> condicaoTempoList = condicaoTempoRepository.findAll();
        assertThat(condicaoTempoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
