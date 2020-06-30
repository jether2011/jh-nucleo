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
 * Criteria class for the {@link org.tempestade.nucleo.domain.Empresa} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.EmpresaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmpresaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter descricao;

    private StringFilter email;

    private StringFilter titulo;

    private StringFilter nomeReduzido;

    private StringFilter logo;

    private StringFilter apelido;

    private StringFilter observacao;

    private InstantFilter created;

    private InstantFilter updated;

    public EmpresaCriteria() {
    }

    public EmpresaCriteria(EmpresaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.nomeReduzido = other.nomeReduzido == null ? null : other.nomeReduzido.copy();
        this.logo = other.logo == null ? null : other.logo.copy();
        this.apelido = other.apelido == null ? null : other.apelido.copy();
        this.observacao = other.observacao == null ? null : other.observacao.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
    }

    @Override
    public EmpresaCriteria copy() {
        return new EmpresaCriteria(this);
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

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public StringFilter getNomeReduzido() {
        return nomeReduzido;
    }

    public void setNomeReduzido(StringFilter nomeReduzido) {
        this.nomeReduzido = nomeReduzido;
    }

    public StringFilter getLogo() {
        return logo;
    }

    public void setLogo(StringFilter logo) {
        this.logo = logo;
    }

    public StringFilter getApelido() {
        return apelido;
    }

    public void setApelido(StringFilter apelido) {
        this.apelido = apelido;
    }

    public StringFilter getObservacao() {
        return observacao;
    }

    public void setObservacao(StringFilter observacao) {
        this.observacao = observacao;
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
        final EmpresaCriteria that = (EmpresaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(email, that.email) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(nomeReduzido, that.nomeReduzido) &&
            Objects.equals(logo, that.logo) &&
            Objects.equals(apelido, that.apelido) &&
            Objects.equals(observacao, that.observacao) &&
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
        titulo,
        nomeReduzido,
        logo,
        apelido,
        observacao,
        created,
        updated
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpresaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (nomeReduzido != null ? "nomeReduzido=" + nomeReduzido + ", " : "") +
                (logo != null ? "logo=" + logo + ", " : "") +
                (apelido != null ? "apelido=" + apelido + ", " : "") +
                (observacao != null ? "observacao=" + observacao + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
            "}";
    }

}
