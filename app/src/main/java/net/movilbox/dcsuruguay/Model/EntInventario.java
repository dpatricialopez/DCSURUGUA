package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 11/07/16.
 */
public class EntInventario {

    @SerializedName("id")
    private int id;

    @SerializedName("id_referencia")
    private int id_referencia;

    @SerializedName("serie")
    private String serie;

    @SerializedName("paquete")
    private int paquete;

    @SerializedName("id_vendedor")
    private int id_vendedor;

    @SerializedName("distri")
    private int distri;

    @SerializedName("tipo_pro")
    private int tipo_pro;

    @SerializedName("tipo_tabla")
    private int tipo_tabla;

    @SerializedName("estado_accion")
    private int estado_accion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_referencia() {
        return id_referencia;
    }

    public void setId_referencia(int id_referencia) {
        this.id_referencia = id_referencia;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getPaquete() {
        return paquete;
    }

    public void setPaquete(int paquete) {
        this.paquete = paquete;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public int getDistri() {
        return distri;
    }

    public void setDistri(int distri) {
        this.distri = distri;
    }

    public int getTipo_pro() {
        return tipo_pro;
    }

    public void setTipo_pro(int tipo_pro) {
        this.tipo_pro = tipo_pro;
    }

    public int getTipo_tabla() {
        return tipo_tabla;
    }

    public void setTipo_tabla(int tipo_tabla) {
        this.tipo_tabla = tipo_tabla;
    }

    public int getEstado_accion() {
        return estado_accion;
    }

    public void setEstado_accion(int estado_accion) {
        this.estado_accion = estado_accion;
    }
}
