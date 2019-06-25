package com.example.gentlepad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gentlepad.common.CommonUtils;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Main.db";
        public static final String TABLE_NAME = "saved_notes";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NOTES_TITLE";
    public static final String COL_3 = "NOTES_DESC";
    public static final String COL_4 = "DATE";
    //data types
    public static final String DATA_TYPE_1 = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String DATA_TYPE_2 = "varchar(200)";
    public static final String DATA_TYPE_3 = "int(20)";
    public static final String DATA_TYPE_4 = "char(64)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COL_1 + " " + DATA_TYPE_1 + ", " +
                COL_2 + " " + DATA_TYPE_2 + ", " +
                COL_3 + " " + DATA_TYPE_2 + ", " +
                COL_4 + " " + DATA_TYPE_2 + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(Context context, String notesTitle, String notesDesc, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, notesTitle);
        contentValues.put(COL_3, notesDesc);
        contentValues.put(COL_4, date);
        long rowInserted = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        if(rowInserted != -1) {
            CommonUtils.showToastMessage(context, "Save successful");
            return true;
        } else {
            CommonUtils.showToastMessage(context, "Something went wrong");
            return false;
        }

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Cursor getSelectedItemDetails(String notesTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " WHERE " + COL_2 + " = " + notesTitle, null);
        return res;
    }

}
