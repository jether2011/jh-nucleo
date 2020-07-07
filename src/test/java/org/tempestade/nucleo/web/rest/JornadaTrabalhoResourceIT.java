package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.JornadaTrabalho;
import org.tempestade.nucleo.domain.Plano;
import org.tempestade.nucleo.domain.DiaSemana;
import org.tempestade.nucleo.repository.JornadaTrabalhoRepository;
import org.tempestade.nucleo.service.JornadaTrabalhoService;
import org.tempestade.nucleo.service.dto.JornadaTrabalhoDTO;
import org.tempestade.nucleo.service.mapper.JornadaTrabalhoMapper;
import org.tempestade.nucleo.service.dto.JornadaTrabalhoCriteria;
import org.tempestade.nucleo.service.JornadaTrabalhoQueryService;

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
    private JornadaTrabalhoQueryService jornadaTrabalhoQueryService;

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
    public void getJornadaTrabalhosByIdFiltering() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        Long id = jornadaTrabalho.getId();

        defaultJornadaTrabalhoShouldBeFound("id.equals=" + id);
        defaultJornadaTrabalhoShouldNotBeFound("id.notEquals=" + id);

        defaultJornadaTrabalhoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultJornadaTrabalhoShouldNotBeFound("id.greaterThan=" + id);

        defaultJornadaTrabalhoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultJornadaTrabalhoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllJornadaTrabalhosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where nome equals to DEFAULT_NOME
        defaultJornadaTrabalhoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the jornadaTrabalhoList where nome equals to UPDATED_NOME
        defaultJornadaTrabalhoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where nome not equals to DEFAULT_NOME
        defaultJornadaTrabalhoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the jornadaTrabalhoList where nome not equals to UPDATED_NOME
        defaultJornadaTrabalhoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultJornadaTrabalhoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the jornadaTrabalhoList where nome equals to UPDATED_NOME
        defaultJornadaTrabalhoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where nome is not null
        defaultJornadaTrabalhoShouldBeFound("nome.specified=true");

        // Get all the jornadaTrabalhoList where nome is null
        defaultJornadaTrabalhoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllJornadaTrabalhosByNomeContainsSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where nome contains DEFAULT_NOME
        defaultJornadaTrabalhoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the jornadaTrabalhoList where nome contains UPDATED_NOME
        defaultJornadaTrabalhoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where nome does not contain DEFAULT_NOME
        defaultJornadaTrabalhoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the jornadaTrabalhoList where nome does not contain UPDATED_NOME
        defaultJornadaTrabalhoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllJornadaTrabalhosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where descricao equals to DEFAULT_DESCRICAO
        defaultJornadaTrabalhoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the jornadaTrabalhoList where descricao equals to UPDATED_DESCRICAO
        defaultJornadaTrabalhoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where descricao not equals to DEFAULT_DESCRICAO
        defaultJornadaTrabalhoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the jornadaTrabalhoList where descricao not equals to UPDATED_DESCRICAO
        defaultJornadaTrabalhoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultJornadaTrabalhoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the jornadaTrabalhoList where descricao equals to UPDATED_DESCRICAO
        defaultJornadaTrabalhoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where descricao is not null
        defaultJornadaTrabalhoShouldBeFound("descricao.specified=true");

        // Get all the jornadaTrabalhoList where descricao is null
        defaultJornadaTrabalhoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllJornadaTrabalhosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where descricao contains DEFAULT_DESCRICAO
        defaultJornadaTrabalhoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the jornadaTrabalhoList where descricao contains UPDATED_DESCRICAO
        defaultJornadaTrabalhoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where descricao does not contain DEFAULT_DESCRICAO
        defaultJornadaTrabalhoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the jornadaTrabalhoList where descricao does not contain UPDATED_DESCRICAO
        defaultJornadaTrabalhoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllJornadaTrabalhosByHorainicioIsEqualToSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where horainicio equals to DEFAULT_HORAINICIO
        defaultJornadaTrabalhoShouldBeFound("horainicio.equals=" + DEFAULT_HORAINICIO);

        // Get all the jornadaTrabalhoList where horainicio equals to UPDATED_HORAINICIO
        defaultJornadaTrabalhoShouldNotBeFound("horainicio.equals=" + UPDATED_HORAINICIO);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByHorainicioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where horainicio not equals to DEFAULT_HORAINICIO
        defaultJornadaTrabalhoShouldNotBeFound("horainicio.notEquals=" + DEFAULT_HORAINICIO);

        // Get all the jornadaTrabalhoList where horainicio not equals to UPDATED_HORAINICIO
        defaultJornadaTrabalhoShouldBeFound("horainicio.notEquals=" + UPDATED_HORAINICIO);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByHorainicioIsInShouldWork() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where horainicio in DEFAULT_HORAINICIO or UPDATED_HORAINICIO
        defaultJornadaTrabalhoShouldBeFound("horainicio.in=" + DEFAULT_HORAINICIO + "," + UPDATED_HORAINICIO);

        // Get all the jornadaTrabalhoList where horainicio equals to UPDATED_HORAINICIO
        defaultJornadaTrabalhoShouldNotBeFound("horainicio.in=" + UPDATED_HORAINICIO);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByHorainicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where horainicio is not null
        defaultJornadaTrabalhoShouldBeFound("horainicio.specified=true");

        // Get all the jornadaTrabalhoList where horainicio is null
        defaultJornadaTrabalhoShouldNotBeFound("horainicio.specified=false");
    }
                @Test
    @Transactional
    public void getAllJornadaTrabalhosByHorainicioContainsSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where horainicio contains DEFAULT_HORAINICIO
        defaultJornadaTrabalhoShouldBeFound("horainicio.contains=" + DEFAULT_HORAINICIO);

        // Get all the jornadaTrabalhoList where horainicio contains UPDATED_HORAINICIO
        defaultJornadaTrabalhoShouldNotBeFound("horainicio.contains=" + UPDATED_HORAINICIO);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByHorainicioNotContainsSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where horainicio does not contain DEFAULT_HORAINICIO
        defaultJornadaTrabalhoShouldNotBeFound("horainicio.doesNotContain=" + DEFAULT_HORAINICIO);

        // Get all the jornadaTrabalhoList where horainicio does not contain UPDATED_HORAINICIO
        defaultJornadaTrabalhoShouldBeFound("horainicio.doesNotContain=" + UPDATED_HORAINICIO);
    }


    @Test
    @Transactional
    public void getAllJornadaTrabalhosByDuracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where duracao equals to DEFAULT_DURACAO
        defaultJornadaTrabalhoShouldBeFound("duracao.equals=" + DEFAULT_DURACAO);

        // Get all the jornadaTrabalhoList where duracao equals to UPDATED_DURACAO
        defaultJornadaTrabalhoShouldNotBeFound("duracao.equals=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByDuracaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where duracao not equals to DEFAULT_DURACAO
        defaultJornadaTrabalhoShouldNotBeFound("duracao.notEquals=" + DEFAULT_DURACAO);

        // Get all the jornadaTrabalhoList where duracao not equals to UPDATED_DURACAO
        defaultJornadaTrabalhoShouldBeFound("duracao.notEquals=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByDuracaoIsInShouldWork() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where duracao in DEFAULT_DURACAO or UPDATED_DURACAO
        defaultJornadaTrabalhoShouldBeFound("duracao.in=" + DEFAULT_DURACAO + "," + UPDATED_DURACAO);

        // Get all the jornadaTrabalhoList where duracao equals to UPDATED_DURACAO
        defaultJornadaTrabalhoShouldNotBeFound("duracao.in=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByDuracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where duracao is not null
        defaultJornadaTrabalhoShouldBeFound("duracao.specified=true");

        // Get all the jornadaTrabalhoList where duracao is null
        defaultJornadaTrabalhoShouldNotBeFound("duracao.specified=false");
    }
                @Test
    @Transactional
    public void getAllJornadaTrabalhosByDuracaoContainsSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where duracao contains DEFAULT_DURACAO
        defaultJornadaTrabalhoShouldBeFound("duracao.contains=" + DEFAULT_DURACAO);

        // Get all the jornadaTrabalhoList where duracao contains UPDATED_DURACAO
        defaultJornadaTrabalhoShouldNotBeFound("duracao.contains=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByDuracaoNotContainsSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where duracao does not contain DEFAULT_DURACAO
        defaultJornadaTrabalhoShouldNotBeFound("duracao.doesNotContain=" + DEFAULT_DURACAO);

        // Get all the jornadaTrabalhoList where duracao does not contain UPDATED_DURACAO
        defaultJornadaTrabalhoShouldBeFound("duracao.doesNotContain=" + UPDATED_DURACAO);
    }


    @Test
    @Transactional
    public void getAllJornadaTrabalhosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where created equals to DEFAULT_CREATED
        defaultJornadaTrabalhoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the jornadaTrabalhoList where created equals to UPDATED_CREATED
        defaultJornadaTrabalhoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where created not equals to DEFAULT_CREATED
        defaultJornadaTrabalhoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the jornadaTrabalhoList where created not equals to UPDATED_CREATED
        defaultJornadaTrabalhoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultJornadaTrabalhoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the jornadaTrabalhoList where created equals to UPDATED_CREATED
        defaultJornadaTrabalhoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where created is not null
        defaultJornadaTrabalhoShouldBeFound("created.specified=true");

        // Get all the jornadaTrabalhoList where created is null
        defaultJornadaTrabalhoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where updated equals to DEFAULT_UPDATED
        defaultJornadaTrabalhoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the jornadaTrabalhoList where updated equals to UPDATED_UPDATED
        defaultJornadaTrabalhoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where updated not equals to DEFAULT_UPDATED
        defaultJornadaTrabalhoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the jornadaTrabalhoList where updated not equals to UPDATED_UPDATED
        defaultJornadaTrabalhoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultJornadaTrabalhoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the jornadaTrabalhoList where updated equals to UPDATED_UPDATED
        defaultJornadaTrabalhoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);

        // Get all the jornadaTrabalhoList where updated is not null
        defaultJornadaTrabalhoShouldBeFound("updated.specified=true");

        // Get all the jornadaTrabalhoList where updated is null
        defaultJornadaTrabalhoShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllJornadaTrabalhosByPlanoIsEqualToSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);
        Plano plano = PlanoResourceIT.createEntity(em);
        em.persist(plano);
        em.flush();
        jornadaTrabalho.setPlano(plano);
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);
        Long planoId = plano.getId();

        // Get all the jornadaTrabalhoList where plano equals to planoId
        defaultJornadaTrabalhoShouldBeFound("planoId.equals=" + planoId);

        // Get all the jornadaTrabalhoList where plano equals to planoId + 1
        defaultJornadaTrabalhoShouldNotBeFound("planoId.equals=" + (planoId + 1));
    }


    @Test
    @Transactional
    public void getAllJornadaTrabalhosByDiaSemanaIsEqualToSomething() throws Exception {
        // Initialize the database
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);
        DiaSemana diaSemana = DiaSemanaResourceIT.createEntity(em);
        em.persist(diaSemana);
        em.flush();
        jornadaTrabalho.setDiaSemana(diaSemana);
        jornadaTrabalhoRepository.saveAndFlush(jornadaTrabalho);
        Long diaSemanaId = diaSemana.getId();

        // Get all the jornadaTrabalhoList where diaSemana equals to diaSemanaId
        defaultJornadaTrabalhoShouldBeFound("diaSemanaId.equals=" + diaSemanaId);

        // Get all the jornadaTrabalhoList where diaSemana equals to diaSemanaId + 1
        defaultJornadaTrabalhoShouldNotBeFound("diaSemanaId.equals=" + (diaSemanaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultJornadaTrabalhoShouldBeFound(String filter) throws Exception {
        restJornadaTrabalhoMockMvc.perform(get("/api/jornada-trabalhos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jornadaTrabalho.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].horainicio").value(hasItem(DEFAULT_HORAINICIO)))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restJornadaTrabalhoMockMvc.perform(get("/api/jornada-trabalhos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultJornadaTrabalhoShouldNotBeFound(String filter) throws Exception {
        restJornadaTrabalhoMockMvc.perform(get("/api/jornada-trabalhos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJornadaTrabalhoMockMvc.perform(get("/api/jornada-trabalhos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
