package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.ContatoTipoEnvio;
import org.tempestade.nucleo.domain.Contato;
import org.tempestade.nucleo.domain.TipoEnvio;
import org.tempestade.nucleo.repository.ContatoTipoEnvioRepository;
import org.tempestade.nucleo.service.ContatoTipoEnvioService;
import org.tempestade.nucleo.service.dto.ContatoTipoEnvioDTO;
import org.tempestade.nucleo.service.mapper.ContatoTipoEnvioMapper;
import org.tempestade.nucleo.service.dto.ContatoTipoEnvioCriteria;
import org.tempestade.nucleo.service.ContatoTipoEnvioQueryService;

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
 * Integration tests for the {@link ContatoTipoEnvioResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContatoTipoEnvioResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ContatoTipoEnvioRepository contatoTipoEnvioRepository;

    @Autowired
    private ContatoTipoEnvioMapper contatoTipoEnvioMapper;

    @Autowired
    private ContatoTipoEnvioService contatoTipoEnvioService;

    @Autowired
    private ContatoTipoEnvioQueryService contatoTipoEnvioQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContatoTipoEnvioMockMvc;

    private ContatoTipoEnvio contatoTipoEnvio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContatoTipoEnvio createEntity(EntityManager em) {
        ContatoTipoEnvio contatoTipoEnvio = new ContatoTipoEnvio()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        // Add required entity
        Contato contato;
        if (TestUtil.findAll(em, Contato.class).isEmpty()) {
            contato = ContatoResourceIT.createEntity(em);
            em.persist(contato);
            em.flush();
        } else {
            contato = TestUtil.findAll(em, Contato.class).get(0);
        }
        contatoTipoEnvio.setContato(contato);
        // Add required entity
        TipoEnvio tipoEnvio;
        if (TestUtil.findAll(em, TipoEnvio.class).isEmpty()) {
            tipoEnvio = TipoEnvioResourceIT.createEntity(em);
            em.persist(tipoEnvio);
            em.flush();
        } else {
            tipoEnvio = TestUtil.findAll(em, TipoEnvio.class).get(0);
        }
        contatoTipoEnvio.setTipoEnvio(tipoEnvio);
        return contatoTipoEnvio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContatoTipoEnvio createUpdatedEntity(EntityManager em) {
        ContatoTipoEnvio contatoTipoEnvio = new ContatoTipoEnvio()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        // Add required entity
        Contato contato;
        if (TestUtil.findAll(em, Contato.class).isEmpty()) {
            contato = ContatoResourceIT.createUpdatedEntity(em);
            em.persist(contato);
            em.flush();
        } else {
            contato = TestUtil.findAll(em, Contato.class).get(0);
        }
        contatoTipoEnvio.setContato(contato);
        // Add required entity
        TipoEnvio tipoEnvio;
        if (TestUtil.findAll(em, TipoEnvio.class).isEmpty()) {
            tipoEnvio = TipoEnvioResourceIT.createUpdatedEntity(em);
            em.persist(tipoEnvio);
            em.flush();
        } else {
            tipoEnvio = TestUtil.findAll(em, TipoEnvio.class).get(0);
        }
        contatoTipoEnvio.setTipoEnvio(tipoEnvio);
        return contatoTipoEnvio;
    }

    @BeforeEach
    public void initTest() {
        contatoTipoEnvio = createEntity(em);
    }

    @Test
    @Transactional
    public void createContatoTipoEnvio() throws Exception {
        int databaseSizeBeforeCreate = contatoTipoEnvioRepository.findAll().size();
        // Create the ContatoTipoEnvio
        ContatoTipoEnvioDTO contatoTipoEnvioDTO = contatoTipoEnvioMapper.toDto(contatoTipoEnvio);
        restContatoTipoEnvioMockMvc.perform(post("/api/contato-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoTipoEnvioDTO)))
            .andExpect(status().isCreated());

        // Validate the ContatoTipoEnvio in the database
        List<ContatoTipoEnvio> contatoTipoEnvioList = contatoTipoEnvioRepository.findAll();
        assertThat(contatoTipoEnvioList).hasSize(databaseSizeBeforeCreate + 1);
        ContatoTipoEnvio testContatoTipoEnvio = contatoTipoEnvioList.get(contatoTipoEnvioList.size() - 1);
        assertThat(testContatoTipoEnvio.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testContatoTipoEnvio.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testContatoTipoEnvio.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testContatoTipoEnvio.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createContatoTipoEnvioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contatoTipoEnvioRepository.findAll().size();

        // Create the ContatoTipoEnvio with an existing ID
        contatoTipoEnvio.setId(1L);
        ContatoTipoEnvioDTO contatoTipoEnvioDTO = contatoTipoEnvioMapper.toDto(contatoTipoEnvio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContatoTipoEnvioMockMvc.perform(post("/api/contato-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContatoTipoEnvio in the database
        List<ContatoTipoEnvio> contatoTipoEnvioList = contatoTipoEnvioRepository.findAll();
        assertThat(contatoTipoEnvioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoTipoEnvioRepository.findAll().size();
        // set the field null
        contatoTipoEnvio.setNome(null);

        // Create the ContatoTipoEnvio, which fails.
        ContatoTipoEnvioDTO contatoTipoEnvioDTO = contatoTipoEnvioMapper.toDto(contatoTipoEnvio);


        restContatoTipoEnvioMockMvc.perform(post("/api/contato-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        List<ContatoTipoEnvio> contatoTipoEnvioList = contatoTipoEnvioRepository.findAll();
        assertThat(contatoTipoEnvioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoTipoEnvioRepository.findAll().size();
        // set the field null
        contatoTipoEnvio.setCreated(null);

        // Create the ContatoTipoEnvio, which fails.
        ContatoTipoEnvioDTO contatoTipoEnvioDTO = contatoTipoEnvioMapper.toDto(contatoTipoEnvio);


        restContatoTipoEnvioMockMvc.perform(post("/api/contato-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        List<ContatoTipoEnvio> contatoTipoEnvioList = contatoTipoEnvioRepository.findAll();
        assertThat(contatoTipoEnvioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnvios() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList
        restContatoTipoEnvioMockMvc.perform(get("/api/contato-tipo-envios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contatoTipoEnvio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getContatoTipoEnvio() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get the contatoTipoEnvio
        restContatoTipoEnvioMockMvc.perform(get("/api/contato-tipo-envios/{id}", contatoTipoEnvio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contatoTipoEnvio.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getContatoTipoEnviosByIdFiltering() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        Long id = contatoTipoEnvio.getId();

        defaultContatoTipoEnvioShouldBeFound("id.equals=" + id);
        defaultContatoTipoEnvioShouldNotBeFound("id.notEquals=" + id);

        defaultContatoTipoEnvioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContatoTipoEnvioShouldNotBeFound("id.greaterThan=" + id);

        defaultContatoTipoEnvioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContatoTipoEnvioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllContatoTipoEnviosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where nome equals to DEFAULT_NOME
        defaultContatoTipoEnvioShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the contatoTipoEnvioList where nome equals to UPDATED_NOME
        defaultContatoTipoEnvioShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnviosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where nome not equals to DEFAULT_NOME
        defaultContatoTipoEnvioShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the contatoTipoEnvioList where nome not equals to UPDATED_NOME
        defaultContatoTipoEnvioShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnviosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultContatoTipoEnvioShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the contatoTipoEnvioList where nome equals to UPDATED_NOME
        defaultContatoTipoEnvioShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnviosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where nome is not null
        defaultContatoTipoEnvioShouldBeFound("nome.specified=true");

        // Get all the contatoTipoEnvioList where nome is null
        defaultContatoTipoEnvioShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllContatoTipoEnviosByNomeContainsSomething() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where nome contains DEFAULT_NOME
        defaultContatoTipoEnvioShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the contatoTipoEnvioList where nome contains UPDATED_NOME
        defaultContatoTipoEnvioShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnviosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where nome does not contain DEFAULT_NOME
        defaultContatoTipoEnvioShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the contatoTipoEnvioList where nome does not contain UPDATED_NOME
        defaultContatoTipoEnvioShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllContatoTipoEnviosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where descricao equals to DEFAULT_DESCRICAO
        defaultContatoTipoEnvioShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the contatoTipoEnvioList where descricao equals to UPDATED_DESCRICAO
        defaultContatoTipoEnvioShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnviosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where descricao not equals to DEFAULT_DESCRICAO
        defaultContatoTipoEnvioShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the contatoTipoEnvioList where descricao not equals to UPDATED_DESCRICAO
        defaultContatoTipoEnvioShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnviosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultContatoTipoEnvioShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the contatoTipoEnvioList where descricao equals to UPDATED_DESCRICAO
        defaultContatoTipoEnvioShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnviosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where descricao is not null
        defaultContatoTipoEnvioShouldBeFound("descricao.specified=true");

        // Get all the contatoTipoEnvioList where descricao is null
        defaultContatoTipoEnvioShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllContatoTipoEnviosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where descricao contains DEFAULT_DESCRICAO
        defaultContatoTipoEnvioShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the contatoTipoEnvioList where descricao contains UPDATED_DESCRICAO
        defaultContatoTipoEnvioShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnviosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where descricao does not contain DEFAULT_DESCRICAO
        defaultContatoTipoEnvioShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the contatoTipoEnvioList where descricao does not contain UPDATED_DESCRICAO
        defaultContatoTipoEnvioShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllContatoTipoEnviosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where created equals to DEFAULT_CREATED
        defaultContatoTipoEnvioShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the contatoTipoEnvioList where created equals to UPDATED_CREATED
        defaultContatoTipoEnvioShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnviosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where created not equals to DEFAULT_CREATED
        defaultContatoTipoEnvioShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the contatoTipoEnvioList where created not equals to UPDATED_CREATED
        defaultContatoTipoEnvioShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnviosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultContatoTipoEnvioShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the contatoTipoEnvioList where created equals to UPDATED_CREATED
        defaultContatoTipoEnvioShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnviosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where created is not null
        defaultContatoTipoEnvioShouldBeFound("created.specified=true");

        // Get all the contatoTipoEnvioList where created is null
        defaultContatoTipoEnvioShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnviosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where updated equals to DEFAULT_UPDATED
        defaultContatoTipoEnvioShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the contatoTipoEnvioList where updated equals to UPDATED_UPDATED
        defaultContatoTipoEnvioShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnviosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where updated not equals to DEFAULT_UPDATED
        defaultContatoTipoEnvioShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the contatoTipoEnvioList where updated not equals to UPDATED_UPDATED
        defaultContatoTipoEnvioShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnviosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultContatoTipoEnvioShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the contatoTipoEnvioList where updated equals to UPDATED_UPDATED
        defaultContatoTipoEnvioShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnviosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList where updated is not null
        defaultContatoTipoEnvioShouldBeFound("updated.specified=true");

        // Get all the contatoTipoEnvioList where updated is null
        defaultContatoTipoEnvioShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnviosByContatoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Contato contato = contatoTipoEnvio.getContato();
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);
        Long contatoId = contato.getId();

        // Get all the contatoTipoEnvioList where contato equals to contatoId
        defaultContatoTipoEnvioShouldBeFound("contatoId.equals=" + contatoId);

        // Get all the contatoTipoEnvioList where contato equals to contatoId + 1
        defaultContatoTipoEnvioShouldNotBeFound("contatoId.equals=" + (contatoId + 1));
    }


    @Test
    @Transactional
    public void getAllContatoTipoEnviosByTipoEnvioIsEqualToSomething() throws Exception {
        // Get already existing entity
        TipoEnvio tipoEnvio = contatoTipoEnvio.getTipoEnvio();
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);
        Long tipoEnvioId = tipoEnvio.getId();

        // Get all the contatoTipoEnvioList where tipoEnvio equals to tipoEnvioId
        defaultContatoTipoEnvioShouldBeFound("tipoEnvioId.equals=" + tipoEnvioId);

        // Get all the contatoTipoEnvioList where tipoEnvio equals to tipoEnvioId + 1
        defaultContatoTipoEnvioShouldNotBeFound("tipoEnvioId.equals=" + (tipoEnvioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContatoTipoEnvioShouldBeFound(String filter) throws Exception {
        restContatoTipoEnvioMockMvc.perform(get("/api/contato-tipo-envios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contatoTipoEnvio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restContatoTipoEnvioMockMvc.perform(get("/api/contato-tipo-envios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContatoTipoEnvioShouldNotBeFound(String filter) throws Exception {
        restContatoTipoEnvioMockMvc.perform(get("/api/contato-tipo-envios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContatoTipoEnvioMockMvc.perform(get("/api/contato-tipo-envios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingContatoTipoEnvio() throws Exception {
        // Get the contatoTipoEnvio
        restContatoTipoEnvioMockMvc.perform(get("/api/contato-tipo-envios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContatoTipoEnvio() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        int databaseSizeBeforeUpdate = contatoTipoEnvioRepository.findAll().size();

        // Update the contatoTipoEnvio
        ContatoTipoEnvio updatedContatoTipoEnvio = contatoTipoEnvioRepository.findById(contatoTipoEnvio.getId()).get();
        // Disconnect from session so that the updates on updatedContatoTipoEnvio are not directly saved in db
        em.detach(updatedContatoTipoEnvio);
        updatedContatoTipoEnvio
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        ContatoTipoEnvioDTO contatoTipoEnvioDTO = contatoTipoEnvioMapper.toDto(updatedContatoTipoEnvio);

        restContatoTipoEnvioMockMvc.perform(put("/api/contato-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoTipoEnvioDTO)))
            .andExpect(status().isOk());

        // Validate the ContatoTipoEnvio in the database
        List<ContatoTipoEnvio> contatoTipoEnvioList = contatoTipoEnvioRepository.findAll();
        assertThat(contatoTipoEnvioList).hasSize(databaseSizeBeforeUpdate);
        ContatoTipoEnvio testContatoTipoEnvio = contatoTipoEnvioList.get(contatoTipoEnvioList.size() - 1);
        assertThat(testContatoTipoEnvio.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testContatoTipoEnvio.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testContatoTipoEnvio.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testContatoTipoEnvio.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingContatoTipoEnvio() throws Exception {
        int databaseSizeBeforeUpdate = contatoTipoEnvioRepository.findAll().size();

        // Create the ContatoTipoEnvio
        ContatoTipoEnvioDTO contatoTipoEnvioDTO = contatoTipoEnvioMapper.toDto(contatoTipoEnvio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContatoTipoEnvioMockMvc.perform(put("/api/contato-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContatoTipoEnvio in the database
        List<ContatoTipoEnvio> contatoTipoEnvioList = contatoTipoEnvioRepository.findAll();
        assertThat(contatoTipoEnvioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContatoTipoEnvio() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        int databaseSizeBeforeDelete = contatoTipoEnvioRepository.findAll().size();

        // Delete the contatoTipoEnvio
        restContatoTipoEnvioMockMvc.perform(delete("/api/contato-tipo-envios/{id}", contatoTipoEnvio.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContatoTipoEnvio> contatoTipoEnvioList = contatoTipoEnvioRepository.findAll();
        assertThat(contatoTipoEnvioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
