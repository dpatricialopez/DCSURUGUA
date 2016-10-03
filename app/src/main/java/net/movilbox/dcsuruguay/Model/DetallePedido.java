package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DetallePedido implements Serializable {

    @SerializedName("nombre_sku")
    private String nombre_sku;

    @SerializedName("id_sku")
    private int id_sku;

    @SerializedName("cant_pedido")
    private int cant_pedido;

    @SerializedName("total_pedido")
    private double total_pedido;

    @SerializedName("cant_pedido_p")
    private int cant_pedido_p;

    @SerializedName("total_pedido_p")
    private double total_pedido_p;

    @SerializedName("tipo_pro")
    private String tipo_pro;

    @SerializedName("nroPedido")
    private int nroPedido;

    public int getNroPedido() {
        return nroPedido;
    }

    public void setNroPedido(int nroPedido) {
        this.nroPedido = nroPedido;
    }

    public String getNombre_sku() {
        return nombre_sku;
    }

    public void setNombre_sku(String nombre_sku) {
        this.nombre_sku = nombre_sku;
    }

    public int getId_sku() {
        return id_sku;
    }

    public void setId_sku(int id_sku) {
        this.id_sku = id_sku;
    }

    public int getCant_pedido() {
        return cant_pedido;
    }

    public void setCant_pedido(int cant_pedido) {
        this.cant_pedido = cant_pedido;
    }

    public double getTotal_pedido() {
        return total_pedido;
    }

    public void setTotal_pedido(double total_pedido) {
        this.total_pedido = total_pedido;
    }

    public int getCant_pedido_p() {
        return cant_pedido_p;
    }

    public void setCant_pedido_p(int cant_pedido_p) {
        this.cant_pedido_p = cant_pedido_p;
    }

    public double getTotal_pedido_p() {
        return total_pedido_p;
    }

    public void setTotal_pedido_p(double total_pedido_p) {
        this.total_pedido_p = total_pedido_p;
    }

    public String getTipo_pro() {
        return tipo_pro;
    }

    public void setTipo_pro(String tipo_pro) {
        this.tipo_pro = tipo_pro;
    }
}
