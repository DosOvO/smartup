package de.unibremen.smartup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TimePicker;

import de.unibremen.smartup.model.Alarm;

public class AddAlarmActivity extends AppCompatActivity {

    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        timePicker = (TimePicker)findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, Layout.class);
        startActivity(intent);
    }

    public void processAlarm(View view) {
        Intent intent = new Intent(this, Layout.class);
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        Alarm newAlarm = new Alarm(hour, minute, false);
        intent.putExtra("newAlarm", newAlarm);
        startActivity(intent);
    }
}
