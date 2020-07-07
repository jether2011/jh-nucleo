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
 * Criteria class for the {@link org.tempestade.nucleo.domain.AcumuladoChuvaFaixa} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.AcumuladoChuvaFaixaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /acumulado-chuva-faixas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AcumuladoChuvaFaixaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter descricao;

    private IntegerFilter deMm;

    private IntegerFilter ateMm;

    private InstantFilter created;

    private InstantFilter updated;

    public AcumuladoChuvaFaixaCriteria() {
    }

    public AcumuladoChuvaFaixaCriteria(AcumuladoChuvaFaixaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.deMm = other.deMm == null ? null : other.deMm.copy();
        this.ateMm = other.ateMm == null ? null : other.ateMm.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
    }

    @Override
    public AcumuladoChuvaFaixaCriteria copy() {
        return new AcumuladoChuvaFaixaCriteria(this);
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

    public IntegerFilter getDeMm() {
        return deMm;
    }

    public void setDeMm(IntegerFilter deMm) {
        this.deMm = deMm;
    }

    public IntegerFilter getAteMm() {
        return ateMm;
    }

    public void setAteMm(IntegerFilter ateMm) {
        this.ateMm = ateMm;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AcumuladoChuvaFaixaCriteria that = (AcumuladoChuvaFaixaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(deMm, that.deMm) &&
            Objects.equals(ateMm, that.ateMm) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        descricao,
        deMm,
        ateMm,
        created,
        updated
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcumuladoChuvaFaixaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (deMm != null ? "deMm=" + deMm + ", " : "") +
                (ateMm != null ? "ateMm=" + ateMm + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
            "}";
    }

}
