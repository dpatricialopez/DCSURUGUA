package net.movilbox.dcsuruguay.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PedidosEntrega implements Serializable {

    @SerializedName("nroPedido")
    private int nroPedido;

    @SerializedName("cant_pedido")
    private int cant_pedido;

    @SerializedName("cant_pedido_p")
    private int cant_pedido_p;

    @SerializedName("total_pedido_p")
    private double total_pedido_p;

    @SerializedName("fecha_pedido")
    private String fecha_pedido;

    @SerializedName("hora_pedido")
    private String hora_pedido;

    @SerializedName("estado")
    private String estado;

    @SerializedName("fecha_entrega")
    private String fecha_entrega;

    @SerializedName("igv")
    private double igv;

    @SerializedName("sub_total")
    private double sub_total;

    @SerializedName("nombre_usu")
    private String nombre_usu;

    @SerializedName("total_impueto_igv")
    private double total_impueto_igv;

    @SerializedName("idpos")
    private int idpos;

    public int getIdpos() {
        return idpos;
    }

    public void setIdpos(int idpos) {
        this.idpos = idpos;
    }

    @SerializedName("deta_pedido")
    private List<DetallePedido> detallePedidoList;

    public int getNroPedido() {
        return nroPedido;
    }

    public void setNroPedido(int nroPedido) {
        this.nroPedido = nroPedido;
    }

    public int getCant_pedido() {
        return cant_pedido;
    }

    public void setCant_pedido(int cant_pedido) {
        this.cant_pedido = cant_pedido;
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

    public String getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(String fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public String getHora_pedido() {
        return hora_pedido;
    }

    public void setHora_pedido(String hora_pedido) {
        this.hora_pedido = hora_pedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public double getSub_total() {
        return sub_total;
    }

    public void setSub_total(double sub_total) {
        this.sub_total = sub_total;
    }

    public String getNombre_usu() {
        return nombre_usu;
    }

    public void setNombre_usu(String nombre_usu) {
        this.nombre_usu = nombre_usu;
    }

    public double getTotal_impueto_igv() {
        return total_impueto_igv;
    }

    public void setTotal_impueto_igv(double total_impueto_igv) {
        this.total_impueto_igv = total_impueto_igv;
    }

    public List<DetallePedido> getDetallePedidoList() {
        return detallePedidoList;
    }

    public void setDetallePedidoList(List<DetallePedido> detallePedidoList) {
        this.detallePedidoList = detallePedidoList;
    }

}
