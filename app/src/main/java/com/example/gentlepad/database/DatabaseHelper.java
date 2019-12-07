package com.example.gentlepad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.gentlepad.Utilities.Constants;
import com.example.gentlepad.common.CommonUtils;
import com.example.gentlepad.models.NoteItem;

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

    private Context context;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
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
        if (rowInserted != -1) {
            CommonUtils.showToastMessage(context, "Saved");
            return true;
        } else {
            CommonUtils.showToastMessage(context, "Error");
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
        Cursor res = db.query
                (
                        TABLE_NAME,
                        new String[]{COL_2, COL_3, COL_4},
                        COL_2 + "=?",
                        new String[]{notesTitle}, null, null, null, null
                );
        return res;
    }

    public void updateNotes(Context context, String[] noteItems, String oldNotesTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (noteItems == null) {
            CommonUtils.showToastMessage(context, "Save failed");
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put(COL_2, noteItems[0]);
        cv.put(COL_3, noteItems[1]);
        cv.put(COL_4, noteItems[2]);
//        CommonUtils.showToastMessage(context, "Items--> " + noteItems[0] + "\n" + noteItems[1]);
        long result = db.update(TABLE_NAME, cv, COL_2 + "=?", new String[]{oldNotesTitle});
        db.close();
        if (result > 0) {
            /*CommonUtils.showToastMessage(context, "Save successful-->" + result);
            CommonUtils.showToastMessage(context, "Items--> " + noteItems[0] + "\n" + noteItems[1] + oldNotesTitle );*/
        } else {
//            CommonUtils.showToastMessage(context, "Something went wrong-->" + result);
        }
    }

    public boolean deleteNotes(String noteTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_2 + "=?", new String[]{noteTitle}) > 0;
    }

    public Cursor orderNotes(String sortBy) {

        String sort = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        if (sortBy != null) {
            if (sortBy.equalsIgnoreCase(Constants.ASCENDING)) {
                sort = "ASC";
                c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_2 + " ASC", new String[] {});
                if(c != null) {
                    CommonUtils.showToastMessage(context, "Sorting with cursor--> " + c.toString());
                }
            } else if (sortBy.equalsIgnoreCase(Constants.DESCENDING)) {
                sort = "DESC";
                c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_2 + " DESC", new String[] {});
                if(c != null) {
                    CommonUtils.showToastMessage(context, "Sorting with cursor--> " + c.toString());
                }
            } else if (sortBy.equalsIgnoreCase(Constants.DATE_MODIFIED)) {
                sort = "datetime";
                c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY date(" + COL_4 +") " + " DESC", new String[] {});
                if(c != null) {
                    CommonUtils.showToastMessage(context, "Sorting with cursor--> " + c.toString());
                }
            }
        }
        return c;
    }

}
