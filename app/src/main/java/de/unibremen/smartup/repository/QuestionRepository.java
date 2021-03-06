package de.unibremen.smartup.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import de.unibremen.smartup.dao.QuestionDAO;
import de.unibremen.smartup.db.AppDatabase;
import de.unibremen.smartup.model.Question;

public class QuestionRepository {

    private QuestionDAO questionDao;
    private LiveData<List<Question>> allQuestions;

    public QuestionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        questionDao = db.questionDao();
        allQuestions = questionDao.getAllQuestions();
    }

    public LiveData<List<Question>> getAllQuestions() {
        return allQuestions;
    }

    public void insert(Question question) {
        new insertAsyncTask(questionDao).execute(question);
    }

    public void delete(Question question) { new deleteAsyncTask(questionDao).execute(question); }

    public  void update(Question question) { new updateAsyncTask(questionDao).execute(question);}

    private static class insertAsyncTask extends AsyncTask<Question, Void, Void> {

        private QuestionDAO questionDao;

        insertAsyncTask(QuestionDAO dao) {
            questionDao = dao;
        }

        @Override
        protected Void doInBackground(final Question... params) {
            for (final Question q : params) {
                questionDao.insert(q);
            }
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Question, Void, Void> {

        private QuestionDAO questionDao;

        deleteAsyncTask(QuestionDAO dao) {
            questionDao = dao;
        }

        @Override
        protected Void doInBackground(final Question... params) {
            for (final Question q : params) {
                questionDao.delete(q);
            }
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Question, Void, Void> {

        private QuestionDAO questionDao;

        updateAsyncTask(QuestionDAO dao) {
            questionDao = dao;
        }

        @Override
        protected Void doInBackground(final Question... params) {
            for (final Question q : params) {
                questionDao.update(q);
            }
            return null;
        }
    }
}
