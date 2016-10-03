package net.movilbox.dcsuruguay.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import net.movilbox.dcsuruguay.Model.EntRefeSerial;
import net.movilbox.dcsuruguay.R;

import java.util.List;

public class AdapterSeriales extends BaseAdapter {

    private Activity actx;
    private List<EntRefeSerial> data;

    public AdapterSeriales(Activity actx, List<EntRefeSerial> data) {
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
    public EntRefeSerial getItem(int position) {
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
            convertView = View.inflate(actx, R.layout.item_serial, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final EntRefeSerial referencias = getItem(position);

        holder.checkBox_serial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                referencias.isChecked = isChecked;
                int getPosition = (Integer) buttonView.getTag();
                data.get(getPosition).setSelected(buttonView.isChecked());

            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                referencias.isChecked =! referencias.isChecked;
                holder.checkBox_serial.setChecked(referencias.isChecked);

            }
        });

        holder.checkBox_serial.setTag(position);

        holder.txt_serial.setText(String.format("Serial: %1$s", referencias.getSerie()));

        holder.checkBox_serial.setChecked(data.get(position).isSelected());

        return convertView;

    }

    class ViewHolder {

        TextView txt_serial;
        CheckBox checkBox_serial;

        public ViewHolder(View view) {

            txt_serial = (TextView) view.findViewById(R.id.txt_serial);
            checkBox_serial = (CheckBox) view.findViewById(R.id.checkBox_serial);

            view.setTag(this);
        }
    }

}
