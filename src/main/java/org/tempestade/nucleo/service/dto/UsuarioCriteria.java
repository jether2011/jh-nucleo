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
 * Criteria class for the {@link org.tempestade.nucleo.domain.Usuario} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.UsuarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /usuarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UsuarioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter descricao;

    private StringFilter email;

    private StringFilter senha;

    private StringFilter cnpj;

    private StringFilter cpf;

    private StringFilter cep;

    private StringFilter endereco;

    private IntegerFilter numero;

    private StringFilter bairro;

    private StringFilter cidade;

    private StringFilter estado;

    private StringFilter telefone;

    private StringFilter fax;

    private StringFilter celular;

    private StringFilter detalhe;

    private BooleanFilter bloqueado;

    private StringFilter complemento;

    private BooleanFilter naoPodeExcluir;

    private InstantFilter ultimoAcesso;

    private StringFilter senhaFirebase;

    private InstantFilter created;

    private InstantFilter updated;

    public UsuarioCriteria() {
    }

    public UsuarioCriteria(UsuarioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.senha = other.senha == null ? null : other.senha.copy();
        this.cnpj = other.cnpj == null ? null : other.cnpj.copy();
        this.cpf = other.cpf == null ? null : other.cpf.copy();
        this.cep = other.cep == null ? null : other.cep.copy();
        this.endereco = other.endereco == null ? null : other.endereco.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.bairro = other.bairro == null ? null : other.bairro.copy();
        this.cidade = other.cidade == null ? null : other.cidade.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.telefone = other.telefone == null ? null : other.telefone.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.celular = other.celular == null ? null : other.celular.copy();
        this.detalhe = other.detalhe == null ? null : other.detalhe.copy();
        this.bloqueado = other.bloqueado == null ? null : other.bloqueado.copy();
        this.complemento = other.complemento == null ? null : other.complemento.copy();
        this.naoPodeExcluir = other.naoPodeExcluir == null ? null : other.naoPodeExcluir.copy();
        this.ultimoAcesso = other.ultimoAcesso == null ? null : other.ultimoAcesso.copy();
        this.senhaFirebase = other.senhaFirebase == null ? null : other.senhaFirebase.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
    }

    @Override
    public UsuarioCriteria copy() {
        return new UsuarioCriteria(this);
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

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getSenha() {
        return senha;
    }

    public void setSenha(StringFilter senha) {
        this.senha = senha;
    }

    public StringFilter getCnpj() {
        return cnpj;
    }

    public void setCnpj(StringFilter cnpj) {
        this.cnpj = cnpj;
    }

    public StringFilter getCpf() {
        return cpf;
    }

    public void setCpf(StringFilter cpf) {
        this.cpf = cpf;
    }

    public StringFilter getCep() {
        return cep;
    }

    public void setCep(StringFilter cep) {
        this.cep = cep;
    }

    public StringFilter getEndereco() {
        return endereco;
    }

    public void setEndereco(StringFilter endereco) {
        this.endereco = endereco;
    }

    public IntegerFilter getNumero() {
        return numero;
    }

    public void setNumero(IntegerFilter numero) {
        this.numero = numero;
    }

    public StringFilter getBairro() {
        return bairro;
    }

    public void setBairro(StringFilter bairro) {
        this.bairro = bairro;
    }

    public StringFilter getCidade() {
        return cidade;
    }

    public void setCidade(StringFilter cidade) {
        this.cidade = cidade;
    }

    public StringFilter getEstado() {
        return estado;
    }

    public void setEstado(StringFilter estado) {
        this.estado = estado;
    }

    public StringFilter getTelefone() {
        return telefone;
    }

    public void setTelefone(StringFilter telefone) {
        this.telefone = telefone;
    }

    public StringFilter getFax() {
        return fax;
    }

    public void setFax(StringFilter fax) {
        this.fax = fax;
    }

    public StringFilter getCelular() {
        return celular;
    }

    public void setCelular(StringFilter celular) {
        this.celular = celular;
    }

    public StringFilter getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(StringFilter detalhe) {
        this.detalhe = detalhe;
    }

    public BooleanFilter getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(BooleanFilter bloqueado) {
        this.bloqueado = bloqueado;
    }

    public StringFilter getComplemento() {
        return complemento;
    }

    public void setComplemento(StringFilter complemento) {
        this.complemento = complemento;
    }

    public BooleanFilter getNaoPodeExcluir() {
        return naoPodeExcluir;
    }

    public void setNaoPodeExcluir(BooleanFilter naoPodeExcluir) {
        this.naoPodeExcluir = naoPodeExcluir;
    }

    public InstantFilter getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(InstantFilter ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public StringFilter getSenhaFirebase() {
        return senhaFirebase;
    }

    public void setSenhaFirebase(StringFilter senhaFirebase) {
        this.senhaFirebase = senhaFirebase;
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
        final UsuarioCriteria that = (UsuarioCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(email, that.email) &&
            Objects.equals(senha, that.senha) &&
            Objects.equals(cnpj, that.cnpj) &&
            Objects.equals(cpf, that.cpf) &&
            Objects.equals(cep, that.cep) &&
            Objects.equals(endereco, that.endereco) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(bairro, that.bairro) &&
            Objects.equals(cidade, that.cidade) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(telefone, that.telefone) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(celular, that.celular) &&
            Objects.equals(detalhe, that.detalhe) &&
            Objects.equals(bloqueado, that.bloqueado) &&
            Objects.equals(complemento, that.complemento) &&
            Objects.equals(naoPodeExcluir, that.naoPodeExcluir) &&
            Objects.equals(ultimoAcesso, that.ultimoAcesso) &&
            Objects.equals(senhaFirebase, that.senhaFirebase) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        descricao,
        email,
        senha,
        cnpj,
        cpf,
        cep,
        endereco,
        numero,
        bairro,
        cidade,
        estado,
        telefone,
        fax,
        celular,
        detalhe,
        bloqueado,
        complemento,
        naoPodeExcluir,
        ultimoAcesso,
        senhaFirebase,
        created,
        updated
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (senha != null ? "senha=" + senha + ", " : "") +
                (cnpj != null ? "cnpj=" + cnpj + ", " : "") +
                (cpf != null ? "cpf=" + cpf + ", " : "") +
                (cep != null ? "cep=" + cep + ", " : "") +
                (endereco != null ? "endereco=" + endereco + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (bairro != null ? "bairro=" + bairro + ", " : "") +
                (cidade != null ? "cidade=" + cidade + ", " : "") +
                (estado != null ? "estado=" + estado + ", " : "") +
                (telefone != null ? "telefone=" + telefone + ", " : "") +
                (fax != null ? "fax=" + fax + ", " : "") +
                (celular != null ? "celular=" + celular + ", " : "") +
                (detalhe != null ? "detalhe=" + detalhe + ", " : "") +
                (bloqueado != null ? "bloqueado=" + bloqueado + ", " : "") +
                (complemento != null ? "complemento=" + complemento + ", " : "") +
                (naoPodeExcluir != null ? "naoPodeExcluir=" + naoPodeExcluir + ", " : "") +
                (ultimoAcesso != null ? "ultimoAcesso=" + ultimoAcesso + ", " : "") +
                (senhaFirebase != null ? "senhaFirebase=" + senhaFirebase + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
            "}";
    }

}
