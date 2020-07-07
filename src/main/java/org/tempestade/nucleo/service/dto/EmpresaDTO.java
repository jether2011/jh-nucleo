package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.Empresa} entity.
 */
public class EmpresaDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String nome;

    private String descricao;

    @NotNull
    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String titulo;

    @Size(max = 255)
    private String nomeReduzido;

    @Size(max = 255)
    private String logo;

    @Size(max = 255)
    private String apelido;

    @Size(max = 255)
    private String observacao;

    @NotNull
    private Instant created;

    private Instant updated;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNomeReduzido() {
        return nomeReduzido;
    }

    public void setNomeReduzido(String nomeReduzido) {
        this.nomeReduzido = nomeReduzido;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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
        if (!(o instanceof EmpresaDTO)) {
            return false;
        }

        return id != null && id.equals(((EmpresaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpresaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", email='" + getEmail() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", nomeReduzido='" + getNomeReduzido() + "'" +
            ", logo='" + getLogo() + "'" +
            ", apelido='" + getApelido() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
