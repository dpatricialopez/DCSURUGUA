package net.movilbox.dcsuruguay.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.movilbox.dcsuruguay.R;


/**
 * Created by germangarcia on 23/06/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DCSCOLOMBIA.db";
    private static final int DATABASE_VERSION = 14;
    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlLoginUsuario = context.getResources().getString(R.string.tabla_login);

        String sqlDepartamento = context.getResources().getString(R.string.tabla_departamento);

        String sqlmunicipios = context.getResources().getString(R.string.tabla_municipio);

        String sqlDireccion = context.getResources().getString(R.string.tabla_direccion);

        String sqlCategoria = context.getResources().getString(R.string.tabla_categoria);

        String sqlEstadoComercial = context.getResources().getString(R.string.tabla_estado_comercial);

        String sqlIndicadores = context.getResources().getString(R.string.tabla_territorio);

        String sqlZona = context.getResources().getString(R.string.tabla_zona);

        String sqlPunto = context.getResources().getString(R.string.tabla_punto);

        String sqlRefsims = context.getResources().getString(R.string.tabla_refes_sims);

        String sqlCatalogo = context.getResources().getString(R.string.tabla_catalogo);

        String sqlImgCatalog = context.getResources().getString(R.string.tabla_img_catalogo);

        String sqlDetalleCatalogo = context.getResources().getString(R.string.tabla_detalle_catalogo);

        String sqlLisPrecio = context.getResources().getString(R.string.tabla_lista_precio);

        String sqlInventario = context.getResources().getString(R.string.tabla_inventario);

        String sqlPuntoLocal = context.getResources().getString(R.string.tabla_punto_local);

        String sqlNoVisita = context.getResources().getString(R.string.tabla_no_venta);

        String sqlIndicadoresreporte = "CREATE TABLE indicadoresdas (id_auto integer primary key AUTOINCREMENT, cant_ventas_sim INT, cant_ventas_combo INT, cant_cumplimiento_sim INT, cant_cumplimiento_combo INT, cant_quiebre_sim_mes INT, id_vendedor INT, id_distri INT )";

        String sqlIndicadores_detalle = context.getResources().getString(R.string.tabla_indicadoros_detalle);

        String sqlCarritoDetalle = context.getResources().getString(R.string.tabla_carrito_detalle);

        String sqlTimeServices = "CREATE TABLE time_services (id integer primary key AUTOINCREMENT, idUser int, traking int, timeservice int, idDistri int, dataBase TEXT, fechainicial TEXT, fechafinal TEXT )";

        String sqlTraking = context.getResources().getString(R.string.tabla_trancing);

        String sqlDetallePedido = "CREATE TABLE detalle_pedido (id_auto_carrito integer primary key AUTOINCREMENT, id_referencia INT, tipo_product INT, valor_refe REAL, valor_directo REAL, serie TEXT, id_punto INT, id_paquete INT, estado_venta INT, tipo_venta INT, latitud REAL, longitud REAL)";

        db.execSQL(sqlLoginUsuario);
        db.execSQL(sqlDepartamento);
        db.execSQL(sqlmunicipios);
        db.execSQL(sqlDireccion);
        db.execSQL(sqlCategoria);
        db.execSQL(sqlEstadoComercial);
        db.execSQL(sqlIndicadores);

        db.execSQL(sqlZona);
        db.execSQL(sqlPunto);
        db.execSQL(sqlRefsims);
        db.execSQL(sqlCatalogo);
        db.execSQL(sqlImgCatalog);
        db.execSQL(sqlDetalleCatalogo);
        db.execSQL(sqlLisPrecio);
        db.execSQL(sqlInventario);

        db.execSQL(sqlIndicadoresreporte);
        db.execSQL(sqlIndicadores_detalle);
        db.execSQL(sqlCarritoDetalle);
        db.execSQL(sqlTimeServices);
        db.execSQL(sqlTraking);
        db.execSQL(sqlDetallePedido);
        db.execSQL(sqlPuntoLocal);
        db.execSQL(sqlNoVisita);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS login");
        db.execSQL("DROP TABLE IF EXISTS departamento");
        db.execSQL("DROP TABLE IF EXISTS municipios");
        db.execSQL("DROP TABLE IF EXISTS direccion");
        db.execSQL("DROP TABLE IF EXISTS categoria");
        db.execSQL("DROP TABLE IF EXISTS estado_comercial");

        db.execSQL("DROP TABLE IF EXISTS territorio");
        db.execSQL("DROP TABLE IF EXISTS zona");
        db.execSQL("DROP TABLE IF EXISTS punto");
        db.execSQL("DROP TABLE IF EXISTS refes_sims");
        db.execSQL("DROP TABLE IF EXISTS catalogo");
        db.execSQL("DROP TABLE IF EXISTS img_catalogo");
        db.execSQL("DROP TABLE IF EXISTS detalle_catalogo");
        db.execSQL("DROP TABLE IF EXISTS lista_precios");
        db.execSQL("DROP TABLE IF EXISTS inventario");

        db.execSQL("DROP TABLE IF EXISTS indicadoresdas");
        db.execSQL("DROP TABLE IF EXISTS indicadoresdas_detalle");
        db.execSQL("DROP TABLE IF EXISTS carrito_detalle");
        db.execSQL("DROP TABLE IF EXISTS time_services");
        db.execSQL("DROP TABLE IF EXISTS tracing");
        db.execSQL("DROP TABLE IF EXISTS nomenclaturas");
        db.execSQL("DROP TABLE IF EXISTS cabeza_pedido");
        db.execSQL("DROP TABLE IF EXISTS detalle_pedido");
        db.execSQL("DROP TABLE IF EXISTS punto_local");
        db.execSQL("DROP TABLE IF EXISTS no_venta");

        this.onCreate(db);

    }


}
