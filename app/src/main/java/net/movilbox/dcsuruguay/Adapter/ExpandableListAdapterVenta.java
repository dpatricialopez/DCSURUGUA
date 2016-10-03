package net.movilbox.dcsuruguay.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import net.movilbox.dcsuruguay.Model.DetalleVenta2;
import net.movilbox.dcsuruguay.R;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapterVenta extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<DetalleVenta2>> expandableListDetail;
    private DecimalFormat format;

    public ExpandableListAdapterVenta(Context context, List<String> expandableListTitle, HashMap<String, List<DetalleVenta2>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        format = new DecimalFormat("#,###");
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final DetalleVenta2 expandedListText = (DetalleVenta2) getChild(listPosition, expandedListPosition);


        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }

        TextView txt_referencia = (TextView) convertView.findViewById(R.id.txt_referencia);
        TextView txt_cantidad = (TextView) convertView.findViewById(R.id.txt_cantidad);
        TextView txt_total = (TextView) convertView.findViewById(R.id.txt_total);

        txt_referencia.setText(expandedListText.getProducto());
        txt_cantidad.setText(String.valueOf(String.format("Cantidad: %s", expandedListText.getCantidad())));
        txt_total.setText(String.format("Valor $ %s", format.format(expandedListText.getValor())));

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {

        if (this.expandableListDetail.get(this.expandableListTitle.get(listPosition)) == null) {
            return 0;
        } else {
            return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
        }

    }

    @Override
    public Object getGroup(int listPosition) {
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
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }

        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

}