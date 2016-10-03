package net.movilbox.dcsuruguay.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.movilbox.dcsuruguay.Activity.SpacesItemDecoration;
import net.movilbox.dcsuruguay.Adapter.AdapterRecyclerCatalogoDirecta;
import net.movilbox.dcsuruguay.Controller.ControllerInventario;
import net.movilbox.dcsuruguay.Model.EntCatalogo;
import net.movilbox.dcsuruguay.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProducto extends BaseVolleyFragment {

    private RecyclerView recycler2;
    private GridLayoutManager gridLayoutManagerVertical;

    public FragmentProducto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_producto, container, false);

        recycler2 = (RecyclerView) view.findViewById(R.id.recycler_catalogo);
        gridLayoutManagerVertical = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);

        controllerInventario = new ControllerInventario(getActivity());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getComboLocal();
    }

    private void getComboLocal() {

        List<EntCatalogo> list = controllerInventario.listCatalogo();

        AdapterRecyclerCatalogoDirecta adapter = new AdapterRecyclerCatalogoDirecta(getActivity(), list);
        recycler2.setAdapter(adapter);
        recycler2.setLayoutManager(gridLayoutManagerVertical);
        int dips = 0;
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        switch(metrics.densityDpi) {
            case DisplayMetrics.DENSITY_XHIGH:
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                dips = 20;
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                break;
            case DisplayMetrics.DENSITY_HIGH: //HDPI
                dips = 6;
                break;
            case DisplayMetrics.DENSITY_MEDIUM: //MDPI
                break;
            case DisplayMetrics.DENSITY_LOW:  //LDPI
                break;
        }

        recycler2.addItemDecoration(new SpacesItemDecoration(dips));

    }
}
