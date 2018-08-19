package me.astashenkov.basicdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "diary_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase _db) {
        _db.execSQL(Diary.CREATE_DIARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {

        _db.execSQL("DROP TABLE IF EXISTS " + Diary.DIARY);
        onCreate(_db);
    }

    public void setupDiary(Diary[] diaries) {
        for (int i = 0; i < diaries.length; i++) {
            insertDiary(diaries[i]);
        }
    }

    public long insertDiary(Diary diary) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Diary.TITLE, diary.getTitle());
        values.put(Diary.DESCRIPTION, diary.getDescription());

        long id = db.insert(Diary.DIARY, null, values);

        db.close();
        return id;
    }

    public Diary getDiary(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Diary.DIARY,
                null,
                Diary.ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) cursor.moveToFirst();

        Diary diary = new Diary(cursor.getInt(cursor.getColumnIndex(Diary.ID)),
                cursor.getString(cursor.getColumnIndex(Diary.TITLE)),
                cursor.getString(cursor.getColumnIndex(Diary.DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(Diary.CREATED)),
                cursor.getString(cursor.getColumnIndex(Diary.MODIFIED)));

        cursor.close();
        return diary;
    }

    public ArrayList<Diary> getAllDiaries(String sortColumn) {
        ArrayList<Diary> diaries = new ArrayList<>();

        // Select All Query

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Diary.DIARY, null, null, null, null, null, sortColumn);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Diary diary = new Diary();
                diary.setId(cursor.getInt(cursor.getColumnIndex(Diary.ID)));
                diary.setTitle(cursor.getString(cursor.getColumnIndex(Diary.TITLE)));
                diary.setDescription(cursor.getString(cursor.getColumnIndex(Diary.DESCRIPTION)));
                diary.setDateCreated(cursor.getString(cursor.getColumnIndex(Diary.CREATED)));
                diary.setDateModified(cursor.getString(cursor.getColumnIndex(Diary.MODIFIED)));

                diaries.add(diary);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return diaries;
    }

    public int getDiariesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Diary.DIARY, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int updateDiary(Diary diary) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Diary.TITLE, diary.getTitle());
        values.put(Diary.DESCRIPTION, diary.getDescription());
        values.put(Diary.MODIFIED, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        int id = db.update(Diary.DIARY, values, Diary.ID + "=?",
                new String[]{String.valueOf(diary.getId())});
        db.close();
        return id;
    }

    public void deleteDiary(Diary diary) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Diary.DIARY, Diary.ID + "=?",
                new String[]{String.valueOf(diary.getId())});
        db.close();
    }

}
