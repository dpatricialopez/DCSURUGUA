package net.movilbox.dcsuruguay.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;


import net.movilbox.dcsuruguay.Controller.ControllerCarrito;
import net.movilbox.dcsuruguay.Model.EntCarritoVenta;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.Model.Motivos;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;

public class ActMarcarVisita extends BaseActivity implements View.OnClickListener {

    private EntLisSincronizar entLisSincronizar;
    private Spinner spinner_tipo_dos;

    private TextView text_idpos;
    private TextView text_razon;
    private TextView text_ruta;
    private TextView text_direccion;
    private TextView text_departamento;
    private TextView text_circuito;
    private Button btn_no_venta;
    private int id_estado;
    private int id_estado_dos;
    private ConnectionDetector connectionDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_visita);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Marcar Venta");
        setSupportActionBar(toolbar);

        controllerCarrito = new ControllerCarrito(this);
        connectionDetector = new ConnectionDetector(this);

        text_idpos = (TextView) findViewById(R.id.text_idpos);
        text_razon = (TextView) findViewById(R.id.text_razon);
        text_ruta = (TextView) findViewById(R.id.text_ruta);
        text_direccion = (TextView) findViewById(R.id.text_direccion);
        text_departamento = (TextView) findViewById(R.id.text_departamento);
        text_circuito = (TextView) findViewById(R.id.text_circuito);

        btn_no_venta = (Button) findViewById(R.id.btn_no_venta);
        btn_no_venta.setOnClickListener(this);
        Button btn_tomar_pedido = (Button) findViewById(R.id.btn_tomar_pedido);
        btn_tomar_pedido.setOnClickListener(this);
        Button btn_inventariar = (Button) findViewById(R.id.btn_inventariar);
        btn_inventariar.setOnClickListener(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            entLisSincronizar = (EntLisSincronizar) bundle.getSerializable("value");
            llenarInformacionPunto(entLisSincronizar);
        }

        if (connectionDetector.isConnected()) {
            toolbar.setTitle("Marcar Venta");
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            toolbar.setTitle("Marcar Venta OFFLINE");
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.rojo_progress));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_carrito, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_carrito) {

            List<EntCarritoVenta> entCarritoVentaList = controllerCarrito.listCarrito(entLisSincronizar.getIdpos());

            if (entCarritoVentaList.size() > 0) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(this, ActCarrito.class);
                bundle.putInt("id_pos", entLisSincronizar.getIdpos());
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                TastyToast.makeText(this, "No tiene productos en el carro.", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void llenarInformacionPunto(EntLisSincronizar entLisSincronizar) {

        text_idpos.setText(String.format("%1$s", entLisSincronizar.getIdpos()));
        text_razon.setText(String.format("%1$s", entLisSincronizar.getNombre_punto()));
        text_circuito.setText(String.format("%1$s", entLisSincronizar.getNombreTerritorio()));
        text_ruta.setText(String.format("%1$s", entLisSincronizar.getNombreZona()));
        text_direccion.setText(String.format("%1$s", entLisSincronizar.getTexto_direccion()));
        text_departamento.setText(String.format("%1$s", entLisSincronizar.getNombreDepartamento()));

        if (entLisSincronizar.getEstado_visita() == 0)
            btn_no_venta.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_no_venta:

                Bundle bundle = new Bundle();
                Intent intent = new Intent(this, ActNoVenta.class);
                bundle.putSerializable("value", entLisSincronizar);
                intent.putExtras(bundle);
                startActivity(intent);

                break;
            case R.id.btn_inventariar:

                if (connectionDetector.isConnected()) {

                    Bundle bundle3 = new Bundle();
                    Intent intent3 = new Intent(this, ActInventariar.class);
                    bundle3.putSerializable("value", entLisSincronizar);
                    intent3.putExtras(bundle3);
                    startActivity(intent3);

                } else {
                    TastyToast.makeText(this, "El modulo solo funciona en modo ONLINE", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }

                break;
            case R.id.btn_tomar_pedido:
                cargarDialogVenta();
                break;

        }
    }

    private void cargarDialogVenta() {

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_select_sim_com, null);

        Spinner spinner_tipo_venta = (Spinner) dialoglayout.findViewById(R.id.spinner_tipo_venta);
        spinner_tipo_dos = (Spinner) dialoglayout.findViewById(R.id.spinner_tipo_dos);
        LinearLayout resulTipoEvent = (LinearLayout) dialoglayout.findViewById(R.id.resulTipoEvent);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("TIPO DE VENTA");
        builder.setCancelable(false);
        llenarSpinner(spinner_tipo_venta, resulTipoEvent, spinner_tipo_dos);
        builder.setView(dialoglayout);
        builder.setView(dialoglayout).setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

    private void llenarSpinner(Spinner spinner_tipo_venta, LinearLayout resulTipoEvent, Spinner spinner_tipo_dos) {
        List<Motivos> motivosList = new ArrayList<>();

        motivosList.add(new Motivos(0, "SELECCIONAR VENTA"));
        motivosList.add(new Motivos(1, "VENTA DE SIMCARD"));
        motivosList.add(new Motivos(2, "VENTA DE EQUIPO"));
        loadCausa(motivosList, spinner_tipo_venta, resulTipoEvent, spinner_tipo_dos);
    }

    private void loadCausa(final List<Motivos> thumbs, Spinner spinner_tipo_venta, final LinearLayout resulTipoEvent, final Spinner spinner_tipo_dos) {

        ArrayAdapter<Motivos> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, thumbs);
        spinner_tipo_venta.setAdapter(prec3);
        spinner_tipo_venta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                id_estado = thumbs.get(position).getId();

                if (id_estado == 1) {
                    llenarSpinnerTipo(spinner_tipo_dos);
                    resulTipoEvent.setVisibility(View.VISIBLE);

                } else if (id_estado == 2) {
                    resulTipoEvent.setVisibility(View.GONE);
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(ActMarcarVisita.this, ActTipoEquipo.class);
                    bundle.putSerializable("value", entLisSincronizar);
                    bundle.putInt("indicador", 0);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void llenarSpinnerTipo(Spinner spinner_) {
        List<Motivos> motivosList = new ArrayList<>();

        motivosList.add(new Motivos(0, "SELECCIONAR"));
        motivosList.add(new Motivos(1, "PAQUETE"));
        motivosList.add(new Motivos(2, "UNIDAD"));
        loadTipoVenta(motivosList, spinner_);
    }

    private void loadTipoVenta(final List<Motivos> motivosList, Spinner spinner_) {
        ArrayAdapter<Motivos> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, motivosList);
        spinner_.setAdapter(prec3);
        spinner_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                id_estado_dos = motivosList.get(position).getId();

                if (id_estado_dos == 1) {

                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(ActMarcarVisita.this, ActTipoVenta.class);
                    bundle.putSerializable("value", entLisSincronizar);
                    bundle.putInt("indicador", 0);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1);

                } else if (id_estado_dos == 2) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(ActMarcarVisita.this, ActTipoVentaUnida.class);
                    bundle.putSerializable("value", entLisSincronizar);
                    bundle.putInt("indicador", -1);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == 1) {
            spinner_tipo_dos.setSelection(0);
        }

    }

}
