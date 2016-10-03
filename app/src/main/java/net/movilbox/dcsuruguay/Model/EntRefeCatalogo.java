package net.movilbox.dcsuruguay.Model;

/**
 * Created by germangarcia on 19/07/16.
 */
public class EntRefeCatalogo {
    private int id_refe;
    private String descripcion;
    private double valor_valor_referencia;
    private double valor_directo;
    private int cant;
    private int tipo_pro;

    public int getTipo_pro() {
        return tipo_pro;
    }

    public void setTipo_pro(int tipo_pro) {
        this.tipo_pro = tipo_pro;
    }

    public int getId_refe() {
        return id_refe;
    }

    public void setId_refe(int id_refe) {
        this.id_refe = id_refe;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descrepcion) {
        this.descripcion = descrepcion;
    }

    public double getValor_valor_referencia() {
        return valor_valor_referencia;
    }

    public void setValor_valor_referencia(double valor_valor_referencia) {
        this.valor_valor_referencia = valor_valor_referencia;
    }

    public double getValor_directo() {
        return valor_directo;
    }

    public void setValor_directo(double valor_directo) {
        this.valor_directo = valor_directo;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }
}
