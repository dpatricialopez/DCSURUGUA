package net.movilbox.dcsuruguay.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Activity.ActBusquedaAvPunto;
import net.movilbox.dcsuruguay.Activity.ActMarcarVisita;
import net.movilbox.dcsuruguay.Activity.ActResponAvanBusqueda;
import net.movilbox.dcsuruguay.Controller.ControllerPunto;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;

public class FragmentVisitarPunto extends BaseVolleyFragment implements View.OnClickListener {

    private EditText edit_buscar;
    private ConnectionDetector connectionDetector;

    public FragmentVisitarPunto() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_visitar_punto, container, false);
        Button btnBuscar = (Button) view.findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(this);
        Button btnAvBus = (Button) view.findViewById(R.id.btnAvBus);
        btnAvBus.setOnClickListener(this);

        edit_buscar = (EditText) view.findViewById(R.id.edit_buscar);

        controllerPunto = new ControllerPunto(getActivity());

        setHasOptionsMenu(false);
        connectionDetector = new ConnectionDetector(getActivity());

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBuscar:
                if (isValidNumber(edit_buscar.getText().toString().trim())){
                    edit_buscar.setError("Campo requerido");
                    edit_buscar.requestFocus();
                } else {
                    //Busqueda punto base de datos interna...
                    EntLisSincronizar entLisSincronizar = controllerPunto.getRuteroVendedor(Integer.parseInt(edit_buscar.getText().toString()));
                    if (entLisSincronizar != null) {

                        Bundle bundle = new Bundle();
                        Intent intent = new Intent(getActivity(), ActMarcarVisita.class);
                        bundle.putSerializable("value", entLisSincronizar);
                        bundle.putString("page", "marcar_rutero");
                        intent.putExtras(bundle);
                        startActivity(intent);

                    } else {
                        TastyToast.makeText(getActivity(), "No se encontraron resultados", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }
                }
                break;

            case R.id.btnAvBus:
                if (connectionDetector.isConnected()) {
                    startActivity(new Intent(getActivity(), ActBusquedaAvPunto.class));
                    getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    TastyToast.makeText(getActivity(), "El modulo solo funciona en modo ONLINE", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }

                break;
        }
    }

}
