package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InventariarProducto implements Serializable, Cloneable {

    public InventariarProducto() {
    }

    @SerializedName("id_pro_sim")
    private int id_pro_sim;

    @SerializedName("id_referencia")
    private int id_referencia;

    @SerializedName("producto")
    private String producto;

    @SerializedName("serie")
    private String serie;

    @SerializedName("fecha_venta")
    private String fecha_venta;

    public boolean isCheck;

    @SerializedName("tipo")
    private int tipo;

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getId_pro_sim() {
        return id_pro_sim;
    }

    public void setId_pro_sim(int id_pro_sim) {
        this.id_pro_sim = id_pro_sim;
    }

    public int getId_referencia() {
        return id_referencia;
    }

    public void setId_referencia(int id_referencia) {
        this.id_referencia = id_referencia;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }
}
