package net.movilbox.dcsuruguay.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.movilbox.dcsuruguay.Model.EntRefeCatalogo;
import net.movilbox.dcsuruguay.R;

import java.util.List;

public class AdapterListCatalogo extends BaseAdapter {

    private Activity actx;
    private List<EntRefeCatalogo> data;

    public AdapterListCatalogo(Activity actx, List<EntRefeCatalogo> data) {
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
    public EntRefeCatalogo getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_catalog_dos, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        EntRefeCatalogo referencias = getItem(position);

        holder.txt_serial.setText(String.format("%1$s", referencias.getDescripcion()));
        holder.txt_cantidadw.setText(String.format("%1$s", referencias.getCant()));

        return convertView;
    }

    class ViewHolder {

        TextView txt_serial;
        TextView txt_cantidadw;

        public ViewHolder(View view) {

            txt_serial = (TextView) view.findViewById(R.id.txt_serial);
            txt_cantidadw = (TextView) view.findViewById(R.id.txt_cantidadw);

            view.setTag(this);
        }
    }

}
