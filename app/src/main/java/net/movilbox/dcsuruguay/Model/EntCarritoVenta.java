package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by germangarcia on 21/07/16.
 */
public class EntCarritoVenta implements Serializable {

    @SerializedName("idAutoCarrito")
    private int idAutoCarrito;

    @SerializedName("idReferencia")
    private int idReferencia;

    @SerializedName("tipoProducto")
    private int tipoProducto;

    @SerializedName("valorRefe")
    private double valorRefe;

    @SerializedName("valorDirecto")
    private double valorDirecto;

    @SerializedName("serie")
    private String serie;

    @SerializedName("idPunto")
    private int idPunto;

    @SerializedName("idPaquete")
    private int idPaquete;

    @SerializedName("estadoVenta")
    private int estadoVenta;

    @SerializedName("tipoVenta")
    private int tipoVenta;

    @SerializedName("latitud")
    private double latitud;

    @SerializedName("longitud")
    private double longitud;

    private int cantidad;

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getEstadoVenta() {
        return estadoVenta;
    }

    public void setEstadoVenta(int estadoVenta) {
        this.estadoVenta = estadoVenta;
    }

    public int getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(int tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public int getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(int idPunto) {
        this.idPunto = idPunto;
    }

    public int getIdAutoCarrito() {
        return idAutoCarrito;
    }

    public void setIdAutoCarrito(int idAutoCarrito) {
        this.idAutoCarrito = idAutoCarrito;
    }

    public int getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(int tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public double getValorRefe() {
        return valorRefe;
    }

    public void setValorRefe(double valorRefe) {
        this.valorRefe = valorRefe;
    }

    public double getValorDirecto() {
        return valorDirecto;
    }

    public void setValorDirecto(double valorDirecto) {
        this.valorDirecto = valorDirecto;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(int idReferencia) {
        this.idReferencia = idReferencia;
    }

}
