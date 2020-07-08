package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import org.tempestade.nucleo.domain.enumeration.LayerType;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.Layer} entity.
 */
public class LayerDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String description;

    @NotNull
    private String mapHost;

    @NotNull
    private LayerType layerType;

    @NotNull
    private String title;

    private String attribution;

    @NotNull
    private String workspace;

    @NotNull
    private Integer opacity;

    @NotNull
    private Boolean baselayer;

    @NotNull
    private Boolean tiled;

    @NotNull
    private Boolean gwcActived;

    @NotNull
    private Boolean active;

    @NotNull
    private Boolean enabled;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMapHost() {
        return mapHost;
    }

    public void setMapHost(String mapHost) {
        this.mapHost = mapHost;
    }

    public LayerType getLayerType() {
        return layerType;
    }

    public void setLayerType(LayerType layerType) {
        this.layerType = layerType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public Integer getOpacity() {
        return opacity;
    }

    public void setOpacity(Integer opacity) {
        this.opacity = opacity;
    }

    public Boolean isBaselayer() {
        return baselayer;
    }

    public void setBaselayer(Boolean baselayer) {
        this.baselayer = baselayer;
    }

    public Boolean isTiled() {
        return tiled;
    }

    public void setTiled(Boolean tiled) {
        this.tiled = tiled;
    }

    public Boolean isGwcActived() {
        return gwcActived;
    }

    public void setGwcActived(Boolean gwcActived) {
        this.gwcActived = gwcActived;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
        if (!(o instanceof LayerDTO)) {
            return false;
        }

        return id != null && id.equals(((LayerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LayerDTO{" +
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
