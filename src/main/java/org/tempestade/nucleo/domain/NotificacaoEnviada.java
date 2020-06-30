package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A NotificacaoEnviada.
 */
@Entity
@Table(name = "notificacao_enviada")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NotificacaoEnviada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @NotNull
    @Size(max = 255)
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @Size(max = 255)
    @Column(name = "destinatarios", length = 255)
    private String destinatarios;

    @Size(max = 255)
    @Column(name = "tipo", length = 255)
    private String tipo;

    @Size(max = 255)
    @Column(name = "status", length = 255)
    private String status;

    @Size(max = 255)
    @Column(name = "assunto", length = 255)
    private String assunto;

    @Column(name = "enviado")
    private Integer enviado;

    @Column(name = "contador")
    private Integer contador;

    @Size(max = 255)
    @Column(name = "amazon_message_id", length = 255)
    private String amazonMessageId;

    @NotNull
    @Column(name = "amazon_date_log", nullable = false)
    private Instant amazonDateLog;

    @Column(name = "price_in_usd")
    private Double priceInUsd;

    @Size(max = 255)
    @Column(name = "amazon_resposta", length = 255)
    private String amazonResposta;

    @Column(name = "reference_id")
    private Integer referenceId;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @ManyToOne
    @JsonIgnoreProperties(value = "notificacaoEnviadas", allowSetters = true)
    private PlanoRecurso planoRecurso;

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

    public NotificacaoEnviada name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public NotificacaoEnviada descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDestinatarios() {
        return destinatarios;
    }

    public NotificacaoEnviada destinatarios(String destinatarios) {
        this.destinatarios = destinatarios;
        return this;
    }

    public void setDestinatarios(String destinatarios) {
        this.destinatarios = destinatarios;
    }

    public String getTipo() {
        return tipo;
    }

    public NotificacaoEnviada tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public NotificacaoEnviada status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssunto() {
        return assunto;
    }

    public NotificacaoEnviada assunto(String assunto) {
        this.assunto = assunto;
        return this;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Integer getEnviado() {
        return enviado;
    }

    public NotificacaoEnviada enviado(Integer enviado) {
        this.enviado = enviado;
        return this;
    }

    public void setEnviado(Integer enviado) {
        this.enviado = enviado;
    }

    public Integer getContador() {
        return contador;
    }

    public NotificacaoEnviada contador(Integer contador) {
        this.contador = contador;
        return this;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    public String getAmazonMessageId() {
        return amazonMessageId;
    }

    public NotificacaoEnviada amazonMessageId(String amazonMessageId) {
        this.amazonMessageId = amazonMessageId;
        return this;
    }

    public void setAmazonMessageId(String amazonMessageId) {
        this.amazonMessageId = amazonMessageId;
    }

    public Instant getAmazonDateLog() {
        return amazonDateLog;
    }

    public NotificacaoEnviada amazonDateLog(Instant amazonDateLog) {
        this.amazonDateLog = amazonDateLog;
        return this;
    }

    public void setAmazonDateLog(Instant amazonDateLog) {
        this.amazonDateLog = amazonDateLog;
    }

    public Double getPriceInUsd() {
        return priceInUsd;
    }

    public NotificacaoEnviada priceInUsd(Double priceInUsd) {
        this.priceInUsd = priceInUsd;
        return this;
    }

    public void setPriceInUsd(Double priceInUsd) {
        this.priceInUsd = priceInUsd;
    }

    public String getAmazonResposta() {
        return amazonResposta;
    }

    public NotificacaoEnviada amazonResposta(String amazonResposta) {
        this.amazonResposta = amazonResposta;
        return this;
    }

    public void setAmazonResposta(String amazonResposta) {
        this.amazonResposta = amazonResposta;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public NotificacaoEnviada referenceId(Integer referenceId) {
        this.referenceId = referenceId;
        return this;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public Instant getCreated() {
        return created;
    }

    public NotificacaoEnviada created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public NotificacaoEnviada updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public PlanoRecurso getPlanoRecurso() {
        return planoRecurso;
    }

    public NotificacaoEnviada planoRecurso(PlanoRecurso planoRecurso) {
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
        if (!(o instanceof NotificacaoEnviada)) {
            return false;
        }
        return id != null && id.equals(((NotificacaoEnviada) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificacaoEnviada{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", destinatarios='" + getDestinatarios() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", status='" + getStatus() + "'" +
            ", assunto='" + getAssunto() + "'" +
            ", enviado=" + getEnviado() +
            ", contador=" + getContador() +
            ", amazonMessageId='" + getAmazonMessageId() + "'" +
            ", amazonDateLog='" + getAmazonDateLog() + "'" +
            ", priceInUsd=" + getPriceInUsd() +
            ", amazonResposta='" + getAmazonResposta() + "'" +
            ", referenceId=" + getReferenceId() +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
