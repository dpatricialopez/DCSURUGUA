package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 11/07/16.
 */
public class EntZona {

    @SerializedName("id")
    private int id;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("id_territorio")
    private int id_territorio;

    @SerializedName("tipo_tabla")
    private int tipo_tabla;

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

    public int getId_territorio() {
        return id_territorio;
    }

    public void setId_territorio(int id_territorio) {
        this.id_territorio = id_territorio;
    }

    public int getTipo_tabla() {
        return tipo_tabla;
    }

    public void setTipo_tabla(int tipo_tabla) {
        this.tipo_tabla = tipo_tabla;
    }

}
