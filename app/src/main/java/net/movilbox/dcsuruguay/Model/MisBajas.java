package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MisBajas implements Serializable {

    @SerializedName("id_solicitud")
    private int id_solicitud;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("id_pos")
    private int id_pos;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("vendedor")
    private String vendedor;

    @SerializedName("estado")
    private String estado;

    @SerializedName("razon")
    private String razon;

    @SerializedName("mis_bajas_detalle1")
    private List<MisBajasDetalle1> misBajasDetalle1List;

    public int getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId_pos() {
        return id_pos;
    }

    public void setId_pos(int id_pos) {
        this.id_pos = id_pos;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public List<MisBajasDetalle1> getMisBajasDetalle1List() {
        return misBajasDetalle1List;
    }

    public void setMisBajasDetalle1List(List<MisBajasDetalle1> misBajasDetalle1List) {
        this.misBajasDetalle1List = misBajasDetalle1List;
    }
}
