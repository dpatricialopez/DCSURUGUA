package net.movilbox.dcsuruguay.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Adapter.AppAdapterInventariar;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Model.CategoriasEstandar;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.Model.InventariarProducto;
import net.movilbox.dcsuruguay.Model.ListInventariarProducto;
import net.movilbox.dcsuruguay.Model.ResponseMarcarPedido;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.GpsServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActInventariar extends BaseActivity {

    private EntLisSincronizar thumbs = new EntLisSincronizar();
    private int tipo_busqueda;
    private TextView text_idpos;
    private TextView text_razon;
    private Spinner spinner_tipo;
    private GpsServices gpsServices;
    private List<InventariarProducto> inventariarProductos;
    private AppAdapterInventariar actDetalleProductos;
    private ListInventariarProducto datosN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventariar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controllerLogin = new ControllerLogin(this);
        gpsServices = new GpsServices(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            thumbs = (EntLisSincronizar) bundle.getSerializable("value");
        }

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_buscar_inventariar, null);

        text_idpos = (TextView) dialoglayout.findViewById(R.id.text_idpos);
        text_razon = (TextView) dialoglayout.findViewById(R.id.text_razon);
        spinner_tipo = (Spinner) dialoglayout.findViewById(R.id.spinner_tipo);

        text_idpos.setText(String.valueOf(thumbs.getIdpos()));
        text_razon.setText(thumbs.getNombre_punto());
        loadTipo();

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ActInventariar.this);
        builder.setCancelable(false);
        builder.setView(dialoglayout).setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                consultarInventario();
                dialog.dismiss();
            }
        }).setNegativeButton("Regresar", new  DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        builder.show();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act_inventariar_producto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.act_buscar) {
            LayoutInflater inflater = getLayoutInflater();
            View dialoglayout = inflater.inflate(R.layout.dialog_buscar_inventariar, null);

            text_idpos = (TextView) dialoglayout.findViewById(R.id.text_idpos);
            text_razon = (TextView) dialoglayout.findViewById(R.id.text_razon);
            spinner_tipo = (Spinner) dialoglayout.findViewById(R.id.spinner_tipo);

            text_idpos.setText(String.valueOf(thumbs.getIdpos()));
            text_razon.setText(thumbs.getNombre_punto());
            loadTipo();

            android.support.v7.app.AlertDialog.Builder builder2 = new android.support.v7.app.AlertDialog.Builder(ActInventariar.this);
            builder2.setCancelable(false);
            //builder2.setTitle("Motivo de Cancelación");
            builder2.setView(dialoglayout).setPositiveButton("Inventariar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    consultarInventario();
                    dialog.dismiss();
                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            builder2.show();
            return true;
        }
        if (id == R.id.act_guardar) {

            LayoutInflater inflater = getLayoutInflater();
            View dialoglayout = inflater.inflate(R.layout.dialog_bajas_inventariar, null);

            List<InventariarProducto> datosBajas = cloneList(inventariarProductos);

            datosN = new ListInventariarProducto();

            int cantidad = datosBajas.size();
            for (int a = 0; a < cantidad; a++) {
                if (!datosBajas.get(a).isCheck) {
                    datosN.add(datosBajas.get(a));
                }
            }

            if (datosN.size() > 0) {

                ListView mListViewBajas = (ListView) dialoglayout.findViewById(R.id.listView);
                actDetalleProductos = new AppAdapterInventariar(this, datosN, false);
                mListViewBajas.setAdapter(actDetalleProductos);

                android.support.v7.app.AlertDialog.Builder builderBajas = new android.support.v7.app.AlertDialog.Builder(ActInventariar.this);
                builderBajas.setCancelable(false);
                builderBajas.setTitle("¿ Estas seguro de dar baja a estos productos ?");
                builderBajas.setView(dialoglayout).setPositiveButton("Dar De Baja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        darBajaProductos();
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builderBajas.show();
            } else {
                TastyToast.makeText(this, "No hay productos para dar de baja", TastyToast.LENGTH_LONG, TastyToast.INFO);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void darBajaProductos() {

        cargarDialogo(this, "Por favor espere un momento", "Enviando información al servidor");

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "baja_manual");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        respuestaGuardar(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(ActInventariar.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActInventariar.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActInventariar.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActInventariar.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActInventariar.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }

                        progressDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                String parJSON = new Gson().toJson(datosN, ListInventariarProducto.class);

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());
                params.put("perfil", String.valueOf(controllerLogin.getUserLogin().getPerfil()));

                params.put("idpos", String.valueOf(thumbs.getIdpos()));
                params.put("datos", parJSON);
                params.put("latitud", String.valueOf(gpsServices.getLatitude()));
                params.put("longitud", String.valueOf(gpsServices.getLongitude()));

                return params;
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);

    }

    private void respuestaGuardar(String response) {
        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                ResponseMarcarPedido responseMarcarPedido = gson.fromJson(response, ResponseMarcarPedido.class);

                if (responseMarcarPedido.getEstado() == -1) {
                    //Error
                    TastyToast.makeText(this, responseMarcarPedido.getMsg(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                } else if (responseMarcarPedido.getEstado() == 0) {
                    // ok
                    TastyToast.makeText(this, responseMarcarPedido.getMsg(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    //Activity Detalle
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(this, ActMarcarVisita.class);
                    bundle.putSerializable("value", thumbs);
                    intent.putExtras(bundle);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    TastyToast.makeText(this, responseMarcarPedido.getMsg(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                progressDialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }
        } else {
            progressDialog.dismiss();
        }
    }

    public static List<InventariarProducto> cloneList(List<InventariarProducto> list) {
        List<InventariarProducto> clone = new ArrayList<InventariarProducto>(list.size());
        for (InventariarProducto item : list)
            try {
                clone.add((InventariarProducto) item.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        return clone;
    }

    private void loadTipo() {
        final List<CategoriasEstandar> listTipo = new ArrayList<>();
        listTipo.add(new CategoriasEstandar(1, "SIMCARDS"));
        listTipo.add(new CategoriasEstandar(2, "PRODUCTOS"));
        ArrayAdapter<CategoriasEstandar> adapterTipo = new ArrayAdapter<>(this, R.layout.textview_spinner, listTipo);
        spinner_tipo.setAdapter(adapterTipo);
        spinner_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo_busqueda = listTipo.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

    }

    private void consultarInventario() {

        cargarDialogo(this, "Por favor espere un momento", "Enviando información al servidor");

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "listar_productos_bajas");

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
                            Toast.makeText(ActInventariar.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActInventariar.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActInventariar.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActInventariar.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActInventariar.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());
                params.put("perfil", String.valueOf(controllerLogin.getUserLogin().getPerfil()));

                params.put("idpos", String.valueOf(thumbs.getIdpos()));
                params.put("tipo", String.valueOf(tipo_busqueda));


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

                inventariarProductos = gson.fromJson(response, ListInventariarProducto.class);
                mostrarProductos(inventariarProductos);

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                progressDialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }
        } else {
            progressDialog.dismiss();
            TastyToast.makeText(this, "No se encontraron datos para esta consulta", TastyToast.LENGTH_LONG, TastyToast.INFO);
            finish();
        }
    }

    private void mostrarProductos(List<InventariarProducto> inventariarProductos) {
        ListView mListView = (ListView) findViewById(R.id.listView);
        actDetalleProductos = new AppAdapterInventariar(this, inventariarProductos, true);
        mListView.setAdapter(actDetalleProductos);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                InventariarProducto inve = (InventariarProducto) parent.getAdapter().getItem(position);
                inve.isCheck = !inve.isCheck;
                SmoothCheckBox chk = (SmoothCheckBox) view.findViewById(R.id.checkBox);
                chk.setChecked(inve.isCheck, true);

            }

        });

    }

}
