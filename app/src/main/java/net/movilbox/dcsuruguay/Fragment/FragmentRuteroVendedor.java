package net.movilbox.dcsuruguay.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.movilbox.dcsuruguay.Adapter.RecyclerRutero;
import net.movilbox.dcsuruguay.Controller.ControllerPunto;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.R;

import java.util.List;

public class FragmentRuteroVendedor extends BaseVolleyFragment {

    private RecyclerView recycler;
    private ControllerPunto controllerPunto;

    public FragmentRuteroVendedor() {
        // Required empty public constructor
    }

    public static FragmentRuteroVendedor newInstance(int vendedorpara) {
        FragmentRuteroVendedor fragment = new FragmentRuteroVendedor();
        Bundle bundle = new Bundle();
        bundle.putInt("vendedor", vendedorpara);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_rutero_vendedor, container, false);

        recycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler.setHasFixedSize(true);

        RecyclerView.LayoutManager lManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(lManager);

        setHasOptionsMenu(false);

        controllerPunto = new ControllerPunto(getActivity());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadeRuteroDia(controllerPunto);

    }

    private void loadeRuteroDia(ControllerPunto controllerPunto) {

        final List<EntLisSincronizar> lisSincronizarList = controllerPunto.getRuteroVendedorUne(getArguments().getInt("vendedor"));

        RecyclerRutero adapter = new RecyclerRutero(getActivity(), lisSincronizarList);
        recycler.setAdapter(adapter);
    }

}
