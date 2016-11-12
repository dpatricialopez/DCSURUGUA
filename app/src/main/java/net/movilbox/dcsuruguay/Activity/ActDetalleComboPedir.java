package net.movilbox.dcsuruguay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import net.movilbox.dcsuruguay.Adapter.AdapterListReferencia;
import net.movilbox.dcsuruguay.Model.ReferenciasCombos;
import net.movilbox.dcsuruguay.R;

public class ActDetalleComboPedir extends AppCompatActivity {

    private Bundle bundle;
    private ReferenciasCombos mDescribable = new ReferenciasCombos();
    private SwipeMenuListView mListView;
    private AdapterListReferencia adapterListReferencia;
    private int idPos;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_combo_pedir);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (ReferenciasCombos) bundle.getSerializable("value");
            idPos = bundle.getInt("idPos");
            idUser = bundle.getInt("idUser");

            mListView = (SwipeMenuListView) findViewById(R.id.listView);

            adapterListReferencia = new AdapterListReferencia(this, mDescribable.getReferenciaLis(), idPos, idUser, mDescribable.getId());
            mListView.setAdapter(adapterListReferencia);

        }

    }

}
