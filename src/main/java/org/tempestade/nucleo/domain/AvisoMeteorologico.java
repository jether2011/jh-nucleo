package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AvisoMeteorologico.
 */
@Entity
@Table(name = "aviso_meteorologico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AvisoMeteorologico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nome", length = 255, nullable = false, unique = true)
    private String nome;

    @Size(max = 255)
    @Column(name = "assunto", length = 255)
    private String assunto;

    @NotNull
    @Column(name = "inicio", nullable = false)
    private Instant inicio;

    @NotNull
    @Column(name = "fim", nullable = false)
    private Instant fim;

    @NotNull
    @Size(max = 255)
    @Column(name = "texto", length = 255, nullable = false)
    private String texto;

    @NotNull
    @Size(max = 255)
    @Column(name = "imagem", length = 255, nullable = false)
    private String imagem;

    @Size(max = 255)
    @Column(name = "imagem_assinatura", length = 255)
    private String imagemAssinatura;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "avisoMeteorologicos", allowSetters = true)
    private PlanoRecurso planoRecurso;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public AvisoMeteorologico nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAssunto() {
        return assunto;
    }

    public AvisoMeteorologico assunto(String assunto) {
        this.assunto = assunto;
        return this;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Instant getInicio() {
        return inicio;
    }

    public AvisoMeteorologico inicio(Instant inicio) {
        this.inicio = inicio;
        return this;
    }

    public void setInicio(Instant inicio) {
        this.inicio = inicio;
    }

    public Instant getFim() {
        return fim;
    }

    public AvisoMeteorologico fim(Instant fim) {
        this.fim = fim;
        return this;
    }

    public void setFim(Instant fim) {
        this.fim = fim;
    }

    public String getTexto() {
        return texto;
    }

    public AvisoMeteorologico texto(String texto) {
        this.texto = texto;
        return this;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getImagem() {
        return imagem;
    }

    public AvisoMeteorologico imagem(String imagem) {
        this.imagem = imagem;
        return this;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getImagemAssinatura() {
        return imagemAssinatura;
    }

    public AvisoMeteorologico imagemAssinatura(String imagemAssinatura) {
        this.imagemAssinatura = imagemAssinatura;
        return this;
    }

    public void setImagemAssinatura(String imagemAssinatura) {
        this.imagemAssinatura = imagemAssinatura;
    }

    public Instant getCreated() {
        return created;
    }

    public AvisoMeteorologico created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public AvisoMeteorologico updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public PlanoRecurso getPlanoRecurso() {
        return planoRecurso;
    }

    public AvisoMeteorologico planoRecurso(PlanoRecurso planoRecurso) {
        this.planoRecurso = planoRecurso;
        return this;
    }

    public void setPlanoRecurso(PlanoRecurso planoRecurso) {
        this.planoRecurso = planoRecurso;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvisoMeteorologico)) {
            return false;
        }
        return id != null && id.equals(((AvisoMeteorologico) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvisoMeteorologico{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", assunto='" + getAssunto() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", fim='" + getFim() + "'" +
            ", texto='" + getTexto() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", imagemAssinatura='" + getImagemAssinatura() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
