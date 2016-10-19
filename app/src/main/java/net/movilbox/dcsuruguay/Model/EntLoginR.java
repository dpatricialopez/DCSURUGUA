package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by germangarcia on 23/06/16.
 */
public class EntLoginR {

    @SerializedName("id")
    private int id;

    @SerializedName("cedula")
    private int cedula;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("apellido")
    private String apellido;

    @SerializedName("user")
    private String user;

    @SerializedName("estado")
    private int estado;

    @SerializedName("bd")
    private String bd;

    @SerializedName("id_distri")
    private int id_distri;

    @SerializedName("perfil")
    private int perfil;

    @SerializedName("igv")
    private double igv;

    @SerializedName("intervalo")
    private int intervalo;

    @SerializedName("hora_inicial")
    private String hora_inicial;

    @SerializedName("hora_final")
    private String hora_final;

    @SerializedName("cantidad_envios")
    private int cantidad_envios;

    @SerializedName("msg")
    private String msg;

    @SerializedName("fecha_sincroniza")
    private String fecha_sincroniza;

    @SerializedName("fecha_sincroniza_offline")
    private String fecha_sincroniza_offline;

    private String password;

    @SerializedName("datos_generales")
    private List<EntEstandar> datosGenerales;

    public static int indicador_refres;

    public static int getIndicador_refres() {
        return indicador_refres;
    }

    public static void setIndicador_refres(int indicador_refres) {
        EntLoginR.indicador_refres = indicador_refres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getBd() {
        return bd;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }

    public int getId_distri() {
        return id_distri;
    }

    public void setId_distri(int id_distri) {
        this.id_distri = id_distri;
    }

    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(int perfil) {
        this.perfil = perfil;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public int getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(int intervalo) {
        this.intervalo = intervalo;
    }

    public String getHora_inicial() {
        return hora_inicial;
    }

    public void setHora_inicial(String hora_inicial) {
        this.hora_inicial = hora_inicial;
    }

    public String getHora_final() {
        return hora_final;
    }

    public void setHora_final(String hora_final) {
        this.hora_final = hora_final;
    }

    public int getCantidad_envios() {
        return cantidad_envios;
    }

    public void setCantidad_envios(int cantidad_envios) {
        this.cantidad_envios = cantidad_envios;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFecha_sincroniza() {
        return fecha_sincroniza;
    }

    public void setFecha_sincroniza(String fecha_sincroniza) {
        this.fecha_sincroniza = fecha_sincroniza;
    }

    public List<EntEstandar> getDatosGenerales() {
        return datosGenerales;
    }

    public void setDatosGenerales(List<EntEstandar> datosGenerales) {
        this.datosGenerales = datosGenerales;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFecha_sincroniza_offline() {
        return fecha_sincroniza_offline;
    }

    public void setFecha_sincroniza_offline(String fecha_sincroniza_offline) {
        this.fecha_sincroniza_offline = fecha_sincroniza_offline;
    }

}
