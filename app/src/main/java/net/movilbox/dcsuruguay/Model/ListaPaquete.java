package net.movilbox.dcsuruguay.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jhonjimenez on 26/10/16.
 */

public class ListaPaquete implements Serializable {

    private int idPaquete;
    private int cantidadPaquete;
    private int cantidadSoli;
    private List<Serie> listaSerie;

    public int getCantidadSoli() {
        return cantidadSoli;
    }

    public void setCantidadSoli(int cantidadSoli) {
        this.cantidadSoli = cantidadSoli;
    }

    public List<Serie> getListaSerie() {
        return listaSerie;
    }

    public void setListaSerie(List<Serie> listaSerie) {
        this.listaSerie = listaSerie;
    }

    public int getCantidadPaquete() {
        return cantidadPaquete;
    }

    public void setCantidadPaquete(int cantidadPaquete) {
        this.cantidadPaquete = cantidadPaquete;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }
}
