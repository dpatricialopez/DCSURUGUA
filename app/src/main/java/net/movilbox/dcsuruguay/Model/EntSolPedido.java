package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by germangarcia on 29/07/16.
 */
public class EntSolPedido {

    @SerializedName("id")
    private int id;

    @SerializedName("nombre_bodega")
    private String nombre_bodega;

    @SerializedName("tipo_bodega")
    private int tipo_bodega;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("hora")
    private String hora;

    @SerializedName("nom_bod")
    private String nom_bod;

    public String getNom_bod() {
        return nom_bod;
    }

    public void setNom_bod(String nom_bod) {
        this.nom_bod = nom_bod;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @SerializedName("referencias")
    private List<EntReferenciaSol> entReferenciaSols;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_bodega() {
        return nombre_bodega;
    }

    public void setNombre_bodega(String nombre_bodega) {
        this.nombre_bodega = nombre_bodega;
    }

    public int getTipo_bodega() {
        return tipo_bodega;
    }

    public void setTipo_bodega(int tipo_bodega) {
        this.tipo_bodega = tipo_bodega;
    }

    public List<EntReferenciaSol> getEntReferenciaSols() {
        return entReferenciaSols;
    }

    public void setEntReferenciaSols(List<EntReferenciaSol> entReferenciaSols) {
        this.entReferenciaSols = entReferenciaSols;
    }
}
