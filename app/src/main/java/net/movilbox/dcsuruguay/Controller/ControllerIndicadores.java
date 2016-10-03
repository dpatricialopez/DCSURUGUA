package net.movilbox.dcsuruguay.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import net.movilbox.dcsuruguay.DataBase.DBHelper;
import net.movilbox.dcsuruguay.Model.EntIndicadores;
import net.movilbox.dcsuruguay.Model.EntPuntoIndicado;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by germangarcia on 15/07/16.
 */
public class ControllerIndicadores {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public ControllerIndicadores(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean insertIndicadores(EntIndicadores data) {

        ContentValues values = new ContentValues();

        try {
            values.put("cant_ventas_sim", data.getCant_ventas_sim());
            values.put("cant_ventas_combo", data.getCant_ventas_combo());
            values.put("cant_cumplimiento_sim", data.getCant_cumplimiento_sim());
            values.put("cant_cumplimiento_combo", data.getCant_cumplimiento_combo());
            values.put("cant_quiebre_sim_mes", data.getCant_quiebre_sim_mes());
            values.put("id_vendedor", data.getId_vendedor());
            values.put("id_distri", data.getId_distri());

            database.insert("indicadoresdas", null, values);

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        }

        return true;
    }

    public boolean insertDetalleIndicadores(EntIndicadores data) {

        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getEntPuntoIndicadoList().size(); i++) {
                values.put("idpos", data.getEntPuntoIndicadoList().get(i).getIdpos());
                values.put("tipo_visita", data.getEntPuntoIndicadoList().get(i).getTipo_visita());
                values.put("stock_sim", data.getEntPuntoIndicadoList().get(i).getStock_sim());
                values.put("stock_combo", data.getEntPuntoIndicadoList().get(i).getStock_combo());
                values.put("stock_seguridad_sim", data.getEntPuntoIndicadoList().get(i).getStock_seguridad_sim());
                values.put("stock_seguridad_combo", data.getEntPuntoIndicadoList().get(i).getStock_seguridad_combo());
                values.put("dias_inve_sim", data.getEntPuntoIndicadoList().get(i).getDias_inve_sim());
                values.put("dias_inve_combo", data.getEntPuntoIndicadoList().get(i).getDias_inve_combo());
                values.put("id_vendedor", data.getEntPuntoIndicadoList().get(i).getId_vendedor());
                values.put("id_distri", data.getEntPuntoIndicadoList().get(i).getId_distri());
                values.put("fecha_dia", data.getEntPuntoIndicadoList().get(i).getFecha_dia());
                values.put("fecha_ult", data.getEntPuntoIndicadoList().get(i).getFecha_ult());
                values.put("hora_ult", data.getEntPuntoIndicadoList().get(i).getHora_ult());
                values.put("orden", data.getEntPuntoIndicadoList().get(i).getOrden());

                database.insert("indicadoresdas_detalle", null, values);
            }

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        }

        return true;
    }

    public List<EntPuntoIndicado> getIndicadores(int idVendedor, int idDistri) {

        List<EntPuntoIndicado> entIndicadoresArrayList = new ArrayList<>();

        String sql = "SELECT idpos, tipo_visita FROM indicadoresdas_detalle WHERE id_vendedor = ? AND id_distri = ? ";

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(idVendedor), String.valueOf(idDistri)});
        EntPuntoIndicado entEstandar;

        if (cursor.moveToFirst()) {

            do {
                entEstandar = new EntPuntoIndicado();
                entEstandar.setIdpos(cursor.getInt(0));
                entEstandar.setTipo_visita(cursor.getInt(1));

                entIndicadoresArrayList.add(entEstandar);

            } while (cursor.moveToNext());

        }

        return entIndicadoresArrayList;

    }

}
