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
 * Criteria class for the {@link org.tempestade.nucleo.domain.Aviso} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.AvisoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /avisos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AvisoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter descricao;

    private BooleanFilter enviado;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter avisoTipoId;

    public AvisoCriteria() {
    }

    public AvisoCriteria(AvisoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.enviado = other.enviado == null ? null : other.enviado.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.avisoTipoId = other.avisoTipoId == null ? null : other.avisoTipoId.copy();
    }

    @Override
    public AvisoCriteria copy() {
        return new AvisoCriteria(this);
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

    public BooleanFilter getEnviado() {
        return enviado;
    }

    public void setEnviado(BooleanFilter enviado) {
        this.enviado = enviado;
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

    public LongFilter getAvisoTipoId() {
        return avisoTipoId;
    }

    public void setAvisoTipoId(LongFilter avisoTipoId) {
        this.avisoTipoId = avisoTipoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AvisoCriteria that = (AvisoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(enviado, that.enviado) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(avisoTipoId, that.avisoTipoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        descricao,
        enviado,
        created,
        updated,
        avisoTipoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvisoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (enviado != null ? "enviado=" + enviado + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (avisoTipoId != null ? "avisoTipoId=" + avisoTipoId + ", " : "") +
            "}";
    }

}
