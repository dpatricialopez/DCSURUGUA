package net.movilbox.dcsuruguay.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Pedidos implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("estado")
    private String estado;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("detalle")
    private List<Detalle> detalles;

    @SerializedName("tipo_producto")
    private String tipo_producto;

    @SerializedName("cant_pro")
    private int cant_pro;

    public String getTipo_producto() {
        return tipo_producto;
    }

    public void setTipo_producto(String tipo_producto) {
        this.tipo_producto = tipo_producto;
    }

    public int getCant_pro() {
        return cant_pro;
    }

    public void setCant_pro(int cant_pro) {
        this.cant_pro = cant_pro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }
}
