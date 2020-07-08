package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Noticia;
import org.tempestade.nucleo.repository.NoticiaRepository;
import org.tempestade.nucleo.service.NoticiaService;
import org.tempestade.nucleo.service.dto.NoticiaDTO;
import org.tempestade.nucleo.service.mapper.NoticiaMapper;

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
 * Integration tests for the {@link NoticiaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NoticiaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENVIADO = false;
    private static final Boolean UPDATED_ENVIADO = true;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Autowired
    private NoticiaMapper noticiaMapper;

    @Autowired
    private NoticiaService noticiaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNoticiaMockMvc;

    private Noticia noticia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Noticia createEntity(EntityManager em) {
        Noticia noticia = new Noticia()
            .name(DEFAULT_NAME)
            .texto(DEFAULT_TEXTO)
            .enviado(DEFAULT_ENVIADO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return noticia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Noticia createUpdatedEntity(EntityManager em) {
        Noticia noticia = new Noticia()
            .name(UPDATED_NAME)
            .texto(UPDATED_TEXTO)
            .enviado(UPDATED_ENVIADO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return noticia;
    }

    @BeforeEach
    public void initTest() {
        noticia = createEntity(em);
    }

    @Test
    @Transactional
    public void createNoticia() throws Exception {
        int databaseSizeBeforeCreate = noticiaRepository.findAll().size();
        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);
        restNoticiaMockMvc.perform(post("/api/noticias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noticiaDTO)))
            .andExpect(status().isCreated());

        // Validate the Noticia in the database
        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeCreate + 1);
        Noticia testNoticia = noticiaList.get(noticiaList.size() - 1);
        assertThat(testNoticia.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNoticia.getTexto()).isEqualTo(DEFAULT_TEXTO);
        assertThat(testNoticia.isEnviado()).isEqualTo(DEFAULT_ENVIADO);
        assertThat(testNoticia.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testNoticia.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createNoticiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = noticiaRepository.findAll().size();

        // Create the Noticia with an existing ID
        noticia.setId(1L);
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoticiaMockMvc.perform(post("/api/noticias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noticiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Noticia in the database
        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticiaRepository.findAll().size();
        // set the field null
        noticia.setName(null);

        // Create the Noticia, which fails.
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);


        restNoticiaMockMvc.perform(post("/api/noticias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noticiaDTO)))
            .andExpect(status().isBadRequest());

        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextoIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticiaRepository.findAll().size();
        // set the field null
        noticia.setTexto(null);

        // Create the Noticia, which fails.
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);


        restNoticiaMockMvc.perform(post("/api/noticias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noticiaDTO)))
            .andExpect(status().isBadRequest());

        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticiaRepository.findAll().size();
        // set the field null
        noticia.setCreated(null);

        // Create the Noticia, which fails.
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);


        restNoticiaMockMvc.perform(post("/api/noticias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noticiaDTO)))
            .andExpect(status().isBadRequest());

        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNoticias() throws Exception {
        // Initialize the database
        noticiaRepository.saveAndFlush(noticia);

        // Get all the noticiaList
        restNoticiaMockMvc.perform(get("/api/noticias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noticia.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO)))
            .andExpect(jsonPath("$.[*].enviado").value(hasItem(DEFAULT_ENVIADO.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getNoticia() throws Exception {
        // Initialize the database
        noticiaRepository.saveAndFlush(noticia);

        // Get the noticia
        restNoticiaMockMvc.perform(get("/api/noticias/{id}", noticia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(noticia.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.texto").value(DEFAULT_TEXTO))
            .andExpect(jsonPath("$.enviado").value(DEFAULT_ENVIADO.booleanValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingNoticia() throws Exception {
        // Get the noticia
        restNoticiaMockMvc.perform(get("/api/noticias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNoticia() throws Exception {
        // Initialize the database
        noticiaRepository.saveAndFlush(noticia);

        int databaseSizeBeforeUpdate = noticiaRepository.findAll().size();

        // Update the noticia
        Noticia updatedNoticia = noticiaRepository.findById(noticia.getId()).get();
        // Disconnect from session so that the updates on updatedNoticia are not directly saved in db
        em.detach(updatedNoticia);
        updatedNoticia
            .name(UPDATED_NAME)
            .texto(UPDATED_TEXTO)
            .enviado(UPDATED_ENVIADO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(updatedNoticia);

        restNoticiaMockMvc.perform(put("/api/noticias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noticiaDTO)))
            .andExpect(status().isOk());

        // Validate the Noticia in the database
        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeUpdate);
        Noticia testNoticia = noticiaList.get(noticiaList.size() - 1);
        assertThat(testNoticia.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNoticia.getTexto()).isEqualTo(UPDATED_TEXTO);
        assertThat(testNoticia.isEnviado()).isEqualTo(UPDATED_ENVIADO);
        assertThat(testNoticia.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testNoticia.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingNoticia() throws Exception {
        int databaseSizeBeforeUpdate = noticiaRepository.findAll().size();

        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoticiaMockMvc.perform(put("/api/noticias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noticiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Noticia in the database
        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNoticia() throws Exception {
        // Initialize the database
        noticiaRepository.saveAndFlush(noticia);

        int databaseSizeBeforeDelete = noticiaRepository.findAll().size();

        // Delete the noticia
        restNoticiaMockMvc.perform(delete("/api/noticias/{id}", noticia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
