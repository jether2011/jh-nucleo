package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.AvisoMeteorologico} entity.
 */
public class AvisoMeteorologicoDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String nome;

    @Size(max = 255)
    private String assunto;

    @NotNull
    private Instant inicio;

    @NotNull
    private Instant fim;

    @NotNull
    @Size(max = 255)
    private String texto;

    @NotNull
    @Size(max = 255)
    private String imagem;

    @Size(max = 255)
    private String imagemAssinatura;

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

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Instant getInicio() {
        return inicio;
    }

    public void setInicio(Instant inicio) {
        this.inicio = inicio;
    }

    public Instant getFim() {
        return fim;
    }

    public void setFim(Instant fim) {
        this.fim = fim;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getImagemAssinatura() {
        return imagemAssinatura;
    }

    public void setImagemAssinatura(String imagemAssinatura) {
        this.imagemAssinatura = imagemAssinatura;
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
        if (!(o instanceof AvisoMeteorologicoDTO)) {
            return false;
        }

        return id != null && id.equals(((AvisoMeteorologicoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvisoMeteorologicoDTO{" +
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
            ", planoRecursoId=" + getPlanoRecursoId() +
            "}";
    }
}
