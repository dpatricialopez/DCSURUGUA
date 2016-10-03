package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by germangarcia on 28/06/16.
 */
public class EntIndicadores {

    @SerializedName("indicadores_puntos")
    private List<EntPuntoIndicado> entPuntoIndicadoList;

    @SerializedName("cant_ventas_sim")
    private int cant_ventas_sim;

    @SerializedName("cant_ventas_combo")
    private int cant_ventas_combo;

    @SerializedName("cant_cumplimiento_sim")
    private int cant_cumplimiento_sim;

    @SerializedName("cant_cumplimiento_combo")
    private int cant_cumplimiento_combo;

    @SerializedName("cant_quiebre_sim_mes")
    private int cant_quiebre_sim_mes;

    @SerializedName("id_vendedor")
    private int id_vendedor;

    @SerializedName("id_distri")
    private int id_distri;

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

    public List<EntPuntoIndicado> getEntPuntoIndicadoList() {
        return entPuntoIndicadoList;
    }

    public void setEntPuntoIndicadoList(List<EntPuntoIndicado> entPuntoIndicadoList) {
        this.entPuntoIndicadoList = entPuntoIndicadoList;
    }

    public int getCant_ventas_sim() {
        return cant_ventas_sim;
    }

    public void setCant_ventas_sim(int cant_ventas_sim) {
        this.cant_ventas_sim = cant_ventas_sim;
    }

    public int getCant_ventas_combo() {
        return cant_ventas_combo;
    }

    public void setCant_ventas_combo(int cant_ventas_combo) {
        this.cant_ventas_combo = cant_ventas_combo;
    }

    public int getCant_cumplimiento_sim() {
        return cant_cumplimiento_sim;
    }

    public void setCant_cumplimiento_sim(int cant_cumplimiento_sim) {
        this.cant_cumplimiento_sim = cant_cumplimiento_sim;
    }

    public int getCant_cumplimiento_combo() {
        return cant_cumplimiento_combo;
    }

    public void setCant_cumplimiento_combo(int cant_cumplimiento_combo) {
        this.cant_cumplimiento_combo = cant_cumplimiento_combo;
    }

    public int getCant_quiebre_sim_mes() {
        return cant_quiebre_sim_mes;
    }

    public void setCant_quiebre_sim_mes(int cant_quiebre_sim_mes) {
        this.cant_quiebre_sim_mes = cant_quiebre_sim_mes;
    }
}
