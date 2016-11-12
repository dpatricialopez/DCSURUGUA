package net.movilbox.dcsuruguay.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.movilbox.dcsuruguay.DataBase.DBHelper;
import net.movilbox.dcsuruguay.Model.EntCarritoVenta;
import net.movilbox.dcsuruguay.Model.Referencia;
import net.movilbox.dcsuruguay.Model.ReferenciasCombos;
import net.movilbox.dcsuruguay.Model.ReferenciasSims;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by germangarcia on 21/07/16.
 */
public class ControllerTomarPedido {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private FuncionesGenerales funciones;

    public ControllerTomarPedido(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    private SQLiteDatabase database;

    public List<ReferenciasSims> getSimcardLocal(String indicardor) {

        List<ReferenciasSims> referenciasSimses = new ArrayList<>();

        String sql = "SELECT refe.id, refe.pn, 0 stock, refe.producto, 0 dias_inve, 0 ped_sugerido, lprecio.valor_referencia, lprecio.valor_directo, 0 quiebre " +
                " FROM " +
                "  referencia_simcard refe INNER JOIN lista_precios lprecio ON lprecio.id_referencia = refe.id AND lprecio.idpos = ? GROUP BY refe.id";

        Cursor cursor = db.rawQuery(sql, new String[] {indicardor});
        ReferenciasSims referenciasSims;

        if (cursor.moveToFirst()) {
            do {

                referenciasSims = new ReferenciasSims();

                referenciasSims.setId(cursor.getInt(0));
                referenciasSims.setPn(cursor.getString(1));
                referenciasSims.setStock(cursor.getInt(2));
                referenciasSims.setProducto(cursor.getString(3));
                referenciasSims.setDias_inve(cursor.getInt(4));
                referenciasSims.setPed_sugerido(cursor.getString(5));
                referenciasSims.setPrecio_referencia(cursor.getDouble(6));
                referenciasSims.setPrecio_publico(cursor.getDouble(7));
                referenciasSims.setQuiebre(cursor.getInt(8));

                referenciasSimses.add(referenciasSims);

            } while (cursor.moveToNext());

        }
        cursor.close();
        return referenciasSimses;
    }

    public int countReferenceProduct(int idUsuario, int idpos, int idreferen) {

        Cursor cursor;
        int indicador = 0;

        String sql = "SELECT SUM(cantidad_pedida) FROM carrito_pedido WHERE referencia = "+idreferen+" AND  id_usuario = "+idUsuario+ " AND id_punto = "+idpos+" ";
        cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            indicador = cursor.getInt(0);
        }
        cursor.close();
        return indicador;

    }
    public int countReferenciaProducto(int id, int id_punto, int id_usuario) {
        Cursor cursor;
        int indicador = 0;
        String sql = "SELECT SUM(cantidad_pedida) FROM carrito_pedido WHERE id_producto = "+id+" AND  id_usuario = "+id_usuario+ " AND id_punto = "+id_punto+" ";

        cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            indicador = cursor.getInt(0);
        }
        cursor.close();
        return indicador;
    }


    private boolean validarProducto(int id_producto, int id_pos) {

        Cursor cursor;
        boolean indicador = false;

        String[] args = new String[] {String.valueOf(id_producto), String.valueOf(id_pos)};

        String sql = "SELECT id_producto FROM carrito_pedido WHERE id_producto = ? AND id_punto = ? LIMIT 1 ";

        cursor = db.rawQuery(sql, args);
        if (cursor.moveToFirst()) {
            indicador = true;
        }
        cursor.close();
        return indicador;

    }

    public List<ReferenciasCombos> getProductosCombos(String indicardor) {

        List<ReferenciasCombos> referenciasComboses = new ArrayList<>();

        String sql = "SELECT refe.id, refe.descripcion, refe.precioventa, refe.speech, refe.pantalla, refe.cam_frontal, refe.cam_tras, refe.flash, refe.banda, refe.memoria, " +
                " refe.expandible, refe.bateria, refe.bluetooth, refe.tactil, refe.tec_fisico, refe.carrito_compras, refe.correo, refe.enrutador, refe.radio, refe.wifi, refe.gps, " +
                " refe.so, refe.web, 0 quiebre, lprecio.valor_referencia, lprecio.valor_directo, refe.img " +
                " FROM " +
                "   referencia_combo refe INNER JOIN lista_precios lprecio ON lprecio.id_referencia = refe.id AND lprecio.idpos = ? AND " +
                " lprecio.tipo_pro = 2 GROUP BY refe.id";

        Cursor cursor = db.rawQuery(sql, new String[] {indicardor});
        ReferenciasCombos referenciasCombos;

        if (cursor.moveToFirst()) {
            do {

                referenciasCombos = new ReferenciasCombos();

                referenciasCombos.setId(cursor.getInt(0));
                referenciasCombos.setDescripcion(cursor.getString(1));
                referenciasCombos.setPrecio_referencia(cursor.getDouble(24));
                referenciasCombos.setPrecio_publico(cursor.getDouble(25));
                referenciasCombos.setImg(cursor.getString(26));

                String sqlDestall = "SELECT deta.id, deta.pn, 0 stock, deta.producto, 0 dias_inve, 0 ped_sugerido, deta.descripcion, 0 stock_seguridad, lprecio.valor_referencia, " +
                        " lprecio.valor_directo, deta.img, 0 quiebre " +
                        "   FROM " +
                        "     detalle_combo deta INNER JOIN lista_precios lprecio ON lprecio.id_referencia = deta.id " +
                        "   WHERE " +
                        "     deta.id_padre = ? AND " +
                        "     lprecio.tipo_pro = 2 GROUP BY deta.id";

                Cursor cursor_detalle = db.rawQuery(sqlDestall, new String[] {String.valueOf(cursor.getInt(0))});
                List<Referencia> referenciaList = new ArrayList<>();
                Referencia referencia;

                if (cursor_detalle.moveToFirst()) {

                    do {
                        referencia = new Referencia();
                        referencia.setId(cursor_detalle.getInt(0));
                        referencia.setPn(cursor_detalle.getString(1));
                        referencia.setStock(cursor_detalle.getInt(2));
                        referencia.setProducto(cursor_detalle.getString(3));
                        referencia.setDias_inve(cursor_detalle.getDouble(4));
                        referencia.setPed_sugerido(cursor_detalle.getString(5));

                        referencia.setPrecio_referencia(cursor_detalle.getDouble(8));
                        referencia.setPrecio_publico(cursor_detalle.getDouble(9));

                        referenciaList.add(referencia);

                    } while (cursor_detalle.moveToNext());
                }
                cursor_detalle.close();
                referenciasCombos.setReferenciaLis(referenciaList);

                referenciasComboses.add(referenciasCombos);

            } while (cursor.moveToNext());

        }
        cursor.close();
        return referenciasComboses;

    }


    private int ultimoRegistro(String table){
        int _id = 0;
        String sql = "SELECT id FROM "+ table +" ORDER BY id DESC LIMIT 1";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                _id = Integer.parseInt(cursor.getString(0));
            } while(cursor.moveToNext());
        }
        cursor.close();
        return _id;
    }

    public int countSimcardProduct(int idUsuario, int idpos, int idProduct) {

        Cursor cursor;
        int indicador = 0;

        String sql = "SELECT SUM(cantidad_pedida) FROM carrito_pedido WHERE id_producto = "+idProduct+" AND  id_usuario = "+idUsuario+ " AND id_punto = "+idpos+" ";
        cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            indicador = cursor.getInt(0);
        }
        cursor.close();
        return indicador;

    }

    public boolean insertPedidoOffLine(List<ReferenciasSims> data, int iduser, String iddistri, String bd, int idpos, double latitud, double longitud, int comprobante) {

        ContentValues values = new ContentValues();
        ContentValues values2 = new ContentValues();

        try {

            values.put("iduser", iduser);
            values.put("iddistri", iddistri);
            values.put("db", bd);
            values.put("idpos", idpos);
            values.put("latitud", latitud);
            values.put("longitud", longitud);
            values.put("fecha", funciones.getDatePhoneFecha());
            values.put("hora", funciones.getDatePhoneHora());
            values.put("comprobante", comprobante);

            db.insert("cabeza_pedido", null, values);

            int id_auto = ultimoRegistro("cabeza_pedido");

            for (int i = 0; i < data.size(); i++) {

                values2.put("idCabeza", id_auto);
                values2.put("id_producto", data.get(i).getId());
                values2.put("cantidad_pedida", data.get(i).getCantidadPedida());
                values2.put("tipo_producto", data.get(i).getTipo_producto());
                values2.put("referencia", data.get(i).getId());

                db.insert("detalle_pedido", null, values2);
            }

            updateTipoVisita(idpos, 1);

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        }

        return true;
    }

    public boolean deleteAll(int id_pos, int id_usuario) {
        int a = db.delete("carrito_pedido", "id_punto = ? AND id_usuario = ?", new String[]{String.valueOf(id_pos), String.valueOf(id_usuario)});

        db.close();
        return a > 0;
    }
    public List<ReferenciasSims> getCarrito(int id_pos, int id_usuario) {

        List<ReferenciasSims> referenciasSimsList = new ArrayList<>();

        String sql = "SELECT id_carrito, id_producto, pn_pro, stock, producto, dias_inve, ped_sugerido, cantidad_pedida, id_usuario, id_punto, latitud, longitud, tipo_producto, producto_img, precio_referencia, precio_publico FROM carrito_pedido WHERE id_punto = " + id_pos + " AND id_usuario = " + id_usuario + " ";

        Cursor cursor = db.rawQuery(sql, null);

        ReferenciasSims referenciasSims;

        if (cursor.moveToFirst()) {
            do {
                referenciasSims = new ReferenciasSims();
                referenciasSims.setId_auto_carrito(cursor.getInt(0));
                referenciasSims.setId(cursor.getInt(1));
                referenciasSims.setPn(cursor.getString(2));
                referenciasSims.setStock(cursor.getInt(3));
                referenciasSims.setProducto(cursor.getString(4));
                referenciasSims.setDias_inve(cursor.getDouble(5));
                referenciasSims.setPed_sugerido(cursor.getString(6));
                referenciasSims.setCantidadPedida(cursor.getInt(7));
                referenciasSims.setId_usuario(cursor.getInt(8));
                referenciasSims.setId_punto(cursor.getInt(9));
                referenciasSims.setTipo_producto(cursor.getInt(12));
                referenciasSims.setUrl_imagen(cursor.getString(13));
                referenciasSims.setPrecio_referencia(cursor.getDouble(14));
                referenciasSims.setPrecio_publico(cursor.getDouble(15));

                referenciasSimsList.add(referenciasSims);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return referenciasSimsList;

    }

    public boolean deleteCarritoProducto(int id, int id_pos, int id_usuario) {
        int a = db.delete("carrito_pedido", "id_carrito = ? AND id_punto = ? AND id_usuario = ?", new String[]{String.valueOf(id), String.valueOf(id_pos), String.valueOf(id_usuario)});

        db.close();
        return a > 0;

    }

    private boolean updateTipoVisita(int idpos, int valor){

        ContentValues valores = new ContentValues();

        valores.put("tipo_visita", valor);

        String[] args = new String[]{String.valueOf(idpos)};
        int p = db.update("indicadoresdas_detalle", valores, "idpos = ?", args);
        //db.close();
        return p > 0;

    }

    public String insertCarritoPedido(ReferenciasSims data) {

        String resultado;
        ContentValues values = new ContentValues();

        if (validarProducto(data.getId(), data.getId_punto())) {

            values.put("cantidad_pedida", data.getCantidadPedida());

            int p = db.update("carrito_pedido", values, String.format("id_producto = %1$s", data.getId()), null);
            db.close();

            if (p > 0)
                resultado = "update";
            else
                resultado = "no update";

            return resultado;

        } else {

            try {
                values.put("id_producto", data.getId());
                values.put("pn_pro", data.getPn());
                values.put("stock", data.getStock());
                values.put("producto", data.getProducto());
                values.put("dias_inve", data.getDias_inve());
                values.put("ped_sugerido", data.getPed_sugerido());
                values.put("cantidad_pedida", data.getCantidadPedida());
                values.put("id_usuario", data.getId_usuario());
                values.put("id_punto", data.getId_punto());
                values.put("tipo_producto", data.getTipo_producto());
                values.put("precio_referencia", data.getPrecio_referencia());
                values.put("precio_publico", data.getPrecio_publico());

                db.insert("carrito_pedido", null, values);

            } catch (SQLiteConstraintException e) {
                Log.d("data", "failure to insert word,", e);
                return resultado = "no inserto";
            } finally {
                db.close();
            }
        }

        return resultado = "inserto";
    }

    public String insertCarritoPedidoCombos(Referencia data) {

        String resultado;
        ContentValues values = new ContentValues();

        if (validarProducto(data.getId(), data.getId_punto())) {

            values.put("cantidad_pedida", data.getCantidadPedida());

            int p = db.update("carrito_pedido", values, String.format("id_producto = %1$s", data.getId()), null);
            db.close();

            if (p > 0)
                resultado = "update";
            else
                resultado = "no update";

            return resultado;

        } else {

            try {
                values.put("id_producto", data.getId());
                values.put("pn_pro", data.getPn());
                values.put("stock", data.getStock());
                values.put("producto", data.getProducto());
                values.put("dias_inve", data.getDias_inve());
                values.put("ped_sugerido", data.getPed_sugerido());
                values.put("cantidad_pedida", data.getCantidadPedida());
                values.put("id_usuario", data.getId_usuario());
                values.put("id_punto", data.getId_punto());
                values.put("tipo_producto", data.getTipo_producto());
                values.put("producto_img", data.getUrl_imagen());
                values.put("precio_referencia", data.getPrecio_referencia());
                values.put("precio_publico", data.getPrecio_publico());
                values.put("referencia", data.getReferencia());
                values.put("latitud", data.getLatitud());
                values.put("longitud", data.getLongitud());

                db.insert("carrito_pedido", null, values);

            } catch (SQLiteConstraintException e) {
                Log.d("data", "failure to insert word,", e);
                return resultado = "no inserto";
            } finally {
                db.close();
            }
        }

        return resultado = "inserto";
    }

    public boolean getFuncionesHabilitadasByTag(String tag)  {
        String sql = "SELECT status FROM FuncionesHabilitadas  WHERE function=? ";
        String[] args = new String[] {tag};
        Cursor cursor = db.rawQuery(sql, args);
        int status=0;
        if (!cursor.moveToFirst()) {
            status=cursor.getInt(0);
        }
        cursor.close();

        return status>0;
    }

}
