package mmt.source.com.babitafuels.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "fuelMng.db";
    public static DbHelper vendorDbHelper = null;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void createVendorDbHelper(Context context){
        if(vendorDbHelper == null){
            vendorDbHelper = new DbHelper(context);
        }
    }

    public static DbHelper getInstance(){
        return vendorDbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("db created is "+db);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}