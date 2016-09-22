package com.ngohoang.along.mytodoapp.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.ngohoang.along.mytodoapp.Navigator;
import com.ngohoang.along.mytodoapp.R;
import com.ngohoang.along.mytodoapp.model.BaseItem;
import com.ngohoang.along.mytodoapp.model.TodoItem;
import com.ngohoang.along.mytodoapp.ui.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    static final int PICK_CONTACT_REQUEST = 1;
    @BindView(R.id.todo_list)
    RecyclerView todoList;
    @BindView(R.id.add_btn)
    Button addBtn;

    Navigator navigator;
    Context context;
    Activity activity;


    List<BaseItem> list = new ArrayList<BaseItem>();
    MainAdapter mainAdapter =new MainAdapter(this,list);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigator = Navigator.getInstance();
        context =this;
        activity = this;
        addBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
//                navigator.navigateToAddItem(activity,PICK_CONTACT_REQUEST);
                Intent intentToLaunch = DetailActivity.getCallingIntent(activity);
                activity.startActivityForResult(intentToLaunch,PICK_CONTACT_REQUEST);
            }
        });

        RecyclerView.LayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        todoList.setLayoutManager(llm);
        todoList.setAdapter(mainAdapter);

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==PICK_CONTACT_REQUEST){
            if (resultCode == RESULT_OK) {
                TodoItem todoItem = (TodoItem) data.getSerializableExtra("result");
                list.add(todoItem);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainAdapter.notifyDataSetChanged();
            }
        });
    }


}
