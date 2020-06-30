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
 * Criteria class for the {@link org.tempestade.nucleo.domain.PlanoRecursoTipoEnvio} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.PlanoRecursoTipoEnvioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /plano-recurso-tipo-envios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PlanoRecursoTipoEnvioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter descricao;

    private IntegerFilter qtd;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter planoRecursoId;

    private LongFilter tipoEnvioId;

    public PlanoRecursoTipoEnvioCriteria() {
    }

    public PlanoRecursoTipoEnvioCriteria(PlanoRecursoTipoEnvioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.qtd = other.qtd == null ? null : other.qtd.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.planoRecursoId = other.planoRecursoId == null ? null : other.planoRecursoId.copy();
        this.tipoEnvioId = other.tipoEnvioId == null ? null : other.tipoEnvioId.copy();
    }

    @Override
    public PlanoRecursoTipoEnvioCriteria copy() {
        return new PlanoRecursoTipoEnvioCriteria(this);
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

    public IntegerFilter getQtd() {
        return qtd;
    }

    public void setQtd(IntegerFilter qtd) {
        this.qtd = qtd;
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

    public LongFilter getTipoEnvioId() {
        return tipoEnvioId;
    }

    public void setTipoEnvioId(LongFilter tipoEnvioId) {
        this.tipoEnvioId = tipoEnvioId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PlanoRecursoTipoEnvioCriteria that = (PlanoRecursoTipoEnvioCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(qtd, that.qtd) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(planoRecursoId, that.planoRecursoId) &&
            Objects.equals(tipoEnvioId, that.tipoEnvioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        descricao,
        qtd,
        created,
        updated,
        planoRecursoId,
        tipoEnvioId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoRecursoTipoEnvioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (qtd != null ? "qtd=" + qtd + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (planoRecursoId != null ? "planoRecursoId=" + planoRecursoId + ", " : "") +
                (tipoEnvioId != null ? "tipoEnvioId=" + tipoEnvioId + ", " : "") +
            "}";
    }

}
