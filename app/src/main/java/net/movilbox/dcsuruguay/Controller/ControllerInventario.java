package net.movilbox.dcsuruguay.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.movilbox.dcsuruguay.DataBase.DBHelper;
import net.movilbox.dcsuruguay.Model.EntCatalogo;
import net.movilbox.dcsuruguay.Model.EntEstandar;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.Model.EntListReferencias;
import net.movilbox.dcsuruguay.Model.EntRefeCatalogo;
import net.movilbox.dcsuruguay.Model.EntRefeSerial;
import net.movilbox.dcsuruguay.Model.Motivos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by germangarcia on 11/07/16.
 */
public class ControllerInventario {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public ControllerInventario(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean insertLisInventario(EntLisSincronizar data) {

        ContentValues values = new ContentValues();
        try {
            switch (data.getEstado_accion()) {
                case 1:
                    values.put("id", data.getId());
                    values.put("id_referencia", data.getId_referencia());
                    values.put("serie", data.getSerie());
                    values.put("paquete", data.getPaquete());
                    values.put("id_vendedor", data.getId_vendedor());
                    values.put("distri", data.getDistri());
                    values.put("tipo_pro", data.getTipo_pro());
                    values.put("tipo_tabla", data.getTipo_tabla());
                    values.put("estado_accion", data.getEstado_accion());

                    database.insert("inventario", null, values);
                    break;
                case 0:
                    database.delete("inventario", "id = ? AND tipo_pro = ? ", new String[]{String.valueOf(data.getId()), String.valueOf(data.getTipo_pro())});
                    break;
            }
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        }

        return true;

    }

    public List<Motivos> listPaquetes(int indicador){

        List<Motivos> motivosListist = new ArrayList<>();
        String sql = "";

        if (indicador == -1)
            sql = "SELECT -1 id, 'SELECCIONAR' descripcion UNION SELECT paquete,(CASE paquete WHEN 0 THEN ('SIN PAQUETE ('|| paquete ||')') ELSE ('PAQUETE ('|| paquete ||')') END) descripcion  FROM inventario WHERE paquete > ? GROUP BY paquete";
        else
            sql = "SELECT 0 id, 'SELECCIONAR' descripcion UNION SELECT paquete,('PAQUETE ('|| paquete ||')') descripcion  FROM inventario WHERE paquete > ? GROUP BY paquete";

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(indicador)});

        Motivos entRutero;

        if (cursor.moveToFirst()) {

            do {
                entRutero = new Motivos();
                entRutero.setId(cursor.getInt(0));
                entRutero.setDescripcion(cursor.getString(1));

                motivosListist.add(entRutero);

            } while (cursor.moveToNext());

        }

        return motivosListist;
    }

    public List<Motivos> listPaquetesDirecta(int indicador) {

        List<Motivos> motivosListist = new ArrayList<>();
        String sql = "";

        if (indicador == -1)
            sql = "SELECT -1 id, 'SELECCIONAR' descripcion UNION SELECT paquete,(CASE paquete WHEN 0 THEN ('SIN PAQUETE ('|| paquete ||')') ELSE ('PAQUETE ('|| paquete ||')') END) descripcion  FROM inventario WHERE paquete > ? AND tipo_pro = 1 GROUP BY paquete";
        else
            sql = "SELECT 0 id, 'SELECCIONAR' descripcion UNION SELECT paquete,('PAQUETE ('|| paquete ||')') descripcion  FROM inventario WHERE paquete > ? AND tipo_pro = 1 GROUP BY paquete";

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(indicador)});

        Motivos entRutero;

        if (cursor.moveToFirst()) {

            do {
                entRutero = new Motivos();
                entRutero.setId(cursor.getInt(0));
                entRutero.setDescripcion(cursor.getString(1));

                motivosListist.add(entRutero);

            } while (cursor.moveToNext());

        }

        return motivosListist;
    }

    public List<EntListReferencias> listReferencias(int indicador) {

        List<EntListReferencias> referenciaList = new ArrayList<>();
        String sql = "";

        if (indicador == -1)
            sql = "SELECT 0 paquete, 0 id_referencia, 0 tipo_pro,'SELECCIONAR' producto, 0 cantidad UNION SELECT inv.paquete,inv.id_referencia,inv.tipo_pro,rs.producto,count(inv.id) cantidad FROM inventario inv INNER JOIN refes_sims rs ON rs.id = inv.id_referencia WHERE inv.paquete = ? GROUP BY inv.id_referencia";
        else
            sql = "SELECT inv.paquete,inv.id_referencia,inv.tipo_pro,rs.producto,count(inv.id) cantidad FROM inventario inv INNER JOIN refes_sims rs ON rs.id = inv.id_referencia WHERE inv.paquete = ? GROUP BY inv.id_referencia";

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(indicador)});

        EntListReferencias referencias;

        if (cursor.moveToFirst()) {

            do {
                referencias = new EntListReferencias();
                referencias.setIdPaquete(cursor.getInt(0));
                referencias.setIdRefe(cursor.getInt(1));
                referencias.setTipoPro(cursor.getInt(2));
                referencias.setNomPro(cursor.getString(3));
                referencias.setCantidad(cursor.getInt(4));

                referenciaList.add(referencias);

            } while (cursor.moveToNext());

        }

        return referenciaList;
    }

    public List<EntRefeSerial> listSeriePaquete(int paquete, int tipoPro) {

        List<EntRefeSerial> referenciaList = new ArrayList<>();

        String sql = "SELECT inv.id, inv.serie, inv.tipo_pro, inv.paquete, inv.id_referencia, rs.producto FROM inventario AS inv INNER JOIN  refes_sims AS rs ON inv.id_referencia = rs.id WHERE paquete = ? AND tipo_pro = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(paquete), String.valueOf(tipoPro)});

        EntRefeSerial referencias;

        if (cursor.moveToFirst()) {

            do {
                referencias = new EntRefeSerial();
                referencias.setId_referencia(cursor.getInt(0));
                referencias.setSerie(cursor.getString(1));
                referencias.setTipo_pro(cursor.getInt(2));
                referencias.setId_paquete(cursor.getInt(3));
                referencias.setId_referencia(cursor.getInt(4));
                referencias.setNombreReferencia(cursor.getString(5));

                referenciaList.add(referencias);

            } while (cursor.moveToNext());

        }

        return referenciaList;
    }

    public List<EntRefeSerial> listReferenciasSerial(int id_paquete, int id_refe, int tipo_pro, int indicador) {

        List<EntRefeSerial> referenciaList = new ArrayList<>();

        String sql;

        if (indicador == 1) {
            sql = "SELECT 0 id, 'SELECCIONAR' serie, 0 tipo_pro, 0 paquete, 0 id_referencia UNION SELECT inv.id, inv.serie, inv.tipo_pro, inv.paquete, inv.id_referencia FROM inventario AS inv WHERE inv.paquete = ? AND inv.id_referencia = ? AND inv.tipo_pro = ?  ";
        } else {
            sql = "SELECT id, serie, tipo_pro, paquete, id_referencia FROM inventario WHERE paquete = ? AND id_referencia = ? AND tipo_pro = ? ";
        }

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(id_paquete), String.valueOf(id_refe), String.valueOf(tipo_pro)});

        EntRefeSerial referencias;

        if (cursor.moveToFirst()) {

            do {
                referencias = new EntRefeSerial();
                referencias.setId_inven(cursor.getInt(0));
                referencias.setSerie(cursor.getString(1));
                referencias.setTipo_pro(cursor.getInt(2));
                referencias.setId_paquete(cursor.getInt(3));
                referencias.setId_referencia(cursor.getInt(4));

                referenciaList.add(referencias);

            } while (cursor.moveToNext());

        }

        return referenciaList;
    }

    public List<EntListReferencias> listPaqueteInvent(int id_referencia) {

        List<EntListReferencias> referenciaList = new ArrayList<>();
        String sql = "SELECT paquete, id_referencia, tipo_pro, count(id) cantidad FROM inventario inv WHERE id_referencia = ? GROUP BY paquete ";

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(id_referencia)});

        if (cursor.moveToFirst()) {

            do {

                EntListReferencias referencias = new EntListReferencias();
                referencias.setIdPaquete(cursor.getInt(0));
                referencias.setIdRefe(cursor.getInt(1));
                referencias.setTipoPro(cursor.getInt(2));
                referencias.setCantidad(cursor.getInt(3));

                String sql2 = "SELECT id, serie FROM inventario WHERE paquete = ? AND tipo_pro = ? AND id_referencia = ? ";
                Cursor cursor2 = database.rawQuery(sql2, new String[]{String.valueOf(cursor.getInt(0)), String.valueOf(cursor.getInt(2)), String.valueOf(id_referencia) });
                List<EntEstandar> entEstandarList = new ArrayList<>();
                if (cursor2.moveToFirst()) {

                    do {
                        EntEstandar entEstandar = new EntEstandar();
                        entEstandar.setId(cursor2.getInt(0));
                        entEstandar.setDescripcion(cursor2.getString(1));

                        entEstandarList.add(entEstandar);
                    } while (cursor2.moveToNext());

                    referencias.setEntEstandarList(entEstandarList);
                }

                referenciaList.add(referencias);

            } while (cursor.moveToNext());

        }

        return referenciaList;
    }

    public List<EntListReferencias> listReferenciasesReport(int indicador) {

        List<EntListReferencias> referenciaList = new ArrayList<>();

        String sql = "";

        // 1. Reporte
        // 2. Spinner

        if (indicador == 1) {
            sql = "SELECT inv.paquete, inv.id_referencia, inv.tipo_pro, CASE WHEN rs.producto is null THEN d_ca.producto ELSE rs.producto END producto, count(inv.id) cantidad FROM inventario inv LEFT JOIN refes_sims rs ON rs.id = inv.id_referencia LEFT JOIN detalle_catalogo d_ca ON d_ca.id = inv.id_referencia GROUP BY inv.id_referencia";
        } else if (indicador == 2) {
            sql = "SELECT 0 paquete, 0 id_referencia, 0 tipo_pro,'SELECCIONAR' producto, 0 cantidad UNION SELECT inv.paquete,inv.id_referencia, inv.tipo_pro, rs.producto,count(inv.id) cantidad FROM inventario inv INNER JOIN refes_sims rs ON rs.id = inv.id_referencia GROUP BY inv.id_referencia";
        }

        Cursor cursor = database.rawQuery(sql, null);

        EntListReferencias referencias;

        if (cursor.moveToFirst()) {

            do {
                referencias = new EntListReferencias();
                referencias.setIdPaquete(cursor.getInt(0));
                referencias.setIdRefe(cursor.getInt(1));
                referencias.setTipoPro(cursor.getInt(2));
                referencias.setNomPro(cursor.getString(3));
                referencias.setCantidad(cursor.getInt(4));

                referenciaList.add(referencias);

            } while (cursor.moveToNext());

        }

        return referenciaList;
    }

    public List<EntCatalogo> listCatalogo() {

        List<EntCatalogo> referenciaList = new ArrayList<>();

        String sql = "SELECT id, descripcion, speech, pantalla, cam_frontal, cam_tras, flash, banda, memoria, expandible, bateria, bluetooth, tactil, tec_fisico, carrito_compras, correo, enrutador, radio, wifi, gps, so, web, img_pri_catalogo FROM catalogo";

        Cursor cursor = database.rawQuery(sql, null);

        EntCatalogo referencias;

        if (cursor.moveToFirst()) {

            do {
                referencias = new EntCatalogo();
                referencias.setId(cursor.getInt(0));
                referencias.setDescripcion(cursor.getString(1));
                referencias.setSpeech(cursor.getString(2));
                referencias.setPantalla(cursor.getString(3));
                referencias.setCam_frontal(cursor.getString(4));
                referencias.setCam_tras(cursor.getString(5));
                referencias.setFlash(cursor.getString(6));
                referencias.setBanda(cursor.getString(7));
                referencias.setMemoria(cursor.getString(8));
                referencias.setExpandible(cursor.getString(9));
                referencias.setBateria(cursor.getString(10));
                referencias.setBluetooth(cursor.getString(11));
                referencias.setTactil(cursor.getString(12));
                referencias.setTec_fisico(cursor.getString(13));
                referencias.setCarrito_compras(cursor.getString(14));
                referencias.setCorreo(cursor.getString(15));
                referencias.setEnrutador(cursor.getString(16));
                referencias.setRadio(cursor.getString(17));
                referencias.setWifi(cursor.getString(18));
                referencias.setGps(cursor.getString(19));
                referencias.setSo(cursor.getString(20));
                referencias.setWeb(cursor.getString(21));
                referencias.setImg_pri_catalogo(cursor.getString(22));

                referenciaList.add(referencias);

            } while (cursor.moveToNext());

        }

        return referenciaList;
    }

    public double getValorReferencia(int idRefencia, int valorInt) {

        double valorReferencia = 0.0;
        String sql = "";

        if (valorInt == 1) {
            // Valor directo
            sql = "SELECT valor_directo FROM lista_precios WHERE id_referencia = ? GROUP BY id_referencia";
        } else if (valorInt == 2) {
            // Valor Referencia
            sql = "SELECT valor_referencia FROM lista_precios WHERE id_referencia = ? GROUP BY id_referencia";
        }


        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(idRefencia)});

        if (cursor.moveToFirst()) {
            valorReferencia = cursor.getDouble(0);
        }

        return valorReferencia;

    }

    public List<EntRefeCatalogo> listCatalogoReferencia(int id_catalogo, int id_ruta) {

        List<EntRefeCatalogo> referenciaList = new ArrayList<>();

        String sql = "SELECT dc.id, dc.producto, lp.valor_referencia, lp.valor_directo, count(inv.id) cant, inv.tipo_pro FROM detalle_catalogo dc INNER JOIN lista_precios lp ON lp.id_referencia = dc.id INNER JOIN inventario inv ON (inv.id_referencia = dc.id AND inv.tipo_pro = 2) WHERE dc.id_catalogo = ? AND lp.idpos = ? GROUP BY dc.id";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(id_catalogo), String.valueOf(id_ruta)});

        EntRefeCatalogo referencias;

        if (cursor.moveToFirst()) {

            do {
                referencias = new EntRefeCatalogo();
                referencias.setId_refe(cursor.getInt(0));
                referencias.setDescripcion(cursor.getString(1));
                referencias.setValor_valor_referencia(cursor.getDouble(2));
                referencias.setValor_directo(cursor.getDouble(3));
                referencias.setCant(cursor.getInt(4));
                referencias.setTipo_pro(cursor.getInt(5));

                referenciaList.add(referencias);

            } while (cursor.moveToNext());

        }

        return referenciaList;
    }

    public List<EntRefeCatalogo> listCatalogoReferenciaDirecta(int id_catalogo) {

        List<EntRefeCatalogo> referenciaList = new ArrayList<>();

        String sql = "SELECT dc.id, dc.producto, count(inv.id) cant, inv.tipo_pro FROM detalle_catalogo dc INNER JOIN inventario inv ON (inv.id_referencia = dc.id AND inv.tipo_pro = 2) WHERE dc.id_catalogo = ? GROUP BY dc.id ";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(id_catalogo)});

        EntRefeCatalogo referencias;

        if (cursor.moveToFirst()) {

            do {
                referencias = new EntRefeCatalogo();
                referencias.setId_refe(cursor.getInt(0));
                referencias.setDescripcion(cursor.getString(1));
                //referencias.setValor_valor_referencia(cursor.getDouble(2));
                //referencias.setValor_directo(cursor.getDouble(3));
                referencias.setCant(cursor.getInt(2));
                referencias.setTipo_pro(cursor.getInt(3));

                referenciaList.add(referencias);

            } while (cursor.moveToNext());

        }

        return referenciaList;
    }

    public boolean deleteOneInventsrio(String serie, int idreferencia, int tipoPro) {
        int a = database.delete("inventario", "serie = ? AND id_referencia = ? AND tipo_pro = ?", new String[]{String.valueOf(serie), String.valueOf(idreferencia), String.valueOf(tipoPro)});
        return a > 0;
    }

}
