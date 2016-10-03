package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by germangarcia on 29/07/16.
 */
public class ListEntReferenciaSol {

    @SerializedName("accion")
    private int accion;

    @SerializedName("msg")
    private String msg;

    @SerializedName("datos")
    private List<EntSolPedido> entSolPedidos;

    @SerializedName("datos2")
    private List<EntSolPedido> entSolPedidos2;

    public List<EntSolPedido> getEntSolPedidos2() {
        return entSolPedidos2;
    }

    public void setEntSolPedidos2(List<EntSolPedido> entSolPedidos2) {
        this.entSolPedidos2 = entSolPedidos2;
    }

    public int getAccion() {
        return accion;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<EntSolPedido> getEntSolPedidos() {
        return entSolPedidos;
    }

    public void setEntSolPedidos(List<EntSolPedido> entSolPedidos) {
        this.entSolPedidos = entSolPedidos;
    }
}


