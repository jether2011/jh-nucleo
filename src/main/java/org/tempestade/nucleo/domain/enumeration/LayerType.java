package org.tempestade.nucleo.domain.enumeration;

/**
 * The LayerType enumeration.
 */
public enum LayerType {
    WMS("Web Map Service");

    private final String value;


    LayerType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
