package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.AvisoMeteorologico;
import org.tempestade.nucleo.repository.AvisoMeteorologicoRepository;
import org.tempestade.nucleo.service.AvisoMeteorologicoService;
import org.tempestade.nucleo.service.dto.AvisoMeteorologicoDTO;
import org.tempestade.nucleo.service.mapper.AvisoMeteorologicoMapper;

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
