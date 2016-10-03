package net.movilbox.dcsuruguay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Adapter.AdapterSeriales;
import net.movilbox.dcsuruguay.Controller.ControllerCarrito;
import net.movilbox.dcsuruguay.Controller.ControllerInventario;
import net.movilbox.dcsuruguay.Model.EntCarritoVenta;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.Model.EntListReferencias;
import net.movilbox.dcsuruguay.Model.EntRefeSerial;
import net.movilbox.dcsuruguay.Model.Motivos;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;

import java.util.List;

public class ActTipoVentaUnida extends BaseActivity {

    private EntLisSincronizar entLisSincronizar;
    private int indicador;
    private Spinner spinner1;
    private Spinner spinner2;
    private ListView listView_serial;
    private List<EntRefeSerial> refeSerials;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_venta_unida);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tipo venta");
        setSupportActionBar(toolbar);

        controllerInventario = new ControllerInventario(this);
        controllerCarrito = new ControllerCarrito(this);
        connectionDetector = new ConnectionDetector(this);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        listView_serial = (ListView) findViewById(R.id.listView_serial);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            entLisSincronizar = (EntLisSincronizar) bundle.getSerializable("value");
            indicador = bundle.getInt("indicador");

            llenarSpinner1();

        }

        if (connectionDetector.isConnected()) {
            toolbar.setTitle("Tipo venta");
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            toolbar.setTitle("Tipo venta OFFLINE");
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.rojo_progress));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tipo_venta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_enviar) {
            boolean bandera = false;
            if (refeSerials != null) {
                for (int i = 0; i < refeSerials.size(); i++) {
                    if (refeSerials.get(i).isSelected()) {

                        if (validateCarritoProducto(refeSerials.get(i).getId_referencia(), refeSerials.get(i).getSerie(), 2)) {
                            EntCarritoVenta entCarritoVenta = new EntCarritoVenta();

                            entCarritoVenta.setIdReferencia(refeSerials.get(i).getId_referencia());
                            entCarritoVenta.setTipoProducto(refeSerials.get(i).getTipo_pro());


                            entCarritoVenta.setValorRefe(controllerInventario.getValorReferencia(refeSerials.get(i).getId_referencia(), 2));

                            entCarritoVenta.setValorDirecto(controllerInventario.getValorReferencia(refeSerials.get(i).getId_referencia(), 1));

                            entCarritoVenta.setSerie(refeSerials.get(i).getSerie());

                            if (entLisSincronizar == null)
                                entCarritoVenta.setIdPunto(1);
                            else
                                entCarritoVenta.setIdPunto(entLisSincronizar.getIdpos());

                            entCarritoVenta.setIdPaquete(refeSerials.get(i).getId_paquete());

                            if (entLisSincronizar == null)
                                entCarritoVenta.setTipoVenta(1);
                            else
                                entCarritoVenta.setTipoVenta(2);

                            controllerCarrito.guardarVentaCarrito(entCarritoVenta);

                            bandera = true;

                        } else {
                            TastyToast.makeText(this, String.format("El producto: %s ya esta en el carrito", refeSerials.get(i).getSerie()), TastyToast.LENGTH_LONG, TastyToast.WARNING);
                        }
                    }
                }

                if (bandera) {
                    TastyToast.makeText(this, "El producto se guardo correctamente en el carrito", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    finish();
                } else {
                    TastyToast.makeText(this, "Debe seleccionar almenos una referencia para realizar la venta", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }

            } else {
                TastyToast.makeText(this, "Debe seleccionar un paquete y referencias para guerdar en el carrito", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void llenarSpinner1() {

        final List<Motivos> paquete = controllerInventario.listPaquetes(indicador);

        ArrayAdapter<Motivos> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, paquete);
        spinner1.setAdapter(prec3);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargarReferencias(paquete.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void cargarReferencias(int id) {

        final List<EntListReferencias> referencia = controllerInventario.listReferencias(id);

        ArrayAdapter<EntListReferencias> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, referencia);
        spinner2.setAdapter(prec3);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(referencia.get(position).getIdRefe() > 0) {
                    cargarReferenciasSerial(referencia.get(position).getIdPaquete(), referencia.get(position).getIdRefe(), referencia.get(position).getTipoPro());
                    listView_serial.setVisibility(View.VISIBLE);
                }else{
                    listView_serial.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void cargarReferenciasSerial(int idPaquete, int idRefe, int tipoPro) {

        refeSerials = controllerInventario.listReferenciasSerial(idPaquete,idRefe,tipoPro, 0);

        AdapterSeriales adapter = new AdapterSeriales(this, refeSerials);
        listView_serial.setAdapter(adapter);
    }

    private boolean validateCarritoProducto(int idReferencia, String idSerie, int i) {
        return controllerCarrito.validarCarrito(idReferencia, idSerie, i);
    }

}
