package org.tempestade.nucleo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 60)
    @Column(name = "name", length = 60, nullable = false, unique = true)
    private String name;

    @NotNull
    @Size(max = 255)
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @NotNull
    @Size(max = 40)
    @Column(name = "email", length = 40, nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(max = 40)
    @Column(name = "senha", length = 40, nullable = false)
    private String senha;

    @Size(max = 25)
    @Column(name = "cnpj", length = 25, unique = true)
    private String cnpj;

    @Size(max = 20)
    @Column(name = "cpf", length = 20, unique = true)
    private String cpf;

    @Size(max = 9)
    @Column(name = "cep", length = 9)
    private String cep;

    @Size(max = 50)
    @Column(name = "endereco", length = 50)
    private String endereco;

    @Column(name = "numero")
    private Integer numero;

    @Size(max = 50)
    @Column(name = "bairro", length = 50)
    private String bairro;

    @Size(max = 50)
    @Column(name = "cidade", length = 50)
    private String cidade;

    @Size(max = 2)
    @Column(name = "estado", length = 2)
    private String estado;

    @Size(max = 20)
    @Column(name = "telefone", length = 20)
    private String telefone;

    @Size(max = 20)
    @Column(name = "fax", length = 20)
    private String fax;

    @Size(max = 20)
    @Column(name = "celular", length = 20)
    private String celular;

    @Size(max = 255)
    @Column(name = "detalhe", length = 255)
    private String detalhe;

    @Column(name = "bloqueado")
    private Boolean bloqueado;

    @Size(max = 40)
    @Column(name = "complemento", length = 40)
    private String complemento;

    @Column(name = "nao_pode_excluir")
    private Boolean naoPodeExcluir;

    @NotNull
    @Column(name = "ultimo_acesso", nullable = false)
    private Instant ultimoAcesso;

    @Size(max = 255)
    @Column(name = "senha_firebase", length = 255)
    private String senhaFirebase;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Usuario name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public Usuario descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public Usuario email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public Usuario senha(String senha) {
        this.senha = senha;
        return this;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Usuario cnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpf() {
        return cpf;
    }

    public Usuario cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public Usuario cep(String cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public Usuario endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getNumero() {
        return numero;
    }

    public Usuario numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public Usuario bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public Usuario cidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public Usuario estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefone() {
        return telefone;
    }

    public Usuario telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFax() {
        return fax;
    }

    public Usuario fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCelular() {
        return celular;
    }

    public Usuario celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public Usuario detalhe(String detalhe) {
        this.detalhe = detalhe;
        return this;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public Boolean isBloqueado() {
        return bloqueado;
    }

    public Usuario bloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
        return this;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public String getComplemento() {
        return complemento;
    }

    public Usuario complemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Boolean isNaoPodeExcluir() {
        return naoPodeExcluir;
    }

    public Usuario naoPodeExcluir(Boolean naoPodeExcluir) {
        this.naoPodeExcluir = naoPodeExcluir;
        return this;
    }

    public void setNaoPodeExcluir(Boolean naoPodeExcluir) {
        this.naoPodeExcluir = naoPodeExcluir;
    }

    public Instant getUltimoAcesso() {
        return ultimoAcesso;
    }

    public Usuario ultimoAcesso(Instant ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
        return this;
    }

    public void setUltimoAcesso(Instant ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public String getSenhaFirebase() {
        return senhaFirebase;
    }

    public Usuario senhaFirebase(String senhaFirebase) {
        this.senhaFirebase = senhaFirebase;
        return this;
    }

    public void setSenhaFirebase(String senhaFirebase) {
        this.senhaFirebase = senhaFirebase;
    }

    public Instant getCreated() {
        return created;
    }

    public Usuario created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public Usuario updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }
        return id != null && id.equals(((Usuario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", email='" + getEmail() + "'" +
            ", senha='" + getSenha() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", cep='" + getCep() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", numero=" + getNumero() +
            ", bairro='" + getBairro() + "'" +
            ", cidade='" + getCidade() + "'" +
            ", estado='" + getEstado() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", fax='" + getFax() + "'" +
            ", celular='" + getCelular() + "'" +
            ", detalhe='" + getDetalhe() + "'" +
            ", bloqueado='" + isBloqueado() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", naoPodeExcluir='" + isNaoPodeExcluir() + "'" +
            ", ultimoAcesso='" + getUltimoAcesso() + "'" +
            ", senhaFirebase='" + getSenhaFirebase() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
