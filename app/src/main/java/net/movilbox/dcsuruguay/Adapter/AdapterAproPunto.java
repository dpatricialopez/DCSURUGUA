package net.movilbox.dcsuruguay.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.movilbox.dcsuruguay.Model.AprobarPunto;
import net.movilbox.dcsuruguay.R;

import java.util.List;

public class AdapterAproPunto extends BaseAdapter {

    private Activity actx;
    private List<AprobarPunto> data;

    public AdapterAproPunto(Activity actx, List<AprobarPunto> data) {
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
    public AprobarPunto getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_rutero_aprob, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        AprobarPunto referencias = getItem(position);

        holder.txtFecha.setText(String.format("Fecha: %1$s", referencias.getFecha()));
        holder.txtSolicitud.setText(String.format("Solicitud: %1$s", referencias.getSolicitud()));
        holder.txtEstado.setText(String.format("Estado: %1$s", referencias.getEstado()));
        holder.idPdv.setText(String.format("Id Pdv: %1$s", referencias.getIdpdv()));
        holder.txtVendedor.setText(String.format("Vendedor: %1$s", referencias.getNombre_vende()));

        return convertView;
    }

    class ViewHolder {

        TextView txtFecha;
        TextView txtSolicitud;
        TextView txtEstado;
        TextView idPdv;
        TextView txtVendedor;

        public ViewHolder(View view) {

            txtFecha = (TextView) view.findViewById(R.id.txtFecha);
            txtSolicitud = (TextView) view.findViewById(R.id.txtSolicitud);
            txtEstado = (TextView) view.findViewById(R.id.txtEstado);
            idPdv = (TextView) view.findViewById(R.id.idPdv);
            txtVendedor = (TextView) view.findViewById(R.id.txtVendedor);

            view.setTag(this);
        }
    }

}
