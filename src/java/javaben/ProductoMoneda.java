package javaben;

import java.io.Serializable;

/**
 *
 * @author nunez-pc
 */
public class ProductoMoneda implements Serializable{
    private String moneda;
    private float precio;
    private float nuevoPrecio;
    private int webId;

    public ProductoMoneda() {
    }

    public ProductoMoneda(String moneda, float precio, float nuevoPrecio, int webId) {
        this.moneda = moneda;
        this.precio = precio;
        this.nuevoPrecio = nuevoPrecio;
        this.webId = webId;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getNuevoPrecio() {
        return nuevoPrecio;
    }

    public void setNuevoPrecio(float nuevoPrecio) {
        this.nuevoPrecio = nuevoPrecio;
    }

    public int getWebId() {
        return webId;
    }

    public void setWebId(int webId) {
        this.webId = webId;
    }
    
    
    
}
