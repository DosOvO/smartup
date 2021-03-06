package de.unibremen.smartup.adapter;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import de.unibremen.smartup.R;
import de.unibremen.smartup.clicklistener.OnAlarmOverflowSelectedListener;
import de.unibremen.smartup.clicklistener.OnAlarmToggleListener;
import de.unibremen.smartup.model.Alarm;

public class RecyclerAlarmAdapter extends RecyclerView.Adapter<RecyclerAlarmAdapter.AlarmViewHolder> {

    class AlarmViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        Switch checked;

        private AlarmViewHolder(View view) {
            super(view);
            txtName = (TextView)view.findViewById(R.id.firstLine);
            checked = (Switch)view.findViewById(R.id.clock_switch);
        }
    }

    private final LayoutInflater inflater;

    private List<Alarm> alarmList;

    public RecyclerAlarmAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row_card, parent, false);
        return new AlarmViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        if (alarmList != null) {
            Alarm current = alarmList.get(position);
            holder.txtName.setText(getString(current));
            holder.checked.setChecked(current.isActive());
            View overflow = holder.itemView.findViewById(R.id.menu_overflow);
            overflow.setOnClickListener(new OnAlarmOverflowSelectedListener(inflater.getContext(), current));
            holder.checked.setOnClickListener(new OnAlarmToggleListener(inflater.getContext(), current));
        }
    }

    public void setWords(List<Alarm> alarms){
        alarmList = alarms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (alarmList != null) {
            return alarmList.size();
        }
        return 0;
    }

    private String getString(final Alarm alarm) {
        String timeAsString = "";
        timeAsString += alarm.getHour() <= 9 ? "0" + alarm.getHour() : alarm.getHour();
        timeAsString += ":";
        timeAsString += alarm.getMinute() <= 9 ? "0" + alarm.getMinute() : alarm.getMinute();
        return timeAsString;
    }
}
