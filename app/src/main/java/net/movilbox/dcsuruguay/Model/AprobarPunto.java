package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AprobarPunto implements Serializable {

    @SerializedName("id_soli")
    private int id_soli;

    @SerializedName("tipo_solicitud")
    private int tipo_solicitud;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("solicitud")
    private String solicitud;

    @SerializedName("estado")
    private String estado;

    @SerializedName("idpdv")
    private int idpdv;

    @SerializedName("nombre_vende")
    private String nombre_vende;

    @SerializedName("id_pos")
    private int id_pos;

    @SerializedName("fecha_accion")
    private String fecha_accion;

    @SerializedName("hora_accion")
    private String hora_accion;

    @SerializedName("nombre_punto")
    private String nombre_punto;

    @SerializedName("detalle")
    private List<DetalleAprobacion> detalleAprobacionList;

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdpdv() {
        return idpdv;
    }

    public void setIdpdv(int idpdv) {
        this.idpdv = idpdv;
    }

    public String getNombre_vende() {
        return nombre_vende;
    }

    public void setNombre_vende(String nombre_vende) {
        this.nombre_vende = nombre_vende;
    }

    public int getId_pos() {
        return id_pos;
    }

    public void setId_pos(int id_pos) {
        this.id_pos = id_pos;
    }

    public List<DetalleAprobacion> getDetalleAprobacionList() {
        return detalleAprobacionList;
    }

    public void setDetalleAprobacionList(List<DetalleAprobacion> detalleAprobacionList) {
        this.detalleAprobacionList = detalleAprobacionList;
    }

    public String getFecha_accion() {
        return fecha_accion;
    }

    public void setFecha_accion(String fecha_accion) {
        this.fecha_accion = fecha_accion;
    }

    public String getHora_accion() {
        return hora_accion;
    }

    public void setHora_accion(String hora_accion) {
        this.hora_accion = hora_accion;
    }

    public String getNombre_punto() {
        return nombre_punto;
    }

    public void setNombre_punto(String nombre_punto) {
        this.nombre_punto = nombre_punto;
    }
}
