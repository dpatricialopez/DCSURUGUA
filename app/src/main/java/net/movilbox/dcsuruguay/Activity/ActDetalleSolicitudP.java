package net.movilbox.dcsuruguay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import net.movilbox.dcsuruguay.Adapter.AdapterDetalleSolicitud;
import net.movilbox.dcsuruguay.Model.ListSolicitudVendedor;
import net.movilbox.dcsuruguay.R;


public class ActDetalleSolicitudP extends AppCompatActivity {

    private ListView listVDetalleSol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_detalle_solicitud_p);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listVDetalleSol = (ListView) findViewById(R.id.listVDetalleSol);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            ListSolicitudVendedor entLisSincronizar = (ListSolicitudVendedor) bundle.getSerializable("value");

            AdapterDetalleSolicitud adapter = new AdapterDetalleSolicitud(this, entLisSincronizar.getEntSolicitudVendedors());
            listVDetalleSol.setAdapter(adapter);
        }
    }

}
