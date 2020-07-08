package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Boletim;
import org.tempestade.nucleo.repository.BoletimRepository;
import org.tempestade.nucleo.service.BoletimService;
import org.tempestade.nucleo.service.dto.BoletimDTO;
import org.tempestade.nucleo.service.mapper.BoletimMapper;

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
