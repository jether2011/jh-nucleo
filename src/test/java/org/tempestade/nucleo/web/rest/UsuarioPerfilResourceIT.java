package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.UsuarioPerfil;
import org.tempestade.nucleo.domain.Usuario;
import org.tempestade.nucleo.domain.Perfil;
import org.tempestade.nucleo.repository.UsuarioPerfilRepository;
import org.tempestade.nucleo.service.UsuarioPerfilService;
import org.tempestade.nucleo.service.dto.UsuarioPerfilDTO;
import org.tempestade.nucleo.service.mapper.UsuarioPerfilMapper;
import org.tempestade.nucleo.service.dto.UsuarioPerfilCriteria;
import org.tempestade.nucleo.service.UsuarioPerfilQueryService;

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
    private UsuarioPerfilQueryService usuarioPerfilQueryService;

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
    public void getUsuarioPerfilsByIdFiltering() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        Long id = usuarioPerfil.getId();

        defaultUsuarioPerfilShouldBeFound("id.equals=" + id);
        defaultUsuarioPerfilShouldNotBeFound("id.notEquals=" + id);

        defaultUsuarioPerfilShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUsuarioPerfilShouldNotBeFound("id.greaterThan=" + id);

        defaultUsuarioPerfilShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUsuarioPerfilShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUsuarioPerfilsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where name equals to DEFAULT_NAME
        defaultUsuarioPerfilShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the usuarioPerfilList where name equals to UPDATED_NAME
        defaultUsuarioPerfilShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfilsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where name not equals to DEFAULT_NAME
        defaultUsuarioPerfilShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the usuarioPerfilList where name not equals to UPDATED_NAME
        defaultUsuarioPerfilShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfilsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUsuarioPerfilShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the usuarioPerfilList where name equals to UPDATED_NAME
        defaultUsuarioPerfilShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfilsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where name is not null
        defaultUsuarioPerfilShouldBeFound("name.specified=true");

        // Get all the usuarioPerfilList where name is null
        defaultUsuarioPerfilShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuarioPerfilsByNameContainsSomething() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where name contains DEFAULT_NAME
        defaultUsuarioPerfilShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the usuarioPerfilList where name contains UPDATED_NAME
        defaultUsuarioPerfilShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfilsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where name does not contain DEFAULT_NAME
        defaultUsuarioPerfilShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the usuarioPerfilList where name does not contain UPDATED_NAME
        defaultUsuarioPerfilShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllUsuarioPerfilsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where descricao equals to DEFAULT_DESCRICAO
        defaultUsuarioPerfilShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the usuarioPerfilList where descricao equals to UPDATED_DESCRICAO
        defaultUsuarioPerfilShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfilsByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where descricao not equals to DEFAULT_DESCRICAO
        defaultUsuarioPerfilShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the usuarioPerfilList where descricao not equals to UPDATED_DESCRICAO
        defaultUsuarioPerfilShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfilsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultUsuarioPerfilShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the usuarioPerfilList where descricao equals to UPDATED_DESCRICAO
        defaultUsuarioPerfilShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfilsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where descricao is not null
        defaultUsuarioPerfilShouldBeFound("descricao.specified=true");

        // Get all the usuarioPerfilList where descricao is null
        defaultUsuarioPerfilShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuarioPerfilsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where descricao contains DEFAULT_DESCRICAO
        defaultUsuarioPerfilShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the usuarioPerfilList where descricao contains UPDATED_DESCRICAO
        defaultUsuarioPerfilShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfilsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where descricao does not contain DEFAULT_DESCRICAO
        defaultUsuarioPerfilShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the usuarioPerfilList where descricao does not contain UPDATED_DESCRICAO
        defaultUsuarioPerfilShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllUsuarioPerfilsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where created equals to DEFAULT_CREATED
        defaultUsuarioPerfilShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the usuarioPerfilList where created equals to UPDATED_CREATED
        defaultUsuarioPerfilShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfilsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where created not equals to DEFAULT_CREATED
        defaultUsuarioPerfilShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the usuarioPerfilList where created not equals to UPDATED_CREATED
        defaultUsuarioPerfilShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfilsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultUsuarioPerfilShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the usuarioPerfilList where created equals to UPDATED_CREATED
        defaultUsuarioPerfilShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfilsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where created is not null
        defaultUsuarioPerfilShouldBeFound("created.specified=true");

        // Get all the usuarioPerfilList where created is null
        defaultUsuarioPerfilShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfilsByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where updated equals to DEFAULT_UPDATED
        defaultUsuarioPerfilShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the usuarioPerfilList where updated equals to UPDATED_UPDATED
        defaultUsuarioPerfilShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfilsByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where updated not equals to DEFAULT_UPDATED
        defaultUsuarioPerfilShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the usuarioPerfilList where updated not equals to UPDATED_UPDATED
        defaultUsuarioPerfilShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfilsByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultUsuarioPerfilShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the usuarioPerfilList where updated equals to UPDATED_UPDATED
        defaultUsuarioPerfilShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfilsByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);

        // Get all the usuarioPerfilList where updated is not null
        defaultUsuarioPerfilShouldBeFound("updated.specified=true");

        // Get all the usuarioPerfilList where updated is null
        defaultUsuarioPerfilShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuarioPerfilsByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);
        Usuario usuario = UsuarioResourceIT.createEntity(em);
        em.persist(usuario);
        em.flush();
        usuarioPerfil.setUsuario(usuario);
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);
        Long usuarioId = usuario.getId();

        // Get all the usuarioPerfilList where usuario equals to usuarioId
        defaultUsuarioPerfilShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the usuarioPerfilList where usuario equals to usuarioId + 1
        defaultUsuarioPerfilShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }


    @Test
    @Transactional
    public void getAllUsuarioPerfilsByPerfilIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);
        Perfil perfil = PerfilResourceIT.createEntity(em);
        em.persist(perfil);
        em.flush();
        usuarioPerfil.setPerfil(perfil);
        usuarioPerfilRepository.saveAndFlush(usuarioPerfil);
        Long perfilId = perfil.getId();

        // Get all the usuarioPerfilList where perfil equals to perfilId
        defaultUsuarioPerfilShouldBeFound("perfilId.equals=" + perfilId);

        // Get all the usuarioPerfilList where perfil equals to perfilId + 1
        defaultUsuarioPerfilShouldNotBeFound("perfilId.equals=" + (perfilId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUsuarioPerfilShouldBeFound(String filter) throws Exception {
        restUsuarioPerfilMockMvc.perform(get("/api/usuario-perfils?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioPerfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restUsuarioPerfilMockMvc.perform(get("/api/usuario-perfils/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUsuarioPerfilShouldNotBeFound(String filter) throws Exception {
        restUsuarioPerfilMockMvc.perform(get("/api/usuario-perfils?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUsuarioPerfilMockMvc.perform(get("/api/usuario-perfils/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
