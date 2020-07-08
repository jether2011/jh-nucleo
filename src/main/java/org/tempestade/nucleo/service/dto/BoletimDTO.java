package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.Boletim} entity.
 */
public class BoletimDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String nome;

    private String descricao;

    @Size(max = 255)
    private String texto;

    @Size(max = 255)
    private String textoSms;

    @Size(max = 255)
    private String imagem;

    @Size(max = 255)
    private String assunto;

    @Size(max = 255)
    private String textoParte2;

    @Size(max = 255)
    private String textoParte3;

    @Size(max = 255)
    private String subAssunto;

    private Integer naoExibirPagEmpresa;

    private Boolean critico;

    private Boolean aprovado;

    private Boolean enviarSms;

    private Boolean enviarEmail;

    @NotNull
    private Instant created;

    private Instant updated;


    private Long planoRecursoId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTextoSms() {
        return textoSms;
    }

    public void setTextoSms(String textoSms) {
        this.textoSms = textoSms;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getTextoParte2() {
        return textoParte2;
    }

    public void setTextoParte2(String textoParte2) {
        this.textoParte2 = textoParte2;
    }

    public String getTextoParte3() {
        return textoParte3;
    }

    public void setTextoParte3(String textoParte3) {
        this.textoParte3 = textoParte3;
    }

    public String getSubAssunto() {
        return subAssunto;
    }

    public void setSubAssunto(String subAssunto) {
        this.subAssunto = subAssunto;
    }

    public Integer getNaoExibirPagEmpresa() {
        return naoExibirPagEmpresa;
    }

    public void setNaoExibirPagEmpresa(Integer naoExibirPagEmpresa) {
        this.naoExibirPagEmpresa = naoExibirPagEmpresa;
    }

    public Boolean isCritico() {
        return critico;
    }

    public void setCritico(Boolean critico) {
        this.critico = critico;
    }

    public Boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(Boolean aprovado) {
        this.aprovado = aprovado;
    }

    public Boolean isEnviarSms() {
        return enviarSms;
    }

    public void setEnviarSms(Boolean enviarSms) {
        this.enviarSms = enviarSms;
    }

    public Boolean isEnviarEmail() {
        return enviarEmail;
    }

    public void setEnviarEmail(Boolean enviarEmail) {
        this.enviarEmail = enviarEmail;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Long getPlanoRecursoId() {
        return planoRecursoId;
    }

    public void setPlanoRecursoId(Long planoRecursoId) {
        this.planoRecursoId = planoRecursoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoletimDTO)) {
            return false;
        }

        return id != null && id.equals(((BoletimDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BoletimDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", texto='" + getTexto() + "'" +
            ", textoSms='" + getTextoSms() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", assunto='" + getAssunto() + "'" +
            ", textoParte2='" + getTextoParte2() + "'" +
            ", textoParte3='" + getTextoParte3() + "'" +
            ", subAssunto='" + getSubAssunto() + "'" +
            ", naoExibirPagEmpresa=" + getNaoExibirPagEmpresa() +
            ", critico='" + isCritico() + "'" +
            ", aprovado='" + isAprovado() + "'" +
            ", enviarSms='" + isEnviarSms() + "'" +
            ", enviarEmail='" + isEnviarEmail() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", planoRecursoId=" + getPlanoRecursoId() +
            "}";
    }
}
