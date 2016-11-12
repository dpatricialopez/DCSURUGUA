package net.movilbox.dcsuruguay.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.movilbox.dcsuruguay.R;


public class RowViewHolderSimcard extends RecyclerView.ViewHolder  {

    public TextView txt_referemcia;
    public TextView txtStock;
    public Button btnCatalogoSim;
    public TextView txtCantidadPedida;
    public TextView txtInven;
    public TextView txtprecio;
    public ImageView imgQuiebre;
    public ImageView img_producto;

    public RowViewHolderSimcard(View itemView, Context context) {
        super(itemView);

        this.txt_referemcia = (TextView) itemView.findViewById(R.id.txt_referemcia);
        this.txtprecio = (TextView) itemView.findViewById(R.id.txtprecio);
        this.txtStock = (TextView) itemView.findViewById(R.id.txtStock);
        this.txtCantidadPedida = (TextView) itemView.findViewById(R.id.txtCantidadPedida);
        this.txtInven = (TextView) itemView.findViewById(R.id.txtInven);
        this.btnCatalogoSim = (Button) itemView.findViewById(R.id.btnCatalogoSim);
        this.imgQuiebre = (ImageView) itemView.findViewById(R.id.imgQuiebre);
        this.img_producto = (ImageView) itemView.findViewById(R.id.img_producto);

    }

}
