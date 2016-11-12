package net.movilbox.dcsuruguay.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.movilbox.dcsuruguay.DataBase.DBHelper;
import net.movilbox.dcsuruguay.Model.NoVisita;
import net.movilbox.dcsuruguay.Model.TimeService;
import net.movilbox.dcsuruguay.Model.Tracing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by germangarcia on 7/07/16.
 */
public class FuncionesGenerales {

    private SQLiteDatabase database;

    public FuncionesGenerales(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public int validarTablas() {
        int indicador = 0;
        String sql = "SELECT * FROM departamento";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            indicador = 1;
        }
        cursor.close();
        return indicador;
    }

    public boolean deleteObjectPuntos(String name_database) {
        int a = database.delete(name_database, "accion = ?", new String[]{""});

        return a > 0;

    }

    public boolean deleteObject(String name_database) {
        int a = database.delete(name_database, null, null);

        return a > 0;

    }

    public boolean updateFechaSincro(String data, int idUsert) {

        ContentValues valores = new ContentValues();

        valores.put("fechaSincroOffline", data);

        int p = database.update("login", valores, String.format("id = %1$s", idUsert), null);

        return p > 0;

    }

    public String getDatePhoneFecha() {

        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();

        String df = new SimpleDateFormat("yyyy-MM-dd").format(date);

        return df;

    }

    public String getDatePhoneHora() {

        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();

        String df = new SimpleDateFormat("HH:mm:ss").format(date);

        return df;

    }
    public TimeService getTimeService () {

        Cursor cursor;
        TimeService indicador = new TimeService();

        String sql = "SELECT idUser, traking, timeservice, idDistri, dataBase, fechainicial, fechafinal FROM time_services";
        cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            indicador = new TimeService();
            indicador.setIdUser(cursor.getInt(0));
            indicador.setTraking(cursor.getInt(1));
            indicador.setTimeservice(cursor.getInt(2));
            indicador.setIdDistri(cursor.getString(3));
            indicador.setDataBase(cursor.getString(4));
            indicador.setFechainicial(cursor.getString(5));
            indicador.setFechaFinal(cursor.getString(6));
        }
        cursor.close();
        return indicador;
    }

    public boolean insertTracing(Tracing tracing) {
        ContentValues values = new ContentValues();

        try {
            values.put("idUser", tracing.getIdUser());
            values.put("imei", tracing.getImei());
            values.put("dateTime", tracing.getDateTime());
            values.put("batteryLavel", tracing.getBatteryLavel());
            values.put("temperatura", tracing.getTemperatura());
            values.put("latitud", tracing.getLatitud());
            values.put("longitud", tracing.getLongitud());
            values.put("idDistri", tracing.getIdDistri());
            values.put("dataBase", tracing.getDataBase());

            database.insert("tracing", null, values);
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        }

        return true;
    }

    public List<Tracing> getTracingService () {

        List<Tracing> tracingArrayList = new ArrayList<>();

        String sql = "SELECT idUser, imei, dateTime, batteryLavel, temperatura, latitud, longitud, idDistri, dataBase FROM tracing ";
        Cursor cursor = database.rawQuery(sql, null);

        Tracing tracing;

        if (cursor.moveToFirst()) {
            do {
                tracing = new Tracing();
                tracing.setIdUser(cursor.getInt(0));
                tracing.setImei(cursor.getString(1));
                tracing.setDateTime(cursor.getString(2));
                tracing.setBatteryLavel(cursor.getInt(3));
                tracing.setTemperatura(cursor.getInt(4));
                tracing.setLatitud(cursor.getDouble(5));
                tracing.setLongitud(cursor.getDouble(6));
                tracing.setIdDistri(cursor.getString(7));
                tracing.setDataBase(cursor.getString(8));

                tracingArrayList.add(tracing);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return tracingArrayList;

    }

    public boolean insertTimeServices(TimeService timeService) {
        ContentValues values = new ContentValues();
        try {

            values.put("idUser", timeService.getIdUser());
            values.put("traking", timeService.getTraking());
            values.put("timeservice", timeService.getTimeservice());
            values.put("idDistri", timeService.getIdDistri());
            values.put("dataBase", timeService.getDataBase());
            values.put("fechainicial", timeService.getFechainicial());
            values.put("fechafinal", timeService.getFechaFinal());

            database.insert("time_services", null, values);
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        }

        return true;

    }

    public boolean deleteAllServiTime() {
        int a = database.delete("time_services", null, null);

        return a > 0;
    }

    public boolean deleteAllServiTimeTrace() {
        int a = database.delete("tracing", null, null);

        return a > 0;
    }

    public boolean insertNoVenta(NoVisita noVisita) {
        ContentValues values = new ContentValues();
        try {

            values.put("idpos", noVisita.getIdpos());
            values.put("motivo", noVisita.getMotivo());
            values.put("observacion", noVisita.getObservacion());
            values.put("latitud", noVisita.getLatitud());
            values.put("longitud", noVisita.getLongitud());

            database.insert("no_venta", null, values);
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        }

        updateTipoVisita(noVisita.getIdpos(), 2);


        return true;

    }

    public boolean updateTipoVisita(int idpos, int valor){

        ContentValues valores = new ContentValues();

        valores.put("tipo_visita", valor);

        String[] args = new String[]{String.valueOf(idpos)};
        int p = database.update("indicadoresdas_detalle", valores, "idpos = ?", args);
        //db.close();
        return p > 0;

    }

    public List<NoVisita> getNoVenta () {

        List<NoVisita> noVisitaArrayList = new ArrayList<>();

        String sql = "SELECT idpos, motivo, observacion, latitud, longitud FROM no_venta ";
        Cursor cursor = database.rawQuery(sql, null);

        NoVisita noVisita;

        if (cursor.moveToFirst()) {
            do {
                noVisita = new NoVisita();
                noVisita.setIdpos(cursor.getInt(0));
                noVisita.setMotivo(cursor.getInt(1));
                noVisita.setObservacion(cursor.getString(2));
                noVisita.setLatitud(cursor.getDouble(3));
                noVisita.setLongitud(cursor.getDouble(4));

                noVisitaArrayList.add(noVisita);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return noVisitaArrayList;

    }

    public boolean deleteNoVenta() {
        int a = database.delete("no_venta", null, null);
        return a > 0;
    }

}
