package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DetalleVenta implements Serializable {

    @SerializedName("pos")
    private int pos;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("valor")
    private double valor;

    @SerializedName("detalle2")
    private List<DetalleVenta2> detalleVenta2List;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
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

    public List<DetalleVenta2> getDetalleVenta2List() {
        return detalleVenta2List;
    }

    public void setDetalleVenta2List(List<DetalleVenta2> detalleVenta2List) {
        this.detalleVenta2List = detalleVenta2List;
    }
}
