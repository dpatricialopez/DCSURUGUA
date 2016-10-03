package net.movilbox.dcsuruguay.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import net.movilbox.dcsuruguay.Activity.ActListCatalogo;
import net.movilbox.dcsuruguay.Model.EntCatalogo;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.Model.RowViewHolderCatalog;
import net.movilbox.dcsuruguay.R;

import java.util.List;

public class AdapterRecyclerCatalogo extends RecyclerView.Adapter<RowViewHolderCatalog> {

    private Activity context;
    private List<EntCatalogo> responseHomeList;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader1;
    private DisplayImageOptions options1;
    private EntLisSincronizar entLisSincronizar;

    public AdapterRecyclerCatalogo(Activity context, List<EntCatalogo> responseHomeList, EntLisSincronizar entLisSincronizar) {
        super();
        this.context = context;
        this.responseHomeList = responseHomeList;
        this.entLisSincronizar = entLisSincronizar;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        imageLoader1 = ImageLoader.getInstance();
        imageLoader1.init(config);

        //Setup options for ImageLoader so it will handle caching for us.
        options1 = new DisplayImageOptions.Builder()
                .cacheInMemory()
                .cacheOnDisc()
                .build();

    }

    @Override
    public RowViewHolderCatalog onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalog, parent, false);
        return new RowViewHolderCatalog(v, context);

    }

    @Override
    public void onBindViewHolder(final RowViewHolderCatalog holder, final int position) {

        final EntCatalogo items = responseHomeList.get(position);

        holder.txtNomCatalogo.setText(items.getDescripcion());

        loadeImagenView(holder.img_producto, items.getImg_pri_catalogo());

        holder.btnpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(context, ActListCatalogo.class);
                bundle.putSerializable("value", items);
                bundle.putSerializable("value2", entLisSincronizar);
                bundle.putInt("idRuta", entLisSincronizar.getZona());
                intent.putExtras(bundle);
                context.startActivityForResult(intent, 1);
            }
        });

    }

    private void loadeImagenView(ImageView img_producto, String img) {

        ImageLoadingListener listener = new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String arg0, View arg1) {
                // TODO Auto-generated method stub
                //Inicia metodo
                //holder.progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            public void onLoadingCancelled(String arg0, View arg1) {
                // TODO Auto-generated method stub
                //Cancelar
                //holder.progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                //Completado
                //holder.progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                // TODO Auto-generated method stub
                //Error al cargar la imagen.
                //holder.progressBar.setVisibility(View.GONE);
            }
        };
        imageLoader1.displayImage(img, img_producto, options1, listener);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (responseHomeList == null) {
            return 0;
        } else {
            return responseHomeList.size();
        }
    }

}
