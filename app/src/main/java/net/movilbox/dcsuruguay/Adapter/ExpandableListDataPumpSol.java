package net.movilbox.dcsuruguay.Adapter;

import net.movilbox.dcsuruguay.Model.EntReferenciaSol;
import net.movilbox.dcsuruguay.Model.ListEntReferenciaSol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListDataPumpSol {

    public static HashMap<String, List<EntReferenciaSol>> getData(ListEntReferenciaSol data) {

        HashMap<String, List<EntReferenciaSol>> expandableListDetail = new LinkedHashMap<>();

        if (data != null) {

            for (int i = 0; i < data.getEntSolPedidos().size(); i++) {

                List<EntReferenciaSol> technology = new ArrayList<>();

                EntReferenciaSol entEstandar;
                for (int a = 0; a < data.getEntSolPedidos().get(i).getEntReferenciaSols().size(); a++) {

                    entEstandar = new EntReferenciaSol();
                    entEstandar.setId_bodega(data.getEntSolPedidos().get(i).getEntReferenciaSols().get(a).getId_bodega());
                    entEstandar.setId_referencia(data.getEntSolPedidos().get(i).getEntReferenciaSols().get(a).getId_referencia());
                    entEstandar.setProducto(data.getEntSolPedidos().get(i).getEntReferenciaSols().get(a).getProducto());
                    entEstandar.setTotal(data.getEntSolPedidos().get(i).getEntReferenciaSols().get(a).getTotal());
                    entEstandar.setTipo_bodega(data.getEntSolPedidos().get(i).getEntReferenciaSols().get(a).getTipo_bodega());
                    entEstandar.setTipo_ref(data.getEntSolPedidos().get(i).getEntReferenciaSols().get(a).getTipo_ref());
                    entEstandar.setCantidadSol(data.getEntSolPedidos().get(i).getEntReferenciaSols().get(a).getCantidadSol());

                    technology.add(entEstandar);

                }

                expandableListDetail.put(data.getEntSolPedidos().get(i).getNombre_bodega()+ " - " +data.getEntSolPedidos().get(i).getId(), technology);

            }

        }

        return expandableListDetail;
    }


}
