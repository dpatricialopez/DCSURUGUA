package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseMisBajas implements Serializable {

    @SerializedName("mis_bajas")
    private List<MisBajas> misBajas;

    public List<MisBajas> getMisBajas() {
        return misBajas;
    }

    public void setMisBajas(List<MisBajas> misBajas) {
        this.misBajas = misBajas;
    }
}
