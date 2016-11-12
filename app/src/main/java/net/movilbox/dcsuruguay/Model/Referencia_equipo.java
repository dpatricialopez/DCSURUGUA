package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by germangarcia on 7/05/16.
 */
public class Referencia_equipo implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("pn")
    private String pn;

    @SerializedName("stock")
    private int stock;

    @SerializedName("producto")
    private String producto;

    @SerializedName("dias_inve")
    private double dias_inve;

    @SerializedName("ped_sugerido")
    private String ped_sugerido;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("img")
    private String url_imagen;

    @SerializedName("precio_referencia")
    private double precio_referencia;

    @SerializedName("precio_publico")
    private double precio_publico;

    @SerializedName("estado_accion")
    private int estado_accion;

    @SerializedName("id_padre")
    private int id_padre;

    @SerializedName("latitud")
    private double latitud;

    @SerializedName("longitud")
    private double longitud;

    @SerializedName("estado_line")
    private int estado_line;

    @SerializedName("quiebre")
    private int quiebre;

    public int cantidadPedida;

    public int id_punto;

    public int id_usuario;

    public int tipo_producto;

    public int referencia;

    public int getEstado_line() {
        return estado_line;
    }

    public void setEstado_line(int estado_line) {
        this.estado_line = estado_line;
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

    public int getId_padre() {
        return id_padre;
    }

    public void setId_padre(int id_padre) {
        this.id_padre = id_padre;
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

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
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

    public int getCantidadPedida() {
        return cantidadPedida;
    }

    public void setCantidadPedida(int cantidadPedida) {
        this.cantidadPedida = cantidadPedida;
    }

    public int getId_punto() {
        return id_punto;
    }

    public void setId_punto(int id_punto) {
        this.id_punto = id_punto;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getTipo_producto() {
        return tipo_producto;
    }

    public void setTipo_producto(int tipo_producto) {
        this.tipo_producto = tipo_producto;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getDias_inve() {
        return dias_inve;
    }

    public void setDias_inve(double dias_inve) {
        this.dias_inve = dias_inve;
    }

    public String getPed_sugerido() {
        return ped_sugerido;
    }

    public void setPed_sugerido(String ped_sugerido) {
        this.ped_sugerido = ped_sugerido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
