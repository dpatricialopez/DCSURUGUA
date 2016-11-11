package net.movilbox.dcsuruguay.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.movilbox.dcsuruguay.DataBase.DBHelper;
import net.movilbox.dcsuruguay.Model.EntCarritoVenta;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by germangarcia on 11/07/16.
 */
public class ControllerPunto {

    private SQLiteDatabase database;

    public ControllerPunto(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // Inserta en la base de datos local los puntos recuperados en cada sincronización
    public boolean insertPunto(EntLisSincronizar data, int indicardor) {

        ContentValues values = new ContentValues();

        try {

            values.put("categoria", data.getCategoria());
            values.put("cedula", data.getCedula());
            values.put("celular", data.getCelular());
            values.put("ciudad", data.getCiudad());
            values.put("depto", data.getDepto());
            values.put("email", data.getEmail());
            values.put("estado_com", data.getEstado_com());

            if (indicardor == 1)
                values.put("idpos", (int) (Math.random()*100+1));
            else
                values.put("idpos",  data.getIdpos());

            values.put("nombre_cliente", data.getNombre_cliente());
            values.put("nombre_punto", data.getNombre_punto());
            values.put("telefono", data.getTelefono());
            values.put("territorio", data.getTerritorio());
            values.put("texto_direccion", data.getTexto_direccion());
            values.put("zona", data.getZona());
            values.put("latitud", data.getLatitud());
            values.put("longitud", data.getLongitud());
            values.put("estado_visita", data.getEstado_visita());
            values.put("detalle", data.getDetalle());
            values.put("tipo_tabla", data.getTipo_tabla());
            values.put("accion", "");
            values.put("dato_otro", data.getDato_otro());
            values.put("otro", data.getOtro());
            values.put("tipo_documento", data.getTipo_documento());
            values.put("barrio", data.getBarrio());
            values.put("vende_recargas", data.getVende_recargas());

            database.insert("punto", null, values);

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        }
        return true;
    }

    // Lista el rutero del vendedor asignado desde la administración
    public List<EntLisSincronizar> getRuteroVendedorUne(int vendedor){

        List<EntLisSincronizar> ruteroArrayList = new ArrayList<>();
        String sql;
        Cursor cursor;
        if (vendedor != 0) {
            sql = "SELECT pun.categoria, pun.cedula, pun.celular, mun.descripcion, det.descripcion AS departamento, pun.email, pun.idpos, pun.nombre_cliente, pun.nombre_punto, pun.telefono, ter.descripcion, zon.descripcion, pun.latitud, pun.longitud, pun.detalle, pun.texto_direccion, " +
                    "       ind.tipo_visita,pun.zona, pun.territorio, pun.dato_otro, pun.otro, ind.stock_sim, ind.stock_combo, ind.stock_seguridad_sim, ind.stock_seguridad_combo, ind.dias_inve_sim, ind.dias_inve_combo, tipo_documento,pun.vende_recargas" +
                    " FROM punto AS pun, departamento AS det, municipios AS mun, territorio AS ter, zona AS zon, indicadoresdas_detalle  AS ind \n" +
                    " WHERE " +
                    "  pun.depto = det.id AND " +
                    "  pun.ciudad = mun.id_muni AND " +
                    "  pun.territorio = ter.id AND " +
                    "  pun.zona = zon.id AND " +
                    "  ind.id_vendedor = ? AND "+
                    "  ind.idpos = pun.idpos ORDER BY ind.tipo_visita, ind.orden ";
            cursor = database.rawQuery(sql, new String[]{String.valueOf(vendedor)});
        } else {
            sql = "SELECT pun.categoria, pun.cedula, pun.celular, mun.descripcion, det.descripcion AS departamento, pun.email, pun.idpos, pun.nombre_cliente, pun.nombre_punto, pun.telefono, ter.descripcion, zon.descripcion, pun.latitud, pun.longitud, pun.detalle, pun.texto_direccion, " +
                    "       ind.tipo_visita,pun.zona, pun.territorio, pun.dato_otro, pun.otro, ind.stock_sim, ind.stock_combo, ind.stock_seguridad_sim, ind.stock_seguridad_combo, ind.dias_inve_sim, ind.dias_inve_combo, tipo_documento,pun.vende_recargas" +
                    " FROM punto AS pun, departamento AS det, municipios AS mun, territorio AS ter, zona AS zon, indicadoresdas_detalle  AS ind \n" +
                    " WHERE " +
                    "  pun.depto = det.id AND " +
                    "  pun.ciudad = mun.id_muni AND " +
                    "  pun.territorio = ter.id AND " +
                    "  pun.zona = zon.id AND " +
                    "  ind.idpos = pun.idpos ORDER BY ind.tipo_visita, ind.orden ";

            cursor = database.rawQuery(sql, null);
        }


        EntLisSincronizar entRutero;

        if (cursor.moveToFirst()) {

            do {
                entRutero = new EntLisSincronizar();
                entRutero.setCategoria(cursor.getInt(0));
                entRutero.setCedula(cursor.getString(1));
                entRutero.setCelular(cursor.getString(2));
                entRutero.setNombreCiudad(cursor.getString(3));
                entRutero.setNombreDepartamento(cursor.getString(4));
                entRutero.setEmail(cursor.getString(5));
                entRutero.setIdpos(cursor.getInt(6));
                entRutero.setNombre_cliente(cursor.getString(7));
                entRutero.setNombre_punto(cursor.getString(8));
                entRutero.setTelefono(cursor.getString(9));
                entRutero.setNombreTerritorio(cursor.getString(10));
                entRutero.setNombreZona(cursor.getString(11));
                entRutero.setLatitud(cursor.getDouble(12));
                entRutero.setLongitud(cursor.getDouble(13));
                entRutero.setDetalle(cursor.getString(14));
                entRutero.setTexto_direccion(cursor.getString(15));
                entRutero.setEstado_visita(cursor.getInt(16));
                entRutero.setZona(cursor.getInt(17));
                entRutero.setTerritorio(cursor.getInt(18));
                entRutero.setDato_otro(cursor.getString(19));
                entRutero.setOtro(cursor.getInt(20));

                entRutero.setStock_sim(cursor.getInt(21));
                entRutero.setStock_combo(cursor.getInt(22));
                entRutero.setStock_seguridad_sim(cursor.getInt(23));
                entRutero.setStock_seguridad_combo(cursor.getInt(24));
                entRutero.setDias_inve_sim(cursor.getDouble(25));
                entRutero.setDias_inve_combo(cursor.getDouble(26));

                entRutero.setTipo_documento(cursor.getInt(27));
                entRutero.setVende_recargas(cursor.getInt(28));

                ruteroArrayList.add(entRutero);

            } while (cursor.moveToNext());

        }
        cursor.close();
        return ruteroArrayList;
    }

    // Recupera el punto de venta... parametros id del punto
    public EntLisSincronizar getRuteroVendedor(int idpos){

        EntLisSincronizar  entRutero = null;

        String sql = "SELECT pun.categoria, pun.cedula, pun.celular, mun.descripcion, det.descripcion AS departamento, pun.email, pun.idpos, pun.nombre_cliente, pun.nombre_punto, pun.telefono, ter.descripcion, zon.descripcion, pun.latitud, pun.longitud, pun.detalle, pun.texto_direccion, " +
                "            ind.tipo_visita, pun.zona, pun.territorio, pun.depto AS iddepartamento, pun.ciudad AS idciudad, pun.barrio AS barrio, pun.estado_com, tipo_documento,pun.vende_recargas " +
                " FROM punto AS pun, departamento AS det, municipios AS mun, territorio AS ter, zona AS zon, indicadoresdas_detalle  AS ind \n" +
                " WHERE " +
                "  pun.depto = det.id AND " +
                "  pun.ciudad = mun.id_muni AND mun.departamento = det.id AND" +
                "  pun.territorio = ter.id AND " +
                "  pun.zona = zon.id AND " +
                "  ind.idpos = pun.idpos AND "+
                "  pun.idpos = ? ORDER BY ind.tipo_visita, ind.orden" ;

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(idpos)});

        if (cursor.moveToFirst()) {
            entRutero = new EntLisSincronizar();
            entRutero.setCategoria(cursor.getInt(0));
            entRutero.setCedula(cursor.getString(1));
            entRutero.setCelular(cursor.getString(2));
            entRutero.setNombreCiudad(cursor.getString(3));
            entRutero.setNombreDepartamento(cursor.getString(4));
            entRutero.setEmail(cursor.getString(5));
            entRutero.setIdpos(cursor.getInt(6));
            entRutero.setNombre_cliente(cursor.getString(7));
            entRutero.setNombre_punto(cursor.getString(8));
            entRutero.setTelefono(cursor.getString(9));
            entRutero.setNombreTerritorio(cursor.getString(10));
            entRutero.setNombreZona(cursor.getString(11));
            entRutero.setLatitud(cursor.getDouble(12));
            entRutero.setLongitud(cursor.getDouble(13));
            entRutero.setDetalle(cursor.getString(14));
            entRutero.setTexto_direccion(cursor.getString(15));
            entRutero.setEstado_visita(cursor.getInt(16));
            entRutero.setZona(cursor.getInt(17));
            entRutero.setTerritorio(cursor.getInt(18));
            entRutero.setIdDepartameto(cursor.getInt(19));
            entRutero.setIdCiudad(cursor.getInt(20));
            entRutero.setBarrio(cursor.getString(21));
            entRutero.setEstado_com(cursor.getInt(22));
            entRutero.setTipo_documento(cursor.getInt(23));
            entRutero.setVende_recargas(cursor.getInt(24));
        }
        cursor.close();
        return entRutero;
    }

    public int ultimoRegistro(String table){
        int _id = 0;
        String sql = "SELECT id FROM "+ table +" ORDER BY id DESC LIMIT 1";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                _id = Integer.parseInt(cursor.getString(0));
            } while(cursor.moveToNext());
        }
        return _id;
    }

    public boolean insertVentaOffLine(List<EntCarritoVenta> data, double latitude, double longitude, int idpos) {

        ContentValues values = new ContentValues();

            for (int i = 0; i < data.size(); i++) {

                values.put("id_referencia", data.get(i).getIdReferencia());
                values.put("tipo_product", data.get(i).getTipoProducto());
                values.put("valor_refe", data.get(i).getValorRefe());
                values.put("valor_directo", data.get(i).getValorDirecto());
                values.put("serie", data.get(i).getSerie());
                values.put("id_punto", data.get(i).getIdPunto());
                values.put("id_paquete", data.get(i).getIdPaquete());
                values.put("estado_venta", data.get(i).getEstadoVenta());
                values.put("tipo_venta", data.get(i).getTipoVenta());

                values.put("latitud", latitude);
                values.put("longitud", longitude);

                database.insert("detalle_pedido", null, values);
            }

            updateTipoVisita(idpos, 1);

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

    public List<EntCarritoVenta> sincronizarVentas() {

        List<EntCarritoVenta> sincronizarPedidosArrayList = new ArrayList<>();

        String sql = "SELECT '0' id_auto_carrito, id_referencia, tipo_product, valor_refe, valor_directo, serie, id_punto, id_paquete, estado_venta, tipo_venta, latitud, longitud FROM detalle_pedido";

        Cursor cursor = database.rawQuery(sql, null);
        EntCarritoVenta entCarritoVenta;

        if (cursor.moveToFirst()) {
            do {

                entCarritoVenta = new EntCarritoVenta();

                entCarritoVenta.setIdAutoCarrito(cursor.getInt(0));
                entCarritoVenta.setIdReferencia(cursor.getInt(1));
                entCarritoVenta.setTipoProducto(cursor.getInt(2));
                entCarritoVenta.setValorRefe(cursor.getInt(3));
                entCarritoVenta.setValorDirecto(cursor.getInt(4));
                entCarritoVenta.setSerie(cursor.getString(5));
                entCarritoVenta.setIdPunto(cursor.getInt(6));
                entCarritoVenta.setIdPaquete(cursor.getInt(7));
                entCarritoVenta.setEstadoVenta(cursor.getInt(8));
                entCarritoVenta.setTipoVenta(cursor.getInt(9));
                entCarritoVenta.setLatitud(cursor.getDouble(10));
                entCarritoVenta.setLongitud(cursor.getDouble(11));

                sincronizarPedidosArrayList.add(entCarritoVenta);

            } while (cursor.moveToNext());

        }
        cursor.close();
        return sincronizarPedidosArrayList;
    }

    public boolean deleteOneVentaOff(String idAuto) {
        int a = database.delete("detalle_pedido", "serie = ?", new String[]{idAuto});
        return a > 0;
    }

    // Inserta en la base de datos local los puntos recuperados en cada sincronización
    public boolean insertPuntoLocal(EntLisSincronizar data, int idusuario) {

        ContentValues values = new ContentValues();

        try {

            values.put("categoria", data.getCategoria());
            values.put("cedula", data.getCedula());
            values.put("celular", data.getCelular());
            values.put("ciudad", data.getCiudad());
            values.put("depto", data.getDepto());
            values.put("email", data.getEmail());
            values.put("estado_com", data.getEstado_com());

            values.put("nombre_cliente", data.getNombre_cliente());
            values.put("nombre_punto", data.getNombre_punto());
            values.put("telefono", data.getTelefono());
            values.put("territorio", data.getTerritorio());
            values.put("texto_direccion", data.getTexto_direccion());
            values.put("zona", data.getZona());
            values.put("latitud", data.getLatitud());
            values.put("longitud", data.getLongitud());
            values.put("tipo_documento", data.getTipo_documento());
            values.put("idusuario", idusuario);
            values.put("barrio", data.getBarrio());
            values.put("vende_recargas", data.getVende_recargas());

            database.insert("punto_local", null, values);

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        }
        return true;
    }

    public List<EntLisSincronizar> sincronizarPuntos(int idUsuario) {

        List<EntLisSincronizar> sincronizarPedidosArrayList = new ArrayList<>();

        String[] args = new String[] {String.valueOf(idUsuario)};
        String sql = "SELECT categoria, cedula, celular, ciudad, depto, nombre_punto, nombre_cliente, email, telefono, estado_com, zona, territorio, texto_direccion, tipo_documento, latitud, longitud, barrio, vende_recargas FROM punto_local WHERE idusuario = ?";

        Cursor cursor = database.rawQuery(sql, args);
        EntLisSincronizar entLisSincronizar;

        if (cursor.moveToFirst()) {
            do {

                entLisSincronizar = new EntLisSincronizar();

                entLisSincronizar.setCategoria(cursor.getInt(0));
                entLisSincronizar.setCedula(cursor.getString(1));
                entLisSincronizar.setCelular(cursor.getString(2));
                entLisSincronizar.setCiudad(cursor.getInt(3));
                entLisSincronizar.setDepto(cursor.getInt(4));


                entLisSincronizar.setNombre_punto(cursor.getString(5));
                entLisSincronizar.setNombre_cliente(cursor.getString(6));
                entLisSincronizar.setEmail(cursor.getString(7));
                entLisSincronizar.setTelefono(cursor.getString(8));
                entLisSincronizar.setEstado_com(cursor.getInt(9));
                entLisSincronizar.setZona(cursor.getInt(10));
                entLisSincronizar.setTerritorio(cursor.getInt(11));
                entLisSincronizar.setTexto_direccion(cursor.getString(12));
                entLisSincronizar.setTipo_documento(cursor.getInt(13));
                entLisSincronizar.setLatitud(cursor.getDouble(14));
                entLisSincronizar.setLongitud(cursor.getDouble(15));
                entLisSincronizar.setBarrio(cursor.getString(16));
                entLisSincronizar.setVende_recargas(cursor.getInt(17));

                sincronizarPedidosArrayList.add(entLisSincronizar);

            } while (cursor.moveToNext());

        }
        cursor.close();
        return sincronizarPedidosArrayList;
    }

    public boolean deletePuunto(int idUsuario) {
        int a = database.delete("punto_local", "idusuario = ?", new String[]{String.valueOf(idUsuario)});
        return a > 0;
    }


}
