package net.movilbox.dcsuruguay.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import net.movilbox.dcsuruguay.Model.MisBajasDetalle1;
import net.movilbox.dcsuruguay.R;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapterMisBajas extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<MisBajasDetalle1>> expandableListDetail;
    private DecimalFormat format;

    public ExpandableListAdapterMisBajas(Context context, List<String> expandableListTitle, HashMap<String, List<MisBajasDetalle1>> expandableListDetail) {
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
    public View getChildView(final int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final MisBajasDetalle1 expandedListText = (MisBajasDetalle1) getChild(listPosition, expandedListPosition);


        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_bajas, null);
        }

        TextView txt_referencia = (TextView) convertView.findViewById(R.id.txt_referencia);
        TextView txt_cantidad = (TextView) convertView.findViewById(R.id.txt_cantidad);

        txt_referencia.setText(expandedListText.getSku());
        txt_cantidad.setText(expandedListText.getCant_sku() + "");


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
            convertView = layoutInflater.inflate(R.layout.list_group_bajas, null);
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