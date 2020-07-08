package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.ContatoAlvo;
import org.tempestade.nucleo.repository.ContatoAlvoRepository;
import org.tempestade.nucleo.service.ContatoAlvoService;
import org.tempestade.nucleo.service.dto.ContatoAlvoDTO;
import org.tempestade.nucleo.service.mapper.ContatoAlvoMapper;

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
 * Integration tests for the {@link ContatoAlvoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContatoAlvoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ContatoAlvoRepository contatoAlvoRepository;

    @Autowired
    private ContatoAlvoMapper contatoAlvoMapper;

    @Autowired
    private ContatoAlvoService contatoAlvoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContatoAlvoMockMvc;

    private ContatoAlvo contatoAlvo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContatoAlvo createEntity(EntityManager em) {
        ContatoAlvo contatoAlvo = new ContatoAlvo()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return contatoAlvo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContatoAlvo createUpdatedEntity(EntityManager em) {
        ContatoAlvo contatoAlvo = new ContatoAlvo()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return contatoAlvo;
    }

    @BeforeEach
    public void initTest() {
        contatoAlvo = createEntity(em);
    }

    @Test
    @Transactional
    public void createContatoAlvo() throws Exception {
        int databaseSizeBeforeCreate = contatoAlvoRepository.findAll().size();
        // Create the ContatoAlvo
        ContatoAlvoDTO contatoAlvoDTO = contatoAlvoMapper.toDto(contatoAlvo);
        restContatoAlvoMockMvc.perform(post("/api/contato-alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoAlvoDTO)))
            .andExpect(status().isCreated());

        // Validate the ContatoAlvo in the database
        List<ContatoAlvo> contatoAlvoList = contatoAlvoRepository.findAll();
        assertThat(contatoAlvoList).hasSize(databaseSizeBeforeCreate + 1);
        ContatoAlvo testContatoAlvo = contatoAlvoList.get(contatoAlvoList.size() - 1);
        assertThat(testContatoAlvo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testContatoAlvo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testContatoAlvo.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testContatoAlvo.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createContatoAlvoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contatoAlvoRepository.findAll().size();

        // Create the ContatoAlvo with an existing ID
        contatoAlvo.setId(1L);
        ContatoAlvoDTO contatoAlvoDTO = contatoAlvoMapper.toDto(contatoAlvo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContatoAlvoMockMvc.perform(post("/api/contato-alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoAlvoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContatoAlvo in the database
        List<ContatoAlvo> contatoAlvoList = contatoAlvoRepository.findAll();
        assertThat(contatoAlvoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoAlvoRepository.findAll().size();
        // set the field null
        contatoAlvo.setNome(null);

        // Create the ContatoAlvo, which fails.
        ContatoAlvoDTO contatoAlvoDTO = contatoAlvoMapper.toDto(contatoAlvo);


        restContatoAlvoMockMvc.perform(post("/api/contato-alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoAlvoDTO)))
            .andExpect(status().isBadRequest());

        List<ContatoAlvo> contatoAlvoList = contatoAlvoRepository.findAll();
        assertThat(contatoAlvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoAlvoRepository.findAll().size();
        // set the field null
        contatoAlvo.setCreated(null);

        // Create the ContatoAlvo, which fails.
        ContatoAlvoDTO contatoAlvoDTO = contatoAlvoMapper.toDto(contatoAlvo);


        restContatoAlvoMockMvc.perform(post("/api/contato-alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoAlvoDTO)))
            .andExpect(status().isBadRequest());

        List<ContatoAlvo> contatoAlvoList = contatoAlvoRepository.findAll();
        assertThat(contatoAlvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContatoAlvos() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList
        restContatoAlvoMockMvc.perform(get("/api/contato-alvos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contatoAlvo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getContatoAlvo() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get the contatoAlvo
        restContatoAlvoMockMvc.perform(get("/api/contato-alvos/{id}", contatoAlvo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contatoAlvo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingContatoAlvo() throws Exception {
        // Get the contatoAlvo
        restContatoAlvoMockMvc.perform(get("/api/contato-alvos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContatoAlvo() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        int databaseSizeBeforeUpdate = contatoAlvoRepository.findAll().size();

        // Update the contatoAlvo
        ContatoAlvo updatedContatoAlvo = contatoAlvoRepository.findById(contatoAlvo.getId()).get();
        // Disconnect from session so that the updates on updatedContatoAlvo are not directly saved in db
        em.detach(updatedContatoAlvo);
        updatedContatoAlvo
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        ContatoAlvoDTO contatoAlvoDTO = contatoAlvoMapper.toDto(updatedContatoAlvo);

        restContatoAlvoMockMvc.perform(put("/api/contato-alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoAlvoDTO)))
            .andExpect(status().isOk());

        // Validate the ContatoAlvo in the database
        List<ContatoAlvo> contatoAlvoList = contatoAlvoRepository.findAll();
        assertThat(contatoAlvoList).hasSize(databaseSizeBeforeUpdate);
        ContatoAlvo testContatoAlvo = contatoAlvoList.get(contatoAlvoList.size() - 1);
        assertThat(testContatoAlvo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testContatoAlvo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testContatoAlvo.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testContatoAlvo.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingContatoAlvo() throws Exception {
        int databaseSizeBeforeUpdate = contatoAlvoRepository.findAll().size();

        // Create the ContatoAlvo
        ContatoAlvoDTO contatoAlvoDTO = contatoAlvoMapper.toDto(contatoAlvo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContatoAlvoMockMvc.perform(put("/api/contato-alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoAlvoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContatoAlvo in the database
        List<ContatoAlvo> contatoAlvoList = contatoAlvoRepository.findAll();
        assertThat(contatoAlvoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContatoAlvo() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        int databaseSizeBeforeDelete = contatoAlvoRepository.findAll().size();

        // Delete the contatoAlvo
        restContatoAlvoMockMvc.perform(delete("/api/contato-alvos/{id}", contatoAlvo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContatoAlvo> contatoAlvoList = contatoAlvoRepository.findAll();
        assertThat(contatoAlvoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
