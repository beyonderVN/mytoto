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
    private static final int DATABASE_VERSION = 1;


    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "Todo_Manager";


    // Tên bảng: Note.
    private static final String TABLE_TODO = "Todo";

    private static final String COLUMN_TODO_ID ="Note_Id";
    private static final String COLUMN_TODO_TITLE ="Note_Title";

    public TodoDataBase(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate ... ");
        // Script tạo bảng.
        String script = "CREATE TABLE " + TABLE_TODO + "("
                + COLUMN_TODO_ID + " INTEGER PRIMARY KEY," + COLUMN_TODO_TITLE + " TEXT"
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
        Log.i(TAG, "addTodo ... " + todoItem.getTaskName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TODO_TITLE, todoItem.getTaskName());

        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_TODO, null, values);


        // Đóng kết nối database.
        db.close();
    }

    public List<TodoItem> getAllTodos() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        List<TodoItem> noteList = new ArrayList<TodoItem>();
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


                // Thêm vào danh sách.
                noteList.add(todoItem);
            } while (cursor.moveToNext());
        }

        // return note list
        return noteList;
    }

    public int editTodo(TodoItem todoItem) {
        Log.i(TAG, "editTodo ... "  + todoItem.getTaskName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TODO_TITLE, todoItem.getTaskName());


        // updating row
        return db.update(TABLE_TODO, values, COLUMN_TODO_ID + " = ?",
                new String[]{String.valueOf(todoItem.getId())});
    }

    public void removeTodo(TodoItem todoItem) {
        Log.i(TAG, "removeTodo ... " + todoItem.getTaskName() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, COLUMN_TODO_ID + " = ?",
                new String[] { String.valueOf(todoItem.getId()) });
        db.close();
    }
}
