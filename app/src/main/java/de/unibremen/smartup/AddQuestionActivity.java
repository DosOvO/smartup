package de.unibremen.smartup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import de.unibremen.smartup.model.Question;
import de.unibremen.smartup.viewmodel.QuestionViewModel;

public class AddQuestionActivity extends AppCompatActivity {

    private TextView questionView;
    private TextView answer;
    private QuestionViewModel viewModel;
    private Question questionToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        viewModel = new QuestionViewModel(getApplication());


        questionView = (TextView)findViewById(R.id.question);
        answer = (TextView)findViewById(R.id.answer);

        Intent intent = getIntent();
        questionToEdit = null;
        if (intent.hasExtra("editQuestion")) {
            questionToEdit = (Question)intent.getSerializableExtra("editQuestion");
            questionView.setText(questionToEdit.getQuestion());
            answer.setText(questionToEdit.getAnswer());
        }
    }

    public void cancel(View view) {
       Intent intent = new Intent(this, Layout.class);
       intent.putExtra("tabNumber", 1);
       startActivity(intent);
    }

    public void processQuestion(View view) {
        Intent intent = new Intent(this, Layout.class);
        Question question = new Question(questionView.getText().toString(), answer.getText().toString(), true);
        if (questionToEdit == null) {
            intent.putExtra("newQuestion", question);
        } else {
            question.setDbId(questionToEdit.getDbId());
            intent.putExtra("updateQuestion", question);
        }
        intent.putExtra("tabNumber", 1);
        startActivity(intent);
    }
}
