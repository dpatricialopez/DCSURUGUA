package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;


public class EntEstandar extends EntNomenclaturas {

    @SerializedName("id")
    private int id;

    @SerializedName("descripcion")
    public String descripcion;

    @SerializedName("estado_accion")
    public int estado_accion;

    @SerializedName("id_muni")
    private int id_muni;

    @SerializedName("id_depto")
    private int id_depto;

    @SerializedName("departamento")
    private int departamento;

    @SerializedName("id_categoria")
    private int id_categoria;

    @SerializedName("tipo_tabla")
    public int tipoTabla;

    public EntEstandar() {}

    public EntEstandar(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getTipoTabla() {
        return tipoTabla;
    }

    public void setTipoTabla(int tipoTabla) {
        this.tipoTabla = tipoTabla;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado_accion() {
        return estado_accion;
    }

    public void setEstado_accion(int estado_accion) {
        this.estado_accion = estado_accion;
    }

    public int getId_muni() {
        return id_muni;
    }

    public void setId_muni(int id_muni) {
        this.id_muni = id_muni;
    }

    public int getId_depto() {
        return id_depto;
    }

    public void setId_depto(int id_depto) {
        this.id_depto = id_depto;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
