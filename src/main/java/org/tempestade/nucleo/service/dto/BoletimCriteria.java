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
 * Criteria class for the {@link org.tempestade.nucleo.domain.Boletim} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.BoletimResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /boletims?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BoletimCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter descricao;

    private StringFilter texto;

    private StringFilter textoSms;

    private StringFilter imagem;

    private StringFilter assunto;

    private StringFilter textoParte2;

    private StringFilter textoParte3;

    private StringFilter subAssunto;

    private IntegerFilter naoExibirPagEmpresa;

    private BooleanFilter critico;

    private BooleanFilter aprovado;

    private BooleanFilter enviarSms;

    private BooleanFilter enviarEmail;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter planoRecursoId;

    public BoletimCriteria() {
    }

    public BoletimCriteria(BoletimCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.texto = other.texto == null ? null : other.texto.copy();
        this.textoSms = other.textoSms == null ? null : other.textoSms.copy();
        this.imagem = other.imagem == null ? null : other.imagem.copy();
        this.assunto = other.assunto == null ? null : other.assunto.copy();
        this.textoParte2 = other.textoParte2 == null ? null : other.textoParte2.copy();
        this.textoParte3 = other.textoParte3 == null ? null : other.textoParte3.copy();
        this.subAssunto = other.subAssunto == null ? null : other.subAssunto.copy();
        this.naoExibirPagEmpresa = other.naoExibirPagEmpresa == null ? null : other.naoExibirPagEmpresa.copy();
        this.critico = other.critico == null ? null : other.critico.copy();
        this.aprovado = other.aprovado == null ? null : other.aprovado.copy();
        this.enviarSms = other.enviarSms == null ? null : other.enviarSms.copy();
        this.enviarEmail = other.enviarEmail == null ? null : other.enviarEmail.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.planoRecursoId = other.planoRecursoId == null ? null : other.planoRecursoId.copy();
    }

    @Override
    public BoletimCriteria copy() {
        return new BoletimCriteria(this);
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

    public StringFilter getTexto() {
        return texto;
    }

    public void setTexto(StringFilter texto) {
        this.texto = texto;
    }

    public StringFilter getTextoSms() {
        return textoSms;
    }

    public void setTextoSms(StringFilter textoSms) {
        this.textoSms = textoSms;
    }

    public StringFilter getImagem() {
        return imagem;
    }

    public void setImagem(StringFilter imagem) {
        this.imagem = imagem;
    }

    public StringFilter getAssunto() {
        return assunto;
    }

    public void setAssunto(StringFilter assunto) {
        this.assunto = assunto;
    }

    public StringFilter getTextoParte2() {
        return textoParte2;
    }

    public void setTextoParte2(StringFilter textoParte2) {
        this.textoParte2 = textoParte2;
    }

    public StringFilter getTextoParte3() {
        return textoParte3;
    }

    public void setTextoParte3(StringFilter textoParte3) {
        this.textoParte3 = textoParte3;
    }

    public StringFilter getSubAssunto() {
        return subAssunto;
    }

    public void setSubAssunto(StringFilter subAssunto) {
        this.subAssunto = subAssunto;
    }

    public IntegerFilter getNaoExibirPagEmpresa() {
        return naoExibirPagEmpresa;
    }

    public void setNaoExibirPagEmpresa(IntegerFilter naoExibirPagEmpresa) {
        this.naoExibirPagEmpresa = naoExibirPagEmpresa;
    }

    public BooleanFilter getCritico() {
        return critico;
    }

    public void setCritico(BooleanFilter critico) {
        this.critico = critico;
    }

    public BooleanFilter getAprovado() {
        return aprovado;
    }

    public void setAprovado(BooleanFilter aprovado) {
        this.aprovado = aprovado;
    }

    public BooleanFilter getEnviarSms() {
        return enviarSms;
    }

    public void setEnviarSms(BooleanFilter enviarSms) {
        this.enviarSms = enviarSms;
    }

    public BooleanFilter getEnviarEmail() {
        return enviarEmail;
    }

    public void setEnviarEmail(BooleanFilter enviarEmail) {
        this.enviarEmail = enviarEmail;
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
        final BoletimCriteria that = (BoletimCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(texto, that.texto) &&
            Objects.equals(textoSms, that.textoSms) &&
            Objects.equals(imagem, that.imagem) &&
            Objects.equals(assunto, that.assunto) &&
            Objects.equals(textoParte2, that.textoParte2) &&
            Objects.equals(textoParte3, that.textoParte3) &&
            Objects.equals(subAssunto, that.subAssunto) &&
            Objects.equals(naoExibirPagEmpresa, that.naoExibirPagEmpresa) &&
            Objects.equals(critico, that.critico) &&
            Objects.equals(aprovado, that.aprovado) &&
            Objects.equals(enviarSms, that.enviarSms) &&
            Objects.equals(enviarEmail, that.enviarEmail) &&
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
        texto,
        textoSms,
        imagem,
        assunto,
        textoParte2,
        textoParte3,
        subAssunto,
        naoExibirPagEmpresa,
        critico,
        aprovado,
        enviarSms,
        enviarEmail,
        created,
        updated,
        planoRecursoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BoletimCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (texto != null ? "texto=" + texto + ", " : "") +
                (textoSms != null ? "textoSms=" + textoSms + ", " : "") +
                (imagem != null ? "imagem=" + imagem + ", " : "") +
                (assunto != null ? "assunto=" + assunto + ", " : "") +
                (textoParte2 != null ? "textoParte2=" + textoParte2 + ", " : "") +
                (textoParte3 != null ? "textoParte3=" + textoParte3 + ", " : "") +
                (subAssunto != null ? "subAssunto=" + subAssunto + ", " : "") +
                (naoExibirPagEmpresa != null ? "naoExibirPagEmpresa=" + naoExibirPagEmpresa + ", " : "") +
                (critico != null ? "critico=" + critico + ", " : "") +
                (aprovado != null ? "aprovado=" + aprovado + ", " : "") +
                (enviarSms != null ? "enviarSms=" + enviarSms + ", " : "") +
                (enviarEmail != null ? "enviarEmail=" + enviarEmail + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (planoRecursoId != null ? "planoRecursoId=" + planoRecursoId + ", " : "") +
            "}";
    }

}
