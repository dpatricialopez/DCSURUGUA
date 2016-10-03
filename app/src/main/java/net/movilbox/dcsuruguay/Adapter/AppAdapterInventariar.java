package net.movilbox.dcsuruguay.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import net.movilbox.dcsuruguay.Activity.SmoothCheckBox;
import net.movilbox.dcsuruguay.Model.InventariarProducto;
import net.movilbox.dcsuruguay.R;

import java.util.List;

public class AppAdapterInventariar extends BaseAdapter {

    private Activity actx;
    List<InventariarProducto> data;
    private boolean check;

    public AppAdapterInventariar(Activity actx, List<InventariarProducto> data, boolean check) {
        this.actx = actx;
        this.data = data;
        this.check = check;

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
    public InventariarProducto getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.items_inventariar, null);
            new ViewHolder(convertView);
        }

        final ViewHolder holder = (ViewHolder) convertView.getTag();
        final InventariarProducto invePro = getItem(position);

        holder.serie.setText("Producto: " + invePro.getProducto());
        holder.fecha.setText("Serie: " + invePro.getSerie());

        if (check) {
            holder.checkBox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                    data.get(position).isCheck = isChecked;
                }
            });
            holder.checkBox.setChecked(data.get(position).isCheck);
        } else {
            holder.checkBox.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    class ViewHolder {

        TextView serie;
        TextView fecha;
        SmoothCheckBox checkBox;


        public ViewHolder(View view) {
            serie = (TextView) view.findViewById(R.id.txt_serie);
            fecha = (TextView) view.findViewById(R.id.txt_fecha);
            checkBox = (SmoothCheckBox) view.findViewById(R.id.checkBox);

            view.setTag(this);
        }
    }
}
