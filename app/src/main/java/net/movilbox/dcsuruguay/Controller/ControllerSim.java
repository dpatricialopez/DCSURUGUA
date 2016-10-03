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
public class ControllerSim {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public ControllerSim(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean insertReferenciaSim(EntLisSincronizar data) {

        ContentValues values = new ContentValues();

        try {
            values.put("id", data.getId());
            values.put("pn", data.getPn());
            values.put("producto", data.getProducto());
            values.put("precio_referencia", data.getPrecio_referencia());
            values.put("precio_publico", data.getPrecio_publico());
            values.put("estado_accion", data.getEstado_accion());
            values.put("tipo_tabla", data.getTipo_tabla());

            database.insert("refes_sims", null, values);

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        }

        return true;
    }
}
