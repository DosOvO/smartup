package de.unibremen.smartup.clicklistener;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import de.unibremen.smartup.AddQuestionActivity;
import de.unibremen.smartup.R;
import de.unibremen.smartup.model.Question;
import de.unibremen.smartup.viewmodel.QuestionViewModel;

public class OnQuestionOverflowSelectedListener implements View.OnClickListener {

    private Question question;
    private Context context;
    private QuestionViewModel viewModel;

    public OnQuestionOverflowSelectedListener(Context context, Question question) {
        this.question = question;
        this.context = context;
        viewModel = new QuestionViewModel((Application)context.getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.overflow_delete:
                        viewModel.delete(question);
                        return true;
                    case R.id.overflow_edit:
                        Intent intent = new Intent(context, AddQuestionActivity.class);
                        intent.putExtra("editQuestion", question);
                        context.startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.inflate(R.menu.overflow_menu);

        popupMenu.show();
    }
}
