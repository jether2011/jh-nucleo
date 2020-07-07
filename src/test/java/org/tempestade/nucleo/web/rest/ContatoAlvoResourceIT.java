package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.ContatoAlvo;
import org.tempestade.nucleo.domain.Contato;
import org.tempestade.nucleo.domain.Alvo;
import org.tempestade.nucleo.repository.ContatoAlvoRepository;
import org.tempestade.nucleo.service.ContatoAlvoService;
import org.tempestade.nucleo.service.dto.ContatoAlvoDTO;
import org.tempestade.nucleo.service.mapper.ContatoAlvoMapper;
import org.tempestade.nucleo.service.dto.ContatoAlvoCriteria;
import org.tempestade.nucleo.service.ContatoAlvoQueryService;

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
 * Integration tests for the {@link ContatoAlvoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContatoAlvoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ContatoAlvoRepository contatoAlvoRepository;

    @Autowired
    private ContatoAlvoMapper contatoAlvoMapper;

    @Autowired
    private ContatoAlvoService contatoAlvoService;

    @Autowired
    private ContatoAlvoQueryService contatoAlvoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContatoAlvoMockMvc;

    private ContatoAlvo contatoAlvo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContatoAlvo createEntity(EntityManager em) {
        ContatoAlvo contatoAlvo = new ContatoAlvo()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return contatoAlvo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContatoAlvo createUpdatedEntity(EntityManager em) {
        ContatoAlvo contatoAlvo = new ContatoAlvo()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return contatoAlvo;
    }

    @BeforeEach
    public void initTest() {
        contatoAlvo = createEntity(em);
    }

    @Test
    @Transactional
    public void createContatoAlvo() throws Exception {
        int databaseSizeBeforeCreate = contatoAlvoRepository.findAll().size();
        // Create the ContatoAlvo
        ContatoAlvoDTO contatoAlvoDTO = contatoAlvoMapper.toDto(contatoAlvo);
        restContatoAlvoMockMvc.perform(post("/api/contato-alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoAlvoDTO)))
            .andExpect(status().isCreated());

        // Validate the ContatoAlvo in the database
        List<ContatoAlvo> contatoAlvoList = contatoAlvoRepository.findAll();
        assertThat(contatoAlvoList).hasSize(databaseSizeBeforeCreate + 1);
        ContatoAlvo testContatoAlvo = contatoAlvoList.get(contatoAlvoList.size() - 1);
        assertThat(testContatoAlvo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testContatoAlvo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testContatoAlvo.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testContatoAlvo.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createContatoAlvoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contatoAlvoRepository.findAll().size();

        // Create the ContatoAlvo with an existing ID
        contatoAlvo.setId(1L);
        ContatoAlvoDTO contatoAlvoDTO = contatoAlvoMapper.toDto(contatoAlvo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContatoAlvoMockMvc.perform(post("/api/contato-alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoAlvoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContatoAlvo in the database
        List<ContatoAlvo> contatoAlvoList = contatoAlvoRepository.findAll();
        assertThat(contatoAlvoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoAlvoRepository.findAll().size();
        // set the field null
        contatoAlvo.setNome(null);

        // Create the ContatoAlvo, which fails.
        ContatoAlvoDTO contatoAlvoDTO = contatoAlvoMapper.toDto(contatoAlvo);


        restContatoAlvoMockMvc.perform(post("/api/contato-alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoAlvoDTO)))
            .andExpect(status().isBadRequest());

        List<ContatoAlvo> contatoAlvoList = contatoAlvoRepository.findAll();
        assertThat(contatoAlvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoAlvoRepository.findAll().size();
        // set the field null
        contatoAlvo.setCreated(null);

        // Create the ContatoAlvo, which fails.
        ContatoAlvoDTO contatoAlvoDTO = contatoAlvoMapper.toDto(contatoAlvo);


        restContatoAlvoMockMvc.perform(post("/api/contato-alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoAlvoDTO)))
            .andExpect(status().isBadRequest());

        List<ContatoAlvo> contatoAlvoList = contatoAlvoRepository.findAll();
        assertThat(contatoAlvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContatoAlvos() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList
        restContatoAlvoMockMvc.perform(get("/api/contato-alvos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contatoAlvo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getContatoAlvo() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get the contatoAlvo
        restContatoAlvoMockMvc.perform(get("/api/contato-alvos/{id}", contatoAlvo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contatoAlvo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getContatoAlvosByIdFiltering() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        Long id = contatoAlvo.getId();

        defaultContatoAlvoShouldBeFound("id.equals=" + id);
        defaultContatoAlvoShouldNotBeFound("id.notEquals=" + id);

        defaultContatoAlvoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContatoAlvoShouldNotBeFound("id.greaterThan=" + id);

        defaultContatoAlvoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContatoAlvoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllContatoAlvosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where nome equals to DEFAULT_NOME
        defaultContatoAlvoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the contatoAlvoList where nome equals to UPDATED_NOME
        defaultContatoAlvoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoAlvosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where nome not equals to DEFAULT_NOME
        defaultContatoAlvoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the contatoAlvoList where nome not equals to UPDATED_NOME
        defaultContatoAlvoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoAlvosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultContatoAlvoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the contatoAlvoList where nome equals to UPDATED_NOME
        defaultContatoAlvoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoAlvosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where nome is not null
        defaultContatoAlvoShouldBeFound("nome.specified=true");

        // Get all the contatoAlvoList where nome is null
        defaultContatoAlvoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllContatoAlvosByNomeContainsSomething() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where nome contains DEFAULT_NOME
        defaultContatoAlvoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the contatoAlvoList where nome contains UPDATED_NOME
        defaultContatoAlvoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllContatoAlvosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where nome does not contain DEFAULT_NOME
        defaultContatoAlvoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the contatoAlvoList where nome does not contain UPDATED_NOME
        defaultContatoAlvoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllContatoAlvosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where descricao equals to DEFAULT_DESCRICAO
        defaultContatoAlvoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the contatoAlvoList where descricao equals to UPDATED_DESCRICAO
        defaultContatoAlvoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContatoAlvosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where descricao not equals to DEFAULT_DESCRICAO
        defaultContatoAlvoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the contatoAlvoList where descricao not equals to UPDATED_DESCRICAO
        defaultContatoAlvoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContatoAlvosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultContatoAlvoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the contatoAlvoList where descricao equals to UPDATED_DESCRICAO
        defaultContatoAlvoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContatoAlvosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where descricao is not null
        defaultContatoAlvoShouldBeFound("descricao.specified=true");

        // Get all the contatoAlvoList where descricao is null
        defaultContatoAlvoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllContatoAlvosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where descricao contains DEFAULT_DESCRICAO
        defaultContatoAlvoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the contatoAlvoList where descricao contains UPDATED_DESCRICAO
        defaultContatoAlvoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContatoAlvosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where descricao does not contain DEFAULT_DESCRICAO
        defaultContatoAlvoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the contatoAlvoList where descricao does not contain UPDATED_DESCRICAO
        defaultContatoAlvoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllContatoAlvosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where created equals to DEFAULT_CREATED
        defaultContatoAlvoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the contatoAlvoList where created equals to UPDATED_CREATED
        defaultContatoAlvoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllContatoAlvosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where created not equals to DEFAULT_CREATED
        defaultContatoAlvoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the contatoAlvoList where created not equals to UPDATED_CREATED
        defaultContatoAlvoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllContatoAlvosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultContatoAlvoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the contatoAlvoList where created equals to UPDATED_CREATED
        defaultContatoAlvoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllContatoAlvosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where created is not null
        defaultContatoAlvoShouldBeFound("created.specified=true");

        // Get all the contatoAlvoList where created is null
        defaultContatoAlvoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoAlvosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where updated equals to DEFAULT_UPDATED
        defaultContatoAlvoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the contatoAlvoList where updated equals to UPDATED_UPDATED
        defaultContatoAlvoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllContatoAlvosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where updated not equals to DEFAULT_UPDATED
        defaultContatoAlvoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the contatoAlvoList where updated not equals to UPDATED_UPDATED
        defaultContatoAlvoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllContatoAlvosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultContatoAlvoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the contatoAlvoList where updated equals to UPDATED_UPDATED
        defaultContatoAlvoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllContatoAlvosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        // Get all the contatoAlvoList where updated is not null
        defaultContatoAlvoShouldBeFound("updated.specified=true");

        // Get all the contatoAlvoList where updated is null
        defaultContatoAlvoShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllContatoAlvosByContatoIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);
        Contato contato = ContatoResourceIT.createEntity(em);
        em.persist(contato);
        em.flush();
        contatoAlvo.setContato(contato);
        contatoAlvoRepository.saveAndFlush(contatoAlvo);
        Long contatoId = contato.getId();

        // Get all the contatoAlvoList where contato equals to contatoId
        defaultContatoAlvoShouldBeFound("contatoId.equals=" + contatoId);

        // Get all the contatoAlvoList where contato equals to contatoId + 1
        defaultContatoAlvoShouldNotBeFound("contatoId.equals=" + (contatoId + 1));
    }


    @Test
    @Transactional
    public void getAllContatoAlvosByAlvoIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);
        Alvo alvo = AlvoResourceIT.createEntity(em);
        em.persist(alvo);
        em.flush();
        contatoAlvo.setAlvo(alvo);
        contatoAlvoRepository.saveAndFlush(contatoAlvo);
        Long alvoId = alvo.getId();

        // Get all the contatoAlvoList where alvo equals to alvoId
        defaultContatoAlvoShouldBeFound("alvoId.equals=" + alvoId);

        // Get all the contatoAlvoList where alvo equals to alvoId + 1
        defaultContatoAlvoShouldNotBeFound("alvoId.equals=" + (alvoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContatoAlvoShouldBeFound(String filter) throws Exception {
        restContatoAlvoMockMvc.perform(get("/api/contato-alvos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contatoAlvo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restContatoAlvoMockMvc.perform(get("/api/contato-alvos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContatoAlvoShouldNotBeFound(String filter) throws Exception {
        restContatoAlvoMockMvc.perform(get("/api/contato-alvos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContatoAlvoMockMvc.perform(get("/api/contato-alvos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingContatoAlvo() throws Exception {
        // Get the contatoAlvo
        restContatoAlvoMockMvc.perform(get("/api/contato-alvos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContatoAlvo() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        int databaseSizeBeforeUpdate = contatoAlvoRepository.findAll().size();

        // Update the contatoAlvo
        ContatoAlvo updatedContatoAlvo = contatoAlvoRepository.findById(contatoAlvo.getId()).get();
        // Disconnect from session so that the updates on updatedContatoAlvo are not directly saved in db
        em.detach(updatedContatoAlvo);
        updatedContatoAlvo
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        ContatoAlvoDTO contatoAlvoDTO = contatoAlvoMapper.toDto(updatedContatoAlvo);

        restContatoAlvoMockMvc.perform(put("/api/contato-alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoAlvoDTO)))
            .andExpect(status().isOk());

        // Validate the ContatoAlvo in the database
        List<ContatoAlvo> contatoAlvoList = contatoAlvoRepository.findAll();
        assertThat(contatoAlvoList).hasSize(databaseSizeBeforeUpdate);
        ContatoAlvo testContatoAlvo = contatoAlvoList.get(contatoAlvoList.size() - 1);
        assertThat(testContatoAlvo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testContatoAlvo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testContatoAlvo.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testContatoAlvo.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingContatoAlvo() throws Exception {
        int databaseSizeBeforeUpdate = contatoAlvoRepository.findAll().size();

        // Create the ContatoAlvo
        ContatoAlvoDTO contatoAlvoDTO = contatoAlvoMapper.toDto(contatoAlvo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContatoAlvoMockMvc.perform(put("/api/contato-alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoAlvoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContatoAlvo in the database
        List<ContatoAlvo> contatoAlvoList = contatoAlvoRepository.findAll();
        assertThat(contatoAlvoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContatoAlvo() throws Exception {
        // Initialize the database
        contatoAlvoRepository.saveAndFlush(contatoAlvo);

        int databaseSizeBeforeDelete = contatoAlvoRepository.findAll().size();

        // Delete the contatoAlvo
        restContatoAlvoMockMvc.perform(delete("/api/contato-alvos/{id}", contatoAlvo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContatoAlvo> contatoAlvoList = contatoAlvoRepository.findAll();
        assertThat(contatoAlvoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
