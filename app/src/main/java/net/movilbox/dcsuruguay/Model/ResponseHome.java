package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseHome implements Serializable {

    @SerializedName("idpos")
    private int idpos;

    @SerializedName("razon")
    private String razon;

    @SerializedName("nombre_cli")
    private String nombre_cli;

    @SerializedName("barrio")
    private String barrio;

    @SerializedName("cel")
    private String cel;

    @SerializedName("estado_comercial")
    private String estado_comercial;

    @SerializedName("email")
    private String email;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("departamento")
    private String departamento;

    @SerializedName("munucipio")
    private String munucipio;

    @SerializedName("distrito")
    private String distrito;

    @SerializedName("id_circuito")
    private String id_circuito;

    @SerializedName("circuito")
    private String circuito;

    @SerializedName("idruta")
    private String idruta;

    @SerializedName("ruta")
    private String ruta;

    @SerializedName("tel")
    private String tel;

    @SerializedName("detalle")
    private String detalle;

    @SerializedName("tipo_visita")
    private int tipo_visita;

    @SerializedName("rutero")
    private int rutero;

    @SerializedName("latitud")
    private double latitud;

    @SerializedName("longitud")
    private double longitud;

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

    @SerializedName("fecha_ult")
    private String fecha_ult;

    @SerializedName("hora_ult")
    private String hora_ult;

    @SerializedName("persona_ultima")
    private String persona_ultima;

    @SerializedName("pedidos")
    private ResponseEntregarPedido list;

    @SerializedName("cod_cum")
    private String cod_cum;

    public static List<ResponseHome> responseHomeListS;

    public String getCod_cum() {
        return cod_cum;
    }

    public void setCod_cum(String cod_cum) {
        this.cod_cum = cod_cum;
    }

    public ResponseEntregarPedido getList() {
        return list;
    }

    public void setList(ResponseEntregarPedido list) {
        this.list = list;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }


    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
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

    public String getPersona_ultima() {
        return persona_ultima;
    }

    public void setPersona_ultima(String persona_ultima) {
        this.persona_ultima = persona_ultima;
    }

    public static List<ResponseHome> getResponseHomeListS() {
        return responseHomeListS;
    }

    public static void setResponseHomeListS(List<ResponseHome> responseHomeListS) {
        ResponseHome.responseHomeListS = responseHomeListS;
    }

    public int getIdpos() {
        return idpos;
    }

    public void setIdpos(int idpos) {
        this.idpos = idpos;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getNombre_cli() {
        return nombre_cli;
    }

    public void setNombre_cli(String nombre_cli) {
        this.nombre_cli = nombre_cli;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getEstado_comercial() {
        return estado_comercial;
    }

    public void setEstado_comercial(String estado_comercial) {
        this.estado_comercial = estado_comercial;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunucipio() {
        return munucipio;
    }

    public void setMunucipio(String munucipio) {
        this.munucipio = munucipio;
    }

    public String getId_circuito() {
        return id_circuito;
    }

    public void setId_circuito(String id_circuito) {
        this.id_circuito = id_circuito;
    }

    public String getCircuito() {
        return circuito;
    }

    public void setCircuito(String circuito) {
        this.circuito = circuito;
    }

    public String getIdruta() {
        return idruta;
    }

    public void setIdruta(String idruta) {
        this.idruta = idruta;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getTipo_visita() {
        return tipo_visita;
    }

    public void setTipo_visita(int tipo_visita) {
        this.tipo_visita = tipo_visita;
    }

    public int getRutero() {
        return rutero;
    }

    public void setRutero(int rutero) {
        this.rutero = rutero;
    }

}
