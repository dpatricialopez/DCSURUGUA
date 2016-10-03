package net.movilbox.dcsuruguay.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Activity.ActMenu;
import net.movilbox.dcsuruguay.Adapter.DialogEmail;
import net.movilbox.dcsuruguay.Adapter.ExpandableListAdapterSol;
import net.movilbox.dcsuruguay.Adapter.ExpandableListDataPumpSol;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Model.EntReferenciaSol;
import net.movilbox.dcsuruguay.Model.EntRespuestaServices;
import net.movilbox.dcsuruguay.Model.LisSolicitarProduct;
import net.movilbox.dcsuruguay.Model.ListEntReferenciaSol;
import net.movilbox.dcsuruguay.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSolProducto extends BaseVolleyFragment {

    public ProgressDialog progressDialog;
    private ExpandableListView expandable_sol;
    protected DialogEmail dialog;
    private EditText editSol;
    private HashMap<String, List<EntReferenciaSol>> expandableListDetail;
    private ListEntReferenciaSol listEntReferenciaSol;

    public FragmentSolProducto() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sol_producto, container, false);

        expandable_sol = (ExpandableListView) view.findViewById(R.id.expandable_sol);

        controllerLogin = new ControllerLogin(getActivity());

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item2 = menu.add("Guardar");
        item2.setIcon(R.drawable.ic_save_white_24dp); // sets icon
        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Guardar Aceptacion de pedidos validaciones
                List<EntReferenciaSol> referenciaSolList = new ArrayList<>();

                for (int i = 0; i < listEntReferenciaSol.getEntSolPedidos().size(); i++) {
                    for (int t = 0; t < listEntReferenciaSol.getEntSolPedidos().get(i).getEntReferenciaSols().size(); t++) {
                        if (listEntReferenciaSol.getEntSolPedidos().get(i).getEntReferenciaSols().get(t).getCantidadSol() > 0) {
                            EntReferenciaSol entReferenciaSol = new EntReferenciaSol();

                            entReferenciaSol.setId_referencia(listEntReferenciaSol.getEntSolPedidos().get(i).getEntReferenciaSols().get(t).getId_referencia());
                            entReferenciaSol.setCantidadSol(listEntReferenciaSol.getEntSolPedidos().get(i).getEntReferenciaSols().get(t).getCantidadSol());
                            entReferenciaSol.setId_bodega(listEntReferenciaSol.getEntSolPedidos().get(i).getEntReferenciaSols().get(t).getId_bodega());
                            entReferenciaSol.setTipo_bodega(listEntReferenciaSol.getEntSolPedidos().get(i).getEntReferenciaSols().get(t).getTipo_bodega());
                            entReferenciaSol.setTipo_ref(listEntReferenciaSol.getEntSolPedidos().get(i).getEntReferenciaSols().get(t).getTipo_ref());

                            referenciaSolList.add(entReferenciaSol);
                        }
                    }
                }

                if (referenciaSolList.size() > 0) {
                    //Llamar servicio para realizar el pedido.
                    solicitarProducto(referenciaSolList);
                } else {
                    TastyToast.makeText(getActivity(), "Digite una cantidad para realizar el pedido", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }

                return true;
            }

        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        consultarReporte();

    }

    private void cancelarPedido(final int id) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Alerta.");
        progressDialog.setMessage("Cargando Información");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "cancel_pedido");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        JSONCancelPedido(response);
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
                params.put("id", String.valueOf(id));

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void JSONCancelPedido(String response) {

        progressDialog.dismiss();

        Gson gson = new Gson();
        EntRespuestaServices entRespuestaServices = gson.fromJson(response, EntRespuestaServices.class);

        if (entRespuestaServices.getEstado() == 0) {
            TastyToast.makeText(getActivity(), entRespuestaServices.getMsg(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            consultarReporte();
        } else if (entRespuestaServices.getEstado() == -1) {
            TastyToast.makeText(getActivity(), entRespuestaServices.getMsg(), TastyToast.LENGTH_LONG, TastyToast.WARNING);
        }

    }

    private void solicitarProducto(final List<EntReferenciaSol> referenciaSolList) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Alerta.");
        progressDialog.setMessage("Cargando Información");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "save_pedido");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        JSONSolicitud(response);
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

                String parJSON = new Gson().toJson(referenciaSolList, LisSolicitarProduct.class);

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());

                params.put("datos", parJSON);

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void JSONSolicitud(String response) {

        progressDialog.dismiss();

        Gson gson = new Gson();
        EntRespuestaServices entRespuestaServices = gson.fromJson(response, EntRespuestaServices.class);

        if (entRespuestaServices.getEstado() == 0) {
            TastyToast.makeText(getActivity(), entRespuestaServices.getMsg(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

            startActivity(new Intent(getActivity(), ActMenu.class));
            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            getActivity().finish();

        } else if (entRespuestaServices.getEstado() == -1) {
            TastyToast.makeText(getActivity(), entRespuestaServices.getMsg(), TastyToast.LENGTH_LONG, TastyToast.WARNING);
        }

    }

    private void consultarReporte() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Alerta.");
        progressDialog.setMessage("Cargando Información");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "solicitar_pedido");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        JSONresponce(response);
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

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void JSONresponce(String response) {

        progressDialog.dismiss();

        Gson gson = new Gson();

        listEntReferenciaSol = gson.fromJson(response, ListEntReferenciaSol.class);

        if (listEntReferenciaSol.getAccion() == 0) {
            //Tiene pedidos pendientes
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
            builder.setCancelable(false);
            builder.setTitle("Pedido: "+listEntReferenciaSol.getEntSolPedidos2().get(0).getId());
            builder.setMessage(listEntReferenciaSol.getMsg());
            builder.setPositiveButton("Cancelar Pedido", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //Llamar servicio para cancelar el pedido
                    cancelarPedido(listEntReferenciaSol.getEntSolPedidos2().get(0).getId());
                }
            }).setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    startActivity(new Intent(getActivity(), ActMenu.class));
                    getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    getActivity().finish();
                }
            });

            builder.show();

        } else if (listEntReferenciaSol.getAccion() == 1) {
            //Puede realizar o solicitar inventario.
            expandableListDetail = ExpandableListDataPumpSol.getData(listEntReferenciaSol);
            final ArrayList<String> expandableListTitle = new ArrayList<>(expandableListDetail.keySet());

            final ExpandableListAdapterSol expandableListAdapter = new ExpandableListAdapterSol(getActivity(), expandableListTitle, expandableListDetail);
            expandable_sol.setAdapter(expandableListAdapter);

            expandable_sol.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {

                    dialog = new DialogEmail(getActivity(), listEntReferenciaSol.getEntSolPedidos().get(groupPosition).getEntReferenciaSols().get(childPosition).getProducto());
                    dialog.show();
                    dialog.setCancelable(false);
                    editSol = (EditText) dialog.findViewById(R.id.editSol);
                    Button acceptButton = dialog.getButtonAccept();
                    acceptButton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            if (isValidNumber(editSol.getText().toString().trim())) {
                                editSol.setError("Campo requerido");
                                editSol.requestFocus();
                            } else if (Integer.parseInt(editSol.getText().toString().trim()) > listEntReferenciaSol.getEntSolPedidos().get(groupPosition).getEntReferenciaSols().get(childPosition).getTotal()) {
                                editSol.setError("La cantidad solicitada es mayor al inventario");
                                editSol.requestFocus();
                            } else {
                                //...
                                dialog.dismiss();

                                listEntReferenciaSol.getEntSolPedidos().get(groupPosition).getEntReferenciaSols().get(childPosition).setCantidadSol(Integer.parseInt(editSol.getText().toString().trim()));

                                expandableListDetail = ExpandableListDataPumpSol.getData(listEntReferenciaSol);

                                expandableListAdapter.setData(expandableListDetail);

                            }

                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(editSol.getWindowToken(), 0);

                        }
                    });

                    Button cancelButton = dialog.getButtonCancel();
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(editSol.getWindowToken(), 0);
                        }
                    });

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                    return false;

                }
            });


        }

    }

}
