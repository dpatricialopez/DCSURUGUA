package net.movilbox.dcsuruguay.Model;

import java.io.Serializable;

/**
 * Created by jhonjimenez on 27/10/16.
 */

public class Serie implements Serializable {

    private String serie;
    private int id_pro;
    private int check;

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }
}
