package de.unibremen.smartup.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import de.unibremen.smartup.dao.AlarmDAO;
import de.unibremen.smartup.dao.QuestionDAO;
import de.unibremen.smartup.model.Alarm;
import de.unibremen.smartup.model.Question;
import lombok.NonNull;

@Database(entities = {Alarm.class, Question.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AlarmDAO alarmDao();
    public abstract QuestionDAO questionDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(dbCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase database) {
            super.onCreate(database);
            //Insert sample data
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AlarmDAO alarmDao;
        private final QuestionDAO questionDao;

        PopulateDbAsync(AppDatabase db) {
            questionDao = db.questionDao();
            alarmDao = db.alarmDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            alarmDao.deleteAll();
            questionDao.deleteAll();

            Alarm alarm = new Alarm(13, 37, false);
            alarmDao.insert(alarm);
            alarm = new Alarm(8, 30, false);
            alarmDao.insert(alarm);
            alarm = new Alarm(9, 0, false);
            alarmDao.insert(alarm);

            Question question = new Question("Unterschied von mobilen Endgeräten zu stationären Geräten?", "Kleinere Displays, Touchinteraktion, Energieverbrauch", true);
            questionDao.insert(question);
            question = new Question("5 + 3 = ?", "8, Acht", true);
            questionDao.insert(question);
            question = new Question("Antwort nach dem Leben, dem Universum und dem ganzen Rest", "42, zweiundvierzig",true);
            questionDao.insert(question);
            return null;
        }
    }
}
