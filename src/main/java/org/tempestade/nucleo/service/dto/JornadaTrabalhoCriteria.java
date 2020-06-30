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
 * Criteria class for the {@link org.tempestade.nucleo.domain.JornadaTrabalho} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.JornadaTrabalhoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /jornada-trabalhos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class JornadaTrabalhoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter descricao;

    private StringFilter horainicio;

    private StringFilter duracao;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter planoId;

    private LongFilter diaSemanaId;

    public JornadaTrabalhoCriteria() {
    }

    public JornadaTrabalhoCriteria(JornadaTrabalhoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.horainicio = other.horainicio == null ? null : other.horainicio.copy();
        this.duracao = other.duracao == null ? null : other.duracao.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.planoId = other.planoId == null ? null : other.planoId.copy();
        this.diaSemanaId = other.diaSemanaId == null ? null : other.diaSemanaId.copy();
    }

    @Override
    public JornadaTrabalhoCriteria copy() {
        return new JornadaTrabalhoCriteria(this);
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

    public StringFilter getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(StringFilter horainicio) {
        this.horainicio = horainicio;
    }

    public StringFilter getDuracao() {
        return duracao;
    }

    public void setDuracao(StringFilter duracao) {
        this.duracao = duracao;
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

    public LongFilter getPlanoId() {
        return planoId;
    }

    public void setPlanoId(LongFilter planoId) {
        this.planoId = planoId;
    }

    public LongFilter getDiaSemanaId() {
        return diaSemanaId;
    }

    public void setDiaSemanaId(LongFilter diaSemanaId) {
        this.diaSemanaId = diaSemanaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final JornadaTrabalhoCriteria that = (JornadaTrabalhoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(horainicio, that.horainicio) &&
            Objects.equals(duracao, that.duracao) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(planoId, that.planoId) &&
            Objects.equals(diaSemanaId, that.diaSemanaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        descricao,
        horainicio,
        duracao,
        created,
        updated,
        planoId,
        diaSemanaId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JornadaTrabalhoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (horainicio != null ? "horainicio=" + horainicio + ", " : "") +
                (duracao != null ? "duracao=" + duracao + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (planoId != null ? "planoId=" + planoId + ", " : "") +
                (diaSemanaId != null ? "diaSemanaId=" + diaSemanaId + ", " : "") +
            "}";
    }

}
