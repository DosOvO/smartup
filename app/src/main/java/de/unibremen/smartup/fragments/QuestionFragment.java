package de.unibremen.smartup.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.unibremen.smartup.R;
import de.unibremen.smartup.RandomQuestion;
import de.unibremen.smartup.adapter.RecyclerQuestionAdapter;
import de.unibremen.smartup.model.Question;
import de.unibremen.smartup.viewmodel.QuestionViewModel;

public class QuestionFragment extends Fragment {

    private RecyclerView listView;
    private QuestionViewModel questionViewModel;

    public QuestionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_content, container, false);
        listView = (RecyclerView)view.findViewById(R.id.question_list);

        Intent intent = getActivity().getIntent();
        final RecyclerQuestionAdapter adapter = new RecyclerQuestionAdapter(getActivity());
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getAllQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(@Nullable List<Question> questions) {
                adapter.setWords(questions);

                List<Question> activeQuestions = new ArrayList<>();
                for (Question q : questions) {
                    if (q.isActive()) {
                        activeQuestions.add(q);
                    }
                }
                RandomQuestion.questionList = activeQuestions;
                Log.d("CLOCK", "Item List: " + adapter.getItemCount());
            }
        });

        if (intent.hasExtra("newQuestion")) {
            Question question = (Question)intent.getSerializableExtra("newQuestion");
            questionViewModel.insert(question);
            intent.removeExtra("newQuestion");
        } else if (intent.hasExtra("updateQuestion")) {
            Question questionToUpdate = (Question)intent.getSerializableExtra("updateQuestion");
            questionViewModel.update(questionToUpdate);
            intent.removeExtra("updateQuestion");
        }

        return view;
    }
}
