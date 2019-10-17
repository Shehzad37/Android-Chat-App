package com.example.shehzad.finaltabchat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Shehzad on 9/29/2016.
 */
public class DBHanlder extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;
    UserFragment ud = new UserFragment();

    private static final String DATABASE_NAME ="UserMessages";
    private static final String TABLE_Messages = "Messages";
    //Table Columns names
    private static final String KEY_SIDE = "side";
    private static final String KEY_NAME = "name";
    private static final String KEY_SENT ="sent_messages";
    private static final String KEY_RECV ="recieved_messages";
    private static final String KEY_DATE ="date";


    DBHanlder(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_Messages + "("  + KEY_SIDE + " boolean," + KEY_NAME + " TEXT," + KEY_SENT + " TEXT," + KEY_RECV + " TEXT," + KEY_DATE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_Messages);
            // Creating tables again
            onCreate(db);
        }

    public void addMsg(ChatMessage msg)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, UserFragment.name);

         values.put(KEY_SIDE,msg.left);

        if   (msg.isLeft()){values.put(KEY_RECV, msg.getMessage());}

         else  {values.put(KEY_SENT,msg.getMessage());}

        values.put(KEY_DATE, msg.getTime());
        // Inserting Row
        db.insert(TABLE_Messages, null, values);

        db.close(); // Closing database connection

       // System.out.println("Added to DataBase");
       // System.out.println("SELECT * FROM " + TABLE_Messages + " WHERE " + KEY_NAME + "=" + UserFragment.name);
    }

    public void getAllChatsByName()
    {

        String selectQuery = "SELECT * FROM " + TABLE_Messages + " WHERE " + KEY_NAME + "='" + UserFragment.name+"'";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst())
        {
            do {

                    ChatMessage msg = new ChatMessage();
                    msg.setLeft(cursor.getInt(0) == 1);

                if(msg.isLeft())
                {
                     msg.setMessage(cursor.getString(3));
                     System.out.println("Receiver msg");
                }
                else
                {
                     System.out.println("sender msg");
                     msg.setMessage(cursor.getString(2));
                    msg.setIcon(MainActivity.thePic);
                }
                     msg.setTime((cursor.getString(4)));


                UserFragment.ud.addToList(msg);

            } while (cursor.moveToNext());
        }

    }

    void dropTable()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Messages);
        onCreate(db);


    }
}
