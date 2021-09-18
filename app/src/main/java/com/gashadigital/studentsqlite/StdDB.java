package com.gashadigital.studentsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class StdDB extends SQLiteOpenHelper {
    public static final String DBNAME = "student.db";
    public static final String TABLE_NAME="course";
    public static final int VER=1;

    public StdDB(@Nullable Context context) {
        super(context, DBNAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " +TABLE_NAME+"(id intger primary key,name text, avatar blob)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query ="drop table if exists "+TABLE_NAME+"";
        db.execSQL(query);
    }
}
