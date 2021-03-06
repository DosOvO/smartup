package de.unibremen.smartup;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Random;

import de.unibremen.smartup.model.Question;
import de.unibremen.smartup.viewmodel.QuestionViewModel;

public class AlarmService extends IntentService {
    private NotificationManager alarmNotificationManager;
    private QuestionViewModel viewModel;

    public AlarmService() { super("AlarmService"); }

    @Override
    public void onHandleIntent(Intent intent) {
        viewModel = new QuestionViewModel(getApplication());
        String question = "";
        String answer = "";
        if (intent.hasExtra("question") && intent.hasExtra("answer")) {
            question = intent.getStringExtra("question");
            answer = intent.getStringExtra("answer");
        }

        sendNotification(question, answer);
    }

    private void sendNotification(final String question, final String answer) {
        Log.d("AlarmService", "Preparing to send notification...: " + question);
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, WakeupActivity.class);
        intent.putExtra("question", question);
        intent.putExtra("answer", answer);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder alarmNotificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("SmartUp")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(question))
                .setContentText(question)
                .setAutoCancel(true);


        alarmNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alarmNotificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");
    }
}
