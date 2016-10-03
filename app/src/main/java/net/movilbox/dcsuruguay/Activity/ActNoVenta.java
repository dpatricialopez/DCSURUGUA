package net.movilbox.dcsuruguay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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


import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.FuncionesGenerales;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.Model.EntLoginR;
import net.movilbox.dcsuruguay.Model.Motivos;
import net.movilbox.dcsuruguay.Model.NoVisita;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;
import net.movilbox.dcsuruguay.Services.GpsServices;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActNoVenta extends BaseActivity implements View.OnClickListener {

    private Spinner spinner_motivos;
    private EditText EditComment;
    private Button btn_guardar_no;
    private Button btn_guardar;
    private int id_estado;
    private EntLisSincronizar entLisSincronizar;
    GpsServices gpsServices;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_venta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("No venta");
        setSupportActionBar(toolbar);

        controllerLogin = new ControllerLogin(this);
        gpsServices = new GpsServices(this);

        spinner_motivos = (Spinner) findViewById(R.id.spinner_motivos);
        EditComment = (EditText) findViewById(R.id.EditComment);

        btn_guardar_no = (Button) findViewById(R.id.btn_guardar_no);
        btn_guardar_no.setOnClickListener(this);

        btn_guardar = (Button) findViewById(R.id.btn_guardar);
        btn_guardar.setOnClickListener(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            entLisSincronizar = (EntLisSincronizar) bundle.getSerializable("value");
        }

        connectionDetector = new ConnectionDetector(this);
        funcionesGenerales = new FuncionesGenerales(this);

        llenarSpinner();

        if (connectionDetector.isConnected()) {
            toolbar.setTitle("No venta");
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            toolbar.setTitle("No venta OFFLINE");
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.rojo_progress));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void llenarSpinner() {
        List<Motivos> motivosList = new ArrayList<>();

        motivosList.add(new Motivos(1, "STOCK SUFICIENTE"));
        motivosList.add(new Motivos(2, "CERRADO"));
        motivosList.add(new Motivos(3, "NO SE ENCUENTRA EL DUEÑO"));
        motivosList.add(new Motivos(4, "FALTA DE CAPITAL"));
        motivosList.add(new Motivos(5, "NO EXISTE EL PDV"));
        loadCausa(motivosList);
    }

    private void loadCausa(final List<Motivos> thumbs) {

        ArrayAdapter<Motivos> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, thumbs);
        spinner_motivos.setAdapter(prec3);
        spinner_motivos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_estado = thumbs.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_guardar_no:
                finish();
                break;

            case R.id.btn_guardar:
                if (isValidNumber(EditComment.getText().toString().trim())) {
                    EditComment.setFocusable(true);
                    EditComment.setFocusableInTouchMode(true);
                    EditComment.requestFocus();
                    EditComment.setError("Este campo es obligatorio");
                } else {

                    if (connectionDetector.isConnected()) {
                        guardarVisita();
                    } else {
                        guardarVisitaLocal();
                    }
                }
                break;

        }
    }

    private void guardarVisitaLocal() {

        NoVisita noVisita = new NoVisita();
        noVisita.setIdpos(entLisSincronizar.getIdpos());
        noVisita.setMotivo(id_estado);
        noVisita.setObservacion(EditComment.getText().toString());
        noVisita.setLatitud(gpsServices.getLatitude());
        noVisita.setLongitud(gpsServices.getLongitude());

        if (funcionesGenerales.insertNoVenta(noVisita)) {
            TastyToast.makeText(this, "Se guardo la no venta localmente", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            Bundle bundle = new Bundle();
            Intent intent = new Intent(ActNoVenta.this, ActMenu.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, 1);
        } else {
            TastyToast.makeText(this, "Problemas al guardar", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
        }

    }

    private void guardarVisita() {

        cargarDialogo(this, "Por favor espere un momento", "Enviando información al servidor");

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "guardar_no_venta");
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
                            Toast.makeText(ActNoVenta.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActNoVenta.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActNoVenta.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActNoVenta.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActNoVenta.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }

                        progressDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("idpos", String.valueOf(entLisSincronizar.getIdpos()));
                params.put("motivo", String.valueOf(id_estado));
                params.put("observacion", EditComment.getText().toString());
                params.put("latitud", String.valueOf(gpsServices.getLatitude()));
                params.put("longitud", String.valueOf(gpsServices.getLongitude()));

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

    private void parseJSON(String response) {
        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                Charset.forName("UTF-8").encode(response);
                byte ptext[] = response.getBytes(Charset.forName("ISO-8859-1"));
                String value = new String(ptext, Charset.forName("UTF-8"));

                EntLoginR entLoginR = gson.fromJson(value, EntLoginR.class);
                if (entLoginR.getEstado() == -1) {
                    //Error
                    TastyToast.makeText(this, entLoginR.getMsg(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                } else if (entLoginR.getEstado() == -2) {
                    //No tiene permiso
                    TastyToast.makeText(this, entLoginR.getMsg(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                } else {
                    // ok
                    TastyToast.makeText(this, entLoginR.getMsg(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(this, ActMenu.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1);
                    finish();
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
