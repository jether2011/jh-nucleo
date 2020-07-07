package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.ContatoPlanoRecurso;
import org.tempestade.nucleo.domain.Contato;
import org.tempestade.nucleo.domain.PlanoRecurso;
import org.tempestade.nucleo.repository.ContatoPlanoRecursoRepository;
import org.tempestade.nucleo.service.ContatoPlanoRecursoService;
import org.tempestade.nucleo.service.dto.ContatoPlanoRecursoDTO;
import org.tempestade.nucleo.service.mapper.ContatoPlanoRecursoMapper;
import org.tempestade.nucleo.service.dto.ContatoPlanoRecursoCriteria;
import org.tempestade.nucleo.service.ContatoPlanoRecursoQueryService;

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
    private ContatoPlanoRecursoQueryService contatoPlanoRecursoQueryService;

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
    public void getContatoPlanoRecursosByIdFiltering() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        Long id = contatoPlanoRecurso.getId();

        defaultContatoPlanoRecursoShouldBeFound("id.equals=" + id);
        defaultContatoPlanoRecursoShouldNotBeFound("id.notEquals=" + id);

        defaultContatoPlanoRecursoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContatoPlanoRecursoShouldNotBeFound("id.greaterThan=" + id);

        defaultContatoPlanoRecursoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContatoPlanoRecursoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where nome equals to DEFAULT_NOME
        defaultContatoPlanoRecursoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the contatoPlanoRecursoList where nome equals to UPDATED_NOME
        defaultContatoPlanoRecursoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where nome not equals to DEFAULT_NOME
        defaultContatoPlanoRecursoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the contatoPlanoRecursoList where nome not equals to UPDATED_NOME
        defaultContatoPlanoRecursoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultContatoPlanoRecursoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the contatoPlanoRecursoList where nome equals to UPDATED_NOME
        defaultContatoPlanoRecursoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where nome is not null
        defaultContatoPlanoRecursoShouldBeFound("nome.specified=true");

        // Get all the contatoPlanoRecursoList where nome is null
        defaultContatoPlanoRecursoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllContatoPlanoRecursosByNomeContainsSomething() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where nome contains DEFAULT_NOME
        defaultContatoPlanoRecursoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the contatoPlanoRecursoList where nome contains UPDATED_NOME
        defaultContatoPlanoRecursoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where nome does not contain DEFAULT_NOME
        defaultContatoPlanoRecursoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the contatoPlanoRecursoList where nome does not contain UPDATED_NOME
        defaultContatoPlanoRecursoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where descricao equals to DEFAULT_DESCRICAO
        defaultContatoPlanoRecursoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the contatoPlanoRecursoList where descricao equals to UPDATED_DESCRICAO
        defaultContatoPlanoRecursoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where descricao not equals to DEFAULT_DESCRICAO
        defaultContatoPlanoRecursoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the contatoPlanoRecursoList where descricao not equals to UPDATED_DESCRICAO
        defaultContatoPlanoRecursoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultContatoPlanoRecursoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the contatoPlanoRecursoList where descricao equals to UPDATED_DESCRICAO
        defaultContatoPlanoRecursoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where descricao is not null
        defaultContatoPlanoRecursoShouldBeFound("descricao.specified=true");

        // Get all the contatoPlanoRecursoList where descricao is null
        defaultContatoPlanoRecursoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllContatoPlanoRecursosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where descricao contains DEFAULT_DESCRICAO
        defaultContatoPlanoRecursoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the contatoPlanoRecursoList where descricao contains UPDATED_DESCRICAO
        defaultContatoPlanoRecursoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where descricao does not contain DEFAULT_DESCRICAO
        defaultContatoPlanoRecursoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the contatoPlanoRecursoList where descricao does not contain UPDATED_DESCRICAO
        defaultContatoPlanoRecursoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where created equals to DEFAULT_CREATED
        defaultContatoPlanoRecursoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the contatoPlanoRecursoList where created equals to UPDATED_CREATED
        defaultContatoPlanoRecursoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where created not equals to DEFAULT_CREATED
        defaultContatoPlanoRecursoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the contatoPlanoRecursoList where created not equals to UPDATED_CREATED
        defaultContatoPlanoRecursoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultContatoPlanoRecursoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the contatoPlanoRecursoList where created equals to UPDATED_CREATED
        defaultContatoPlanoRecursoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where created is not null
        defaultContatoPlanoRecursoShouldBeFound("created.specified=true");

        // Get all the contatoPlanoRecursoList where created is null
        defaultContatoPlanoRecursoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where updated equals to DEFAULT_UPDATED
        defaultContatoPlanoRecursoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the contatoPlanoRecursoList where updated equals to UPDATED_UPDATED
        defaultContatoPlanoRecursoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where updated not equals to DEFAULT_UPDATED
        defaultContatoPlanoRecursoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the contatoPlanoRecursoList where updated not equals to UPDATED_UPDATED
        defaultContatoPlanoRecursoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultContatoPlanoRecursoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the contatoPlanoRecursoList where updated equals to UPDATED_UPDATED
        defaultContatoPlanoRecursoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);

        // Get all the contatoPlanoRecursoList where updated is not null
        defaultContatoPlanoRecursoShouldBeFound("updated.specified=true");

        // Get all the contatoPlanoRecursoList where updated is null
        defaultContatoPlanoRecursoShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByContatoIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);
        Contato contato = ContatoResourceIT.createEntity(em);
        em.persist(contato);
        em.flush();
        contatoPlanoRecurso.setContato(contato);
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);
        Long contatoId = contato.getId();

        // Get all the contatoPlanoRecursoList where contato equals to contatoId
        defaultContatoPlanoRecursoShouldBeFound("contatoId.equals=" + contatoId);

        // Get all the contatoPlanoRecursoList where contato equals to contatoId + 1
        defaultContatoPlanoRecursoShouldNotBeFound("contatoId.equals=" + (contatoId + 1));
    }


    @Test
    @Transactional
    public void getAllContatoPlanoRecursosByPlanoRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);
        PlanoRecurso planoRecurso = PlanoRecursoResourceIT.createEntity(em);
        em.persist(planoRecurso);
        em.flush();
        contatoPlanoRecurso.setPlanoRecurso(planoRecurso);
        contatoPlanoRecursoRepository.saveAndFlush(contatoPlanoRecurso);
        Long planoRecursoId = planoRecurso.getId();

        // Get all the contatoPlanoRecursoList where planoRecurso equals to planoRecursoId
        defaultContatoPlanoRecursoShouldBeFound("planoRecursoId.equals=" + planoRecursoId);

        // Get all the contatoPlanoRecursoList where planoRecurso equals to planoRecursoId + 1
        defaultContatoPlanoRecursoShouldNotBeFound("planoRecursoId.equals=" + (planoRecursoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContatoPlanoRecursoShouldBeFound(String filter) throws Exception {
        restContatoPlanoRecursoMockMvc.perform(get("/api/contato-plano-recursos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contatoPlanoRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restContatoPlanoRecursoMockMvc.perform(get("/api/contato-plano-recursos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContatoPlanoRecursoShouldNotBeFound(String filter) throws Exception {
        restContatoPlanoRecursoMockMvc.perform(get("/api/contato-plano-recursos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContatoPlanoRecursoMockMvc.perform(get("/api/contato-plano-recursos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
