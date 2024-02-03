package com.example.remider_app.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.remider_app.Model.AssignmentModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "assignmentListDatabase";
    private static final String ASSIGNMENT_TABLE = "assignment";
    private static final String ID = "id";
    private static final String ASSIGNMENT_TASK = "assignment_task";
    private static final String STATUS = "status";
    private static final String CREATE_ASSIGNMENT_TABLE = "CREATE TABLE " + ASSIGNMENT_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ASSIGNMENT_TASK + " TEXT, " + STATUS + " INTEGER)";


    private SQLiteDatabase db;

    public DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ASSIGNMENT_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + ASSIGNMENT_TABLE);
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertAssignment(AssignmentModel assignment_task){
        ContentValues cv = new ContentValues();
        cv.put(ASSIGNMENT_TASK, assignment_task.getAssignment());
        cv.put(STATUS, 0);
        db.insert(ASSIGNMENT_TABLE, null, cv);
    }

    @SuppressLint("Range")
    public List<AssignmentModel> getAllAssignments() {
        List<AssignmentModel> assignmentList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(ASSIGNMENT_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        AssignmentModel assignment = new AssignmentModel();
                        assignment.setId(cur.getInt(cur.getColumnIndex(ID)));
                        assignment.setAssignment(cur.getString(cur.getColumnIndex(ASSIGNMENT_TASK)));
                        assignment.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        assignmentList.add(assignment);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return assignmentList;
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(ASSIGNMENT_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String assignment_task) {
        ContentValues cv = new ContentValues();
        cv.put(ASSIGNMENT_TASK, assignment_task);
        db.update(ASSIGNMENT_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(ASSIGNMENT_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }
}



