package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by germangarcia on 2/08/16.
 */
public class EntSolicitudVendedor implements Serializable {

    @SerializedName("id_soli")
    private int id_soli;

    @SerializedName("tipo_solicitud")
    private int tipo_solicitud;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("solicitud")
    private String solicitud;

    @SerializedName("estado")
    private int estado;

    @SerializedName("idpdv")
    private int idpdv;

    @SerializedName("nombre_punto")
    private String nombre_punto;

    @SerializedName("nombre_vende")
    private String nombre_vende;

    public int getId_soli() {
        return id_soli;
    }

    public void setId_soli(int id_soli) {
        this.id_soli = id_soli;
    }

    public int getTipo_solicitud() {
        return tipo_solicitud;
    }

    public void setTipo_solicitud(int tipo_solicitud) {
        this.tipo_solicitud = tipo_solicitud;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdpdv() {
        return idpdv;
    }

    public void setIdpdv(int idpdv) {
        this.idpdv = idpdv;
    }

    public String getNombre_punto() {
        return nombre_punto;
    }

    public void setNombre_punto(String nombre_punto) {
        this.nombre_punto = nombre_punto;
    }

    public String getNombre_vende() {
        return nombre_vende;
    }

    public void setNombre_vende(String nombre_vende) {
        this.nombre_vende = nombre_vende;
    }
}
