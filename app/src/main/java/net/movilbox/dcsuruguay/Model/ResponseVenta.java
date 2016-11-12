package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseVenta {

    @SerializedName("referencias_sims")
    private List<ReferenciasSims> referenciasSimsList;

    @SerializedName("referencias_combos")
    private List<ReferenciasCombos> referenciasCombosList;

    @SerializedName("referencias_equipos")
    private List<ReferenciasEquipos> referenciasEquiposList;

    public static int id_posStacti;

    public static int getId_posStacti() {
        return id_posStacti;
    }

    public static void setId_posStacti(int id_posStacti) {
        ResponseVenta.id_posStacti = id_posStacti;
    }

    public static ResponseVenta responseVentaStatic;

    public static ResponseVenta getResponseVentaStatic() {
        return responseVentaStatic;
    }

    public static void setResponseVentaStatic(ResponseVenta responseVentaStatic) {
        ResponseVenta.responseVentaStatic = responseVentaStatic;
    }

    public List<ReferenciasEquipos> getReferenciasEquiposList() {
        return referenciasEquiposList;
    }

    public void setReferenciasEquiposList(List<ReferenciasEquipos> referenciasEquiposList) {
        this.referenciasEquiposList = referenciasEquiposList;
    }

    public List<ReferenciasSims> getReferenciasSimsList() {
        return referenciasSimsList;
    }

    public void setReferenciasSimsList(List<ReferenciasSims> referenciasSimsList) {
        this.referenciasSimsList = referenciasSimsList;
    }

    public List<ReferenciasCombos> getReferenciasCombosList() {
        return referenciasCombosList;
    }

    public void setReferenciasCombosList(List<ReferenciasCombos> referenciasCombosList) {
        this.referenciasCombosList = referenciasCombosList;
    }

}
