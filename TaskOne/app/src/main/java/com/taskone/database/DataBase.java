package com.taskone.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase {
    public SQLiteDatabase db;
    public Context context;
    private DB_DATA db_data;

    public DataBase(Context con) {
        this.context = con;
        db_data = new DB_DATA(context, DB_PARAMS.ids.DB_NAME, null, DB_PARAMS.ids.DB_VERSION);
        db = db_data.getReadableDatabase();
    }

    public void insertNewsFeedListTable(String name) {
        SQLiteDatabase db = db_data.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_PARAMS.COLUMNS.favListColumn.name, name);

        db.insert(DB_PARAMS.TABLES.favListTable, null, values);
    }

    public void updateNewsFeedListTable(String name, String pic) {
        SQLiteDatabase db = db_data.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_PARAMS.COLUMNS.favListColumn.name, name);

        db.update(DB_PARAMS.TABLES.favListTable, values,
                DB_PARAMS.COLUMNS.favListColumn.name + "='" + name + "'",
                null);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = db_data.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + DB_PARAMS.TABLES.favListTable, null);
        return res;
    }

    public int deleteData(String name) {
        SQLiteDatabase db = db_data.getWritableDatabase();
        return db.delete(DB_PARAMS.TABLES.favListTable, "name = ?", new String[]{name});
    }
//    public void deleteContact(FavListModel contact) {
//        SQLiteDatabase db = db_data.getWritableDatabase();
//        db.delete(DB_PARAMS.TABLES.favListTable, DB_PARAMS.COLUMNS.favListColumn.id + " = ?",
//                new String[]{String.valueOf(contact.getId())});
//        db.close();
//    }

    private class DB_DATA extends SQLiteOpenHelper {
        DB_DATA(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DB_PARAMS.ids.DB_NAME, factory, DB_PARAMS.ids.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_QUERIES.favListData());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
