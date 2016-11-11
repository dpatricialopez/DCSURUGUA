package net.movilbox.dcsuruguay.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import net.movilbox.dcsuruguay.Model.EntReferenciaSol;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Model.EntReferenciaSol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SecondLevelAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> expandableListTitle;
    private HashMap<String, List<EntReferenciaSol>> expandable;

    public SecondLevelAdapter(Context context, ArrayList<String>expandableListTitle, HashMap<String, List<EntReferenciaSol>> expandable ) {
        this.mContext = context;
        this.expandableListTitle=expandableListTitle;
      /*  //Cambio de orden de los items
        this.expandableListTitle.clear();
        this.expandableListTitle.add("Sims");
        this.expandableListTitle.add("Combos");
        this.expandableListTitle.add("Equipos");
        //*/
        this.expandable=expandable;

    }



    @Override
    public EntReferenciaSol getChild(int listPosition, int expandedListPosition) {
        return this.expandable.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final EntReferenciaSol expandedListText = (EntReferenciaSol) getChild(listPosition, expandedListPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_sol_producto, null);
        }

        TextView txt_referencia_sol = (TextView) convertView.findViewById(R.id.txt_referencia_sol);
        TextView txt_cantidad_sol = (TextView) convertView.findViewById(R.id.txt_cantidad_sol);
        TextView txtCantSol = (TextView) convertView.findViewById(R.id.txtCantSol);
       // TextView txtPrecio = (TextView) convertView.findViewById(R.id.txtPrecio);

        txt_referencia_sol.setText(String.format("%s", expandedListText.getProducto()));
        txt_cantidad_sol.setText(String.format("Disponible: %s", expandedListText.getTotal()));
     //   txtPrecio.setText(String.format("S/.  %s", expandedListText.getPrecio_pdv()));

        txtCantSol.setVisibility(View.GONE);
        txtCantSol.setText(String.format("%s", 0));

        if (expandedListText.getCantidadSol() > 0) {
            txtCantSol.setVisibility(View.VISIBLE);
            txtCantSol.setText(String.format("Cantidad Solicitada: %s", expandedListText.getCantidadSol()));
        } else {
            txtCantSol.setVisibility(View.GONE);
            txtCantSol.setText(String.format("%s", 0));
        }

        convertView.setPadding(80, 0, 0, 0);
        return convertView;
    }

    @Override
        public int getChildrenCount( int listPosition) {
        if (this.expandable.get(this.expandableListTitle.get(listPosition)) == null) {
            return 0;
        } else {
            return this.expandable.get(this.expandableListTitle.get(listPosition)).size();
        }
        }

    @Override
    public String getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        if (this.expandableListTitle == null) {
            return 0;
        } else {
            return this.expandableListTitle.size();
        }
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override

    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_inventario_list_detalle, null);
        }

        TextView txt = (TextView) convertView.findViewById(R.id.listTitle);
        txt.setTypeface(null, Typeface.NORMAL);
        convertView.setPadding(20, 0, 0, 0);
        txt.setText(getGroup(listPosition));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return true;
        }

    public void setData(HashMap<String, List<EntReferenciaSol>> expandableListDetail){
        this.expandable = expandable;
        notifyDataSetChanged();
    }
    }