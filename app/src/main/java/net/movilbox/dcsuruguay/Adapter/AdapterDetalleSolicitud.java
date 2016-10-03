package net.movilbox.dcsuruguay.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import net.movilbox.dcsuruguay.Model.EntSolicitudVendedor;
import net.movilbox.dcsuruguay.R;

import java.util.List;

public class AdapterDetalleSolicitud extends BaseAdapter {

    private Activity actx;
    private List<EntSolicitudVendedor> data;

    public AdapterDetalleSolicitud(Activity actx, List<EntSolicitudVendedor> data) {
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
    public EntSolicitudVendedor getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_detalle_solicitud, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final EntSolicitudVendedor referencias = getItem(position);

        holder.txtIdSolicitud.setText(String.format("Solicitud: %s", referencias.getId_soli()));
        holder.txtIdPos.setText(String.format("Punto: %s", referencias.getIdpdv()));
        holder.txtFecha.setText(String.format("%s", referencias.getFecha()));
        holder.txtSolicitud.setText(String.format("%s", referencias.getSolicitud()));
        holder.txtNombrePunto.setText(String.format("%s", referencias.getNombre_punto()));
        holder.txtNombreVendedor.setText(String.format("%s", referencias.getNombre_vende()));

        return convertView;

    }

    class ViewHolder {

        TextView txtIdSolicitud;
        TextView txtIdPos;
        TextView txtFecha;
        TextView txtSolicitud;
        TextView txtNombrePunto;
        TextView txtNombreVendedor;


        public ViewHolder(View view) {

            txtIdSolicitud = (TextView) view.findViewById(R.id.txtIdSolicitud);
            txtIdPos = (TextView) view.findViewById(R.id.txtIdPos);
            txtFecha = (TextView) view.findViewById(R.id.txtFecha);
            txtSolicitud = (TextView) view.findViewById(R.id.txtSolicitud);
            txtNombrePunto = (TextView) view.findViewById(R.id.txtNombrePunto);
            txtNombreVendedor = (TextView) view.findViewById(R.id.txtNombreVendedor);

            view.setTag(this);
        }
    }

}
