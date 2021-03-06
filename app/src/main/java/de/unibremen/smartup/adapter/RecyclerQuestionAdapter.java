package de.unibremen.smartup.adapter;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import de.unibremen.smartup.R;
import de.unibremen.smartup.clicklistener.OnQuestionOverflowSelectedListener;
import de.unibremen.smartup.model.Question;
import de.unibremen.smartup.viewmodel.QuestionViewModel;

public class RecyclerQuestionAdapter extends RecyclerView.Adapter<RecyclerQuestionAdapter.QuestionViewHolder> {

    class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        TextView answer;
        Switch active;

        private QuestionViewHolder(View view) {
            super(view);
            question = (TextView)view.findViewById(R.id.question);
            answer = (TextView)view.findViewById(R.id.answer);
            active = (Switch)view.findViewById(R.id.active_switch);
        }
    }

    private final LayoutInflater inflater;
    private QuestionViewModel viewModel;
    private List<Question> questionList;

    public RecyclerQuestionAdapter(Context context) {

        inflater = LayoutInflater.from(context);
        viewModel = new QuestionViewModel((Application)context.getApplicationContext());
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row, parent, false);
        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        if (questionList != null) {
            final Question current = questionList.get(position);
            holder.question.setText(current.getQuestion());
            holder.answer.setText(current.getAnswer());
            holder.active.setChecked(current.isActive());
            View overflow = holder.itemView.findViewById(R.id.menu_overflow);
            overflow.setOnClickListener(new OnQuestionOverflowSelectedListener(inflater.getContext(), current));
            holder.active.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    current.setActive(!current.isActive());
                    viewModel.update(current);
                }
            });
        }
    }

    public void setWords(List<Question> questions){
        questionList = questions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (questionList != null) {
            return questionList.size();
        }
        return 0;
    }
}
