package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Usuario;
import org.tempestade.nucleo.repository.UsuarioRepository;
import org.tempestade.nucleo.service.UsuarioService;
import org.tempestade.nucleo.service.dto.UsuarioDTO;
import org.tempestade.nucleo.service.mapper.UsuarioMapper;
import org.tempestade.nucleo.service.dto.UsuarioCriteria;
import org.tempestade.nucleo.service.UsuarioQueryService;

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
 * Integration tests for the {@link UsuarioResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UsuarioResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SENHA = "AAAAAAAAAA";
    private static final String UPDATED_SENHA = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;
    private static final Integer SMALLER_NUMERO = 1 - 1;

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AA";
    private static final String UPDATED_ESTADO = "BB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_DETALHE = "AAAAAAAAAA";
    private static final String UPDATED_DETALHE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BLOQUEADO = false;
    private static final Boolean UPDATED_BLOQUEADO = true;

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NAO_PODE_EXCLUIR = false;
    private static final Boolean UPDATED_NAO_PODE_EXCLUIR = true;

    private static final Instant DEFAULT_ULTIMO_ACESSO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ULTIMO_ACESSO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SENHA_FIREBASE = "AAAAAAAAAA";
    private static final String UPDATED_SENHA_FIREBASE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioQueryService usuarioQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuarioMockMvc;

    private Usuario usuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuario createEntity(EntityManager em) {
        Usuario usuario = new Usuario()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .email(DEFAULT_EMAIL)
            .senha(DEFAULT_SENHA)
            .cnpj(DEFAULT_CNPJ)
            .cpf(DEFAULT_CPF)
            .cep(DEFAULT_CEP)
            .endereco(DEFAULT_ENDERECO)
            .numero(DEFAULT_NUMERO)
            .bairro(DEFAULT_BAIRRO)
            .cidade(DEFAULT_CIDADE)
            .estado(DEFAULT_ESTADO)
            .telefone(DEFAULT_TELEFONE)
            .fax(DEFAULT_FAX)
            .celular(DEFAULT_CELULAR)
            .detalhe(DEFAULT_DETALHE)
            .bloqueado(DEFAULT_BLOQUEADO)
            .complemento(DEFAULT_COMPLEMENTO)
            .naoPodeExcluir(DEFAULT_NAO_PODE_EXCLUIR)
            .ultimoAcesso(DEFAULT_ULTIMO_ACESSO)
            .senhaFirebase(DEFAULT_SENHA_FIREBASE)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return usuario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuario createUpdatedEntity(EntityManager em) {
        Usuario usuario = new Usuario()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .cnpj(UPDATED_CNPJ)
            .cpf(UPDATED_CPF)
            .cep(UPDATED_CEP)
            .endereco(UPDATED_ENDERECO)
            .numero(UPDATED_NUMERO)
            .bairro(UPDATED_BAIRRO)
            .cidade(UPDATED_CIDADE)
            .estado(UPDATED_ESTADO)
            .telefone(UPDATED_TELEFONE)
            .fax(UPDATED_FAX)
            .celular(UPDATED_CELULAR)
            .detalhe(UPDATED_DETALHE)
            .bloqueado(UPDATED_BLOQUEADO)
            .complemento(UPDATED_COMPLEMENTO)
            .naoPodeExcluir(UPDATED_NAO_PODE_EXCLUIR)
            .ultimoAcesso(UPDATED_ULTIMO_ACESSO)
            .senhaFirebase(UPDATED_SENHA_FIREBASE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return usuario;
    }

    @BeforeEach
    public void initTest() {
        usuario = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsuario() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();
        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);
        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate + 1);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUsuario.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testUsuario.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUsuario.getSenha()).isEqualTo(DEFAULT_SENHA);
        assertThat(testUsuario.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testUsuario.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testUsuario.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testUsuario.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testUsuario.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testUsuario.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testUsuario.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testUsuario.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testUsuario.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testUsuario.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testUsuario.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testUsuario.getDetalhe()).isEqualTo(DEFAULT_DETALHE);
        assertThat(testUsuario.isBloqueado()).isEqualTo(DEFAULT_BLOQUEADO);
        assertThat(testUsuario.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testUsuario.isNaoPodeExcluir()).isEqualTo(DEFAULT_NAO_PODE_EXCLUIR);
        assertThat(testUsuario.getUltimoAcesso()).isEqualTo(DEFAULT_ULTIMO_ACESSO);
        assertThat(testUsuario.getSenhaFirebase()).isEqualTo(DEFAULT_SENHA_FIREBASE);
        assertThat(testUsuario.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testUsuario.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createUsuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

        // Create the Usuario with an existing ID
        usuario.setId(1L);
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setName(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);


        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setDescricao(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);


        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setEmail(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);


        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSenhaIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setSenha(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);


        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUltimoAcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setUltimoAcesso(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);


        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setCreated(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);


        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUsuarios() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList
        restUsuarioMockMvc.perform(get("/api/usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)))
            .andExpect(jsonPath("$.[*].detalhe").value(hasItem(DEFAULT_DETALHE)))
            .andExpect(jsonPath("$.[*].bloqueado").value(hasItem(DEFAULT_BLOQUEADO.booleanValue())))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].naoPodeExcluir").value(hasItem(DEFAULT_NAO_PODE_EXCLUIR.booleanValue())))
            .andExpect(jsonPath("$.[*].ultimoAcesso").value(hasItem(DEFAULT_ULTIMO_ACESSO.toString())))
            .andExpect(jsonPath("$.[*].senhaFirebase").value(hasItem(DEFAULT_SENHA_FIREBASE)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get the usuario
        restUsuarioMockMvc.perform(get("/api/usuarios/{id}", usuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuario.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.senha").value(DEFAULT_SENHA))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR))
            .andExpect(jsonPath("$.detalhe").value(DEFAULT_DETALHE))
            .andExpect(jsonPath("$.bloqueado").value(DEFAULT_BLOQUEADO.booleanValue()))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.naoPodeExcluir").value(DEFAULT_NAO_PODE_EXCLUIR.booleanValue()))
            .andExpect(jsonPath("$.ultimoAcesso").value(DEFAULT_ULTIMO_ACESSO.toString()))
            .andExpect(jsonPath("$.senhaFirebase").value(DEFAULT_SENHA_FIREBASE))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getUsuariosByIdFiltering() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        Long id = usuario.getId();

        defaultUsuarioShouldBeFound("id.equals=" + id);
        defaultUsuarioShouldNotBeFound("id.notEquals=" + id);

        defaultUsuarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUsuarioShouldNotBeFound("id.greaterThan=" + id);

        defaultUsuarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUsuarioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUsuariosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where name equals to DEFAULT_NAME
        defaultUsuarioShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the usuarioList where name equals to UPDATED_NAME
        defaultUsuarioShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where name not equals to DEFAULT_NAME
        defaultUsuarioShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the usuarioList where name not equals to UPDATED_NAME
        defaultUsuarioShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUsuarioShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the usuarioList where name equals to UPDATED_NAME
        defaultUsuarioShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where name is not null
        defaultUsuarioShouldBeFound("name.specified=true");

        // Get all the usuarioList where name is null
        defaultUsuarioShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByNameContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where name contains DEFAULT_NAME
        defaultUsuarioShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the usuarioList where name contains UPDATED_NAME
        defaultUsuarioShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where name does not contain DEFAULT_NAME
        defaultUsuarioShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the usuarioList where name does not contain UPDATED_NAME
        defaultUsuarioShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllUsuariosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where descricao equals to DEFAULT_DESCRICAO
        defaultUsuarioShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the usuarioList where descricao equals to UPDATED_DESCRICAO
        defaultUsuarioShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where descricao not equals to DEFAULT_DESCRICAO
        defaultUsuarioShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the usuarioList where descricao not equals to UPDATED_DESCRICAO
        defaultUsuarioShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultUsuarioShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the usuarioList where descricao equals to UPDATED_DESCRICAO
        defaultUsuarioShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where descricao is not null
        defaultUsuarioShouldBeFound("descricao.specified=true");

        // Get all the usuarioList where descricao is null
        defaultUsuarioShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where descricao contains DEFAULT_DESCRICAO
        defaultUsuarioShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the usuarioList where descricao contains UPDATED_DESCRICAO
        defaultUsuarioShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where descricao does not contain DEFAULT_DESCRICAO
        defaultUsuarioShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the usuarioList where descricao does not contain UPDATED_DESCRICAO
        defaultUsuarioShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllUsuariosByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where email equals to DEFAULT_EMAIL
        defaultUsuarioShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the usuarioList where email equals to UPDATED_EMAIL
        defaultUsuarioShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where email not equals to DEFAULT_EMAIL
        defaultUsuarioShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the usuarioList where email not equals to UPDATED_EMAIL
        defaultUsuarioShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultUsuarioShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the usuarioList where email equals to UPDATED_EMAIL
        defaultUsuarioShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where email is not null
        defaultUsuarioShouldBeFound("email.specified=true");

        // Get all the usuarioList where email is null
        defaultUsuarioShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByEmailContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where email contains DEFAULT_EMAIL
        defaultUsuarioShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the usuarioList where email contains UPDATED_EMAIL
        defaultUsuarioShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where email does not contain DEFAULT_EMAIL
        defaultUsuarioShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the usuarioList where email does not contain UPDATED_EMAIL
        defaultUsuarioShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllUsuariosBySenhaIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where senha equals to DEFAULT_SENHA
        defaultUsuarioShouldBeFound("senha.equals=" + DEFAULT_SENHA);

        // Get all the usuarioList where senha equals to UPDATED_SENHA
        defaultUsuarioShouldNotBeFound("senha.equals=" + UPDATED_SENHA);
    }

    @Test
    @Transactional
    public void getAllUsuariosBySenhaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where senha not equals to DEFAULT_SENHA
        defaultUsuarioShouldNotBeFound("senha.notEquals=" + DEFAULT_SENHA);

        // Get all the usuarioList where senha not equals to UPDATED_SENHA
        defaultUsuarioShouldBeFound("senha.notEquals=" + UPDATED_SENHA);
    }

    @Test
    @Transactional
    public void getAllUsuariosBySenhaIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where senha in DEFAULT_SENHA or UPDATED_SENHA
        defaultUsuarioShouldBeFound("senha.in=" + DEFAULT_SENHA + "," + UPDATED_SENHA);

        // Get all the usuarioList where senha equals to UPDATED_SENHA
        defaultUsuarioShouldNotBeFound("senha.in=" + UPDATED_SENHA);
    }

    @Test
    @Transactional
    public void getAllUsuariosBySenhaIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where senha is not null
        defaultUsuarioShouldBeFound("senha.specified=true");

        // Get all the usuarioList where senha is null
        defaultUsuarioShouldNotBeFound("senha.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosBySenhaContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where senha contains DEFAULT_SENHA
        defaultUsuarioShouldBeFound("senha.contains=" + DEFAULT_SENHA);

        // Get all the usuarioList where senha contains UPDATED_SENHA
        defaultUsuarioShouldNotBeFound("senha.contains=" + UPDATED_SENHA);
    }

    @Test
    @Transactional
    public void getAllUsuariosBySenhaNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where senha does not contain DEFAULT_SENHA
        defaultUsuarioShouldNotBeFound("senha.doesNotContain=" + DEFAULT_SENHA);

        // Get all the usuarioList where senha does not contain UPDATED_SENHA
        defaultUsuarioShouldBeFound("senha.doesNotContain=" + UPDATED_SENHA);
    }


    @Test
    @Transactional
    public void getAllUsuariosByCnpjIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cnpj equals to DEFAULT_CNPJ
        defaultUsuarioShouldBeFound("cnpj.equals=" + DEFAULT_CNPJ);

        // Get all the usuarioList where cnpj equals to UPDATED_CNPJ
        defaultUsuarioShouldNotBeFound("cnpj.equals=" + UPDATED_CNPJ);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCnpjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cnpj not equals to DEFAULT_CNPJ
        defaultUsuarioShouldNotBeFound("cnpj.notEquals=" + DEFAULT_CNPJ);

        // Get all the usuarioList where cnpj not equals to UPDATED_CNPJ
        defaultUsuarioShouldBeFound("cnpj.notEquals=" + UPDATED_CNPJ);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCnpjIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cnpj in DEFAULT_CNPJ or UPDATED_CNPJ
        defaultUsuarioShouldBeFound("cnpj.in=" + DEFAULT_CNPJ + "," + UPDATED_CNPJ);

        // Get all the usuarioList where cnpj equals to UPDATED_CNPJ
        defaultUsuarioShouldNotBeFound("cnpj.in=" + UPDATED_CNPJ);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCnpjIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cnpj is not null
        defaultUsuarioShouldBeFound("cnpj.specified=true");

        // Get all the usuarioList where cnpj is null
        defaultUsuarioShouldNotBeFound("cnpj.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByCnpjContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cnpj contains DEFAULT_CNPJ
        defaultUsuarioShouldBeFound("cnpj.contains=" + DEFAULT_CNPJ);

        // Get all the usuarioList where cnpj contains UPDATED_CNPJ
        defaultUsuarioShouldNotBeFound("cnpj.contains=" + UPDATED_CNPJ);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCnpjNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cnpj does not contain DEFAULT_CNPJ
        defaultUsuarioShouldNotBeFound("cnpj.doesNotContain=" + DEFAULT_CNPJ);

        // Get all the usuarioList where cnpj does not contain UPDATED_CNPJ
        defaultUsuarioShouldBeFound("cnpj.doesNotContain=" + UPDATED_CNPJ);
    }


    @Test
    @Transactional
    public void getAllUsuariosByCpfIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cpf equals to DEFAULT_CPF
        defaultUsuarioShouldBeFound("cpf.equals=" + DEFAULT_CPF);

        // Get all the usuarioList where cpf equals to UPDATED_CPF
        defaultUsuarioShouldNotBeFound("cpf.equals=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCpfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cpf not equals to DEFAULT_CPF
        defaultUsuarioShouldNotBeFound("cpf.notEquals=" + DEFAULT_CPF);

        // Get all the usuarioList where cpf not equals to UPDATED_CPF
        defaultUsuarioShouldBeFound("cpf.notEquals=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCpfIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cpf in DEFAULT_CPF or UPDATED_CPF
        defaultUsuarioShouldBeFound("cpf.in=" + DEFAULT_CPF + "," + UPDATED_CPF);

        // Get all the usuarioList where cpf equals to UPDATED_CPF
        defaultUsuarioShouldNotBeFound("cpf.in=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCpfIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cpf is not null
        defaultUsuarioShouldBeFound("cpf.specified=true");

        // Get all the usuarioList where cpf is null
        defaultUsuarioShouldNotBeFound("cpf.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByCpfContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cpf contains DEFAULT_CPF
        defaultUsuarioShouldBeFound("cpf.contains=" + DEFAULT_CPF);

        // Get all the usuarioList where cpf contains UPDATED_CPF
        defaultUsuarioShouldNotBeFound("cpf.contains=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCpfNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cpf does not contain DEFAULT_CPF
        defaultUsuarioShouldNotBeFound("cpf.doesNotContain=" + DEFAULT_CPF);

        // Get all the usuarioList where cpf does not contain UPDATED_CPF
        defaultUsuarioShouldBeFound("cpf.doesNotContain=" + UPDATED_CPF);
    }


    @Test
    @Transactional
    public void getAllUsuariosByCepIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cep equals to DEFAULT_CEP
        defaultUsuarioShouldBeFound("cep.equals=" + DEFAULT_CEP);

        // Get all the usuarioList where cep equals to UPDATED_CEP
        defaultUsuarioShouldNotBeFound("cep.equals=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCepIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cep not equals to DEFAULT_CEP
        defaultUsuarioShouldNotBeFound("cep.notEquals=" + DEFAULT_CEP);

        // Get all the usuarioList where cep not equals to UPDATED_CEP
        defaultUsuarioShouldBeFound("cep.notEquals=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCepIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cep in DEFAULT_CEP or UPDATED_CEP
        defaultUsuarioShouldBeFound("cep.in=" + DEFAULT_CEP + "," + UPDATED_CEP);

        // Get all the usuarioList where cep equals to UPDATED_CEP
        defaultUsuarioShouldNotBeFound("cep.in=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCepIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cep is not null
        defaultUsuarioShouldBeFound("cep.specified=true");

        // Get all the usuarioList where cep is null
        defaultUsuarioShouldNotBeFound("cep.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByCepContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cep contains DEFAULT_CEP
        defaultUsuarioShouldBeFound("cep.contains=" + DEFAULT_CEP);

        // Get all the usuarioList where cep contains UPDATED_CEP
        defaultUsuarioShouldNotBeFound("cep.contains=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCepNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cep does not contain DEFAULT_CEP
        defaultUsuarioShouldNotBeFound("cep.doesNotContain=" + DEFAULT_CEP);

        // Get all the usuarioList where cep does not contain UPDATED_CEP
        defaultUsuarioShouldBeFound("cep.doesNotContain=" + UPDATED_CEP);
    }


    @Test
    @Transactional
    public void getAllUsuariosByEnderecoIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where endereco equals to DEFAULT_ENDERECO
        defaultUsuarioShouldBeFound("endereco.equals=" + DEFAULT_ENDERECO);

        // Get all the usuarioList where endereco equals to UPDATED_ENDERECO
        defaultUsuarioShouldNotBeFound("endereco.equals=" + UPDATED_ENDERECO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEnderecoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where endereco not equals to DEFAULT_ENDERECO
        defaultUsuarioShouldNotBeFound("endereco.notEquals=" + DEFAULT_ENDERECO);

        // Get all the usuarioList where endereco not equals to UPDATED_ENDERECO
        defaultUsuarioShouldBeFound("endereco.notEquals=" + UPDATED_ENDERECO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEnderecoIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where endereco in DEFAULT_ENDERECO or UPDATED_ENDERECO
        defaultUsuarioShouldBeFound("endereco.in=" + DEFAULT_ENDERECO + "," + UPDATED_ENDERECO);

        // Get all the usuarioList where endereco equals to UPDATED_ENDERECO
        defaultUsuarioShouldNotBeFound("endereco.in=" + UPDATED_ENDERECO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEnderecoIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where endereco is not null
        defaultUsuarioShouldBeFound("endereco.specified=true");

        // Get all the usuarioList where endereco is null
        defaultUsuarioShouldNotBeFound("endereco.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByEnderecoContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where endereco contains DEFAULT_ENDERECO
        defaultUsuarioShouldBeFound("endereco.contains=" + DEFAULT_ENDERECO);

        // Get all the usuarioList where endereco contains UPDATED_ENDERECO
        defaultUsuarioShouldNotBeFound("endereco.contains=" + UPDATED_ENDERECO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEnderecoNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where endereco does not contain DEFAULT_ENDERECO
        defaultUsuarioShouldNotBeFound("endereco.doesNotContain=" + DEFAULT_ENDERECO);

        // Get all the usuarioList where endereco does not contain UPDATED_ENDERECO
        defaultUsuarioShouldBeFound("endereco.doesNotContain=" + UPDATED_ENDERECO);
    }


    @Test
    @Transactional
    public void getAllUsuariosByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where numero equals to DEFAULT_NUMERO
        defaultUsuarioShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the usuarioList where numero equals to UPDATED_NUMERO
        defaultUsuarioShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNumeroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where numero not equals to DEFAULT_NUMERO
        defaultUsuarioShouldNotBeFound("numero.notEquals=" + DEFAULT_NUMERO);

        // Get all the usuarioList where numero not equals to UPDATED_NUMERO
        defaultUsuarioShouldBeFound("numero.notEquals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultUsuarioShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the usuarioList where numero equals to UPDATED_NUMERO
        defaultUsuarioShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where numero is not null
        defaultUsuarioShouldBeFound("numero.specified=true");

        // Get all the usuarioList where numero is null
        defaultUsuarioShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByNumeroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where numero is greater than or equal to DEFAULT_NUMERO
        defaultUsuarioShouldBeFound("numero.greaterThanOrEqual=" + DEFAULT_NUMERO);

        // Get all the usuarioList where numero is greater than or equal to UPDATED_NUMERO
        defaultUsuarioShouldNotBeFound("numero.greaterThanOrEqual=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNumeroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where numero is less than or equal to DEFAULT_NUMERO
        defaultUsuarioShouldBeFound("numero.lessThanOrEqual=" + DEFAULT_NUMERO);

        // Get all the usuarioList where numero is less than or equal to SMALLER_NUMERO
        defaultUsuarioShouldNotBeFound("numero.lessThanOrEqual=" + SMALLER_NUMERO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNumeroIsLessThanSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where numero is less than DEFAULT_NUMERO
        defaultUsuarioShouldNotBeFound("numero.lessThan=" + DEFAULT_NUMERO);

        // Get all the usuarioList where numero is less than UPDATED_NUMERO
        defaultUsuarioShouldBeFound("numero.lessThan=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNumeroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where numero is greater than DEFAULT_NUMERO
        defaultUsuarioShouldNotBeFound("numero.greaterThan=" + DEFAULT_NUMERO);

        // Get all the usuarioList where numero is greater than SMALLER_NUMERO
        defaultUsuarioShouldBeFound("numero.greaterThan=" + SMALLER_NUMERO);
    }


    @Test
    @Transactional
    public void getAllUsuariosByBairroIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where bairro equals to DEFAULT_BAIRRO
        defaultUsuarioShouldBeFound("bairro.equals=" + DEFAULT_BAIRRO);

        // Get all the usuarioList where bairro equals to UPDATED_BAIRRO
        defaultUsuarioShouldNotBeFound("bairro.equals=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByBairroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where bairro not equals to DEFAULT_BAIRRO
        defaultUsuarioShouldNotBeFound("bairro.notEquals=" + DEFAULT_BAIRRO);

        // Get all the usuarioList where bairro not equals to UPDATED_BAIRRO
        defaultUsuarioShouldBeFound("bairro.notEquals=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByBairroIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where bairro in DEFAULT_BAIRRO or UPDATED_BAIRRO
        defaultUsuarioShouldBeFound("bairro.in=" + DEFAULT_BAIRRO + "," + UPDATED_BAIRRO);

        // Get all the usuarioList where bairro equals to UPDATED_BAIRRO
        defaultUsuarioShouldNotBeFound("bairro.in=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByBairroIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where bairro is not null
        defaultUsuarioShouldBeFound("bairro.specified=true");

        // Get all the usuarioList where bairro is null
        defaultUsuarioShouldNotBeFound("bairro.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByBairroContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where bairro contains DEFAULT_BAIRRO
        defaultUsuarioShouldBeFound("bairro.contains=" + DEFAULT_BAIRRO);

        // Get all the usuarioList where bairro contains UPDATED_BAIRRO
        defaultUsuarioShouldNotBeFound("bairro.contains=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByBairroNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where bairro does not contain DEFAULT_BAIRRO
        defaultUsuarioShouldNotBeFound("bairro.doesNotContain=" + DEFAULT_BAIRRO);

        // Get all the usuarioList where bairro does not contain UPDATED_BAIRRO
        defaultUsuarioShouldBeFound("bairro.doesNotContain=" + UPDATED_BAIRRO);
    }


    @Test
    @Transactional
    public void getAllUsuariosByCidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cidade equals to DEFAULT_CIDADE
        defaultUsuarioShouldBeFound("cidade.equals=" + DEFAULT_CIDADE);

        // Get all the usuarioList where cidade equals to UPDATED_CIDADE
        defaultUsuarioShouldNotBeFound("cidade.equals=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cidade not equals to DEFAULT_CIDADE
        defaultUsuarioShouldNotBeFound("cidade.notEquals=" + DEFAULT_CIDADE);

        // Get all the usuarioList where cidade not equals to UPDATED_CIDADE
        defaultUsuarioShouldBeFound("cidade.notEquals=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCidadeIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cidade in DEFAULT_CIDADE or UPDATED_CIDADE
        defaultUsuarioShouldBeFound("cidade.in=" + DEFAULT_CIDADE + "," + UPDATED_CIDADE);

        // Get all the usuarioList where cidade equals to UPDATED_CIDADE
        defaultUsuarioShouldNotBeFound("cidade.in=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cidade is not null
        defaultUsuarioShouldBeFound("cidade.specified=true");

        // Get all the usuarioList where cidade is null
        defaultUsuarioShouldNotBeFound("cidade.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByCidadeContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cidade contains DEFAULT_CIDADE
        defaultUsuarioShouldBeFound("cidade.contains=" + DEFAULT_CIDADE);

        // Get all the usuarioList where cidade contains UPDATED_CIDADE
        defaultUsuarioShouldNotBeFound("cidade.contains=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCidadeNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where cidade does not contain DEFAULT_CIDADE
        defaultUsuarioShouldNotBeFound("cidade.doesNotContain=" + DEFAULT_CIDADE);

        // Get all the usuarioList where cidade does not contain UPDATED_CIDADE
        defaultUsuarioShouldBeFound("cidade.doesNotContain=" + UPDATED_CIDADE);
    }


    @Test
    @Transactional
    public void getAllUsuariosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where estado equals to DEFAULT_ESTADO
        defaultUsuarioShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the usuarioList where estado equals to UPDATED_ESTADO
        defaultUsuarioShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEstadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where estado not equals to DEFAULT_ESTADO
        defaultUsuarioShouldNotBeFound("estado.notEquals=" + DEFAULT_ESTADO);

        // Get all the usuarioList where estado not equals to UPDATED_ESTADO
        defaultUsuarioShouldBeFound("estado.notEquals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultUsuarioShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the usuarioList where estado equals to UPDATED_ESTADO
        defaultUsuarioShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where estado is not null
        defaultUsuarioShouldBeFound("estado.specified=true");

        // Get all the usuarioList where estado is null
        defaultUsuarioShouldNotBeFound("estado.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByEstadoContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where estado contains DEFAULT_ESTADO
        defaultUsuarioShouldBeFound("estado.contains=" + DEFAULT_ESTADO);

        // Get all the usuarioList where estado contains UPDATED_ESTADO
        defaultUsuarioShouldNotBeFound("estado.contains=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEstadoNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where estado does not contain DEFAULT_ESTADO
        defaultUsuarioShouldNotBeFound("estado.doesNotContain=" + DEFAULT_ESTADO);

        // Get all the usuarioList where estado does not contain UPDATED_ESTADO
        defaultUsuarioShouldBeFound("estado.doesNotContain=" + UPDATED_ESTADO);
    }


    @Test
    @Transactional
    public void getAllUsuariosByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where telefone equals to DEFAULT_TELEFONE
        defaultUsuarioShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the usuarioList where telefone equals to UPDATED_TELEFONE
        defaultUsuarioShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllUsuariosByTelefoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where telefone not equals to DEFAULT_TELEFONE
        defaultUsuarioShouldNotBeFound("telefone.notEquals=" + DEFAULT_TELEFONE);

        // Get all the usuarioList where telefone not equals to UPDATED_TELEFONE
        defaultUsuarioShouldBeFound("telefone.notEquals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllUsuariosByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultUsuarioShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the usuarioList where telefone equals to UPDATED_TELEFONE
        defaultUsuarioShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllUsuariosByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where telefone is not null
        defaultUsuarioShouldBeFound("telefone.specified=true");

        // Get all the usuarioList where telefone is null
        defaultUsuarioShouldNotBeFound("telefone.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByTelefoneContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where telefone contains DEFAULT_TELEFONE
        defaultUsuarioShouldBeFound("telefone.contains=" + DEFAULT_TELEFONE);

        // Get all the usuarioList where telefone contains UPDATED_TELEFONE
        defaultUsuarioShouldNotBeFound("telefone.contains=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllUsuariosByTelefoneNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where telefone does not contain DEFAULT_TELEFONE
        defaultUsuarioShouldNotBeFound("telefone.doesNotContain=" + DEFAULT_TELEFONE);

        // Get all the usuarioList where telefone does not contain UPDATED_TELEFONE
        defaultUsuarioShouldBeFound("telefone.doesNotContain=" + UPDATED_TELEFONE);
    }


    @Test
    @Transactional
    public void getAllUsuariosByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where fax equals to DEFAULT_FAX
        defaultUsuarioShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the usuarioList where fax equals to UPDATED_FAX
        defaultUsuarioShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllUsuariosByFaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where fax not equals to DEFAULT_FAX
        defaultUsuarioShouldNotBeFound("fax.notEquals=" + DEFAULT_FAX);

        // Get all the usuarioList where fax not equals to UPDATED_FAX
        defaultUsuarioShouldBeFound("fax.notEquals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllUsuariosByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultUsuarioShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the usuarioList where fax equals to UPDATED_FAX
        defaultUsuarioShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllUsuariosByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where fax is not null
        defaultUsuarioShouldBeFound("fax.specified=true");

        // Get all the usuarioList where fax is null
        defaultUsuarioShouldNotBeFound("fax.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByFaxContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where fax contains DEFAULT_FAX
        defaultUsuarioShouldBeFound("fax.contains=" + DEFAULT_FAX);

        // Get all the usuarioList where fax contains UPDATED_FAX
        defaultUsuarioShouldNotBeFound("fax.contains=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllUsuariosByFaxNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where fax does not contain DEFAULT_FAX
        defaultUsuarioShouldNotBeFound("fax.doesNotContain=" + DEFAULT_FAX);

        // Get all the usuarioList where fax does not contain UPDATED_FAX
        defaultUsuarioShouldBeFound("fax.doesNotContain=" + UPDATED_FAX);
    }


    @Test
    @Transactional
    public void getAllUsuariosByCelularIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where celular equals to DEFAULT_CELULAR
        defaultUsuarioShouldBeFound("celular.equals=" + DEFAULT_CELULAR);

        // Get all the usuarioList where celular equals to UPDATED_CELULAR
        defaultUsuarioShouldNotBeFound("celular.equals=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCelularIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where celular not equals to DEFAULT_CELULAR
        defaultUsuarioShouldNotBeFound("celular.notEquals=" + DEFAULT_CELULAR);

        // Get all the usuarioList where celular not equals to UPDATED_CELULAR
        defaultUsuarioShouldBeFound("celular.notEquals=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCelularIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where celular in DEFAULT_CELULAR or UPDATED_CELULAR
        defaultUsuarioShouldBeFound("celular.in=" + DEFAULT_CELULAR + "," + UPDATED_CELULAR);

        // Get all the usuarioList where celular equals to UPDATED_CELULAR
        defaultUsuarioShouldNotBeFound("celular.in=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCelularIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where celular is not null
        defaultUsuarioShouldBeFound("celular.specified=true");

        // Get all the usuarioList where celular is null
        defaultUsuarioShouldNotBeFound("celular.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByCelularContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where celular contains DEFAULT_CELULAR
        defaultUsuarioShouldBeFound("celular.contains=" + DEFAULT_CELULAR);

        // Get all the usuarioList where celular contains UPDATED_CELULAR
        defaultUsuarioShouldNotBeFound("celular.contains=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCelularNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where celular does not contain DEFAULT_CELULAR
        defaultUsuarioShouldNotBeFound("celular.doesNotContain=" + DEFAULT_CELULAR);

        // Get all the usuarioList where celular does not contain UPDATED_CELULAR
        defaultUsuarioShouldBeFound("celular.doesNotContain=" + UPDATED_CELULAR);
    }


    @Test
    @Transactional
    public void getAllUsuariosByDetalheIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where detalhe equals to DEFAULT_DETALHE
        defaultUsuarioShouldBeFound("detalhe.equals=" + DEFAULT_DETALHE);

        // Get all the usuarioList where detalhe equals to UPDATED_DETALHE
        defaultUsuarioShouldNotBeFound("detalhe.equals=" + UPDATED_DETALHE);
    }

    @Test
    @Transactional
    public void getAllUsuariosByDetalheIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where detalhe not equals to DEFAULT_DETALHE
        defaultUsuarioShouldNotBeFound("detalhe.notEquals=" + DEFAULT_DETALHE);

        // Get all the usuarioList where detalhe not equals to UPDATED_DETALHE
        defaultUsuarioShouldBeFound("detalhe.notEquals=" + UPDATED_DETALHE);
    }

    @Test
    @Transactional
    public void getAllUsuariosByDetalheIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where detalhe in DEFAULT_DETALHE or UPDATED_DETALHE
        defaultUsuarioShouldBeFound("detalhe.in=" + DEFAULT_DETALHE + "," + UPDATED_DETALHE);

        // Get all the usuarioList where detalhe equals to UPDATED_DETALHE
        defaultUsuarioShouldNotBeFound("detalhe.in=" + UPDATED_DETALHE);
    }

    @Test
    @Transactional
    public void getAllUsuariosByDetalheIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where detalhe is not null
        defaultUsuarioShouldBeFound("detalhe.specified=true");

        // Get all the usuarioList where detalhe is null
        defaultUsuarioShouldNotBeFound("detalhe.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByDetalheContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where detalhe contains DEFAULT_DETALHE
        defaultUsuarioShouldBeFound("detalhe.contains=" + DEFAULT_DETALHE);

        // Get all the usuarioList where detalhe contains UPDATED_DETALHE
        defaultUsuarioShouldNotBeFound("detalhe.contains=" + UPDATED_DETALHE);
    }

    @Test
    @Transactional
    public void getAllUsuariosByDetalheNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where detalhe does not contain DEFAULT_DETALHE
        defaultUsuarioShouldNotBeFound("detalhe.doesNotContain=" + DEFAULT_DETALHE);

        // Get all the usuarioList where detalhe does not contain UPDATED_DETALHE
        defaultUsuarioShouldBeFound("detalhe.doesNotContain=" + UPDATED_DETALHE);
    }


    @Test
    @Transactional
    public void getAllUsuariosByBloqueadoIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where bloqueado equals to DEFAULT_BLOQUEADO
        defaultUsuarioShouldBeFound("bloqueado.equals=" + DEFAULT_BLOQUEADO);

        // Get all the usuarioList where bloqueado equals to UPDATED_BLOQUEADO
        defaultUsuarioShouldNotBeFound("bloqueado.equals=" + UPDATED_BLOQUEADO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByBloqueadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where bloqueado not equals to DEFAULT_BLOQUEADO
        defaultUsuarioShouldNotBeFound("bloqueado.notEquals=" + DEFAULT_BLOQUEADO);

        // Get all the usuarioList where bloqueado not equals to UPDATED_BLOQUEADO
        defaultUsuarioShouldBeFound("bloqueado.notEquals=" + UPDATED_BLOQUEADO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByBloqueadoIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where bloqueado in DEFAULT_BLOQUEADO or UPDATED_BLOQUEADO
        defaultUsuarioShouldBeFound("bloqueado.in=" + DEFAULT_BLOQUEADO + "," + UPDATED_BLOQUEADO);

        // Get all the usuarioList where bloqueado equals to UPDATED_BLOQUEADO
        defaultUsuarioShouldNotBeFound("bloqueado.in=" + UPDATED_BLOQUEADO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByBloqueadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where bloqueado is not null
        defaultUsuarioShouldBeFound("bloqueado.specified=true");

        // Get all the usuarioList where bloqueado is null
        defaultUsuarioShouldNotBeFound("bloqueado.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByComplementoIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where complemento equals to DEFAULT_COMPLEMENTO
        defaultUsuarioShouldBeFound("complemento.equals=" + DEFAULT_COMPLEMENTO);

        // Get all the usuarioList where complemento equals to UPDATED_COMPLEMENTO
        defaultUsuarioShouldNotBeFound("complemento.equals=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByComplementoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where complemento not equals to DEFAULT_COMPLEMENTO
        defaultUsuarioShouldNotBeFound("complemento.notEquals=" + DEFAULT_COMPLEMENTO);

        // Get all the usuarioList where complemento not equals to UPDATED_COMPLEMENTO
        defaultUsuarioShouldBeFound("complemento.notEquals=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByComplementoIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where complemento in DEFAULT_COMPLEMENTO or UPDATED_COMPLEMENTO
        defaultUsuarioShouldBeFound("complemento.in=" + DEFAULT_COMPLEMENTO + "," + UPDATED_COMPLEMENTO);

        // Get all the usuarioList where complemento equals to UPDATED_COMPLEMENTO
        defaultUsuarioShouldNotBeFound("complemento.in=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByComplementoIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where complemento is not null
        defaultUsuarioShouldBeFound("complemento.specified=true");

        // Get all the usuarioList where complemento is null
        defaultUsuarioShouldNotBeFound("complemento.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByComplementoContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where complemento contains DEFAULT_COMPLEMENTO
        defaultUsuarioShouldBeFound("complemento.contains=" + DEFAULT_COMPLEMENTO);

        // Get all the usuarioList where complemento contains UPDATED_COMPLEMENTO
        defaultUsuarioShouldNotBeFound("complemento.contains=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByComplementoNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where complemento does not contain DEFAULT_COMPLEMENTO
        defaultUsuarioShouldNotBeFound("complemento.doesNotContain=" + DEFAULT_COMPLEMENTO);

        // Get all the usuarioList where complemento does not contain UPDATED_COMPLEMENTO
        defaultUsuarioShouldBeFound("complemento.doesNotContain=" + UPDATED_COMPLEMENTO);
    }


    @Test
    @Transactional
    public void getAllUsuariosByNaoPodeExcluirIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where naoPodeExcluir equals to DEFAULT_NAO_PODE_EXCLUIR
        defaultUsuarioShouldBeFound("naoPodeExcluir.equals=" + DEFAULT_NAO_PODE_EXCLUIR);

        // Get all the usuarioList where naoPodeExcluir equals to UPDATED_NAO_PODE_EXCLUIR
        defaultUsuarioShouldNotBeFound("naoPodeExcluir.equals=" + UPDATED_NAO_PODE_EXCLUIR);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNaoPodeExcluirIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where naoPodeExcluir not equals to DEFAULT_NAO_PODE_EXCLUIR
        defaultUsuarioShouldNotBeFound("naoPodeExcluir.notEquals=" + DEFAULT_NAO_PODE_EXCLUIR);

        // Get all the usuarioList where naoPodeExcluir not equals to UPDATED_NAO_PODE_EXCLUIR
        defaultUsuarioShouldBeFound("naoPodeExcluir.notEquals=" + UPDATED_NAO_PODE_EXCLUIR);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNaoPodeExcluirIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where naoPodeExcluir in DEFAULT_NAO_PODE_EXCLUIR or UPDATED_NAO_PODE_EXCLUIR
        defaultUsuarioShouldBeFound("naoPodeExcluir.in=" + DEFAULT_NAO_PODE_EXCLUIR + "," + UPDATED_NAO_PODE_EXCLUIR);

        // Get all the usuarioList where naoPodeExcluir equals to UPDATED_NAO_PODE_EXCLUIR
        defaultUsuarioShouldNotBeFound("naoPodeExcluir.in=" + UPDATED_NAO_PODE_EXCLUIR);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNaoPodeExcluirIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where naoPodeExcluir is not null
        defaultUsuarioShouldBeFound("naoPodeExcluir.specified=true");

        // Get all the usuarioList where naoPodeExcluir is null
        defaultUsuarioShouldNotBeFound("naoPodeExcluir.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByUltimoAcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where ultimoAcesso equals to DEFAULT_ULTIMO_ACESSO
        defaultUsuarioShouldBeFound("ultimoAcesso.equals=" + DEFAULT_ULTIMO_ACESSO);

        // Get all the usuarioList where ultimoAcesso equals to UPDATED_ULTIMO_ACESSO
        defaultUsuarioShouldNotBeFound("ultimoAcesso.equals=" + UPDATED_ULTIMO_ACESSO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByUltimoAcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where ultimoAcesso not equals to DEFAULT_ULTIMO_ACESSO
        defaultUsuarioShouldNotBeFound("ultimoAcesso.notEquals=" + DEFAULT_ULTIMO_ACESSO);

        // Get all the usuarioList where ultimoAcesso not equals to UPDATED_ULTIMO_ACESSO
        defaultUsuarioShouldBeFound("ultimoAcesso.notEquals=" + UPDATED_ULTIMO_ACESSO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByUltimoAcessoIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where ultimoAcesso in DEFAULT_ULTIMO_ACESSO or UPDATED_ULTIMO_ACESSO
        defaultUsuarioShouldBeFound("ultimoAcesso.in=" + DEFAULT_ULTIMO_ACESSO + "," + UPDATED_ULTIMO_ACESSO);

        // Get all the usuarioList where ultimoAcesso equals to UPDATED_ULTIMO_ACESSO
        defaultUsuarioShouldNotBeFound("ultimoAcesso.in=" + UPDATED_ULTIMO_ACESSO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByUltimoAcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where ultimoAcesso is not null
        defaultUsuarioShouldBeFound("ultimoAcesso.specified=true");

        // Get all the usuarioList where ultimoAcesso is null
        defaultUsuarioShouldNotBeFound("ultimoAcesso.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosBySenhaFirebaseIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where senhaFirebase equals to DEFAULT_SENHA_FIREBASE
        defaultUsuarioShouldBeFound("senhaFirebase.equals=" + DEFAULT_SENHA_FIREBASE);

        // Get all the usuarioList where senhaFirebase equals to UPDATED_SENHA_FIREBASE
        defaultUsuarioShouldNotBeFound("senhaFirebase.equals=" + UPDATED_SENHA_FIREBASE);
    }

    @Test
    @Transactional
    public void getAllUsuariosBySenhaFirebaseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where senhaFirebase not equals to DEFAULT_SENHA_FIREBASE
        defaultUsuarioShouldNotBeFound("senhaFirebase.notEquals=" + DEFAULT_SENHA_FIREBASE);

        // Get all the usuarioList where senhaFirebase not equals to UPDATED_SENHA_FIREBASE
        defaultUsuarioShouldBeFound("senhaFirebase.notEquals=" + UPDATED_SENHA_FIREBASE);
    }

    @Test
    @Transactional
    public void getAllUsuariosBySenhaFirebaseIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where senhaFirebase in DEFAULT_SENHA_FIREBASE or UPDATED_SENHA_FIREBASE
        defaultUsuarioShouldBeFound("senhaFirebase.in=" + DEFAULT_SENHA_FIREBASE + "," + UPDATED_SENHA_FIREBASE);

        // Get all the usuarioList where senhaFirebase equals to UPDATED_SENHA_FIREBASE
        defaultUsuarioShouldNotBeFound("senhaFirebase.in=" + UPDATED_SENHA_FIREBASE);
    }

    @Test
    @Transactional
    public void getAllUsuariosBySenhaFirebaseIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where senhaFirebase is not null
        defaultUsuarioShouldBeFound("senhaFirebase.specified=true");

        // Get all the usuarioList where senhaFirebase is null
        defaultUsuarioShouldNotBeFound("senhaFirebase.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosBySenhaFirebaseContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where senhaFirebase contains DEFAULT_SENHA_FIREBASE
        defaultUsuarioShouldBeFound("senhaFirebase.contains=" + DEFAULT_SENHA_FIREBASE);

        // Get all the usuarioList where senhaFirebase contains UPDATED_SENHA_FIREBASE
        defaultUsuarioShouldNotBeFound("senhaFirebase.contains=" + UPDATED_SENHA_FIREBASE);
    }

    @Test
    @Transactional
    public void getAllUsuariosBySenhaFirebaseNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where senhaFirebase does not contain DEFAULT_SENHA_FIREBASE
        defaultUsuarioShouldNotBeFound("senhaFirebase.doesNotContain=" + DEFAULT_SENHA_FIREBASE);

        // Get all the usuarioList where senhaFirebase does not contain UPDATED_SENHA_FIREBASE
        defaultUsuarioShouldBeFound("senhaFirebase.doesNotContain=" + UPDATED_SENHA_FIREBASE);
    }


    @Test
    @Transactional
    public void getAllUsuariosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where created equals to DEFAULT_CREATED
        defaultUsuarioShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the usuarioList where created equals to UPDATED_CREATED
        defaultUsuarioShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where created not equals to DEFAULT_CREATED
        defaultUsuarioShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the usuarioList where created not equals to UPDATED_CREATED
        defaultUsuarioShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultUsuarioShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the usuarioList where created equals to UPDATED_CREATED
        defaultUsuarioShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where created is not null
        defaultUsuarioShouldBeFound("created.specified=true");

        // Get all the usuarioList where created is null
        defaultUsuarioShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where updated equals to DEFAULT_UPDATED
        defaultUsuarioShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the usuarioList where updated equals to UPDATED_UPDATED
        defaultUsuarioShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllUsuariosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where updated not equals to DEFAULT_UPDATED
        defaultUsuarioShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the usuarioList where updated not equals to UPDATED_UPDATED
        defaultUsuarioShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllUsuariosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultUsuarioShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the usuarioList where updated equals to UPDATED_UPDATED
        defaultUsuarioShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllUsuariosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where updated is not null
        defaultUsuarioShouldBeFound("updated.specified=true");

        // Get all the usuarioList where updated is null
        defaultUsuarioShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUsuarioShouldBeFound(String filter) throws Exception {
        restUsuarioMockMvc.perform(get("/api/usuarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)))
            .andExpect(jsonPath("$.[*].detalhe").value(hasItem(DEFAULT_DETALHE)))
            .andExpect(jsonPath("$.[*].bloqueado").value(hasItem(DEFAULT_BLOQUEADO.booleanValue())))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].naoPodeExcluir").value(hasItem(DEFAULT_NAO_PODE_EXCLUIR.booleanValue())))
            .andExpect(jsonPath("$.[*].ultimoAcesso").value(hasItem(DEFAULT_ULTIMO_ACESSO.toString())))
            .andExpect(jsonPath("$.[*].senhaFirebase").value(hasItem(DEFAULT_SENHA_FIREBASE)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restUsuarioMockMvc.perform(get("/api/usuarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUsuarioShouldNotBeFound(String filter) throws Exception {
        restUsuarioMockMvc.perform(get("/api/usuarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUsuarioMockMvc.perform(get("/api/usuarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUsuario() throws Exception {
        // Get the usuario
        restUsuarioMockMvc.perform(get("/api/usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Update the usuario
        Usuario updatedUsuario = usuarioRepository.findById(usuario.getId()).get();
        // Disconnect from session so that the updates on updatedUsuario are not directly saved in db
        em.detach(updatedUsuario);
        updatedUsuario
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .cnpj(UPDATED_CNPJ)
            .cpf(UPDATED_CPF)
            .cep(UPDATED_CEP)
            .endereco(UPDATED_ENDERECO)
            .numero(UPDATED_NUMERO)
            .bairro(UPDATED_BAIRRO)
            .cidade(UPDATED_CIDADE)
            .estado(UPDATED_ESTADO)
            .telefone(UPDATED_TELEFONE)
            .fax(UPDATED_FAX)
            .celular(UPDATED_CELULAR)
            .detalhe(UPDATED_DETALHE)
            .bloqueado(UPDATED_BLOQUEADO)
            .complemento(UPDATED_COMPLEMENTO)
            .naoPodeExcluir(UPDATED_NAO_PODE_EXCLUIR)
            .ultimoAcesso(UPDATED_ULTIMO_ACESSO)
            .senhaFirebase(UPDATED_SENHA_FIREBASE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(updatedUsuario);

        restUsuarioMockMvc.perform(put("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUsuario.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testUsuario.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUsuario.getSenha()).isEqualTo(UPDATED_SENHA);
        assertThat(testUsuario.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testUsuario.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testUsuario.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testUsuario.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testUsuario.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testUsuario.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testUsuario.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testUsuario.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testUsuario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testUsuario.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testUsuario.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testUsuario.getDetalhe()).isEqualTo(UPDATED_DETALHE);
        assertThat(testUsuario.isBloqueado()).isEqualTo(UPDATED_BLOQUEADO);
        assertThat(testUsuario.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testUsuario.isNaoPodeExcluir()).isEqualTo(UPDATED_NAO_PODE_EXCLUIR);
        assertThat(testUsuario.getUltimoAcesso()).isEqualTo(UPDATED_ULTIMO_ACESSO);
        assertThat(testUsuario.getSenhaFirebase()).isEqualTo(UPDATED_SENHA_FIREBASE);
        assertThat(testUsuario.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testUsuario.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioMockMvc.perform(put("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeDelete = usuarioRepository.findAll().size();

        // Delete the usuario
        restUsuarioMockMvc.perform(delete("/api/usuarios/{id}", usuario.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
