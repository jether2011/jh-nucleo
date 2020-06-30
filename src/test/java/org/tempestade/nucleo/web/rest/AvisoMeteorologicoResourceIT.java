package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.AvisoMeteorologico;
import org.tempestade.nucleo.domain.PlanoRecurso;
import org.tempestade.nucleo.repository.AvisoMeteorologicoRepository;
import org.tempestade.nucleo.service.AvisoMeteorologicoService;
import org.tempestade.nucleo.service.dto.AvisoMeteorologicoDTO;
import org.tempestade.nucleo.service.mapper.AvisoMeteorologicoMapper;
import org.tempestade.nucleo.service.dto.AvisoMeteorologicoCriteria;
import org.tempestade.nucleo.service.AvisoMeteorologicoQueryService;

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
 * Integration tests for the {@link AvisoMeteorologicoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AvisoMeteorologicoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_ASSUNTO = "AAAAAAAAAA";
    private static final String UPDATED_ASSUNTO = "BBBBBBBBBB";

    private static final Instant DEFAULT_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGEM_ASSINATURA = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEM_ASSINATURA = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AvisoMeteorologicoRepository avisoMeteorologicoRepository;

    @Autowired
    private AvisoMeteorologicoMapper avisoMeteorologicoMapper;

    @Autowired
    private AvisoMeteorologicoService avisoMeteorologicoService;

    @Autowired
    private AvisoMeteorologicoQueryService avisoMeteorologicoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvisoMeteorologicoMockMvc;

    private AvisoMeteorologico avisoMeteorologico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvisoMeteorologico createEntity(EntityManager em) {
        AvisoMeteorologico avisoMeteorologico = new AvisoMeteorologico()
            .nome(DEFAULT_NOME)
            .assunto(DEFAULT_ASSUNTO)
            .inicio(DEFAULT_INICIO)
            .fim(DEFAULT_FIM)
            .texto(DEFAULT_TEXTO)
            .imagem(DEFAULT_IMAGEM)
            .imagemAssinatura(DEFAULT_IMAGEM_ASSINATURA)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        // Add required entity
        PlanoRecurso planoRecurso;
        if (TestUtil.findAll(em, PlanoRecurso.class).isEmpty()) {
            planoRecurso = PlanoRecursoResourceIT.createEntity(em);
            em.persist(planoRecurso);
            em.flush();
        } else {
            planoRecurso = TestUtil.findAll(em, PlanoRecurso.class).get(0);
        }
        avisoMeteorologico.setPlanoRecurso(planoRecurso);
        return avisoMeteorologico;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvisoMeteorologico createUpdatedEntity(EntityManager em) {
        AvisoMeteorologico avisoMeteorologico = new AvisoMeteorologico()
            .nome(UPDATED_NOME)
            .assunto(UPDATED_ASSUNTO)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .texto(UPDATED_TEXTO)
            .imagem(UPDATED_IMAGEM)
            .imagemAssinatura(UPDATED_IMAGEM_ASSINATURA)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        // Add required entity
        PlanoRecurso planoRecurso;
        if (TestUtil.findAll(em, PlanoRecurso.class).isEmpty()) {
            planoRecurso = PlanoRecursoResourceIT.createUpdatedEntity(em);
            em.persist(planoRecurso);
            em.flush();
        } else {
            planoRecurso = TestUtil.findAll(em, PlanoRecurso.class).get(0);
        }
        avisoMeteorologico.setPlanoRecurso(planoRecurso);
        return avisoMeteorologico;
    }

    @BeforeEach
    public void initTest() {
        avisoMeteorologico = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvisoMeteorologico() throws Exception {
        int databaseSizeBeforeCreate = avisoMeteorologicoRepository.findAll().size();
        // Create the AvisoMeteorologico
        AvisoMeteorologicoDTO avisoMeteorologicoDTO = avisoMeteorologicoMapper.toDto(avisoMeteorologico);
        restAvisoMeteorologicoMockMvc.perform(post("/api/aviso-meteorologicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoMeteorologicoDTO)))
            .andExpect(status().isCreated());

        // Validate the AvisoMeteorologico in the database
        List<AvisoMeteorologico> avisoMeteorologicoList = avisoMeteorologicoRepository.findAll();
        assertThat(avisoMeteorologicoList).hasSize(databaseSizeBeforeCreate + 1);
        AvisoMeteorologico testAvisoMeteorologico = avisoMeteorologicoList.get(avisoMeteorologicoList.size() - 1);
        assertThat(testAvisoMeteorologico.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAvisoMeteorologico.getAssunto()).isEqualTo(DEFAULT_ASSUNTO);
        assertThat(testAvisoMeteorologico.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testAvisoMeteorologico.getFim()).isEqualTo(DEFAULT_FIM);
        assertThat(testAvisoMeteorologico.getTexto()).isEqualTo(DEFAULT_TEXTO);
        assertThat(testAvisoMeteorologico.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testAvisoMeteorologico.getImagemAssinatura()).isEqualTo(DEFAULT_IMAGEM_ASSINATURA);
        assertThat(testAvisoMeteorologico.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAvisoMeteorologico.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createAvisoMeteorologicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avisoMeteorologicoRepository.findAll().size();

        // Create the AvisoMeteorologico with an existing ID
        avisoMeteorologico.setId(1L);
        AvisoMeteorologicoDTO avisoMeteorologicoDTO = avisoMeteorologicoMapper.toDto(avisoMeteorologico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvisoMeteorologicoMockMvc.perform(post("/api/aviso-meteorologicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoMeteorologicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AvisoMeteorologico in the database
        List<AvisoMeteorologico> avisoMeteorologicoList = avisoMeteorologicoRepository.findAll();
        assertThat(avisoMeteorologicoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoMeteorologicoRepository.findAll().size();
        // set the field null
        avisoMeteorologico.setNome(null);

        // Create the AvisoMeteorologico, which fails.
        AvisoMeteorologicoDTO avisoMeteorologicoDTO = avisoMeteorologicoMapper.toDto(avisoMeteorologico);


        restAvisoMeteorologicoMockMvc.perform(post("/api/aviso-meteorologicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoMeteorologicoDTO)))
            .andExpect(status().isBadRequest());

        List<AvisoMeteorologico> avisoMeteorologicoList = avisoMeteorologicoRepository.findAll();
        assertThat(avisoMeteorologicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoMeteorologicoRepository.findAll().size();
        // set the field null
        avisoMeteorologico.setInicio(null);

        // Create the AvisoMeteorologico, which fails.
        AvisoMeteorologicoDTO avisoMeteorologicoDTO = avisoMeteorologicoMapper.toDto(avisoMeteorologico);


        restAvisoMeteorologicoMockMvc.perform(post("/api/aviso-meteorologicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoMeteorologicoDTO)))
            .andExpect(status().isBadRequest());

        List<AvisoMeteorologico> avisoMeteorologicoList = avisoMeteorologicoRepository.findAll();
        assertThat(avisoMeteorologicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFimIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoMeteorologicoRepository.findAll().size();
        // set the field null
        avisoMeteorologico.setFim(null);

        // Create the AvisoMeteorologico, which fails.
        AvisoMeteorologicoDTO avisoMeteorologicoDTO = avisoMeteorologicoMapper.toDto(avisoMeteorologico);


        restAvisoMeteorologicoMockMvc.perform(post("/api/aviso-meteorologicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoMeteorologicoDTO)))
            .andExpect(status().isBadRequest());

        List<AvisoMeteorologico> avisoMeteorologicoList = avisoMeteorologicoRepository.findAll();
        assertThat(avisoMeteorologicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextoIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoMeteorologicoRepository.findAll().size();
        // set the field null
        avisoMeteorologico.setTexto(null);

        // Create the AvisoMeteorologico, which fails.
        AvisoMeteorologicoDTO avisoMeteorologicoDTO = avisoMeteorologicoMapper.toDto(avisoMeteorologico);


        restAvisoMeteorologicoMockMvc.perform(post("/api/aviso-meteorologicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoMeteorologicoDTO)))
            .andExpect(status().isBadRequest());

        List<AvisoMeteorologico> avisoMeteorologicoList = avisoMeteorologicoRepository.findAll();
        assertThat(avisoMeteorologicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImagemIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoMeteorologicoRepository.findAll().size();
        // set the field null
        avisoMeteorologico.setImagem(null);

        // Create the AvisoMeteorologico, which fails.
        AvisoMeteorologicoDTO avisoMeteorologicoDTO = avisoMeteorologicoMapper.toDto(avisoMeteorologico);


        restAvisoMeteorologicoMockMvc.perform(post("/api/aviso-meteorologicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoMeteorologicoDTO)))
            .andExpect(status().isBadRequest());

        List<AvisoMeteorologico> avisoMeteorologicoList = avisoMeteorologicoRepository.findAll();
        assertThat(avisoMeteorologicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoMeteorologicoRepository.findAll().size();
        // set the field null
        avisoMeteorologico.setCreated(null);

        // Create the AvisoMeteorologico, which fails.
        AvisoMeteorologicoDTO avisoMeteorologicoDTO = avisoMeteorologicoMapper.toDto(avisoMeteorologico);


        restAvisoMeteorologicoMockMvc.perform(post("/api/aviso-meteorologicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoMeteorologicoDTO)))
            .andExpect(status().isBadRequest());

        List<AvisoMeteorologico> avisoMeteorologicoList = avisoMeteorologicoRepository.findAll();
        assertThat(avisoMeteorologicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicos() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList
        restAvisoMeteorologicoMockMvc.perform(get("/api/aviso-meteorologicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avisoMeteorologico.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO)))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fim").value(hasItem(DEFAULT_FIM.toString())))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.[*].imagemAssinatura").value(hasItem(DEFAULT_IMAGEM_ASSINATURA)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getAvisoMeteorologico() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get the avisoMeteorologico
        restAvisoMeteorologicoMockMvc.perform(get("/api/aviso-meteorologicos/{id}", avisoMeteorologico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avisoMeteorologico.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.assunto").value(DEFAULT_ASSUNTO))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO.toString()))
            .andExpect(jsonPath("$.fim").value(DEFAULT_FIM.toString()))
            .andExpect(jsonPath("$.texto").value(DEFAULT_TEXTO))
            .andExpect(jsonPath("$.imagem").value(DEFAULT_IMAGEM))
            .andExpect(jsonPath("$.imagemAssinatura").value(DEFAULT_IMAGEM_ASSINATURA))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getAvisoMeteorologicosByIdFiltering() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        Long id = avisoMeteorologico.getId();

        defaultAvisoMeteorologicoShouldBeFound("id.equals=" + id);
        defaultAvisoMeteorologicoShouldNotBeFound("id.notEquals=" + id);

        defaultAvisoMeteorologicoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAvisoMeteorologicoShouldNotBeFound("id.greaterThan=" + id);

        defaultAvisoMeteorologicoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAvisoMeteorologicoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where nome equals to DEFAULT_NOME
        defaultAvisoMeteorologicoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the avisoMeteorologicoList where nome equals to UPDATED_NOME
        defaultAvisoMeteorologicoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where nome not equals to DEFAULT_NOME
        defaultAvisoMeteorologicoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the avisoMeteorologicoList where nome not equals to UPDATED_NOME
        defaultAvisoMeteorologicoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAvisoMeteorologicoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the avisoMeteorologicoList where nome equals to UPDATED_NOME
        defaultAvisoMeteorologicoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where nome is not null
        defaultAvisoMeteorologicoShouldBeFound("nome.specified=true");

        // Get all the avisoMeteorologicoList where nome is null
        defaultAvisoMeteorologicoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAvisoMeteorologicosByNomeContainsSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where nome contains DEFAULT_NOME
        defaultAvisoMeteorologicoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the avisoMeteorologicoList where nome contains UPDATED_NOME
        defaultAvisoMeteorologicoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where nome does not contain DEFAULT_NOME
        defaultAvisoMeteorologicoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the avisoMeteorologicoList where nome does not contain UPDATED_NOME
        defaultAvisoMeteorologicoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByAssuntoIsEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where assunto equals to DEFAULT_ASSUNTO
        defaultAvisoMeteorologicoShouldBeFound("assunto.equals=" + DEFAULT_ASSUNTO);

        // Get all the avisoMeteorologicoList where assunto equals to UPDATED_ASSUNTO
        defaultAvisoMeteorologicoShouldNotBeFound("assunto.equals=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByAssuntoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where assunto not equals to DEFAULT_ASSUNTO
        defaultAvisoMeteorologicoShouldNotBeFound("assunto.notEquals=" + DEFAULT_ASSUNTO);

        // Get all the avisoMeteorologicoList where assunto not equals to UPDATED_ASSUNTO
        defaultAvisoMeteorologicoShouldBeFound("assunto.notEquals=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByAssuntoIsInShouldWork() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where assunto in DEFAULT_ASSUNTO or UPDATED_ASSUNTO
        defaultAvisoMeteorologicoShouldBeFound("assunto.in=" + DEFAULT_ASSUNTO + "," + UPDATED_ASSUNTO);

        // Get all the avisoMeteorologicoList where assunto equals to UPDATED_ASSUNTO
        defaultAvisoMeteorologicoShouldNotBeFound("assunto.in=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByAssuntoIsNullOrNotNull() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where assunto is not null
        defaultAvisoMeteorologicoShouldBeFound("assunto.specified=true");

        // Get all the avisoMeteorologicoList where assunto is null
        defaultAvisoMeteorologicoShouldNotBeFound("assunto.specified=false");
    }
                @Test
    @Transactional
    public void getAllAvisoMeteorologicosByAssuntoContainsSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where assunto contains DEFAULT_ASSUNTO
        defaultAvisoMeteorologicoShouldBeFound("assunto.contains=" + DEFAULT_ASSUNTO);

        // Get all the avisoMeteorologicoList where assunto contains UPDATED_ASSUNTO
        defaultAvisoMeteorologicoShouldNotBeFound("assunto.contains=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByAssuntoNotContainsSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where assunto does not contain DEFAULT_ASSUNTO
        defaultAvisoMeteorologicoShouldNotBeFound("assunto.doesNotContain=" + DEFAULT_ASSUNTO);

        // Get all the avisoMeteorologicoList where assunto does not contain UPDATED_ASSUNTO
        defaultAvisoMeteorologicoShouldBeFound("assunto.doesNotContain=" + UPDATED_ASSUNTO);
    }


    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where inicio equals to DEFAULT_INICIO
        defaultAvisoMeteorologicoShouldBeFound("inicio.equals=" + DEFAULT_INICIO);

        // Get all the avisoMeteorologicoList where inicio equals to UPDATED_INICIO
        defaultAvisoMeteorologicoShouldNotBeFound("inicio.equals=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByInicioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where inicio not equals to DEFAULT_INICIO
        defaultAvisoMeteorologicoShouldNotBeFound("inicio.notEquals=" + DEFAULT_INICIO);

        // Get all the avisoMeteorologicoList where inicio not equals to UPDATED_INICIO
        defaultAvisoMeteorologicoShouldBeFound("inicio.notEquals=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByInicioIsInShouldWork() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where inicio in DEFAULT_INICIO or UPDATED_INICIO
        defaultAvisoMeteorologicoShouldBeFound("inicio.in=" + DEFAULT_INICIO + "," + UPDATED_INICIO);

        // Get all the avisoMeteorologicoList where inicio equals to UPDATED_INICIO
        defaultAvisoMeteorologicoShouldNotBeFound("inicio.in=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where inicio is not null
        defaultAvisoMeteorologicoShouldBeFound("inicio.specified=true");

        // Get all the avisoMeteorologicoList where inicio is null
        defaultAvisoMeteorologicoShouldNotBeFound("inicio.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByFimIsEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where fim equals to DEFAULT_FIM
        defaultAvisoMeteorologicoShouldBeFound("fim.equals=" + DEFAULT_FIM);

        // Get all the avisoMeteorologicoList where fim equals to UPDATED_FIM
        defaultAvisoMeteorologicoShouldNotBeFound("fim.equals=" + UPDATED_FIM);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByFimIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where fim not equals to DEFAULT_FIM
        defaultAvisoMeteorologicoShouldNotBeFound("fim.notEquals=" + DEFAULT_FIM);

        // Get all the avisoMeteorologicoList where fim not equals to UPDATED_FIM
        defaultAvisoMeteorologicoShouldBeFound("fim.notEquals=" + UPDATED_FIM);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByFimIsInShouldWork() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where fim in DEFAULT_FIM or UPDATED_FIM
        defaultAvisoMeteorologicoShouldBeFound("fim.in=" + DEFAULT_FIM + "," + UPDATED_FIM);

        // Get all the avisoMeteorologicoList where fim equals to UPDATED_FIM
        defaultAvisoMeteorologicoShouldNotBeFound("fim.in=" + UPDATED_FIM);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByFimIsNullOrNotNull() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where fim is not null
        defaultAvisoMeteorologicoShouldBeFound("fim.specified=true");

        // Get all the avisoMeteorologicoList where fim is null
        defaultAvisoMeteorologicoShouldNotBeFound("fim.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByTextoIsEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where texto equals to DEFAULT_TEXTO
        defaultAvisoMeteorologicoShouldBeFound("texto.equals=" + DEFAULT_TEXTO);

        // Get all the avisoMeteorologicoList where texto equals to UPDATED_TEXTO
        defaultAvisoMeteorologicoShouldNotBeFound("texto.equals=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByTextoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where texto not equals to DEFAULT_TEXTO
        defaultAvisoMeteorologicoShouldNotBeFound("texto.notEquals=" + DEFAULT_TEXTO);

        // Get all the avisoMeteorologicoList where texto not equals to UPDATED_TEXTO
        defaultAvisoMeteorologicoShouldBeFound("texto.notEquals=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByTextoIsInShouldWork() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where texto in DEFAULT_TEXTO or UPDATED_TEXTO
        defaultAvisoMeteorologicoShouldBeFound("texto.in=" + DEFAULT_TEXTO + "," + UPDATED_TEXTO);

        // Get all the avisoMeteorologicoList where texto equals to UPDATED_TEXTO
        defaultAvisoMeteorologicoShouldNotBeFound("texto.in=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByTextoIsNullOrNotNull() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where texto is not null
        defaultAvisoMeteorologicoShouldBeFound("texto.specified=true");

        // Get all the avisoMeteorologicoList where texto is null
        defaultAvisoMeteorologicoShouldNotBeFound("texto.specified=false");
    }
                @Test
    @Transactional
    public void getAllAvisoMeteorologicosByTextoContainsSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where texto contains DEFAULT_TEXTO
        defaultAvisoMeteorologicoShouldBeFound("texto.contains=" + DEFAULT_TEXTO);

        // Get all the avisoMeteorologicoList where texto contains UPDATED_TEXTO
        defaultAvisoMeteorologicoShouldNotBeFound("texto.contains=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByTextoNotContainsSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where texto does not contain DEFAULT_TEXTO
        defaultAvisoMeteorologicoShouldNotBeFound("texto.doesNotContain=" + DEFAULT_TEXTO);

        // Get all the avisoMeteorologicoList where texto does not contain UPDATED_TEXTO
        defaultAvisoMeteorologicoShouldBeFound("texto.doesNotContain=" + UPDATED_TEXTO);
    }


    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where imagem equals to DEFAULT_IMAGEM
        defaultAvisoMeteorologicoShouldBeFound("imagem.equals=" + DEFAULT_IMAGEM);

        // Get all the avisoMeteorologicoList where imagem equals to UPDATED_IMAGEM
        defaultAvisoMeteorologicoShouldNotBeFound("imagem.equals=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByImagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where imagem not equals to DEFAULT_IMAGEM
        defaultAvisoMeteorologicoShouldNotBeFound("imagem.notEquals=" + DEFAULT_IMAGEM);

        // Get all the avisoMeteorologicoList where imagem not equals to UPDATED_IMAGEM
        defaultAvisoMeteorologicoShouldBeFound("imagem.notEquals=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByImagemIsInShouldWork() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where imagem in DEFAULT_IMAGEM or UPDATED_IMAGEM
        defaultAvisoMeteorologicoShouldBeFound("imagem.in=" + DEFAULT_IMAGEM + "," + UPDATED_IMAGEM);

        // Get all the avisoMeteorologicoList where imagem equals to UPDATED_IMAGEM
        defaultAvisoMeteorologicoShouldNotBeFound("imagem.in=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where imagem is not null
        defaultAvisoMeteorologicoShouldBeFound("imagem.specified=true");

        // Get all the avisoMeteorologicoList where imagem is null
        defaultAvisoMeteorologicoShouldNotBeFound("imagem.specified=false");
    }
                @Test
    @Transactional
    public void getAllAvisoMeteorologicosByImagemContainsSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where imagem contains DEFAULT_IMAGEM
        defaultAvisoMeteorologicoShouldBeFound("imagem.contains=" + DEFAULT_IMAGEM);

        // Get all the avisoMeteorologicoList where imagem contains UPDATED_IMAGEM
        defaultAvisoMeteorologicoShouldNotBeFound("imagem.contains=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByImagemNotContainsSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where imagem does not contain DEFAULT_IMAGEM
        defaultAvisoMeteorologicoShouldNotBeFound("imagem.doesNotContain=" + DEFAULT_IMAGEM);

        // Get all the avisoMeteorologicoList where imagem does not contain UPDATED_IMAGEM
        defaultAvisoMeteorologicoShouldBeFound("imagem.doesNotContain=" + UPDATED_IMAGEM);
    }


    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByImagemAssinaturaIsEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where imagemAssinatura equals to DEFAULT_IMAGEM_ASSINATURA
        defaultAvisoMeteorologicoShouldBeFound("imagemAssinatura.equals=" + DEFAULT_IMAGEM_ASSINATURA);

        // Get all the avisoMeteorologicoList where imagemAssinatura equals to UPDATED_IMAGEM_ASSINATURA
        defaultAvisoMeteorologicoShouldNotBeFound("imagemAssinatura.equals=" + UPDATED_IMAGEM_ASSINATURA);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByImagemAssinaturaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where imagemAssinatura not equals to DEFAULT_IMAGEM_ASSINATURA
        defaultAvisoMeteorologicoShouldNotBeFound("imagemAssinatura.notEquals=" + DEFAULT_IMAGEM_ASSINATURA);

        // Get all the avisoMeteorologicoList where imagemAssinatura not equals to UPDATED_IMAGEM_ASSINATURA
        defaultAvisoMeteorologicoShouldBeFound("imagemAssinatura.notEquals=" + UPDATED_IMAGEM_ASSINATURA);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByImagemAssinaturaIsInShouldWork() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where imagemAssinatura in DEFAULT_IMAGEM_ASSINATURA or UPDATED_IMAGEM_ASSINATURA
        defaultAvisoMeteorologicoShouldBeFound("imagemAssinatura.in=" + DEFAULT_IMAGEM_ASSINATURA + "," + UPDATED_IMAGEM_ASSINATURA);

        // Get all the avisoMeteorologicoList where imagemAssinatura equals to UPDATED_IMAGEM_ASSINATURA
        defaultAvisoMeteorologicoShouldNotBeFound("imagemAssinatura.in=" + UPDATED_IMAGEM_ASSINATURA);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByImagemAssinaturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where imagemAssinatura is not null
        defaultAvisoMeteorologicoShouldBeFound("imagemAssinatura.specified=true");

        // Get all the avisoMeteorologicoList where imagemAssinatura is null
        defaultAvisoMeteorologicoShouldNotBeFound("imagemAssinatura.specified=false");
    }
                @Test
    @Transactional
    public void getAllAvisoMeteorologicosByImagemAssinaturaContainsSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where imagemAssinatura contains DEFAULT_IMAGEM_ASSINATURA
        defaultAvisoMeteorologicoShouldBeFound("imagemAssinatura.contains=" + DEFAULT_IMAGEM_ASSINATURA);

        // Get all the avisoMeteorologicoList where imagemAssinatura contains UPDATED_IMAGEM_ASSINATURA
        defaultAvisoMeteorologicoShouldNotBeFound("imagemAssinatura.contains=" + UPDATED_IMAGEM_ASSINATURA);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByImagemAssinaturaNotContainsSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where imagemAssinatura does not contain DEFAULT_IMAGEM_ASSINATURA
        defaultAvisoMeteorologicoShouldNotBeFound("imagemAssinatura.doesNotContain=" + DEFAULT_IMAGEM_ASSINATURA);

        // Get all the avisoMeteorologicoList where imagemAssinatura does not contain UPDATED_IMAGEM_ASSINATURA
        defaultAvisoMeteorologicoShouldBeFound("imagemAssinatura.doesNotContain=" + UPDATED_IMAGEM_ASSINATURA);
    }


    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where created equals to DEFAULT_CREATED
        defaultAvisoMeteorologicoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the avisoMeteorologicoList where created equals to UPDATED_CREATED
        defaultAvisoMeteorologicoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where created not equals to DEFAULT_CREATED
        defaultAvisoMeteorologicoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the avisoMeteorologicoList where created not equals to UPDATED_CREATED
        defaultAvisoMeteorologicoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultAvisoMeteorologicoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the avisoMeteorologicoList where created equals to UPDATED_CREATED
        defaultAvisoMeteorologicoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where created is not null
        defaultAvisoMeteorologicoShouldBeFound("created.specified=true");

        // Get all the avisoMeteorologicoList where created is null
        defaultAvisoMeteorologicoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where updated equals to DEFAULT_UPDATED
        defaultAvisoMeteorologicoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the avisoMeteorologicoList where updated equals to UPDATED_UPDATED
        defaultAvisoMeteorologicoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where updated not equals to DEFAULT_UPDATED
        defaultAvisoMeteorologicoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the avisoMeteorologicoList where updated not equals to UPDATED_UPDATED
        defaultAvisoMeteorologicoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultAvisoMeteorologicoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the avisoMeteorologicoList where updated equals to UPDATED_UPDATED
        defaultAvisoMeteorologicoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        // Get all the avisoMeteorologicoList where updated is not null
        defaultAvisoMeteorologicoShouldBeFound("updated.specified=true");

        // Get all the avisoMeteorologicoList where updated is null
        defaultAvisoMeteorologicoShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvisoMeteorologicosByPlanoRecursoIsEqualToSomething() throws Exception {
        // Get already existing entity
        PlanoRecurso planoRecurso = avisoMeteorologico.getPlanoRecurso();
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);
        Long planoRecursoId = planoRecurso.getId();

        // Get all the avisoMeteorologicoList where planoRecurso equals to planoRecursoId
        defaultAvisoMeteorologicoShouldBeFound("planoRecursoId.equals=" + planoRecursoId);

        // Get all the avisoMeteorologicoList where planoRecurso equals to planoRecursoId + 1
        defaultAvisoMeteorologicoShouldNotBeFound("planoRecursoId.equals=" + (planoRecursoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAvisoMeteorologicoShouldBeFound(String filter) throws Exception {
        restAvisoMeteorologicoMockMvc.perform(get("/api/aviso-meteorologicos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avisoMeteorologico.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO)))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fim").value(hasItem(DEFAULT_FIM.toString())))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.[*].imagemAssinatura").value(hasItem(DEFAULT_IMAGEM_ASSINATURA)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restAvisoMeteorologicoMockMvc.perform(get("/api/aviso-meteorologicos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAvisoMeteorologicoShouldNotBeFound(String filter) throws Exception {
        restAvisoMeteorologicoMockMvc.perform(get("/api/aviso-meteorologicos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAvisoMeteorologicoMockMvc.perform(get("/api/aviso-meteorologicos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAvisoMeteorologico() throws Exception {
        // Get the avisoMeteorologico
        restAvisoMeteorologicoMockMvc.perform(get("/api/aviso-meteorologicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvisoMeteorologico() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        int databaseSizeBeforeUpdate = avisoMeteorologicoRepository.findAll().size();

        // Update the avisoMeteorologico
        AvisoMeteorologico updatedAvisoMeteorologico = avisoMeteorologicoRepository.findById(avisoMeteorologico.getId()).get();
        // Disconnect from session so that the updates on updatedAvisoMeteorologico are not directly saved in db
        em.detach(updatedAvisoMeteorologico);
        updatedAvisoMeteorologico
            .nome(UPDATED_NOME)
            .assunto(UPDATED_ASSUNTO)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .texto(UPDATED_TEXTO)
            .imagem(UPDATED_IMAGEM)
            .imagemAssinatura(UPDATED_IMAGEM_ASSINATURA)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        AvisoMeteorologicoDTO avisoMeteorologicoDTO = avisoMeteorologicoMapper.toDto(updatedAvisoMeteorologico);

        restAvisoMeteorologicoMockMvc.perform(put("/api/aviso-meteorologicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoMeteorologicoDTO)))
            .andExpect(status().isOk());

        // Validate the AvisoMeteorologico in the database
        List<AvisoMeteorologico> avisoMeteorologicoList = avisoMeteorologicoRepository.findAll();
        assertThat(avisoMeteorologicoList).hasSize(databaseSizeBeforeUpdate);
        AvisoMeteorologico testAvisoMeteorologico = avisoMeteorologicoList.get(avisoMeteorologicoList.size() - 1);
        assertThat(testAvisoMeteorologico.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAvisoMeteorologico.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testAvisoMeteorologico.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testAvisoMeteorologico.getFim()).isEqualTo(UPDATED_FIM);
        assertThat(testAvisoMeteorologico.getTexto()).isEqualTo(UPDATED_TEXTO);
        assertThat(testAvisoMeteorologico.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testAvisoMeteorologico.getImagemAssinatura()).isEqualTo(UPDATED_IMAGEM_ASSINATURA);
        assertThat(testAvisoMeteorologico.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAvisoMeteorologico.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAvisoMeteorologico() throws Exception {
        int databaseSizeBeforeUpdate = avisoMeteorologicoRepository.findAll().size();

        // Create the AvisoMeteorologico
        AvisoMeteorologicoDTO avisoMeteorologicoDTO = avisoMeteorologicoMapper.toDto(avisoMeteorologico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvisoMeteorologicoMockMvc.perform(put("/api/aviso-meteorologicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoMeteorologicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AvisoMeteorologico in the database
        List<AvisoMeteorologico> avisoMeteorologicoList = avisoMeteorologicoRepository.findAll();
        assertThat(avisoMeteorologicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvisoMeteorologico() throws Exception {
        // Initialize the database
        avisoMeteorologicoRepository.saveAndFlush(avisoMeteorologico);

        int databaseSizeBeforeDelete = avisoMeteorologicoRepository.findAll().size();

        // Delete the avisoMeteorologico
        restAvisoMeteorologicoMockMvc.perform(delete("/api/aviso-meteorologicos/{id}", avisoMeteorologico.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AvisoMeteorologico> avisoMeteorologicoList = avisoMeteorologicoRepository.findAll();
        assertThat(avisoMeteorologicoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
