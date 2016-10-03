package net.movilbox.dcsuruguay.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.movilbox.dcsuruguay.Model.MisBajasDetalle2;
import net.movilbox.dcsuruguay.R;

import java.util.List;

public class AdapterDialogMisBajas extends BaseAdapter {

    private Activity actx;
    List<MisBajasDetalle2> data;

    public AdapterDialogMisBajas(Activity actx, List<MisBajasDetalle2> data) {
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
    public MisBajasDetalle2 getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_mis_bajas_serie, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        MisBajasDetalle2 resMis = getItem(position);

        holder.serie.setText(resMis.getSerie());

        return convertView;
    }

    class ViewHolder {

        TextView serie;

        public ViewHolder(View view) {
            serie = (TextView) view.findViewById(R.id.text_serie);

            view.setTag(this);
        }
    }
}
