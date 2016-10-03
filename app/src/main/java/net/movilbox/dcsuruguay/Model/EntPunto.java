package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 11/07/16.
 */
public class EntPunto {

    private int id_auto;

    @SerializedName("categoria")
    private int categoria;

    @SerializedName("cedula")
    private String cedula;

    @SerializedName("celular")
    private String celular;

    @SerializedName("ciudad")
    private int ciudad;

    @SerializedName("depto")
    private int depto;

    @SerializedName("email")
    private String email;

    @SerializedName("estado_com")
    private int estado_com;

    @SerializedName("idpos")
    private int idpos;

    @SerializedName("nombre_cliente")
    private String nombre_cliente;

    @SerializedName("nombre_punto")
    private String nombre_punto;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("territorio")
    private int territorio;

    @SerializedName("texto_direccion")
    private String texto_direccion;

    @SerializedName("zona")
    private int zona;

    @SerializedName("latitud")
    private double latitud;

    @SerializedName("longitud")
    private double longitud;

    @SerializedName("estado_visita")
    private int estado_visita;

    @SerializedName("detalle")
    private String detalle;

    @SerializedName("tipo_tabla")
    private int tipo_tabla;


    public int getId_auto() {
        return id_auto;
    }

    public void setId_auto(int id_auto) {
        this.id_auto = id_auto;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public int getCiudad() {
        return ciudad;
    }

    public void setCiudad(int ciudad) {
        this.ciudad = ciudad;
    }

    public int getDepto() {
        return depto;
    }

    public void setDepto(int depto) {
        this.depto = depto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEstado_com() {
        return estado_com;
    }

    public void setEstado_com(int estado_com) {
        this.estado_com = estado_com;
    }

    public int getIdpos() {
        return idpos;
    }

    public void setIdpos(int idpos) {
        this.idpos = idpos;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getNombre_punto() {
        return nombre_punto;
    }

    public void setNombre_punto(String nombre_punto) {
        this.nombre_punto = nombre_punto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getTerritorio() {
        return territorio;
    }

    public void setTerritorio(int territorio) {
        this.territorio = territorio;
    }

    public String getTexto_direccion() {
        return texto_direccion;
    }

    public void setTexto_direccion(String texto_direccion) {
        this.texto_direccion = texto_direccion;
    }

    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
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

    public int getEstado_visita() {
        return estado_visita;
    }

    public void setEstado_visita(int estado_visita) {
        this.estado_visita = estado_visita;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getTipo_tabla() {
        return tipo_tabla;
    }

    public void setTipo_tabla(int tipo_tabla) {
        this.tipo_tabla = tipo_tabla;
    }

}
