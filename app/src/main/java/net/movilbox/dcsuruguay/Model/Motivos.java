package net.movilbox.dcsuruguay.Model;

/**
 * Created by germangarcia on 12/07/16.
 */
public class Motivos {

    private int id;

    private String descripcion;

    public Motivos (){

    }

    public Motivos(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
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

    @Override
    public String toString() {
        return descripcion;
    }
}
