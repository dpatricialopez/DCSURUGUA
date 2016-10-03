package net.movilbox.dcsuruguay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Adapter.AdapterListCatalogo;
import net.movilbox.dcsuruguay.Controller.ControllerCarrito;
import net.movilbox.dcsuruguay.Controller.ControllerInventario;
import net.movilbox.dcsuruguay.Model.EntCarritoVenta;
import net.movilbox.dcsuruguay.Model.EntCatalogo;
import net.movilbox.dcsuruguay.Model.EntRefeCatalogo;
import net.movilbox.dcsuruguay.R;

import java.util.List;

public class ActListCatalogoDirecta extends BaseActivity {

    private ListView listView_catalogo;
    private EntCatalogo entCatalogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_list_catalogo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controllerCarrito = new ControllerCarrito(this);

        listView_catalogo = (ListView) findViewById(R.id.listViewDetCatalogo);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            entCatalogo = (EntCatalogo) bundle.getSerializable("value");
            cargarReferenciasCatalogo();
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
        // Inflate the menu; this adds items to the act               ion bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_carrito, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_carrito) {

            List<EntCarritoVenta> entCarritoVentaList = controllerCarrito.listCarrito(1);

            if (entCarritoVentaList.size() > 0) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(this, ActCarrito.class);
                bundle.putInt("id_pos", 1);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                TastyToast.makeText(this, "No tiene productos en el carro.", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cargarReferenciasCatalogo() {

        controllerInventario = new ControllerInventario(this);

        final List<EntRefeCatalogo> refeCatalogoList = controllerInventario.listCatalogoReferenciaDirecta(entCatalogo.getId());

        AdapterListCatalogo adapter = new AdapterListCatalogo(ActListCatalogoDirecta.this, refeCatalogoList);
        listView_catalogo.setAdapter(adapter);

        listView_catalogo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                Intent intent = new Intent(ActListCatalogoDirecta.this, ActDetalleSerial.class);
                bundle.putInt("id_refe", refeCatalogoList.get(position).getId_refe());
                //bundle.putDouble("valorRef", refeCatalogoList.get(position).getValor_valor_referencia());
                //bundle.putDouble("valorDirec", refeCatalogoList.get(position).getValor_directo());
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);

            }

        });

    }


}
