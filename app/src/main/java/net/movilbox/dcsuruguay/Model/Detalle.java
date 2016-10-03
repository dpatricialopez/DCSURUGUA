package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Detalle implements Serializable {

    @SerializedName("nombre_usuario")
    private String nombre_usuario;

    @SerializedName("hora")
    private String hora;

    @SerializedName("pn")
    private String pn;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("total")
    private double total;

    public Detalle(String nombre_usuario, String hora, String pn, int cantidad, double total) {
        this.nombre_usuario = nombre_usuario;
        this.hora = hora;
        this.pn = pn;
        this.cantidad = cantidad;
        this.total = total;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
