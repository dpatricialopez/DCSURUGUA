package net.movilbox.dcsuruguay.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.movilbox.dcsuruguay.R;

/**
 * Created by germangarcia on 19/07/16.
 */
public class RowViewHolderCatalog extends RecyclerView.ViewHolder {

    public TextView txtNomCatalogo;
    public ImageView img_producto;
    public Button btnpedido;

    public RowViewHolderCatalog(View itemView, Context context) {
        super(itemView);

        this.txtNomCatalogo = (TextView) itemView.findViewById(R.id.txtNomCatalogo);
        this.btnpedido = (Button) itemView.findViewById(R.id.btnpedido);

        this.img_producto = (ImageView) itemView.findViewById(R.id.img_producto);

    }

}
