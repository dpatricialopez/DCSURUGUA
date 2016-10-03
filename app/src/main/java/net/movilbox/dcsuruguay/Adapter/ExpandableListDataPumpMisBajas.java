package net.movilbox.dcsuruguay.Adapter;

import android.text.Html;

import net.movilbox.dcsuruguay.Model.MisBajas;
import net.movilbox.dcsuruguay.Model.MisBajasDetalle1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListDataPumpMisBajas {

    public static HashMap<String, List<MisBajasDetalle1>> getData(List<MisBajas> data) {
        int elements = 0;

        HashMap<String, List<MisBajasDetalle1>> expandableListDetail = new LinkedHashMap<>();


        if (data != null) {

            for (int i = 0; i < data.size(); i++) {

                if (data.get(i).getMisBajasDetalle1List() != null) {

                    List<MisBajasDetalle1> technology = new ArrayList<>();

                    elements = data.get(i).getMisBajasDetalle1List().size();

                    for (int a = 0; a < elements; a++) {
                        technology.add(new MisBajasDetalle1(data.get(i).getMisBajasDetalle1List().get(a).getSku(), data.get(i).getMisBajasDetalle1List().get(a).getCant_sku()));
                    }

                    expandableListDetail.put(String.valueOf(Html.fromHtml("Solicitud: " + data.get(i).getId_solicitud() + " &nbsp; &nbsp; &nbsp; &nbsp; Id Pdv: " +data.get(i).getId_pos()+ "<br> Nombre Punto: "+ data.get(i).getRazon() + " <br> " + data.get(i).getEstado() + " &nbsp; &nbsp;  - &nbsp; &nbsp; " + data.get(i).getFecha())), technology);


                }
            }
        }
        return expandableListDetail;
    }
}
