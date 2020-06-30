package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Boletim;
import org.tempestade.nucleo.domain.PlanoRecurso;
import org.tempestade.nucleo.repository.BoletimRepository;
import org.tempestade.nucleo.service.BoletimService;
import org.tempestade.nucleo.service.dto.BoletimDTO;
import org.tempestade.nucleo.service.mapper.BoletimMapper;
import org.tempestade.nucleo.service.dto.BoletimCriteria;
import org.tempestade.nucleo.service.BoletimQueryService;

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
 * Integration tests for the {@link BoletimResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BoletimResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_SMS = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_SMS = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_ASSUNTO = "AAAAAAAAAA";
    private static final String UPDATED_ASSUNTO = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_PARTE_2 = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_PARTE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_PARTE_3 = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_PARTE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_ASSUNTO = "AAAAAAAAAA";
    private static final String UPDATED_SUB_ASSUNTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NAO_EXIBIR_PAG_EMPRESA = 1;
    private static final Integer UPDATED_NAO_EXIBIR_PAG_EMPRESA = 2;
    private static final Integer SMALLER_NAO_EXIBIR_PAG_EMPRESA = 1 - 1;

    private static final Boolean DEFAULT_CRITICO = false;
    private static final Boolean UPDATED_CRITICO = true;

    private static final Boolean DEFAULT_APROVADO = false;
    private static final Boolean UPDATED_APROVADO = true;

    private static final Boolean DEFAULT_ENVIAR_SMS = false;
    private static final Boolean UPDATED_ENVIAR_SMS = true;

    private static final Boolean DEFAULT_ENVIAR_EMAIL = false;
    private static final Boolean UPDATED_ENVIAR_EMAIL = true;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BoletimRepository boletimRepository;

    @Autowired
    private BoletimMapper boletimMapper;

    @Autowired
    private BoletimService boletimService;

    @Autowired
    private BoletimQueryService boletimQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoletimMockMvc;

    private Boletim boletim;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Boletim createEntity(EntityManager em) {
        Boletim boletim = new Boletim()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .texto(DEFAULT_TEXTO)
            .textoSms(DEFAULT_TEXTO_SMS)
            .imagem(DEFAULT_IMAGEM)
            .assunto(DEFAULT_ASSUNTO)
            .textoParte2(DEFAULT_TEXTO_PARTE_2)
            .textoParte3(DEFAULT_TEXTO_PARTE_3)
            .subAssunto(DEFAULT_SUB_ASSUNTO)
            .naoExibirPagEmpresa(DEFAULT_NAO_EXIBIR_PAG_EMPRESA)
            .critico(DEFAULT_CRITICO)
            .aprovado(DEFAULT_APROVADO)
            .enviarSms(DEFAULT_ENVIAR_SMS)
            .enviarEmail(DEFAULT_ENVIAR_EMAIL)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return boletim;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Boletim createUpdatedEntity(EntityManager em) {
        Boletim boletim = new Boletim()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .texto(UPDATED_TEXTO)
            .textoSms(UPDATED_TEXTO_SMS)
            .imagem(UPDATED_IMAGEM)
            .assunto(UPDATED_ASSUNTO)
            .textoParte2(UPDATED_TEXTO_PARTE_2)
            .textoParte3(UPDATED_TEXTO_PARTE_3)
            .subAssunto(UPDATED_SUB_ASSUNTO)
            .naoExibirPagEmpresa(UPDATED_NAO_EXIBIR_PAG_EMPRESA)
            .critico(UPDATED_CRITICO)
            .aprovado(UPDATED_APROVADO)
            .enviarSms(UPDATED_ENVIAR_SMS)
            .enviarEmail(UPDATED_ENVIAR_EMAIL)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return boletim;
    }

    @BeforeEach
    public void initTest() {
        boletim = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoletim() throws Exception {
        int databaseSizeBeforeCreate = boletimRepository.findAll().size();
        // Create the Boletim
        BoletimDTO boletimDTO = boletimMapper.toDto(boletim);
        restBoletimMockMvc.perform(post("/api/boletims")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimDTO)))
            .andExpect(status().isCreated());

        // Validate the Boletim in the database
        List<Boletim> boletimList = boletimRepository.findAll();
        assertThat(boletimList).hasSize(databaseSizeBeforeCreate + 1);
        Boletim testBoletim = boletimList.get(boletimList.size() - 1);
        assertThat(testBoletim.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testBoletim.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testBoletim.getTexto()).isEqualTo(DEFAULT_TEXTO);
        assertThat(testBoletim.getTextoSms()).isEqualTo(DEFAULT_TEXTO_SMS);
        assertThat(testBoletim.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testBoletim.getAssunto()).isEqualTo(DEFAULT_ASSUNTO);
        assertThat(testBoletim.getTextoParte2()).isEqualTo(DEFAULT_TEXTO_PARTE_2);
        assertThat(testBoletim.getTextoParte3()).isEqualTo(DEFAULT_TEXTO_PARTE_3);
        assertThat(testBoletim.getSubAssunto()).isEqualTo(DEFAULT_SUB_ASSUNTO);
        assertThat(testBoletim.getNaoExibirPagEmpresa()).isEqualTo(DEFAULT_NAO_EXIBIR_PAG_EMPRESA);
        assertThat(testBoletim.isCritico()).isEqualTo(DEFAULT_CRITICO);
        assertThat(testBoletim.isAprovado()).isEqualTo(DEFAULT_APROVADO);
        assertThat(testBoletim.isEnviarSms()).isEqualTo(DEFAULT_ENVIAR_SMS);
        assertThat(testBoletim.isEnviarEmail()).isEqualTo(DEFAULT_ENVIAR_EMAIL);
        assertThat(testBoletim.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testBoletim.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createBoletimWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boletimRepository.findAll().size();

        // Create the Boletim with an existing ID
        boletim.setId(1L);
        BoletimDTO boletimDTO = boletimMapper.toDto(boletim);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoletimMockMvc.perform(post("/api/boletims")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Boletim in the database
        List<Boletim> boletimList = boletimRepository.findAll();
        assertThat(boletimList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimRepository.findAll().size();
        // set the field null
        boletim.setNome(null);

        // Create the Boletim, which fails.
        BoletimDTO boletimDTO = boletimMapper.toDto(boletim);


        restBoletimMockMvc.perform(post("/api/boletims")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimDTO)))
            .andExpect(status().isBadRequest());

        List<Boletim> boletimList = boletimRepository.findAll();
        assertThat(boletimList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimRepository.findAll().size();
        // set the field null
        boletim.setCreated(null);

        // Create the Boletim, which fails.
        BoletimDTO boletimDTO = boletimMapper.toDto(boletim);


        restBoletimMockMvc.perform(post("/api/boletims")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimDTO)))
            .andExpect(status().isBadRequest());

        List<Boletim> boletimList = boletimRepository.findAll();
        assertThat(boletimList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBoletims() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList
        restBoletimMockMvc.perform(get("/api/boletims?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boletim.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO)))
            .andExpect(jsonPath("$.[*].textoSms").value(hasItem(DEFAULT_TEXTO_SMS)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO)))
            .andExpect(jsonPath("$.[*].textoParte2").value(hasItem(DEFAULT_TEXTO_PARTE_2)))
            .andExpect(jsonPath("$.[*].textoParte3").value(hasItem(DEFAULT_TEXTO_PARTE_3)))
            .andExpect(jsonPath("$.[*].subAssunto").value(hasItem(DEFAULT_SUB_ASSUNTO)))
            .andExpect(jsonPath("$.[*].naoExibirPagEmpresa").value(hasItem(DEFAULT_NAO_EXIBIR_PAG_EMPRESA)))
            .andExpect(jsonPath("$.[*].critico").value(hasItem(DEFAULT_CRITICO.booleanValue())))
            .andExpect(jsonPath("$.[*].aprovado").value(hasItem(DEFAULT_APROVADO.booleanValue())))
            .andExpect(jsonPath("$.[*].enviarSms").value(hasItem(DEFAULT_ENVIAR_SMS.booleanValue())))
            .andExpect(jsonPath("$.[*].enviarEmail").value(hasItem(DEFAULT_ENVIAR_EMAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getBoletim() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get the boletim
        restBoletimMockMvc.perform(get("/api/boletims/{id}", boletim.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(boletim.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.texto").value(DEFAULT_TEXTO))
            .andExpect(jsonPath("$.textoSms").value(DEFAULT_TEXTO_SMS))
            .andExpect(jsonPath("$.imagem").value(DEFAULT_IMAGEM))
            .andExpect(jsonPath("$.assunto").value(DEFAULT_ASSUNTO))
            .andExpect(jsonPath("$.textoParte2").value(DEFAULT_TEXTO_PARTE_2))
            .andExpect(jsonPath("$.textoParte3").value(DEFAULT_TEXTO_PARTE_3))
            .andExpect(jsonPath("$.subAssunto").value(DEFAULT_SUB_ASSUNTO))
            .andExpect(jsonPath("$.naoExibirPagEmpresa").value(DEFAULT_NAO_EXIBIR_PAG_EMPRESA))
            .andExpect(jsonPath("$.critico").value(DEFAULT_CRITICO.booleanValue()))
            .andExpect(jsonPath("$.aprovado").value(DEFAULT_APROVADO.booleanValue()))
            .andExpect(jsonPath("$.enviarSms").value(DEFAULT_ENVIAR_SMS.booleanValue()))
            .andExpect(jsonPath("$.enviarEmail").value(DEFAULT_ENVIAR_EMAIL.booleanValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getBoletimsByIdFiltering() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        Long id = boletim.getId();

        defaultBoletimShouldBeFound("id.equals=" + id);
        defaultBoletimShouldNotBeFound("id.notEquals=" + id);

        defaultBoletimShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBoletimShouldNotBeFound("id.greaterThan=" + id);

        defaultBoletimShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBoletimShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBoletimsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where nome equals to DEFAULT_NOME
        defaultBoletimShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the boletimList where nome equals to UPDATED_NOME
        defaultBoletimShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllBoletimsByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where nome not equals to DEFAULT_NOME
        defaultBoletimShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the boletimList where nome not equals to UPDATED_NOME
        defaultBoletimShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllBoletimsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultBoletimShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the boletimList where nome equals to UPDATED_NOME
        defaultBoletimShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllBoletimsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where nome is not null
        defaultBoletimShouldBeFound("nome.specified=true");

        // Get all the boletimList where nome is null
        defaultBoletimShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimsByNomeContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where nome contains DEFAULT_NOME
        defaultBoletimShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the boletimList where nome contains UPDATED_NOME
        defaultBoletimShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllBoletimsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where nome does not contain DEFAULT_NOME
        defaultBoletimShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the boletimList where nome does not contain UPDATED_NOME
        defaultBoletimShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllBoletimsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where descricao equals to DEFAULT_DESCRICAO
        defaultBoletimShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the boletimList where descricao equals to UPDATED_DESCRICAO
        defaultBoletimShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where descricao not equals to DEFAULT_DESCRICAO
        defaultBoletimShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the boletimList where descricao not equals to UPDATED_DESCRICAO
        defaultBoletimShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultBoletimShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the boletimList where descricao equals to UPDATED_DESCRICAO
        defaultBoletimShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where descricao is not null
        defaultBoletimShouldBeFound("descricao.specified=true");

        // Get all the boletimList where descricao is null
        defaultBoletimShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where descricao contains DEFAULT_DESCRICAO
        defaultBoletimShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the boletimList where descricao contains UPDATED_DESCRICAO
        defaultBoletimShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where descricao does not contain DEFAULT_DESCRICAO
        defaultBoletimShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the boletimList where descricao does not contain UPDATED_DESCRICAO
        defaultBoletimShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllBoletimsByTextoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where texto equals to DEFAULT_TEXTO
        defaultBoletimShouldBeFound("texto.equals=" + DEFAULT_TEXTO);

        // Get all the boletimList where texto equals to UPDATED_TEXTO
        defaultBoletimShouldNotBeFound("texto.equals=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByTextoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where texto not equals to DEFAULT_TEXTO
        defaultBoletimShouldNotBeFound("texto.notEquals=" + DEFAULT_TEXTO);

        // Get all the boletimList where texto not equals to UPDATED_TEXTO
        defaultBoletimShouldBeFound("texto.notEquals=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByTextoIsInShouldWork() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where texto in DEFAULT_TEXTO or UPDATED_TEXTO
        defaultBoletimShouldBeFound("texto.in=" + DEFAULT_TEXTO + "," + UPDATED_TEXTO);

        // Get all the boletimList where texto equals to UPDATED_TEXTO
        defaultBoletimShouldNotBeFound("texto.in=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByTextoIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where texto is not null
        defaultBoletimShouldBeFound("texto.specified=true");

        // Get all the boletimList where texto is null
        defaultBoletimShouldNotBeFound("texto.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimsByTextoContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where texto contains DEFAULT_TEXTO
        defaultBoletimShouldBeFound("texto.contains=" + DEFAULT_TEXTO);

        // Get all the boletimList where texto contains UPDATED_TEXTO
        defaultBoletimShouldNotBeFound("texto.contains=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByTextoNotContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where texto does not contain DEFAULT_TEXTO
        defaultBoletimShouldNotBeFound("texto.doesNotContain=" + DEFAULT_TEXTO);

        // Get all the boletimList where texto does not contain UPDATED_TEXTO
        defaultBoletimShouldBeFound("texto.doesNotContain=" + UPDATED_TEXTO);
    }


    @Test
    @Transactional
    public void getAllBoletimsByTextoSmsIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoSms equals to DEFAULT_TEXTO_SMS
        defaultBoletimShouldBeFound("textoSms.equals=" + DEFAULT_TEXTO_SMS);

        // Get all the boletimList where textoSms equals to UPDATED_TEXTO_SMS
        defaultBoletimShouldNotBeFound("textoSms.equals=" + UPDATED_TEXTO_SMS);
    }

    @Test
    @Transactional
    public void getAllBoletimsByTextoSmsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoSms not equals to DEFAULT_TEXTO_SMS
        defaultBoletimShouldNotBeFound("textoSms.notEquals=" + DEFAULT_TEXTO_SMS);

        // Get all the boletimList where textoSms not equals to UPDATED_TEXTO_SMS
        defaultBoletimShouldBeFound("textoSms.notEquals=" + UPDATED_TEXTO_SMS);
    }

    @Test
    @Transactional
    public void getAllBoletimsByTextoSmsIsInShouldWork() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoSms in DEFAULT_TEXTO_SMS or UPDATED_TEXTO_SMS
        defaultBoletimShouldBeFound("textoSms.in=" + DEFAULT_TEXTO_SMS + "," + UPDATED_TEXTO_SMS);

        // Get all the boletimList where textoSms equals to UPDATED_TEXTO_SMS
        defaultBoletimShouldNotBeFound("textoSms.in=" + UPDATED_TEXTO_SMS);
    }

    @Test
    @Transactional
    public void getAllBoletimsByTextoSmsIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoSms is not null
        defaultBoletimShouldBeFound("textoSms.specified=true");

        // Get all the boletimList where textoSms is null
        defaultBoletimShouldNotBeFound("textoSms.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimsByTextoSmsContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoSms contains DEFAULT_TEXTO_SMS
        defaultBoletimShouldBeFound("textoSms.contains=" + DEFAULT_TEXTO_SMS);

        // Get all the boletimList where textoSms contains UPDATED_TEXTO_SMS
        defaultBoletimShouldNotBeFound("textoSms.contains=" + UPDATED_TEXTO_SMS);
    }

    @Test
    @Transactional
    public void getAllBoletimsByTextoSmsNotContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoSms does not contain DEFAULT_TEXTO_SMS
        defaultBoletimShouldNotBeFound("textoSms.doesNotContain=" + DEFAULT_TEXTO_SMS);

        // Get all the boletimList where textoSms does not contain UPDATED_TEXTO_SMS
        defaultBoletimShouldBeFound("textoSms.doesNotContain=" + UPDATED_TEXTO_SMS);
    }


    @Test
    @Transactional
    public void getAllBoletimsByImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where imagem equals to DEFAULT_IMAGEM
        defaultBoletimShouldBeFound("imagem.equals=" + DEFAULT_IMAGEM);

        // Get all the boletimList where imagem equals to UPDATED_IMAGEM
        defaultBoletimShouldNotBeFound("imagem.equals=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllBoletimsByImagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where imagem not equals to DEFAULT_IMAGEM
        defaultBoletimShouldNotBeFound("imagem.notEquals=" + DEFAULT_IMAGEM);

        // Get all the boletimList where imagem not equals to UPDATED_IMAGEM
        defaultBoletimShouldBeFound("imagem.notEquals=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllBoletimsByImagemIsInShouldWork() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where imagem in DEFAULT_IMAGEM or UPDATED_IMAGEM
        defaultBoletimShouldBeFound("imagem.in=" + DEFAULT_IMAGEM + "," + UPDATED_IMAGEM);

        // Get all the boletimList where imagem equals to UPDATED_IMAGEM
        defaultBoletimShouldNotBeFound("imagem.in=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllBoletimsByImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where imagem is not null
        defaultBoletimShouldBeFound("imagem.specified=true");

        // Get all the boletimList where imagem is null
        defaultBoletimShouldNotBeFound("imagem.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimsByImagemContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where imagem contains DEFAULT_IMAGEM
        defaultBoletimShouldBeFound("imagem.contains=" + DEFAULT_IMAGEM);

        // Get all the boletimList where imagem contains UPDATED_IMAGEM
        defaultBoletimShouldNotBeFound("imagem.contains=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllBoletimsByImagemNotContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where imagem does not contain DEFAULT_IMAGEM
        defaultBoletimShouldNotBeFound("imagem.doesNotContain=" + DEFAULT_IMAGEM);

        // Get all the boletimList where imagem does not contain UPDATED_IMAGEM
        defaultBoletimShouldBeFound("imagem.doesNotContain=" + UPDATED_IMAGEM);
    }


    @Test
    @Transactional
    public void getAllBoletimsByAssuntoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where assunto equals to DEFAULT_ASSUNTO
        defaultBoletimShouldBeFound("assunto.equals=" + DEFAULT_ASSUNTO);

        // Get all the boletimList where assunto equals to UPDATED_ASSUNTO
        defaultBoletimShouldNotBeFound("assunto.equals=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByAssuntoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where assunto not equals to DEFAULT_ASSUNTO
        defaultBoletimShouldNotBeFound("assunto.notEquals=" + DEFAULT_ASSUNTO);

        // Get all the boletimList where assunto not equals to UPDATED_ASSUNTO
        defaultBoletimShouldBeFound("assunto.notEquals=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByAssuntoIsInShouldWork() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where assunto in DEFAULT_ASSUNTO or UPDATED_ASSUNTO
        defaultBoletimShouldBeFound("assunto.in=" + DEFAULT_ASSUNTO + "," + UPDATED_ASSUNTO);

        // Get all the boletimList where assunto equals to UPDATED_ASSUNTO
        defaultBoletimShouldNotBeFound("assunto.in=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByAssuntoIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where assunto is not null
        defaultBoletimShouldBeFound("assunto.specified=true");

        // Get all the boletimList where assunto is null
        defaultBoletimShouldNotBeFound("assunto.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimsByAssuntoContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where assunto contains DEFAULT_ASSUNTO
        defaultBoletimShouldBeFound("assunto.contains=" + DEFAULT_ASSUNTO);

        // Get all the boletimList where assunto contains UPDATED_ASSUNTO
        defaultBoletimShouldNotBeFound("assunto.contains=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByAssuntoNotContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where assunto does not contain DEFAULT_ASSUNTO
        defaultBoletimShouldNotBeFound("assunto.doesNotContain=" + DEFAULT_ASSUNTO);

        // Get all the boletimList where assunto does not contain UPDATED_ASSUNTO
        defaultBoletimShouldBeFound("assunto.doesNotContain=" + UPDATED_ASSUNTO);
    }


    @Test
    @Transactional
    public void getAllBoletimsByTextoParte2IsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoParte2 equals to DEFAULT_TEXTO_PARTE_2
        defaultBoletimShouldBeFound("textoParte2.equals=" + DEFAULT_TEXTO_PARTE_2);

        // Get all the boletimList where textoParte2 equals to UPDATED_TEXTO_PARTE_2
        defaultBoletimShouldNotBeFound("textoParte2.equals=" + UPDATED_TEXTO_PARTE_2);
    }

    @Test
    @Transactional
    public void getAllBoletimsByTextoParte2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoParte2 not equals to DEFAULT_TEXTO_PARTE_2
        defaultBoletimShouldNotBeFound("textoParte2.notEquals=" + DEFAULT_TEXTO_PARTE_2);

        // Get all the boletimList where textoParte2 not equals to UPDATED_TEXTO_PARTE_2
        defaultBoletimShouldBeFound("textoParte2.notEquals=" + UPDATED_TEXTO_PARTE_2);
    }

    @Test
    @Transactional
    public void getAllBoletimsByTextoParte2IsInShouldWork() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoParte2 in DEFAULT_TEXTO_PARTE_2 or UPDATED_TEXTO_PARTE_2
        defaultBoletimShouldBeFound("textoParte2.in=" + DEFAULT_TEXTO_PARTE_2 + "," + UPDATED_TEXTO_PARTE_2);

        // Get all the boletimList where textoParte2 equals to UPDATED_TEXTO_PARTE_2
        defaultBoletimShouldNotBeFound("textoParte2.in=" + UPDATED_TEXTO_PARTE_2);
    }

    @Test
    @Transactional
    public void getAllBoletimsByTextoParte2IsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoParte2 is not null
        defaultBoletimShouldBeFound("textoParte2.specified=true");

        // Get all the boletimList where textoParte2 is null
        defaultBoletimShouldNotBeFound("textoParte2.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimsByTextoParte2ContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoParte2 contains DEFAULT_TEXTO_PARTE_2
        defaultBoletimShouldBeFound("textoParte2.contains=" + DEFAULT_TEXTO_PARTE_2);

        // Get all the boletimList where textoParte2 contains UPDATED_TEXTO_PARTE_2
        defaultBoletimShouldNotBeFound("textoParte2.contains=" + UPDATED_TEXTO_PARTE_2);
    }

    @Test
    @Transactional
    public void getAllBoletimsByTextoParte2NotContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoParte2 does not contain DEFAULT_TEXTO_PARTE_2
        defaultBoletimShouldNotBeFound("textoParte2.doesNotContain=" + DEFAULT_TEXTO_PARTE_2);

        // Get all the boletimList where textoParte2 does not contain UPDATED_TEXTO_PARTE_2
        defaultBoletimShouldBeFound("textoParte2.doesNotContain=" + UPDATED_TEXTO_PARTE_2);
    }


    @Test
    @Transactional
    public void getAllBoletimsByTextoParte3IsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoParte3 equals to DEFAULT_TEXTO_PARTE_3
        defaultBoletimShouldBeFound("textoParte3.equals=" + DEFAULT_TEXTO_PARTE_3);

        // Get all the boletimList where textoParte3 equals to UPDATED_TEXTO_PARTE_3
        defaultBoletimShouldNotBeFound("textoParte3.equals=" + UPDATED_TEXTO_PARTE_3);
    }

    @Test
    @Transactional
    public void getAllBoletimsByTextoParte3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoParte3 not equals to DEFAULT_TEXTO_PARTE_3
        defaultBoletimShouldNotBeFound("textoParte3.notEquals=" + DEFAULT_TEXTO_PARTE_3);

        // Get all the boletimList where textoParte3 not equals to UPDATED_TEXTO_PARTE_3
        defaultBoletimShouldBeFound("textoParte3.notEquals=" + UPDATED_TEXTO_PARTE_3);
    }

    @Test
    @Transactional
    public void getAllBoletimsByTextoParte3IsInShouldWork() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoParte3 in DEFAULT_TEXTO_PARTE_3 or UPDATED_TEXTO_PARTE_3
        defaultBoletimShouldBeFound("textoParte3.in=" + DEFAULT_TEXTO_PARTE_3 + "," + UPDATED_TEXTO_PARTE_3);

        // Get all the boletimList where textoParte3 equals to UPDATED_TEXTO_PARTE_3
        defaultBoletimShouldNotBeFound("textoParte3.in=" + UPDATED_TEXTO_PARTE_3);
    }

    @Test
    @Transactional
    public void getAllBoletimsByTextoParte3IsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoParte3 is not null
        defaultBoletimShouldBeFound("textoParte3.specified=true");

        // Get all the boletimList where textoParte3 is null
        defaultBoletimShouldNotBeFound("textoParte3.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimsByTextoParte3ContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoParte3 contains DEFAULT_TEXTO_PARTE_3
        defaultBoletimShouldBeFound("textoParte3.contains=" + DEFAULT_TEXTO_PARTE_3);

        // Get all the boletimList where textoParte3 contains UPDATED_TEXTO_PARTE_3
        defaultBoletimShouldNotBeFound("textoParte3.contains=" + UPDATED_TEXTO_PARTE_3);
    }

    @Test
    @Transactional
    public void getAllBoletimsByTextoParte3NotContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where textoParte3 does not contain DEFAULT_TEXTO_PARTE_3
        defaultBoletimShouldNotBeFound("textoParte3.doesNotContain=" + DEFAULT_TEXTO_PARTE_3);

        // Get all the boletimList where textoParte3 does not contain UPDATED_TEXTO_PARTE_3
        defaultBoletimShouldBeFound("textoParte3.doesNotContain=" + UPDATED_TEXTO_PARTE_3);
    }


    @Test
    @Transactional
    public void getAllBoletimsBySubAssuntoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where subAssunto equals to DEFAULT_SUB_ASSUNTO
        defaultBoletimShouldBeFound("subAssunto.equals=" + DEFAULT_SUB_ASSUNTO);

        // Get all the boletimList where subAssunto equals to UPDATED_SUB_ASSUNTO
        defaultBoletimShouldNotBeFound("subAssunto.equals=" + UPDATED_SUB_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllBoletimsBySubAssuntoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where subAssunto not equals to DEFAULT_SUB_ASSUNTO
        defaultBoletimShouldNotBeFound("subAssunto.notEquals=" + DEFAULT_SUB_ASSUNTO);

        // Get all the boletimList where subAssunto not equals to UPDATED_SUB_ASSUNTO
        defaultBoletimShouldBeFound("subAssunto.notEquals=" + UPDATED_SUB_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllBoletimsBySubAssuntoIsInShouldWork() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where subAssunto in DEFAULT_SUB_ASSUNTO or UPDATED_SUB_ASSUNTO
        defaultBoletimShouldBeFound("subAssunto.in=" + DEFAULT_SUB_ASSUNTO + "," + UPDATED_SUB_ASSUNTO);

        // Get all the boletimList where subAssunto equals to UPDATED_SUB_ASSUNTO
        defaultBoletimShouldNotBeFound("subAssunto.in=" + UPDATED_SUB_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllBoletimsBySubAssuntoIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where subAssunto is not null
        defaultBoletimShouldBeFound("subAssunto.specified=true");

        // Get all the boletimList where subAssunto is null
        defaultBoletimShouldNotBeFound("subAssunto.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimsBySubAssuntoContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where subAssunto contains DEFAULT_SUB_ASSUNTO
        defaultBoletimShouldBeFound("subAssunto.contains=" + DEFAULT_SUB_ASSUNTO);

        // Get all the boletimList where subAssunto contains UPDATED_SUB_ASSUNTO
        defaultBoletimShouldNotBeFound("subAssunto.contains=" + UPDATED_SUB_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllBoletimsBySubAssuntoNotContainsSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where subAssunto does not contain DEFAULT_SUB_ASSUNTO
        defaultBoletimShouldNotBeFound("subAssunto.doesNotContain=" + DEFAULT_SUB_ASSUNTO);

        // Get all the boletimList where subAssunto does not contain UPDATED_SUB_ASSUNTO
        defaultBoletimShouldBeFound("subAssunto.doesNotContain=" + UPDATED_SUB_ASSUNTO);
    }


    @Test
    @Transactional
    public void getAllBoletimsByNaoExibirPagEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where naoExibirPagEmpresa equals to DEFAULT_NAO_EXIBIR_PAG_EMPRESA
        defaultBoletimShouldBeFound("naoExibirPagEmpresa.equals=" + DEFAULT_NAO_EXIBIR_PAG_EMPRESA);

        // Get all the boletimList where naoExibirPagEmpresa equals to UPDATED_NAO_EXIBIR_PAG_EMPRESA
        defaultBoletimShouldNotBeFound("naoExibirPagEmpresa.equals=" + UPDATED_NAO_EXIBIR_PAG_EMPRESA);
    }

    @Test
    @Transactional
    public void getAllBoletimsByNaoExibirPagEmpresaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where naoExibirPagEmpresa not equals to DEFAULT_NAO_EXIBIR_PAG_EMPRESA
        defaultBoletimShouldNotBeFound("naoExibirPagEmpresa.notEquals=" + DEFAULT_NAO_EXIBIR_PAG_EMPRESA);

        // Get all the boletimList where naoExibirPagEmpresa not equals to UPDATED_NAO_EXIBIR_PAG_EMPRESA
        defaultBoletimShouldBeFound("naoExibirPagEmpresa.notEquals=" + UPDATED_NAO_EXIBIR_PAG_EMPRESA);
    }

    @Test
    @Transactional
    public void getAllBoletimsByNaoExibirPagEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where naoExibirPagEmpresa in DEFAULT_NAO_EXIBIR_PAG_EMPRESA or UPDATED_NAO_EXIBIR_PAG_EMPRESA
        defaultBoletimShouldBeFound("naoExibirPagEmpresa.in=" + DEFAULT_NAO_EXIBIR_PAG_EMPRESA + "," + UPDATED_NAO_EXIBIR_PAG_EMPRESA);

        // Get all the boletimList where naoExibirPagEmpresa equals to UPDATED_NAO_EXIBIR_PAG_EMPRESA
        defaultBoletimShouldNotBeFound("naoExibirPagEmpresa.in=" + UPDATED_NAO_EXIBIR_PAG_EMPRESA);
    }

    @Test
    @Transactional
    public void getAllBoletimsByNaoExibirPagEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where naoExibirPagEmpresa is not null
        defaultBoletimShouldBeFound("naoExibirPagEmpresa.specified=true");

        // Get all the boletimList where naoExibirPagEmpresa is null
        defaultBoletimShouldNotBeFound("naoExibirPagEmpresa.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimsByNaoExibirPagEmpresaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where naoExibirPagEmpresa is greater than or equal to DEFAULT_NAO_EXIBIR_PAG_EMPRESA
        defaultBoletimShouldBeFound("naoExibirPagEmpresa.greaterThanOrEqual=" + DEFAULT_NAO_EXIBIR_PAG_EMPRESA);

        // Get all the boletimList where naoExibirPagEmpresa is greater than or equal to UPDATED_NAO_EXIBIR_PAG_EMPRESA
        defaultBoletimShouldNotBeFound("naoExibirPagEmpresa.greaterThanOrEqual=" + UPDATED_NAO_EXIBIR_PAG_EMPRESA);
    }

    @Test
    @Transactional
    public void getAllBoletimsByNaoExibirPagEmpresaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where naoExibirPagEmpresa is less than or equal to DEFAULT_NAO_EXIBIR_PAG_EMPRESA
        defaultBoletimShouldBeFound("naoExibirPagEmpresa.lessThanOrEqual=" + DEFAULT_NAO_EXIBIR_PAG_EMPRESA);

        // Get all the boletimList where naoExibirPagEmpresa is less than or equal to SMALLER_NAO_EXIBIR_PAG_EMPRESA
        defaultBoletimShouldNotBeFound("naoExibirPagEmpresa.lessThanOrEqual=" + SMALLER_NAO_EXIBIR_PAG_EMPRESA);
    }

    @Test
    @Transactional
    public void getAllBoletimsByNaoExibirPagEmpresaIsLessThanSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where naoExibirPagEmpresa is less than DEFAULT_NAO_EXIBIR_PAG_EMPRESA
        defaultBoletimShouldNotBeFound("naoExibirPagEmpresa.lessThan=" + DEFAULT_NAO_EXIBIR_PAG_EMPRESA);

        // Get all the boletimList where naoExibirPagEmpresa is less than UPDATED_NAO_EXIBIR_PAG_EMPRESA
        defaultBoletimShouldBeFound("naoExibirPagEmpresa.lessThan=" + UPDATED_NAO_EXIBIR_PAG_EMPRESA);
    }

    @Test
    @Transactional
    public void getAllBoletimsByNaoExibirPagEmpresaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where naoExibirPagEmpresa is greater than DEFAULT_NAO_EXIBIR_PAG_EMPRESA
        defaultBoletimShouldNotBeFound("naoExibirPagEmpresa.greaterThan=" + DEFAULT_NAO_EXIBIR_PAG_EMPRESA);

        // Get all the boletimList where naoExibirPagEmpresa is greater than SMALLER_NAO_EXIBIR_PAG_EMPRESA
        defaultBoletimShouldBeFound("naoExibirPagEmpresa.greaterThan=" + SMALLER_NAO_EXIBIR_PAG_EMPRESA);
    }


    @Test
    @Transactional
    public void getAllBoletimsByCriticoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where critico equals to DEFAULT_CRITICO
        defaultBoletimShouldBeFound("critico.equals=" + DEFAULT_CRITICO);

        // Get all the boletimList where critico equals to UPDATED_CRITICO
        defaultBoletimShouldNotBeFound("critico.equals=" + UPDATED_CRITICO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByCriticoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where critico not equals to DEFAULT_CRITICO
        defaultBoletimShouldNotBeFound("critico.notEquals=" + DEFAULT_CRITICO);

        // Get all the boletimList where critico not equals to UPDATED_CRITICO
        defaultBoletimShouldBeFound("critico.notEquals=" + UPDATED_CRITICO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByCriticoIsInShouldWork() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where critico in DEFAULT_CRITICO or UPDATED_CRITICO
        defaultBoletimShouldBeFound("critico.in=" + DEFAULT_CRITICO + "," + UPDATED_CRITICO);

        // Get all the boletimList where critico equals to UPDATED_CRITICO
        defaultBoletimShouldNotBeFound("critico.in=" + UPDATED_CRITICO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByCriticoIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where critico is not null
        defaultBoletimShouldBeFound("critico.specified=true");

        // Get all the boletimList where critico is null
        defaultBoletimShouldNotBeFound("critico.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimsByAprovadoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where aprovado equals to DEFAULT_APROVADO
        defaultBoletimShouldBeFound("aprovado.equals=" + DEFAULT_APROVADO);

        // Get all the boletimList where aprovado equals to UPDATED_APROVADO
        defaultBoletimShouldNotBeFound("aprovado.equals=" + UPDATED_APROVADO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByAprovadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where aprovado not equals to DEFAULT_APROVADO
        defaultBoletimShouldNotBeFound("aprovado.notEquals=" + DEFAULT_APROVADO);

        // Get all the boletimList where aprovado not equals to UPDATED_APROVADO
        defaultBoletimShouldBeFound("aprovado.notEquals=" + UPDATED_APROVADO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByAprovadoIsInShouldWork() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where aprovado in DEFAULT_APROVADO or UPDATED_APROVADO
        defaultBoletimShouldBeFound("aprovado.in=" + DEFAULT_APROVADO + "," + UPDATED_APROVADO);

        // Get all the boletimList where aprovado equals to UPDATED_APROVADO
        defaultBoletimShouldNotBeFound("aprovado.in=" + UPDATED_APROVADO);
    }

    @Test
    @Transactional
    public void getAllBoletimsByAprovadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where aprovado is not null
        defaultBoletimShouldBeFound("aprovado.specified=true");

        // Get all the boletimList where aprovado is null
        defaultBoletimShouldNotBeFound("aprovado.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimsByEnviarSmsIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where enviarSms equals to DEFAULT_ENVIAR_SMS
        defaultBoletimShouldBeFound("enviarSms.equals=" + DEFAULT_ENVIAR_SMS);

        // Get all the boletimList where enviarSms equals to UPDATED_ENVIAR_SMS
        defaultBoletimShouldNotBeFound("enviarSms.equals=" + UPDATED_ENVIAR_SMS);
    }

    @Test
    @Transactional
    public void getAllBoletimsByEnviarSmsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where enviarSms not equals to DEFAULT_ENVIAR_SMS
        defaultBoletimShouldNotBeFound("enviarSms.notEquals=" + DEFAULT_ENVIAR_SMS);

        // Get all the boletimList where enviarSms not equals to UPDATED_ENVIAR_SMS
        defaultBoletimShouldBeFound("enviarSms.notEquals=" + UPDATED_ENVIAR_SMS);
    }

    @Test
    @Transactional
    public void getAllBoletimsByEnviarSmsIsInShouldWork() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where enviarSms in DEFAULT_ENVIAR_SMS or UPDATED_ENVIAR_SMS
        defaultBoletimShouldBeFound("enviarSms.in=" + DEFAULT_ENVIAR_SMS + "," + UPDATED_ENVIAR_SMS);

        // Get all the boletimList where enviarSms equals to UPDATED_ENVIAR_SMS
        defaultBoletimShouldNotBeFound("enviarSms.in=" + UPDATED_ENVIAR_SMS);
    }

    @Test
    @Transactional
    public void getAllBoletimsByEnviarSmsIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where enviarSms is not null
        defaultBoletimShouldBeFound("enviarSms.specified=true");

        // Get all the boletimList where enviarSms is null
        defaultBoletimShouldNotBeFound("enviarSms.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimsByEnviarEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where enviarEmail equals to DEFAULT_ENVIAR_EMAIL
        defaultBoletimShouldBeFound("enviarEmail.equals=" + DEFAULT_ENVIAR_EMAIL);

        // Get all the boletimList where enviarEmail equals to UPDATED_ENVIAR_EMAIL
        defaultBoletimShouldNotBeFound("enviarEmail.equals=" + UPDATED_ENVIAR_EMAIL);
    }

    @Test
    @Transactional
    public void getAllBoletimsByEnviarEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where enviarEmail not equals to DEFAULT_ENVIAR_EMAIL
        defaultBoletimShouldNotBeFound("enviarEmail.notEquals=" + DEFAULT_ENVIAR_EMAIL);

        // Get all the boletimList where enviarEmail not equals to UPDATED_ENVIAR_EMAIL
        defaultBoletimShouldBeFound("enviarEmail.notEquals=" + UPDATED_ENVIAR_EMAIL);
    }

    @Test
    @Transactional
    public void getAllBoletimsByEnviarEmailIsInShouldWork() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where enviarEmail in DEFAULT_ENVIAR_EMAIL or UPDATED_ENVIAR_EMAIL
        defaultBoletimShouldBeFound("enviarEmail.in=" + DEFAULT_ENVIAR_EMAIL + "," + UPDATED_ENVIAR_EMAIL);

        // Get all the boletimList where enviarEmail equals to UPDATED_ENVIAR_EMAIL
        defaultBoletimShouldNotBeFound("enviarEmail.in=" + UPDATED_ENVIAR_EMAIL);
    }

    @Test
    @Transactional
    public void getAllBoletimsByEnviarEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where enviarEmail is not null
        defaultBoletimShouldBeFound("enviarEmail.specified=true");

        // Get all the boletimList where enviarEmail is null
        defaultBoletimShouldNotBeFound("enviarEmail.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where created equals to DEFAULT_CREATED
        defaultBoletimShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the boletimList where created equals to UPDATED_CREATED
        defaultBoletimShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllBoletimsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where created not equals to DEFAULT_CREATED
        defaultBoletimShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the boletimList where created not equals to UPDATED_CREATED
        defaultBoletimShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllBoletimsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultBoletimShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the boletimList where created equals to UPDATED_CREATED
        defaultBoletimShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllBoletimsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where created is not null
        defaultBoletimShouldBeFound("created.specified=true");

        // Get all the boletimList where created is null
        defaultBoletimShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimsByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where updated equals to DEFAULT_UPDATED
        defaultBoletimShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the boletimList where updated equals to UPDATED_UPDATED
        defaultBoletimShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllBoletimsByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where updated not equals to DEFAULT_UPDATED
        defaultBoletimShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the boletimList where updated not equals to UPDATED_UPDATED
        defaultBoletimShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllBoletimsByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultBoletimShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the boletimList where updated equals to UPDATED_UPDATED
        defaultBoletimShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllBoletimsByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        // Get all the boletimList where updated is not null
        defaultBoletimShouldBeFound("updated.specified=true");

        // Get all the boletimList where updated is null
        defaultBoletimShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimsByPlanoRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);
        PlanoRecurso planoRecurso = PlanoRecursoResourceIT.createEntity(em);
        em.persist(planoRecurso);
        em.flush();
        boletim.setPlanoRecurso(planoRecurso);
        boletimRepository.saveAndFlush(boletim);
        Long planoRecursoId = planoRecurso.getId();

        // Get all the boletimList where planoRecurso equals to planoRecursoId
        defaultBoletimShouldBeFound("planoRecursoId.equals=" + planoRecursoId);

        // Get all the boletimList where planoRecurso equals to planoRecursoId + 1
        defaultBoletimShouldNotBeFound("planoRecursoId.equals=" + (planoRecursoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBoletimShouldBeFound(String filter) throws Exception {
        restBoletimMockMvc.perform(get("/api/boletims?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boletim.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO)))
            .andExpect(jsonPath("$.[*].textoSms").value(hasItem(DEFAULT_TEXTO_SMS)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO)))
            .andExpect(jsonPath("$.[*].textoParte2").value(hasItem(DEFAULT_TEXTO_PARTE_2)))
            .andExpect(jsonPath("$.[*].textoParte3").value(hasItem(DEFAULT_TEXTO_PARTE_3)))
            .andExpect(jsonPath("$.[*].subAssunto").value(hasItem(DEFAULT_SUB_ASSUNTO)))
            .andExpect(jsonPath("$.[*].naoExibirPagEmpresa").value(hasItem(DEFAULT_NAO_EXIBIR_PAG_EMPRESA)))
            .andExpect(jsonPath("$.[*].critico").value(hasItem(DEFAULT_CRITICO.booleanValue())))
            .andExpect(jsonPath("$.[*].aprovado").value(hasItem(DEFAULT_APROVADO.booleanValue())))
            .andExpect(jsonPath("$.[*].enviarSms").value(hasItem(DEFAULT_ENVIAR_SMS.booleanValue())))
            .andExpect(jsonPath("$.[*].enviarEmail").value(hasItem(DEFAULT_ENVIAR_EMAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restBoletimMockMvc.perform(get("/api/boletims/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBoletimShouldNotBeFound(String filter) throws Exception {
        restBoletimMockMvc.perform(get("/api/boletims?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBoletimMockMvc.perform(get("/api/boletims/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBoletim() throws Exception {
        // Get the boletim
        restBoletimMockMvc.perform(get("/api/boletims/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoletim() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        int databaseSizeBeforeUpdate = boletimRepository.findAll().size();

        // Update the boletim
        Boletim updatedBoletim = boletimRepository.findById(boletim.getId()).get();
        // Disconnect from session so that the updates on updatedBoletim are not directly saved in db
        em.detach(updatedBoletim);
        updatedBoletim
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .texto(UPDATED_TEXTO)
            .textoSms(UPDATED_TEXTO_SMS)
            .imagem(UPDATED_IMAGEM)
            .assunto(UPDATED_ASSUNTO)
            .textoParte2(UPDATED_TEXTO_PARTE_2)
            .textoParte3(UPDATED_TEXTO_PARTE_3)
            .subAssunto(UPDATED_SUB_ASSUNTO)
            .naoExibirPagEmpresa(UPDATED_NAO_EXIBIR_PAG_EMPRESA)
            .critico(UPDATED_CRITICO)
            .aprovado(UPDATED_APROVADO)
            .enviarSms(UPDATED_ENVIAR_SMS)
            .enviarEmail(UPDATED_ENVIAR_EMAIL)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        BoletimDTO boletimDTO = boletimMapper.toDto(updatedBoletim);

        restBoletimMockMvc.perform(put("/api/boletims")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimDTO)))
            .andExpect(status().isOk());

        // Validate the Boletim in the database
        List<Boletim> boletimList = boletimRepository.findAll();
        assertThat(boletimList).hasSize(databaseSizeBeforeUpdate);
        Boletim testBoletim = boletimList.get(boletimList.size() - 1);
        assertThat(testBoletim.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testBoletim.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testBoletim.getTexto()).isEqualTo(UPDATED_TEXTO);
        assertThat(testBoletim.getTextoSms()).isEqualTo(UPDATED_TEXTO_SMS);
        assertThat(testBoletim.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testBoletim.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testBoletim.getTextoParte2()).isEqualTo(UPDATED_TEXTO_PARTE_2);
        assertThat(testBoletim.getTextoParte3()).isEqualTo(UPDATED_TEXTO_PARTE_3);
        assertThat(testBoletim.getSubAssunto()).isEqualTo(UPDATED_SUB_ASSUNTO);
        assertThat(testBoletim.getNaoExibirPagEmpresa()).isEqualTo(UPDATED_NAO_EXIBIR_PAG_EMPRESA);
        assertThat(testBoletim.isCritico()).isEqualTo(UPDATED_CRITICO);
        assertThat(testBoletim.isAprovado()).isEqualTo(UPDATED_APROVADO);
        assertThat(testBoletim.isEnviarSms()).isEqualTo(UPDATED_ENVIAR_SMS);
        assertThat(testBoletim.isEnviarEmail()).isEqualTo(UPDATED_ENVIAR_EMAIL);
        assertThat(testBoletim.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testBoletim.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingBoletim() throws Exception {
        int databaseSizeBeforeUpdate = boletimRepository.findAll().size();

        // Create the Boletim
        BoletimDTO boletimDTO = boletimMapper.toDto(boletim);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoletimMockMvc.perform(put("/api/boletims")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Boletim in the database
        List<Boletim> boletimList = boletimRepository.findAll();
        assertThat(boletimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBoletim() throws Exception {
        // Initialize the database
        boletimRepository.saveAndFlush(boletim);

        int databaseSizeBeforeDelete = boletimRepository.findAll().size();

        // Delete the boletim
        restBoletimMockMvc.perform(delete("/api/boletims/{id}", boletim.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Boletim> boletimList = boletimRepository.findAll();
        assertThat(boletimList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
