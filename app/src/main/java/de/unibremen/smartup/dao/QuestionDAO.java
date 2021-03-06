package de.unibremen.smartup.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import de.unibremen.smartup.model.Question;

@Dao
public interface QuestionDAO {

    @Insert
    void insert(Question question);

    @Update
    void update(Question question);

    @Delete
    void delete(Question question);

    @Query("DELETE FROM questions")
    void deleteAll();

    @Query("SELECT * FROM questions")
    LiveData<List<Question>> getAllQuestions();
}
