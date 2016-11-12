package net.movilbox.dcsuruguay.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import net.movilbox.dcsuruguay.Model.ReferenciasCombos;
import net.movilbox.dcsuruguay.R;

public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    LayoutInflater inflater;
    ReferenciasCombos referenciasCombos;
    Context context;
    private ImageLoader imageLoader1;
    private DisplayImageOptions options1;

    public ViewPagerAdapter(Context context, ReferenciasCombos referenciasCombos) {
        this.context = context;
        this.referenciasCombos = referenciasCombos;

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
    public int getCount() {

        if (referenciasCombos.getEstandarList() == null) {
            return 0;
        } else {
            return referenciasCombos.getEstandarList().size();
        }

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView img_producto;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container, false);

        // Locate the ImageView in viewpager_item.xml
        img_producto = (ImageView) itemView.findViewById(R.id.displayImagen);
        // Capture position and set to the ImageView

        loadeImagenView(img_producto, referenciasCombos.getEstandarList().get(position).getDescripcion());

        // Add viewpager_item.xml to ViewPager
        container.addView(itemView);

        return itemView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        container.removeView((RelativeLayout) object);

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

}