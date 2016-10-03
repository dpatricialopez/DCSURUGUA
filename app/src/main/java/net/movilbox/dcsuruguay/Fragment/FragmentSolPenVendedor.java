package net.movilbox.dcsuruguay.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Activity.ActDetalleSolicitudP;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Model.ListSolicitudVendedor;
import net.movilbox.dcsuruguay.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSolPenVendedor extends BaseVolleyFragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    public ProgressDialog progressDialog;
    private EditText edit_fecha_inicial;
    private EditText edit_fecha_final;
    private EditText edit_idpos;
    private boolean fecha_idicador;
    private Date dia_inicial;
    private Date dia_final;

    public FragmentSolPenVendedor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sol_pen_vendedor, container, false);

        edit_fecha_inicial = (EditText) view.findViewById(R.id.edit_fecha_ini);
        edit_fecha_final = (EditText) view.findViewById(R.id.edit_fecha_fin);
        edit_idpos = (EditText) view.findViewById(R.id.edit_idpos);

        setHasOptionsMenu(false);

        FloatingActionButton btnSolicitud = (FloatingActionButton) view.findViewById(R.id.btnSolicitud);
        btnSolicitud.setOnClickListener(this);

        controllerLogin = new ControllerLogin(getActivity());

        edit_fecha_inicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        FragmentSolPenVendedor.this,
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
                        FragmentSolPenVendedor.this,
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

    }

    private void getSolicitudVendedor() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Alerta.");
        progressDialog.setMessage("Cargando Información");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "consultar_reporte_aprobaciones");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        responseSolicitud(response);
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

                return params;

            }
        };
        addToQueue(jsonRequest);
    }

    private void responseSolicitud(String response) {

        progressDialog.dismiss();

        Gson gson = new Gson();
        ListSolicitudVendedor entRespuestaServices = gson.fromJson(response, ListSolicitudVendedor.class);

        if (entRespuestaServices.getEstado() == 0) {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(getActivity(), ActDetalleSolicitudP.class);
            bundle.putSerializable("value", entRespuestaServices);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (entRespuestaServices.getEstado() == -1) {
            TastyToast.makeText(getActivity(), entRespuestaServices.getMsg(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSolicitud:

                if (isValidNumber(edit_fecha_inicial.getText().toString())) {
                    TastyToast.makeText(getActivity(), "La fecha inicial es un campo requerido", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                } else if (isValidNumber(edit_fecha_final.getText().toString())) {
                    TastyToast.makeText(getActivity(), "La fecha final es un campo requerido", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                } else {
                    if (checkDate(dia_inicial, dia_final)) {
                        TastyToast.makeText(getActivity(), "La fecha no puede superar más de 5 días de rango", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    } else {
                        getSolicitudVendedor();
                    }
                }
                break;
        }
    }

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

}
