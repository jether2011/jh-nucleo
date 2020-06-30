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
 * Criteria class for the {@link org.tempestade.nucleo.domain.AvisoMeteorologico} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.AvisoMeteorologicoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /aviso-meteorologicos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AvisoMeteorologicoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter assunto;

    private InstantFilter inicio;

    private InstantFilter fim;

    private StringFilter texto;

    private StringFilter imagem;

    private StringFilter imagemAssinatura;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter planoRecursoId;

    public AvisoMeteorologicoCriteria() {
    }

    public AvisoMeteorologicoCriteria(AvisoMeteorologicoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.assunto = other.assunto == null ? null : other.assunto.copy();
        this.inicio = other.inicio == null ? null : other.inicio.copy();
        this.fim = other.fim == null ? null : other.fim.copy();
        this.texto = other.texto == null ? null : other.texto.copy();
        this.imagem = other.imagem == null ? null : other.imagem.copy();
        this.imagemAssinatura = other.imagemAssinatura == null ? null : other.imagemAssinatura.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.planoRecursoId = other.planoRecursoId == null ? null : other.planoRecursoId.copy();
    }

    @Override
    public AvisoMeteorologicoCriteria copy() {
        return new AvisoMeteorologicoCriteria(this);
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

    public StringFilter getAssunto() {
        return assunto;
    }

    public void setAssunto(StringFilter assunto) {
        this.assunto = assunto;
    }

    public InstantFilter getInicio() {
        return inicio;
    }

    public void setInicio(InstantFilter inicio) {
        this.inicio = inicio;
    }

    public InstantFilter getFim() {
        return fim;
    }

    public void setFim(InstantFilter fim) {
        this.fim = fim;
    }

    public StringFilter getTexto() {
        return texto;
    }

    public void setTexto(StringFilter texto) {
        this.texto = texto;
    }

    public StringFilter getImagem() {
        return imagem;
    }

    public void setImagem(StringFilter imagem) {
        this.imagem = imagem;
    }

    public StringFilter getImagemAssinatura() {
        return imagemAssinatura;
    }

    public void setImagemAssinatura(StringFilter imagemAssinatura) {
        this.imagemAssinatura = imagemAssinatura;
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
        final AvisoMeteorologicoCriteria that = (AvisoMeteorologicoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(assunto, that.assunto) &&
            Objects.equals(inicio, that.inicio) &&
            Objects.equals(fim, that.fim) &&
            Objects.equals(texto, that.texto) &&
            Objects.equals(imagem, that.imagem) &&
            Objects.equals(imagemAssinatura, that.imagemAssinatura) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(planoRecursoId, that.planoRecursoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        assunto,
        inicio,
        fim,
        texto,
        imagem,
        imagemAssinatura,
        created,
        updated,
        planoRecursoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvisoMeteorologicoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (assunto != null ? "assunto=" + assunto + ", " : "") +
                (inicio != null ? "inicio=" + inicio + ", " : "") +
                (fim != null ? "fim=" + fim + ", " : "") +
                (texto != null ? "texto=" + texto + ", " : "") +
                (imagem != null ? "imagem=" + imagem + ", " : "") +
                (imagemAssinatura != null ? "imagemAssinatura=" + imagemAssinatura + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (planoRecursoId != null ? "planoRecursoId=" + planoRecursoId + ", " : "") +
            "}";
    }

}
