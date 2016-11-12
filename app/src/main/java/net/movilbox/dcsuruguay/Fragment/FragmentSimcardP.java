package net.movilbox.dcsuruguay.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import net.movilbox.dcsuruguay.Adapter.AdapterRecyclerSimcard;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerTomarPedido;
import net.movilbox.dcsuruguay.DataBase.DBHelper;
import net.movilbox.dcsuruguay.Model.ReferenciasSims;
import net.movilbox.dcsuruguay.Model.ResponseVenta;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static net.movilbox.dcsuruguay.Model.ResponseVenta.setId_posStacti;

@SuppressLint("ValidFragment")
public class FragmentSimcardP extends BaseVolleyFragment {

    private int mPosition;
    private int idZonaLocal;
    private SpotsDialog alertDialog;
    private RecyclerView.Adapter adapter;
    private RecyclerView recycler;
    private List<ReferenciasSims> filterList;
    private ResponseVenta responseVenta;
    private GridLayoutManager gridLayoutManagerVertical;
    private ControllerTomarPedido mydb;
    private ControllerLogin controllerLogin;

    public FragmentSimcardP(int position, int idZona) {
        mPosition = position;
        idZonaLocal = idZona;
    }

    public FragmentSimcardP() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_simcard_ped, container, false);

        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);

        recycler = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recycler.setHasFixedSize(true);

        RecyclerView.LayoutManager lManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(lManager);
        gridLayoutManagerVertical = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mydb = new ControllerTomarPedido(getActivity());
        ConnectionDetector connectionDetector = new ConnectionDetector(getActivity());

        setHasOptionsMenu(true);

        if (connectionDetector.isConnected()){
            getSimcard();
        } else {
            getSimcardLocal();
        }


    }



    private void getSimcardLocal() {

        List<ReferenciasSims> referenciasSimsList = mydb.getSimcardLocal(String.valueOf(idZonaLocal));

        setId_posStacti(mPosition);
        adapter = new AdapterRecyclerSimcard(getActivity(), referenciasSimsList, mPosition, controllerLogin.getUserLogin().getId());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(gridLayoutManagerVertical);
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

        recycler.addItemDecoration(new SpacesItemDecoration(dips));

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
                adapter = new AdapterRecyclerSimcard(getActivity(), filterList, mPosition, controllerLogin.getUserLogin().getId());
                recycler.setAdapter(adapter);

                return true;
            }
        });

        item.setActionView(sv);

    }

    private List<ReferenciasSims> getNewListFromFilter(CharSequence query) {

        query = query.toString().toLowerCase();

        List<ReferenciasSims> filteredListCategoria = new ArrayList<>();

        for (int i = 0; i < responseVenta.getReferenciasSimsList().size(); i++) {
            if (responseVenta.getReferenciasSimsList().get(i).getProducto().toLowerCase().contains(query)) {
                filteredListCategoria.add(responseVenta.getReferenciasSimsList().get(i));
            }
        }
        return filteredListCategoria;
    }

    private void getSimcard() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "cargar_toma_pedido_sim");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        parseJSONVisita(response);
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

    private void parseJSONVisita(String response) {

        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                responseVenta = gson.fromJson(response, ResponseVenta.class);

                setId_posStacti(mPosition);
                adapter = new AdapterRecyclerSimcard(getActivity(), responseVenta.getReferenciasSimsList(), mPosition, controllerLogin.getUserLogin().getId());
                recycler.setAdapter(adapter);
                recycler.setLayoutManager(gridLayoutManagerVertical);

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

                recycler.addItemDecoration(new SpacesItemDecoration(dips));

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

        if (adapter != null)
            adapter.notifyDataSetChanged();

        super.onResume();
    }

}