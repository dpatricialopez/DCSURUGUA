package net.movilbox.dcsuruguay.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.movilbox.dcsuruguay.Model.EntMisVentas;
import net.movilbox.dcsuruguay.R;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterMisVentas extends BaseAdapter {

    private Activity actx;
    List<EntMisVentas> data;
    private DecimalFormat format;

    public AdapterMisVentas(Activity actx, List<EntMisVentas> data) {
        this.actx = actx;
        this.data = data;
        format = new DecimalFormat("#,###");
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
    public EntMisVentas getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_mis_ventas, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        EntMisVentas resMis = getItem(position);

        holder.txtFecha.setText(String.format("Fecha: %s", resMis.getFecha()));
        holder.txtCantidad.setText(String.format("Cantidad: %s", resMis.getCantidad()));
        holder.txtValor.setText(String.format("Valor: $ %s", format.format(resMis.getValor())));

        return convertView;
    }

    class ViewHolder {

        TextView txtFecha;
        TextView txtCantidad;
        TextView txtValor;

        public ViewHolder(View view) {
            txtFecha = (TextView) view.findViewById(R.id.txtFecha);
            txtCantidad = (TextView) view.findViewById(R.id.txtCantidad);
            txtValor = (TextView) view.findViewById(R.id.txtValor);

            view.setTag(this);
        }
    }
}
