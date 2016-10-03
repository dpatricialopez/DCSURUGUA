package net.movilbox.dcsuruguay.Model;

import java.util.List;

/**
 * Created by germangarcia on 18/07/16.
 */
public class EntListReferencias {

    private int idPaquete;
    private int idRefe;
    private int tipoPro;
    private String nomPro;
    private int cantidad;

    private List<EntEstandar> entEstandarList;

    public List<EntEstandar> getEntEstandarList() {
        return entEstandarList;
    }

    public void setEntEstandarList(List<EntEstandar> entEstandarList) {
        this.entEstandarList = entEstandarList;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public int getIdRefe() {
        return idRefe;
    }

    public void setIdRefe(int idRefe) {
        this.idRefe = idRefe;
    }

    public int getTipoPro() {
        return tipoPro;
    }

    public void setTipoPro(int tipoPro) {
        this.tipoPro = tipoPro;
    }

    public String getNomPro() {
        return nomPro;
    }

    public void setNomPro(String nomPro) {
        this.nomPro = nomPro;
    }

    @Override
    public String toString() {

        if(!nomPro.equals("SELECCIONAR")){
            return nomPro +" -- ("+cantidad+")";
        }

        return nomPro;
    }
}
