package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoriasEstandar implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("descripcion")
    public String descripcion;

    @SerializedName("descripcion1")
    public String descripcion1;

    @SerializedName("descripcion2")
    public String descripcion2;

    @SerializedName("descripcion3")
    public String descripcion3;

    @SerializedName("subcategorias")
    public List<Subcategorias> listSubCategoria;

    @SerializedName("estado_accion")
    public int estado_accion;

    public int getEstado_accion() {
        return estado_accion;
    }

    public void setEstado_accion(int estado_accion) {
        this.estado_accion = estado_accion;
    }

    public List<Subcategorias> getListSubCategoria() {
        return listSubCategoria;
    }

    public void setListSubCategoria(List<Subcategorias> listSubCategoria) {
        this.listSubCategoria = listSubCategoria;
    }

    public CategoriasEstandar(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
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

    public String getDescripcion1() {
        return descripcion1;
    }

    public void setDescripcion1(String descripcion1) {
        this.descripcion1 = descripcion1;
    }

    public String getDescripcion2() {
        return descripcion2;
    }

    public void setDescripcion2(String descripcion2) {
        this.descripcion2 = descripcion2;
    }

    public String getDescripcion3() {
        return descripcion3;
    }

    public void setDescripcion3(String descripcion3) {
        this.descripcion3 = descripcion3;
    }

    @Override
    public String toString() {
        return descripcion;
    }



}
