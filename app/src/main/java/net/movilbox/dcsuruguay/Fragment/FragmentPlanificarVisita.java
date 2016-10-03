package net.movilbox.dcsuruguay.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Activity.ActPlanificarOrdenar;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Model.EntEstandar;
import net.movilbox.dcsuruguay.Model.ListResponsePlaniVisita;
import net.movilbox.dcsuruguay.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPlanificarVisita extends BaseVolleyFragment implements View.OnClickListener {

    private FloatingActionButton btnBuscarPlanificar;
    private Spinner spinner_planificacion;
    private Spinner spinner_tipo;
    private LinearLayout liner_tipo;
    private int idPlanificacion;
    private int idtipo;
    public ProgressDialog progressDialog;

    public FragmentPlanificarVisita() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_planificar_visita, container, false);

        btnBuscarPlanificar = (FloatingActionButton) view.findViewById(R.id.btnBuscarPlanificar);
        btnBuscarPlanificar.setOnClickListener(this);

        spinner_planificacion = (Spinner) view.findViewById(R.id.spinner_planificacion);
        spinner_tipo = (Spinner) view.findViewById(R.id.spinner_tipo);

        liner_tipo = (LinearLayout) view.findViewById(R.id.liner_tipo);

        controllerLogin = new ControllerLogin(getActivity());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(false);

        loadPlanificacion();
        loadTipo();

    }

    private void loadPlanificacion() {

        final List<EntEstandar> dataSpinnerPla = new ArrayList<>();

        dataSpinnerPla.add(new EntEstandar(0, "SELECCIONAR"));
        dataSpinnerPla.add(new EntEstandar(1, "Planificación Manual"));
        dataSpinnerPla.add(new EntEstandar(2, "Planificación días de Inventario"));
        dataSpinnerPla.add(new EntEstandar(3, "Planificación Promedio de Ventas"));

        ArrayAdapter<EntEstandar> adapterEstados = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, dataSpinnerPla);
        spinner_planificacion.setAdapter(adapterEstados);
        spinner_planificacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idPlanificacion = dataSpinnerPla.get(position).getId();
                if (idPlanificacion == 2 || idPlanificacion == 3) {
                    liner_tipo.setVisibility(View.VISIBLE);
                } else {
                    liner_tipo.setVisibility(View.GONE);
                    spinner_tipo.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void loadTipo() {

        final List<EntEstandar> dataSpinnerPla = new ArrayList<>();


        dataSpinnerPla.add(new EntEstandar(0, "SELECCIONAR"));
        dataSpinnerPla.add(new EntEstandar(1, "Combos"));
        dataSpinnerPla.add(new EntEstandar(2, "Simcard"));

        ArrayAdapter<EntEstandar> adapterEstados = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, dataSpinnerPla);
        spinner_tipo.setAdapter(adapterEstados);
        spinner_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idtipo = dataSpinnerPla.get(position).getId();
                if (idPlanificacion == 2 || idPlanificacion == 3) {
                    liner_tipo.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBuscarPlanificar:

                if (idPlanificacion == 0) {
                    TastyToast.makeText(getActivity(), "Por favor seleccione un parametro", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (liner_tipo.getVisibility() == View.VISIBLE && idtipo == 0) {
                    TastyToast.makeText(getActivity(), "Por favor selecciones un tipo", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else {
                    getPlanificar();
                }
                break;
        }
    }

    private void getPlanificar() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Alerta.");
        progressDialog.setMessage("Cargando Información");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "planifica_visita");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("tipo", String.valueOf(idPlanificacion));
                params.put("valor", String.valueOf(idtipo));
                params.put("perfil", String.valueOf(controllerLogin.getUserLogin().getPerfil()));

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());

                return params;
            }
        };

        addToQueue(jsonRequest);
    }

    private void parseJSON(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                ListResponsePlaniVisita responseMarcarPedido = gson.fromJson(response, ListResponsePlaniVisita.class);

                //Activity Detalle
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), ActPlanificarOrdenar.class);
                bundle.putSerializable("value", responseMarcarPedido);
                bundle.putInt("tipo", idPlanificacion);
                bundle.putInt("valor", idtipo);
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
            TastyToast.makeText(getActivity(), "No se encontraron datos", TastyToast.LENGTH_LONG, TastyToast.WARNING);
        }
    }
}
