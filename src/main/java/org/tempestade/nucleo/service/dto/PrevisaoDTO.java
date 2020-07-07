package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.Previsao} entity.
 */
public class PrevisaoDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String descricao;

    @Size(max = 255)
    private String textoNorte;

    @Size(max = 255)
    private String textoNorteImagem;

    @Size(max = 255)
    private String textoNordeste;

    @Size(max = 255)
    private String textoNordesteImagem;

    @Size(max = 255)
    private String textoSul;

    @Size(max = 255)
    private String textoSulImagem;

    @Size(max = 255)
    private String textoSudeste;

    @Size(max = 255)
    private String textoSudesteImagem;

    @Size(max = 255)
    private String textoCentroOeste;

    @Size(max = 255)
    private String textoCentroOesteImagem;

    private Boolean enviado;

    @Size(max = 255)
    private String textoBrasil;

    @Size(max = 255)
    private String textoBrasilImagem;

    @NotNull
    private Instant created;

    private Instant updated;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTextoNorte() {
        return textoNorte;
    }

    public void setTextoNorte(String textoNorte) {
        this.textoNorte = textoNorte;
    }

    public String getTextoNorteImagem() {
        return textoNorteImagem;
    }

    public void setTextoNorteImagem(String textoNorteImagem) {
        this.textoNorteImagem = textoNorteImagem;
    }

    public String getTextoNordeste() {
        return textoNordeste;
    }

    public void setTextoNordeste(String textoNordeste) {
        this.textoNordeste = textoNordeste;
    }

    public String getTextoNordesteImagem() {
        return textoNordesteImagem;
    }

    public void setTextoNordesteImagem(String textoNordesteImagem) {
        this.textoNordesteImagem = textoNordesteImagem;
    }

    public String getTextoSul() {
        return textoSul;
    }

    public void setTextoSul(String textoSul) {
        this.textoSul = textoSul;
    }

    public String getTextoSulImagem() {
        return textoSulImagem;
    }

    public void setTextoSulImagem(String textoSulImagem) {
        this.textoSulImagem = textoSulImagem;
    }

    public String getTextoSudeste() {
        return textoSudeste;
    }

    public void setTextoSudeste(String textoSudeste) {
        this.textoSudeste = textoSudeste;
    }

    public String getTextoSudesteImagem() {
        return textoSudesteImagem;
    }

    public void setTextoSudesteImagem(String textoSudesteImagem) {
        this.textoSudesteImagem = textoSudesteImagem;
    }

    public String getTextoCentroOeste() {
        return textoCentroOeste;
    }

    public void setTextoCentroOeste(String textoCentroOeste) {
        this.textoCentroOeste = textoCentroOeste;
    }

    public String getTextoCentroOesteImagem() {
        return textoCentroOesteImagem;
    }

    public void setTextoCentroOesteImagem(String textoCentroOesteImagem) {
        this.textoCentroOesteImagem = textoCentroOesteImagem;
    }

    public Boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    public String getTextoBrasil() {
        return textoBrasil;
    }

    public void setTextoBrasil(String textoBrasil) {
        this.textoBrasil = textoBrasil;
    }

    public String getTextoBrasilImagem() {
        return textoBrasilImagem;
    }

    public void setTextoBrasilImagem(String textoBrasilImagem) {
        this.textoBrasilImagem = textoBrasilImagem;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrevisaoDTO)) {
            return false;
        }

        return id != null && id.equals(((PrevisaoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrevisaoDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", textoNorte='" + getTextoNorte() + "'" +
            ", textoNorteImagem='" + getTextoNorteImagem() + "'" +
            ", textoNordeste='" + getTextoNordeste() + "'" +
            ", textoNordesteImagem='" + getTextoNordesteImagem() + "'" +
            ", textoSul='" + getTextoSul() + "'" +
            ", textoSulImagem='" + getTextoSulImagem() + "'" +
            ", textoSudeste='" + getTextoSudeste() + "'" +
            ", textoSudesteImagem='" + getTextoSudesteImagem() + "'" +
            ", textoCentroOeste='" + getTextoCentroOeste() + "'" +
            ", textoCentroOesteImagem='" + getTextoCentroOesteImagem() + "'" +
            ", enviado='" + isEnviado() + "'" +
            ", textoBrasil='" + getTextoBrasil() + "'" +
            ", textoBrasilImagem='" + getTextoBrasilImagem() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
