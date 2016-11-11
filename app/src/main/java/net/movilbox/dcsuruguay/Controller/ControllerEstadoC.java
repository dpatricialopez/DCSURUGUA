package net.movilbox.dcsuruguay.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import net.movilbox.dcsuruguay.DataBase.DBHelper;
import net.movilbox.dcsuruguay.Model.EntEstandar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by germangarcia on 7/07/16.
 */
public class ControllerEstadoC {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public ControllerEstadoC(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean insertEstadoComercial(EntEstandar entEstandar) {

        ContentValues values = new ContentValues();
        try {

            switch (entEstandar.getEstado_accion()) {

                case 1:
                    //Insertar.
                    values.put("id", entEstandar.getId());
                    values.put("descripcion", entEstandar.getDescripcion());
                    values.put("estado_accion", entEstandar.getEstado_accion());

                    database.insert("estado_comercial", null, values);
                    break;

                case 2:
                    //Actualizar.
                    values.put("id", entEstandar.getId());
                    values.put("descripcion", entEstandar.getDescripcion());
                    values.put("estado_accion", entEstandar.getEstado_accion());

                    database.update("estado_comercial", values, String.format("id = %1$s", entEstandar.getId()), null);
                    break;

                case 0:
                    //Eliminar
                    database.delete("estado_comercial", "id = ? ", new String[]{String.valueOf(entEstandar.getId())});
                    break;
            }

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            database.close();
            return false;
        } finally {
            //database.close();
        }

        return true;
    }

    public List<EntEstandar> getEstadoComercial() {

        List<EntEstandar> entEstandars = new ArrayList<>();
        String sql = "SELECT id, descripcion FROM estado_comercial";
        Cursor cursor = database.rawQuery(sql, null);
        EntEstandar entEstandar;

        if (cursor.moveToFirst()) {

            do {
                entEstandar = new EntEstandar();
                entEstandar.setId(cursor.getInt(0));
                entEstandar.setDescripcion(cursor.getString(1));

                entEstandars.add(entEstandar);

            } while (cursor.moveToNext());

        }
        cursor.close();
        return entEstandars;

    }
}
