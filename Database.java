package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    Context context;
    public static final String DatabaseName = "database";
    public static final int DatabaseVersion = 1;
    public static final String TableName = "tables";
    public static final String Id = "id";
    public static final String Title = "name";
    public static final String Phone = "phone";
    public static final String Desc = "description";
    public static final String Date = "date";
    public static final String Location = "location";

    public Database(@Nullable Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TableName +
                " (" + Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Title + " TEXT, " + Phone + " TEXT, " + Desc + " TEXT, " + Date + " TEXT, " + Location + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }

    void add(String title, String phone, String desc, String date, String location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Title, title);
        cv.put(Phone, phone);
        cv.put(Desc, desc);
        cv.put(Date, date);
        cv.put(Location, location);
        long resultValue = db.insert(TableName,null,cv);
        if (resultValue == -1)
        {
            Toast.makeText(context, "Info not added!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Info has been added!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData()
    {
        String query = "SELECT * FROM " + TableName;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;
        if(database!= null)
        {
            cursor = database.rawQuery(query,null);
        }
        return cursor;
    }

    void delete(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result1 = db.delete(TableName,"id=?", new String[]{id});
        if(result1 == -1)
        {
            Toast.makeText(context, "Unable to delete info!", Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(context, "Info has been deleted!", Toast.LENGTH_SHORT).show();
        }
    }
}