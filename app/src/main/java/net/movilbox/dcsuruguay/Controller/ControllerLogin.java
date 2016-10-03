package net.movilbox.dcsuruguay.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.movilbox.dcsuruguay.DataBase.DBHelper;
import net.movilbox.dcsuruguay.Model.EntLoginR;

/**
 * Created by germangarcia on 7/07/16.
 */
public class ControllerLogin {

    private SQLiteDatabase database;

    public ControllerLogin(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public EntLoginR getUserLogin() {

        EntLoginR indicador = new EntLoginR();
        String sql = "SELECT * FROM login";

        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            indicador.setId(cursor.getInt(0));
            indicador.setCedula(cursor.getInt(1));
            indicador.setNombre(cursor.getString(2));
            indicador.setApellido(cursor.getString(3));
            indicador.setUser(cursor.getString(4));
            indicador.setEstado(cursor.getInt(5));
            indicador.setBd(cursor.getString(6));
            indicador.setId_distri(cursor.getInt(7));
            indicador.setPerfil(cursor.getInt(8));
            indicador.setIgv(cursor.getInt(9));
            indicador.setIntervalo(cursor.getInt(10));
            indicador.setHora_inicial(cursor.getString(11));
            indicador.setHora_final(cursor.getString(12));
            indicador.setCantidad_envios(cursor.getInt(13));
            indicador.setFecha_sincroniza(cursor.getString(14));
            indicador.setFecha_sincroniza_offline(cursor.getString(15));
            indicador.setPassword(cursor.getString(16));
        }

        return indicador;
    }

    public boolean updateFechaSincroLogin(String data, int idUsert) {

        ContentValues valores = new ContentValues();

        valores.put("fechaSincro", data);

        int p = database.update("login", valores, String.format("id = %1$s", idUsert), null);
        return p > 0;
    }

    public boolean updateFechaSincro(String data, int idUsert) {

        ContentValues valores = new ContentValues();

        valores.put("fechaSincroOffline", data);

        int p = database.update("login", valores, String.format("id = %1$s", idUsert), null);
        return p > 0;

    }

    public boolean insertLoginUser(EntLoginR data) {

        ContentValues values = new ContentValues();
        try {

            values.put("id", data.getId());
            values.put("cedula", data.getCedula());
            values.put("nombre", data.getNombre());
            values.put("apellido", data.getApellido());
            values.put("user", data.getUser().toUpperCase());
            values.put("estado", data.getEstado());
            values.put("bd", data.getBd());
            values.put("id_distri", data.getId_distri());
            values.put("perfil", data.getPerfil());
            values.put("igv", data.getIgv());
            values.put("intervalo", data.getIntervalo());
            values.put("hora_inicial", data.getHora_inicial());
            values.put("hora_final", data.getHora_final());
            values.put("cantidad_envios", data.getCantidad_envios());
            values.put("fechaSincro", data.getFecha_sincroniza());
            values.put("fechaSincroOffline", "");
            values.put("password", data.getPassword());

            database.insert("login", null, values);
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        }

        return true;

    }

    public boolean validateLoginUser(String user, String password ) {
        Cursor cursor;
        boolean indicador = false;
        String[] args = new String[] {user.trim(), password.trim()};
        String sql = "SELECT * FROM login WHERE user =? AND password =?";
        cursor = database.rawQuery(sql, args);
        if (cursor.moveToFirst()) {
            indicador = true;
        }
        return indicador;
    }



}
