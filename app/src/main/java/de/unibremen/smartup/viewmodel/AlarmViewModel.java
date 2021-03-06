package de.unibremen.smartup.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import de.unibremen.smartup.model.Alarm;
import de.unibremen.smartup.repository.AlarmRepository;

public class AlarmViewModel extends AndroidViewModel {

    private AlarmRepository repository;
    private LiveData<List<Alarm>> allAlarms;

    public AlarmViewModel(Application application) {
        super(application);
        repository = new AlarmRepository(application);
        allAlarms = repository.getAllAlarms();
    }

    public LiveData<List<Alarm>> getAllAlarms() {
        return allAlarms;
    }

    public void insert(Alarm alarm) {
        repository.insert(alarm);
    }

    public void delete(Alarm alarm) {
        repository.delete(alarm);
    }

    public void update(Alarm alarm) {repository.update(alarm);}
}
