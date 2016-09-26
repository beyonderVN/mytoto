package com.ngohoang.along.mytodoapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ngohoang.along.mytodoapp.model.TodoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 9/23/2016.
 */

public class TodoDataBase extends SQLiteOpenHelper {

    private static final String TAG = "TodoDataBase";


    // Phiên bản
    private static final int DATABASE_VERSION = 3;


    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "Todo_Manager";


    // Tên bảng: Note.
    private static final String TABLE_TODO = "Todo";

    private static final String COLUMN_TODO_ID ="Todo_Id";
    private static final String COLUMN_TODO_TITLE ="Todo_Title";
    private static final String COLUMN_TODO_DATETIME ="Todo_Datetime";
    private static final String COLUMN_TODO_PRIORITY ="Todo_Priority";

    public TodoDataBase(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate ... ");
        // Script tạo bảng.
        String script = "CREATE TABLE " + TABLE_TODO + "("
                + COLUMN_TODO_ID + " INTEGER PRIMARY KEY," + COLUMN_TODO_TITLE + " TEXT,"
                + COLUMN_TODO_DATETIME + " LONG, "
                + COLUMN_TODO_PRIORITY + " INTEGER"
                + ")";
        // Chạy lệnh tạo bảng.
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade ... ");

        // Hủy (drop) bảng cũ nếu nó đã tồn tại.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);

        // Và tạo lại.
        onCreate(db);
    }

    public void addTodo(TodoItem todoItem) {
        Log.i(TAG, "addTodo ... " + todoItem.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TODO_TITLE, todoItem.getTaskName());
        values.put(COLUMN_TODO_DATETIME, todoItem.getDateTime());

        values.put(COLUMN_TODO_PRIORITY, todoItem.getPriority());


        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_TODO, null, values);


        // Đóng kết nối database.
        db.close();
    }

    public List<TodoItem> getAllTodos() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        List<TodoItem> todoItems = new ArrayList<TodoItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TODO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                TodoItem todoItem = new TodoItem();
                todoItem.setId(Integer.parseInt(cursor.getString(0)));
                todoItem.setTaskName(cursor.getString(1));
                todoItem.setDateTime(Long.parseLong(cursor.getString(2)));
                todoItem.setPriority(Integer.parseInt(cursor.getString(3)));


                // Thêm vào danh sách.
                todoItems.add(todoItem);
            } while (cursor.moveToNext());
        }

        // return note list
        return todoItems;
    }

    public int editTodo(TodoItem todoItem) {
        Log.i(TAG, "editTodo ... "  + todoItem.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_TODO_TITLE, todoItem.getTaskName());
        values.put(COLUMN_TODO_DATETIME, todoItem.getDateTime());

        values.put(COLUMN_TODO_PRIORITY, todoItem.getPriority());
        int result;
        String id = String.valueOf(todoItem.getId());
        // updating row
        result=db.update(TABLE_TODO, values, COLUMN_TODO_ID + " = ?",
                new String[]{id});
        db.close();
        return result;
    }

    public void removeTodo(TodoItem todoItem) {
        Log.i(TAG, "removeTodo ... " + todoItem.toString() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, COLUMN_TODO_ID + " = ?",
                new String[] { String.valueOf(todoItem.getId()) });
        db.close();
    }
}
