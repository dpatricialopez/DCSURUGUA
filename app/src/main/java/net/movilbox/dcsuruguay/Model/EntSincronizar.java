package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by germangarcia on 19/05/16.
 */
public class EntSincronizar {

    @SerializedName("datos_generales")
    private List<EntLisSincronizar> entLisSincronizars;

    @SerializedName("fecha_sincroniza_offline")
    private String fecha_sincroniza;

    @SerializedName("territorios")
    private int territorios;

    @SerializedName("zonas")
    private int zonas;

    @SerializedName("puntos")
    private int puntos;

    @SerializedName("refes_sims")
    private int refes_sims;

    @SerializedName("refes_combo")
    private int refes_combo;

    @SerializedName("lista_precios")
    private int lista_precios;

    @SerializedName("inventario")
    private int inventario;

    public int getInventario() {
        return inventario;
    }

    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

    public int getTerritorios() {
        return territorios;
    }

    public void setTerritorios(int territorios) {
        this.territorios = territorios;
    }

    public int getZonas() {
        return zonas;
    }

    public void setZonas(int zonas) {
        this.zonas = zonas;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getRefes_sims() {
        return refes_sims;
    }

    public void setRefes_sims(int refes_sims) {
        this.refes_sims = refes_sims;
    }

    public int getRefes_combo() {
        return refes_combo;
    }

    public void setRefes_combo(int refes_combo) {
        this.refes_combo = refes_combo;
    }

    public int getLista_precios() {
        return lista_precios;
    }

    public void setLista_precios(int lista_precios) {
        this.lista_precios = lista_precios;
    }

    public List<EntLisSincronizar> getEntLisSincronizars() {
        return entLisSincronizars;
    }

    public void setEntLisSincronizars(List<EntLisSincronizar> entLisSincronizars) {
        this.entLisSincronizars = entLisSincronizars;
    }

    public String getFecha_sincroniza() {
        return fecha_sincroniza;
    }

    public void setFecha_sincroniza(String fecha_sincroniza) {
        this.fecha_sincroniza = fecha_sincroniza;
    }

}
