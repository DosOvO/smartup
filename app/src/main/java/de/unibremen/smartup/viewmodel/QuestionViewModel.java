package de.unibremen.smartup.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import de.unibremen.smartup.model.Question;
import de.unibremen.smartup.repository.QuestionRepository;

public class QuestionViewModel extends AndroidViewModel {

    private QuestionRepository repository;
    private LiveData<List<Question>> allQuestions;

    public QuestionViewModel(Application application) {
        super(application);
        repository = new QuestionRepository(application);
        allQuestions = repository.getAllQuestions();
    }

    public LiveData<List<Question>> getAllQuestions() { return allQuestions; }

    public void insert(Question question) {
        repository.insert(question);
    }

    public void delete(Question question) {
        repository.delete(question);
    }

    public void update(Question question) {repository.update(question);}
}
