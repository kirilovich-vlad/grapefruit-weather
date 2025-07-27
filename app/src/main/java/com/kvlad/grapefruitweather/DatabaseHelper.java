package com.kvlad.grapefruitweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Table and column names for the saved locations table.
    // Columns in version 1 - ID, location name, location coordinates, order number (bigger -> lower on display).
    private final String SAVED_LOCATIONS_TABLE = "saved_locations";
    private final String COLUMN_LOCATION_ID = "id";
    private final String COLUMN_LOCATION_NAME = "name";
    private final String COLUMN_LOCATION_COORDINATES = "coordinates";
    private final String COLUMN_LOCATION_ORDER_NUMBER = "order_number";

    public String getSAVED_LOCATIONS_TABLE() {
        return SAVED_LOCATIONS_TABLE;
    }

    public String getCOLUMN_LOCATION_ID() {
        return COLUMN_LOCATION_ID;
    }

    public String getCOLUMN_LOCATION_NAME() {
        return COLUMN_LOCATION_NAME;
    }

    public String getCOLUMN_LOCATION_COORDINATES() {
        return COLUMN_LOCATION_COORDINATES;
    }

    public String getCOLUMN_LOCATION_ORDER_NUMBER() {
        return COLUMN_LOCATION_ORDER_NUMBER;
    }

    public DatabaseHelper(@Nullable Context context) {
        super(context, "GWeatherDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createLocationListTable = "CREATE TABLE " + SAVED_LOCATIONS_TABLE + " (" +
                COLUMN_LOCATION_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                COLUMN_LOCATION_NAME + " TEXT," +
                COLUMN_LOCATION_COORDINATES + " TEXT," +
                COLUMN_LOCATION_ORDER_NUMBER + " INTEGER NOT NULL)";

        db.execSQL(createLocationListTable);
    }

    // Add new location to the database.
    public long addLocation(String name, String coordinates) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LOCATION_NAME, name);
        cv.put(COLUMN_LOCATION_COORDINATES, coordinates);
        cv.put(COLUMN_LOCATION_ORDER_NUMBER, this.getLastOrderNumber() + 1);
        return db.insert(SAVED_LOCATIONS_TABLE, null, cv);
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

    // Get ID of the location with last (the biggest) order ID.
    public int getLastOrderNumber() {
        int maxOrderNumber;
        SQLiteDatabase readDB = this.getReadableDatabase();
        String maxIDQuery = "SELECT " + COLUMN_LOCATION_ORDER_NUMBER +
                " FROM " + SAVED_LOCATIONS_TABLE +
                " ORDER BY " + COLUMN_LOCATION_ORDER_NUMBER + " DESC LIMIT 1";
        Cursor cursor = readDB.rawQuery(maxIDQuery, null);
        if (cursor.moveToFirst()) {
            maxOrderNumber = cursor.getInt(0);
        } else maxOrderNumber = -1;
        cursor.close();
        return maxOrderNumber;
    }

    // Returns ArrayList of every saved location.
    public List<SavedLocation> getAllLocations() {
        ArrayList<SavedLocation> result = new ArrayList<>();
        String query = "SELECT * FROM " +
                SAVED_LOCATIONS_TABLE;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String coordinates = cursor.getString(2);
                int orderNumber = cursor.getInt(3);
                SavedLocation location = new SavedLocation(id, name, coordinates, orderNumber);
                result.add(location);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: Has to be implemented from the next release
    }
}
