package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.JornadaTrabalho;
import org.tempestade.nucleo.repository.JornadaTrabalhoRepository;
import org.tempestade.nucleo.service.JornadaTrabalhoService;
import org.tempestade.nucleo.service.dto.JornadaTrabalhoDTO;
import org.tempestade.nucleo.service.mapper.JornadaTrabalhoMapper;

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
 * Integration tests for the {@link JornadaTrabalhoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JornadaTrabalhoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_HORAINICIO = "06:23:02";
    private static final String UPDATED_HORAINICIO = "11:41:41";

    private static final String DEFAULT_DURACAO = "12:21:04";
    private static final String UPDATED_DURACAO = "23:12:29";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private JornadaTrabalhoRepository jornadaTrabalhoRepository;

    @Autowired
    private JornadaTrabalhoMapper jornadaTrabalhoMapper;

    @Autowired
    private JornadaTrabalhoService jornadaTrabalhoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJornadaTrabalhoMockMvc;

    private JornadaTrabalho jornadaTrabalho;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JornadaTrabalho createEntity(EntityManager em) {
        JornadaTrabalho jornadaTrabalho = new JornadaTrabalho()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .horainicio(DEFAULT_HORAINICIO)
            .duracao(DEFAULT_DURACAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return jornadaTrabalho;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JornadaTrabalho createUpdatedEntity(EntityManager em) {
        JornadaTrabalho jornadaTrabalho = new JornadaTrabalho()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .horainicio(UPDATED_HORAINICIO)
            .duracao(UPDATED_DURACAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return jornadaTrabalho;
    }

    @BeforeEach
    public void initTest() {
        jornadaTrabalho = createEntity(em);
    }

    @Test
    @Transactional
    public void createJornadaTrabalho() throws Exception {
        int databaseSizeBeforeCreate = jornadaTrabalhoRepository.findAll().size();
        // Create the JornadaTrabalho
        JornadaTrabalhoDTO jornadaTrabalhoDTO = jornadaTrabalhoMapper.toDto(jornadaTrabalho);
        restJornadaTrabalhoMockMvc.perform(post("/api/jornada-trabalhos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jornadaTrabalhoDTO)))
            .andExpect(status().isCreated());

        // Validate the JornadaTrabalho in the database
        List<JornadaTrabalho> jornadaTrabalhoList = jornadaTrabalhoRepository.findAll();
        assertThat(jornadaTrabalhoList).hasSize(databaseSizeBeforeCreate + 1);
        JornadaTrabalho testJornadaTrabalho = jornadaTrabalhoList.get(jornadaTrabalhoList.size() - 1);
        assertThat(testJornadaTrabalho.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testJornadaTrabalho.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testJornadaTrabalho.getHorainicio()).isEqualTo(DEFAULT_HORAINICIO);
        assertThat(testJornadaTrabalho.getDuracao()).isEqualTo(DEFAULT_DURACAO);
        assertThat(testJornadaTrabalho.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testJornadaTrabalho.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createJornadaTrabalhoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jornadaTrabalhoRepository.findAll().size();

        // Create the JornadaTrabalho with an existing ID
        jornadaTrabalho.setId(1L);
        JornadaTrabalhoDTO jornadaTrabalhoDTO = jornadaTrabalhoMapper.toDto(jornadaTrabalho);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJornadaTrabalhoMockMvc.perform(post("/api/jornada-trabalhos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jornadaTrabalhoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JornadaTrabalho in the database
        List<JornadaTrabalho> jornadaTrabalhoList = jornadaTrabalhoRepository.findAll();
        assertThat(jornadaTrabalhoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = jornadaTrabalhoRepository.findAll().size();
        // set the field null
        jornadaTrabalho.setNome(null);

        // Create the JornadaTrabalho, which fails.
        JornadaTrabalhoDTO jornadaTrabalhoDTO = jornadaTrabalhoMapper.toDto(jornadaTrabalho);


        restJornadaTrabalhoMockMvc.perform(post("/api/jornada-trabalhos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jornadaTrabalhoDTO)))
            .andExpect(status().isBadRequest());

        List<JornadaTrabalho> jornadaTrabalhoList = jornadaTrabalhoRepository.findAll();
        assertThat(jornadaTrabalhoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = jornadaTrabalhoRepository.findAll().size();
        // set the field null
        jornadaTrabalho.setDescricao(null);

        // Create the JornadaTrabalho, which fails.
        JornadaTrabalhoDTO jornadaTrabalhoDTO = jornadaTrabalhoMapper.toDto(jornadaTrabalho);


        restJornadaTrabalhoMockMvc.perform(post("/api/jornada-trabalhos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jornadaTrabalhoDTO)))
            .andExpect(status().isBadRequest());

        List<JornadaTrabalho> jornadaTrabalhoList = jornadaTrabalhoRepository.findAll();
        assertThat(jornadaTrabalhoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = jornadaTrabalhoRepository.findAll().size();
        // set the field null
        jornadaTrabalho.setCreated(null);

        // Create the JornadaTrabalho, which fails.
        JornadaTrabalhoDTO jornadaTrabalhoDTO = jornadaTrabalhoMapper.toDto(jornadaTrabalho);


        restJornadaTrabalhoMockMvc.perform(post("/api/jornada-trabalhos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jornadaTrabalhoDTO)))
            .andExpect(status().isBadRequest());

        List<JornadaTrabalho> jornadaTrabalhoList = jornadaTrabalhoRepository.findAll();
        assertThat(jornadaTrabalhoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhos() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList
        restJornadaTrabalhoMockMvc.perform(get("/api/jornada-trabalhos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jornadaTrabalho.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].horainicio").value(hasItem(DEFAULT_HORAINICIO)))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getJornadaTrabalho() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get the jornadaTrabalho
        restJornadaTrabalhoMockMvc.perform(get("/api/jornada-trabalhos/{id}", jornadaTrabalho.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jornadaTrabalho.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.horainicio").value(DEFAULT_HORAINICIO))
            .andExpect(jsonPath("$.duracao").value(DEFAULT_DURACAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingJornadaTrabalho() throws Exception {
        // Get the jornadaTrabalho
        restJornadaTrabalhoMockMvc.perform(get("/api/jornada-trabalhos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJornadaTrabalho() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        int databaseSizeBeforeUpdate = jornadaTrabalhoRepository.findAll().size();

        // Update the jornadaTrabalho
        JornadaTrabalho updatedJornadaTrabalho = jornadaTrabalhoRepository.findById(jornadaTrabalho.getId()).get();
        // Disconnect from session so that the updates on updatedJornadaTrabalho are not directly saved in db
        em.detach(updatedJornadaTrabalho);
        updatedJornadaTrabalho
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .horainicio(UPDATED_HORAINICIO)
            .duracao(UPDATED_DURACAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        JornadaTrabalhoDTO jornadaTrabalhoDTO = jornadaTrabalhoMapper.toDto(updatedJornadaTrabalho);

        restJornadaTrabalhoMockMvc.perform(put("/api/jornada-trabalhos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jornadaTrabalhoDTO)))
            .andExpect(status().isOk());

        // Validate the JornadaTrabalho in the database
        List<JornadaTrabalho> jornadaTrabalhoList = jornadaTrabalhoRepository.findAll();
        assertThat(jornadaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
        JornadaTrabalho testJornadaTrabalho = jornadaTrabalhoList.get(jornadaTrabalhoList.size() - 1);
        assertThat(testJornadaTrabalho.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testJornadaTrabalho.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testJornadaTrabalho.getHorainicio()).isEqualTo(UPDATED_HORAINICIO);
        assertThat(testJornadaTrabalho.getDuracao()).isEqualTo(UPDATED_DURACAO);
        assertThat(testJornadaTrabalho.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testJornadaTrabalho.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingJornadaTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = jornadaTrabalhoRepository.findAll().size();

        // Create the JornadaTrabalho
        JornadaTrabalhoDTO jornadaTrabalhoDTO = jornadaTrabalhoMapper.toDto(jornadaTrabalho);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJornadaTrabalhoMockMvc.perform(put("/api/jornada-trabalhos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jornadaTrabalhoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JornadaTrabalho in the database
        List<JornadaTrabalho> jornadaTrabalhoList = jornadaTrabalhoRepository.findAll();
        assertThat(jornadaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJornadaTrabalho() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        int databaseSizeBeforeDelete = jornadaTrabalhoRepository.findAll().size();

        // Delete the jornadaTrabalho
        restJornadaTrabalhoMockMvc.perform(delete("/api/jornada-trabalhos/{id}", jornadaTrabalho.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JornadaTrabalho> jornadaTrabalhoList = jornadaTrabalhoRepository.findAll();
        assertThat(jornadaTrabalhoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
