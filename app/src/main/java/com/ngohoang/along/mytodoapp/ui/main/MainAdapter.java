package com.ngohoang.along.mytodoapp.ui.main;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ngohoang.along.mytodoapp.R;
import com.ngohoang.along.mytodoapp.model.BaseItem;
import com.ngohoang.along.mytodoapp.model.TodoItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 21/09/2016.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BaseItem> items ;
    Context context;
    Activity activity;
    public MainAdapter(Context context,Activity activity, List<BaseItem> items) {
        this.context = context;
        this.activity = activity;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==BaseItem.TYPE_TODO_ITEM){
            return createTodoHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseItem  baseItem= getItem(position);
        if(baseItem instanceof TodoItem){
            bindTodoHolder((TodoItem) baseItem,(TodoHolder) holder) ;
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getItemViewType();
    }

    public BaseItem getItem(int position){
        return items.get(position);
    }

    protected static class TodoHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.task_name)

        TextView taskName;
        @BindView(R.id.date_picker_btn)
        Button datePickerBtn;
        @BindView(R.id.time_picker_btn)
        Button timePickerBtn;
        @BindView(R.id.priority_picker_btn)
        Button priorityPickerBtn;

        public TodoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            datePickerBtn.setClickable(false);
            timePickerBtn.setClickable(false);
            priorityPickerBtn.setClickable(false);
            taskName.setClickable(false);
            taskName.setLongClickable(false);
            taskName.setBackground(null);
            taskName.setCursorVisible(false);

        }
    }

    protected TodoHolder createTodoHolder(ViewGroup parent) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_todo_item_card,parent,false);
        final TodoHolder viewHolder = new TodoHolder(v);

        return viewHolder;
    }

    protected void bindTodoHolder(final TodoItem todoItem, final TodoHolder todoHolder){
        todoHolder.taskName.setText(todoItem.getTaskName());
        todoHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) activity).callEditItem(todoItem);

            }
        });
        todoHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((MainActivity) activity).callRemoveItem(todoItem);
                return false;
            }

        });
    }
}
