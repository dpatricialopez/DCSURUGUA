package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by germangarcia on 2/08/16.
 */
public class DetalleVenta2 implements Serializable {

    @SerializedName("idrefe")
    private int idrefe;

    @SerializedName("producto")
    private String producto;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("id_pos")
    private int id_pos;

    @SerializedName("valor")
    private double valor;

    @SerializedName("detalle3")
    private List<DetalleVenta3> detalleVenta3List;


    public int getIdrefe() {
        return idrefe;
    }

    public void setIdrefe(int idrefe) {
        this.idrefe = idrefe;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

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

    public int getId_pos() {
        return id_pos;
    }

    public void setId_pos(int id_pos) {
        this.id_pos = id_pos;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public List<DetalleVenta3> getDetalleVenta3List() {
        return detalleVenta3List;
    }

    public void setDetalleVenta3List(List<DetalleVenta3> detalleVenta3List) {
        this.detalleVenta3List = detalleVenta3List;
    }
}
