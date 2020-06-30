package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Empresa;
import org.tempestade.nucleo.repository.EmpresaRepository;
import org.tempestade.nucleo.service.EmpresaService;
import org.tempestade.nucleo.service.dto.EmpresaDTO;
import org.tempestade.nucleo.service.mapper.EmpresaMapper;
import org.tempestade.nucleo.service.dto.EmpresaCriteria;
import org.tempestade.nucleo.service.EmpresaQueryService;

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
 * Integration tests for the {@link EmpresaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmpresaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_REDUZIDO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_REDUZIDO = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final String DEFAULT_APELIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELIDO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaMapper empresaMapper;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private EmpresaQueryService empresaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpresaMockMvc;

    private Empresa empresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .email(DEFAULT_EMAIL)
            .titulo(DEFAULT_TITULO)
            .nomeReduzido(DEFAULT_NOME_REDUZIDO)
            .logo(DEFAULT_LOGO)
            .apelido(DEFAULT_APELIDO)
            .observacao(DEFAULT_OBSERVACAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return empresa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createUpdatedEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .email(UPDATED_EMAIL)
            .titulo(UPDATED_TITULO)
            .nomeReduzido(UPDATED_NOME_REDUZIDO)
            .logo(UPDATED_LOGO)
            .apelido(UPDATED_APELIDO)
            .observacao(UPDATED_OBSERVACAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return empresa;
    }

    @BeforeEach
    public void initTest() {
        empresa = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpresa() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();
        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);
        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isCreated());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate + 1);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEmpresa.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testEmpresa.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmpresa.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testEmpresa.getNomeReduzido()).isEqualTo(DEFAULT_NOME_REDUZIDO);
        assertThat(testEmpresa.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testEmpresa.getApelido()).isEqualTo(DEFAULT_APELIDO);
        assertThat(testEmpresa.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testEmpresa.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testEmpresa.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createEmpresaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();

        // Create the Empresa with an existing ID
        empresa.setId(1L);
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setNome(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);


        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setEmail(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);


        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setCreated(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);


        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmpresas() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList
        restEmpresaMockMvc.perform(get("/api/empresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].nomeReduzido").value(hasItem(DEFAULT_NOME_REDUZIDO)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].apelido").value(hasItem(DEFAULT_APELIDO)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get the empresa
        restEmpresaMockMvc.perform(get("/api/empresas/{id}", empresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empresa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.nomeReduzido").value(DEFAULT_NOME_REDUZIDO))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO))
            .andExpect(jsonPath("$.apelido").value(DEFAULT_APELIDO))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getEmpresasByIdFiltering() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        Long id = empresa.getId();

        defaultEmpresaShouldBeFound("id.equals=" + id);
        defaultEmpresaShouldNotBeFound("id.notEquals=" + id);

        defaultEmpresaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmpresaShouldNotBeFound("id.greaterThan=" + id);

        defaultEmpresaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmpresaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmpresasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nome equals to DEFAULT_NOME
        defaultEmpresaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the empresaList where nome equals to UPDATED_NOME
        defaultEmpresaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nome not equals to DEFAULT_NOME
        defaultEmpresaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the empresaList where nome not equals to UPDATED_NOME
        defaultEmpresaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultEmpresaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the empresaList where nome equals to UPDATED_NOME
        defaultEmpresaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nome is not null
        defaultEmpresaShouldBeFound("nome.specified=true");

        // Get all the empresaList where nome is null
        defaultEmpresaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresasByNomeContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nome contains DEFAULT_NOME
        defaultEmpresaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the empresaList where nome contains UPDATED_NOME
        defaultEmpresaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nome does not contain DEFAULT_NOME
        defaultEmpresaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the empresaList where nome does not contain UPDATED_NOME
        defaultEmpresaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllEmpresasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where descricao equals to DEFAULT_DESCRICAO
        defaultEmpresaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the empresaList where descricao equals to UPDATED_DESCRICAO
        defaultEmpresaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where descricao not equals to DEFAULT_DESCRICAO
        defaultEmpresaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the empresaList where descricao not equals to UPDATED_DESCRICAO
        defaultEmpresaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultEmpresaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the empresaList where descricao equals to UPDATED_DESCRICAO
        defaultEmpresaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where descricao is not null
        defaultEmpresaShouldBeFound("descricao.specified=true");

        // Get all the empresaList where descricao is null
        defaultEmpresaShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where descricao contains DEFAULT_DESCRICAO
        defaultEmpresaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the empresaList where descricao contains UPDATED_DESCRICAO
        defaultEmpresaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where descricao does not contain DEFAULT_DESCRICAO
        defaultEmpresaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the empresaList where descricao does not contain UPDATED_DESCRICAO
        defaultEmpresaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllEmpresasByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where email equals to DEFAULT_EMAIL
        defaultEmpresaShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the empresaList where email equals to UPDATED_EMAIL
        defaultEmpresaShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where email not equals to DEFAULT_EMAIL
        defaultEmpresaShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the empresaList where email not equals to UPDATED_EMAIL
        defaultEmpresaShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEmpresaShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the empresaList where email equals to UPDATED_EMAIL
        defaultEmpresaShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where email is not null
        defaultEmpresaShouldBeFound("email.specified=true");

        // Get all the empresaList where email is null
        defaultEmpresaShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresasByEmailContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where email contains DEFAULT_EMAIL
        defaultEmpresaShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the empresaList where email contains UPDATED_EMAIL
        defaultEmpresaShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where email does not contain DEFAULT_EMAIL
        defaultEmpresaShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the empresaList where email does not contain UPDATED_EMAIL
        defaultEmpresaShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllEmpresasByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where titulo equals to DEFAULT_TITULO
        defaultEmpresaShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the empresaList where titulo equals to UPDATED_TITULO
        defaultEmpresaShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where titulo not equals to DEFAULT_TITULO
        defaultEmpresaShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the empresaList where titulo not equals to UPDATED_TITULO
        defaultEmpresaShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultEmpresaShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the empresaList where titulo equals to UPDATED_TITULO
        defaultEmpresaShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where titulo is not null
        defaultEmpresaShouldBeFound("titulo.specified=true");

        // Get all the empresaList where titulo is null
        defaultEmpresaShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresasByTituloContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where titulo contains DEFAULT_TITULO
        defaultEmpresaShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the empresaList where titulo contains UPDATED_TITULO
        defaultEmpresaShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where titulo does not contain DEFAULT_TITULO
        defaultEmpresaShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the empresaList where titulo does not contain UPDATED_TITULO
        defaultEmpresaShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllEmpresasByNomeReduzidoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nomeReduzido equals to DEFAULT_NOME_REDUZIDO
        defaultEmpresaShouldBeFound("nomeReduzido.equals=" + DEFAULT_NOME_REDUZIDO);

        // Get all the empresaList where nomeReduzido equals to UPDATED_NOME_REDUZIDO
        defaultEmpresaShouldNotBeFound("nomeReduzido.equals=" + UPDATED_NOME_REDUZIDO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNomeReduzidoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nomeReduzido not equals to DEFAULT_NOME_REDUZIDO
        defaultEmpresaShouldNotBeFound("nomeReduzido.notEquals=" + DEFAULT_NOME_REDUZIDO);

        // Get all the empresaList where nomeReduzido not equals to UPDATED_NOME_REDUZIDO
        defaultEmpresaShouldBeFound("nomeReduzido.notEquals=" + UPDATED_NOME_REDUZIDO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNomeReduzidoIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nomeReduzido in DEFAULT_NOME_REDUZIDO or UPDATED_NOME_REDUZIDO
        defaultEmpresaShouldBeFound("nomeReduzido.in=" + DEFAULT_NOME_REDUZIDO + "," + UPDATED_NOME_REDUZIDO);

        // Get all the empresaList where nomeReduzido equals to UPDATED_NOME_REDUZIDO
        defaultEmpresaShouldNotBeFound("nomeReduzido.in=" + UPDATED_NOME_REDUZIDO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNomeReduzidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nomeReduzido is not null
        defaultEmpresaShouldBeFound("nomeReduzido.specified=true");

        // Get all the empresaList where nomeReduzido is null
        defaultEmpresaShouldNotBeFound("nomeReduzido.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresasByNomeReduzidoContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nomeReduzido contains DEFAULT_NOME_REDUZIDO
        defaultEmpresaShouldBeFound("nomeReduzido.contains=" + DEFAULT_NOME_REDUZIDO);

        // Get all the empresaList where nomeReduzido contains UPDATED_NOME_REDUZIDO
        defaultEmpresaShouldNotBeFound("nomeReduzido.contains=" + UPDATED_NOME_REDUZIDO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNomeReduzidoNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nomeReduzido does not contain DEFAULT_NOME_REDUZIDO
        defaultEmpresaShouldNotBeFound("nomeReduzido.doesNotContain=" + DEFAULT_NOME_REDUZIDO);

        // Get all the empresaList where nomeReduzido does not contain UPDATED_NOME_REDUZIDO
        defaultEmpresaShouldBeFound("nomeReduzido.doesNotContain=" + UPDATED_NOME_REDUZIDO);
    }


    @Test
    @Transactional
    public void getAllEmpresasByLogoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where logo equals to DEFAULT_LOGO
        defaultEmpresaShouldBeFound("logo.equals=" + DEFAULT_LOGO);

        // Get all the empresaList where logo equals to UPDATED_LOGO
        defaultEmpresaShouldNotBeFound("logo.equals=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByLogoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where logo not equals to DEFAULT_LOGO
        defaultEmpresaShouldNotBeFound("logo.notEquals=" + DEFAULT_LOGO);

        // Get all the empresaList where logo not equals to UPDATED_LOGO
        defaultEmpresaShouldBeFound("logo.notEquals=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByLogoIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where logo in DEFAULT_LOGO or UPDATED_LOGO
        defaultEmpresaShouldBeFound("logo.in=" + DEFAULT_LOGO + "," + UPDATED_LOGO);

        // Get all the empresaList where logo equals to UPDATED_LOGO
        defaultEmpresaShouldNotBeFound("logo.in=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByLogoIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where logo is not null
        defaultEmpresaShouldBeFound("logo.specified=true");

        // Get all the empresaList where logo is null
        defaultEmpresaShouldNotBeFound("logo.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresasByLogoContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where logo contains DEFAULT_LOGO
        defaultEmpresaShouldBeFound("logo.contains=" + DEFAULT_LOGO);

        // Get all the empresaList where logo contains UPDATED_LOGO
        defaultEmpresaShouldNotBeFound("logo.contains=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByLogoNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where logo does not contain DEFAULT_LOGO
        defaultEmpresaShouldNotBeFound("logo.doesNotContain=" + DEFAULT_LOGO);

        // Get all the empresaList where logo does not contain UPDATED_LOGO
        defaultEmpresaShouldBeFound("logo.doesNotContain=" + UPDATED_LOGO);
    }


    @Test
    @Transactional
    public void getAllEmpresasByApelidoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where apelido equals to DEFAULT_APELIDO
        defaultEmpresaShouldBeFound("apelido.equals=" + DEFAULT_APELIDO);

        // Get all the empresaList where apelido equals to UPDATED_APELIDO
        defaultEmpresaShouldNotBeFound("apelido.equals=" + UPDATED_APELIDO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByApelidoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where apelido not equals to DEFAULT_APELIDO
        defaultEmpresaShouldNotBeFound("apelido.notEquals=" + DEFAULT_APELIDO);

        // Get all the empresaList where apelido not equals to UPDATED_APELIDO
        defaultEmpresaShouldBeFound("apelido.notEquals=" + UPDATED_APELIDO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByApelidoIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where apelido in DEFAULT_APELIDO or UPDATED_APELIDO
        defaultEmpresaShouldBeFound("apelido.in=" + DEFAULT_APELIDO + "," + UPDATED_APELIDO);

        // Get all the empresaList where apelido equals to UPDATED_APELIDO
        defaultEmpresaShouldNotBeFound("apelido.in=" + UPDATED_APELIDO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByApelidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where apelido is not null
        defaultEmpresaShouldBeFound("apelido.specified=true");

        // Get all the empresaList where apelido is null
        defaultEmpresaShouldNotBeFound("apelido.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresasByApelidoContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where apelido contains DEFAULT_APELIDO
        defaultEmpresaShouldBeFound("apelido.contains=" + DEFAULT_APELIDO);

        // Get all the empresaList where apelido contains UPDATED_APELIDO
        defaultEmpresaShouldNotBeFound("apelido.contains=" + UPDATED_APELIDO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByApelidoNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where apelido does not contain DEFAULT_APELIDO
        defaultEmpresaShouldNotBeFound("apelido.doesNotContain=" + DEFAULT_APELIDO);

        // Get all the empresaList where apelido does not contain UPDATED_APELIDO
        defaultEmpresaShouldBeFound("apelido.doesNotContain=" + UPDATED_APELIDO);
    }


    @Test
    @Transactional
    public void getAllEmpresasByObservacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where observacao equals to DEFAULT_OBSERVACAO
        defaultEmpresaShouldBeFound("observacao.equals=" + DEFAULT_OBSERVACAO);

        // Get all the empresaList where observacao equals to UPDATED_OBSERVACAO
        defaultEmpresaShouldNotBeFound("observacao.equals=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByObservacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where observacao not equals to DEFAULT_OBSERVACAO
        defaultEmpresaShouldNotBeFound("observacao.notEquals=" + DEFAULT_OBSERVACAO);

        // Get all the empresaList where observacao not equals to UPDATED_OBSERVACAO
        defaultEmpresaShouldBeFound("observacao.notEquals=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByObservacaoIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where observacao in DEFAULT_OBSERVACAO or UPDATED_OBSERVACAO
        defaultEmpresaShouldBeFound("observacao.in=" + DEFAULT_OBSERVACAO + "," + UPDATED_OBSERVACAO);

        // Get all the empresaList where observacao equals to UPDATED_OBSERVACAO
        defaultEmpresaShouldNotBeFound("observacao.in=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByObservacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where observacao is not null
        defaultEmpresaShouldBeFound("observacao.specified=true");

        // Get all the empresaList where observacao is null
        defaultEmpresaShouldNotBeFound("observacao.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresasByObservacaoContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where observacao contains DEFAULT_OBSERVACAO
        defaultEmpresaShouldBeFound("observacao.contains=" + DEFAULT_OBSERVACAO);

        // Get all the empresaList where observacao contains UPDATED_OBSERVACAO
        defaultEmpresaShouldNotBeFound("observacao.contains=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByObservacaoNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where observacao does not contain DEFAULT_OBSERVACAO
        defaultEmpresaShouldNotBeFound("observacao.doesNotContain=" + DEFAULT_OBSERVACAO);

        // Get all the empresaList where observacao does not contain UPDATED_OBSERVACAO
        defaultEmpresaShouldBeFound("observacao.doesNotContain=" + UPDATED_OBSERVACAO);
    }


    @Test
    @Transactional
    public void getAllEmpresasByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where created equals to DEFAULT_CREATED
        defaultEmpresaShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the empresaList where created equals to UPDATED_CREATED
        defaultEmpresaShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllEmpresasByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where created not equals to DEFAULT_CREATED
        defaultEmpresaShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the empresaList where created not equals to UPDATED_CREATED
        defaultEmpresaShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllEmpresasByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultEmpresaShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the empresaList where created equals to UPDATED_CREATED
        defaultEmpresaShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllEmpresasByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where created is not null
        defaultEmpresaShouldBeFound("created.specified=true");

        // Get all the empresaList where created is null
        defaultEmpresaShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmpresasByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where updated equals to DEFAULT_UPDATED
        defaultEmpresaShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the empresaList where updated equals to UPDATED_UPDATED
        defaultEmpresaShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllEmpresasByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where updated not equals to DEFAULT_UPDATED
        defaultEmpresaShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the empresaList where updated not equals to UPDATED_UPDATED
        defaultEmpresaShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllEmpresasByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultEmpresaShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the empresaList where updated equals to UPDATED_UPDATED
        defaultEmpresaShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllEmpresasByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where updated is not null
        defaultEmpresaShouldBeFound("updated.specified=true");

        // Get all the empresaList where updated is null
        defaultEmpresaShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmpresaShouldBeFound(String filter) throws Exception {
        restEmpresaMockMvc.perform(get("/api/empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].nomeReduzido").value(hasItem(DEFAULT_NOME_REDUZIDO)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].apelido").value(hasItem(DEFAULT_APELIDO)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restEmpresaMockMvc.perform(get("/api/empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmpresaShouldNotBeFound(String filter) throws Exception {
        restEmpresaMockMvc.perform(get("/api/empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmpresaMockMvc.perform(get("/api/empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmpresa() throws Exception {
        // Get the empresa
        restEmpresaMockMvc.perform(get("/api/empresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa
        Empresa updatedEmpresa = empresaRepository.findById(empresa.getId()).get();
        // Disconnect from session so that the updates on updatedEmpresa are not directly saved in db
        em.detach(updatedEmpresa);
        updatedEmpresa
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .email(UPDATED_EMAIL)
            .titulo(UPDATED_TITULO)
            .nomeReduzido(UPDATED_NOME_REDUZIDO)
            .logo(UPDATED_LOGO)
            .apelido(UPDATED_APELIDO)
            .observacao(UPDATED_OBSERVACAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        EmpresaDTO empresaDTO = empresaMapper.toDto(updatedEmpresa);

        restEmpresaMockMvc.perform(put("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEmpresa.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testEmpresa.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmpresa.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testEmpresa.getNomeReduzido()).isEqualTo(UPDATED_NOME_REDUZIDO);
        assertThat(testEmpresa.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testEmpresa.getApelido()).isEqualTo(UPDATED_APELIDO);
        assertThat(testEmpresa.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testEmpresa.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testEmpresa.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc.perform(put("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeDelete = empresaRepository.findAll().size();

        // Delete the empresa
        restEmpresaMockMvc.perform(delete("/api/empresas/{id}", empresa.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
