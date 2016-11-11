package net.movilbox.dcsuruguay.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Activity.ActMenu;
import net.movilbox.dcsuruguay.Adapter.DialogEmail;
import net.movilbox.dcsuruguay.Adapter.ExpandableListAdapterSol;
import net.movilbox.dcsuruguay.Adapter.ExpandableListAdapterSol2;
import net.movilbox.dcsuruguay.Adapter.ExpandableListDataPumpSol;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.DataBase.DBHelper;
import net.movilbox.dcsuruguay.Fragment.BaseVolleyFragment;
import net.movilbox.dcsuruguay.Model.EntCupo;
import net.movilbox.dcsuruguay.Model.EntReferenciaSol;
import net.movilbox.dcsuruguay.Model.EntRespuestaServices;
import net.movilbox.dcsuruguay.Model.LisSolicitarProduct;
import net.movilbox.dcsuruguay.Model.ListEntReferenciaSol;
import net.movilbox.dcsuruguay.Model.ListaEntidadSolprod;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;
import net.movilbox.dcsuruguay.Model.EntReferenciaSol;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;


public class ActSolProd extends AppCompatActivity {

        private ExpandableListView expandable_sol;
        private SpotsDialog alertDialog;
        private DBHelper mydb;
        public ControllerLogin controllerLogin;
        private ListEntReferenciaSol listEntReferenciaSol;
        private HashMap<String, List<EntReferenciaSol>> expandableListDetail;
        private EditText editSol;
        private HashMap<String, HashMap<String, List<EntReferenciaSol>>> expandable;
        private RequestQueue rq;
        public TextView txtCupo;
        private ConnectionDetector connectionDetector;
        public ProgressDialog progressDialog;
        private DecimalFormat format;
        private Toolbar toolbar;
        public ExpandableListAdapterSol2 expandableListAdapter;
        public ArrayList<String> expandableListTitle;
        private List<EntCupo> listaCupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectionDetector = new ConnectionDetector(this);

        setContentView(R.layout.act_sol_prod);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Solicitar producto");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        expandable_sol = (ExpandableListView) findViewById(R.id.expandable_sol);

        controllerLogin = new ControllerLogin(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


            }

    @Override
    protected void onStart() {
        super.onStart();

        consultarReporte();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem item2 = menu.add("Guardar");
        item2.setIcon(R.drawable.ic_save_white_24dp); // sets icon
        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                List<EntReferenciaSol> referenciaSolList = new ArrayList<>();
                   /* //Guardar Aceptacion de pedidos validaciones- VERSIÒN ANTES DE LISTA EXPANDIBLE ANIDADA
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
                    }*/


                //Guardar Aceptacion de pedidos validaciones con lista expandible anidada
                for (int i = 0; i < expandable.size(); i++) {
                    for (int t = 0; t < expandable.get(expandableListTitle.get(i)).size(); t++) {
                        ArrayList<String> tipos = new ArrayList<>(expandable.get(expandableListTitle.get(i)).keySet());

                        for (int j = 0; j < expandable.get(expandableListTitle.get(i)).get(tipos.get(t)).size(); j++) {
                            if (expandable.get(expandableListTitle.get(i)).get(tipos.get(t)).get(j).getCantidadSol() > 0) {
                                EntReferenciaSol entReferenciaSol = new EntReferenciaSol();
                                entReferenciaSol.setId_referencia(expandable.get(expandableListTitle.get(i)).get(tipos.get(t)).get(j).getId_referencia());
                                entReferenciaSol.setCantidadSol(expandable.get(expandableListTitle.get(i)).get(tipos.get(t)).get(j).getCantidadSol());
                                entReferenciaSol.setId_bodega(expandable.get(expandableListTitle.get(i)).get(tipos.get(t)).get(j).getId_bodega());
                                entReferenciaSol.setTipo_bodega(expandable.get(expandableListTitle.get(i)).get(tipos.get(t)).get(j).getTipo_bodega());
                                entReferenciaSol.setTipo_ref(expandable.get(expandableListTitle.get(i)).get(tipos.get(t)).get(j).getTipo_ref());
                                referenciaSolList.add(entReferenciaSol);
                            }
                        }
                    }

                }
                if (referenciaSolList.size() > 0) {
                    //Llamar servicio para realizar el pedido.
                    solicitarProducto(referenciaSolList);
                } else {
                    Toast.makeText(ActSolProd.this, "Digite una cantidad para realizar el pedido", Toast.LENGTH_LONG).show();
                }
                return true;


            }
        });
        return true;
    }

            private void cancelarPedido(final int id) {

                progressDialog = new ProgressDialog(ActSolProd.this);
                progressDialog.setTitle("Alerta.");
                progressDialog.setMessage("Cargando Información");
                progressDialog.setCancelable(false);
                progressDialog.show();
                rq = Volley.newRequestQueue(this);
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

                rq.add(jsonRequest);

            }

            private void JSONCancelPedido(String response) {

                progressDialog.dismiss();
                rq = Volley.newRequestQueue(this);
                Gson gson = new Gson();
                EntRespuestaServices entRespuestaServices = gson.fromJson(response, EntRespuestaServices.class);

                if (entRespuestaServices.getEstado() == 0) {
                    TastyToast.makeText(ActSolProd.this, entRespuestaServices.getMsg(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    consultarReporte();
                } else if (entRespuestaServices.getEstado() == -1) {
                    TastyToast.makeText(ActSolProd.this, entRespuestaServices.getMsg(), TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }

            }

            private void solicitarProducto(final List<EntReferenciaSol> referenciaSolList) {

                progressDialog = new ProgressDialog(ActSolProd.this);
                progressDialog.setTitle("Alerta.");
                progressDialog.setMessage("Cargando Información");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String url = String.format("%1$s%2$s", getString(R.string.url_base), "save_pedido");
                rq = Volley.newRequestQueue(this);
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

                rq.add(jsonRequest);

            }

            private void JSONSolicitud(String response) {

                progressDialog.dismiss();

                Gson gson = new Gson();
                EntRespuestaServices entRespuestaServices = gson.fromJson(response, EntRespuestaServices.class);

                if (entRespuestaServices.getEstado() == 0) {
                    TastyToast.makeText(ActSolProd.this, entRespuestaServices.getMsg(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                    startActivity(new Intent(ActSolProd.this, ActMenu.class));
                    ActSolProd.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    ActSolProd.this.finish();

                } else if (entRespuestaServices.getEstado() == -1) {
                    TastyToast.makeText(ActSolProd.this, entRespuestaServices.getMsg(), TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }

            }

            private void consultarReporte() {

                progressDialog = new ProgressDialog(ActSolProd.this);
                progressDialog.setTitle("Alerta.");
                progressDialog.setMessage("Cargando Información");
                progressDialog.setCancelable(false);
                progressDialog.show();
                rq = Volley.newRequestQueue(this);
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

                rq.add(jsonRequest);

            }

            private boolean isValidNumber(String number) {
                return number == null || number.length() == 0;
            }

            private void JSONresponce(String response) {

                progressDialog.dismiss();

                Gson gson = new Gson();
                listaCupo = new ArrayList<>();
                listEntReferenciaSol = gson.fromJson(response, ListEntReferenciaSol.class);

                if (listEntReferenciaSol.getAccion() == 0) {
                    //Tiene pedidos pendientes
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ActSolProd.this);
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
                            startActivity(new Intent(ActSolProd.this, ActMenu.class));
                            ActSolProd.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            ActSolProd.this.finish();
                        }
                    });

                    builder.show();

                } else if (listEntReferenciaSol.getAccion() == 1) {
                    //Puede realizar o solicitar inventario.
                    expandableListDetail = ExpandableListDataPumpSol.getData(listEntReferenciaSol);
                    expandableListTitle = new ArrayList<>(expandableListDetail.keySet());

                    // construcciòn de arreglo para lista expandible
                    expandable= new HashMap<String, HashMap<String, List<EntReferenciaSol>>>();

                    for (int i=0;i<expandableListTitle.size();i++){
                        ListaEntidadSolprod listaCombos= new ListaEntidadSolprod();
                        ListaEntidadSolprod listaEquipos= new ListaEntidadSolprod();
                        ListaEntidadSolprod listaSims= new ListaEntidadSolprod();
                        HashMap<String, List<EntReferenciaSol>> tipo= new HashMap<String, List<EntReferenciaSol>> ();
                        for (int j=0;j<expandableListDetail.get(expandableListTitle.get(i)).size();j++){
                            switch(expandableListDetail.get(expandableListTitle.get(i)).get(j).getTipo_grupo()){
                                case 1:
                                    listaSims.add(expandableListDetail.get(expandableListTitle.get(i)).get(j));
                                    break;
                                case 2:
                                    listaCombos.add(expandableListDetail.get(expandableListTitle.get(i)).get(j));
                                    break;
                                case 3:
                                    listaEquipos.add(expandableListDetail.get(expandableListTitle.get(i)).get(j));
                                    break;
                            }
                        }
                        if(listaSims.size()>0)
                            tipo.put("Sims",listaSims);
                        if(listaCombos.size()>0)
                            tipo.put("Combos",listaCombos);
                        if(listaEquipos.size()>0)
                            tipo.put("Equipos",listaEquipos);
                        expandable.put(String.valueOf(expandableListTitle.get(i)), tipo);

                    }
                    expandableListAdapter = new ExpandableListAdapterSol2(this, expandableListTitle, expandableListDetail, expandable);//




                    final ExpandableListAdapterSol2 expandableListAdapter = new ExpandableListAdapterSol2(this, expandableListTitle, expandableListDetail, expandable);
                    expandable_sol.setAdapter(expandableListAdapter);

/*                    expandable_sol.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {

                            DialogSolProducto dialog = new DialogSolProducto(ActSolProd.this, listEntReferenciaSol.getEntSolPedidos().get(groupPosition).getEntReferenciaSols().get(childPosition).getProducto());
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

                                    InputMethodManager imm = (InputMethodManager) ActSolProd.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(editSol.getWindowToken(), 0);

                                }
                            });

                            Button cancelButton = dialog.getButtonCancel();
                            cancelButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    InputMethodManager imm = (InputMethodManager) ActSolProd.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(editSol.getWindowToken(), 0);
                                }
                            });

                            InputMethodManager imm = (InputMethodManager) ActSolProd.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                            return false;


                    });}*/


                }

            }


    public void pedido(final int listposition, final int groupoposition, final int children){
        final ArrayList<String> expandableListTipo=new ArrayList<>();
        expandableListTipo.add("Combos");
        expandableListTipo.add("Sims");
        expandableListTipo.add("Equipos");

        final DialogSolProducto dialog = new DialogSolProducto(ActSolProd.this, expandable.get(expandableListTitle.get(listposition)).get(expandableListTipo.get(groupoposition)).get(children).getProducto());
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
                } else if (Integer.parseInt(editSol.getText().toString().trim()) > expandable.get(expandableListTitle.get(listposition)).get(expandableListTipo.get(groupoposition)).get(children).getTotal()) {
                    editSol.setError("La cantidad solicitada es mayor al inventario");
                    editSol.requestFocus();
                } else {
                    //...
                    dialog.dismiss();

                    EntCupo pojoCupo = new EntCupo();

                    int idBode = expandable.get(expandableListTitle.get(listposition)).get(expandableListTipo.get(groupoposition)).get(children).getId_bodega();
                    int idReferencia = expandable.get(expandableListTitle.get(listposition)).get(expandableListTipo.get(groupoposition)).get(children).getId_referencia();
                    int tipoBode = expandable.get(expandableListTitle.get(listposition)).get(expandableListTipo.get(groupoposition)).get(children).getTipo_bodega();
                    boolean bandera = true;
                    double totalView = 0;
                    //double precio = expandable.get(expandableListTitle.get(listposition)).get(expandableListTipo.get(groupoposition)).get(children).getPrecio_pdv();
                    int cantidad = Integer.parseInt(editSol.getText().toString().trim());
                    //double total = cantidad * precio;



                    if(listaCupo.size() == 0)
                    {

                       /* if(total <= listEntReferenciaSol.getCupo_disponible())
                        {*/
                            pojoCupo.setIdBode(idBode);
                            pojoCupo.setIdReferencia(idReferencia);
                            pojoCupo.setTipo_bodega(tipoBode);
                           // pojoCupo.setTotalRef(total);
                            listaCupo.add(pojoCupo);
                            //listEntReferenciaSol.setCupo_disponible(listEntReferenciaSol.getCupo_disponible() - total);

                            //txtCupo.setText(String.format("S/. %s", format.format(listEntReferenciaSol.getCupo_disponible() - total)));

                            expandable.get(expandableListTitle.get(listposition)).get(expandableListTipo.get(groupoposition)).get(children).setCantidadSol(cantidad);

                            expandableListDetail = ExpandableListDataPumpSol.getData(listEntReferenciaSol);

                            // expandableListAdapter.setData(expandableListDetail);
                       /* }
                        else
                        {
                            Toast.makeText(ActSolProd.this,"La cantidas solicitada supera el valor del cupo disonible",Toast.LENGTH_LONG).show();
                        }*/
                    }
                    else
                    {
                        for (int j = 0;j < listaCupo.size(); j++ )
                        {
                            totalView += listaCupo.get(j).getTotalRef();
                        }

                        for (int i = 0;i < listaCupo.size(); i++ )
                        {
                            if (idBode == listaCupo.get(i).getIdBode() && idReferencia == listaCupo.get(i).getIdReferencia() && tipoBode == listaCupo.get(i).getTipo_bodega())
                            {
                                //totalView = (totalView - listaCupo.get(i).getTotalRef()) + total;

                                /*if(totalView <= listEntReferenciaSol.getCupo_disponible())
                                {
                                    listaCupo.get(i).setTotalRef(total);

                                    txtCupo.setText(String.format("S/. %s", format.format(listEntReferenciaSol.getCupo_disponible() - totalView)));*/

                                    expandable.get(expandableListTitle.get(listposition)).get(expandableListTipo.get(groupoposition)).get(children).setCantidadSol(cantidad);

                                    expandableListDetail = ExpandableListDataPumpSol.getData(listEntReferenciaSol);

                                    //expandableListAdapter.setData(expandableListDetail);

                                    bandera = false;
                               /* }
                                else
                                {
                                    Toast.makeText(ActSolProd.this,"La cantidad solicitada supera el valor del cupo disonible",Toast.LENGTH_LONG).show();
                                }*/
                                break;
                            }
                        }

                        if (bandera)
                        {    /*totalView = totalView + total;
                            if(totalView <= listEntReferenciaSol.getCupo_disponible())
                            {*/
                                pojoCupo.setIdBode(idBode);
                                pojoCupo.setIdReferencia(idReferencia);
                                pojoCupo.setTipo_bodega(tipoBode);
                                //pojoCupo.setTotalRef(total);
                                listaCupo.add(pojoCupo);

                                //txtCupo.setText(String.format("S/. %s", format.format(listEntReferenciaSol.getCupo_disponible() - totalView)));

                                expandable.get(expandableListTitle.get(listposition)).get(expandableListTipo.get(groupoposition)).get(children).setCantidadSol(cantidad);

                                expandableListDetail = ExpandableListDataPumpSol.getData(listEntReferenciaSol);

                                //   expandableListAdapter.setData(expandableListDetail);

                           /* }
                            else
                            {
                                Toast.makeText(ActSolProd.this,"La cantidad solicitada supera el valor del cupo disonible",Toast.LENGTH_LONG).show();
                            }*/
                        }
                    }


                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editSol.getWindowToken(), 0);

            }
        });

        Button cancelButton = dialog.getButtonCancel();
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editSol.getWindowToken(), 0);
            }
        });

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);


    }

        }
