package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReferenciasCombos implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("precioventa")
    private double precioventa;

    @SerializedName("speech")
    private String speech;

    @SerializedName("pantalla")
    private String pantalla;

    @SerializedName("cam_frontal")
    private String cam_frontal;

    @SerializedName("cam_tras")
    private String cam_tras;

    @SerializedName("flash")
    private String flash;

    @SerializedName("banda")
    private String banda;

    @SerializedName("memoria")
    private String memoria;

    @SerializedName("expandible")
    private String expandible;

    @SerializedName("bateria")
    private String bateria;

    @SerializedName("bluetooth")
    private String bluetooth;

    @SerializedName("tactil")
    private String tactil;

    @SerializedName("tec_fisico")
    private String tec_fisico;

    @SerializedName("carrito_compras")
    private String carrito_compras;

    @SerializedName("correo")
    private String correo;

    @SerializedName("enrutador")
    private String enrutador;

    @SerializedName("radio")
    private String radio;

    @SerializedName("wifi")
    private String wifi;

    @SerializedName("gps")
    private String gps;

    @SerializedName("so")
    private String so;

    @SerializedName("web")
    private String web;

    @SerializedName("precio_referencia")
    private double precio_referencia;

    @SerializedName("precio_publico")
    private double precio_publico;

    @SerializedName("img")
    private String img;

    @SerializedName("list_imgs")
    private List<CategoriasEstandar> estandarList;

    @SerializedName("referencias")
    private List<Referencia> referenciaLis;

    @SerializedName("quiebre")
    private int quiebre;

    @SerializedName("estado_accion")
    private int estado_accion;

    @SerializedName("stock")
    private int stock;

    public int cantidadPe;

    public int getCantidadPe() {
        return cantidadPe;
    }

    public void setCantidadPe(int cantidadPe) {
        this.cantidadPe = cantidadPe;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getEstado_accion() {
        return estado_accion;
    }

    public void setEstado_accion(int estado_accion) {
        this.estado_accion = estado_accion;
    }

    public int getQuiebre() {
        return quiebre;
    }

    public void setQuiebre(int quiebre) {
        this.quiebre = quiebre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioventa() {
        return precioventa;
    }

    public void setPrecioventa(double precioventa) {
        this.precioventa = precioventa;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public String getPantalla() {
        return pantalla;
    }

    public void setPantalla(String pantalla) {
        this.pantalla = pantalla;
    }

    public String getCam_frontal() {
        return cam_frontal;
    }

    public void setCam_frontal(String cam_frontal) {
        this.cam_frontal = cam_frontal;
    }

    public String getCam_tras() {
        return cam_tras;
    }

    public void setCam_tras(String cam_tras) {
        this.cam_tras = cam_tras;
    }

    public String getFlash() {
        return flash;
    }

    public void setFlash(String flash) {
        this.flash = flash;
    }

    public String getBanda() {
        return banda;
    }

    public void setBanda(String banda) {
        this.banda = banda;
    }

    public String getMemoria() {
        return memoria;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

    public String getExpandible() {
        return expandible;
    }

    public void setExpandible(String expandible) {
        this.expandible = expandible;
    }

    public String getBateria() {
        return bateria;
    }

    public void setBateria(String bateria) {
        this.bateria = bateria;
    }

    public String getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getTactil() {
        return tactil;
    }

    public void setTactil(String tactil) {
        this.tactil = tactil;
    }

    public String getTec_fisico() {
        return tec_fisico;
    }

    public void setTec_fisico(String tec_fisico) {
        this.tec_fisico = tec_fisico;
    }

    public String getCarrito_compras() {
        return carrito_compras;
    }

    public void setCarrito_compras(String carrito_compras) {
        this.carrito_compras = carrito_compras;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEnrutador() {
        return enrutador;
    }

    public void setEnrutador(String enrutador) {
        this.enrutador = enrutador;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public double getPrecio_referencia() {
        return precio_referencia;
    }

    public void setPrecio_referencia(double precio_referencia) {
        this.precio_referencia = precio_referencia;
    }

    public double getPrecio_publico() {
        return precio_publico;
    }

    public void setPrecio_publico(double precio_publico) {
        this.precio_publico = precio_publico;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Referencia> getReferenciaLis() {
        return referenciaLis;
    }

    public void setReferenciaLis(List<Referencia> referenciaLis) {
        this.referenciaLis = referenciaLis;
    }

    public List<CategoriasEstandar> getEstandarList() {
        return estandarList;
    }

    public void setEstandarList(List<CategoriasEstandar> estandarList) {
        this.estandarList = estandarList;
    }
}
