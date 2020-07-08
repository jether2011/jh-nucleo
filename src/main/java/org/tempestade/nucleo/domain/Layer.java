package org.tempestade.nucleo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import org.tempestade.nucleo.domain.enumeration.LayerType;

/**
 * A Layer.
 */
@Entity
@Table(name = "layer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Layer implements Serializable {

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
    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @NotNull
    @Column(name = "map_host", nullable = false)
    private String mapHost;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "layer_type", nullable = false)
    private LayerType layerType;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "attribution")
    private String attribution;

    @NotNull
    @Column(name = "workspace", nullable = false)
    private String workspace;

    @NotNull
    @Column(name = "opacity", nullable = false)
    private Integer opacity;

    @NotNull
    @Column(name = "baselayer", nullable = false)
    private Boolean baselayer;

    @NotNull
    @Column(name = "tiled", nullable = false)
    private Boolean tiled;

    @NotNull
    @Column(name = "gwc_actived", nullable = false)
    private Boolean gwcActived;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

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

    public Layer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Layer description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMapHost() {
        return mapHost;
    }

    public Layer mapHost(String mapHost) {
        this.mapHost = mapHost;
        return this;
    }

    public void setMapHost(String mapHost) {
        this.mapHost = mapHost;
    }

    public LayerType getLayerType() {
        return layerType;
    }

    public Layer layerType(LayerType layerType) {
        this.layerType = layerType;
        return this;
    }

    public void setLayerType(LayerType layerType) {
        this.layerType = layerType;
    }

    public String getTitle() {
        return title;
    }

    public Layer title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAttribution() {
        return attribution;
    }

    public Layer attribution(String attribution) {
        this.attribution = attribution;
        return this;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getWorkspace() {
        return workspace;
    }

    public Layer workspace(String workspace) {
        this.workspace = workspace;
        return this;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public Integer getOpacity() {
        return opacity;
    }

    public Layer opacity(Integer opacity) {
        this.opacity = opacity;
        return this;
    }

    public void setOpacity(Integer opacity) {
        this.opacity = opacity;
    }

    public Boolean isBaselayer() {
        return baselayer;
    }

    public Layer baselayer(Boolean baselayer) {
        this.baselayer = baselayer;
        return this;
    }

    public void setBaselayer(Boolean baselayer) {
        this.baselayer = baselayer;
    }

    public Boolean isTiled() {
        return tiled;
    }

    public Layer tiled(Boolean tiled) {
        this.tiled = tiled;
        return this;
    }

    public void setTiled(Boolean tiled) {
        this.tiled = tiled;
    }

    public Boolean isGwcActived() {
        return gwcActived;
    }

    public Layer gwcActived(Boolean gwcActived) {
        this.gwcActived = gwcActived;
        return this;
    }

    public void setGwcActived(Boolean gwcActived) {
        this.gwcActived = gwcActived;
    }

    public Boolean isActive() {
        return active;
    }

    public Layer active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Layer enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Instant getCreated() {
        return created;
    }

    public Layer created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public Layer updated(Instant updated) {
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
        if (!(o instanceof Layer)) {
            return false;
        }
        return id != null && id.equals(((Layer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Layer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", mapHost='" + getMapHost() + "'" +
            ", layerType='" + getLayerType() + "'" +
            ", title='" + getTitle() + "'" +
            ", attribution='" + getAttribution() + "'" +
            ", workspace='" + getWorkspace() + "'" +
            ", opacity=" + getOpacity() +
            ", baselayer='" + isBaselayer() + "'" +
            ", tiled='" + isTiled() + "'" +
            ", gwcActived='" + isGwcActived() + "'" +
            ", active='" + isActive() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
