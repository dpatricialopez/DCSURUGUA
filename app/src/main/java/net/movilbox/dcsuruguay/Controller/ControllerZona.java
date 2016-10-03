package net.movilbox.dcsuruguay.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.movilbox.dcsuruguay.DataBase.DBHelper;
import net.movilbox.dcsuruguay.Model.EntEstandar;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by germangarcia on 11/07/16.
 */
public class ControllerZona {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public ControllerZona(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public boolean insertZona(EntLisSincronizar data) {

        ContentValues values = new ContentValues();
        try {
            values.put("id", data.getId());
            values.put("descripcion", data.getDescripcion());
            values.put("id_territorio", data.getId_territorio());
            values.put("tipo_tabla", data.getTipo_tabla());

            database.insert("zona", null, values);

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        }

        return true;
    }

    public List<EntEstandar> getRuta(int id_territorio) {

        List<EntEstandar> entEstandars = new ArrayList<>();
        String sql = "SELECT 0 AS id, 'SELECCIONAR' AS descripcion UNION SELECT id, descripcion FROM zona WHERE id_territorio = ? ";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(id_territorio)});
        EntEstandar entEstandar;

        if (cursor.moveToFirst()) {

            do {
                entEstandar = new EntEstandar();
                entEstandar.setId(cursor.getInt(0));
                entEstandar.setDescripcion(cursor.getString(1));

                entEstandars.add(entEstandar);

            } while (cursor.moveToNext());

        }

        return entEstandars;

    }

}
