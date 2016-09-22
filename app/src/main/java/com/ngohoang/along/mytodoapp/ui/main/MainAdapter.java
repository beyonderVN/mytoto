package com.ngohoang.along.mytodoapp.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ngohoang.along.mytodoapp.R;
import com.ngohoang.along.mytodoapp.model.BaseItem;
import com.ngohoang.along.mytodoapp.model.TodoItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 21/09/2016.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BaseItem> items ;
    Context context;
    public MainAdapter(Context context, List<BaseItem> items) {
        this.context = context;
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
        public TodoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    protected TodoHolder createTodoHolder(ViewGroup parent) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_todo_item,parent,false);
        final TodoHolder viewHolder = new TodoHolder(v);

        return viewHolder;
    }

    protected void bindTodoHolder(TodoItem todoItem, TodoHolder todoHolder){
        todoHolder.taskName.setText(todoItem.getTaskName());
    }
}
