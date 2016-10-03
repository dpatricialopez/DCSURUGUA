package net.movilbox.dcsuruguay.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Adapter.AdapterCarrito;
import net.movilbox.dcsuruguay.Controller.ControllerCarrito;
import net.movilbox.dcsuruguay.Controller.ControllerInventario;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerPunto;
import net.movilbox.dcsuruguay.Model.EntCarritoVenta;
import net.movilbox.dcsuruguay.Model.ListCarritoCompra;
import net.movilbox.dcsuruguay.Model.ListResponseCompra;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;
import net.movilbox.dcsuruguay.Services.GpsServices;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActCarrito extends BaseActivity implements View.OnClickListener {

    private SwipeMenuListView swipeMenuListView;
    private List<EntCarritoVenta> entCarritoVentaList;
    private TextView txtValorPagar;
    private DecimalFormat format;
    private AdapterCarrito adapterCarrito;
    private int idPos;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        connectionDetector = new ConnectionDetector(this);

        controllerCarrito = new ControllerCarrito(this);
        controllerInventario = new ControllerInventario(this);

        controllerPunto = new ControllerPunto(this);

        controllerLogin = new ControllerLogin(this);

        format = new DecimalFormat("#,###.##");

        swipeMenuListView = (SwipeMenuListView) findViewById(R.id.listView);

        txtValorPagar = (TextView) findViewById(R.id.txtValorPagar);

        Button btnFinalizarVenta = (Button) findViewById(R.id.btnFinalizarVenta);
        btnFinalizarVenta.setOnClickListener(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            idPos = bundle.getInt("id_pos");
            entCarritoVentaList = controllerCarrito.listCarritoAgrupado(idPos);
        }

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(255, 61, 0)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Eliminar");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

            }
        };

        swipeMenuListView.setMenuCreator(creator);

        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        deletePrduct(position);
                        break;
                }

                return false;

            }
        });

        swipeMenuListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        swipeMenuListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //deletePrduct(position);
                return false;
            }
        });

        llenarDataCarrito(entCarritoVentaList);

    }

    private void deletePrduct(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(entCarritoVentaList.get(position).getSerie());
        builder.setMessage("¿ Estas seguro de eliminar el producto ?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (controllerCarrito.deleteOne(entCarritoVentaList.get(position).getIdAutoCarrito())) {
                    TastyToast.makeText(ActCarrito.this, "Producto: "+ entCarritoVentaList.get(position).getSerie()+" fue eliminado", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    adapterCarrito.setData(controllerCarrito.listCarrito(idPos));
                } else {
                    TastyToast.makeText(ActCarrito.this, "Problemas al eliminar el producto", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }

            }

        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

    private void llenarDataCarrito(List<EntCarritoVenta> entCarritoVentaList) {

        adapterCarrito = new AdapterCarrito(this, entCarritoVentaList);
        swipeMenuListView.setAdapter(adapterCarrito);

        sumarValor(entCarritoVentaList);

    }

    private void sumarValor(List<EntCarritoVenta> entCarritoVentaList) {

        double suma = 0.0;

        for (int i = 0; i < entCarritoVentaList.size(); i++) {

            if (entCarritoVentaList.get(i).getTipoVenta() == 1) {
                suma = (suma + entCarritoVentaList.get(i).getValorDirecto());
            } else {
                suma = (suma + entCarritoVentaList.get(i).getValorRefe());
            }
        }

        txtValorPagar.setText(String.format("Total a pagar: $ %1$s", format.format(suma)));

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carrito, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            finish();
            return true;
        } else if(id == R.id.action_borrar){
            borrarCarrito();
        }

        return super.onOptionsItemSelected(item);
    }

    private void borrarCarrito() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Borrar Carrito");
        builder.setMessage("¿ Estas seguro de eliminar los datos del pedido ?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (controllerCarrito.deleteAll()) {
                    entCarritoVentaList.clear();
                    adapterCarrito.notifyDataSetChanged();
                    sumarValor(entCarritoVentaList);
                    TastyToast.makeText(ActCarrito.this, "Se eliminaron todos los productos", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                } else {
                    TastyToast.makeText(ActCarrito.this, "Problemas al eliminar los productos", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }
            }

        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFinalizarVenta:

                 if (controllerCarrito.listCarrito(idPos).size() > 0) {

                     if (connectionDetector.isConnected()) {
                         enviarPedidoCompra();
                     } else {
                         enviarPedidoLocal();
                     }

                 } else {
                     TastyToast.makeText(ActCarrito.this, "No tiene productos en el carrito", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                 }

                break;
        }
    }

    private void enviarPedidoLocal() {

        entCarritoVentaList = controllerCarrito.listCarrito(idPos);
        GpsServices gpsServices = new GpsServices(ActCarrito.this);

        if (controllerPunto.insertVentaOffLine(entCarritoVentaList, gpsServices.getLatitude(), gpsServices.getLongitude(), idPos)) {

            for (int i = 0; i < entCarritoVentaList.size(); i++) {

                controllerCarrito.deleteOne(entCarritoVentaList.get(i).getIdAutoCarrito());
                controllerInventario.deleteOneInventsrio(entCarritoVentaList.get(i).getSerie(), entCarritoVentaList.get(i).getIdReferencia(), entCarritoVentaList.get(i).getTipoProducto());
            }

            TastyToast.makeText(this, "Los venta se guardaron localmente recuerde sincronizar", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            Intent intent = new Intent(this, ActMenu.class);
            startActivity(intent);
            finish();

        } else {
            TastyToast.makeText(this, "Problemas al guardar el pedido local", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }
    }

    private void enviarPedidoCompra() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Por favor espere");
        progressDialog.setMessage("Enviando información.");
        progressDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "save_pedido_cli_final");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJSONCompra(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        progressDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                GpsServices gpsServices = new GpsServices(ActCarrito.this);

                entCarritoVentaList = controllerCarrito.listCarrito(idPos);

                String parJSON = new Gson().toJson(entCarritoVentaList, ListCarritoCompra.class);

                params.put("datos", parJSON);

                params.put("latitud", String.valueOf(gpsServices.getLatitude()));
                params.put("longitud", String.valueOf(gpsServices.getLongitude()));

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());
                params.put("indicador", "online");

                return params;

            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);

    }

    private void parseJSONCompra(String response) {

        progressDialog.dismiss();

        Gson gson = new Gson();

        ListResponseCompra listResposeCompras = gson.fromJson(response, ListResponseCompra.class);

        boolean banderaError = false;

        for (int i = 0; i < listResposeCompras.size(); i++) {

            if (listResposeCompras.get(i).getAccion() == -1) {
                // Borrar de la base de datos local
                controllerCarrito.deleteOne(listResposeCompras.get(i).getIdAutoCarrito());
                TastyToast.makeText(ActCarrito.this, listResposeCompras.get(i).getMsg(), TastyToast.LENGTH_LONG, TastyToast.ERROR);

            } else if (listResposeCompras.get(i).getAccion() == -2) {
                TastyToast.makeText(ActCarrito.this, listResposeCompras.get(i).getMsg(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
            } else if (listResposeCompras.get(i).getAccion() == 0) {
                // Borrar de la base de datos local
                controllerCarrito.deleteOne(listResposeCompras.get(i).getIdAutoCarrito());
                controllerInventario.deleteOneInventsrio(listResposeCompras.get(i).getSerie(), listResposeCompras.get(i).getIdReferencia(), listResposeCompras.get(i).getTipoProducto());
                //TastyToast.makeText(ActCarrito.this, listResposeCompras.get(i).getMsg(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                banderaError = true;
            }

        }

        if (banderaError)
            TastyToast.makeText(ActCarrito.this, "La venta se realizo exitosamente", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);



        Bundle bundle = new Bundle();
        Intent intent = new Intent(ActCarrito.this, ActMenu.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
        finish();
    }

}
