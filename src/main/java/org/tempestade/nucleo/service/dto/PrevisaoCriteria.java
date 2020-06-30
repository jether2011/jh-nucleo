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
 * Criteria class for the {@link org.tempestade.nucleo.domain.Previsao} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.PrevisaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /previsaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PrevisaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter descricao;

    private StringFilter textoNorte;

    private StringFilter textoNorteImagem;

    private StringFilter textoNordeste;

    private StringFilter textoNordesteImagem;

    private StringFilter textoSul;

    private StringFilter textoSulImagem;

    private StringFilter textoSudeste;

    private StringFilter textoSudesteImagem;

    private StringFilter textoCentroOeste;

    private StringFilter textoCentroOesteImagem;

    private BooleanFilter enviado;

    private StringFilter textoBrasil;

    private StringFilter textoBrasilImagem;

    private InstantFilter created;

    private InstantFilter updated;

    public PrevisaoCriteria() {
    }

    public PrevisaoCriteria(PrevisaoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.textoNorte = other.textoNorte == null ? null : other.textoNorte.copy();
        this.textoNorteImagem = other.textoNorteImagem == null ? null : other.textoNorteImagem.copy();
        this.textoNordeste = other.textoNordeste == null ? null : other.textoNordeste.copy();
        this.textoNordesteImagem = other.textoNordesteImagem == null ? null : other.textoNordesteImagem.copy();
        this.textoSul = other.textoSul == null ? null : other.textoSul.copy();
        this.textoSulImagem = other.textoSulImagem == null ? null : other.textoSulImagem.copy();
        this.textoSudeste = other.textoSudeste == null ? null : other.textoSudeste.copy();
        this.textoSudesteImagem = other.textoSudesteImagem == null ? null : other.textoSudesteImagem.copy();
        this.textoCentroOeste = other.textoCentroOeste == null ? null : other.textoCentroOeste.copy();
        this.textoCentroOesteImagem = other.textoCentroOesteImagem == null ? null : other.textoCentroOesteImagem.copy();
        this.enviado = other.enviado == null ? null : other.enviado.copy();
        this.textoBrasil = other.textoBrasil == null ? null : other.textoBrasil.copy();
        this.textoBrasilImagem = other.textoBrasilImagem == null ? null : other.textoBrasilImagem.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
    }

    @Override
    public PrevisaoCriteria copy() {
        return new PrevisaoCriteria(this);
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

    public StringFilter getTextoNorte() {
        return textoNorte;
    }

    public void setTextoNorte(StringFilter textoNorte) {
        this.textoNorte = textoNorte;
    }

    public StringFilter getTextoNorteImagem() {
        return textoNorteImagem;
    }

    public void setTextoNorteImagem(StringFilter textoNorteImagem) {
        this.textoNorteImagem = textoNorteImagem;
    }

    public StringFilter getTextoNordeste() {
        return textoNordeste;
    }

    public void setTextoNordeste(StringFilter textoNordeste) {
        this.textoNordeste = textoNordeste;
    }

    public StringFilter getTextoNordesteImagem() {
        return textoNordesteImagem;
    }

    public void setTextoNordesteImagem(StringFilter textoNordesteImagem) {
        this.textoNordesteImagem = textoNordesteImagem;
    }

    public StringFilter getTextoSul() {
        return textoSul;
    }

    public void setTextoSul(StringFilter textoSul) {
        this.textoSul = textoSul;
    }

    public StringFilter getTextoSulImagem() {
        return textoSulImagem;
    }

    public void setTextoSulImagem(StringFilter textoSulImagem) {
        this.textoSulImagem = textoSulImagem;
    }

    public StringFilter getTextoSudeste() {
        return textoSudeste;
    }

    public void setTextoSudeste(StringFilter textoSudeste) {
        this.textoSudeste = textoSudeste;
    }

    public StringFilter getTextoSudesteImagem() {
        return textoSudesteImagem;
    }

    public void setTextoSudesteImagem(StringFilter textoSudesteImagem) {
        this.textoSudesteImagem = textoSudesteImagem;
    }

    public StringFilter getTextoCentroOeste() {
        return textoCentroOeste;
    }

    public void setTextoCentroOeste(StringFilter textoCentroOeste) {
        this.textoCentroOeste = textoCentroOeste;
    }

    public StringFilter getTextoCentroOesteImagem() {
        return textoCentroOesteImagem;
    }

    public void setTextoCentroOesteImagem(StringFilter textoCentroOesteImagem) {
        this.textoCentroOesteImagem = textoCentroOesteImagem;
    }

    public BooleanFilter getEnviado() {
        return enviado;
    }

    public void setEnviado(BooleanFilter enviado) {
        this.enviado = enviado;
    }

    public StringFilter getTextoBrasil() {
        return textoBrasil;
    }

    public void setTextoBrasil(StringFilter textoBrasil) {
        this.textoBrasil = textoBrasil;
    }

    public StringFilter getTextoBrasilImagem() {
        return textoBrasilImagem;
    }

    public void setTextoBrasilImagem(StringFilter textoBrasilImagem) {
        this.textoBrasilImagem = textoBrasilImagem;
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
        final PrevisaoCriteria that = (PrevisaoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(textoNorte, that.textoNorte) &&
            Objects.equals(textoNorteImagem, that.textoNorteImagem) &&
            Objects.equals(textoNordeste, that.textoNordeste) &&
            Objects.equals(textoNordesteImagem, that.textoNordesteImagem) &&
            Objects.equals(textoSul, that.textoSul) &&
            Objects.equals(textoSulImagem, that.textoSulImagem) &&
            Objects.equals(textoSudeste, that.textoSudeste) &&
            Objects.equals(textoSudesteImagem, that.textoSudesteImagem) &&
            Objects.equals(textoCentroOeste, that.textoCentroOeste) &&
            Objects.equals(textoCentroOesteImagem, that.textoCentroOesteImagem) &&
            Objects.equals(enviado, that.enviado) &&
            Objects.equals(textoBrasil, that.textoBrasil) &&
            Objects.equals(textoBrasilImagem, that.textoBrasilImagem) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        descricao,
        textoNorte,
        textoNorteImagem,
        textoNordeste,
        textoNordesteImagem,
        textoSul,
        textoSulImagem,
        textoSudeste,
        textoSudesteImagem,
        textoCentroOeste,
        textoCentroOesteImagem,
        enviado,
        textoBrasil,
        textoBrasilImagem,
        created,
        updated
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrevisaoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (textoNorte != null ? "textoNorte=" + textoNorte + ", " : "") +
                (textoNorteImagem != null ? "textoNorteImagem=" + textoNorteImagem + ", " : "") +
                (textoNordeste != null ? "textoNordeste=" + textoNordeste + ", " : "") +
                (textoNordesteImagem != null ? "textoNordesteImagem=" + textoNordesteImagem + ", " : "") +
                (textoSul != null ? "textoSul=" + textoSul + ", " : "") +
                (textoSulImagem != null ? "textoSulImagem=" + textoSulImagem + ", " : "") +
                (textoSudeste != null ? "textoSudeste=" + textoSudeste + ", " : "") +
                (textoSudesteImagem != null ? "textoSudesteImagem=" + textoSudesteImagem + ", " : "") +
                (textoCentroOeste != null ? "textoCentroOeste=" + textoCentroOeste + ", " : "") +
                (textoCentroOesteImagem != null ? "textoCentroOesteImagem=" + textoCentroOesteImagem + ", " : "") +
                (enviado != null ? "enviado=" + enviado + ", " : "") +
                (textoBrasil != null ? "textoBrasil=" + textoBrasil + ", " : "") +
                (textoBrasilImagem != null ? "textoBrasilImagem=" + textoBrasilImagem + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
            "}";
    }

}
