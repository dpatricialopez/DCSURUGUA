package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 29/07/16.
 */
public class EntReferenciaSol {

    @SerializedName("id_bodega")
    private int id_bodega;

    @SerializedName("id_referencia")
    private int id_referencia;

    @SerializedName("producto")
    private String producto;

    @SerializedName("total")
    private int total;

    @SerializedName("tipo_bodega")
    private int tipo_bodega_re;

    @SerializedName("tipo_ref")
    private int tipo_ref;

    private int cantidadSol;

    public int getCantidadSol() {
        return cantidadSol;
    }

    public void setCantidadSol(int cantidadSol) {
        this.cantidadSol = cantidadSol;
    }

    public int getId_bodega() {
        return id_bodega;
    }

    public void setId_bodega(int id_bodega) {
        this.id_bodega = id_bodega;
    }

    public int getId_referencia() {
        return id_referencia;
    }

    public void setId_referencia(int id_referencia) {
        this.id_referencia = id_referencia;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTipo_bodega() {
        return tipo_bodega_re;
    }

    public void setTipo_bodega(int tipo_bodega) {
        this.tipo_bodega_re = tipo_bodega;
    }

    public int getTipo_ref() {
        return tipo_ref;
    }

    public void setTipo_ref(int tipo_ref) {
        this.tipo_ref = tipo_ref;
    }
}
