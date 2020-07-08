package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.NotificacaoEnviada} entity.
 */
public class NotificacaoEnviadaDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String descricao;

    @Size(max = 255)
    private String destinatarios;

    @Size(max = 255)
    private String tipo;

    @Size(max = 255)
    private String status;

    @Size(max = 255)
    private String assunto;

    private Integer enviado;

    private Integer contador;

    @Size(max = 255)
    private String amazonMessageId;

    @NotNull
    private Instant amazonDateLog;

    private Double priceInUsd;

    @Size(max = 255)
    private String amazonResposta;

    private Integer referenceId;

    @NotNull
    private Instant created;

    private Instant updated;


    private Long planoRecursoId;
    
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

    public String getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(String destinatarios) {
        this.destinatarios = destinatarios;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Integer getEnviado() {
        return enviado;
    }

    public void setEnviado(Integer enviado) {
        this.enviado = enviado;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    public String getAmazonMessageId() {
        return amazonMessageId;
    }

    public void setAmazonMessageId(String amazonMessageId) {
        this.amazonMessageId = amazonMessageId;
    }

    public Instant getAmazonDateLog() {
        return amazonDateLog;
    }

    public void setAmazonDateLog(Instant amazonDateLog) {
        this.amazonDateLog = amazonDateLog;
    }

    public Double getPriceInUsd() {
        return priceInUsd;
    }

    public void setPriceInUsd(Double priceInUsd) {
        this.priceInUsd = priceInUsd;
    }

    public String getAmazonResposta() {
        return amazonResposta;
    }

    public void setAmazonResposta(String amazonResposta) {
        this.amazonResposta = amazonResposta;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
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

    public Long getPlanoRecursoId() {
        return planoRecursoId;
    }

    public void setPlanoRecursoId(Long planoRecursoId) {
        this.planoRecursoId = planoRecursoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificacaoEnviadaDTO)) {
            return false;
        }

        return id != null && id.equals(((NotificacaoEnviadaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificacaoEnviadaDTO{" +
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
            ", planoRecursoId=" + getPlanoRecursoId() +
            "}";
    }
}
