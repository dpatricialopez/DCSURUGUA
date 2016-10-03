package net.movilbox.dcsuruguay.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.movilbox.dcsuruguay.R;

/**
 * Created by germangarcia on 8/07/16.
 */
public class HolderRutero extends RecyclerView.ViewHolder {

    public ImageView imageViewEstado;
    public TextView txtNombrePunto;
    public TextView txtDireccion;
    public TextView txtStock;
    public TextView txtDiasInven;
    public ImageView imgMap;
    public RelativeLayout itemRutero;
    public ImageView imgquiebre;

    public HolderRutero(View itemView, Context context) {

        super(itemView);

        this.imageViewEstado = (ImageView) itemView.findViewById(R.id.imgIndicador);
        this.imgquiebre = (ImageView) itemView.findViewById(R.id.imgquiebre);
        this.txtNombrePunto = (TextView) itemView.findViewById(R.id.txtNombrePunto);
        this.txtDireccion = (TextView) itemView.findViewById(R.id.txtDireccion);
        this.txtStock = (TextView) itemView.findViewById(R.id.txtStock);
        this.txtDiasInven = (TextView) itemView.findViewById(R.id.txtDiasInven);
        this.imgMap = (ImageView) itemView.findViewById(R.id.imgMap);
        this.itemRutero = (RelativeLayout) itemView.findViewById(R.id.itemRutero);

    }

}
