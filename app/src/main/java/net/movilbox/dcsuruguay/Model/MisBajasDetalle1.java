package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class MisBajasDetalle1 implements Serializable {

    @SerializedName("sku")
    private String sku;

    @SerializedName("cant_sku")
    private int cant_sku;

    @SerializedName("tipon")
    private int tipon;

    @SerializedName("tipo")
    private String tipo;

    @SerializedName("producto")
    private String producto;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("id_referencia")
    private int id_referencia;


    @SerializedName("mis_bajas_detalle2")
    private List<MisBajasDetalle2> misBajasDetalle2List;

    @SerializedName("detalle_series")
    private List<MisBajasDetalle2> detalle_series;


    public List<MisBajasDetalle2> getDetalle_series() {
        return detalle_series;
    }

    public void setDetalle_series(List<MisBajasDetalle2> detalle_series) {
        this.detalle_series = detalle_series;
    }


    public MisBajasDetalle1(String sku, int cant_sku) {
        this.sku = sku;
        this.cant_sku = cant_sku;
    }

    public List<MisBajasDetalle2> getMisBajasDetalle2List() {
        return misBajasDetalle2List;
    }

    public void setMisBajasDetalle2List(List<MisBajasDetalle2> misBajasDetalle2List) {
        this.misBajasDetalle2List = misBajasDetalle2List;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getCant_sku() {
        return cant_sku;
    }

    public void setCant_sku(int cant_sku) {
        this.cant_sku = cant_sku;
    }

    public int getTipon() {
        return tipon;
    }

    public void setTipon(int tipon) {
        this.tipon = tipon;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_referencia() {
        return id_referencia;
    }

    public void setId_referencia(int id_referencia) {
        this.id_referencia = id_referencia;
    }
}
