package net.movilbox.dcsuruguay.Fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Model.EntEstandar;
import net.movilbox.dcsuruguay.Model.ListEstandar;
import net.movilbox.dcsuruguay.R;

import java.util.HashMap;
import java.util.Map;

public class FragmentInicioSupervisor extends BaseVolleyFragment {

    private Spinner spinner_vendedor;
    private int vendedor;
    private FragmentInicioVendedor fragmentInicioVendedor;

    public FragmentInicioSupervisor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_inicio_supervisor, container, false);

        controllerLogin = new ControllerLogin(getActivity());

        spinner_vendedor = (Spinner) view.findViewById(R.id.spinner_vendedor);
        llenarSpinnerVendedor();

        FloatingActionButton btn_buscar = (FloatingActionButton) view.findViewById(R.id.buscar_vendedor);
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vendedor != 0) {
                    FragmentManager fManager = getFragmentManager();
                    fragmentInicioVendedor = new FragmentInicioVendedor();
                    Bundle args = new Bundle();
                    args.putInt("id_vendedor", vendedor);
                    fragmentInicioVendedor.setArguments(args);
                    fManager.beginTransaction().replace(R.id.contentPanel, fragmentInicioVendedor).commit();
                } else {
                    Toast.makeText(getActivity(),"Por favor selecciona un vendedor", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;

    }

    private void llenarSpinnerVendedor() {
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "cargar_vendedores_supervisor");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        cargarVendedores(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        TastyToast.makeText(getActivity(), "Problemas con el internet", TastyToast.LENGTH_LONG, TastyToast.WARNING);
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

    private void cargarVendedores(String response) {
        Gson gson = new Gson();

        final ListEstandar listCategoria = gson.fromJson(response, ListEstandar.class);

        ArrayAdapter<EntEstandar> adapterEstado = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, listCategoria);

        spinner_vendedor.setAdapter(adapterEstado);
        spinner_vendedor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vendedor = listCategoria.get(position).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

    }

}
