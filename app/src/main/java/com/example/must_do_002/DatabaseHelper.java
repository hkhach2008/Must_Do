package com.example.must_do_002;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.must_do_002.Model.ToDoModel;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserManager.db";
    private static final String TABLE_USER = "user";
    private static final String TABLE_TASK = "task";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private static final String COLUMN_TASK_ID = "task_id";
    private static final String COLUMN_TASK = "task";
    private static final String COLUMN_STATUS = "status";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT UNIQUE,"
            + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "("
            + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TASK + " TEXT,"
            + COLUMN_STATUS + " INTEGER,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + "FOREIGN KEY(" + COLUMN_USER_EMAIL + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_EMAIL + "))";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_TASK_TABLE = "DROP TABLE IF EXISTS " + TABLE_TASK;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_TASK_TABLE);
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_USER_ID };
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;
    }

    public String getUserNameByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_NAME};
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            String username = cursor.getString(0);
            cursor.close();
            db.close();
            return username;
        } else {
            cursor.close();
            db.close();
            return null; // User not found
        }
    }

    public void addTask(String task, int status, String userEmail) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, task);
        values.put(COLUMN_STATUS, status);
        values.put(COLUMN_USER_EMAIL, userEmail);

        db.insert(TABLE_TASK, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<ToDoModel> getAllTasksForUser(String userEmail) {
        List<ToDoModel> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_TASK_ID, COLUMN_TASK, COLUMN_STATUS};
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {userEmail};

        Cursor cursor = db.query(TABLE_TASK, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                ToDoModel task = new ToDoModel();
                task.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID)));
                task.setTask(cursor.getString(cursor.getColumnIndex(COLUMN_TASK)));
                task.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)));
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK, COLUMN_TASK_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
