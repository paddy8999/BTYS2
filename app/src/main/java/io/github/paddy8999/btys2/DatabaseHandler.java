package io.github.paddy8999.btys2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;


/**
 * Created by padraig on 20/12/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    public static abstract class TableInfo implements BaseColumns {
        public static final String TABLE_NAME = "entry_table";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_USER_NAME ="name";
        public static final String COLUMN_NAME_NOTES = "notes";
        public static final String COLUMN_NAME_WEIGHT = "weight";
        public static final String COLUMN_NAME_SP02 = "oxygen_in_blood";
        public static final String COLUMN_NAME_PULSE_RATE = "pulse_rate";
        public static final String COLUMN_NAME_BLOOD_PRESSURE_SYS = "blood_pressure_sys";
        public static final String COLUMN_NAME_BLOOD_PRESSURE_DIA = "blood_pressure_dia";
        public static final String COLUMN_NAME_TEMPERATURE = "temperature";
    }

    public static final int DATABASE_VERSION = 10;
    public static final String DATABASE_NAME = "MedicalData.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TableInfo.TABLE_NAME + "(" +
                    TableInfo._ID + " INTEGER PRIMARY KEY," +
                    TableInfo.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    TableInfo.COLUMN_NAME_USER_NAME + TEXT_TYPE + COMMA_SEP +
                    TableInfo.COLUMN_NAME_NOTES + TEXT_TYPE  + COMMA_SEP +
                    TableInfo.COLUMN_NAME_WEIGHT + REAL_TYPE  + COMMA_SEP +
                    TableInfo.COLUMN_NAME_SP02 + REAL_TYPE  + COMMA_SEP +
                    TableInfo.COLUMN_NAME_PULSE_RATE + REAL_TYPE  + COMMA_SEP +
                    TableInfo.COLUMN_NAME_BLOOD_PRESSURE_SYS + REAL_TYPE  + COMMA_SEP +
                    TableInfo.COLUMN_NAME_BLOOD_PRESSURE_DIA + REAL_TYPE  + COMMA_SEP +
                    TableInfo.COLUMN_NAME_TEMPERATURE + REAL_TYPE  +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TableInfo.TABLE_NAME;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database's upgrade policy is to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public static void deleteEntries(SQLiteDatabase db){
        db.execSQL(SQL_DELETE_ENTRIES);
    }



}

