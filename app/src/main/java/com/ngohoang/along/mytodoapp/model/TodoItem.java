package com.ngohoang.along.mytodoapp.model;

/**
 * Created by Admin on 21/09/2016.
 */

public class TodoItem extends BaseItem{
    String taskName;

    public TodoItem() {
        setItemViewType();
    }

    public TodoItem(String taskName) {
        this();
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void setItemViewType() {
        itemViewType = BaseItem.TYPE_TODO_ITEM;
    }
}
