package net.movilbox.dcsuruguay.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Activity.ActBuscarPunto;
import net.movilbox.dcsuruguay.Activity.ActBusquedaAvPunto;
import net.movilbox.dcsuruguay.Activity.ActCarrito;
import net.movilbox.dcsuruguay.Activity.ActMenu;
import net.movilbox.dcsuruguay.Controller.ControllerCategoria;
import net.movilbox.dcsuruguay.Controller.ControllerDepartamento;
import net.movilbox.dcsuruguay.Controller.ControllerDireccion;
import net.movilbox.dcsuruguay.Controller.ControllerEstadoC;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerMunicipio;
import net.movilbox.dcsuruguay.Controller.ControllerPunto;
import net.movilbox.dcsuruguay.Controller.ControllerTerritorio;
import net.movilbox.dcsuruguay.Controller.ControllerZona;
import net.movilbox.dcsuruguay.Model.CategoriasEstandar;
import net.movilbox.dcsuruguay.Model.EntEstandar;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.Model.EntListResposeCompra;
import net.movilbox.dcsuruguay.Model.EntPuntoList;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;
import net.movilbox.dcsuruguay.Services.GpsServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCrearPunto extends BaseVolleyFragment implements View.OnClickListener {

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
    private Switch swVendeRecargas;
     int venta_recarga = 2;
    public ProgressDialog progressDialog;
    private int idDepartamento;
    private int idCiudad;
    private int idEstadoCom;
    private int idRutaZona;
    private int idCircuito;
    private int idCategoria;
    private Spinner spinnerTipoDocumento;
    private List<CategoriasEstandar> ListaTipoDoc = new ArrayList<>();
    private int tipoDocumento;
    private ConnectionDetector connectionDetector;

    //endregion

    public FragmentCrearPunto() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_punto, container, false);

        //region Controles Spinner...
        spinner_departamento = (Spinner) view.findViewById(R.id.spinner_departamento);
        spinner_ciudad = (Spinner) view.findViewById(R.id.spinner_ciudad);
        spinner_est_comercial = (Spinner) view.findViewById(R.id.spinner_est_comercial);
        spinner_circuito = (Spinner) view.findViewById(R.id.spinner_circuito);
        spinner_ruta = (Spinner) view.findViewById(R.id.spinner_ruta);
        spinner_categoria = (Spinner) view.findViewById(R.id.spinner_categoria);
        spinnerTipoDocumento = (Spinner) view.findViewById(R.id.spinner_tipo_documento);
        FloatingActionButton guarda_punto = (FloatingActionButton) view.findViewById(R.id.guarda_punto);
        guarda_punto.setOnClickListener(this);
        connectionDetector = new ConnectionDetector(getActivity());
        //endregion

        //region Controles EditText...
        edit_nombres = (EditText) view.findViewById(R.id.edit_nombres);
        edit_cedula = (EditText) view.findViewById(R.id.edit_cedula);
        edit_nom_cli = (EditText) view.findViewById(R.id.edit_nom_cli);
        edit_correo_edit = (EditText) view.findViewById(R.id.edit_correo_edit);
        edit_tel_edit = (EditText) view.findViewById(R.id.edit_tel_edit);
        edit_cel_edit = (EditText) view.findViewById(R.id.edit_cel_edit);
        edit_a = (EditText) view.findViewById(R.id.edit_a);
        edit_tel_barrio = (EditText) view.findViewById(R.id.edit_tel_barrio);
        swVendeRecargas = (Switch) view.findViewById(R.id.swVendeRecargas);
        //endregion

        controllerDepartamento = new ControllerDepartamento(getActivity());
        controllerMunicipio = new ControllerMunicipio(getActivity());
        controllerDireccion = new ControllerDireccion(getActivity());
        controllerEstadoC = new ControllerEstadoC(getActivity());
        controllerTerritorio = new ControllerTerritorio(getActivity());
        controllerZona = new ControllerZona(getActivity());
        controllerCategoria = new ControllerCategoria(getActivity());

        controllerLogin = new ControllerLogin(getActivity());

        controllerPunto = new ControllerPunto(getActivity());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item = menu.add("Buscar");
        item.setIcon(R.drawable.ic_search_white_24dp); // sets icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Actividad para buscar puntos...
                startActivity(new Intent(getActivity(), ActBusquedaAvPunto.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                return true;
            }

        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        llenarInformacionSpinner();

        loadSpinnerTipo();

       /* swVendeRecargas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    venta_recarga = 1;

                } else {
                    venta_recarga = 2;
                }
            }
        });*/

    }

    //region Llenar Spinner Tipo de documento.
    public void loadSpinnerTipo() {

        ListaTipoDoc = new ArrayList<>();

        ListaTipoDoc.add(new CategoriasEstandar(1, "RUC"));
        ListaTipoDoc.add(new CategoriasEstandar(2, "CEDULA"));

        ArrayAdapter<CategoriasEstandar> adapterEstados = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, ListaTipoDoc);
        spinnerTipoDocumento.setAdapter(adapterEstados);
        spinnerTipoDocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoDocumento = ListaTipoDoc.get(position).getId();
                edit_cedula.setHint("");
                edit_cedula.setText("");
                if (tipoDocumento == 1) {
                    int maxLength = 16;
                    edit_cedula.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                    edit_cedula.setHint("Ruc");
                } else {
                    int maxLength = 8;
                    edit_cedula.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                    edit_cedula.setHint("Cedula");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }
    //endregion

    private void llenarInformacionSpinner() {

        llenarDepartamentos(controllerDepartamento.getDespartamentos());
        llenarEstadoComercial(controllerEstadoC.getEstadoComercial());
        llenarCircuitoTerritorio(controllerTerritorio.getCircuito());
        llenarCategoria(controllerCategoria.getCategoria());

    }

    //region Llenar Spinner de Categorias.
    private void llenarCategoria(final List<EntEstandar> categoria) {

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, categoria);
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

    }
    //endregion

    //region Llenar Spinner de Circuitos / Territorios.
    private void llenarCircuitoTerritorio(final List<EntEstandar> circuitoZona) {

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, circuitoZona);
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

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, entEstandars);
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

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, estadoComercial);
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

    //region Llenar Spinner de los departamentos
    private void llenarDepartamentos(final List<EntEstandar> entEstandarList) {

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, entEstandarList);
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
    }
    //endregion

    //region Llenar Spinner de las ciudades
    private void llenarCiudades(final List<EntEstandar> municipios) {

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, municipios);
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
    }
    //endregion

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guarda_punto:

                if (!validarCampos()) {
                    //Llamamos el servicio de guardar el punto

                    if (connectionDetector.isConnected()) {
                        guardarPunto();
                    } else {
                        guardarPuntoLocal();
                    }

                }

                break;
        }
    }

    private void guardarPuntoLocal() {

        GpsServices gpsServices = new GpsServices(getActivity());
        EntLisSincronizar entLisSincronizar = new EntLisSincronizar();

        entLisSincronizar.setCategoria(idCategoria);
        entLisSincronizar.setCedula(edit_cedula.getText().toString().trim());
        entLisSincronizar.setCelular(edit_cel_edit.getText().toString().trim());
        entLisSincronizar.setCiudad(idCiudad);
        entLisSincronizar.setDepto(idDepartamento);
        entLisSincronizar.setBarrio(edit_tel_barrio.getText().toString());


        entLisSincronizar.setNombre_punto(edit_nombres.getText().toString());
        entLisSincronizar.setNombre_cliente(edit_nom_cli.getText().toString());
        entLisSincronizar.setEmail(edit_correo_edit.getText().toString());
        entLisSincronizar.setTelefono(edit_tel_edit.getText().toString());
        entLisSincronizar.setEstado_com(idEstadoCom);
        entLisSincronizar.setZona(idRutaZona);
        entLisSincronizar.setTerritorio(idCircuito);
        entLisSincronizar.setTexto_direccion(edit_a.getText().toString());
        entLisSincronizar.setTipo_documento(tipoDocumento);
        entLisSincronizar.setLatitud(gpsServices.getLatitude());
        entLisSincronizar.setLongitud(gpsServices.getLongitude());
        if(swVendeRecargas.isChecked()){
            entLisSincronizar.setVende_recargas(1);
        }else{
            entLisSincronizar.setVende_recargas(0);
        }

        if (controllerPunto.insertPuntoLocal(entLisSincronizar, controllerLogin.getUserLogin().getId())) {
            TastyToast.makeText(getActivity(), "Punto guardado local sincronizar para ver los cambios", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            Bundle bundle = new Bundle();
            Intent intent = new Intent(getActivity(), ActMenu.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, 1);
            getActivity().finish();
        } else {
            TastyToast.makeText(getActivity(), "No se guardaron los puntos", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
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

    private void guardarPunto() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Alerta.");
        progressDialog.setMessage("Cargando Información");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "guardar_punto");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        responseAprobacion(response);
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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                GpsServices gpsServices = new GpsServices(getActivity());

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));

                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());
                params.put("perfil", String.valueOf(controllerLogin.getUserLogin().getPerfil()));

                params.put("accion", "Guardar");

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
                entPuntoList.setNumero_via(edit_a.getText().toString());
                entPuntoList.setTipo_doc(tipoDocumento);
                if(swVendeRecargas.isChecked()){
                    entPuntoList.setVende_recargas(1);
                }else{
                    entPuntoList.setVende_recargas(0);
                }


                String parJSON = new Gson().toJson(entPuntoList, EntPuntoList.class);

                params.put("datos", parJSON);

                return params;

            }
        };
        addToQueue(jsonRequest);
    }

    private void responseAprobacion(String response) {

        progressDialog.dismiss();

        Gson gson = new Gson();

        EntListResposeCompra entListResposeCompra = gson.fromJson(response, EntListResposeCompra.class);

        if (entListResposeCompra.getAccion() == -1) {
            TastyToast.makeText(getActivity(), entListResposeCompra.getMsg(), TastyToast.LENGTH_LONG, TastyToast.WARNING);
        } else if (entListResposeCompra.getAccion() == 0) {
            TastyToast.makeText(getActivity(), entListResposeCompra.getMsg(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            Bundle bundle = new Bundle();
            Intent intent = new Intent(getActivity(), ActMenu.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, 1);
            getActivity().finish();
        }

    }

}
