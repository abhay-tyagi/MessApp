package com.example.abhay.messapp;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;
import android.widget.Toast;


public class MessDBHandler extends SQLiteOpenHelper{

    private static final String TAG = "com.example.abhay.messapp";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mess.db";

    public static final String TABLE_USERS = "users";
    public static final String TABLE_MESSES = "messes";
    public static final String TABLE_MESSINSTANCES = "messinstances";
    public static final String TABLE_SCANINSTANCES = "scaninstances";


    public MessDBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, MessDBHandler.DATABASE_NAME, factory, MessDBHandler.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String q = "CREATE TABLE " + TABLE_MESSES +
                " (MESSID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MESSNAME TEXT, " +
                "MESSHEAD TEXT, " +
                "DAILYCOST INTEGER, " +
                "CAPACITY INTEGER, " +
                "NONVEGCOST INTEGER );";
        db.execSQL(q);

        q = "CREATE TABLE " + TABLE_USERS +
                " (USERNAME TEXT PRIMARY KEY, " +
                "PASSWORD TEXT, " +
                "YEAR INTEGER, " +
                "MESS REFERENCES MESS, " +
                "COURSE TEXT, " +
                "BRANCH TEXT);";
        db.execSQL(q);

        q = "CREATE TABLE " + TABLE_MESSINSTANCES +
                " (MESSINSTANCEID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MESS REFERENCES MESS, " +
                "BREAKFASTTOTAL INTEGER, " +
                "LUNCHTOTAL INTEGER, " +
                "DINNERTOTAL INTEGER, " +
                "MIDATE DATE);";
        db.execSQL(q);

        q = "CREATE TABLE " + TABLE_SCANINSTANCES +
                " (SCANINSTANCEID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MESS REFERENCES MESS, " +
                "USERSCANNER TEXT, " +
                "SCANDATE DATE, " +
                "SCANTIME INTEGER);";
        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put("USERNAME", user.getUsername());
        values.put("PASSWORD", user.getPassword());
        values.put("YEAR", user.getYear());
        values.put("MESS", user.getMess());
        values.put("COURSE", user.getCourse());
        values.put("BRANCH", user.getBranch());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public void addMess(Mess mess) {
        ContentValues values = new ContentValues();
        values.put("MESSNAME", mess.getMessName());
        values.put("MESSHEAD", mess.getMessHead());
        values.put("DAILYCOST", mess.getDailyCost());
        values.put("CAPACITY", mess.getCapacity());
        values.put("NONVEGCOST", mess.getNonvegCost());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_MESSES, null, values);
        db.close();
    }

    public void addMessInstance(MessInstance messInstance) {
        ContentValues values = new ContentValues();
        values.put("MESS", messInstance.getMess());
        values.put("BREAKFASTTOTAL", messInstance.getBreakfastTotal());
        values.put("LUNCHTOTAL", messInstance.getLunchTotal());
        values.put("DINNERTOTAL", messInstance.getDinnerTotal());
        values.put("MIDATE", messInstance.getDate());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_MESSINSTANCES, null, values);
        db.close();
    }

    public void addScanInstance(ScanInstance scanInstance) {
        ContentValues values = new ContentValues();
        values.put("MESS", scanInstance.getMess());
        values.put("USERSCANNER", scanInstance.getUserScanner());
        values.put("SCANDATE", scanInstance.getScanDate());
        values.put("SCANTIME", scanInstance.getScanTime());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_SCANINSTANCES, null, values);
        db.close();
    }

    public void deleteUser(String username) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USERS + " WHERE USERNAME" + "=\"" + username + "\";");
    }

    public void deleteMess(String messname) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MESSES + " WHERE MESSNAME" + "=\"" + messname + "\";");
    }

    public void deleteMessInstance(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MESSINSTANCES + " WHERE MESSINSTANCEID" + "=\"" + id + "\";");
    }

    public void deleteScanInstance(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SCANINSTANCES + " WHERE SCANINSTANCEID" + "=\"" + id + "\";");
    }

    public String getEntireTable(String tableName) {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        if(tableName.equals("MESS"))
            tableName = TABLE_MESSES;
        else if(tableName.equals("USER"))
            tableName = TABLE_USERS;
        else if(tableName.equals("MESSINSTANCE"))
            tableName = TABLE_MESSINSTANCES;
        else if(tableName.equals("SCANINSTANCE"))
            tableName = TABLE_SCANINSTANCES;

        Log.i(TAG, tableName);

        String q = "SELECT * FROM " + tableName + ";";

        Cursor c = db.rawQuery(q, null);
        c.moveToFirst();

        if(c.isAfterLast())
            Log.i(TAG, "Empty");
        else
            Log.i(TAG, "Not empty");

        if(tableName.equals(TABLE_MESSES)) {
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex("MESSNAME")) != null) {
                    dbString += c.getString(c.getColumnIndex("MESSNAME"));
                    dbString += " ";
                    dbString += c.getString(c.getColumnIndex("MESSID"));
                    dbString += "\n";
                }
                c.moveToNext();
            }
        }
        else if(tableName.equals(TABLE_USERS)) {
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex("USERNAME")) != null) {
                    dbString += c.getString(c.getColumnIndex("USERNAME"));
                    dbString += " ";
                    dbString += c.getString(c.getColumnIndex("MESS"));
                    dbString += "\n";
                }
                c.moveToNext();
            }
        }
        else if(tableName.equals(TABLE_MESSINSTANCES)) {
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex("MIDATE")) != null) {
                    dbString += c.getString(c.getColumnIndex("MIDATE"));
                    dbString += " ";
                    dbString += c.getString(c.getColumnIndex("MESS"));
                    dbString += "\n";
                }
                c.moveToNext();
            }
        }
        else if(tableName.equals(TABLE_SCANINSTANCES)) {
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex("USERSCANNER")) != null) {
                    dbString += c.getString(c.getColumnIndex("USERSCANNER"));
                    dbString += " ";
                    dbString += c.getString(c.getColumnIndex("MESS"));
                    dbString += "\n";
                }
                c.moveToNext();
            }
        }

        c.close();
        db.close();

        return dbString;
    }

    public Cursor getResult(String q) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(q, null);
        c.moveToFirst();
//        c.close();

        return c;
    }
}