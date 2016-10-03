package net.movilbox.dcsuruguay.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.movilbox.dcsuruguay.Model.EntListReferencias;
import net.movilbox.dcsuruguay.R;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterInventario extends BaseAdapter {

    private Activity actx;
    List<EntListReferencias> data;
    private DecimalFormat format;

    public AdapterInventario(Activity actx, List<EntListReferencias> data) {
        this.actx = actx;
        this.data = data;

        format = new DecimalFormat("#,###.##");

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
    public EntListReferencias getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_inventario, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        EntListReferencias entListReferencias = getItem(position);

        holder.txt_referencia.setText(String.format("Referencia: %1$s", entListReferencias.getNomPro()));

        holder.txt_cantidad.setText(String.format("Cantidad: %1$s", entListReferencias.getCantidad()));

        return convertView;
    }

    class ViewHolder {

        TextView txt_referencia;
        TextView txt_cantidad;

        public ViewHolder(View view) {

            txt_referencia = (TextView) view.findViewById(R.id.txt_referencia);
            txt_cantidad = (TextView) view.findViewById(R.id.txt_cantidad);

            view.setTag(this);
        }
    }

}
