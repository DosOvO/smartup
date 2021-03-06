package de.unibremen.smartup.clicklistener;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import de.unibremen.smartup.AlarmReceiver;
import de.unibremen.smartup.R;
import de.unibremen.smartup.model.Alarm;
import de.unibremen.smartup.viewmodel.AlarmViewModel;

public class OnAlarmOverflowSelectedListener implements View.OnClickListener {
    private Alarm alarm;
    private Context context;
    private AlarmViewModel viewModel;

    public OnAlarmOverflowSelectedListener(Context context, Alarm alarm) {
        this.alarm = alarm;
        this.context = context;
        this.viewModel = new AlarmViewModel((Application)context.getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.overflow_delete:
                        if (alarm.isActive()) {
                            cancelAlarm();
                        }
                        viewModel.delete(alarm);
                        return true;

                    default:
                        return false;
                }
            }
        });

        popupMenu.inflate(R.menu.overflow_menu);

        popupMenu.show();
    }

    private void cancelAlarm() {
        Intent myIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getDbId(), myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

}
