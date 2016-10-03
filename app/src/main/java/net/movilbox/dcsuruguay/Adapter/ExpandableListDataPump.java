package net.movilbox.dcsuruguay.Adapter;

import net.movilbox.dcsuruguay.Model.EntEstandar;
import net.movilbox.dcsuruguay.Model.EntListReferencias;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListDataPump {

    public static HashMap<String, List<EntEstandar>> getData(List<EntListReferencias> data) {

        HashMap<String, List<EntEstandar>> expandableListDetail = new LinkedHashMap<>();

        if (data != null) {

            for (int i = 0; i < data.size(); i++) {

                List<EntEstandar> technology = new ArrayList<>();

                EntEstandar entEstandar;
                for (int a = 0; a < data.get(i).getEntEstandarList().size(); a++) {

                    entEstandar = new EntEstandar();
                    entEstandar.setId(data.get(i).getEntEstandarList().get(a).getId());
                    entEstandar.setDescripcion(data.get(i).getEntEstandarList().get(a).getDescripcion());
                    technology.add(entEstandar);
                }

                expandableListDetail.put(data.get(i).getIdPaquete()+"", technology);

            }

        }

        return expandableListDetail;
    }


}
