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
 * Criteria class for the {@link org.tempestade.nucleo.domain.Alerta} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.AlertaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /alertas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AlertaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter contato;

    private StringFilter duracao;

    private BooleanFilter automatico;

    private BooleanFilter critico;

    private StringFilter observacao;

    private IntegerFilter alertaPaiId;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter planoRecursoId;

    private LongFilter alvoId;

    private LongFilter operadorUsuarioId;

    private LongFilter alertaRiscoId;

    private LongFilter tempestadeNivelId;

    private LongFilter alertaTipoId;

    public AlertaCriteria() {
    }

    public AlertaCriteria(AlertaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.contato = other.contato == null ? null : other.contato.copy();
        this.duracao = other.duracao == null ? null : other.duracao.copy();
        this.automatico = other.automatico == null ? null : other.automatico.copy();
        this.critico = other.critico == null ? null : other.critico.copy();
        this.observacao = other.observacao == null ? null : other.observacao.copy();
        this.alertaPaiId = other.alertaPaiId == null ? null : other.alertaPaiId.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.planoRecursoId = other.planoRecursoId == null ? null : other.planoRecursoId.copy();
        this.alvoId = other.alvoId == null ? null : other.alvoId.copy();
        this.operadorUsuarioId = other.operadorUsuarioId == null ? null : other.operadorUsuarioId.copy();
        this.alertaRiscoId = other.alertaRiscoId == null ? null : other.alertaRiscoId.copy();
        this.tempestadeNivelId = other.tempestadeNivelId == null ? null : other.tempestadeNivelId.copy();
        this.alertaTipoId = other.alertaTipoId == null ? null : other.alertaTipoId.copy();
    }

    @Override
    public AlertaCriteria copy() {
        return new AlertaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getContato() {
        return contato;
    }

    public void setContato(StringFilter contato) {
        this.contato = contato;
    }

    public StringFilter getDuracao() {
        return duracao;
    }

    public void setDuracao(StringFilter duracao) {
        this.duracao = duracao;
    }

    public BooleanFilter getAutomatico() {
        return automatico;
    }

    public void setAutomatico(BooleanFilter automatico) {
        this.automatico = automatico;
    }

    public BooleanFilter getCritico() {
        return critico;
    }

    public void setCritico(BooleanFilter critico) {
        this.critico = critico;
    }

    public StringFilter getObservacao() {
        return observacao;
    }

    public void setObservacao(StringFilter observacao) {
        this.observacao = observacao;
    }

    public IntegerFilter getAlertaPaiId() {
        return alertaPaiId;
    }

    public void setAlertaPaiId(IntegerFilter alertaPaiId) {
        this.alertaPaiId = alertaPaiId;
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

    public LongFilter getAlvoId() {
        return alvoId;
    }

    public void setAlvoId(LongFilter alvoId) {
        this.alvoId = alvoId;
    }

    public LongFilter getOperadorUsuarioId() {
        return operadorUsuarioId;
    }

    public void setOperadorUsuarioId(LongFilter operadorUsuarioId) {
        this.operadorUsuarioId = operadorUsuarioId;
    }

    public LongFilter getAlertaRiscoId() {
        return alertaRiscoId;
    }

    public void setAlertaRiscoId(LongFilter alertaRiscoId) {
        this.alertaRiscoId = alertaRiscoId;
    }

    public LongFilter getTempestadeNivelId() {
        return tempestadeNivelId;
    }

    public void setTempestadeNivelId(LongFilter tempestadeNivelId) {
        this.tempestadeNivelId = tempestadeNivelId;
    }

    public LongFilter getAlertaTipoId() {
        return alertaTipoId;
    }

    public void setAlertaTipoId(LongFilter alertaTipoId) {
        this.alertaTipoId = alertaTipoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AlertaCriteria that = (AlertaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(contato, that.contato) &&
            Objects.equals(duracao, that.duracao) &&
            Objects.equals(automatico, that.automatico) &&
            Objects.equals(critico, that.critico) &&
            Objects.equals(observacao, that.observacao) &&
            Objects.equals(alertaPaiId, that.alertaPaiId) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(planoRecursoId, that.planoRecursoId) &&
            Objects.equals(alvoId, that.alvoId) &&
            Objects.equals(operadorUsuarioId, that.operadorUsuarioId) &&
            Objects.equals(alertaRiscoId, that.alertaRiscoId) &&
            Objects.equals(tempestadeNivelId, that.tempestadeNivelId) &&
            Objects.equals(alertaTipoId, that.alertaTipoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        contato,
        duracao,
        automatico,
        critico,
        observacao,
        alertaPaiId,
        created,
        updated,
        planoRecursoId,
        alvoId,
        operadorUsuarioId,
        alertaRiscoId,
        tempestadeNivelId,
        alertaTipoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlertaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (contato != null ? "contato=" + contato + ", " : "") +
                (duracao != null ? "duracao=" + duracao + ", " : "") +
                (automatico != null ? "automatico=" + automatico + ", " : "") +
                (critico != null ? "critico=" + critico + ", " : "") +
                (observacao != null ? "observacao=" + observacao + ", " : "") +
                (alertaPaiId != null ? "alertaPaiId=" + alertaPaiId + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (planoRecursoId != null ? "planoRecursoId=" + planoRecursoId + ", " : "") +
                (alvoId != null ? "alvoId=" + alvoId + ", " : "") +
                (operadorUsuarioId != null ? "operadorUsuarioId=" + operadorUsuarioId + ", " : "") +
                (alertaRiscoId != null ? "alertaRiscoId=" + alertaRiscoId + ", " : "") +
                (tempestadeNivelId != null ? "tempestadeNivelId=" + tempestadeNivelId + ", " : "") +
                (alertaTipoId != null ? "alertaTipoId=" + alertaTipoId + ", " : "") +
            "}";
    }

}
