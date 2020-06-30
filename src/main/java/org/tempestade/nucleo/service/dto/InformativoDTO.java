package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.Informativo} entity.
 */
public class InformativoDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String nome;

    private String descricao;

    @Size(max = 255)
    private String texto;

    private Integer qtdEmail;

    @Size(max = 255)
    private String imagem;

    @Size(max = 255)
    private String arquivoEml;

    @Size(max = 255)
    private String assunto;

    @Size(max = 255)
    private String subAssunto;

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

    public Integer getQtdEmail() {
        return qtdEmail;
    }

    public void setQtdEmail(Integer qtdEmail) {
        this.qtdEmail = qtdEmail;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getArquivoEml() {
        return arquivoEml;
    }

    public void setArquivoEml(String arquivoEml) {
        this.arquivoEml = arquivoEml;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getSubAssunto() {
        return subAssunto;
    }

    public void setSubAssunto(String subAssunto) {
        this.subAssunto = subAssunto;
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
        if (!(o instanceof InformativoDTO)) {
            return false;
        }

        return id != null && id.equals(((InformativoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InformativoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", texto='" + getTexto() + "'" +
            ", qtdEmail=" + getQtdEmail() +
            ", imagem='" + getImagem() + "'" +
            ", arquivoEml='" + getArquivoEml() + "'" +
            ", assunto='" + getAssunto() + "'" +
            ", subAssunto='" + getSubAssunto() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", planoRecursoId=" + getPlanoRecursoId() +
            "}";
    }
}
