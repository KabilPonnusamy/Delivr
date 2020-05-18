package com.delivr.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.delivr.backend.models.CAdddress;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.delivr/databases/";

    private static String DB_NAME = "delivrboy.db";
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public CAdddress getAddress(String zipCode) {
        CAdddress cadddress = new CAdddress();

        String selectQuery = "SELECT POSTALCODE, REPLACE(ROADNUMBER || \", \" || ROADNAME ||\", \" || IFNULL(BUILDINGNAME,'') || \", \" || POSTALCODE,\", ,\",\",\") Address,\n" +
                "LATITUDE, LONGITUDE \n" +
                "FROM Addlookup\n" +
                "WHERE POSTALCODE = '" + zipCode + "'" +
                "LIMIT 0,100";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                cadddress = new CAdddress(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return cadddress;
    }

    public List<CAdddress> getFilteredAddress(String userinput) {
        List<CAdddress> cadddress = new ArrayList<CAdddress>();

        String selectQuery = "SELECT POSTALCODE, REPLACE(ROADNUMBER || \", \" || ROADNAME ||\", \" || IFNULL(BUILDINGNAME,'') || \", \" || POSTALCODE,\", ,\",\",\") Address,\n" +
                "LATITUDE, LONGITUDE \n" +
                "FROM Addlookup\n" +
                "WHERE ROADNAME LIKE '%" + userinput + "%'" +
                "OR BUILDINGNAME LIKE '%" + userinput + "%'" +
                "OR POSTALCODE LIKE '%" + userinput + "%'" +
                "LIMIT 0,100";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                cadddress.add(new CAdddress(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return cadddress;
    }


    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            //do nothing - database already exist
        } else {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            //database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}