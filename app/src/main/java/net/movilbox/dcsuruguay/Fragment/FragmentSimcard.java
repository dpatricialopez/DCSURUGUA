package net.movilbox.dcsuruguay.Fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Controller.ControllerCarrito;
import net.movilbox.dcsuruguay.Controller.ControllerInventario;
import net.movilbox.dcsuruguay.Model.EntCarritoVenta;
import net.movilbox.dcsuruguay.Model.EntListReferencias;
import net.movilbox.dcsuruguay.Model.EntRefeSerial;
import net.movilbox.dcsuruguay.Model.Motivos;
import net.movilbox.dcsuruguay.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSimcard extends BaseVolleyFragment implements View.OnClickListener {

    private Spinner spinner_paquete;
    private Spinner spinner_referencia;
    private Spinner spinner_serial;
    private int idpaquete;
    private int idReferencia;
    private String idSerie;
    private int tipoProducto;

    public FragmentSimcard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_simcard, container, false);

        spinner_paquete = (Spinner) view.findViewById(R.id.spinner_paquete);
        spinner_referencia = (Spinner) view.findViewById(R.id.spinner_referencia);
        spinner_serial = (Spinner) view.findViewById(R.id.spinner_serial);
        FloatingActionButton btnVentaClienteSim = (FloatingActionButton) view.findViewById(R.id.btnVentaClienteSim);
        btnVentaClienteSim.setOnClickListener(this);

        controllerInventario = new ControllerInventario(getActivity());
        controllerCarrito = new ControllerCarrito(getActivity());

        return view;

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cargarPaqueteDesmas(controllerInventario.listPaquetesDirecta(-1));

    }



    private void cargarPaqueteDesmas(final List<Motivos> motivos) {

        ArrayAdapter<Motivos> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, motivos);
        spinner_paquete.setAdapter(prec3);
        spinner_paquete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idpaquete = motivos.get(position).getId();
                cargarReferencias(motivos.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void cargarReferencias(int id) {

        final List<EntListReferencias> listReferenciases = controllerInventario.listReferencias(id);

        ArrayAdapter<EntListReferencias> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, listReferenciases);
        spinner_referencia.setAdapter(prec3);
        spinner_referencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idReferencia = listReferenciases.get(position).getIdRefe();
                tipoProducto = listReferenciases.get(position).getTipoPro();
                cargarReferenciasSerial(listReferenciases.get(position).getIdPaquete(), listReferenciases.get(position).getIdRefe(), listReferenciases.get(position).getTipoPro());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

    }

    private void cargarReferenciasSerial(int idPaquete, int idRefe, int tipoPro) {

        final List<EntRefeSerial> entRefeSerialList = controllerInventario.listReferenciasSerial(idPaquete, idRefe, tipoPro, 1);

        ArrayAdapter<EntRefeSerial> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, entRefeSerialList);
        spinner_serial.setAdapter(prec3);
        spinner_serial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idSerie = entRefeSerialList.get(position).getSerie();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVentaClienteSim:

                if (idpaquete < 0) {
                    spinner_paquete.requestFocus();
                    spinner_paquete.performClick();
                    TastyToast.makeText(getActivity(), "Seleccione un paquete", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (idReferencia <= 0) {
                    spinner_referencia.requestFocus();
                    spinner_referencia.performClick();
                    TastyToast.makeText(getActivity(), "Seleccione una referencia", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (idSerie.equals("SELECCIONAR") || idSerie.equals("")) {
                    spinner_serial.requestFocus();
                    spinner_serial.performClick();
                    TastyToast.makeText(getActivity(), "Seleccione un serial", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (validateCarritoProducto(idReferencia, idSerie, 1)) {
                    //Guaradar el producto en el carrito
                    EntCarritoVenta entCarritoVenta = new EntCarritoVenta();

                    entCarritoVenta.setIdReferencia(idReferencia);
                    entCarritoVenta.setTipoProducto(tipoProducto);
                    entCarritoVenta.setValorDirecto(controllerInventario.getValorReferencia(idReferencia, 1));
                    entCarritoVenta.setSerie(idSerie);
                    entCarritoVenta.setIdPunto(1);
                    entCarritoVenta.setIdPaquete(idpaquete);

                    //1 si es venrta directa.
                    //2 si es venta a puntod de venta.

                    entCarritoVenta.setTipoVenta(1);

                    if (controllerCarrito.guardarVentaCarrito(entCarritoVenta)) {
                        TastyToast.makeText(getActivity(), "Producto guardado en el carrito", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    } else {
                        TastyToast.makeText(getActivity(), "Problemas al guardar el producto", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }

                } else {
                    TastyToast.makeText(getActivity(), "El producto ya esta en el carrito", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }

                break;

        }
    }

    private boolean validateCarritoProducto(int idReferencia, String idSerie, int i) {
        return controllerCarrito.validarCarrito(idReferencia, idSerie, i);
    }

}
