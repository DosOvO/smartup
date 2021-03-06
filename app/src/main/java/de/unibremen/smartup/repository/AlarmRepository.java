package de.unibremen.smartup.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import de.unibremen.smartup.dao.AlarmDAO;
import de.unibremen.smartup.db.AppDatabase;
import de.unibremen.smartup.model.Alarm;

public class AlarmRepository {

    private AlarmDAO alarmDao;
    private LiveData<List<Alarm>> allAlarms;

    public AlarmRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        alarmDao = db.alarmDao();
        allAlarms = alarmDao.getAllAlarms();
    }

    public LiveData<List<Alarm>> getAllAlarms() {
        return allAlarms;
    }

    public void insert(Alarm alarm) {
        new insertAsyncTask(alarmDao).execute(alarm);
    }

    public void delete(Alarm alarm) { new deleteAsyncTask(alarmDao).execute(alarm); }

    public  void update(Alarm alarm) { new updateAsyncTask(alarmDao).execute(alarm);}

    private static class insertAsyncTask extends AsyncTask<Alarm, Void, Void> {

        private AlarmDAO alarmDao;

        insertAsyncTask(AlarmDAO dao) {
            alarmDao = dao;
        }

        @Override
        protected Void doInBackground(final Alarm... params) {
            for (final Alarm a : params) {
                alarmDao.insert(a);
            }
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Alarm, Void, Void> {

        private AlarmDAO alarmDao;

        deleteAsyncTask(AlarmDAO dao) {
            alarmDao = dao;
        }

        @Override
        protected Void doInBackground(final Alarm... params) {
            for (final Alarm a : params) {
                alarmDao.delete(a);
            }
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Alarm, Void, Void> {

        private AlarmDAO alarmDao;

        updateAsyncTask(AlarmDAO dao) {
            alarmDao = dao;
        }

        @Override
        protected Void doInBackground(final Alarm... params) {
            for (final Alarm a : params) {
                alarmDao.update(a);
            }
            return null;
        }
    }
}
