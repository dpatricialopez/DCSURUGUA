package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 24/06/16.
 */
public class EntNomenclaturas {

    private int idInter;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("letras")
    private String letras;

    @SerializedName("tipo_nom")
    private int tipo_nom;

    public int getIdInter() {
        return idInter;
    }

    public void setIdInter(int idInter) {
        this.idInter = idInter;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLetras() {
        return letras;
    }

    public void setLetras(String letras) {
        this.letras = letras;
    }

    public int getTipo_nom() {
        return tipo_nom;
    }

    public void setTipo_nom(int tipo_nom) {
        this.tipo_nom = tipo_nom;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
