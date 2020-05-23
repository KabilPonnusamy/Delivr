package com.delivr.ui.LocalDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by govindaraj on 08/08/18.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_PROFILE_TABLE = "create table " + DbContract.PROFILE_TABLE + " ( " +
            DbContract.P_ID + " integer primary key autoincrement, " +
            DbContract.P_USER_ID + " text, " +
            DbContract.P_IMAGE_PATH + " text, " +
            DbContract.P_CREATED + " text);" ;


    public static final String DROP_PROFILE_TABLE = "drop table if EXISTS " + DbContract.PROFILE_TABLE;


    public DbHelper(Context context) {
        super(context, DbContract.DATABASE_NAME, null, DATABASE_VERSION);
                    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROFILE_TABLE);
                    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_PROFILE_TABLE);
        onCreate(db);
             }

    public void saveProfile(SQLiteDatabase sqLiteDatabase, String userId, String imagePath,
                           String date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.P_USER_ID, userId);
        contentValues.put(DbContract.P_IMAGE_PATH, imagePath);
        contentValues.put(DbContract.P_CREATED, date);
        sqLiteDatabase.insert(DbContract.PROFILE_TABLE, null, contentValues);
                }

    public Cursor readProfile(SQLiteDatabase sqLiteDatabase, String user_id) {
        String selection = DbContract.P_USER_ID + " = ? ";
        String args[] = {user_id};

        String coloumns[] = {DbContract.P_ID, DbContract.P_USER_ID,
                DbContract.P_IMAGE_PATH, DbContract.P_CREATED};
        Cursor CR = sqLiteDatabase.query(DbContract.PROFILE_TABLE, coloumns, selection, args,
                null, null, null);
        return CR;
                }

    public void deleteProfile(String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbContract.PROFILE_TABLE, DbContract.P_USER_ID + " != ? ",
                new String[]{userId});
        db.close();
                }

    public void updateProfile(String userId, String local_path, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.P_IMAGE_PATH, local_path);
        String selection = DbContract.P_USER_ID + " LIKE ?";
        String[] selection_args = {userId};
        database.update(DbContract.PROFILE_TABLE, contentValues, selection, selection_args);
                }

    /***************   Removing Local DB   ************/
    public void removeLocalDatabase(SQLiteDatabase database) {
        database.delete(DbContract.PROFILE_TABLE,null,null);
                    }
            }
