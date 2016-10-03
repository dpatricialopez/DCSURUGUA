package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 5/08/16.
 */
public class EntListResposeCompra {

    @SerializedName("accion")
    private int accion;

    @SerializedName("msg")
    private String msg;

    @SerializedName("idAutoCarrito")
    private int idAutoCarrito;

    @SerializedName("serie")
    private String serie;

    @SerializedName("idReferencia")
    private int idReferencia;

    @SerializedName("idPaquete")
    private int idPaquete;

    @SerializedName("tipoProducto")
    private int tipoProducto;

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

    public int getIdAutoCarrito() {
        return idAutoCarrito;
    }

    public void setIdAutoCarrito(int idAutoCarrito) {
        this.idAutoCarrito = idAutoCarrito;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(int idReferencia) {
        this.idReferencia = idReferencia;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public int getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(int tipoProducto) {
        this.tipoProducto = tipoProducto;
    }
}
