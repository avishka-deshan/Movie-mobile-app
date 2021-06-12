package com.example.movieapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "MovieDetails.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table MovieDetails(title TEXT primary key, year TEXT, director TEXT, actors TEXT, rating TEXT, review TEXT, favourites TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists MovieDetails");
    }

    public Boolean saveMovieData(String title, String year, String director, String actors, String rating, String review){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("year", year);
        contentValues.put("director", director);
        contentValues.put("actors", actors);
        contentValues.put("rating", rating);
        contentValues.put("review", review);
        contentValues.put("favourites","No");
        long result = DB.insert("MovieDetails", null, contentValues);
        return result != -1;
    }

    public void saveFavourites(String favourites, String title){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put("favourites", favourites);
        long result = sqLiteDatabase.update("MovieDetails", values, "title=?", new String[]{title});
    }

    public void updateUserData(String title, String year, String director, String actors, String rating, String review, String favourites){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("year", year);
        contentValues.put("director", director);
        contentValues.put("actors", actors);
        contentValues.put("rating", rating);
        contentValues.put("review", review);
        contentValues.put("favourites",favourites);
        Cursor cursor = DB.rawQuery("Select * from MovieDetails where title = ?", new String[]{title});
        if (cursor.getCount() > 0) {
            long result = DB.update("MovieDetails", contentValues, "title=?", new String[]{title});
        }
    }

    public Cursor searchData(String userInput){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select title from MovieDetails where title like ? OR director like ? OR actors like ?",new String [] {userInput,userInput,userInput});
    }

    /*public Boolean updateUserData(String id, String name, String address, String age, String position ) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("address", address);
        contentValues.put("age", age);
        contentValues.put("position", position);
        Cursor cursor = DB.rawQuery("Select * from EmployeeDetails where id = ?", new String[]{id});
        if (cursor.getCount() > 0) {
            long result = DB.update("EmployeeDetails", contentValues, "id=?", new String[]{id});
            return result != -1;
        }
        else {
            return false;
        }
    }

    public Boolean deleteUserData(String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from EmployeeDetails where id = ?", new String[]{id});
        if (cursor.getCount() > 0) {

            long result = DB.delete("EmployeeDetails", "id=?", new String[]{id});
            return result != -1;
        }
        else {
            return false;
        }
    }
*/
    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from MovieDetails", null);
        return  cursor;
    }


}
