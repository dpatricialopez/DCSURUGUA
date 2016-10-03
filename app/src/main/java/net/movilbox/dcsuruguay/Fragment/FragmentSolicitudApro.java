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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Activity.ActDetalleAproPunto;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Model.EntEstandar;
import net.movilbox.dcsuruguay.Model.ListAprobarPunto;
import net.movilbox.dcsuruguay.Model.ListEstandar;
import net.movilbox.dcsuruguay.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSolicitudApro extends BaseVolleyFragment implements DatePickerDialog.OnDateSetListener {

    private boolean fecha_idicador;
    private Date dia_inicial;
    private Date dia_final;
    private EditText edit_fecha_inicial;
    private EditText edit_fecha_final;
    private EditText edit_idpos;
    private Spinner spinner_vendedor;
    private Spinner spinner_solicitud;
    private int estado;
    private int estado_vendedor;
    private ProgressDialog progressDialog;

    public FragmentSolicitudApro() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_solicitud_apro, container, false);

        controllerLogin = new ControllerLogin(getActivity());

        spinner_vendedor = (Spinner) view.findViewById(R.id.spinner_vendedor);
        spinner_solicitud = (Spinner) view.findViewById(R.id.spinner_solicitud);
        edit_fecha_inicial = (EditText) view.findViewById(R.id.edit_fecha_ini);
        edit_fecha_final = (EditText) view.findViewById(R.id.edit_fecha_fin);
        edit_idpos = (EditText) view.findViewById(R.id.edit_idpos);

        FloatingActionButton btn_buscar = (FloatingActionButton) view.findViewById(R.id.aprobacion_punto);
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValidNumber(edit_fecha_inicial.getText().toString())) {
                    Toast.makeText(getActivity(), "La fecha inicial es un campo requerido", Toast.LENGTH_LONG).show();
                } else if (isValidNumber(edit_fecha_final.getText().toString())) {
                    Toast.makeText(getActivity(), "La fecha final es un campo requerido", Toast.LENGTH_LONG).show();
                } else {
                    if (checkDate(dia_inicial, dia_final)) {
                        Toast.makeText(getActivity(), "La fecha no puede superar más de 5 días de rango", Toast.LENGTH_LONG).show();
                    } else {
                        consultarSolicitud();
                    }
                }
            }
        });


        edit_fecha_inicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        FragmentSolicitudApro.this,
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
                        FragmentSolicitudApro.this,
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

        loadVendedor();

        loadSolicitud();

    }

    private void loadVendedor() {
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "cargar_vendedores_aprobacion");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        mostrarReporte(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        String error_string = "";

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            error_string = "Error de tiempo de espera";
                        } else if (error instanceof AuthFailureError) {
                            error_string = "Error Servidor";
                        } else if (error instanceof ServerError) {
                            error_string = "Server Error";
                        } else if (error instanceof NetworkError) {
                            error_string = "Error de red";
                        } else if (error instanceof ParseError) {
                            error_string = "Error al serializar los datos";
                        }

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());

                return params;

            }
        };
        addToQueue(jsonRequest);
    }

    private void mostrarReporte(String response) {
        Gson gson = new Gson();

        final ListEstandar listCategoria = gson.fromJson(response, ListEstandar.class);

        ArrayAdapter<EntEstandar> adapterEstado = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, listCategoria);
        spinner_vendedor.setAdapter(adapterEstado);
        spinner_vendedor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estado_vendedor = listCategoria.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void consultarSolicitud() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Alerta.");
        progressDialog.setMessage("Cargando Información");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "cargar_peticiones_aprobacion");
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
                params.put("perfil", String.valueOf(controllerLogin.getUserLogin().getPerfil()));

                params.put("fecha_ini", edit_fecha_inicial.getText().toString());
                params.put("fecha_fin", edit_fecha_final.getText().toString());
                params.put("idpos", edit_idpos.getText().toString().trim());
                params.put("solicitud", String.valueOf(estado));
                params.put("vendedor", String.valueOf(estado_vendedor));

                return params;

            }
        };

        addToQueue(jsonRequest);
    }

    private void responseAprobacion(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                ListAprobarPunto listAprobarPunto = gson.fromJson(response, ListAprobarPunto.class);

                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), ActDetalleAproPunto.class);
                bundle.putSerializable("value", listAprobarPunto);
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

    private void loadSolicitud() {

        final List<EntEstandar> listaEstados = new ArrayList<>();
        listaEstados.add(new EntEstandar(0, "Seleccionar"));
        listaEstados.add(new EntEstandar(1, "Creación"));
        listaEstados.add(new EntEstandar(2, "Modificación"));

        ArrayAdapter<EntEstandar> adapterEstado = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, listaEstados);
        spinner_solicitud.setAdapter(adapterEstado);
        spinner_solicitud.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estado = listaEstados.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

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
