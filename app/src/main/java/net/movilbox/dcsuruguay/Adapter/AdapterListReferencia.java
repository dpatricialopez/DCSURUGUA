package net.movilbox.dcsuruguay.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerTomarPedido;
import net.movilbox.dcsuruguay.DataBase.DBHelper;
import net.movilbox.dcsuruguay.Model.Referencia;
import net.movilbox.dcsuruguay.R;

import java.util.List;

import static net.movilbox.dcsuruguay.Model.ResponseVenta.getId_posStacti;

public class AdapterListReferencia extends BaseAdapter {

    private Activity actx;
    private List<Referencia> data;
    private ControllerTomarPedido mydb;
    private ImageLoader imageLoader1;
    private DisplayImageOptions options1;
    private int idPos;
    private int idUser;
    private int id_referencia;
    private ControllerLogin controllerLogin;

    public AdapterListReferencia(Activity actx, List<Referencia> data, int idPos, int idUser, int id_referencia) {
        this.actx = actx;
        this.data = data;
        this.idPos = idPos;
        this.idUser = idUser;
        this.id_referencia = id_referencia;

        controllerLogin = new ControllerLogin(actx);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(actx).build();
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
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    @Override
    public Referencia getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_list_carrito, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        final Referencia referencias = getItem(position);

        holder.imgQuiebre.setVisibility(View.GONE);
        if (referencias.getQuiebre() == 1) {
            holder.imgQuiebre.setVisibility(View.VISIBLE);
        }

        holder.txtStock.setText(String.format("STOCK  %s", referencias.getStock()));

        holder.txtDiasInven.setText(String.format("D. INV %s", (int) referencias.getDias_inve()));

        holder.txtReferencia.setText(referencias.getProducto());
        holder.txtPn.setText(String.format("%1$s", referencias.getPn()));

        holder.txtcantidad.setText(String.format("Cantidad %1$s", mydb.countReferenciaProducto(referencias.getId(), idPos, idUser)));

        holder.imageView2.setVisibility(View.GONE);
        holder.txtprecio.setVisibility(View.GONE);

        loadeImagenView(holder.profile_image, referencias.getUrl_imagen());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = actx.getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_sim_pedido, null);

                TextView txtCanSugerida = (TextView) dialoglayout.findViewById(R.id.txtCanSugerida);
                final EditText numeroCan = (EditText) dialoglayout.findViewById(R.id.editCantidad);

                txtCanSugerida.setText(String.format("%s", referencias.getPed_sugerido()));

                AlertDialog.Builder builder = new AlertDialog.Builder(actx);
                builder.setCancelable(false);
                builder.setTitle(referencias.getProducto());
                builder.setView(dialoglayout);

                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (isValidNumber(numeroCan.getText().toString())) {
                            Toast.makeText(actx, "La cantidad es un campo requerido", Toast.LENGTH_SHORT).show();
                        } else {
                            int cantidad_dig = Integer.parseInt(numeroCan.getText().toString());
                            if (cantidad_dig <= 0) {
                                Toast.makeText(actx, "la cantidad digitada tiene que ser mayor a 0", Toast.LENGTH_SHORT).show();
                            } else {
                                //Guardar

                                referencias.setCantidadPedida(cantidad_dig);
                                referencias.setId_punto(getId_posStacti());
                                referencias.setId_usuario(controllerLogin.getUserLogin().getId());
                                referencias.setTipo_producto(2); // Combo
                                referencias.setUrl_imagen(referencias.getUrl_imagen());
                                referencias.setReferencia(id_referencia);

                                String resultado = mydb.insertCarritoPedidoCombos(referencias);

                                if (resultado.equals("inserto")) {
                                    Toast.makeText(actx, "Pedido guardado correctamente", Toast.LENGTH_SHORT).show();
                                } else if (resultado.equals("no inserto")) {
                                    Toast.makeText(actx, "Problemas al guardar el pedido", Toast.LENGTH_SHORT).show();
                                } else if (resultado.equals("update")) {
                                    Toast.makeText(actx, "Pedido actualizado correctamente", Toast.LENGTH_SHORT).show();
                                } else if (resultado.equals("no update")) {
                                    Toast.makeText(actx, "Pedido no se pudo actualizar", Toast.LENGTH_SHORT).show();
                                }

                                InputMethodManager imm = (InputMethodManager) actx.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(numeroCan.getWindowToken(), 0);

                            }
                        }
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        InputMethodManager imm = (InputMethodManager) actx.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(numeroCan.getWindowToken(), 0);
                    }
                });

                builder.show();

                InputMethodManager imm = (InputMethodManager) actx.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            }
        });

        return convertView;

    }

    private boolean isValidNumber(String number) {
        return number == null || number.length() == 0;
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

    class ViewHolder {

        TextView txtReferencia;
        TextView txtcantidad;
        TextView txtPn;
        TextView txtprecio;
        TextView txtStock;
        TextView txtDiasInven;
        ImageView profile_image;
        ImageView imageView2;
        ImageView imgQuiebre;

        public ViewHolder(View view) {

            txtReferencia = (TextView) view.findViewById(R.id.txtReferencia);
            txtPn = (TextView) view.findViewById(R.id.txtPn);
            txtcantidad = (TextView) view.findViewById(R.id.txtcantidad);
            txtprecio = (TextView) view.findViewById(R.id.txtprecio);

            txtStock = (TextView) view.findViewById(R.id.txtStock);
            txtDiasInven = (TextView) view.findViewById(R.id.txtDiasInven);

            profile_image = (ImageView) view.findViewById(R.id.profile_image);
            imageView2 = (ImageView) view.findViewById(R.id.imageView2);
            imgQuiebre = (ImageView) view.findViewById(R.id.imgquiebre);

            view.setTag(this);
        }
    }

}
