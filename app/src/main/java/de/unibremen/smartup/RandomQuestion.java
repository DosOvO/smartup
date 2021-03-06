package de.unibremen.smartup;

import java.util.List;
import java.util.Random;

import de.unibremen.smartup.model.Question;

public class RandomQuestion {

    public static List<Question> questionList;

    public static Question get() {
        if (questionList != null) {
            Random random = new Random();
            return questionList.get(random.nextInt(questionList.size()));
        }
        return null;
    }
}
