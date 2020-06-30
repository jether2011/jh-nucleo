package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.Usuario} entity.
 */
public class UsuarioDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 60)
    private String name;

    @NotNull
    @Size(max = 255)
    private String descricao;

    @NotNull
    @Size(max = 40)
    private String email;

    @NotNull
    @Size(max = 40)
    private String senha;

    @Size(max = 25)
    private String cnpj;

    @Size(max = 20)
    private String cpf;

    @Size(max = 9)
    private String cep;

    @Size(max = 50)
    private String endereco;

    private Integer numero;

    @Size(max = 50)
    private String bairro;

    @Size(max = 50)
    private String cidade;

    @Size(max = 2)
    private String estado;

    @Size(max = 20)
    private String telefone;

    @Size(max = 20)
    private String fax;

    @Size(max = 20)
    private String celular;

    @Size(max = 255)
    private String detalhe;

    private Boolean bloqueado;

    @Size(max = 40)
    private String complemento;

    private Boolean naoPodeExcluir;

    @NotNull
    private Instant ultimoAcesso;

    @Size(max = 255)
    private String senhaFirebase;

    @NotNull
    private Instant created;

    private Instant updated;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public Boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Boolean isNaoPodeExcluir() {
        return naoPodeExcluir;
    }

    public void setNaoPodeExcluir(Boolean naoPodeExcluir) {
        this.naoPodeExcluir = naoPodeExcluir;
    }

    public Instant getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(Instant ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public String getSenhaFirebase() {
        return senhaFirebase;
    }

    public void setSenhaFirebase(String senhaFirebase) {
        this.senhaFirebase = senhaFirebase;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioDTO)) {
            return false;
        }

        return id != null && id.equals(((UsuarioDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioDTO{" +
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
