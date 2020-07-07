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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link org.tempestade.nucleo.domain.Consolidacao} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.ConsolidacaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /consolidacaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConsolidacaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter descricao;

    private LocalDateFilter data;

    private StringFilter texto;

    private IntegerFilter qtdEmail;

    private StringFilter imagem;

    private StringFilter arquivoEml;

    private StringFilter assunto;

    private StringFilter subAssunto;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter planoRecursoId;

    public ConsolidacaoCriteria() {
    }

    public ConsolidacaoCriteria(ConsolidacaoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.texto = other.texto == null ? null : other.texto.copy();
        this.qtdEmail = other.qtdEmail == null ? null : other.qtdEmail.copy();
        this.imagem = other.imagem == null ? null : other.imagem.copy();
        this.arquivoEml = other.arquivoEml == null ? null : other.arquivoEml.copy();
        this.assunto = other.assunto == null ? null : other.assunto.copy();
        this.subAssunto = other.subAssunto == null ? null : other.subAssunto.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.planoRecursoId = other.planoRecursoId == null ? null : other.planoRecursoId.copy();
    }

    @Override
    public ConsolidacaoCriteria copy() {
        return new ConsolidacaoCriteria(this);
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

    public LocalDateFilter getData() {
        return data;
    }

    public void setData(LocalDateFilter data) {
        this.data = data;
    }

    public StringFilter getTexto() {
        return texto;
    }

    public void setTexto(StringFilter texto) {
        this.texto = texto;
    }

    public IntegerFilter getQtdEmail() {
        return qtdEmail;
    }

    public void setQtdEmail(IntegerFilter qtdEmail) {
        this.qtdEmail = qtdEmail;
    }

    public StringFilter getImagem() {
        return imagem;
    }

    public void setImagem(StringFilter imagem) {
        this.imagem = imagem;
    }

    public StringFilter getArquivoEml() {
        return arquivoEml;
    }

    public void setArquivoEml(StringFilter arquivoEml) {
        this.arquivoEml = arquivoEml;
    }

    public StringFilter getAssunto() {
        return assunto;
    }

    public void setAssunto(StringFilter assunto) {
        this.assunto = assunto;
    }

    public StringFilter getSubAssunto() {
        return subAssunto;
    }

    public void setSubAssunto(StringFilter subAssunto) {
        this.subAssunto = subAssunto;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ConsolidacaoCriteria that = (ConsolidacaoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(data, that.data) &&
            Objects.equals(texto, that.texto) &&
            Objects.equals(qtdEmail, that.qtdEmail) &&
            Objects.equals(imagem, that.imagem) &&
            Objects.equals(arquivoEml, that.arquivoEml) &&
            Objects.equals(assunto, that.assunto) &&
            Objects.equals(subAssunto, that.subAssunto) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(planoRecursoId, that.planoRecursoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        descricao,
        data,
        texto,
        qtdEmail,
        imagem,
        arquivoEml,
        assunto,
        subAssunto,
        created,
        updated,
        planoRecursoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsolidacaoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (data != null ? "data=" + data + ", " : "") +
                (texto != null ? "texto=" + texto + ", " : "") +
                (qtdEmail != null ? "qtdEmail=" + qtdEmail + ", " : "") +
                (imagem != null ? "imagem=" + imagem + ", " : "") +
                (arquivoEml != null ? "arquivoEml=" + arquivoEml + ", " : "") +
                (assunto != null ? "assunto=" + assunto + ", " : "") +
                (subAssunto != null ? "subAssunto=" + subAssunto + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (planoRecursoId != null ? "planoRecursoId=" + planoRecursoId + ", " : "") +
            "}";
    }

}
