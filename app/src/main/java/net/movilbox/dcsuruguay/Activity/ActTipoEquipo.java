package net.movilbox.dcsuruguay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Adapter.AdapterRecyclerCatalogo;
import net.movilbox.dcsuruguay.Controller.ControllerCarrito;
import net.movilbox.dcsuruguay.Controller.ControllerInventario;
import net.movilbox.dcsuruguay.Model.EntCarritoVenta;
import net.movilbox.dcsuruguay.Model.EntCatalogo;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;

import java.util.List;

public class ActTipoEquipo extends BaseActivity {

    private RecyclerView recycler2;
    private GridLayoutManager gridLayoutManagerVertical;
    private EntLisSincronizar entLisSincronizar;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_equipo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tipo venta");
        setSupportActionBar(toolbar);

        connectionDetector = new ConnectionDetector(this);

        recycler2 = (RecyclerView) findViewById(R.id.recycler_view);
        gridLayoutManagerVertical = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);

        controllerInventario = new ControllerInventario(this);
        controllerCarrito = new ControllerCarrito(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            entLisSincronizar = (EntLisSincronizar) bundle.getSerializable("value");
            getComboLocal(entLisSincronizar);
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

    private void getComboLocal(EntLisSincronizar entLisSincronizar) {

        List<EntCatalogo> list = controllerInventario.listCatalogo();

        AdapterRecyclerCatalogo adapter = new AdapterRecyclerCatalogo(this, list, entLisSincronizar);
        recycler2.setAdapter(adapter);
        recycler2.setLayoutManager(gridLayoutManagerVertical);
        int dips = 0;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

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

        recycler2.addItemDecoration(new SpacesItemDecoration(dips));

    }

}
