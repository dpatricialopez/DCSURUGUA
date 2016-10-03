package net.movilbox.dcsuruguay.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import net.movilbox.dcsuruguay.Adapter.AdapterMisVentas;
import net.movilbox.dcsuruguay.Adapter.ExpandableListAdapterVenta;
import net.movilbox.dcsuruguay.Adapter.ExpandableListDataPumpVenta;
import net.movilbox.dcsuruguay.Model.DetalleVenta2;
import net.movilbox.dcsuruguay.Model.EntMisVentasList;
import net.movilbox.dcsuruguay.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActReporteMisVentas extends BaseActivity {

    private EntMisVentasList mDescribable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_mis_ventas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (EntMisVentasList) bundle.getSerializable("value");
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
        AdapterMisVentas adapterMisPedidos = new AdapterMisVentas(this, mDescribable.getEntMisVentasList());
        mListView.setAdapter(adapterMisPedidos);

        // test item long click
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_detalle_venta, null);

                ExpandableListView expandableListView = (ExpandableListView) dialoglayout.findViewById(R.id.expandableListView);

                HashMap<String, List<DetalleVenta2>> expandableListDetail = ExpandableListDataPumpVenta.getData(mDescribable.getEntMisVentasList().get(position).getDetalleList());
                ArrayList<String> expandableListTitle = new ArrayList<>(expandableListDetail.keySet());

                ExpandableListAdapterVenta expandableListAdapter = new ExpandableListAdapterVenta(ActReporteMisVentas.this, expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(ActReporteMisVentas.this);
                builder.setCancelable(false);
                builder.setTitle("Detalle Venta");
                builder.setView(dialoglayout).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });


    }


}
