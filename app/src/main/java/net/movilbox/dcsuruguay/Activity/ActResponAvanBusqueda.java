package net.movilbox.dcsuruguay.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;

import net.movilbox.dcsuruguay.Adapter.AdapterRuteroBusAv;
import net.movilbox.dcsuruguay.Adapter.RecyclerItemClickListener;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.Model.ResponseMarcarPedido;
import net.movilbox.dcsuruguay.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActResponAvanBusqueda extends BaseActivity {

    private List<EntLisSincronizar> thumbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_buscar_punto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controllerLogin = new ControllerLogin(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            thumbs = (List<EntLisSincronizar>) bundle.getSerializable("value");
        }

        final SwipeMenuListView mListView = (SwipeMenuListView) findViewById(R.id.listView);

        final AdapterRuteroBusAv appAdapterRutero = new AdapterRuteroBusAv(this, thumbs);
        mListView.setAdapter(appAdapterRutero);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                buscarIdPos(thumbs.get(i).getIdpos());
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /*private void buscarPuntoLocal(int edit_buscar) {
        //Recuperar punto local

        ResponseMarcarPedido responseMarcarPedido = mydb.getPuntoLocal(String.valueOf(edit_buscar));

        if (responseMarcarPedido.getRazon_social() == null) {
            Toast.makeText(this, "El punto no se encuentra en la base de datos local", Toast.LENGTH_LONG).show();
        } else {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(this, ActMarcarVisita.class);
            bundle.putSerializable("value", responseMarcarPedido);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }*/


    public void buscarIdPos(final int idPos) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Por favor espere");
        progressDialog.setMessage("Enviando información.");
        progressDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "buscar_punto_visita");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJSONVisita(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(ActResponAvanBusqueda.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActResponAvanBusqueda.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActResponAvanBusqueda.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActResponAvanBusqueda.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActResponAvanBusqueda.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }

                        progressDialog.dismiss();
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
                params.put("idpos", String.valueOf(idPos));

                return params;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);

    }

    public void parseJSONVisita(String response) {

        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                EntLisSincronizar responseMarcarPedido = gson.fromJson(response, EntLisSincronizar.class);

                if (responseMarcarPedido.getEstado() == -1) {
                    //No tiene permisos del punto
                    Toast.makeText(getApplicationContext(), responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else if (responseMarcarPedido.getEstado() == -2) {
                    //El punto no existe
                    Toast.makeText(getApplicationContext(), responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else {

                    //Activity Detalle
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(this, ActMarcarVisita.class);
                    bundle.putSerializable("value", responseMarcarPedido);
                    intent.putExtras(bundle);
                    startActivity(intent);
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

}
