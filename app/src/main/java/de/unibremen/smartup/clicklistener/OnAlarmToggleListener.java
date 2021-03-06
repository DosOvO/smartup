package de.unibremen.smartup.clicklistener;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import java.util.Calendar;

import de.unibremen.smartup.AlarmReceiver;
import de.unibremen.smartup.RandomQuestion;
import de.unibremen.smartup.model.Alarm;
import de.unibremen.smartup.model.Question;
import de.unibremen.smartup.viewmodel.AlarmViewModel;

public class OnAlarmToggleListener implements View.OnClickListener {

    private Alarm alarm;
    private Context context;
    private AlarmManager alarmManager;
    private AlarmViewModel viewModel;
    private PendingIntent pendingIntent;

    public OnAlarmToggleListener(Context context, Alarm alarm) {
        this.context = context;
        this.alarm = alarm;
        viewModel = new AlarmViewModel((Application)context.getApplicationContext());
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public void onClick(View view) {
        Switch alarmActive = (Switch)view;
        if (alarmActive.isChecked()) {
            Log.d("OnClick", "Alarm ON");
            Intent myIntent = new Intent(context, AlarmReceiver.class);
            Question question = RandomQuestion.get();
            if (question != null) {
                myIntent.putExtra("question", question.getQuestion());
                myIntent.putExtra("answer", question.getAnswer());
            }
            pendingIntent = PendingIntent.getBroadcast(context, alarm.getDbId(), myIntent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
            calendar.set(Calendar.MINUTE, alarm.getMinute());

            //Wenn Uhrzeit in Vergangenheit liegt, darf er nicht sofort klingeln
            if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1); // zeit um einen tag erhöhen
            }

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            alarm.setActive(true);
            viewModel.update(alarm);
        } else {
            Log.d("OnClick", "Alarm OFF");

            if (pendingIntent == null) {
                Intent myIntent = new Intent(context, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(context, alarm.getDbId(), myIntent, 0);
            }
            alarmManager.cancel(pendingIntent);
            alarm.setActive(false);
            viewModel.update(alarm);
        }

    }
}
