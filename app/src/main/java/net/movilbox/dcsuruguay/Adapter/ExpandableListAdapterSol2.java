package net.movilbox.dcsuruguay.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import net.movilbox.dcsuruguay.Model.EntReferenciaSol;

import net.movilbox.dcsuruguay.Activity.ActSolProd;
import net.movilbox.dcsuruguay.Activity.DialogSolProducto;
import net.movilbox.dcsuruguay.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jhonjimenez on 10/10/16.
 */

public class ExpandableListAdapterSol2 extends BaseExpandableListAdapter {

    private Context context;
    protected DialogSolProducto dialog;
    private List<String> expandableListTitle;
    private HashMap<String, List<EntReferenciaSol>> expandableListDetail;
    private HashMap<String, HashMap<String, List<EntReferenciaSol>>> expandable;

    public ExpandableListAdapterSol2(Context context, List<String> expandableListTitle, HashMap<String, List<EntReferenciaSol>> expandableListDetail, HashMap<String, HashMap<String, List<EntReferenciaSol>>> expandable ) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.expandable=expandable;

}

    @Override
    public HashMap<String, List<EntReferenciaSol>> getChild(int listPosition, int expandedListPosition) {
        return this.expandable.get(this.expandableListTitle.get(listPosition));
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(final int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final HashMap<String, List<EntReferenciaSol>> hijo= getChild(listPosition, expandedListPosition);
        final CustomExpListView secondLevelExpListView = new CustomExpListView(this.context);

        final ArrayList<String> title = new ArrayList<>(hijo.keySet());
        secondLevelExpListView.setAdapter(new SecondLevelAdapter(context,title,hijo));
        secondLevelExpListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {
                ((ActSolProd)context).pedido(listPosition, groupPosition,  childPosition);

                return false;
            }
        });

        return secondLevelExpListView;

    }

    @Override
    public int getChildrenCount(int listPosition) {
      return 1;
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
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_inventario_list_detalle, null);
        }

        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        String nombretitulo;

        if (listTitle.equals("0"))
            nombretitulo = "Sin Paquete";
        else
            nombretitulo = String.format("Paquete: %s", listTitle);

        listTitleTextView.setText(nombretitulo);
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    public void setData(HashMap<String, List<EntReferenciaSol>> expandableListDetail){
        this.expandable = expandable;
        notifyDataSetChanged();
    }

    private boolean isValidNumber(String number) {
        return number == null || number.length() == 0;
    }
}
