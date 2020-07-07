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
 * Criteria class for the {@link org.tempestade.nucleo.domain.RecursoTemplate} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.RecursoTemplateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /recurso-templates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RecursoTemplateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter descricao;

    private StringFilter template;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter recursoId;

    private LongFilter tipoEnvioId;

    private LongFilter alertaTipoId;

    public RecursoTemplateCriteria() {
    }

    public RecursoTemplateCriteria(RecursoTemplateCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.template = other.template == null ? null : other.template.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.recursoId = other.recursoId == null ? null : other.recursoId.copy();
        this.tipoEnvioId = other.tipoEnvioId == null ? null : other.tipoEnvioId.copy();
        this.alertaTipoId = other.alertaTipoId == null ? null : other.alertaTipoId.copy();
    }

    @Override
    public RecursoTemplateCriteria copy() {
        return new RecursoTemplateCriteria(this);
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

    public StringFilter getTemplate() {
        return template;
    }

    public void setTemplate(StringFilter template) {
        this.template = template;
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

    public LongFilter getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(LongFilter recursoId) {
        this.recursoId = recursoId;
    }

    public LongFilter getTipoEnvioId() {
        return tipoEnvioId;
    }

    public void setTipoEnvioId(LongFilter tipoEnvioId) {
        this.tipoEnvioId = tipoEnvioId;
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
        final RecursoTemplateCriteria that = (RecursoTemplateCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(template, that.template) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(recursoId, that.recursoId) &&
            Objects.equals(tipoEnvioId, that.tipoEnvioId) &&
            Objects.equals(alertaTipoId, that.alertaTipoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        descricao,
        template,
        created,
        updated,
        recursoId,
        tipoEnvioId,
        alertaTipoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecursoTemplateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (template != null ? "template=" + template + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (recursoId != null ? "recursoId=" + recursoId + ", " : "") +
                (tipoEnvioId != null ? "tipoEnvioId=" + tipoEnvioId + ", " : "") +
                (alertaTipoId != null ? "alertaTipoId=" + alertaTipoId + ", " : "") +
            "}";
    }

}
