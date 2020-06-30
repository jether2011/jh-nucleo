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
 * Criteria class for the {@link org.tempestade.nucleo.domain.Contato} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.ContatoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contatoes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContatoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter descricao;

    private StringFilter email;

    private StringFilter celular;

    private BooleanFilter ativo;

    private BooleanFilter contatoAlertaTelefonico;

    private IntegerFilter prioridade;

    private StringFilter horaLigacaoInicial;

    private StringFilter horaLigacaoFinal;

    private InstantFilter created;

    private InstantFilter updated;

    public ContatoCriteria() {
    }

    public ContatoCriteria(ContatoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.celular = other.celular == null ? null : other.celular.copy();
        this.ativo = other.ativo == null ? null : other.ativo.copy();
        this.contatoAlertaTelefonico = other.contatoAlertaTelefonico == null ? null : other.contatoAlertaTelefonico.copy();
        this.prioridade = other.prioridade == null ? null : other.prioridade.copy();
        this.horaLigacaoInicial = other.horaLigacaoInicial == null ? null : other.horaLigacaoInicial.copy();
        this.horaLigacaoFinal = other.horaLigacaoFinal == null ? null : other.horaLigacaoFinal.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
    }

    @Override
    public ContatoCriteria copy() {
        return new ContatoCriteria(this);
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

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getCelular() {
        return celular;
    }

    public void setCelular(StringFilter celular) {
        this.celular = celular;
    }

    public BooleanFilter getAtivo() {
        return ativo;
    }

    public void setAtivo(BooleanFilter ativo) {
        this.ativo = ativo;
    }

    public BooleanFilter getContatoAlertaTelefonico() {
        return contatoAlertaTelefonico;
    }

    public void setContatoAlertaTelefonico(BooleanFilter contatoAlertaTelefonico) {
        this.contatoAlertaTelefonico = contatoAlertaTelefonico;
    }

    public IntegerFilter getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(IntegerFilter prioridade) {
        this.prioridade = prioridade;
    }

    public StringFilter getHoraLigacaoInicial() {
        return horaLigacaoInicial;
    }

    public void setHoraLigacaoInicial(StringFilter horaLigacaoInicial) {
        this.horaLigacaoInicial = horaLigacaoInicial;
    }

    public StringFilter getHoraLigacaoFinal() {
        return horaLigacaoFinal;
    }

    public void setHoraLigacaoFinal(StringFilter horaLigacaoFinal) {
        this.horaLigacaoFinal = horaLigacaoFinal;
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
        final ContatoCriteria that = (ContatoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(email, that.email) &&
            Objects.equals(celular, that.celular) &&
            Objects.equals(ativo, that.ativo) &&
            Objects.equals(contatoAlertaTelefonico, that.contatoAlertaTelefonico) &&
            Objects.equals(prioridade, that.prioridade) &&
            Objects.equals(horaLigacaoInicial, that.horaLigacaoInicial) &&
            Objects.equals(horaLigacaoFinal, that.horaLigacaoFinal) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        descricao,
        email,
        celular,
        ativo,
        contatoAlertaTelefonico,
        prioridade,
        horaLigacaoInicial,
        horaLigacaoFinal,
        created,
        updated
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContatoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (celular != null ? "celular=" + celular + ", " : "") +
                (ativo != null ? "ativo=" + ativo + ", " : "") +
                (contatoAlertaTelefonico != null ? "contatoAlertaTelefonico=" + contatoAlertaTelefonico + ", " : "") +
                (prioridade != null ? "prioridade=" + prioridade + ", " : "") +
                (horaLigacaoInicial != null ? "horaLigacaoInicial=" + horaLigacaoInicial + ", " : "") +
                (horaLigacaoFinal != null ? "horaLigacaoFinal=" + horaLigacaoFinal + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
            "}";
    }

}
