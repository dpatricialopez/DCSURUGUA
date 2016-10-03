package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 11/07/16.
 */
public class EntRefSims {

    @SerializedName("id")
    private int id;

    @SerializedName("producto")
    private String producto;

    @SerializedName("pn")
    private String pn;

    @SerializedName("precio_referencia")
    private double precio_referencia;

    @SerializedName("precio_publico")
    private double precio_publico;

    @SerializedName("estado_accion")
    private int estado_accion;

    @SerializedName("tipo_tabla")
    private int tipo_tabla;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public double getPrecio_referencia() {
        return precio_referencia;
    }

    public void setPrecio_referencia(double precio_referencia) {
        this.precio_referencia = precio_referencia;
    }

    public double getPrecio_publico() {
        return precio_publico;
    }

    public void setPrecio_publico(double precio_publico) {
        this.precio_publico = precio_publico;
    }

    public int getEstado_accion() {
        return estado_accion;
    }

    public void setEstado_accion(int estado_accion) {
        this.estado_accion = estado_accion;
    }

    public int getTipo_tabla() {
        return tipo_tabla;
    }

    public void setTipo_tabla(int tipo_tabla) {
        this.tipo_tabla = tipo_tabla;
    }
}
