package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by germangarcia on 11/07/16.
 */
public class EntCatalogo implements Serializable {

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

    @SerializedName("img")
    private String img_pri_catalogo;

    @SerializedName("tipo_tabla")
    private int tipo_tabla;

    @SerializedName("referencias")
    private List<EntDetalleCatalogo> entRefSimsList;

    @SerializedName("img_catalogo")
    private List<EntImgCatalogo> entImgCatalogoList;

    public List<EntImgCatalogo> getEntImgCatalogoList() {
        return entImgCatalogoList;
    }

    public void setEntImgCatalogoList(List<EntImgCatalogo> entImgCatalogoList) {
        this.entImgCatalogoList = entImgCatalogoList;
    }

    public List<EntDetalleCatalogo> getEntRefSimsList() {
        return entRefSimsList;
    }

    public void setEntRefSimsList(List<EntDetalleCatalogo> entRefSimsList) {
        this.entRefSimsList = entRefSimsList;
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

    public String getImg_pri_catalogo() {
        return img_pri_catalogo;
    }

    public void setImg_pri_catalogo(String img_pri_catalogo) {
        this.img_pri_catalogo = img_pri_catalogo;
    }

    public int getTipo_tabla() {
        return tipo_tabla;
    }

    public void setTipo_tabla(int tipo_tabla) {
        this.tipo_tabla = tipo_tabla;
    }
}
