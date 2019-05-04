package com.carrillo.movieflix.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.carrillo.movieflix.data.DBModels.*;

public class FavoritesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movieflix.db";
    private static final int DATABASE_VERSION = 1;

    public FavoritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " + FavoritesEntry.TABLE_NAME + " (" +
                FavoritesEntry.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                FavoritesEntry.COLUMN_POSTER_PATH + " STRING NOT NULL, " +
                FavoritesEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoritesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}