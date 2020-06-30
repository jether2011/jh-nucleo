package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.Contato} entity.
 */
public class ContatoDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String nome;

    private String descricao;

    @NotNull
    @Size(max = 255)
    private String email;

    @Size(max = 20)
    private String celular;

    private Boolean ativo;

    private Boolean contatoAlertaTelefonico;

    private Integer prioridade;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    private String horaLigacaoInicial;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    private String horaLigacaoFinal;

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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean isContatoAlertaTelefonico() {
        return contatoAlertaTelefonico;
    }

    public void setContatoAlertaTelefonico(Boolean contatoAlertaTelefonico) {
        this.contatoAlertaTelefonico = contatoAlertaTelefonico;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

    public String getHoraLigacaoInicial() {
        return horaLigacaoInicial;
    }

    public void setHoraLigacaoInicial(String horaLigacaoInicial) {
        this.horaLigacaoInicial = horaLigacaoInicial;
    }

    public String getHoraLigacaoFinal() {
        return horaLigacaoFinal;
    }

    public void setHoraLigacaoFinal(String horaLigacaoFinal) {
        this.horaLigacaoFinal = horaLigacaoFinal;
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
        if (!(o instanceof ContatoDTO)) {
            return false;
        }

        return id != null && id.equals(((ContatoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContatoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", email='" + getEmail() + "'" +
            ", celular='" + getCelular() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", contatoAlertaTelefonico='" + isContatoAlertaTelefonico() + "'" +
            ", prioridade=" + getPrioridade() +
            ", horaLigacaoInicial='" + getHoraLigacaoInicial() + "'" +
            ", horaLigacaoFinal='" + getHoraLigacaoFinal() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
