package net.movilbox.dcsuruguay.Adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.ActMapsPunto;
import net.movilbox.dcsuruguay.Activity.ActEditarPunto;
import net.movilbox.dcsuruguay.Activity.ActMarcarVisita;
import net.movilbox.dcsuruguay.Controller.ControllerPunto;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.Model.HolderRutero;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;

import java.io.Serializable;
import java.util.List;

public class RecyclerRutero extends RecyclerView.Adapter<HolderRutero> {

    private Activity context;
    private List<EntLisSincronizar> entRuteroList;
    private ConnectionDetector connectionDetector;
    private ControllerPunto controllerPunto;

    public RecyclerRutero(Activity context, List<EntLisSincronizar> entRuteroList) {
        super();
        this.context = context;
        this.entRuteroList = entRuteroList;
    }

    @Override
    public HolderRutero onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rutero, parent, false);

        return new HolderRutero(v, context);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final HolderRutero holder, final int position) {

        final EntLisSincronizar rutero = entRuteroList.get(position);

        connectionDetector = new ConnectionDetector(context);
        controllerPunto = new ControllerPunto(context);

        holder.txtNombrePunto.setText(rutero.getNombre_punto().toUpperCase());

        String direccion;

        if (rutero.getTexto_direccion() != null) {

            if (rutero.getTexto_direccion().trim().isEmpty())
                direccion = "N/A";
            else
                direccion = rutero.getTexto_direccion();

        } else {
            direccion = "N/A";
        }

        holder.txtDireccion.setText(direccion);

        holder.txtStock.setText(String.format("%1$s", rutero.getIdpos()));

        holder.imageViewEstado.setImageResource(R.drawable.ic_help_black_24dp);

        holder.itemRutero.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_item_rutero));

        //Punto visitado y con venta
        if (rutero.getEstado_visita() == 1) {
            holder.imageViewEstado.setImageResource(R.drawable.ic_check_circle_black_24dp);
            holder.itemRutero.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_item_rutero_si_venta));
        }

        //Punto con visita pero no tiene venta
        if (rutero.getEstado_visita() == 2) {
            holder.imageViewEstado.setImageResource(R.drawable.ic_offline_pin_black_24dp);
            holder.itemRutero.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_item_rutero_no_venta));
        }

        if (rutero.getDias_inve_combo() < rutero.getDias_inve_sim()) {

            if (rutero.getDias_inve_combo() == 0)
                holder.txtDiasInven.setText("D. Inve N/A");
            else
                holder.txtDiasInven.setText("D. Inve "+rutero.getDias_inve_combo());

        } else {

            if (rutero.getDias_inve_sim() == 0)
                holder.txtDiasInven.setText("D. Inve N/A");
            else
                holder.txtDiasInven.setText("D. Inve "+rutero.getDias_inve_sim());

        }

        int stockCombo;
        int stockSimcard;
        boolean quiebre = false;

        if (rutero.getStock_combo() < rutero.getStock_seguridad_combo()) {
            stockCombo = rutero.getStock_seguridad_combo();
        } else {
            stockCombo = rutero.getStock_combo();
            quiebre = true;
        }

        if (rutero.getStock_sim() < rutero.getStock_seguridad_sim()) {
            stockSimcard = rutero.getStock_seguridad_sim();
        } else {
            stockSimcard = rutero.getStock_sim();
            quiebre = true;
        }

        if (stockCombo < stockSimcard) {
            holder.txtStock.setText(String.format("Stock %1$s", stockSimcard));
        } else {
            holder.txtStock.setText(String.format("Stock %1$s", stockCombo));
        }

        holder.imgquiebre.setVisibility(View.INVISIBLE);
        if (quiebre) {
            holder.imgquiebre.setVisibility(View.VISIBLE);
        }

        holder.imgMap.setVisibility(View.VISIBLE);
        if(entRuteroList.get(position).getLatitud() == 0) {
            holder.imgMap.setVisibility(View.INVISIBLE);
        }else{

            holder.imgMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, ActMapsPunto.class);
                    bundle.putSerializable("values", rutero);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }


       // holder.imgMap.setOnClickListener(this);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LayoutInflater inflater = context.getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_detalle_punto, null);

                TextView txtDireccion = (TextView) dialoglayout.findViewById(R.id.txtDireccion);
                txtDireccion.setText(entRuteroList.get(position).getTexto_direccion());

                TextView txtDepartamento = (TextView) dialoglayout.findViewById(R.id.txtDepartamento);
                txtDepartamento.setText(entRuteroList.get(position).getNombreDepartamento());

                TextView txtCiudad = (TextView) dialoglayout.findViewById(R.id.txtCiudad);
                txtCiudad.setText(entRuteroList.get(position).getNombreCiudad());

                TextView txtCircuito = (TextView) dialoglayout.findViewById(R.id.txtCircuito);
                txtCircuito.setText(entRuteroList.get(position).getNombreTerritorio());

                TextView txtRuta = (TextView) dialoglayout.findViewById(R.id.txtRuta);
                txtRuta.setText(entRuteroList.get(position).getNombreZona());

                TextView txtTelefono = (TextView) dialoglayout.findViewById(R.id.txtTelefono);
                txtTelefono.setText(entRuteroList.get(position).getTelefono());

                TextView txtDias = (TextView) dialoglayout.findViewById(R.id.txtDias);
                txtDias.setText(entRuteroList.get(position).getDetalle());

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setTitle(entRuteroList.get(position).getNombre_punto().toUpperCase());
                builder.setView(dialoglayout).setPositiveButton("Visitar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Visitar Punto.
                        visitarPunto(entRuteroList.get(position).getIdpos());
                    }
                }).setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Editar Punto.
                        if (connectionDetector.isConnected()) {

                            EntLisSincronizar lisSincronizarList23 = controllerPunto.getRuteroVendedor(entRuteroList.get(position).getIdpos());

                            Bundle bundle = new Bundle();
                            Intent intent = new Intent(context, ActEditarPunto.class);
                            bundle.putSerializable("value", lisSincronizarList23);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        } else {
                            TastyToast.makeText(context, "El modulo solo funciona en modo ONLINE", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                        }
                    }
                }).setNeutralButton("Cerrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (entRuteroList == null) {
            return 0;
        } else {
            return entRuteroList.size();
        }
    }

    private void visitarPunto(int idpos) {

        EntLisSincronizar lisSincronizarList = controllerPunto.getRuteroVendedor(idpos);

        Bundle bundle = new Bundle();
        Intent intent = new Intent(context, ActMarcarVisita.class);
        bundle.putSerializable("value", lisSincronizarList);
        bundle.putString("page", "marcar_rutero");
        intent.putExtras(bundle);
        context.startActivity(intent);

    }
}
