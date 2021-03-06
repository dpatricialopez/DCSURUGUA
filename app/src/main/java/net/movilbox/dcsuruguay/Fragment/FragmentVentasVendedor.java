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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Activity.ActReporteMisVentas;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerTerritorio;
import net.movilbox.dcsuruguay.Controller.ControllerZona;
import net.movilbox.dcsuruguay.Model.EntEstandar;
import net.movilbox.dcsuruguay.Model.EntMisVentasList;
import net.movilbox.dcsuruguay.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVentasVendedor extends BaseVolleyFragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private Spinner spinner_circuito;
    private Spinner spinner_ruta;

    private EditText edit_fecha_inicial;
    private EditText edit_fecha_final;
    private EditText edit_idpos;
    private boolean fecha_idicador;
    private Date dia_inicial;
    private Date dia_final;
    public ProgressDialog progressDialog;
    private int circuito;
    private int ruta;

    public FragmentVentasVendedor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_ventas_vendedor, container, false);

        spinner_circuito = (Spinner) view.findViewById(R.id.spinner_circuito);
        spinner_ruta = (Spinner) view.findViewById(R.id.spinner_ruta);

        setHasOptionsMenu(false);

        edit_fecha_inicial = (EditText) view.findViewById(R.id.edit_fecha_ini);
        edit_fecha_final = (EditText) view.findViewById(R.id.edit_fecha_fin);
        edit_idpos = (EditText) view.findViewById(R.id.edit_idpos);

        FloatingActionButton mis_ventas = (FloatingActionButton) view.findViewById(R.id.mis_ventas);
        mis_ventas.setOnClickListener(this);


        controllerTerritorio = new ControllerTerritorio(getActivity());
        controllerZona = new ControllerZona(getActivity());
        controllerLogin = new ControllerLogin(getActivity());

        edit_fecha_inicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        FragmentVentasVendedor.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                //Inicial
                fecha_idicador = true;

                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        edit_fecha_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        FragmentVentasVendedor.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                fecha_idicador = false;

                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");

            }
        });

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        llenarCircuitoTerritorio(controllerTerritorio.getCircuito());

    }

    //region Llenar Spinner de Circuitos / Territorios.
    private void llenarCircuitoTerritorio(final List<EntEstandar> circuitoZona) {

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, circuitoZona);
        spinner_circuito.setAdapter(prec3);
        spinner_circuito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                circuito = circuitoZona.get(position).getId();
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
                ruta = entEstandars.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

    }
    //endregion


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        monthOfYear = (monthOfYear + 1);

        if (fecha_idicador) {
            edit_fecha_inicial.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
            dia_inicial = converFecha(year, monthOfYear, dayOfMonth);
        } else {
            edit_fecha_final.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
            dia_final = converFecha(year, monthOfYear, dayOfMonth);
        }
    }

    public Date converFecha(int year, int mes, int dia) {

        Calendar calendar = new GregorianCalendar(year, mes, dia);
        Date fecha = new Date(calendar.getTimeInMillis());

        return fecha;
    }

    public boolean checkDate(Date startDate, Date endDate) {

        long milisegundos = 24 * 60 * 60 * 1000;

        long sum = (endDate.getTime() - startDate.getTime()) / milisegundos;

        if (sum > 5) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mis_ventas:

                if (isValidNumber(edit_fecha_inicial.getText().toString())) {
                    Toast.makeText(getActivity(), "La fecha inicial es un campo requerido", Toast.LENGTH_LONG).show();
                } else if (isValidNumber(edit_fecha_final.getText().toString())) {
                    Toast.makeText(getActivity(), "La fecha final es un campo requerido", Toast.LENGTH_LONG).show();
                } else {
                    if (checkDate(dia_inicial, dia_final)) {
                        Toast.makeText(getActivity(), "La fecha no puede superar más de 5 días de rango", Toast.LENGTH_LONG).show();
                    } else {
                        consultarReporte();
                    }
                }
                break;
        }
    }

    private void consultarReporte() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Alerta.");
        progressDialog.setMessage("Cargando Información");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "reporte_ventas_vendedor");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        JSONresponce(response);
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

                params.put("fecha_ini", edit_fecha_inicial.getText().toString().trim());
                params.put("fecha_fin", edit_fecha_final.getText().toString().trim());
                params.put("idpos", edit_idpos.getText().toString().trim());

                params.put("circuito", String.valueOf(circuito));
                params.put("ruta", String.valueOf(ruta));

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void JSONresponce(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                EntMisVentasList responseMisPedidos = gson.fromJson(response, EntMisVentasList.class);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), ActReporteMisVentas.class);
                bundle.putSerializable("value", responseMisPedidos);
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
            TastyToast.makeText(getActivity(), "No se encontraron datos para mostrar", TastyToast.LENGTH_LONG, TastyToast.WARNING);
        }


    }
}
