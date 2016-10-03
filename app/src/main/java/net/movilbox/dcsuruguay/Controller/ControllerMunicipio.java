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
public class ControllerMunicipio {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public ControllerMunicipio(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean insertMunicipios(EntEstandar entEstandar) {
        ContentValues values = new ContentValues();
        try {
            switch (entEstandar.getEstado_accion()) {
                case 1:
                    //Insertar.
                    values.put("id_muni", entEstandar.getId_muni());
                    values.put("descripcion", entEstandar.getDescripcion());
                    values.put("departamento", entEstandar.getDepartamento());
                    values.put("estado_accion", entEstandar.getEstado_accion());

                    database.insert("municipios", null, values);
                    break;

                case 2:
                    //Actualizar.
                    values.put("id_muni", entEstandar.getId_muni());
                    values.put("descripcion", entEstandar.getDescripcion());
                    values.put("departamento", entEstandar.getDepartamento());
                    values.put("estado_accion", entEstandar.getEstado_accion());

                    database.update("municipios", values, String.format("id_muni = %1$s", entEstandar.getId_muni()), null);
                    break;

                case 0:
                    //Eliminar
                    database.delete("municipios", "id_muni = ? ", new String[]{String.valueOf(entEstandar.getId_muni())});
                    break;
            }

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            database.close();
            return false;
        }

        return true;
    }

    public List<EntEstandar> getMunicipios(int parametro) {

        List<EntEstandar> entEstandars = new ArrayList<>();
        String sql = "SELECT id_muni, descripcion, departamento FROM municipios WHERE departamento = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(parametro)});
        EntEstandar entEstandar;
        if (cursor.moveToFirst()) {
            do {
                entEstandar = new EntEstandar();
                entEstandar.setId_muni(cursor.getInt(0));
                entEstandar.setDescripcion(cursor.getString(1));
                entEstandar.setDepartamento(cursor.getInt(2));

                entEstandars.add(entEstandar);

            } while (cursor.moveToNext());

        }

        return entEstandars;

    }

}
