package com.finale.phiser.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class RegisterDB {


    public static final String KEY_NAME="Name";
    public static final String KEY_REGNO="RegNo";
    public static final String KEY_PHONE="Phone";
    public static final String KEY_USERNAME="Username";
    public static final String KEY_PASSWORD="Password";

    private static final String DATABASE_NAME="asig";
    private static final String DATABASE_TABLE="register";
    private static final int DATABASE_VERSION=1;

    private DbHelper ourHelper;
    private Context ourContext;
    private SQLiteDatabase ourDatabase;


    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase DB) {
            // TODO Auto-generated method stub
            DB.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_NAME + " TEXT NOT NULL, " +
                    KEY_REGNO + " INTEGER NOT NULL, " + KEY_PHONE + " INTEGER NOT NULL, " + KEY_USERNAME + " TEXT NOT NULL, " + KEY_PASSWORD + " TEXT NOT NULL);");
        }
        @Override
        public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            DB.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(DB);
        }

    }
    public RegisterDB(Context c){
        ourContext= c;
    }
    public RegisterDB open()throws SQLException{
        ourHelper= new DbHelper(ourContext);
        ourDatabase= ourHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        ourHelper.close();
    }
    public RegisterDB() {
    }

    public long createEntry(String Name, int RegNo, int Phone, String Username, String Password) {
        ContentValues cv= new ContentValues();

        cv.put(KEY_NAME, Name);
        cv.put(KEY_REGNO, RegNo);
        cv.put(KEY_PHONE, Phone);
        cv.put(KEY_USERNAME, Username);
        cv.put(KEY_PASSWORD, Password);
        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }
    public String searchPass(String Username) {
        ourDatabase= ourHelper.getWritableDatabase();
        String query = "SELECT Username, Password FROM register";
        Cursor cursor = ourDatabase.rawQuery(query, null);
        String a, b;
        b = "not found";
        if (cursor.moveToFirst()){
            do {
                a = cursor.getString(0);
                if (a.equals(Username)){
                    b = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return b;
    }

    public String getName() {
        String[]  columns=new String[]{KEY_NAME,KEY_REGNO,KEY_PHONE};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        if (c!= null){
            c.moveToFirst();
            String Name = c.getString(0);
            return Name;

        }

        return null;
    }

    public String getRegNo() {
        String[]  columns=new String[]{KEY_NAME,KEY_REGNO,KEY_PHONE};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        if (c!= null){
            c.moveToFirst();
            String RegNo = c.getString(1);
            return RegNo;

        }

        return null;
    }

    public String getPhone() {
        String[]  columns=new String[]{KEY_NAME,KEY_REGNO,KEY_PHONE};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        if (c!= null){
            c.moveToFirst();
            String Phone = c.getString(2);
            return Phone;

        }

        return null;
    }


}
