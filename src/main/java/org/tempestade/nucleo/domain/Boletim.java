package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Boletim.
 */
@Entity
@Table(name = "boletim")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Boletim implements Serializable {

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

    @Size(max = 255)
    @Column(name = "texto", length = 255)
    private String texto;

    @Size(max = 255)
    @Column(name = "texto_sms", length = 255)
    private String textoSms;

    @Size(max = 255)
    @Column(name = "imagem", length = 255)
    private String imagem;

    @Size(max = 255)
    @Column(name = "assunto", length = 255)
    private String assunto;

    @Size(max = 255)
    @Column(name = "texto_parte_2", length = 255)
    private String textoParte2;

    @Size(max = 255)
    @Column(name = "texto_parte_3", length = 255)
    private String textoParte3;

    @Size(max = 255)
    @Column(name = "sub_assunto", length = 255)
    private String subAssunto;

    @Column(name = "nao_exibir_pag_empresa")
    private Integer naoExibirPagEmpresa;

    @Column(name = "critico")
    private Boolean critico;

    @Column(name = "aprovado")
    private Boolean aprovado;

    @Column(name = "enviar_sms")
    private Boolean enviarSms;

    @Column(name = "enviar_email")
    private Boolean enviarEmail;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @ManyToOne
    @JsonIgnoreProperties(value = "boletims", allowSetters = true)
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

    public Boletim nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boletim descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTexto() {
        return texto;
    }

    public Boletim texto(String texto) {
        this.texto = texto;
        return this;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTextoSms() {
        return textoSms;
    }

    public Boletim textoSms(String textoSms) {
        this.textoSms = textoSms;
        return this;
    }

    public void setTextoSms(String textoSms) {
        this.textoSms = textoSms;
    }

    public String getImagem() {
        return imagem;
    }

    public Boletim imagem(String imagem) {
        this.imagem = imagem;
        return this;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getAssunto() {
        return assunto;
    }

    public Boletim assunto(String assunto) {
        this.assunto = assunto;
        return this;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getTextoParte2() {
        return textoParte2;
    }

    public Boletim textoParte2(String textoParte2) {
        this.textoParte2 = textoParte2;
        return this;
    }

    public void setTextoParte2(String textoParte2) {
        this.textoParte2 = textoParte2;
    }

    public String getTextoParte3() {
        return textoParte3;
    }

    public Boletim textoParte3(String textoParte3) {
        this.textoParte3 = textoParte3;
        return this;
    }

    public void setTextoParte3(String textoParte3) {
        this.textoParte3 = textoParte3;
    }

    public String getSubAssunto() {
        return subAssunto;
    }

    public Boletim subAssunto(String subAssunto) {
        this.subAssunto = subAssunto;
        return this;
    }

    public void setSubAssunto(String subAssunto) {
        this.subAssunto = subAssunto;
    }

    public Integer getNaoExibirPagEmpresa() {
        return naoExibirPagEmpresa;
    }

    public Boletim naoExibirPagEmpresa(Integer naoExibirPagEmpresa) {
        this.naoExibirPagEmpresa = naoExibirPagEmpresa;
        return this;
    }

    public void setNaoExibirPagEmpresa(Integer naoExibirPagEmpresa) {
        this.naoExibirPagEmpresa = naoExibirPagEmpresa;
    }

    public Boolean isCritico() {
        return critico;
    }

    public Boletim critico(Boolean critico) {
        this.critico = critico;
        return this;
    }

    public void setCritico(Boolean critico) {
        this.critico = critico;
    }

    public Boolean isAprovado() {
        return aprovado;
    }

    public Boletim aprovado(Boolean aprovado) {
        this.aprovado = aprovado;
        return this;
    }

    public void setAprovado(Boolean aprovado) {
        this.aprovado = aprovado;
    }

    public Boolean isEnviarSms() {
        return enviarSms;
    }

    public Boletim enviarSms(Boolean enviarSms) {
        this.enviarSms = enviarSms;
        return this;
    }

    public void setEnviarSms(Boolean enviarSms) {
        this.enviarSms = enviarSms;
    }

    public Boolean isEnviarEmail() {
        return enviarEmail;
    }

    public Boletim enviarEmail(Boolean enviarEmail) {
        this.enviarEmail = enviarEmail;
        return this;
    }

    public void setEnviarEmail(Boolean enviarEmail) {
        this.enviarEmail = enviarEmail;
    }

    public Instant getCreated() {
        return created;
    }

    public Boletim created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public Boletim updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public PlanoRecurso getPlanoRecurso() {
        return planoRecurso;
    }

    public Boletim planoRecurso(PlanoRecurso planoRecurso) {
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
        if (!(o instanceof Boletim)) {
            return false;
        }
        return id != null && id.equals(((Boletim) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Boletim{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", texto='" + getTexto() + "'" +
            ", textoSms='" + getTextoSms() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", assunto='" + getAssunto() + "'" +
            ", textoParte2='" + getTextoParte2() + "'" +
            ", textoParte3='" + getTextoParte3() + "'" +
            ", subAssunto='" + getSubAssunto() + "'" +
            ", naoExibirPagEmpresa=" + getNaoExibirPagEmpresa() +
            ", critico='" + isCritico() + "'" +
            ", aprovado='" + isAprovado() + "'" +
            ", enviarSms='" + isEnviarSms() + "'" +
            ", enviarEmail='" + isEnviarEmail() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
