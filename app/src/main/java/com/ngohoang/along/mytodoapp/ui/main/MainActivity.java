package com.ngohoang.along.mytodoapp.ui.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ngohoang.along.mytodoapp.ComonInterface;
import com.ngohoang.along.mytodoapp.Navigator;
import com.ngohoang.along.mytodoapp.R;
import com.ngohoang.along.mytodoapp.data.TodoDataBase;
import com.ngohoang.along.mytodoapp.dialog.DialogUtil;
import com.ngohoang.along.mytodoapp.model.BaseItem;
import com.ngohoang.along.mytodoapp.model.TodoItem;
import com.ngohoang.along.mytodoapp.utils.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    static final String TODO_ITEM = "TODO_ITEM";
    static final String CONTROL_VIEW_TYPE = "CONTROL_VIEW_TYPE";
    @BindView(R.id.todo_list)
    RecyclerView todoList;
    @BindView(R.id.add_btn)
    Button addBtn;



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



        addBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                callAddItem();

            }
        });

        RecyclerView.LayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        todoList.setLayoutManager(llm);
        todoList.setAdapter(mainAdapter);
        refresh();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return super.onCreateDialog(id);
    }

    @Override
    protected void onResume() {
        mainAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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
    public void callAddItem(){
        ModalBottomSheet modalBottomSheet = ModalBottomSheet.newInstance();
        modalBottomSheet.show(getSupportFragmentManager(), "bottom sheet");
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
            extends BottomSheetDialogFragment implements ComonInterface {
        public static final int DATE_PICKER_DIALOG = 1;
        public static final int TIME_PICKER_DIALOG = 2;
        @BindView(R.id.task_name)
        EditText taskName;
        @BindView(R.id.add_btn)
        Button addBtn;
        @BindView(R.id.date_picker_btn)
        Button datePickerBtn;
        @BindView(R.id.time_picker_btn)
        Button timePickerBtn;
        @BindView(R.id.priority_picker_btn)
        Button priorityPickerBtn;

        int priority = 0;
        String[] priorityNames ;
        int[] priorityColors;

        String controViewlType = Navigator.TYPE_ADD;
        TodoItem todoItem ;

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
                    R.layout.layout_todo_item_bottom_sheet, container, false);
            ButterKnife.bind(this, v);

            Resources res = getResources();
            priorityNames = res.getStringArray(R.array.priority_name);
            priorityColors = res.getIntArray(R.array.priority_color);


            setupDailog();
            onSubmitClick();
            if (controViewlType!=Navigator.TYPE_REMOVE){
                onTimePickerBtnClick();
                onDatePickerBtnClick();
                onPriorityPickerBtnClick();
            }

            return v;
        }


        private void onTimePickerBtnClick() {
            timePickerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        DialogUtil.TimePickerFragment newFragment = new DialogUtil.TimePickerFragment();
                        newFragment.setTargetFragment(ModalBottomSheet.this,TIME_PICKER_DIALOG);
                        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");


                }
            });
        }
        private void onDatePickerBtnClick() {
            datePickerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        DialogUtil.DatePickerFragment newFragment = new DialogUtil.DatePickerFragment();
                        newFragment.setTargetFragment(ModalBottomSheet.this, DATE_PICKER_DIALOG);
                        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");

                }
            });
        }


        private void onPriorityPickerBtnClick() {
            priorityPickerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        priority = (priority + 1) % priorityNames.length;
                        priorityPickerBtn.setText(String.valueOf(priorityNames[priority]));
                        priorityPickerBtn.setBackgroundColor(priorityColors[priority]);

                }
            });
        }

        void setupDailog(){
            Bundle bundle = getArguments();
            if(bundle!=null){
                controViewlType = bundle.getString(CONTROL_VIEW_TYPE);
            }


            switch (controViewlType){
                case (Navigator.TYPE_ADD):
                    todoItem = new TodoItem();
                    addBtn.setText("ADD");
                    break;
                case (Navigator.TYPE_EDIT):
                    try {
                        todoItem = (TodoItem) getArguments().getSerializable(TODO_ITEM);
                        taskName.setText(todoItem.getTaskName());
                        priority = todoItem.getPriority();

                        taskName.setSelection(0, taskName.getText().length());
                    }catch (Exception e){

                    }
                    addBtn.setText("EDIT");
                    break;
                case (Navigator.TYPE_REMOVE):
                    try {
                        todoItem = (TodoItem) getArguments().getSerializable(TODO_ITEM);
                        taskName.setText(todoItem.getTaskName());
                    }catch (Exception e){

                    }
                    addBtn.setText("REMOVE");
                    datePickerBtn.setClickable(false);
                    timePickerBtn.setClickable(false);
                    priorityPickerBtn.setClickable(false);
                    taskName.setClickable(false);
                    taskName.setLongClickable(false);
                    taskName.setBackground(null);
                    taskName.setCursorVisible(false);
                    taskName.setFocusable(false);
                    break;
            }



            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            timePickerBtn.setText(String.format(getResources().getString(R.string.time_format), hour, minute));
            datePickerBtn.setText(String.format(getResources().getString(R.string.date_format), day,month, year));
            priorityPickerBtn.setTextColor(getResources().getColor(R.color.white));
            priorityPickerBtn.setText( String.valueOf(priorityNames[priority]));
            priorityPickerBtn.setBackgroundColor( priorityColors[priority]);

        }

        void onSubmitClick(){
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity activity = (MainActivity) getActivity();
                    todoItem.setTaskName(taskName.getText().toString());
                    String dateTime = datePickerBtn.getText().toString()+" "+timePickerBtn.getText().toString();


                    todoItem.setDateTime(DateUtil.longValueFromDateTime(dateTime));
                    todoItem.setPriority(priority);
                    switch (controViewlType){
                        case (Navigator.TYPE_ADD):
                            activity.addItem(todoItem);
                            break;
                        case (Navigator.TYPE_EDIT):
                            activity.editItem(todoItem);
                            break;
                        case (Navigator.TYPE_REMOVE):
                            activity.removeItem(todoItem);
                            break;
                    }
                    dismiss();

                }
            });
        }


        @Override
        public void doWork(Object ...obj) {
            switch ((Integer)obj[0]){
                case DATE_PICKER_DIALOG :
                    int year = (Integer)obj[1];
                    int month = (Integer)obj[2];
                    int day = (Integer)obj[3];
                    datePickerBtn.setText(String.format(getResources().getString(R.string.date_format), day,month, year));
                    break;
                case TIME_PICKER_DIALOG :
                    int hourOfDay = (Integer)obj[1];
                    int minute = (Integer)obj[2];
                    timePickerBtn.setText(String.format(getResources().getString(R.string.time_format), hourOfDay, minute));
                    break;
            }

        }
    }

}
