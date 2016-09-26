package com.ngohoang.along.mytodoapp.model;

/**
 * Created by Admin on 21/09/2016.
 */

public class TodoItem extends BaseItem{
    String taskName;
    int id;
    long dateTime;
    int priority;

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public TodoItem() {
        setItemViewType();
    }

    public TodoItem(String taskName) {
        this();
        this.taskName = taskName;
    }

    public TodoItem( int id,String taskName) {
        this.taskName = taskName;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** Todo Entity Details *****\n");
        stringBuilder.append("id= " + this.getId() + "\n");
        stringBuilder.append("taskName= " + this.getTaskName() + "\n");
        stringBuilder.append("dateTime= " + this.getDateTime() + "\n");
        stringBuilder.append("priority= " + this.getPriority() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
