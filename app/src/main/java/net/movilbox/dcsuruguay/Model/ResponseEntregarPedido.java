package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by germangarcia on 12/05/16.
 */
public class ResponseEntregarPedido implements Serializable {

    @SerializedName("idpos")
    private int idpos;

    @SerializedName("razon_social")
    private String razon_social;

    @SerializedName("territorio")
    private String territorio;

    @SerializedName("zona")
    private String zona;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("pedido")
    private List<PedidosEntrega> pedidosEntregaList;

    public int getIdpos() {
        return idpos;
    }

    public void setIdpos(int idpos) {
        this.idpos = idpos;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getTerritorio() {
        return territorio;
    }

    public void setTerritorio(String territorio) {
        this.territorio = territorio;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<PedidosEntrega> getPedidosEntregaList() {
        return pedidosEntregaList;
    }

    public void setPedidosEntregaList(List<PedidosEntrega> pedidosEntregaList) {
        this.pedidosEntregaList = pedidosEntregaList;
    }
}
