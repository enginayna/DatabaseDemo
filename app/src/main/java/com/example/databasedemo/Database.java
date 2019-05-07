package com.example.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private static final int VERSIYON = 1;
    private static final String DATABASE_NAME = "Sozluk";
    private static final String TABLE_NAME = "words";
    private Dictionary dictionary;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, VERSIYON);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       /* String sgl = "CREATE TABLE " + TABLE_NAME + " ("+dictionary.getWord_id() + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + dictionary.getIngilizce() + " TEXT ," + dictionary.getTurkce() + " TEXT )";*/
        String sql = "CREATE TABLE words (word_id INTEGER PRIMARY KEY AUTOINCREMENT , ingilizce TEXT , turkce TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addWord(Dictionary dictionary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ingilizce", dictionary.getIngilizce());
        values.put("turkce", dictionary.getTurkce());
        long word_id = db.insertOrThrow(TABLE_NAME, null, values);
        db.close();
        return word_id;
    }

    public ArrayList<Dictionary> allWords() {
        ArrayList<Dictionary> wordList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor read = db.rawQuery("SELECT*FROM words", null);
        while (read.moveToNext()) {
            Dictionary dictionary = new Dictionary(
                    read.getInt(read.getColumnIndex("word_id")),
                    read.getString(read.getColumnIndex("ingilizce")),
                    read.getString(read.getColumnIndex("turkce")));
            wordList.add(dictionary);
        }
        return wordList;
    }

    public void delete(int word_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("words" , "word_id = ?" , new String[]{String.valueOf(word_id)});
        db.close();
    }

    public void update(int word_id , String ingilizce , String turkce){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ingilizce" , ingilizce);
        values.put("turkce" , turkce);
        db.update("words" ,values , "word_id=?" , new String[]{String.valueOf(word_id)});
        db.close();
    }

}
