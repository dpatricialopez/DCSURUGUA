package net.movilbox.dcsuruguay.Model;

import java.io.Serializable;

/**
 * Created by germangarcia on 19/07/16.
 */
public class EntRefeSerial implements Serializable {

    private int id_inven;

    private String serie;

    private int tipo_pro;

    private int id_paquete;

    private int id_referencia;

    public boolean isChecked;

    public double precioDirecto;

    public String nombreReferencia;

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getNombreReferencia() {
        return nombreReferencia;
    }

    public void setNombreReferencia(String nombreReferencia) {
        this.nombreReferencia = nombreReferencia;
    }

    public double getPrecioDirecto() {
        return precioDirecto;
    }

    public void setPrecioDirecto(double precioDirecto) {
        this.precioDirecto = precioDirecto;
    }

    public int getId_inven() {
        return id_inven;
    }

    public void setId_inven(int id_inven) {
        this.id_inven = id_inven;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getTipo_pro() {
        return tipo_pro;
    }

    public void setTipo_pro(int tipo_pro) {
        this.tipo_pro = tipo_pro;
    }

    public int getId_paquete() {
        return id_paquete;
    }

    public void setId_paquete(int id_paquete) {
        this.id_paquete = id_paquete;
    }

    public int getId_referencia() {
        return id_referencia;
    }

    public void setId_referencia(int id_referencia) {
        this.id_referencia = id_referencia;
    }

    @Override
    public String toString() {
        return serie;
    }
}
