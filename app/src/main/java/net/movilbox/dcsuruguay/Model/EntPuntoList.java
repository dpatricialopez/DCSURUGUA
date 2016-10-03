package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 8/08/16.
 */
public class EntPuntoList {

    @SerializedName("nombre_punto")
    private String nombre_punto;

    @SerializedName("cedula")
    private String cedula;

    @SerializedName("nombre_cliente")
    private String nombre_cliente;

    @SerializedName("email")
    private String email;

    @SerializedName("depto")
    private int depto;

    @SerializedName("ciudad")
    private int ciudad;

    @SerializedName("barrio")
    private String barrio;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("celular")
    private String celular;

    @SerializedName("estado_com")
    private int estado_com;

    @SerializedName("zona")
    private int zona;

    @SerializedName("territorio")
    private int territorio;

    @SerializedName("categoria")
    private int categoria;

    @SerializedName("tipo_via")
    private int tipo_via;

    @SerializedName("txt_direccion")
    private String numero_via;

    @SerializedName("numero_genera")
    private String numero_genera;

    @SerializedName("numero_placa")
    private String numero_placa;

    @SerializedName("otra_direccion")
    private int otra_direccion;

    @SerializedName("descripcion_otro")
    private String descripcion_otro;

    @SerializedName("tipo_doc")
    private int tipo_doc;

    public int getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(int tipo_doc) {
        this.tipo_doc = tipo_doc;
    }

    public String getNombre_punto() {
        return nombre_punto;
    }

    public void setNombre_punto(String nombre_punto) {
        this.nombre_punto = nombre_punto;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDepto() {
        return depto;
    }

    public void setDepto(int depto) {
        this.depto = depto;
    }

    public int getCiudad() {
        return ciudad;
    }

    public void setCiudad(int ciudad) {
        this.ciudad = ciudad;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public int getEstado_com() {
        return estado_com;
    }

    public void setEstado_com(int estado_com) {
        this.estado_com = estado_com;
    }

    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
    }

    public int getTerritorio() {
        return territorio;
    }

    public void setTerritorio(int territorio) {
        this.territorio = territorio;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getTipo_via() {
        return tipo_via;
    }

    public void setTipo_via(int tipo_via) {
        this.tipo_via = tipo_via;
    }

    public String getNumero_via() {
        return numero_via;
    }

    public void setNumero_via(String numero_via) {
        this.numero_via = numero_via;
    }

    public String getNumero_genera() {
        return numero_genera;
    }

    public void setNumero_genera(String numero_genera) {
        this.numero_genera = numero_genera;
    }

    public String getNumero_placa() {
        return numero_placa;
    }

    public void setNumero_placa(String numero_placa) {
        this.numero_placa = numero_placa;
    }

    public int getOtra_direccion() {
        return otra_direccion;
    }

    public void setOtra_direccion(int otra_direccion) {
        this.otra_direccion = otra_direccion;
    }

    public String getDescripcion_otro() {
        return descripcion_otro;
    }

    public void setDescripcion_otro(String descripcion_otro) {
        this.descripcion_otro = descripcion_otro;
    }
}
