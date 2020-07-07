package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Contato;
import org.tempestade.nucleo.repository.ContatoRepository;
import org.tempestade.nucleo.service.ContatoService;
import org.tempestade.nucleo.service.dto.ContatoDTO;
import org.tempestade.nucleo.service.mapper.ContatoMapper;
import org.tempestade.nucleo.service.dto.ContatoCriteria;
import org.tempestade.nucleo.service.ContatoQueryService;

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
 * Integration tests for the {@link ContatoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContatoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Boolean DEFAULT_CONTATO_ALERTA_TELEFONICO = false;
    private static final Boolean UPDATED_CONTATO_ALERTA_TELEFONICO = true;

    private static final Integer DEFAULT_PRIORIDADE = 1;
    private static final Integer UPDATED_PRIORIDADE = 2;
    private static final Integer SMALLER_PRIORIDADE = 1 - 1;

    private static final String DEFAULT_HORA_LIGACAO_INICIAL = "21:56:27";
    private static final String UPDATED_HORA_LIGACAO_INICIAL = "23:39:25";

    private static final String DEFAULT_HORA_LIGACAO_FINAL = "23:28:39";
    private static final String UPDATED_HORA_LIGACAO_FINAL = "03:24:16";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private ContatoMapper contatoMapper;

    @Autowired
    private ContatoService contatoService;

    @Autowired
    private ContatoQueryService contatoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContatoMockMvc;

    private Contato contato;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contato createEntity(EntityManager em) {
        Contato contato = new Contato()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .email(DEFAULT_EMAIL)
            .celular(DEFAULT_CELULAR)
            .ativo(DEFAULT_ATIVO)
            .contatoAlertaTelefonico(DEFAULT_CONTATO_ALERTA_TELEFONICO)
            .prioridade(DEFAULT_PRIORIDADE)
            .horaLigacaoInicial(DEFAULT_HORA_LIGACAO_INICIAL)
            .horaLigacaoFinal(DEFAULT_HORA_LIGACAO_FINAL)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return contato;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contato createUpdatedEntity(EntityManager em) {
        Contato contato = new Contato()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .email(UPDATED_EMAIL)
            .celular(UPDATED_CELULAR)
            .ativo(UPDATED_ATIVO)
            .contatoAlertaTelefonico(UPDATED_CONTATO_ALERTA_TELEFONICO)
            .prioridade(UPDATED_PRIORIDADE)
            .horaLigacaoInicial(UPDATED_HORA_LIGACAO_INICIAL)
            .horaLigacaoFinal(UPDATED_HORA_LIGACAO_FINAL)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return contato;
    }

    @BeforeEach
    public void initTest() {
        contato = createEntity(em);
    }

    @Test
    @Transactional
    public void createContato() throws Exception {
        int databaseSizeBeforeCreate = contatoRepository.findAll().size();
        // Create the Contato
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);
        restContatoMockMvc.perform(post("/api/contatoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isCreated());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeCreate + 1);
        Contato testContato = contatoList.get(contatoList.size() - 1);
        assertThat(testContato.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testContato.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testContato.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContato.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testContato.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testContato.isContatoAlertaTelefonico()).isEqualTo(DEFAULT_CONTATO_ALERTA_TELEFONICO);
        assertThat(testContato.getPrioridade()).isEqualTo(DEFAULT_PRIORIDADE);
        assertThat(testContato.getHoraLigacaoInicial()).isEqualTo(DEFAULT_HORA_LIGACAO_INICIAL);
        assertThat(testContato.getHoraLigacaoFinal()).isEqualTo(DEFAULT_HORA_LIGACAO_FINAL);
        assertThat(testContato.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testContato.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createContatoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contatoRepository.findAll().size();

        // Create the Contato with an existing ID
        contato.setId(1L);
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContatoMockMvc.perform(post("/api/contatoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoRepository.findAll().size();
        // set the field null
        contato.setNome(null);

        // Create the Contato, which fails.
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);


        restContatoMockMvc.perform(post("/api/contatoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoRepository.findAll().size();
        // set the field null
        contato.setEmail(null);

        // Create the Contato, which fails.
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);


        restContatoMockMvc.perform(post("/api/contatoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoRepository.findAll().size();
        // set the field null
        contato.setCreated(null);

        // Create the Contato, which fails.
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);


        restContatoMockMvc.perform(post("/api/contatoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContatoes() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList
        restContatoMockMvc.perform(get("/api/contatoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contato.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].contatoAlertaTelefonico").value(hasItem(DEFAULT_CONTATO_ALERTA_TELEFONICO.booleanValue())))
            .andExpect(jsonPath("$.[*].prioridade").value(hasItem(DEFAULT_PRIORIDADE)))
            .andExpect(jsonPath("$.[*].horaLigacaoInicial").value(hasItem(DEFAULT_HORA_LIGACAO_INICIAL)))
            .andExpect(jsonPath("$.[*].horaLigacaoFinal").value(hasItem(DEFAULT_HORA_LIGACAO_FINAL)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getContato() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get the contato
        restContatoMockMvc.perform(get("/api/contatoes/{id}", contato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contato.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.contatoAlertaTelefonico").value(DEFAULT_CONTATO_ALERTA_TELEFONICO.booleanValue()))
            .andExpect(jsonPath("$.prioridade").value(DEFAULT_PRIORIDADE))
            .andExpect(jsonPath("$.horaLigacaoInicial").value(DEFAULT_HORA_LIGACAO_INICIAL))
            .andExpect(jsonPath("$.horaLigacaoFinal").value(DEFAULT_HORA_LIGACAO_FINAL))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getContatoesByIdFiltering() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        Long id = contato.getId();

        defaultContatoShouldBeFound("id.equals=" + id);
        defaultContatoShouldNotBeFound("id.notEquals=" + id);

        defaultContatoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContatoShouldNotBeFound("id.greaterThan=" + id);

        defaultContatoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContatoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllContatoesByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where nome equals to DEFAULT_NOME
        defaultContatoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the contatoList where nome equals to UPDATED_NOME
        defaultContatoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoesByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where nome not equals to DEFAULT_NOME
        defaultContatoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the contatoList where nome not equals to UPDATED_NOME
        defaultContatoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoesByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultContatoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the contatoList where nome equals to UPDATED_NOME
        defaultContatoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoesByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where nome is not null
        defaultContatoShouldBeFound("nome.specified=true");

        // Get all the contatoList where nome is null
        defaultContatoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllContatoesByNomeContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where nome contains DEFAULT_NOME
        defaultContatoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the contatoList where nome contains UPDATED_NOME
        defaultContatoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoesByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where nome does not contain DEFAULT_NOME
        defaultContatoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the contatoList where nome does not contain UPDATED_NOME
        defaultContatoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllContatoesByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where descricao equals to DEFAULT_DESCRICAO
        defaultContatoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the contatoList where descricao equals to UPDATED_DESCRICAO
        defaultContatoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContatoesByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where descricao not equals to DEFAULT_DESCRICAO
        defaultContatoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the contatoList where descricao not equals to UPDATED_DESCRICAO
        defaultContatoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContatoesByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultContatoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the contatoList where descricao equals to UPDATED_DESCRICAO
        defaultContatoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContatoesByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where descricao is not null
        defaultContatoShouldBeFound("descricao.specified=true");

        // Get all the contatoList where descricao is null
        defaultContatoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllContatoesByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where descricao contains DEFAULT_DESCRICAO
        defaultContatoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the contatoList where descricao contains UPDATED_DESCRICAO
        defaultContatoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContatoesByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where descricao does not contain DEFAULT_DESCRICAO
        defaultContatoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the contatoList where descricao does not contain UPDATED_DESCRICAO
        defaultContatoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllContatoesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where email equals to DEFAULT_EMAIL
        defaultContatoShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the contatoList where email equals to UPDATED_EMAIL
        defaultContatoShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllContatoesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where email not equals to DEFAULT_EMAIL
        defaultContatoShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the contatoList where email not equals to UPDATED_EMAIL
        defaultContatoShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllContatoesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultContatoShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the contatoList where email equals to UPDATED_EMAIL
        defaultContatoShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllContatoesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where email is not null
        defaultContatoShouldBeFound("email.specified=true");

        // Get all the contatoList where email is null
        defaultContatoShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllContatoesByEmailContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where email contains DEFAULT_EMAIL
        defaultContatoShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the contatoList where email contains UPDATED_EMAIL
        defaultContatoShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllContatoesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where email does not contain DEFAULT_EMAIL
        defaultContatoShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the contatoList where email does not contain UPDATED_EMAIL
        defaultContatoShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllContatoesByCelularIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where celular equals to DEFAULT_CELULAR
        defaultContatoShouldBeFound("celular.equals=" + DEFAULT_CELULAR);

        // Get all the contatoList where celular equals to UPDATED_CELULAR
        defaultContatoShouldNotBeFound("celular.equals=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void getAllContatoesByCelularIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where celular not equals to DEFAULT_CELULAR
        defaultContatoShouldNotBeFound("celular.notEquals=" + DEFAULT_CELULAR);

        // Get all the contatoList where celular not equals to UPDATED_CELULAR
        defaultContatoShouldBeFound("celular.notEquals=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void getAllContatoesByCelularIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where celular in DEFAULT_CELULAR or UPDATED_CELULAR
        defaultContatoShouldBeFound("celular.in=" + DEFAULT_CELULAR + "," + UPDATED_CELULAR);

        // Get all the contatoList where celular equals to UPDATED_CELULAR
        defaultContatoShouldNotBeFound("celular.in=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void getAllContatoesByCelularIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where celular is not null
        defaultContatoShouldBeFound("celular.specified=true");

        // Get all the contatoList where celular is null
        defaultContatoShouldNotBeFound("celular.specified=false");
    }
                @Test
    @Transactional
    public void getAllContatoesByCelularContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where celular contains DEFAULT_CELULAR
        defaultContatoShouldBeFound("celular.contains=" + DEFAULT_CELULAR);

        // Get all the contatoList where celular contains UPDATED_CELULAR
        defaultContatoShouldNotBeFound("celular.contains=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void getAllContatoesByCelularNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where celular does not contain DEFAULT_CELULAR
        defaultContatoShouldNotBeFound("celular.doesNotContain=" + DEFAULT_CELULAR);

        // Get all the contatoList where celular does not contain UPDATED_CELULAR
        defaultContatoShouldBeFound("celular.doesNotContain=" + UPDATED_CELULAR);
    }


    @Test
    @Transactional
    public void getAllContatoesByAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where ativo equals to DEFAULT_ATIVO
        defaultContatoShouldBeFound("ativo.equals=" + DEFAULT_ATIVO);

        // Get all the contatoList where ativo equals to UPDATED_ATIVO
        defaultContatoShouldNotBeFound("ativo.equals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllContatoesByAtivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where ativo not equals to DEFAULT_ATIVO
        defaultContatoShouldNotBeFound("ativo.notEquals=" + DEFAULT_ATIVO);

        // Get all the contatoList where ativo not equals to UPDATED_ATIVO
        defaultContatoShouldBeFound("ativo.notEquals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllContatoesByAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where ativo in DEFAULT_ATIVO or UPDATED_ATIVO
        defaultContatoShouldBeFound("ativo.in=" + DEFAULT_ATIVO + "," + UPDATED_ATIVO);

        // Get all the contatoList where ativo equals to UPDATED_ATIVO
        defaultContatoShouldNotBeFound("ativo.in=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllContatoesByAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where ativo is not null
        defaultContatoShouldBeFound("ativo.specified=true");

        // Get all the contatoList where ativo is null
        defaultContatoShouldNotBeFound("ativo.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoesByContatoAlertaTelefonicoIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where contatoAlertaTelefonico equals to DEFAULT_CONTATO_ALERTA_TELEFONICO
        defaultContatoShouldBeFound("contatoAlertaTelefonico.equals=" + DEFAULT_CONTATO_ALERTA_TELEFONICO);

        // Get all the contatoList where contatoAlertaTelefonico equals to UPDATED_CONTATO_ALERTA_TELEFONICO
        defaultContatoShouldNotBeFound("contatoAlertaTelefonico.equals=" + UPDATED_CONTATO_ALERTA_TELEFONICO);
    }

    @Test
    @Transactional
    public void getAllContatoesByContatoAlertaTelefonicoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where contatoAlertaTelefonico not equals to DEFAULT_CONTATO_ALERTA_TELEFONICO
        defaultContatoShouldNotBeFound("contatoAlertaTelefonico.notEquals=" + DEFAULT_CONTATO_ALERTA_TELEFONICO);

        // Get all the contatoList where contatoAlertaTelefonico not equals to UPDATED_CONTATO_ALERTA_TELEFONICO
        defaultContatoShouldBeFound("contatoAlertaTelefonico.notEquals=" + UPDATED_CONTATO_ALERTA_TELEFONICO);
    }

    @Test
    @Transactional
    public void getAllContatoesByContatoAlertaTelefonicoIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where contatoAlertaTelefonico in DEFAULT_CONTATO_ALERTA_TELEFONICO or UPDATED_CONTATO_ALERTA_TELEFONICO
        defaultContatoShouldBeFound("contatoAlertaTelefonico.in=" + DEFAULT_CONTATO_ALERTA_TELEFONICO + "," + UPDATED_CONTATO_ALERTA_TELEFONICO);

        // Get all the contatoList where contatoAlertaTelefonico equals to UPDATED_CONTATO_ALERTA_TELEFONICO
        defaultContatoShouldNotBeFound("contatoAlertaTelefonico.in=" + UPDATED_CONTATO_ALERTA_TELEFONICO);
    }

    @Test
    @Transactional
    public void getAllContatoesByContatoAlertaTelefonicoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where contatoAlertaTelefonico is not null
        defaultContatoShouldBeFound("contatoAlertaTelefonico.specified=true");

        // Get all the contatoList where contatoAlertaTelefonico is null
        defaultContatoShouldNotBeFound("contatoAlertaTelefonico.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoesByPrioridadeIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where prioridade equals to DEFAULT_PRIORIDADE
        defaultContatoShouldBeFound("prioridade.equals=" + DEFAULT_PRIORIDADE);

        // Get all the contatoList where prioridade equals to UPDATED_PRIORIDADE
        defaultContatoShouldNotBeFound("prioridade.equals=" + UPDATED_PRIORIDADE);
    }

    @Test
    @Transactional
    public void getAllContatoesByPrioridadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where prioridade not equals to DEFAULT_PRIORIDADE
        defaultContatoShouldNotBeFound("prioridade.notEquals=" + DEFAULT_PRIORIDADE);

        // Get all the contatoList where prioridade not equals to UPDATED_PRIORIDADE
        defaultContatoShouldBeFound("prioridade.notEquals=" + UPDATED_PRIORIDADE);
    }

    @Test
    @Transactional
    public void getAllContatoesByPrioridadeIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where prioridade in DEFAULT_PRIORIDADE or UPDATED_PRIORIDADE
        defaultContatoShouldBeFound("prioridade.in=" + DEFAULT_PRIORIDADE + "," + UPDATED_PRIORIDADE);

        // Get all the contatoList where prioridade equals to UPDATED_PRIORIDADE
        defaultContatoShouldNotBeFound("prioridade.in=" + UPDATED_PRIORIDADE);
    }

    @Test
    @Transactional
    public void getAllContatoesByPrioridadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where prioridade is not null
        defaultContatoShouldBeFound("prioridade.specified=true");

        // Get all the contatoList where prioridade is null
        defaultContatoShouldNotBeFound("prioridade.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoesByPrioridadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where prioridade is greater than or equal to DEFAULT_PRIORIDADE
        defaultContatoShouldBeFound("prioridade.greaterThanOrEqual=" + DEFAULT_PRIORIDADE);

        // Get all the contatoList where prioridade is greater than or equal to UPDATED_PRIORIDADE
        defaultContatoShouldNotBeFound("prioridade.greaterThanOrEqual=" + UPDATED_PRIORIDADE);
    }

    @Test
    @Transactional
    public void getAllContatoesByPrioridadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where prioridade is less than or equal to DEFAULT_PRIORIDADE
        defaultContatoShouldBeFound("prioridade.lessThanOrEqual=" + DEFAULT_PRIORIDADE);

        // Get all the contatoList where prioridade is less than or equal to SMALLER_PRIORIDADE
        defaultContatoShouldNotBeFound("prioridade.lessThanOrEqual=" + SMALLER_PRIORIDADE);
    }

    @Test
    @Transactional
    public void getAllContatoesByPrioridadeIsLessThanSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where prioridade is less than DEFAULT_PRIORIDADE
        defaultContatoShouldNotBeFound("prioridade.lessThan=" + DEFAULT_PRIORIDADE);

        // Get all the contatoList where prioridade is less than UPDATED_PRIORIDADE
        defaultContatoShouldBeFound("prioridade.lessThan=" + UPDATED_PRIORIDADE);
    }

    @Test
    @Transactional
    public void getAllContatoesByPrioridadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where prioridade is greater than DEFAULT_PRIORIDADE
        defaultContatoShouldNotBeFound("prioridade.greaterThan=" + DEFAULT_PRIORIDADE);

        // Get all the contatoList where prioridade is greater than SMALLER_PRIORIDADE
        defaultContatoShouldBeFound("prioridade.greaterThan=" + SMALLER_PRIORIDADE);
    }


    @Test
    @Transactional
    public void getAllContatoesByHoraLigacaoInicialIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where horaLigacaoInicial equals to DEFAULT_HORA_LIGACAO_INICIAL
        defaultContatoShouldBeFound("horaLigacaoInicial.equals=" + DEFAULT_HORA_LIGACAO_INICIAL);

        // Get all the contatoList where horaLigacaoInicial equals to UPDATED_HORA_LIGACAO_INICIAL
        defaultContatoShouldNotBeFound("horaLigacaoInicial.equals=" + UPDATED_HORA_LIGACAO_INICIAL);
    }

    @Test
    @Transactional
    public void getAllContatoesByHoraLigacaoInicialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where horaLigacaoInicial not equals to DEFAULT_HORA_LIGACAO_INICIAL
        defaultContatoShouldNotBeFound("horaLigacaoInicial.notEquals=" + DEFAULT_HORA_LIGACAO_INICIAL);

        // Get all the contatoList where horaLigacaoInicial not equals to UPDATED_HORA_LIGACAO_INICIAL
        defaultContatoShouldBeFound("horaLigacaoInicial.notEquals=" + UPDATED_HORA_LIGACAO_INICIAL);
    }

    @Test
    @Transactional
    public void getAllContatoesByHoraLigacaoInicialIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where horaLigacaoInicial in DEFAULT_HORA_LIGACAO_INICIAL or UPDATED_HORA_LIGACAO_INICIAL
        defaultContatoShouldBeFound("horaLigacaoInicial.in=" + DEFAULT_HORA_LIGACAO_INICIAL + "," + UPDATED_HORA_LIGACAO_INICIAL);

        // Get all the contatoList where horaLigacaoInicial equals to UPDATED_HORA_LIGACAO_INICIAL
        defaultContatoShouldNotBeFound("horaLigacaoInicial.in=" + UPDATED_HORA_LIGACAO_INICIAL);
    }

    @Test
    @Transactional
    public void getAllContatoesByHoraLigacaoInicialIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where horaLigacaoInicial is not null
        defaultContatoShouldBeFound("horaLigacaoInicial.specified=true");

        // Get all the contatoList where horaLigacaoInicial is null
        defaultContatoShouldNotBeFound("horaLigacaoInicial.specified=false");
    }
                @Test
    @Transactional
    public void getAllContatoesByHoraLigacaoInicialContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where horaLigacaoInicial contains DEFAULT_HORA_LIGACAO_INICIAL
        defaultContatoShouldBeFound("horaLigacaoInicial.contains=" + DEFAULT_HORA_LIGACAO_INICIAL);

        // Get all the contatoList where horaLigacaoInicial contains UPDATED_HORA_LIGACAO_INICIAL
        defaultContatoShouldNotBeFound("horaLigacaoInicial.contains=" + UPDATED_HORA_LIGACAO_INICIAL);
    }

    @Test
    @Transactional
    public void getAllContatoesByHoraLigacaoInicialNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where horaLigacaoInicial does not contain DEFAULT_HORA_LIGACAO_INICIAL
        defaultContatoShouldNotBeFound("horaLigacaoInicial.doesNotContain=" + DEFAULT_HORA_LIGACAO_INICIAL);

        // Get all the contatoList where horaLigacaoInicial does not contain UPDATED_HORA_LIGACAO_INICIAL
        defaultContatoShouldBeFound("horaLigacaoInicial.doesNotContain=" + UPDATED_HORA_LIGACAO_INICIAL);
    }


    @Test
    @Transactional
    public void getAllContatoesByHoraLigacaoFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where horaLigacaoFinal equals to DEFAULT_HORA_LIGACAO_FINAL
        defaultContatoShouldBeFound("horaLigacaoFinal.equals=" + DEFAULT_HORA_LIGACAO_FINAL);

        // Get all the contatoList where horaLigacaoFinal equals to UPDATED_HORA_LIGACAO_FINAL
        defaultContatoShouldNotBeFound("horaLigacaoFinal.equals=" + UPDATED_HORA_LIGACAO_FINAL);
    }

    @Test
    @Transactional
    public void getAllContatoesByHoraLigacaoFinalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where horaLigacaoFinal not equals to DEFAULT_HORA_LIGACAO_FINAL
        defaultContatoShouldNotBeFound("horaLigacaoFinal.notEquals=" + DEFAULT_HORA_LIGACAO_FINAL);

        // Get all the contatoList where horaLigacaoFinal not equals to UPDATED_HORA_LIGACAO_FINAL
        defaultContatoShouldBeFound("horaLigacaoFinal.notEquals=" + UPDATED_HORA_LIGACAO_FINAL);
    }

    @Test
    @Transactional
    public void getAllContatoesByHoraLigacaoFinalIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where horaLigacaoFinal in DEFAULT_HORA_LIGACAO_FINAL or UPDATED_HORA_LIGACAO_FINAL
        defaultContatoShouldBeFound("horaLigacaoFinal.in=" + DEFAULT_HORA_LIGACAO_FINAL + "," + UPDATED_HORA_LIGACAO_FINAL);

        // Get all the contatoList where horaLigacaoFinal equals to UPDATED_HORA_LIGACAO_FINAL
        defaultContatoShouldNotBeFound("horaLigacaoFinal.in=" + UPDATED_HORA_LIGACAO_FINAL);
    }

    @Test
    @Transactional
    public void getAllContatoesByHoraLigacaoFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where horaLigacaoFinal is not null
        defaultContatoShouldBeFound("horaLigacaoFinal.specified=true");

        // Get all the contatoList where horaLigacaoFinal is null
        defaultContatoShouldNotBeFound("horaLigacaoFinal.specified=false");
    }
                @Test
    @Transactional
    public void getAllContatoesByHoraLigacaoFinalContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where horaLigacaoFinal contains DEFAULT_HORA_LIGACAO_FINAL
        defaultContatoShouldBeFound("horaLigacaoFinal.contains=" + DEFAULT_HORA_LIGACAO_FINAL);

        // Get all the contatoList where horaLigacaoFinal contains UPDATED_HORA_LIGACAO_FINAL
        defaultContatoShouldNotBeFound("horaLigacaoFinal.contains=" + UPDATED_HORA_LIGACAO_FINAL);
    }

    @Test
    @Transactional
    public void getAllContatoesByHoraLigacaoFinalNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where horaLigacaoFinal does not contain DEFAULT_HORA_LIGACAO_FINAL
        defaultContatoShouldNotBeFound("horaLigacaoFinal.doesNotContain=" + DEFAULT_HORA_LIGACAO_FINAL);

        // Get all the contatoList where horaLigacaoFinal does not contain UPDATED_HORA_LIGACAO_FINAL
        defaultContatoShouldBeFound("horaLigacaoFinal.doesNotContain=" + UPDATED_HORA_LIGACAO_FINAL);
    }


    @Test
    @Transactional
    public void getAllContatoesByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where created equals to DEFAULT_CREATED
        defaultContatoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the contatoList where created equals to UPDATED_CREATED
        defaultContatoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllContatoesByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where created not equals to DEFAULT_CREATED
        defaultContatoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the contatoList where created not equals to UPDATED_CREATED
        defaultContatoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllContatoesByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultContatoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the contatoList where created equals to UPDATED_CREATED
        defaultContatoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllContatoesByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where created is not null
        defaultContatoShouldBeFound("created.specified=true");

        // Get all the contatoList where created is null
        defaultContatoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoesByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where updated equals to DEFAULT_UPDATED
        defaultContatoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the contatoList where updated equals to UPDATED_UPDATED
        defaultContatoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllContatoesByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where updated not equals to DEFAULT_UPDATED
        defaultContatoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the contatoList where updated not equals to UPDATED_UPDATED
        defaultContatoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllContatoesByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultContatoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the contatoList where updated equals to UPDATED_UPDATED
        defaultContatoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllContatoesByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where updated is not null
        defaultContatoShouldBeFound("updated.specified=true");

        // Get all the contatoList where updated is null
        defaultContatoShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContatoShouldBeFound(String filter) throws Exception {
        restContatoMockMvc.perform(get("/api/contatoes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contato.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].contatoAlertaTelefonico").value(hasItem(DEFAULT_CONTATO_ALERTA_TELEFONICO.booleanValue())))
            .andExpect(jsonPath("$.[*].prioridade").value(hasItem(DEFAULT_PRIORIDADE)))
            .andExpect(jsonPath("$.[*].horaLigacaoInicial").value(hasItem(DEFAULT_HORA_LIGACAO_INICIAL)))
            .andExpect(jsonPath("$.[*].horaLigacaoFinal").value(hasItem(DEFAULT_HORA_LIGACAO_FINAL)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restContatoMockMvc.perform(get("/api/contatoes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContatoShouldNotBeFound(String filter) throws Exception {
        restContatoMockMvc.perform(get("/api/contatoes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContatoMockMvc.perform(get("/api/contatoes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingContato() throws Exception {
        // Get the contato
        restContatoMockMvc.perform(get("/api/contatoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContato() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        int databaseSizeBeforeUpdate = contatoRepository.findAll().size();

        // Update the contato
        Contato updatedContato = contatoRepository.findById(contato.getId()).get();
        // Disconnect from session so that the updates on updatedContato are not directly saved in db
        em.detach(updatedContato);
        updatedContato
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .email(UPDATED_EMAIL)
            .celular(UPDATED_CELULAR)
            .ativo(UPDATED_ATIVO)
            .contatoAlertaTelefonico(UPDATED_CONTATO_ALERTA_TELEFONICO)
            .prioridade(UPDATED_PRIORIDADE)
            .horaLigacaoInicial(UPDATED_HORA_LIGACAO_INICIAL)
            .horaLigacaoFinal(UPDATED_HORA_LIGACAO_FINAL)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        ContatoDTO contatoDTO = contatoMapper.toDto(updatedContato);

        restContatoMockMvc.perform(put("/api/contatoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isOk());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeUpdate);
        Contato testContato = contatoList.get(contatoList.size() - 1);
        assertThat(testContato.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testContato.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testContato.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContato.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testContato.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testContato.isContatoAlertaTelefonico()).isEqualTo(UPDATED_CONTATO_ALERTA_TELEFONICO);
        assertThat(testContato.getPrioridade()).isEqualTo(UPDATED_PRIORIDADE);
        assertThat(testContato.getHoraLigacaoInicial()).isEqualTo(UPDATED_HORA_LIGACAO_INICIAL);
        assertThat(testContato.getHoraLigacaoFinal()).isEqualTo(UPDATED_HORA_LIGACAO_FINAL);
        assertThat(testContato.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testContato.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingContato() throws Exception {
        int databaseSizeBeforeUpdate = contatoRepository.findAll().size();

        // Create the Contato
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContatoMockMvc.perform(put("/api/contatoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContato() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        int databaseSizeBeforeDelete = contatoRepository.findAll().size();

        // Delete the contato
        restContatoMockMvc.perform(delete("/api/contatoes/{id}", contato.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
