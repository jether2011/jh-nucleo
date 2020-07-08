package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Contato;
import org.tempestade.nucleo.repository.ContatoRepository;
import org.tempestade.nucleo.service.ContatoService;
import org.tempestade.nucleo.service.dto.ContatoDTO;
import org.tempestade.nucleo.service.mapper.ContatoMapper;

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
 * Integration tests for the {@link ContatoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContatoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Boolean DEFAULT_CONTATO_ALERTA_TELEFONICO = false;
    private static final Boolean UPDATED_CONTATO_ALERTA_TELEFONICO = true;

    private static final Integer DEFAULT_PRIORIDADE = 1;
    private static final Integer UPDATED_PRIORIDADE = 2;

    private static final String DEFAULT_HORA_LIGACAO_INICIAL = "21:56:27";
    private static final String UPDATED_HORA_LIGACAO_INICIAL = "23:39:25";

    private static final String DEFAULT_HORA_LIGACAO_FINAL = "23:28:39";
    private static final String UPDATED_HORA_LIGACAO_FINAL = "03:24:16";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private ContatoMapper contatoMapper;

    @Autowired
    private ContatoService contatoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContatoMockMvc;

    private Contato contato;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contato createEntity(EntityManager em) {
        Contato contato = new Contato()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .email(DEFAULT_EMAIL)
            .celular(DEFAULT_CELULAR)
            .ativo(DEFAULT_ATIVO)
            .contatoAlertaTelefonico(DEFAULT_CONTATO_ALERTA_TELEFONICO)
            .prioridade(DEFAULT_PRIORIDADE)
            .horaLigacaoInicial(DEFAULT_HORA_LIGACAO_INICIAL)
            .horaLigacaoFinal(DEFAULT_HORA_LIGACAO_FINAL)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return contato;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contato createUpdatedEntity(EntityManager em) {
        Contato contato = new Contato()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .email(UPDATED_EMAIL)
            .celular(UPDATED_CELULAR)
            .ativo(UPDATED_ATIVO)
            .contatoAlertaTelefonico(UPDATED_CONTATO_ALERTA_TELEFONICO)
            .prioridade(UPDATED_PRIORIDADE)
            .horaLigacaoInicial(UPDATED_HORA_LIGACAO_INICIAL)
            .horaLigacaoFinal(UPDATED_HORA_LIGACAO_FINAL)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return contato;
    }

    @BeforeEach
    public void initTest() {
        contato = createEntity(em);
    }

    @Test
    @Transactional
    public void createContato() throws Exception {
        int databaseSizeBeforeCreate = contatoRepository.findAll().size();
        // Create the Contato
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);
        restContatoMockMvc.perform(post("/api/contatoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isCreated());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeCreate + 1);
        Contato testContato = contatoList.get(contatoList.size() - 1);
        assertThat(testContato.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testContato.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testContato.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContato.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testContato.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testContato.isContatoAlertaTelefonico()).isEqualTo(DEFAULT_CONTATO_ALERTA_TELEFONICO);
        assertThat(testContato.getPrioridade()).isEqualTo(DEFAULT_PRIORIDADE);
        assertThat(testContato.getHoraLigacaoInicial()).isEqualTo(DEFAULT_HORA_LIGACAO_INICIAL);
        assertThat(testContato.getHoraLigacaoFinal()).isEqualTo(DEFAULT_HORA_LIGACAO_FINAL);
        assertThat(testContato.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testContato.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createContatoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contatoRepository.findAll().size();

        // Create the Contato with an existing ID
        contato.setId(1L);
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContatoMockMvc.perform(post("/api/contatoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoRepository.findAll().size();
        // set the field null
        contato.setNome(null);

        // Create the Contato, which fails.
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);


        restContatoMockMvc.perform(post("/api/contatoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoRepository.findAll().size();
        // set the field null
        contato.setEmail(null);

        // Create the Contato, which fails.
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);


        restContatoMockMvc.perform(post("/api/contatoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoRepository.findAll().size();
        // set the field null
        contato.setCreated(null);

        // Create the Contato, which fails.
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);


        restContatoMockMvc.perform(post("/api/contatoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContatoes() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList
        restContatoMockMvc.perform(get("/api/contatoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contato.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].contatoAlertaTelefonico").value(hasItem(DEFAULT_CONTATO_ALERTA_TELEFONICO.booleanValue())))
            .andExpect(jsonPath("$.[*].prioridade").value(hasItem(DEFAULT_PRIORIDADE)))
            .andExpect(jsonPath("$.[*].horaLigacaoInicial").value(hasItem(DEFAULT_HORA_LIGACAO_INICIAL)))
            .andExpect(jsonPath("$.[*].horaLigacaoFinal").value(hasItem(DEFAULT_HORA_LIGACAO_FINAL)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getContato() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get the contato
        restContatoMockMvc.perform(get("/api/contatoes/{id}", contato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contato.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.contatoAlertaTelefonico").value(DEFAULT_CONTATO_ALERTA_TELEFONICO.booleanValue()))
            .andExpect(jsonPath("$.prioridade").value(DEFAULT_PRIORIDADE))
            .andExpect(jsonPath("$.horaLigacaoInicial").value(DEFAULT_HORA_LIGACAO_INICIAL))
            .andExpect(jsonPath("$.horaLigacaoFinal").value(DEFAULT_HORA_LIGACAO_FINAL))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingContato() throws Exception {
        // Get the contato
        restContatoMockMvc.perform(get("/api/contatoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContato() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        int databaseSizeBeforeUpdate = contatoRepository.findAll().size();

        // Update the contato
        Contato updatedContato = contatoRepository.findById(contato.getId()).get();
        // Disconnect from session so that the updates on updatedContato are not directly saved in db
        em.detach(updatedContato);
        updatedContato
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .email(UPDATED_EMAIL)
            .celular(UPDATED_CELULAR)
            .ativo(UPDATED_ATIVO)
            .contatoAlertaTelefonico(UPDATED_CONTATO_ALERTA_TELEFONICO)
            .prioridade(UPDATED_PRIORIDADE)
            .horaLigacaoInicial(UPDATED_HORA_LIGACAO_INICIAL)
            .horaLigacaoFinal(UPDATED_HORA_LIGACAO_FINAL)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        ContatoDTO contatoDTO = contatoMapper.toDto(updatedContato);

        restContatoMockMvc.perform(put("/api/contatoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isOk());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeUpdate);
        Contato testContato = contatoList.get(contatoList.size() - 1);
        assertThat(testContato.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testContato.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testContato.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContato.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testContato.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testContato.isContatoAlertaTelefonico()).isEqualTo(UPDATED_CONTATO_ALERTA_TELEFONICO);
        assertThat(testContato.getPrioridade()).isEqualTo(UPDATED_PRIORIDADE);
        assertThat(testContato.getHoraLigacaoInicial()).isEqualTo(UPDATED_HORA_LIGACAO_INICIAL);
        assertThat(testContato.getHoraLigacaoFinal()).isEqualTo(UPDATED_HORA_LIGACAO_FINAL);
        assertThat(testContato.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testContato.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingContato() throws Exception {
        int databaseSizeBeforeUpdate = contatoRepository.findAll().size();

        // Create the Contato
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContatoMockMvc.perform(put("/api/contatoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContato() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        int databaseSizeBeforeDelete = contatoRepository.findAll().size();

        // Delete the contato
        restContatoMockMvc.perform(delete("/api/contatoes/{id}", contato.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
