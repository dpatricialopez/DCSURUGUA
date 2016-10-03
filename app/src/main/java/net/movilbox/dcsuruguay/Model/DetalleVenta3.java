package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by germangarcia on 2/08/16.
 */
public class DetalleVenta3 implements Serializable {

    @SerializedName("serie")
    private String serie;

    @SerializedName("valor_venta")
    private double valor_venta;

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public double getValor_venta() {
        return valor_venta;
    }

    public void setValor_venta(double valor_venta) {
        this.valor_venta = valor_venta;
    }

}
