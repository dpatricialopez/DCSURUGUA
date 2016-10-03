package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 11/07/16.
 */
public class EntImgCatalogo {

    private int id_auto;

    @SerializedName("url")
    private String url;

    @SerializedName("id_catalogo")
    private int id_catalogo;

    public int getId_auto() {
        return id_auto;
    }

    public void setId_auto(int id_auto) {
        this.id_auto = id_auto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId_catalogo() {
        return id_catalogo;
    }

    public void setId_catalogo(int id_catalogo) {
        this.id_catalogo = id_catalogo;
    }
}
