package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.UsuarioPerfil;
import org.tempestade.nucleo.repository.UsuarioPerfilRepository;
import org.tempestade.nucleo.service.UsuarioPerfilService;
import org.tempestade.nucleo.service.dto.UsuarioPerfilDTO;
import org.tempestade.nucleo.service.mapper.UsuarioPerfilMapper;

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
 * Integration tests for the {@link UsuarioPerfilResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UsuarioPerfilResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UsuarioPerfilRepository usuarioPerfilRepository;

    @Autowired
    private UsuarioPerfilMapper usuarioPerfilMapper;

    @Autowired
    private UsuarioPerfilService usuarioPerfilService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuarioPerfilMockMvc;

    private UsuarioPerfil usuarioPerfil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioPerfil createEntity(EntityManager em) {
        UsuarioPerfil usuarioPerfil = new UsuarioPerfil()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return usuarioPerfil;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioPerfil createUpdatedEntity(EntityManager em) {
        UsuarioPerfil usuarioPerfil = new UsuarioPerfil()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return usuarioPerfil;
    }

    @BeforeEach
    public void initTest() {
        usuarioPerfil = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsuarioPerfil() throws Exception {
        int databaseSizeBeforeCreate = usuarioPerfilRepository.findAll().size();
        // Create the UsuarioPerfil
        UsuarioPerfilDTO usuarioPerfilDTO = usuarioPerfilMapper.toDto(usuarioPerfil);
        restUsuarioPerfilMockMvc.perform(post("/api/usuario-perfils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioPerfilDTO)))
            .andExpect(status().isCreated());

        // Validate the UsuarioPerfil in the database
        List<UsuarioPerfil> usuarioPerfilList = usuarioPerfilRepository.findAll();
        assertThat(usuarioPerfilList).hasSize(databaseSizeBeforeCreate + 1);
        UsuarioPerfil testUsuarioPerfil = usuarioPerfilList.get(usuarioPerfilList.size() - 1);
        assertThat(testUsuarioPerfil.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUsuarioPerfil.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testUsuarioPerfil.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testUsuarioPerfil.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createUsuarioPerfilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuarioPerfilRepository.findAll().size();

        // Create the UsuarioPerfil with an existing ID
        usuarioPerfil.setId(1L);
        UsuarioPerfilDTO usuarioPerfilDTO = usuarioPerfilMapper.toDto(usuarioPerfil);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioPerfilMockMvc.perform(post("/api/usuario-perfils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioPerfilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioPerfil in the database
        List<UsuarioPerfil> usuarioPerfilList = usuarioPerfilRepository.findAll();
        assertThat(usuarioPerfilList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioPerfilRepository.findAll().size();
        // set the field null
        usuarioPerfil.setName(null);

        // Create the UsuarioPerfil, which fails.
        UsuarioPerfilDTO usuarioPerfilDTO = usuarioPerfilMapper.toDto(usuarioPerfil);


        restUsuarioPerfilMockMvc.perform(post("/api/usuario-perfils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioPerfilDTO)))
            .andExpect(status().isBadRequest());

        List<UsuarioPerfil> usuarioPerfilList = usuarioPerfilRepository.findAll();
        assertThat(usuarioPerfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioPerfilRepository.findAll().size();
        // set the field null
        usuarioPerfil.setDescricao(null);

        // Create the UsuarioPerfil, which fails.
        UsuarioPerfilDTO usuarioPerfilDTO = usuarioPerfilMapper.toDto(usuarioPerfil);


        restUsuarioPerfilMockMvc.perform(post("/api/usuario-perfils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioPerfilDTO)))
            .andExpect(status().isBadRequest());

        List<UsuarioPerfil> usuarioPerfilList = usuarioPerfilRepository.findAll();
        assertThat(usuarioPerfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioPerfilRepository.findAll().size();
        // set the field null
        usuarioPerfil.setCreated(null);

        // Create the UsuarioPerfil, which fails.
        UsuarioPerfilDTO usuarioPerfilDTO = usuarioPerfilMapper.toDto(usuarioPerfil);


        restUsuarioPerfilMockMvc.perform(post("/api/usuario-perfils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioPerfilDTO)))
            .andExpect(status().isBadRequest());

        List<UsuarioPerfil> usuarioPerfilList = usuarioPerfilRepository.findAll();
        assertThat(usuarioPerfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfils() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList
        restUsuarioPerfilMockMvc.perform(get("/api/usuario-perfils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioPerfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getUsuarioPerfil() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get the usuarioPerfil
        restUsuarioPerfilMockMvc.perform(get("/api/usuario-perfils/{id}", usuarioPerfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuarioPerfil.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUsuarioPerfil() throws Exception {
        // Get the usuarioPerfil
        restUsuarioPerfilMockMvc.perform(get("/api/usuario-perfils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsuarioPerfil() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        int databaseSizeBeforeUpdate = usuarioPerfilRepository.findAll().size();

        // Update the usuarioPerfil
        UsuarioPerfil updatedUsuarioPerfil = usuarioPerfilRepository.findById(usuarioPerfil.getId()).get();
        // Disconnect from session so that the updates on updatedUsuarioPerfil are not directly saved in db
        em.detach(updatedUsuarioPerfil);
        updatedUsuarioPerfil
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        UsuarioPerfilDTO usuarioPerfilDTO = usuarioPerfilMapper.toDto(updatedUsuarioPerfil);

        restUsuarioPerfilMockMvc.perform(put("/api/usuario-perfils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioPerfilDTO)))
            .andExpect(status().isOk());

        // Validate the UsuarioPerfil in the database
        List<UsuarioPerfil> usuarioPerfilList = usuarioPerfilRepository.findAll();
        assertThat(usuarioPerfilList).hasSize(databaseSizeBeforeUpdate);
        UsuarioPerfil testUsuarioPerfil = usuarioPerfilList.get(usuarioPerfilList.size() - 1);
        assertThat(testUsuarioPerfil.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUsuarioPerfil.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testUsuarioPerfil.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testUsuarioPerfil.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingUsuarioPerfil() throws Exception {
        int databaseSizeBeforeUpdate = usuarioPerfilRepository.findAll().size();

        // Create the UsuarioPerfil
        UsuarioPerfilDTO usuarioPerfilDTO = usuarioPerfilMapper.toDto(usuarioPerfil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioPerfilMockMvc.perform(put("/api/usuario-perfils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioPerfilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioPerfil in the database
        List<UsuarioPerfil> usuarioPerfilList = usuarioPerfilRepository.findAll();
        assertThat(usuarioPerfilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsuarioPerfil() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        int databaseSizeBeforeDelete = usuarioPerfilRepository.findAll().size();

        // Delete the usuarioPerfil
        restUsuarioPerfilMockMvc.perform(delete("/api/usuario-perfils/{id}", usuarioPerfil.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsuarioPerfil> usuarioPerfilList = usuarioPerfilRepository.findAll();
        assertThat(usuarioPerfilList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
