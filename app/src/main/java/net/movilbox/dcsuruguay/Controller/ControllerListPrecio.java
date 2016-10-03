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
public class ControllerListPrecio {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public ControllerListPrecio(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean insertLisPrecios(EntLisSincronizar data) {

        ContentValues values = new ContentValues();

        try {

            values.put("id_referencia", data.getId_referencia());
            values.put("idpos", data.getIdpos());
            values.put("valor_referencia", data.getValor_referencia());
            values.put("valor_directo", data.getValor_directo());
            values.put("tipo_pro", data.getTipo_pro());
            values.put("estado_accion", data.getEstado_accion());

            database.insert("lista_precios", null, values);

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        }

        return true;
    }
}
