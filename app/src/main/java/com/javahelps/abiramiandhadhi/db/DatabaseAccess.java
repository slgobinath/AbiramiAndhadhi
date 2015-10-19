package com.javahelps.abiramiandhadhi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.javahelps.abiramiandhadhi.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gobinath on 8/28/15.
 */
public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public Song getSong(int no) {
        Song song = new Song();
        Cursor cursor = database.rawQuery("SELECT * FROM Abirami_Andhadhi WHERE song_no = " + no, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            song.setNumber(no);
            song.setSong(cursor.getString(1));
            song.setDescription(cursor.getString(2));
            song.setBookmarked(cursor.getInt(3) != 0);
            cursor.moveToNext();
            break;
        }
        cursor.close();
        return song;
    }

    public void addSookmark(int no) {
        ContentValues values = new ContentValues();
        values.put("is_bookmarked", 1);
        database.update("Abirami_Andhadhi", values, "song_no = " + no, null);
    }

    public void removeSookmark(int no) {
        ContentValues values = new ContentValues();
        values.put("is_bookmarked", 0);
        database.update("Abirami_Andhadhi", values, "song_no = " + no, null);
    }

    public List<Song> getBookmarkedSongs() {
        List<Song> songs = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM Abirami_Andhadhi WHERE is_bookmarked = 1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Song song = new Song();
            song.setNumber(cursor.getInt(0));
            song.setSong(cursor.getString(1));
            song.setDescription(cursor.getString(2));
            song.setBookmarked(cursor.getInt(3) != 0);
            songs.add(song);
            cursor.moveToNext();
        }
        cursor.close();
        return songs;
    }
}