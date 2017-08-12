package com.xrdcode.pertemuan3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;
import android.widget.Toast;

import com.xrdcode.pertemuan3.model.MovieDetail;
import com.xrdcode.pertemuan3.model.Movies;

/**
 * Created by reysd on 12/08/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    //database name
    public static final String DATABASE_NAME = "MovieDB";
    //table
    public static final String TABLE_FAVORITE = "favorited";
    //table column
    public static final String COL_ID = "id";
    public static final String COL_POSTER = "poster_path";
    public static final String COL_TITLE = "title";
    public static final String COL_OVERVIEW = "overview";

    private Context mContext;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_FAVORITE + "("
                + COL_ID + " INTEGER,"
                + COL_TITLE + " TEXT,"
                + COL_OVERVIEW + " TEXT,"
                + COL_POSTER  + " TEXT )";
        db.execSQL(CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);

        // Create tables again
        onCreate(db);
    }

    /**
     * CRUD SECTION
     */

    public void setFavourite(MovieDetail movieDetail) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ID, movieDetail.id);
        values.put(COL_TITLE, movieDetail.title);
        values.put(COL_OVERVIEW, movieDetail.overview);
        values.put(COL_POSTER, movieDetail.poster_path);

        db.insert(TABLE_FAVORITE, null, values);
        db.close();
        Toast.makeText(mContext, "Berhasil ditambahkan ke favorit...", Toast.LENGTH_SHORT).show();
    }

    public void unFavorite(MovieDetail movieDetail) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_FAVORITE, COL_ID + " = ?", new String[]{String.valueOf(movieDetail.id)});
        db.close();
        Toast.makeText(mContext, "Berhasil dihapus dari favorit...", Toast.LENGTH_SHORT).show();
    }

    public boolean checkFavorite(MovieDetail movieDetail) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FAVORITE + " WHERE "
                + COL_ID + " = " + String.valueOf(movieDetail.id), null);
        if(cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public Movies getAllFavorite() {
        Movies movies = new Movies();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FAVORITE , null);

        int i = 0;
        while(cursor.moveToFirst()) {
            Movies.Result temp = new Movies.Result();
            temp.id = cursor.getInt(0);
            temp.title = cursor.getString(1);
            temp.overview = cursor.getString(2);
            temp.poster_path = cursor.getString(3);

            movies.results.add(temp);
        }
        return movies;
    }
}
