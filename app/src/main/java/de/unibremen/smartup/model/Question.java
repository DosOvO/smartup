package de.unibremen.smartup.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "questions")
public class Question implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int dbId;
    @NonNull
    private String question;
    @NonNull
    private String answer;
    private boolean active;

    @Ignore
    public Question() {}

    public Question(String question, String answer, boolean active) {
        this.question = question;
        this.answer = answer;
        this.active = active;
    }

}
