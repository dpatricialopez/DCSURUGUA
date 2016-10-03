package net.movilbox.dcsuruguay.Fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import net.movilbox.dcsuruguay.Adapter.AdapterInventario;
import net.movilbox.dcsuruguay.Adapter.ExpandableListAdapter;
import net.movilbox.dcsuruguay.Adapter.ExpandableListDataPump;
import net.movilbox.dcsuruguay.Controller.ControllerInventario;
import net.movilbox.dcsuruguay.Model.EntEstandar;
import net.movilbox.dcsuruguay.Model.EntListReferencias;
import net.movilbox.dcsuruguay.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInventarioVende extends BaseVolleyFragment {

    private ListView listViewInventario;

    public FragmentInventarioVende() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        controllerInventario = new ControllerInventario(getActivity());
        View view = inflater.inflate(R.layout.fragment_inventario_vende, container, false);

        listViewInventario = (ListView) view.findViewById(R.id.listViewInventario);

        setHasOptionsMenu(false);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final List<EntListReferencias> listReferenciases = controllerInventario.listReferenciasesReport(1);

        AdapterInventario adapterCarrito = new AdapterInventario(getActivity(), listReferenciases);
        listViewInventario.setAdapter(adapterCarrito);
        listViewInventario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                LayoutInflater inflater = getActivity().getLayoutInflater();

                View dialoglayout = inflater.inflate(R.layout.dialog_inventario, null);

                ExpandableListView expandableListView = (ExpandableListView) dialoglayout.findViewById(R.id.expandableListView);

                //Recuperar los paquetes con sus respectivos seriales.
                List<EntListReferencias> entListReferenciases = controllerInventario.listPaqueteInvent(listReferenciases.get(position).getIdRefe());

                HashMap<String, List<EntEstandar>> expandableListDetail = ExpandableListDataPump.getData(entListReferenciases);
                ArrayList<String> expandableListTitle = new ArrayList<>(expandableListDetail.keySet());

                ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("Detalle Referencia");
                builder.setView(dialoglayout).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });

    }

}
