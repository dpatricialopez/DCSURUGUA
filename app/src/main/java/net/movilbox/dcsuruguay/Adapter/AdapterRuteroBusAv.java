package net.movilbox.dcsuruguay.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.R;

import java.util.List;

public class AdapterRuteroBusAv extends BaseAdapter {

    private Activity actx;
    private List<EntLisSincronizar> data;

    public AdapterRuteroBusAv(Activity actx, List<EntLisSincronizar> data) {
        this.actx = actx;
        this.data = data;
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
    public EntLisSincronizar getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_list_rutero_avanzado, null);
            new ViewHolder(convertView);
        }

        final ViewHolder holder = (ViewHolder) convertView.getTag();

        final EntLisSincronizar responseHome = getItem(position);

        holder.txtIdPos.setText(String.valueOf(responseHome.getIdpos()));
        holder.txtNombrePunto.setText(responseHome.getRazon());
        holder.txtNombreCliente.setText(responseHome.getNombre_cli());

        String direccion;

        if (responseHome.getDireccion() != null) {
            if (responseHome.getDireccion().trim().isEmpty())
                direccion = "N/A";
            else
                direccion = responseHome.getDireccion();

            holder.txtDireccion.setText(direccion);
        }

        holder.imgIndicador.setImageResource(R.drawable.ic_help_black_24dp);

        //Punto visitado y con venta
        if (responseHome.getEstado_visita() == 1)
            holder.imgIndicador.setImageResource(R.drawable.ic_check_circle_black_24dp);

        //Punto con visita pero no tiene venta
        if (responseHome.getEstado_visita() == 2)
            holder.imgIndicador.setImageResource(R.drawable.ic_offline_pin_black_24dp);

        return convertView;

    }

    class ViewHolder {

        TextView txtNombrePunto;
        TextView txtDireccion;
        TextView txtIdPos;
        TextView txtNombreCliente;
        ImageView imgIndicador;


        public ViewHolder(View view) {
            txtNombrePunto = (TextView) view.findViewById(R.id.txtNombrePunto);
            txtDireccion = (TextView) view.findViewById(R.id.txtDireccion);
            txtIdPos = (TextView) view.findViewById(R.id.txtIdPos);
            txtNombreCliente = (TextView) view.findViewById(R.id.txtNombreCliente);
            imgIndicador = (ImageView) view.findViewById(R.id.imgIndicador);

            view.setTag(this);
        }
    }

}
