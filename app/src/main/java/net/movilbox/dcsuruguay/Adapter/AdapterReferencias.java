package net.movilbox.dcsuruguay.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.movilbox.dcsuruguay.Model.EntRefeSerial;
import net.movilbox.dcsuruguay.R;

import java.util.List;

public class AdapterReferencias extends BaseAdapter {

    private Activity actx;
    private List<EntRefeSerial> data;

    public AdapterReferencias(Activity actx, List<EntRefeSerial> data) {
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
    public EntRefeSerial getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_paquete, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        EntRefeSerial referencias = getItem(position);

        holder.txtSerial.setText(String.format("Serial: %1$s", referencias.getSerie()));
        holder.txtNombreRef.setText(String.format("Referencia: %1$s", referencias.getNombreReferencia()));

        return convertView;
    }

    class ViewHolder {

        TextView txtSerial;
        TextView txtNombreRef;

        public ViewHolder(View view) {

            txtSerial = (TextView) view.findViewById(R.id.txtSerial);
            txtNombreRef = (TextView) view.findViewById(R.id.txtNombreRef);

            view.setTag(this);
        }
    }

}
