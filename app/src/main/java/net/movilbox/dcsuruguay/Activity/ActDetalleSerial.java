package net.movilbox.dcsuruguay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.sdsmdg.tastytoast.TastyToast;


import net.movilbox.dcsuruguay.Adapter.AdapterSeriales;
import net.movilbox.dcsuruguay.Controller.ControllerCarrito;
import net.movilbox.dcsuruguay.Controller.ControllerInventario;
import net.movilbox.dcsuruguay.Model.EntCarritoVenta;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.Model.EntRefeSerial;
import net.movilbox.dcsuruguay.R;

import java.util.List;

public class ActDetalleSerial extends BaseActivity {

    private List<EntRefeSerial> refeSerials;
    private double valorRefencia = 0;
    private double valorDirecto = 0;
    private EntLisSincronizar entLisSincronizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_serial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controllerInventario = new ControllerInventario(this);

        controllerCarrito = new ControllerCarrito(this);

        ListView listViewReferen = (ListView) findViewById(R.id.listViewReferen);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            entLisSincronizar = (EntLisSincronizar) bundle.getSerializable("value");

            int idReferencia = bundle.getInt("id_refe");

            valorRefencia = bundle.getDouble("valorRef");
            valorDirecto = bundle.getDouble("valorDirec");

            refeSerials = controllerInventario.listReferenciasSerial(0, idReferencia, 2, 0);

            AdapterSeriales adapter = new AdapterSeriales(ActDetalleSerial.this, refeSerials);
            listViewReferen.setAdapter(adapter);

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
            for (int i = 0; i < refeSerials.size(); i++) {
                if (refeSerials.get(i).isChecked) {

                    if (validateCarritoProducto(refeSerials.get(i).getId_referencia(), refeSerials.get(i).getSerie(), 1)) {
                        EntCarritoVenta entCarritoVenta = new EntCarritoVenta();

                        entCarritoVenta.setIdReferencia(refeSerials.get(i).getId_referencia());
                        entCarritoVenta.setTipoProducto(refeSerials.get(i).getTipo_pro());

                        if (valorRefencia == 0.0)
                            entCarritoVenta.setValorRefe(controllerInventario.getValorReferencia(refeSerials.get(i).getId_referencia(), 2));
                        else
                            entCarritoVenta.setValorRefe(valorRefencia);

                        if (valorDirecto == 0.0)
                            entCarritoVenta.setValorDirecto(controllerInventario.getValorReferencia(refeSerials.get(i).getId_referencia(), 1));
                        else
                            entCarritoVenta.setValorDirecto(valorDirecto);

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

            if (bandera)
                TastyToast.makeText(this, "El producto se guardo correctamente en el carrito", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            else
                TastyToast.makeText(this, "Debe seleccionar almenos una referencia para realizar la venta", TastyToast.LENGTH_LONG, TastyToast.WARNING);

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private boolean validateCarritoProducto(int idReferencia, String idSerie, int i) {
        return controllerCarrito.validarCarrito(idReferencia, idSerie, i);
    }

}
