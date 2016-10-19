package net.movilbox.dcsuruguay.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EntLisSincronizar extends EntCatalogo implements Serializable {

    @SerializedName("id_territorio")
    private int id_territorio;

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

    @SerializedName("pn")
    private String pn;

    @SerializedName("producto")
    private String producto;

    @SerializedName("precio_referencia")
    private int precio_referencia;

    @SerializedName("precio_publico")
    private int precio_publico;

    @SerializedName("estado_accion")
    private int estado_accion;

    @SerializedName("id_referencia")
    private int id_referencia;

    @SerializedName("valor_referencia")
    private double valor_referencia;

    @SerializedName("valor_directo")
    private double valor_directo;

    @SerializedName("tipo_pro")
    private int tipo_pro;

    @SerializedName("id_lista")
    private int id_lista;

    @SerializedName("tipo_nivel")
    private int tipo_nivel;

    @SerializedName("serie")
    private String serie;

    @SerializedName("paquete")
    private int paquete;

    @SerializedName("id_vendedor")
    private int id_vendedor;

    @SerializedName("distri")
    private int distri;

    @SerializedName("departamento")
    private String nombreDepartamento;

    @SerializedName("ciudad_nombre")
    private String nombreCiudad;

    @SerializedName("nombre_territorio")
    private String nombreTerritorio;

    @SerializedName("nombre_zona")
    private String nombreZona;

    @SerializedName("id_tipo")
    private int id_tipo;

    @SerializedName("tipo_via")
    private int tipo_via;

    @SerializedName("dato_via")
    private String dato_via;

    @SerializedName("nro")
    private String nro;

    @SerializedName("placa")
    private String placa;

    @SerializedName("otro")
    private int otro;

    @SerializedName("dato_otro")
    private String dato_otro;

    @SerializedName("barrio")
    private String barrio;

    @SerializedName("nombre_cli")
    private String nombre_cli;

    @SerializedName("tipo_visita")
    private int tipo_visita;

    @SerializedName("razon")
    private String razon;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("estado_solicitud")
    private String estado_solicitud;

    @SerializedName("estado")
    private int estado;

    @SerializedName("msg")
    private String msg;

    @SerializedName("razon_social")
    private String razon_social;

    @SerializedName("nombre_depto")
    private String nombre_depto;

    @SerializedName("provincia")
    private String provincia;

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

    @SerializedName("tipo_documento")
    private int tipo_documento;

    @SerializedName("vende_recargas")
    private int vende_recargas;

    private int idDepartameto;

    private int idCiudad;

    public int getIdDepartameto() {
        return idDepartameto;
    }

    public void setIdDepartameto(int idDepartameto) {
        this.idDepartameto = idDepartameto;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public int getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(int tipo_documento) {
        this.tipo_documento = tipo_documento;
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getNombre_depto() {
        return nombre_depto;
    }

    public void setNombre_depto(String nombre_depto) {
        this.nombre_depto = nombre_depto;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public int getTipo_via() {
        return tipo_via;
    }

    public void setTipo_via(int tipo_via) {
        this.tipo_via = tipo_via;
    }

    public String getDato_via() {
        return dato_via;
    }

    public void setDato_via(String dato_via) {
        this.dato_via = dato_via;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getOtro() {
        return otro;
    }

    public void setOtro(int otro) {
        this.otro = otro;
    }

    public String getDato_otro() {
        return dato_otro;
    }

    public void setDato_otro(String dato_otro) {
        this.dato_otro = dato_otro;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public String getNombreTerritorio() {
        return nombreTerritorio;
    }

    public void setNombreTerritorio(String nombreTerritorio) {
        this.nombreTerritorio = nombreTerritorio;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public int getId_territorio() {
        return id_territorio;
    }

    public void setId_territorio(int id_territorio) {
        this.id_territorio = id_territorio;
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

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getPrecio_referencia() {
        return precio_referencia;
    }

    public void setPrecio_referencia(int precio_referencia) {
        this.precio_referencia = precio_referencia;
    }

    public int getPrecio_publico() {
        return precio_publico;
    }

    public void setPrecio_publico(int precio_publico) {
        this.precio_publico = precio_publico;
    }

    public int getEstado_accion() {
        return estado_accion;
    }

    public void setEstado_accion(int estado_accion) {
        this.estado_accion = estado_accion;
    }

    public int getId_referencia() {
        return id_referencia;
    }

    public void setId_referencia(int id_referencia) {
        this.id_referencia = id_referencia;
    }

    public double getValor_referencia() {
        return valor_referencia;
    }

    public void setValor_referencia(double valor_referencia) {
        this.valor_referencia = valor_referencia;
    }

    public double getValor_directo() {
        return valor_directo;
    }

    public void setValor_directo(double valor_directo) {
        this.valor_directo = valor_directo;
    }

    public int getTipo_pro() {
        return tipo_pro;
    }

    public void setTipo_pro(int tipo_pro) {
        this.tipo_pro = tipo_pro;
    }

    public int getId_lista() {
        return id_lista;
    }

    public void setId_lista(int id_lista) {
        this.id_lista = id_lista;
    }

    public int getTipo_nivel() {
        return tipo_nivel;
    }

    public void setTipo_nivel(int tipo_nivel) {
        this.tipo_nivel = tipo_nivel;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getPaquete() {
        return paquete;
    }

    public void setPaquete(int paquete) {
        this.paquete = paquete;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public int getDistri() {
        return distri;
    }

    public void setDistri(int distri) {
        this.distri = distri;
    }

    public String getEstado_solicitud() {
        return estado_solicitud;
    }

    public void setEstado_solicitud(String estado_solicitud) {
        this.estado_solicitud = estado_solicitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public int getTipo_visita() {
        return tipo_visita;
    }

    public void setTipo_visita(int tipo_visita) {
        this.tipo_visita = tipo_visita;
    }

    public String getNombre_cli() {
        return nombre_cli;
    }

    public void setNombre_cli(String nombre_cli) {
        this.nombre_cli = nombre_cli;
    }

    public int getVende_recargas() {
        return vende_recargas;
    }

    public void setVende_recargas(int vende_recargas) {
        this.vende_recargas = vende_recargas;
    }
}
