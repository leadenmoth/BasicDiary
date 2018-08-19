package me.astashenkov.basicdiary;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter {/*
    private SQLiteDatabase db;
    private Context context;
    private BaseDBOpenHelper dbHelper;

    static final String DIARY = "Diary";
    static final String ID = "_id";
    static final String TITLE = "Title";
    static final String DESCRIPTION = "Description";
    static final String CREATED = "Created";
    static final String MODIFIED = "Modified";


    public DatabaseAdapter(Context _context) {
        context = _context;
        dbHelper = new BaseDBOpenHelper(context);
    }

    public void close() {
        db.close();
    }

    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public long insertDiary(Diary diary) {

        ContentValues values = new ContentValues();
        values.put(TITLE, diary.getTitle());
        values.put(DESCRIPTION, diary.getDescription());
        //diary.put(CREATED, new Timestamp(System.currentTimeMillis()));

        return db.insert(DIARY, null, values);
    }



    public void emptyDiary(String table) {
        db.delete(table, null, null);
    }

    public void setupDiary(Diary[] diary) {
        for (int i = 0; i < diary.length; i++) {
            insertDiary(diary[i]);
        }
    }

    public int getId(String table, String signature) {
        int id = 0;
        Cursor cursor = db.query(table, null, "signature=?", new String[]{signature}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex("_id"));
        }
        return id;
    }

    public int getDataId(String table) {
        int id = -1;
        Cursor cursor = db.query(table, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex("_id"));
        }
        return id;
    }

    public Cursor generalWhereStatement(String table, String column, int value) {

        Cursor cursor = db.query(table, null, column + "=?", new String[]{Integer.toString(value)}, null, null, null);
        return cursor;
    }

    public ArrayList<String[]> getAllDiaries(String table) {
        ArrayList<String[]> rows = new ArrayList<String[]>();
        Cursor cursor = db.query(table, null, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String[] content = new String[4];
                    content[0] = Integer.toString(cursor.getInt(cursor.getColumnIndex(ID)));
                    content[1] = cursor.getString(cursor.getColumnIndex(TITLE));
                    content[2] = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                    //GET DATES
                    rows.add(content);
                } while (cursor.moveToNext());
            }
        }

        return rows;
    }




    static class BaseDBOpenHelper extends SQLiteOpenHelper {

        public BaseDBOpenHelper(Context context) {
            super(context, "diary.db", null, 1);
        }

        private static final String CREATE_DIARY = "create table " + DIARY +
                " (" + ID + " integer primary key autoincrement, " + TITLE +
                " text null, " + DESCRIPTION + " text null, " + CREATED + " timestamp null, " + MODIFIED + "timestamp null); ";

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(CREATE_DIARY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {

            _db.execSQL("DROP TABLE IF EXISTS " + DIARY);
            onCreate(_db);
        }
    }
*/}