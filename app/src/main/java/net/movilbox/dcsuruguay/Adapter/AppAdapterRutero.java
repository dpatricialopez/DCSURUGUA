package net.movilbox.dcsuruguay.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import net.movilbox.dcsuruguay.Model.ResponseHome;
import net.movilbox.dcsuruguay.R;

import java.util.List;

public class AppAdapterRutero extends BaseAdapter {

    private Activity actx;
    private List<ResponseHome> data;
    private String rutero;

    public AppAdapterRutero(Activity actx, List<ResponseHome> data, String rutero) {
        this.actx = actx;
        this.data = data;
        this.rutero = rutero;
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
    public ResponseHome getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_list_rutero_inicio, null);
            new ViewHolder(convertView);
        }

         final ViewHolder holder = (ViewHolder) convertView.getTag();

         final ResponseHome responseHome = getItem(position);

        holder.txtNombrePunto.setText(responseHome.getRazon());

        holder.imgIndicadorvisita.setVisibility(View.VISIBLE);
        if (rutero.equals("rutero"))
            holder.imgIndicadorvisita.setVisibility(View.GONE);

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
        if (responseHome.getTipo_visita() == 1)
            holder.imgIndicador.setImageResource(R.drawable.ic_check_circle_black_24dp);

        //Punto con visita pero no tiene venta
        if (responseHome.getTipo_visita() == 2)
            holder.imgIndicador.setImageResource(R.drawable.ic_offline_pin_black_24dp);

        // Punto sin tener ninguna visita
        /*if(mydb.getUserLogin().getPerfil() == 3) {
            holder.imgIndicador.setImageResource(R.drawable.ic_help_black_24dp);
        }*/

        if (responseHome.getDias_inve_combo() < responseHome.getDias_inve_sim()) {

            if (responseHome.getDias_inve_combo() == 0)
                holder.txtDiasInven.setText("D. Inve N/A");
            else
                holder.txtDiasInven.setText("D. Inve "+responseHome.getDias_inve_combo());

        } else {

            if (responseHome.getDias_inve_sim() == 0)
                holder.txtDiasInven.setText("D. Inve N/A");
            else
                holder.txtDiasInven.setText("D. Inve "+responseHome.getDias_inve_sim());

        }

        int stockCombo;
        int stockSimcard;
        boolean quiebre = false;

        if (responseHome.getStock_combo() < responseHome.getStock_seguridad_combo()) {
            stockCombo = responseHome.getStock_seguridad_combo();
        } else {
            stockCombo = responseHome.getStock_combo();
            quiebre = true;
        }

        if (responseHome.getStock_sim() < responseHome.getStock_seguridad_sim()) {
            stockSimcard = responseHome.getStock_seguridad_sim();
        } else {
            stockSimcard = responseHome.getStock_sim();
            quiebre = true;
        }

        if (stockCombo < stockSimcard) {
            holder.txtStock.setText(String.format("Stock %1$s", stockSimcard));
        } else {
            holder.txtStock.setText(String.format("Stock %1$s", stockCombo));
        }

        holder.imgquiebre.setVisibility(View.INVISIBLE);
        if (quiebre) {
            holder.imgquiebre.setVisibility(View.VISIBLE);
        }

        holder.imgMap.setVisibility(View.VISIBLE);
        if(responseHome.getLatitud() == 0) {
            holder.imgMap.setVisibility(View.INVISIBLE);
        }else{

            holder.imgMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Bundle bundle = new Bundle();
                    Intent intent = new Intent(v.getContext(), ActMapsPunto.class);
                    bundle.putSerializable("values",responseHome);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);*/
                }
            });
        }

        return convertView;

    }

    class ViewHolder {

        TextView txtNombrePunto;
        TextView txtDireccion;
        TextView txtDiasInven;
        TextView txtStock;
        ImageView imgIndicador;
        ImageView imgquiebre;
        ImageView imgMap;
        ImageView imgIndicadorvisita;

        public ViewHolder(View view) {
            txtNombrePunto = (TextView) view.findViewById(R.id.txtNombrePunto);
            txtDireccion = (TextView) view.findViewById(R.id.txtDireccion);
            txtDiasInven = (TextView) view.findViewById(R.id.txtDiasInven);
            txtStock = (TextView) view.findViewById(R.id.txtStock);

            imgquiebre = (ImageView) view.findViewById(R.id.imgquiebre);
            imgIndicador = (ImageView) view.findViewById(R.id.imgIndicador);
            imgMap = (ImageView) view.findViewById(R.id.imgMap);
            imgIndicadorvisita = (ImageView) view.findViewById(R.id.imgIndicadorvisita);

            view.setTag(this);
        }
    }

}
