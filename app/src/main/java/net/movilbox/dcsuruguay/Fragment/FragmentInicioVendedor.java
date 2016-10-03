package net.movilbox.dcsuruguay.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Adapter.TabsAdapter;
import net.movilbox.dcsuruguay.Controller.ControllerIndicadores;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerPunto;
import net.movilbox.dcsuruguay.Controller.FuncionesGenerales;
import net.movilbox.dcsuruguay.Model.EntCarritoVenta;
import net.movilbox.dcsuruguay.Model.EntIndicadores;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.Model.EntListResposeCompra;
import net.movilbox.dcsuruguay.Model.ListCarritoCompra;
import net.movilbox.dcsuruguay.Model.ListNoVenta;
import net.movilbox.dcsuruguay.Model.ListResponseCompra;
import net.movilbox.dcsuruguay.Model.NoVisita;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentInicioVendedor extends BaseVolleyFragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabsAdapter tabsAdapter;
    public ProgressDialog progressDialog;
    private ControllerLogin controllerLogin;
    private int vendedor = 0;
    private ConnectionDetector connectionDetector;

    public FragmentInicioVendedor() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inicio_vendedor, container, false);

        controllerLogin = new ControllerLogin(getActivity());

        funcionesGenerales = new FuncionesGenerales(getActivity());

        controllerPunto = new ControllerPunto(getActivity());

        controllerIndicadores = new ControllerIndicadores(getActivity());

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);

        tabsAdapter = new TabsAdapter(getChildFragmentManager());

        setHasOptionsMenu(true);

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item2 = menu.add("Carrito");
        item2.setIcon(R.drawable.ic_cloud_upload_white_24dp); // sets icon
        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Sincronizar...
                if (connectionDetector.isConnected()) {
                    List<EntCarritoVenta> entCarritoVentaList = controllerPunto.sincronizarVentas();
                    if (entCarritoVentaList.size() > 0) {
                        sincronizarVentas();
                    } else {
                        List<EntLisSincronizar> punto = controllerPunto.sincronizarPuntos(controllerLogin.getUserLogin().getId());
                        if (punto.size() > 0) {
                            enviarPuntosLocales(punto);
                        } else {
                            List<NoVisita> noVisita = funcionesGenerales.getNoVenta();
                            if (noVisita.size() > 0) {
                                enviarNoVenta(noVisita);
                            } else {
                                TastyToast.makeText(getActivity(), "No tiene información para sincronizar", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                            }
                        }
                    }
                } else {
                    TastyToast.makeText(getActivity(), "No tiene acceso a internet para sincronizar", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }

                return true;
            }

        });
    }

    private void enviarNoVenta(final List<NoVisita> noVisita) {

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "guardar_no_venta_offline");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        parseJSONSincroNoVenta(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        TastyToast.makeText(getActivity(), "Problemas al sincronizar", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                String parJSON = new Gson().toJson(noVisita, ListNoVenta.class);

                params.put("datos", parJSON);

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void parseJSONSincroNoVenta(String response) {

        Gson gson = new Gson();

        ListResponseCompra listResposeCompras = gson.fromJson(response, ListResponseCompra.class);

        boolean banderaError = false;

        for (int i = 0; i < listResposeCompras.size(); i++) {

            if (listResposeCompras.get(i).getAccion() == -1) {
                // Borrar de la base de datos local
                TastyToast.makeText(getActivity(), listResposeCompras.get(i).getMsg(), TastyToast.LENGTH_LONG, TastyToast.ERROR);

            } else if (listResposeCompras.get(i).getAccion() == 0) {
                banderaError = true;
            }

        }

        funcionesGenerales.deleteNoVenta();

        if (banderaError)
            TastyToast.makeText(getActivity(), "Las no ventas se guardaron", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
    }

    private void sincronizarVentas() {

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "save_pedido_cli_final");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        parseJSONSincroVenta(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        TastyToast.makeText(getActivity(), "Problemas al sincronizar", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                List<EntCarritoVenta> entCarritoVentaList = controllerPunto.sincronizarVentas();

                String parJSON = new Gson().toJson(entCarritoVentaList, ListCarritoCompra.class);

                params.put("datos", parJSON);

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());

                params.put("indicador", "offline");

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void parseJSONSincroVenta(String response) {

        Gson gson = new Gson();

        ListResponseCompra listResposeCompras = gson.fromJson(response, ListResponseCompra.class);

        boolean banderaError = false;

        for (int i = 0; i < listResposeCompras.size(); i++) {

            if (listResposeCompras.get(i).getAccion() == -1) {
                // Borrar de la base de datos local
                controllerPunto.deleteOneVentaOff(listResposeCompras.get(i).getSerie());
                TastyToast.makeText(getActivity(), listResposeCompras.get(i).getMsg(), TastyToast.LENGTH_LONG, TastyToast.ERROR);

            } else if (listResposeCompras.get(i).getAccion() == -2) {
                TastyToast.makeText(getActivity(), listResposeCompras.get(i).getMsg(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
            } else if (listResposeCompras.get(i).getAccion() == 0) {
                // Borrar de la base de datos local
                controllerPunto.deleteOneVentaOff(listResposeCompras.get(i).getSerie());
                banderaError = true;
            }

        }

        if (banderaError)
            TastyToast.makeText(getActivity(), "La venta se realizo exitosamente", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);


        List<EntLisSincronizar> punto = controllerPunto.sincronizarPuntos(controllerLogin.getUserLogin().getId());
        if (punto.size() > 0) {
            enviarPuntosLocales(punto);
        } else {
            List<NoVisita> noVisita = funcionesGenerales.getNoVenta();
            if (noVisita.size() > 0)
                enviarNoVenta(noVisita);
        }

    }

    private void enviarPuntosLocales(final List<EntLisSincronizar> punto) {

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "guardar_punto_offline");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        parseJSONSincroPunto(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        TastyToast.makeText(getActivity(), "Problemas al sincronizar", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                String parJSON = new Gson().toJson(punto, ListCarritoCompra.class);

                params.put("datos", parJSON);

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());
                params.put("accion", "Guardar");

                return params;

            }
        };
        addToQueue(jsonRequest);

    }

    private void parseJSONSincroPunto(String response) {
        Gson gson = new Gson();

        ListResponseCompra listResposeCompras = gson.fromJson(response, ListResponseCompra.class);

        boolean banderaError = false;

        for (int i = 0; i < listResposeCompras.size(); i++) {

            if (listResposeCompras.get(i).getAccion() == -1) {
                // Borrar de la base de datos local
                TastyToast.makeText(getActivity(), listResposeCompras.get(i).getMsg(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
            } else if (listResposeCompras.get(i).getAccion() == 0) {
                controllerPunto.deletePuunto(controllerLogin.getUserLogin().getId());
                banderaError = true;
            }

        }

        List<NoVisita> noVisita = funcionesGenerales.getNoVenta();
        if (noVisita.size() > 0)
            enviarNoVenta(noVisita);

        if (banderaError)
            TastyToast.makeText(getActivity(), "Se crearon los puntos", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        connectionDetector = new ConnectionDetector(getActivity());

        if (getArguments() != null) {
            vendedor = getArguments().getInt("id_vendedor");
        }
        if (connectionDetector.isConnected()) {
            indicadorVendedor();
        } else {

            tabsAdapter = new TabsAdapter(getChildFragmentManager());

            tabsAdapter.addFragment(new FragmentDasboardVendedor(), "Dashboard");
            tabsAdapter.addFragment(new FragmentRuteroVendedor().newInstance(vendedor), "Rutero");

            viewPager.setAdapter(tabsAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }

    }

    private void indicadorVendedor() {

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "indicadores_vendedor");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        parseJSONVendedor(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //progressDialog.dismiss();

                        tabsAdapter = new TabsAdapter(getChildFragmentManager());

                        tabsAdapter.addFragment(new FragmentDasboardVendedor(), "Dashboard");
                        tabsAdapter.addFragment(new FragmentRuteroVendedor().newInstance(vendedor), "Rutero");

                        viewPager.setAdapter(tabsAdapter);
                        tabLayout.setupWithViewPager(viewPager);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                if (vendedor != 0) {
                    params.put("iduser", String.valueOf(vendedor));
                } else {
                    params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                }

                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());
                params.put("perfil", String.valueOf(controllerLogin.getUserLogin().getPerfil()));

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void parseJSONVendedor(String response) {

        //progressDialog.dismiss();

        Gson gson = new Gson();

        final EntIndicadores entIndicadores = gson.fromJson(response, EntIndicadores.class);

        funcionesGenerales.deleteObject("indicadoresdas");
        funcionesGenerales.deleteObject("indicadoresdas_detalle");

        controllerIndicadores.insertIndicadores(entIndicadores);
        controllerIndicadores.insertDetalleIndicadores(entIndicadores);

        tabsAdapter = new TabsAdapter(getChildFragmentManager());

        tabsAdapter.addFragment(new FragmentDasboardVendedor(), "Dashboard");
        tabsAdapter.addFragment(new FragmentRuteroVendedor().newInstance(vendedor), "Rutero");

        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

}
