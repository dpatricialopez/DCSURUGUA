package net.movilbox.dcsuruguay.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Adapter.AdapterAproPunto;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Model.AprobarPunto;
import net.movilbox.dcsuruguay.Model.DetalleAprobacion;
import net.movilbox.dcsuruguay.Model.ResponseMarcarPedido;
import net.movilbox.dcsuruguay.R;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActDetalleAproPunto extends BaseActivity {

    private List<AprobarPunto> mDescribable;
    private AdapterAproPunto actDetalleAproPunto;
    private String comentario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_apro_punto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controllerLogin = new ControllerLogin(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (List<AprobarPunto>) bundle.getSerializable("value");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SwipeMenuListView mListView = (SwipeMenuListView) findViewById(R.id.listView);
        //mListView = (SwipeMenuListView) findViewById(R.drawable.i);
        actDetalleAproPunto = new AdapterAproPunto(this, mDescribable);
        mListView.setAdapter(actDetalleAproPunto);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(16, 98, 138)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Detalle");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                SwipeMenuItem openItem2 = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem2.setBackground(new ColorDrawable(Color.rgb(219, 68, 55)));
                // set item width
                openItem2.setWidth(dp2px(90));
                // set item title
                openItem2.setTitle("Rechazar");
                // set item title fontsize
                openItem2.setTitleSize(18);
                // set item title font color
                openItem2.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem2);

                SwipeMenuItem openItem3 = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem3.setBackground(new ColorDrawable(Color.rgb(15, 157, 88)));
                // set item width
                openItem3.setWidth(dp2px(90));
                // set item title
                openItem3.setTitle("Aprobar");
                // set item title fontsize
                openItem3.setTitleSize(18);
                // set item title font color
                openItem3.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem3);
            }
        };

        // set creator
        mListView.setMenuCreator(creator);

        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        loadDetallePunto(position);
                        break;
                    case 1:
                        LayoutInflater inflater = getLayoutInflater();
                        View dialoglayout = inflater.inflate(R.layout.dialog_comentario_aproba, null);

                        final EditText editTextComent = (EditText) dialoglayout.findViewById(R.id.EditComment);

                        AlertDialog.Builder builder2 = new AlertDialog.Builder(ActDetalleAproPunto.this);
                        builder2.setCancelable(false);
                        builder2.setTitle("Motivo de rechazo");
                        builder2.setView(dialoglayout).setPositiveButton("Rechazar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if (isValidNumber(editTextComent.getText().toString().trim())) {
                                    Toast.makeText(ActDetalleAproPunto.this, "El comentario es un campo requerido", Toast.LENGTH_LONG).show();
                                } else {
                                    comentario = editTextComent.getText().toString();
                                    aprobarPedido(position, 2);
                                }

                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                        builder2.show();


                        break;
                    case 2:

                        AlertDialog.Builder builder = new AlertDialog.Builder(ActDetalleAproPunto.this);
                        builder.setCancelable(false);
                        builder.setTitle("Aprobación de solicitud");
                        builder.setMessage("¿ Seguro que desea aprobar esta solicitud  ?");
                        builder.setPositiveButton("Aprobar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                aprobarPedido(position, 1);
                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                        builder.show();

                        break;
                }

                return false;

            }
        });

        // set SwipeListener
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                // swipe start
                //loadDetallePunto(position);
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // test item long click
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });
    }

    private void aprobarPedido(final int position, final int aprobar) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Alerta.");
        progressDialog.setMessage("Cargando Información");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "guardar_rechazar_aprobacion");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJSON(response, position);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(ActDetalleAproPunto.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActDetalleAproPunto.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActDetalleAproPunto.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActDetalleAproPunto.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActDetalleAproPunto.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("idpos", String.valueOf(mDescribable.get(position).getId_pos()));
                params.put("idsol", String.valueOf(mDescribable.get(position).getId_soli()));
                params.put("idtiposol", String.valueOf(mDescribable.get(position).getTipo_solicitud()));
                params.put("identificador", String.valueOf(aprobar));
                params.put("motivo", comentario);

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());
                params.put("perfil", String.valueOf(controllerLogin.getUserLogin().getPerfil()));

                return params;
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);

    }

    private void parseJSON(String response, int position) {

        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                Charset.forName("UTF-8").encode(response);

                byte ptext[] = response.getBytes(Charset.forName("ISO-8859-1"));

                String value = new String(ptext, Charset.forName("UTF-8"));

                ResponseMarcarPedido responseMarcarPedido = gson.fromJson(value, ResponseMarcarPedido.class);

                if (responseMarcarPedido.getEstado() == -1) {
                    //Error
                    TastyToast.makeText(this, responseMarcarPedido.getMsg(), TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (responseMarcarPedido.getEstado() == -2) {
                    //No tiene permiso
                    TastyToast.makeText(this, responseMarcarPedido.getMsg(), TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else {
                    // ok
                    TastyToast.makeText(this, responseMarcarPedido.getMsg(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    mDescribable.remove(position);
                    actDetalleAproPunto.notifyDataSetChanged();

                    if (mDescribable == null || mDescribable.size() == 0) {
                        //Activity Principal, Para acceder al fragment
                        finish();
                    }

                }

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                progressDialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }
        } else {
            progressDialog.dismiss();
        }
    }

    private void loadDetallePunto(int position) {

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_detalle_punto_venta, null);
        LinearLayout lm = (LinearLayout) dialoglayout.findViewById(R.id.liner_dinamic);

        LinearLayout ll1 = new LinearLayout(this);
        ll1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll1.setOrientation(LinearLayout.HORIZONTAL);

        TextView productTitulo = new TextView(this);
        productTitulo.setText("Información");
        productTitulo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        productTitulo.setTextColor(getResources().getColor(R.color.colorPrimary));
        productTitulo.setGravity(Gravity.LEFT);
        productTitulo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        productTitulo.setTypeface(productTitulo.getTypeface(), Typeface.BOLD);
        ll1.addView(productTitulo);

        TextView cantidadTitulo = new TextView(this);
        cantidadTitulo.setText("Descripción");
        cantidadTitulo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        cantidadTitulo.setTextColor(getResources().getColor(R.color.colorPrimary));
        cantidadTitulo.setTypeface(cantidadTitulo.getTypeface(), Typeface.BOLD);
        cantidadTitulo.setGravity(Gravity.CENTER);
        cantidadTitulo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        ll1.addView(cantidadTitulo);

        TextView valorTitulo = new TextView(this);
        valorTitulo.setText("Modificación");
        valorTitulo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        valorTitulo.setTextColor(getResources().getColor(R.color.colorPrimary));
        valorTitulo.setTypeface(valorTitulo.getTypeface(), Typeface.BOLD);
        valorTitulo.setGravity(Gravity.RIGHT);
        valorTitulo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        ll1.addView(valorTitulo);

        lm.addView(ll1);

        List<DetalleAprobacion> detallePedidoList = mDescribable.get(position).getDetalleAprobacionList();

        for (int j = 0; j < detallePedidoList.size(); j++) {

            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ll.setOrientation(LinearLayout.HORIZONTAL);

            // Create TextView
            TextView product = new TextView(this);
            product.setText(String.format("%1s", detallePedidoList.get(j).getCampo()));
            product.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            product.setGravity(Gravity.LEFT);
            product.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            ll.addView(product);

            // Create TextView
            TextView cantidad = new TextView(this);
            cantidad.setText(String.format("%1s", detallePedidoList.get(j).getValor()));
            cantidad.setGravity(Gravity.CENTER | Gravity.FILL_VERTICAL);
            cantidad.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            cantidad.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
            ll.addView(cantidad);

            // Create TextView
            TextView valor = new TextView(this);
            valor.setText(String.format("%1s", detallePedidoList.get(j).getCambio()));
            valor.setGravity(Gravity.RIGHT | Gravity.FILL_VERTICAL);
            valor.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            valor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
            ll.addView(valor);

            // Create Button
            lm.addView(ll);

        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Detalle del punto");
        builder.setView(dialoglayout).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
