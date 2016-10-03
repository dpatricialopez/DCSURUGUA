package net.movilbox.dcsuruguay.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import net.movilbox.dcsuruguay.Controller.ControllerEstadoC;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerTerritorio;
import net.movilbox.dcsuruguay.Controller.ControllerZona;
import net.movilbox.dcsuruguay.Model.EntEstandar;
import net.movilbox.dcsuruguay.Model.ListHome;
import net.movilbox.dcsuruguay.Model.ResponseHome;
import net.movilbox.dcsuruguay.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActBusquedaAvPunto extends BaseActivity implements View.OnClickListener {

    private Spinner spinner_est_comercial;
    private Spinner spinner_circuito;
    private Spinner spinner_ruta;
    private int idCircuito;
    private int idRutaZona;
    private int idEstadoCom;
    private EditText edit_nombre_punto;
    private EditText edit_nombre_cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_av_punto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner_est_comercial = (Spinner) findViewById(R.id.spinner_est_comercial);
        spinner_circuito = (Spinner) findViewById(R.id.spinner_circuito);
        spinner_ruta = (Spinner) findViewById(R.id.spinner_ruta);
        edit_nombre_punto = (EditText) findViewById(R.id.edit_nombre_punto);
        edit_nombre_cliente = (EditText) findViewById(R.id.edit_nombre_cliente);

        FloatingActionButton fabConsultar = (FloatingActionButton) findViewById(R.id.fabConsultar);
        fabConsultar.setOnClickListener(this);

        controllerEstadoC = new ControllerEstadoC(this);
        controllerTerritorio = new ControllerTerritorio(this);
        controllerZona = new ControllerZona(this);
        controllerLogin = new ControllerLogin(this);

        llenarEstadoComercial(controllerEstadoC.getEstadoComercial());
        llenarCircuitoTerritorio(controllerTerritorio.getCircuito());

    }

    //region Llenar Spinner de Circuitos / Territorios.
    private void llenarCircuitoTerritorio(final List<EntEstandar> circuitoZona) {

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, circuitoZona);
        spinner_circuito.setAdapter(prec3);
        spinner_circuito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCircuito = circuitoZona.get(position).getId();

                llenarRuta(circuitoZona.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

    }
    //endregion

    //region Llenar Spinner de las rutas
    private void llenarRuta(int id) {

        final List<EntEstandar> entEstandars = controllerZona.getRuta(id);

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, entEstandars);
        spinner_ruta.setAdapter(prec3);
        spinner_ruta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idRutaZona = entEstandars.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

    }
    //endregion

    //region Llenar Spinner de los estados comerciales
    private void llenarEstadoComercial(final List<EntEstandar> estadoComercial) {

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, estadoComercial);
        spinner_est_comercial.setAdapter(prec3);
        spinner_est_comercial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idEstadoCom = estadoComercial.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }
    //endregion

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabConsultar:

                if (isValidNumber(edit_nombre_punto.getText().toString()) && isValidNumber(edit_nombre_cliente.getText().toString()) &&  idCircuito == 0 && idRutaZona == 0 ) {
                    Toast.makeText(this,"Debes ingresar mínimo un campo",Toast.LENGTH_LONG).show();
                } else {
                    buscarPuntoJSO();
                }

                break;
        }
    }

    private void buscarPuntoJSO() {

        String consulta = "consultar_puntos_avanzados";
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Por favor espere");
        progressDialog.setMessage("Enviando información.");
        progressDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), consulta);
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJSONBuscar(response);
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

                params.put("nombre_punto", edit_nombre_punto.getText().toString());
                params.put("nombre_cliente", edit_nombre_cliente.getText().toString());

                params.put("circuito", String.valueOf(idCircuito));
                params.put("ruta", String.valueOf(idRutaZona));
                params.put("est_comercial", String.valueOf(idEstadoCom));

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());
                params.put("perfil", String.valueOf(controllerLogin.getUserLogin().getPerfil()));


                return params;
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);

    }

    private void parseJSONBuscar(String response) {

        Gson gson = new Gson();
        progressDialog.dismiss();

        if (!response.equals("[]")) {

            ListHome responseHomeList = gson.fromJson(response, ListHome.class);

            //setResponseHomeListS(responseHomeList);

            Bundle bundle = new Bundle();
            Intent intent = new Intent(this, ActResponAvanBusqueda.class);
            bundle.putSerializable("value", responseHomeList);
            intent.putExtras(bundle);
            startActivity(intent);

        } else {
            Toast.makeText(this, "No se encontraron puntos", Toast.LENGTH_LONG).show();
        }

    }

}
