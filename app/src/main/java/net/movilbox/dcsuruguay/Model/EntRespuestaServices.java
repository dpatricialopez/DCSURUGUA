package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by germangarcia on 2/08/16.
 */
public class EntRespuestaServices implements Serializable {

    @SerializedName("estado")
    private int estado;

    @SerializedName("msg")
    private String msg;

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
}
