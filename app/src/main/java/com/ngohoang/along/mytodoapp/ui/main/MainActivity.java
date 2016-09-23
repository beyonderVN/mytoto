package com.ngohoang.along.mytodoapp.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ngohoang.along.mytodoapp.Navigator;
import com.ngohoang.along.mytodoapp.R;
import com.ngohoang.along.mytodoapp.data.TodoDataBase;
import com.ngohoang.along.mytodoapp.model.BaseItem;
import com.ngohoang.along.mytodoapp.model.TodoItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    static final int PICK_CONTACT_REQUEST = 1;
    static final String TODO_ITEM = "TODO_ITEM";
    static final String CONTROL_VIEW_TYPE = "CONTROL_VIEW_TYPE";
    @BindView(R.id.todo_list)
    RecyclerView todoList;
    @BindView(R.id.add_btn)
    Button addBtn;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheetViewgroup;


    Navigator navigator;
    Context context;
    Activity activity;


    List<BaseItem> list = new ArrayList<BaseItem>();
    MainAdapter mainAdapter =new MainAdapter(this,this,list);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigator = Navigator.getInstance();
        context =this;
        activity = this;



        final BottomSheetBehavior bottomSheetBehavior =
                BottomSheetBehavior.from(bottomSheetViewgroup);


        addBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                ModalBottomSheet modalBottomSheet = ModalBottomSheet.newInstance();
                modalBottomSheet.show(getSupportFragmentManager(), "bottom sheet");

            }
        });

        RecyclerView.LayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        todoList.setLayoutManager(llm);
        todoList.setAdapter(mainAdapter);
        refresh();
    }

    @Override
    protected void onResume() {
        mainAdapter.notifyDataSetChanged();
        super.onResume();
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

    public void addItem(TodoItem todoItem){
        TodoDataBase db = new TodoDataBase(this);
        db.addTodo(todoItem);
        refresh();
    }
    public void editItem(TodoItem todoItem){
        TodoDataBase db = new TodoDataBase(this);
        db.editTodo(todoItem);
        refresh();
    }
    public void removeItem(TodoItem todoItem){
        TodoDataBase db = new TodoDataBase(this);
        db.removeTodo(todoItem);
        refresh();
    }

    public void callEditItem(TodoItem todoItem){
        ModalBottomSheet modalBottomSheet = ModalBottomSheet.newInstance(todoItem,Navigator.TYPE_EDIT);
        modalBottomSheet.show(getSupportFragmentManager(), "bottom sheet");
    }
    public void callRemoveItem(TodoItem todoItem){
        ModalBottomSheet modalBottomSheet = ModalBottomSheet.newInstance(todoItem,Navigator.TYPE_REMOVE);
        modalBottomSheet.show(getSupportFragmentManager(), "bottom sheet");
    }

    public void refresh(){
        TodoDataBase db = new TodoDataBase(this);
        List<TodoItem> todos =  db.getAllTodos();
        list.clear();
        list.addAll(todos);
        mainAdapter.notifyDataSetChanged();
    }

    public static class ModalBottomSheet
            extends BottomSheetDialogFragment {
        @BindView(R.id.task_name)
        EditText taskName;
        @BindView(R.id.add_btn)
        Button addBtn;

        String controViewlType = Navigator.TYPE_ADD;


        static ModalBottomSheet newInstance() {
            ModalBottomSheet f = new ModalBottomSheet();
            return f;
        }

        static ModalBottomSheet newInstance(TodoItem todoItem,String controlViewType) {
            ModalBottomSheet f = new ModalBottomSheet();
            Bundle bdl = new Bundle();
            bdl.putString(CONTROL_VIEW_TYPE,controlViewType);
            bdl.putSerializable(TODO_ITEM, todoItem);
            f.setArguments(bdl);
            return f;
        }

        @Override
        public View onCreateView(
                LayoutInflater inflater,
                ViewGroup container,
                Bundle savedInstanceState
        ) {

            View v = inflater.inflate(
                    R.layout.activity_main_bottom_sheets, container, false);
            ButterKnife.bind(this, v);

            Bundle bundle = getArguments();
            if(bundle!=null){
                controViewlType = bundle.getString(CONTROL_VIEW_TYPE);
            }


            switch (controViewlType){
                case (Navigator.TYPE_ADD):
                    break;
                case (Navigator.TYPE_EDIT):
                    try {
                        TodoItem todoItem = (TodoItem) getArguments().getSerializable(TODO_ITEM);
                        taskName.setText(todoItem.getTaskName());
                    }catch (Exception e){

                    }
                    break;
                case (Navigator.TYPE_REMOVE):
                    taskName.setEnabled(false);
                    taskName.setFocusable(false);
                    break;
            }

                taskName.setSelection(0, taskName.getText().length());


            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity activity = (MainActivity) getActivity();

                    switch (controViewlType){
                        case (Navigator.TYPE_ADD):
                            activity.addItem(new TodoItem(taskName.getText().toString()));
                            dismiss();
                        case (Navigator.TYPE_EDIT):
                            activity.editItem(new TodoItem(taskName.getText().toString()));
                            dismiss();
                        case (Navigator.TYPE_REMOVE):
                            activity.removeItem(new TodoItem(taskName.getText().toString()));
                            dismiss();
                    }

                }
            });
            return v;
        }

    }

}
