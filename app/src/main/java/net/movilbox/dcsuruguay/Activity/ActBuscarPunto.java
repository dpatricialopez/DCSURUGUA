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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Controller.ControllerEstadoC;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerTerritorio;
import net.movilbox.dcsuruguay.Controller.ControllerZona;
import net.movilbox.dcsuruguay.Model.EntEstandar;
import net.movilbox.dcsuruguay.Model.ListHome;
import net.movilbox.dcsuruguay.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActBuscarPunto extends BaseActivity {

    //Comentario de versiónyisus.
    private Spinner spinner_circuito_buscar;
    private Spinner spinner_ruta;
    private Spinner spinner_est_comercial;
    private EditText edit_idpos;
    private EditText edit_cedula;
    private EditText edit_nombre;
    private int distrito;
    private int estado_circuito;
    private int estado_ruta;
    private int estado_comercial;
    private int idCircuito;
    private int idRutaZona;
    private int idEstadoCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_punto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controllerEstadoC = new ControllerEstadoC(this);
        controllerTerritorio = new ControllerTerritorio(this);
        controllerZona = new ControllerZona(this);
        controllerLogin = new ControllerLogin(this);

        spinner_circuito_buscar = (Spinner) findViewById(R.id.spinner_circuito_buscar);
        spinner_ruta = (Spinner) findViewById(R.id.spinner_ruta);
        spinner_est_comercial = (Spinner) findViewById(R.id.spinner_est_comercial);
        edit_idpos = (EditText) findViewById(R.id.edit_idpos);
        edit_cedula = (EditText) findViewById(R.id.edit_cedula);
        edit_nombre = (EditText) findViewById(R.id.edit_nombre);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarPuntoJSO();
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

        llenarParametrosSpinner();
    }

    private void llenarParametrosSpinner() {

        controllerEstadoC = new ControllerEstadoC(this);
        llenarEstadoComercial(controllerEstadoC.getEstadoComercial());

        controllerTerritorio = new ControllerTerritorio(this);
        llenarCircuitoTerritorio(controllerTerritorio.getCircuito());
    }

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

    //region Llenar Spinner de Circuitos / Territorios.
    private void llenarCircuitoTerritorio(final List<EntEstandar> circuitoZona) {

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, circuitoZona);
        spinner_circuito_buscar.setAdapter(prec3);
        spinner_circuito_buscar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void buscarPuntoJSO() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sincronización de Información");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("");
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setIndeterminate(false);

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "consultar_info_puntos");
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

                int values = 0;

                if (!isValidNumber(edit_idpos.getText().toString().trim()))
                    values = Integer.parseInt(edit_idpos.getText().toString().trim());

                params.put("idpos", String.valueOf(values));
                params.put("cedula", edit_cedula.getText().toString());
                params.put("nombre", edit_nombre.getText().toString());

                params.put("circuito", String.valueOf(estado_circuito));
                params.put("ruta", String.valueOf(estado_ruta));
                params.put("est_comercial", String.valueOf(estado_comercial));

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));

                params.put("db", controllerLogin.getUserLogin().getBd());
                params.put("perfil", String.valueOf(controllerLogin.getUserLogin().getPerfil()));

                return params;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(100000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);
    }

    private void parseJSONBuscar(String response) {

        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                ListHome responseHomeList = gson.fromJson(response, ListHome.class);

                Bundle bundle = new Bundle();
                Intent intent = new Intent(this, ActDetalleBuscarPunto.class);
                bundle.putSerializable("value", responseHomeList);
                intent.putExtras(bundle);
                startActivity(intent);

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                progressDialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }
        } else {
            progressDialog.dismiss();
            TastyToast.makeText(this, "No se encontraron puntos", TastyToast.LENGTH_LONG, TastyToast.WARNING);
        }
    }

}
