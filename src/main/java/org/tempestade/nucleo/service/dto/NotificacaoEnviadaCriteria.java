package org.tempestade.nucleo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link org.tempestade.nucleo.domain.NotificacaoEnviada} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.NotificacaoEnviadaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notificacao-enviadas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NotificacaoEnviadaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter descricao;

    private StringFilter destinatarios;

    private StringFilter tipo;

    private StringFilter status;

    private StringFilter assunto;

    private IntegerFilter enviado;

    private IntegerFilter contador;

    private StringFilter amazonMessageId;

    private InstantFilter amazonDateLog;

    private DoubleFilter priceInUsd;

    private StringFilter amazonResposta;

    private IntegerFilter referenceId;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter planoRecursoId;

    public NotificacaoEnviadaCriteria() {
    }

    public NotificacaoEnviadaCriteria(NotificacaoEnviadaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.destinatarios = other.destinatarios == null ? null : other.destinatarios.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.assunto = other.assunto == null ? null : other.assunto.copy();
        this.enviado = other.enviado == null ? null : other.enviado.copy();
        this.contador = other.contador == null ? null : other.contador.copy();
        this.amazonMessageId = other.amazonMessageId == null ? null : other.amazonMessageId.copy();
        this.amazonDateLog = other.amazonDateLog == null ? null : other.amazonDateLog.copy();
        this.priceInUsd = other.priceInUsd == null ? null : other.priceInUsd.copy();
        this.amazonResposta = other.amazonResposta == null ? null : other.amazonResposta.copy();
        this.referenceId = other.referenceId == null ? null : other.referenceId.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.planoRecursoId = other.planoRecursoId == null ? null : other.planoRecursoId.copy();
    }

    @Override
    public NotificacaoEnviadaCriteria copy() {
        return new NotificacaoEnviadaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public StringFilter getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(StringFilter destinatarios) {
        this.destinatarios = destinatarios;
    }

    public StringFilter getTipo() {
        return tipo;
    }

    public void setTipo(StringFilter tipo) {
        this.tipo = tipo;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public StringFilter getAssunto() {
        return assunto;
    }

    public void setAssunto(StringFilter assunto) {
        this.assunto = assunto;
    }

    public IntegerFilter getEnviado() {
        return enviado;
    }

    public void setEnviado(IntegerFilter enviado) {
        this.enviado = enviado;
    }

    public IntegerFilter getContador() {
        return contador;
    }

    public void setContador(IntegerFilter contador) {
        this.contador = contador;
    }

    public StringFilter getAmazonMessageId() {
        return amazonMessageId;
    }

    public void setAmazonMessageId(StringFilter amazonMessageId) {
        this.amazonMessageId = amazonMessageId;
    }

    public InstantFilter getAmazonDateLog() {
        return amazonDateLog;
    }

    public void setAmazonDateLog(InstantFilter amazonDateLog) {
        this.amazonDateLog = amazonDateLog;
    }

    public DoubleFilter getPriceInUsd() {
        return priceInUsd;
    }

    public void setPriceInUsd(DoubleFilter priceInUsd) {
        this.priceInUsd = priceInUsd;
    }

    public StringFilter getAmazonResposta() {
        return amazonResposta;
    }

    public void setAmazonResposta(StringFilter amazonResposta) {
        this.amazonResposta = amazonResposta;
    }

    public IntegerFilter getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(IntegerFilter referenceId) {
        this.referenceId = referenceId;
    }

    public InstantFilter getCreated() {
        return created;
    }

    public void setCreated(InstantFilter created) {
        this.created = created;
    }

    public InstantFilter getUpdated() {
        return updated;
    }

    public void setUpdated(InstantFilter updated) {
        this.updated = updated;
    }

    public LongFilter getPlanoRecursoId() {
        return planoRecursoId;
    }

    public void setPlanoRecursoId(LongFilter planoRecursoId) {
        this.planoRecursoId = planoRecursoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NotificacaoEnviadaCriteria that = (NotificacaoEnviadaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(destinatarios, that.destinatarios) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(status, that.status) &&
            Objects.equals(assunto, that.assunto) &&
            Objects.equals(enviado, that.enviado) &&
            Objects.equals(contador, that.contador) &&
            Objects.equals(amazonMessageId, that.amazonMessageId) &&
            Objects.equals(amazonDateLog, that.amazonDateLog) &&
            Objects.equals(priceInUsd, that.priceInUsd) &&
            Objects.equals(amazonResposta, that.amazonResposta) &&
            Objects.equals(referenceId, that.referenceId) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(planoRecursoId, that.planoRecursoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        descricao,
        destinatarios,
        tipo,
        status,
        assunto,
        enviado,
        contador,
        amazonMessageId,
        amazonDateLog,
        priceInUsd,
        amazonResposta,
        referenceId,
        created,
        updated,
        planoRecursoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificacaoEnviadaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (destinatarios != null ? "destinatarios=" + destinatarios + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (assunto != null ? "assunto=" + assunto + ", " : "") +
                (enviado != null ? "enviado=" + enviado + ", " : "") +
                (contador != null ? "contador=" + contador + ", " : "") +
                (amazonMessageId != null ? "amazonMessageId=" + amazonMessageId + ", " : "") +
                (amazonDateLog != null ? "amazonDateLog=" + amazonDateLog + ", " : "") +
                (priceInUsd != null ? "priceInUsd=" + priceInUsd + ", " : "") +
                (amazonResposta != null ? "amazonResposta=" + amazonResposta + ", " : "") +
                (referenceId != null ? "referenceId=" + referenceId + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (planoRecursoId != null ? "planoRecursoId=" + planoRecursoId + ", " : "") +
            "}";
    }

}
