package mmt.source.com.babitafuels.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.util.DbHelper;

public class UserDetailsDao {

    SQLiteDatabase db;

    public UserDetailsDao() {
        if(DbHelper.getInstance() != null){
            db = DbHelper.getInstance().getWritableDatabase();
            db.execSQL("CREATE TABLE IF NOT EXISTS UserDetails(usrId INT, mobileNumber VARCHAR(20), password VARCHAR(20), usrType VARCHAR(25), usrEmail VARCHAR(50));");
            db.execSQL("CREATE TABLE IF NOT EXISTS AccessCtrl(mobileNumber VARCHAR(500));");
        }else {
            throw new RuntimeException("DBHelper not initialized");
        }
    }

    public void addUsrDetails() {
        FuelMnt fm_inst = FuelMnt.getInstance();
        db.execSQL("INSERT INTO UserDetails VALUES('"+fm_inst.getUsrInfo().getUsrId()+"','"+fm_inst.getUsrInfo().getMobileNum()+"','"+fm_inst.getUsrInfo().getPassword()+"','"+fm_inst.getUsrInfo().getRegType()+"','"+fm_inst.getUsrInfo().getEmailId()+"');");
    }

    public void addAccessCtrl(String access) {
        db.execSQL("INSERT INTO AccessCtrl VALUES('"+access+"');");
    }

    public void deleteUserDetails() {
        db.delete("UserDetails", null, null);
    }

    public void deleteAccessCtrl() {
        db.delete("AccessCtrl", null, null);
    }

    public void getUserDetails() {
        Cursor cursor = db.rawQuery("Select * from UserDetails", new String[]{});
        FuelMnt fm_inst = FuelMnt.getInstance();

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            try {
                do {

                    fm_inst.getUsrInfo().setUsrId(cursor.getString(0));
                    fm_inst.getUsrInfo().setMobileNum(cursor.getString(1));
                    fm_inst.getUsrInfo().setPassword(cursor.getString(2));
                    fm_inst.getUsrInfo().setRegType(cursor.getString(3));
                    fm_inst.getUsrInfo().setEmailId(cursor.getString(4));
                }while (cursor.moveToNext());
            }catch (Exception e) {
                System.out.println("shiva failed to extract from sqllite ");
            }

        }
        else {
            fm_inst.getUsrInfo().setUsrId("-1");
        }
    }


    public String getAccessCtrl() {
        Cursor cursor = db.rawQuery("Select * from AccessCtrl", new String[]{});
        FuelMnt fm_inst = FuelMnt.getInstance();

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            try {
                do {

                    return  (cursor.getString(0));

                }while (cursor.moveToNext());
            }catch (Exception e) {

                System.out.println("shiva failed to extract from sqllite ");
                return "-1";
            }

        }
        else {
            return "-1";
        }
    }
}
