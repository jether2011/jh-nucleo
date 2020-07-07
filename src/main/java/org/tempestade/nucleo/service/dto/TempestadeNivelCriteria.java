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
 * Criteria class for the {@link org.tempestade.nucleo.domain.TempestadeNivel} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.TempestadeNivelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tempestade-nivels?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TempestadeNivelCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter descricao;

    private StringFilter taxaDeRaios;

    private StringFilter ventosVelocidade;

    private StringFilter granizo;

    private StringFilter potencialDeDanos;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter intensidadeChuvaId;

    public TempestadeNivelCriteria() {
    }

    public TempestadeNivelCriteria(TempestadeNivelCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.taxaDeRaios = other.taxaDeRaios == null ? null : other.taxaDeRaios.copy();
        this.ventosVelocidade = other.ventosVelocidade == null ? null : other.ventosVelocidade.copy();
        this.granizo = other.granizo == null ? null : other.granizo.copy();
        this.potencialDeDanos = other.potencialDeDanos == null ? null : other.potencialDeDanos.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.intensidadeChuvaId = other.intensidadeChuvaId == null ? null : other.intensidadeChuvaId.copy();
    }

    @Override
    public TempestadeNivelCriteria copy() {
        return new TempestadeNivelCriteria(this);
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

    public StringFilter getTaxaDeRaios() {
        return taxaDeRaios;
    }

    public void setTaxaDeRaios(StringFilter taxaDeRaios) {
        this.taxaDeRaios = taxaDeRaios;
    }

    public StringFilter getVentosVelocidade() {
        return ventosVelocidade;
    }

    public void setVentosVelocidade(StringFilter ventosVelocidade) {
        this.ventosVelocidade = ventosVelocidade;
    }

    public StringFilter getGranizo() {
        return granizo;
    }

    public void setGranizo(StringFilter granizo) {
        this.granizo = granizo;
    }

    public StringFilter getPotencialDeDanos() {
        return potencialDeDanos;
    }

    public void setPotencialDeDanos(StringFilter potencialDeDanos) {
        this.potencialDeDanos = potencialDeDanos;
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

    public LongFilter getIntensidadeChuvaId() {
        return intensidadeChuvaId;
    }

    public void setIntensidadeChuvaId(LongFilter intensidadeChuvaId) {
        this.intensidadeChuvaId = intensidadeChuvaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TempestadeNivelCriteria that = (TempestadeNivelCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(taxaDeRaios, that.taxaDeRaios) &&
            Objects.equals(ventosVelocidade, that.ventosVelocidade) &&
            Objects.equals(granizo, that.granizo) &&
            Objects.equals(potencialDeDanos, that.potencialDeDanos) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(intensidadeChuvaId, that.intensidadeChuvaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        descricao,
        taxaDeRaios,
        ventosVelocidade,
        granizo,
        potencialDeDanos,
        created,
        updated,
        intensidadeChuvaId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TempestadeNivelCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (taxaDeRaios != null ? "taxaDeRaios=" + taxaDeRaios + ", " : "") +
                (ventosVelocidade != null ? "ventosVelocidade=" + ventosVelocidade + ", " : "") +
                (granizo != null ? "granizo=" + granizo + ", " : "") +
                (potencialDeDanos != null ? "potencialDeDanos=" + potencialDeDanos + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (intensidadeChuvaId != null ? "intensidadeChuvaId=" + intensidadeChuvaId + ", " : "") +
            "}";
    }

}
