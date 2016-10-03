package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 28/06/16.
 */
public class EntPuntoIndicado {

    @SerializedName("idpos")
    private int idpos;

    @SerializedName("tipo_visita")
    private int tipo_visita;

    @SerializedName("stock_sim")
    private int stock_sim;

    @SerializedName("stock_combo")
    private int stock_combo;

    @SerializedName("stock_seguridad_sim")
    private int stock_seguridad_sim;

    @SerializedName("stock_seguridad_combo")
    private int stock_seguridad_combo;

    @SerializedName("dias_inve_sim")
    private double dias_inve_sim;

    @SerializedName("dias_inve_combo")
    private double dias_inve_combo;

    @SerializedName("id_vendedor")
    private int id_vendedor;

    @SerializedName("id_distri")
    private int id_distri;

    @SerializedName("fecha_dia")
    private String fecha_dia;

    @SerializedName("fecha_ult")
    private String fecha_ult;

    @SerializedName("hora_ult")
    private String hora_ult;

    @SerializedName("orden")
    private int orden;

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getFecha_ult() {
        return fecha_ult;
    }

    public void setFecha_ult(String fecha_ult) {
        this.fecha_ult = fecha_ult;
    }

    public String getHora_ult() {
        return hora_ult;
    }

    public void setHora_ult(String hora_ult) {
        this.hora_ult = hora_ult;
    }

    public int getIdpos() {
        return idpos;
    }

    public void setIdpos(int idpos) {
        this.idpos = idpos;
    }

    public int getTipo_visita() {
        return tipo_visita;
    }

    public void setTipo_visita(int tipo_visita) {
        this.tipo_visita = tipo_visita;
    }

    public int getStock_sim() {
        return stock_sim;
    }

    public void setStock_sim(int stock_sim) {
        this.stock_sim = stock_sim;
    }

    public int getStock_combo() {
        return stock_combo;
    }

    public void setStock_combo(int stock_combo) {
        this.stock_combo = stock_combo;
    }

    public int getStock_seguridad_sim() {
        return stock_seguridad_sim;
    }

    public void setStock_seguridad_sim(int stock_seguridad_sim) {
        this.stock_seguridad_sim = stock_seguridad_sim;
    }

    public int getStock_seguridad_combo() {
        return stock_seguridad_combo;
    }

    public void setStock_seguridad_combo(int stock_seguridad_combo) {
        this.stock_seguridad_combo = stock_seguridad_combo;
    }

    public double getDias_inve_sim() {
        return dias_inve_sim;
    }

    public void setDias_inve_sim(double dias_inve_sim) {
        this.dias_inve_sim = dias_inve_sim;
    }

    public double getDias_inve_combo() {
        return dias_inve_combo;
    }

    public void setDias_inve_combo(double dias_inve_combo) {
        this.dias_inve_combo = dias_inve_combo;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public int getId_distri() {
        return id_distri;
    }

    public void setId_distri(int id_distri) {
        this.id_distri = id_distri;
    }

    public String getFecha_dia() {
        return fecha_dia;
    }

    public void setFecha_dia(String fecha_dia) {
        this.fecha_dia = fecha_dia;
    }
}
