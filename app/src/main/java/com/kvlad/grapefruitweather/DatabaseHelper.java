package com.kvlad.grapefruitweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Table and column names for the saved locations table.
    // Columns in version 1 - ID, location name, location coordinates, last access time.
    private static final String SAVED_LOCATIONS_TABLE = "SAVED_LOCATIONS_TABLE";
    private static final String COLUMN_LOCATION_ID = "ID";
    private static final String COLUMN_LOCATION_NAME = "NAME";
    private static final String COLUMN_LOCATION_COORDINATES = "COORDINATES";
    private static final String COLUMN_LOCATION_LAST_ACCESS_TIME = "LAST_ACCESS_TIME";
    
    // Table and column names for the settings table.
    // Columns in version 1 - Setting ID, Setting name, setting status.
    private static final String SETTINGS_TABLE = "SETTINGS_TABLE";
    private static final String COLUMN_SETTING_ID = "ID";
    private static final String COLUMN_SETTING_NAME = "NAME";
    private static final String COLUMN_SETTING_STATUS = "STATUS";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "GWeatherDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSettingsTable = "CREATE TABLE " + SETTINGS_TABLE + " (" +
                COLUMN_SETTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_SETTING_NAME + " TEXT," +
                COLUMN_SETTING_STATUS + " TEXT)";

        String createLocationListTable = "CREATE TABLE " + SAVED_LOCATIONS_TABLE + " (" +
                COLUMN_LOCATION_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                COLUMN_LOCATION_NAME + " TEXT," +
                COLUMN_LOCATION_COORDINATES + " TEXT," +
                COLUMN_LOCATION_LAST_ACCESS_TIME + " INTEGER)";

        db.execSQL(createSettingsTable);
        db.execSQL(createLocationListTable);
    }

    // Add new location to the database.
    public boolean addLocation(String name, String coordinates) {
        SQLiteDatabase db = getWritableDatabase();

        String accessTime = LocalDateTime.now().toString();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LOCATION_NAME, name);
        cv.put(COLUMN_LOCATION_COORDINATES, coordinates);
        cv.put(COLUMN_LOCATION_LAST_ACCESS_TIME, accessTime);

        long insert = db.insert(SAVED_LOCATIONS_TABLE, null, cv);
        return insert != -1;
    }

    // Delete saved location from the database.
    public boolean removeLocation(SavedLocation location) {
        SQLiteDatabase db = getWritableDatabase();

        int result = db.delete(
                SAVED_LOCATIONS_TABLE,
                COLUMN_LOCATION_ID + " = ?",
                new String[]{String.valueOf(location.getId())}
        );

        return result != 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: Has to be implemented from the next release
    }
}
