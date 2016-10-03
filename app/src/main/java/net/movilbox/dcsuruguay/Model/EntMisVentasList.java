package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by germangarcia on 2/08/16.
 */
public class EntMisVentasList implements Serializable {

    @SerializedName("reporte")
    private List<EntMisVentas> entMisVentasList;

    public List<EntMisVentas> getEntMisVentasList() {
        return entMisVentasList;
    }

    public void setEntMisVentasList(List<EntMisVentas> entMisVentasList) {
        this.entMisVentasList = entMisVentasList;
    }

}
