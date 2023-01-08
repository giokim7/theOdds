package com.giorgiolupo.theodds;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AllChallengesSQL extends SQLiteOpenHelper {

    private static final String DB_NAME = "mainDB";
    private static final int DB_VERSION = 1;
    SQLiteDatabase db = this.getWritableDatabase();
    Context context;

    // creating a constructor for our database handler.
    public AllChallengesSQL(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS challenges(id INTEGER PRIMARY KEY AUTOINCREMENT, uniqueId VARCHAR)";
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //create and insert ID
    public void insertUniqueIdSQL(String uniqueId) {
        onCreate(db);
        try {
            db.execSQL("INSERT INTO challenges(uniqueId) VALUES('" + uniqueId + "' )");
        } catch (SQLException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //return all challenges
   public List getAllChallenges(){
       List<String> temp = new ArrayList<>();
       Cursor allRows  = db.rawQuery("SELECT DISTINCT uniqueId FROM challenges ORDER BY uniqueId DESC", null);
       if (allRows.moveToFirst() ){
           String[] columnNames = allRows.getColumnNames();
           do {
               for (String name: columnNames) {
                   temp.add(allRows.getString(allRows.getColumnIndex(name)));
               }

           } while (allRows.moveToNext());
       }

       return temp;
   }


}
