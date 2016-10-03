package net.movilbox.dcsuruguay.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseMarcarPedido implements Serializable {

    @SerializedName("estado")
    private int estado;

    @SerializedName("id_pos")
    private int id_pos;

    @SerializedName("razon_social")
    private String razon_social;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("zona")
    private String zona;

    @SerializedName("territorio")
    private String territorio;

    @SerializedName("depto")
    private String depto;

    @SerializedName("provincia")
    private String provincia;

    @SerializedName("distrito")
    private String distrito;

    @SerializedName("pedidos")
    private List<Pedidos> pedidosList;

    @SerializedName("tiene_dir")
    private int tiene_dir;

    @SerializedName("msg")
    private String msg;

    @SerializedName("stock_sim")
    private int stock_sim;

    @SerializedName("stock_seguridad_sim")
    private int stock_seguridad_sim;

    @SerializedName("stock_combo")
    private int stock_combo;

    @SerializedName("stock_seguridad_combo")
    private int stock_seguridad_combo;

    @SerializedName("dias_inve_sim")
    private int dias_inve_sim;

    @SerializedName("dias_inve_combo")
    private int dias_inve_combo;

    @SerializedName("quiebre")
    private int quiebre;

    @SerializedName("cod_cum")
    private String cod_cum;

    @SerializedName("motivos")
    private List<Motivos> motivosList;

    private int idZona;

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public String getCod_cum() {
        return cod_cum;
    }

    public void setCod_cum(String cod_cum) {
        this.cod_cum = cod_cum;
    }

    public int getStock_sim() {
        return stock_sim;
    }

    public void setStock_sim(int stock_sim) {
        this.stock_sim = stock_sim;
    }

    public int getStock_seguridad_sim() {
        return stock_seguridad_sim;
    }

    public void setStock_seguridad_sim(int stock_seguridad_sim) {
        this.stock_seguridad_sim = stock_seguridad_sim;
    }

    public int getStock_combo() {
        return stock_combo;
    }

    public void setStock_combo(int stock_combo) {
        this.stock_combo = stock_combo;
    }

    public int getStock_seguridad_combo() {
        return stock_seguridad_combo;
    }

    public void setStock_seguridad_combo(int stock_seguridad_combo) {
        this.stock_seguridad_combo = stock_seguridad_combo;
    }

    public int getDias_inve_sim() {
        return dias_inve_sim;
    }

    public void setDias_inve_sim(int dias_inve_sim) {
        this.dias_inve_sim = dias_inve_sim;
    }

    public int getDias_inve_combo() {
        return dias_inve_combo;
    }

    public void setDias_inve_combo(int dias_inve_combo) {
        this.dias_inve_combo = dias_inve_combo;
    }

    public int getQuiebre() {
        return quiebre;
    }

    public void setQuiebre(int quiebre) {
        this.quiebre = quiebre;
    }

    private int puntoPlanificacion;

    public int getPuntoPlanificacion() {
        return puntoPlanificacion;
    }

    public void setPuntoPlanificacion(int puntoPlanificacion) {
        this.puntoPlanificacion = puntoPlanificacion;
    }

    public List<Motivos> getMotivosList() {
        return motivosList;
    }

    public void setMotivosList(List<Motivos> motivosList) {
        this.motivosList = motivosList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getId_pos() {
        return id_pos;
    }

    public void setId_pos(int id_pos) {
        this.id_pos = id_pos;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getTerritorio() {
        return territorio;
    }

    public void setTerritorio(String territorio) {
        this.territorio = territorio;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public List<Pedidos> getPedidosList() {
        return pedidosList;
    }

    public void setPedidosList(List<Pedidos> pedidosList) {
        this.pedidosList = pedidosList;
    }

    public int getTiene_dir() {
        return tiene_dir;
    }

    public void setTiene_dir(int tiene_dir) {
        this.tiene_dir = tiene_dir;
    }
}
