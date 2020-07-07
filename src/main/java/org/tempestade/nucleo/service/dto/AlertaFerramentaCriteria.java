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
 * Criteria class for the {@link org.tempestade.nucleo.domain.AlertaFerramenta} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.AlertaFerramentaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /alerta-ferramentas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AlertaFerramentaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter descricao;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter alertaId;

    private LongFilter ferramentaId;

    public AlertaFerramentaCriteria() {
    }

    public AlertaFerramentaCriteria(AlertaFerramentaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.alertaId = other.alertaId == null ? null : other.alertaId.copy();
        this.ferramentaId = other.ferramentaId == null ? null : other.ferramentaId.copy();
    }

    @Override
    public AlertaFerramentaCriteria copy() {
        return new AlertaFerramentaCriteria(this);
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

    public StringFilter getDescricao() {
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
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

    public LongFilter getAlertaId() {
        return alertaId;
    }

    public void setAlertaId(LongFilter alertaId) {
        this.alertaId = alertaId;
    }

    public LongFilter getFerramentaId() {
        return ferramentaId;
    }

    public void setFerramentaId(LongFilter ferramentaId) {
        this.ferramentaId = ferramentaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AlertaFerramentaCriteria that = (AlertaFerramentaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(alertaId, that.alertaId) &&
            Objects.equals(ferramentaId, that.ferramentaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        descricao,
        created,
        updated,
        alertaId,
        ferramentaId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlertaFerramentaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (alertaId != null ? "alertaId=" + alertaId + ", " : "") +
                (ferramentaId != null ? "ferramentaId=" + ferramentaId + ", " : "") +
            "}";
    }

}
