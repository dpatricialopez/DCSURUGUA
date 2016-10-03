package net.movilbox.dcsuruguay.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import net.movilbox.dcsuruguay.DataBase.DBHelper;
import net.movilbox.dcsuruguay.Model.EntCarritoVenta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by germangarcia on 21/07/16.
 */
public class ControllerCarrito {

    private SQLiteDatabase database;

    public ControllerCarrito(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean guardarVentaCarrito(EntCarritoVenta entCarritoVenta) {

        ContentValues values = new ContentValues();

        try {
            values.put("id_referencia", entCarritoVenta.getIdReferencia());
            values.put("tipo_product", entCarritoVenta.getTipoProducto());
            values.put("valor_refe", entCarritoVenta.getValorRefe());
            values.put("valor_directo", entCarritoVenta.getValorDirecto());
            values.put("serie", entCarritoVenta.getSerie());
            values.put("id_punto", entCarritoVenta.getIdPunto());
            values.put("id_paquete", entCarritoVenta.getIdPaquete());
            values.put("estado_venta", entCarritoVenta.getEstadoVenta());
            values.put("tipo_venta", entCarritoVenta.getTipoVenta());

            database.insert("carrito_detalle", null, values);

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        }

        return true;

    }

    public List<EntCarritoVenta> listCarritoAgrupado(int idPunto) {

        List<EntCarritoVenta> entCarritoVentas = new ArrayList<>();

        String sql = "SELECT id_auto_carrito, id_referencia, tipo_product, SUM(valor_refe), SUM(valor_directo), serie, id_punto, estado_venta, tipo_venta, id_paquete, COUNT(*) AS cantidad FROM carrito_detalle WHERE id_punto = ? GROUP BY id_paquete ";

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(idPunto)});

        EntCarritoVenta carritoVenta;

        if (cursor.moveToFirst()) {

            do {
                carritoVenta = new EntCarritoVenta();
                carritoVenta.setIdAutoCarrito(cursor.getInt(0));
                carritoVenta.setIdReferencia(cursor.getInt(1));
                carritoVenta.setTipoProducto(cursor.getInt(2));
                carritoVenta.setValorRefe(cursor.getDouble(3));
                carritoVenta.setValorDirecto(cursor.getDouble(4));
                carritoVenta.setSerie(cursor.getString(5));
                carritoVenta.setIdPunto(cursor.getInt(6));

                carritoVenta.setEstadoVenta(cursor.getInt(7));
                carritoVenta.setTipoVenta(cursor.getInt(8));
                carritoVenta.setIdPaquete(cursor.getInt(9));
                carritoVenta.setCantidad(cursor.getInt(10));

                entCarritoVentas.add(carritoVenta);

            } while (cursor.moveToNext());

        }

        return entCarritoVentas;
    }

    public List<EntCarritoVenta> listCarrito(int idPunto) {

        List<EntCarritoVenta> entCarritoVentas = new ArrayList<>();

        String sql = "SELECT id_auto_carrito, id_referencia, tipo_product, valor_refe, valor_directo, serie, id_punto, estado_venta, tipo_venta, id_paquete FROM carrito_detalle WHERE id_punto = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(idPunto)});

        EntCarritoVenta carritoVenta;

        if (cursor.moveToFirst()) {

            do {
                carritoVenta = new EntCarritoVenta();
                carritoVenta.setIdAutoCarrito(cursor.getInt(0));
                carritoVenta.setIdReferencia(cursor.getInt(1));
                carritoVenta.setTipoProducto(cursor.getInt(2));
                carritoVenta.setValorRefe(cursor.getDouble(3));
                carritoVenta.setValorDirecto(cursor.getDouble(4));
                carritoVenta.setSerie(cursor.getString(5));
                carritoVenta.setIdPunto(cursor.getInt(6));

                carritoVenta.setEstadoVenta(cursor.getInt(7));
                carritoVenta.setTipoVenta(cursor.getInt(8));
                carritoVenta.setIdPaquete(cursor.getInt(9));

                entCarritoVentas.add(carritoVenta);

            } while (cursor.moveToNext());

        }

        return entCarritoVentas;
    }

    public boolean validarCarrito(int idReferencia, String idSerie, int i) {

        boolean validador = true;

        String sql = "SELECT * FROM carrito_detalle WHERE id_referencia = ? AND serie = ? AND tipo_venta = ? ";

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(idReferencia), idSerie, String.valueOf(i)});

        if (cursor.moveToFirst()) {
            validador = false;
        }

        return validador;
    }

    public boolean deleteAll() {
        int a = database.delete("carrito_detalle", null, null);
        return a > 0;
    }

    public boolean deleteOne(int idAuto) {
        int a = database.delete("carrito_detalle", "id_auto_carrito = ?", new String[]{String.valueOf(idAuto)});
        return a > 0;
    }


}
