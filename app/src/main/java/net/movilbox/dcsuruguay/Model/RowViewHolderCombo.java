package net.movilbox.dcsuruguay.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.movilbox.dcsuruguay.R;


public class RowViewHolderCombo extends RecyclerView.ViewHolder {

    public TextView txtReferencia;
    public TextView txtValorR;
    public TextView txtValorR2;
    public TextView txtcantidadGlo;
    public ImageView img_producto;
    public Button btnpedido;

    public RowViewHolderCombo(View itemView, Context context) {
        super(itemView);

        this.txtReferencia = (TextView) itemView.findViewById(R.id.txtReferencia);
        this.txtValorR = (TextView) itemView.findViewById(R.id.txtValorR);
        this.txtValorR2 = (TextView) itemView.findViewById(R.id.txtValorR2);
        this.txtcantidadGlo = (TextView) itemView.findViewById(R.id.txtcantidadGlo);
        this.btnpedido = (Button) itemView.findViewById(R.id.btnpedido);

        this.img_producto = (ImageView) itemView.findViewById(R.id.img_producto);

    }

}
