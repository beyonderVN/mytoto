package com.ngohoang.along.mytodoapp.ui.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ngohoang.along.mytodoapp.R;
import com.ngohoang.along.mytodoapp.model.TodoItem;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.task_name)
    EditText taskName;

    @BindView(R.id.add_btn)
    Button addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    public static Intent getCallingIntent(Context context){
        Intent intent = new Intent(context, DetailActivity.class);
        return intent;
    }

    private void submit(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",new TodoItem(taskName.getText().toString()) );


        if (getParent() == null) {
            setResult(Activity.RESULT_OK,returnIntent);
        } else {
            getParent().setResult(Activity.RESULT_OK,returnIntent);
        }
        finish();
    }
}
