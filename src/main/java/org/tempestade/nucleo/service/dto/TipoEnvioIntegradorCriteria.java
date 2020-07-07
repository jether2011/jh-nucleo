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
 * Criteria class for the {@link org.tempestade.nucleo.domain.TipoEnvioIntegrador} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.TipoEnvioIntegradorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tipo-envio-integradors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TipoEnvioIntegradorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter descricao;

    private BooleanFilter ativo;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter tipoEnvioId;

    private LongFilter integradorId;

    public TipoEnvioIntegradorCriteria() {
    }

    public TipoEnvioIntegradorCriteria(TipoEnvioIntegradorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.ativo = other.ativo == null ? null : other.ativo.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.tipoEnvioId = other.tipoEnvioId == null ? null : other.tipoEnvioId.copy();
        this.integradorId = other.integradorId == null ? null : other.integradorId.copy();
    }

    @Override
    public TipoEnvioIntegradorCriteria copy() {
        return new TipoEnvioIntegradorCriteria(this);
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

    public BooleanFilter getAtivo() {
        return ativo;
    }

    public void setAtivo(BooleanFilter ativo) {
        this.ativo = ativo;
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

    public LongFilter getTipoEnvioId() {
        return tipoEnvioId;
    }

    public void setTipoEnvioId(LongFilter tipoEnvioId) {
        this.tipoEnvioId = tipoEnvioId;
    }

    public LongFilter getIntegradorId() {
        return integradorId;
    }

    public void setIntegradorId(LongFilter integradorId) {
        this.integradorId = integradorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TipoEnvioIntegradorCriteria that = (TipoEnvioIntegradorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(ativo, that.ativo) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(tipoEnvioId, that.tipoEnvioId) &&
            Objects.equals(integradorId, that.integradorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        descricao,
        ativo,
        created,
        updated,
        tipoEnvioId,
        integradorId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoEnvioIntegradorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (ativo != null ? "ativo=" + ativo + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (tipoEnvioId != null ? "tipoEnvioId=" + tipoEnvioId + ", " : "") +
                (integradorId != null ? "integradorId=" + integradorId + ", " : "") +
            "}";
    }

}
