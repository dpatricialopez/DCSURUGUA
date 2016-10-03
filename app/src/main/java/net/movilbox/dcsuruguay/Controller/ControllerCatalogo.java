package net.movilbox.dcsuruguay.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.movilbox.dcsuruguay.DataBase.DBHelper;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;

/**
 * Created by germangarcia on 11/07/16.
 */
public class ControllerCatalogo {

    private SQLiteDatabase database;

    public ControllerCatalogo(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean insertReferenciaCombos(EntLisSincronizar data) {

        ContentValues values = new ContentValues();
        ContentValues values2 = new ContentValues();
        ContentValues values3 = new ContentValues();

        try {

            values.put("id", data.getId());
            values.put("descripcion", data.getDescripcion());
            values.put("precioventa", data.getPrecioventa());
            values.put("speech", data.getSpeech());
            values.put("pantalla", data.getPantalla());
            values.put("cam_frontal", data.getCam_frontal());
            values.put("cam_tras", data.getCam_tras());
            values.put("flash", data.getFlash());
            values.put("banda", data.getBanda());
            values.put("memoria", data.getMemoria());
            values.put("expandible", data.getExpandible());
            values.put("bateria", data.getBateria());
            values.put("bluetooth", data.getBluetooth());
            values.put("tactil", data.getTactil());
            values.put("tec_fisico", data.getTec_fisico());
            values.put("carrito_compras", data.getCarrito_compras());
            values.put("correo", data.getCorreo());
            values.put("enrutador", data.getEnrutador());
            values.put("radio", data.getRadio());
            values.put("wifi", data.getWifi());
            values.put("gps", data.getGps());
            values.put("so", data.getSo());
            values.put("web", data.getWeb());
            values.put("img_pri_catalogo", data.getImg_pri_catalogo());
            values.put("tipo_tabla", data.getTipo_tabla());

            database.insert("catalogo", null, values);

            for (int l = 0; l < data.getEntRefSimsList().size(); l++) {

                values2.put("id", data.getEntRefSimsList().get(l).getId());
                values2.put("id_catalogo", data.getEntRefSimsList().get(l).getId_catalogo());
                values2.put("pn", data.getEntRefSimsList().get(l).getPn());
                values2.put("producto", data.getEntRefSimsList().get(l).getProducto());
                values2.put("estado_accion", data.getEntRefSimsList().get(l).getEstado_accion());

                database.insert("detalle_catalogo", null, values2);

            }

            for (int l = 0; l < data.getEntImgCatalogoList().size(); l++) {

                values3.put("url", data.getEntImgCatalogoList().get(l).getUrl());
                values3.put("id_catalogo", data.getEntImgCatalogoList().get(l).getId_catalogo());

                database.insert("img_catalogo", null, values3);

            }

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        }

        return true;
    }
}
