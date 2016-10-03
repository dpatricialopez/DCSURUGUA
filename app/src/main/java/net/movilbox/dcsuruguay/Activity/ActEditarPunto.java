package net.movilbox.dcsuruguay.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Controller.ControllerCategoria;
import net.movilbox.dcsuruguay.Controller.ControllerDepartamento;
import net.movilbox.dcsuruguay.Controller.ControllerDireccion;
import net.movilbox.dcsuruguay.Controller.ControllerEstadoC;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerMunicipio;
import net.movilbox.dcsuruguay.Controller.ControllerTerritorio;
import net.movilbox.dcsuruguay.Controller.ControllerZona;
import net.movilbox.dcsuruguay.Model.CategoriasEstandar;
import net.movilbox.dcsuruguay.Model.EntEstandar;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.Model.EntListResposeCompra;
import net.movilbox.dcsuruguay.Model.EntPuntoList;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.GpsServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActEditarPunto extends BaseActivity implements View.OnClickListener {

    //region Variables globales...
    private Spinner spinner_departamento;
    private Spinner spinner_ciudad;
    private Spinner spinner_est_comercial;
    private Spinner spinner_circuito;
    private Spinner spinner_ruta;
    private Spinner spinner_categoria;
    private EditText edit_nombres;
    private EditText edit_cedula;
    private EditText edit_nom_cli;
    private EditText edit_correo_edit;
    private EditText edit_tel_edit;
    private EditText edit_cel_edit;
    private EditText edit_a;
    private EditText edit_tel_barrio;
    public ProgressDialog progressDialog;
    private int idDepartamento;
    private int idCiudad;
    private int idEstadoCom;
    private int idRutaZona;
    private int idCircuito;
    private int idCategoria;
    private int idTipoVia;
    private int idOtra;
    private EntLisSincronizar entLisSincronizar;
    private LinearLayout numeroLayout;
    private int tipoDocumento;
    private Spinner spinnerTipoDocumento;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_punto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            entLisSincronizar = (EntLisSincronizar) bundle.getSerializable("value");
        }

        //region Controles Spinner...
        spinner_departamento = (Spinner) findViewById(R.id.spinner_departamento);
        spinner_ciudad = (Spinner) findViewById(R.id.spinner_ciudad);
        spinner_est_comercial = (Spinner) findViewById(R.id.spinner_est_comercial);
        spinner_circuito = (Spinner) findViewById(R.id.spinner_circuito);
        spinner_ruta = (Spinner) findViewById(R.id.spinner_ruta);
        spinner_categoria = (Spinner) findViewById(R.id.spinner_categoria);
        spinnerTipoDocumento = (Spinner) findViewById(R.id.spinner_tipo_documento);
        FloatingActionButton guarda_punto = (FloatingActionButton) findViewById(R.id.guarda_punto);
        guarda_punto.setOnClickListener(this);
        //endregion

        //region Controles EditText...
        edit_nombres = (EditText) findViewById(R.id.edit_nombres);
        edit_nombres.setText(entLisSincronizar.getNombre_punto());

        edit_cedula = (EditText) findViewById(R.id.edit_cedula);
        edit_cedula.setText(entLisSincronizar.getCedula());

        edit_nom_cli = (EditText) findViewById(R.id.edit_nom_cli);
        edit_nom_cli.setText(entLisSincronizar.getNombre_cliente());

        edit_correo_edit = (EditText) findViewById(R.id.edit_correo_edit);
        edit_correo_edit.setText(entLisSincronizar.getEmail());

        edit_tel_edit = (EditText) findViewById(R.id.edit_tel_edit);
        edit_tel_edit.setText(entLisSincronizar.getTelefono());

        edit_cel_edit = (EditText) findViewById(R.id.edit_cel_edit);
        edit_cel_edit.setText(entLisSincronizar.getCelular());

        edit_a = (EditText) findViewById(R.id.edit_a);
        edit_a.setText(entLisSincronizar.getTexto_direccion());

        edit_tel_barrio = (EditText) findViewById(R.id.edit_tel_barrio);
        edit_tel_barrio.setText(entLisSincronizar.getBarrio());

        FloatingActionButton guardarPrunto = (FloatingActionButton) findViewById(R.id.guarda_punto);
        guardarPrunto.setOnClickListener(this);
        //endregion

        controllerDepartamento = new ControllerDepartamento(this);
        controllerMunicipio = new ControllerMunicipio(this);
        controllerDireccion = new ControllerDireccion(this);
        controllerEstadoC = new ControllerEstadoC(this);
        controllerTerritorio = new ControllerTerritorio(this);
        controllerZona = new ControllerZona(this);
        controllerCategoria = new ControllerCategoria(this);

        controllerLogin = new ControllerLogin(this);

        llenarInformacionSpinner();
    }

    private void llenarInformacionSpinner() {

        llenarDepartamentos(controllerDepartamento.getDespartamentos());
        llenarEstadoComercial(controllerEstadoC.getEstadoComercial());
        llenarCircuitoTerritorio(controllerTerritorio.getCircuito());
        llenarCategoria(controllerCategoria.getCategoria());
        loadSpinnerTipo();

    }

    private void selectSpinnerValue(List<EntEstandar> ListaEstado, Spinner spinner, int id) {
        for (int i = 0; i < ListaEstado.size(); i++) {
            if (ListaEstado.get(i).getId() == id) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void selectSpinnerValueSp(List<EntEstandar> ListaEstado, Spinner spinner, int id) {
        for (int i = 0; i < ListaEstado.size(); i++) {
            if (ListaEstado.get(i).getId_muni() == id) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    //region Llenar Spinner Tipo de documento.
    public void loadSpinnerTipo() {

        final List<EntEstandar> entEstandars = new ArrayList<>();

        entEstandars.add(new EntEstandar(1, "RUC"));
        entEstandars.add(new EntEstandar(2, "CEDULA"));

        ArrayAdapter<EntEstandar> adapterEstados = new ArrayAdapter<>(this, R.layout.textview_spinner, entEstandars);
        spinnerTipoDocumento.setAdapter(adapterEstados);
        spinnerTipoDocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoDocumento = entEstandars.get(position).getId();
                if (tipoDocumento == 1) {
                    int maxLength = 11;
                    edit_cedula.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                } else {
                    int maxLength = 8;
                    edit_cedula.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

        selectSpinnerValue(entEstandars, spinnerTipoDocumento, entLisSincronizar.getTipo_documento());
    }
    //endregion

    //region Llenar Spinner de Categorias.
    private void llenarCategoria(final List<EntEstandar> categoria) {

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, categoria);
        spinner_categoria.setAdapter(prec3);
        spinner_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCategoria = categoria.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        selectSpinnerValue(categoria, spinner_categoria, entLisSincronizar.getCategoria());

    }
    //endregion

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

        selectSpinnerValue(circuitoZona, spinner_circuito, entLisSincronizar.getTerritorio());

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

        selectSpinnerValue(entEstandars, spinner_ruta, entLisSincronizar.getZona());

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

        selectSpinnerValue(estadoComercial, spinner_est_comercial, entLisSincronizar.getEstado_com());

    }
    //endregion

    //region Llenar Spinner de los departamentos
    private void llenarDepartamentos(final List<EntEstandar> entEstandarList) {

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, entEstandarList);
        spinner_departamento.setAdapter(prec3);
        spinner_departamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idDepartamento = entEstandarList.get(position).getId();

                llenarCiudades(controllerMunicipio.getMunicipios(entEstandarList.get(position).getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        selectSpinnerValue(entEstandarList, spinner_departamento, entLisSincronizar.getIdDepartameto());

    }
    //endregion

    //region Llenar Spinner de las ciudades
    private void llenarCiudades(final List<EntEstandar> municipios) {

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, municipios);
        spinner_ciudad.setAdapter(prec3);
        spinner_ciudad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCiudad = municipios.get(position).getId_muni();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        selectSpinnerValueSp(municipios, spinner_ciudad, entLisSincronizar.getIdCiudad());

    }
    //endregion

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guarda_punto:

                if (!validarCampos()) {
                    editarPunto();
                }

                break;
        }

    }

    private void editarPunto() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Por favor espere");
        progressDialog.setMessage("Enviando información.");
        progressDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "guardar_punto");
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
                        progressDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                GpsServices gpsServices = new GpsServices(ActEditarPunto.this);

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));

                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());
                params.put("perfil", String.valueOf(controllerLogin.getUserLogin().getPerfil()));
                params.put("idpos", String.valueOf(entLisSincronizar.getIdpos()));

                params.put("accion", "Editar");

                params.put("latitud", String.valueOf(gpsServices.getLatitude()));
                params.put("longitud", String.valueOf(gpsServices.getLongitude()));

                EntPuntoList entPuntoList = new EntPuntoList();

                entPuntoList.setNombre_punto(edit_nombres.getText().toString());
                entPuntoList.setCedula(edit_cedula.getText().toString().trim());
                entPuntoList.setNombre_cliente(edit_nom_cli.getText().toString());
                entPuntoList.setEmail(edit_correo_edit.getText().toString());
                entPuntoList.setDepto(idDepartamento);
                entPuntoList.setCiudad(idCiudad);
                entPuntoList.setBarrio(edit_tel_barrio.getText().toString());
                entPuntoList.setTelefono(edit_tel_edit.getText().toString());
                entPuntoList.setCelular(edit_cel_edit.getText().toString());
                entPuntoList.setEstado_com(idEstadoCom);
                entPuntoList.setZona(idRutaZona);
                entPuntoList.setTerritorio(idCircuito);
                entPuntoList.setCategoria(idCategoria);
                entPuntoList.setTipo_via(idTipoVia);
                entPuntoList.setNumero_via(edit_a.getText().toString());
                entPuntoList.setOtra_direccion(idOtra);
                entPuntoList.setTipo_doc(tipoDocumento);

                String parJSON = new Gson().toJson(entPuntoList, EntPuntoList.class);

                params.put("datos", parJSON);

                return params;

            }

        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);

    }

    private void parseJSON(String response) {
        Gson gson = new Gson();

        EntListResposeCompra entListResposeCompra = gson.fromJson(response, EntListResposeCompra.class);

        progressDialog.dismiss();

        if (entListResposeCompra.getAccion() == -1) {
            TastyToast.makeText(this, entListResposeCompra.getMsg(), TastyToast.LENGTH_LONG, TastyToast.WARNING);
        } else if (entListResposeCompra.getAccion() == 0) {
            TastyToast.makeText(this, entListResposeCompra.getMsg(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            Bundle bundle = new Bundle();
            Intent intent = new Intent(this, ActMenu.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, 1);
            finish();
        }
    }

    public boolean validarCampos() {

        boolean indicadorValidate = false;

        if (isValidNumber(edit_nombres.getText().toString().trim())) {
            edit_nombres.setFocusable(true);
            edit_nombres.setFocusableInTouchMode(true);
            edit_nombres.requestFocus();
            edit_nombres.setText("");
            edit_nombres.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidNumber(edit_cedula.getText().toString().trim())) {
            edit_cedula.setFocusable(true);
            edit_cedula.setFocusableInTouchMode(true);
            edit_cedula.requestFocus();
            edit_cedula.setText("");
            edit_cedula.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidNumber(edit_nom_cli.getText().toString().trim())) {
            edit_nom_cli.setFocusable(true);
            edit_nom_cli.setFocusableInTouchMode(true);
            edit_nom_cli.requestFocus();
            edit_nom_cli.setText("");
            edit_nom_cli.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValiEmail(edit_correo_edit.getText().toString().trim())) {
            edit_correo_edit.setFocusable(true);
            edit_correo_edit.setFocusableInTouchMode(true);
            edit_correo_edit.requestFocus();
            edit_correo_edit.setText("");
            edit_correo_edit.setError("Formarto de correo no valido");
            indicadorValidate = true;
        } else if (isValidNumber(edit_tel_edit.getText().toString().trim())) {
            edit_tel_edit.setFocusable(true);
            edit_tel_edit.setFocusableInTouchMode(true);
            edit_tel_edit.requestFocus();
            edit_tel_edit.setText("");
            edit_tel_edit.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidNumber(edit_cel_edit.getText().toString().trim())) {
            edit_cel_edit.setFocusable(true);
            edit_cel_edit.setFocusableInTouchMode(true);
            edit_cel_edit.requestFocus();
            edit_cel_edit.setText("");
            edit_cel_edit.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidNumber(edit_a.getText().toString().trim())) {
            edit_a.setFocusable(true);
            edit_a.setFocusableInTouchMode(true);
            edit_a.requestFocus();
            edit_a.setText("");
            edit_a.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (idDepartamento == 0) {
            spinner_departamento.setFocusable(true);
            spinner_departamento.setFocusableInTouchMode(true);
            spinner_departamento.requestFocus();
            indicadorValidate = true;
        } else if (idEstadoCom == 0) {
            spinner_est_comercial.setFocusable(true);
            spinner_est_comercial.setFocusableInTouchMode(true);
            spinner_est_comercial.requestFocus();
            indicadorValidate = true;
        } else if (idRutaZona == 0) {
            spinner_ruta.setFocusable(true);
            spinner_ruta.setFocusableInTouchMode(true);
            spinner_ruta.requestFocus();
            indicadorValidate = true;
        } else if (idCircuito == 0) {
            spinner_circuito.setFocusable(true);
            spinner_circuito.setFocusableInTouchMode(true);
            spinner_circuito.requestFocus();
            indicadorValidate = true;
        } else if (idCategoria == 0) {
            spinner_categoria.setFocusable(true);
            spinner_categoria.setFocusableInTouchMode(true);
            spinner_categoria.requestFocus();
            indicadorValidate = true;
        }

        return indicadorValidate;

    }

}
