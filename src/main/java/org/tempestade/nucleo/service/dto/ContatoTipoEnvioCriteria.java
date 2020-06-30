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
 * Criteria class for the {@link org.tempestade.nucleo.domain.ContatoTipoEnvio} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.ContatoTipoEnvioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contato-tipo-envios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContatoTipoEnvioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter descricao;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter contatoId;

    private LongFilter tipoEnvioId;

    public ContatoTipoEnvioCriteria() {
    }

    public ContatoTipoEnvioCriteria(ContatoTipoEnvioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.contatoId = other.contatoId == null ? null : other.contatoId.copy();
        this.tipoEnvioId = other.tipoEnvioId == null ? null : other.tipoEnvioId.copy();
    }

    @Override
    public ContatoTipoEnvioCriteria copy() {
        return new ContatoTipoEnvioCriteria(this);
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

    public LongFilter getContatoId() {
        return contatoId;
    }

    public void setContatoId(LongFilter contatoId) {
        this.contatoId = contatoId;
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
        final ContatoTipoEnvioCriteria that = (ContatoTipoEnvioCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(contatoId, that.contatoId) &&
            Objects.equals(tipoEnvioId, that.tipoEnvioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        descricao,
        created,
        updated,
        contatoId,
        tipoEnvioId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContatoTipoEnvioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (contatoId != null ? "contatoId=" + contatoId + ", " : "") +
                (tipoEnvioId != null ? "tipoEnvioId=" + tipoEnvioId + ", " : "") +
            "}";
    }

}
