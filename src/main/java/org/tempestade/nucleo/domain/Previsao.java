package org.tempestade.nucleo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Previsao.
 */
@Entity
@Table(name = "previsao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Previsao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @NotNull
    @Size(max = 255)
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @Size(max = 255)
    @Column(name = "texto_norte", length = 255)
    private String textoNorte;

    @Size(max = 255)
    @Column(name = "texto_norte_imagem", length = 255)
    private String textoNorteImagem;

    @Size(max = 255)
    @Column(name = "texto_nordeste", length = 255)
    private String textoNordeste;

    @Size(max = 255)
    @Column(name = "texto_nordeste_imagem", length = 255)
    private String textoNordesteImagem;

    @Size(max = 255)
    @Column(name = "texto_sul", length = 255)
    private String textoSul;

    @Size(max = 255)
    @Column(name = "texto_sul_imagem", length = 255)
    private String textoSulImagem;

    @Size(max = 255)
    @Column(name = "texto_sudeste", length = 255)
    private String textoSudeste;

    @Size(max = 255)
    @Column(name = "texto_sudeste_imagem", length = 255)
    private String textoSudesteImagem;

    @Size(max = 255)
    @Column(name = "texto_centro_oeste", length = 255)
    private String textoCentroOeste;

    @Size(max = 255)
    @Column(name = "texto_centro_oeste_imagem", length = 255)
    private String textoCentroOesteImagem;

    @Column(name = "enviado")
    private Boolean enviado;

    @Size(max = 255)
    @Column(name = "texto_brasil", length = 255)
    private String textoBrasil;

    @Size(max = 255)
    @Column(name = "texto_brasil_imagem", length = 255)
    private String textoBrasilImagem;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Previsao name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public Previsao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTextoNorte() {
        return textoNorte;
    }

    public Previsao textoNorte(String textoNorte) {
        this.textoNorte = textoNorte;
        return this;
    }

    public void setTextoNorte(String textoNorte) {
        this.textoNorte = textoNorte;
    }

    public String getTextoNorteImagem() {
        return textoNorteImagem;
    }

    public Previsao textoNorteImagem(String textoNorteImagem) {
        this.textoNorteImagem = textoNorteImagem;
        return this;
    }

    public void setTextoNorteImagem(String textoNorteImagem) {
        this.textoNorteImagem = textoNorteImagem;
    }

    public String getTextoNordeste() {
        return textoNordeste;
    }

    public Previsao textoNordeste(String textoNordeste) {
        this.textoNordeste = textoNordeste;
        return this;
    }

    public void setTextoNordeste(String textoNordeste) {
        this.textoNordeste = textoNordeste;
    }

    public String getTextoNordesteImagem() {
        return textoNordesteImagem;
    }

    public Previsao textoNordesteImagem(String textoNordesteImagem) {
        this.textoNordesteImagem = textoNordesteImagem;
        return this;
    }

    public void setTextoNordesteImagem(String textoNordesteImagem) {
        this.textoNordesteImagem = textoNordesteImagem;
    }

    public String getTextoSul() {
        return textoSul;
    }

    public Previsao textoSul(String textoSul) {
        this.textoSul = textoSul;
        return this;
    }

    public void setTextoSul(String textoSul) {
        this.textoSul = textoSul;
    }

    public String getTextoSulImagem() {
        return textoSulImagem;
    }

    public Previsao textoSulImagem(String textoSulImagem) {
        this.textoSulImagem = textoSulImagem;
        return this;
    }

    public void setTextoSulImagem(String textoSulImagem) {
        this.textoSulImagem = textoSulImagem;
    }

    public String getTextoSudeste() {
        return textoSudeste;
    }

    public Previsao textoSudeste(String textoSudeste) {
        this.textoSudeste = textoSudeste;
        return this;
    }

    public void setTextoSudeste(String textoSudeste) {
        this.textoSudeste = textoSudeste;
    }

    public String getTextoSudesteImagem() {
        return textoSudesteImagem;
    }

    public Previsao textoSudesteImagem(String textoSudesteImagem) {
        this.textoSudesteImagem = textoSudesteImagem;
        return this;
    }

    public void setTextoSudesteImagem(String textoSudesteImagem) {
        this.textoSudesteImagem = textoSudesteImagem;
    }

    public String getTextoCentroOeste() {
        return textoCentroOeste;
    }

    public Previsao textoCentroOeste(String textoCentroOeste) {
        this.textoCentroOeste = textoCentroOeste;
        return this;
    }

    public void setTextoCentroOeste(String textoCentroOeste) {
        this.textoCentroOeste = textoCentroOeste;
    }

    public String getTextoCentroOesteImagem() {
        return textoCentroOesteImagem;
    }

    public Previsao textoCentroOesteImagem(String textoCentroOesteImagem) {
        this.textoCentroOesteImagem = textoCentroOesteImagem;
        return this;
    }

    public void setTextoCentroOesteImagem(String textoCentroOesteImagem) {
        this.textoCentroOesteImagem = textoCentroOesteImagem;
    }

    public Boolean isEnviado() {
        return enviado;
    }

    public Previsao enviado(Boolean enviado) {
        this.enviado = enviado;
        return this;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    public String getTextoBrasil() {
        return textoBrasil;
    }

    public Previsao textoBrasil(String textoBrasil) {
        this.textoBrasil = textoBrasil;
        return this;
    }

    public void setTextoBrasil(String textoBrasil) {
        this.textoBrasil = textoBrasil;
    }

    public String getTextoBrasilImagem() {
        return textoBrasilImagem;
    }

    public Previsao textoBrasilImagem(String textoBrasilImagem) {
        this.textoBrasilImagem = textoBrasilImagem;
        return this;
    }

    public void setTextoBrasilImagem(String textoBrasilImagem) {
        this.textoBrasilImagem = textoBrasilImagem;
    }

    public Instant getCreated() {
        return created;
    }

    public Previsao created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public Previsao updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Previsao)) {
            return false;
        }
        return id != null && id.equals(((Previsao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Previsao{" +
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
