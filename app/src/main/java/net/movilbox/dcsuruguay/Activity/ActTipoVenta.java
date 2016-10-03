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
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Adapter.AdapterReferencias;
import net.movilbox.dcsuruguay.Controller.ControllerCarrito;
import net.movilbox.dcsuruguay.Controller.ControllerInventario;
import net.movilbox.dcsuruguay.Model.EntCarritoVenta;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.Model.EntRefeSerial;
import net.movilbox.dcsuruguay.Model.Motivos;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;

public class ActTipoVenta extends BaseActivity {

    private EntLisSincronizar entLisSincronizar;
    private Spinner paquete_spinner;
    private ListView listView_paquete;
    private TextView txtSinPaq;
    private List<EntRefeSerial> listReferenciases = new ArrayList<>();
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_venta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tipo venta");
        setSupportActionBar(toolbar);

        controllerCarrito = new ControllerCarrito(this);
        connectionDetector = new ConnectionDetector(this);

        controllerInventario = new ControllerInventario(this);

        paquete_spinner = (Spinner) findViewById(R.id.paquete_spinner);
        listView_paquete = (ListView) findViewById(R.id.listView_paquete);
        txtSinPaq = (TextView) findViewById(R.id.txtSinPaq);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            entLisSincronizar = (EntLisSincronizar) bundle.getSerializable("value");
            int indicador = bundle.getInt("indicador");

            cargarPaquetes(indicador);
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

            if (listReferenciases.size() > 0) {
                //Guardar BD
                boolean bandera = false;
                for (int i = 0; i < listReferenciases.size(); i++) {

                    if (validateCarritoProducto(listReferenciases.get(i).getId_referencia(), listReferenciases.get(i).getSerie(), 1)) {
                        EntCarritoVenta entCarritoVenta = new EntCarritoVenta();

                        entCarritoVenta.setIdReferencia(listReferenciases.get(i).getId_referencia());
                        entCarritoVenta.setTipoProducto(listReferenciases.get(i).getTipo_pro());


                        entCarritoVenta.setValorRefe(controllerInventario.getValorReferencia(listReferenciases.get(i).getId_referencia(), 2));

                        entCarritoVenta.setValorDirecto(controllerInventario.getValorReferencia(listReferenciases.get(i).getId_referencia(), 1));

                        entCarritoVenta.setSerie(listReferenciases.get(i).getSerie());

                        if (entLisSincronizar == null)
                            entCarritoVenta.setIdPunto(1);
                        else
                            entCarritoVenta.setIdPunto(entLisSincronizar.getIdpos());

                        entCarritoVenta.setIdPaquete(listReferenciases.get(i).getId_paquete());

                        if (entLisSincronizar == null)
                            entCarritoVenta.setTipoVenta(1);
                        else
                            entCarritoVenta.setTipoVenta(2);

                        controllerCarrito.guardarVentaCarrito(entCarritoVenta);

                        bandera = true;

                    } else {
                        TastyToast.makeText(this, String.format("El producto: %s ya esta en el carrito", listReferenciases.get(i).getSerie()), TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                }

                if (bandera)
                    TastyToast.makeText(this, "El producto se guardo correctamente en el carrito", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                else
                    TastyToast.makeText(this, "Debe seleccionar almenos una referencia para realizar la venta", TastyToast.LENGTH_LONG, TastyToast.WARNING);

                finish();

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validateCarritoProducto(int idReferencia, String idSerie, int tipo) {
        return controllerCarrito.validarCarrito(idReferencia, idSerie, tipo);
    }

    private void cargarPaquetes(int indicador) {

        loadPaquetes(controllerInventario.listPaquetes(indicador));

    }

    private void loadPaquetes(final List<Motivos> motivos) {

        ArrayAdapter<Motivos> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, motivos);
        paquete_spinner.setAdapter(prec3);
        paquete_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(motivos.get(position).getId() > 0){
                    listView_paquete.setVisibility(View.VISIBLE);
                    txtSinPaq.setVisibility(View.GONE);
                    cargarReferencias(motivos.get(position).getId());
                } else {
                    listView_paquete.setVisibility(View.GONE);
                    txtSinPaq.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void cargarReferencias(int id) {

        listReferenciases = controllerInventario.listSeriePaquete(id, 1);

        AdapterReferencias adapter = new AdapterReferencias(this, listReferenciases);
        listView_paquete.setAdapter(adapter);

    }

}
