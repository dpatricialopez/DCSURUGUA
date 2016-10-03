package net.movilbox.dcsuruguay.Adapter;


import net.movilbox.dcsuruguay.Model.DetalleVenta;
import net.movilbox.dcsuruguay.Model.DetalleVenta2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListDataPumpVenta {

    public static HashMap<String, List<DetalleVenta2>> getData(List<DetalleVenta> data) {

        DecimalFormat format = new DecimalFormat("#,###");

        HashMap<String, List<DetalleVenta2>> expandableListDetail = new LinkedHashMap<>();

        if (data != null) {

            String idpos = "";

            for (int i = 0; i < data.size(); i++) {

                List<DetalleVenta2> technology = new ArrayList<>();

                for (int a = 0; a < data.get(i).getDetalleVenta2List().size(); a++) {

                    DetalleVenta2 detalleVenta2 = new DetalleVenta2();

                    detalleVenta2.setIdrefe(data.get(i).getDetalleVenta2List().get(a).getIdrefe());
                    detalleVenta2.setProducto(data.get(i).getDetalleVenta2List().get(a).getProducto());
                    detalleVenta2.setFecha(data.get(i).getDetalleVenta2List().get(a).getFecha());
                    detalleVenta2.setCantidad(data.get(i).getDetalleVenta2List().get(a).getCantidad());
                    detalleVenta2.setId_pos(data.get(i).getDetalleVenta2List().get(a).getId_pos());
                    detalleVenta2.setValor(data.get(i).getDetalleVenta2List().get(a).getValor());
                    detalleVenta2.setDetalleVenta3List(data.get(i).getDetalleVenta2List().get(a).getDetalleVenta3List());

                    technology.add(detalleVenta2);

                }

                if (data.get(i).getPos() == 0) {
                    idpos = "V Direccta";
                } else {
                    idpos = String.valueOf(data.get(i).getPos());
                }

                expandableListDetail.put("Id: "+ idpos + " - Cantidad: " + data.get(i).getCantidad()+" - Valor: $ "+format.format(data.get(i).getValor()), technology);

            }
        }
        return expandableListDetail;
    }


}
