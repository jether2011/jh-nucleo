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
 * Criteria class for the {@link org.tempestade.nucleo.domain.BoletimPrevVariavelMet} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.BoletimPrevVariavelMetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /boletim-prev-variavel-mets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BoletimPrevVariavelMetCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter descricao;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter boletimPrevisaoId;

    private LongFilter variavelMeteorologicaId;

    public BoletimPrevVariavelMetCriteria() {
    }

    public BoletimPrevVariavelMetCriteria(BoletimPrevVariavelMetCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.boletimPrevisaoId = other.boletimPrevisaoId == null ? null : other.boletimPrevisaoId.copy();
        this.variavelMeteorologicaId = other.variavelMeteorologicaId == null ? null : other.variavelMeteorologicaId.copy();
    }

    @Override
    public BoletimPrevVariavelMetCriteria copy() {
        return new BoletimPrevVariavelMetCriteria(this);
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

    public LongFilter getBoletimPrevisaoId() {
        return boletimPrevisaoId;
    }

    public void setBoletimPrevisaoId(LongFilter boletimPrevisaoId) {
        this.boletimPrevisaoId = boletimPrevisaoId;
    }

    public LongFilter getVariavelMeteorologicaId() {
        return variavelMeteorologicaId;
    }

    public void setVariavelMeteorologicaId(LongFilter variavelMeteorologicaId) {
        this.variavelMeteorologicaId = variavelMeteorologicaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BoletimPrevVariavelMetCriteria that = (BoletimPrevVariavelMetCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(boletimPrevisaoId, that.boletimPrevisaoId) &&
            Objects.equals(variavelMeteorologicaId, that.variavelMeteorologicaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        descricao,
        created,
        updated,
        boletimPrevisaoId,
        variavelMeteorologicaId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BoletimPrevVariavelMetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (boletimPrevisaoId != null ? "boletimPrevisaoId=" + boletimPrevisaoId + ", " : "") +
                (variavelMeteorologicaId != null ? "variavelMeteorologicaId=" + variavelMeteorologicaId + ", " : "") +
            "}";
    }

}
