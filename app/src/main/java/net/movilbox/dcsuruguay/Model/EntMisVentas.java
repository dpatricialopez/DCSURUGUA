package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by germangarcia on 2/08/16.
 */
public class EntMisVentas implements Serializable {

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("valor")
    private double valor;

    @SerializedName("detalle")
    private List<DetalleVenta> detalleList;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public List<DetalleVenta> getDetalleList() {
        return detalleList;
    }

    public void setDetalleList(List<DetalleVenta> detalleList) {
        this.detalleList = detalleList;
    }
}
