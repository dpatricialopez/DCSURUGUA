package net.movilbox.dcsuruguay.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import net.movilbox.dcsuruguay.Model.EntCarritoVenta;
import net.movilbox.dcsuruguay.R;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterCarrito extends BaseAdapter {

    private Activity actx;
    List<EntCarritoVenta> data;
    private DecimalFormat format;

    public AdapterCarrito(Activity actx, List<EntCarritoVenta> data) {
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
    public EntCarritoVenta getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_carrito, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        EntCarritoVenta entCarritoVenta = getItem(position);

        holder.txt_referencia.setText(String.format("Referencia: %1$s", entCarritoVenta.getIdReferencia()));

        holder.txt_serial.setText(String.format("Serie/Imei: %1$s", entCarritoVenta.getSerie()));

        if (entCarritoVenta.getIdPaquete() == 0)
            holder.txt_paquete.setText(String.format("Paquete: %1$s", "Sin paquete"));
        else
            holder.txt_paquete.setText(String.format("Paquete: %1$s", entCarritoVenta.getIdPaquete()));

        if (entCarritoVenta.getTipoVenta() == 1) {
            holder.txt_valor.setText(String.format("Valor: $ %1$s", format.format(entCarritoVenta.getValorDirecto())));
        } else {
            holder.txt_valor.setText(String.format("Valor: $ %1$s", format.format(entCarritoVenta.getValorRefe())));
        }

        if (entCarritoVenta.getTipoProducto() == 1) {
            //Simcard.
            holder.txtTipoProducto.setText("SIMCARD");
        } else {
            //Equipo.
            holder.txtTipoProducto.setText("EQUIPO");
        }

        holder.txtCantidad.setText(String.format("Cantidad: %1$s", entCarritoVenta.getCantidad()));

        return convertView;
    }

    public void setData(List<EntCarritoVenta> list){
        this.data = list;
        notifyDataSetChanged();
    }

    class ViewHolder {

        TextView txt_referencia;
        TextView txt_serial;
        TextView txt_paquete;
        TextView txt_valor;
        TextView txtTipoProducto;
        TextView txtCantidad;

        public ViewHolder(View view) {

            txt_referencia = (TextView) view.findViewById(R.id.txt_referencia);
            txt_serial = (TextView) view.findViewById(R.id.txt_serial);
            txt_paquete = (TextView) view.findViewById(R.id.txt_paquete);
            txt_valor = (TextView) view.findViewById(R.id.txt_valor);
            txtTipoProducto = (TextView) view.findViewById(R.id.txtTipoProducto);
            txtCantidad = (TextView) view.findViewById(R.id.txtCantidad);

            view.setTag(this);
        }
    }

}
