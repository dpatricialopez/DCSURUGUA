package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by germangarcia on 2/08/16.
 */
public class ListSolicitudVendedor implements Serializable {

    @SerializedName("estado")
    private int estado;

    @SerializedName("msg")
    private String msg;

    @SerializedName("datos")
    private List<EntSolicitudVendedor> entSolicitudVendedors;

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<EntSolicitudVendedor> getEntSolicitudVendedors() {
        return entSolicitudVendedors;
    }

    public void setEntSolicitudVendedors(List<EntSolicitudVendedor> entSolicitudVendedors) {
        this.entSolicitudVendedors = entSolicitudVendedors;
    }
}
