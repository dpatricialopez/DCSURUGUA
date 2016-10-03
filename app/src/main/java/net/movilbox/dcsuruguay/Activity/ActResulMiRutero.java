package net.movilbox.dcsuruguay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import net.movilbox.dcsuruguay.Adapter.RecyclerRutero;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.R;

import java.util.List;

public class ActResulMiRutero extends AppCompatActivity {

    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resul_mi_rutero);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recycler = (RecyclerView) findViewById(R.id.recycler_view);
        recycler.setHasFixedSize(true);

        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            List<EntLisSincronizar> entLisSincronizar = (List<EntLisSincronizar>) bundle.getSerializable("value");
            llenarRutero(entLisSincronizar);
        }

    }

    private void llenarRutero(List<EntLisSincronizar> entLisSincronizar) {
        RecyclerRutero adapter = new RecyclerRutero(this, entLisSincronizar);
        recycler.setAdapter(adapter);
    }

}
