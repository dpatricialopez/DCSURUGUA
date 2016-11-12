package net.movilbox.dcsuruguay.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.movilbox.dcsuruguay.Adapter.AdapterCarrito;
import net.movilbox.dcsuruguay.Adapter.AdapterCarritopedido;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerTomarPedido;
import net.movilbox.dcsuruguay.DataBase.DBHelper;
import net.movilbox.dcsuruguay.Model.Motivos;
import net.movilbox.dcsuruguay.Model.ReferenciasSims;
import net.movilbox.dcsuruguay.Model.ResponseMarcarPedido;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;
import net.movilbox.dcsuruguay.Services.GpsServices;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static net.movilbox.dcsuruguay.Model.EntLoginR.setIndicador_refres;

public class ActCarritoPedido extends AppCompatActivity {

    private int id_punto;
    private int id_usuario;
    private ControllerTomarPedido mydb;
    private AdapterCarritopedido adapterCarrito;
    private SwipeMenuListView mListView;
    private List<ReferenciasSims> simsList;
    private SpotsDialog alertDialog;
    private RequestQueue rq;
    public static final String TAG = "MyTag";
    private GpsServices gpsServices;
    private ControllerLogin controllerLogin;
    private DecimalFormat format;
    private TextView sub_total;
    private TextView igv;
    private TextView total_ped;

    private ConnectionDetector connectionDetector;
    private int id_comprobante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        format = new DecimalFormat("#.00");
        mydb = new ControllerTomarPedido(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gpsServices = new GpsServices(this);

        alertDialog = new SpotsDialog(this, R.style.Custom);

        connectionDetector = new ConnectionDetector(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            id_punto = bundle.getInt("id_punto");
            id_usuario = bundle.getInt("id_usuario");
        }
        sub_total = (TextView) findViewById(R.id.sub_total);
        igv = (TextView) findViewById(R.id.igv);
        total_ped = (TextView) findViewById(R.id.total_ped);

        mListView = (SwipeMenuListView) findViewById(R.id.listView);

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

        mListView.setMenuCreator(creator);

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
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

        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deletePrduct(position);
                return false;
            }
        });

        llenarDataCarrito(id_punto, id_usuario);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.btn_guardar_pedido);
        assert myFab != null;
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                savePedido();
            }
        });

        //Calcular Datos Total, SubTotal

    }

    private void calculaTotal() {
        double subtotal;
        double total = 0;
        double val_igv;

        for (int i = 0; i < simsList.size(); i++) {
            total = total + (simsList.get(i).getPrecio_referencia() * simsList.get(i).getCantidadPedida());
        }
        subtotal = (total / ((controllerLogin.getUserLogin().getIgv()/100)+1));
        val_igv = ((controllerLogin.getUserLogin().getIgv()/100) * subtotal);
        sub_total.setText(String.format("S/. %s", format.format(subtotal)));
        igv.setText(String.format("S/. %s", format.format(val_igv)));
        total_ped.setText(String.format("S/. %s", format.format(total)));

    }

    private void savePedido() {

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_tipo_comprobante, null);

        Spinner spinner_tipo_venta = (Spinner) dialoglayout.findViewById(R.id.spinner_tipo_venta);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿ Que tipo de comprobante desea utilizar ?");
        builder.setCancelable(false);
        llenarData(spinner_tipo_venta);
        builder.setView(dialoglayout).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (connectionDetector.isConnected()) {
                    loginServices();
                } else {
                    saveLocalDada();
                }
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

    private void llenarData(Spinner spinner_tipo_venta) {

        List<Motivos> motivosList = new ArrayList<>();

        motivosList.add(new Motivos(1, "Boleta"));
        motivosList.add(new Motivos(2, "Factura"));

        loadCausa(motivosList, spinner_tipo_venta);

    }

    private void loadCausa(final List<Motivos> thumbs, Spinner spinner_tipo_venta) {

        ArrayAdapter<Motivos> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, thumbs);
        spinner_tipo_venta.setAdapter(prec3);
        spinner_tipo_venta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_comprobante = thumbs.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void saveLocalDada() {

        simsList = mydb.getCarrito(id_punto, id_usuario);

        if (mydb.insertPedidoOffLine(simsList, controllerLogin.getUserLogin().getId(), String.valueOf(controllerLogin.getUserLogin().getId_distri()), controllerLogin.getUserLogin().getBd(), id_punto, gpsServices.getLatitude(), gpsServices.getLongitude(), id_comprobante)) {
            if (mydb.deleteAll(id_punto, id_usuario)) {
                Toast.makeText(this, "Los pedidos se guardaron localmente recuerde sincronizar", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, ActMenu.class);
                startActivity(intent);
            }

        } else {
            Toast.makeText(this, "Problemas al guardar el pedido local", Toast.LENGTH_LONG).show();
        }

    }

    private void deletePrduct(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(simsList.get(position).getProducto());
        builder.setMessage("¿ Estas seguro de eliminar el producto ?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (!mydb.deleteCarritoProducto(simsList.get(position).getId_auto_carrito(), simsList.get(position).getId_punto(), simsList.get(position).getId_usuario())) {
                    Toast.makeText(getApplicationContext(), "Problemas al eliminar el producto", Toast.LENGTH_SHORT).show();
                } else {
                    simsList.clear();
                    llenarDataCarrito(id_punto, id_usuario);
                    adapterCarrito = new AdapterCarritopedido(ActCarritoPedido.this, simsList);
                    mListView.setAdapter(adapterCarrito);
                    Toast.makeText(getApplicationContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
                    calculaTotal();
                }
            }

        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

    private void llenarDataCarrito(int id_punto, int id_usuario) {

        simsList = mydb.getCarrito(id_punto, id_usuario);
        calculaTotal();
        adapterCarrito = new AdapterCarritopedido(this, simsList);
        mListView.setAdapter(adapterCarrito);

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
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
                mydb.deleteAll(id_punto, id_usuario);
                simsList.clear();
                adapterCarrito.notifyDataSetChanged();
                calculaTotal();

            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

    private void loginServices() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "guardar_pedido");
        rq = Volley.newRequestQueue(this);

        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJSON(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(ActCarritoPedido.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActCarritoPedido.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActCarritoPedido.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActCarritoPedido.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActCarritoPedido.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }

                        alertDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                simsList = mydb.getCarrito(id_punto, id_usuario);
                Type listType = new TypeToken<ArrayList<ReferenciasSims>>(){}.getType();
                String parJSON = new Gson().toJson(simsList, listType);

                params.put("datos", parJSON);

                params.put("latitud", String.valueOf(gpsServices.getLatitude()));
                params.put("longitud", String.valueOf(gpsServices.getLongitude()));

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());
                params.put("idpos", String.valueOf(id_punto));
                params.put("comprobante", String.valueOf(id_comprobante));

                return params;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);
    }

    private void parseJSON(String response) {

        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                Charset.forName("UTF-8").encode(response);

                byte ptext[] = response.getBytes(Charset.forName("ISO-8859-1"));

                String value = new String(ptext, Charset.forName("UTF-8"));

                ResponseMarcarPedido responseMarcarPedido = gson.fromJson(value, ResponseMarcarPedido.class);

                if (responseMarcarPedido.getEstado() == -1) {
                    //El punto no tiene regional
                    Toast.makeText(this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else if (responseMarcarPedido.getEstado() == -2) {
                    //Error al intentar el pedido no tiene zona o territorio
                    Toast.makeText(this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else if (responseMarcarPedido.getEstado() == -3) {
                    //Una de las referencia no cuenta con precio
                    Toast.makeText(this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else if (responseMarcarPedido.getEstado() == -4) {
                    //Error al intentar guardar el detalle del pedido
                    Toast.makeText(this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else if (responseMarcarPedido.getEstado() == -5) {
                    //Error al intentar guardar el encabezado
                    Toast.makeText(this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else if (responseMarcarPedido.getEstado() == -6) {
                    // no se encontro datos para guardar
                    Toast.makeText(this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else if (responseMarcarPedido.getEstado() == -7) {
                    // Error al intentar actualizar el estado del punto.
                    Toast.makeText(this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else if (responseMarcarPedido.getEstado() == 1) {

                    if (mydb.deleteAll(id_punto, id_usuario)) {
                        Toast.makeText(this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                        //Enviar pantalla visitar punto
                        setIndicador_refres(1);
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent(this, ActMenu.class);
                        startActivity(intent);

                    } else {
                        // Error bd sqlite
                        Toast.makeText(this, "Error al eliminar los productos", Toast.LENGTH_LONG).show();
                    }
                }

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        } else {
            alertDialog.dismiss();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (rq != null) {
            rq.cancelAll(TAG);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rq != null) {
            rq.cancelAll(TAG);
        }
    }
}
