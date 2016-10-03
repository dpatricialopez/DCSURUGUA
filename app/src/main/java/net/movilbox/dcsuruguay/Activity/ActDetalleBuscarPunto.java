package net.movilbox.dcsuruguay.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.movilbox.dcsuruguay.Adapter.RecyclerItemClickListener;
import net.movilbox.dcsuruguay.Adapter.RecyclerRutero;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.R;

import java.util.List;

public class ActDetalleBuscarPunto extends BaseActivity {

    private RecyclerView recycler;
    private List<EntLisSincronizar> entLisSincronizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_buscar_punto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            entLisSincronizar = (List<EntLisSincronizar>) bundle.getSerializable("value");
        }

        recycler = (RecyclerView) findViewById(R.id.recycler_view);
        recycler.setHasFixedSize(true);

        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        RecyclerRutero adapter = new RecyclerRutero(this, entLisSincronizar);
        recycler.setAdapter(adapter);

        recycler.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_detalle_punto, null);

                TextView txtDireccion = (TextView) dialoglayout.findViewById(R.id.txtDireccion);
                txtDireccion.setText(entLisSincronizar.get(position).getTexto_direccion());

                TextView txtDepartamento = (TextView) dialoglayout.findViewById(R.id.txtDepartamento);
                txtDepartamento.setText(entLisSincronizar.get(position).getNombreDepartamento());

                TextView txtCiudad = (TextView) dialoglayout.findViewById(R.id.txtCiudad);
                txtCiudad.setText(entLisSincronizar.get(position).getNombreCiudad());

                TextView txtCircuito = (TextView) dialoglayout.findViewById(R.id.txtCircuito);
                txtCircuito.setText(entLisSincronizar.get(position).getNombreTerritorio());

                TextView txtRuta = (TextView) dialoglayout.findViewById(R.id.txtRuta);
                txtRuta.setText(entLisSincronizar.get(position).getNombreZona());

                TextView txtTelefono = (TextView) dialoglayout.findViewById(R.id.txtTelefono);
                txtTelefono.setText(entLisSincronizar.get(position).getTelefono());

                TextView txtDias = (TextView) dialoglayout.findViewById(R.id.txtDias);
                txtDias.setText(entLisSincronizar.get(position).getDetalle());

                AlertDialog.Builder builder = new AlertDialog.Builder(ActDetalleBuscarPunto.this);
                builder.setCancelable(false);
                builder.setTitle(entLisSincronizar.get(position).getNombre_punto().toUpperCase());
                builder.setView(dialoglayout).setPositiveButton("Visitar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Visitar Punto.
                        //visitarPunto(lisSincronizarList.get(position).getIdpos());
                    }
                }).setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Editar Punto.
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent(ActDetalleBuscarPunto.this, ActEditarPunto.class);
                        bundle.putSerializable("value", entLisSincronizar.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }).setNeutralButton("Cerrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }

        }));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
