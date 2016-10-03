package net.movilbox.dcsuruguay.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import net.movilbox.dcsuruguay.Activity.ActResulMiRutero;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerTerritorio;
import net.movilbox.dcsuruguay.Controller.ControllerZona;
import net.movilbox.dcsuruguay.Model.EntEstandar;
import net.movilbox.dcsuruguay.Model.EntListRutero;
import net.movilbox.dcsuruguay.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMenuRutero extends BaseVolleyFragment implements View.OnClickListener {

    private Spinner spinner_circuito;
    private Spinner spinner_ruta;
    private Spinner spinner_estado_visita;
    private Spinner spinner_dia_visita;
    public ProgressDialog progressDialog;
    public EditText edit_idpos;
    public EditText edit_nombre;
    public int circuito;
    public int ruta;
    public int estadoVisi;
    public int diaVisita;

    public FragmentMenuRutero() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_menu_rutero, container, false);

        spinner_circuito = (Spinner) view.findViewById(R.id.spinner_circuito);
        spinner_ruta = (Spinner) view.findViewById(R.id.spinner_ruta);
        spinner_estado_visita = (Spinner) view.findViewById(R.id.spinner_estado_visita);
        spinner_dia_visita = (Spinner) view.findViewById(R.id.spinner_dia_visita);
        FloatingActionButton cargar_reporte = (FloatingActionButton) view.findViewById(R.id.cargar_reporte);
        cargar_reporte.setOnClickListener(this);

        edit_idpos = (EditText) view.findViewById(R.id.edit_idpos);
        edit_nombre = (EditText) view.findViewById(R.id.edit_nombre);

        controllerTerritorio = new ControllerTerritorio(getActivity());
        controllerZona = new ControllerZona(getActivity());
        controllerLogin = new ControllerLogin(getActivity());

        setHasOptionsMenu(false);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        llenarCircuito(controllerTerritorio.getCircuito());
        llenarEstadoVisita();
        llenarDiasVisita();

    }

    //region Llenar Spinner de Circuito.
    private void llenarCircuito(final List<EntEstandar> categoria) {

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, categoria);
        spinner_circuito.setAdapter(prec3);
        spinner_circuito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                circuito = categoria.get(position).getId();

                llenarRuta(categoria.get(position).getId());
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
                ruta = entEstandars.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

    }
    //endregion

    //region Llenar Spinner de tipos de calles
    private void llenarEstadoVisita() {

        final List<EntEstandar> entEstandar = new ArrayList<>();
        entEstandar.add(new EntEstandar(0, "SELECCIONAR"));
        entEstandar.add(new EntEstandar(1, "Visitado"));
        entEstandar.add(new EntEstandar(2, "Sin Visitar"));

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, entEstandar);
        spinner_estado_visita.setAdapter(prec3);
        spinner_estado_visita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoVisi = entEstandar.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }
    //endregion

    //region Llenar Spinner Días de visita
    private void llenarDiasVisita() {

        final List<EntEstandar> entEstandar = new ArrayList<>();
        entEstandar.add(new EntEstandar(0, "SELECCIONAR"));
        entEstandar.add(new EntEstandar(1, "Lunes"));
        entEstandar.add(new EntEstandar(2, "Martes"));
        entEstandar.add(new EntEstandar(3, "Miercoles"));
        entEstandar.add(new EntEstandar(4, "Jueves"));
        entEstandar.add(new EntEstandar(5, "Viernes"));
        entEstandar.add(new EntEstandar(6, "Sábado"));
        entEstandar.add(new EntEstandar(7, "Domingo"));

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, entEstandar);
        spinner_dia_visita.setAdapter(prec3);
        spinner_dia_visita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diaVisita = entEstandar.get(position).getId();
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
            case R.id.cargar_reporte:
                guardarPunto();
                break;
        }
    }


    private void guardarPunto() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Alerta.");
        progressDialog.setMessage("Cargando Información");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "reporte_mirutero");
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

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());

                params.put("pto_idpos", edit_idpos.getText().toString().trim());
                params.put("pto_name", edit_nombre.getText().toString());

                params.put("pto_circ", String.valueOf(circuito));
                params.put("pto_ruta", String.valueOf(ruta));
                params.put("pto_est_vis", String.valueOf(estadoVisi));
                params.put("pto_dias", String.valueOf(diaVisita));

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void responseAprobacion(String response) {
        Gson gson = new Gson();
        progressDialog.dismiss();
        EntListRutero lisSincronizarList = gson.fromJson(response, EntListRutero.class);

        Bundle bundle = new Bundle();
        Intent intent = new Intent(getActivity(), ActResulMiRutero.class);
        bundle.putSerializable("value", lisSincronizarList);
        intent.putExtras(bundle);
        startActivity(intent);



    }
}
