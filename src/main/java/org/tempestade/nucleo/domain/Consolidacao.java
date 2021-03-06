package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A Consolidacao.
 */
@Entity
@Table(name = "consolidacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Consolidacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nome", length = 255, nullable = false, unique = true)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Size(max = 255)
    @Column(name = "texto", length = 255)
    private String texto;

    @Column(name = "qtd_email")
    private Integer qtdEmail;

    @Size(max = 255)
    @Column(name = "imagem", length = 255)
    private String imagem;

    @Size(max = 255)
    @Column(name = "arquivo_eml", length = 255)
    private String arquivoEml;

    @Size(max = 255)
    @Column(name = "assunto", length = 255)
    private String assunto;

    @Size(max = 255)
    @Column(name = "sub_assunto", length = 255)
    private String subAssunto;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @ManyToOne
    @JsonIgnoreProperties(value = "consolidacaos", allowSetters = true)
    private PlanoRecurso planoRecurso;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Consolidacao nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Consolidacao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public Consolidacao data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTexto() {
        return texto;
    }

    public Consolidacao texto(String texto) {
        this.texto = texto;
        return this;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getQtdEmail() {
        return qtdEmail;
    }

    public Consolidacao qtdEmail(Integer qtdEmail) {
        this.qtdEmail = qtdEmail;
        return this;
    }

    public void setQtdEmail(Integer qtdEmail) {
        this.qtdEmail = qtdEmail;
    }

    public String getImagem() {
        return imagem;
    }

    public Consolidacao imagem(String imagem) {
        this.imagem = imagem;
        return this;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getArquivoEml() {
        return arquivoEml;
    }

    public Consolidacao arquivoEml(String arquivoEml) {
        this.arquivoEml = arquivoEml;
        return this;
    }

    public void setArquivoEml(String arquivoEml) {
        this.arquivoEml = arquivoEml;
    }

    public String getAssunto() {
        return assunto;
    }

    public Consolidacao assunto(String assunto) {
        this.assunto = assunto;
        return this;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getSubAssunto() {
        return subAssunto;
    }

    public Consolidacao subAssunto(String subAssunto) {
        this.subAssunto = subAssunto;
        return this;
    }

    public void setSubAssunto(String subAssunto) {
        this.subAssunto = subAssunto;
    }

    public Instant getCreated() {
        return created;
    }

    public Consolidacao created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public Consolidacao updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public PlanoRecurso getPlanoRecurso() {
        return planoRecurso;
    }

    public Consolidacao planoRecurso(PlanoRecurso planoRecurso) {
        this.planoRecurso = planoRecurso;
        return this;
    }

    public void setPlanoRecurso(PlanoRecurso planoRecurso) {
        this.planoRecurso = planoRecurso;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Consolidacao)) {
            return false;
        }
        return id != null && id.equals(((Consolidacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Consolidacao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", data='" + getData() + "'" +
            ", texto='" + getTexto() + "'" +
            ", qtdEmail=" + getQtdEmail() +
            ", imagem='" + getImagem() + "'" +
            ", arquivoEml='" + getArquivoEml() + "'" +
            ", assunto='" + getAssunto() + "'" +
            ", subAssunto='" + getSubAssunto() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
