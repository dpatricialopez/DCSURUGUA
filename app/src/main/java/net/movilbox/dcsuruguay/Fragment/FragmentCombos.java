package net.movilbox.dcsuruguay.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.google.gson.Gson;

import net.movilbox.dcsuruguay.Activity.ActCarritoPedido;
import net.movilbox.dcsuruguay.Activity.ActTomarPedido;
import net.movilbox.dcsuruguay.Activity.SpacesItemDecoration;
import net.movilbox.dcsuruguay.Adapter.AdapterRecyclerCombos;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerTomarPedido;
import net.movilbox.dcsuruguay.DataBase.DBHelper;
import net.movilbox.dcsuruguay.Model.ReferenciasCombos;
import net.movilbox.dcsuruguay.Model.ResponseVenta;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

@SuppressLint("ValidFragment")
public class FragmentCombos extends BaseVolleyFragment {

    private int mPosition;
    private int idZonaLocal;
    private RecyclerView.Adapter adapter2;
    private RecyclerView recycler2;
    private SpotsDialog alertDialog;
    private GridLayoutManager gridLayoutManagerVertical;
    private ResponseVenta responseVenta;
    private List<ReferenciasCombos> filterList;
    private ConnectionDetector connectionDetector;
    private ControllerTomarPedido mydb;
    private ControllerLogin controllerLogin;

    public FragmentCombos(int position, int idZona) {
        mPosition = position;
        idZonaLocal = idZona;
    }

    public FragmentCombos() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_combos, container, false);

        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);

        recycler2 = (RecyclerView) rootView.findViewById(R.id.recycler_view2);

        gridLayoutManagerVertical = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mydb = new ControllerTomarPedido(getActivity());

        connectionDetector = new ConnectionDetector(getActivity());

        setHasOptionsMenu(true);

        if (connectionDetector.isConnected()){
            getCombo();
        } else {
            getComboLocal();
        }
    }

    private void getComboLocal() {

        List<ReferenciasCombos> list = mydb.getProductosCombos(String.valueOf(idZonaLocal));

        adapter2 = new AdapterRecyclerCombos(getActivity(), list, mPosition, controllerLogin.getUserLogin().getId());
        recycler2.setAdapter(adapter2);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item2 = menu.add("Carrito");
        item2.setIcon(R.drawable.ic_shopping_cart_white_24dp); // sets icon
        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), ActCarritoPedido.class);
                bundle.putInt("id_punto", mPosition);
                bundle.putInt("id_usuario", controllerLogin.getUserLogin().getId());
                intent.putExtras(bundle);
                startActivity(intent);

                return true;
            }

        });

        // Implementing ActionBar Search inside a fragment
        MenuItem item = menu.add("Search");
        item.setIcon(R.drawable.ic_search_white_24dp); // sets icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView sv = new SearchView(((ActTomarPedido) getActivity()).getSupportActionBar().getThemedContext());
        int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv.findViewById(id);

        // implementing the listener
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //doSearch(s);
                return s.length() < 4;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText = newText.toLowerCase();

                filterList = getNewListFromFilter(newText);
                adapter2 = new AdapterRecyclerCombos(getActivity(), filterList, mPosition, controllerLogin.getUserLogin().getId());
                recycler2.setAdapter(adapter2);

                return true;
            }
        });

        item.setActionView(sv);
    }

    private List<ReferenciasCombos> getNewListFromFilter(CharSequence query) {

        query = query.toString().toLowerCase();

        List<ReferenciasCombos> filteredListCategoria = new ArrayList<>();

        for (int i = 0; i < responseVenta.getReferenciasCombosList().size(); i++) {
            if (responseVenta.getReferenciasCombosList().get(i).getDescripcion().toLowerCase().contains(query)) {
                filteredListCategoria.add(responseVenta.getReferenciasCombosList().get(i));
            }
        }

        return filteredListCategoria;
    }

    private void getCombo() {

        alertDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "cargar_toma_pedido_com");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        parseJSONCombo(response);
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

                        alertDialog.dismiss();

                        onConnectionFailed(error_string);
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
                params.put("idpos", String.valueOf(mPosition));

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void parseJSONCombo(String response) {

        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                responseVenta = gson.fromJson(response, ResponseVenta.class);

                adapter2 = new AdapterRecyclerCombos(getActivity(), responseVenta.getReferenciasCombosList(), mPosition, controllerLogin.getUserLogin().getId());
                recycler2.setAdapter(adapter2);
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

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        } else {
            alertDialog.dismiss();
        }

    }

    @Override
    public void onResume() {

        if (adapter2 != null)
            adapter2.notifyDataSetChanged();

        super.onResume();
    }

}